<template>
  <div class="visitor-audit tabled-wrap">
    <div class="tabled-header">
      <template v-if="flag">
        <div class="tabled-header-search">
          <myInput placeholder="请输入姓名 / 账号"
                   v-model="queryName"
                   radius="half"
                   :search="true"
                   @handleClick="queryVisitorAudit" />
        </div>
        <div class="tabled-header-left">
          <DatePicker type="daterange"
                      split-panels
                      placeholder="选择日期"
                      v-model="date"
                      @on-change="queryVisitorAudit"></DatePicker>
        </div>
      </template>
      <div class="visitor-audit-chat-header"
           v-else>
        <div class="visitor-audit-chat-header-item">
          <b>统计维度：</b>
          <RadioGroup v-model="customType"
                      @on-change="CustomChange">
            <Radio class="TableRadio"
                   label="全部">账户类型（员工/访客）</Radio>
            <Radio class="TableRadio TableRadioMagin"
                   label="SSID">SSID(Potal描述)</Radio>
          </RadioGroup>
        </div>
        <div class="visitor-audit-chat-header-item">
          <b>时间粒度：</b>
          <DatePicker type="daterange"
                      placeholder="选择日期"
                      v-model="dateTime"
                      @on-change="DateSearch"></DatePicker>
        </div>
      </div>
      <div class="tabled-header-btn">
        <template v-if="flag">
          <mySelect :dataList="visitorTypeList"
                    showString="选择"
                    v-model="visitorType"
                    @on-change="queryVisitorAudit" />
          <myButton btnType="info"
                    @click="downLoadExcel">导出报表</myButton>
        </template>
        <myButton btnType="info"
                  @click="viewTabs">{{ toggleButtonText}}</myButton>
      </div>
    </div>
    <template v-if="flag">
      <div class="tabled-table">
        <Table :columns="columns4"
               :data="data6"
               ref="table">
          <template slot-scope="{row}"
                    slot="visitorType">
            {{row.visitorType===0?"员工":"访客"}}
          </template>
        </Table>
      </div>
      <Page class="tabled-page"
            :total="total"
            :current="curentPage"
            show-elevator
            show-sizer
            show-total
            @on-change="changePage"
            @on-page-size-change="pageSizeChange"></Page>
    </template>
    <template v-else>
      <div class="visitor-audit-chatType">
        <span @click="lineChange"
              style="cursor: pointer;"
              :class="{'Tableactive': isactive==0}">折线图</span>/
        <span @click="barChange"
              style="cursor: pointer;"
              :class="{'Tableactive': isactive==1}">柱状图</span>
      </div>
      <div id="myChart"
           class="myChart"
           v-show="!flag">
      </div>
    </template>
  </div>
</template>

<script>
import chart from "@/util/charts.js";
export default {
  data () {
    return {
      demo: "日",
      toggleButtonText: "图表展示", // 按钮展示图表还是表格
      monthDate2: "", // 結束月份
      monthDate1: "", // 開始月份
      dateTime: [],
      flag: true,
      date: [],
      queryName: "",
      visitorType: null, // 选择的用户类型
      visitorTypeList: [{ value: "", label: "全部" }, { value: 0, label: "员工" }, { value: 1, label: "访客" }],
      total: 0,
      curentPage: 1,
      size: 10,
      columns4: [
        {
          title: "姓名",
          key: "vistorName",
          align: "center"
        },
        {
          title: "类型",
          slot: "visitorType",
          align: "center"
        },
        {
          title: "无线Portal 描述",
          key: "msg",
          align: "center"
        },
        {
          title: "上线时间",
          key: "createTime",
          align: "center"
        }
      ],
      data6: [],
      TableType: "line",
      isactive: 0,
      isRadioactive: 0,
      demo1: "全部",
      totalPage: 0,
      customType: "全部",
      legendName: [],
      dimension: 0, // 统计维度
      granularity: 0, // 时间维度
      startTime: "",
      endTime: "",
      portal: {
        data: [],
        timeList: [],
        portalName: [],
        series: []
      },
      seriesColor: ["#ff9933", "#1890ff", "#eaff56", "#9ed048", "#9d2933"]
    };
  },
  mounted () {
    var that = this;
    let params = {
      rows: that.size,
      page: 1
    };
    that.finddata(params);
  },
  methods: {
    queryVisitorAudit () {
      let params = {
        startTime: this.date[0],
        endTime: this.date[1],
        name: this.queryName,
        visitorType: this.visitorType,
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
        url: "/cipher/vistorLoginLog/list?json",
        data: params
      })
        .then(function (res) {
          that.data6 = res.data.rows;
          that.total = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 更换页码
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        name: this.queryName,
        visitorType: this.visitorType,
        startTime: this.date[0],
        endTime: this.date[1]
      };

      this.curentPage = index;
      this.finddata(params);
    },
    // 更换table尺寸
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        name: this.queryName,
        visitorType: this.visitorType,
        startTime: this.date[0],
        endTime: this.date[1]
      };
      this.finddata(params);
      this.size = size;
      this.curentPage = 1;
    },
    // 下载报表
    downLoadExcel () {
      window.location.href = "/cipher/vistorLoginLog/downloadExcelLog";
    },
    // 报表展示
    viewTabs () {
      this.flag = !this.flag;
      this.toggleButtonText = this.flag === true ? "图表展示" : "表格展示";
      if (this.flag === false) {
        this.axiosDrawLine();
      }
    },
    // 一点击报表展示，发送请求，获取数据画图
    axiosDrawLine () {
      this.axios({
        method: "post",
        url: "/cipher/vistor/chartData",
        data: {
          dimension: this.dimension, // 统计维度
          granularity: this.granularity, // 时间维度
          startTime: this.startTime,
          endTime: this.endTime
        }
      })
        .then(res => {
          // console.log(res.data)
          if (this.dimension === 0) {
            // 选择账号类型时：
            this.accountData(res.data, this.TableType);
          } else {
            this.assemblyData(res.data.portal, this.TableType);
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
          this.portal = {};
          this.drawLine(this.portal, this.tableType);
        });
    },
    // 画图:
    drawLine (portal, tableType) {
      let myChart = this.$echarts.init(document.getElementById("myChart"));
      chart.drawLine(myChart, portal, 5, 1, tableType);
    },
    // 在时间维度选择日期时触发
    DateSearch (time) {
      this.startTime = time[0];
      this.endTime = time[1];
      let dateDemo = new Date(this.endTime);
      let dateDemo1 = new Date(this.startTime);
      let second = dateDemo - dateDemo1;
      let days = second / 1000 / 60 / 60 / 24;
      if (days > 30) {
        this.$Modal.error({
          title: "选择日期错误",
          content: "日期相差大于30天,最多只能显示30天"
        });
      } else if (days < 2) {
        this.$Modal.error({
          title: "选择日期错误",
          content: "日期相差小于2天"
        });
      } else {
        this.axiosDrawLine();
      }
    },
    echartChange () {
      for (var i in this.portal.series) {
        this.portal.series[i].type = this.TableType;
        i++;
      }
      this.drawLine(this.portal, this.TableType);
    },
    // 选择折现图时触发
    lineChange () {
      this.isactive = 0;
      this.TableType = "line";
      this.echartChange();
    },
    // 选择柱状图时触发
    barChange () {
      this.isactive = 1;
      this.TableType = "bar";
      this.echartChange();
    },
    // 当点击图例时触发
    CustomChange () {
      if (this.customType === "全部") {
        this.dimension = 0;
        this.granularity = 0;
        this.axiosDrawLine();
      } else {
        this.customType = "SSID";
        this.dimension = 1;
        this.axiosDrawLine();
      }
    },
    // 生成隨機顏色
    colorCreate () {
      let num = ["0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"];
      let color = "#";
      for (var i = 0; i < 6; i++) {
        color = color + num[Math.floor(Math.random() * 15)];
      }
      console.log(color);
      return color;
    },
    accountData (accoutData, tableType) {
      let accountName = ["员工使用量", "访客使用量"];
      this.portal.yName = "使用人数";
      this.portal.xName = "日期";
      let data = [];
      this.portal.timeList = accoutData.timeList;
      for (var key in accoutData) {
        if (key.indexOf("data") !== -1) {
          data.push(accoutData[key]);
        }
      };
      for (let i in data) {
        let portalName = {};
        portalName.name = accountName[i];
        portalName.icon = "stack";
        this.portal.portalName[i] = portalName;

        let series = {};
        series.name = accountName[i];
        series.type = tableType;
        series.color = i > this.seriesColor.length - 1 ? this.colorCreate() : this.seriesColor[i];
        series.data = data[i];
        this.portal.series[i] = series;
        i++;
      }
      this.drawLine(this.portal, this.TableType);
    },
    assemblyData (portal, TableType) {
      this.portal.yName = "Potal人数";
      this.portal.xName = "日期";
      for (var i in portal) {
        let item = portal[i];
        let portalName = {};
        portalName.name = item.portalName;
        portalName.icon = "stack";
        this.portal.portalName[i] = portalName;
        this.portal.data[i] = item.data;

        let series = {};
        series.name = item.portalName;
        series.type = TableType;
        series.data = item.data;
        series.color = i > this.seriesColor.length - 1 ? this.colorCreate() : this.seriesColor[i];
        this.portal.series[i] = series;
        i++;
      }
      this.portal.timeList = portal[0].timeList;
      this.drawLine(this.portal, this.TableType);
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.visitor-audit-chat-header,
.visitor-audit-chat-header-item {
  display: inline-block;
}
.visitor-audit-chatType {
  position: absolute;
  right: 110px;
  z-index: 999;
}
.myChart {
  height: calc(~"100% - @{headerHeight} - @{footerHeight}");
  box-sizing: border-box;
  position: relative;
  background-color: #ffffff;
  box-sizing: border-box;
}
.Tableactive {
  color: @colorPrimary;
}
</style>
