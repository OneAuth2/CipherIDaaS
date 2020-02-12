<template>
  <div class="approval wrap">
    <div class="tabled-table">
      <Table :columns="title"
             :data="list"></Table>
    </div>
    <div class="page">
      <!-- <span>共{{total}} 条记录 第 {{current}} / {{Math.ceil(total/pageSize)}} 页</span> -->
      <Page :total="total"
            :page-size="pageSize"
            :current="current"
            show-sizer
            show-elevator
            show-total
            @on-change="changePage"
            @on-page-size-change="pageSizeChange" />
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 表格数据
      title: [
        {
          title: "姓名",
          key: "userName"
        },
        {
          title: "邮箱",
          key: "userEmail"
        },
        {
          title: "手机",
          key: "phoneNumber"
        },
        {
          title: "审批时间",
          key: "modifyTime"
        },
        {
          title: "状态",
          key: "status",
          render: (h, params) => {
            const row = params.row;
            const status = row.status === 1 ? "success" : "error";
            const text = row.status === 1 ? "已通过" : "已拒绝";
            return h("Badge", {
              props: {
                status: status,
                text: text
              }
            });
          }
        }
      ],
      list: [], // 表格列表数据
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1 // 当前页
    };
  },
  created () {
    this.getAllList();
  },
  methods: {
    // 获取列表所有数据
    getAllList () {
      let params;
      params = {
        page: this.current,
        rows: this.pageSize
      };
      this.getApprovalList(params);
    },
    // 每页页面条数改变
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1
      };
      this.getApprovalList(params);
      this.pageSize = size;
    },
    // 页数改变
    changePage (index) {
      let params = {
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getApprovalList(params);
    },
    getApprovalList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/register/approvalRecords",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.total = res.data.return_result.total;
            this.list = res.data.return_result.rows;
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
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
.tabled-table {
  height: calc(~"100% - @{footerHeight}");
}
</style>
