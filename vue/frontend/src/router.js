import { createWebHistory, createRouter } from "vue-router"
import AppHome from './views/home/AppHome.vue'
import UserRegister from './views/user/UserRegister.vue'
import ArtworkRegister from './views/art/ArtworkRegister.vue'
// import TermsOfService from './views/TermsOfService.vue'
import CreateAccount from './views/user/CreateAccount.vue'
import IdFind from './views/login/IdFind.vue'
import PasswordFind from './views/login/PasswordFind.vue'
import PasswordReset from './views/login/PasswordReset.vue'
import UserPointCharge from './views/myPage/UserPointCharge.vue'
import UserPointRefund from './views/myPage/UserPointRefund.vue'
import ArtworkPurchase from './views/art/ArtworkPurchase.vue'
import UserLogin from './views/login/UserLogin.vue'
import SearchResults from './views/search/SearchResults.vue'
import UpdateUserInfo from './views/myPage/UpdateUserInfo.vue'
import MyPage from './views/myPage/MyPage.vue'

const routes = [
  {
    path: "/createAccount",
    component: CreateAccount,
    children: [
      // {
      //   path: "termsOfService",
      //   component: TermsOfService,
      // },
      {
        path: "userRegister",
        component: UserRegister,
      },
    ]
  },
  {
    path: "/myPage",
    component: MyPage,
    children: [
      {
        path: "updateUserInfo",
        component: UpdateUserInfo,
      },
      {
        path: "userPointCharge",
        component: UserPointCharge,
      },
      {
        path: "userPointRefund",
        component: UserPointRefund,
      },
    ]
  },
  {
    path: "/vue",
    component: AppHome,
  },
  {
    path: "/artworkRegister",
    component: ArtworkRegister,
  },
  {
    path: "/idFind",
    component: IdFind,
  },
  {
    path: "/passwordFind",
    component: PasswordFind,
  },
  {
    path: "/passwordReset",
    component: PasswordReset,
  },
  {
    path: "/userPointCharge",
    component: UserPointCharge,
  },
  {
    path: "/userPointRefund",
    component: UserPointRefund,
  },
  {
    path: "/artworkPurchase",
    component: ArtworkPurchase,
  },
  {
    path: "/userLogin",
    component: UserLogin,
  },
  {
    path: "/searchResults",
    component: SearchResults,
  },
  {
    path: "/updateUserInfo",
    component: UpdateUserInfo,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;