export default {
    actions: {
        updateBreadcrumbs(ctx, params) {
            if (params.breadcrumbs) {
                params.breadcrumbs.forEach(i => {
                    if (i.link) {
                        i.link = i.link.replace(':clientUuid', params.clientUuid)
                        i.link = i.link.replace(':shopUuid', params.shopUuid)
                    }
                })
                ctx.commit('updateBreadcrumbs', params.breadcrumbs)
            }
        }
    },
    mutations: {
        updateBreadcrumbs(state, breadcrumbs) {
            state.list = breadcrumbs
        }
    },
    state() {
        return {
            list: []
        }
    },
    getters: {
        allBreadcrumbs(state) {
            return state.list
        },
        showWorkplace(state) {
            return state.list.length === 3
        }
    }
}