<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <myButton btnType="info"
                @click="edit"
                class="formed-header-btn">编辑</myButton>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <template v-if="ronglianyun">
          <div class="formed-form-part">
            <div class="formed-form-part-title">短信验证码</div>
            <div class="formed-form-item">
              <span>短信服务商：</span>
              <span>{{selected}}</span>
            </div>
            <div class="formed-form-item">
              <span>accountSid：</span>
              <span>{{formItem.accountSid}}</span>
            </div>
            <div class="formed-form-item">
              <span>authToken：</span>
              <span>{{formItem.authToken}}</span>
            </div>
            <div class="formed-form-item">
              <span>appId：</span>
              <span>{{formItem.appId}}</span>
            </div>
          </div>
          <div class="formed-form-part">
            <div class="formed-form-part-title">短信随机码</div>
            <div class="formed-form-item">
              <span>短信模版：</span>
              <span>{{formItem.templateId}}</span>
            </div>
          </div>
          <div class="formed-form-part">
            <div class="formed-form-part-title">开通账号通知</div>
            <div class="formed-form-item">
              <span>短信模版：</span>
              <span>{{formItem.templateIdOne}}</span>
            </div>
          </div>
        </template>
        <template v-if="aliyun">
          <div class="formed-form-part">
            <div class="formed-form-part-title">短信验证码</div>
            <div class="formed-form-item">
              <span>短信服务商：</span>
              <span>{{selected}}</span>
            </div>
            <div class="formed-form-item">
              <span>product：</span>
              <span>{{formItem.product}}</span>
            </div>
            <div class="formed-form-item">
              <span>域名：</span>
              <span>{{formItem.domain}}</span>
            </div>
            <div class="formed-form-item">
              <span>accessKeyId：</span>
              <span>{{formItem.accessKeyId}}</span>
            </div>
            <div class="formed-form-item">
              <span>accessKeySecret：</span>
              <span>{{formItem.accessKeySecret}}</span>
            </div>
            <div class="formed-form-item">
              <span>signName：</span>
              <span>{{formItem.signName}}</span>
            </div>
          </div>
          <div class="formed-form-part">
            <div class="formed-form-part-title">短信随机码</div>
            <div class="formed-form-item">
              <span>短信模版：</span>
              <span>{{formItem.templateCode}}</span>
            </div>
          </div>
          <div class="formed-form-part">
            <div class="formed-form-part-title">开通账号通知</div>
            <div class="formed-form-item">
              <span>短信模版：</span>
              <span>{{formItem.templateCodeOne}}</span>
            </div>
          </div>
        </template>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
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
      selected: "",
      ronglianyun: false,
      aliyun: true
    };
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
            that.selected = "荣联云";
            that.ronglianyun = true;
            that.aliyun = false;
            that.formItem = res.data.ronglianSmsConfigInfo;
          } else if (res.data.type === 2) {
            that.selected = "阿里云";
            that.ronglianyun = false;
            that.aliyun = true;
            that.formItem = res.data.aliyunSmsConfigInfo;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 进入编辑页面
     * @param {*void}
     * @author yezi 2019-08-02
     */
    edit () {
      this.$router.push({ name: "messageEdit" });
    }
  }
};
</script>
<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
.edit_body_box {
  padding: 0 30px;
  width: 100%;
}
.selected {
  width: 500px;
  margin-top: 20px;
}
.text {
  float: left;
}
.part {
  text-align: left;
  padding: 15px 0;
  border: 0 solid #999;
  border-top-width: 1px;
  > span {
    font-size: 15px;
    font-weight: 600;
    display: block;
    margin: 15px 0;
  }
}
</style>
