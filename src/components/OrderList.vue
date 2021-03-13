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
import dishes from '@/randomizer/dishes.json'
import Workplaces from "@/randomizer/workplaces.json";

export default {
  data() {
    return {
      loading: true,
      orders: [],
    }
  },
  emits: ['handle-active-orders-number'],
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
    },
    workplace() {
      const places = Array.from(Workplaces)
      for (let i = 0; i < places.length; i++) {
        if (places[i].id === this.$route.params.id) {
          console.log(places[i].id)
          return places[i]
        }
      }
      return {id: '', name: ''}
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
          const item = {
            id: 0,
            place: '',
            name: '',
            qnt: 0,
            status: '',
            comment: ''
          }
          item.id = Date.now()
          item.place = 'Kitchen #1'
          const numDish = this.getRandomInt(0, 5)
          item.name = dishes[numDish]
          item.qnt = this.getRandomInt(1, 100)
          item.status = 'new'
          if (numDish % 2 === 0) {
            item.comment = numDish === 4 ? 'spicy' : 'deep fried'
          }
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