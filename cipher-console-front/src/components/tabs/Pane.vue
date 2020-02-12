<template>
  <div class="pane"
       v-show="show">
    <slot></slot>
  </div>
</template>
<script>
export default {
  props: {
    // 用来标识点击的哪个标签
    name: {
      type: String
    },
    // 标签的标题内容
    label: {
      type: String,
      default: ""
    }
  },
  data: function () {
    // show控制显示哪个内容
    return {
      show: true
    };
  },
  computed: {
    paneStyle () {
      if (!this.show) {
        return "visibility:hidden; opacity: 0;";
      } else {
        return "visibility:visible; opacity: 1";
      }
    }
  },
  watch: {
    // 监听label，label变化时，调用updateNav
    label () {
      this.updateNav();
    }
  },
  mounted () {
    // mounted生命周期钩子同样调用updateNav
    this.updateNav();
  },
  methods: {
    // this.$parent访问父组件中的方法，注意在业务代码中尽量不要使用$parent来操作父链，这种方法试用于标签这样独立的组件
    updateNav () {
      this.$parent.updateNave();
    }
  }
};
</script>
<style lang="less" scoped>
.pane {
  // flex-shrink: 0;
  width: 100%;
  transition: opacity 0.3s;
  opacity: 1;
  outline: 0;
}
</style>
