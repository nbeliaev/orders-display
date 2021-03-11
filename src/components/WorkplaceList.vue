<template>
  <div class="container text-center">
    <div class="row text-center">
      <Loader v-if="loading"/>
      <div class="col-12 center-block text-center" v-else>
        <h1>Please select your workplace to start</h1>
        <ul class="list-group list-group-flush">
          <li
              class="list-group-item"
              v-for="(workplace, indx) in workplaces"
              :key="workplace.id"
              @click="$emit('handle-workplace-choosing', this.workplaces[indx])">
            {{ workplace.name }}
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import Loader from '@/components/Loader'

export default {
  data() {
    return {
      loading: true,
      workplaces: []
    }
  },
  components: {
    Loader
  },
  mounted() {
    setTimeout(this.getWorkplaces, 0)
  },
  methods: {
    getWorkplaces() {
      const workplaces = [
        {
          id: 'K',
          name: 'Kitchen #1'
        },
        {
          id: 'B',
          name: 'Bar'
        }
      ]
      if (this.loading) {
        setTimeout(() => {
          this.loading = false
          this.workplaces = workplaces
        }, 1_000)
      } else {
        this.workplaces = workplaces
      }
    }
  }
}
</script>