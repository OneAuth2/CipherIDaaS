import MyDialog from "./Dialog.vue";

const Dialog = {
  install: function (Vue) {
    Vue.component("Dialog", MyDialog);
  }
};

// 导出组件
export default Dialog;
