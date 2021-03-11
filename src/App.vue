<template>
  <Navbar
      :workplace="workplace"
      :activeOrdersNumber="activeOrdersNumber"/>
  <WorkplaceList
      v-if="workplace.id === ''"
      @handle-workplace-choosing='changeWorkplace'/>
  <OrderList
      v-else
      :workplace="workplace"
      @handle-active-orders-number="changeActiveOrdersNumber"/>
</template>

<script>
import OrderList from '@/components/OrderList'
import Navbar from '@/components/Navbar'
import WorkplaceList from "@/components/WorkplaceList";

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
    WorkplaceList, OrderList, Navbar
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
