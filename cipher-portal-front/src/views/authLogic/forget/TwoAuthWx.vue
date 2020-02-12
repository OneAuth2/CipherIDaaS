<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <div class="wx">
          <div id="wx_qrcode"></div>
          <div v-if="errorNoticeObj.errorNotice"
               :class="errorNoticeObj">
            认证失败请重试
          </div>
        </div>
        <div class="scanLogin scan">
          <span class="logo wx">
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
import wxUtil from "@/views/authLogic/wx.js";
import { mapState, mapMutations } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  data () {
    return {
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
      if (this.$route.query.wxRedirect) {
        this.getWxScanCode();
      } else {
        this.getWxId();
      }
    },
    // 企业微信扫码成功,获取企业微信扫码信息
    getWxScanCode () {
      let code = this.$route.query.code;
      // 防止企业微信跳转时，userId保存失败
      this.authLogic.userInfo.userId = this.$route.query.userId;
      this.changeAuthLogic(this.authLogic);
      let params = { code: code };
      this.axios({
        method: "post",
        data: params,
        url: apiLogin.weixinSecondAuth
      })
        .then(response => {
          if (response.data.return_code === 0) {
            let skipPath = skipPathUtil.fogetPWSkipNextPage(this.authLogic.forgetPwdFlow, 6);
            this.$router.push({ path: skipPath });
          } else {
            console.error(response);
            this.errorNoticeObj.errorNotice = true;
          }
        }).catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取企业微信id和二维码
    getWxId () {
      this.axios({
        method: "post",
        data: {},
        url: apiLogin.getWxId
      })
        .then(response => {
          if (response.data.return_code === 0
          ) {
            let data = response.data;
            wxUtil.initGetWx("/authLogic/forgetPW/twoAuthWx", this.$store, data);
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
    color: red;
    padding: 10px 0;
    position: absolute;
    width: 100%;
    top: 0;
  }
  .scan {
    margin-bottom: 30px;
  }
}
.wx {
  margin-top: 60px;
  position: relative;
  #wx_qrcode {
    height: 300px;
  }
}
</style>
