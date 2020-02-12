<template>
  <Form ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate">
    <div class="config-title">配置登录页面：</div>
    <div class="page clearfix">
      <div class="page-top clearfix">
        <FormItem class="img">
          <Upload ref="upload"
                  :show-upload-list="false"
                  :on-success="handleSuccessLogo"
                  :format="['jpg','jpeg','png']"
                  :max-size="2048"
                  :on-format-error="handleFormatError"
                  :on-exceeded-size="handleMaxSize"
                  type="drag"
                  action="/cipher/app/imgUpload"
                  style="width:50px;height:48px;display: inline-block;">
            <img :src="logo" />
          </Upload>
        </FormItem>
        <FormItem prop="topText"
                  class="text">
          <Input v-model.trim="formValidate.topText" />
        </FormItem>
      </div>
      <div class="page-center">
        <FormItem class="pageleft"
                  v-if="selected==='0'">
          <Upload ref="upload"
                  :show-upload-list="false"
                  :format="['jpg','jpeg','png']"
                  :max-size="2048"
                  :on-format-error="handleFormatError"
                  type="drag"
                  action="/cipher/app/imgUpload"
                  :on-success="handleSuccessLogoDraw"
                  style="height:350px">
            <img :src="loginDraw"
                 class="pageleft"
                 v-if="loginDraw">
          </Upload>
        </FormItem>
        <div class="pageright">
          <FormItem class="page-right-title"
                    prop="loginText">
            <Input v-model.trim="formValidate.loginText" />
          </FormItem>
          <img :src="loginUrl" />
        </div>
      </div>
      <div class="page-bottom">©2019 CipherChina.com All rights reserved.</div>
    </div>
    <Divider />
    <div class="config-title">配置登录成功页面：</div>

    <div class="page clearfix">
      <div class="page-top clearfix">
        <FormItem class="img">
          <img :src="logo" />
        </FormItem>
        <FormItem prop="leftTitle"
                  class="text">
          {{formValidate.topText}}
        </FormItem>
      </div>
      <div class="page-center">
        <div class="pageright">
          <FormItem class="page-right-title page-right-success-title"
                    prop="successText">
            <Input v-model.trim="formValidate.successText" />
          </FormItem>
          <img :src="loginSuccessUrl" />
        </div>
      </div>
      <div class="page-bottom">©2019 CipherChina.com All rights reserved.</div>
    </div>
  </Form>
</template>

<script>
export default {
  data () {
    return {
      loginUrl: require("@/assets/wifi-portal-login.png"),
      loginSuccessUrl: require("@/assets/wifi-portal-success.png"),
      logo: "",
      loginDraw: "",
      formValidate: {
        topText: "",
        loginText: "",
        successText: ""
      },
      ruleValidate: {
        topText: [
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        loginText: [
          // { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        successText: [
          // { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }]
      }
    };
  },
  props: ["selected"],
  methods: {
    /**
     * 校验表单数据，成功返回true并执行保存数据函数
     * @param {*void}
     * @author yezi 2019-08-02
     */
    validateForm () {
      let flag = null;
      this.$refs["formValidate"].validate(valid => {
        if (valid) {
          flag = true;
          this.saveData();
          this.$emit("ListenChild", this.formValidate); // 验证通过将数据传递到父组件
        } else {
          flag = false;
        }
      });
      return flag;
    },
    /**
     * 保存数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    saveData () {
      let params = {
        logo: this.logo,
        topText: this.formValidate.topText,
        loginDraw: this.loginDraw,
        loginText: this.formValidate.loginText,
        successText: this.formValidate.successText,
        portalType: this.selected
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wifiportal/addThird"
      })
        .then(res => {
          // 成功发送跳转
          if (res.data.return_code === 1) {
            this.$myModal.error({ title: "保存失败", content: "res.data.return_msg" });
          } else {
            this.$router.push("/netAccessServicePortal");
            this.$myMessage.success("保存成功!");
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    // 左上角logo图片上传触发
    handleSuccessLogo (res, file) {
      if (res.status === 0) {
        this.logo = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    // 左边图片上传触发
    handleSuccessLogoDraw (res, file) {
      if (res.status === 0) {
        this.loginDraw = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    // 图片上传成功提示框
    updateSuccessModal () {
      this.$myModal.success({
        title: "上传成功",
        content: "图片上传成功"
      });
    },
    // 图片上传失败提示框
    updateFailModal () {
      this.$myModal.error({
        title: "上传失败",
        content: "图片上传失败"
      });
    },
    handleFormatError (file) {
      this.$myModal.error({
        title: "请选择一张图片！",
        content: "文件不正确！ " + file.name + " 该文件不是一个图片"
      });
    },
    // 图片上传超过2M触发
    handleMaxSize (file) {
      this.$Notice.warning({
        title: "大小超过2M",
        desc: "该文件  " + file.name + "太大，不能超过2M"
      });
    },
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.save();
        } else {
          this.$myMessage.error("表单验证失败，请重试");
        }
      });
    }
  }
};
</script>

<style lang="less" scoped>
.config-title {
  text-align: left;
  font-size: 14px;
  margin-bottom: 10px;
}
.page {
  width: 900px;
  border: 1px solid;
  padding: 15px;
  margin: 0 auto;
}
.page-top {
  height: 60px;
  line-height: 60px;
  vertical-align: middle;
  .img {
    float: left;
    margin-top: -6px;
    max-width: 50px;
    height: calc(~"100% - 6px");
  }
  .text {
    float: left;
    font-size: 18px;
    color: #93c0cd;
    margin-left: 10px;
    margin-left: 10px;
  }
}
.page-center {
  width: 540px;
  margin: 0 auto;
  text-align: center;
}
.pageleft {
  display: inline-block;
  width: 270px;
  height: 350px;
}
.pageright {
  vertical-align: top;
  display: inline-block;
  width: 270px;
  height: 350px;
  border: 4px solid #93c0cd;
  border-radius: 5px;
  /deep/ .ivu-form-item-content {
    text-align: center;
  }
  /deep/ .ivu-form-item-error-tip {
    padding-top: 30px;
    left: 31px;
  }
  .page-right-title {
    height: 28px;
    padding: 20px 20px 0;
    text-align: center;
  }
  .page-right-success-title {
    padding-top: 50px;
    padding-bottom: 27px;
    /deep/ .ivu-form-item-error-tip {
      padding-top: 36px;
    }
  }
  img {
    max-width: 100%;
  }
}
.page-bottom {
  margin-top: 10px;
  text-align: center;
  color: #93c0cd;
}
.wrap2 {
  height: 100%;
  position: relative;
}
</style>
