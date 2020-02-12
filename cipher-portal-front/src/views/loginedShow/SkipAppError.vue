<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <img src="@/assets/img/authLogic/logo.png"
             alt="">
        <div class="titleTop titleLevel1">{{$route.query.error}}</div>
        <div class="login">
          <ButtonSf @click="skip">确定</ButtonSf>
        </div>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
export default {
  components: {
    AuthLogicBasic
  },
  data () {
    return {
    };
  },
  mounted () {
  },
  methods: {
    /**
     * 点击确认，跳转到应用列表页
     * @param {*void}
     * @author yezi 2019-08-12
     */
    skip () {
      this.$router.push("/loginedShow");
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  img {
    width: 90px;
    height: 90px;
    margin: 100px 0 40px 0;
  }
  .titleTop {
    padding-bottom: 40px;
  }

  .login {
    margin-bottom: 30px;
  }
}
</style>
