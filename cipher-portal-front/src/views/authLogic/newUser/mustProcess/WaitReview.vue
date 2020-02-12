<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <img src="@/assets/img/authLogic/logo.png"
             alt="">
        <div class="titleTop titleLevel1">{{title}}</div>
        <div class="error">{{msg}}</div>
        <div class="titleLevel1"
             v-if="msg===''">欢迎</div>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import apiBinding from "@/api/authLogic/binding.js";
import TYPE from "@/views/authLogic/constantType/index.js";
import { mapState, mapMutations } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  data () {
    return {
      msg: "",
      bindApiObj: {
        bindApi: "",
        params: {}
      } // 要绑定api对象
    };
  },
  computed: {
    scanWay () {
      return this.authLogic.scanWay;
    },
    title () {
      return this.authLogic.portalConfig.rightTitle;
    },
    ...mapState({ authLogic: "authLogic" })
  },
  created () {
    this.getBindApi();
    this.binding();
  },
  methods: {
    // 获取要绑定api
    getBindApi () {
      let userInfo = this.authLogic.userInfo;
      switch (this.scanWay) {
        case TYPE.CIPHER:
          this.bindApiObj.bindApi = apiBinding.cipherBinding;
          this.bindApiObj.params.userId = userInfo.userId;
          this.bindApiObj.params.saiFuId = this.authLogic.saiFuInfo.uuid;
          break;
        case TYPE.DABAI:
          this.bindApiObj.bindApi = apiBinding.daBaiBinding;
          this.bindApiObj.params.userId = userInfo.userId;
          this.bindApiObj.params.daBaiId = this.authLogic.daBaiInfo.id_num;
          break;
        case TYPE.DINGDING:
          let dingTalkInfo = this.authLogic.dingTalkInfo;
          this.bindApiObj.bindApi = apiBinding.dingTalkBinding;
          this.bindApiObj.params.userId = userInfo.userId;
          this.bindApiObj.params.unionId = dingTalkInfo.unionId;
          this.bindApiObj.params.openId = dingTalkInfo.openId;
          this.bindApiObj.params.dingTalkUserId = dingTalkInfo.userId;
          break;
        case TYPE.WX:
          this.bindApiObj.bindApi = apiBinding.wxBinding;
          this.bindApiObj.params.wxUserId = this.authLogic.wxInfo.userid;
          break;
        default:
          break;
      }
    },
    // 执行绑定
    binding () {
      let params = this.bindApiObj.params;
      this.axios({
        method: "post",
        data: params,
        url: this.bindApiObj.bindApi
      })
        .then(response => {
          let skipPath = "";
          let data = response.data;
          if (
            data.return_code === 0
          ) { // 绑定成功
            let loginInfo = {};
            loginInfo.userId = data.userId;
            loginInfo.firstLogin = data.firstLogin;
            loginInfo.updatePwd = data.updatePwd;
            this.authLogic.loginInfo = loginInfo;
            this.changeAuthLogic(this.authLogic);
            setTimeout(() => {
              if (data.updatePwd === 0 && data.firstLogin === 0) {
                skipPath = "/authLogic/setNewPassWord";
              } else {
                skipPath = "/loginedShow";
              }
              this.$router.push(skipPath);
            }, 1000);
          } else {
            let errorMsg = response.data.return_msg;
            this.msg = errorMsg;
            console.error(errorMsg);
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
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  img {
    width: 90px;
    height: 90px;
    margin: 100px 0 40px 0;
  }
  .error {
    position: relative;
    top: -30px;
    color: @colorError;
    font-size: 14px;
  }
  .titleTop {
    padding-bottom: 70px;
  }
}
</style>
