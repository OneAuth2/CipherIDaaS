<template>
  <div class="page-portal wrap">
    <transition name="fade"
                mode='out-in'>
      <!-- 显示部分 -->
      <div class="container"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="show-content">
          <div class="logo clearfix">
            <img :src="showItem.iconfont"
                 class="logoImg" />
            <span class="text">{{showItem.leftTitle}}</span>
          </div>
          <div class="title">{{showItem.title}}</div>
          <div class="portal-content clearfix">
            <div class="left"> <img :src="showItem.logo" /></div>
            <div class="right">
              <div class="login">
                <span>{{showItem.rightTitle}}</span>
                <img :src="loginUrl" />
              </div>
            </div>
          </div>
        </div>
      </div>
      <!-- 编辑部分 -->
      <div class="container edit-container"
           v-else
           key='edit'>
        <div class="show-content">
          <Form ref="formItem"
                :model="formItem"
                :rules="ruleValidate">
            <div class="logo clearfix">
              <FormItem class="logoImg"
                        prop="iconfont">
                <Upload ref="upload"
                        :show-upload-list="false"
                        :on-success="handleSuccessIconfont"
                        :max-size="10240"
                        :format="['jpg','jpeg','png']"
                        :on-format-error="handleFormatError"
                        :on-exceeded-size="handleMaxSize"
                        :on-progress="handleProgress"
                        type="drag"
                        action="/cipher/app/imgUpload">
                  <img :src="formItem.iconfont"
                       class="logoImgEdit" />
                </Upload>
              </FormItem>
              <FormItem prop="leftTitle"
                        class="text">
                <Input v-model.trim="formItem.leftTitle" />
              </FormItem>
            </div>
            <FormItem class="title"
                      prop="title"> <Input v-model.trim="formItem.title" /></FormItem>
            <div class="clearfix relative">
              <FormItem class="left ct"
                        prop="logo">
                <Upload ref="upload"
                        :show-upload-list="false"
                        :on-success="handleSuccessLogo"
                        :max-size="10240"
                        :format="['jpg','jpeg','png']"
                        :on-format-error="handleFormatError"
                        :on-exceeded-size="handleMaxSize"
                        :on-progress="handleProgress"
                        type="drag"
                        action="/cipher/app/imgUpload"
                        style="display: inline-block;">
                  <img :src="formItem.logo" />
                </Upload>
              </FormItem>
              <FormItem class="right"
                        prop="rightTitle">
                <div class="login rightEdit">
                  <Input v-model.trim="formItem.rightTitle" />
                  <img :src="loginUrl" />
                </div>
              </FormItem>
            </div>
          </Form>
        </div>
      </div>
    </transition>
    <div class="btn-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="handleSubmit('formItem')">保存</myButton>
      <myButton @click="cancel('formItem')">取消</myButton>
    </div>
    <!-- 图片上传进度条 -->
    <div v-for="(item,index) in uploadList"
         :key="index">
      <FixedCircle v-if="item.showProgress"
                   :percent="item.percentage"></FixedCircle>
    </div>
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      loginUrl: require("@/assets/portal-login.jpg"),
      uploadList: [], // 图片上传列表
      showItem: { // 显示页面数据
        title: "赛赋身份服务",
        leftTitle: "CipherChina智慧互联",
        rightTitle: "CipherChina智慧互联",
        iconfont: require("@/assets/login-logo.png"),
        logo: require("@/assets/login-pic.png")
      },
      formItem: { // 编辑页面数据
        title: "赛赋身份服务",
        leftTitle: "CipherChina智慧互联",
        rightTitle: "CipherChina智慧互联",
        iconfont: require("@/assets/login-logo.png"),
        logo: require("@/assets/login-pic.png")
      },
      ruleValidate: { // 表单验证规则
        title: [
          // { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        leftTitle: [
          // { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        rightTitle: [
          // { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }]
      }
    };
  },
  mounted () {
    this.getBasedata();
  },
  methods: {
    /**
     * 进入编辑状态时 并重新请求数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    editStatus () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "portal"
      });
      this.getBasedata(); // 进入编辑状态时 重新请求数据，防止上次未保存数据（v-model）显示
    },
    /**
     * 取消编辑状态时,表单进行重置
     * @param {*String} 需要重置的表单名称
     * @author yezi 2019-08-02
     */
    cancel (name) {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "portal"
      });
      this.$refs[name].resetFields();
    },
    /**
     * 获取页面数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getBasedata () {
      this.axios({
        method: "post",
        url: "/cipher/setting/doorPageEcho",
        data: {}
      })
        .then(res => {
          if (res.data.return_code === 0) {
            if (JSON.stringify(res.data.return_result) !== "{}") {
              this.showItem = JSON.parse(JSON.stringify(res.data.return_result));
              this.formItem = res.data.return_result;
            }
          } else {
            this.$myMessage.error(res.data.return_msg);
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
     * 保存页面配置，发送请求
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let params = {
            title: this.formItem.title,
            leftTitle: this.formItem.leftTitle,
            rightTitle: this.formItem.rightTitle,
            iconfont: this.formItem.iconfont,
            logo: this.formItem.logo
          };
          this.axios({
            method: "post",
            url: "/cipher/setting/doorPage",
            data: params
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.$myMessage.success(res.data.return_msg);
                this.getBasedata();
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
    },
    /**
    * 文件上传时的钩子
    * @param 详见iview
    * @author yezi 2019-08-09
    */
    handleProgress (event, file, fileList) {
      // uploadList 就是 文件列表，渲染的 uplist 是个数组，所以要把filelist 赋值给他
      this.uploadList = fileList;
    },
    /**
     * 图片上传成功触发提示框
     * @param {*void}
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
     * 图片上传失败触发提示框
     * @param {*void}
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
     * 左上角logo图片上传触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
     */
    handleSuccessIconfont (res, file) {
      if (res.status === 0) {
        this.formItem.iconfont = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    /**
     * 左边插画图片上传触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
     */
    handleSuccessLogo (res, file) {
      if (res.status === 0) {
        this.formItem.logo = res.imgName;
        this.updateSuccessModal();
      } else {
        this.updateFailModal();
      }
    },
    /**
     * 图片上传格式不正确触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
     */
    handleFormatError (file) {
      this.$Notice.warning({
        title: "请选择一张图片！",
        desc: "文件不正确！ " + file.name + " 该文件不是一个图片"
      });
    },
    /**
     * 图片上传超过10M触发
     * @param 参数详见iview
     * @author yezi 2019-08-02
     */
    handleMaxSize (file) {
      this.$Notice.warning({
        title: "大小超过10M",
        desc: "该文件  " + file.name + "太大，不能超过10M"
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
/deep/ .ivu-upload {
  line-height: 1;
}
.container {
  height: 100%;
  overflow: auto;
  padding: 20px;
  max-width: 1400px;
  margin: auto;
}
.edit-container {
  height: calc(~"100% - @{footerHeight}");
}
img {
  max-width: 100%;
  max-height: 100%;
}
.show-content {
  padding: 15px;
  .logo {
    height: 30px;
    line-height: 30px;
    vertical-align: middle;
    margin-bottom: 5px;
    > div {
      margin-bottom: 0;
    }
    .logoImg {
      float: left;
      .logoImgEdit {
        height: 30px;
        width: auto;
      }
    }
    .text {
      float: left;
      font-size: 18px;
      color: #93c0cd;
      margin-left: 10px;
      margin-left: 10px;
    }
  }
  .title {
    font-size: 18px;
    color: #93c0cd;
    margin-bottom: 30px;
    text-align: center;
    /deep/ .ivu-form-item-content {
      display: inline-block;
      width: 200px;
    }
  }
  /deep/ .ivu-input-wrapper {
    display: inline-block;
    width: 200px;
  }
  .left,
  .right {
    display: inline-block;
    width: 50%;
    padding: 15px 80px;
    vertical-align: middle;
  }
}
.left {
  left: 0;
}
.login {
  border: 4px solid #93c0cd;
  border-radius: 10px;
  span {
    font-size: 18px;
    color: #93c0cd;
    display: block;
    width: 294px;
    margin: auto;
    padding: 24px 0 10px 0;
  }
  img {
    width: 90%;
    margin-bottom: 24px;
  }
}
.rightEdit {
  padding-top: 25px;
}
.footer {
  padding: 10px 0;
  button {
    margin-right: 20px;
  }
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
</style>
