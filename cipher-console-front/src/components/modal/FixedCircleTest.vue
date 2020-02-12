<template>
  <div class="test">
    <FixedCircle :percent="100" />
  </div>
</template>
<script>
import FixedCircle from "@/components/modal/FixedCircle.vue";
export default {
  components: {
    FixedCircle
  }
};
</script>
<style lang="less" scoped>
.test {
}
</style>
