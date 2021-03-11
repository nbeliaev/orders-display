<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light mb-1">
    <div class="navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto" v-if="workplace.id !== ''">
        <li class="nav-item active mr-3">
          <p>Workplace:</p>
          <strong>{{ workplace.name }}</strong>
        </li>
        <li class="nav-item active mr-3">
          <p>Active orders:</p>
          <strong>{{ activeOrdersNumber || 0 }}</strong>
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