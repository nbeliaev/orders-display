<template>
  <div
      :class="['d-flex', 'flex-row', 'no-gutters', 'order-item',
      {'in-process': item.status !== 'new'}, {completed: item.status === 'completed'}]"
      v-for="item in items"
      :key="item.id"
      @click="handleChangeStatus(item)">
    <div class="col-10">
      {{ item.name }}
      <br v-if="item.comment.length">
      <strong>{{ item.comment }}</strong>
    </div>
    <div class="col-2">{{ item.qnt }}</div>
  </div>
</template>

<script>
import {useSound} from '@vueuse/sound'
import ding from '@/assets/the-ding.mp3'

export default {
  props: {
    items: {
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
    handleChangeStatus(item) {
      if (item.status === 'new') {
        item.status = 'in-process'
      } else if (item.status === 'in-process') {
        item.status = 'completed'
      } else {
        item.status = 'new'
      }
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

.order-item:hover {
  background: #eee;
}

.completed {
  background: darkseagreen;
}

.in-process {
  text-decoration: line-through;
}
</style>