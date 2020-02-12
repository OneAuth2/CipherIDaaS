<template>
  <div class="page-admin wrap">
    <transition name="fade"
                mode='out-in'>
      <!-- 显示部分 -->
      <div class="container"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="show-content">
          <div class="logo clearfix">
            <img :src="showItem.iconfont"
                 class="img" />
            <span class="text">{{showItem.leftTitle}}</span>
          </div>
          <div class="title">{{showItem.title}}</div>
          <div class="portal-content">
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
      <div class="container edit-part edit-container"
           v-else
           key='edit'>
        <div class="show-content">
          <Form ref="formItem"
                :model="formItem"
                :rules="ruleValidate">
            <div class="logo clearfix">
              <FormItem class="img"
                        prop="iconfont">
                <Upload ref="upload"
                        :show-upload-list="false"
                        :on-success="handleSuccess"
                        :format="['jpg','jpeg','png']"
                        :max-size="2048"
                        :on-format-error="handleFormatError"
                        :on-exceeded-size="handleMaxSize"
                        type="drag"
                        action="/cipher/app/imgUpload"
                        style="width:50px;display: inline-block;">
                  <img :src="formItem.iconfont" />
                </Upload>
              </FormItem>
              <FormItem prop="leftTitle"
                        class="text">
                <Input v-model.trim="formItem.leftTitle" />
              </FormItem>
            </div>
            <FormItem class="title"
                      prop="title"> <Input v-model.trim="formItem.title" /></FormItem>
            <FormItem class="right"
                      prop="rightTitle">
              <div class="login">
                <Input v-model.trim="formItem.rightTitle" />
                <img :src="loginUrl" />
              </div>
            </FormItem>
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
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      loginUrl: require("@/assets/admin-login.png"),
      showItem: {
        title: "赛赋IDaaS平台",
        leftTitle: "CipherChina智慧互联",
        rightTitle: "CipherChina智慧互联",
        iconfont: require("@/assets/login-logo.png")
      },
      formItem: {
        title: "赛赋IDaaS平台",
        leftTitle: "CipherChina智慧互联",
        rightTitle: "CipherChina智慧互联",
        iconfont: require("@/assets/login-logo.png")
      },
      ruleValidate: {
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        leftTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" },
          { type: "string", max: 30, message: "标题不能超过30个字符", trigger: "blur" }],
        rightTitle: [{ required: true, message: "标题不能为空", trigger: "blur" },
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
        name: "admin"
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
        name: "admin"
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
        url: "/cipher/setting/managePageEcho",
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
        console.log(valid);
        if (valid) {
          let params = {
            title: this.formItem.title,
            leftTitle: this.formItem.leftTitle,
            rightTitle: this.formItem.rightTitle,
            iconfont: this.formItem.iconfont
          };
          this.axios({
            method: "post",
            url: "/cipher/setting/managePage",
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
              this.$Notice.warning({
                title: "网络未知错误！",
                desc: error
              });
            });
        } else {
          this.$myMessage.error("表单验证失败，请重试！");
        }
      });
    },
    /**
     * 图片上传成功触发提示框
     * @param 详见iview
     * @author yezi 2019-08-02
     */
    handleSuccess (res, file) {
      if (res.status === 0) {
        this.formItem.iconfont = res.imgName;
        this.$Modal.success({
          title: "上传成功",
          content: "图片上传成功",
          width: modal.simpleModal.width
        });
      } else {
        this.$Modal.error({
          title: "上传失败",
          content: "图片上传失败",
          width: modal.simpleModal.width
        });
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
@import "~@/assets/styles/common.less";
.container {
  height: 100%;
  overflow: auto;
  padding: 20px;
}
.edit-container {
  height: calc(~"100% - @{footerHeight}");
}
.edit-part {
  /deep/ .ivu-input-wrapper {
    line-height: 60px;
    height: 60px;
  }
}
img {
  max-width: 100%;
  max-height: 100%;
}
.show-content {
  border: 1px solid #ddd;
  padding: 15px;
  .logo {
    height: 60px;
    line-height: 60px;
    .img {
      float: left;
      margin-top: 3px;
      max-width: 80px;
      max-height: calc(~"100% - 6px");
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
.login {
  border: 4px solid #93c0cd;
  border-radius: 10px;
  span {
    font-size: 18px;
    color: #93c0cd;
    display: block;
    width: 100px;
    margin: auto;
    padding: 25px 0 10px 0;
  }
  img {
    width: 80%;
  }
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
