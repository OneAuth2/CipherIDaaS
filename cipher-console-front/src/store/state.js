import tabHome from "@/data/tab.js";
import { selectOSTree, homeOrg } from "@/data/selectOSTree.js";
let token = null;
let phoneNumber = "";
let top = { toggle: true }; // 顶部状态记录
let left = []; // 左侧列表数据
let tab = tabHome; // tab标签页
let appId = ""; // 应用id
let group = ""; // 工作部门信息(在用户信息的工作部门上点击时获取)，默认为""代表左侧导航点击进入（非其它页面点击对应部门进入）
let applicationInfo = {}; // 应用信息
let breadCrumb = [{ name: "首页" }]; // 面包屑导航
let userInfo = {};
let path = ""; // 刷新请求路径

try {
  if (localStorage.token) {
    token = localStorage.token;
  }
} catch (e) {
  console.log(e);
}

try {
  if (localStorage.phoneNumber) {
    phoneNumber = localStorage.phoneNumber;
  }
} catch (e) {}

try {
  if (localStorage.appId) {
    appId = localStorage.appId;
  }
} catch (e) {}

try {
  if (localStorage.applicationInfo) {
    applicationInfo = JSON.parse(localStorage.applicationInfo);
  }
} catch (e) {}
export default {
  token,
  phoneNumber: phoneNumber,
  top: top,
  left,
  selectOSTree,
  homeOrg,
  tab,
  appId: appId,
  breadCrumb,
  userInfo,
  group,
  applicationInfo,
  path
};
