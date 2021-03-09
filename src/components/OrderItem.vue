<template>
  <div
      :class="['d-flex', 'flex-row', 'no-gutters', 'order-item',{completed: item.completed}]"
      v-for="item in items"
      :key="item.id"
      @click="item.completed = !item.completed">
    <div class="col-10">
      {{ item.name }}
      <br v-if="item.comment.length">
      <strong>{{ item.comment }}</strong>
    </div>
    <div class="col-2">{{ item.qnt }}</div>
  </div>
</template>

<script>

export default {
  props: {
    items: {
      type: Object,
      required: true
    }
  },
  mounted() {
    const a = new Audio('the-ding.mp3')
    a.play()
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
  text-decoration: line-through;
}
</style>