<template>
  <div class="app-audit tabled-wrap"
       :style="bgColor">
    <div class="tabled-header app-audit-header">
      <div class="tabled-header-search"
           v-if="displayModelFlag">
        <myInput placeholder="请输入应用名称"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="querynamebtn" />
      </div>
      <div class="tabled-header-left"
           v-if="displayModelFlag">
        <DatePicker type="daterange"
                    split-panels
                    placeholder="选择日期"
                    v-model="data"
                    @on-change="querynamebtn"></DatePicker>
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="selDisplayModel">{{displayModelText}}</myButton>
      </div>
    </div>
    <template v-if="displayModelFlag">
      <div class="tabled-table">
        <Table border
               :columns="columns7"
               :data="data6"></Table>
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

    <div ref="appAuditChart"
         class="auditChart"
         v-show="!displayModelFlag">
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      conditionDay: "7天",
      displayModelText: "图表展示",
      displayModelFlag: true, // 展示方式，默认true列表，false图表
      date: "",
      recently: 1, // 最近时间，0代表7天，1代表30天
      recentlyData: {
        day7: [],
        day30: [],
        applicationName: []
      }, // 最近数据
      recently7DaysData: [], // 最近7天数据
      recently30DaysData: [], // 最近30天数据
      queryName: "",
      total: 0,
      size: 10,
      curentPage: 1,
      columns7: [
        {
          title: "应用",
          key: "applicationName",
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.show(params.index);
                    }
                  }
                },
                params.row.applicationName
              )
            ]);
          }
        },
        {
          title: "访问次数",
          key: "visitTimes"
        },
        {
          title: "占比",
          key: "proportion"
        },
        {
          title: "状态",
          key: "applicationStatus"
        },
        {
          title: "操作",
          key: "action",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.show(params.index);
                    }
                  }
                },
                "查看详情"
              )
            ]);
          }
        }
      ],
      data6: []
    };
  },
  mounted () {
    var that = this;
    let params = {
      rows: that.size,
      recently: this.recently,
      page: 1
    };
    that.finddata(params);
  },
  methods: {
    querynamebtn () {
      let params = {
        rows: this.size,
        page: 1,
        startTime: this.data[0],
        endTime: this.data[1],
        applicationName: this.queryName
      };
      this.finddata(params);
      this.curentPage = 1;
    },
    // 初始化数据
    finddata (params) {
      this.axios({
        method: "post",
        url: "/cipher/applyAudit/list?json",
        data: params
      })
        .then(res => {
          this.data6 = res.data.rows;
          this.total = res.data.total;
          return res.data.rows;
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        startTime: this.data[0],
        endTime: this.data[1],
        applicationName: this.queryName
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        startTime: this.data[0],
        endTime: this.data[1],
        applicationName: this.queryName
      };
      this.curentPage = index;
      this.finddata(params);
    },
    // 跳转到查看详情页面
    show (index) {
      this.$router.push({
        name: "appAuditDetail",
        query: {
          id: this.data6[index].id,
          applicationName: this.data6[index].applicationName
        }
      });
    },
    // 展示方式改变
    selDisplayModel () {
      if (this.displayModelFlag) {
        this.displayModelFlag = false;
        this.displayModelText = "表格展示";
        this.getRecentlyData();
      } else {
        this.displayModelFlag = true;
        this.displayModelText = "图表展示";
      }
    },
    initChart () {
      // 基于准备好的dom，初始化echarts实例
      var myChart = this.$echarts.init(this.$refs.appAuditChart);
      // 指定图表的配置项和数据
      var option = {
        backgroundColor: "#fff",
        title: {
          text: "应用审计图表"
        },
        tooltip: {
          trigger: "axis",
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: "shadow" // 默认为直线，可选为：'line' | 'shadow'
          }
        },
        legend: {
          left: "right",
          data: ["近7天", "近30天"]
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true
        },
        xAxis: [
          {
            type: "category",
            data: this.recentlyData.applicationName,
            axisLabel: {
              interval: 0,
              rotate: 40,
              textStyle: {
                color: "#666666",
                fontSize: "11"
              }
            },
            axisLine: {
              lineStyle: {
                color: "DFE4EF", // 坐标轴线颜色
                width: "2"
              }
            }
          }
        ],
        yAxis: [
          {
            type: "value"
          }
        ],
        series: [
          {
            name: "近7天",
            type: "bar",
            data: this.recentlyData.day7,
            itemStyle: {
              color: "#FF9900"
            }
          },
          {
            name: "近30天",
            type: "bar",
            stack: "广告",
            data: this.recentlyData.day30,
            itemStyle: {
              color: "#61A5E8"
            }
          }
        ]
      };
      // 使用刚指定的配置项和数据显示图表。
      if (option && typeof option === "object") {
        myChart.setOption(option, true);
      }
    },
    // 获取最近xxx天数数据
    getRecentlyData () {
      let params = {
        days: JSON.stringify([7, 30])
      };
      this.axios({
        method: "post",
        url: "/cipher/applyAudit/chart",
        data: params
      })
        .then(res => {
          let recentlyData = res.data.rows;
          recentlyData.forEach((elem, index, arr) => {
            if (index < 30) {
              this.recentlyData.day7[index] = elem.day7;
              this.recentlyData.day30[index] = elem.day30;
              this.recentlyData.applicationName[index] = elem.applicationName;
            }
          });
          this.initChart();
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.auditChart {
  width: 100%;
  height: 80%;
  margin-top: 10px;
  padding-right: 10px;
  box-sizing: border-box;
  overflow: auto;
}
</style>
