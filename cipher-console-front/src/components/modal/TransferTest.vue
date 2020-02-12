<template>
  <div class="example">
    <TreeTransfer :leftTreeData="data1"
                  :leftDataType="leftType2"
                  @rightData="getRightData1"
                  :placeholder="placeholder1">
      <span slot="leftText">授权部门</span>
      <span slot="rightText">已授权部门</span>
    </TreeTransfer>
    <TreeTransfer :leftTreeData="data"
                  :leftDataType="leftType1"
                  @rightData="getRightData2"
                  :placeholder="placeholder2">
      <span slot="leftText">授权用户</span>
      <span slot="rightText">已授权用户</span>
    </TreeTransfer>

    <TreeTransfer :leftTreeData="data3"
                  :leftDataType="leftType"
                  @rightData="getRightData3"
                  :placeholder="placeholder3">
      <span slot="leftText">授权安全组</span>
      <span slot="rightText">已授权安全组</span>
    </TreeTransfer>
    <TreeTransfer :leftTreeData="data4"
                  :leftDataType=" leftType4"
                  @rightData="getRightData4"
                  :placeholder="placeholder1">
      <span slot="leftText">授权部门</span>
      <span slot="rightText">已授权部门</span>
    </TreeTransfer>
  </div>
</template>

<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
const cloneChangeKeys = {
  title: {
    target: "title",
    original: "text"
  },
  children: {
    target: "children",
    original: "nodes"
  }
};
export default {
  data () {
    return {
      leftType: 0,
      leftType1: 0,
      leftType2: 0,
      leftType4: 0,
      rightData1: [],
      rightData2: [],
      rightData3: [],
      rightData4: [],
      data: [],
      data1: [],
      data3: [],
      data4: [],
      placeholder1: "请输入部门",
      placeholder2: "请输入用户",
      placeholder3: "请输入安全组"
    };
  },
  components: {
    TreeTransfer
  },
  mounted () {
    this.initData();
  },
  methods: {

    initData () {
      this.leftType1 = 1;
      let params = { applicationId: "141" };
      this.axios({
        method: "post",
        url: "/cipher/app/authorizationUser",
        data: params
      })
        .then(res => {
          this.data = this.copyDeepTreeData(res.data.trees, cloneChangeKeys, true);
          this.data = this.initSelect(this.data);
          this.data = this.data;
        })
        .catch(() => {
          // console.log(error);
        });

      this.axios({
        method: "post",
        url: "/cipher/app/getAuthGroup",
        data: params
      })
        .then(res => {
          this.data1 = this.copyDeepTreeData(res.data.trees, cloneChangeKeys, true);
          this.data1 = this.initSelect(this.data1);
          this.data4 = this.initSelect(this.data1);
          this.leftType2 = 2;
        })
        .catch(() => {
          // console.log(error);
        });

      this.axios({
        method: "post",
        url: "/cipher/app/getAuthTeam",
        data: params
      })
        .then(res => {
          this.data3 = res.data.msg.list;
        })
        .catch(() => {
          // console.log(error);
        });

      this.leftType = 3;
      this.axios({
        method: "post",
        url: "/cipher/app/getAuthTeam",
        data: params
      })
        .then(res => {
          this.data3 = res.data.msg.list;
          this.placeholder = "请输入安全组";
        })
        .catch(() => {

        });
      this.leftType4 = 4;
    },
    getRightData1 (data) {
      this.rightData1 = data;
    },
    getRightData2 (data) {
      this.rightData2 = data;
    },
    getRightData3 (data) {
      this.rightData3 = data;
    },
    getRightData4 (data) {
      this.rightData4 = data;
    },
    // 对树的深度copy，并指定数据
    copyDeepTreeData (sourceObj, replaceCopy, copyOtherData = true) {
      var o;
      switch (typeof sourceObj) {
        case "undefined":
          break;
        case "string":
          o = sourceObj + "";
          break;
        case "number":
          o = sourceObj - 0;
          break;
        case "boolean":
          o = sourceObj;
          break;
        case "object":
          if (sourceObj === null) {
            o = "null";
          } else if (
            Object.prototype.toString.call(sourceObj).slice(8, -1) === "Array"
          ) {
            o = [];
            for (var i = 0; i < sourceObj.length; i++) {
              o.push(
                this.copyDeepTreeData(sourceObj[i], replaceCopy, copyOtherData)
              );
            }
          } else {
            o = {};
            for (var k in sourceObj) {
              let keys = Object.keys(replaceCopy);
              var flag = true;
              for (var j in keys) {
                let temp = replaceCopy[keys[j]];
                if (sourceObj[k] && k === temp.original) {
                  temp.target = temp.target || temp.original;
                  o[temp.target] = this.copyDeepTreeData(
                    sourceObj[k],
                    replaceCopy,
                    copyOtherData
                  );
                  o[k] = this.copyDeepTreeData(
                    sourceObj[k],
                    replaceCopy,
                    copyOtherData
                  );
                  // o["sign"]=k+"_";
                  flag = false;
                  break;
                }
              }
              if (copyOtherData && flag) {
                o[k] = this.copyDeepTreeData(
                  sourceObj[k],
                  replaceCopy,
                  copyOtherData
                );
              }
            }
          }
          break;
        default:
          o = sourceObj;
          break;
      }
      return o;
    },
    // 对数据进行处理看哪些数据被选中
    initSelect (obj) {
      var o;
      switch (typeof obj) {
        case "undefined":
          break;
        case "string":
          o = obj + "";
          break;
        case "boolean":
          o = obj;
          break;
        case "object":
          if (obj === null) {
            o = "null";
          } else if (
            Object.prototype.toString.call(obj).slice(8, -1) === "Array"
          ) {
            o = [];
            for (var i = 0; i < obj.length; i++) {
              o.push(this.initSelect(obj[i]));
            }
          } else {
            o = {};
            for (var k in obj) {
              if (obj[k] !== "null" && obj[k] != null && k === "state") {
                o["checked"] = this.initSelect(obj[k].checked);
              }
              if (k === "accountNumber") {
                o["expand"] = false;
              }

              o[k] = this.initSelect(obj[k]);
            }
          }
          break;
        default:
          o = obj;
          break;
      }
      return o;
    }
  }
};
</script>

<style scoped>
</style>
