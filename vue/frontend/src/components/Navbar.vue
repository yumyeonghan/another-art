<template>
  <div>
    <!-- AISearchModal -->
    <div class="modal fade" id="AISearchModal" data-bs-backdrop="static" data-bs-keyboard="false" tabindex="-1"
      aria-labelledby="staticBackdropLabel" aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered modal-dialog-scrollable">
        <div class="modal-content h-598">
          <div class="modal-header">
            <h5 class="modal-title" id="staticBackdropLabel">이미지를 통해 검색</h5>
            <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
          </div>
          <div class="modal-body">
            <div class="input-group rounded text-center">
              <h1 v-if="isHashtagSearchKeywordEmpty" class="w-100">Loading∙∙∙</h1>
              <div id="webcam-container" class="w-100"></div>
              <div id="label-container" class="w-100 mt-3"></div>
            </div>
            <div @click="artworkHashtagSearch" class="text-center h-60 mt-3" style="vertical-align: bottom;">
              <button type="button" class="btn btn-outline-primary" data-bs-dismiss="modal" aria-label="Close">검색하기
              </button>
            </div>
          </div>
        </div>
      </div>
    </div>

    <nav id="main-navbar"
      class="fixed-top navbar navbar-expand-lg navbar-light bg-white py-4 border-bottom border-black-50">
      <div class="container-fluid">
        <!-- Container wrapper -->
        <a @click="$router.push('/');" class="navbar-brand fs-3" style="margin-left: 1%;" href="#">Another Art</a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent"
          aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
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
            <select v-model="keywordSearchData.type" class="btn btn-outline-primary rounded-start" aria-expanded="false"
              @change="test" style="border-end-end-radius: 0; border-start-end-radius: 0;">
              <option :value="typeList[0].type">{{ typeList[0].name }}</option>
              <option :value="typeList[1].type">{{ typeList[1].name }}</option>
            </select>

            <input autocomplete="off" type="text" v-model="keywordSearchData.keyword"
              class="form-control rounded-0 border border-primary" placeholder='Search'
              style="min-width: 225px; max-width: 600px;" aria-label="Text input with dropdown button" />
            <button @click="artworkKeywordSearch" class="input-group-text border border-primary bg-white form-control">
              <font-awesome-icon icon="search" />
            </button>
            <button @click="activeAIModal" class="input-group-text border border-primary bg-white form-control"
              data-bs-toggle="modal" data-bs-target="#AISearchModal">
              <font-awesome-icon icon="fa-solid fa-robot" />
              <!-- <font-awesome-icon icon="fa-regular fa-image" /> -->
            </button>
          </div>

          <!-- Right links -->
          <ul class="navbar-nav d-flex align-items-center flex-row">

            <ul class="navbar-nav me-auto mb-2 mb-lg-0">
              <!-- 비로그인 시 -->
              <li v-if="$store.state.isLogined === false" class="nav-item">
                <a href="#" @click="$router.push('/login')" class="nav-link active" aria-current="page">로그인</a>
              </li>
              <li v-if="$store.state.isLogined === false" class="nav-item">
                <h6 class="nav-link active text-secondary" aria-current="page" href="#">|</h6>
              </li>
              <li v-if="$store.state.isLogined === false" class="nav-item">
                <a href="#" @click="$router.push('/signup')" class="nav-link active">회원가입</a>
              </li>
              <!-- 로그인 시 -->
              <li v-if="$store.state.isLogined === true" class="nav-item">
                <a href="#" @click="logout" class="nav-link active">로그아웃</a>
              </li>
              <li v-if="$store.state.isLogined === true" class="nav-item">
                <h6 class="nav-link active text-secondary" aria-current="page" href="#">|</h6>
              </li>
              <li v-if="$store.state.isLogined === true" class="nav-item">
                <a href="#" @click="$router.push('/user')" class="nav-link active">마이페이지</a>
              </li>
              <button @click="artRegister" class="btn btn-outline-primary mx-2">
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

// the link to your model provided by Teachable Machine export panel
const URL = 'https://teachablemachine.withgoogle.com/models/cWFTIou2L/';
let model, webcam, labelContainer, maxPredictions;

export default {
  name: 'navbar',
  data() {
    return {
      searchDropdownValue: '',
      keywordSearchData: {
        keyword: 'art3',
        page: 1,
        sort: 'date',
        type: 'auction',
      },
      hashtagSearchData: {
        hashtag: '',
        page: 1,
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
      loginData: {
        userId: '초기값',
        userName: "초기값",
        userNickname: '초기값',
        loginId: '초기값',
      },
      num: 0,
      AIModalCount: 0,
    }
  },
  methods: {
    artworkKeywordSearch() {
      console.log(this.keywordSearchData);
      axios.post('/api/keyword/arts', this.keywordSearchData)
        .then((res) => {
          console.log("then " + JSON.stringify(res.data));
          this.$store.commit("setSearchType", this.keywordSearchData.type);
          this.$store.commit("setSearchData", JSON.stringify(res.data));
          this.$router.push(`/search?keyword=${this.keywordSearchData.keyword}`);
        }).catch((err) => {
          let errMsg = JSON.stringify(err.response.data.message);
          errMsg = errMsg.substring(1, errMsg.length - 1);
          console.log("errMsg -> " + errMsg);
          alert(errMsg);
        })
    },
    artworkHashtagSearch() {
      console.log(this.hashtagSearchData);
      axios.post('/api/hashtag/arts', this.hashtagSearchData)
        .then((res) => {
          console.log("then " + JSON.stringify(res.data));
          this.$store.commit("setSearchType", this.hashtagSearchData.type);
          this.$store.commit("setSearchData", JSON.stringify(res.data));
          this.$router.push(`/search?hashtag=${this.hashtagSearchData.hashtag}`);
        }).catch((err) => {
          let errMsg = JSON.stringify(err.response.data.message);
          errMsg = errMsg.substring(1, errMsg.length - 1);
          console.log("errMsg -> " + errMsg);
          alert(errMsg);
        })
    },
    logout() {
      axios.post('/api/logout')
        .then(() => {
          // sessionStorage.clear();
          sessionStorage.setItem("loginData", JSON.stringify(this.loginData));
          this.$store.commit("setIsLogined", false);
          this.$store.commit("setLoginData", {
            userId: '초기값',
            userName: "초기값",
            userNickname: '초기값',
            loginId: '초기값',
          });
          this.$router.push('/')
        }).catch((err) => {
          let errMsg = JSON.stringify(err.response.data.message);
          errMsg = errMsg.substring(1, errMsg.length - 1);
          console.log("errMsg -> " + errMsg);
          alert(errMsg);
        })
    },
    artRegister() {
      if (this.$store.state.isLogined === true) {
        this.$router.push('/art/register');
      } else {
        alert('로그인하지 않은 사용자입니다');
        this.$router.push('/login');
      }
    },
    activeAIModal() {
      if (this.AIModalCount == 0) {
        this.init();
      }
      this.AIModalCount++;
    },
    // AI 추천 검색
    // Load the image model and setup the webcam
    async init() {
      const modelURL = URL + "model.json";
      const metadataURL = URL + "metadata.json";

      // load the model and metadata
      // Refer to tmImage.loadFromFiles() in the API to support files from a file picker
      // or files from your local hard drive
      // Note: the pose library adds "tmImage" object to your window (window.tmImage)
      model = await window.tmImage.load(modelURL, metadataURL);
      maxPredictions = model.getTotalClasses();

      // Convenience function to setup a webcam
      const flip = true; // whether to flip the webcam
      webcam = new window.tmImage.Webcam(400, 400, flip); // width, height, flip
      await webcam.setup(); // request access to the webcam
      await webcam.play();
      window.requestAnimationFrame(this.loop);

      // append elements to the DOM
      document.getElementById("webcam-container").appendChild(webcam.canvas);
      labelContainer = document.getElementById("label-container");
      for (let i = 0; i < maxPredictions; i++) { // and class labels
        labelContainer.appendChild(document.createElement("div"));
      }
    },

    async loop() {
      webcam.update(); // update the webcam frame
      await this.predict();
      window.requestAnimationFrame(this.loop);
    },

    // run the webcam image through the image model
    async predict() {
      // predict can take in an image, video or canvas html element
      const prediction = await model.predict(webcam.canvas);

      if ((prediction[0].className = "hand") && (prediction[0].probability.toFixed(2) >= 0.80)) {
        this.hashtagSearchData.hashtag = "손";
        labelContainer.childNodes[0].innerHTML = "손";
      } else if ((prediction[1].className = "face") && (prediction[1].probability.toFixed(2) >= 0.80)) {
        this.hashtagSearchData.hashtag = "얼굴";
        labelContainer.childNodes[0].innerHTML = "얼굴";
      } else {
        labelContainer.childNodes[0].innerHTML = "추천 작품을 찾지 못했습니다.";
      }

      // for (let i = 0; i < maxPredictions; i++) {
      //     const classPrediction =
      //         prediction[i].className + ": " + prediction[i].probability.toFixed(2);
      //     labelContainer.childNodes[0].innerHTML = classPrediction;
      // }
    },
  },
  computed: {
    isHashtagSearchKeywordEmpty() {
      return this.hashtagSearchData.hashtag == '';
    }
  },
  beforeMount() {
    axios.post('/api/session-check').then((res) => {
      console.log("session-check success -> " + JSON.stringify(res.data));
      this.$store.commit('setSessionData', { ...res.data });
      console.log("sessionData -> " + JSON.stringify(this.$store.state.sessionData));
      console.log("isLogined -> " + this.$store.state.isLogined);
    }).catch((res) => {
      console.log("session-check fail -> " + JSON.stringify(res));
      this.$store.commit('setSessionData', {});
      console.log("sessionData -> " + JSON.stringify(this.$store.state.sessionData));
      console.log("isLogined -> " + this.$store.state.isLogined);
    });

    // 로그인 중인 유저 정보가 존재하지 않을 때
    if (Object.keys(this.$store.state.sessionData).length === 0) {
      this.$store.state.isLogined = false;
      // 로그인 중인 유저 정보가 존재할 떄
    } else {
      this.$store.state.isLogined = true;
    }
  },

}
</script>

<style>

</style>