<template>
<div>
  <div class="d-flex justify-content-center">
    <div>
      <RegisterModals></RegisterModals>
      <Navbar></Navbar>
      <p style="height: 100px;">hi
      </p>
      <!-- <div class="btn-group">
        <button @click="$router.push('/');" class="btn btn-outline-dark">Home</button>
        <button @click="$router.push('/createAccount/termsOfService')"
          class="btn btn-outline-dark">CreateAccount</button>
        <button @click="$router.push('/artworkRegister')" class="btn btn-outline-dark">ArtworkRegister</button>
        <button @click="$router.push('/idFind')" class="btn btn-outline-dark">IdFind</button>
        <button @click="$router.push('/passwordFind')" class="btn btn-outline-dark">PasswordFind</button>
        <button @click="$router.push('/userPointCharge')" class="btn btn-outline-dark">UserPointCharge</button>
        <button @click="$router.push('/userPointRefund')" class="btn btn-outline-dark">UserPointRefund</button>
      </div> -->
      
      <!-- :key="$route.fullPath" -->
      <router-view></router-view>
    </div>
  </div>
</div>
</template>

<script>
import RegisterModals from './components/RegisterModals.vue'
import Navbar from './components/Navbar.vue'

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
    Navbar,
}
}
</script>

<style>
body {
}
</style>
