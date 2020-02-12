import Vue from "vue";
import Circle from "./CircleProgress.vue";

let CircleConstructor = Vue.extend(Circle); // 构造函数
let instance; // 实例对象
let seed = 1; // 计数

const CircleProgress = (options = {}) => {
  if (
    typeof options.percent !== "number" ||
    typeof options.showProgress !== "boolean"
  ) {
    return;
  }
  let id = `circlet_${seed++}`;
  instance = new CircleConstructor({
    data: options
  });
  instance.id = id;
  instance.vm = instance.$mount();
  document.body.appendChild(instance.vm.$el);
  instance.dom = instance.vm.$el;
  instance.dom.style.zIndex = 999 + seed;
  return instance.vm;
};

export default CircleProgress;
