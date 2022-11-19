<template>
  <div id="wrapper" class="container">
    <!--정렬-->
    <div>
    </div>
    <div class="clearfix">
      <button class="btn btn-outline-primary dropdown-toggle dropdown-toggle-split float-end" style="margin: 3% 2% -2% 0;" data-bs-toggle="dropdown">정렬
        기준</button>
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
            <ArtList :art="art" />
          </div>
        </div>
      </div>
    </div>

    <!-- Pagination -->
    <ul class="pagination justify-content-center pagination-circle">
      <li v-if="pagination.prev === true" class="page-item">
        <button class="page-link" type="button" @click="goToPreviousPage">Previous</button>
      </li>
      <li v-for="(idx) in range" :key="idx" :class="checkActive(pagination.currentPage, idx)" class="page-item">
        <button class="page-link" type="button" @click="goToPage(idx)">{{ idx }}</button>
      </li>
      <li v-if="pagination.next === true" class="page-item">
        <button class="page-link" type="button" @click="goToNextPage">Next</button>
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
  components: { ArtList },
  data() {
    return {
      page: 1,
      contents: [],
      pagination: {},
      range: [],
      img: '',
      artRequest: {
        page: this.$store.state.currentPage,
        sort: this.$store.state.sort,
      }
    }
  },
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
          this.$store.commit('setCurrentPage', res.data.pagination.currentPage);
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
    fetchArtData(){
      axios.post(`api/main/arts`, this.artRequest)
          .then(res => {
            this.contents = [...res.data.artList];
            this.$router.push(`/?sort=${this.artRequest.sort}&page=${this.artRequest.page}`);
          })
          .catch(error => {
            console.log(error);
          });
    },
    goToPage(page) {
      this.$store.commit('setCurrentPage', page);
      this.fetchData();
      this.$router.push(`/?page=${this.artRequest.page}`);
    },
    goToPreviousPage() {
      this.$store.commit('setCurrentPage', this.$store.state.currentPage - 1);
      if (this.$store.state.currentPage < 1) {
        alert('첫 번째 페이지입니다.');
        return 0;
      }
      this.fetchData();
      this.$router.push(`/?page=${this.artRequest.page}`);
      this.scrollToTop();
    },
    goToNextPage() {
      this.$store.commit('setCurrentPage', this.$store.state.currentPage + 1);
      if (this.$store.state.currentPage > this.pagination.totalPages) {
        alert('마지막 페이지입니다.');
        return 0;
      }
      this.fetchData();
      this.$router.push(`/?page=${this.artRequest.page}`);
      this.scrollToTop();
    },
    clickRD(){
      this.artRequest.sort = 'date';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);
    },

    clickrRD(){
      this.artRequest.sort = 'rdate';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);


    },
    clickBP(){
      this.artRequest.sort = 'price';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);
    },
    clickrBP(){
      this.artRequest.sort = 'rprice';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);
    },
    clickBC(){
      this.artRequest.sort = 'count';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);
    },
    clickrBC(){
      this.artRequest.sort = 'rcount';
      this.$store.commit('setSort',this.artRequest.sort);
      this.fetchArtData();
      this.$router.push({Path:'/', query: {sort:this.artRequest.sort,page:this.artRequest.page}});
      console.log(this.artRequest);
    }
  }
}

</script>

<style>

</style>