<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-right">
        <myButton @click="doClickDemo">扫描目录</myButton>
        <myButton @click="doSure">同步已选</myButton>
        <myButton @click="doall(true)">同步全部</myButton>
        <mySelect :dataList="statusList"
                  showString="请选择"
                  v-model="checkNumber"
                  @on-change="changeStatus" />
      </div>
    </div>

    <div class="tabled-table">
      <Table border
             :columns="columns7"
             :data="data6"
             @on-selection-change="handleSelectRow()"
             ref="selection"></Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="curentPage"
          show-elevator
          show-sizer
          show-total
          :page-size="10"
          :page-size-opts="[10,50,100,200]"
          @on-change="changePage"
          @on-page-size-change="pageSizeChange"></Page>

    <Modal v-model="modal1"
           @on-ok="ok"
           width="400">
      <p slot="header"
         class="header">
        <span>扫描目录</span>
        <span class="sub-title">（请选择你要的更新）</span>
      </p>
      <div class="radiogroup">
        <RadioGroup v-model="valup">
          <Radio label="lastTime">从上一次时间点扫描</Radio>
          <Radio label="anew">重新扫描此AD目录</Radio>
        </RadioGroup>
      </div>
    </Modal>
  </div>
</template>
<script>
export default {
  data () {
    return {
      ids: [],
      muldata: [],
      valup: "lastTime", // 上一次
      modal1: false,
      modal2: false,
      curentPage: 1,
      querytype: 0,
      checkNumber: "",
      statusList: [{ value: null, label: "全部" }, { value: "1", label: "新增" }, { value: "2", label: "更新" }],
      size: 10,
      adId: 0,
      total: 0,
      columns7: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        {
          title: "用户名",
          key: "accountNumber",
          width: 400
        },
        {
          title: "姓名",
          key: "username"
        },
        { title: "邮箱", key: "mail" },
        {
          title: "电话",
          key: "phoneNumber"
        },
        {
          title: "同步选项",
          key: "checkNumber"
        }
      ],
      data6: []
    };
  },
  created () {
    this.adId = this.$route.query.adId;
  },
  mounted () {
    this.getData();
  },
  methods: {
    // 获取数据
    getData () {
      let params = {
        rows: this.size,
        page: 1,
        adId: this.adId,
        sidx: "",
        sord: "asc"
      };
      this.finddata(params);
    },
    // 同步已选
    syncSelected () {
      this.axios({
        method: "post",
        url: "/cipher/syncBuffer2User",
        data: {
          bufferIdList: this.ids.join(",")
        }
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$Modal.success({
              title: "同步成功",
              content:
                "添加" +
                res.data.add +
                "条" +
                "更新" +
                res.data.update +
                "条",
              onOk: () => {
                this.getData();
              }
            });
          } else {
            this.$Modal.error({
              title: "失败",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 同步全部
    syncAll () {
      this.axios({
        method: "post",
        url: "/cipher/syncAll",
        data: {
          bufferIdList: this.ids.join(",")
        }
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$Modal.success({
              title: "同步成功",
              content:
                "添加" +
                res.data.add +
                "条" +
                "更新" +
                res.data.update +
                "条",
              onOk: () => {
                this.getData();
              }
            });
          } else {
            this.$Modal.error({
              title: "失败",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 同步全部
    doall (status) {
      this.$myModal.confirm({
        title: "同步全部",
        content: "将当前扫描结果下的全部用户同步到当前组织结构，没有的将新建，存在的将更新。"
      }).then(async (val) => {
        this.syncAll();
      }).catch(() => { });
    },
    // 同步已选
    doSure () {
      if (this.muldata.length !== 0) {
        this.muldata.forEach(item => {
          this.ids.push(item.id);
        });
        this.$myModal.confirm({
          title: "同步已选",
          content: "将选中的用户同步到当前组织结构下，没有的将新建，存在的将更新。"
        }).then(async (val) => {
          this.syncSelected();
        }).catch(() => { });
      } else {
        this.$myModal.error({
          title: "请先选择一个要同步的选项"
        });
      }
    },
    // 查询全部、新增、更新
    changeStatus () {
      let params = {
        rows: this.size,
        page: 1,
        adId: this.adId,
        sidx: "",
        sord: "asc",
        checkNumber: this.checkNumber
      };
      this.finddata(params);
    },
    // 处理表格已选择的数据
    handleSelectRow (selection, index) {
      this.muldata = this.$refs.selection.getSelection();
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/aduserbuffer/list?json",
        data: params
      })
        .then(function (res) {
          res.data.rows.forEach(item => {
            if (item.checkNumber === null) {
              item.checkNumber = "新增";
            } else {
              item.checkNumber = "更新";
            }
          });
          that.data6 = res.data.rows;
          that.total = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    docancel () {
      this.$router.go(-1);
    },
    changePage (index) {
      let that = this;
      let params = {
        rows: that.size,
        page: index,
        adId: that.adId,
        sidx: "",
        sord: "asc",
        checkNumber: that.checkNumber
      };
      that.curentPage = index;
      that.finddata(params);
    },
    pageSizeChange (size) {
      let that = this;
      let params = {
        rows: size,
        page: 1,
        adId: that.adId,
        sidx: "",
        sord: "asc",
        checkNumber: that.checkNumber
      };
      that.finddata(params);
      that.size = size;
    },
    doClickDemo () {
      this.modal1 = true;
    },
    // 更新
    ok () {
      if (this.valup === "lastTime") {
        this.doClick();// 从上一次时间点扫描
      }
      if (this.valup === "anew") {
        this.doAllClick();// 重新扫描此AD目录
      }
    },
    doClick () {
      let params = {
        adId: this.adId
      };
      this.axios({
        method: "post",
        url: "/cipher/depart_sync",
        data: params
      })
        .then(res => {
          this.$myMessage.success("更新成功");
          // this.$Modal.success({
          //   title: '扫到' + res.data.allData + '记录',
          //   content:
          //         '新的账号记录' +
          //         res.data.add +
          //         '条<br/>更新账号记录' +
          //         res.data.update +
          //         '条'
          // })
          let params = {
            rows: this.size,
            page: 1,
            sidx: "",
            sord: "asc",
            adId: this.adId
          };
          this.finddata(params);
        })
        .catch(error => {
          console.log(error);
          this.$Modal.error({
            title: "提示",
            content: "扫描错误"
          });
        });
    },
    doAllClick () {
      let params = {
        adId: this.adId
      };
      this.axios({
        method: "post",
        url: "/cipher/all_sync",
        data: params
      })
        .then(res => {
          this.$myMessage.success("更新成功");
          let params = {
            rows: this.size,
            page: 1,
            sidx: "",
            sord: "asc",
            adId: this.adId
          };
          this.finddata(params);
        })
        .catch(error => {
          console.log(error);
          this.$Modal.error({
            title: "提示",
            content: "扫描错误"
          });
        });
    }

  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/tableStyle.less";
</style>
