<template>
  <div>
    <span v-on:click="run"
          :disabled="isDisabled || time > 0"
          :class="{'disabled':isDisabled || time > 0}">{{ text }}</span>
  </div>
</template>

<script>
export default {
  props: {
    second: {
      type: Number,
      default: 60
    },
    disabled: {
      type: Boolean,
      default: false
    }
  },
  data: function () {
    return {
      time: 0,
      isDisabled: this.disabled
    };
  },
  methods: {
    run: function () {
      this.$emit("click");
    },
    start: function () {
      this.time = this.second;
      this.timer();
    },
    newStart: function (intervalTime) {
      this.time = intervalTime;
      this.timer();
    },
    stop: function () {
      this.time = 0;
      this.isDisabled = false;
    },
    setDisabled: function (val) {
      this.isDisabled = val;
    },
    timer: function () {
      if (this.time > 0) {
        this.time--;
        setTimeout(this.timer, 1000);
      } else {
        this.isDisabled = false;
      }
    }
  },
  computed: {
    text: function () {
      return this.time > 0 ? "重新获取 (" + this.time + "s)" : "获取验证码";
    }
  }
};
</script>

<style lang="less" scoped>
span {
  color: #fff;
  background-color: #81cfe6;
  font-size: 14px;
  display: inline-block;
  width: 140px;
  text-align: center;
  height: 44px;
  line-height: 44px;
  border-radius: 5px;
  cursor: pointer;
}
.disabled {
  background-color: #c5c8ce;
  pointer-events: none;
  cursor: not-allowed;
}
</style>
