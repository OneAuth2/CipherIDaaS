<template>
  <div class="login-out">
    <div class="formsBody margin-center">
      <h3 class="title login-out-title">退出登录</h3>
      <button class="button"
              type="button"
              @click="cancel">取消</button>
      <button class="button"
              type="button"
              @click="confirm">确认</button>
    </div>
  </div>
</template>

<script>
import api from "@/api/loginedShow/index.js";
import { mapState, mapMutations } from "vuex";
export default {
  computed: {
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    // 取消注销
    cancel () {
      this.$router.push("/loginedShow");
    },
    // 确认注销账号
    confirm () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.loginOut
      })
        .then(res => {
          if (res.data.return_code === 0) {
            // let tempInfo = JSON.parse(localStorage.getItem("authLogic"));
            // tempInfo.authLogic.userInfo = {};
            // localStorage.setItem("authLogic", JSON.stringify(tempInfo)); // 保存userId到localStorage
            // this.changeAuthLogic(tempInfo.authLogic);
            this.authLogic.userInfo.userId = "";
            this.changeAuthLogic(this.authLogic);

            this.$Message.success("退出成功");
            this.$router.push("/");
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    ...mapMutations(["changeAuthLogic"])
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/showList/common.less";
.login-out {
  position: relative;
  width: 100%;
  height: 100%;
  .padding(120px, 0);
}
.login-out-title {
  margin-bottom: 40px;
  margin-top: 30px;
}
</style>
