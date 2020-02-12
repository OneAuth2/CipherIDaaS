<template>
  <div class="bread-crumb">
    <Breadcrumb>
      <span v-for="(item,index) in breadCrumb"
            :key="index">
        <span :class="index==0 || breadCrumb.length-1==index?'':'toclick'"
              @click="handleClick(index,item.href)">{{item.name}}</span>
        <span class="seperator"
              v-if="breadCrumb.length-1>index">/</span>
      </span>
    </Breadcrumb>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  name: "bread-crumb",
  computed: {
    ...mapState(["breadCrumb"])
  },
  watch: {
    $route () {
      // 拼接面包屑数组
      let name = this.$route.meta.title || "";
      let href = this.$route.meta.lsUrl || "";
      let breadNameList = [];
      breadNameList = name.split("/");

      let breadList = [];
      for (let i = 0; i < breadNameList.length; i++) {
        let bread = {};
        bread.name = breadNameList[i];
        bread.href = "";
        if (i === 1) {
          bread.href = href;
        }
        breadList.push(bread);
        if (i > 4) {
          break;
        }
      }
      this.changeBreadCrumb(breadList);
    }
  },
  methods: {
    handleClick (index, href) {
      if (href) {
        this.$router.push({ path: href });
        // let temp = this.breadCrumb.slice(0, index + 1);
        // this.changeBreadCrumb(temp);
      }
    },
    ...mapMutations(["changeBreadCrumb"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/common.less";
.bread-crumb {
  .head;
  text-align: left;
}
.seperator {
  margin: 0 @customMargin;
}
.toclick {
  cursor: pointer;
  transition: color 0.2s;
  color: @colorPrimary;
  font-weight: @fontBold;
}
.toclick:hover {
  color: @colorHover;
}
</style>
