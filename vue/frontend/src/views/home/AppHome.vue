<template>
  <div id="wrapper" class="container">
    <!--정렬-->
    <div>
    </div>
    <div class="d-flex justify-content-center align-items-center flex-row">
      <button class="btn btn-outline-primary dropdown-toggle dropdown-toggle-split" data-bs-toggle="dropdown">정렬 기준</button>
      <ul class="dropdown-menu" aria-labelledby="navbarDropdown">
        <li>
          <button class="dropdown-item" href="#" @click="clickRD">등록날짜 내림차순</button>
        </li>
        <li>
          <button class="dropdown-item" href="#" @click="clickrRD">등록날짜 오름차순</button>
        </li>
        <li>
          <hr class="dropdown-divider">
        </li>
        <li>
          <button class="dropdown-item" href="#" @click="clickBP">비드 가격 내림차순</button>
        </li>
        <li>
          <button class="dropdown-item" href="#" @click="clickrBP">비드 가격 오름차순</button>
        </li>
        <li>
          <hr class="dropdown-divider">
        </li>
        <li>
          <button class="dropdown-item" href="#" @click="clickBC">비드 횟수 내림차순</button>
        </li>
        <li>
          <button class="dropdown-item" href="#" @click="clickrBC">비드 횟수 오름차순</button>
        </li>
      </ul>
    </div>

    <!-- 작품 리스트-->
    <div class="album py-5">
      <div class="container">
        <div class="row row-cols-1 row-cols-sm-2 row-cols-md-4 g-3">
          <div v-for="(art,i) in contents" :key="i">
            <ArtList :art="art"/>
          </div>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <ul class="pagination justify-content-center pagination-circle">
      <li v-if="pagination.prev === true" class="page-item">
        <button class="page-link" type="button">Previous</button>
      </li>
      <li v-for="(idx) in this.range" :key="idx" v-bind:class="checkActive(this.pagination.currentPage, idx)" class="page-item">
        <button class="page-link" type="button">{{ idx }}</button>
      </li>
      <li v-if="pagination.next === true" class="page-item">
        <button class="page-link" type="button">Next</button>
      </li>
    </ul>
  </div>

</template>

<script>
import axios from 'axios'
// import PasswordFind from "@/views/PasswordFind";
import ArtList from "@/components/ArtList";

export default {
  name: 'appHome',
  components: {ArtList},
  data: () => ({
    page: 1,
    contents: [],
    pagination: {},
    range: [],
    img: '',
    artRequest: {
      page: 1,
      sort: "date",
    }
  }),
  created: function () {
    this.fetchData()
  },
  methods: {
    checkActive(currentPage, idx) {
      if (currentPage === idx) {
        return 'active';
      } else {
        return '';
      }
    },
    fetchData() {
      axios.post(`api/main/arts`, this.artRequest)
          .then(res => {
            console.log("res.data.artList" + res.data.artList);
            this.contents = [...res.data.artList];
            this.pagination = res.data.pagination;
            this.page = res.data.pagination.currentPage;
            for (let i = this.pagination.rangeStartNumber; i <= this.pagination.rangeEndNumber; i++) {
              this.range.push(i);
            }
            console.log('Hello --> ' + JSON.stringify(this.contents, null, 2));
            console.log('Hello --> ' + JSON.stringify(this.pagination, null, 2));
          })
          .catch(error => {
            console.log(error);
          });
    },
    clickRD() {
      this.$router.push({Path: '/', query: {sort: 'date', page: this.page}})
    },
    clickrRD() {
      this.$router.push({Path: '/', query: {sort: 'rdate', page: this.page}})
    },
    clickBP() {
      this.$router.push({Path: '/', query: {sort: 'price', page: this.page}})
    },
    clickrBP() {
      this.$router.push({Path: '/', query: {sort: 'rprice', page: this.page}})
    },
    clickBC() {
      this.$router.push({Path: '/', query: {sort: 'count', page: this.page}})
    },
    clickrBC() {
      this.$router.push({Path: '/', query: {sort: 'rcount', page: this.page}})
    },
  }
}

</script>

<style>

</style>