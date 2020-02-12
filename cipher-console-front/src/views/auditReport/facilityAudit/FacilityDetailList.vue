<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="输入姓名，账号"
                 v-model.trim="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="searchFacility" />
      </div>
      <div class="tabled-header-left">
        <DatePicker type="daterange"
                    split-panels
                    placeholder="选择日期"
                    @on-change="dateChange"></DatePicker>
      </div>
      <div class="tabled-header-btn">
        <mySelect :dataList="eventTypeList"
                  showString="事件类型"
                  v-model="eventType"
                  @on-change="eventTypeChange" />
        <myButton btnType="info"
                  class="visitor-audit-header__btn"
                  @click="downLoadExcel">导出报表</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table :columns="columns4"
             :data="data6"
             ref="table">
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
      vpnId: 0, // 设备id
      queryName: "",
      current: 1,
      startTime: "",
      endTime: "",
      total: 0,
      currentPage: 1,
      pageSize: 10,
      eventType: 0, // 件类型：0-设备访问，1-设备维护
      eventTypeList: [{ value: "", label: "全部" }, { value: 0, label: "设备访问" }, { value: 1, label: "设备维护" }],
      columns4: [
        {
          title: "账号",
          key: "userInfo",
          align: "center"
        },
        {
          title: "事件简介",
          key: "intro",
          align: "center"
        },
        {
          title: "事件内容",
          key: "msg",
          align: "center"
        },
        {
          title: "时间",
          key: "createTime",
          align: "center"
        }
      ],
      data6: []
    };
  },
  mounted () {
    this.init();
  },
  computed: {
    params () {
      return { page: this.current, rows: this.pageSize, vpnId: this.vpnId };
    }
  },
  methods: {
    init () {
      this.vpnId = this.$route.query.vpnId;
      this.getData(this.params);
    },
    /**
     * 获取数据
     * @param {*params}请求参数
     * @author menglei 2019-09-26
     */
    getData (params) {
      this.axios({
        method: "post",
        url: api.equipBehaviorLogList,
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
      this.params.userInfo = this.queryName;
      console.log(this.params);
      this.getData(this.params);
    },
    /**
     * 改变页面页码
     * @param {*void}
     * @author menglei 2019-09-26
     */
    changePage (currentPage) {
      this.params.page = currentPage;
      this.getData(this.params);
    },
    /**
     * 改变每页条数
     * @param {*void}
     * @author menglei 2019-09-26
     */
    changePageSize (pageSize) {
      this.params.rows = pageSize;
      this.getData(this.params);
    },
    /**
     * 日期改变
     * @param {*void}
     * @author menglei 2019-09-27
     */
    dateChange (date) {
      this.params.startTime = date[0];
      this.params.endTime = date[1];
      this.getData(this.params);
    },
    /**
     * 事件类型改变
     * @param {*void}
     * @author menglei 2019-09-27
     */
    eventTypeChange (eventType) {
      this.params.type = eventType.value;
      this.getData(this.params);
    },
    /**
     * 导出报表
     * @param {*void}
     * @author menglei 2019-09-27
     */
    downLoadExcel () {
      window.location.href = "/cipher/equipBehavior/exportExcle";
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
