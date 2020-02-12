<template>
  <div>
    <div class="header">
      <Input placeholder="请输入姓名 / 账号"
             class="queryName"
             v-model="queryName" />
      <Button class="querybtn"
              @click="querynamebtn">搜索</Button>
    </div>

    <div class="tablelist">
      <Table border
             :columns="columns7"
             :data="data6"></Table>
      <Page :total="total"
            :current="curentPage"
            show-elevator
            show-sizer
            show-total
            @on-change="changePage"
            @on-page-size-change="pageSizeChange"></Page>
    </div>

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
      columns7: [
        {
          title: "管理员用户登录名",
          key: "adminLoginName"
        },
        {
          title: "用户登录名",
          key: "loginName"
        },
        {
          title: "用户手机号",
          key: "mobile"
        },

        {
          title: "开始时间",
          key: "startTime"
        },
        {
          title: "结束时间",
          key: "endTime"
        },
        {
          title: "创建时间",
          key: "createTime"
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
    show (index) {
      this.$Modal.info({
        title: "User Info",
        content: `Name：${this.data6[index].name}<br>Age：${this.data6[index].age}<br>Address：${this.data6[index].address}`
      });
    },
    remove (index) {
      this.data6.splice(index, 1);
    },
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
        url: "/cipher/goldmantis/list?json",
        data: params
      })
        .then(function (res) {
          that.data6 = res.data.rows;
          that.total = res.data.total;
          console.log(res.data);
        })
        .catch(error => {
          console.log(error);
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
    }
  }

};
</script>

<style scoped>
.header {
  margin-bottom: 30px;
}
.queryName {
  float: left;
  width: 200px;
}
.querybtn {
  float: left;
}
.tablelist {
  clear: both;
}
</style>
