<template>
  <div>
    <div class="body">
      <p class="titleLevel1 loginHomeTitle">{{title}}</p>
      <!-- 账号密码登录 -->
      <Form v-if="accountFlag"
            ref="formCustom"
            key="accountNumber"
            :model="formCustom"
            :rules="ruleCustom">
        <FormItem prop="accountNumber">
          <Input v-model="formCustom.accountNumber"
                 placeholder="账号，手机号或者邮箱"></Input>
        </FormItem>
        <FormItem prop="pwd"
                  class="passwordVerifyCode">
          <Input type="password"
                 v-model="formCustom.pwd"
                 placeholder="密码"
                 @on-enter="handleSubmitAccount('formCustom')"></Input>
          <div class="verifyMode">
            <span class="errorMsg"
                  :class="{'only-error':authLogic.modes.otherAuthNum}">{{errorMsgAccount}}</span>
            <span class="accountAuth"
                  v-if="!authLogic.modes.otherAuthNum">
              <span @click="switchLoginWay(false)">手机随机码登录</span>
            </span>
          </div>
        </FormItem>
        <FormItem class="login">
          <Button type="primary"
                  @click="handleSubmitAccount('formCustom')">登录</Button>
        </FormItem>
      </Form>
      <!-- 手机验证码登录 -->
      <Form v-else
            ref="formCustomVerify"
            key="verifyCode"
            :model="formCustomVerify"
            :rules="ruleCustomVerify">
        <FormItem prop="phoneNumber">
          <Input v-model="formCustomVerify.phoneNumber"
                 placeholder="手机号"></Input>
        </FormItem>
        <div class="code passwordVerifyCode">
          <div class="inputCode"
               :class="{'hiddenError':isHiddenError}">
            <FormItem prop="verifyCode">
              <Input v-model="formCustomVerify.verifyCode"
                     placeholder="验证码"></Input>
            </FormItem>
          </div>
          <div class="getCode">
            <ButtonCode @click="getCode"
                        ref="timerbtn"></ButtonCode>
          </div>
          <div class="verifyMode">
            <span class="errorMsg">{{errorMsgVerify}}</span>
            <span class="accountAuth"
                  v-if="!authLogic.modes.otherAuthNum">
              <span @click="switchLoginWay(true)">账号密码登录</span>
            </span>
          </div>
        </div>
        <FormItem class="login">
          <Button type="primary"
                  @click="handleSubmitVerify('formCustomVerify')">登录</Button>
        </FormItem>
      </Form>
      <p class="otherLoginText"
         v-if="logoLength!==0">使用其它方式登录</p>
      <div class="otherLogin">
        <template v-for="item in logoItems">
          <span :key="item.index"
                v-if="item.show"
                @mouseenter="mouseEnter(item.index)"
                @mouseleave="mouseLeave"
                :class="setClass(item.index,item)"
                @click="scan(item)">
          </span>
        </template>
      </div>
      <div class="regist"
           v-if="isRegist">
        <router-link to="/authLogic/newUserRegist/accountRegist">
          <span class="select">注册</span>
        </router-link>
      </div>
      <div class="forget"
           v-if="isForget"><span class="select"
              @click="getForgetPasswordFlow">忘记密码</span></div>
    </div>
  </div>
</template>
<script>
import api from "@/api/authLogic/index.js";
import apiLogin from "@/api/authLogic/login.js";
import apiForget from "@/api/authLogic/fogetPassword.js";
import skipPathUtil from "@/views/authLogic/skipLogic.js";
import verify from "@/util/verify.js";
import util from "@/views/authLogic/util/index.js";
import { mapState, mapMutations } from "vuex";
export default {
  data () {
    // 账号校验
    const validateAccount = (rule, value, callback) => {
      this.errorMsgAccount = "";
      if (value.length === 0 || value.length > 50) {
        callback(new Error("请输入不超过50位账号"));
      }
      let regx = verify.loginAccount;
      if (!regx.test(value)) {
        callback(new Error("请输入字符（英文不区分大小写）"));
      }
      callback();
    };
    // 密码校验
    const validatePass = (rule, value, callback) => {
      this.errorMsgAccount = "";
      if (value.length === 0 || value.length < 6) {
        callback(new Error("请输入不少于6位密码"));
      } else if (value.length > 30) {
        callback(new Error("请输入不超过30位密码"));
      }
      let regx = verify.accountNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入字符（英文不区分大小写）"));
      }
      callback();
    };
    // 验证码校验
    const validateVerifyCode = (rule, value, callback) => {
      this.errorMsgVerify = "";
      this.isHiddenError = false;
      if (!verify.verifyCode.test(value)) {
        callback(new Error("请输入6位验证码"));
      }
      let regx = verify.accountNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入字符（英文不区分大小写）"));
      }
      callback();
    };
    // 手机号校验
    const validatePhoneNumber = (rule, value, callback) => {
      this.errorMsgVerify = "";
      let regx = verify.phoneNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入正确的手机号"));
      }
      callback();
    };
    return {
      errorMsgAccount: "", // 账户密码方式后端接口返回错误信息
      errorMsgVerify: "", // 手机验证码方式后端接口返回错误信息
      isHiddenError: false, // 验证码input验证自带信息是否显示
      accountFlag: true, // true账号登录，false手机随机码登录
      // 账号密码登录
      formCustom: {
        accountNumber: "",
        pwd: "" // 密码
      },
      ruleCustom: {
        accountNumber: [
          { validator: validateAccount, trigger: "blur" }
        ],
        pwd: [
          { validator: validatePass, trigger: "blur" }
        ]
      },
      // 手机验证码登录
      formCustomVerify: {
        phoneNumber: "",
        verifyCode: "" // 验证码
      },
      ruleCustomVerify: {
        phoneNumber: [
          { validator: validatePhoneNumber, trigger: "blur" }
        ],
        verifyCode: [
          { validator: validateVerifyCode, trigger: "blur" }
        ]
      },
      isActive: false
    };
  },
  computed: {
    title () {
      return this.authLogic.portalConfig.rightTitle;
    },
    logoItems () {
      return util.logoItems(this.authLogic.modes);
    },
    logoLength () { // 选择扫码方式的个数
      let array = util.logoItems(this.authLogic.modes);
      let temp = array.filter(function (elem) {
        return elem.show === true;
      });
      return temp.length;
    },
    isRegist () {
      return this.authLogic.registMode.regist === 0;
    },
    isForget () {
      return this.authLogic.registMode.forget === 0;
    },
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    // 切换登录方式：1.账号密码登录;2.手机随机码登录
    switchLoginWay (flag) {
      this.accountFlag = flag;
      // this.handleReset("formCustom");
      // this.handleReset("formCustomVerify");
    },
    // 账号密码登录提交
    handleSubmitAccount (name) {
      this.$refs[name].validate((valid) => {
        if (!valid) {
          return;
        }
        let params = {
          accountNumber: this.formCustom.accountNumber,
          pwd: this.formCustom.pwd
        };
        this.axios({
          method: "post",
          data: params,
          url: apiLogin.pwdLoginChecked
        })
          .then(response => {
            if (
              response.data.return_code === 0
            ) {
              // 清除错误信息
              this.errorMsgAccount = "";
              this.loginSkip(response);
            } else {
              let errorMsg = response.data.return_msg;
              this.errorMsgAccount = errorMsg;
              console.error(errorMsg);
            }
          })
          .catch(function (error) {
            this.axios.error.handlingErrors(error);
          });
      });
    },
    // 手机验证码登录提交
    handleSubmitVerify (name) {
      this.$refs[name].validate((valid) => {
        if (!valid) {
          return;
        }
        let params = {
          phoneNumber: this.formCustomVerify.phoneNumber,
          code: this.formCustomVerify.verifyCode
        };
        this.axios({
          method: "post",
          data: params,
          url: apiLogin.phoneCodeLogin
        })
          .then(response => {
            if (
              response.data.return_code === 0
            ) {
              this.errorMsgVerify = ""; // 清除错误信息
              this.loginSkip(response);
            } else {
              let errorMsg = response.data.return_msg;
              this.errorMsgVerify = errorMsg;
              console.error(errorMsg);
            }
          })
          .catch(function (error) {
            this.axios.error.handlingErrors(error);
          });
      });
    },
    handleReset (name) {
      this.$refs[name].resetFields();
    },
    // 扫码方式
    scan (scanWay) {
      let obj = { path: "/authLogic/login/scan", query: { scanWay: scanWay.logo } };
      this.$router.push(obj);
    },
    // 鼠标进入
    mouseEnter (index) {
      this.isActive = index;
    },
    // 鼠标离开
    mouseLeave () {
      this.isActive = null;
    },
    setClass (index, item) {
      return { logo: true, [item.logo]: true, active: index === this.isActive };
    },
    // 获取验证码
    getCode () {
      this.isHiddenError = true;
      let regx = verify.phoneNumber;
      if (!regx.test(this.formCustomVerify.phoneNumber)) {
        this.errorMsgVerify = "请输入正确手机号";
        return;
      }
      this.errorMsgVerify = "";
      this.$refs.timerbtn.setDisabled(true);
      let params = {
        phoneNumber: this.formCustomVerify.phoneNumber
      };
      this.axios({
        method: "post",
        data: params,
        url: api.sendPhoneCode
      })
        .then(response => {
          if (
            response.data.return_code !== 0
          ) {
            this.$refs.timerbtn.stop(); // 停止倒计时
            let errorMsg = response.data.return_msg;
            this.errorMsgVerify = errorMsg;
            console.error(errorMsg);
          } else {
            this.$refs.timerbtn.start(); // 启动倒计时
          }
        })
        .catch(function (error) {
          this.$refs.timerbtn.stop(); // 停止倒计时
          this.axios.error.handlingErrors(error);
        });
      // });
    },
    // 登录跳转
    loginSkip (response) {
      let data = response.data;
      let temp = {
        loginInfo: data.loginInfo,
        secondLoginInfo: data.secondLoginInfo
      };
      temp.userInfo = {};
      temp.userInfo.userId = data.loginInfo.userId;
      let authLogic = Object.assign({}, this.authLogic, temp);
      this.changeAuthLogic(authLogic);
      let getSkipPath = skipPathUtil.getSkipPath(authLogic.loginInfo, authLogic.secondLoginInfo);
      this.$router.push(getSkipPath);
    },
    // 获取忘记密码流程信息
    getForgetPasswordFlow () {
      this.axios({
        method: "post",
        data: {},
        url: apiForget.forgetPwdFlow
      })
        .then(response => {
          if (
            response.data.return_code === 0
          ) {
            this.forgetPasswordSkip(response);
          } else {
            let errorMsg = response.data.return_msg;
            this.errorMsgVerify = errorMsg;
            console.error(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 忘记密码跳转
    forgetPasswordSkip (response) {
      let temp = {
        forgetPwdFlow: response.data.forgetPwdFlow
      };
      let authLogic = Object.assign({}, this.authLogic, temp);
      this.changeAuthLogic(authLogic);
      let getForgetSkipPath = skipPathUtil.fogetPWSkipNextPage(authLogic.forgetPwdFlow, 1);
      this.$router.push(getForgetSkipPath);
    },
    ...mapMutations(["changeAuthLogic"])
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.body {
  .titleLevel1;
  border: @borderColor;
  // width: 40%;
  margin: auto;
  // min-width: 250px;
  width: 380px;
  border-radius: @borderRadius8;
  padding: 15px 40px 25px 40px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  .loginHomeTitle {
    margin-top: 15px;
    margin-bottom: 45px;
  }
  // .titleLevel3 {
  //   margin-bottom: 45px;
  // }
  .hiddenError /deep/ .ivu-form-item-error-tip {
    display: none;
  }
  .passwordVerifyCode {
    margin-bottom: 40px;
    .verifyMode {
      position: absolute;
      width: 100%;
      right: 0;
      // bottom: -28px;
      font-size: @fontSize14;
      & > span {
        width: 50%;
        display: inline-block;
      }
      .errorMsg {
        color: @colorError;
        font-size: 12px;
        text-align: left;
      }
      .only-error {
        width: 100% !important;
      }
      .hidden {
        visibility: hidden;
      }
      .accountAuth {
        color: @colorBase7;
        text-align: right;
        font-size: 12px;
        & > span {
          cursor: pointer;
        }
      }
    }
  }
  .login {
    margin-bottom: 0px;
    /deep/ button {
      background-color: @colorBase4;
      border-color: @colorBase4;
      /deep/ span {
        color: #fff;
        font-size: @fontSize16;
      }
    }
  }
  .otherLoginText {
    color: @colorBase2;
    font-size: @fontSize12;
    padding: 15px 0 5px 0;
  }
  .otherLogin {
    .scanLogin;
  }
  .regist {
    .select {
      color: @colorBase7;
      font-size: @fontSize14;
      &:hover {
        .mouseHover;
      }
    }
  }
  .forget {
    span {
      font-size: @fontSize12;
    }
    .select {
      color: @colorBase7;
      &:hover {
        .mouseHover;
      }
    }
  }
  // 手机随机码
  .code {
    position: relative;
    text-align: left;
    /deep/ & > div {
      display: inline-block;
      width: 48%;
    }
    .inputCode {
      div {
        margin-bottom: 0px;
      }
      /deep/ input {
        top: -3px;
      }
    }
    .getCode {
      position: relative;
      right: -10px;
      /deep/ span {
        width: 100% !important;
        height: 44px;
        line-height: 44px;
        font-size: 14px;
      }
    }
    .verifyMode {
      bottom: -20px;
    }
  }
}
</style>
