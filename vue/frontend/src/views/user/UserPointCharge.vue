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
                                <input type="number" v-model="userPoint" class="form-control form-control-lg p-3"
                                    id="userPoint" name="userPoint" placeholder="금액을 입력해주세요." required>
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

const { IMP } = window;

export default {
    name: 'userPointCharge',
    data() {
        return {
            totalPoint: 5000,
            userPoint: 0,
        }
    },
    created() {
        document.cookie = "safeCookie1=foo; SameSite=Lax";
        document.cookie = "safeCookie2=foo";
        document.cookie = "crossCookie=bar; SameSite=None; Secure";
    },
    computed: {
        sumPoint() {
            return this.totalPoint + this.userPoint;
        }
    },
    methods: {
        clear() {
            this.userPoint = 0;
        },
        pointIncrease(num) {
            this.userPoint += num;
            return this.userPoint;
        },
        // purchase() {

        // },
        // requestPay() {
        //     // IMP.request_pay(param, callback) 결제창 호출
        //     const IMP = window.IMP; 
        //     // 생략 가능
        //     IMP.init("{imp43261534}");
        //     // Example: imp00000000

        //     IMP.request_pay({ // param
        //         pg: "html5_inicis",
        //         pay_method: "card",
        //         merchant_uid: "ORD20180131-0000011",
        //         name: "노르웨이 회전 의자",
        //         amount: this.userPoint,
        //         buyer_email: "gildong@gmail.com",
        //         buyer_name: "홍길동",
        //         buyer_tel: "010-4242-4242",
        //         buyer_addr: "서울특별시 강남구 신사동",
        //         buyer_postcode: "01181"
        //     }, rsp => { // callback
        //         if (rsp.success) {

        //             // 결제 성공 시 로직,

        //         } else {

        //             // 결제 실패 시 로직,

        //         }
        //     });
        // },
        requestPay() {
            window.IMP.init("imp43261534");

            IMP.request_pay({ // param
                pg: "kakaopay.TC0ONETIME",
                pay_method: "card",
                merchant_uid: 'merchant_' + new Date().getTime(),
                name: "테스터",
                amount: this.userPoint,
                buyer_email: "funidea_woo@naver.com",
                buyer_name: "테스터",
                buyer_tel: "010-8832-4280",
                buyer_addr: "서울특별시 영등포구 당산동",
                buyer_postcode: "07222"
            }, (rsp) => { // callback
                console.log(rsp);
                if (rsp.success) {
                    console.log("결제 성공");
                } else {
                    console.log("결제 실패");
                }
            });
        },
        // cancelPay() {
        //     jQuery.ajax({
        //         "url": "{환불요청을 받을 서비스 URL}", // 예: http://www.myservice.com/payments/cancel
        //         "type": "POST",
        //         "contentType": "application/json",
        //         "data": JSON.stringify({
        //             "merchant_uid": "{결제건의 주문번호}", // 예: ORD20180131-0000011
        //             "cancel_request_amount": 2000, // 환불금액
        //             "reason": "테스트 결제 환불", // 환불사유
        //             "refund_holder": "홍길동", // [가상계좌 환불시 필수입력] 환불 수령계좌 예금주
        //             "refund_bank": "88", // [가상계좌 환불시 필수입력] 환불 수령계좌 은행코드(예: KG이니시스의 경우 신한은행은 88번)
        //             "refund_account": "56211105948400" // [가상계좌 환불시 필수입력] 환불 수령계좌 번호
        //         }),
        //         "dataType": "json"
        //     });
        // }
        // getToken() {
        //     axios({
        //         url: "https://api.iamport.kr/users/getToken",
        //         method: "post", // POST method
        //         headers: { "Content-Type": "application/json" }, // "Content-Type": "application/json"
        //         data: {
        //             imp_key: "imp_apikey", // REST API키
        //             imp_secret: "ekKoeW8RyKuT0zgaZsUtXXTLQ4AhPFW3ZGseDA6bkA5lamv9OqDMnxyeB9wqOsuO9W3Mx9YSJ4dTqJ3f" // REST API Secret
        //         }
        //     });
        // }
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