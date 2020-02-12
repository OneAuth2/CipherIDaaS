import Message from "./Message.vue";
// 定义插件对象
const MessageBox = {};

// vue的install方法，用于定义vue插件
MessageBox.install = function (Vue, options) {
  let MessageConstructor = Vue.extend(Message); // 构造函数
  let instance; // 实例对象
  let seed = 1; // 计数
  // 实例化vue实例
  const initInstance = () => {
    let id = `Message_${seed++}`;
    instance = new MessageConstructor();
    instance.id = id;
    instance.vm = instance.$mount();
    document.body.appendChild(instance.vm.$el);
    instance.vm.visible = true;
    instance.dom = instance.vm.$el;
    instance.dom.style.zIndex = 999 + seed;
  };
  // 函数
  function showMsgBox (options) {
    initInstance();
    Object.assign(instance, options); //  Object.assign方法只会拷贝源对象自身的并且可枚举的属性到目标对象
    return instance;
  }
  // 在Vue的原型上添加实例方法，以全局调用
  Vue.prototype.$myMessage = {
    success (options = {}) {
      if (typeof options === "string") {
        options = { content: options };
      }
      options.icon = "success";

      return showMsgBox(options);
    },
    fail (options = {}) {
      if (typeof options === "string") {
        options = { content: options };
      }
      options.icon = "fail";
      return showMsgBox(options);
    },
    error (options = {}) {
      if (typeof options === "string") {
        options = { content: options };
      }
      options.icon = "fail";
      return showMsgBox(options);
    }
  };
};

export default MessageBox;
