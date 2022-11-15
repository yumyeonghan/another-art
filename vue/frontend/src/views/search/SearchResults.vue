<template>
  <div id="searchpage">
    <!-- 경매 검색결과 -->
    <div v-if="searchType === 'auction'">
      <div class="album py-5">
        <div class="container">
          <div :class="rowStyle">
            <div v-for="(art,i) in searchData.artList" :key="i">
              <div class="col">
                <div class="card border-black-50">
                  <div class="" style="background-color: white;">
                    <a @click="goPurchasePage(art)" href="#"><img
                        :src="require(`../../../../../src/main/resources/images/${art.auctionArt.artStorageName}`)"
                        alt="" style="width: 100%; height: 200px; margin-bottom: 10px;"></a>
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
                    <span className="product_tag" v-for="(tag,index) in art.artHashtagList" :key="index" small label>#{{
                    tag }}</span>
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
                <div class="card border-black-50">
                  <div class="" style="background-color: white;">
                    <a @click="goPurchasePage(art)" href="#"><img
                        :src="require(`../../../../../src/main/resources/images/${art.generalArt.artStorageName}`)"
                        style="width: 100%; height: 200px; margin-bottom: 10px;"></a>
                  </div>
                  <div class="card-header" style="background-color: white;">
                    <a @click="goPurchasePage(art)" href="#" style="text-decoration: none; color: black;">
                      <h3> {{ art.generalArt.artName }}</h3>
                      <h6> {{ art.generalArt.artOwnerNickname }} </h6>
                    </a>
                  </div>
                  <div class="card-body" style="background-color: white;">
                    <p><b>현재 판매가</b><br><small>{{art.generalArt.artInitPrice}}원</small></p>
                    <p><b>좋아요 횟수</b><br><small>{{art.artLikeCount}}회</small></p>
                  </div>
                  <div class="card-footer" style="background-color: white; height: 65px;">
                    <span className="product_tag" v-for="(tag,index) in art.artHashtagList" :key="index" small
                      label>#{{tag}}</span>
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
    rowStyle: 'row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3',
    colStyle: 'col',
  }),
  beforeMount() {
    this.searchData = { ...this.$store.state.searchData };
    this.searchType = this.$store.state.searchType;
    if (this.searchData.contentSize == 1) {
      this.rowStyle='row g-3';
    } else {
      this.rowStyle='row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3';
    }
  },
  methods: {
    goPurchasePage(art) {
      this.$store.commit("setSelectedArt", art);
      console.log("selectedArt: " + JSON.stringify(this.$store.state.selectedArt));
      this.$router.push('/art/detail');
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