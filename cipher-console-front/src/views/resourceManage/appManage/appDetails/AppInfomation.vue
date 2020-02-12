  <template>
  <div class="formed-wrap">
    <!-- 显示模块 -->
    <div class="formed-form app-form clearfix"
         v-if="!isEdit">
      <div class="choose-img">
        <img :src="appInform.applicationIconUrl">
      </div>
      <div class="left">
        <div v-for="item in showAppList"
             :key="item.name"
             class="formed-form-item">
          <!-- configInfo字段需要循环 -->
          <template v-if="item.name==='configInfo'">
            <div v-for="v in getConfigInfoList"
                 :key="v.name"
                 class="formed-form-item">
              <span>{{v.label}}</span>
              <span>{{appInform.configInfo[v.name]}}</span>
            </div>
          </template>

           <!-- saml下载元数据 -->
            <template v-else-if="item.formType==='a'">
              <div class="formed-form-item">
                  <span><a style="font-size:15px" :href="metadataUrl"> 下载元数据</a></span>
              </div>
            </template>
          <!-- 其他字段显示 -->
          <div class="formed-form-item"
               v-else>
            <span>{{item.label}}</span>
            <span v-if="item.formType==='input'">{{appInform[item.name]}}</span>
            <span v-else-if="item.name==='applicationStatus'">
              {{appInform[item.name].status?"已启用":"已停用"}}
              <Icon :class="appInform[item.name].class"
                    :type="appInform[item.name].type" />
            </span>
            <span v-else>{{getRadioLabel(item)}}</span>
          </div>
        </div>
      </div>
    </div>
    <!-- 编辑模块 -->
    <div class="formed-form app-form clearfix"
         v-else>
      <div class="choose-img">
        <img :src="appInform.applicationIconUrl"
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
      <div class="left"
           style="width:100%">
        <Form :label-width="225"
              class="left"
              ref="formValidate"
              :model="appEditInform"
              :rules="ruleValidate">
          <div v-for="item in showAppList"
               :key="item.name">
            <!-- configInfo字段需要循环 -->
            <template v-if="item.name==='configInfo'">
              <FormItem v-for="v in getConfigInfoList"
                        :key="v.name"
                        :label="v.label"
                        :prop="'configInfo.'+ v.name">
                <Input v-model.trim="appEditInform.configInfo[v.name]"
                       :placeholder="'请输入'+v.label.substr(0,v.label.length-1)" />
              </FormItem>
            </template>
            <!-- 认证方式直接显示 -->
            <FormItem :label="item.label"
                      :prop="item.name"
                      v-else-if="item.name==='applicationAuth'">
              {{appInform[item.name]}}
            </FormItem>
             <!-- 认证方式直接显示 -->
            <FormItem :label="item.label"
                      :prop="item.name"
                      v-else-if="item.name==='applicationDescription'">
              <Input v-model.trim="appEditInform[item.name]"
                     :disabled="!item.isEdit"
                     :placeholder="'请输入'+item.label.substr(0,item.label.length-1)" :maxlength="24"/>
            </FormItem>
            <FormItem :label="item.label"
                      :prop="item.name"
                      v-else-if="item.formType==='input'">
              <Input v-model.trim="appEditInform[item.name]"
                     :disabled="!item.isEdit"
                     :placeholder="'请输入'+item.label.substr(0,item.label.length-1)" />
            </FormItem>
             <!-- saml下载元数据 -->
            <template v-else-if="item.formType==='a'">
             <div class="formed-form-item">
                  <span><a style="font-size:15px" :href="metadataUrl"> 下载元数据</a></span>
              </div>
            </template>
            <FormItem :label="item.label"
                      :prop="item.name"
                      v-else-if="item.formType==='radio'">
              <RadioGroup v-model="appEditInform[item.name]">
                <Radio v-for="item in getRadioList(item)"
                       :label="item.value"
                       :key="item.label">
                  <span>{{item.label}}</span>
                </Radio>
              </RadioGroup>
            </FormItem>
          </div>
        </Form>
      </div>
    </div>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="saveEdit('formValidate')">保存</myButton>
      <myButton @click="cancel">取消</myButton>
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
import app from "./app.js";
import icon from "@/util/icon.js";
import { mapState } from "vuex";
export default {
  props: ["appInfoData"],
  data () {
    return {
      metadataUrl: "",
      appData: {},
      showAppList: [], // 页面显示的字段列表
      isEdit: false, // 是否编辑状态
      appInform: {}, // app 所有显示信息
      appEditInform: {}, // app 所有编辑信息
      chooseIconIndex: null, // 系统图标选择的index
      imageList: [], // 选择图片的图片列表
      uploadList: [], // 上传图片显示的数据时
      iconModal: false, // 选择图片的modal绑定值
      appSysIdList: [1, 2, 5, 6] // 有configInfo字段的应用
    };
  },
  computed: {
    ...mapState(["appId"]),
    getConfigInfoList () {
      return app.configInfoList.filter(item => { return item.appSysId === this.appInform.appSysId; });
    },
    getRadioList () {
      return function (item) {
        return app[item.radioList];
      };
    },
    getRadioLabel () {
      return function (item) {
        let list = app[item.radioList];
        let label;
        if (item.name !== "subAccountConfig" && item.name !== "subAccountPwdConfig") {
          let index = this.appInform[item.name] - 1;
          label = list[index].label;
        } else {
          for (let item of list) {
            if (this.appInform.associatedAccount[item.value] === 0) {
              label = item.label;
            }
          }
        }
        return label;
      };
    },
    ruleValidate () {
      let ruleValidate = {
        applicationName: [{ required: true, message: "请输入应用名称", trigger: "blur" }],
        applicationUrl: [{ required: true, message: "请输入应用地址", trigger: "blur" }]
      };
      for (let item of app.configInfoList) {
        if (item.appSysId === this.appData.appSysId) {
          ruleValidate["configInfo." + item.name] = [{ required: true, message: "请输入" + item.label, trigger: "blur" }];
        }
      }
      return ruleValidate;
    }
  },
  mounted () {
    this.appData = this.$props.appInfoData;
    this.init();
  },
  methods: {
    /**
    * 初始化数据，将父组件传递过来的数据进行处理
    * @param {*void}
    * @author yezi 2019-07-30
    */
    init () {
      let data = this.appData;
      this.metadataUrl = "/cipher/saml/metadata?appId=" + data.id;
      this.showAppList = app.getShowAppList(data.applicationType, data.appSysId);
      // 显示数据
      this.appInform = Object.assign({}, data);
      this.appInform.applicationAuth = app.applicationAuthList[this.appInform.applicationType - 1].label; // 认证方式
      this.appInform.applicationStatus = data.applicationStatus === "可用" ? icon.openIcon : icon.closeIcon; // 应用状态
      if (this.appSysIdList.includes(data.appSysId)) {
        this.appInform.configInfo = JSON.parse(data.configInfo) || [];
      }

      // 编辑数据
      this.appEditInform = Object.assign({}, data);
      if (this.appSysIdList.includes(data.appSysId)) {
        this.appEditInform.configInfo = JSON.parse(data.configInfo) || [];
      }

      for (let item of app.subAccountConfigList) { // 从账号
        if (this.appInform.associatedAccount[item.value] === 0) {
          this.appEditInform.subAccountConfig = item.value;
        }
      }

      for (let item of app.subAccountPwdConfigList) { // 从账号密码
        if (this.appInform.associatedAccount[item.value] === 0) {
          this.appEditInform.subAccountPwdConfig = item.value;
        }
      }

      // 如果为空，则显示默认图标
      if (!data.applicationIconUrl) { // 应用图标
        this.appEditInform.applicationIconUrl = this.appInform.applicationIconUrl = "/static/img/img-01.png";
      }
    },
    /**
     * 进入编辑状态
     * @param {*void}
     * @author yezi 2019-07-30
     */
    editStatus () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "base"
      });
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-07-30
     */
    cancel () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "base"
      });
    },
    /**
     * 点击编辑保存按钮
     * @param {*void}
     * @author yezi 2019-07-30
     */
    saveEdit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          for (let key in this.appEditInform.associatedAccount) {
            this.appEditInform.associatedAccount[key] = 1;
          }
          this.appEditInform.associatedAccount[this.appEditInform.subAccountConfig] = 0;
          this.appEditInform.associatedAccount[this.appEditInform.subAccountPwdConfig] = 0;
          let params = {
            id: this.appId, // 应用id
            applicationName: this.appEditInform.applicationName, // 应用名称
            applicationIconUrl: this.appEditInform.applicationIconUrl === "/assets/img/img-01.png" ? "" : this.appEditInform.applicationIconUrl, // 应用图片
            applicationStatus: this.appEditInform.applicationStatus, // 应用状态
            applicationIndex: this.appEditInform.applicationIndex, // 排序序号
            applicationUrl: this.appEditInform.applicationUrl || "", // cas应用h或c/s应用应用地址
            casServerUrl: this.appEditInform.casServerUrl, // cas服务器地址
            server: this.appEditInform.server || "", // c/s应用服务器地址
            data: this.appEditInform.data || "", // c/s应用账套名称
            pos: parseInt(this.appEditInform.pos) || 0, // c/s应用账套序号
            loginType: this.appEditInform.loginType, // C/S 登陆方式
            xdsgUrl: this.appEditInform.xdsgUrl, // dsg网关
            assManual: this.appEditInform.associatedAccount.assManual, // （从账号）手动配置（0-是，1-否）
            assPrimaryAccount: this.appEditInform.associatedAccount.assPrimaryAccount, // （从账号）主账号（0-是，1-否）
            assEmail: this.appEditInform.associatedAccount.assEmail, // （从账号）邮箱（0-是，1-否）
            assEmailPrefix: this.appEditInform.associatedAccount.assEmailPrefix, // （从账号）邮箱前缀（0-是，1-否）
            assTelephone: this.appEditInform.associatedAccount.assTelephone, // （从账号）手机号（0-是，1-否）
            assWorkers: this.appEditInform.associatedAccount.assWorkers, // （从账号）工号（0-是，1-否）
            assPwdManual: this.appEditInform.associatedAccount.assPwdManual, // （从账号密码）手动配置（0-是，1-否）
            assPwdPrimaryAccount: this.appEditInform.associatedAccount.assPwdPrimaryAccount, // （从账号密码）主账号密码（0-是，1-否）
            relayState: this.appEditInform.relayState,
            assertionConsumerServiceUrl: this.appEditInform.assertionConsumerServiceUrl,
            exitTime: this.appEditInform.exitTime,
            entityId: this.appEditInform.entityId,
            applicationDescription: this.appEditInform.applicationDescription
          };
          if (this.appSysIdList.includes(this.appData.appSysId)) { // 封装应用特有信息，不同应用信息不同
            params.configInfo = JSON.stringify(this.appEditInform.configInfo);
          }
          console.log(params);
          this.changeAppInform(params);
        }
      });
    },
    /**
     * 发送ajax请求，保存基本信息数据
     * @param {*Object 基本信息数据} params
     * @author yezi 2019-07-30
     */
    changeAppInform (params) {
      this.axios({
        method: "post",
        url: "/cipher/app/change?json",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            this.$myMessage.success("保存成功");
            this.cancel();
            this.$emit("getData");
          } else {
            this.$myModal.error({
              title: "错误",
              content: res.data.msg
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
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
      if (!Array.isArray(this.imageList)) return;
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
      this.appInform.applicationIconUrl = this.imageList[this.chooseIconIndex].imageUrl;
      this.appEditInform.applicationIconUrl = this.appInform.applicationIconUrl;
      this.iconModal = false;
    },
    /**
     * 图片上传成功时触发该方法
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleSuccess (res, file) {
      if (res.status === 0) {
        this.appInform.applicationIconUrl = res.imgName;
        this.appEditInform.applicationIconUrl = this.appInform.applicationIconUrl;
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
    }
  }
};
</script>
<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.app-form {
  position: relative;
}
.choose-img {
  position: absolute;
  left: 56px;
  top: 48px;
  img {
    .wh(123px, 123px);
    background: #fff;
    border: 1px solid @colorFontGrey;
    .border-radius(50%);
  }
}
.formed-form .formed-form-item > span:nth-child(1) {
  width: 225px;
}
.copy {
  margin-left: 8px;
  position: relative;
  z-index: 1;
}
/deep/ .ivu-input {
  width: 326px;
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
</style>
