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
                        name: 'Shops'
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
                        name: 'Shops',
                        link: '/clients/:clientUuid/shops'
                    },
                    {
                        name: 'Workplaces'
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
                        name: 'Shops',
                        link: '/clients/:clientUuid/shops'
                    },
                    {
                        name: 'Workplaces',
                        link: '/clients/:clientUuid/shops/:shopUuid/workplaces'
                    },
                    {
                        name: 'Orders'
                    },
                ]
            }
        },
        {
            path: '/:pathMatch(.*)*',
            component: () => import('@/components/NotFound'),
            meta: {
                breadcrumbs: []
            }
        }
    ]
})

export default index