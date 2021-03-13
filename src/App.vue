<template>
  <Navbar
      :workplace="workplace"
      :activeOrdersNumber="activeOrdersNumber"/>
  <router-view></router-view>
</template>

<script>
import Navbar from '@/components/Navbar'

export default {
  name: 'App',
  data() {
    return {
      activeOrdersNumber: 0,
      workplace: {
        id: '',
        name: ''
      }
    }
  },
  components: {
    Navbar
  },
  methods: {
    changeWorkplace(workplace) {
      this.workplace = workplace
    },
    changeActiveOrdersNumber(num) {
      this.activeOrdersNumber = num
    }
  }
}
</script>