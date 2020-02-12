<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <div id="dingding">
        </div>
        <div v-if="errorNoticeObj.errorNotice"
             :class="errorNoticeObj">
          二次认证账号不匹配请重试
        </div>
        <div class="scanLogin scan">
          <span class="logo dingding">
          </span>
        </div>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import skipPathUtil from "@/views/authLogic/skipLogic.js";
import apiLogin from "@/api/authLogic/login.js";
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import dingdingUtil from "@/views/authLogic/dingding.js";
import { mapState, mapMutations } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  data () {
    return {
      dingdingRedirect: "false", // true钉钉扫码成功
      errorNoticeObj: { errorNotice: false },
      qrcodeObj: {
        qrcode: "", // 二维码的地址
        uuid: ""
      },
      interval: null
    };
  },
  mounted () {
    this.init();
  },
  computed: {
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    init () {
      this.dingdingRedirect = this.$route.query.dingdingRedirect;
      if (this.dingdingRedirect) {
        this.getDingdingScanCode();
      } else {
        this.getDingTalkId();
      }
    },
    // 钉钉扫码成功,获取钉钉扫码信息
    getDingdingScanCode () {
      let code = this.$route.query.code;
      // 防止dingding跳转时，userId保存失败
      this.authLogic.userInfo.userId = this.$route.query.userId;
      this.changeAuthLogic(this.authLogic);
      let params = { code: code };
      this.axios({
        method: "post",
        data: params,
        url: apiLogin.dingTalkSecondAuth
      })
        .then(response => {
          if (response.data.return_code === 0
          ) {
            let skipPath = skipPathUtil.fogetPWSkipNextPage(this.authLogic.forgetPwdFlow, 4);
            this.$router.push({ path: skipPath });
          } else {
            console.error(response);
          }
        }).catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取钉钉id
    getDingTalkId () {
      this.axios({
        method: "post",
        data: {},
        url: apiLogin.getDingTalkId
      })
        .then(response => {
          if (response.data.return_code === 0
          ) {
            let dingTalkData = response.data;
            this.authLogic.userInfo.dingTalkScanId = dingTalkData.dingTalkScanId;
            this.changeAuthLogic(this.authLogic);
            dingdingUtil.initGetDingding("/authLogic/forgetPW/twoAuthDd", this.$store, dingTalkData);
          } else {
            console.error(response);
          }
        }).catch(function (error) {
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
  #dingding {
    margin-top: 50px;
  }
  img {
    margin-top: 60px;
  }
  .title {
    font-size: 24px;
    padding: 10px 0;
  }
  .errorNotice {
    transition: all 0.3s ease-in-out;
    visibility: visible;
    color: @colorError;
    padding: 10px 0;
  }
  .scan {
    margin-bottom: 30px;
  }
}
</style>
