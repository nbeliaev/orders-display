<template>
  <div class="grey darken-1 empty-layout">
    <div class="card auth-card">
      <div class="card-content">
        <h5 class="text-center">Please select the shop</h5>
        <ul class="list-group list-group-flush">
          <li
              class="list-group-item text-center"
              v-for="shop in allShops"
              :key="shop.uuid">
            <router-link :to="`/clients/${shop.client}/shops/${shop.uuid}/workplaces`"
                         tag="button"
                         class="btn btn-outline-dark btn-lg btn-block">
              {{ shop.name }}
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
    ...mapGetters(['allShops']),
    clientUuid() {
      return this.$route.params.clientUuid
    },
  },
  methods: mapActions(['fetchShops']),
  async mounted() {
    this.fetchShops(this.clientUuid)
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