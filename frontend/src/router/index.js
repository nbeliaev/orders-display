import {createWebHistory, createRouter} from 'vue-router'
import WorkplaceList from '@/components/WorkplaceList'

const history = createWebHistory()
const index = createRouter({
    history,
    routes: [
        {
            path: '/',
            component: WorkplaceList
        },
        {
            path: '/workplace/:id',
            component: () => import('@/components/OrderList')
        }
    ]
})

export default index