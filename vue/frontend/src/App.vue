<template>
  <div class="container">
    <div>
      <RegisterModals></RegisterModals>
      <div class="btn-group">
        <button @click="$router.push('/');" class="btn btn-outline-dark">Home</button>
        <button @click="$router.push('/CreateAccount/TermsOfService')"
          class="btn btn-outline-dark">CreateAccount</button>
        <button @click="$router.push('/ArtworkRegister')" class="btn btn-outline-dark">ArtworkRegister</button>
      </div>
      <router-view></router-view>
    </div>
  </div>

</template>

<script>
import RegisterModals from './components/RegisterModals.vue'

export default {
  name: 'App',
  data() {
    return {
      page: 1,
    }
  },
  methods: {
  },
  components: {
    RegisterModals,
  }
}
</script>

<style>
body {
  /* background-color: rgb(249, 249, 249); */
  /* background-image: url('./assets/registerBackgroundImage3.jpg');
  background-size: cover;   */
}
</style>
