<template>
  <div class="btn select"
       :style="{width:width}"
       @mouseover="onDplOver($event)"
       @mouseout="onDplOut($event)">
    <myDropBtn :width="width">{{dplLable}}</myDropBtn>
    <ul>
      <li v-for="(item, index) in getDataList"
          :key="index"
          ref="selectItem"
          :class="{'active':index===getActiveIndex}"
          @click="onLiClick(index, $event)">{{item[labelKey]}}</li>
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
    },
    valueKey: {
      type: String,
      default: "value"
    },
    labelKey: {
      type: String,
      default: "label"
    },
    activeName: {
      type: [String, Number],
      default: ""
    },
    width: {
      type: String,
      default: ""
    }
  },
  model: {
    prop: "activeName"
  },
  data () {
    return {
      activeIndex: -1
    };
  },
  // watch: {
  //   activeName () {
  //     this.getActiveIndex();
  //   }
  // },
  // mounted () {
  //   this.getActiveIndex();
  // },
  computed: {
    getDataList () {
      return this.dataList.map(item => {
        if (Object.prototype.toString.call(item) !== "[object Object]") {
          let tempObj = {};
          tempObj.value = item;
          tempObj.label = item;
          return tempObj;
        }
        return item;
      });
    },
    getActiveIndex () {
      let activeIndex = -1;
      for (let i = 0; i < this.getDataList.length; i++) {
        if (this.getDataList[i][this.valueKey] === this.activeName) {
          activeIndex = i;
        }
      }
      return activeIndex;
    },
    dplLable () {
      if (this.getActiveIndex < 0) return this.showString;
      return this.getDataList[this.getActiveIndex][this.labelKey];
    },
    dpValue () {
      if (this.getActiveIndex < 0) return "";
      return this.getDataList[this.getActiveIndex][this.valueKey];
    }
  },
  methods: {
    onDplOver (event) {
      let ul = event.currentTarget.childNodes[1];
      let itemHeight = event.currentTarget.childNodes[1].childNodes[0].offsetHeight;
      ul.style.height = this.getDataList.length * itemHeight + "px";
      ul.style.opacity = "1";
    },
    onDplOut (event) {
      let ul = event.currentTarget.childNodes[1];
      ul.style.height = "0";
      ul.style.opcity = "0";
    },
    onLiClick (index) {
      let path = this.$common.eventPath(event);
      path[1].style.height = "0";
      path[1].style.opacity = "0";
      this.$emit("input", this.getDataList[index][this.valueKey]);
      this.$emit("on-change", {
        index: index,
        value: this.getDataList[index][this.valueKey],
        label: this.getDataList[index][this.labelKey]
      });
    }
  }
};
</script>

<style scoped lang="less">
.select {
  display: inline-block;
  height: 32px;
  line-height: 32px;
  position: relative;
  cursor: pointer;
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
    overflow: auto;
    height: 0;
    transition: all 0.3s;
    li {
      list-style: none;
      padding: 7px 16px;
      line-height: normal;
      font-size: 12px;
      background: #ffffff;
      color: #a7a7a7;
      text-align: center;
    }
    li:hover {
      background: #d2f0f9;
      color: #81cfe6;
    }
    .active {
      background: #fff !important;
      color: #08142c !important;
    }
  }
}
</style>
