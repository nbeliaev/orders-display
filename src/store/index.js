import {createStore} from 'vuex'
import workplace from "@/store/modules/workplace";
import order from "@/store/modules/order"

export default createStore({
    modules: {
        workplace, order
    }
})