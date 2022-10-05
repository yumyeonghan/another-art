<template>
  <div style="height: 448px;">
    <a @click="goPurchasePage(art)" href="#"><img
              :src="require(`../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)" alt=""
              style="width: 100%;"></a>
      <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
            <h5 className="product_title"> {{art.auctionArt.artName}}</h5>
          </a>
    <p className="product_des"> {{art.auctionArt.artDescription}}</p>
    <p className="product_des"> 작품 등록 시간 : {{art.auctionArt.registerDate}}</p>
    <p className="product_des"> 시작 경매가 : {{art.auctionArt.artInitPrice}}원</p>
    <p className="product_des"> 현재 경매가 : {{art.auctionArt.highestBidPrice}}원</p>
    <p className="product_des"> 경매 기간 : {{art.auctionArt.auctionStartDate.split("T")[0]}} ~ {{art.auctionArt.auctionEndDate.split("T")[0]}}
    </p>
    <p className="product_des"> 현재 경매횟수 : {{art.auctionArtBidCount}}회</p>
    <p className="product_des"> 작가 : {{art.auctionArt.artOwnerNickname}}</p>
    <div className="product_hash">
      <div className="product_tag" v-for="(tag,index) in art.artHashtagList" :key="index" small label>#{{tag}}</div>
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
      this.$router.push('/artworkPurchase');
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
}
</style>