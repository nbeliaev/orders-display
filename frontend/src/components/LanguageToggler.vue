<template>
  <select
      class="form-select"
      aria-label="Default select example"
      v-model="language">
    <option selected value="en-US">English</option>
    <option value="vi_VI">Tiếng Việt</option>
    <option value="ru-RU">Русский</option>
  </select>
</template>

<script>
import {mapActions} from 'vuex'

export default {
  data() {
    return {
      language: ''
    }
  },
  mounted() {
    this.language = this.$store.getters.currentLanguage
  },
  methods: mapActions(['updateLanguage']),
  watch: {
    language() {
      this.updateLanguage(this.language)
    }
  }
}

</script>

<style scoped>
.form-select{
  width: 10rem;
}
</style>