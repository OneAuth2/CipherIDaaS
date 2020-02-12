<template>
  <div class="my-tabs">
    <div class="tabs-bar-nav">
      <myButton v-for="(item,index) in navList"
                :key="index"
                btnRadius="none"
                class="tabs-tab"
                :btnType="item.name === currentValue ? 'info' : 'secondary'"
                @click="changeTab(index)">
        {{item.label}}
      </MyButton>
    </div>
    <div class="tabs-content ivu-tabs-content-animated">
      <slot></slot>
    </div>
  </div>
</template>

<script>
export default {
  name: "MyTabs",
  props:
  {
    value: {
      // 接收父组件的value
      type: [String]
    }

  },
  data () {
    return {
      currentValue: this.value, // 保存父组件的value到currentValue变量中，以便在本地维护,现在活跃的pane名称
      navList: [] // 将pane的标题保存到数组中
    };
  },
  computed: {
    // contentStyle () {
    //   const x = this.getTabIndex(this.currentValue);
    //   const p = x === 0 ? "0%" : `-${x}00%`;

    //   let style = {};
    //   if (x > -1) {
    //     style = {
    //       transform: `translateX(${p}) translateZ(0px)`
    //     };
    //   }
    //   return style;
    // }
  },

  watch: {
    // 监听value变化
    value: function (val) {
      this.currentValue = val;
    },
    // 监听currentValue变化，更新对应的pane组件
    currentValue: function () {
      this.updateStatus();
    }
  },
  methods: {
    /**
     * 点击tab触发
     * @param {*Object} tab
     */
    changeTab: function (index) {
      this.activeKey = index;
      var nav = this.navList[index];
      var name = nav.name;
      this.currentValue = name; // 改变当前选中的tab，触发watch
      this.$emit("input", name); // 实现子组件与父组件通信
      this.$emit("on-click", name);
    },
    getTabIndex (name) {
      return this.navList.findIndex(nav => nav.name === name);
    },
    getTabs () {
      // 使用$children遍历子组件，得到所有的pane组件
      return this.$children.filter(function (item) {
        return item.$options.name === "myPane";
      });
    },
    // 更新tabs
    updateNave () {
      this.navList = [];
      this.getTabs().forEach((pane, index) => {
        this.navList.push({
          label: pane.label,
          name: pane.name || index
        });
        // 如果没有设置name，默认设置为索引值
        if (!pane.name) pane.name = index;
        // 设置第一个pane为当前显示的tab
        if (index === 0) {
          if (!this.currentValue) {
            this.currentValue = pane.name || index;
          }
        }
      });
      this.updateStatus();
    },
    updateStatus () {
      var tabs = this.getTabs();
      // 显示当前选中的tab对应的pane组件，隐藏没有选中的
      tabs.forEach(tab => { tab.show = tab.name === this.currentValue; });
    }
  }
};
</script>

<style lang="less" scoped>
.my-tabs {
  overflow: hidden;
  height: 100%;
}
.tabs-bar-nav {
  text-align: center;
}
.tabs-tab {
  user-select: none;
  border-right: 1px solid #cecece;
  &:last-of-type {
    border-right: none;
  }
}
// .ivu-tabs-content-animated {
//   // display: flex;
//   // flex-direction: row;
//   // will-change: transform;
//   // transition: transform 0.3s ease-in-out;
// }
</style>
