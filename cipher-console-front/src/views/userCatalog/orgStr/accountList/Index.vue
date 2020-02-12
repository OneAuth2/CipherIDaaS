<template>
  <div class="org-list clearfix">
    <div class="org-list-left">
      <AccountListLeft @change="selObjChange"
                       @showAll="showAll"
                       :changeTree="changeTree"
                       :skipMenuPath="skipMenuPath"></AccountListLeft>
    </div>
    <div class="org-list-right">
      <AccountListTop :showAllTemp="showAllFlag"
                      :selObj="selObj"
                      :navArray="navArray"
                      @changeLeftTree="changeLeftTree"
                      @skipMenu="getSkipMenu"></AccountListTop>
      <AccountListTable :selObj="selObj"
                        :initPageNum="initPageNum"
                        :initPageSize="initPageSize"
                        :showAllTemp="showAllFlag"
                        @changeLeftTree="changeLeftTree"></AccountListTable>
    </div>
  </div>
</template>

<script>
import AccountListLeft from "@/views/userCatalog/orgStr/accountList/Left.vue";
import AccountListTop from "@/views/userCatalog/orgStr/accountList/Top.vue";
import AccountListTable from "@/views/userCatalog/orgStr/accountList/Table.vue";
import { mapState } from "vuex";
export default {
  name: "Index",
  components: {
    AccountListLeft,
    AccountListTable,
    AccountListTop
  },
  data () {
    return {
      selObj: {},
      navArray: [],
      initPageNum: 1, // 初始化显示页码号为1
      initPageSize: 10, // 初始化每页显示条数
      showAllFlag: true, // 是否显示全部用户，true显示全部用户，false根据左侧组织树显示
      changeTree: false,
      skipMenuPath: []
    };
  },
  computed: {
    // ...mapState({ orgStrList: "selectOSTree" })
    ...mapState(["group"])
  },
  watch: {
    group () {
      if (this.group) {
        this.selObj.groupId = this.group.groupId;
      }
    }
  },
  methods: {
    selObjChange (selObj) {
      this.showAllFlag = false;
      this.selObj = selObj;
      this.createNavMenu();
    },
    showAll () {
      this.showAllFlag = true;
      this.selObj = { showAllFlag: this.showAllFlag }; // 设置为显示全部
    },
    // 创建导航菜单
    createNavMenu () {
      this.navArray = [];
      if (!this.selObj.arrayPath) { // 如果未返回，tree根下第一个元素
        let temp = this.selObj;
        temp.arrayPath = this.selObj.rootNode;
        temp.path = this.selObj.groupName;
        temp.groupId = this.selObj.groupId;
        this.navArray.push(temp);
      } else {
        let arrayPath = this.selObj.arrayPath.split("/"); // 选中的元素 左侧树数组序号位置
        let path = this.selObj.path.split("/");
        let groupIdPath = this.selObj.idPath.split("/");
        for (let i = 0; i < path.length - 1; i++) {
          let temp = {};
          let tempArrayPath = [];
          for (let j = 0; j < i + 1; j++) { // 导航节点对应的 左侧树数组序号位置 如[0,0,1] 则表示在左侧树treeList[0][0][1]
            tempArrayPath.push(arrayPath[j]);
          }
          temp.arrayPath = tempArrayPath;
          temp.path = path[i];
          temp.groupId = groupIdPath[i];
          this.navArray.push(temp);
        }
      }
    },
    // 触发更新左侧组织树
    changeLeftTree () {
      this.changeTree = !this.changeTree;
    },
    // 触发menu跳转,获取跳转路径，对应左侧树 数组序号
    getSkipMenu (data) {
      this.skipMenuPath = data.arrayPath; // 选中面包屑的路径，根据路径改变左侧树的展开和选中项
      this.selObj = {};
      this.selObj.href = "/cipher/newUser/userlist?json";
      this.selObj.groupId = data.groupId; // 选中的groupId变成选中的groupID，触发table数据改变
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
.org-list {
  width: 100%;
  height: 100%;
}
.org-list-left,
.org-list-right {
  height: 100%;
  .border-radius;
  float: left;
}
.org-list-left {
  width: 200px;
  background-color: #fff;
  box-shadow: 0px 2px 4px 0px rgba(206, 206, 206, 1);
}

.org-list-right {
  width: calc(~"100% - 200px");
  padding-left: 8px;
}
</style>
