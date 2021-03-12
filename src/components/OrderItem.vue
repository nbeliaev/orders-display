<template>
  <div class="container-fluid">
    <div class="row gy-3">
      <div
          v-for="item in items"
          :key="item.id"
          @click="handleChangeStatus(item)">
        <div class="row" :class="[
            {'in-process': item.status !== 'new'},
            {completed: item.status === 'completed'}
            ]">
          <div class="col-10 border-bottom">
            <h5>
              {{ item.name }}
              <br v-if="item.comment.length">
              <strong>{{ item.comment }}</strong>
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

.completed {
  background: darkseagreen;
}

.in-process {
  text-decoration: line-through;
}
</style>