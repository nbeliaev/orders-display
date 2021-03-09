<template>
  <div class="m-2">
    <div class="row">
      <Order
          v-for="order in orders"
          :key="order.id"
          v-bind:order="order"
          :ref=order.id
      />
    </div>
  </div>
</template>

<script>
import Order from '@/components/Order'

export default {
  props: ['orders'],
  components: {
    Order
  },
  mounted() {
    setTimeout(this.refreshOrdersTimeDiff, 500)
  },
  methods: {
    refreshOrdersTimeDiff() {
      this.orders.forEach(i=> {
        this.$refs[i.id].refreshTimeDiff()
      })
      setTimeout(this.refreshOrdersTimeDiff, 60_000)
    }
  }
}
</script>