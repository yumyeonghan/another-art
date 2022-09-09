import { createWebHistory, createRouter } from "vue-router"
import AppHome from './views/AppHome.vue'
import UserRegister from './views/UserRegister.vue'
import ArtworkRegister from './views/ArtworkRegister.vue'
import TermsOfService from './views/TermsOfService.vue'
import CreateAccount from './views/CreateAccount.vue'
import IdFind from './views/IdFind.vue'
import PasswordFind from './views/PasswordFind.vue'
import UserPointCharge from './views/UserPointCharge.vue'
import UserPointRefund from './views/UserPointRefund.vue'

const routes = [
  {
    path: "/createAccount",
    component: CreateAccount,
    children: [
      {
        path: "termsOfService",
        component: TermsOfService,
      },
      {
        path: "userRegister",
        component: UserRegister,
      },
    ]
  },
  {
    path: "/",
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
    path: "/userPointCharge",
    component: UserPointCharge,
  },
  {
    path: "/userPointRefund",
    component: UserPointRefund,
  },
];

const router = createRouter({
  history: createWebHistory(),
  routes,
});

export default router;