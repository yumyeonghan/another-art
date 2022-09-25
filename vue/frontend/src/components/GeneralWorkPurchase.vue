<template>
    <div class="container">
        <div class="row">
            <div class="col-md-5">
                <div class="row">
                    <div class="col-md-12 offset-md-1 mt-5">
                        <img :src="require(`../../../../src/main/resources/images/${artwork.imageStorageName}`)"
                            class="w-100 border border-1">
                        <!-- src="https://via.placeholder.com/470x305" -->
                    </div>
                </div>
                <div class="row">
                    <div class="col-md-12 offset-md-1 mt-2">
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                        <a href="#"><img src="https://via.placeholder.com/90x60" class="mx-1"
                                style="width: 23.3%; height: 100px;"></a>
                    </div>
                </div>

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
                                    <a > {{ art.generalArt.artRegisterDate }}</a>
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
                                <a href="#" class="btn btn-lg btn-outline-danger"
                                    style="border-radius: 6px; width: 120px;">
                                    <font-awesome-icon icon="fa-regular fa-heart" /> 찜
                                </a>
                            </div>
                            <div class="col-md-3">
                                <a href="#" class="btn btn-lg btn-outline-primary"
                                    style="border-radius: 6px; width: 120px;">바로구매</a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'generalWorkPurchase',
    data() {
        return {
            artwork: {
                artId: 2,
                name: '',
                price: '',
                description: '',
                saleType: '',
                likes: 2,
                views: 46,
                registerDate: "2022-07-13",
                imageSource: `../assets/6e0dc3f002.png`,
                // `../../../../src/main/resources/images/6e0dc3f002.png`
                imageStorageName: '6e0dc3f002.png',
            },
            art: {
                artLikeCount: 9,
                generalArt: {
                    artId: 3,
                    artName: "art3",
                    artDescription: "이 작품은 작품입니다 - 3",
                    artInitPrice: 10000,
                    artRegisterDate: "2022-09-09 20:28:23",
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
        }
    },
    beforeMount() {
        axios.get(`/api/art/9`).then((res) => {
            // console.log(res.data.art);
            this.art = { ...res.data.art };
            // console.log("복사본", this.art);
        }).catch((res) => {
            console.log(res);
        })
    },
}
</script>

<style>
</style>