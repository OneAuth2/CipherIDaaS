<template>
  <div>
    <AuthLogicBasic>
      <div class="main">
        <div class="title titleLevel1">未匹配到账号，请选择
          <template v-if="isRegist">注册或</template>
          绑定
        </div>
        <ButtonSf class="regist"
                  @click="regist"
                  v-if="isRegist">注册账号</ButtonSf>
        <ButtonSf @click="bind">绑定</ButtonSf>
      </div>
    </AuthLogicBasic>
  </div>
</template>

<script>
import AuthLogicBasic from "@/components/authLogic/AuthLogicBasic.vue";
import { mapState } from "vuex";
export default {
  components: {
    AuthLogicBasic
  },
  computed: {
    title () {
      return this.authLogic.portalConfig.rightTitle;
    },
    isRegist () {
      return this.authLogic.registMode.regist === 0;
    },
    ...mapState({ authLogic: "authLogic" })
  },
  methods: {
    regist () {
      this.$router.push("/authLogic/newUserRegist/accountRegist");
    },
    bind () {
      this.$router.push("/authLogic/newUser/manualInput");
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  .title {
    padding: 127px 0 101px 0;
  }
  .regist {
    padding-bottom: 21px;
  }
}
</style>
