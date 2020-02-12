<template>
  <div class="ua tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryUserAudit" />
      </div>
      <div class="tabled-header-left">
        <mySelect :dataList="options"
                  showString="事件类型"
                  v-model="selected"
                  valueKey="type"
                  labelKey="desc"
                  @on-change="queryUserAudit" />
        <DatePicker type="daterange"
                    split-panels
                    class="user-header__data"
                    placeholder="选择日期"
                    v-model="data"
                    @on-change="queryUserAudit"></DatePicker>
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
      data: "", // 日期
      queryName: "",
      curentPage: 1,
      total: 0,
      size: 10,
      options: [], // 事件类型下拉框初始化数据
      selected: "",
      columns1: [
        {
          align: "center",
          title: "姓名",
          key: "userInfo"
        },
        {
          align: "center",
          title: "事件类型",
          key: "typeStr"
        },
        {
          align: "center",
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
          align: "center",
          title: "时间",
          key: "createTime"
        },
        {
          align: "center",
          title: "IP",
          key: "ip"
        }
      ],
      data1: []
    };
  },
  mounted () {
    this.initoption();
    let params = {
      rows: this.size,
      page: 1,
      sord: "DESC"
    };
    this.finddata(params);
  },
  methods: {
    queryUserAudit () {
      let params = {
        startTime: this.data[0],
        endTime: this.data[1],
        queryName: this.queryName,
        type: this.selected,
        rows: this.size,
        page: 1,
        sord: "DESC"
      };
      this.finddata(params);
      this.curentPage = 1;
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/userbehavior/list?json",
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
    initoption () {
      this.axios({
        method: "post",
        url: "/cipher/userbehavior/content",
        data: {}
      })
        .then(res => {
          let item = { type: 0, desc: "所有" };
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
        endTime: this.data[1],
        sord: "DESC"
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        queryName: this.queryName,
        type: this.selected,
        startTime: this.data[0],
        endTime: this.data[1],
        sord: "DESC"
      };
      this.curentPage = index;
      this.finddata(params);
    },
    exportExcel () {
      // this.$refs.table.exportCsv({
      //   filename: '报表'
      // })
      window.location.href = "/cipher/userbehavior/exportExcle?startTime=" + this.data[0] + "&endTime=" + this.data[1] + "&queryName=" + this.queryName + "&type=" + this.selected;
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.user-header__data {
  position: relative;
  top: -2px;
  margin-left: @customMargin;
}
</style>
