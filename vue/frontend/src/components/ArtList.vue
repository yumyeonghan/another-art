<template>
  <div class="col">
    <!-- style="border: 1px solid cornflowerblue;" -->
    <div class="card border-black-50">
      <!-- <div class="card-header" style="background-color: white;">
        <a @click="goPurchasePage(art)" href="#"><img
            :src="require(`../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)" alt=""
            style="width: 100%;"></a>
        <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
          <h3> {{ art.auctionArt.artName }}</h3>
          <p> {{ art.auctionArt.artDescription }}</p>
        </a>
      </div>
      <div class="card-body" style="background-color: white;">
        <p><b>작가</b><br><small>{{ art.auctionArt.artOwnerNickname }}</small></p>
        <p><b>작품 등록 시간</b><br><small>{{ art.auctionArt.artRegisterDate }}</small></p>
        <p><b>시작 경매가</b><br><small>{{ art.auctionArt.artInitPrice }}원</small></p>
        <p><b>현재 경매가</b><br><small>{{ art.auctionArt.highestBidPrice }}원</small></p>
        <p><b>경매 시작 날짜</b><br><small>{{ art.auctionArt.auctionStartDate }}</small></p>
        <p><b>경매 종료 날짜</b><br><small>{{ art.auctionArt.auctionEndDate }}</small></p>
        <p><b>현재 경매횟수</b><br><small>{{ art.auctionArtBidCount }}회</small></p>
      </div> -->
      <div class="" style="background-color: white;">
        <a @click="goPurchasePage(art)" href="#"><img
            :src="require(`@/../../../src/main/resources/images/${art.auctionArt.artStorageName}`)" alt=""
            style="width: 100%; height: 200px; margin-bottom: 10px;"></a>
      </div>
      <div class="card-header" style="background-color: white;">
        <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
          <h3> {{ art.auctionArt.artName }}</h3>
          <h6> {{ art.auctionArt.artOwnerNickname }} </h6>
        </a>
      </div>
      <div class="card-body" style="background-color: white;">
        <p><b>현재 경매가</b><br><small>{{ art.auctionArt.highestBidPrice }}원</small></p>
        <p><b>현재 응찰횟수</b><br><small>{{ art.auctionArtBidCount }}회</small></p>
        <p><b>경매 종료 날짜</b><br><small>{{ art.auctionArt.auctionEndDate }}</small></p>
      </div>
      <div class="card-footer" style="background-color: white; height: 65px;">
        <span className="product_tag" v-for="(tag, index) in art.artHashtagList" :key="index" small label>#{{ tag
        }}</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'artList',
  props: {
    art: Object,
  },
  methods: {
    goPurchasePage(art) {
      this.$store.commit("setSelectedArt", art);
      this.$store.commit("setSearchType", 'auction');
      console.log("selectedArt: " + JSON.stringify(this.$store.state.selectedArt));
      this.$router.push('/art/detail');
    },
  }
}
</script>

<style>
.product_container {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  width: 100%;
}

.product {
  width: 23%;
  height: 20%;
  margin-bottom: 4%;
  margin: 5px;
  padding: 0.5%;
  border: 1px solid black;
  border-radius: 10px;
}

.product_img_div {
  text-align: center;
}

.product_img {
  max-width: 95%;
  max-height: 100%;
}

.product_title {
  font-size: 1.3rem;
  font-weight: 600;
  margin: 4%;
}

.product_des {
  font-size: 0.8rem;
  margin: 2% 3% 2% 4%;
}

.product_hash {
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
}

.product_tag {
  background: lightgray;
  border-radius: 4px;
  margin: 0.5%;
  padding-left: 7px;
  padding-right: 7px;
  font-size: 0.8rem;
  font-style: italic;
  font-weight: bold;
}
</style>