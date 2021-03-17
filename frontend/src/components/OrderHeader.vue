<template>
  <div class="div card-header">
    <div class="container-fluid">
      <div class="row">
        <div class="col-10">
          <h5 class="card-title text-muted">{{ tableName }}</h5>
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
</template>

<script>
export default {
  data() {
    return {
      timeDiff: 0
    }
  },
  props: {
    tableName: {
      type: String,
      required: true
    },
    timestamp: {
      type: Number,
      required: true
    },
    completed: {
      type: Boolean,
      required: false
    }
  },
  mounted() {
    setTimeout(this.addTimeDiffUpdater, 500)
  },
  methods: {
    addTimeDiffUpdater() {
      this.refreshTimeDiff()
      setInterval(this.refreshTimeDiff, 30_000)
    },
    refreshTimeDiff() {
      if (!this.completed) {
        this.timeDiff = new Date().getTime() - this.timestamp
      }
    },
    convertMs(ms) {
      const min = Math.floor((ms / 1000 / 60) << 0)
      return (min < 1 ? '1' : min.toString()) + 'm'
    }
  }
}
</script>