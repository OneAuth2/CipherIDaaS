<template>
  <div class="load">
    <div class="show">
      <Spin fix>
        <Icon type="ios-loading"
              size=18
              class="demo-spin-icon-load"></Icon>
        <div>Loading</div>
      </Spin>
    </div>
  </div>
</template>
<script>
// 部分样式代码冗长，未作展示
export default {

};
</script>

<style>
.load {
  position: fixed;
  top: 0px;
  left: 0;
  height: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.2);
}
.show {
  width: 100px;
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
}
.demo-spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}
@keyframes ani-demo-spin {
  from {
    transform: rotate(0deg);
  }
  50% {
    transform: rotate(180deg);
  }
  to {
    transform: rotate(360deg);
  }
}
</style>
