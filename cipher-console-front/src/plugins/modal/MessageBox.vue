<template>
  <div v-show="isShowMessageBox"
       :mask-closable="maskHide"
       @click.self="onCancel"
       class='my-modal'>
    <div :style="{width:width}"
         class="container">
      <div class="header"
           :class="headerCls">
        <Icon :type="iconType" />
        <span>{{title}}</span>
      </div>
      <div class="center">{{content}}</div>
      <div class="footer">
        <myButton btnType="info"
                  v-if="isShowConfimrBtn"
                  @click="confirm">{{confirmVal}}</myButton>
        <myButton v-if="isShowCancelBtn"
                  @click="cancel">{{cancelVal}}</myButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  props: {
    title: { // 标题
      type: String,
      default: ""
    },
    content: {
      type: String,
      default: ""
    },
    width: {
      type: String,
      default: "528px"
    },
    isShowConfimrBtn: { // 是否展示确认按钮
      type: Boolean,
      default: true
    },
    isShowCancelBtn: { // 是否展示取消按钮
      type: Boolean,
      default: true
    },
    confirmVal: {
      type: String, // 确认文字
      default: "确定"
    },
    cancelVal: { // 取消文字
      type: String,
      default: "取消"
    },
    closable: {
      type: Boolean, // 是否有左上角X，默认没有
      default: false
    },
    maskHide: {
      type: Boolean, // 是否可以点击蒙层关闭
      default: true
    },
    icon: {
      type: String,
      default: "info"
    }
  },
  data () {
    return {
      isShowMessageBox: false,
      resolve: "",
      reject: "",
      promise: "" // 保存promise对象
    };
  },
  computed: {
    iconType () {
      let icoType;
      switch (this.icon) {
        case "info":
          icoType = "md-information-circle";
          break;
        case "success":
          icoType = "md-checkmark-circle";
          break;
        case "error":
          icoType = "md-remove-circle";
          break;
        case "confirm":
          icoType = "md-alert";
          break;
      }
      return icoType;
    },
    headerCls () {
      return `header-${this.icon}`;
    }
  },
  methods: {
    // 确定,将promise断定为resolve状态
    confirm () {
      this.isShowMessageBox = false;
      this.resolve("confirm");
      this.remove();
    },
    // 取消,将promise断定为reject状态
    cancel () {
      this.isShowMessageBox = false;
      this.reject("cancel");
      this.remove();
    },
    onCancel () {
      if (!this.maskHide) {
        return;
      }
      this.cancel();
    },
    // 弹出messageBox,并创建promise对象
    showMsgBox () {
      this.isShowMessageBox = true;
      this.promise = new Promise((resolve, reject) => {
        this.resolve = resolve;
        this.reject = reject;
      });
      // 返回promise对象
      return this.promise;
    },
    remove () {
      setTimeout(() => {
        this.destroy();
      }, 100);
    },
    destroy () {
      this.$destroy();
      document.body.removeChild(this.$el);
    }

  }
};

</script>
<style lang="less" scoped>
// 模态框
.my-modal {
  position: fixed;
  z-index: 9999;
  top: 0;
  right: 0;
  left: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.6);
}
.container {
  background: #fff;
  padding: 24px 32px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.header {
  font-size: 16px;
  i {
    font-size: 40px;

    margin-right: 16px;
    position: relative;
    top: -1px;
  }
  span {
    font-size: 20px;
    font-weight: 500;
  }
}
.header-confirm {
  i {
    color: #f7b500;
  }
  span {
    color: #fa6400;
  }
}
.header-success {
  i,
  span {
    color: #81cfe6;
  }
}
.header-error {
  i,
  span {
    color: #fa6400;
  }
}
.center {
  display: block;
  width: 100%;
  font-size: 14px;
  line-height: 20px;
  color: #08142c;
  padding: 24px 56px 0;
  min-height: 44px;
  box-sizing: border-box;
}
.footer {
  width: 100%;
  padding-top: 20px;
  text-align: right;
  .btn {
    margin-left: 8px;
  }
}
</style>
