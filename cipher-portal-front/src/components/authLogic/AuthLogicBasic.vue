<template>
  <div class="authLogicBody">
    <div class="header">
      <router-link to="/authLogic/login/staff/">
        <img class="logo"
             :src="iconfont">
      </router-link>
      <span class="title">{{logoTitle}}</span>
    </div>
    <div class="middle">
      <slot>
        中间内容
      </slot>
    </div>
    <div class="footer">
      {{copyRight}}
    </div>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  computed: {
    logoTitle () {
      return this.authLogic.portalConfig.leftTitle;
    },
    iconfont () {
      return this.authLogic.portalConfig.iconfont;
    },
    copyRight () {
      return this.authLogic.portalConfig.copyright;
    },
    ...mapState({ authLogic: "authLogic" })
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.authLogicBody {
  height: 100%;
  min-width: 1120px;
  min-height: 630px;
  color: #93c0cd;
  background-color: #fff;
  .header {
    text-align: left;
    padding: 20px 0 0 20px;
    font-size: 16px;
    .logo {
      vertical-align: middle;
      height: 30px;
      width: auto;
    }
    .title {
      color: @colorBase8;
      font-size: 20px;
      padding-left: 15px;
      vertical-align: middle;
    }
  }
  .middle {
    position: relative;
    height: calc(~"100% - 130px");
  }
  .footer {
    color: @colorBase3;
    font-size: @fontSize18;
    padding: 10px;
  }
}
</style>
