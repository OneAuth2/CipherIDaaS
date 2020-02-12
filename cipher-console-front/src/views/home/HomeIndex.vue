<template>
  <div class="content">
    <Row>
      <Col span="24">
        <ul>
          <li>
            <a href="#" @click="skip(1)">{{noGroupCount}}个无部门用户</a>
          </li>
          <li>
            <a href="#" @click="skip(2)">{{lockCount}}个被锁定的账号</a>
          </li>
          <li>
            <a href="#" @click="skip(3)" class="li_demo">{{noLoginCount}}个最近30天未登录账号</a>
          </li>
        </ul>
      </Col>
      <Col span="24">
        <div id="myChart" class="myChart" style></div>
      </Col>
    </Row>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";

export default {
  name: "Index",
  data () {
    return {
      noGroupCount: "",
      lockCount: "",
      noLoginCount: ""
    };
  },
  mounted () {
    this.drawLine();
    this.init();
  },
  computed: {
    ...mapState(["tab"])
  },
  methods: {
    skip (flag) {
      let queryType;
      switch (flag) {
        case 1:
          queryType = 1;
          break;
        case 2:
          queryType = 2;
          break;
        case 3:
          queryType = 3;
          break;
        default:
          break;
      }
      let params = {
        rows: 10,
        page: 1,
        queryType: queryType
      };
      this.changeHomeOrg(params);
      // 跳转组织结构页面
      this.$router.push({ path: "/userCatalogOS" });
    },
    init () {
      this.axios({
        method: "post",
        url: "/cipher/welcome/userInfo",
        data: {}
      })
        .then(res => {
          let data = res.data;
          this.noGroupCount = data.noGroupCount;
          this.lockCount = data.lockCount;
          this.noLoginCount = data.noLoginCount;
        })
        .catch(() => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: "请查看网络"
          });
        });
    },
    drawLine () {
      let timelist = [];
      let data = [];
      let data1 = [];
      this.axios({
        method: "post",
        url: "/cipher/welcome/draw",
        data: {}
      })
        .then(res => {
          timelist = res.data.timeList;
          data = res.data.data;
          data1 = res.data.data2;
          let myChart = this.$echarts.init(document.getElementById("myChart"));
          var option = {
            title: {
              text: "访问统计",
              textStyle: {
                // 标题内容的样式
                color: "#333333", //
                fontStyle: "normal", // 主标题文字字体风格，默认normal，有italic(斜体),oblique(斜体)
                fontWeight: "normal", // 可选normal(正常)，bold(加粗)，bolder(加粗)，lighter(变细)，100|200|300|400|500...
                fontFamily: "Lucida Grande", // 主题文字字体，默认微软雅黑
                fontSize: 18 // 主题文字字体大小，默认为18px
              },
              left: "center"
            },
            grid: {
              backgroundColor: "F3F3F3",

              bottom: "18%" // 距离下边距
            },
            tooltip: {
              trigger: "axis"
            },
            legend: {
              bottom: 0,
              left: "center",
              data: ["近7天访问量", "近30天访问量"],
              icon: "circle",
              itemGap: 40,
              itemWidth: 10
            },
            toolbox: {
              show: true,
              feature: {
                magicType: { show: true, type: ["line", "bar"] },
                restore: { show: true }
              }
            },
            calculable: true,
            xAxis: [
              {
                type: "category",
                data: timelist,
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
                },
                splitLine: {
                  show: false // 显示网格线
                }
              }
            ],
            yAxis: [
              {
                type: "value",
                name: "访问量",
                nameLocation: "middle",
                nameGap: 35,
                interval: 30,
                axisLabel: {
                  interval: 0,
                  rotate: 0,
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
                },
                splitLine: {
                  show: true, // 显示网格线
                  lineStyle: {
                    color: "#DFE4EF" // 网格线颜色设置
                  }
                }
              }
            ],
            series: [
              {
                name: "近7天访问量",
                type: "bar",
                data: data,
                color: ["#7CB5EC"]
              },
              {
                name: "近30天访问量",
                type: "bar",
                color: ["#434348"],
                data: data1
              }
            ]
          };

          myChart.setOption(option);
        })
        .catch(() => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: "请查看网络"
          });
        });
    },
    ...mapMutations(["changeHomeOrg"])
  }
};
</script>

<style scoped>
.myChart {
  width: 90%;
  height: 470px;
  background-color: #ffffff;
  margin-left: 9%;
}
.content {
  background-color: #f3f3f4;
  z-index: -1;
  overflow-y: auto;
  overflow-x: hidden;
}
div {
  font-size: 15px;
  color: blue;
}

ul {
  margin-left: 10%;
}
ul,
li {
  text-align: left;
  color: #9ea6b9;
  list-style: disc;
  margin-bottom: 1%;
}

ul,
li,
a {
  color: #9ea6b9;
}
</style>
