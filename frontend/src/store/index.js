import {createStore} from 'vuex'
import shop from "@/store/modules/shop";
import workplace from "@/store/modules/workplace";
import order from "@/store/modules/order"
import language from "@/store/modules/language"

export default createStore({
    modules: {
        shop, workplace, order, language
    }
})