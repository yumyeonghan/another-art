<template>
  <div id="searchpage">
    <!-- 경매 검색결과 -->
    <div v-if="searchType === 'auction'">
      <div class="album py-5">
        <div class="container">
          <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
            <div v-for="(art,i) in searchData.artList" :key="i">
              <div class="col">
                <div class="card" style="border: 3px solid cornflowerblue;">
                  <div class="card-header" style="background-color: white;">
                    <a @click="goPurchasePage(art)" href="#"><img
                        :src="require(`../../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)" alt=""
                        style="width: 100%;"></a>
                    <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
                      <h3> {{art.auctionArt.artName}}</h3>
                      <p> {{art.auctionArt.artDescription}}</p>
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
                  </div>
                  <div class="card-footer" style="background-color: white;">
                    <span className="product_tag" v-for="(tag,index) in art.artHashtagList" :key="index" small label>#{{tag}}</span>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <!-- 일반 검색결과 -->
    <div v-if="searchType === 'general'">
      <div class="album py-5">
        <div class="container">
          <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
            <div v-for="(art,i) in searchData.artList" :key="i">
              <div class="col">
                <div class="card" style="border: 3px solid cornflowerblue;">
                  <div class="card-header" style="background-color: white;">
                    <a @click="goPurchasePage(art)" href="#"><img
                        :src="require(`../../../../../src/main/resources/images/${art.generalArt.artStorageName}`)" alt=""
                        style="width: 100%;"></a>
                    <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
                      <h3> {{art.generalArt.artName}}</h3>
                      <p> {{art.generalArt.artDescription}}</p>
                    </a>
                  </div>
                  <div class="card-body" style="background-color: white;">
                    <p><b>작가</b><br><small>{{art.generalArt.artOwnerNickname}}</small></p>
                    <p><b>현재 판매가</b><br><small>{{art.generalArt.artInitPrice}}원</small></p>
                    <p><b>작품 등록 시간</b><br><small>{{art.generalArt.artRegisterDate}}</small></p>
                    <p><b>좋아요 횟수</b><br><small>{{art.artLikeCount}}회</small></p>
                  </div>
                  <div class="card-footer" style="background-color: white;">
                    <span className="product_tag" v-for="(tag,index) in art.artHashtagList" :key="index" small label>#{{tag}}</span>
                  </div>
                </div>
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
  name: "SearchPage",
  data: () => ({
    searchType: '',
    searchData: {},
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