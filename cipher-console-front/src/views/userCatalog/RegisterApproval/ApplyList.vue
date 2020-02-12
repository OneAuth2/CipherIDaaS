<template>
  <div class="apply wrap">
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
          title: "申请时间",
          key: "createTime"
        },
        {
          title: "操作",
          key: "option",
          width: 150,
          render: (h, params) => {
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.pass(params.index);
                  }
                }
              }, "通过"),
              h("a", {
                on: {
                  click: () => {
                    this.reject(params.index);
                  }
                }
              }, "拒绝")

            ]);
          }
        }
      ],
      list: [], // 表格列表数据
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1, // 当前页
      isDisabled: "" // 点击是否禁用
    };
  },
  created () {
    this.getAllList();
  },
  methods: {
    // 获取列表所有数据
    getAllList () {
      let params = {
        page: this.current,
        rows: this.pageSize
      };
      this.getApplyList(params);
    },
    // 每页页面条数改变
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1
      };
      this.getApplyList(params);
      this.pageSize = size;
    },
    // 页数改变
    changePage (index) {
      let params = {
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getApplyList(params);
    },
    getApplyList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/register/registerApproval",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.total = res.data.return_result.total;
            if (res.data.return_result.rows instanceof Array) {
              this.list = res.data.return_result.rows;
            }
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
    },
    // 调用后台接口删除某条数据 根据mac地址
    accountApproval (id, status) {
      let params = {
        uuid: id,
        status: status
      };
      this.axios({
        method: "post",
        url: "/cipher/register/accountApproval",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            let text = status === 1 ? "已通过" : "已拒绝";
            this.$myMessage.success(text);
            let params = {
              page: this.current,
              rows: this.pageSize
            };
            this.getApplyList(params);
          } else {
            this.$myModal.error({ title: "操作失败", content: res.data.return_msg });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    //  点击删除时执行的函数
    reject (index) {
      this.$myModal.confirm({
        title: "确定要拒绝该申请？"
      }).then(async () => {
        this.accountApproval(this.list[index].uuid, 2);
      }).catch(() => { });
    },
    pass (index) {
      this.$myModal.confirm({
        title: "确认通过该成员申请？"
      }).then(async (val) => {
        this.accountApproval(this.list[index].uuid, 1);
      }).catch(() => { });
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
