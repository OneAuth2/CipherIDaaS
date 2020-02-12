<template>
  <div class="page-web formed-wrap">
    <transition name="fade"
                mode='out-in'>
      <!-- 显示部分 -->
      <div class="formed-form"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="formed-form-item">
          <span> 页面标题：</span>
          <span>{{showItem.title}}</span>
        </div>
        <div class="formed-form-item">
          <span> 页面Logo：</span>
          <span><img :src="showItem.iconfont"
                 class="uploadPic" /></span>
        </div>
      </div>
      <!-- 编辑部分 -->
      <div class="formed-form"
           v-else
           key='edit'>
        <Form ref="formItem"
              :model="formItem"
              :label-width="120"
              :rules="ruleValidate">
          <FormItem label="页面标题："
                    prop="title">
            <Input v-model.trim="formItem.title"
                   placeholder="请输入"
                   style="width:300px" />
          </FormItem>
          <FormItem label="页面Logo："
                    prop="iconfont">
            <Upload ref="upload"
                    :show-upload-list="false"
                    :on-success="handleSuccessIconfont"
                    :max-size="1024"
                    :format="['ico']"
                    :on-format-error="handleFormatError"
                    :on-exceeded-size="handleMaxSize"
                    :on-progress="handleProgress"
                    action="/cipher/app/imgUpload"
                    style="display: inline-block;">
              <img :src="formItem.iconfont"
                   class="uploadPic" />
              <span class="link"> 点击上传</span>
              <span class="sub-title">Logo要求为 .ico格式，尺寸32x32</span>
            </Upload>
          </FormItem>
          <FormItem>
            <div class="greyTip">页面标题和页面Logo为显示在浏览器标签页中</div>
          </FormItem>
        </Form>

      </div>
    </transition>
    <div class="formed-footer"
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
      uploadList: [], // 图片上传列表
      showItem: {}, // 显示页面数据
      formItem: {}, // 编辑页面数据
      ruleValidate: { // 表单验证规则
        title: [
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
        name: "web"
      });
      this.getBasedata(); // 进入编辑状态时 重新请求数据，防止上次未保存数据（v-model）显示
    },
    /**
     * 取消编辑状态时,表单进行重置
     * @param {*String} 需要重置的表单名称
     * @author yezi 2019-08-08
     */
    cancel (name) {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "web"
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
        url: "/cipher/setting/titleTagEcho",
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
            url: "/cipher/setting/titleTag",
            data: params
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.$myMessage.success(res.data.return_msg);
                this.getBasedata();
                this.configHead(params);
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
     * 配置标签的信息，ico和title
     * @param {*Object 包含ico图标和title} icon
     * @author yezi 2019-09-17
     */
    configHead (icon) {
      var link = document.querySelector("link[rel*='icon']") || document.createElement("link");
      link.type = "image/x-icon";
      link.rel = "shortcut icon";
      link.href = icon.iconfont;
      document.getElementsByTagName("head")[0].appendChild(link);
      let headTitle = document.getElementsByTagName("head")[0].querySelector("title");
      headTitle.innerText = icon.title;
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
     * @author yezi 2019-08-08
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
     * @author yezi 2019-08-08
     */
    updateFailModal () {
      this.$Modal.error({
        title: "上传失败",
        content: "图片上传失败",
        width: modal.simpleModal.width
      });
    },
    /**
     * 标签logo图片上传触发
     * @param 参数详见iview
     * @author yezi 2019-08-08
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
     * 图片上传格式不正确触发
     * @param 参数详见iview
     * @author yezi 2019-08-08
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
     * @author yezi 2019-08-08
     */
    handleMaxSize (file) {
      this.$Notice.warning({
        title: "大小超过1M",
        desc: "该文件  " + file.name + "太大，不能超过1M"
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.edit-container {
  height: calc(~"100% - @{footerHeight}");
}
img {
  max-width: 100%;
  max-height: 100%;
}
.show-content {
  // border: 1px solid #ddd;
  width: 800px;
  margin: 50px auto;
  text-align: left;
  font-size: 14px;
  .part {
    padding: 15px;
  }
  .left,
  .right {
    display: inline-block;
    vertical-align: middle;
    width: 50%;
  }
  .left {
    text-align: right;
  }
  img {
    vertical-align: middle;
  }
  .greyTip {
    margin-left: 15px;
  }
}
.uploadPic {
  max-width: 50px;
  vertical-align: middle;
}
.greyTip {
  color: #c5c8ce;
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
