<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <img src="@/assets/img/authLogic/logo.png"
             alt="">
        <div class="titleTop titleLevel1">{{title}}</div>
        <div class="login">
          <ButtonSf @click="getBindFlowInfo">确定</ButtonSf>
        </div>
        <div class="titleLevel1">授权获取信息</div>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import skipPathUtil from "@/views/authLogic/skipLogic.js";
import apiBinding from "@/api/authLogic/binding.js";
import TYPE from "@/views/authLogic/constantType/index.js";
import { mapState, mapMutations } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  data () {
    return {
      bindingApiObj: { // 绑定Api相关
        AutoApi: "", // 获取自动绑定api
        ManualApi: "", // 获取手动绑定api
        params: { // 请求参数
        }
      }
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
  mounted () {
    this.assembleGetInfo();
  },
  methods: {
    // 组装api,参数
    assembleGetInfo () {
      switch (this.scanWay) {
        case TYPE.CIPHER:
          this.bindingApiObj.AutoApi = apiBinding.saiFuAutoMatch;
          this.bindingApiObj.params.saiFuId = this.authLogic.saiFuInfo.uuid;
          this.bindingApiObj.params.phoneNumber = this.authLogic.saiFuInfo.phoneNumber;
          this.bindingApiObj.params.mail = this.authLogic.saiFuInfo.mail;
          break;
        case TYPE.DABAI:
          this.bindingApiObj.AutoApi = apiBinding.daBaiAutoMatch;
          this.bindingApiObj.params.daBaiId = this.authLogic.daBaiInfo.id_num;
          break;
        case TYPE.DINGDING:
          this.bindingApiObj.AutoApi = apiBinding.dingTalkAutoMatch;
          this.bindingApiObj.params.unionId = this.authLogic.dingTalkInfo.unionId;
          this.bindingApiObj.params.phone = this.authLogic.dingTalkInfo.phone;
          this.bindingApiObj.params.mail = this.authLogic.dingTalkInfo.mail;
          break;
        case TYPE.WX:
          this.bindingApiObj.AutoApi = apiBinding.wxAutoMatch;
          this.bindingApiObj.params.wxUserId = this.authLogic.wxInfo.userid;
          this.bindingApiObj.params.phone = this.authLogic.wxInfo.mobile;
          this.bindingApiObj.params.mail = this.authLogic.wxInfo.email;
          break;
        default:
          console.error("scanWay:" + this.scanWay);
          break;
      }
    },
    // 1.获取绑定流程信息
    getBindFlowInfo () {
      this.axios({
        method: "post",
        data: {},
        url: apiBinding.bindingFlow
      })
        .then(response => {
          if (
            response.data.return_code === 0
          ) {
            let binDingFlow = response.data.binDingFlow;
            this.authLogic.binDingFlow = binDingFlow;
            this.changeAuthLogic(this.authLogic);
            this.dealBindingFlow(binDingFlow);
          } else {
            let errorMsg = response.data.return_msg;
            console.error(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 2.处理绑定流程
    dealBindingFlow (binDingFlow) {
      let bindingFlowTemp = {};
      switch (this.scanWay) {
        case TYPE.CIPHER:
          bindingFlowTemp.bindingApp = binDingFlow.bindingAppSf; // 是否开启认证绑定
          bindingFlowTemp.autoBinding = binDingFlow.autoBindingSf; // 是否开启自动绑定
          break;
        case TYPE.DABAI:
          bindingFlowTemp.bindingApp = binDingFlow.bindingAppDb;
          bindingFlowTemp.autoBinding = binDingFlow.autoBindingDb;
          break;
        case TYPE.DINGDING:
          bindingFlowTemp.bindingApp = binDingFlow.bindingAppDd;
          bindingFlowTemp.autoBinding = binDingFlow.autoBindingDd;
          break;
        case TYPE.WX:
          bindingFlowTemp.bindingApp = binDingFlow.bindingAppWx;
          bindingFlowTemp.autoBinding = binDingFlow.autoBindingWx;
          break;
        default:
          console.error("scanWay:" + this.scanWay);
          break;
      }
      bindingFlowTemp.isOpenMailBind = binDingFlow.isOpenMailBind; // 开启邮箱校验
      bindingFlowTemp.isSkipMailBind = binDingFlow.isSkipMailBind; // 跳过邮箱校验
      bindingFlowTemp.isOpenNumBind = binDingFlow.isOpenNumBind; // 开启手机校验
      bindingFlowTemp.isSkipNumBind = binDingFlow.isSkipNumBind; // 跳过手机校验
      // 保存起来
      this.authLogic.binDingFlow = bindingFlowTemp;
      this.changeAuthLogic(this.authLogic);
      if (bindingFlowTemp.bindingApp === 0 && bindingFlowTemp.autoBinding === 0) { // 开启认证绑定和自动绑定才走自动匹配
        this.handleSubmitAuth(bindingFlowTemp);
      } else { // 不走自动绑定就到匹配失败页面
        this.$router.push("/authLogic/newUser/matchFail");
      }
    },
    // 3.授权获取信息--自动匹配
    handleSubmitAuth (binDingFlow) {
      this.axios({
        method: "post",
        data: this.bindingApiObj.params,
        url: this.bindingApiObj.AutoApi
      })
        .then(response => {
          let skipPath = "";
          if (
            response.data.return_code === 0
          ) { // 自动匹配成功
            // 更新用户id
            this.authLogic.userInfo.userId = response.data.userId;
            this.changeAuthLogic(this.authLogic);
            skipPath = skipPathUtil.bindSkipNextPage(binDingFlow, 1); // 跳到手机号验证或手机号验证下一步
          } else { // 自动匹配失败
            let errorMsg = response.data.return_msg;
            console.error(errorMsg);
            skipPath = "/authLogic/newUser/matchFail";
          }
          skipPath = skipPath + "?scanWay=" + this.scanWay;
          this.$router.push(skipPath);
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
  .titleTop {
    padding-bottom: 40px;
  }

  .login {
    margin-bottom: 30px;
  }
}
</style>
