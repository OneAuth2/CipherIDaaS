<template>
<!-- 发送验证码，包含手机和邮箱两种方式 -->
  <div>
    <a
      href="#"
      class="sendcode"
      :class="{disabled: !this.canClick}"
      @click.prevent="countDown"
    >{{content}}</a>
  </div>
</template>

<script>

export default {
  name: "SendCode",
  data () {
    return {
      content: "发送验证码", // 按钮里显示的内容
      totalTime: 60, // 记录具体倒计时时间
      canClick: true // 添加canClick
    };
  },
  props: {
    verifyCode: {
      type: Object
    }
  },
  methods: {
    countDown () {
      if (!this.canClick) return;
      this.canClick = false;
      this.content = this.totalTime + "s后重新发送";
      let clock = window.setInterval(() => {
        this.totalTime--;
        this.content = this.totalTime + "s后重新发送";
        if (this.totalTime < 0) {
          window.clearInterval(clock);
          this.content = "重新发送验证码";
          this.totalTime = 60;
          this.canClick = true; // 这里重新开启
        }
      }, 1000);
      this.sendVerifyCode();
    },
    sendVerifyCode () {
      let _this = this;
      let entity = this.verifyCode.entity;
      let sendWay = this.verifyCode.sendWay;
      let params =
        sendWay === "phone" ? { phoneNumber: entity } : { mail: entity };
      this.axios({
        method: "post",
        data: params,
        url: this.verifyCode.url
      })
        .then(function (response) {
          let data = response.data;
          // 成功跳转
          if (data.code === 0) {
            // _this.$Message.success("send verify code success!");
            console.log("send verify code success!");
          } else {
            throw data.msg;
          }
        })
        .catch(function (error) {
          if (!error) {
            error = "网络有问题，请稍后重试";
          }
          _this.$Message.error(error);
        });
    }
  }
};
</script>

<style scoped>
.right {
  position: absolute;
  display: inline-block;
  right: -130px;
  top: -30px;
}
</style>
