<template>
  <div class="org-leftTree"
       ref="leftTree">
    <div class="org-leftTree__search">
      <myInput :search="true"
               radius="half"
               v-model="keyword"
               placeholder="输入要搜索的内容" />
    </div>
    <span @click="showAllObj"
          :class="{'org-leftTree__all--active':orgBtnActive}"
          class="org-leftTree__all">
      <Icon type="md-home" />
      <span>全部用户</span>
    </span>
    <Tree :data="orgList"
          class="tree"
          :render="renderContent"
          ref="tree"></Tree>
  </div>
</template>
<script>
import { mapMutations, mapState } from "vuex";
import api from "@/api/userCatalog/index";
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
      keyword: "", // 搜索关键字
      orgList: [],
      orgBtnActive: true, // 全部用户按钮激活状态
      selObj: {}, // 当前点选树中的某个节点对象
      orgList_bak: [], // 左侧导航树数据备份
      selTempObj: {} // 当前选中节点的临时存储，增加数据
    };
  },
  props: {
    changeTree: {
      type: Boolean,
      default: false
    }, // 在top组件和table组件中对此值更改
    skipMenuPath: {
      type: Array,
      default: function () {
        return [];
      }
    } // 选中的面包屑路径
  },
  mounted () {
    this.getOrgList();
  },
  computed: {
    ...mapState(["selectOSTree", "group"])
  },
  watch: {
    changeTree: function () {
      this.getOrgList();
    },
    keyword (val, oldVal) {
      this.setExpandData("title", this.keyword);
    },
    group () {
      if (!this.group) {
        // 当工作组为空时，将路径展开关闭
        this.exchangeTree(false, "selected");
      } else {
        this.$nextTick(() => {
          this.orgBtnActive = false;
          let btns = this.$refs.tree.$el.querySelectorAll(".orgLeftBtn");
          for (let i = 0; i < btns.length; i++) {
            btns[i].style.backgroundColor = "#fff";
          }
        });
      }
    },
    skipMenuPath () { // menu跳转路径
      let btns = this.$refs.tree.$el.querySelectorAll(".orgLeftBtn");
      for (let i = 0; i < btns.length; i++) {
        btns[i].style.backgroundColor = "#fff";
      }
      let length = this.skipMenuPath.length;
      this.exchangeTree(false, "selected");
      let node = "";
      node = this.orgList[this.skipMenuPath[0]];
      if (length === 1) { // 选中根节点
        this.$set(this.orgList[this.skipMenuPath[0]], "selected", true);
      } else { // 选择非根节点
        for (var i = 1; i < length - 1; i++) {
          node = node.children[this.skipMenuPath[i]];
          this.$set(node, "expand", true);
        }
        this.$set(node.children[this.skipMenuPath[length - 1]], "selected", true);
      }
    }
  },
  methods: {
    /**
     * 获取用户组织列表树
     * @param {*void}
     * @author menglei 2019-07-30
     */
    getOrgList () {
      let params = {};
      this.axios({
        method: "post",
        data: params,
        url: api.orgList
      })
        .then(response => {
          if (response.status === api.statusOk) {
            let btns = this.$refs.tree.$el.querySelectorAll(".orgLeftBtn");
            for (let i = 0; i < btns.length; i++) {
              btns[i].style.backgroundColor = "#fff";
            }
            this.orgList = this.$common.cloneAssemblyAssignData(
              response.data,
              cloneChangeKeys,
              true
            );
            this.orgList_bak = this.$common.cloneAssemblyAssignData(
              response.data,
              cloneChangeKeys,
              true
            );
            if (this.group) {
              // 如果有工作部门，展开相应部门
              this.changeSelGroupItem();
            }
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * iview渲染函数
     * @param 详见iview
     * @author menglei 2019-07-30
     */
    renderContent (h, { root, node, data }) {
      return h(
        "span",
        {
          style: {
            display: "inline-block",
            width: "100%",
            padding: "15px",
            cursor: "pointer"
          },
          class: { selItem: data.selected === true, orgLeftBtn: true }
        },
        [
          h(
            "span", {
              on: {
                click: e => {
                // 点击菜单的时候清空ddetail页面跳转清空返回部门数据
                  if (this.group) {
                    this.changeGroup("");
                  }
                  this.setSelItemState(e);
                  this.handleRelatedItem(data);
                }
              }
            },
            [
              h("span", {
                domProps: { // 添加标签,使用自己引入的iconfont图标
                  innerHTML: "<i class='icon-tree'></i>"
                },
                style: {
                  width: "25px",
                  display: data.uuid !== "null" ? "none" : "inline-block"
                }
              }),
              h("Icon", {
                props: {
                  type: "md-person"
                },
                style: {
                  width: "25px",
                  display: data.uuid === "null" ? "none" : "inline-block"
                }
              }),
              h(
                "span",
                {
                  style: {
                    display: "inline-block",
                    width: "100%"
                  }
                }, data.title
              )
            ]
          )
        ]
      );
    },
    /**
     * 生成右侧menu导航需要的数据
     * @param {*String} key
     * @param {*Number} selItem
     * @author menglei 2019-07-31
     */
    generateRightMenuData (key, selItem) {
      this.getSelData(this.orgList, key, selItem);
      this.selObj.arrayPath = this.selTempObj.selPath; // 选中节点对应数数组位置
    },
    /**
     * 执行选中组织结构树后相关操作（生成存储数据，向兄弟组件发送变更消息）
     * @param {*Object工作部门信息对象} data
     * @author menglei 2019-07-31
     */
    handleRelatedItem (data) {
      this.generateRightMenuData("nodeKey", data.nodeKey);
      this.selectOSTree.selectTree = this.selObj;
      this.changeSelectOSTree(this.selectOSTree);
      this.$emit("change", this.selObj);
    },
    /**
     * 改变选中节点，展开相应部门
     * @param {*void}
     * @author menglei 2019-07-30
     */
    // changeSelGroupItem () {
    //   console.log(this.group);
    //   let groupId = this.group.groupId;
    //   let result = this.$common.deepFind(this.orgList, (item, index, level) => item.groupId === groupId.toString(), "children");
    //   this.selObj = result[result.length - 1];
    //   console.log(this.selObj);
    //   this.generateRightMenuData("groupId", this.selObj.groupId);
    //   this.selectOSTree.selectTree = this.selObj;
    //   this.changeSelectOSTree(this.selectOSTree);
    //   this.$emit("change", this.selObj);
    //   this.setExpandData("title", this.selObj.groupName);
    // },
    changeSelGroupItem () {
      this.selObj = this.group;
      this.generateRightMenuData("groupId", this.selObj.groupId);
      this.selectOSTree.selectTree = this.selObj;
      this.changeSelectOSTree(this.selectOSTree);
      this.$emit("change", this.selObj);
      this.setExpandData("title", this.selObj.groupName);
    },
    /**
     * 设置需要展开的路径
     * @param {*Object} obj
     * @author menglei 2019-08-02
     */
    setExpandData (searchKey, searchString) {
      let expandItemArray = []; // 要展开的路径数组
      let orgList = this.$common.searchCloneData(
        this.orgList_bak,
        [searchKey],
        searchString,
        expandItemArray
      ); // 搜索标记对应item
      this.orgList = this.$common.setExpandData(orgList, expandItemArray);
    },
    // 获取选中节点对象，保存在selObj
    getSelData (orgList, key, selItem) { // orgList左侧树数据，key表示需要查找的key值，selItem表示选中数据相应key值对应的值
      for (var i in orgList) {
        let item = orgList[i];
        let childrenItems = orgList[i].children;
        if (childrenItems) {
          var temp = this.getSelData(childrenItems, key, selItem);
          if (temp) {
            let selPath = `${i}/${temp}`;
            this.selTempObj.selPath = selPath;
            return selPath;
          }; // 选中元素对应树数组位置
        }
        if (item[key] === selItem) {
          this.selObj = item;
          return i;
        }
      }
    },
    /**
     * 点击显示所有
     * @param {*void}
     * @author yezi 2019-09-10
     */
    showAllObj () {
      let btns = this.$refs.tree.$el.querySelectorAll(".orgLeftBtn");
      for (let i = 0; i < btns.length; i++) {
        btns[i].style.backgroundColor = "#fff";
      }
      this.orgBtnActive = true;
      this.$emit("showAll");
    },
    /**
     * 设置选中项状态
     * @param {*Object鼠标事件对象} e
     * @author menglei 2019-07-31
     */
    setSelItemState (e) {
      this.orgBtnActive = false;
      let btns = this.$refs.tree.$el.querySelectorAll(".orgLeftBtn");
      for (let i = 0; i < btns.length; i++) {
        btns[i].style.backgroundColor = "#fff";
      }
      let path = this.$common.eventPath(event);
      let item = path[2].style;
      item.backgroundColor = "#d2f0f9"; // 当前点击的元素
    },
    // 修改树中某属性所有节点的状态，如type时'expand'时:当flag为true时全部展开，flag为false时全部合并
    exchangeTree (flag, type) {
      this.orgList = this.treeChangeSel(this.orgList, flag, type);
    },
    treeChangeSel (treeData, flag, type) {
      for (var i in treeData) {
        this.$set(treeData[i], type, flag); // 重要！用set方法
        if (treeData[i].children) {
          treeData[i].children = this.treeChangeSel(
            treeData[i].children,
            flag,
            type
          );
        }
      }
      return treeData;
    },
    ...mapMutations(["changeSelectOSTree", "changeGroup"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/fonts/iconfont.css";
@import "~@/assets/styles/iviewTree.less";
@import "~@/assets/styles/custom.less";
.org-leftTree {
  padding: 15px 0;
  height: 100%;
  overflow-x: hidden;
  overflow-y: auto;
}
.org-leftTree__search {
  padding: 0 10px;
}
.org-leftTree__all {
  display: block;
  margin-top: 10px;
  padding: 15px;
  text-align: left;
  cursor: pointer;
  i {
    display: inline-block;
    width: 25px;
    font-size: 20px;
    vertical-align: middle;
    position: relative;
    top: -2px;
    left: -2px;
  }
  span {
    height: 100%;
    display: inline-block;
    vertical-align: middle;
  }
}
.org-leftTree /deep/ ul > li:hover > span {
  color: @colorPrimary!important;
}
.org-leftTree__all--active {
  background: rgba(210, 240, 249, 1);
}
.org-leftTree /deep/ div.ivu-tree > ul {
  font-size: 16px;
}
.org-leftTree /deep/ div.ivu-tree li {
  text-align: left;
}
.org-leftTree /deep/ .ivu-tree li ul {
  font-size: 16px;
}
.org-leftTree /deep/ ul > li span:nth-child(2) {
  font-size: 14px;
}
/* 加载状态 */
.spin-icon-load {
  animation: ani-demo-spin 1s linear infinite;
}

/* 组织结构左侧树选中状态 */
.tree /deep/ .selItem {
  color: red;
}
</style>
