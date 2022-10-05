<template>
    <div id="wrapper" class="container-fluid">
        <div class="container px-4s my-5">
            <div class="row gx-4 justify-content-center m-auto">
                <div class="col-md-10 position-static d-block p-3 text-black"
                    style="--bs-bg-opacity: .4; --bs-text-opacity: 0.6;">
                    <div class="p-4">
                        <h4 class="mb-3 text-center text-black fs-2 fw-bold">포인트 충전</h4>
                    </div>

                    <form class="needs-validation" novalidate>
                        <div class="row g-3">
                            <div class="col-md-6 offset-md-3">
                                <div class="p-3"
                                    style="background-color: #f9fafb; border-color: #f9fafb; border-radius: 9px;">
                                    <div class="row g-3">
                                        <div class="col-md-4 offset-md-2">
                                            <h6 class="fontColor-gray my-1">잔여 포인트</h6>
                                        </div>
                                        <div class="col-md-4 offset-md-1">
                                            <h6 class="fs-5 fw-semibold my-1" style="color: #3181F6;">{{ totalPoint }}
                                            </h6>
                                        </div>
                                        <div class="col-md-4 offset-md-2">
                                            <h6 class="fontColor-gray my-1">충전 후 포인트</h6>
                                        </div>
                                        <div class="col-md-4 offset-md-1">
                                            <h6 class="fs-5 fw-semibold my-1" style="color: #3181F6;">{{ sumPoint }}
                                            </h6>
                                        </div>
                                    </div>

                                </div>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <input type="number" v-model="IMPRequestData.amount"
                                    class="form-control form-control-lg p-3" id="userPoint" name="userPoint"
                                    placeholder="금액을 입력해주세요." required>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <div class="btn-group" role="group" aria-label="Basic example"
                                    style="height: 100px; width: 100%;">
                                    <button type="button" @click="pointIncrease(5000)" class="btn btn-outline-dark"
                                        style="opacity: 0.6;">+5000</button>
                                    <button type="button" @click="pointIncrease(10000)" class="btn btn-outline-dark"
                                        style="opacity: 0.6;">+10000</button>
                                    <button type="button" @click="pointIncrease(30000)" class="btn btn-outline-dark"
                                        style="opacity: 0.6;">+30000</button>
                                    <button type="button" @click="pointIncrease(50000)" class="btn btn-outline-dark"
                                        style="opacity: 0.6;">+50000</button>
                                </div>
                            </div>
                        </div>

                        <div class="row">
                            <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
                        </div>

                        <div class="row g-3 text-center">
                            <button type="button" @click="requestPay"
                                class="btn btn-lg btn-outline-dark col-md-6 offset-md-3 p-2 fs-5"
                                style="background-color: #3181F6; color: white; border-radius: 9px; border-color: #3181F6;">충전</button>
                        </div>
                        <!-- <div class="text-center">
                            <button type="button" @click="cancelPay" class="btn btn-outline-dark">cancelPay</button>
                        </div> -->

                    </form>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';
const { IMP } = window;

export default {
    name: 'userPointCharge',
    data() {
        return {
            totalPoint: 5000,
            userPoint: 0,
            IMPRequestData: { // param
                pg: "kakaopay.TC0ONETIME",
                pay_method: "card",
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: '포인트 충전',
                amount: 0,
                buyer_email: this.$store.state.sessionData.email,
                buyer_name: this.$store.state.sessionData.name,
                buyer_tel: this.$store.state.sessionData.phoneNumber,
                buyer_addr: this.$store.state.sessionData.address,
                buyer_postcode: this.$store.state.sessionData.address
            },
        }
    },
    created() {
        document.cookie = "safeCookie1=foo; SameSite=Lax";
        document.cookie = "safeCookie2=foo";
        document.cookie = "crossCookie=bar; SameSite=None; Secure";
    },
    computed: {
        sumPoint() {
            return this.totalPoint + this.IMPRequestData.amount;
        },
    },
    methods: {
        clear() {
            this.userPoint = 0;
        },
        pointIncrease(num) {
            this.IMPRequestData.amount += num;
            return this.IMPRequestData.amount;
        },
        requestPay() {
            window.IMP.init("imp43261534");

            IMP.request_pay(this.IMPRequestData, (rsp) => { // callback
                console.log(rsp);
                if (rsp.success) {
                    console.log("결제 성공");
                    axios.post('/api/point/charge', {
                        dealAmount: this.IMPRequestData.amount,
                        loginId: this.$store.state.sessionData.loginId,
                    }).then(() => {
                        console.log('포인트 충전 완료');
                        this.$router.push('/myPage');
                    }).catch(() => {
                        console.log('point charge fail');
                    })

                } else {
                    console.log("결제 실패");
                }
            });
        },
        productName() {
            return this.IMPRequestData.amount + '포인트';
        }
    },
    beforeMount() {
        axios.get('/api/session-check').
            then((res) => {
                console.log("session-check success: " + JSON.stringify(res.data));
                this.$store.commit('setSessionData', res.data);
                console.log('sessionData: ' + JSON.stringify(this.$store.state.sessionData));
            }).catch((res) => {
                console.log('session-check fail:' + res);
            });
    },

}
</script>

<style>
.fontColor-gray {
    color: #4e5968;
}

.borderColor-gray {
    border-color: #4e5968;
}
</style>