<template>
  <div class="admin-audit tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryAdminAudit" />
      </div>
      <div class="tabled-header-left">
        <mySelect :dataList="options"
                  showString="事件类型"
                  v-model="selected"
                  valueKey="type"
                  labelKey="desc"
                  @on-change="queryAdminAudit" />
        <DatePicker type="daterange"
                    split-panels
                    class="admin-header__data"
                    placeholder="选择日期"
                    v-model="data"
                    @on-change="queryAdminAudit"></DatePicker>
      </div>
      <div class="tabled-header-btn">

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
          @on-page-size-change="pageSizeChange"></Page>
  </div>
</template>

<script>
export default {
  data () {
    return {
      data: [], // 日期
      total: 0,
      queryName: "",
      curentPage: 1,
      size: 10,
      options: [],
      selected: 0,
      columns1: [
        {
          title: "姓名",
          key: "userName",
          align: "center"
        },
        {
          title: "事件",
          key: "typeStr",
          align: "center"
        },
        {
          title: "事件简介",
          key: "operation",
          align: "center"
        },
        {
          title: "时间",
          key: "createTime",
          align: "center"
        }
      ],
      data1: []
    };
  },
  mounted () {
    this.initoption();
    let params = {
      rows: this.size,
      page: 1
    };
    this.finddata(params);
  },
  methods: {
    queryAdminAudit () {
      let params = {
        startTime: this.data[0],
        endTime: this.data[1],
        queryName: this.queryName,
        type: this.selected,
        rows: this.size,
        page: 1
      };
      this.finddata(params);
      this.curentPage = 1;
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/adminbehavior/list?json",
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
    // 初始化事件类型选择框
    initoption () {
      this.axios({
        method: "post",
        url: "/cipher/adminbehavior/content",
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
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        queryName: this.queryName,
        type: this.selected,
        startTime: this.data[0],
        endTime: this.data[1]
      };
      this.finddata(params);
      this.size = size;
      this.curentPage = 1;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        queryName: this.queryName,
        type: this.selected,
        startTime: this.data[0],
        endTime: this.data[1]
      };
      this.curentPage = index;
      this.finddata(params);
    },
    // 导出报表
    exportExcel () {
      window.location.href = "/cipher/adminbehavior/exportExcle?startTime=" +
        this.data[0] +
        "&endTime=" +
        this.data[1] +
        "&queryName=" +
        this.queryName +
        "&type=" +
        this.selected;
    }
  }
};
</script>

<style  lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
.admin-header__data {
  position: relative;
  top: -2px;
  margin-left: @customMargin;
}
</style>
