export default {
    actions: {
        async fetchWorkplaces(ctx, params) {
            const url = '/api/v1/clients/' + params.clientUuid + '/shops/' + params.shopUuid + '/workplaces'
            const resp = await fetch(url)
            if (resp.status >= 400 && resp.status < 600) {
                throw new Error('Bad server response')
            }
            const data = await resp.json()
            ctx.commit('updateWorkplaces', data)
        }
    },
    mutations: {
        updateWorkplaces(state, workplaces) {
            state.workplaces = workplaces
        }
    },
    state() {
        return {
            workplaces: []
        }
    },
    getters: {
        allWorkplaces(state) {
            return state.workplaces
        },
        findWorkplaceById(state) {
            return uuid => {
                const empty = {uuid: '', name: ''}
                if (uuid === undefined) {
                    return empty
                }
                const workplace = state.workplaces.find(i => i.uuid === uuid)
                if (workplace === undefined) {
                    return empty
                } else {
                    return workplace
                }
            }
        }
    }
}