<template>
  <div id="app">
    <template v-if="$route.path === '/login'">
      <transition name="fade"
                  mode="out-in">
        <router-view />
      </transition>
    </template>
    <template v-else>
      <TopIndex></TopIndex>
      <div class="main">
        <Row>
          <Col class="appLeft"
               span="4">
          <NavBarExpanded></NavBarExpanded>
          </Col>
          <Col class="appRight"
               span="20">
          <Tab></Tab>
          <BreadCrumb />
          <div class="content">
            <transition name="fade"
                        mode="out-in">
              <!-- <keep-alive> -->
              <router-view />
              <!-- </keep-alive> -->
            </transition>
          </div>
          </Col>
        </Row>
      </div>
    </template>
  </div>
</template>
<script>
import NavBarExpanded from "@/views/left/NavBarExpanded";

import TopIndex from "@/views/top/TopIndex";
import Tab from "@/views/tab/Index";
import BreadCrumb from "@/views/tab/breadCrumb.vue";

export default {
  name: "App",
  components: {
    NavBarExpanded,
    TopIndex,
    Tab,
    BreadCrumb
  }
};
</script>

<style lang="less">
@import "~@/assets/styles/detail.less";
@import "~@/assets/styles/custom.less";
html,
body {
  height: 100%;
}
* {
  user-select: none;
}
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  height: 100%;
  min-width: 1280px;
  background-color: @colorBg;
}

.main {
  height: calc(~"100% - @{headerHeight}");
}
.main > div {
  margin-left: 0 !important;
  margin-right: 0 !important;
}
.appLeft {
  min-width: @leftWidth;
  overflow-y: auto;
  overflow-x: hidden;
  width: @leftWidth !important;
}
.main > div,
.appLeft,
.appLeft > div,
.appRight,
.content > div {
  height: 100%;
}
// 右侧
.appRight {
  width: calc(~"100% - @{leftWidth}") !important;
  padding-left: @customPadding;
}
// 面包屑导航
.bread-crumb {
  margin: @customMargin 0;
}
.content {
  height: calc(~"100% - 2 * @{headerHeight} - 2 * @{customMargin}");
  .border-radius;
}
.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}

// 模态弹窗公共样式-开始
.simpleModalContent {
  color: red;
}
.ivu-modal-wrap {
  display: flex;
  align-items: center;
  justify-content: center;
  .ivu-modal {
    top: 0;
  }
  .header {
    .right {
      font-size: @fontSizePercent80;
      font-weight: @fontNormal;
      padding-left: 10px;
      color: #666;
    }
  }
  .center {
    .ivu-form-item {
      label {
        width: 82px;
        font-size: @fontSize14;
        text-align: right;
      }
      .ivu-form-item-content {
        margin-left: 82px;
        input {
          font-size: @fontSize14;
        }
      }
    }
  }

  // 通过coverage类覆盖上面样式
  .center.coverage {
    .ivu-form-item {
      label {
        width: 100px;
      }
      .ivu-form-item-content {
        margin-left: 100px;
      }
    }
  }
}
// 模态弹窗公共样式 -结束

// 滚动条样式-开始
&::-webkit-scrollbar {
  width: 6px;
  height: 8px;
  background: transparent;
}

&::-webkit-scrollbar-thumb {
  background: hsla(0, 0%, 53%, 0.6);
  border-radius: 4px;
}
&::-webkit-scrollbar-track {
  background: hsla(0, 0%, 53%, 0.1);
}

// 滚动条样式-结束
</style>
