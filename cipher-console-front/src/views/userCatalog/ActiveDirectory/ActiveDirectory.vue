<template>
  <div class="AD tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <Tooltip content="请输入AD目录中的ip地址"
                 theme="light">
          <myInput placeholder="请输入AD域地址"
                   v-model="queryName"
                   radius="half"
                   :search="true"
                   @handleClick="querynamebtn" />
        </Tooltip>
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="addad">加AD活动目录</myButton>
      </div>
    </div>

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
    <Modal v-model="modal1"
           @on-ok="deletead2"
           :closable="false"
           class="tip-modal"
           width="528">
      <p slot="header"
         class="header-confirm">
        <Icon type="md-alert" />
        <span>确定要删除这个AD目录吗</span>
      </p>
      <div class="tip-modal-content"
           style="width:326px">
        <myInput v-model="ifdelcode"
                 placeholder="确认删除，请输入DELETE"></myInput>
      </div>
      <div style="width:326px;text-align: left;margin-top: 10px;color: red;"
           v-if="viladateInput">
        <span>请输入字符DELETE</span>
      </div>
    </Modal>

    <Modal v-model="modal2"
           :closable="false"
           class="tip-modal"
           width="400">
      <p slot="header"
         class="header-confirm">
        <Icon type="md-alert" />
        <span>删除失败 请输入字符DELETE</span>
      </p>
    </Modal>
  </div>
</template>

<script>
export default {
  data () {
    return {
      modal2: false, // 失败弹出框
      viladateInput: false, // 校验输入的值是否为delete
      id: 0,
      ifdelcode: "",
      modal1: false,
      queryName: "",
      total: 0,
      curentPage: 1,
      size: 10,
      columns7: [
        {
          title: "AD目录",
          key: "ip",
          width: 300,
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.toadactivity(params.index);
                    }
                  }
                },
                this.data6[params.index].ip
              )
            ]);
          }
        },
        {
          title: "最后同步时间",
          key: "createTime"
        },
        {
          title: "操作",
          key: "oper",
          width: 300,
          align: "center",
          render: (h, params) => {
            return h("span", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.adset(params.index);
                    }
                  }
                },
                "配置目录" + " "
              ),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.deletead(params.index);
                    }
                  }
                },
                " " + "删除"
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
      page: 1
    };
    that.finddata(params);
  },
  methods: {
    deletead2 () {
      let that = this;
      if (that.ifdelcode === "DELETE") {
        that.deletead3();
        that.ifdelcode = "";
      } else {
        this.modal2 = true;
      }
    },
    deletead3 () {
      var that = this;
      let params = {
        id: that.id
      };
      this.axios({
        method: "post",
        url: "/cipher/ldap/deleteAd",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            that.$myMessage.success(res.data.msg);
            let params = {
              rows: that.size,
              page: 1
            };
            that.finddata(params);
          } else {
            that.$myMessage.error(res.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    deletead (index) {
      let that = this;
      that.id = that.data6[index].id;
      that.modal1 = true;
    },
    querynamebtn () {
      let params = {
        queryName: this.queryName,
        page: 1,
        rows: this.size
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/ldap/list?json",
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
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index
      };
      this.curentPage = index;
      this.finddata(params);
    },
    adset (index) {
      this.$router.push({
        name: "adSet",
        query: { adId: this.data6[index].id }
      });
    },
    addad () {
      this.$router.push({ name: "adAdd" });
    },
    toadactivity (index) {
      this.$router.push({
        name: "adList",
        query: { adId: this.data6[index].id }
      });
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
/deep/.ivu-tooltip {
  width: 100%;
}
</style>
