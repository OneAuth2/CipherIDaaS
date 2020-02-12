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
      } else if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
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
          o.push(
            cloneAssemblyAssignData(sourceObj[i], assignKeys, copyOtherKeys)
          );
        }
      } else {
        o = {};
        for (var k in sourceObj) {
          let keys = Object.keys(assignKeys);
          var flag = true;
          for (var j in keys) {
            let temp = assignKeys[keys[j]];
            if (sourceObj[k] && k === temp.original) {
              // 匹配要更改的key
              temp.target = temp.target || temp.original; // 不传入目标对象时使用源对象
              o[temp.target] = cloneAssemblyAssignData(
                sourceObj[k],
                assignKeys,
                copyOtherKeys
              );
              flag = false;
              break;
            }
          }
          // 1.判断是否拷贝指定源对象key外的其它key;2.已经指定拷贝过的源对象不再进行拷贝
          if (copyOtherKeys && flag) {
            o[k] = cloneAssemblyAssignData(
              sourceObj[k],
              assignKeys,
              copyOtherKeys
            );
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
      } else if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
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
 *        {key1:"描述1",key2:"描述2",....}
 * @param {*Array具体要匹配的源对象属性名字组} keyArray
 * @param {*String匹配的关键字} keyword
 * @param {*保存要展开的路径} selItemArray
 * @return {*Object拷贝出的新对象} o
 * @author menglei 2019-07-29
 */
function searchCloneData (obj, keyArray, keyword, selItemArray) {
  var o; // 搜索拷贝出的新对象
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
      } else if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
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
          for (var key in keyArray) {
            // 遍历传入数组
            if (
              keyword !== "" &&
              k === keyArray[key] &&
              obj[k].includes(keyword)
            ) {
              // obj[k]是string类型
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
 * 设置需要展开的路径
 * @param {*Object需要搜索的源对象} obj
 * @param {*Array需要展开的路径数组} selItemArray
 * @return {*Object tree组件所需数组对象（包含需要展开的标志）} o
 * @author menglei 2019-08-02
 */
function setExpandData (obj, selItemArray) {
  var o; // tree组件所需数组对象（包含需要展开的标志）
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
      } else if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
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
 * 增加keys深度拷贝功能
 * @param {*要操作的对象} obj
 * return 拷贝出的新对象
 */
function addKeysAndClone (obj) {
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
      } else if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        o = [];
        for (var i = 0; i < obj.length; i++) {
          o.push(addKeysAndClone(obj[i]));
        }
      } else {
        o = {};
        for (var k in obj) {
          o[k] = addKeysAndClone(obj[k]);
        }
        if (o.children.length !== 0) {
          o.expand = true;
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
  m = m < 10 ? "0" + m : m;
  var d = date.getDate();
  d = d < 10 ? "0" + d : d;
  var h = date.getHours();
  var minute = date.getMinutes();
  minute = minute < 10 ? "0" + minute : minute;
  var second = date.getSeconds();
  second = minute < 10 ? "0" + second : second;
  return y + "-" + m + "-" + d + " " + h + ":" + minute + ":" + second;
};
/**
 * 深度递归搜索
 * @param {Array} arr 你要搜索的数组
 * @param {Function} condition 回调函数，必须返回谓词，判断是否找到了。会传入(item, index, level)三个参数
 * @param {String} children 子数组的key
 * exp let myarr = deepFind(arr, (item, index, level) => item.value === 63, 'children')
 */
let deepFind = (arr, condition, children) => {
  // 即将返回的数组
  let main = [];

  // 用try方案方便直接中止所有递归的程序
  try {
    // 开始轮询
    (function poll (arr, level) {
      // 如果传入非数组
      if (!Array.isArray(arr)) return;
      // 遍历数组
      for (let i = 0; i < arr.length; i++) {
        // 获取当前项
        const item = arr[i];
        // 先占位预设值
        main[level] = item;
        // 检验是否已经找到了
        const isFind = (condition && condition(item, i, level)) || false;
        if (isFind) {
          // 如果已经找到了
          // 直接抛出错误中断所有轮询
          throw Error;
        } else if (children && item[children] && item[children].length) {
          // 如果存在children，那么深入递归
          poll(item[children], level + 1);
        } else if (i === arr.length - 1) {
          // 如果是最后一个且没有找到值，那么通过修改数组长度来删除当前项
          // 删除占位预设值
          main.length = main.length - 1;
        }
      }
    })(arr, 0);
    // 使用try/catch是为了中止所有轮询中的任务
  } catch (err) {}

  // 返回最终数组
  return main;
};
/*
 * 获取事件冒泡路径，
 * @param {*Object node} 当前事件节点
 * @param {*Object memo} 需要获取的所有路径节点，可传window
 * @author yezi 2019-09-12
 */
function getParents (node, memo) {
  memo = memo || [];
  const parentNode = node.parentNode;

  if (!parentNode) {
    return memo;
  } else {
    return getParents(parentNode, memo.concat(parentNode));
  }
}
/*
 * 获取事件冒泡路径，兼容ie11,edge,chrome,firefox,safari
 * @param {*Object 事件event} evt
 * @author yezi 2019-09-12
 */
let eventPath = (evt) => {
  const path = (evt.composedPath && evt.composedPath()) || evt.path; // 兼容火狐和safari chrome

  const target = evt.target;
  if (path != null) { // 如果路径存在
    return (path.indexOf(window) < 0) ? path.concat(window) : path;
  }

  if (target === window) { // 如果是window，直接返回
    return [window];
  }
  // 其他情况
  return [target].concat(getParents(target), window);
};
export default {
  clone,
  cloneAssemblyAssignData,
  searchCloneData,
  setExpandData,
  addKeysAndClone,
  initSelect,
  formatDate,
  deepFind,
  eventPath
};
