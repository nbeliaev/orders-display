<template>
  <div class="col-auto mb-3 d-flex">
    <div class="card" :class="{completed, completed}">
      <OrderHeader
          :table-name="order.table"
          :completed="completed"
          :timestamp="order.timestamp"/>
      <div class="card-body">
        <OrderItem :items="order.items"/>
      </div>
    </div>
  </div>
</template>

<script>
import OrderHeader from "@/components/OrderHeader";
import OrderItem from "@/components/OrderItem";

export default {
  data() {
    return {
      timeDiff: 0
    }
  },
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  components: {
    OrderHeader, OrderItem
  },
  computed: {
    completed() {
      return this.order.items.filter(i => i.status !== 'completed').length === 0
    }
  }
}
</script>

<style scoped>
.completed {
  background: #eeeeee;
}

.card {
  width: 36rem;
}
</style>