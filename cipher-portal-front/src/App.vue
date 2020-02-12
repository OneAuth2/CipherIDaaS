<template>
  <div id="app">
    <transition name="fade"
                mode="out-in">
      <router-view />
    </transition>
  </div>
</template>
<script>
export default {
};
</script>

<style lang="less">
html,
body,
#app,
#app > div,
#app > div > div {
  height: 100%;
}
#app {
  font-family: "Microsoft YaHei", "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
#nav {
  padding: 30px;
  a {
    font-weight: bold;
    color: #2c3e50;
    &.router-link-exact-active {
      color: #42b983;
    }
  }
}
#dingding {
  iframe {
    #refreashQrCodeBtn {
      span {
        font-size: 30px;
      }
    }
  }
}
.clearfix {
  &::after,
  &::before {
    content: "";
    display: table;
  }
  &::after {
    clear: both;
  }
}
.ivu-form > .grey > .ivu-form-item-label {
  font-size: 15px !important;
  padding: 10px 8px 8px 0px !important;
}
</style>
