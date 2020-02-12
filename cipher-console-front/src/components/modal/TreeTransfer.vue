<template>
  <div class="tree-transfer clearfix">
    <div class="left tree-transfer-left">
      <div class="choose">
        <slot name="leftText"></slot>
      </div>
      <div class="tree-transfer-search">
        <myInput v-model="searchDefaultData"
                 radius="half"
                 :search="true"
                 style="width: 100%"
                 @handleClick="searchDataMethod"
                 :placeholder="placeholder" />
      </div>
      <div v-if="type===1"
           class="height2">
        <Tree @on-check-change="checkTreeData"
              :data="leftData1"
              show-checkbox
              :render="showSelectClass"
              @on-toggle-expand="changeCheckStyle"
              class="modalClass"
              ref="trees"></Tree>
      </div>
      <div v-else-if="type===2"
           class="height2">
        <Tree :data="leftData2"
              :render="showSelectClass1"
              class="modalClass"
              ref="trees"></Tree>
      </div>
      <div v-else-if="type===3"
           class="height2">
        <CheckboxGroup v-model="teams"
                       @on-change="getCheckedData">
          <div v-for="(userInfo,index) in leftData3"
               :key="index"
               class="padding1">
            <Checkbox :label="userInfo.id"
                      v-if="userInfo.selected===true"
                      class="modalClass1"
                      style="color:red">{{userInfo.teamName}}</Checkbox>
            <Checkbox :label="userInfo.id"
                      v-if="userInfo.selected===false"
                      class="modalClass">{{userInfo.teamName}}</Checkbox>
          </div>
        </CheckboxGroup>
      </div>
      <div v-else-if="type===4"
           class="height2">
        <Tree @on-check-change="getCheckTreeData4"
              :data="leftData4"
              show-checkbox
              check-strictly
              :render="showSelectClass"
              class="modalClass"
              ref="tree4"></Tree>
      </div>
    </div>
    <div class="right tree-transfer-right">
      <div class="choose">
        <slot name="rightText"></slot>
      </div>
      <div class="modalClass height3"
           v-if="type==1">
        <div v-for="(userInfo,i) in rightData1"
             :key="i"
             class="nowhite">
          <Row span="24"
               class="padding">
            <Col span="10"
                 offset="1"
                 style=" text-align: left;">{{userInfo.title}}.</Col>
            <Col span="3"
                 offset="10"
                 style=" text-align: right;">
            <p>
              <Icon type="ios-close"
                    size="32"
                    @click="leftTransfer1(userInfo)" />
            </p>
            </Col>
          </Row>
        </div>
      </div>
      <div class="modalClass height3"
           v-if="type==3">
        <div v-for="(userInfo,i) in rightData3"
             :key="i"
             class="nowhite">
          <Row span="24"
               class="padding">
            <Col span="10"
                 offset="1"
                 style=" text-align: left;">{{userInfo.teamName}}.</Col>
            <Col span="3"
                 offset="10"
                 style=" text-align: right;">
            <p>
              <Icon type="ios-close"
                    size="32"
                    @click="leftTransfer3(userInfo)" />
            </p>
            </Col>
          </Row>
        </div>
      </div>
      <div class="modalClass height3"
           v-if="type==2">
        <div v-for="(userInfo,index)  in rightData2"
             :key="index"
             class="nowhite">
          <Row span="24"
               class="padding">
            <Col span="10"
                 offset="1"
                 style=" text-align: left;">{{userInfo.title}}.</Col>
            <Col span="3"
                 offset="10"
                 style=" text-align: right;">
            <p>
              <Icon type="ios-close"
                    size="32"
                    @click="leftTransfer2(userInfo)" />
            </p>
            </Col>
          </Row>
        </div>
      </div>
      <div class="modalClass height3"
           v-else-if="type==4">
        <div v-for="item in rightData4"
             :key="item.groupId"
             class="nowhite">
          <Row span="24"
               class="padding">
            <Col span="10"
                 offset="1"
                 style=" text-align: left;">{{item.groupName}}.</Col>
            <Col span="3"
                 offset="10"
                 style=" text-align: right;">
            <p>
              <Icon type="ios-close"
                    size="32"
                    @click="leftTransfer4(item)" />
            </p>
            </Col>
          </Row>
        </div>
      </div>
      </Col>
    </div>
  </div>
</template>
<script>
/**
 * 穿梭框使用说明1 部门人穿梭框的使用用法
 * 传参 1 leftTreeData leftData的数据的必传参数为nodes title  selected state{checked：true/false} groupId accountNumber
 * 传参2 leftDataType 1 需要哪种类型的穿梭款
 * placeholder:传进输入框中的值
 * slot="leftText"插槽一 左侧input上方的显示文字
 * slot="rightText" 插槽2 右侧input上方的显示文字
 * @rightData="" 方法，时时返回右侧数据的方法，返回值为 list数组
 * 该leftTreeData 在从接口拿到数据后需要把数据进行处理
 * 该数据的处理需要经历两个方法
 * 1。cloneAssemblyAssignData  具体说明在 util包下的common.js下
 * 2.initSelect  具体说明在 util包下的common.js下
 * 经过这两种方法处理过的数据，在该组件下才可以使用，否则不能够使用
 *
 *
 *
 */

/**
 * 穿梭框使用说明2 部门穿梭框的使用用法
 * 传参 1 leftTreeData leftData的数据的必传参数为nodes title  selected state{checked：true/false} groupId
 * 传参2 leftDataType 2 需要哪种类型的穿梭款 1是部门下有人，2是全是部门树，3 安全组列表穿梭框
 * placeholder:传进输入框中的值
 * slot="leftText"插槽一 左侧input上方的显示文字
 * slot="rightText" 插槽2 右侧input上方的显示文字
 * @rightData="" 方法，时时返回右侧数据的方法，返回值为 list数组
 *
 * 该leftTreeData 在从接口拿到数据后需要把数据进行处理
 * 该数据的处理需要经历两个方法
 * 1。cloneAssemblyAssignData  具体说明在 util包下的common.js下
 * 2.initSelect  具体说明在 util包下的common.js下
 * 经过这两种方法处理过的数据，在该组件下才可以使用，否则不能够使用
 */

/**
 * 穿梭框使用说明2 安全组穿梭框的使用用法
 * 传参 1 leftTreeData leftData的数据的必传参数为state{checked：true/false} select ：true：false  teamName："显示名字"，teamId:"唯一标识"
 * 传参2 leftDataType 3 需要哪种类型的穿梭款 1是部门下有人，2是全是部门树，3 安全组列表穿梭框
 * placeholder:传进输入框中的值
 * slot="leftText"插槽一 左侧input上方的显示文字
 * slot="rightText" 插槽2 右侧input上方的显示文字
 * @rightData="" 方法，时时返回右侧数据的方法，返回值为 list数组
 * leftDataType  如果该数据不具备上面传参的 属性，需要把拿到的数组进行转换，转换为相应的属性，该类型的数组主要需要四个属性 即state{checked：true/false} select ：true：false  teamName："显示名字"，teamId:"唯一标识"
 */

export default {
  name: "TreeTransfer",
  data () {
    return {
      teams: [], // 安全组对象
      leftData1: [], // 类型1数组对象
      leftData2: [], // 类型2数组
      leftData3: [], // 类型3数组
      leftData4: [], // 类型4数组

      rightData1: [], // 类型1右侧数组
      dataType: {},
      searchDefaultData: "", // 搜索绑定的数据
      type: 2,
      rightData2: [], // 类型2右侧数据
      rightData3: [], // 类型3右侧数据
      rightData4: [] // 类型4右侧数据
    };
  },
  props: {
    leftTreeData: {
      type: Array,
      default: () => []
    },
    leftDataType: {
      type: Number,
      default: 0
    },
    placeholder: {
      type: String,
      default: ""
    }
  },
  /**
   * 监测树种数据的变化，一旦有变化，重新加载树
   */
  watch: {
    leftTreeData: {
      handler: function (newVal, oldVal) {
        if (newVal === oldVal) {
        } else {
          this.teams = [];
          this.initRight(newVal);
          // type为1时，tree展开收缩时调用，修改节点为人时的样式
          if (this.leftDataType === 1) {
            this.$nextTick(() => {
              let persons = document.querySelectorAll(".person");
              for (let item of persons) {
                item.previousElementSibling.classList.add("person-circle");
              }
            });
          }
        }
      },
      deep: true
    },
    leftDataType: {
      handler: function (newVal, oldVal) {
        this.type = newVal;
      }
    }
  },
  methods: {
    /**
     * 获取安全组的复选框的值
     */
    getCheckedData (data) {
      let obj = [];
      let data1 = this.leftData3;
      for (let i = 0; i < data1.length; i++) {
        for (let k = 0; k < data.length; k++) {
          if (data1[i].id === data[k]) {
            obj.push(data1[i]);
          }
        }
      }
      this.rightData3 = obj;
      this.$emit("rightData", this.rightData3);
    },

    /** 类型3方法
     * 当用户点击安全组右侧数据的X号时，
     * 触发该方法，取消左侧选中状态，同时再右侧列表中移除
     */
    leftTransfer3 (userInfo) {
      for (let i = 0; i < this.teams.length; i++) {
        if (userInfo.id === this.teams[i]) {
          this.teams.splice(i, 1);
        }
      }
      this.getCheckedData(this.teams); // 重新获取右侧数据
    },

    /**
     * 类型2 方法
     * 传参 user Info 点击某个操作的数据
     *当点击部门下的右侧X号时 触发该方法
     该方法主要是 取消右侧与之关联的数据，使之取消关联 并且移除该条数据
     * 结束之后重更新获取所有节点，并且将右侧数据，返回给调用者
     */
    leftTransfer2 (userInfo) {
      let data = this.leftData2;
      this.cancelCheckDemo2(data, userInfo); // 取消与左侧数据的关联 使左侧树中与之对应的数据的checked为false
      this.rightData2 = this.dataRight(this.leftData2); // 重新获取右侧数据
      this.$emit("rightData", this.rightData2); // 返回给上级组件
    },
    /**
     *类型1方法
     * 传参 传入该节点的信息
     * 当点击用户和部门树右侧的显示框下的X时，触发该方法
     * 该方法主要的流程是:1先取到点击的accountNumber的所在所有部门的path，获取所有的父节点
     * 2.根据所有的父节点遍历整棵树，把这些节点的checked 变为false 然后再把这些节点的模糊态变为true
     * 3，最后再把这个用户的节点的checked 变为false
     * 最后 初始化右侧数据 把右侧数据返回给父节点
     *
     *
     */
    leftTransfer1 (userInfo) {
      let data = this.leftData1;
      let paths = this.getpaths(this.leftData1, userInfo.accountNumber);
      this.arrayTraversal(data, paths, userInfo.accountNumber);
      this.rightData1 = this.deleteRepeatData(this.getRightData(this.leftData1));
      this.$emit("rightData", this.rightData1);
      // 获取userInfo的父节点，不包含兄弟节点
    },
    leftTransfer4 (item) {
      let data = this.leftData4;
      this.leftData4 = this.cancelLeftCheck(data, item); // 点击时，使左侧树中与之对应的数据的checked为false
      this.rightData4 = this.$refs.tree4.getCheckedNodes(); // 右侧根据左侧勾选状态改变
      this.$emit("rightData", this.rightData4);
    },
    // type4:点击穿梭框右侧X的时候，找到相应节点取消选中状态
    cancelLeftCheck (data, cancelItem) {
      for (var i in data) {
        if (data[i].groupId === cancelItem.groupId) {
          this.$set(data[i], "checked", false);
          break;
        } else {
          if (data[i].children) {
            data[i].children = this.cancelLeftCheck(data[i].children, cancelItem);
          }
        }
      }
      return data;
    },
    /**
     * 遍历左侧树找到accountNumber的所有父节点
     */

    getpaths (obj, accountNumber, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (let i = 0; i < obj.length; i++) {
          this.getpaths(obj[i], accountNumber, o);
        }
      } else {
        for (let k in obj) {
          if (Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array") {
            this.getpaths(obj[k], accountNumber, o);
          }
          if (k === "accountNumber" && obj[k] === accountNumber) {
            obj["checked"] = false;
            let path = obj["path"]
              .substring(0, obj["path"].length - 1)
              .split("/");
            for (let j = 0; j < path.length; j++) {
              if (o.indexOf(path[j]) === -1) {
                o.push(path[j]);
              }
            }
          }
        }
      }
      return o;
    },

    /**
     * type==2的时候右侧点击X的时候，取消节点与右侧节点的选中状态
     */

    cancelCheckDemo2 (obj, userInfo) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (let i = 0; i < obj.length; i++) {
          this.cancelCheckDemo2(obj[i], userInfo);
        }
      } else {
        for (let k in obj) {
          if (Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array") {
            this.cancelCheckDemo2(obj[k], userInfo);
          }
          if (k === "groupId" && obj[k].indexOf(userInfo.groupId) !== -1) {
            obj.state = { checked: false };
          }
        }
      }
      return obj;
    },
    /**
     * 参数 左侧树   部门id的数组   用户的主账号
     * 方法属于 部门用户的类型
     * 该方法主要是 取消一个用户的选中，那个该用户在其他部门的账号也会被取消选中
     *
     * 取消父节点的全选
     */

    arrayTraversal (obj, groupIds, accountNumber) {
      for (let i = 0; i < obj.length; i++) {
        if (Object.prototype.toString.call(obj[i]).slice(8, -1) === "Array") {
          this.arrayTraversal(obj[i]);
        } else {
          this.objectTraversal(obj[i], groupIds, accountNumber);
        }
      }
      return obj;
    },
    objectTraversal (obj, groupIds, accountNumber) {
      for (let k in obj) {
        if (Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array") {
          this.arrayTraversal(obj[k], groupIds, accountNumber);
        } else {
          for (let i = 0; i < groupIds.length; i++) {
            if (obj.groupId === groupIds[i] && obj.accountNumber === "null") {
              obj.checked = false;
              obj["indeterminate"] = true;
            }
          }
          if (k === "accountNumber" && obj[k] === accountNumber) {
            obj["checked"] = false;
          }
        }
      }
    },

    /**
     * 参数 左侧树数据 空的数组对象
     * 获取左侧数据中Checked=true的数据
     * 返回值 为数组类型
     *
     *
     * 获取所有左侧被选中的数据
     */
    getRightData (obj, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.getRightData(obj[i], o);
        }
      } else if (typeof obj === "object") {
        for (var k in obj) {
          if (
            obj.accountNumber !== null &&
            obj.accountNumber !== "null" &&
            k === "checked" &&
            obj[k] === true
          ) {
            o.push(obj);
            break;
          } else if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            o = this.getRightData(obj[k], o);
          }
        }
      }
      return o;
    },
    /**
     *
     *
     *
     * 左侧点击树种的checkbox触发的方法
     * 返回值 val 右侧中被选中的数组   nowChechked  当前选中的节点的信息
     * 该方法主要判断 当点击复选框的时候 有两种情况
     * 取消复选框的情况 判断 右侧树属于哪种类型
     * 类型1 部门用户树
     * 类型2   部门树
     *
     *
     *
     *
     *
     */

    checkTreeData (val, nowChecked) {
      if (nowChecked.checked === true) {
        if (nowChecked.accountNumber !== "null") {
          // 选中用户的时候执行该逻辑
          if (this.rightData1 !== null) {
            // 判断右侧数据是否为空的 不为空的时候往里面加入，并且删除重复值
            this.rightData1.push(nowChecked);
            this.rightData1 = this.deleteRepeatData(this.rightData1);
            this.$emit("rightData", this.rightData1);
          } else {
            // 为空直接加入
            this.rightData1.push(nowChecked);
            this.$emit("rightData", this.rightData1);
          }
        } else {
          // 选中部门的时候执行该逻辑  获取该部门下的所有人 并且去重，
          this.rightData1 = this.gainCheckedAllAccountNumber(nowChecked);
          this.$emit("rightData", this.rightData1);
        }
      } else {
        // 当点击取消选中的时候执行该逻辑
        let o = this.getCheckedUser(nowChecked); // 获取该部门下的所有人信息
        let paths = [];
        let accountNumbers = [];
        for (let c = 0; c < val.length; c++) {
          // 遍历所有的用户  把在不同部门的同个账号的路径 加入到path path为【“/1/2/3/”，“3/4/5”】的数组
          for (let d = 0; d < o.length; d++) {
            if (val[c]["accountNumber"] === o[d]["accountNumber"]) {
              if (accountNumbers.indexOf(val[c]["accountNumber"]) === -1) {
                accountNumbers.push(val[c]["accountNumber"]);
              }
              let path = val[c]["path"]
                .substring(0, val[c]["path"].length - 1)
                .split("/");
              for (let e = 0; e < path.length; e++) {
                if (paths.indexOf(path[e]) === -1) {
                  paths.push(path[e]);
                }
              }
            }
          }
        }

        for (let l = 0; l < accountNumbers.length; l++) {
          // 找到每个accountNumber的节点并且取消该节点的选中状态
          this.arrayTraversal(this.leftData1, paths, accountNumbers[l]);
        }
        this.rightData1 = this.deleteRepeatData(
          this.getRightData(this.leftData1)
        ); // 重新获取左侧树的选中数据，并且删除重复数据
        this.$emit("rightData", this.rightData1); // 把右侧数据返回给父组件

        // this.rightData = this.deleteRepeatData(
        //   this.initRightData(this.leftData)
        // );
        // this.$emit("rightData", this.rightData);
      }
    },
    /**
     * 参数：obj为一个节点的数据 o为数组对象
     * 遍历当前数据并获得
     * 当前节点下是用户的节点，把用户的节点 放到数组中返回
     *
     */
    getCheckedUser (obj, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.getCheckedUser(obj[i], o);
        }
      } else if (typeof obj === "object") {
        for (var k in obj) {
          if (k === "accountNumber" && obj[k] !== "" && obj[k] !== "null") {
            o.push(obj);
          } else if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.getCheckedUser(obj[k], o);
          }
        }
      }
      return o;
    },
    getCheckTreeData4 (checkedArr, checkedItem) {
      if (checkedItem.checked) {
        this.getAllSonNodes(this.leftData4, checkedItem);
      }
      this.rightData4 = this.$refs.tree4.getCheckedNodes();
      this.$emit("rightData", this.rightData4);
    },
    // 选中父节点，选中父节点下面所有子节点
    getAllSonNodes (data, checkedItem) {
      for (var i in data) {
        if (data[i].groupId === checkedItem.groupId) {
          data[i].children = this.treeChangeSel(data[i].children, "checked", true);
        } else if (data[i].children) {
          data[i].children = this.getAllSonNodes(data[i].children, checkedItem);
        }
      }
      return data;
    },
    treeChangeSel (treeData, type, flag) {
      console.log(treeData);
      for (var i in treeData) {
        this.$set(treeData[i], type, flag); // 重要！用set方法
        if (treeData[i].children) {
          treeData[i].children = this.treeChangeSel(
            treeData[i].children,
            type,
            flag
          );
        }
      }
      return treeData;
    },
    /**
     *
     * 参数  点击复选框的某个节点
     * 获取当前选中的节点下的所有用户
     *
     * 和右侧的数据去重后的数据
     * 并且往右侧数据中添加不重复的数据并返回
     */
    gainCheckedAllAccountNumber (checkedData) {
      let checkDataArray = [];
      checkDataArray.push(checkedData);
      checkDataArray = this.gainCheckedAccountNumber(checkDataArray); // 获取选中的用户去除部门
      let checked = this.rightData1;
      for (var i = 0; i < checkDataArray.length; i++) {
        if (checked.length === 0) {
          checked.push(checkDataArray[i]);
        } else {
          let flag = false;
          for (var k = 0; k < checked.length; k++) {
            if (checkDataArray[i].accountNumber === checked[k].accountNumber) {
              flag = true;
            }
          }
          if (flag === false) {
            checked.push(checkDataArray[i]);
          }
        }
      }
      return checked;
    },

    /**
     *
     * 参数
     * 获取点击选中的用户去除部门
     */
    gainCheckedAccountNumber (obj, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.gainCheckedAccountNumber(obj[i], o);
        }
      } else if (typeof obj === "object") {
        for (var k in obj) {
          if (
            obj[k] === true &&
            k === "checked" &&
            obj.accountNumber !== "null" &&
            obj.accountNumber !== ""
          ) {
            o.push(obj);
          } else if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array"
          ) {
            o = this.gainCheckedAccountNumber(obj[k], o);
          }
        }
      }
      return o;
    },

    /**
     * 去除成员的数组中重复元素
     */

    deleteRepeatData (obj) {
      var noRepeatData = [];
      for (var i = 0; i < obj.length; i++) {
        let flag = false;
        if (i === 0) {
          flag = false;
        } else {
          for (var k = 0; k < noRepeatData.length; k++) {
            if (obj[i].accountNumber === noRepeatData[k].accountNumber) {
              flag = true;
            }
          }
        }
        if (flag === false) {
          noRepeatData.push(obj[i]);
        }
      }
      return noRepeatData;
    },
    /**
     * 参数 右侧树数据
     * 初始化右侧选中数据
     */
    initRight (newValue) {
      if (this.type === 1) {
        // 当类型为1的时候 即用户部门树的时候，执行该逻辑
        this.leftData1 = newValue;
        this.rightData1 = this.deleteRepeatData(
          this.initRightData(this.leftData1)
        );
        this.$emit("rightData", this.rightData1);
      } else if (this.type === 2) {
        // 当类型为2的时候，即部门树的时候执行该逻辑
        this.leftData2 = newValue;
        this.rightData2 = this.dataRight(this.leftData2);
        this.$emit("rightData", this.rightData2);
      } else if (this.type === 3) {
        // 当类性为3的时候，即安全组穿梭框的时候执行该逻辑
        this.leftData3 = newValue;
        this.rightData3 = this.getRightDataList(this.leftData3);
        this.$emit("rightData", this.rightData3);
      } else if (this.type === 4) {
        // 当类性为4的时候，即部门批量选择的时候执行
        this.leftData4 = newValue;
        this.rightData4 = this.$refs.tree4.getCheckedNodes();
        this.$emit("rightData", this.rightData4);
      }
    },
    /**
     * 类型为3 安全组穿梭框需要的方法
     * 该方法主要是获取 右侧数据的数据
     *   获取列表右侧数据
     */
    getRightDataList (obj) {
      let o = [];
      for (let i = 0; i < obj.length; i++) {
        if (obj[i].state !== null && obj[i].state.checked === true) {
          o.push(obj[i]);
          this.teams.push(obj[i].id);
        }
      }
      return o;
    },
    /**
     *   该方法属于类型为2的时候即部门树的方法
     * 参数 左侧树的数据
     * 该方法主要是从左侧书中获取state.checked为true的数据  并添加到数组中，返回数组     */
    dataRight (obj, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.dataRight(obj[i], o);
        }
      } else if (typeof obj === "object") {
        for (var k in obj) {
          if (k === "state" && obj[k].checked === true) {
            o.push(obj);
            break;
          } else if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.dataRight(obj[k], o);
          }
        }
      }
      return o;
    },
    /**
     * 类型为1 即部门用户树的类型
     * 参数为 左侧树数据
     *
     * 该方法主要是从左侧书中获取checked为true的数据  并添加到数组中，返回数组
     */
    initRightData (obj, o = []) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.initRightData(obj[i], o);
        }
      } else if (typeof obj === "object") {
        for (var k in obj) {
          if (k === "checked" && obj[k] === true) {
            o.push(obj);
          } else if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.initRightData(obj[k], o);
          }
        }
      }
      return o;
    },

    /**
     * 搜索框时执行的方法
     * 三种类型同用该方法

     *
     */
    searchDataMethod () {
      if (this.searchDefaultData === "") {
        // 当搜索框的值为空的时候，初始化树，把所有之前搜到的值的seleced为false
        if (this.type === 1) {
          this.leftData1 = this.initSearchDataTree(this.leftData1);
        } else if (this.type === 2) {
          this.leftData2 = this.initSearchDataTree(this.leftData2);
        } else if (this.type === 3) {
          this.initSearchData(this.leftData3);
        } else if (this.type === 4) {
          this.leftData4 = this.initSearchDataTree(this.leftData4);
        }
      } else {
        if (this.type === 1) {
          // 如果搜索框的内容不为空的时候且搜索框处为用户部门树下执行该逻辑
          this.groupIdDemo = []; // 初始化搜索的id
          this.leftData1 = this.initSearchDataTree(this.leftData1); // 初始化选中状态
          this.leftData1 = this.searchDataTree(this.leftData1); // 搜索树的数据将搜到的值的selected 变为true  并将伏所有的找到的节点的path放入groupIdDemo中
          let temp = [];
          for (let i = 0; i < this.groupIdDemo.length; i++) {
            // 对path  【“1/2/3/”，“3/4/5/”】 进行处理获取所有的不重复的groupid的数组
            this.groupIdDemo[i] = this.groupIdDemo[i].substring(
              0,
              this.groupIdDemo[i].length - 1
            );

            let temp1 = this.groupIdDemo[i].split("/");

            for (let k = 0; k < temp1.length; k++) {
              if (temp.indexOf(temp1[k]) === -1 && temp1[k] !== "") {
                temp.push(temp1[k]);
              }
            }
          }
          for (let g = 0; g < temp.length; g++) {
            // 对groupids进行遍历 统一将该找到的节点的expand 变为true
            this.leftData1 = this.searchDataTree1(this.leftData1, temp[g]);
          }
        } else if (this.type === 2) {
          // 如果搜索框的内容不为空的时候且搜索框处为部门树下执行该逻辑
          this.groupIdDemo = [];
          this.leftData2 = this.initSearchDataTree(this.leftData2);
          this.leftData2 = this.searchDataTree(this.leftData2);
          let temp = [];
          for (let c = 0; c < this.groupIdDemo.length; c++) {
            this.groupIdDemo[c] = this.groupIdDemo[c].substring(
              0,
              this.groupIdDemo[c].length - 1
            );

            let temp1 = this.groupIdDemo[c].split("/");

            for (let d = 0; d < temp1.length; d++) {
              if (temp.indexOf(temp1[d]) === -1 && temp1[d] !== "") {
                temp.push(temp1[d]);
              }
            }
          }
          for (let e = 0; e < temp.length; e++) {
            this.leftData2 = this.searchDataTree1(this.leftData2, temp[e]);
          }
        } else if (this.type === 3) {
          // 如果搜索框的内容不为空的时候且搜索框处安全组下执行该逻辑
          this.initSearchData(this.leftData3); // 初始化左侧数据
          this.searchData(this.leftData3); // 搜索左侧数据，并将搜到节点的selected 变为true
        } else if (this.type === 4) {
          // 如果搜索框的内容不为空的时候且搜索框处为部门树下执行该逻辑
          this.groupIdDemo = [];
          this.leftData4 = this.initSearchDataTree(this.leftData4);
          this.leftData4 = this.searchDataTree(this.leftData4);
          let temp = [];
          for (let c = 0; c < this.groupIdDemo.length; c++) {
            this.groupIdDemo[c] = this.groupIdDemo[c].substring(
              0,
              this.groupIdDemo[c].length - 1
            );

            let temp1 = this.groupIdDemo[c].split("/");

            for (let d = 0; d < temp1.length; d++) {
              if (temp.indexOf(temp1[d]) === -1 && temp1[d] !== "") {
                temp.push(temp1[d]);
              }
            }
          }
          for (let e = 0; e < temp.length; e++) {
            this.leftData4 = this.searchDataTree1(this.leftData4, temp[e]);
          }
        }
      }
    },

    /**
     * 参数 左侧树数据  groupId
     * 该方法主要是 通过groupId来把找到该groupId的节点
     * 并将找到的节点的expand变为true
     *
     *
     *
     */
    searchDataTree1 (obj, groupId) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.searchDataTree1(obj[i], groupId);
        }
      } else {
        for (var k in obj) {
          if (
            obj[k] !== null &&
            obj[k] !== "null" &&
            obj[k] !== "" &&
            k === "groupId"
          ) {
            if (obj[k] === groupId) {
              obj["expand"] = true;
              // obj["expand"]=true;
            }
          }
          if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.searchDataTree1(obj[k], groupId);
          }
        }
      }
      return obj;
    },

    /***
     *
     * 该类型属于安全组下的搜索
     * 参数为 左侧安全组数据
     * 将查询到的数据的selected 变为true
     *
     *
     *
     */

    searchData (obj) {
      for (let i = 0; i < obj.length; i++) {
        if (obj[i].teamName.indexOf(this.searchDefaultData) !== -1) {
          obj[i].selected = true;
        }
      }
    },

    /**
     * 参数 左侧安全组的数据
     *
     * 对安全组下的数据统一将selected 变为false
     *
     *
     */
    initSearchData (obj) {
      for (let i = 0; i < obj.length; i++) {
        obj[i].selected = false;
      }
    },

    /**
     *
     * 对类型为1  即用户部门树，某个属性初始化
     * 参数  左侧树的数据
     * 统一将每个节点的select和expand变为false
     * 返回值为 tree的类型
     */
    initSearchDataTree (obj) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.initSearchDataTree(obj[i]);
        }
      } else {
        for (var k in obj) {
          if (
            (obj[k] !== null &&
              obj[k] !== "null" &&
              obj[k] !== "" &&
              k === "selected") ||
            k === "expand"
          ) {
            obj[k] = false;
          }
          if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.initSearchDataTree(obj[k]);
          }
        }
      }

      return obj;
    },

    /**
     *
     * 对类型为2  即部门树，某个属性初始化
     * 参数  左侧树的数据
     * 统一将每个节点的selected和expand变为false
     * 返回值为 tree的类型
     */
    searchDataTree (obj) {
      if (Object.prototype.toString.call(obj).slice(8, -1) === "Array") {
        for (var i = 0; i < obj.length; i++) {
          this.searchDataTree(obj[i]);
        }
      } else {
        for (var k in obj) {
          if (
            obj[k] !== null &&
            obj[k] !== "null" &&
            obj[k] !== "" &&
            k === "title"
          ) {
            if (obj[k].indexOf(this.searchDefaultData) !== -1) {
              obj["selected"] = true;
              // obj["expand"]=true;
              let path = obj.path;
              if (path !== null && path !== "") {
                this.groupIdDemo.push(path);
              }
            }
          }
          if (
            Object.prototype.toString.call(obj[k]).slice(8, -1) === "Array" &&
            k === "children"
          ) {
            this.searchDataTree(obj[k]);
          }
        }
      }
      return obj;
    },
    /**
     * type为1时，tree展开收缩时调用，修改节点为人时的样式
     * @param {*void}
     * @author yezi 2019-09-02
     */
    changeCheckStyle () {
      this.$nextTick(() => {
        let persons = document.querySelectorAll(".person");
        for (let item of persons) {
          item.previousElementSibling.classList.add("person-circle");
        }
      });
    },
    /**
     *
     *里面自带的有该事件点击触发的方法
     *
     * 类型为1的树的显示rander函数
     */

    showSelectClass (h, { root, node, data }) {
      if (data.selected === true) {
        return h(
          "span",
          {
            style: {
              color: "red",
              width: "100%"
            },
            class: { person: data.uuid !== "null" }
          },
          [h("span", data.title)]
        );
      } else {
        return h("span", { class: { person: data.uuid !== "null" } }, data.title);
      }
    },

    /**
     *
     *
     *
     * 类型为2的树的显示rander函数
     */
    showSelectClass1 (h, { root, node, data }) {
      if (data.selected === true) {
        // 判断是否是搜索状态 如果是就把数字变为红色
        if (data.state !== "null" && data.state.checked === true) {
          return h("span", [
            h("Checkbox", {
              props: {
                value: true
              },
              on: {
                "on-change": flag => {
                  if (flag === true) {
                    data.state = { checked: true };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  } else {
                    data.state = { checked: false };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  }
                }
              }
            }),
            h(
              "span",
              {
                style: {
                  color: "red",
                  width: "100%"
                }
              },
              data.title
            )
          ]);
        } else {
          return h(
            "span",
            [
              h("Checkbox", {
                on: {
                  "on-change": flag => {
                    if (flag === true) {
                      data.state = { checked: true };
                      this.rightData2 = this.dataRight(this.leftData2);
                      this.$emit("rightData", this.rightData2);
                    } else {
                      data.state = { checked: false };
                      this.rightData2 = this.dataRight(this.leftData2);
                      this.$emit("rightData", this.rightData2);
                    }
                  }
                }
              }),
              h(
                "span",
                {
                  style: {
                    color: "red",
                    width: "100%"
                  }
                },
                data.title
              )
            ]
          );
        }
      } else {
        // 判断是否是搜索状态 如果不是就把内容变为黑色
        if (data.state !== "null" && data.state.checked === true) {
          return h("span", [
            h("Checkbox", {
              props: {
                value: true
              },
              on: {
                "on-change": flag => {
                  if (flag === true) {
                    data.state = { checked: true };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  } else {
                    data.state = { checked: false };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  }
                }
              }
            }),
            h("span", data.title)
          ]);
        } else {
          return h("span", [
            h("Checkbox", {
              on: {
                "on-change": flag => {
                  if (flag === true) {
                    data.state = { checked: true };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  } else {
                    data.state = { checked: false };
                    this.rightData2 = this.dataRight(this.leftData2);
                    this.$emit("rightData", this.rightData2);
                  }
                }
              }
            }),
            h("span", data.title)
          ]);
        }
      }
    }
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/common.less";
.tree-transfer {
  padding: 8px 34px;
}
.tree-transfer > div {
  width: 280px;
  border: 1px solid #979797;
  height: 400px;
}
.tree-transfer-search {
  padding: 14px 24px 14px 16px;
}
.posiotion {
  display: -webkit-inline-box;
}
.tabs {
  border-bottom: none;
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-size: 14px;
}
.tabs /deep/ .ivu-tabs-nav .ivu-tabs-tab-active {
  color: #62bfff !important;
}
.tabs /deep/ .ivu-tabs-ink-bar {
  background-color: #62bfff;
}
.tabs /deep/ .nav-text {
  padding-bottom: 10px;
  margin-bottom: 10px;
  margin-left: 15px;
}
.demo {
  overflow-y: auto;
}
.choose {
  background-color: #e7e7e7;
  text-align: center;
  height: 56px;
  line-height: 56px;
  border-bottom: 1px solid #979797;
  font-size: 16px;
  color: #6d7278;
}
.borderSetting {
  border-width: 1px;
  border-style: solid;
  border-color: rgba(232, 232, 232, 1);
  border-radius: 4px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
}
.borderSetting1 {
  border-width: 1px;
  border-style: solid;
  border-color: rgba(232, 232, 232, 1);
  border-radius: 4px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
  height: 400px;
}
.height2 {
  padding-left: 10px;
  height: calc(100% - 120px);
  overflow-y: auto;
  overflow-x: auto;
  padding-right: 10px;
}

.height3 {
  padding-top: 52px;
  height: calc(100% - 56px);
  overflow-y: auto;
  overflow-x: auto;
}
.alien_right {
  text-align: right;
}
.nowhite {
  white-space: nowrap;
}
.checkBoxClass {
  display: grid;
}
.modalClass {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.647058823529412);
  text-align: left;
  line-height: 22px;
}
.modalClass1 {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.647058823529412);
  text-align: left;
  line-height: 22px;
}
.modalClass /deep/ span {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  text-align: left;
  line-height: 22px;
}
.padding {
  padding-left: 20px;
  padding-right: 20px;
}
.padding1 {
  padding-left: 10px;
  padding-right: 10px;
  padding-top: 5px;
  padding-bottom: 5px;
}
/deep/ .person-circle .ivu-checkbox-inner {
  border-radius: 50%;
}
</style>
