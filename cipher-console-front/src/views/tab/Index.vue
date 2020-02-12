<template>
  <div class="tab">
    <div class="middle"
         ref="middle">
      <div class="left"
           ref="left"
           @click="handleChangeTab('left')">
        <myButton btnType="primary"
                  class="leftIcon">
          <Icon type="ios-rewind" />
        </myButton>
      </div>
      <div :style="offsetStyle"
           ref="tabContent"
           class="tabContent">
        <Tab v-for="item in tabList"
             :key="item.id"
             :item="item"
             ref="tab"
             @closeItem="handleClose" />
      </div>
      <div class="right"
           ref="right">
        <myButton class="rightIcon"
                  btnType="primary"
                  @click="handleChangeTab('right')">
          <Icon type="ios-fastforward" />
        </myButton>
        <Dropdown>
          <myDropBtn class="tab--close">关闭操作</myDropBtn>
          <DropdownMenu slot="list">
            <DropdownItem @click.native="handleCloseAll">关闭全部选项卡</DropdownItem>
            <DropdownItem @click.native="handleCloseOther">关闭其它选项卡</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import Tab from "./Tab.vue";

export default {
  name: "TabIndex",
  components: {
    Tab
  },
  data () {
    return {
      show: true,
      isTabGroupChange: false,
      offsetStyle: {
        marginLeft: "0px"
      }
    };
  },
  computed: {
    tabList: function () {
      if (this.tab.length) {
        return this.tab;
      }
      return [];
    },
    ...mapState(["tab"])
  },
  watch: {
    $route () {
      let activeId = this.tabList[0].activeId; // 目前active的id

      // 存储tab的href，query，params,防止其他tab页面切换时没有数据
      this.changeSelTabInfo(this.$route);

      // 点击tab时改变位置
      this.$nextTick(() => {
        let tabFrameWidth = this.$refs.middle.offsetWidth; // tab显示的宽度
        let activeOffset = this.$refs.tab[activeId].showDom().offsetLeft + this.$refs.tab[activeId].showDom().offsetWidth + 120; // 到active的tab的距离加上自身宽度
        if (activeOffset > tabFrameWidth) {
          if (this.tabList[0].activeId > 6) {
            let marginLeftTab = -(this.tabList[0].activeId - 6) * 150;
            this.$refs.tabContent.style.marginLeft = marginLeftTab + "px";
          } else {
            this.$refs.tabContent.style.marginLeft = 0;
          }
        } else {
          this.$refs.tabContent.style.marginLeft = 0;
        }

        if (this.tabList[0].activeId >= 7) {
          this.tabList[0].groupTab.currentNum = 1;
        } else {
          this.tabList[0].groupTab.currentNum = 0;
        }
      });
    }
  },
  methods: {
    /**
     * 关闭选项卡
     * itemId 所关闭选项卡的id
     */
    handleClose (itemId) {
      let allTab = this.tabList; // 所有的选项卡
      let activeItemId = allTab[0].activeId; // 当前选择tab项id
      let arrayIndex; // itemId选项id获取在所有选项卡的对应位置
      allTab.filter(function (elem, index) {
        if (elem.id === itemId) {
          arrayIndex = index;
        }
      });
      let showArrayIndex; // 需要显示的tab标签项所在数组索引
      let showId; // 需要显示的tab标签项id
      /**
       * 1.关闭的是非当前显示项，还显示原来项
       * 2.关闭当前显示项，根据规则显示
       */
      if (activeItemId !== itemId) {
        showId = activeItemId;
      } else {
        if (arrayIndex === allTab.length - 1) {
          // 1.关闭项是最后一项时，默认显示前一项
          showArrayIndex = arrayIndex - 1;
        } else {
          // 2.关闭项不是最后一项时，默认显示后一项
          showArrayIndex = arrayIndex + 1;
        }
        showId = allTab[showArrayIndex].id;
        this.$router.push({ path: allTab[showArrayIndex].href });
      }
      let reserveTab = allTab.filter(function (elem) {
        if (elem.id === showId) {
          // 当前项是所要显示id时，设置为显示
          elem.select = true;
        } else {
          elem.select = false;
        }
        return elem.id !== itemId;
      }); // 保留过滤后的tab标签页
      reserveTab[0].activeId = showId; // 更新当前选中tab项
      this.changeTab(reserveTab);
    },
    // 关闭所有tab项
    handleCloseAll () {
      let temp = [];
      let homeItem = this.tabList[0];
      homeItem.activeId = 0;
      homeItem.select = true;
      temp.push(homeItem);
      this.changeTab(temp);
      this.$router.push({ path: homeItem.href });
      this.$refs.tabContent.style.marginLeft = 0;
    },
    // 关闭其他tab项
    handleCloseOther () {
      let temp = [];
      temp.push(this.tabList[0]); // 取出home页
      let activeItemId = this.tabList[0].activeId;
      let reserveItem = this.tabList.filter(function (elem) {
        if (activeItemId === elem.id) {
          return elem;
        }
        // return activeItemId === elem.id;
      }); // 取出home页或者home页和某一个一面
      if (activeItemId !== 0) {
        temp.push(reserveItem[0]);
      }
      temp[0].activeId = activeItemId;
      this.changeTab(temp);
      this.$refs.tabContent.style.marginLeft = 0;
    },
    // 向前一组
    handleChangeTab (direction) {
      let index = this.tabList.length - 1;
      let tabContentWidth = this.$refs.tab[index].showDom().offsetLeft + this.$refs.tab[index].showDom().offsetWidth; // 目前所有tab宽度
      let middleWidth = this.$refs.middle.offsetWidth;
      let tabList = this.tabList;
      let currentNum = tabList[0].groupTab.currentNum;
      let totalNum = Math.ceil(tabContentWidth / middleWidth) - 1; // 计算出总组数
      let marginLeft;
      if (direction === "left") {
        if (currentNum > 0) {
          currentNum = currentNum - 1;
          marginLeft = middleWidth * currentNum;
          this.$refs.tabContent.style.marginLeft = -marginLeft + "px";
        }
      } else {
        if (currentNum < totalNum) {
          currentNum = currentNum + 1;
          marginLeft = middleWidth * currentNum;
          this.$refs.tabContent.style.marginLeft = -marginLeft + "px";
        }
      }
      tabList[0].groupTab.totalNum = totalNum;
      tabList[0].groupTab.currentNum = currentNum;
      this.changeTab(tabList);
    },
    /**
    * 在路由发生变化时，如果信息（path，params，query）发生变化，改变当前选中tab存储信息
    * @param {*Object 路由信息} route
    * @author yezi 2019-09-18
    */
    changeSelTabInfo (route) {
      let activeId = this.tabList[0].activeId;
      let oldInfo = this.tabList[activeId];
      if (oldInfo.href !== route.path) {
        this.tabList[activeId].href = route.path;
      }
      if (JSON.stringify(oldInfo.query) !== JSON.stringify(route.query)) {
        this.tabList[activeId].query = route.query;
      }
      if (JSON.stringify(oldInfo.params) !== JSON.stringify(route.params)) {
        this.tabList[activeId].params = route.params;
      }
      this.changeTab(this.tabList);
    },
    ...mapMutations(["changeTab"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/common.less";
.tab {
  width: calc(~"100% + 8px");
  background-color: #fff;
  height: 60px;
  line-height: 60px;
  margin-left: -8px;
  padding: 0 8px;
}
.left,
.middle,
.middle > div,
.right {
  display: inline-block;
}
.leftIcon,
.rightIcon {
  width: 32px;
  padding: 0 !important;
  text-align: center;
  position: relative;
  top: 2px;
  i {
    font-size: 16px;
  }
}

/* 左侧 */
.left {
  min-width: 40px;
  position: absolute;
  left: 8px;
  top: 0;
}

/* 中间 */
.middle {
  text-align: left;
  width: calc(~"100% - @{leftWidth}");
  margin-left: -120px;
  overflow: hidden;
}
.tabContent {
  position: relative;
  width: 500%;
}
.middle /deep/ span {
  font-size: 14px;
}
.middle /deep/ .ivu-tabs-bar {
  margin-bottom: 0px;
}
/* 右侧 */
.right {
  min-width: 140px;
  position: absolute;
  right: 8px;
  top: 0;
  /deep/ .ivu-dropdown {
    margin-left: 8px;
    .ivu-select-dropdown {
      // 兼容火狐，绝对定位宽度无法自适应
      min-width: 116px;
    }
  }
}
.right /deep/ button {
  padding: 3px 10px;
}
.tab--close {
  top: 1px;
  /deep/ .drop-btn__text {
    position: relative;
    top: -1px;
  }
}
</style>
