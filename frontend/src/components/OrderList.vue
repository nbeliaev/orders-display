<template>
  <div class="m-2" v-if="allOrders.length !== 0">
    <div class="container-fluid">
      <div class="row">
        <Order
            v-for="order in sortedOrders"
            :key="order.id"
            v-bind:order="order"
        />
      </div>
    </div>
  </div>
  <h1 v-else class="text-center">No active orders for now ;)</h1>
</template>

<script>
import Order from '@/components/Order'
import {mapActions, mapGetters} from 'vuex'

export default {
  data() {
    return {
      polling: null
    }
  },
  async mounted() {
    this.fetchOrders(this.workplaceId)
    this.pollData()
  },
  beforeUnmount() {
    if (this.polling !== null) {
      clearInterval(this.polling)
    }
    this.clearState()
  },
  components: {
    Order
  },
  computed: {
    ...mapGetters(['allOrders']),
    workplaceId() {
      return this.$route.params.id
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
      this.polling = setInterval(this.fetchOrders, 6000, this.workplaceId)
    }
  }

}
</script>