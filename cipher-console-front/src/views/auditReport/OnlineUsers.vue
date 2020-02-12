<template>
    <div class="tablelist">
      <Table border :columns="columns7" :data="data6"></Table>
     <Page
                :total="total"
                :current="curentPage"
                show-elevator
                show-sizer
                show-total
                @on-change="changePage"
                @on-page-size-change="pageSizeChange"
              ></Page>
    </div>
</template>

<script>
export default {
  data () {
    return {
      curentPage: 1,
      size: 10,
      total: 0,
      ip: "",
      username: "",
      columns7: [
        {
          title: "名称",
          key: "username"
        },
        {
          title: "身份",
          key: "type"
        },
        {
          title: "ip地址",
          key: "ip"
        },
        {
          title: "登录时间",
          key: "loginTime"
        },
        {
          title: "操作",
          key: "edit",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("a", [
              h("span", {
                on: {
                  click: () => {
                    this.delete(params.index);
                  }
                }
              }, "下线")
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
      page: 1,
      rows: that.size
    };
    this.finddata(params);
  },
  methods: {
    finddata (params) {
      var that = this;
      that.axios({
        method: "post",
        url: "/cipher/vistor/online/list?json",
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
        page: 1,
        rows: size,
        sidx: "",
        sord: "asc"
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        page: index,
        rows: this.size,
        sidx: "",
        sord: "asc"
      };
      this.curentPage = index;
      this.finddata(params);
    },
    delete (index) {
      this.$Modal.confirm({
        title: "提示",
        content: "<p>确定要下线吗？</p>",
        onOk: () => {
          this.dodelete(index);
        },
        onCancel: () => {
        }
      });
    },
    dodelete (index) {
      let that = this;
      let params = {
        username: "/" + this.data6[index].username + "/",
        ip: "/" + this.data6[index].ip + "/"

      };
      this.axios({
        method: "post",
        url: "/cipher/vistor/logout?json",
        data: params
      })
        .then(function (res) {
          if (res.data.errorCode === 0) {
            location.reload();
          } else {
            that.$Modal.error({
              title: "提示",
              content: res.data.errorMessage
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped>
  .header {
    margin-bottom: 30px;

  }
  #queryName{
    float: left;
    border: 1px solid #ccc;
    padding: 7px 0px;
    border-radius: 3px; /*css3属性IE不支持*/
    padding-left:5px;
    width:15%;
  }
  .querybtn{
    float: left;
    background-color:#C4C4C4;
    border:none;
    color:white;
    padding:5px 13px;
    text-align:center;
    text-decoration:none;
    display:inline-block;
    font-size:16px;
    margin:4px 2px;
    cursor: pointer;
  }
  .tablelist{
    clear:both;
  }
</style>
