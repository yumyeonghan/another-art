<template>
  <div id="wrapper" class="container-fluid">
    <div class="container px-4s my-5">
      <div class="row g-3 m-auto">

        <div class="p-4">
          <h4 class="mb-3 text-center text-black fs-3" style="--bs-text-opacity: 0.6;">비밀번호 재설정</h4>
        </div>

        <div class="col-md-4 offset-md-4">
          <input type="password" v-model="passwordResetForm.changePassword" class="form-control form-control-lg p-3" id="password"
            name="name" placeholder="새 비밀번호" required>
        </div>

        <div class="col-md-4 offset-md-4">
          <input type="password" class="form-control form-control-lg p-3" id="password2"
            name="loginId" placeholder="새 비밀번호 확인" required>
        </div>

      </div>

      <div class="row">
        <hr class="my-4 col-md-8 offset-md-2 border border-1 border-dark" style="opacity: 0.1;">
      </div>

      <div class="row g-3">
        <div class="col-md-4 offset-md-4">
          <button @click="submit" class="btn btn-outline-primary btn-lg col-md-12 p-3" style="opacity: 0.7;"
            type="submit" id="signup">확인</button>
        </div>
      </div>
      
    </div>
  </div>
</template>

<script>
import axios from 'axios';

export default {
  name: 'passwordReset',

  data() {
    return {
      isShow: false,
      result: {},
      passwordResetForm: {
        changePassword: '',
        loginId: '',
      },
    }
  },

  methods: {
    submit() {
      axios.post('/api/reset/password', this.passwordResetForm)
        .then((res) => {
          this.result = { ...res.data };
          console.log(JSON.stringify(res.data));
          alert('비밀번호 재설정 완료');
          this.$router.push('/');
          this.isShow = true;
        }).catch((res) => {
          console.log("catch " + res.data);
        })
    },
    modalControl(boolean) {
      this.isShow = boolean;
    },
  },

  beforeMount() {
    const urlParams = new URLSearchParams(window.location.search);
    this.passwordResetForm.loginId = urlParams.get('loginId');
  }
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