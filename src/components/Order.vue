<template>
  <div class="col-auto mb-3 d-flex">
    <div>
      <div class="card" :class="{completed, completed}">
        <div class="div card-header">
          <div class="container-fluid">
            <div class="row">
              <div class="col-10">
                <h5 class="card-title text-muted">{{ order.table }}</h5>
              </div>
              <div class="col-2 ml-auto">
             <span
                 :class="{'text-warning': timeDiff > 60_000}"
                 v-if="!completed">
               {{ convertMs(timeDiff) }}
             </span>
              </div>
            </div>
          </div>
        </div>
        <div class="card-body">
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
  computed: {
    completed() {
      return this.order.items.filter(i => i.status !== 'completed').length === 0
    }
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

<style scoped>
.completed {
  background: #eeeeee;
}

.card {
  width: 36rem;
}
</style>