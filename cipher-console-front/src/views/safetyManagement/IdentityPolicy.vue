<template>
  <div class="ips tabled-wrap">
    <div class="tabled-header">
      <myButton btnType="info"
                @click="addStrategy">添加策略</myButton>
    </div>
    <div class="tabled-table">
      <Table :columns="columns7"
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
      queryName: "",
      total: 0,
      size: 10,
      curentPage: 1,
      columns7: [
        {
          title: "策略名称",
          key: "strategyName",
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.toEdit(params.index);
                    }
                  }
                },
                this.data6[params.index].strategyName
              )
            ]);
          }
        },
        {
          title: "优先级",
          key: "priority"
        },
        {
          title: "认证源",
          key: "authType",
          render: (h, params) => {
            const text = this.data6[params.index].authType === 0 ? "本地认证" : this.data6[params.index].authType === 1 ? "AD域" : "JLT_ERP";
            return h("div", [h("span", text)]);
          }
        },

        {
          title: "二次认证",
          key: "secondAuth",
          render: (h, params) => {
            let text = "";
            let tempArray = [];
            if (this.data6[params.index].secondAuth === 0) {
              var secondAuthType = this.data6[params.index].secondAuthType;
              // if (secondAuthType.twoAuthSf === 0) {
              //   tempArray.push("赛赋扫码");
              // }
              if (secondAuthType.twoAuthDt === 0) {
                tempArray.push("动态密码");
              }
              if (secondAuthType.twoAuthDd === 0) {
                tempArray.push("钉钉扫码");
              }
              // if (secondAuthType.twoAuthDingDt === 0) {
              //   tempArray.push("钉钉动态密码");
              // }
              if (secondAuthType.twoAuthDingPush === 0) {
                tempArray.push("钉钉PUSH认证");
              }
              if (secondAuthType.twoAuthDb === 0 && this.data6[params.index].switches) {
                tempArray.push("大白扫码");
              }
              if (secondAuthType.twoAuthWx === 0 && this.data6[params.index].switches === 0) {
                tempArray.push("企业微信扫码");
              }
              if (secondAuthType.twoAuthNum === 0) {
                tempArray.push("手机随机码");
              }
              if (secondAuthType.twoAuthMail === 0 && this.data6[params.index].switches) {
                tempArray.push("邮件随机码");
              }
            } else {
              text = "关闭";
            }
            if (tempArray.length) {
              text = tempArray.join(",");
            }
            return h("div", [h("span", text)]);
          }
        },
        {
          title: "生效范围",
          key: "groupNames"
        },
        {
          title: "操作",
          key: "edit",
          width: 150,
          align: "center",
          render: (h, params) => {
            if (this.data6[params.index].id === 1) {
            } else {
              return h("a", [
                h(
                  "span",
                  {
                    on: {
                      click: () => {
                        this.delete(params.index);
                      }
                    }
                  },
                  "删除"
                )
              ]);
            }
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
      page: 1,
      sord: "asc",
      sidx: "priority"
    };
    that.finddata(params);
  },
  methods: {
    toEdit (index) {
      this.$router.push({
        name: "strategyDetail",
        query: {
          id: this.data6[index].id,
          strategyName: this.data6[index].strategyName
        }
      });
    },
    addStrategy () {
      this.$router.push({ name: "strategyAdd" });
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/list",
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
        page: 1,
        sord: "asc",
        sidx: "priority"
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        sord: "asc",
        sidx: "priority"
      };
      this.curentPage = index;
      this.finddata(params);
    },
    edit (index) {
      this.$router.push({
        path: this.editurl,
        query: { id: this.data6[index].id }
      });
    },
    delete (index) {
      this.$myModal.confirm({
        title: "确定要删除这条策略信息吗",
        content: "删除后将无法恢复,确认删除?"
      }).then(async (val) => {
        this.dodelete(index);
      }).catch(() => { });
    },
    dodelete (index) {
      let params = {
        id: this.data6[index].id
      };
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/delete",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
            this.data6.splice(index, 1);
          } else if (res.data.return_code === 0) {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.tabled-header {
  text-align: right;
}
</style>
