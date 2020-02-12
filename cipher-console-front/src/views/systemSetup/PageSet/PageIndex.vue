<template>
  <div class="page-set wrap">
    <myButton btnType="info"
              class="page-set-change__btn"
              v-if="isShowEdit"
              @click="changeStatus">编辑</myButton>
    <myTabs :tabList="tabList"
            :tabIndex="tabIndex"
            @changeTab="changeTab">
      <keep-alive>
        <component :is="currentContent"
                   :ref="currentContent"
                   @statusChange="getChildStatus">
        </component>
      </keep-alive>
    </myTabs>
  </div>
</template>

<script>
import PagePortal from "./PagePortal.vue";
import PageAppList from "./PageAppList.vue";
import PageWeb from "./PageWeb.vue";
// import Pageadmin from "./Pageadmin.vue";

export default {
  components: {
    "portal": PagePortal,
    "appList": PageAppList,
    "web": PageWeb
    // Pageadmin
  },
  data () {
    return {
      componentStatus: { // 组件是否编辑状态
        isPortalEdit: false,
        isAppListEdit: false,
        isWebEdit: false
      },
      tabIndex: 0,
      currentContent: "portal",
      tabList: [
        {
          index: 0,
          name: "门户登陆页面",
          component: "portal"
        },
        {
          index: 1,
          name: "应用列表页面",
          component: "appList"
        },
        {
          index: 2,
          name: "页面标题LOGO",
          component: "web"
        }
      ]
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      let isShow;
      if (this.currentContent === "portal") isShow = !this.componentStatus.isPortalEdit;
      if (this.currentContent === "appList") isShow = !this.componentStatus.isAppListEdit;
      if (this.currentContent === "web") isShow = !this.componentStatus.isWebEdit;
      return isShow;
    }
  },
  methods: {
    /**
   * tabs组件函数，改变tab的时候执行
   * @param {*Object 点击进入的tab，包含index序号和对应的组件名称} tab
   * @author yezi 2019-09-09
   */
    changeTab (tab) {
      this.tabIndex = tab.index;
      this.currentContent = tab.component;
    },
    /**
   * 点击修改时执行，调用子组件方法，进入编辑状态
   * @param {*void}
   * @author yezi 2019-09-09
   */
    changeStatus () {
      this.$refs[this.currentContent].editStatus();
    },
    /**
     * 子组件触发的函数，获取子组件状态，是修改还是显示状态
     * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
     * @author yezi 2019-09-09
     */
    getChildStatus (status) {
      if (status.name === "portal") {
        this.componentStatus.isPortalEdit = status.isEdit;
      } else if (status.name === "appList") {
        this.componentStatus.isAppListEdit = status.isEdit;
      } else {
        this.componentStatus.isWebEdit = status.isEdit;
      }
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.page-set-change__btn {
  position: absolute;
  right: 8px;
  top: 30px;
  transform: translateY(-50%);
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - 61px");
    background-color: #fff;
    .border-radius;
  }
}
</style>
