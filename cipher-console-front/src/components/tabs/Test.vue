<template>
  <div class="tabs-test">
    <myTabs :tabList="tabList"
            :tabIndex="tabIndex"
            @changeTab="changeTab">
      <keep-alive>
        <component :is="currentContent">
        </component>
      </keep-alive>
    </myTabs>
    <myComplexTabs v-model="value"
                   @on-click="tabData">
      <myPane label="标签一"
              name="1">
        标签一的内容
      </myPane>
      <myPane label="标签二"
              name="2">
        标签二的内容
      </myPane>
      <myPane label="标签三"
              name="3">
        标签三的内容
      </myPane>
    </myComplexTabs>
  </div>
</template>

<script>
import One from "../custom/Test.vue";
import Two from "../modal/TransferTest.vue";
export default {
  components: {
    "one": One,
    "two": Two
  },
  data () {
    return {
      tabIndex: 0,
      currentContent: "one",
      tabList: [
        {
          index: 0,
          name: "选项一",
          component: "one"
        },
        {
          index: 1,
          name: "选项二",
          component: "two"
        }
      ],
      value: "1"
    };
  },
  methods: {
    changeTab: function (tab) {
      this.tabIndex = tab.index;
      this.currentContent = tab.component;
    },
    tabData: function (item) {
      console.log(item);
    }
  }
};
</script>

<style scoped lang="less">
.tabs-test {
  padding: 30px;
  text-align: left;
  background-color: #fff;
}
</style>
