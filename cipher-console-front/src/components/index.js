import FixedCircle from "@/components/modal/FixedCircle.vue";
import Button from "@/components/custom/Button.vue";
import DropBtn from "@/components/custom/DropBtn.vue";
import Select from "@/components/custom/Select.vue";
import Input from "@/components/custom/Input.vue";
import Tabs from "@/components/tabs/Tabs.vue";
import TabsComplex from "@/components/tabs/TabsComplex.vue";
import pane from "@/components/tabs/Pane.vue";
function plugin (Vue) {
  if (plugin.installed) {
    return;
  }
  Vue.component("FixedCircle", FixedCircle);
  Vue.component("myButton", Button);
  Vue.component("myDropBtn", DropBtn);
  Vue.component("mySelect", Select);
  Vue.component("myInput", Input);
  Vue.component("myTabs", Tabs);
  Vue.component("myComplexTabs", TabsComplex);
  Vue.component("myPane", pane);
}

export default plugin;
