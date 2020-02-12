<template>
  <div class="page-app-list wrap">
    <transition name="fade"
                mode='out-in'>
      <!-- 显示部分 -->
      <div class="container"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="logo clearfix">
          <img :src="showItem.iconfont"
               class="img" />
          <span class="text">{{showItem.title}}</span>
          <img :src="loginedTopUrl"
               class="right-pic" />
        </div>
        <div class="portal-content">
          <img :src="loginUrl" />
        </div>
      </div>
      <!-- 编辑部分 -->
      <div class="container edit-part edit-container"
           v-else
           key='edit'>
        <Form ref="formItem"
              :model="formItem"
              :rules="ruleValidate"
              class="logo clearfix">
          <FormItem class="img"
                    prop="iconfont">
            <Upload ref="upload"
                    :show-upload-list="false"
                    :on-success="handleSuccess"
                    :format="['jpg','jpeg','png']"
                    :max-size="2048"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleMaxSize"
                    :on-progress="handleProgress"
                    type="drag"
                    action="/cipher/app/imgUpload"
                    style="margin-top:-5px;display: inline-block">
              <div class="logo-bg"><img :src="formItem.iconfont" /></div>
            </Upload>
          </FormItem>
          <FormItem prop="title"
                    class="text">
            <Input v-model.trim="formItem.title" />
          </FormItem>

          <img :src="loginedTopUrl"
               class="right-pic" />

        </Form>
        <div class="portal-content">
          <img :src="loginUrl" />
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
      loginedTopUrl: require("@/assets/logined-show-top.jpg"),
      loginUrl: require("@/assets/logined-show.png"),
      uploadList: [], // 图片上传列表
      showItem: { // 显示页面数据
        title: "安全访问门户",
        iconfont: require("@/assets/app-list-logo.png")
      },
      formItem: { // 编辑页面数据
        title: "安全访问门户",
        iconfont: require("@/assets/app-list-logo.png")
      },
      ruleValidate: { // 表单验证规则
        title: [
          { required: true, message: "标题不能为空", trigger: "blur" },
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
        name: "appList"
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
        name: "appList"
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
        url: "/cipher/setting/applicationPageEcho",
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
            iconfont: this.formItem.iconfont
          };
          this.axios({
            method: "post",
            url: "/cipher/setting/applicationPage",
            data: params
          })
            .then(res => {
              console.log(res);
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
    display: inline-block;
    width: 200px;
    line-height: 50px;
  }
}
img {
  max-width: 100%;
  max-height: 100%;
}
.logo-bg {
  background: #7eb5cb;
  height: 39px;
}
.logo {
  height: 50px;
  line-height: 50px;
  background-color: #7eb5cb;
  padding: 0 200px;
  color: #fff;
  margin-top: -5px;
  > .img {
    float: left;
    margin-top: 10px;
    max-width: 80px;
    max-height: calc(~"100% - 20px");
  }
  > .text {
    float: left;
    font-size: 12px;
    margin-left: 10px;
  }
  .right-pic {
    float: right;
    margin-top: 8px;
    max-width: calc(~"100% - 270px");
  }
}
.portal-content img {
  width: 100%;
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
@media (max-width: 1600px) {
  .right-pic {
    margin-top: 10px !important;
  }
}
@media (max-width: 1400px) {
  .right-pic {
    margin-top: 15px !important;
  }
}
</style>
