<template>
  <div class="mail-setup formed-wrap">
    <!-- 显示部分 -->
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-item">
        <span>邮件主题：</span>
        <span>{{showItem.title}}</span>
      </div>
      <div class="formed-form-item">
        <span>邮件文案：</span>
        <span class="mail-setup-writer"
              v-html="getWritter"></span>
      </div>
    </div>
    <!-- 编辑部分 -->
    <div class="formed-form mail-setup-form"
         v-else>
      <Form ref="formItem"
            :model="formItem"
            :rules="ruleValidate"
            :label-width="130">
        <FormItem label="服务状态："
                  prop="serviceState">
          <RadioGroup v-model="formItem.serviceState">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">
              <span>{{item.label}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="邮件主题："
                  prop="title">
          <Input v-model.trim="formItem.title" />
        </FormItem>
        <FormItem label="邮件文案："
                  prop="writer">
          <Input v-model.trim="formItem.writer"
                 type="textarea"
                 :rows="8" />
        </FormItem>
        <FormItem label="邮箱地址：">
          <div id="emailaddress">
            <Input v-model.trim="emailAddress"
                   style="width:255px" />
            <myButton @click="testEmail"
                      style="margin-left:16px;vertical-align: top;">测试</myButton>
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
      switchList: [
        {
          value: 0,
          label: "开启"
        },
        {
          value: 1,
          label: "关闭"
        }
      ],
      showItem: {
        serviceState: 0,
        title: "",
        writer: ""
      },
      formItem: {
        serviceState: 0,
        title: "",
        writer: ""
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
  computed: {
    getWritter () {
      return this.showItem.writer.replace(/(\r\n|\n|\r)/gm, "<br />");
    }
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
        name: "setUpAccount"
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
        name: "setUpAccount"
      });
      this.$refs[name].resetFields();
    },
    /**
     * 获取页面所需数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getBasedata () {
      this.axios({
        method: "post",
        url: "/cipher/mail/createAccountEcho",
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
    testEmail () {
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
            this.$myModal.success({
              title: "提示",
              content: "邮件发送成功"
            });
          } else {
            this.$myModal.error({
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
            serviceState: this.formItem.serviceState
          };
          this.axios({
            method: "post",
            url: "/cipher/mail/createAccount",
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
.mail-setup-writer {
  width: 330px;
  line-height: 1.5 !important;
  padding-top: 7px;
}
.mail-setup-form /deep/ .ivu-form-item-content {
  width: 366px;
}
/deep/ textarea.ivu-input {
  line-height: 1;
}
</style>
