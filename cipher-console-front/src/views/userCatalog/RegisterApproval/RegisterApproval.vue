<template>
  <div class="approval">
    <myTabs :tabList="tabList"
            :tabIndex="tabIndex"
            @changeTab="changeTab">
      <keep-alive>
        <component :is="currentContent">
        </component>
      </keep-alive>
    </myTabs>
  </div>
</template>

<script>
import ApplyList from "./ApplyList.vue";
import ApprovalList from "./ApprovalList.vue";

export default {
  components: {
    "ApplyList": ApplyList,
    "ApprovalList": ApprovalList
  },
  data () {
    return {
      tabIndex: 0,
      currentContent: "ApplyList",
      tabList: [
        {
          index: 0,
          name: "申请列表",
          component: "ApplyList"
        },
        {
          index: 1,
          name: "审批记录",
          component: "ApprovalList"
        }
      ]
    };
  },
  methods: {
    changeTab: function (tab) {
      this.tabIndex = tab.index;
      this.currentContent = tab.component;
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
/deep/ .my-tabs {
  height: 100%;
 .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - 61px");
  }
}
</style>
