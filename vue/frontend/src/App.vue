<template>
  <div class="container">
    <div>
      <RegisterModals></RegisterModals>
      <div class="btn-group">
        <button @click="$router.push('/');" class="btn btn-outline-dark">Home</button>
        <button @click="$router.push('/createAccount/termsOfService')"
          class="btn btn-outline-dark">CreateAccount</button>
        <button @click="$router.push('/artworkRegister')" class="btn btn-outline-dark">ArtworkRegister</button>
        <button @click="$router.push('/idFind')" class="btn btn-outline-dark">IdFind</button>
        <button @click="$router.push('/passwordFind')" class="btn btn-outline-dark">PasswordFind</button>

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
