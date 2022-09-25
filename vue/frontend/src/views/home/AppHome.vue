<template>
  <div>
    <div class="mb-3">
      <button @click="$router.push('/searchResults')" class="btn btn-primary">searchResults</button>
    </div>
    <div class="mb-3">
      <button @click="$router.push('/updateUserInfo')" class="btn btn-primary">updateUserInfo</button>
    </div>
    <img @click="$router.push('/artworkPurchase')" src="https://via.placeholder.com/150">
    <p>This is home</p>
  </div>
</template>

<script>
export default {
  name: 'appHome',
}
</script>

<style>

</style>