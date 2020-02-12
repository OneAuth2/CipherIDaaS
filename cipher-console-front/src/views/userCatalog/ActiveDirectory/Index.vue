<template>
  <div class="text">
    <transition name="fade" mode="out-in">
      <!-- <keep-alive> -->
        <router-view/>
      <!-- </keep-alive> -->
    </transition>
  </div>
</template>

<script>
export default {};
</script>
<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.text {
 // background-color: @colorBg;
  height: calc(~"100% - 10px") !important;
}
</style>
