<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">添加Portal</div>
      <div class="formed-header-btn">
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <Steps :current="current"
               class="step">
          <Step title="基本配置"></Step>
          <Step title="网络配置"></Step>
          <Step title="页面配置"></Step>
        </Steps>
        <Base v-show="current===0"
              ref="childRules0"
              v-on:ListenChild="getBaseData" />
        <Ac v-show="current===1"
            ref="childRules1"
            v-on:ListenChild="getAcData" />
        <Page v-show="current===2"
              ref="childRules2"
              v-on:ListenChild="getPageData"
              :selected="baseData.portalType" />
      </div>
      <div class="formed-footer">
        <myButton @click="prev"
                  v-if="current!==0">上一步</myButton>
        <myButton btnType="info"
                  @click="next"
                  v-if="current!==2">下一步</myButton>
        <myButton btnType="info"
                  v-if="current===2"
                  @click="handleSubmit()">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
  </div>
</template>
<script>
import Base from "./BaseConfig.vue";
import Ac from "./AcConfig.vue";
import Page from "./PageConfig.vue";
export default {
  components: {
    Base,
    Ac,
    Page
  },
  data () {
    return {
      current: 0, // 当前步骤
      buttonSize: "large",
      baseData: {},
      acData: {},
      pageData: {}
    };
  },
  methods: {
    /**
     * iview步骤条组件，点击上一步触发，回到上一步
     * @param {*void}
     * @author yezi 2019-08-02
     */
    prev () {
      this.current -= 1;
    },
    /**
     * iview步骤条组件，点击下一步触发,回到下一步吗，并触发当前步骤的子组件方法
     * @param {*void}
     * @author yezi 2019-08-02
     */
    next () {
      let flag = this.$refs["childRules" + this.current].validateForm(); // 子组件表单验证，成功返回true并保存数据，失败返回false
      if (flag) { // flag为true，表单验证通过到下一步
        this.current += 1;
      }
    },
    /**
     * 点击取消触发，返回列表页
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.$router.push("/netAccessServicePortal");
    },
    /**
     * 基本配置子组件传递的数据
     * @param {*Object 子组件传递回来的数据} Data
     * @author yezi 2019-08-02
     */
    getBaseData (data) {
      this.baseData = data;
    },
    /**
     * 网络配置子组件传递的数据
     * @param {*Object 子组件传递回来的数据} Data
     * @author yezi 2019-08-02
     */
    getAcData (data) {
      this.acData = data;
    },
    /**
     * 页面配置子组件传递的数据
     * @param {*Object 子组件传递回来的数据} Data
     * @author yezi 2019-08-02
     */
    getPageData (data) {
      this.pageData = data;
    },
    /**
     * 最后一步点击保存触发子组件函数
     * @param {*void}
     * @author yezi 2019-08-02
     */
    handleSubmit () {
      this.$refs["childRules" + this.current].validateForm();
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
.step {
  padding: 30px 0 15px 15px;
  text-align: left;
}
/deep/ .ivu-form-item-content {
  text-align: left;
  max-height: 100%;
}
/deep/ .ivu-upload {
  height: 100%;
}
img {
  max-width: 100%;
  max-height: 100%;
}
</style>
