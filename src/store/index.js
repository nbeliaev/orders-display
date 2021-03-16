import {createStore} from 'vuex'
import workplace from "@/store/modules/workplace";
import order from "@/store/modules/order"
import language from "@/store/modules/language"

export default createStore({
    modules: {
        workplace, order, language
    }
})