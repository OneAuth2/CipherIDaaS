<template>
  <div class="mail-auth wrap">
    <myButton btnType="info"
              class="mail-auth-change__btn"
              v-if="isShowEdit"
              @click="changeStatus">编辑</myButton>
    <myComplexTabs v-model="mailAuth"
                   class="mail-auth-tab">
      <myPane label="基本配置"
              name="base">
        <MailBase ref="base"
                  @statusChange="getChildStatus" />
      </myPane>
      <myPane label="随机码配置"
              name="randomCode">
        <MailRandomCode ref="randomCode"
                        @statusChange="getChildStatus" />
      </myPane>
      <myPane label="开通账号通知"
              name="setUpAccount">
        <MailSetUp ref="setUpAccount"
                   @statusChange="getChildStatus" />
      </myPane>
      <myPane label="下发种子秘钥"
              name="sentDownKey">
        <MailSentDownKey ref="sentDownKey"
                         @statusChange="getChildStatus" />
      </myPane>
    </myComplexTabs>
  </div>
</template>

<script>
import MailBase from "./MailBase.vue";
import MailRandomCode from "./MailRandomCode.vue";
import MailSetUp from "./MailSetUp.vue";
import MailSentDownKey from "./MailSentDownKey.vue";

export default {
  components: {
    MailBase,
    MailRandomCode,
    MailSetUp,
    MailSentDownKey
  },
  data () {
    return {
      mailAuth: "base",
      componentStatus: { // 组件是否编辑状态
        isBaseEdit: false,
        isRandomCodeEdit: false,
        isSetUpAccountEdit: false,
        isSentDownKeyEdit: false
      }
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      let isShow;
      if (this.mailAuth === "base") { isShow = !this.componentStatus.isBaseEdit; }
      if (this.mailAuth === "randomCode") { isShow = !this.componentStatus.isRandomCodeEdit; }
      if (this.mailAuth === "setUpAccount") { isShow = !this.componentStatus.isSetUpAccountEdit; }
      if (this.mailAuth === "sentDownKey") { isShow = !this.componentStatus.isSetDownKeyEdit; }
      return isShow;
    }
  },
  methods: {
    /**
     * 点击修改时执行，调用子组件方法，进入编辑状态
     * @param {*void}
     * @author yezi 2019-09-10
     */
    changeStatus () {
      this.$refs[this.mailAuth].editStatus();
    },
    /**
   * 子组件触发的函数，获取子组件状态，是修改还是显示状态
   * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
   * @author yezi 2019-09-10
   */
    getChildStatus (status) {
      if (status.name === "base") {
        this.componentStatus.isBaseEdit = status.isEdit;
      } else if (status.name === "randomCode") {
        this.componentStatus.isRandomCodeEdit = status.isEdit;
      } else if (status.name === "setUpAccount") {
        this.componentStatus.isSetUpAccountEdit = status.isEdit;
      } else if (status.name === "sentDownKey") {
        this.componentStatus.isSentDownKeyEdit = status.isEdit;
      }
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.mail-auth-change__btn {
  position: absolute;
  right: @customPadding;
  top: @headerHeight / 2;
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
    }
  }
}
</style>
