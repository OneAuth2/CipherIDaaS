<template>
  <div class="was wrap">
    <myButton btnType="info"
              class="was-change__btn"
              v-if="isShowEdit"
              @click="changeStrategy">修改</myButton>
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
import Staff from "./Staff.vue";
import Visitor from "./Visitor.vue";

export default {
  components: {
    "staff": Staff,
    "visitor": Visitor
  },
  data () {
    return {
      componentStatus: { // 组件是否编辑状态
        isStaffEdit: false,
        isVisitorEdit: false
      },
      tabIndex: 0,
      currentContent: "staff",
      tabList: [
        {
          index: 0,
          name: "员工策略",
          component: "staff"
        },
        {
          index: 1,
          name: "访客策略",
          component: "visitor"
        }
      ]
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      return this.currentContent === "staff" ? !this.componentStatus.isStaffEdit : !this.componentStatus.isVisitorEdit;
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
     * @author yezi 2019-08-27
     */
    changeStrategy () {
      this.$refs[this.currentContent].enterEdit();
    },
    /**
     * 子组件触发的函数，获取子组件状态，是修改还是显示状态
     * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
     * @author yezi 2019-08-27
     */
    getChildStatus (status) {
      if (status.name === "staff") {
        this.componentStatus.isStaffEdit = status.isEdit;
      } else {
        this.componentStatus.isVisitorEdit = status.isEdit;
      }
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.was {
  .was-change__btn {
    position: absolute;
    right: 8px;
    top: 30px;
    transform: translateY(-50%);
  }
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
