<template>
  <nav class="navbar navbar-expand-md navbar-light bg-light mb-1">
    <div class="navbar-collapse" id="navbarSupportedContent">
      <ul class="navbar-nav mr-auto">
        <li class="nav-item active mr-3">
          <a class="nav-link" href="#"><img src="https://vuejs.org/images/logo.png" width="10%" height="10%" alt="Logo"></a>
        </li>
      </ul>
    </div>

    <div class="input-group mb-3">
      <label for="placeSelector">
        <select
            id="placeSelector"
            class="custom-select"
            v-model="selected"
            @change="$emit('change-place', selected)">
          <option
              v-for="place in places"
              :key="place"
              :value="place">{{ place }}
          </option>
        </select>
      </label>
    </div>

  </nav>
</template>

<script>
export default {
  data() {
    return {
      selected: this.places.length > 0 ? this.places[0] : ''
    }
  },
  props: {
    places: {
      type: Array,
      required: true
    }
  }
}
</script>