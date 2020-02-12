<template>
  <div class="dropdown"
       @mouseover="onDplOver($event)"
       @mouseout="onDplOut($event)">
    <span>{{dplLable}}<Icon type="ios-arrow-down"></Icon></span>
    <ul>
      <li v-for="(item, index) in dataList"
          :key="index"
          :class="{'active':index===activeIndex}"
          @click="onLiClick(index, $event)">{{item}}</li>
    </ul>
  </div>
</template>

<script>
export default {
  props: {
    showString: {
      type: String,
      default: "选择"
    },
    dataList: {
      type: Array,
      default () {
        return [];
      }
    }
  },
  data () {
    return {
      activeIndex: -1
    };
  },
  computed: {
    dplLable () {
      if (this.activeIndex < 0) return this.showString;
      return this.dataList[this.activeIndex];
    }
  },
  methods: {
    onDplOver (event) {
      let ul = event.currentTarget.childNodes[1];
      ul.style.height = this.dataList.length * 40 + "px";
      ul.style.opacity = "1";
    },
    onDplOut (event) {
      let ul = event.currentTarget.childNodes[1];
      ul.style.height = "0";
      ul.style.opcity = "0";
    },
    onLiClick (index) {
      let path = this.$common.eventPath(event); // 兼容火狐和safari
      path[1].style.height = "0";
      path[1].style.opacity = "0";
      this.activeIndex = index;
      this.$emit("change", {
        index: index,
        value: this.dataList[index]
      });
    }
  }
};
</script>

<style scoped lang="less">
.dropdown {
  display: inline-block;
  min-width: 120px;
  position: relative;
  cursor: pointer;
  span {
    display: block;
    height: 32px;
    line-height: 32px;
    padding: 0 15px;
    background: #fff;
    color: #81cfe6;
    font-size: 14px;
    text-align: center;
    border: 1px solid rgba(129, 207, 230, 1);
    border-radius: 4px;
    i {
      margin-left: 6px;
      display: inline-block;
    }
  }
  &::before {
    content: "";
    display: block;
    position: absolute;
    top: 32px;
    height: 10px;
    width: 100%;
    background: transparent;
  }
  ul {
    position: absolute;
    top: 42px;
    left: 0;
    z-index: 2;
    width: 100%;
    margin: 0;
    padding: 0;
    background: rgba(255, 255, 255, 1);
    box-shadow: 0px 2px 4px 0px rgba(0, 0, 0, 0.5);
    border-radius: 4px;
    border-radius: 4px;
    overflow: hidden;
    height: 0;
    transition: all 0.3s;
    li {
      list-style: none;
      height: 40px;
      line-height: 40px;
      font-size: 14px;
      background: #ffffff;
      color: #78c1d6;
      text-align: center;
    }
    li:hover {
      background: rgba(129, 207, 230, 0.8);
      color: #fff;
    }
    .active {
      background: #fff !important;
      color: #08142c !important;
    }
  }
}
</style>
