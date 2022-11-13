<template>
    <div class="needs-validation" novalidate>

        <!-- hashtagModal -->
        <div class="modal fade" id="hashtagModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
            aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">해쉬태그 추가</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input type="search" v-model="hashtagKeyword" class="form-control rounded"
                                placeholder="해쉬태그" aria-label="Search" aria-describedby="search-addon" autofucus />
                            <span class="input-group-text border-0" id="search-addon">
                                <button @click="addHashtag(hashtagKeyword)" class="btn">추가</button>
                            </span>
                        </div>
                        <div class="text-center mt-4">
                            <button type="button" class="btn btn-outline-primary"
                                data-bs-dismiss="modal" aria-label="Close">사용하기</button>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <div class="row gx-4 m-auto p-2">
            <div class="col-md-6 position-static d-block p-3 text-black">
                <div class="row g-3">
                    <div :class="col_style" class="mb-4">
                        <label for="artworkType" class="form-label">작품유형</label>
                        <div class="btn-group form-control" style="height: 120px; border: none;" role="group"
                            aria-label="Basic example">
                            <button type="button" @click="$emit('setSaleType', 'auction')"
                                class="btn btn-outline-secondary">
                                <div class="mb-2">
                                    <font-awesome-icon icon="fa-solid fa-gavel" style="height: 40px;" />
                                </div>
                                <h6>경매 작품</h6>
                            </button>
                            <button type="button" @click="$emit('setSaleType', 'general')" id="artworkType"
                                class="btn btn-secondary">
                                <div class="mb-2">
                                    <font-awesome-icon icon="fa-solid fa-won-sign" style="height: 40px;" />
                                </div>
                                <h6>일반 작품</h6>
                            </button>
                        </div>
                    </div>
                </div>
                <div>
                    <div class="row g-3">
                        <div :class="col_style" class="mb-2">
                            <label for="name" class="form-label">작품명</label>
                            <input type="text" v-model="artData.name" class="form-control form-control-lg p-3" id="name"
                                name="name" required>
                        </div>

                        <div :class="col_style" class="mb-2">
                            <div class="row">
                                <div class="col-md-12">
                                    <label for="tag" class="form-label">해시태그</label>
                                </div>
                                <div class="col-md-8">
                                    <input type="text" :value="hashtagList"
                                        class="form-control form-control-lg p-3" id="tag" name="tag" disabled required>
                                </div>
                                <div class="col-md-4">
                                    <button class="form-control btn btn-outline-secondary p-3" data-bs-toggle="modal"
                                        data-bs-target="#hashtagModal">
                                        <h6 class="my-1">해시태그 추가</h6>
                                    </button>
                                </div>
                            </div>
                        </div>

                        <div :class="col_style" class="mb-2">
                            <div class="row">
                                <label for="price" class="form-label">판매 가격</label>
                                <div class="col-md-1 px-3 pt-3 text-center">
                                    <h4 class="fw-lighter">₩</h4>
                                </div>
                                <div class="col-md-11">
                                    <input type="number" v-model="artData.initPrice"
                                        class="form-control form-control-lg p-3" id="price" name="initPrice"
                                        placeholder="" required>
                                </div>
                                <div class="col-md-4">
                                </div>
                            </div>
                        </div>

                        <div :class="col_style" class="mb-2">
                            <label for="description" class="form-label">상세 설명</label>
                            <div class="row">
                                <div class="col-md-12">
                                    <div class="form-floating">
                                        <textarea v-model="artData.description" class="form-control p-3"
                                            style="height: 145px; resize: none;" placeholder="Leave a comment here"
                                            id="description" name="description" required></textarea>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="col-md-6 position-static d-block p-3 text-black">
                <p class="fw-semibold">미리보기</p>
                <div class="row">
                    <div class="col-md-6">
                        <div class="card mt-3 mb-5">
                            <label for="imageFile" class="input-button" style="cursor: pointer;">
                                <button type="button" class="btn btn-outline-dark importar">
                                    <h5 style="position: relative; top: 30%;">이미지 등록</h5>
                                </button>
                            </label>
                            <input type="file" @change="upload" accept="image/*" enctype="multipart/form-data"
                            id="imageFile" name="file" class="inputfile" style="display: none;" />
                            <div class="card-body pt-4">
                                <!-- <h2 class="card-title"></h2> -->
                                <p class="card-text">이미지 파일을 추가하면 오른쪽 공간에 이미지가 표시됩니다.</p>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6 mt-3">
                        <div class="row">
                            <div class="col-md-5">
                                <img src="https://via.placeholder.com/150" class="mb-4" style="height:125px;">
                                <img src="https://via.placeholder.com/150" style="height:125px;">
                            </div>
                            <div class="col-md-5 offset-md-1">
                                <img src="https://via.placeholder.com/150" class="mb-4" style="height:125px;">
                                <img src="https://via.placeholder.com/150" style="height:125px;">
                            </div>
                        </div>
                    </div>
                </div>
            </div>

            <div class="row">
                <hr class="my-4 col-md-12 offset-md-0 border border-1 border-dark" style="opacity: 0.1;">
            </div>

            <div class="row">
                <button @click="registerArt" class="btn btn-outline-dark btn-lg col-md-4 offset-md-4 mt-3 p-3"
                    style="opacity: 0.7;" id="signup">완료</button>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'generalWorkRegister',
    data() {
        return {
            artData: {
                file: '',
                saleType: this.saleType,
                name: '',
                initPrice: '',
                description: '',
                userId: JSON.parse(JSON.parse(sessionStorage.getItem("loginData"))).userId,
            },
            hashtagList: [],
            hashtagIndex: 0,
            hashtagKeyword: '',
        }
    },
    props: {
        col_style: String,
        saleType: String,
    },
    methods: {
        addHashtag(hashtag) {
            this.hashtagList.push(hashtag);
            this.hashtagKeyword = '';
            console.log(this.hashtagList);
        },
        registerArt() {
            let formData = new FormData();
            let file = document.getElementById("imageFile");
            // var startDate = new Date(this.artData.startDate).toLocaleDateString()
            // var endDate = new Date(this.artData.startDate).toLocaleDateString()
            // var startDate = new Intl.DateTimeFormat('kr').format(new Date(this.artData.startDate));
            // var endDate = new Intl.DateTimeFormat('kr').format(new Date(this.artData.endDate));


            formData.append('file', file.files[0]);
            formData.append('saleType', this.artData.saleType);
            formData.append('name', this.artData.name);
            formData.append('initPrice', this.artData.initPrice);
            formData.append('description', this.artData.description);
            formData.append('userId', this.artData.userId);
            formData.append('hashtagList', this.hashtagList);
            console.log("formdata: " + JSON.stringify(formData));

            axios.post('/api/art', formData, {
                headers:
                    { 'Content-Type': 'multipart/form-data' }
            }).then((res) => {
                console.log("then res: " + JSON.stringify(res.data));
                alert('등록 완료');
                this.$router.push('/vue');
            }).catch((res) => {
                console.log("catch res: " + JSON.stringify(res.data));
            })
        },
    },
}
</script>

<style>

</style>