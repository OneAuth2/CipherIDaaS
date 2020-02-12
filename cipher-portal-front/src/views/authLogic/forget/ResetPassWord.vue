<template>
  <div>
    <AuthLogicBasic>
      <Wait></Wait>
      <div class="main">
        <div class="title titleLevel1">请设置新密码</div>
        <Form ref="formCustom"
              :model="formCustom"
              :rules="ruleCustom">
          <FormItem prop="passwd"
                    class="passwd">
            <Input type="password"
                   v-model="formCustom.passwd"
                   placeholder="输入新密码"></Input>
          </FormItem>
          <FormItem prop="passwdCheck"
                    class="passwd">
            <Input type="password"
                   v-model="formCustom.passwdCheck"
                   placeholder="确认新密码"></Input>
            <ErrorSf :msg="msg"></ErrorSf>
          </FormItem>
          <div class="login">
            <ButtonSf @click="handleSubmitSet('formCustom')">提交</ButtonSf>
          </div>
        </Form>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import apiFogetPW from "@/api/authLogic/fogetPassword.js";
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import { mapState } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  computed: {
    title () {
      return this.authLogic.portalConfig.rightTitle;
    },
    ...mapState({ authLogic: "authLogic" })
  },
  data () {
    const validatePass = (rule, value, callback) => {
      this.msg = "";
      if (value === "") {
        callback(new Error("请输入密码"));
      } else {
        if (this.formCustom.passwdCheck !== "") {
          // 对第二个密码框单独验证
          this.$refs.formCustom.validateField("passwdCheck");
        }
        callback();
      }
    };
    const validatePassCheck = (rule, value, callback) => {
      this.msg = "";
      if (value === "") {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.formCustom.passwd) {
        callback(new Error("两次输入的密码不匹配"));
      } else {
        callback();
      }
    };
    return {
      msg: "",
      formCustom: {
        passwd: "",
        phoneNumber: ""
      },
      ruleCustom: {
        passwd: [
          { validator: validatePass, trigger: "blur" }
        ],
        passwdCheck: [
          { validator: validatePassCheck, trigger: "blur" }
        ]
      },
      clauseFlag: true,
      show: false
    };
  },
  methods: {
    // 提交注册账号
    handleSubmitSet (name) {
      this.$refs[name].validate((valid) => {
        if (!valid) {
          return;
        }
        let params = {
          userId: this.authLogic.userInfo.userId,
          password: this.formCustom.passwd
        };
        this.axios({
          method: "post",
          data: params,
          url: apiFogetPW.reset
        })
          .then(response => {
            if (
              response.data.return_code === 0
            ) {
              this.$router.push({ path: "/loginedShow" });
            } else {
              this.msg = response.data.return_msg;
            }
          })
          .catch(function (error) {
            this.axios.error.handlingErrors(error);
          });
      });
    },
    showForm () {
      // this.show = true;
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  .title {
    padding: 63px 0 66px 0;
  }
  form {
    /deep/ .passwd {
      margin-bottom: 23px;
    }
    /deep/ .phoneNumber {
      margin-bottom: 50px;
      .notice {
        text-align: left;
        font-size: 20px;
        padding-top: 6px;
        a {
          color: @colorBase1;
        }
      }
    }
    /deep/ .login {
      margin-top: 50px;
    }
    /deep/ .clause {
      font-size: 18px;
      span {
        color: @colorBase1;
      }
    }
  }
}
</style>
