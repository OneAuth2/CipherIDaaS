import Vue from "vue";
import Router from "vue-router";
import portal from "@/router/authLogic.js";

Vue.use(Router);
export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    portal.authLogic,
    // { path: "/", redirect: "/authLogic/login/staff/" },
    {
      path: "/skipAppError",
      component: () =>
        import(
          /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/SkipAppError.vue"
        )
    },
    {
      path: "/skipSubConfig",
      component: () =>
        import(
          /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/SkipSubConfig.vue"
        )
    },
    {
      path: "/loginedShow",
      component: () =>
        import(
          /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/ShowIndex.vue"
        ),
      children: [
        {
          path: "", // 主页
          component: () =>
            import(
              /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/children/Home.vue"
            )
        },
        {
          path: "inform", //  信息页
          component: () =>
            import(
              /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/children/Inform.vue"
            )
        },
        {
          path: "changePwd", //  修改密码
          component: () =>
            import(
              /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/children/ChangePwd.vue"
            )
        },
        {
          path: "loginOut", //  修改密码
          component: () =>
            import(
              /* webpackChunkName: "LoginedShow" */ "@/views/loginedShow/children/LoginOut.vue"
            )
        }
      ]
    },
    {
      path: "*",
      name: "NotFound",
      component: () =>
        import(/* webpackChunkName: "NotFound" */ "@/views/404.vue")
    }
  ]
});
