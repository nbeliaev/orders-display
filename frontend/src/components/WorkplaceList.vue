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
            <router-link
                :to="`/clients/${workplace.client}/shops/${workplace.shop}/workplaces/${workplace.uuid}`"
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
  computed: {
    ...mapGetters(['allWorkplaces']),
    clientUuid() {
      return this.$route.params.clientUuid
    },
    shopUuid() {
      return this.$route.params.shopUuid
    }
  },
  methods: mapActions(['fetchWorkplaces']),
  async mounted() {
    this.fetchWorkplaces({
      clientUuid: this.clientUuid,
      shopUuid: this.shopUuid
    })
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