<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light mb-1">
    <div class="navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto" v-if="workplace.id !== ''">
        <li class="nav-item mr-3">
          <div class="mx-3">
            <h5>
              Workplace: <strong>{{ workplace.name }}</strong>
            </h5>
          </div>
        </li>
        <li class="nav-item mr-3">
          <div>
            <h5>
              Active orders: <strong>{{ activeOrdersNumber || 0 }}</strong>
            </h5>
          </div>
        </li>
      </ul>
    </div>
  </nav>
</template>

<script>
export default {
  props: {
    workplace: {
      type: Object,
      required: false
    },
    activeOrdersNumber: {
      type: Number,
      required: false
    }
  }
}
</script>