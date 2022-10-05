<template>
  <div id="wrapper" class="container-fluid">
    <div class="container px-4s my-5">

      <!-- passwordFindModal -->
      <div class="modal fade" id="passwordFindModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
        aria-labelledby="staticBackdropLabel" aria-hidden="true">
        <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
          <div class="modal-content">
            <div class="modal-header">
              <h5 class="modal-title" id="staticBackdropLabel">비밀번호 찾기 결과</h5>
              <button type="button" @click="modalControl(false)" class="btn-close" data-bs-dismiss="modal"
                aria-label="Close"></button>
            </div>
            <div class="modal-body">
              <div v-show="isShow" class="mt-3 text-center">
                <h4>비밀번호: {{ result.loginPassword }}</h4>
              </div>
              <div v-show="!isShow" class="mt-3 text-center">
                <h4>사용자 정보를 입력해주세요.</h4>
              </div>
            </div>
            <div class="modal-footer">
              <button type="button" @click="$router.push('/userLogin')" class="btn btn-outline-primary"
                data-bs-dismiss="modal">로그인하기</button>
              <button type="button" @click="modalControl(false)" class="btn btn-outline-secondary"
                data-bs-dismiss="modal">닫기</button>
            </div>
          </div>
        </div>
      </div>

      <!--모달-->
      <!-- <div class="black-bg" v-show="isShow">
            <div class="white-bg">
              <h5>비밀번호 찾기 결과</h5>
              <p>비밀번호 : {{result.loginPassword}}</p>
              <button @click="modalControl" type="button" class="btn btn-outline-dark btn-lg col-md-6 offset-md-3 p-3" style="opacity: 0.7;">
                닫기
              </button>
            </div>
          </div> -->

      <div class="row g-3 m-auto">

        <div class="p-4">
          <h4 class="mb-3 text-center text-black fs-3" style="--bs-text-opacity: 0.6;">비밀번호 찾기</h4>
        </div>

        <div class="col-md-4 offset-md-4">
          <input type="text" v-model="passwordFindForm.name" class="form-control form-control-lg p-3" id="name"
            name="name" placeholder="이름" required>
        </div>

        <div class="col-md-4 offset-md-4">
          <input type="text" v-model="passwordFindForm.loginId" class="form-control form-control-lg p-3" id="loginId"
            name="loginId" placeholder="아이디" required>
        </div>

        <div class="col-md-4 offset-md-4">
          <input type="email" v-model="passwordFindForm.email" class="form-control form-control-lg p-3" id="email"
            name="email" placeholder="이메일" required>
        </div>

      </div>

      <div class="row">
        <hr class="my-4 col-md-8 offset-md-2 border border-1 border-dark" style="opacity: 0.1;">
      </div>

      <div class="row g-3">
        <div class="col-md-4 offset-md-4">
          <button @click="submit" class="btn btn-outline-primary btn-lg col-md-12 p-3" style="opacity: 0.7;"
            type="submit" id="signup" data-bs-toggle="modal" data-bs-target="#passwordFindModal">계속</button>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'passwordFind',

  data() {
    return {
      isShow: false,
      result: {},
      passwordFindForm: {
        email: '',
        name: '',
        loginId: '',
      },
    }
  },

  methods: {
    submit() {
      axios.post('/api/find/password', this.passwordFindForm)
        .then((res) => {
          this.result = { ...res.data };
          console.log(JSON.stringify(res.data));
          this.isShow = true;
        }).catch((res) => {
          console.log("catch " + res.data);
        })
    },
    modalControl(boolean) {
      this.isShow = boolean;
    },
  },
}
</script>

<style>
.black-bg {
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.6);
  position: fixed;
}

.white-bg {
  width: 90%;
  margin: 80px auto;
  background: white;
  border-radius: 5px;
  padding: 20px 0;
}
</style>