<template>
  <div class="container">
    <!-- auctionMoadl -->
    <div class="modal fade" id="auctionModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">경매 입찰</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="input-group rounded">
              <input v-model="bidData.bidPrice" type="search" class="form-control rounded" placeholder="응찰금액"
                aria-label="Search" aria-describedby="search-addon" autofucus />
            </div>
            <div class="text-center mt-4">
              <button type="button" @click="bidArt" class="btn btn-outline-primary" data-bs-dismiss="modal"
                aria-label="Close">입찰하기</button>
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
        <!--        <div class="row">-->
        <!--          <div class="col-md-12 offset-md-1 mt-2">-->
        <!--            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"-->
        <!--                style="width: 23.3%; height: 100px;"></a>-->
        <!--            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"-->
        <!--                style="width: 23.3%; height: 100px;"></a>-->
        <!--            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"-->
        <!--                style="width: 23.3%; height: 100px;"></a>-->
        <!--            <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"-->
        <!--                style="width: 23.3%; height: 100px;"></a>-->
        <!--          </div>-->
        <!--        </div>-->

      </div>

      <div class="col-lg-5 offset-lg-1 mt-5">
        <div class="s_product_text">
          <div class="row g-3 mt-2">
            <div class="mb-2">
              <h2>{{ art.auctionArt.artName }} - {{ art.auctionArt.artOwnerNickname }}</h2>
            </div>

            <div class="mb-2">
              <p>
                <!-- Mill Oil is an innovative oil filled radiator with the most modern technology. If you
                                are
                                looking for something that can make your interior look awesome, and at the same time. -->
                {{ art.auctionArt.artDescription }}
              </p>
            </div>

            <hr>

            <div class="mb-2">
              <p>입찰 횟수 | {{ art.auctionArtBidCount }}회</p>
              <p>경매 시작 날짜 | {{ art.auctionArt.auctionStartDate }}</p>
              <p>경매 종료 날짜 | {{ art.auctionArt.auctionEndDate }}</p>
              <!--              <ul class="list-group list-group-horizontal">-->
              <!--                <li class="list-group-item" style="border: none;">-->
              <!--                  <font-awesome-icon icon="fa-solid fa-gavel" class="mx-1" />-->
              <!--                  <a> {{ art.auctionArtBidCount }}</a>-->
              <!--                </li>-->
              <!--                <li class="list-group-item" style="border: none;">-->
              <!--                  <font-awesome-icon icon="fa-solid fa-eye" class="mx-1" />-->
              <!--                  <a> 46 </a>-->
              <!--                </li>-->
              <!--                <li class="list-group-item" style="border: none;">-->
              <!--                  <font-awesome-icon icon="fa-solid fa-clock" class="mx-1" />-->
              <!--                  <a> {{art.auctionArt.auctionStartDate.split("T")[0]}} ~-->
              <!--                    {{art.auctionArt.auctionEndDate.split("T")[0]}}</a>-->
              <!--                </li>-->
              <!--              </ul>-->
            </div>

            <hr>

            <div class="mb-2">
              <h4>시작 경매가 | {{ art.auctionArt.artInitPrice }}원</h4>
              <h4>현재 경매가 | {{ art.auctionArt.highestBidPrice }}원 ({{ art.auctionArt.highestBidUserNickname }})</h4>
            </div>
          </div>
          <br>
          <div class="card_area">
            <div class="row g-3">
              <div class="col-md-3">
                <a href="#" @click="likeArtControl" :class="likeButtonStyle" style="border-radius: 6px; width: 120px;">
                  <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                </a>
              </div>
              <div v-if="isForSale === true" class="col-md-2">
                <a href="#" class="btn btn-lg btn-outline-success" data-bs-toggle="modal" data-bs-target="#auctionModal"
                  style="border-radius: 6px; width: 120px;">입찰하기</a>
              </div>
              <div v-if="isForSale === false && isHighestBidUser === true" class="col-md-2 offset-md-2">
                <a href="#" class="btn btn-lg btn-outline-primary" @click="purchaseArt"
                  style="border-radius: 6px; width: 120px;">구매하기</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
    <br><br><br>
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
        // userId: JSON.parse(sessionStorage.getItem('loginData')).userId,
        userId: parseInt(this.$store.state.loginData.userId),
      },
      purchaseData: {
        auctionId: this.$store.state.selectedArt.auctionArt.auctionId,
        // userId: JSON.parse(sessionStorage.getItem('loginData')).userId,
        userId: parseInt(this.$store.state.loginData.userId),
      },
      likeData: {
        artId: this.$store.state.selectedArt.auctionArt.artId,
        // userId: JSON.parse(sessionStorage.getItem('loginData')).userId,
        userId: parseInt(this.$store.state.loginData.userId),
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
        console.log('this.bidData -> ' + JSON.stringify(this.bidData));
        console.log('bid success -> ' + JSON.stringify(res));
        alert('입찰 완료');
        this.$router.push('/');
      }).catch((err) => {
        let errMsg = JSON.stringify(err.response.data.message);
        errMsg = errMsg.substring(1, errMsg.length - 1);
        console.log("errMsg -> " + errMsg);
        if (this.bidData.userId == 0) {
          alert('로그인하지 않은 사용자입니다');
        } else {
          alert(errMsg);
        }
      })
    },
    purchaseArt() {
      axios.post('/api/purchase/auction', this.purchaseData).then((res) => {
        console.log("req: " + JSON.stringify(this.purchaseData));
        console.log("res: " + JSON.stringify(res.data));
        alert('구매 완료');
        this.$router.push('/');
      }).catch((err) => {
        let errMsg = JSON.stringify(err.response.data.message);
        errMsg = errMsg.substring(1, errMsg.length - 1);
        console.log("errMsg -> " + errMsg);
        if (this.purchaseData.userId == 0) {
          alert('로그인하지 않은 사용자입니다');
        } else {
          alert(errMsg);
        }
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
        }).catch((err) => {
          let errMsg = JSON.stringify(err.response.data.message);
          errMsg = errMsg.substring(1, errMsg.length - 1);
          console.log("errMsg -> " + errMsg);
          if (this.likeData.userId == 0) {
            alert('로그인하지 않은 사용자입니다');
          } else {
            alert(errMsg);
          }
        })
        // 작품 좋아요 취소
      } else if (this.isLiked) {
        axios.post('/api/art/cancel', this.likeData).then((res) => {
          console.log("req: " + JSON.stringify(this.likeData));
          console.log("res: " + JSON.stringify(res.data));
          this.isLiked = false;
          this.likeButtonStyle = 'btn btn-lg btn-outline-danger';
        }).catch((err) => {
          let errMsg = JSON.stringify(err.response.data.message);
          errMsg = errMsg.substring(1, errMsg.length - 1);
          console.log("errMsg -> " + errMsg);
          if (this.likeData.userId == 0) {
            alert('로그인하지 않은 사용자입니다');
          } else {
            alert(errMsg);
          }
        });
      }
    },
  },
  beforeMount() {
    // console.log("this.$store.state.loginData -> " + this.$store.state.loginData);
    // console.log("sessionStorage.getItem('loginData') -> " + JSON.parse(sessionStorage.getItem('loginData')));
    // console.log("isNaN(sessionStorage.getItem('loginData')) -> " + isNaN(JSON.parse(sessionStorage.getItem('loginData'))));
    console.log("userId -> " + this.bidData.userId);
    // if (isNaN(sessionStorage.getItem('loginData'))) {
    //   this.bidData.userId = '초기값';
    //   this.purchaseData.userId = '초기값';
    //   this.likeData.userID = '초기값';
    // }
    this.art = { ...this.$store.state.selectedArt };
    let today = new Date();
    let endDate = new Date(this.art.auctionArt.auctionEndDate);
    let remainTime = endDate.getTime() - today.getTime();

    axios.get('/api/session-check').then((res) => {
      console.log('session-check success ' + JSON.stringify(res.data));
      if (remainTime <= 0) {
        this.isForSale = false;
        if (this.art.highestBidUserId == res.data.id) {
          this.isHighestBidUser = true;
          alert('축하합니다! 낙찰입니다. 작품을 구매하세요.');
        } else {
          alert('작품이 다른 사용자에게 낙찰되었습니다. 메인 페이지로 이동합니다.');
          this.$router.push('/');
        }
      }
    }).catch((res) => {
      console.log('session-check fail ' + res);
    })
  },
}
</script>

<style>

</style>