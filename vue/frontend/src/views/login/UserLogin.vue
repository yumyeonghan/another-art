<template>
  <div>
    <div class="p-5 pb-4 border-bottom-0">
      <h2 class="mb-0 text-center">로그인</h2>
      <!-- <div class="text-center">
      <button @click="test1" class="btn btn-outline-primary">sessionSet</button>
      <button @click="test2" class="btn btn-outline-success">sessionGet</button>
      <button @click="test3" class="btn btn-outline-danger">sessionDelete</button>
      </div> -->
    </div>
    <div class="row g-3">
      <div class="col-md-6 offset-md-3">
        <input type="text" v-model="loginData.loginId" class="form-control form-control-lg p-2" id="loginId"
               name="loginId" placeholder="아이디" required>
      </div>

      <div class="col-md-6 offset-md-3">
        <input type="password" v-model="loginData.loginPassword" class="form-control form-control-lg p-2"
               id="loginPassword" name="loginPassword" placeholder="비밀번호" required>
      </div>

      <div class="col-md-6 offset-md-3">
        <button @click="login" class="btn btn-outline-primary btn-lg col-md-12 p-2">로그인</button>
      </div>
    </div>

    <div class="row">
      <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
    </div>

    <div class="text-center">
      <button class="link-color-black btn btn-light" @click="$router.push('/idFind')">아이디 찾기</button> |
      <button class="link-color-black btn btn-light" @click="$router.push('/passwordFind')">비밀번호 찾기/재설정</button> |
      <button class="link-color-black btn btn-light" @click="$router.push('/createAccount/userRegister')">회원가입</button>
    </div>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>
    <br>

    <!--    <div class="text-center">-->
    <!--      <a @click="$router.push('/idFind')" href="#">아이디 / </a>-->
    <!--      <a @click="$router.push('/passwordFind')" href="#">비밀번호 찾기 </a>-->
    <!--      <a @click="$router.push('/createAccount/userRegister')" class="mx-5" href="#">회원가입</a>-->
    <!--    </div>-->

  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'userLogin',
  data() {
    return {
      // 로그인 시 폼 입력 정보
      loginData: {
        loginId: '',
        loginPassword: '',
      },
      // 로그인한 유저 정보
      loginedData: {
        userId: '50',
        userName: 'user50',
        userNickname: 'user50-Nickname',
        loginId: 'user50-Id',
      }
    }
  },
  methods: {
    login() {
      axios.post('/api/login', this.loginData)
          .then((res) => {
            this.loginedData = JSON.stringify(res.data);
            sessionStorage.setItem("loginData", JSON.stringify(this.loginedData));
            console.log("sessionStorage loginData: " + JSON.parse(sessionStorage.getItem("loginData")));
            this.$store.commit("setIsLogined", true);
            this.$store.commit("setLoginUserId", this.loginedData.userId);
            this.$store.commit("setLoginUserId", JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId);
            console.log("userId store -> " + this.$store.state.loginUserId);
            this.$router.push('/vue');
          })
          .catch((res) => {
            console.log("catch " + res.data);
          })
    },
  },
}
</script>

<style>
.link-color-black {
  text-decoration: none;
  /*color: black;*/
}
</style>