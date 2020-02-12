/**
 * 深度拷贝功能
 * @param {*要拷贝的对象} obj
 * return 拷贝出的新对象
 */
// todo this作用域问题
function clone (obj, _this) {
  var o;
  switch (typeof obj) {
    case "undefined":
      break;
    case "string":
      o = String(obj);
      break;
    case "number":
      o = obj - 0;
      break;
    case "boolean":
      o = obj;
      break;
    case "object":
      if (obj === null) {
        o = null;
      } else if (
        Object.prototype.toString.call(obj).slice(8, -1) === "Array"
      ) {
        o = [];
        for (var i = 0; i < obj.length; i++) {
          o.push(clone(obj[i]));
        }
      } else {
        o = {};
        for (var k in obj) {
          o[k] = clone(obj[k]);
        }
      }
      break;
    default:
      o = obj;
      break;
  }
  return o;
}

/**
 * 深度拷贝组装指定对象数据
 * @param {*Object要拷贝的源对象} sourceObj
 * @param {*Object指定需要拷贝的对象,assignKeys需指定源original和目标target字段，当target字段不存在时会使用original} assignKeys
 * @param {*Boolean指定是否拷贝指定源对象key外的其它key，默认为true} copyOtherKeys
 * return 拷贝出的新对象
 */
function cloneAssemblyAssignData (sourceObj, assignKeys, copyOtherKeys = true) {
  var o;
  switch (typeof sourceObj) {
    case "undefined":
      break;
    case "string":
      o = String(sourceObj);
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
          o.push(cloneAssemblyAssignData(sourceObj[i], assignKeys, copyOtherKeys));
        }
      } else {
        o = {};
        for (var k in sourceObj) {
          let keys = Object.keys(assignKeys);
          var flag = true;
          for (var j in keys) {
            let temp = assignKeys[keys[j]];
            if (sourceObj[k] && k === temp.original) { // 匹配要更改的key
              temp.target = temp.target || temp.original; // 不传入目标对象时使用源对象
              o[temp.target] = cloneAssemblyAssignData(sourceObj[k], assignKeys, copyOtherKeys);
              flag = false;
              break;
            }
          }
          // 1.判断是否拷贝指定源对象key外的其它key;2.已经指定拷贝过的源对象不再进行拷贝
          if (copyOtherKeys && flag) {
            o[k] = cloneAssemblyAssignData(sourceObj[k], assignKeys, copyOtherKeys);
          }
        }
      }
      break;
    default:
      o = sourceObj;
      // console.log("o");
      // console.log(o);
      break;
  }
  return o;
}
/**
 * 对数据进行处理看哪些数据被选中
 * @param {*} obj 深度copy后的数组
 * 返回处理过的数据
 */
function initSelect (obj) {
  var o;
  switch (typeof obj) {
    case "undefined":
      break;
    case "string":
      o = String(obj);
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
          if (obj[k] !== "null" && obj[k] !== null && k === "state") {
            o["checked"] = this.initSelect(obj[k].checked);
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

/**
 * 搜索匹配关键字的对象
 * @param {*Object需要搜索的源对象} obj
 * @param {*Array具体要匹配的源对象属性名字组} keyArray
 * @param {*String匹配的关键字} keyword
 * return 拷贝出的新对象
 */
function searchCloneData (obj, keyArray, keyword, selItemArray) {
  var o;
  switch (typeof obj) {
    case "undefined":
      break;
    case "string":
      o = String(obj);
      break;
    case "number":
      o = obj - 0;
      break;
    case "boolean":
      o = obj;
      break;
    case "object":
      if (obj === null) {
        o = null;
      } else if (
        Object.prototype.toString.call(obj).slice(8, -1) === "Array"
      ) {
        o = [];
        for (var i = 0; i < obj.length; i++) {
          o.push(searchCloneData(obj[i], keyArray, keyword, selItemArray));
        }
      } else {
        o = {};
        let selected = "selected";
        // let time = 0;
        for (var k in obj) {
          // 过滤处理数据
          for (var key in keyArray) { // 遍历传入数组
            if (keyword !== "" && k === keyArray[key] && obj[k].includes(keyword)) { // obj[k]是string类型
              o[selected] = true;
              // time++;
              // 数组为空时，把匹配到关键字对应的path加入准备展开的数组
              selItemArray.push(obj["path"]);
            }
          }
          // if (o['selected'] === true) { // 选中的展开
          //   // if (o['selected'] === true || (k === 'path' && obj[k].includes(keyword))) { // 选中的展开
          //   o['expand'] = true;
          // } else { // 不选中的折叠
          //   o['expand'] = false;
          // }
          if (k !== selected) {
            o[k] = searchCloneData(obj[k], keyArray, keyword, selItemArray);
          }
        }
        // 当前子节点有搜索到时，设置父节点展开，todo
        // if (time !== 0) {
        // o['expand'] = true;
        // }
      }
      break;
    default:
      o = obj;
      break;
  }
  return o;
}
/**
 *
 * @param {*Object需要搜索的源对象} obj
 * @param {*} selItemArray
 */
function setExpandData (obj, selItemArray) {
  var o;
  switch (typeof obj) {
    case "undefined":
      break;
    case "string":
      o = String(obj);
      break;
    case "number":
      o = obj - 0;
      break;
    case "boolean":
      o = obj;
      break;
    case "object":
      if (obj === null) {
        o = null;
      } else if (
        Object.prototype.toString.call(obj).slice(8, -1) === "Array"
      ) {
        o = [];
        for (var i = 0; i < obj.length; i++) {
          o.push(setExpandData(obj[i], selItemArray));
        }
      } else {
        o = {};
        let expand = "expand";

        for (var k in obj) {
          // 过滤处理数据
          for (var j in selItemArray) {
            if (selItemArray[j].includes(obj.path)) {
              o[expand] = true;
            }
          }
          if (k !== expand) {
            o[k] = setExpandData(obj[k], selItemArray);
          }
        }
      }
      break;
    default:
      o = obj;
      break;
  }
  return o;
}
/**
 * 日期格式格式化
 * @param {*new Date出来的日期字符串} date
 * return 格式“2019-03-15 0:00:00”
 */
let formatDate = function (date) {
  if (date.length === 0) {
    return "";
  }
  var y = date.getFullYear();
  var m = date.getMonth() + 1;
  m = m < 10 ? ("0" + m) : m;
  var d = date.getDate();
  d = d < 10 ? ("0" + d) : d;
  var h = date.getHours();
  var minute = date.getMinutes();
  minute = minute < 10 ? ("0" + minute) : minute;
  var second = date.getSeconds();
  second = minute < 10 ? ("0" + second) : second;
  return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
};

export default {
  clone,
  cloneAssemblyAssignData,
  searchCloneData,
  setExpandData,
  initSelect,
  formatDate
};
