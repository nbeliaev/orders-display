<template>
  <div class="grey darken-1 empty-layout">
    <div class="card auth-card">
      <div class="card-content">
        <h5 class="text-center">Please select your workplace to start</h5>
        <ul class="list-group list-group-flush">
          <li
              class="list-group-item text-center"
              v-for="workplace in allWorkplaces"
              :key="workplace.id">
            <router-link :to="`/workplace/${workplace.id}`"
                         tag="button"
                         class="btn btn-outline-dark btn-lg btn-block">
              {{ workplace.name }}
            </router-link>
          </li>
        </ul>
      </div>
    </div>
  </div>
</template>

<script>
import {mapGetters, mapActions} from 'vuex'

export default {
  computed: mapGetters(['allWorkplaces']),
  methods: mapActions(['fetchWorkplaces']),
  mounted() {
    this.fetchWorkplaces()
  }
}

</script>

<style scoped>
.empty-layout {
  display: -webkit-flex;
  display: -ms-flexbox;
  display: flex;
  -webkit-flex-direction: column;
  -ms-flex-direction: column;
  flex-direction: column;
  -webkit-align-items: center;
  -ms-flex-align: center;
  align-items: center;
  padding-top: 5rem;
  height: 100vh;
  background: #757575
}

.auth-card {
  width: 36rem
}
</style>