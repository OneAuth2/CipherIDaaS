<template>
  <div>
    测试
    <button @click="test">测试</button>
  </div>
</template>

<script>
export default {
  methods: {
    test () {
      this.$toast("登陆超时，请重新登陆");
    }
  }
};
</script>

<style lang="scss" scoped>
</style>
