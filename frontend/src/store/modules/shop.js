export default {
    actions: {
        async fetchShops(ctx, clientUuid) {
            const url = '/api/v1/clients/' + clientUuid + '/shops'
            const resp = await fetch(url)
            if (resp.status >= 400 && resp.status < 600) {
                throw new Error('Bad server response')
            }
            const data = await resp.json()
            ctx.commit('updateShops', data)
        }
    },
    mutations: {
        updateShops(state, shops) {
            state.shops = shops
        }
    },
    state() {
        return {
            shops: []
        }
    },
    getters: {
        allShops(state) {
            return state.shops
        }
    }
}