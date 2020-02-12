<template>
  <AuthLogicBasic>
    <div class="main">
      <p class="titleLevel1 adminTitle">塞赋IDaas平台</p>
      <LoginHome></LoginHome>
    </div>
  </AuthLogicBasic>
</template>

<script>
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import LoginHome from "@/components/authLogic/LoginHome.vue";
export default {
  components: {
    AuthLogicBasic,
    LoginHome
  }
};
</script>
<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .adminTitle{
    margin-bottom: 30px;
  }
  height: 515px;
}
</style>
