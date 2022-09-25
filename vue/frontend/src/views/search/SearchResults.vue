<template>
  <div id="searchpage">
    <div id="searchrequire">

        <input id="input-default" type="text">
      <div class="search-form-button">
        <button class="btn btn-secondary" type="submit" v-on:click="isShow=true">
          검색
        </button>
      </div>
    </div>


    <div v-show="isShow" className="product_container"  >
      <div className="product" v-for="(a,i) in contents" :key="i">
        <ArtList :a="a"/>
      </div>
    </div>
    <div id="note" v-show="!isShow">검색어를 입력해주세요.</div>

  </div>




</template>

<script>

import ArtList from "@/components/ArtList";
import axios from "axios";

export default {
  name: "SearchPage",
  components: {
    ArtList
  },
  data:()=>({
    contents : [],
    isShow:false,
  }),
  created: function(){
    this.fetchData()},
  methods: {
    fetchData() {
      //임의값임
      axios.get(`api/main/art?scroll=0&sort=date`)
          .then(res => {
            console.log(res.data.artList);
            this.contents = res.data.artList;
          })
          .catch(function (error) {
            console.log(error);
          });
    },
    }


  }
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
#searchrequire{
  display: flex;
  width: 100%;
  justify-content: space-evenly;
  margin-bottom: 20px;
}
#input-default{
  width: 70%;
}

#searchpage{
  padding: 30px 30px;
}

#note{
  font-size: 20px;
  text-align : center;
}

</style>