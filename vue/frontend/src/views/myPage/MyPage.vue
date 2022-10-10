<template>
  <div>
    <router-view></router-view>
    <div class="container py-3">
      <div class="p-4 mb-2 bg-light rounded-3">
        <h1 class="name">{{ userData.userNickname }}</h1>
        <hr>
        <div class="listContainer rounded-3">
          <h3 class="text-center p-3">포인트 내역</h3>
          <div class="listContainer">
            <div class="item">
              <div class="text">전체 포인트</div>
              <div class="right"> {axios get 전체 포인트} </div>
            </div>
          </div>
          <div class="listContainer">
            <div class="item">
              <div class="smallLight">사용 가능 포인트</div>
              <div class="right smallLight"> {axios get 사용 가능 포인트} </div>
            </div>
          </div>

        </div>

        <div class="listContainer rounded-3">
          <div class="smallContainer">
            <div class="item">
              <div class="text">이름</div>
              <div class="right"> {{ userData.userName }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">닉네임</div>
              <div class="right"> {{ userData.userNickname }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">이메일</div>
              <div class="right"> {{ userData.email }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">전화번호</div>
              <div class="right"> {{ userData.phoneNumber }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">생년월일</div>
              <div class="right"> {{ userData.birth }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">재학중인 학교</div>
              <div class="right"> {{ userData.schoolName }} </div>
            </div>
          </div>
          <div class="smallContainer">
            <div class="item">
              <div class="text">주소</div>
              <div class="right"> {{ userData.address }} </div>
            </div>
          </div>
        </div>
      </div>

      <div class="row align-items-md-stretch">
        <div class="col-md-6 p-3">
          <div class="h-100 p-3 text-bg-light rounded-3">
            <a @click="$router.push('/userPointCharge')" href="#" class="item" style="color: black; text-decoration: none;">
              <h4 class="text-center">포인트 충전 ></h4>
            </a>
          </div>
        </div>

        <div class="col-md-6 p-3">
          <div class="h-100 p-3 text-bg-light rounded-3">
            <a @click="$router.push('/updateUserInfo')" href="#" class="item" style="color: black; text-decoration: none;">
              <h4 class="text-center">회원정보 수정 ></h4>
            </a>
          </div>
        </div>

        <!--        <div class="row align-items-md-stretch">-->
        <!--          <div class="col-md-6">-->
        <!--            <div class="h-100 p-5 text-bg-dark rounded-3">-->
        <!--              <h2>Change the background</h2>-->
        <!--              <p>Swap the background-color utility and add a `.text-*` color utility to mix up the jumbotron look. Then, mix and match with additional component themes and more.</p>-->
        <!--              <button class="btn btn-outline-light" type="button">Example button</button>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--          <div class="col-md-6">-->
        <!--            <div class="h-100 p-5 bg-light border rounded-3">-->
        <!--              <h2>Add borders</h2>-->
        <!--              <p>Or, keep it light and add a border for some added definition to the boundaries of your content. Be sure to look under the hood at the source HTML here as we've adjusted the alignment and sizing of both column's content for equal-height.</p>-->
        <!--              <button class="btn btn-outline-secondary" type="button">Example button</button>-->
        <!--            </div>-->
        <!--          </div>-->
        <!--        </div>-->

        <div class="infoContainer">
          <a href="#" class="item">
            <div>icon</div>
            <div>공지사항</div>
          </a>
          <a href="#" class="item">
            <div>icos</div>
            <div>이용안내</div>
          </a>
          <a href="#" class="item">
            <div>icon</div>
            <div>고객센터</div>
          </a>
        </div>

      </div>
    </div>

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'myPage',
  data() {
    return {
      userData: {
        userName: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userName,
        userNickname: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userNickname,
        email: '',
        phoneNumber: '',
        birth: '1999-10-13',
        schoolName: '경기대학교',
        address: '',
      }
    }
  },
  beforeMount() {
    axios.get('/api/session-check').
    then((res) => {
      console.log("session-check success: " + JSON.stringify(res.data));
      this.$store.commit('setSessionData', res.data);
      console.log('sessionData: ' + JSON.stringify(this.$store.state.sessionData));

      this.userData.email = this.$store.state.sessionData.email;
      this.userData.phoneNumber = this.$store.state.sessionData.phoneNumber;
      this.userData.address = this.$store.state.sessionData.address;
    }).catch((res) => {
      console.log('session-check fail:' + res);
    });
  },
}
</script>

<style>
body {
  padding: 0;
  margin: 0;
}

div {
  box-sizing: border-box;
}

/* alert badge */
.circle {
  display: inline-block;
  width: 5px;
  height: 5px;
  border-radius: 2.5px;
  background-color: #ff0000;
  position: absolute;
  top: -5px;
  left: 110%;
}

/* 녹색 텍스트 */
.gray {
  color: gray;
}

.wrap {
  background-color: #F8F8F8;
}

/* 녹색배경 */
.grayContainer {
  height: 100px;
  background-color: gray;

  display: flex;
  align-items: flex-end;
  padding: 16px;
}

.grayContainer .name {
  font-size: 20px;
  font-weight: bold;
  color: #ffffff;
}

.grayContainer .modify {
  margin-left: auto;
}

/* 단골상점 , 상품후기 , 적립금 박스 */
.summaryContainer {
  background-color: white;
  display: flex;
  padding: 21px 16px;
  height: 90px;
  margin-bottom: 10px;
}

/* 단골상점 , 상품후기 , 적립금 */
.summaryContainer .item {
  flex-grow: 1
}

/* 녹색 숫자 */
.summaryContainer .number {
  font-size: 19px;
  font-weight: bold;
  color: black;
}

/* 텍스트 */
.summaryContainer .item>div:nth-child(2) {
  font-size: 13px;
}

.small {
  font-size: 10px;
}

/* ================== 주문/배송조회 박스 시작 ==================== */
.shippingStatusContainer {
  padding: 21px 16px;
  background-color: white;
  margin-bottom: 10px;
}

/* 주문/배송조회 타이틀 */
.shippingStatusContainer .title {
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
}

/* 장바구니 결제완료 배송중 구매확정 [로우] */
.shippingStatusContainer .status {
  display: flex;
  justify-content: space-between;
  margin-bottom: 21px;
}

/* 장바구니 결제완료 배송중 구매확정 [아이템]  */
.shippingStatusContainer .item {
  display: flex;
}

.shippingStatusContainer .number {
  font-size: 31px;
  font-weight: 500;
  text-align: right;
}

.shippingStatusContainer .text {
  font-size: 12px;
  font-weight: normal;
  color: #c2c2c2;
  text-align: center;
}

.shippingStatusContainer .icon {
  display: flex;
  align-items: center;
  padding: 20px;
  width: 16px;
  height: 16px;
}

.shippingStatusContainer .right {
  padding: 1px;
  text-align: center;
}


/*=================== 주문목록 ~ 찜한상품 리스트 ==================*/
.listContainer {
  padding: 0;
  background-color: #ffffff;
  margin-bottom: 10px;
}

.listContainer .item {
  display: flex;
  align-items: center;
  padding: 16px;
  color: black;
  text-decoration: none;
  height: 56px;
  box-sizing: border-box;
}

.listContainer .icon {
  margin-right: 14px;
}

.listContainer .text {
  font-size: 16px;
  position: relative;
}

.listContainer .right {
  margin-left: auto;
}


/*=================== 내지갑의 보유 적립금 들어가는 부분 ================*/
.listContainer .smallLight {
  font-size: 14px;
  color: #c2c2c2;
}

.listContainer .smallLight>span {
  margin-left: 10px;
}

.listContainer .right .blct {
  font-size: 14px;
  font-weight: bold;
  margin-right: 5px;
}



/* 공지사항 이용안내 고객센터 */
.infoContainer {
  background-color: white;
  display: flex;
  height: 100px;
  margin-bottom: 10px;
}

.infoContainer .item {
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  font-size: 13px;
  text-decoration: none;
  color: black;
}

.infoContainer .item>div:first-child {
  margin-bottom: 2px;
}

.smallContainer {
  padding: 0;
  background-color: #ffffff;
}

.smallContainer .item {
  display: flex;
  align-items: center;
  padding: 16px;
  color: black;
  text-decoration: none;
  height: 56px;
  box-sizing: border-box;
}

.smallContainer .icon {
  margin-right: 13px;
}

.smallContainer .text {
  font-size: 13px;
  position: relative;
}

.smallContainer .right {
  margin-left: auto;
  font-size: 15px
}

/*  */
.listContainer .item:hover {
  /*   background-color: #f8f8f8; */
}

.infoContainer .item:hover {
  /*   background-color: #f8f8f8; */
}
</style>