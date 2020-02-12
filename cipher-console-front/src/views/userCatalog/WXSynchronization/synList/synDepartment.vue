<template>
  <div class="ding-syn-department tabled-wrap">
    <div class="ding-syn-department-header tabled-header">
      <myButton @click="onConfirm(1)">扫描组织结构</myButton>
      <myButton @click="onConfirm(2)">同步组织结构</myButton>
    </div>
    <div class="tabled-table">
      <Table :columns="title"
             :data="list"></Table>
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
        content: "",
        onOk: this.onConfirmOk,
        onClose: this.onConfirmClose
      },
      type: 1, // 确认框类型——1扫描 2同步
      isLoaading: false, // 是否loading
      title: [
        {
          title: "部门 / 完整路径",
          key: "path",
          align: "center"
        },
        {
          title: "状态",
          key: "status",
          align: "center"
        }
      ],
      list: [], // 表格列表数据
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1 // 当前页
    };
  },
  mounted () {
    this.getAllList();
  },
  methods: {
    /**
     * 获取列表所有数据
     * @param {*void}
     * @author yezi 2019-10-12
     */
    getAllList () {
      let params = {
        page: this.current,
        rows: this.pageSize
      };
      this.getDepartmentList(params);
    },
    /**
     * 每页页面条数改变
     * @param {*Number 每页页面条数} size
     * @author yezi 2019-10-12
     */
    changePageSize (size) {
      let params = {
        rows: size,
        page: 1
      };
      this.pageSize = size;
      this.getDepartmentList(params);
    },
    /**
     * 页码改变
     * @param {*Number 页码} index
     * @author yezi 2019-10-12
     */
    changePage (index) {
      let params = {
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getDepartmentList(params);
    },
    /**
     * 发送ajax请求，获取部门列表数据
     * @param {*Number 页码} index
     * @author yezi 2019-10-12
     */
    getDepartmentList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/wxGroup/getGroupList",
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
     *  点击扫描 或 同步的确认按钮
     * @param {*Number 1-扫描 2-同步} type
     * @author yezi 2019-10-12
     */
    onConfirm (type) { // 点击扫描 或 同步的确认按钮 ，参数 1-扫描 2-同步
      this.type = type;
      this.confirm.title = type === 1 ? "确认扫描吗？" : "确认同步组织结构吗？";
      this.confirm.content = type === 1 ? "重新从企业微信中扫描组织结构，扫描后，使用同步功能，才会改变现有的组织结构。" : "将当前扫描结果下的全部部门进行同步，没有的将新建，存在的将更新。";
      this.confirm.flag = true;
    },
    onConfirmClose () { //  点击取消，关闭确认框
      this.confirm.flag = false;
    },
    onConfirmOk () { // 点击确认，进行扫描或同步
      this.confirm.flag = false;
      this.isLoaading = true;
      if (this.type === 1) { // 扫描同步确认
        this.scan();
      } else {
        this.sync();
      }
    },
    /**
     *  扫描，向后台发送ajax请求
     * @param {*void}
     * @author yezi 2019-10-12
     */
    scan () {
      this.axios({
        method: "post",
        url: "/cipher/wxGroup/scanWxGroup",
        data: {}
      })
        .then(res => {
          this.isLoaading = false;
          if (res.data.return_code === 0) {
            this.getAllList();
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
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
    *  同步，向后台发送ajax请求
    * @param {*void}
    * @author yezi 2019-10-12
    */
    sync () {
      this.axios({
        method: "post",
        url: "/cipher/wxGroup/sync",
        data: {}
      })
        .then(res => {
          this.isLoaading = false;
          if (res.data.return_code === 0) {
            this.getAllList();
            this.$emit("changeLeftTree");
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
.ding-syn-department-header {
  text-align: right;
  .btn {
    margin-left: @customMargin;
  }
}
</style>
