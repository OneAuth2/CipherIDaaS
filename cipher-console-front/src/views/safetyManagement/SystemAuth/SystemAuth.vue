<template>
  <div class="system-auth wrap">
    <myButton btnType="info"
              class="system-auth-change__btn"
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
import PortalAuth from "./PortalAuth.vue";
import AdminAuth from "./AdminAuth.vue";
export default {
  components: {
    "portal": PortalAuth,
    "admin": AdminAuth
  },
  data () {
    return {
      componentStatus: { // 组件是否编辑状态
        isPortalEdit: false,
        isAdminEdit: false
      },
      tabIndex: 0,
      currentContent: "portal",
      tabList: [
        {
          index: 0,
          name: "门户认证设置",
          component: "portal"
        }
        // {
        //   index: 1,
        //   name: "管理后台认证设置",
        //   component: "admin"
        // }
      ]
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      return this.currentContent === "portal" ? !this.componentStatus.isPortalEdit : !this.componentStatus.isAdminEdit;
    }
  },
  methods: {
    /**
   * tabs组件函数，改变tab的时候执行
   * @param {*Object 点击进入的tab，包含index序号和对应的组件名称} tab
   * @author yezi 2019-08-27
   */
    changeTab (tab) {
      this.tabIndex = tab.index;
      this.currentContent = tab.component;
    },
    /**
   * 点击修改时执行，调用子组件方法，进入编辑状态
   * @param {*void}
   * @author yezi 2019-09-06
   */
    changeStatus () {
      this.$refs[this.currentContent].editStatus();
    },
    /**
     * 子组件触发的函数，获取子组件状态，是修改还是显示状态
     * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
     * @author yezi 2019-09-06
     */
    getChildStatus (status) {
      if (status.name === "portal") {
        this.componentStatus.isPortalEdit = status.isEdit;
      } else {
        this.componentStatus.isAdminEdit = status.isEdit;
      }
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.system-auth-change__btn {
  position: absolute;
  right: 8px;
  top: 30px;
  transform: translateY(-50%);
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
    /deep/ .btn {
      background: #fff;
      span {
        .sc(@fontSize18, @colorFontDark);
        font-weight: 500;
      }
    }
  }
  .tabs-content {
    height: calc(~"100% - 61px");
    background-color: #fff;
    .border-radius;
  }
}
</style>
