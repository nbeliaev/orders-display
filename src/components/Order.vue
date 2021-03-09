<template>
  <div class="col-auto mb-3 d-flex">
    <div>
      <div class="card" style="width: 18rem;">
        <div class="card-body">
          <div class="row d-flex no-gutters">
            <div class="col-5">
              <h5 class="card-title">{{ order.zone }}</h5>&nbsp;
            </div>
            <div class="col-4">
              <h5 class="card-title text-muted">{{ order.table }}</h5>
            </div>
            <div class="col-2 ml-auto">
             <span :class="{'text-warning': timeDiff > 60_000}">{{
                 convertMs(timeDiff)
               }}</span>
            </div>
          </div>
          <OrderItem :items="order.items"/>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
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
    OrderItem
  },
  methods: {
    refreshTimeDiff() {
      this.timeDiff = new Date().getTime() - this.order.timestamp
    },
    convertMs(ms) {
      const min = Math.floor((ms / 1000 / 60) << 0)
      return (min < 1 ? '1' : min.toString()) + 'm.'
    }
  }
}
</script>