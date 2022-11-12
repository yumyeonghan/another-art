<template>
  <div class="container px-4s my-5">
    <div class="p-4">
      <h4 class="mb-3 text-center text-black fs-3">작품등록</h4>
    </div>
    <div id="wrapper" class="container-fluid">
      <GeneralWork v-if="saleType == 'general'" @setSaleType="saleType = $event" :col_style="col_style" :saleType="saleType" />
      <AuctionWork v-if="saleType == 'auction'" @setSaleType="saleType = $event" :col_style="col_style" :saleType="saleType" />
    </div>
  </div>
</template>

<script>
import AuctionWork from '../../components/AuctionWorkRegister.vue'
import GeneralWork from '../../components/GeneralWorkRegister.vue'

export default {
  name: 'artworkRegister',
  data() {
    return {
      saleType: 'auction',
      col_style: 'col-md-10 offset-md-1',
    }
  },
  components: {
    AuctionWork,
    GeneralWork
  },
  methods: {
    setSaleType(type) {
      this.saleType = type;
    }
  }
}
</script>

<style>
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
  margin: 0;
}

input[type=number] {
  -moz-appearance: textfield;
}

.importar {
  width: 100%;
  height: 272px;
  pointer-events: none;
  background: url('../../assets/camera-128.jpg');
  background-color: rgb(250, 250, 253);
  background-position: 50% 50%;
  background-repeat: no-repeat;
  background-size: 100px;
  border: 1px solid rgb(230, 229, 239);
  opacity: 0.5;
}
</style>