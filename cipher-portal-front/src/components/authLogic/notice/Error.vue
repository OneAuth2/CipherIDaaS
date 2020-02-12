<template>
  <div class="error">
    {{msg}}
  </div>
</template>

<script>
export default {
  props: {
    msg: {
      type: String,
      default: ""
    }
  }
};
</script>

<style lang="less" scoped>
.error {
  position: absolute;
  color: #d0021b;
  font-size: 12px;
  text-align: left;
  margin-top: -4px;
}
</style>
