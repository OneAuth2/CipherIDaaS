<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">异常登录设置</div>
      <myButton btnType="info"
                @click="toedit"
                class="formed-header-btn">编辑</myButton>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <div class="formed-form-item">
          <span>异地登录验证：</span>
          <span v-if="statusIcon">
            {{statusIcon.status?"已开启":"已关闭"}}
            <Icon :class="statusIcon.class"
                  :type="statusIcon.type" />
          </span>
        </div>
        <div class="formed-form-item">
          <span>异地登录验证方式：</span>
          <span>{{isemailormessages}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import icon from "@/util/icon.js";
export default {
  data () {
    return {
      isemailormessages: "",
      statusIcon: null
    };
  },
  mounted () {
    this.finddata();
  },
  methods: {
    toedit () {
      this.$router.push({ name: "ALEdit" });
    },
    // 初始化数据
    finddata () {
      this.axios({
        method: "post",
        url: "/cipher/exceptionLogin/preList",
        data: {}
      })
        .then(res => {
          this.statusIcon = res.data.exceptionLogin.status === 1 ? icon.openIcon : icon.closeIcon;
          this.isemailormessages = res.data.exceptionLogin.type === 1 ? "邮件随机码" : "短信随机码";
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
</style>
