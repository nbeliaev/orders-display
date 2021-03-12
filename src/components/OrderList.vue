<template>
  <Loader v-if="loading"/>
  <div class="m-2" v-else-if="orders.length !== 0">
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
  <h1 v-else>No active orders for now ;)</h1>
</template>

<script>
import Loader from '@/components/Loader'
import Order from '@/components/Order'

export default {
  data() {
    return {
      loading: true,
      orders: [],
    }
  },
  emits: ['handle-active-orders-number'],
  props: {
    workplace: {
      type: Object,
      required: true
    }
  },
  mounted() {
    setTimeout(this.getOrders)
  },
  components: {
    Loader, Order
  },
  computed: {
    sortedOrders() {
      return this.orders.slice().sort((i1, i2) => {
        const completed1 = i1.items.filter(i => i.status !== 'completed').length === 0
        const completed2 = i2.items.filter(i => i.status !== 'completed').length === 0
        return (completed1 === completed2) ? 0 : completed1 ? 1 : -1;
      })
    },
    activeOrdersNumber() {
      return this.orders.map(order => {
        return {
          ...order, items: order.items.filter(item => item.status !== 'completed')
        }
      }).filter(i => i.items.length).length
    }
  },
  watch: {
    activeOrdersNumber() {
      this.$emit('handle-active-orders-number', this.activeOrdersNumber)
    }
  },
  methods: {
    getOrders() {
      const order = {
        id: new Date().getTime().toString(),
        table: 'table #3',
        time: '18:00',
        timestamp: new Date().getTime(),
        diff: 0,
        items: []
      }
      if (this.workplace.id === 'K') {
        order.items.push(
            {
              id: 0,
              place: 'Kitchen #1',
              name: 'Pâté of roasted indigenous legumes',
              qnt: 2000,
              status: 'new',
              comment: 'deep fried'
            }
        )
        order.items.push(
            {
              id: 1,
              place: 'Kitchen #1',
              name: 'Chocolate ice cream with strawberry jam',
              qnt: 3,
              status: 'new',
              comment: ''
            }
        )
      } else if (this.workplace.id === 'B') {
        order.items.push({
          id: 1,
          place: 'Bar',
          name: 'Pan-roasted pastry rolls',
          qnt: 1000,
          status: 'new',
          comment: ''
        })
      }
      this.orders.push(order)
      if (this.loading) {
        this.loading = false
      }
      setTimeout(this.getOrders, 60_000)
    }
  }
}
</script>