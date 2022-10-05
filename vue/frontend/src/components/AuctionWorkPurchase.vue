<template>
  <div class="container">
    <!-- auctionMoadl -->
    <div class="modal fade" id="auctionModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">경매 응찰</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="input-group rounded">
              <input v-model="bidData.bidPrice" type="search" class="form-control rounded" placeholder="응찰금액"
                aria-label="Search" aria-describedby="search-addon" autofucus />
            </div>
            <div class="text-center mt-4">
              <button type="button" @click="bidArt" class="btn btn-outline-primary" data-bs-dismiss="modal"
                aria-label="Close">응찰하기</button>
            </div>
          </div>
        </div>
      </div>
    </div>

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
                <a href="#" @click="likeArtControl" :class="likeButtonStyle" style="border-radius: 6px; width: 120px;">
                  <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                </a>
              </div>
              <div v-if="isForSale == true" class="col-md-3">
                <a href="#" class="btn btn-lg btn-outline-success" data-bs-toggle="modal" data-bs-target="#auctionModal"
                  style="border-radius: 6px; width: 120px;">응찰하기</a>
              </div>
              <div v-if="isForSale == false && isHighestBidUser == true" class="col-md-3">
                <a href="#" class="btn btn-lg btn-outline-primary" @click="purchaseArt"
                  style="border-radius: 6px; width: 120px;">구매하기</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import axios from 'axios';

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
          auctionEndDate: "2022-10-12T00:00:00",
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
      bidData: {
        auctionId: this.$store.state.selectedArt.auctionArt.auctionId,
        bidPrice: 0,
        userId: JSON.parse(JSON.parse(sessionStorage.getItem('loginData'))).userId,
      },
      purchaseData: {
        auctionId: this.$store.state.selectedArt.auctionArt.auctionId,
        userId: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId,
      },
      likeData: {
        artId: this.$store.state.selectedArt.auctionArt.artId,
        userId: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId,
      },
      isForSale: true,
      isHighestBidUser: false,
      isLiked: false,
      likeButtonStyle: 'btn btn-lg btn-outline-danger',
    }
  },
  methods: {
    bidArt() {
      axios.post(`/api/bid`, this.bidData).then((res) => {
        console.log('this.bidData ' + JSON.stringy(this.bidData));
        console.log('success ' + JSON.stringify(res));
        console.log('응찰완료');
      }).catch((res) => {
        console.log('fail: ' + JSON.stringify(res));
      })
    },
    purchaseArt() {
      axios.post('/api/purchase/auction', this.purchaseData).then((res) => {
        console.log("req: " + JSON.stringify(this.purchaseData));
        console.log("res: " + JSON.stringify(res.data));
        if (res.data.purchaseId) {
          alert('구매완료');
          this.$router.push('/vue');
        }
      }).catch((res) => {
        console.log("catch: " + JSON.stringify(res.data));
      })
    },
    likeArtControl() {
      // 작품 좋아요
      if (!this.isLiked) {
        axios.post('/api/art/like', this.likeData).then((res) => {
          console.log("req: " + JSON.stringify(this.likeData));
          console.log("res: " + JSON.stringify(res.data));
          this.isLiked = true;
          this.likeButtonStyle = 'btn btn-lg btn-danger';
        }).catch((res) => {
          console.log("catch: " + JSON.stringify(res.data));
        })
      // 작품 좋아요 취소
      } else if (this.isLiked) {
        axios.post('/api/art/cancel', this.likeData).then((res) => {
          console.log("req: " + JSON.stringify(this.likeData));
          console.log("res: " + JSON.stringify(res.data));
          this.isLiked = false;
          this.likeButtonStyle = 'btn btn-lg btn-outline-danger';
        }).catch((res) => {
          console.log("catch: " + JSON.stringify(res.data));
        });
      }
    },
  },
  beforeMount() {
    this.art = { ...this.$store.state.selectedArt };
    let today = new Date();
    let endDate = new Date(this.art.auctionArt.auctionEndDate);
    let remainTime = endDate.getTime() - today.getTime();
    console.log('today ' + today);
    console.log('endDate ' + endDate);
    console.log('remainTime ' + remainTime);

    axios.get('/api/session-check').then((res) => {
      console.log('success ' + JSON.stringify(res.data));
      if (remainTime <= 0) {
        this.isForSale = false;
        if (this.art.highestBidUserId == res.data.id) {
          this.isHighestBidUser = true;
          alert('축하합니다! 낙찰입니다. 작품을 구매하세요.');
        } else {
          alert('작품이 다른 사용자에게 낙찰되었습니다. 메인 페이지로 이동합니다.');
          this.$router.push('/vue');
        }
      }
    }).catch((res) => {
      console.log('fail ' + res);
    })
  },
}
</script>

<style>

</style>