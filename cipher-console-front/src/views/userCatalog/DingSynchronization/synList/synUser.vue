<template>
  <div class="ding-syn-user tabled-wrap">
    <div class="ding-syn-user-header tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="searchWord"
                 radius="half"
                 :search="true"
                 @handleClick="getSearchList()" />
      </div>
      <div class="tabled-header-right">
        <myButton @click="onConfirm(1)">扫描用户</myButton>
        <myButton @click="onConfirm(2)">同步已选</myButton>
        <myButton @click="onConfirm(3)">同步全部</myButton>
        <mySelect :dataList="statusList"
                  showString="显示"
                  v-model="status"
                  @on-change="changeState" />
      </div>
    </div>
    <div class="tabled-table">
      <Table :columns="title"
             :data="list"
             ref="selection"
             @on-selection-change="handleSelectRow()"></Table>
    </div>
    <div class="tabled-page">
      <!-- <span>共{{total}} 条记录 第 {{current}} / {{Math.ceil(total/pageSize)}} 页</span> -->
      <Page :total="total"
            :page-size="pageSize"
            :current="current"
            show-sizer
            show-elevator
            show-total
            @on-change="changePage"
            @on-page-size-change="changePageSize" />
    </div>

    <!-- 确认框 -->
    <Confirm v-if="confirm.flag"
             :flag="confirm.flag"
             :title="confirm.title"
             :titleStyle="confirm.titleStyle"
             :content="confirm.content"
             :onOk="confirm.onOk"
             :onClose="confirm.onClose"></Confirm>
    <!-- loading -->
    <Loading v-if="isLoaading" />
  </div>
</template>

<script>
import Confirm from "@/components/modal/Confirm.vue";
import Loading from "@/components/loading/Loading.vue";
export default {
  components: {
    Confirm,
    Loading
  },
  data () {
    return {
      confirm: { // 确认框
        flag: false,
        title: "",
        titleStyle: {
          fontSize: "14px",
          fontWeight: "600"
        },
        content: "",
        onOk: this.onConfirmOk,
        onClose: this.onConfirmClose
      },
      type: 1, // 确认框类型——1扫描 2同步 3同步全部
      isLoaading: false, // 是否loading
      status: 0, // 状态，默认为 0-全部
      statusList: [ // 状态列表
        {
          value: 0,
          label: "全部"
        },
        {
          value: 1,
          label: "新增"
        },
        {
          value: 2,
          label: "待绑定"
        }
      ],
      title: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        {
          title: "姓名",
          key: "name"
        },
        {
          title: "手机",
          key: "mobile"
        },
        {
          title: "邮箱",
          key: "email"
        },
        {
          title: "状态",
          key: "status",
          width: 100
        },
        {
          title: "同步操作",
          key: "action",
          width: 100,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.onConfirm(4, params.row.userid);
                  }
                }
              }, "同步")
            ]);
          }
        }
      ],
      searchWord: "", // 搜索词语
      queryStr: "", // 点击搜索后存放的搜索词
      list: [], // 表格列表数据
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1, // 当前页
      selectList: [] // 勾选的用户
    };
  },
  created () {
    this.getList();
  },
  methods: {

    /**
     * 获取列表所有数据
     * @param {*void}
     * @author yezi 2019-08-26
     */
    getList () {
      let params;
      params = {
        status: this.status,
        queryStr: this.queryStr,
        page: this.current,
        rows: this.pageSize
      };
      this.getUserList(params);
    },
    /**
     * 根据搜索获取列表
     * @param {*void}
     * @author yezi 2019-08-26
     */
    getSearchList () {
      let params;
      params = {
        status: this.status,
        queryStr: this.searchWord,
        page: 1,
        rows: this.pageSize
      };
      this.getUserList(params);
      this.current = 1;
      this.queryStr = this.searchWord;
    },
    /**
    * 状态改变，重新请求数据
    * @param {*Object 包含index，value和label} status
    * @author yezi 2019-08-26
    */
    changeState (status) {
      let params;
      params = {
        status: this.status,
        queryStr: this.searchWord,
        page: 1,
        rows: this.pageSize
      };
      this.getUserList(params);
    },
    /**
    * 每页页面条数改变
    * @param {*Number 每页页面条数} size
    * @author yezi 2019-08-26
    */
    changePageSize (size) {
      let params = {
        status: this.status,
        queryStr: this.queryStr,
        rows: size,
        page: 1
      };
      this.getUserList(params);
      this.pageSize = size;
    },
    /**
     * 页码改变
     * @param {*Number 页码} index
     * @author yezi 2019-08-26
     */
    changePage (index) {
      let params = {
        status: this.status,
        queryStr: this.queryStr,
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getUserList(params);
    },
    /**
     * 发送ajax请求，获取列表数据
     * @param {*Number 页码} index
     * @author yezi 2019-08-26
     */
    getUserList (params) {
      this.axios({
        method: "post",
        url: "/cipher/dingUser/getUserList",
        data: params
      })
        .then(res => {
          this.total = res.data.total;
          this.list = res.data.rows;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 选中的选项
     * @param 详见iview
     * @author yezi 2019-08-26
     */
    handleSelectRow (selection, index) {
      this.selectList = [];
      let selectData = this.$refs.selection.getSelection();
      for (let item of selectData) {
        this.selectList.push(item.userid);
      }
    },
    /**
     *  点击扫描 或 同步的确认按钮
     * @param {*Number 1-扫描 2-同步勾选 3-同步全部} type
     * @author yezi 2019-08-26
     */
    onConfirm (type, data) { // 点击扫描 或 同步的确认按钮 ，参数 1-扫描 2-同步勾选 3-同步全部 4-表格点击同步
      this.type = type;
      let title, content;
      switch (type) {
        case 1:
          title = "确认扫描吗？";
          content = "重新从钉钉中扫描用户，扫描后，使用同步功能，才会改变现有的用户。";
          break;
        case 2:
          title = "确认同步已选账号？";
          content = "将选中的用户同步到当前组织结构，没有的将新建，存在的将更新。";
          break;
        case 3:
          title = "确认同步全部账号？";
          content = "将当前扫描结果下的全部用户同步到当前组织结构，没有的将新建，存在的将更新。";
          break;
        case 4:
          title = "确认同步当前账号？";
          content = "将当前用户同步到当前组织结构，没有的将新建，存在的将更新。";
          this.selectList = data.split(" ");
          break;
        default:
          break;
      }
      this.confirm.title = title;
      this.confirm.content = content;
      this.confirm.flag = true;
    },
    /**
     *  确认框中点击取消按钮执行
     * @param {*void}
     * @author yezi 2019-08-26
     */
    onConfirmClose () { //  点击取消，关闭确认框
      this.confirm.flag = false;
    },
    /**
     *  确认框中点击确认，进行扫描或同步
     * @param {*void}
     * @author yezi 2019-08-26
     */
    onConfirmOk () { // 点击确认，进行扫描或同步
      this.confirm.flag = false;
      this.isLoaading = true;
      switch (this.type) {
        case 1:
          this.scan();
          break;
        case 2:
        case 4:
          this.sync();
          break;
        case 3:
          this.syncAll();
          break;
      }
    },
    /**
     *  扫描，向后台发送ajax请求
     * @param {*void}
     * @author yezi 2019-08-26
     */
    scan () { // 扫描
      this.axios({
        method: "post",
        url: "/cipher/dingUser/scanDingUser",
        data: {}
      })
        .then(res => {
          this.isLoaading = false;
          if (res.data.return_code === 0) {
            this.getList();
            this.$myModal.success({
              title: "扫描成功",
              content: `扫描到${res.data.add}条新数据`
            });
          } else {
            this.$myModal.error({
              title: "扫描失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(error => {
          this.isLoaading = false;
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     *  同步勾选，向后台发送ajax请求
     * @param {*void}
     * @author yezi 2019-08-26
     */
    sync () { // 同步勾选
      if (this.selectList.length === 0) {
        this.isLoaading = false;
        this.$myMessage.error("未勾选数据");
        return;
      }
      this.selectList = this.selectList.join(",");
      let params = {
        userIdList: this.selectList
      };
      this.axios({
        method: "post",
        url: "/cipher/dingUser/sync",
        data: params
      })
        .then(res => {
          this.isLoaading = false;
          if (res.data.return_code === 0) {
            this.getList();
            this.$myModal.success({
              title: "同步成功",
              content: `${res.data.sync_length}条数据同步成功`
            });
          } else {
            this.$myModal.error({
              title: "同步失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(error => {
          this.isLoaading = false;
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     *  同步所有，向后台发送ajax请求
     * @param {*void}
     * @author yezi 2019-08-26
     */
    syncAll () { // 同步所有
      this.axios({
        method: "post",
        url: "/cipher/dingUser/syncAll",
        data: {}
      })
        .then(res => {
          this.isLoaading = false;
          if (res.data.return_code === 0) {
            this.getList();
            this.$myModal.success({
              title: "同步成功",
              content: `${res.data.sync_length}条数据同步成功`
            });
          } else {
            this.$myModal.error({
              title: "同步失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(error => {
          this.isLoaading = false;
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
