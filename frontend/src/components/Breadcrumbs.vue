<template>
  <nav style="--bs-breadcrumb-divider: '>';" aria-label="breadcrumb">
    <ol class="breadcrumb">
      <li class="breadcrumb-item fs-5"
          v-for="(breadcrumb, ind) in allBreadcrumbs"
          :key="ind"
          @click="routeTo(ind)"
          :class="{active: !breadcrumb.link}">
        <a :href="breadcrumb.link">
          {{ breadcrumb.name }}
        </a>
      </li>
    </ol>
  </nav>
</template>

<script>
import {mapActions, mapGetters} from "vuex";

export default {
  watch: {
    '$route'() {
      this.updateBreadcrumbs({
        breadcrumbs: this.$route.meta.breadcrumbs,
        clientUuid: this.$route.params.clientUuid,
        shopUuid: this.$route.params.shopUuid,
      })
    }
  },
  computed: mapGetters(['allBreadcrumbs']),
  methods: {
    ...mapActions(['updateBreadcrumbs']),
    routeTo(ind) {
      const url = this.breadcrumbs[ind].link
      if (url) {
        this.$router.push(url)
      }
    }
  }
}
</script>

<style scoped>

</style>