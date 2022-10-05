<template>
  <div id="wrapper" class="container-fluid">
<!--정렬-->
<div>
    <div class="mb-3">
      <button @click="$router.push('/updateUserInfo')" class="btn btn-outline-primary">updateUserInfo</button>
    </div>
    <div class="mb-3">
      <button @click="test" class="btn btn-outline-primary">timeCheck</button>
    </div>
    <p>This is home</p>
  </div>
    <div>
      <li class="dropdown">
        <a class="dropdown-toggle" href="#" id="navbarDropdown" role="button" data-bs-toggle="dropdown" aria-expanded="false">
          작품 정렬
        </a>
        <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
          <li><a class="dropdown-item" href="#" @click="clickrRD">나중에 등록된순</a></li>
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" href="#" @click="clickBP">비드 가격 높은순</a></li>
          <li><a class="dropdown-item" href="#" @click="clickrBP">비드 가격 낮은순</a></li>
          <li><hr class="dropdown-divider"></li>
          <li><a class="dropdown-item" href="#" @click="clickBC">비드 횟수 많은순</a></li>
          <li><a class="dropdown-item" href="#" @click="clickrBC">비드 횟수 적은순</a></li>
        </ul>
      </li>
    </div>

<!-- 작품 리스트-->
    <div className="product_container" >
      <div className="product" v-for="(art,i) in contents" :key="i">
        <ArtList :art="art"/>
      </div>
    </div>

<!-- top 버튼-->
    <div id="container">
      <div class="btnArea">
        <button type="button" id="topBtn"  @click="ScrollToTop">top버튼</button>
      </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios'
// import PasswordFind from "@/views/PasswordFind";
import ArtList from "@/components/ArtList";

export default {
    name: 'appHome',
  components: {ArtList},
  data:()=>({
        scroll:0,
        contents : [],
        scrollCheck:0,
        img : '',
        artRequest: {
          scroll: 0,
          sort: "date",
        }
  }),
  created: function(){
      this.fetchData(),
    window.addEventListener('scroll',this.handleScroll)
  },
  beforeUnmount: function(){
    window.removeEventListener('scroll',this.handleScroll)
  },
  methods: {
    test() {
      let today = new Date();
    let endDate = new Date(this.$store.state.selectedArt.auctionArt.auctionEndDate);
    let remainTime = endDate.getTime() - today.getTime();

    console.log('현재시간 함수: ' + today);
    console.log('종료시간 데이터: ' + endDate);
    console.log('두 날짜의 차 ' + remainTime);
    console.log('종료까지 남은 시간 ' + Math.trunc((remainTime) / 1000) / 3600);
    console.log('종료까지 남은 일 수 ' + Math.trunc((remainTime) / 1000) / 3600 / 24);
    },
    fetchData() {
      axios.post(`api/main/arts`, this.artRequest)
          .then(res => {
            console.log("res.data.artList" + res.data.artList);
            this.contents = [...res.data.artList];
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    clickrRD(){
      this.scrollCheck = 0;
      this.$router.push({Path:'/', query: {sort:'rdate',scroll:this.scroll}})

    },
    clickBP(){
      this.scrollCheck = 0;
      this.$router.push({Path:'/', query: {sort:'price', scroll:this.scroll}})

    },
    clickrBP(){
      this.scrollCheck = 0;
      this.$router.push({Path:'/', query: {sort:'rprice', scroll:this.scroll}})

    },
    clickBC(){
      this.scrollCheck = 0;
      this.$router.push({Path:'/', query: {sort:'count', scroll:this.scroll}})
    },
    clickrBC(){
      this.scrollCheck = 0;
      this.$router.push({Path:'/', query: {sort:'rcount', scroll:this.scroll}})
    },

    handleScroll(){
      if((window.scrollY+document.documentElement.clientHeight)==document.documentElement.scrollHeight)
        console.log(this.scrollCheck+=1)
        this.$router.replace({query: { sort:this.$route.query.sort ,scroll: this.scrollCheck}})},
    ScrollToTop(){
      var body = document.getElementsByTagName("body")[0];
      window.scroll({
        behavior: 'smooth',
        left: 0,
        top:body.offsetTop});
    },
    test1() {
      console.log("contents: " + this.contents);
    },
}}

</script>

<style>
.product_container{
  display:flex;
  flex-direction: row;
  flex-wrap : wrap;
  width: 100%;
}

.product{
  width:23%;
  height: 20%;
  margin-bottom : 4%;
  margin: 5px;
  padding: 0.5%;
  border: 1px solid black;
  border-radius: 10px;
}


</style>