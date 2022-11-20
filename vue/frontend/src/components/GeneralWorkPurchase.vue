<template>
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <div class="row">
                    <div class="col-md-12 offset-md-1 mt-5">
                        <img :src="require(`../../../../src/main/resources/images/${art.generalArt.artStorageName}`)"
                            class="w-100 border border-1">
                        <!-- src="https://via.placeholder.com/470x305" -->
                    </div>
                </div>
                <!-- <div class="row">
                    <div class="col-md-12 offset-md-1 mt-2">
                        <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="../assets/6e0dc3f002.png" class="mx-1 border border-1"
                                style="width: 23.3%; height: 100px;"></a>
                    </div>
                </div> -->

            </div>

            <div class="col-lg-5 offset-lg-1 mt-5">
                <div class="s_product_text">

                    <div class="row g-3 mt-2">
                        <div class="mb-2">
                            <h3>{{ art.generalArt.artName }}</h3>
                        </div>

                        <div class="mb-2">
                            <h1> {{ art.generalArt.artInitPrice }}원 </h1>
                        </div>

                        <hr>

                        <div class="mb-2">
                            <ul class="list-group list-group-horizontal">
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-heart" class="mx-1" />
                                    <a> {{ art.artLikeCount }}</a>
                                </li>
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-eye" class="mx-1" />
                                    <a> 46 </a>
                                </li>
                                <li class="list-group-item" style="border: none;">
                                    <font-awesome-icon icon="fa-solid fa-clock" class="mx-1" />
                                    <a> {{ art.generalArt.artRegisterDate }}</a>
                                </li>
                            </ul>
                        </div>


                        <div class="mb-2">
                            <p>
                                <!-- Mill Oil is an innovative oil filled radiator with the most modern technology. If you
                                are
                                looking for something that can make your interior look awesome, and at the same time. -->
                                {{ art.generalArt.artDescription }}
                            </p>
                        </div>
                    </div>
                    <div class="card_area">
                        <div class="row g-3">
                            <div class="col-md-3">
                                <a href="#" @click="likeArtControl" :class="likeButtonStyle"
                                    style="border-radius: 6px; width: 120px;">
                                    <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a @click="purchaseArt" href="#" class="btn btn-lg btn-outline-primary"
                                    style="border-radius: 6px; width: 120px;">구매하기</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <br><br><br>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'generalWorkPurchase',
    data() {
        return {
            art: {
                artLikeCount: 9,
                generalArt: {
                    artId: 3,
                    artName: "art3",
                    artDescription: "이 작품은 작품입니다 - 3",
                    artInitPrice: 10000,
                    artRegisterDate: "2022-09-27 19:32:30",
                    artStorageName: "6e0dc3f002.png",
                    artOwnerId: 1,
                    artOwnerNickname: "user1-Nickname",
                    artOwnerSchoolName: "경기대학교"
                },
                artHashtagList: [
                    "일상",
                    "바다",
                    "육아",
                    "인스타",
                    "타조"
                ]
            },
            purchaseData: {
                artId: this.$store.state.selectedArt.generalArt.artId,
                // userId: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId,
                userId: parseInt(this.$store.state.loginData.userId),
            },
            likeData: {
                artId: this.$store.state.selectedArt.generalArt.artId,
                // userId: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId,
                userId: parseInt(this.$store.state.loginData.userId),
            },
            isLiked: false,
            likeButtonStyle: 'btn btn-lg btn-outline-danger',


        }
    },
    methods: {
        purchaseArt() {
            axios.post('/api/purchase/general', this.purchaseData).then((res) => {
                console.log("req: " + JSON.stringify(this.purchaseData));
                console.log("res: " + JSON.stringify(res.data));
                alert('구매완료');
                this.$router.push('/');
            }).catch((err) => {
                let errMsg = JSON.stringify(err.response.data.message);
                errMsg = errMsg.substring(1, errMsg.length - 1);
                console.log("errMsg -> " + errMsg);
                if (this.purchaseData.userId == 0) {
                    alert('로그인하지 않은 사용자입니다');
                } else {
                    alert(errMsg);
                }
            })
        },
        likeArtControl() {
            // 작품 좋아요
            if (!this.isLiked) {
                axios.post('/api/art/like', this.likeData).then((res) => {
                    console.log("req: " + JSON.stringify(this.likeData));
                    console.log("res: " + JSON.stringify(res.data));
                    this.isLiked = true;
                    this.likeButtonStyle = 'btn btn-lg btn-danger';
                }).catch((err) => {
                    let errMsg = JSON.stringify(err.response.data.message);
                    errMsg = errMsg.substring(1, errMsg.length - 1);
                    console.log("errMsg -> " + errMsg);
                    if (this.likeData.userId == 0) {
                        alert('로그인하지 않은 사용자입니다');
                    } else {
                        alert(errMsg);
                    }
                })
                // 작품 좋아요 취소
            } else if (this.isLiked) {
                axios.post('/api/art/cancel', this.likeData).then((res) => {
                    console.log("req: " + JSON.stringify(this.likeData));
                    console.log("res: " + JSON.stringify(res.data));
                    this.isLiked = false;
                    this.likeButtonStyle = 'btn btn-lg btn-outline-danger';
                }).catch((err) => {
                    let errMsg = JSON.stringify(err.response.data.message);
                    errMsg = errMsg.substring(1, errMsg.length - 1);
                    console.log("errMsg -> " + errMsg);
                    if (this.likeData.userId == 0) {
                        alert('로그인하지 않은 사용자입니다');
                    } else {
                        alert(errMsg);
                    }
                });
            }
        },
    },
    beforeMount() {
        this.art = { ...this.$store.state.selectedArt };
        // axios.get('/api/session-check').then((res) => {
        //         console.log("session-check: " + JSON.stringify(res.data));

        //     }).catch((res) =>{
        //         console.log("catch: " + JSON.stringify(res.data));
        //     });
    },

}
</script>

<style>

</style>