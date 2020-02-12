<template>
  <div class="mail-base formed-wrap">
    <!-- 显示部分 -->
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-item">
        <span>smtp服务器：</span>
        <span>{{showItem.smtp}}</span>
      </div>
      <div class="formed-form-item">
        <span>端口：</span>
        <span>
          <span>{{showItem.port}}</span>
          <Checkbox class="ssl"
                    v-model="showItem.isSSL"
                    disabled>SSL</Checkbox>
        </span>
      </div>
      <div class="formed-form-item">
        <span>邮箱账号：</span>
        <span>{{showItem.account}}</span>
      </div>
      <div class="formed-form-item">
        <span>邮箱密码：</span>
        <span>**************</span>
      </div>
    </div>
    <!-- 编辑部分 -->
    <div class="formed-form"
         v-else>
      <Form ref="formItem"
            :model="formItem"
            :rules="ruleValidate"
            class="mail-base-form"
            :label-width="130">
        <FormItem label="smtp服务器："
                  prop="smtp">
          <Input v-model.trim="formItem.smtp" />
        </FormItem>
        <FormItem label="端口："
                  prop="port">
          <div class="text">
            <Input class="port"
                   v-model.trim="formItem.port"
                   style="width:270px" />
            <Checkbox class="ssl"
                      v-model="formItem.isSSL">SSL</Checkbox>
          </div>
        </FormItem>
        <FormItem label="邮箱账号："
                  prop="account">
          <Input v-model.trim="formItem.account" />
        </FormItem>
        <FormItem label="邮箱密码："
                  prop="pwd">
          <Input v-model.trim="formItem.pwd"
                 type="password" />
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
      showItem: {
        smtp: "",
        port: "",
        account: "",
        pwd: "****************",
        isSSL: false
      },
      formItem: {
        smtp: "",
        port: "",
        account: "",
        pwd: "****************",
        isSSL: false
      },
      ruleValidate: {
        smtp: [
          { required: true, message: "smtp服务器不能为空", trigger: "blur" }
        ],
        port: [{ required: true, message: "端口不能为空", trigger: "blur", transform (value) { return String(value); } }],
        account: [
          { required: true, message: "邮箱账号不能为空", trigger: "blur" }
        ],
        pwd: [{ required: true, message: "邮箱密码不能为空", trigger: "blur" }]
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
        name: "base"
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
        name: "base"
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
        url: "/cipher/mail/basicConfigEcho",
        data: {}
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showItem = JSON.parse(JSON.stringify(res.data.return_result));
            this.showItem.isSSL = this.showItem.isSSL === 1;
            this.formItem = res.data.return_result;
            this.formItem.isSSL = this.formItem.isSSL === 1;
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
     * 保存页面配置，发送请求
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let params = {
            smtp: this.formItem.smtp,
            account: this.formItem.account,
            isSSL: this.formItem.isSSL ? 1 : 0,
            port: this.formItem.port,
            pwd: this.formItem.pwd
          };
          this.axios({
            method: "post",
            url: "/cipher/mail/basicConfig",
            data: params
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.$myMessage.success(res.data.return_msg);
                this.cancel(name);
                this.getBasedata();
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
.ssl {
  margin-left: 16px;
  padding-right: 0 !important;
}
.mail-base-form /deep/ .ivu-form-item-content {
  width: 376px;
}
</style>
