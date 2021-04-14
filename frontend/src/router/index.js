import {createWebHistory, createRouter} from 'vue-router'
import ShopList from "@/components/ShopList";

const history = createWebHistory()
const index = createRouter({
    history,
    routes: [
        {
            path: '/clients/:clientUuid/shops',
            name: 'shops',
            component: ShopList,
            meta: {
                breadcrumbs: [
                    {
                        name: 'shops'
                    }
                ]
            }
        },
        {
            path: '/clients/:clientUuid/shops/:shopUuid/workplaces',
            name: 'workplaces',
            component: () => import('@/components/WorkplaceList'),
            meta: {
                breadcrumbs: [
                    {
                        name: 'shops',
                        link: '/clients/:clientUuid/shops'
                    },
                    {
                        name: 'workplaces'
                    }
                ]
            }
        },
        {
            path: '/clients/:clientUuid/shops/:shopUuid/workplaces/:workplaceUuid/orders',
            name: 'orders',
            component: () => import('@/components/OrderList'),
            meta: {
                breadcrumbs: [
                    {
                        name: 'shops',
                        link: '/clients/:clientUuid/shops'
                    },
                    {
                        name: 'workplaces',
                        link: '/clients/:clientUuid/shops/:shopUuid/workplaces'
                    },
                    {
                        name: 'orders'
                    },
                ]
            }
        }
    ]
})

export default index