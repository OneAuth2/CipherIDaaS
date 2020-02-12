<template>
  <myButton ref="item"
            class="item"
            :btnType="item.id===tab[0].activeId?'dark':'primary'"
            @click="changeTabPage(item)">
    <span href="javascript:;">{{item.resourceName}}</span>
    <Icon v-if="item.id!==0"
          type="ios-close"
          @click.stop="handleClose(item.id)" />
  </myButton>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "Tab",
  props: {
    item: Object
  },
  computed: {
    ...mapState(["tab"])
  },

  methods: {
    showDom () { // 显示tab的dom元素
      return this.$refs.item.showBtn(); // 调用按钮组件myButton内部函数，返回按钮的dom
    },
    changeTabPage (item) {
      this.$router.push({ path: item.href, query: item.query, params: item.params });
      let itemTabs = this.tab;
      this.changeTab(itemTabs);
    },
    handleClose (itemId) {
      this.$emit("closeItem", itemId);
    },

    ...mapMutations(["changeTab"])
  }
};
</script>

<style scoped>
.item {
  margin-right: 8px;
  transition: background-color 0.2s;
}
.item i {
  cursor: pointer;
  font-size: 16px;
  position: relative;
  right: -6px;
  font-weight: 600;
}
</style>
