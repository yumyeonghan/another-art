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
                <input type="text" v-model="loginData.loginId" class="form-control form-control-lg p-3" id="loginId"
                    name="loginId" placeholder="아이디" required>
            </div>

            <div class="col-md-6 offset-md-3">
                <input type="text" v-model="loginData.loginPassword" class="form-control form-control-lg p-3"
                    id="loginPassword" name="loginPassword" placeholder="비밀번호" required>
            </div>

            <div class="col-md-6 offset-md-3">
                <button @click="login" class="btn btn-outline-primary btn-lg col-md-12 p-3">로그인</button>
            </div>

            <div class="text-center">
                <a href="">아이디/비밀번호 찾기 </a>
                <a href="#"> | </a>
                <a href="/createAccount/userRegister">회원가입</a>
            </div>

        </div>
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
            axios.post('/api/login', this.loginData).then((res) => {
                this.loginedData = JSON.stringify(res.data);
                sessionStorage.setItem("loginData", JSON.stringify(this.loginedData));
                console.log("sessionStorage loginData: " + JSON.parse(sessionStorage.getItem("loginData")));
                this.$store.commit("setIsLogined", true);
                this.$router.push('/vue');
            }).catch((res) => {
                console.log("catch " + res.data);
            })
        },
        // test1() {
        //     sessionStorage.setItem("loginData", JSON.stringify(this.loginedData));
        // },
        // test2() {
        //     console.log(JSON.parse(sessionStorage.getItem("loginData")));
        // },
        // test3() {
        //     sessionStorage.clear();
        // },
    },
    beforeMount() {
        // 로그인 중인 유저 정보가 존재할 떄
        if (this.$store.state.loginData != null) {
            this.$store.state.isLogined = true;
        // 로그인 중인 유저 정보가 존재하지 않을 때
        } else {
            this.$store.state.isLogined = false;
        }
    },
}
</script>

<style>
</style>