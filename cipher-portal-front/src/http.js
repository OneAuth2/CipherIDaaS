/**
 * Created by superman on 17/2/16.
 * http配置
 */

import axios from "axios";
import error from "@/util/error.js";
import sort from "@/util/sort.js";
import router from "./router/index.js";
import qs from "qs";
import md5 from "blueimp-md5";
import Toast from "@/plugins/Toast/index.js";
// import md5 from 'js-md5'
import store from "./store/index";
axios.error = error;
// axios 配置
// axios.defaults.timeout = 5000
// axios.defaults.timeout = 70000;
axios.defaults.baseURL = "/";

// axios.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';
// axios.defaults.headers.common["Authorization"] = "AUTH_TOKEN";
// axios.defaults.headers = {
//     'Content-type': 'application/x-www-form-urlencoded'
// };

// http request 拦截器
axios.interceptors.request.use(
  config => {
    config.transformRequest = [
      function (data, headers) {
        // 排序-》拼接-》编码-》md5
        let timestamp = new Date().getTime();
        let sortData = sort.objKeySort(data); // 排序后的数据
        // console.log('data:');
        // console.log(data);
        // console.log('sortData:');
        // console.log(sortData);
        let qsSortData = qs.stringify(sortData); // 以key1=value1&key2=value2方式序列化排序后的数据
        // console.log('qsSortData');
        // console.log(qsSortData);
        let decodeData = decodeURIComponent(qsSortData); // 先解码抵消qs里的编码
        // console.log('decodeData');
        // console.log(decodeData);
        let encodeData = encodeURIComponent(decodeData); // 整体编码
        // console.log('encodeData');
        // console.log(encodeData);
        // eslint-disable-next-line
        let singEncodeData = md5(encodeData); // 签名序列化的数据
        // console.log('singEncodeData(after md5):');
        // console.log(singEncodeData);
        let allData = sortData;
        allData.sign = singEncodeData;
        allData.timestamp = timestamp;
        allData.companyUUid = "123456";
        let userInfo = store.state.authLogic.userInfo;
        if (userInfo.userId) {
          allData.userId = userInfo.userId;
        } else {
          allData.userId = "";
        }
        // console.log(allData);
        // console.log(store.state.authLogic);
        let qsAllData = qs.stringify(allData); // 序列化为key-value键值对字符串
        // let qsAllData = JSON.stringify(allData); // 序列化为JSON字符串

        // console.log('allData:');
        // console.log(allData);
        // console.log('qsAllData:');
        // console.log(qsAllData);
        return qsAllData;
      }
    ];

    // config.paramsSerializer = [function (params) {
    //   return qs.stringify(params + "tick='aaa'", { arrayFormat: "brackets" });
    // }];
    return config;
  },
  err => {
    return Promise.reject(err);
  }
);

// http response 拦截器
axios.interceptors.response.use(
  response => {
    // console.log(response);
    // console.log(router);
    if (response) {
      switch (response.data.return_code) {
        case 419:
          // 419 清除token信息并跳转到登录页面
          store.state.authLogic.userInfo.userId = "";
          let login = "/authLogic/login/staff/";
          Toast(response.data.return_msg);
          // 只有在当前路由不是登录页面才跳转
          router.currentRoute.path !== login &&
            router.replace({
              path: login
            });
      }
    }
    return response;
  },
  error => {
    // if (error.response) {
    //   switch (error.response.data.return_code) {
    //     case 419:
    //       // 419 清除token信息并跳转到登录页面
    //       localStorage.removeItem("authLogic");
    //       let login = "/authLogic/login/staff/";
    //       // 只有在当前路由不是登录页面才跳转
    //       router.currentRoute.path !== login &&
    //         router.replace({
    //           path: login
    //         });
    //   }
    // }
    // console.log(JSON.stringify(error));//console : Error: Request failed with status code 402
    return Promise.reject(error.response.data);
  }
);

export default axios;
