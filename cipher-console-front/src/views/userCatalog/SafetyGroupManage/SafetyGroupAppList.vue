<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入应用名称"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryNamebtn" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="authorization">授权</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table :data="data6"
             border
             :columns="columns7"></Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="curentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="pageSizeChange"></Page>
    <!--穿梭框-->
    <Modal v-model="showMadal"
           @on-cancel="cancel"
           width="668"
           class="tree-transfer">
      <p slot="header"
         class="header">
        <span class="title">添加授权应用</span>
        <span class="sub-title">（{{teamName}}）</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="data3"
                      :leftDataType="dataType"
                      @rightData="getRightData"
                      :placeholder="placeholder">
          <span slot="leftText">授权应用</span>
          <span slot="rightText">已授权应用</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                :loading="loading"
                :disabled="isDisable"
                @click="saveTeamApplicationMap">保存</Button>
        <Button @click="reset"
                style="margin-left:10px;">重置</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
export default {
  components: {
    TreeTransfer
  },
  data () {
    return {
      applicationids: "",
      curentPage: 1,
      queryName: "",
      total: 0,
      size: 10,
      placeholder: "",
      data: [],
      data3: [],
      dataType: 0,
      showMadal: false,
      isDisable: false,
      loading: false,
      teamId: "",
      teamName: "",
      columns7: [
        {
          title: "应用名称",
          key: "applicationName",
          align: "center"
        },

        {
          title: "操作",
          key: "edit",
          width: 400,
          align: "center",
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.cancelAuthration(params.index);
                    }
                  }
                },
                "取消授权"
              )
            ]);
          }
        }
      ],
      data6: []
    };
  },
  created () {
    this.teamId = this.$route.query.teamId;
    this.teamName = this.$route.query.teamName;
  },
  mounted () {
    var that = this;
    let params = {
      id: that.teamId,
      rows: that.size,
      page: 1
    };
    that.finddata(params);
  },
  methods: {
    queryNamebtn () {
      var that = this;
      let params = {
        id: that.teamId,
        queryName: that.queryName,
        rows: that.size,
        page: 1
      };
      that.finddata(params);
    },
    reset () {
      this.dataType = 0;
      this.findAuthtree();
    },
    saveTeamApplicationMap () {
      let that = this;
      that.setUnderway();
      let params = {
        id: that.teamId,
        applicationIds: that.applicationids
      };
      this.axios({
        method: "post",
        url: "/cipher/team/saveTeamApplicationMap",
        data: params
      })
        .then(function (res) {
          that.setCompleted();
          that.cancel();
          if (res.data.code === 0) {
            that.$myMessage.success(res.data.msg);
          } else if (res.data.code === 1) {
            that.$myMessage.error(res.data.msg);
          }
        })
        .catch(error => {
          that.setCompleted();
          console.log(error);
        });
    },
    // 设置进行中状态
    setUnderway () {
      this.loading = true;
      this.isDisable = true;
    },
    // 设置完成状态
    setCompleted () {
      this.loading = false;
      this.isDisable = false;
      this.showMadal = false;
    },
    getRightData (data) {
      let appIdArr = [];
      data.forEach(item => {
        appIdArr.push(item.id);
      });
      this.applicationids = appIdArr.join(",");
    },
    findAuthtree () {
      let that = this;
      that.dataType = 3;
      let params = {
        id: that.teamId
      };
      this.axios({
        method: "post",
        url: "/cipher/team/teamAuthApplication",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            that.data3 = res.data.msg;
            that.placeholder = "请输入应用名";
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 取消授权确认框
    cancelAuthration (index) {
      this.$myModal.confirm({
        title: "取消授权",
        content: `确认取消${this.data6[index].applicationName}对${this.teamName}的授权吗?`,
        confirmVal: "继续"
      }).then(async (val) => {
        this.cancelAuth(index);
      }).catch(() => { });
    },
    // 取消授权
    cancelAuth (index) {
      let that = this;
      let params = {
        applicationId: this.data6[index].id,
        id: this.teamId
      };
      this.axios({
        method: "post",
        url: "/cipher/team/cancelAuthration",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            that.$myMessage.success(res.data.msg);
            let params = {
              id: that.teamId,
              rows: that.size,
              page: 1
            };
            that.finddata(params);
          } else if (res.data.code === 1) {
            that.$myMessage.error(res.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancel () {
      this.showMadal = false;
      this.dataType = 0;
      let params = {
        id: this.teamId,
        rows: this.size,
        page: 1
      };
      this.finddata(params);
    },
    // 弹出穿梭框初始化数据
    authorization () {
      this.findAuthtree();
      this.showMadal = true;
    },
    finddata (params) {
      let that = this;
      this.axios({
        method: "post",
        url: "/cipher/team/getTeamApplications",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            that.data6 = res.data.rows;
            that.total = res.data.total;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageSizeChange (size) {
      let params = {
        id: this.teamId,
        rows: size,
        page: 1
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        id: this.teamId,
        rows: this.size,
        page: index
      };
      this.curentPage = index;
      this.finddata(params);
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
</style>
