<template>
  <div class="notice">
    <Icon type="md-checkmark-circle" />
    <span>{{text}}</span>
  </div>
</template>

<script>
export default {
  props: {
    text: {
      type: String,
      default: "成功!"
    },
    show: {
      type: Boolean,
      default: true
    },
    duration: {
      type: Number,
      default: 1.5
    },
    onClose: {
      type: Function
    }
  },
  methods: {
    clearCloseTimer () {
      if (this.closeTimer) {
        clearTimeout(this.closeTimer);
        this.closeTimer = null;
      }
    },
    close () {
      this.clearCloseTimer();
      // this.$emit('close')
      this.onClose();
    }
  },
  mounted () {
    this.clearCloseTimer();

    if (this.duration !== 0) {
      this.closeTimer = setTimeout(() => {
        this.close();
      }, this.duration * 1000);
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.notice {
  z-index: 1;
  padding: 30px;
  border-radius: 4px;
  box-shadow: 0 1px 6px rgba(0, 0, 0, 0.2);
  background: #fff;
  position: fixed;
  overflow: hidden;
  top: 50%;
  left: 50%;
  font-size: @fontSize25;
  i {
    color: rgb(82, 196, 26);
  }
  span {
    font-size: @fontSize18;
    padding-left: 10px;
  }
}
</style>
