<template>
  <transition name="fade">
    <div class="toast"
         v-show="visible">
      <Icon type="ios-alert"></Icon>
      <span>{{ message }}</span>
    </div>
  </transition>
</template>

<script>
export default {
  name: "toast",
  data () {
    return {
      message: "", // 消息文字
      duration: 3000, // 显示时长，毫秒
      closed: false, // 用来判断消息框是否关闭
      timer: null, // 计时器
      visible: false // 是否显示
    };
  },
  mounted () {
    this.startTimer();
  },
  watch: {
    closed (newVal) {
      if (newVal) {
        this.visible = false;
        this.destroyElement();
      }
    }
  },
  methods: {
    destroyElement () {
      this.$destroy();
      this.$el.parentNode.removeChild(this.$el);
    },
    startTimer () {
      this.timer = setTimeout(() => {
        if (!this.closed) {
          this.closed = true;
          clearTimeout(this.timer);
        }
      }, this.duration);
    }
  }
};
</script>

<style lang="less" scoped>
.toast {
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
