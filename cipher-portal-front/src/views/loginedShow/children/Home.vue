<template>
  <div class="home">
    <!-- <div class="download"
         v-if="isDownload">
      <span class="download-notice">系统提示：需要安装SSO插件，请下载并</span>
      <span class="download-install">
        <a href="/soft/csApply.exe"
           download>安装</a>
      </span>
      <Icon class="download-close"
            type="md-close"
            @click="closeInstall" />
    </div> -->
    <div class="center-content welcome-tip">
      <span>欢迎登录安全访问门户</span>
      <span>上次登录IP: <span>{{ip}} </span></span>
      <span>上次登录的时间: <span>{{loginTime}}</span></span>
    </div>
    <ul class="list center-content">
      <li v-for="item in list"
          :key="item.id"
          @click="jumpApp(item.clientId)">
        <div style="display:flex">
          <div class="img"><img :src="item.imgUrl"
                 class="imgStyle"> </div>
          <div class="rightSpan">
            <span class="spanColor">{{item.applyName}}</span>
          </div>
        </div>
        <div class="applicationDev">
          <span class="applicationDes">{{item.applicationDescription}}</span>
        </div>
      </li>
      <li @click="jumpDsg()"
          v-if="isDsg">
        <div style="display:flex">
          <div class="img"><img :src="DsgIcon"
                 class="imgStyle"> </div>
          <div class="rightSpan">
            <span class="spanColor">{{DsgName}}</span>
          </div>
        </div>
        <div class="applicationDev">
          <span class="applicationDes">{{DsgDescription}}</span>
        </div>

        <!-- <div class="img"><img :src="DsgIcon"></div>
        <span>{{DsgName}}</span> -->
      </li>
      <li @click="jumpAdmin()"
          v-if="isAdmin">
        <div style="display:flex">
          <div class="img"><img :src="adminIcon"
                 class="imgStyle"> </div>
          <div class="rightSpan">
            <span class="spanColor">管理后台</span>
          </div>
        </div>
        <div class="applicationDev">
          <span class="applicationDes">{{adminDescription}}</span>
        </div>

        <!-- <div class="img"><img :src="adminIcon"></div>
        <span>管理后台</span> -->
      </li>
      <li v-if="csAppKingdee.isExist && csAppKingdee.canClick">
        <a :href="csAppKingdee.clickUrl">
          <div class="img"><img :src="csAppKingdee.imgUrl">
          </div>
          <span>{{csAppKingdee.name}}</span>
        </a>
      </li>
      <li v-if="csAppUFIDA.isExist"
          @click="jumpCsAppUFIDA">
        <div class="img"><img :src="csAppUFIDA.imgUrl">
        </div>
        <span>{{csAppUFIDA.name}}</span>
      </li>
    </ul>
    <div class="modal"
         @click.self="configModal=false"
         v-if="configModal && !isWaiting">
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
                   type="password"
                   placeholder="输入密码"
                   v-if="isSubPwdEdit"></Input>
            <Input v-model.trim="formValidate.subPwd"
                   type="password"
                   placeholder="输入密码"
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
    <!-- 跳转等待中状态 -->
    <div class="modal"
         v-if="isWaiting">
      <Spin size="large"
            class="center"
            style="color:#fff">
        <Icon type="ios-loading"
              size=20
              class="spin-icon-load"></Icon>
        <div>跳转中...</div>
      </Spin>
    </div>

    <!-- 配置路径 -->
    <div class="confPath"
         v-if="isConfigPath">
      <div class="confPath-mask"></div>
      <div class="confPath-body">
        <div class="confPath-body-close">
          <Icon type="md-close"
                @click="isConfigPath=false" />
        </div>
        <p class="confPath-body-notice">应用启动发生异常，请确认应用安装路径。</p>
        <div class="confPath-body-path">
          <Input v-model="filePath"
                 placeholder="filePath"
                 style="width: 300px" />
          <div class="confPath-body-path-notice">请输入正确的客户端安装路径。</div>
        </div>
        <Button class="confPath-body-submit"
                @click="handleSubmitConfPath">提交</Button>
      </div>
    </div>
  </div>
</template>

<script>
import api from "@/api/loginedShow/index.js";
const csAppId = ["D41m36", "Nc92bt"];
const dsgAppId = "DSG123";
export default {
  props: ["queryName"],
  watch: {
    queryName: function (val) {
      this.getAppList(); // 接收父组件的值
    }
  },
  data () {
    return {
      errorMsg: "",
      alert: {
        show: false,
        title: ""
      },
      adminIcon: require("@/assets/img/loginedShow/admin-icon.png"),
      DsgIcon: require("@/assets/img/loginedShow/dsg-icon.png"),
      DsgDescription: "赛赋portal应用网关配置。",
      adminDescription: "赛赋portal成员管理后台。",
      loginTime: "***************", // 登陆时间
      ip: "************", // ip
      list: [],
      isAdmin: 0, // 是否是管理员，0不是 1是
      isDsg: false, // DSG是否存在
      DsgName: "", // dsg名称
      isWaiting: false, // 跳转等待状态
      configModal: false, // 从账号配置框
      isConfig: false, // 从账号配置中
      isSubNameEdit: true,
      isSubPwdEdit: true,
      applyId: "", // 点击跳转应用保存的应用id
      formValidate: {
        clientId: "",
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
      },
      csAppKingdee: {
        // cs显示
        isExist: false,
        imgUrl: "",
        name: "",
        // cs打开
        canClick: false,
        clickUrl: ""
      }, // cs金蝶应用
      csAppUFIDA: {
        // cs显示
        isExist: false,
        imgUrl: "",
        name: "",
        // cs打开
        canClick: false,
        clickUrl: ""
      }, // cs用友应用
      filePath: "C:\\Program Files (x86)\\Kingdee\\KIS\\Profession\\Advance\\KISMain",
      isConfigPath: false,
      isDownload: true
    };
  },
  mounted () {
    try {
      let isDownload = JSON.parse(localStorage.getItem("isDownload"));
      if (isDownload === null || isDownload) {
        this.isDownload = true;
      } else {
        this.isDownload = false;
      }
    } catch (error) {
      console.log(error);
    }
    setTimeout(() => {
      this.isConfigPath = this.$route.query.isConfigPath === "true";
    }, 1000);
    this.getAppList();
    // this.jumpCsAppKingdee();
    // this.jumpCsAppUFIDA();
  },
  methods: {
    getAppList () {
      let params = {
        queryName: this.queryName
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.getAppList
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.list = res.data.portalList.filter(v => {
              if (v.clientId === dsgAppId) {
                this.isDsg = true;
                this.DsgName = v.applyName;
              }
              if (v.clientId === csAppId[0]) {
                this.csAppKingdee.isExist = true;
                this.csAppKingdee.name = v.applyName;
                this.csAppKingdee.imgUrl = v.imgUrl;
              }
              if (v.clientId === csAppId[1]) {
                this.csAppUFIDA.isExist = true;
                this.csAppUFIDA.name = v.applyName;
                this.csAppUFIDA.imgUrl = v.imgUrl;
              }
              return v.clientId !== dsgAppId && v.clientId !== csAppId[0] && v.clientId !== csAppId[1];
            });
            this.isAdmin = res.data.isAdmin;
            this.loginTime = res.data.loginTime;
            this.ip = res.data.ip;
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$toast(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 点击跳转dsg
    jumpDsg () {
      this.isWaiting = true; // 点击等待跳转出现
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        applyId: "DSG123"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.doOidcAuthorize
      })
        .then(res => {
          this.isWaiting = false;
          if (res.data.return_code === 0) { // 成功直接跳转
            window.open(res.data.applyUrl);
          } else if (res.data.return_code === 3 || res.data.return_code === 4) { // 账号不存在 或 账号密码错误 打开账号配置框
            this.formValidate.subName = res.data.subName;
            this.isSubNameEdit = res.data.subName === null || res.data.subName === "";
            this.formValidate.subPwd = res.data.subPwd;
            this.isSubPwdEdit = res.data.subPwd === null || res.data.subPwd === "";
            setTimeout(() => { this.configModal = true; }, 100); // 延迟出现配置框，防止与等待状态同时出现
          } else if (res.data.return_code !== 419) { // 其他状态 报错
            let errorMsg = res.data.return_msg;
            this.$toast(errorMsg);
            // this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          // this.isWaiting = false;
          this.axios.error.handlingErrors(error);
        });
    },
    // 点击跳转admin
    jumpAdmin () {
      this.isWaiting = true; // 点击等待跳转出现
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.toAdmin
      })
        .then(res => {
          this.isWaiting = false;
          if (res.data.return_code === 0) { // 成功直接跳转
            window.open(res.data.applyUrl);
          } else if (res.data.return_code !== 419) { // 其他状态 报错
            let errorMsg = res.data.return_msg;
            this.$toast(errorMsg);
          }
        })
        .catch(function (error) {
          this.isWaiting = false;
          this.axios.error.handlingErrors(error);
        });
    },
    // 点击跳转应用
    jumpApp (applyId) {
      this.isWaiting = true; // 点击等待跳转出现
      this.applyId = applyId;
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        applyId: applyId
      };
      this.axios({
        method: "post",
        data: params,
        url: api.doAuthOidcAuthorize
      })
        .then(res => {
          this.isWaiting = false;
          if (res.data.return_code === 0) { // 成功直接跳转
            window.open(res.data.applyUrl);
          } else if (res.data.return_code === 3 || res.data.return_code === 4) { // 账号不存在 或 账号密码错误 打开账号配置框
            this.formValidate.subName = res.data.subName;
            this.isSubNameEdit = res.data.subName === null || res.data.subName === "";
            this.formValidate.subPwd = res.data.subPwd;
            this.isSubPwdEdit = res.data.subPwd === null || res.data.subPwd === "";
            setTimeout(() => { this.configModal = true; }, 100); // 延迟出现配置框，防止与等待状态同时出现
          } else if (res.data.return_code !== 419) { // 其他状态 报错
            this.$toast("应用异常，联系管理员");
          }
        })
        .catch(function (error) {
          // this.isWaiting = false;
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
      this.isConfig = true;
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        applyId: this.applyId,
        subName: this.formValidate.subName,
        subPwd: this.formValidate.subPwd
      };
      this.axios({
        method: "post",
        data: params,
        url: api.checkSubAccount
      })
        .then(res => {
          this.isConfig = false;
          if (res.data.return_code === 0) { // 从账号检查成功后保存从账号，并将跳转地址以函数参数形式传递
            this.configModal = false;
            this.saveSubAccount(res.data.applyUrl);
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.isConfig = false;
          this.axios.error.handlingErrors(error);
        });
    },
    // 保存从账号正确
    saveSubAccount (applyUrl) {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        clientId: this.applyId,
        subName: this.formValidate.subName,
        subPwd: this.formValidate.subPwd
      };
      this.axios({
        method: "post",
        data: params,
        url: api.saveSubAccount
      })
        .then(res => {
          if (res.data.return_code === 0) { // 保存成功后跳转到app页面
            window.open(applyUrl);
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 关闭金蝶安装提示
     * @param {*Void}
     * @author menglei 2019-09-26
     */
    closeInstall () {
      this.isDownload = false;
      localStorage.setItem("isDownload", false);
    },
    /**
     * 金蝶跳转相关
     * @param {*Void}
     * @author menglei 2019-09-26
     */
    jumpCsAppKingdee () {
      let params = {
        applyId: csAppId[0]
      };
      this.axios({
        method: "post",
        data: params,
        url: api.doCsAuthorize
      })
        .then(res => {
          let data = res.data;
          if (data.return_code === 0) { // 保存成功后跳转到app页面
            this.csAppKingdee.canClick = true;
            this.csAppKingdee.clickUrl = "SAIFU://" + data.data;
          } else {
            let errorMsg = data.return_msg;
            console.log(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 跳转用友cs应用---todo通过applicationId（clientId）区分cs应用
     * @param {*Void}
     * @author menglei 2019-09-26
     */
    jumpCsAppUFIDA () {
      let params = {
        applyId: csAppId[1]
      };
      this.axios({
        method: "post",
        data: params,
        url: api.doNcAuthorize
      })
        .then(res => {
          let data = res.data;
          if (data.return_code === 0) { // 保存成功后跳转到app页面
            this.csAppUFIDA.clickUrl = data.data;
            this.handleUFIDA();
          } else {
            let errorMsg = data.return_msg;
            console.log(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    handleUFIDA () {
      window.location.href = this.csAppUFIDA.clickUrl;
    },
    /**
     * 提交配置应用路径
     * @param {*Void}
     * @author menglei 2019-09-26
     */
    handleSubmitConfPath () {
      let params = {
        applyId: csAppId[0],
        path: this.filePath
      };
      this.axios({
        method: "post",
        data: params,
        url: api.saveCsPath
      })
        .then(res => {
          let data = res.data;
          if (data.return_code === 0) {
            this.isConfigPath = false;
            this.csAppKingdee.clickUrl = "SAIFU://" + data.data;
            this.$router.push({ path: "/loginedShow" });
          } else {
            let errorMsg = data.return_msg;
            this.$toast(errorMsg);
            console.log(errorMsg);
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
.home {
  .padding(85px, 130px);
  box-sizing: border-box;
  position: relative;
  height: 100%;
  .download {
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    height: 64px;
    line-height: 64px;
    margin: auto;
    font-size: 18px;
    color: #fff;
    background-color: #99cdff;
    font-weight: 500;
    .download-install {
      font-size: 20px;
      padding-left: 10px;
      color: #0091ff;
    }
    .download-close {
      position: absolute;
      right: 0;
      top: 32px;
      margin-top: -9px;
      cursor: pointer;
    }
    /deep/ .ivu-icon {
      width: 20px;
      height: 20px;
      margin-right: 26px;
    }
  }
}
.welcome-tip {
  position: relative;
  top: -50px;
  margin-bottom: 25px;
  font-size: @fontSize2;
  font-family: "微软雅黑";
  color: @fontGrey;
  > span:first-child {
    margin-right: 80px;
  }
  > span:last-child {
    margin-left: 20px;
  }
}
.list {
  display: flex;
  flex-wrap: wrap;
  margin-top: -70px;
  li {
    width: 210px;
    height: 100px;
    margin-left: 34px;
    margin-top: 35px;
    .padding(10px, 0);
    box-shadow: 1px 1px 9px rgba(0, 0, 0, 0.35);
    border-radius: 3%;
    &:hover {
      cursor: pointer;
    }
    .img {
      //border-radius: 50%;
      // margin: 10px;
      margin-left: 10px;
      text-align: left;
    }
    .imgStyle {
      widows: 30px;
      height: 30px;
    }
    // img {
    //   width: 100%;
    //   height: 100%;
    // }
    .applicationDev {
      text-align: left;
      margin-top: 5px;
      padding-left: 15px;
      padding-right: 15px;
    }
    .applicationDes {
      font-size: 14px;
    }
    .rightSpan {
      margin-left: 15px;
      color: #027db4;
    }
    .spanColor {
      color: #000000;
    }
    span {
      // display: inline-block;
      margin-right: 10px;
      .sc(15px, #666);
    }
  }
}
.modal {
  position: fixed;
  min-width: 1200px;
  left: 0;
  top: 0;
  right: 0;
  bottom: 0;
  height: 100%;
  width: 100%;
  background-color: rgba(0, 0, 0, 0.35);
  .formsBody {
    height: 360px;
    width: 280px;
  }
  .move-left {
    left: 50%;
  }
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
.confPath {
  .confPath-mask {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    background: rgba(0, 0, 0, 0.5);
  }
  .confPath-body {
    position: fixed;
    top: 0;
    left: 0;
    bottom: 0;
    right: 0;
    width: 594px;
    height: 390px;
    background-color: rgba(255, 255, 255, 1);
    border-radius: 5px;
    margin: auto;
    .confPath-body-close {
      text-align: right;
      padding: 16px 16px 0 0;
      font-size: 30px;
    }
    .confPath-body-notice {
      color: #08142c;
      font-size: 16px;
      font-weight: 500;
      padding-top: 64px;
    }
    .confPath-body-path {
      padding-top: 40px;
      width: 300px;
      margin: auto;
      /deep/ input {
        text-align: left;
      }
      .confPath-body-path-notice {
        width: 100%;
        text-align: left;
        padding-top: 8px;
      }
    }
    .confPath-body-submit {
      font-size: 14px;
      color: #fff;
      margin-top: 68px;
      margin-bottom: 40px;
      background-color: #81cfe6;
      /deep/ span {
        display: inline-block;
        padding: 6px 46px;
      }
    }
  }
}
</style>
