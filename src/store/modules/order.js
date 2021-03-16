import tables from "@/randomizer/tables.json";
import dishes from "@/randomizer/dishes.json";

export default {
    actions: {
        fetchOrders(ctx, workplaceId) {
            let min = Math.ceil(0);
            let max = Math.floor(5);
            const num = Math.floor(Math.random() * (max - min)) + min
            const order = {
                id: new Date().getTime().toString(),
                table: tables[num],
                timestamp: new Date().getTime(),
                items: []
            }

            if (workplaceId === 'K') {
                let min = Math.ceil(1);
                let max = Math.floor(7);
                const numItems = Math.floor(Math.random() * (max - min)) + min
                for (let i = 0; i < numItems; i++) {
                    const item = {
                        id: 0,
                        place: '',
                        name: '',
                        qnt: 0,
                        status: '',
                        comment: ''
                    }
                    item.id = Date.now()
                    item.place = 'Kitchen #1'
                    let min = Math.ceil(0);
                    let max = Math.floor(5);
                    const numDish = Math.floor(Math.random() * (max - min)) + min
                    item.name = dishes[numDish]
                    let min1 = Math.ceil(0);
                    let max1 = Math.floor(100);
                    item.qnt = Math.floor(Math.random() * (max1 - min1)) + min1
                    item.status = 'new'
                    if (numDish % 2 === 0) {
                        item.comment = numDish === 4 ? 'spicy' : 'deep fried'
                    }
                    order.items.push(item)
                }
            } else if (workplaceId === 'B') {
                order.items.push({
                    id: 1,
                    place: 'Bar',
                    name: 'Pan-roasted pastry rolls',
                    qnt: 1000,
                    status: 'new',
                    comment: ''
                })
            }
            ctx.commit('updateOrders', order)
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
        updateOrders(state, order) {
            state.orders.push(order)
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