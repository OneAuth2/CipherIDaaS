<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <img :src="qrcodeObj.qrcode"
             alt="">
        <p class="title">
          扫二维码登录
        </p>
        <div v-if="errorNoticeObj.errorNotice"
             :class="errorNoticeObj">
          二次认证账号不匹配请重试
        </div>
        <div class="scanLogin scan">
          <span class="logo cipher">
          </span>
          <span class="refresh">
            <Icon type="ios-refresh" />
            <span @click="refresh">刷新</span>
          </span>
        </div>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import skipPathUtil from "@/views/authLogic/skipLogic.js";
import TYPE from "@/views/authLogic/constantType/index.js";
import apiLogin from "@/api/authLogic/login.js";
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import { mapState } from "vuex";
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
    this.getScanCode();
  },
  computed: {
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    // 获取需要扫码信息
    getScanCode () {
      clearInterval(this.interval);
      this.axios({
        method: "post",
        data: {},
        url: apiLogin.saiFuQrcode
      })
        .then(response => {
          if (response.data.return_code === 0
          ) {
            this.qrcodeObj.qrcode = response.data.qrcode;
            this.qrcodeObj.uuid = response.data.uuid;
            // 轮询获取扫码状态
            this.interval = setInterval(() => {
              this.pollingGetScanState();
            }, 1000);
          } else {
            console.error(response);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 轮询获取扫码状态
    pollingGetScanState () {
      let params = { uuid: this.qrcodeObj.uuid };
      this.axios({
        method: "post",
        data: params,
        url: apiLogin.saifuSecondPolling
      })
        .then(response => {
          let scanState = response.data.return_code;
          if (scanState === 0
          ) { // 1.扫码成功
            clearInterval(this.interval);
            let skipPath = skipPathUtil.skipNextPage(this.authLogic.loginInfo, this.authLogic.secondLoginInfo, 5);
            this.$router.push({ path: skipPath });
          } else if (scanState === TYPE.SCAN.UNDERWAY
          ) { // 2.等待扫码
            // 轮询获取扫码状态
          } else { // 3.扫码失败
            clearInterval(this.interval);
            this.errorNoticeObj.errorNotice = true;
            console.error(response);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 刷新
    refresh () {
      this.getScanCode();
    }
  },
  beforeRouteLeave (to, from, next) {
    clearInterval(this.interval); // 定时器一定要清除
    next();
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  img {
    margin-top: 60px;
  }
  .title {
    font-size: 14px;
    padding: 10px 0;
  }
  .errorNotice {
    transition: all 0.3s ease-in-out;
    visibility: visible;
    color: red;
    padding: 10px 0;
  }
  .scan {
    margin-bottom: 30px;
    .refresh {
      color: @colorBase1;
      i {
        font-size: 35px;
      }
      span {
        font-size: 16px;
      }
      &:hover {
        .mouseHover;
      }
    }
  }
}
</style>
