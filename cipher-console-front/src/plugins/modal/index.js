import msgboxVue from "./MessageBox.vue";
// 定义插件对象
const ModalBox = {};
// vue的install方法，用于定义vue插件
ModalBox.install = function (Vue, options) {
  const MessageBoxInstance = Vue.extend(msgboxVue);
  let currentMsg;
  const initInstance = () => {
    // 实例化vue实例
    currentMsg = new MessageBoxInstance();
    let msgBoxEl = currentMsg.$mount().$el;
    document.body.appendChild(msgBoxEl);
  };
  // 函数
  function showMsgBox (options) {
    // 如果未实例化，先实例化
    if (!currentMsg) {
      initInstance();
    }
    Object.assign(currentMsg, options); //  Object.assign方法只会拷贝源对象自身的并且可枚举的属性到目标对象
    return currentMsg
      .showMsgBox()
      .then(val => {
        currentMsg = null;
        return Promise.resolve(val);
      })
      .catch(err => {
        currentMsg = null;
        return Promise.reject(err);
      });
  }
  // 在Vue的原型上添加实例方法，以全局调用
  Vue.prototype.$myModal = {
    info (options = {}) {
      if (typeof options === "string") {
        options = { title: options };
      }
      options.icon = "info";
      options.isShowCancelBtn = false;
      return showMsgBox(options);
    },
    success (options = {}) {
      if (typeof options === "string") {
        options = { title: options };
      }
      options.icon = "success";
      options.isShowCancelBtn = false;
      return showMsgBox(options);
    },
    // warning (options = {}) {
    //   if (typeof options === "string") {
    //     options.content = options;
    //   }
    //   options.icon = "warning";
    //   options.isShowCancelBtn = false;
    //   return showMsgBox(options);
    // },
    error (options = {}) {
      if (typeof options === "string") {
        options = { title: options };
      }
      options.icon = "error";
      options.isShowCancelBtn = false;
      return showMsgBox(options);
    },
    confirm (options = {}) {
      if (typeof options === "string") {
        options = { title: options };
      }
      options.icon = "confirm";
      options.isShowCancelBtn = true;
      return showMsgBox(options);
    }
  };
};
export default ModalBox;
