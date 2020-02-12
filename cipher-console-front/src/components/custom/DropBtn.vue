<template>
  <span class="btn drop-btn"
        ref="drop-btn"
        :style="{width:width}">
    <span class="drop-btn__text">
      <slot></slot>
    </span>
    <span class="drop-btn__arrow">
      <Icon type="ios-arrow-down" /></span>
  </span>
</template>

<script>
export default {
  props: {
    width: {
      type: String,
      default: ""
    }
  }
};
</script>

<style lang="less" scoped>
.drop-btn {
  display: inline-block;
  height: 32px;
  line-height: 32px;
  padding-right: 30px;
  text-align: center;
  background: rgba(255, 255, 255, 1);
  border-radius: 4px;
  border: 1px solid rgba(167, 167, 167, 1);
  color: #a7a7a7;
  cursor: pointer;
  position: relative;
  .drop-btn__text {
    display: inline-block;
    padding: 0 15px;
    font-size: 12px;
  }
  &:hover {
    color: #81cfe6;
    border-color: #81cfe6;
    .drop-btn__arrow {
      background-color: #81cfe6;
    }
  }
}
.drop-btn__arrow {
  display: inline-block;
  width: 30px;
  height: 32px;
  background-color: #a7a7a7;
  color: #fff;
  border-radius: 4px;
  position: absolute;
  right: -1px;
  top: -1px;
}
</style>
