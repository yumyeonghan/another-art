<template>
    <div id="wrapper" class="container-fluid">
        <div class="container px-4s my-5">
            <div class="row gx-4 justify-content-center m-auto">
                <div class="col-md-10 position-static d-block p-3 text-black"
                    style="--bs-bg-opacity: .4; --bs-text-opacity: 0.6;">
                    <div class="p-4">
                        <h4 class="mb-3 text-center text-black fs-2 fw-bold">포인트 환불</h4>
                    </div>

                    <form class="needs-validation" novalidate>
                        <div class="row g-3">
                            <div class="col-md-6 offset-md-3">
                                충전내역
                            </div>
                            <div class="col-md-6 offset-md-3">
                                <div class="p-3"
                                    style="background-color: #f9fafb; border-color: #f9fafb; border-radius: 9px;">
                                    <div v-for="charge in chargeHistory" :key="charge" class="row g-3">
                                        <div class="col-md-4 offset-md-2 mb-3">
                                            <h6 class="fontColor-gray my-1">{{charge.chargeTime}}</h6>
                                        </div>
                                        <div class="col-md-4 offset-md-1">
                                            <h6 class="fs-5 fw-semibold my-1" style="color: #3181F6;"> {{charge.sign}}{{charge.chargeAmount}}
                                            </h6>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                            <!-- <div class="row g-3">
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
                                    <input type="number" v-model="userPoint" class="form-control form-control-lg p-3"
                                        id="userPoint" name="userPoint" placeholder="금액을 입력해주세요." required>
                            </div>

                            <div class="col-md-6 offset-md-3">
                                <div class="btn-group" role="group" aria-label="Basic example"
                                    style="height: 100px; width: 100%;">
                                    <button type="button" @click="pointIncrease(5000)"
                                        class="btn btn-outline-dark" style="opacity: 0.6;">+5000</button>
                                    <button type="button" @click="pointIncrease(10000)"
                                        class="btn btn-outline-dark" style="opacity: 0.6;">+10000</button>
                                    <button type="button" @click="pointIncrease(30000)"
                                        class="btn btn-outline-dark" style="opacity: 0.6;">+30000</button>
                                    <button type="button" @click="pointIncrease(50000)"
                                        class="btn btn-outline-dark" style="opacity: 0.6;">+50000</button>
                                </div>
                            </div>
                        </div> -->

                            <div class="row">
                                <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark"
                                    style="opacity: 0.1;">
                            </div>

                            <div class="row g-3 text-center">
                                <button type="button" @click="cancelPay"
                                    class="btn btn-lg btn-danger col-md-6 offset-md-3 p-2 fs-5"
                                    style="border-radius: 9px;">환불</button>
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

export default {
    name: 'userPointRefund',
    data() {
        return {
            chargeHistory: [
                {
                    chargeTime: new Date("2022-09-07"),
                    sign: '+',
                    chargeAmount: 5000
                },
                {
                    chargeTime: new Date("2022-09-08"),
                    sign: '+',
                    chargeAmount: 10000
                },
                {
                    chargeTime: new Date("2022-09-09"),
                    sign: '-',
                    chargeAmount: 5000
                }
            ],
        }
    },
    methods: {
        cancelPay() {
            axios({
                url: "{환불요청을 받을 서비스 URL}", // 예: http://www.myservice.com/payments/cancel
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                data: {
                    merchant_uid: "{결제건의 주문번호}", // 주문번호
                    cancel_request_amount: 2000, // 환불금액
                    reason: "테스트 결제 환불", // 환불사유
                    refund_holder: "홍길동", // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
                    refund_bank: "88", // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(예: KG이니시스의 경우 신한은행은 88번)
                    refund_account: "56211105948400" // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
                }
            });
        },
        getToken() {
            axios({
                url: "https://api.iamport.kr/users/getToken",
                method: "post", // POST method
                headers: { "Content-Type": "application/json" }, // "Content-Type": "application/json"
                data: {
                    imp_key: 4361355248446112, // REST API키
                    imp_secret: "qb6HSgPbnwDwoQuWDmGiGd1vH0nQHnikYm9pUeupttTZPQOyGGJp7UArBwo3q5DFMWO142wessCUWOas" // REST API Secret
                }
            });
        }
    }
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