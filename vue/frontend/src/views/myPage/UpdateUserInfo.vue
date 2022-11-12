<template>
    <div id="wrapper" class="container-fluid">
        <div class="container px-4s my-5">
            <div class="row gx-4 justify-content-center m-auto">
                <div class="col-md-10 position-static d-block p-3 text-black"
                    style="--bs-bg-opacity: .4; --bs-text-opacity: 0.6;">
                    <div class="p-4">
                        <!-- style="--bs-text-opacity: 0.6;" -->
                        <h4 class="mb-3 text-center text-black fs-2">회원정보 수정</h4>
                    </div>
                    <!-- @submit.prevent="submitForm" -->
                    <form class="needs-validation" novalidate>
                        <div class="row g-3">
                            <div class="col-md-6 offset-md-3">
                                <input type="text" v-model="formData.name" class="form-control form-control-lg p-3"
                                    id="name" name="name" placeholder="이름" required>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <input type="text" v-model="formData.nickname" class="form-control form-control-lg p-3"
                                    id="nickname" name="nickname" placeholder="닉네임" required>
                            </div>
                        </div>

                        <div class="row">
                            <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
                        </div>

                        <div class="row g-3">
                            <div class="col-md-6 offset-md-3">
                                <div class="row g-3">
                                    <div class="col-md-8">
                                        <input type="text" v-model="$store.state.univName"
                                            class="form-control form-control-lg p-3" id="schoolName" name="schoolName"
                                            placeholder="학교명" required>
                                    </div>
                                    <div class="col-md-4">
                                        <button type="button" class="form-control btn btn-outline-dark p-3 mt-1"
                                            style="opacity: 0.7;" data-bs-toggle="modal"
                                            data-bs-target="#schoolModal">학교 찾기</button>
                                    </div>
                                </div>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <div class="row">
                                    <div class="col-md-8">
                                        <input type="text" v-model="formData.address" class="form-control form-control-lg p-3"
                                    id="address" name="address" placeholder="주소" required>
                                    </div>
                                    <div class="col-md-4">
                                        <input type="button" @click="execDaumPostcode" value="주소 검색"
                                            class="form-control btn btn-outline-dark p-3 mt-1" style="opacity: 0.7;" />
                                    </div>
                                </div>

                                <!-- <div class="input-group has-validation">
                                        <input type="text" class="form-control" id="username" placeholder="이름" required>
                                    </div> -->
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <input type="tel" v-model="formData.phoneNumber"
                                    class="form-control form-control-lg p-3" id="phoneNumber" name="phoneNumber"
                                    placeholder="전화번호('-'을 빼고 입력)" required>
                            </div>
                        </div>

                        <div class="row">
                            <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
                        </div>

                        <div class="row g-3">
                            <button type="button" @click="submitForm"
                                class="btn btn-outline-dark btn-lg col-md-6 offset-md-3 p-3" id="signup"
                                style="opacity: 0.7;">완료</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'updateUserInfo',
    data() {
        return {
            userId: '',
            formData: {
                name: '',
                nickname: '',
                schoolName: '',
                phoneNumber: '',
                address: '',
            },
        }
    },
    methods: {
        submitForm() {
            const data = this.formData;
            data.schoolName = this.$store.state.univName;
            let userId = JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId;

            // qs.stringify(data)
            axios.patch(`/api/user/${userId}`, data)
                .then((res) => {
                    // if (res == 'success') {
                    //     $router.push('/');
                    // }
                    console.log(JSON.stringify(res));
                    alert('회원정보 수정 완료');
                    this.$router.push('/vue');
                }).catch((res)=> {
                    console.log('fail:' + JSON.stringify(res));
                });
        },
        execDaumPostcode() {
            new window.daum.Postcode({
                oncomplete: (data) => {
                    if (this.formData.extraAddress !== "") {
                        this.formData.extraAddress = "";
                    }
                    if (data.userSelectedType === "R") {
                        // 사용자가 도로명 주소를 선택했을 경우
                        this.formData.address = data.roadAddress;
                    } else {
                        // 사용자가 지번 주소를 선택했을 경우(J)
                        this.formData.address = data.jibunAddress;
                    }

                    // 사용자가 선택한 주소가 도로명 타입일때 참고항목을 조합한다.
                    if (data.userSelectedType === "R") {
                        // 법정동명이 있을 경우 추가한다. (법정리는 제외)
                        // 법정동의 경우 마지막 문자가 "동/로/가"로 끝난다.
                        if (data.bname !== "" && /[동|로|가]$/g.test(data.bname)) {
                            this.formData.extraAddress += data.bname;
                        }
                        // 건물명이 있고, 공동주택일 경우 추가한다.
                        if (data.buildingName !== "" && data.apartment === "Y") {
                            this.formData.extraAddress +=
                                this.formData.extraAddress !== ""
                                    ? `, ${data.buildingName}`
                                    : data.buildingName;
                        }
                        // 표시할 참고항목이 있을 경우, 괄호까지 추가한 최종 문자열을 만든다.
                        if (this.formData.extraAddress !== "") {
                            this.formData.extraAddress = `(${this.formData.extraAddress})`;
                        }
                    } else {
                        this.formData.extraAddress = "";
                    }
                    // 우편번호를 입력한다.
                    this.formData.postcode = data.zonecode;
                },
            }).open();
        },
    },
}
</script>

<style>

</style>