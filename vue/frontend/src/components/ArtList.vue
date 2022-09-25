<template>
  <div>
  <img v-bind:src="require(`../../../../src/main/resources/images/${a.art.artImage}`)" alt="" style="width: 100%;">
  <h5 className="product_title"> {{a.art.name}}</h5>
  <p className="product_des"> {{a.art.description}}</p>
  <p className="product_des"> 작품 등록 시간 : {{a.art.registerDate}}</p>
  <p className="product_des"> 현재 경매가 : {{a.hightesBidPrice}}원</p>
  <p className="product_des"> 현재 경매횟수 : {{a.bidCount}}회</p>
  <p className="product_des"> 작가 : {{a.art.artOwner.nickname}}</p>
  <p className="product_des"> {{a.art.bidCount}}</p>
  <div className="product_hash">
    <div className="product_tag" v-for="(tag,index) in a.art.hashtagList" :key="index" small label>#{{tag}}</div>
  </div>
  </div>
</template>

<script>
export default {
  name: 'artList',
  props: ["a"]
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
.product_img_div{
  text-align: center;
}
.product_img{
  max-width:95%;
  max-height:100%;
}
.product_title{
  font-size : 1.3rem;
  font-weight: 600;
  margin : 4%;
}
.product_des{
  font-size : 0.8rem;
  margin : 2% 3% 2% 4%;
}
.product_hash{
  display: flex;
  flex-direction: row;
  flex-wrap: wrap;
  align-items: flex-start;
}
.product_tag{
  background: lightgray;
  border-radius: 4px;
  margin:0.5%;
  padding-left: 7px;
  padding-right: 7px;
  font-size: 0.8rem;
}

</style>