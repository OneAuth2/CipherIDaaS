<template>
  <div class="my-tabs">
    <div class="tabs-bar-nav">
      <myButton v-for="tab in tabList"
                :key="tab.index"
                btnRadius="none"
                class="tabs-tab"
                :btnType="tabIndex === tab.index ? 'info' : 'secondary'"
                @click="changeTab(tab)">
        {{tab.name}}
      </MyButton>
    </div>
    <div class="tabs-content">
      <!-- <transition name="slide"
                  mode="out-in"> -->
      <slot></slot>
      <!-- </transition> -->
    </div>
  </div>
</template>

<script>
export default {
  name: "MyTabs",
  props:
  {
    tabList: Array,
    tabIndex: Number
  },
  data () {
    return {
    };
  },
  methods: {
    changeTab: function (tab) {
      this.$emit("changeTab", tab);
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
.tabs-content {
  overflow: hidden;
}
.slide-enter-active,
.slide-leave-active {
  transition: all 0.1s;
}
.slide-enter {
  opacity: 0;
  // transform: translateX(100%);
}
.slide-leave-to {
  opacity: 0;
  // transform: translateX(-100%);
}
</style>
