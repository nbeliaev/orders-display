<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light mb-1">
    <div class="navbar-collapse" id="navbarSupportedContent">

      <div class="container-fluid">
        <div class="row justify-content-md-center">
          <div class="col-md-auto">
            <div class="mx-3">
              <h5>
                Workplace: <strong>{{ workplace.name }}</strong>
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

      <LanguageToggler/>

    </div>
  </nav>
</template>

<script>
import LanguageToggler from "@/components/LanguageToggler";

export default {
  components: {
    LanguageToggler
  },
  computed: {
    workplace() {
      return this.$store.getters.findWorkplaceById(this.$route.params.id)
    },
    activeOrdersNumber() {
      return this.$store.getters.activeOrdersNumber
    }
  }
}
</script>