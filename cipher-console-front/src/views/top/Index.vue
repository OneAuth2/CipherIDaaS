<template>
  <div class="top">
    <!-- <Icon type="ios-arrow-back" @click="collapse"/> -->
    <!-- <i class="ivu-icon ivu-icon-md-menu menu-icon" @click="toggleCollapse"></i> -->
    <span>赛赋身份认证和授权系统管理后台</span>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data () {
    return {
      collapse: true
    };
  },
  computed: {
    ...mapState(["top"])
  },
  methods: {
    toggleCollapse () {
      console.log(this.top.toggle);
      let toggle = !this.top.toggle;
      console.log(toggle);
      this.changeTop(toggle);
    },
    ...mapMutations(["changeTop"])
  }
};
</script>

<style scoped>
.top {
  position: relative;
}
.top > i {
  position: absolute;
  left: 0;
  top: 20px;
  font-size: 30px;
}
</style>
