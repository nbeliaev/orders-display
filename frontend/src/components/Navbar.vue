<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light mb-1">
    <div class="navbar-collapse" id="navbarSupportedContent">

      <Breadcrumbs/>

      <div class="container-fluid" v-if="showWorkplace">
        <div class="row justify-content-md-center">
          <div class="col-md-auto">
            <div class="mx-3">
              <h5>
                Workplace: <strong>DUMMY</strong>
              </h5>
            </div>
          </div>
          <div class="col-md-auto">
            <div class="mx-3">
              <h5>
                Active orders: <strong>{{ activeOrdersNumber || 0 }}</strong>
              </h5>
            </div>
          </div>
        </div>
      </div>
    </div>
  </nav>
</template>

<script>
import Breadcrumbs from "./Breadcrumbs";

export default {
  components: {
    Breadcrumbs
  },
  computed: {
    showWorkplace() {
      return this.$store.getters.showWorkplace
    },
    workplace() {
      return this.$store.getters.findWorkplaceById(this.$route.params.id)
    },
    activeOrdersNumber() {
      return this.$store.getters.activeOrdersNumber
    }
  }
}
</script>