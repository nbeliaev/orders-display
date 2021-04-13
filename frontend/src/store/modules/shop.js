export default {
    actions: {
        async fetchShops(ctx, clientId) {
            const url = '/api/v1/clients/' + clientId + '/shops'
            const resp = await fetch(url)
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