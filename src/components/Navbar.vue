<template>
    <div>
        <nav id="main-navbar"
            class="fixed-top navbar navbar-expand-lg navbar-light bg-white py-4 border-bottom border-black-50">
            <div class="container-fluid">
                <!-- Container wrapper -->
                <a @click="$router.push('/');" class="navbar-brand fs-3" href="#">Another Art</a>
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
                            <select class="btn btn-outline-primary rounded-start dropdown-toggle"
                                data-bs-toggle="dropdown" aria-expanded="false"  @change="test($event)"
                                style="border-end-end-radius: 0; border-start-end-radius: 0;">
                                <option class="dropdown-item" href="#">경매 작품</option>
                                <option class="dropdown-item" href="#">일반 작품</option>
                            </select>

                        <input autocomplete="off" type="text" v-model="searchData.hashtag"
                            class="form-control rounded-0 border border-primary" placeholder='Search'
                            style="min-width: 225px; max-width: 600px;" aria-label="Text input with dropdown button"/>
                        <button @click="artworkSearch" class="input-group-text border border-primary bg-light form-control">
                            <font-awesome-icon icon="search" />
                        </button>
                    </div>

                    <!-- Right links -->
                    <ul class="navbar-nav d-flex align-items-center flex-row">

                        <ul class="navbar-nav me-auto mb-2 mb-lg-0">
                            <li class="nav-item">
                                <a @click="$router.push('/userLogin');" class="nav-link active" aria-current="page"
                                    href="#">로그인</a>
                            </li>
                            <li class="nav-item">
                                <h6 @click="$router.push('');" class="nav-link active text-secondary"
                                    aria-current="page" href="#">|</h6>
                            </li>
                            <li class="nav-item">
                                <a @click="$router.push('/createAccount/termsOfService');" class="nav-link active"
                                    href="#">회원가입</a>
                            </li>
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
                hashtag: '사자',
                scroll: 0,
                sort: 'date',
                type: 'general',
            },
        }
    },
    methods: {
        test($event) {
            if($event.target.value == '일반 작품') {
                this.searchData.type = 'general';
            } else {
                this.searchData.type = 'auction';
            }
        },
        artworkSearch() {
            // axios.get('/api/search/art', this.searchData).then((res) => {
            //     console.log(res);
            // }).catch((res) => {
            //     console.log(res);
            // });
            let url = '/api/search/art';
            console.log(this.searchData);

            axios({
                method: 'GET',
                headers: { 'content-type': 'application/json' },
                data: this.searchData,
                url
            }).then((res) => {
                console.log('success');
                console.log(res);
            }).catch((res) => {
                console.log('fail');
                console.log(res);
            });
                
        },
    }

}
</script>

<style>
</style>