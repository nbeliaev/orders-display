import {createWebHistory, createRouter} from 'vue-router'
import ShopList from "@/components/ShopList";

const history = createWebHistory()
const index = createRouter({
    history,
    routes: [
        {
            path: '/clients/:clientUuid/shops',
            component: ShopList
        }
    ]
})

export default index