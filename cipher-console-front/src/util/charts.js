function drawLine (DOm, portal, splitNumber, invitol) {
  var MaxNumber = [];
  for (var i in portal.series) {
    MaxNumber.push(Math.max.apply(null, portal.series[i].data));
    i++;
  }
  let maxNumber = this.MaxNumber(Math.max.apply(null, MaxNumber));
  let myChart = DOm;
  var option = {
    title: {
      text: "WIFI使用无线人数",
      textStyle: {
        // 标题内容的样式
        color: "#333333", //
        fontStyle: "normal", // 主标题文字字体风格，默认normal，有italic(斜体),oblique(斜体)
        fontWeight: "normal", // 可选normal(正常)，bold(加粗)，bolder(加粗)，lighter(变细)，100|200|300|400|500...
        fontFamily: "Lucida Grande", // 主题文字字体，默认微软雅黑
        fontSize: 18 // 主题文字字体大小，默认为18px
      },
      left: "center",
      padding: [5, 10]
    },
    grid: {
      backgroundColor: "F3F3F3"
    },
    tooltip: {
      trigger: "axis"
    },
    legend: {
      // orient: "vertical",
      right: "100",
      x: "center",
      y: "bottom",
      padding: [0, 40, 0, 0],
      itemGap: 40,
      itemWidth: 40,
      itemHeight: 20,
      data: portal.portalName
    },
    calculable: true,
    xAxis: [
      {
        type: "category",
        name: portal.xName,
        data: portal.timeList,
        axisLabel: {
          interval: invitol,
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
        min: 0,
        max: maxNumber,
        height: 300,
        splitNumber: splitNumber,
        type: "value",
        name: portal.yName,
        nameLocation: "middle",
        nameGap: 35,

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
            type: "dashed",
            color: "#DFE4EF" // 网格线颜色设置
          }
        }
      }
    ],
    series: portal.series
  };
  myChart.clear();
  myChart.setOption(option, true);
}

// 折线图和柱状图-x轴的最最大值校验
function MaxNumber (maxNumber) {
  if (maxNumber >= 0 && maxNumber <= 10) {
    maxNumber = maxNumber + 2;
  } else if (maxNumber >= 10 && maxNumber <= 100) {
    maxNumber = maxNumber + 5;
  } else if (maxNumber >= 100 && maxNumber <= 1000) {
    maxNumber = maxNumber + 100;
  }
  maxNumber = maxNumber % 2 === 0 ? maxNumber : maxNumber + 1;
  return maxNumber;
}
export default {
  drawLine,
  MaxNumber
};
