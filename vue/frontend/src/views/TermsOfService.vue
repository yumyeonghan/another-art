<template>
    <div id="wrapper" class="container-fluid">
        <div class="container text-center px-4s my-5">
            <div class="row gx-4 justify-content-center m-auto">
                <div class="col-md-10 position-static d-block p-3 bg-light" style="--bs-bg-opacity: .4;">
                    <div class="p-4">
                        <h4 class="fs-3 mb-3 text-black" style="--bs-text-opacity: 0.6;">이용약관</h4>
                    </div>
                    <div class="row g-3">
                        <div class="p-3 col-md-8 offset-md-2" style="border: 1px solid #49433c;">
                            <ul>
                                <li>제1장 총칙
                                    제1조 (목적)
                                    본 약관은 서비스 이용자가 번개장터 주식회사(이하 “회사”라 합니다)가 제공하는 온라인 서비스 (이하 “서비스”라 합니다)에 회원으로 가입하고 이를
                                    이용함에
                                    있어 회사와 그
                                    이용자의
                                    권리⋅의무
                                    및 책임
                                    사항을 규정함을 목적으로 합니다.</li>
                                <li>제2조 (정의)
                                    본 약관에서 사용하는 용어의 정의는 다음과 같습니다. 정의되지 않은 본 약관상 용어의 의미는 일반적인 거래 관행에 의합니다.
                                    회원: 본 약관에 동의하고 회사와 서비스 이용계약을 체결한 자를 말합니다.
                                    판매자: 물품을 판매하기 위해 회사의 판매자 등록 절차를 완료한 회원(회사가 판매자로서 직접 판매하거나, 위탁판매서비스를 통해 특정 물품의 판매를 위탁
                                    받아
                                    판매하는
                                    경우에는 회사)을 말합니다. 물품의 판매를 업(業)으로 하는 판매자(이하 “통신판매업자인 판매자”, 개인인지 법인인지 여부를 불문)인 경우와 그렇지
                                    아니한
                                    판매자 (이하 이
                                    둘을 통칭하여 “판매자”)인 경우 판매자 등록 절차 및 번개장터 서비스 이용 시 적용되는 권리와 의무에 차이가 있을 수 있습니다.
                                    위탁자: 위탁판매서비스를 통하여 회사에 위탁판매를 의뢰하는 자를 말합니다.
                                    구매자: 판매자가 판매하는 물품을 구매하고자 하는 회원을 말합니다.
                                    번개페이: 구매자가 물품 구매를 위해 회사에 물품 대금을 결제할 수 있는 결제 시스템을 말합니다.
                                    번개톡: 서비스 내에서 회원들 간에 메시지 송수신할 수 있는 기능을 말합니다.
                                    번개페이거래: 회원이 다른 회원이 판매하는 물품을 구매하기 위하여 회사에 구매 청약의 접수를 하고 판매자와 구매 계약을 체결한 후 번개페이를 통해 물품
                                    대금을
                                    지급하는 거래
                                    형태를 말합니다.
                                    번개톡거래: 회원이 회사를 통하지 않고 회원간 번개톡을 통해 메시지를 주고 받아 물품을 거래하는 형태를 말합니다.
                                    번개포인트: 회원의 누적 물품 구매 액수 또는 회사가 진행하는 이벤트 당첨 결과 등에 따라 회사가 회원에게 부여하고 회원이 적립하여 배송비 결제 등
                                    회사가
                                    지정하는 용도에
                                    사용할 수 있는 혜택을 말합니다.</li>
                            </ul>
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-8 offset-md-2 p-0">
                            <div class="fs-6 fw-bold text-start ps-3">
                                <input type="checkbox" v-model="ifChecked1" class="form-check-input" value="" id="checkbox1">
                                <label class="ms-2 text-black" for="checkbox1"
                                    style="--bs-text-opacity: 0.6;">약관동의</label>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-2">
                        </div>
                        <div class="p-3 col-8" style="border: 1px solid #49433c;">
                            <ul>
                                <li>제1장 총칙
                                    제1조 (목적)
                                    본 약관은 서비스 이용자가 번개장터 주식회사(이하 “회사”라 합니다)가 제공하는 온라인 서비스 (이하 “서비스”라 합니다)에 회원으로 가입하고 이를
                                    이용함에
                                    있어 회사와 그
                                    이용자의
                                    권리⋅의무
                                    및 책임
                                    사항을 규정함을 목적으로 합니다.</li>
                                <li>제2조 (정의)
                                    본 약관에서 사용하는 용어의 정의는 다음과 같습니다. 정의되지 않은 본 약관상 용어의 의미는 일반적인 거래 관행에 의합니다.
                                    회원: 본 약관에 동의하고 회사와 서비스 이용계약을 체결한 자를 말합니다.
                                    판매자: 물품을 판매하기 위해 회사의 판매자 등록 절차를 완료한 회원(회사가 판매자로서 직접 판매하거나, 위탁판매서비스를 통해 특정 물품의 판매를 위탁
                                    받아
                                    판매하는
                                    경우에는 회사)을 말합니다. 물품의 판매를 업(業)으로 하는 판매자(이하 “통신판매업자인 판매자”, 개인인지 법인인지 여부를 불문)인 경우와 그렇지
                                    아니한
                                    판매자 (이하 이
                                    둘을 통칭하여 “판매자”)인 경우 판매자 등록 절차 및 번개장터 서비스 이용 시 적용되는 권리와 의무에 차이가 있을 수 있습니다.
                                    위탁자: 위탁판매서비스를 통하여 회사에 위탁판매를 의뢰하는 자를 말합니다.
                                    구매자: 판매자가 판매하는 물품을 구매하고자 하는 회원을 말합니다.
                                    번개페이: 구매자가 물품 구매를 위해 회사에 물품 대금을 결제할 수 있는 결제 시스템을 말합니다.
                                    번개톡: 서비스 내에서 회원들 간에 메시지 송수신할 수 있는 기능을 말합니다.
                                    번개페이거래: 회원이 다른 회원이 판매하는 물품을 구매하기 위하여 회사에 구매 청약의 접수를 하고 판매자와 구매 계약을 체결한 후 번개페이를 통해 물품
                                    대금을
                                    지급하는 거래
                                    형태를 말합니다.
                                    번개톡거래: 회원이 회사를 통하지 않고 회원간 번개톡을 통해 메시지를 주고 받아 물품을 거래하는 형태를 말합니다.
                                    번개포인트: 회원의 누적 물품 구매 액수 또는 회사가 진행하는 이벤트 당첨 결과 등에 따라 회사가 회원에게 부여하고 회원이 적립하여 배송비 결제 등
                                    회사가
                                    지정하는 용도에
                                    사용할 수 있는 혜택을 말합니다.</li>
                            </ul>
                        </div>
                        <div class="col-2">
                        </div>
                    </div>
                    <div class="row mt-3">
                        <div class="col-md-8 offset-md-2 p-0">
                            <div class="fs-6 fw-bold text-start ps-3">
                                <input type="checkbox" v-model="ifChecked2" class="form-check-input" id="checkbox2">
                                <label class="form-check-label ms-2 text-black" style="--bs-text-opacity: 0.6;"
                                    for="flexCheckDefault">약관동의</label>
                                <input type="checkbox" v-model="ifChecked3" @change="checkAll" class="form-check-input ms-2" id="checkbox3">
                                <label class="form-check-label ms-2 text-black" style="--bs-text-opacity: 0.6;"
                                    for="checkbox3">전체동의</label>
                            </div>
                        </div>
                    </div>
                    <div class="row mt-5">
                        <div class="col-md-8 offset-md-2">
                            <div class="fs-6 fw-bold text-center ps-3">
                                <button type="button" @click="verifyCheckbox($router)"
                                    class="btn btn-outline-dark btn-lg" style="width: 400px;">다음</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>



</template>

<script>
export default {
    name: 'termsOfServie',
    data(){
        return{
            ifChecked1: false,
            ifChecked2: false,
            ifChecked3: false,
        }
    },
    methods: {
        verifyCheckbox($router) {
            if (this.ifChecked1 && this.ifChecked2) {
                $router.push('/createAccount/userRegister');
            } else {
                alert('약관에 동의해야 합니다.');
            }
        },
        checkAll() {
            if (this.ifChecked3) {
                this.ifChecked1 = true;
                this.ifChecked2 = true;
            } else {
                this.ifChecked1 = false;
                this.ifChecked2 = false;
            }
        },
    }
}
</script>

<style>
.border-gray {
    border-color: rgb(229, 229, 229);
}
</style>