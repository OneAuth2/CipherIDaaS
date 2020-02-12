<template>
  <div class="mail-random-code formed-wrap">
    <!-- 显示部分 -->
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-item">
        <span>邮件主题：</span>
        <span>{{showItem.title}}</span>
      </div>
      <div class="formed-form-item">
        <span>邮件文案：</span>
        <span>{{showItem.writer}}</span>
      </div>
      <div class="formed-form-item">
        <span>随机码发送倒计时：</span>
        <span><i>{{showItem.intervalTime}}</i> 分钟</span>
      </div>
      <div class="formed-form-item">
        <span>安全选项：</span>
        <span><i>{{showItem.effectiveTime}}</i>
          分钟内连续验证失败
          <i>{{showItem.sendTime}}</i>
          次后，随机码发送间隔为
          <i>{{showItem.extendTime}}</i>
          分钟
        </span>
      </div>
    </div>
    <!-- 编辑部分 -->
    <div class="formed-form mail-random-code-form"
         v-else>
      <Form ref="formItem"
            :model="formItem"
            :rules="ruleValidate"
            :label-width="130">
        </FormItem>
        <FormItem label="邮件主题："
                  prop="title">
          <Input v-model.trim="formItem.title" />
        </FormItem>
        <FormItem label="邮件文案："
                  prop="writer">
          <Input v-model.trim="formItem.writer" />
        </FormItem>
        <FormItem label="随机码发送倒计时："
                  prop="intervalTime">
          <div class="text">
            <InputNumber v-model.trim="formItem.intervalTime"
                         :min="0"
                         :step="1"
                         :precision="0" /> 分钟
          </div>
        </FormItem>
        <FormItem label="安全选项：">
          <div class="text">
            <InputNumber class="effectiveTime"
                         :min="0"
                         :step="1"
                         :precision="0"
                         v-model.trim="formItem.effectiveTime" /> 分钟内连续验证失败
            <InputNumber class="sendTime"
                         :min="1"
                         :step="1"
                         :precision="0"
                         v-model.trim="formItem.sendTime" /> 次后，
            <br />随机码发送间隔为
            <InputNumber class="extendTime"
                         :min="0"
                         :step="1"
                         :precision="0"
                         v-model.trim="formItem.extendTime" /> 分钟
          </div>
        </FormItem>
        <FormItem label="邮箱地址：">
          <div id="emailaddress">
            <Input v-model.trim="emailAddress"
                   style="width:270px" />
            <myButton @click="testemailaddress"
                      style="margin-left:16px;vertical-align:top">测试</myButton>
          </div>
        </FormItem>
      </Form>
    </div>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="handleSubmit('formItem')">保存</myButton>
      <myButton @click="cancel('formItem')">取消</myButton>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      emailAddress: "",
      showItem: {
        title: "",
        writer: "",
        effectiveTime: 0,
        intervalTime: 0,
        sendTime: 0,
        extendTime: 0
      },
      formItem: {
        title: "",
        writer: "",
        effectiveTime: 0,
        intervalTime: 0,
        sendTime: 0,
        extendTime: 0,
        emailAddr: ""
      },
      ruleValidate: {
        title: [
          { required: true, message: "邮件主题不能为空", trigger: "blur" }
        ],
        writer: [
          { required: true, message: "邮件文案不能为空", trigger: "blur" }
        ]
      }
    };
  },
  mounted () {
    this.getBasedata();
  },
  methods: {
    /**
     * 进入编辑状态，并重新加载数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    editStatus () {
      this.getBasedata(); // 点击编辑重新加载数据
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "randomCode"
      });
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel (name) {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "randomCode"
      });
      // this.cancel(name);
    },
    /**
     * 获取页面所需数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getBasedata () {
      this.axios({
        method: "post",
        url: "/cipher/mail/randomCodeEcho",
        data: {}
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showItem = JSON.parse(JSON.stringify(res.data.return_result));
            this.formItem = res.data.return_result;
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          this.$Notice.error({
            title: error
          });
        });
    },
    /**
     * 测试邮箱验证码，发送请求
     * @param {*void}
     * @author yezi 2019-08-02
     */
    testemailaddress () {
      let params = {
        title: this.formItem.title,
        writer: this.formItem.writer,
        emailAddr: this.emailAddress
      };
      this.axios({
        method: "post",
        url: "/cipher/mail/sendMsg",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$Modal.success({
              title: "提示",
              content: "邮件发送成功"
            });
          } else {
            this.$Modal.error({
              title: "操作失败",
              content: res.data.return_msg
            });
          }
        }).catch(error => {
          this.$myMessage.error(error);
        });
    },
    /**
     * 保存页面配置，发送请求
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let params = {
            title: this.formItem.title,
            writer: this.formItem.writer,
            effectiveTime: this.formItem.effectiveTime,
            intervalTime: this.formItem.intervalTime,
            sendTime: this.formItem.sendTime,
            extendTime: this.formItem.extendTime
          };
          this.axios({
            method: "post",
            url: "/cipher/mail/randomCode",
            data: params
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.$myMessage.success(res.data.return_msg);
                this.getBasedata(); // 点击编辑重新加载数据
                this.cancel(name);
              } else {
                this.$myMessage.error(res.data.return_msg);
              }
            })
            .catch(error => {
              this.$myMessage.error(error);
            });
        } else {
          this.$myMessage.error("表单验证失败，请重试！");
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.mail-random-code-form /deep/ .ivu-form-item-content {
  width: 366px;
}
.formed-form-item i {
  font-style: normal;
  color: #08142c;
  font-size: 24px;
}
/deep/ .ivu-input-number {
  margin-right: @customMargin * 2 !important;
}
</style>
