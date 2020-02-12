<template>
  <div class="header clearfix">
    <div class="header-left">
      <span class="header-circle-bg">
        <span class="header-circle-bg"><img src="@/assets/login-logo.png"></span>
      </span>
      <span class="header-title">CipherChina Technology</span>
    </div>
    <div class="header-right">
      <Dropdown placement="top"
                class="dropdown-header">
        <a href="javascript:void(0)">
          <img src="@/assets/person.png"
               class="admin-img">
          <span v-text="userName"
                class="header-title"></span>
        </a>
        <DropdownMenu slot="list"
                      class="dropdownList">
          <DropdownItem>
            <span @click="quit">
              <img src="@/assets/logout.png"
                   class="logout-img">
              <span>退出登录</span>
            </span>
          </DropdownItem>
        </DropdownMenu>
      </Dropdown>
    </div>
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
import api from "@/api/login/index.js";
import { mapState, mapMutations } from "vuex";

export default {
  name: "TopIndex",
  data () {
    return {
      userName: ""
    };
  },
  computed: {
    ...mapState(["token"])
  },
  created () {
    this.init();
  },
  methods: {
    quit () {
      this.axios({
        method: "post",
        url: api.loginOut,
        data: {}
      })
        .then(res => {
          if (res.data.return_code === api.returnOk) {
            // 退出登录处理逻辑
            this.changeToken(null);
            // 重置Tab项为home
            let homeTab = this.$store.state.tab[0];
            homeTab.activeId = 0;
            this.$store.commit("changeTab", [homeTab]);
            // 重置面包屑为home
            this.$store.commit("changeBreadCrumb", [{
              href: "",
              name: "首页"
            }]);
            this.$router.push({ path: "/login" });
          } else {
            this.failModal(res.data.return_msg);
          }
        })
        .catch(error => {
          this.axios.error.handlingErrors(error);
        });
    },
    // 页面加载时先把ticket写入后端session，成功时再调用获取用户信息接口
    init () {
      this.axios({
        method: "post",
        url: "/cipher/console/setSession",
        data: {}
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.getUser();
          }
        })
        .catch(error => {
          console.log(error);
          // this.$Notice.warning({
          //   title: "网络未知错误！",
          //   desc: "请查看网络"
          // });
        });
    },
    // 获取用户信息
    getUser () {
      this.axios({
        method: "post",
        url: "/cipher/welcome/userInfo",
        data: {}
      })
        .then(res => {
          if (res.status === api.statusOk) {
            let data = res.data;
            this.noGroupCount = data.noGroupCount;
            this.lockCount = data.lockCount;
            this.noLoginCount = data.noLoginCount;
            this.userName = data.userName;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    failModal (errorMsg) {
      this.$Modal.error({
        title: errorMsg,
        content: "请稍后重试",
        width: modal.simpleModal.width
      });
    },
    ...mapMutations(["changeToken"])
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
/* 头部 */
.header {
  background-color: @colorDarkPrimary;
  font-size: 16px;
  height: 60px;
  line-height: 60px;
}
.header-title {
  color: rgba(255, 255, 255, 1);
  font-weight: 500;
}
.header-left {
  float: left;
  height: 60px;
  line-height: 60px;
  margin-left: 40px;
  > span {
    display: inline-block;
  }
}
.header-circle-bg {
  background-color: #fff;
  border-radius: 50%;
  width: 32px;
  height: 32px;
  vertical-align: middle;
  margin-right: 8px;
  display: flex;
  justify-content: center; /* 水平居中 */
  align-items: center; /* 垂直居中 */
  img {
    height: 20px;
  }
}
.header-right {
  margin-right: 40px;
  display: flex;
  justify-content: flex-end;
  align-items: center;
  color: rgba(255, 255, 255, 0.45);
  height: 100%;
}

.dropdownList {
  text-align: center;
  margin: 0px;
}
.header ul.dropdownList a {
  font-size: 12px !important;
  color: #515a6e;
  display: block;
}
.admin-img {
  width: 32px;
  margin-right: 8px;
}
.logout-img {
  width: 24px;
  margin-right: 8px;
  vertical-align: middle;
  position: relative;
  top: -1px;
}
.dropdown-header {
  position: relative;
  a {
    display: flex;
    justify-content: center; /* 水平居中 */
    align-items: center; /* 垂直居中 */
  }
  span {
    font-weight: 500;
    font-size: 14px;
  }
  /deep/ .ivu-select-dropdown {
    width: 100%;
    min-width: 120px;
  }
}
</style>
