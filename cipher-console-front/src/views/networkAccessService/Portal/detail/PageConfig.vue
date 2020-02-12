<template>
  <div class="page-portal formed-wrap">
    <transition name="fade"
                mode='out-in'>
      <div class="formed-form"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="formed-form-part">
          <div class="formed-form-part-title">配置登录页</div>
          <div class="formed-form-item page clearfix">
            <div class="page-top clearfix">
              <div class="img"><img :src="showData.logo" /></div>
              <div class="text">{{showData.topText}}</div>
            </div>
            <div class="page-center">
              <div class="pageleft"
                   v-if="type===0">
                <img :src="showData.loginDraw"
                     class="pageleft">
              </div>
              <div class="pageright">
                <div class="page-right-title">
                  {{showData.loginText}}
                </div>
                <img :src="loginUrl" />
              </div>
            </div>
            <div class="page-bottom">©2019 CipherChina.com All rights reserved.</div>
          </div>
        </div>
        <div class="formed-form-part">
          <div class="formed-form-part-title">配置登录成功页</div>
          <div class="formed-form-item page clearfix">
            <div class="page-top clearfix">
              <div class="img">
                <img :src="showData.logo" />
              </div>
              <div prop="leftTitle"
                   class="text">
                {{showData.topText}}
              </div>
            </div>
            <div class="page-center">
              <div class="pageright">
                <div class="page-right-title page-right-success-title">
                  {{showData.successText}}
                </div>
                <img :src="loginSuccessUrl" />
              </div>
            </div>
            <div class="page-bottom">©2019 CipherChina.com All rights reserved.</div>
          </div>
        </div>
      </div>
      <div class="formed-form"
           v-else
           key='edit'>
        <Form ref="formValidate"
              :model="formValidate"
              :rules="ruleValidate">
          <div class="formed-form-part">
            <div class="formed-form-part-title">配置登录页</div>
            <div class="formed-form-item page clearfix">
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
                          v-if="type===0">
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
          </div>
          <div class="formed-form-part">
            <div class="formed-form-part-title">配置登录成功页</div>
            <div class="formed-form-item page clearfix">
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
          </div>
        </Form>
        <div class="formed-footer"
             v-if="isEdit">
          <myButton btnType="info"
                    @click="handleSubmit('formValidate')">保存</myButton>
          <myButton @click="cancel">取消</myButton>
        </div>
      </div>
    </transition>
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      showData: {}, // 显示数据
      loginUrl: require("@/assets/wifi-portal-login.png"),
      loginSuccessUrl: require("@/assets/wifi-portal-success.png"),
      logo: "",
      loginDraw: "",
      type: "", // 使用类型
      formValidate: {
        logo: "",
        topText: "",
        loginDraw: "",
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
  props: ["data"],
  created () {
    let data = this.$props.data.wifiPotalPageSettingInfo;
    this.getData(data);
  },

  methods: {
    /**
     * 进入编辑状态，并重新获取数据赋值
     * @param {*void}
     * @author yezi 2019-08-02
     */
    edit () {
      this.isEdit = true;
      this.getData(this.$props.data.wifiPotalPageSettingInfo);
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.isEdit = false;
    },
    /**
     * 获取数据并赋值
     * @param {*Object 基本配置信息，从父组件传递的数据} data
     * @author yezi 2019-08-02
     */
    getData (data) {
      this.showData = Object.assign({}, data);
      this.type = data.portalType;
      this.formValidate.logo = data.logo;
      this.formValidate.topText = data.topText;
      this.formValidate.loginDraw = data.loginDraw;
      this.formValidate.loginText = data.loginText;
      this.formValidate.successText = data.successText;
    },
    /**
     * 提交数据并验证，验证成功执行保存数据函数
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.saveData();
        }
      });
    },
    /**
     * 保存数据，发送请求
     * @param {*void}
     * @author yezi 2019-08-02
     */
    saveData () {
      let params = {
        id: this.showData.id,
        logo: this.logo,
        topText: this.formValidate.topText,
        loginDraw: this.loginDraw,
        loginText: this.formValidate.loginText,
        successText: this.formValidate.successText
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wifiportal/updateOne"
      })
        .then(res => {
          // 成功发送跳转
          if (res.data.return_code === 0) {
            this.$myMessage.success(res.data.return_msg);
            this.isEdit = false;
            this.$emit("ListenChild", this.formValidate); // 验证通过将数据传递到父组件
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /**
     * 左上角logo上传触发
     * @param 详见iview
     * @author yezi 2019-08-02
     */
    handleSuccessLogo (res, file) {
      if (res.status === 0) {
        this.logo = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    /**
     * 左边插画图片上传触发
     * @param 详见iview
     * @author yezi 2019-08-02
     */
    handleSuccessLogoDraw (res, file) {
      if (res.status === 0) {
        this.loginDraw = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    /**
     * 图片上传成功触发
     * @param 详见iview
     * @author yezi 2019-08-02
     */
    updateSuccessModal () {
      this.$Modal.success({
        title: "上传成功",
        content: "图片上传成功",
        width: modal.simpleModal.width
      });
    },
    /**
     * 图片上传失败触发
     * @param 详见iview
     * @author yezi 2019-08-02
     */
    updateFailModal () {
      this.$Modal.error({
        title: "上传失败",
        content: "图片上传失败",
        width: modal.simpleModal.width
      });
    },
    /**
     * 图片上传格式不正确触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
     */
    handleFormatError (file) {
      this.$Modal.error({
        title: "请选择一张图片！",
        content: "文件不正确！ " + file.name + " 该文件不是一个图片"
      });
    },
    /**
     * 图片上传超过2M触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
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
.formed-form-part .ivu-form-item {
  margin-left: 0;
}
/* 显示动画样式 */
.fade-enter {
  opacity: 0;
} /* 显示的开始样式*/
.fade-enter-active {
  transition: opacity 0.5s;
} /* 显示的过度 */
.fade-enter-to {
  opacity: 1;
} /* 显示的结束样式*/
/* 隐藏动画样式 */
.fade-leave {
  opacity: 1;
}
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-leave-to {
  opacity: 0;
}
.config-title {
  text-align: left;
  font-size: 14px;
  margin-bottom: 10px;
}
.page {
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
    height: 50px;
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
    max-width: 240px;
  }
}
.page-bottom {
  margin-top: 10px;
  text-align: center;
  color: #93c0cd;
}
.edit-part .pageright .page-right-title {
  height: 27px;
}
/deep/ .ivu-upload-drag {
  height: 100%;
}
</style>
