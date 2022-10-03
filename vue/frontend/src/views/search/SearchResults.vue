<template>
  <div id="searchpage">
    <!-- 경매 검색결과 -->
    <div v-if="searchType == 'auction'" className="product_container">
      <div className="product" v-for="(art,i) in searchData.artList" :key="i">
        <div>
          <a @click="goPurchasePage(art)" href="#"><img
              :src="require(`../../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)" alt=""
              style="width: 100%;"></a>
          <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
            <h5 className="product_title"> {{art.auctionArt.artName}}</h5>
          </a>
          <p className="product_des"> {{art.auctionArt.artDescription}}</p>
          <p className="product_des"> 작품 등록 시간 : {{art.auctionArt.registerDate}}</p>
          <p className="product_des"> 시작 경매가 : {{art.auctionArt.artInitPrice}}원</p>
          <p className="product_des"> 현재 경매가 : {{art.auctionArt.highestBidPrice}}원</p>
          <p className="product_des"> 경매 기간 : {{art.auctionArt.auctionStartDate}} ~ {{art.auctionArt.auctionEndDate}}
          </p>
          <p className="product_des"> 현재 경매횟수 : {{art.auctionArtBidCount}}회</p>
          <p className="product_des"> 작가 : {{art.auctionArt.artOwnerNickname}}</p>
          <div className="product_hash">
            <div className="product_tag" v-for="(tag, index) in art.artHashtagList" :key="index" small label>
              #{{tag}}
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- 일반 검색결과 -->
    <div v-if="searchType == 'general'" className="product_container">
      <div className="product" v-for="(art,i) in searchData.artList" :key="i">
        <div>
          <a @click="goPurchasePage(art)" href="#"><img
              :src="require(`../../../../../src/main/resources/images/${art.generalArt.artStorageName}`)" alt=""
              style="width: 100%;"></a>
          <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
            <h5 className="product_title"> {{art.generalArt.artName}}</h5>
          </a>
          <p className="product_des"> {{art.generalArt.artDescription}}</p>
          <p className="product_des"> 작품 등록 시간 : {{art.generalArt.artRegisterDate}}</p>
          <p className="product_des"> 현재 판매가 : {{art.generalArt.artInitPrice}}원</p>
          <p className="product_des"> 작가 : {{art.generalArt.artOwnerNickname}}</p>
          <div className="product_hash">
            <div className="product_tag" v-for="(tag, index) in art.artHashtagList" :key="index" small label>
              #{{tag}}
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>




</template>

<script>
export default {
  name: "SearchPage",
  data: () => ({
    searchType: '',
    searchData: {},
    auctionArtData: [{
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
    }],
  }),
  beforeMount() {
    this.searchData = { ...this.$store.state.searchData };
    this.searchType = this.$store.state.searchType;
    console.log("searchType: " + this.searchType);
  },
  methods: {
    goPurchasePage(art) {
      this.$store.commit("setSelectedArt", art);
      console.log("selectedArt: " + JSON.stringify(this.$store.state.selectedArt));
      this.$router.push('/artworkPurchase');
    }
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

#searchrequire {
  display: flex;
  width: 100%;
  justify-content: space-evenly;
  margin-bottom: 20px;
}

#input-default {
  width: 70%;
}

#searchpage {
  padding: 30px 30px;
}

#note {
  font-size: 20px;
  text-align: center;
}
</style>