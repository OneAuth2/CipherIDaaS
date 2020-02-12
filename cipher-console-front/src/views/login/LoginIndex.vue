<template>
  <div class="loginMain">
    <div class="loginChild">
      <div class="loginTitle">赛赋IDaaS管理后台</div>
      <Row class="loginBody">
        <Col span="12">
        <img src="/static/img/login/index.png"
             alt=""
             class="img">
        </Col>
        <Col span="12">
        <Form ref="formCustom"
              class="form"
              :model="formCustom"
              :rules="ruleCustom">
          <FormItem class="formTitle">
            <span>账号登录</span>
          </FormItem>
          <FormItem prop="account">
            <Input v-model="formCustom.account"></Input>
          </FormItem>
          <FormItem prop="passwd">
            <Input type="password"
                   v-model="formCustom.passwd"></Input>
          </FormItem>
          <FormItem>
            <Button type="primary"
                    class="login"
                    @click="handleSubmitLogin('formCustom')">登录</Button>
          </FormItem>
        </Form>
        </Col>
      </Row>
    </div>
    <div class="copyRight">{{copyRight}}</div>
  </div>
</template>

<script>
import verify from "@/util/verify.js";
import api from "@/api/login/index.js";

export default {
  data () {
    // 账号校验
    const validateAccount = (rule, value, callback) => {
      if (value.length === 0 || value.length > 50) {
        callback(new Error("请输入不超过50位账号"));
      }
      let regx = verify.loginAccount;
      if (!regx.test(value)) {
        callback(new Error("英文字符（不区分大小写），数字，特殊符号(@-_.)"));
      }
      callback();
    };

    // 密码校验
    const validatePass = (rule, value, callback) => {
      if (value.length === 0 || value.length > 50) {
        callback(new Error("请输入不超过50位密码"));
      }
      let regx = verify.accountNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入包含英文字符，数字或特殊符号的密码"));
      }
      callback();
    };

    return {
      copyRight: "",
      formCustom: {
        account: "",
        passwd: ""
      },
      ruleCustom: {
        account: [
          { validator: validateAccount, trigger: "blur" }
        ],
        passwd: [
          { validator: validatePass, trigger: "blur" }
        ]
      }
    };
  },
  mounted () {
    this.getInfo();
  },
  methods: {
    /**
     * 配置标签的信息，ico和title
     * @param {*Object 包含ico图标和title} icon
     * @author yezi 2019-09-17
     */
    configHead (icon) {
      var link = document.querySelector("link[rel*='icon']") || document.createElement("link");
      link.type = "image/x-icon";
      link.rel = "shortcut icon";
      link.href = icon.iconfont;
      document.getElementsByTagName("head")[0].appendChild(link);
      let headTitle = document.getElementsByTagName("head")[0].querySelector("title") || document.createElement("title");
      headTitle.innerText = icon.title;
      document.getElementsByTagName("head")[0].appendChild(headTitle);
    },
    /**
     * 获取页面配置信息
     * @param {*void}
     * @author yezi 2019-09-17
     */
    getInfo () {
      this.axios({
        method: "post",
        data: {},
        url: "/cipher/obtain/copyright"
      })
        .then(response => {
          if (
            response.data.return_code === 0
          ) {
            this.copyRight = response.data.copyright;
            this.configHead(response.data.icon);
          } else {
            let errorMsg = response.data.return_msg;
            console.error(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 登录
    handleSubmitLogin (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let params = {
            userName: this.formCustom.account,
            pwd: this.formCustom.passwd
          };
          this.axios({
            method: "post",
            data: params,
            url: api.checkpwd
          })
            .then(response => {
              if (response.data.return_code === api.returnOk) {
                // 登录处理
                let token = response.headers.ticket;
                this.setToken(token);
                this.$router.push({ path: "/" });
              } else {
                this.failModal(response.data.return_msg);
              }
            })
            .catch(function (error) {
              this.axios.error.handlingErrors(error);
            });
        }
      });
    },
    setToken (token) {
      if (token && token !== undefined && token !== null) {
        window.localStorage.setItem("token", token);
      } else {
        token = window.localStorage.getItem("token");
      }
      this.$store.commit("changeToken", token);
    },
    failModal (errorMsg) {
      this.$myModal.error({
        title: errorMsg,
        content: "请稍后重试"
      });
    }
  }
};
</script>

<style lang="less" scoped>
.loginMain {
  height: 100%;
  background-color: rgb(0, 0, 255);
}
.loginChild {
  position: absolute;
  margin: auto;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  width: 70%;
  height: 65%;
  background: rgb(255, 255, 255);
  border-radius: 10px;
  min-width: 650px;
  min-height: 470px;
}
.form {
  padding: 50px;
}
.formTitle {
  span {
    font-size: 16px;
    display: inline-block;
    padding: 5px 20px;
    border-bottom: 2px solid rgb(0, 0, 255);
  }
}
.loginBody {
  height: calc(~"100% - 70px");
  > div {
    height: 100%;
    > img {
      width: 100%;
      max-width: 380px;
    }
  }
}
.img {
  width: 400px;
}
.loginTitle {
  font-size: 20px;
  padding: 20px 0;
}
.login {
  background-color: rgb(0, 0, 255);
  width: 100%;
}
.copyRight {
  color: #fff;
  font-size: 20px;
  position: absolute;
  bottom: 20px;
  left: 0;
  right: 0;
}
</style>
