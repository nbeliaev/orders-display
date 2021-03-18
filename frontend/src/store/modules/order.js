export default {
    actions: {
        async fetchOrders(ctx, workplaceId) {
            const resp = await fetch("/api/v1/orders?filter=" + workplaceId)
            const data = await resp.json()
            ctx.commit('updateOrders', data)
        },
        updateOrderItemStatus({commit}, item) {
            commit('updateOrderItemStatus', item)
        },
        clearState({commit}) {
            commit('clearState')
        }
    },
    mutations: {
        clearState(state) {
            state.orders = []
        },
        updateOrders(state, orders) {
            state.orders = orders
        },
        updateOrderItemStatus(state, item) {
            if (item.status === 'new') {
                item.status = 'in-process'
            } else if (item.status === 'in-process') {
                item.status = 'completed'
            } else {
                item.status = 'new'
            }
        }
    },
    state() {
        return {
            orders: []
        }
    },
    getters: {
        allOrders(state) {
            return state.orders
        },
        activeOrdersNumber(state) {
            return state.orders.map(order => {
                return {
                    ...order, items: order.items.filter(item => item.status !== 'completed')
                }
            }).filter(i => i.items.length).length
        }
    }
}