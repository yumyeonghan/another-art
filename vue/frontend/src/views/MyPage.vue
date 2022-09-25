<template>
  <div>
    <div class="wrap">
      <div class="grayContainer">
        <div>
          <div class="name">닉네임</div>
        </div>
      </div>
      <div class="listContainer">
        <a href="#" class="item">
          <div class="icon">ii</div>
          <div class="text">
            <span>내지갑</span>
            <span class="smallLight">
            <span>|</span>
            <span>보유 적립금</span>
          </span>
          </div>
          <div class="right">
            <span class="blct">175 BLCT</span>
            > </div>
        </a>
      </div>
      <div class="shippingStatusContainer">
        <div class="listContainer">
          <a href="#" class="item">
            <div class="icon">ii</div>
            <div class="text">
              <span>내 정보</span>
            </div>
            <div class="right">
              <span class="blct">수정하러 가기</span>
              > </div>
          </a>
        </div>
        <hr>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">사용자 이름<span class="circle"></span></div>
          <div class="right"> ddd </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">사용자 닉네임</div>
          <div class="right"> dd </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">사용자 이메일</div>
          <div class="right"> dd@naver.com </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">사용자 전화번호</div>
          <div class="right"> 010-4848-1234 </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">사용자 생년월일</div>
          <div class="right"> 1234.12.34 </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">재학중인 학교</div>
          <div class="right"> dd대학교 </div>
        </div>
        </div>
        <div class="smallContainer">
        <div class="item">
          <div class="icon">ii</div>
          <div class="text">주소</div>
          <div class="right"> ㅇㅇ시 </div>
        </div>
        </div>
      </div>

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
</template>

<script>
export default {
  name: 'myPage'
}
</script>

<style>
body{
  padding: 0;
  margin: 0;
}
div{
  box-sizing: border-box;
}

/* alert badge */
.circle{
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
.gray{
  color: gray;
}

.wrap{
  background-color: #F8F8F8;
}
/* 녹색배경 */
.grayContainer{
  height: 100px;
  background-color: gray;

  display: flex;
  align-items: flex-end;
  padding: 16px;
}

.grayContainer .name{
  font-size: 20px;
  font-weight: bold;
  color: #ffffff;
}
.grayContainer .modify{
  margin-left: auto;
}

/* 단골상점 , 상품후기 , 적립금 박스 */
.summaryContainer{
  background-color: white;
  display: flex;
  padding: 21px 16px;
  height: 90px;
  margin-bottom: 10px;
}
/* 단골상점 , 상품후기 , 적립금 */
.summaryContainer .item{
  flex-grow: 1
}
/* 녹색 숫자 */
.summaryContainer .number{
  font-size: 19px;
  font-weight: bold;
  color: black
;
}
/* 텍스트 */
.summaryContainer .item > div:nth-child(2){
  font-size: 13px;
}
.small{
  font-size: 10px;
}

/* ================== 주문/배송조회 박스 시작 ==================== */
.shippingStatusContainer{
  padding: 21px 16px;
  background-color: white;
  margin-bottom: 10px;
}

/* 주문/배송조회 타이틀 */
.shippingStatusContainer .title{
  font-size: 16px;
  font-weight: bold;
  margin-bottom: 15px;
}

/* 장바구니 결제완료 배송중 구매확정 [로우] */
.shippingStatusContainer .status{
  display: flex;
  justify-content: space-between;
  margin-bottom: 21px;
}
/* 장바구니 결제완료 배송중 구매확정 [아이템]  */
.shippingStatusContainer .item{
  display: flex;
}

.shippingStatusContainer .number{
  font-size: 31px;
  font-weight: 500;
  text-align: right;
}
.shippingStatusContainer .text{
  font-size: 12px;
  font-weight: normal;
  color: #c2c2c2;
  text-align: center;
}
.shippingStatusContainer .icon{
  display: flex;
  align-items: center;
  padding: 20px;
  width: 16px;
  height: 16px;
}
.shippingStatusContainer .right{
  padding: 1px;
  text-align: center;
}


/*=================== 주문목록 ~ 찜한상품 리스트 ==================*/
.listContainer{
  padding: 0;
  background-color: #ffffff;
  margin-bottom: 10px;
}
.listContainer .item{
  display: flex;
  align-items: center;
  padding: 16px;
  color: black;
  text-decoration: none;
  height: 56px;
  box-sizing: border-box;
}
.listContainer .icon{
  margin-right: 14px;
}
.listContainer .text{
  font-size: 16px;
  position: relative;
}
.listContainer .right{
  margin-left: auto;
}


/*=================== 내지갑의 보유 적립금 들어가는 부분 ================*/
.listContainer .smallLight{
  font-size: 14px;
  color: #c2c2c2;
}
.listContainer .smallLight > span{
  margin-left: 10px;
}

.listContainer .right .blct{
  font-size: 14px;
  font-weight: bold;
  margin-right: 5px;
}



/* 공지사항 이용안내 고객센터 */
.infoContainer{
  background-color: white;
  display: flex;
  height: 100px;
  margin-bottom: 10px;
}

.infoContainer .item{
  flex-grow: 1;
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: column;
  font-size: 13px;
  text-decoration: none;
  color: black;
}
.infoContainer .item > div:first-child{
  margin-bottom: 2px;
}

.smallContainer{
  padding: 0;
  background-color: #ffffff;
}
.smallContainer .item{
  display: flex;
  align-items: center;
  padding: 16px;
  color: black;
  text-decoration: none;
  height: 56px;
  box-sizing: border-box;
}
.smallContainer .icon{
  margin-right: 13px;
}
.smallContainer .text{
  font-size: 13px;
  position: relative;
}
.smallContainer .right{
  margin-left: auto;
  font-size: 15px
}

/*  */
.listContainer .item:hover{
  /*   background-color: #f8f8f8; */
}
.infoContainer .item:hover{
  /*   background-color: #f8f8f8; */
}
</style>