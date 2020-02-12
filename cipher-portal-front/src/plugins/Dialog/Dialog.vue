<template>
  <div>
    <transition name="fade">
      <div class="content"
           v-if='show'>
        <Icon type="ios-alert"></Icon>
        <span>{{title}}</span>
      </div>
    </transition>
  </div>
</template>

<script>
export default {
  props: {
    title: {
      type: String,
      default: "请稍等!"
    },
    show: {
      type: Boolean,
      default: false
    }
  }
};
</script>

<style lang="less" scoped>
.content {
  position: fixed;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  z-index: 1001;
  width: 335px;
  height: 98px !important;
  line-height: 98px;
  border: 1px solid #9b9b9b;
  background-color: #9b9b9b;
  color: #fff;
  border-radius: 10px;
  text-align: center;
  font-size: 18px;
  /deep/ i {
    font-size: 28px;
    margin-right: 3px;
  }
}
.fade-enter,
.fade-leave-to {
  opacity: 0;
}
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s;
}
</style>
