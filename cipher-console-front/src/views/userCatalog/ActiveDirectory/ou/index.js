import api from "@/api/userCatalog/index.js";
const cloneChangeKeys = {
  title: {
    target: "title",
    original: "rDN"
  },
  children: {
    target: "children",
    original: "nodes"
  },
  path: {
    target: "path",
    original: "dn"
  },
  checked: {
    target: "checked",
    original: "select"
  }
};
// 获取AD OU树结构
function getOUTree (_this) {
  let params = {
    ip: _this.formdata.ip,
    port: _this.formdata.port,
    userName: _this.formdata.userName,
    password: _this.formdata.password
  };
  _this
    .axios({
      method: "post",
      data: params,
      url: api.getOuTree
    })
    .then(res => {
      let data = res.data;
      if (data.length !== 0) {
        let OUDataTemp = _this.$common.cloneAssemblyAssignData(
          data,
          cloneChangeKeys,
          true
        );
        _this.OUData = _this.$common.addKeysAndClone(OUDataTemp);
        _this.OUDataBak = _this.$common.clone(_this.OUData);
      }
    })
    .catch(function (error) {
      _this.axios.error.handlingErrors(error);
    });
}
function showOUModal (_this) {
  clearBeforeData(_this);
  _this.OUModal = true;
  getOUTree(_this);
}
function OUSave (_this) {
  _this.syncTarget = [];
  _this.formdata.syncTarget = "";
  let selectOUTree = _this.$refs.OUTree.getCheckedNodes().filter(
    (elem, index, arr) => {
      if (elem.children.length === 0) {
        _this.syncTarget.push(elem.path);
        _this.formdata.syncTarget += elem.path;
        _this.formdata.syncTarget += "\r\n";
        return true;
      }
    }
  );
  clearBeforeData(_this);
  _this.OUModal = false;
  return selectOUTree;
}
function OUCancel (_this) {
  clearBeforeData(_this);
}
function OUReset (_this) {
  _this.OUData = _this.$common.clone(_this.OUDataBak);
}
// 清除上次操作数据
function clearBeforeData (_this) {
  _this.OUData = []; // 清除上一次选中状态
}

/**
 * 搜索关键字
 */
function keyword (_this) {
  let expandItemArray = []; // 要展开的路径数组
  let OUData = _this.$common.searchCloneData(
    _this.OUDataBak,
    ["title"],
    _this.keyword,
    expandItemArray
  ); // 搜索标记对应item
  _this.OUData = _this.$common.setExpandData(OUData, expandItemArray);
}
export default {
  getOUTree,
  showOUModal,
  OUSave,
  OUCancel,
  OUReset,
  clearBeforeData,
  keyword
};
