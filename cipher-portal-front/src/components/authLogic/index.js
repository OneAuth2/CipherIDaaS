import ButtonSf from "@/components/authLogic/button/ButtonSf.vue";
import Wait from "@/components/authLogic/Wait.vue";
import Error from "@/components/authLogic/notice/Error.vue";
import ButtonCode from "@/components/authLogic/button/ButtonCode.vue";
function plugin (Vue) {
  if (plugin.installed) {
    return;
  }
  Vue.component("ButtonSf", ButtonSf);
  Vue.component("Wait", Wait);
  Vue.component("ErrorSf", Error);
  Vue.component("ButtonCode", ButtonCode);
}

export default plugin;
