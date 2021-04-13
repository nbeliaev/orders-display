import HTTP_STATUS_CODES from 'http-status-enum';
import HTTP_METHODS from 'http-methods-enum';
import ORDER_ITEM_STATUSES from '@/enums/order-item-statuses-enum'

export default {
    actions: {
        async fetchOrders(ctx, params) {
            const url = '/api/v1/clients/' + params.clientUuid + '/shops/' + params.shopUuid + '/workplaces/' + params.workplaceUuid + '/orders'
            const resp = await fetch(url)
            const data = await resp.json()
            ctx.commit('updateOrders', data)
        },
        async updateOrderItemStatus({commit}, data) {
            const currentStatus = data.item.status
            const newStatus = ORDER_ITEM_STATUSES.getNextStatus(currentStatus)

            commit('updateOrderItemStatus', {
                order: data.order,
                item: data.item,
                status: newStatus
            })

            const url = '/api/v1/clients/' + data.order.client + '/shops/' + data.order.shop + '/orders'
            const resp = await fetch(
                url,
                {
                    method: HTTP_METHODS.POST,
                    headers: {
                        'Content-Type': 'application/json;charset=utf-8'
                    },
                    body: JSON.stringify(data.order)
                })
            if (resp.status !== HTTP_STATUS_CODES.OK) {
                commit('updateOrderItemStatus', {
                    order: data.order,
                    item: data.item,
                    status: currentStatus
                })
                console.error('Could not update order: status is %s', resp.status)
            }
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
        updateOrderItemStatus(state, data) {
            state.orders
                .find(i => i.uuid === data.order.uuid).items.find(i => i.rowNumber === data.item.rowNumber).status = data.status
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