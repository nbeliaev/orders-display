<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light ptb-0">
    <div class="navbar-collapse" id="navbarSupportedContent">
      <div class="container-fluid">
        <div class="row">
          <div class="col-9">
            <Breadcrumbs/>
          </div>
          <div
              class="col d-flex justify-content-end"
              v-if="showWorkplace">
            <p class="fs-5">
              Workplace: <span class="fw-bold">{{ workplace.name }}</span>
            </p>
          </div>
          <div
              class="col d-flex justify-content-end"
              v-if="showWorkplace">
            <p class="fs-5">
              Active orders: <span class="fw-bold">{{ activeOrdersNumber || 0 }}</span>
            </p>
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
      return this.$store.getters.findWorkplaceById(this.$route.params.workplaceUuid)
    },
    activeOrdersNumber() {
      return this.$store.getters.activeOrdersNumber
    }
  }
}
</script>