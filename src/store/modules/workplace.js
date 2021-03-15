import Workplaces from "@/randomizer/workplaces.json";

export default {
    actions: {
        fetchWorkplaces(ctx) {
            ctx.commit('updateWorkplaces', Workplaces)
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
            return id => {
                const empty = {id: '', name: ''}
                if (id === undefined) {
                    return empty
                }
                const workplace = state.workplaces.find(i => i.id === id)
                if (workplace === undefined) {
                    return empty
                } else {
                    return workplace
                }
            }
        }
    }
}