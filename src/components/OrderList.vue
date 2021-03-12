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
import tables from '@/randomizer/tables.json'
import items from '@/randomizer/items.json'
import dishes from '@/randomizer/dishes.json'

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
    getRandomInt(min, max) {
      min = Math.ceil(min);
      max = Math.floor(max);
      return Math.floor(Math.random() * (max - min)) + min;
    },
    getOrders() {
      const num = this.getRandomInt(0, 5)
      const order = {
        id: new Date().getTime().toString(),
        table: tables[num],
        timestamp: new Date().getTime(),
        items: []
      }

      if (this.workplace.id === 'K') {
        const numItems = this.getRandomInt(1, 7)
        for (let i = 0; i < numItems; i++) {
          const numItem = this.getRandomInt(0, 30)
          const item = items[numItem]
          item.id = Date.now()
          const numDish = this.getRandomInt(0, 5)
          item.name = dishes[numDish]
          item.qnt = this.getRandomInt(1, 100)
          order.items.push(item)
        }
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