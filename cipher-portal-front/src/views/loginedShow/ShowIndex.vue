<template>
  <div>
    <div>
      <div class="logined-show">
        <div class="show-content">
          <div class="top">
            <div class="center-content clear">
              <div class="left">
                <img :src="companyIcon" />
                <span>{{companyTitle}}</span>
              </div>
              <div class="search">
                <Input type="text"
                       placeholder="请输入应用名称..."
                       v-model="searchText"
                       icon="md-close"
                       @on-click="clearInput"
                       @on-enter="inputEnter" />
              </div>
              <div class="right">
                <router-link to="/loginedShow"
                             tag="span">
                  <Icon type="md-home"
                        style="color:#fff"
                        size="28" />
                  <span class="index">首页</span>
                </router-link>
                <span class="inform">
                  <Icon type="md-person"
                        size="24" />
                  {{username}}
                  <ul class="menu">
                    <li v-for="item in menuList"
                        :key="item.index"
                        @click="selMenuList(item)">
                      <span>{{item.text}}</span>
                    </li>
                  </ul>
                </span>
              </div>
            </div>
          </div>
          <div class="content">
            <transition name="slide"
                        mode="out-in"
                        appear>
              <router-view :queryName="queryName"></router-view>
            </transition>
          </div>
        </div>
        <div class="footer">
          <div class="center-content">
            <img :src="logo" />
            <span>安全访问门户</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/loginedShow/index.js";
import axios from "axios";
import store from "@/store/index.js";
export default {
  data () {
    return {
      logo: require("../../assets/img/white-logo.png"),
      companyIcon: "",
      companyTitle: "",
      samlhtml: "",
      searchText: "",
      queryName: "",
      isMenuShow: false, // 下拉菜单是否显示
      menuList: [
        // { text: "主页", icon: "ios-home-outline", path: "/loginedShow" },
        { index: 1, text: "个人信息", icon: "ios-paper-outline", path: "/loginedShow/inform" },
        { index: 2, text: "修改密码", icon: "ios-lock-outline", path: "/loginedShow/changePwd" },
        // { index: 3, text: "下载SSO插件", icon: "ios-lock-outline", path: "/soft/kingdee.exe" },
        { index: 4, text: "注销", icon: "ios-remove-circle-outline", path: "/loginedShow/loginOut" }
      ],
      isLoginOut: false,
      username: "", // 账号
      isAdmin: 0, // 是否是管理员，0-是 1-不是
      portalList: [] // 应用列表
    };
  },
  beforeRouteEnter (to, from, next) {
    localStorage.setItem("authLogic", JSON.stringify(store.state));// 保存登录状态
    if (from.path !== "/skipAppError") { // 由报错页面重新跳转过来，直接进入本列表页
      // 流程请求，type表示进入方式：1-直接登录，2-cas应用进入，3-dsg应用进入
      axios({
        method: "post",
        data: {},
        url: "/cipher/user/flowsuccess"
      })
        .then(res => {
          let type = res.data.type;
          let code = res.data.return_code;
          if (type === "NORMAL" && code === 0) { // 直接登录且成功，直接进入列表页
            next();
          } else if ((type === "CAS" || type === "SAML" || type === "RDSG" || type === "AUTH2") && code === 0) { // 单应用进入且成功，跳转到相应的应用页
            window.location.href = res.data.serviceUrl;
            next(false);
          } else if (type === "RDSG" && code === 1111) { // dsg应用进入，且账号不存在 或 账号密码错误 进入账号配置页
            let data = res.data;
            next({ path: "/skipSubConfig", query: { appId: data.appId, subAccount: data.subAccount, subPwd: data.subPwd, appHost: data.appHost } });
          } else { // 其他错误，进入报错页面
            // console.log(res.data);
            // this.samlhtml = res.data;
            let errorMsg = res.data.return_msg;
            next({ path: "/skipAppError", query: { error: errorMsg } });
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    } else {
      next();
    }
  },
  created () {
    this.getBaseData();
  },
  methods: {
    getBaseData () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.getBaseInform
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.companyIcon = res.data.IconSettingsDomain.iconfont;
            this.companyTitle = res.data.IconSettingsDomain.title;
            this.username = res.data.username;
            this.isAdmin = res.data.isAdmin;
          } else {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    inputEnter () {
      this.queryName = this.searchText;
    },
    clearInput () {
      this.searchText = "";
      this.queryName = this.searchText;
    },
    /**
     * 选择菜单列表
     * @param {*Object 菜单列表信息} item
     * @author menglei 2019-10-08
     */
    selMenuList (item) {
      let path = item.path;
      if (item.index !== 3) {
        this.$router.push({ path: path });
      } else {
        location.href = path;
      }
    }
  }

};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/showList/common.less";
.logined-show {
  min-width: 1200px;
  position: relative;
  min-height: 100%;
}
.show-content {
  height: 100%;
  .top {
    height: @topHeight;
    line-height: @topHeight;
    background-color: @darkBlue;
    text-align: center;
    color: #fff;
    padding-left: 20px;
    > div > div {
      display: inline-block;
    }
    .left {
      position: relative;
      height: 60px;
      line-height: 60px;
      img {
        max-width: 80px;
        max-height: 30px;
        display: inline-block;
        vertical-align: middle;
        margin-top: -2px;
      }
      span {
        margin-left: 10px;
        font-size: 14px;
      }
    }
    .search {
      width: 268px;
      margin: auto;
    }
    .search /deep/ input {
      border-radius: 15px;
    }
    .right {
      > span {
        font-size: @fontSize2;
        margin-right: 15px;
        &:hover {
          cursor: pointer;
        }
        > i {
          display: inline-block;
          margin-right: 5px;
          margin-top: -5px;
        }
      }
      .inform {
        position: relative;
        display: inline-block;
        &:hover {
          .menu {
            .show();
          }
        }
      }
      .menu {
        .hide();

        font-size: 15px;
        color: #666 !important;
        width: 110px;
        background: rgba(255, 255, 255, 1);
        box-shadow: 0px 2px 4px 0px rgba(167, 167, 167, 1);
        color: @fontGrey;
        border-radius: 4px;
        position: absolute;
        left: 0;
        top: calc(~"@{topHeight} + 8px");
        z-index: 999;
        text-align: center;
        &::before {
          display: block;
          content: "";
          position: absolute;
          height: 10px;
          width: 100%;
          top: -8px;
          left: 0;
        }
        li {
          height: 40px;
          line-height: 40px;
          padding: 0 10px;
          a {
            color: #a7a7a7;
          }
          &:hover {
            background: rgba(210, 240, 246, 0.8);
            color: @skyBlue;
          }
        }
      }
    }
  }
  .content {
    height: calc(~"100% - @{topHeight}") !important;
    width: 100%;
  }
}
.footer {
  position: absolute;
  bottom: 0;
  left: 0;
  .wh(100%, @footerHeight) !important;
  line-height: @footerHeight;
  background-color: @darkBlue;
  text-align: right;
  color: #fff;
  img {
    .wh(24px, 24px);
    margin-right: 5px;
    position: relative;
    top: 6px;
  }
}
// 路由动画
.slide-enter-active {
  animation: slide-in 0.8s;
}
.slide-leave-active {
  transition: opacity 0.1s;
}
.slide-leave-to {
  opacity: 0;
}
@keyframes slide-in {
  0% {
    transform: translateX(10%);
    opacity: 0;
  }
  100% {
    transform: translateX(0);
    opacity: 1;
  }
}
</style>
