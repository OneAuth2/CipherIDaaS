<template>
  <div class="md tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="querynamebtn" />
      </div>
      <div class="tabled-header-btn">
        <span>设备数量</span>
        <myInput class="md-header__input--space"
                 type="number"
                 v-model="number"
                 :min="0"
                 :max="99"
                 :precision="0"
                 style="width: 120px"
                 placeholder="请输入设备数量" />
        <myButton btnType="info"
                  @click="saveNumber">保存</myButton>
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
  </div>
</template>

<script>
export default {
  data () {
    return {
      total: 0,
      queryName: "",
      size: 10,
      curentPage: 1,
      id: "",
      openSelect: 0,
      number: 0,
      columns7: [
        {
          title: "姓名",
          key: "name"
        },
        {
          title: "账号",
          key: "accountNumber"
        },
        {
          title: "设备",
          key: "hman"
        },
        {
          title: "绑定时间",
          key: "createTime"
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
                  props: {
                    type: "text",
                    size: "small"
                  },
                  style: {
                    marginRight: "5px",
                    color: "#19A0DD"
                  },
                  on: {
                    click: () => {
                      this.delete(params.index);
                    }
                  }
                },
                "解绑设备编号"
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
    let params2 = {};
    that.finddata(params);
    that.finddata2(params2);
  },
  methods: {
    querynamebtn () {
      let params = {
        rows: this.size,
        page: 1,
        queryName: this.queryName
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/device/list?json",
        data: params
      }).then(function (res) {
        that.data6 = res.data.rows;
        that.total = res.data.total;
      });
    },
    finddata2 (params2) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/device/queryList",
        data: params2
      }).then(function (res) {
        that.number = res.data.number;
      });
    },
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        queryName: this.queryName
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        queryName: this.queryName
      };
      this.curentPage = index;
      this.finddata(params);
    },
    delete (index) {
      this.$myModal.confirm({
        title: "确定要解绑吗？"
      }).then(async (val) => {
        this.dodelete(index);
      }).catch(() => { });
    },
    dodelete (index) {
      let that = this;
      let params = {
        id: this.data6[index].id
      };
      this.axios({
        method: "post",
        url: "/cipher/device/delete",
        data: params
      })
        .then(res => {
          if (res.data.returnCode === 0) {
            that.$myMessage.success(res.data.msg);
            this.data6.splice(index, 1);
          } else if (res.data.returnCode === 1) {
            that.$myMessage.error(res.data.msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    saveNumber () {
      this.$myModal.confirm({
        title: "确定要保存吗？"
      }).then(async (val) => {
        this.dosave();
      }).catch(() => { });
    },
    dosave () {
      let params = {
        number: this.number,
        openSelect: this.openSelect
      };
      this.axios({
        method: "post",
        url: "/cipher/device/onAndoff",
        data: params
      })
        .then(res => {
          if (res.data.returnCode === 0) {
            this.$myMessage.success(res.data.msg);
          } else if (res.data.returnCode === 1) {
            this.$myMessage.error(res.data.msg);
          }
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
.md-header__input--space {
  margin: 0 8px;
}
</style>
