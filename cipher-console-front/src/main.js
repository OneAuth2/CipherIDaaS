import Vue from "vue";
import router from "./router/index.js";
import store from "./store/index";
import App from "./App.vue";
import axios from "./http";
// import axios from 'axios'
import common from "@/util/common.js";
import qs from "qs";
import iView from "iview";
import "iview/dist/styles/iview.css";
import "./theme/index.less";
import "./assets/styles/iviewOver.less";
import { Loading } from "@/plugins/Loading.js";
import ModalBox from "@/plugins/modal/index.js";
import MessageBox from "@/plugins/message/index.js";

import echarts from "echarts";
import pluginSf from "@/components/index.js";

Vue.prototype.$echarts = echarts;
Vue.use(pluginSf);

Vue.use(ModalBox); // 封装的myModal组件 通过this调用
Vue.use(MessageBox); // 封装的myMessage组件 通过this调用

Vue.prototype.$qs = qs;
Vue.prototype.$common = common;

Vue.config.productionTip = false;

Vue.prototype.axios = axios;

Vue.use(iView);
Vue.use(Loading);

// // 页面刷新时，重新赋值token
// if (window.localStorage.getItem('token')) {
//   store.commit('changeToken', window.localStorage.getItem('token'))
// }

// 加载进度
router.beforeEach((to, from, next) => {
  iView.LoadingBar.start();
  // console.log("====beforeEach=====");
  // console.log(to);
  // console.log(from);
  // let token = to.query.ticket;
  // token !== undefined && store.commit("changeToken", token); // token不为空时才保存

  let path = to.path;
  store.commit("changePath", path);
  let token = window.localStorage.getItem("token");
  if (path === "/") {
    // 1.protal页面跳转放过，在路由守卫里拦截
    // store.commit("changeTab", store.state.tab[0]);
    next();
  } else if (path === "/login") {
    // 2.登录页面
    if (token) {
      // 1.已经登录过，则跳转到
      next("/");
    } else {
      next();
    }
  } else {
    // 3.不是protal页面跳转和登录页面
    if (token) {
      // 1.如果请求中有登录状态ticket，则继续请求
      next();
    } else {
      // 2.如果没有ticket，跳转到登录页面
      next("/login");
    }
  }
});

router.afterEach((to, from) => {
  // console.log("***afterEach****");
  // console.log(to);
  // console.log(from);
  iView.LoadingBar.finish();
});

new Vue({
  router,
  store,
  axios,
  render: h => h(App)
}).$mount("#app");
