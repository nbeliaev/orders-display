<template>
  <Loader v-if="isLoading"/>
  <div class="m-2" v-else-if="!isLoading && sortedOrders.length !== 0">
    <div class="container-fluid">
      <div class="row">
        <Order
            v-for="order in sortedOrders"
            :key="order.uuid"
            v-bind:order="order"
        />
      </div>
    </div>
  </div>
  <h1 v-else class="text-center">No active orders for now ;)</h1>
</template>

<script>
import Order from '@/components/Order'
import Loader from '@/components/Loader'
import {mapActions, mapGetters} from 'vuex'

export default {
  data() {
    return {
      polling: null
    }
  },
  async mounted() {
    this.fetchOrders(this.queryParams)
    this.pollData()
  },
  beforeUnmount() {
    if (this.polling !== null) {
      clearInterval(this.polling)
    }
    this.clearState()
  },
  components: {
    Loader, Order
  },
  computed: {
    ...mapGetters(['allOrders', 'isLoading']),
    queryParams() {
      return {
        clientUuid: this.$route.params.clientUuid,
        shopUuid: this.$route.params.shopUuid,
        workplaceUuid: this.$route.params.workplaceUuid
      }
    },
    sortedOrders() {
      return this.allOrders.slice().sort((i1, i2) => {
        const completed1 = i1.items.filter(i => i.status !== 'completed').length === 0
        const completed2 = i2.items.filter(i => i.status !== 'completed').length === 0
        return (completed1 === completed2) ? 0 : completed1 ? 1 : -1;
      })
    }
  },
  methods: {
    ...mapActions(['fetchOrders', 'clearState']),
    pollData() {
      this.polling = setInterval(
          this.fetchOrders,
          15_000,
          this.queryParams)
    }
  }
}
</script>