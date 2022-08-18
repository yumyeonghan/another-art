<template>
  <div id="wrapper" class="container-fluid">
    <div class="container px-4s my-5">
      <div class="p-4">
        <h4 class="mb-3 text-center text-black fs-3">작품등록</h4>
      </div>
      <div class="row gx-4 m-auto p-3">
        <div class="col-md-6 position-static d-block p-3 text-black">
          <form class="needs-validation" novalidate>

            <div class="row g-3">
              <div :class="col_style" class="mb-4">
                <label for="artworkType" class="form-label">작품유형</label>
                <div class="btn-group form-control" style="height: 120px; border: none;" role="group"
                  aria-label="Basic example">
                  <button type="button" @click="workType = 'general';" id="artworkType" class="btn btn-outline-dark">
                    <div class="mb-2">
                      <img src="../assets/won2.png" style="height: 40px;">
                    </div>
                    일반 작품
                  </button>
                  <button type="button" @click="workType = 'auction';" class="btn btn-outline-dark">
                    <div class="mb-2">
                      <img src="../assets/hammer.png" style="height: 40px;">
                    </div>
                    경매 작품
                  </button>
                </div>
              </div>
            </div>

            <GeneralWork v-if="workType == 'general'"></GeneralWork>
            <AuctionWork v-if="workType == 'auction'"></AuctionWork>
            
          </form>
        </div>

        <div class="col-md-6 position-static d-block p-3 text-black">
          <p class="offset-md-1 fw-semibold">미리보기</p>
          <div class="card mt-3 mb-5 mx-5">
            <img src="https://via.placeholder.com/150" style="height: auto;">
            <div class="card-body pt-4">
              <!-- <h2 class="card-title"></h2> -->
              <p class="card-text">이미지 파일을 추가하면 위 공간에 이미지가 표시됩니다.</p>
            </div>
          </div>
        </div>

        <div class="row">
          <button class="btn btn-outline-dark btn-lg col-md-4 offset-md-4 mt-3 p-3" style="opacity: 0.7;" type="submit"
            id="signup">완료</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import AuctionWork from '../components/AuctionWork.vue'
import GeneralWork from '../components/GeneralWork.vue'

export default {
  name: 'artworkRegister',
  data() {
    return {
      workType: 'general',
      col_style: 'col-md-10 offset-md-1'
    }
  },
  components: {
    AuctionWork,
    GeneralWork
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
</style>