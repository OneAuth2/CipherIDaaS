<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="searchName"
                 radius="half"
                 :search="true"
                 @handleClick="searchUser" />
      </div>
      <div class="tabled-header-right">
        <mySelect showString="选择状态"
                  :dataList="statusList"
                  v-model="status"
                  @on-change="changeStatus" />
        <myButton btnType="info"
                  @click="showTreeMadal">批量下发</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table border
             :columns="title"
             :data="list"></Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="currentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="changePageSize"></Page>
    <!-- 确认框 -->
    <Confirm v-if="confirm.flag"
             :flag="confirm.flag"
             :title="confirm.title"
             :content="confirm.content"
             :okString="confirm.okString"
             :onOk="confirm.onOk"
             :onClose="confirm.onClose"></Confirm>
    <!-- 下发的用户树 -->
    <Modal v-model="isShowTreeMadal"
           width="668"
           class="tree-transfer"
           @on-cancel="cancel">
      <p slot="header"
         class="header">
        <span>批量下发种子密钥</span>
      </p>
      <TreeTransfer :leftTreeData="leftTreeData"
                    :leftDataType="treeDataType"
                    @rightData="getRightData"
                    placeholder="输入用户">
        <span slot="leftText">待选用户</span>
        <span slot="rightText">已选用户</span>
      </TreeTransfer>
      <Checkbox v-model="isHideSented"
                class="tree-transfer-check"
                @on-change="changeHideSented">隐藏已下发种子密钥的账号</Checkbox>
      <div slot="footer">
        <Button type="primary"
                @click="sentDownBatch">下发</Button>
        <Button @click="resetTree">重置</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
import Confirm from "@/components/modal/Confirm.vue";
import { mapMutations } from "vuex";
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
  components: {
    Confirm,
    TreeTransfer
  },
  data () {
    return {
      name: "",
      count: 0,
      confirm: { // 确认框
        flag: false,
        title: "",
        content: "",
        okString: "继续",
        onOk: this.onConfirmOk,
        onClose: this.onConfirmClose
      },
      isReset: false,
      isShowTreeMadal: false, // 是否显示用户树modal
      treeData: [], // 初始获取的左侧树的数据
      leftTreeData: [], // 显示的左侧树数据，根据是否隐藏显示不同
      treeDataType: 0, // 树的种类
      isHideSented: true, // 是否隐藏已下发的账号,默认隐藏
      searchName: "", // 查询内容，v-model绑定的查询
      queryName: "", // 点击查询后保存的查询内容
      total: 0,
      currentPage: 1,
      size: 10,
      accountNumber: "",
      scratchCode: [],
      status: 0,
      statusList: [
        { value: 0, label: "全部" },
        { value: 1, label: "已下发" },
        { value: 2, label: "未下发" }
      ],
      userId: "", // 单个userId
      userIds: "", // 多个userId
      title: [
        {
          title: "账号",
          key: "userName",
          align: "center",
          render: (h, params) => {
            return h("a",
              {
                on: {
                  click: () => {
                    this.toUserDetail(params.index);
                  }
                }
              }, params.row.userName);
          }
        },
        {
          title: "部门",
          key: "groupNames",
          align: "center",
          render: (h, params) => {
            return h("a",
              {
                on: {
                  click: () => {
                    if (params.row.groupList) {
                      this.toGroupList(params.row.groupList[0]);
                    }
                  }
                }
              }, params.row.groupNames);
          }
        },
        {
          title: "种子密钥状态",
          key: "issueStatusDescribe",
          align: "center"
        },
        {
          title: "操作",
          key: "edit",
          width: 200,
          align: "center",
          render: (h, params) => {
            let isNotSent = params.row.issueStatusDescribe === "未下发";
            return h("div", [
              h("a", {
                class: { disabled: isNotSent },
                on: {
                  click: () => {
                    if (isNotSent) {
                      return;
                    }
                    this.isReset = true;
                    this.onConfirm("重置", params.row.userId);
                  }
                }
              }, "重置"),
              h("a", {
                on: {
                  click: () => {
                    this.isReset = false;
                    this.onConfirm(params.row.issueStatusDescribe, params.row.userId);
                  }
                }
              }, "下发种子")
            ]);
          }
        }
      ],
      list: []
    };
  },
  mounted () {
    this.getList();
  },
  methods: {
    /**
     * 搜索姓名
     */
    searchUser () {
      this.queryName = this.searchName;
      this.currentPage = 1;
      this.getList();
    },
    changeStatus () {
      this.currentPage = 1;
      this.getList();
    },
    changePageSize (size) {
      this.size = size;
      this.currentPage = 1;
      this.getList();
    },
    changePage (index) {
      this.currentPage = index;
      this.getList();
    },
    getList () {
      let params = {
        rows: this.size,
        page: this.currentPage,
        queryName: this.queryName,
        issueStatus: this.status
      };
      this.axios({
        method: "post",
        url: "/cipher/dynamic/list",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.list = res.data.rows;
            this.total = res.data.total;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
    * 点击列表中的用户，跳转到组织结构详情页
    * @param {*Number table中的序号} index
    * @author yezi 2019-07-31
    */
    toUserDetail (index) {
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.list[index].accountNumber, groupId: this.list[index].groupId, uuid: this.list[index].userId }
      });
    },
    /**
     * 跳转到相应组织结构
     * @param {*Object 工作部门信息} data
     * @author yezi 2019-07-31
     */
    toGroupList (group) {
      this.$router.push({
        path: "/userCatalogOS"
      });
      this.changeGroup(group);
    },
    /**
    *  点击扫描 或 同步的确认按钮
    * @param {*String 未下发,已下发} status
    * @author yezi 2019-09-11
    */
    onConfirm (status, userId) { // 点击table中的种子秘钥下发触发
      // let isReset = status === "重置" || status === "已下发";
      // console.log(isReset);
      this.confirm.title = status === "重置" ? "本次操作将重置该用户的种子密钥" : "本次操作将向用户下发种子密钥";
      this.confirm.content = status === "重置" ? "该用户在绑定新的种子密钥前将无法登录，你还要继续吗？" : "用户扫码绑定后可以使用动态密码。";
      this.confirm.flag = true;
      this.userId = userId;
    },
    /**
    *  点击取消，关闭确认框
    * @param {*void}
    * @author yezi 2019-09-11
    */
    onConfirmClose () { //  点击取消，关闭确认框
      this.confirm.flag = false;
    },
    /**
    *  点击确认
    * @param {*void}
    * @author yezi 2019-09-11
    */
    onConfirmOk () { // 点击确认，进行扫描或同步
      this.confirm.flag = false;
      this.sentDown();
    },
    /**
     * 单个下发种子密钥
     * @param {*void}
     * @author yezi 2019-09-11
     */
    sentDown () {
      let url = this.isReset ? "/cipher/dynamic/reset" : "/cipher/dynamic/issue"; // 是否重置，调用不同的接口
      let params = {
        userId: this.userId
      };
      this.axios({
        method: "post",
        url: url,
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.getList();
            this.$myModal.success({
              title: this.isReset ? "重置成功" : "下发成功",
              content: res.data.return_msg
            });
          } else {
            this.$myModal.error({
              title: this.isReset ? "重置失败" : "下发失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 左侧树隐藏已选择的节点
     * @param {*Array 需要操作的数组} treeData
     * @param {*String 判断的key值} key
     * @param {*[Boolean,String] 判断key值条件} keyValue
     */
    initHideTree (treeData, key, keyValue = true) {
      for (let i = treeData.length - 1; i >= 0; i--) {
        let item = treeData[i];

        if (item[key] === keyValue) { // 条件成立，删除该项
          treeData.splice(i, 1);
        }

        if (Array.isArray(item.children)) { // 如果是数组，继续遍历
          this.initHideTree(item.children, key);
        }
      }
      return treeData;
    },
    /**
    * 根据是否隐藏来判断，需要展示的树的数据
    * @param {*void}
    * @author yezi 2019-09-11
    */
    changeHideSented () {
      let data = this.$common.clone(this.treeData); // 深拷贝树结构数组
      if (this.isHideSented) { // 隐藏已下发节点
        this.leftTreeData = this.initHideTree(data, "checked");
      } else { // 不隐藏已下发节点
        this.leftTreeData = data;
      }
    },
    /**
     * 获取下发的用户树
     * @param {*void}
     * @author yezi 2019-09-11
     */
    getTreeData () {
      this.axios({
        method: "post",
        url: "/cipher/dynamic/getUser",
        data: {}
      })
        .then(res => {
          if (res.data.return_code === 0) {
            let data = this.$common.cloneAssemblyAssignData(res.data.trees, cloneChangeKeys, true);
            this.treeData = this.$common.initSelect(data);
            this.changeHideSented();
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 显示下发用户树穿梭框
     * @param {*void}
     * @author yezi 2019-09-11
     */
    showTreeMadal () {
      this.isShowTreeMadal = true;
      this.treeDataType = 1;
      this.getTreeData();
    },
    /**
     * 重置下发树
     *  @param {*void}
     * @author yezi 2019-09-11
     */
    resetTree () { // 重置
      this.changeHideSented();
    },
    /**
     * 点击穿梭框 X触发，关闭穿梭框
     *  @param {*void}
     * @author yezi 2019-09-11
     */
    cancel () {
      this.isShowTreeMadal = false;
    },
    /**
     * 穿梭框的回调函数，获取选中的右边的数据
     * @param {*Object 右侧数据} data
     * @author yezi 2019-09-11
     */
    getRightData (data) {
      let userIdArr = [];
      data.forEach(item => {
        userIdArr.push(item.uuid);
      });
      this.userIds = userIdArr.join(",");
    },
    /**
     * 批量下发种子秘钥
     * @param {*void}
     * @author yezi 2019-09-11
     */
    sentDownBatch () {
      let params = {
        userIds: this.userIds
      };
      this.axios({
        method: "post",
        url: "/cipher/dynamic/batch",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.getList();
            this.$myModal.success({
              title: "下发成功",
              content: res.data.return_msg
            });
          } else {
            this.$myModal.error({
              title: "下发失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    ...mapMutations(["changeGroup"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
.tree-transfer-check {
  padding-left: 30px;
}
</style>
