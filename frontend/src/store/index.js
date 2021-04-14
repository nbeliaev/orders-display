import {createStore} from 'vuex'
import breadcrumbs from "@/store/modules/breadcrumbs"
import shop from "@/store/modules/shop";
import workplace from "@/store/modules/workplace";
import order from "@/store/modules/order"

export default createStore({
    modules: {
        breadcrumbs, shop, workplace, order
    }
})