<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="输入设备名称"
                 v-model.trim="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="searchFacility" />
      </div>
    </div>
    <div class="tabled-table">
      <Table :columns="columns4"
             :data="data6"
             ref="table">
        <template slot-scope="{ row}"
                  slot="devicename">
          <router-link :to="{path:'/auditReportFacility/detailList',query:{vpnId:row.vpnId}}">{{row.devicename}}</router-link>
        </template>
        <template slot-scope="{row}"
                  slot="status">
          {{row.status===0?"启用":"停用"}}
        </template>
        <template slot-scope="{ row}"
                  slot="action">
          <router-link :to="{path:'/auditReportFacility/detailList',query:{vpnId:row.vpnId}}">查看详情</router-link>
        </template>
      </Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="currentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="changePageSize"></Page>
  </div>
</template>

<script>
import api from "@/api/audit/index.js";
export default {
  data () {
    return {
      queryName: "",
      current: 1,
      total: 0,
      currentPage: 1,
      pageSize: 3,
      columns4: [
        {
          title: "设备",
          slot: "devicename",
          align: "center"
        },
        {
          title: "最近7天访问次数",
          key: "recentlySeven",
          align: "center"
        },
        {
          title: "最近30天访问次数",
          key: "recentlyThirty",
          align: "center"
        },
        {
          title: "状态",
          slot: "status",
          align: "center"
        },
        {
          title: "操作",
          slot: "action",
          align: "center"
        }
      ],
      data6: []
    };
  },
  mounted () {
    this.init();
  },
  methods: {
    init () {
      let params = { page: 1, rows: 10 };
      this.getData(params);
    },
    /**
     * 获取数据
     * @param {*params}请求参数
     * @author menglei 2019-09-26
     */
    getData (params) {
      this.axios({
        method: "post",
        url: api.facilityAuditList,
        data: params
      })
        .then(res => {
          let data = res.data;
          if (data.return_code === 0) {
            this.data6 = data.rows;
            this.total = data.total;
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(error => {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 搜索设备
     * @param {*void}
     * @author menglei 2019-09-26
     */
    searchFacility () {
      let params = { page: 1, rows: 10, devicename: this.queryName };
      this.getData(params);
    },
    /**
     * 改变页面页码
     * @param {*void}
     * @author menglei 2019-09-26
     */
    changePage (currentPage) {
      this.currentPage = currentPage;
      let params = { page: this.currentPage, rows: 10 };
      this.getData(params);
    },
    /**
     * 改变每页条数
     * @param {*void}
     * @author menglei 2019-09-26
     */
    changePageSize (pageSize) {
      this.pageSize = pageSize;
      let params = { page: this.currentPage, rows: this.pageSize };
      this.getData(params);
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
