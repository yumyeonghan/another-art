import { createStore } from 'vuex'

const store = createStore({
    state () {
      return {
        count: 0,
        userData: {
            userId: '',
            userName: '',
            userNickname: '',
            loginId: '',
        }
      }
    },
    mutations: {
      increment (state) {
        state.count++
      },
      saveUserData(state, payload) {
        state.userData = payload.userData;
        console.log("store: " + state.userData);
      },
    }
  })

export default store;