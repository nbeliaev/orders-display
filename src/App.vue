<template>
  <Navbar
      :activeOrdersNumber="activeOrdersNumber"/>
  <router-view></router-view>
</template>

<script>
import Navbar from '@/components/Navbar'

export default {
  name: 'App',
  data() {
    return {
      activeOrdersNumber: 0
    }
  },
  components: {
    Navbar
  },
  methods: {
    changeActiveOrdersNumber(num) {
      this.activeOrdersNumber = num
    }
  }
}
</script>