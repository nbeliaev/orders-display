<template>
  <Navbar
      :places="places"
      @change-place="changePlace"/>
  <Loader v-if="loading"/>
  <OrderList
      v-else-if="orders.length"
      :orders="filteredOrders"/>
  <h1 v-else>No active orders for now ;)</h1>

  <!--  <div class="container">
      <div class="row">
        <div class="col">1</div>
        <div class="col">2</div>
      </div>
      <div class="row">
        <div class="col">1</div>
        <div class="col">2</div>
        <div class="col">3</div>
      </div>
      <div class="row">
        <div class="col">1</div>
        <div class="col-6">2</div>
        <div class="col">3</div>
      </div>
      <div class="row">
        <div class="col">1</div>
        <div class="col-5">2</div>
        <div class="col">3</div>
      </div>

      <div class="row justify-content-sm-center">
        <div class="col-sm-auto">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium aut dicta dolore et
          eum excepturi, fuga hic id ipsum molestiae molestias odio sequi tempora. Tempora!
        </div>
        <div class="col col-md-2">Lorem ipsum dolor sit amet.
        </div>
      </div>


      <div class="row justify-content-sm-center">
        <div class="col-sm-11">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium aut dicta dolore et
          eum excepturi, fuga hic id ipsum molestiae molestias odio sequi tempora. Tempora!
        </div>
        <div class="col-sm-1"><b>Lorem ipsum dolor sit amet.</b>
        </div>
      </div>

      <div class="row justify-content-end">
        <div class="col col-4">Lorem ipsum dolor sit amet, consectetur adipisicing elit. Accusantium aut dicta dolore et
          eum excepturi, fuga hic id ipsum molestiae molestias odio sequi tempora. Tempora!
        </div>
        <div class="col col-4"><b>Lorem ipsum dolor sit amet.</b>
        </div>
      </div>

    </div>-->

</template>

<script>
import OrderList from '@/components/OrderList'
import Navbar from '@/components/Navbar'
import Loader from '@/components/Loader'

export default {
  name: 'App',
  data() {
    return {
      loading: true,
      activePlace: 'All',
      places: ['All', 'Kitchen #1', 'Bar'],
      orders: []
    }
  },
  components: {
    OrderList, Navbar, Loader
  },
  computed: {
    filteredOrders() {
      if (this.activePlace === 'All') {
        return this.orders
      } else {
        return this.orders.map(order => {
          return {...order, items: order.items.filter(subEl => subEl.place === this.activePlace)}
        }).filter(i => i.items.length)
      }
    }

  },
  mounted() {
    setTimeout(this.getOrders, 0)
  },
  methods: {
    changePlace(place) {
      this.activePlace = place;
    },
    getOrders() {
      const order = {
        id: new Date().getTime().toString(),
        zone: 'third floor',
        table: 'table #3',
        time: '18:00',
        timestamp: new Date().getTime(),
        diff: 0,
        items: [
          {
            id: 0,
            place: 'Kitchen #1',
            name: 'Pâté of roasted indigenous legumes',
            qnt: 2000,
            comment: 'deep fried',
            completed: false
          },
          {
            id: 1,
            place: 'Bar',
            name: 'Pan-roasted pastry rolls',
            qnt: 1000,
            comment: '',
            completed: false
          }
        ]
      }
      if (this.loading) {
        setTimeout(() => {
          this.loading = false
          this.orders.push(order)
          setTimeout(this.getOrders, 60_000)
        }, 1_000)
      } else {
        this.orders.push(order)
        setTimeout(this.getOrders, 60_000)
      }

    }
  }
}
</script>

<style type="text/css">
</style>
