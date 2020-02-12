<template>
  <div class="ding-syn">
    <myButton btnType="info"
              class="ding-syn-header--config"
              @click="$router.push('/userCatalogDS/dingConfig')">钉钉配置</myButton>
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
import SynDepartment from "./synList/synDepartment.vue";
import SynUser from "./synList/synUser.vue";
export default {
  components: {
    "department": SynDepartment,
    "SynUser": SynUser
  },
  data () {
    return {
      tabIndex: 0,
      currentContent: "department",
      tabList: [
        {
          index: 0,
          name: "同步部门",
          component: "department"
        },
        {
          index: 1,
          name: "同步用户",
          component: "SynUser"
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
.ding-syn {
  height: 100%;
  position: relative;
}
.ding-syn-header--config {
  position: absolute;
  right: 8px;
  top: 15px;
}
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
