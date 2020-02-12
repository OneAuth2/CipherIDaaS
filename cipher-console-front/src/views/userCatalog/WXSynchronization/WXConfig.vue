<template>
  <div class="ding-config wrap">
    <myButton btnType="info"
              class="ding-config-change__btn"
              v-if="isShowEdit"
              @click="changeStatus">编辑</myButton>
    <myComplexTabs v-model="currentTab"
                   :animated="false">
      <myPane label="基本配置"
              name="base">
        <BaseSet ref="base"
                 @statusChange="getChildStatus" />
      </myPane>
      <myPane label="属性同步配置"
              name="property">
        <PropertySet ref="property"
                     @statusChange="getChildStatus" />
      </myPane>
      <myPane label="账号匹配规则"
              name="account">
        <AccountMatch ref="account"
                      @statusChange="getChildStatus" />
      </myPane>
    </myComplexTabs>
  </div>
</template>

<script>
import BaseSet from "./WXConfig/base.vue";
import PropertySet from "./WXConfig/property.vue";
import AccountMatch from "./WXConfig/account.vue";
export default {
  components: {
    BaseSet,
    PropertySet,
    AccountMatch
  },
  data () {
    return {
      currentTab: "base",
      componentStatus: { // 组件是否编辑状态
        isDingBaseEdit: false,
        isDingPropertyEdit: false,
        isDingAccountMatchEdit: false
      }
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      let isShow;
      switch (this.currentTab) {
        case "base":
          isShow = !this.componentStatus.isDingBaseEdit;
          break;
        case "property":
          isShow = !this.componentStatus.isDingPropertyEdit;
          break;
        case "account":
          isShow = !this.componentStatus.isDingAccountMatchEdit;
          break;
      }
      return isShow;
    }
  },
  methods: {
    /**
    * 点击修改时执行，调用子组件方法，进入编辑状态
    * @param {*void}
    * @author yezi 2019-10-09
    */
    changeStatus () {
      this.$refs[this.currentTab].editStatus();
    },
    /**
   * 子组件触发的函数，获取子组件状态，是修改还是显示状态
   * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
   * @author yezi 2019-10-09
   */
    getChildStatus (status) {
      if (status.name === "base") {
        this.componentStatus.isDingBaseEdit = status.isEdit;
      } else if (status.name === "property") {
        this.componentStatus.isDingPropertyEdit = status.isEdit;
      } else {
        this.componentStatus.isDingAccountMatchEdit = status.isEdit;
      }
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.ding-config-change__btn {
  position: absolute;
  right: 8px;
  top: 31px;
  transform: translateY(-50%);
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - 61px");
    background-color: #fff;
    .border-radius;
    > .pane {
      height: 100%;
      overflow: auto;
    }
  }
}
</style>
