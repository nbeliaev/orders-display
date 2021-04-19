<template>
  <div class="container-fluid">
    <div class="row gy-3">
      <div
          v-for="item in order.items"
          :key="item.rowNumber"
          @click="handleChangeStatus(item)">
        <div class="row" :class="[
            {'in-process': item.status !== 'new'},
            {completed: item.status === 'completed'}
            ]">
          <div class="col-10 border-bottom">
            <h5>
              {{ item.name }}
              <br v-if="item.note.length">
              <strong>{{ item.note }}</strong>
            </h5>
          </div>
          <div class="col-2 border-bottom">
            <h5>{{ item.qnt }}</h5>
          </div>
        </div>
      </div>
    </div>
  </div>

</template>

<script>
import {useSound} from '@vueuse/sound'
import ding from '@/assets/the-ding.mp3'
import {mapActions} from 'vuex'

export default {
  props: {
    order: {
      type: Object,
      required: true
    }
  },
  setup() {
    const {play} = useSound(ding)
    const notify = () => {
      play()
    }
    return {notify}
  },
  mounted() {
    setTimeout(this.notify)
  },
  methods: {
    ...mapActions(['updateOrderItemStatus']),
    handleChangeStatus(item) {
      this.updateOrderItemStatus({
        order: this.order,
        item
      })
    }
  }
}
</script>

<style scoped>
list {
  margin: 0;
  padding: 0;
  list-style: none;
}

.in-process {
  background: darkseagreen;
}

.completed{
  text-decoration: line-through;
}
</style>