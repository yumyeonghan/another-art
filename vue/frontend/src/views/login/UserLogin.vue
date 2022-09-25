<template>
    <div>
        <div class="p-5 pb-4 border-bottom-0">
            <h2 class="mb-0 text-center">로그인</h2>
            <div class="text-center">
                <p>{{ $store.state.userData }}</p>
            </div>
            
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
                <button @click="submitForm" class="btn btn-outline-primary btn-lg col-md-12 p-3">로그인</button>
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
import qs from 'qs';

export default {
    name: 'userLogin',
    data() {
        return {
            loginData: {
                loginId: '',
                loginPassword: '',
            },
        }
    },
    methods: {
        submitForm() {
            axios.post('/api/login', qs.stringify(this.loginData)).then((res) => {
                console.log("then " + JSON.stringify(res.data));
                this.$store.commit({
                    type: 'saveUserData',
                    userData: JSON.stringify(res.data),
                });
            }).catch((res) => {
                console.log("catch " + JSON.stringify(res.data));
            })
        },
        test() {
            console.log("button click");
        },
    }
}
</script>

<style>
</style>