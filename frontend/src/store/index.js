import {createStore} from 'vuex'
import breadcrumbs from "@/store/modules/breadcrumbs"
import shop from "@/store/modules/shop";
import workplace from "@/store/modules/workplace";
import order from "@/store/modules/order"
import language from "@/store/modules/language"

export default createStore({
    modules: {
        breadcrumbs, shop, workplace, order, language
    }
})