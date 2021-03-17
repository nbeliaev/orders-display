export default {
    actions: {
        updateLanguage(ctx, lang) {
            ctx.commit('updateLanguage', lang)
        }
    },
    mutations: {
        updateLanguage(state, lang) {
            state.lang = lang
            localStorage.setItem('lang', lang)
        }
    },
    state: {
        lang: localStorage.getItem('lang') || 'vi-VI'
    },
    getters: {
        currentLanguage(state) {
            return state.lang
        }
    }
}