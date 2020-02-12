<template>
  <div>
    <span @click="ok">
      <slot>提交</slot>
    </span>
  </div>
</template>

<script>
export default {
  methods: {
    ok () {
      this.$emit("click");
    }
  }
};
</script>

<style lang="less" scoped>
span {
  color: #fff;
  background-color: #81cfe6;
  font-size: 16px;
  display: inline-block;
  width: 212px;
  height: 44px;
  line-height: 44px;
  text-align: center;
  border-radius: 5px;
  cursor: pointer;
}
</style>
