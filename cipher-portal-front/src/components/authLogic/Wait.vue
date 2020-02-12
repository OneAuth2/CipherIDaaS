<template>
  <div>
    <transition name="fade"
                mode="out-in">
      <div class="main"
           v-if="waitting">
        <img src="@/assets/img/authLogic/logo.png"
             alt="">
        <div class="titleTop">{{title}}</div>
        <div class="titleBottom">欢迎</div>
      </div>
    </transition>
  </div>
</template>

<script>
import { mapState } from "vuex";
export default {
  data () {
    return {
      waitting: true
    };
  },
  computed: {
    title () {
      return this.authLogic.portalConfig.rightTitle;
    },
    ...mapState({ authLogic: "authLogic" })
  },
  mounted () {
    this.init();
  },
  methods: {
    init () {
      setTimeout(() => {
        this.waitting = false;
        this.$emit("close");
      }, 1000);
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/authLogic/common.less";
.main {
  .authLogicOuther;
  .formsBody;
  background-color: #fff;
  z-index: 1;
  img {
    width: 90px;
    height: 90px;
    margin: 100px 0 40px 0;
  }
  .titleTop {
    font-size: @fontSize24;
    padding-bottom: 70px;
  }
  .titleBottom {
    font-size: @fontSize24;
  }
}
</style>
