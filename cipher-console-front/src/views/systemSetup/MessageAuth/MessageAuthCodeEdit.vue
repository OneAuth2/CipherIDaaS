<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <myButton @click="cancel"
                class="formed-header-btn">取消</myButton>
    </div>
    <div class="formed-container">
      <Form :model="formItem"
            :label-width="130"
            class="formed-form">
        <!-- 短信验证码部分 -->
        <div class="formed-form-part">
          <div class="formed-form-part-title">短信验证码</div>
          <FormItem label="短信服务商：">
            <mySelect v-model="selected"
                      :dataList="options"
                      valueKey="type"
                      labelKey="desc"
                      width="326px">
            </mySelect>
          </FormItem>
          <!-- 荣联云部分 -->
          <template v-if="ronglianyun">
            <FormItem label="accountSid：">
              <Input v-model="formItem.accountSid" />
            </FormItem>
            <FormItem label="accountSid：">
              <Input v-model="formItem.accountSid" />
            </FormItem>
            <FormItem label="authToken：">
              <Input placeholder
                     v-model="formItem.authToken" />
            </FormItem>
            <FormItem label="appId：">
              <Input placeholder
                     v-model="formItem.appId" />
            </FormItem>
          </template>
          <!-- 阿里云部分 -->
          <template v-else>
            <FormItem label="product：">
              <Input v-model="formItem.product" />
            </FormItem>
            <FormItem label="域名：">
              <Input placeholder
                     v-model="formItem.domain" />
            </FormItem>
            <FormItem label="accessKeyId：">
              <Input placeholder
                     v-model="formItem.accessKeyId" />
            </FormItem>
            <FormItem label="accessKeySecret：">
              <Input placeholder
                     v-model="formItem.accessKeySecret" />
            </FormItem>
            <FormItem label="signName：">
              <Input placeholder
                     v-model="formItem.signName" />
            </FormItem>
          </template>
        </div>
        <!-- 短信随机码部分 -->
        <div class="formed-form-part">
          <h3 class="formed-form-part-title">短信随机码</h3>
          <!-- 荣联云部分 -->
          <template v-if="ronglianyun">
            <FormItem label="短信模板：">
              <Input placeholder
                     v-model.trim="formItem.templateId" />
            </FormItem>
            <FormItem label="手机号码：">
              <div class="phoneNumber">
                <Input placeholder
                       v-model.trim="telephoneCode" />
              </div>
              <div class="sendsms">
                <myButton @click="sendsmscode(1)">发送测试短信</myButton>
              </div>
            </FormItem>
          </template>
          <!-- 阿里云部分 -->
          <template v-else>
            <FormItem label="短信模板：">
              <Input placeholder
                     v-model="formItem.templateCode" />
            </FormItem>
            <FormItem label="手机号码：">
              <div class="phoneNumber">
                <Input placeholder
                       v-model.trim="telephoneCode" />
              </div>
              <div class="sendsms">
                <myButton @click="sendsmscode(1)">发送测试短信</myButton>
              </div>
            </FormItem>
          </template>
        </div>
        <div class="formed-form-part">
          <h3 class="formed-form-part-title">开通账号通知</h3>
          <template v-if="ronglianyun">
            <FormItem label="短信模板：">
              <Input placeholder
                     v-model="formItem.templateIdOne" />
            </FormItem>
            <FormItem label="手机号码：">
              <div class="phoneNumber">
                <Input placeholder
                       v-model=" telephoneSetup" />
              </div>
              <div class="sendsms">
                <myButton @click="sendsmscode(2)">发送测试短信</myButton>
              </div>
            </FormItem>
          </template>
          <!-- 阿里云部分 -->
          <template v-else>
            <FormItem label="短信模板：">
              <Input placeholder
                     v-model="formItem.templateCodeOne" />
            </FormItem>
            <FormItem label="手机号码：">
              <div class="phoneNumber">
                <Input placeholder
                       v-model.trim=" telephoneSetup" />
              </div>
              <div class="sendsms">
                <myButton @click="sendsmscode(2)">发送测试短信</myButton>
              </div>
            </FormItem>
          </template>
        </div>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="updateNew">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      telephoneCode: "",
      telephoneSetup: "",
      id: 0,
      companyId: 0,
      buttonSize: "large",
      formItem: {
        id: "",
        companyId: "",
        accountSid: "",
        authToken: "",
        appId: "",
        templateId: "",
        templateIdOne: "",
        product: "",
        domain: "",
        accessKeyId: "",
        accessKeySecret: "",
        signName: "",
        templateCode: "",
        templateCodeOne: ""
      },
      options: [{ type: 1, desc: "荣联云" }, { type: 2, desc: "阿里云" }],
      selected: 2,
      ronglianyun: false,
      aliyun: true

    };
  },
  watch: {
    /**
     * 监听短信服务商的下拉框选项，不同类型显示不同信息页面
     * @param 参数为新数据和旧数据，watch时vue内部实现的参数
     * @author yezi 2019-08-02
     */
    selected (val, oldVal) {
      if (val === 1) { // 显示荣联云信息页面
        this.ronglianyun = true;
        this.aliyun = false;
        let params = {
          type: 1
        };
        this.finddata(params);
      } else if (val === 2) { // 显示阿里云信息页面
        this.ronglianyun = false;
        this.aliyun = true;
        let params = {
          type: 2
        };
        this.finddata(params);
      }
    }
  },

  mounted () {
    let params = {};
    this.finddata(params);
  },
  methods: {
    /**
     * 获取页面数据
     * @param {*Object 请求参数，空对象} param
     * @author yezi 2019-08-02
     */
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/sms/listNew?json",
        data: params
      })
        .then(function (res) {
          if (res.data.type === 1) {
            that.ronglianyun = true;
            that.aliyun = false;
            that.selected = res.data.type;
            that.id = res.data.id;
            that.companyId = res.data.companyId;
            that.formItem = res.data.ronglianSmsConfigInfo;
          } else if (res.data.type === 2) {
            that.ronglianyun = false;
            that.aliyun = true;
            that.selected = res.data.type;
            that.id = res.data.id;
            that.companyId = res.data.companyId;
            that.formItem = res.data.aliyunSmsConfigInfo;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 保存页面数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    updateNew () {
      let that = this;
      let params = {
        // id: that.id,
        companyId: that.companyId,
        type: that.selected,
        telephone: that.telephone,
        accountSid: that.formItem.accountSid,
        authToken: that.formItem.authToken,
        appId: that.formItem.appId,
        templateId: that.formItem.templateId,
        templateIdOne: that.formItem.templateIdOne,
        product: that.formItem.product,
        domain: that.formItem.domain,
        accessKeyId: that.formItem.accessKeyId,
        accessKeySecret: that.formItem.accessKeySecret,
        signName: that.formItem.signName,
        templateCode: that.formItem.templateCode,
        templateCodeOne: that.formItem.templateCodeOne
      };
      this.axios({
        method: "post",
        url: "/cipher/sms/updateNew",
        data: params
      })
        .then(function (res) {
          if (res.data.return_code === 1) {
            that.cancel();
            that.$myMessage.success(res.data.return_msg);
          } else if (res.data.return_code === 0) {
            that.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },

    /**
     * 点击发送短信验证码触发，发送请求，保存页面数据
     * @param {*Number 短信服务商类型，1表示荣联云，2表示阿里云} type
     * @author yezi 2019-08-02
     */
    sendsmscode (type) {
      let that = this;
      let params = {
        // id: that.id,
        companyId: that.companyId,
        type: that.selected,
        accountSid: that.formItem.accountSid,
        authToken: that.formItem.authToken,
        appId: that.formItem.appId,
        templateId: that.formItem.templateId,
        templateIdOne: that.formItem.templateIdOne,
        product: that.formItem.product,
        domain: that.formItem.domain,
        accessKeyId: that.formItem.accessKeyId,
        accessKeySecret: that.formItem.accessKeySecret,
        signName: that.formItem.signName,
        templateCode: that.formItem.templateCode,
        templateCodeOne: that.formItem.templateCodeOne
      };

      this.axios({
        method: "post",
        url: "/cipher/sms/updateNew",
        data: params
      })
        .then(function (res) {
          if (res.data.return_code === 1) {
            that.sendSms(type);
          } else if (res.data.return_code === 0) {
            that.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
    * 发送短信验证码
    * @param {*Number 短信服务商类型，1表示荣联云，2表示阿里云} type
    * @author yezi 2019-08-02
    */
    sendSms (type) {
      let params = {};
      if (type === 1) {
        if (this.telephoneCode === "" || this.telephoneCode === null) {
          this.$myMessage.error("请填写测试号码");
          return;
        }
        params = {
          telephone: this.telephoneCode
        };
        this.axios({
          method: "post",
          url: "/cipher/sms/sendMsg",
          data: params
        })
          .then(res => {
            if (res.data.return_code === 1) {
              this.$myMessage.success(res.data.return_msg);
            } else {
              this.$myMessage.error(res.data.return_msg);
            }
          })
          .catch(error => {
            console.log(error);
          });
      } else {
        if (this.telephoneSetup === "" || this.telephoneSetup === null) {
          this.$myMessage.error("请填写测试号码");
          return;
        }
        params = {
          telephone: this.telephoneSetup
        };
        this.axios({
          method: "post",
          url: "/cipher/sms/sendUserMsg",
          data: params
        })
          .then(res => {
            if (res.data.return_code === 1) {
              this.$myMessage.success(res.data.return_msg);
            } else {
              this.$myMessage.error(res.data.return_msg);
            }
          })
          .catch(error => {
            console.log(error);
          });
      }
    },
    /**
     * 取消编辑状态，返回显示页面路由
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
/deep/ .ivu-form-item {
  width: 455px;
}
.phoneNumber /deep/ .ivu-input-wrapper {
  width: 210px;
  float: left;
}
.sendsms {
  text-align: right;
}
.sendsms .btn {
  vertical-align: top;
}
</style>
