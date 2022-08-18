<template>
    <div>
        <!--duplicateCheckModal-->
        <div class="modal fade" id="duplicateCheckModal" data-bs-backdrop="static" data-bs-keyboard="false"
            tabindex="-1" aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">duplicateCheckModal</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        duplicateCheckModal
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Understood</button>
                    </div>
                </div>
            </div>
        </div>
        <!-- univModal -->
        <div class="modal fade" id="univModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
            aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
                <div class="modal-content h-598">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">학교 찾기</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body">
                        <div class="input-group rounded">
                            <input v-model="univSearchKeyword" type="search" class="form-control rounded" placeholder="학교명"
                                aria-label="Search" aria-describedby="search-addon" autofucus/>
                            <span class="input-group-text border-0" id="search-addon">
                                <button type="button" @click="univSearch(univSearchKeyword)" class="btn">검색</button>
                            </span>
                        </div>
                        <div v-for="(univ, i) in univInfoList[0]" :key="i" class="mt-3">
                            <div>
                                <button type="button" @click="univName = univ.schoolName + ' ' + univ.campusName;" 
                                class="btn btn-outline-dark pt-3" style="width: 466px;"  data-bs-dismiss="modal" aria-label="Close">
                                <h4>{{ univ.schoolName }} ({{ univ.campusName }})</h4>
                                <p>{{ univ.adres }}</p>
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- addressModal -->
        <div class="modal fade" id="addressModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
            aria-labelledby="staticBackdropLabel" aria-hidden="true">
            <div class="modal-dialog modal-dialog-centered">
                <div class="modal-content">
                    <div class="modal-header">
                        <h5 class="modal-title" id="staticBackdropLabel">addressModal</h5>
                        <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                    </div>
                    <div class="modal-body h-25">
                        addressModal
                    </div>
                    <div class="modal-footer">
                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                        <button type="button" class="btn btn-primary">Understood</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import axios from 'axios';

export default {
    name: 'registerModals',
    data() {
        return {
            univName: '',
            univSearchKeyword: '',
            univInfoList: [],
        }
    },
    methods: {
        univSearch(univSearchKeyword) {
            // axios post 요청
            // axios.post('서버URL', '보낼데이터')
            const key = '8dd33f9c8964bf00d59a79639cf65f79';
            let newUnivInfoList = [];

            this.univInfoList = [];

            axios.get(`//www.career.go.kr/cnet/openapi/getOpenApi?apiKey=${key}&svcType=api&svcCode=SCHOOL&contentType=json&gubun=univ_list&thisPage=1&perPage=10&searchSchulNm=${univSearchKeyword}`)
                .then((json) => {
                    // console.log(json.data.dataSearch.content);
                    newUnivInfoList = json.data.dataSearch.content;
                    this.univInfoList.push(newUnivInfoList);
                    // content[i].schoolName -> 학교 이름
                    // content[i].adres -> 학교 주소
                }).catch(() => {
                    console.log('error: no more url');
                })
        },
    }
}
</script>

<style>
.h-598 {
    height: 598px;
}
</style>