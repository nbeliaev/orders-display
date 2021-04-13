import {createWebHistory, createRouter} from 'vue-router'
import ShopList from "@/components/ShopList";

const history = createWebHistory()
const index = createRouter({
    history,
    routes: [
        {
            path: '/clients/:clientUuid/shops',
            component: ShopList
        },
        {
            path: '/clients/:clientUuid/shops/:shopUuid/workplaces',
            component: () => import('@/components/WorkplaceList')
        },
        {
            path: '/clients/:clientUuid/shops/:shopUuid/workplaces/:workplaceUuid/orders',
            component: () => import('@/components/OrderList')
        }
    ]
})

export default index