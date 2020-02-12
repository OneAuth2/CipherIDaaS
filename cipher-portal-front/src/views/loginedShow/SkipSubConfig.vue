<template>
  <div>
    <div>
      <div class="logined-show">
        <div class="show-content">
          <div class="top">
            <div class="center-content clear">
              <div class="left">
                <img :src="companyIcon" />
                <span>{{companyTitle}}</span>
              </div>
              <div class="right">
                <span class="inform">
                  <Icon type="md-person"
                        size="24" />
                  {{username}}
                </span>
              </div>
            </div>
          </div>
          <div class="content">
            <div class="formsBody center move-left">
              <h3 class="title">配置应用账号</h3>
              <Form ref="formValidate"
                    :model="formValidate"
                    :rules="ruleValidate">
                <FormItem prop="subName">
                  <Input v-model.trim="formValidate.subName"
                         placeholder="输入从账号"
                         v-if="isSubNameEdit"></Input>
                  <Input v-model.trim="formValidate.subName"
                         placeholder="输入从账号"
                         v-else
                         readonly></Input>
                </FormItem>
                <FormItem prop="subPwd">
                  <Input v-model.trim="formValidate.subPwd"
                         placeholder="输入从账号密码"
                         type="password"
                         v-if="isSubPwdEdit"></Input>
                  <Input v-model.trim="formValidate.subPwd"
                         placeholder="输入从账号密码"
                         type="password"
                         v-else
                         readonly></Input>
                </FormItem>
                <FormItem>
                  <button class="button"
                          type="button"
                          @click="handleSubmit('formValidate')">保存</button>
                </FormItem>
              </Form>
              <!-- 配置中状态 -->
              <Spin size="large"
                    fix
                    v-if="isConfig">
                <Icon type="ios-loading"
                      size=20
                      class="spin-icon-load"></Icon>
                <div>配置中...</div>
              </Spin>
            </div>
          </div>
        </div>
        <div class="footer">
          <div class="center-content">
            <img :src="logo" />
            <span>安全访问门户</span>
          </div>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import api from "@/api/loginedShow/index.js";
export default {
  data () {
    return {
      logo: require("../../assets/img/white-logo.png"),
      companyIcon: "",
      companyTitle: "",
      username: "",
      isSubNameEdit: true,
      isSubPwdEdit: true,
      isConfig: false, // 是否配置中
      applyId: "",
      skipUrl: "",
      formValidate: {
        subName: "",
        subPwd: ""
      },
      ruleValidate: {
        subName: [
          { required: true, message: "请输入从账号", trigger: "blur" }
        ],
        subPwd: [
          { required: true, message: "请输入从账号密码", trigger: "blur" }
        ]
      }
    };
  },
  created () {
    this.getBaseData();
    let data = this.$route.query;
    this.applyId = data.appId;
    this.formValidate.subName = data.subAccount;
    this.formValidate.subPwd = data.subPwd;
    this.isSubNameEdit = data.subAccount === null || data.subAccount === "";
    this.isSubPwdEdit = data.subPwd === null || data.subPwd === "";
    this.skipUrl = data.appHost;
  },
  methods: {
    /**
     * 获取页面数据
     * @param {*void}
     * @author yezi 2019-08-12
     */
    getBaseData () {
      this.axios({
        method: "post",
        data: {},
        url: api.getBaseInform
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.companyIcon = res.data.IconSettingsDomain.iconfont;
            this.companyTitle = res.data.IconSettingsDomain.title;
            this.username = res.data.username;
            this.isAdmin = res.data.isAdmin;
          } else {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 配置从账号点击保存
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.checkSubAccount(); // 点击保存验证从账号密码是否正确
        }
      });
    },
    // 检验从账号密码是否正确
    checkSubAccount () {
      let params = {
        applyId: this.applyId,
        subName: this.formValidate.subName,
        subPwd: this.formValidate.subPwd
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/oidc/checkAndAddUser"
      })
        .then(res => {
          if (res.data.return_code === 0) { // 从账号检查成功后保存从账号，并将跳转地址以函数参数形式传递
            // this.$router.push({ path: "/cipher/lua/login", query: { appHost: this.skipUrl } });
            let host = window.location.host;
            let url = "http://" + host + "/cipher/lua/login?appHost=" + this.skipUrl;
            window.location.href = url;
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/showList/common.less";
.logined-show {
  min-width: 1200px;
  position: relative;
  min-height: 100%;
}
.show-content {
  height: 100%;
  .top {
    height: @topHeight;
    line-height: @topHeight;
    background-color: @darkBlue;
    text-align: center;
    color: #fff;
    padding-left: 20px;
    > div > div {
      display: inline-block;
    }
    .left {
      position: relative;
      height: 60px;
      line-height: 60px;
      img {
        max-width: 80px;
        max-height: 30px;
        display: inline-block;
        vertical-align: middle;
        margin-top: -2px;
      }
      span {
        margin-left: 10px;
        font-size: 14px;
      }
    }
    .search /deep/ input {
      border-radius: 15px;
    }
    .right {
      > span {
        font-size: @fontSize2;
        margin-right: 15px;
        &:hover {
          cursor: pointer;
        }
        > i {
          display: inline-block;
          margin-right: 5px;
          margin-top: -5px;
        }
      }
      .inform {
        position: relative;
        display: inline-block;
      }
    }
  }
  .content {
    height: calc(~"100% - @{topHeight}");
    min-height: 500px;
    width: 100%;
  }
}
.footer {
  position: absolute;
  bottom: 0;
  left: 0;
  .wh(100%, @footerHeight) !important;
  line-height: @footerHeight;
  background-color: @darkBlue;
  text-align: right;
  color: #fff;
  img {
    .wh(24px, 24px);
    margin-right: 5px;
    position: relative;
    top: 6px;
  }
}
.formsBody {
  height: 360px;
  width: 280px;
}
.move-left {
  left: 50%;
}

.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
@keyframes ani-demo-spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
