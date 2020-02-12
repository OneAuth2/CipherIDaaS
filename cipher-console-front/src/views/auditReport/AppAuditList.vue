<template>
  <div class="app-list-audit tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryUserAudit" />
      </div>
      <div class="tabled-header-right">
        <mySelect :dataList="options"
                  showString="事件类型"
                  v-model="selected"
                  valueKey="type"
                  labelKey="desc"
                  @on-change="queryUserAudit" />
        <DatePicker type="daterange"
                    split-panels
                    class="app-list-header__data"
                    placeholder="选择日期"
                    v-model="data"
                    @on-change="queryUserAudit"></DatePicker>
        <myButton btnType="info"
                  @click="exportExcel">导出报表</myButton>
      </div>

    </div>
    <div class="tabled-table">
      <Table :columns="columns1"
             :data="data1"
             ref="table"></Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="curentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="pageSizeChange">
    </Page>

  </div>
</template>
<script>
export default {
  data () {
    return {
      data: "", // 日期
      queryName: "",
      curentPage: 1,
      total: 0,
      size: 10,
      options: [], // 事件类型初始化数据
      optionsapp: [], // 应用下拉框初始化数据
      selected: 0,
      selectedapp: "",
      id: "",
      applicationName: "",
      columns1: [
        {
          title: "操作主体",
          key: "userName"
        },
        {
          title: "事件类型",
          key: "typeStr"
        },
        {
          title: "事件简介",
          key: "msg",
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    diaplay: "inline-block",
                    width: "100%",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap"
                  },
                  domProps: {
                    title: this.data1[params.index].msg
                  }
                },
                this.data1[params.index].msg
              )
            ]);
          }
        },
        {
          title: "时间",
          key: "createTime"
        }
      ],
      data1: []
    };
  },
  created () {
    this.id = this.$route.query.id;
    this.applicationName = this.$route.query.applicationName;
    this.selectedapp = this.id;
  },
  mounted () {
    this.initoptions();
    this.initoptionsapp();
    let params = {
      applicationId: this.id,
      page: 1,
      rows: this.size,
      sidx: "",
      sord: "asc"
    };
    this.finddata(params);
  },
  methods: {
    queryUserAudit () {
      let params = {
        applicationId: this.selectedapp,
        rows: this.size,
        page: 1,
        userName: this.queryName,
        sidx: "",
        sord: "asc",
        startTime: this.data[0],
        endTime: this.data[1],
        type: this.selected
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/applyAudit/getAuditInfo?json",
        data: params
      })
        .then(function (res) {
          that.data1 = res.data.rows;
          that.total = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 初始化事件类型数据
    initoptions () {
      this.axios({
        method: "post",
        url: "/cipher/applyAudit/content",
        data: {}
      })
        .then(res => {
          let item = { type: 0, desc: "所有类型" };
          this.options = [item].concat(res.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 初始化应用下拉框数据
    initoptionsapp () {
      var that = this;
      let params = {
        applicationId: this.id
      };
      this.axios({
        method: "post",
        url: "/cipher/applyAudit/getList",
        data: params
      })
        .then(function (res) {
          that.optionsapp = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        queryName: this.queryName,
        type: this.selected,
        applicationId: this.selectedapp,
        startTime: this.data[0],
        endTime: this.data[1]
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        applicationId: this.selectedapp,
        rows: this.size,
        page: index,
        queryName: this.queryName,
        type: this.selected,
        startTime: this.data[0],
        endTime: this.data[1],
        sidx: "",
        sord: "asc"
      };
      this.curentPage = index;
      this.finddata(params);
    },
    // 导出报表
    exportExcel () {
      // this.$refs.table.exportCsv({
      //   filename: '报表'
      // })
      window.location.href = "/cipher/applyAudit/exportExcle?startTime=" + this.data[0] + "&endTime=" + this.data[1] + "&queryName=" + this.queryName + "&type=" + this.selected + "&applicationId=" + this.selectedapp;
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.app-list-header__data {
  position: relative;
  top: -2px;
  margin-left: @customMargin;
}
</style>
