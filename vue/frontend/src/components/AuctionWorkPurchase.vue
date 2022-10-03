<template>
  <div class="container">
    <div class="row">
      <div class="col-md-5">
        <div class="row">
          <div class="col-md-12 offset-md-1 mt-5">
            <img :src="require(`../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)"
              class="w-100 border border-1">
            <!-- src="https://via.placeholder.com/470x305" -->
          </div>
        </div>
        <div class="row">
          <div class="col-md-12 offset-md-1 mt-2">
            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                style="width: 23.3%; height: 100px;"></a>
            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                style="width: 23.3%; height: 100px;"></a>
            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                style="width: 23.3%; height: 100px;"></a>
            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                style="width: 23.3%; height: 100px;"></a>
          </div>
        </div>

      </div>

      <div class="col-lg-5 offset-lg-1 mt-5">
        <div class="s_product_text">
          <div class="row g-3 mt-2">
            <div class="mb-2">
              <h3>{{ art.auctionArt.artName }}</h3>
            </div>

            <div class="mb-2">
              <h3> 시작 경매가: {{ art.auctionArt.artInitPrice }}원<br>현재 경매가: {{ art.auctionArt.highestBidPrice }}원 </h3>
            </div>

            <hr>

            <div class="mb-2">
              <ul class="list-group list-group-horizontal">
                <li class="list-group-item" style="border: none;">
                  <font-awesome-icon icon="fa-solid fa-gavel" class="mx-1" />
                  <a> {{ art.auctionArtBidCount }}</a>
                </li>
                <li class="list-group-item" style="border: none;">
                  <font-awesome-icon icon="fa-solid fa-eye" class="mx-1" />
                  <a> 46 </a>
                </li>
                <li class="list-group-item" style="border: none;">
                  <font-awesome-icon icon="fa-solid fa-clock" class="mx-1" />
                  <a> {{art.auctionArt.auctionStartDate.split("T")[0]}} ~
                    {{art.auctionArt.auctionEndDate.split("T")[0]}}</a>
                </li>
              </ul>
            </div>

            <div class="mb-2">
              <p>
                <!-- Mill Oil is an innovative oil filled radiator with the most modern technology. If you
                                are
                                looking for something that can make your interior look awesome, and at the same time. -->
                {{ art.auctionArt.artDescription }}
              </p>
            </div>
          </div>
          <div class="card_area">
            <div class="row g-3">
              <div class="col-md-3">
                <a href="#" class="btn btn-lg btn-outline-danger" style="border-radius: 6px; width: 120px;">
                  <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                </a>
              </div>
              <div class="col-md-3">
                <a href="#" class="btn btn-lg btn-outline-primary" data-bs-toggle="modal" data-bs-target="#auctionModal"
                  style="border-radius: 6px; width: 120px;">응찰하기</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'generalWorkPurchase',
  data() {
    return {
      art: {
        auctionArtBidCount: 13,
        auctionArt: {
          auctionId: 4,
          highestBidUserId: 24,
          highestBidUserNickname: "user24-Nickname",
          highestBidUserSchoolName: "고려대학교",
          highestBidPrice: 75000,
          auctionStartDate: "2022-09-26T00:00:00",
          auctionEndDate: "2022-09-28T00:00:00",
          artId: 5,
          artName: "art5",
          artDescription: "이 작품은 작품입니다 - 5",
          artInitPrice: 30000,
          artRegisterDate: "2022-07-10 09:00:03",
          artStorageName: "c53403583c.png",
          artOwnerId: 5,
          artOwnerNickname: "user5-Nickname",
          artOwnerSchoolName: "수원대학교"
        },
        artHashtagList: [
          "고양이",
          "반려견",
          "음식",
          "산",
          "삶",
          "게임",
          "개",
          "진돗개"
        ]
      },
    }
  },
  methods: {
    purchaseArt() {

    },
  },
  beforeMount() {
    this.art = { ...this.$store.state.selectedArt };
  },
}
</script>

<style>

</style>