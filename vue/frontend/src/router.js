import { createWebHistory, createRouter } from "vue-router"
import AppHome from './views/home/AppHome.vue'
import UserRegister from './views/user/UserRegister.vue'
import ArtworkRegister from './views/art/ArtworkRegister.vue'
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
    path: "/signup",
    component: UserRegister,
  },
  {
    path: "/user",
    component: MyPage,
    children: [
      {
        path: "edit",
        component: UpdateUserInfo,
      },
      {
        path: "point",
        component: UserPointCharge,
      },
      {
        path: "refund",
        component: UserPointRefund,
      },
    ]
  },
  {
    path: "/",
    component: AppHome,
  },
  {
    path: "/art/register",
    component: ArtworkRegister,
  },
  {
    path: "/find-id",
    component: IdFind,
  },
  {
    path: "/find-password",
    component: PasswordFind,
  },
  {
    path: "/reset-password",
    component: PasswordReset,
  },
  {
    path: "/art/detail",
    component: ArtworkPurchase,
  },
  {
    path: "/login",
    component: UserLogin,
  },
  {
    path: "/search",
    component: SearchResults,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;