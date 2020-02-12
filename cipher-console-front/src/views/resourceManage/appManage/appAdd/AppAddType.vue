<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <p class="formed-header-title">添加{{appType}}应用</p>
    </div>
    <div class="formed-container">
      <div class="formed-form ">
        <div class="app-add-form clearfix">
          <div class="choose-img">
            <img :src="applicationIconUrl"
                 class="default-img">
            <div class="choose-img__text">
              <div @click="showChooseIconList"
                   class="link">系统图标</div>
              <Upload ref="upload"
                      :show-upload-list="false"
                      :on-success="handleSuccess"
                      :format="['jpg','jpeg','png']"
                      :max-size="2048"
                      :on-format-error="handleFormatError"
                      :on-exceeded-size="handleMaxSize"
                      type="drag"
                      action="/cipher/app/imgUpload"
                      class="uploadClass">
                <div class="link">本地上传</div>
              </Upload>
            </div>
          </div>
          <Form :label-width="225"
                class="left"
                ref="formValidate"
                :model="formValidate"
                :rules="ruleValidate">
            <FormItem label="应用名称："
                      prop="applicationName">
              <Input v-model.trim="formValidate.applicationName"
                      :maxlength="14"
                     placeholder="请输入应用名称"></Input>
            </FormItem>
            <FormItem label="应用描述："
                      prop="applicationDescription">
              <Input v-model.trim="formValidate.applicationDescription"
                     :maxlength="24"
                     placeholder="请输入应用名称"></Input>
            </FormItem>
            <FormItem label="认证方式："
                      prop="applicationType">
              <RadioGroup v-model="formValidate.applicationType">
                <Radio v-for="item in getApplicationIconTypeList"
                       :key="item.value"
                       :label="item.value">
                  {{item.label}}
                  <Tooltip placement="top"
                           v-if="item.tip"
                           :content="item.tip">
                    <Icon type="md-information-circle"
                          class="tip-icon" />
                  </Tooltip>
                </Radio>

              </RadioGroup>
            </FormItem>
            <FormItem label="子账号："
                      prop="subAccountConfig"
                      v-if="appType!='新'">
              <RadioGroup v-model="formValidate.subAccountConfig">
                <Radio v-for="item in subAccountConfigList"
                       :key="item.value"
                       :label="item.value">
                  {{item.label}}
                </Radio>
              </RadioGroup>
            </FormItem>
            </FormItem>
            <FormItem label="DSG网关地址："
                      prop="xdsgUrl"
                      v-if="appType==='新'||appType==='CAS'">
              <Input v-model="formValidate.xdsgUrl"
                     placeholder="请输入集成应用的DSG网关地址与端口号" />
            </FormItem>
            <!-- cas特有 -->
            <FormItem label="应用地址："
                      prop="applicationUrl"
                      v-if="appType==='CAS'">
              <Input v-model="formValidate.applicationUrl"
                     placeholder="请输入应用地址" />
            </FormItem>
            <!-- saml特有 -->
            <template v-if="appType==='SAML'">
              <FormItem label="用户登录地址:"
                        prop="applicationType">
                <Input  v-model="formValidate.applicationUrl"
                       placeholder="请输入用户登录地址" />
              </FormItem>
              <FormItem label="relayState地址："
                        prop="relayState">
                <Input v-model="formValidate.relayState"
                       placeholder="请输入认证成功后的跳转地址" />
              </FormItem>
              <FormItem label="AssertionConsumerServiceUrl："
                        prop="AssertionConsumerServiceUrl：">
                <Input v-model="formValidate.assertionConsumerServiceUrl"
                       placeholder="请输入sp接受saml地址" />
              </FormItem>
              <FormItem label="断言有效期："
                        prop="exitTime">
                <Input v-model="formValidate.exitTime"
                       placeholder="请输入断言存活的周期（秒）" />
              </FormItem>
              <FormItem label="entityId:"
                        prop="entityId">
                <Input v-model="formValidate.idpEntityId"
                       placeholder="请输入idp的MetaData地址" />
              </FormItem>
            </template>
            <!-- oidc特有 -->
            <template v-if="appType==='OAUTH'">
              <FormItem label="重定向地址："
                        prop="applicationUrl">
                <Tooltip placement="right"
                         max-width="300"
                         content="重定向地址，http，https or APP-Schemep">
                  <Input v-model="formValidate.applicationUrl"
                         placeholder="请输入重定向地址" />
                </Tooltip>
              </FormItem>
              <!-- <FormItem label="授权类型："
                        prop="authType">
                <Tooltip placement="right"
                         max-width="300"
                         content="授权码模式（即先登录获取code，再获取token），标准OAuth2流程； 简化模式（在redirect-uri传递token）适用于验证第三方合法性时使用">
                  <RadioGroup v-model="formValidate.authType">
                    <Radio v-for="item in authTypeList"
                           :key="item.value"
                           :label="item.value">
                      {{item.label}}
                    </Radio>
                  </RadioGroup>
                </Tooltip>
              </FormItem> -->
              <!-- <FormItem label="Access Token有效期 ："
                        prop="applicationIconUrl">
                <Tooltip placement="right"
                         max-width="300"
                         content="Access Token的有效时常（单位：秒），默认7200（2小时）">
                  <Input v-model="formValidate.applicationIconUrl"
                         placeholder="请输入Access Token 有效期" />
                </Tooltip>
              </FormItem>
              <FormItem label="Refresh Token有效期 ："
                        prop="applicationIconUrl">
                <Tooltip placement="right"
                         max-width="300"
                         content="Refresh Token的有效时常（单位：秒），默认604800（7天）">
                  <Input v-model="formValidate.applicationIconUrl"
                         placeholder="请输入Refresh Token有效期" />
                </Tooltip>
              </FormItem> -->
              <!-- <FormItem label="OIDC AppID："
                        prop="applicationType">
                <Input v-model="formValidate.applicationType"
                       placeholder="请输入OIDC AppID" />
              </FormItem>
              <FormItem label="OIDC AppKey："
                        prop="applicationType">
                <Input v-model="formValidate.applicationType"
                       placeholder="请输入OIDC AppKey" />
              </FormItem> -->
            </template>
          </Form>
        </div>
      </div>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="saveAdd('formValidate')">保存</myButton>
        <myButton @click="goBack">返回</myButton>
      </div>
    </div>
    <Modal v-model="iconModal"
           width="700px">
      <p slot="header"
         class="modalHeadr">
        <Icon type="ios-add"></Icon>
        <span>选择图片</span>
      </p>
      <p class="addApplication">
        <Row :gutter="16"
             align="top">
          <div v-for="(item,index) in  imageList"
               :key="index"
               class="imgList"
               @click="chooseIcon(index)">
            <Col span="6"
                 style="height:210px">
            <div>
              <img :src="item.imageUrl"
                   size="30"
                   class="app-col-class">
              <Icon v-if="item.choose"
                    type="md-checkmark"
                    size="30"
                    class="iconClass" />
            </div>
            </Col>
          </div>
        </Row>
      </p>
      <p slot="footer"
         class="footerDemo">
        <myButton btnType="info"
                  @click="confirmIcon">确定</myButton>
        <myButton @click="iconModal=false"
                  style="margin-left: 8px;vertical-align:top">取消</myButton>
      </p>
    </Modal>
  </div>
</template>
<script>
import { mapMutations } from "vuex";
export default {
  name: "AddAppType",
  data () {
    return {
      appType: "", // 当前添加应用的方式，DSg/CAS/SAML/OIDC，通过路由传递过来的数据
      chooseIconIndex: null, // 系统图标选择的index
      imageList: [], // 选择图片的图片列表
      applicationIcontypeList: [
        { type: "新", label: "安全代理", value: 1, tip: "通过DSG网关代理到应用类型" },
        { type: "新", label: "非SSO应用", value: 6, tip: "通过超链接进行网页跳转的网站" },
        { type: "CAS", label: "CAS应用", value: 7 },
        { type: "SAML", label: "SAML协议", value: 10 },
        { type: "OAUTH", label: "OAUTH协议", value: 11 }
      ],
      subAccountConfigList: [
        { label: "手动配置", value: "assManual" },
        { label: "账号", value: "assPrimaryAccount" },
        { label: "邮箱", value: "assEmail" },
        { label: "邮箱前缀", value: "assEmailPrefix" },
        { label: "手机号", value: "assTelephone" },
        { label: "工号", value: "assWorkers" }
      ],
      authTypeList: [
        { label: "授权码模式", value: 0 },
        { label: "简化模式", value: 1 }
      ],
      id: "",
      uploadList: [], // 上传图片显示的数据
      applicationIconUrl: "/static/img/img-01.png", // 添加应用初始化的图片
      iconModal: false, // 选择图片的modal绑定值
      formValidate: {
        applicationType: null,
        applicationName: "",
        subAccountConfig: "assManual",
        applicationUrl: "",
        xdsgUrl: "",
        applicationIconUrl: "",
        authType: "",
        applicationDescription: ""
      },
      ruleValidate: {
        applicationName: [{ required: true, message: "请输入应用名称", trigger: "blur" }],
        applicationUrl: [{ required: true, message: "请输入应用地址", trigger: "blur" }]
      }
    };
  },
  computed: {
    getApplicationIconTypeList () { // 获取认证方式列表，根据传递的添加类型不同过滤成不同的列表
      return this.applicationIcontypeList.filter(v => { return v.type === this.appType; });
    }
  },
  mounted () {
    this.appType = this.$route.query.type;
    this.formValidate.applicationType = this.getApplicationIconTypeList[0].value; // 认证方式，默认选中第一个
  },
  methods: {
    /**
     * 获取系统图标列表，点击系统图标时触发
     * @param {*void}
     * @author yezi 2019-09-26
     */
    showChooseIconList () {
      this.axios({
        method: "post",
        data: {},
        url: "/cipher/app/add"
      })
        .then(res => {
          this.imageList = res.data.imageList;
          this.iconModal = true;
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
    * 点击系统图标里面的图片触发
    * @param {*Number 对应点击的图片在数组的序号} index
    * @author yezi 2019-09-26
    */
    chooseIcon (index) {
      this.imageList.forEach((v, i) => { v.choose = i === index; });
      this.$forceUpdate(); // 强制重新渲染，v-for的只改变数组值，不会重新渲染
      this.chooseIconIndex = index;
    },
    /**
     * 选择系统图标点击确认，选择图片
     * @param {*void}
     * @author yezi 2019-09-26
     */
    confirmIcon () {
      this.applicationIconUrl = this.imageList[this.chooseIconIndex].imageUrl;
      this.iconModal = false;
    },
    /**
     * 图片上传成功时触发该方法
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleSuccess (res, file) {
      if (res.status === 0) {
        this.applicationIconUrl = res.imgName;
        this.$myModal.success({
          title: "上传成功",
          content: "图片上传成功"
        });
      } else {
        this.$myModal.error({
          title: "上传失败",
          content: "图片上传失败"
        });
      }
    },
    /**
     * 选择不是一张图片的时候触发该方法
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleFormatError (file) {
      this.$Notice.warning({
        title: "请选择一张图片！",
        desc: "文件不正确！ " + file.name + " 该文件不是一个图片"
      });
    },
    /**
     * 上传文件超过2M的时候触发该方法
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleMaxSize (file) {
      this.$Notice.warning({
        title: "大小超过2M",
        desc: "该文件  " + file.name + "太大，不能超过2M"
      });
    },
    /**
     * 点击保存的时候触发该操作
     * @param {*void}
     * @author yezi 2019-09-26
     */
    saveAdd (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.applicationIconUrl = this.applicationIconUrl === "/assets/img/img-01.png" ? "" : this.applicationIconUrl;
          let params = {
            applicationName: this.formValidate.applicationName,
            applicationType: this.formValidate.applicationType,
            applicationIconUrl: this.applicationIconUrl,
            xdsgUrl: this.formValidate.xdsgUrl,
            applicationUrl: this.formValidate.applicationUrl,
            assManual: this.formValidate.subAccountConfig === "assManual" ? 0 : 1,
            assPrimaryAccount: this.formValidate.subAccountConfig === "assPrimaryAccount" ? 0 : 1,
            assEmail: this.formValidate.subAccountConfig === "assEmail" ? 0 : 1,
            assEmailPrefix: this.formValidate.subAccountConfig === "assEmailPrefix" ? 0 : 1,
            assTelephone: this.formValidate.subAccountConfig === "assTelephone" ? 0 : 1,
            assWorkers: this.formValidate.subAccountConfig === "assWorkers" ? 0 : 1,
            relayState: this.formValidate.relayState,
            assertionConsumerServiceUrl: this.formValidate.assertionConsumerServiceUrl,
            exitTime: this.formValidate.exitTime,
            entityId: this.formValidate.entityId,
            applicationDescription: this.formValidate.applicationDescription
          };
          this.axios({
            method: "post",
            url: "/cipher/app/add?json",
            data: params
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.id = res.data.return_result;
                this.$myMessage.success("保存成功!");
                this.changeAppId(this.id);
                this.$router.push({ name: "appDetails" });
              } else {
                this.$myModal.error(res.data.return_msg);
              }
            })
            .catch(error => {
              this.$Notice.warning({
                title: "网络未知错误！",
                desc: error
              });
            });
        }
      });
    },
    /**
     * 点击返回添加列表页
     * @param {*void}
     * @author yezi 2019-07-31
     */
    goBack () {
      this.$router.push({ name: "appAddList" });
    },
    ...mapMutations(["changeAppId"])
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
.app-add-form {
  position: relative;
}
.choose-img {
  position: absolute;
  left: 0;
  top: 0;
}
.default-img {
  .wh(123px, 123px);
  background: #fff;
  border: 1px solid @colorFontGrey;
  .border-radius(50%);
}
.choose-img__text {
  text-align: center;
  color: @colorPrimary;
  > div:first-child {
    margin-top: @customMargin*2;
    margin-bottom: @customMargin;
    font-weight: @fontBold;
    cursor: pointer;
  }
}
/deep/ .ivu-input {
  width: 326px;
}
.uploadClass /deep/ div {
  border: none;
  font-weight: @fontBold;
}
.iconClass {
  display: block;
  text-align: center;
  color: #22dd6d;
}
.app-col-class {
  border: 0px;
  text-align: center;
  height: 20%;
  width: 100%;
}

.tip-icon {
  font-size: 20px;
  font-weight: 500;
  color: @colorPrimary;
  position: relative;
  top: -1px;
}
/deep/ .ivu-tooltip-inner-with-width {
  min-width: 200px;
}
.div_lable {
  border: 1px solid #d5d5d5;
  border-bottom: none;
  padding: 15px, 15px;
  padding-top: 10px;
}

.appInfo {
  text-align: left;
  padding: 40px;
  border-bottom: 1px solid rgba(233, 233, 233, 1);
}
.appInfo1 {
  padding: 40px;
  border-bottom: 1px solid rgba(233, 233, 233, 1);
}
.appInfo2 {
  padding: 20px;
  border-bottom: 1px solid rgba(233, 233, 233, 1);
}
.font {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.847058823529412);
  line-height: 22px;
  white-space: nowrap;
  margin-top: 15px;
  margin-bottom: 20px;
}
.radioLeft {
  margin-left: 15px;
}

.position {
  text-align: right;
}

.text {
  color: #797979;
  font-size: 16px;
  text-align: center;
  font-weight: 300;
  position: relative;
  text-align: inherit;
  float: none;
  margin: 0;
  padding: 0;
  line-height: normal;
}
</style>
