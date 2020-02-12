<template>
  <div class="app-detail wrap">
    <div class="formed-header">
      <div class="formed-header-title">{{appInfoData.applicationName}}</div>
    </div>
    <myButton btnType="info"
              class="app-detail-change__btn"
              v-if="isShowEdit"
              @click="changeStatus">编辑</myButton>
    <myComplexTabs v-model="appDetail"
                   v-if="isGetData">
      <myPane label="基本信息"
              name="base">
        <AppBase ref="base"
                 v-if="isGetData"
                 :appInfoData="appInfoData"
                 @getData="getDataAgain"
                 @statusChange="getChildStatus" />
      </myPane>
      <myPane label="应用同步配置"
              v-if="isShowSynConfig"
              name="synConfig">
        <AppSynConfig ref="synConfig"
                      v-if="isGetData"
                      :appData="appData"
                      @getData="getDataAgain"
                      @statusChange="getChildStatus" />
      </myPane>
      <myPane label="子账号管理"
              name="subAccount">
        <AppSubAccount ref="subAccount"
                       v-if="isGetData"
                       :appInfoData="appInfoData" />
      </myPane>
    </myComplexTabs>
  </div>
</template>
<script>
import { mapState } from "vuex";
import AppBase from "./AppInfomation.vue";
import AppSynConfig from "./AppSynConfig.vue";
import AppSubAccount from "./AppSubAccount.vue";
export default {
  components: {
    AppBase,
    AppSynConfig,
    AppSubAccount
  },
  data () {
    return {
      appDetail: "base",
      componentStatus: { // 组件是否编辑状态
        isAppBaseEdit: false,
        isAppSynConfigEdit: false
      },
      isGetData: false, // 是否获取数据
      appData: {}, // app共有所有应用数据
      appInfoData: {} // app基本信息数据
    };
  },
  computed: {
    ...mapState(["appId"]),
    isShowSynConfig () { // 是否显示同步配置 类型为3或者4的时候显示
      return (this.appInfoData.applicationType === 3 || this.appInfoData.applicationType === 4);
    },
    isShowEdit () { // 是否显示修改按钮
      let isShow;
      switch (this.appDetail) {
        case "base":
          isShow = !this.componentStatus.isAppBaseEdit;
          break;
        case "synConfig":
          isShow = !this.componentStatus.isAppSynConfigEdit;
          break;
        case "subAccount":
          isShow = false;
          break;
      }
      return isShow;
    }
  },
  mounted () {
    this.getData();
  },
  methods: {
    /**
     * 点击修改时执行，调用子组件方法，进入编辑状态
     * @param {*void}
     * @author yezi 2019-09-10
     */
    changeStatus () {
      this.$refs[this.appDetail].editStatus();
    },
    /**
   * 子组件触发的函数，获取子组件状态，是修改还是显示状态
   * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
   * @author yezi 2019-09-10
   */
    getChildStatus (status) {
      if (status.name === "base") {
        this.componentStatus.isAppBaseEdit = status.isEdit;
      } else if (status.name === "synConfig") {
        this.componentStatus.isAppSynConfigEdit = status.isEdit;
      }
    },
    /**
     * 获取应用信息数据，三个组件共同调用的接口
     * @param {*void}
     * @author yezi 2019-09-29
     */
    getData () {
      this.axios({
        method: "post",
        url: "/cipher/app/synConfig",
        data: { id: this.appId }
      })
        .then(res => {
          this.appData = Object.assign({}, res.data);
          this.appInfoData = Object.assign({}, res.data.app);
          this.isGetData = true;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误",
            desc: error
          });
        });
    },
    /**
     * 子页面保存后触发重新获取数据
     * @param {*void}
     * @author yezi 2019-09-29
     */
    getDataAgain () {
      this.isGetData = false;
      this.getData();
    }
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
.app-detail-change__btn {
  position: absolute;
  right: 8px;
  top: 91px;
  transform: translateY(-50%);
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - 122px");
    background-color: #fff;
    .border-radius;
    > .pane {
      height: 100%;
      overflow: auto;
    }
  }
}
</style>
