<template>
  <div>
    <AuctionWorkPurchase v-if="$store.state.searchType == 'auction'" />
    <GeneralWorkPurchase v-if="$store.state.searchType == 'general'" />
  </div>
</template>

<script>
import GeneralWorkPurchase from '@/components/GeneralWorkPurchase.vue'
import AuctionWorkPurchase from '@/components/AuctionWorkPurchase.vue';
export default {
    name: 'ArtworkPruchase',
    components: {
    GeneralWorkPurchase,
    AuctionWorkPurchase
},
}
</script>

<style>

</style>