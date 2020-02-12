<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">账号锁定设置</div>
      <myButton btnType="info"
                @click="toedit"
                class="formed-header-btn">编辑</myButton>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <div class="formed-form-item">
          <span>锁定账号：</span>
          <span v-if="statusIcon">
            {{statusIcon.status?"已开启":"已关闭"}}
            <Icon :class="statusIcon.class"
                  :type="statusIcon.type" />
          </span>
        </div>
        <div class="formed-form-item">
          <span>账号锁定策略：</span>
          <span>
            <span class="large-font">{{formItem.timeRang}}</span>
            分钟内，身份认证连接失败
            <span class="large-font">{{formItem.failCount}}</span>
            次后，将账号锁定
            <span class="large-font">{{formItem.freezingTime}}</span>
            分钟。
          </span>
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
      statusIcon: null,
      formItem: {}
    };
  },
  mounted () {
    this.finddata();
  },
  methods: {
    toedit () {
      this.$router.push({ name: "LFEdit" });
    },
    finddata () {
      this.axios({
        method: "post",
        url: "/cipher/loginfail/list",
        data: {}
      })
        .then(res => {
          this.formItem = res.data;
          this.statusIcon = res.data.status === 1 ? icon.openIcon : icon.closeIcon;
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
.formed-form .formed-form-item > span {
  vertical-align: middle;
}
.large-font {
  color: #08142c;
  font-size: 24px;
}
</style>
