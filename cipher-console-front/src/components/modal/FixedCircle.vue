<template>
  <div class="fixed">
    <!-- 圆形进度环 -->
    <i-circle :percent="percent"
              stroke-color="#5cb85c"
              class="center">
      <Icon v-if="percent == 100"
            type="ios-checkmark"
            size="60"
            style="color:#5cb85c"></Icon>
      <span v-else
            style="font-size:24px">{{ percent }}%</span>
    </i-circle>
  </div>
</template>

<script>
export default {
  props: {
    percent: {
      type: Number,
      default: 0
    }
  }
};
</script>

<style lang="less" scoped>
.fixed {
  position: fixed;
  left: 0;
  right: 0;
  top: 0;
  bottom: 0;
  background: rgba(0, 0, 0, 0.5);
  z-index: 1024;
}
.center {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
</style>
