<template>
    <div>
        <nav id="main-navbar"
            class="fixed-top navbar navbar-expand-lg navbar-light bg-white py-4 border-bottom border-black-50">
            <div class="container-fluid">
                <!-- Container wrapper -->
                <a @click="$router.push('/vue');" class="navbar-brand fs-3" href="#">Another Art</a>
                <button class="navbar-toggler" type="button" data-bs-toggle="collapse"
                    data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent"
                    aria-expanded="false" aria-label="Toggle navigation">
                    <span class="navbar-toggler-icon"></span>
                </button>
                <div class="collapse navbar-collapse" id="navbarSupportedContent">
                    <!-- <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                        <li class="nav-item">
                            <a class="nav-link active" aria-current="page" href="#">Home</a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link" href="#">Link</a>
                        </li>
                        <li class="nav-item dropdown">
                            <a class="nav-link dropdown-toggle" href="#" role="button" data-bs-toggle="dropdown"
                                aria-expanded="false">
                                Dropdown
                            </a>
                            <ul class="dropdown-menu">
                                <li><a class="dropdown-item" href="#">Action</a></li>
                                <li><a class="dropdown-item" href="#">Another action</a></li>
                                <li>
                                    <hr class="dropdown-divider">
                                </li>
                                <li><a class="dropdown-item" href="#">Something else here</a></li>
                            </ul>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link disabled">Disabled</a>
                        </li>
                    </ul> -->
                    <!-- Search form -->
                    <!-- class="d-none d-md-flex justify-content-center" -->

                    <div class="mx-auto input-group w-auto my-auto">
                        <select v-model="searchData.type" class="btn btn-outline-primary rounded-start" 
                            aria-expanded="false" @change="test"
                            style="border-end-end-radius: 0; border-start-end-radius: 0;">
                            <option :value="typeList[0].type">{{ typeList[0].name }}</option>
                            <option :value="typeList[1].type">{{ typeList[1].name }}</option>
                        </select>

                        <input autocomplete="off" type="text" v-model="searchData.keyword"
                            class="form-control rounded-0 border border-primary" placeholder='Search'
                            style="min-width: 225px; max-width: 600px;" aria-label="Text input with dropdown button" />
                        <button @click="artworkSearch"
                            class="input-group-text border border-primary bg-light form-control">
                            <font-awesome-icon icon="search" />
                        </button>
                    </div>

                    <!-- Right links -->
                    <ul class="navbar-nav d-flex align-items-center flex-row">

                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <!-- 비로그인 시 -->
                            <li v-if="$store.state.isLogined === false" class="nav-item">
                                <a href="#" @click="$router.push('/userLogin')" class="nav-link active" aria-current="page">로그인</a>
                            </li>
                            <li v-if="$store.state.isLogined === false" class="nav-item">
                                <h6 class="nav-link active text-secondary" aria-current="page" href="#">|</h6>
                            </li>
                            <li v-if="$store.state.isLogined === false" class="nav-item">
                                <a href="#" @click="$router.push('/createAccount/userRegister')" class="nav-link active">회원가입</a>
                            </li>
                            <!-- 로그인 시 -->
                            <li v-if="$store.state.isLogined === true" class="nav-item">
                                <a href="#" @click="logout" class="nav-link active">로그아웃</a>
                            </li>
                            <li v-if="$store.state.isLogined === true" class="nav-item">
                                <h6 class="nav-link active text-secondary" aria-current="page" href="#">|</h6>
                            </li>
                            <li v-if="$store.state.isLogined === true" class="nav-item">
                                <a href="#" @click="$router.push('/myPage')" class="nav-link active">마이페이지</a>
                            </li>
                            <button @click="$router.push('/artworkRegister')" class="btn btn-outline-primary mx-2">
                                작품 등록
                            </button>
                        </ul>
                        <!-- <li class="nav-item">
                            <a class="nav-link me-3 py-1" href="#" data-mdb-toggle="tooltip" title="Create">
                                <font-awesome-icon icon="video" />
                            </a>
                        </li>
                        <li class="nav-item">
                            <a class="nav-link me-3 py-1" href="#" data-mdb-toggle="tooltip" title="YouTube apps">
                                <font-awesome-icon icon="th" />
                            </a>
                        </li>
                        <li class="nav-item me-3">
                            <a class="nav-link py-1" href="#" data-mdb-toggle="tooltip" title="Notifications">
                                <font-awesome-icon icon="bell" />
                            </a>
                        </li> -->

                        <!-- Avatar -->

                    </ul>
                </div>
                <!-- Container wrapper -->
            </div>
        </nav>
        <!--Navbar-->
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'navbar',
    data() {
        return {
            searchDropdownValue: '',
            searchData: {
                keyword: 'art3',
                scroll: 0,
                sort: 'date',
                type: 'auction',
            },
            typeList: [
                {
                    type: 'auction',
                    name: '경매 작품',
                },
                {
                    type: 'general',
                    name: '일반 작품',
                }
            ],
            num: 0,
        }
    },
    methods: {
        artworkSearch() {
            console.log(this.searchData);
            axios.post('/api/keyword/arts', this.searchData).then((res) => {
                console.log("then " + JSON.stringify(res.data));
                this.$store.commit("setSearchType", this.searchData.type);
                this.$store.commit("setSearchData", JSON.stringify(res.data));
                this.$router.push('/searchResults');
            }).catch((res) => {
                console.log("catch " + JSON.stringify(res));
            })
        },
        logout() {
            axios.post('/api/logout').then(() => {
                sessionStorage.clear();
                this.$store.commit("setIsLogined", false);
                this.$router.push('/vue')
            }).catch(() => {

            })
        },
    }

}
</script>

<style>
</style>