<template>
  <div class="wfd tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="设备名称"
                 v-model="searchWord"
                 radius="half"
                 :search="true"
                 @handleClick="getSearchList" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="$router.push({ path: '/netAccessServiceWNVD/WFDAdd'})">添加免证设备</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table :columns="title"
             :data="list"></Table>
    </div>
    <div class="tabled-page">
      <!-- <span>共{{total}} 条记录 第 {{current}} / {{Math.ceil(total/pageSize)}} 页</span> -->
      <Page :total="total"
            :page-size="pageSize"
            :current="current"
            show-sizer
            show-elevator
            show-total
            @on-change="changePage"
            @on-page-size-change="changePageSize" />
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      // 表格数据
      title: [
        {
          title: "名称",
          key: "name",
          align: "center"
        },
        {
          title: "用途",
          key: "application",
          align: "center"
        },
        {
          title: "MAC地址",
          key: "mac",
          align: "center"
        },
        {
          title: "IP地址",
          key: "ip",
          align: "center"
        },
        {
          title: "绑定时间",
          key: "createTime",
          align: "center"
        },
        {
          title: "操作",
          key: "option",
          align: "center",
          render: (h, params) => {
            let isOffline = params.row.ip ? "下线" : "";
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.offline(params.index);
                  }
                }
              }, isOffline),
              h("a", {
                on: {
                  click: () => {
                    this.delete(params.index);
                  }
                }
              }, "删除")

            ]);
          }
        }
      ],
      list: [], // 表格列表数据
      searchWord: "", // 搜索词语
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1, // 当前页
      searchWord2: "", // 点击搜索后存放的搜索词
      isDisabled: "" // 点击是否禁用
    };
  },
  created () {
    this.getAllList();
  },
  methods: {
    /**
     * 获取全部列表数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getAllList () {
      let params;
      params = {
        name: "",
        page: this.current,
        rows: this.pageSize
      };
      this.getWEDList(params);
    },
    /**
     * 根据搜索词获取查询到的列表
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getSearchList () {
      let params;
      params = {
        name: this.searchWord,
        page: 1,
        rows: this.pageSize
      };
      this.getWEDList(params);
      this.current = 1;
      this.searchWord2 = this.searchWord;
    },
    /**
     * 切换每页条数时触发
     * @param {*Number 每页需要显示的条数} pageSize
     * @author yezi 2019-08-02
     */
    changePageSize (size) {
      let params = {
        name: this.searchWord2,
        rows: size,
        page: 1
      };
      this.getWEDList(params);
      this.pageSize = size;
    },
    /**
     * 改变页码时触发
     * @param {*Number 需要请求的页码} index
     * @author yezi 2019-08-02
     */
    changePage (index) {
      let params = {
        name: this.searchWord2,
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getWEDList(params);
    },
    /**
     * 获取列表数据，发送请求
     * @param {*Object 发送请求参数} params
     *        {name:"查询搜索词",rows:"每页显示的条数",page:"请求数据的页码"}
     * @author yezi 2019-08-02
     */
    getWEDList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/acl/list",
        data: params
      })
        .then(res => {
          this.total = res.data.total;
          this.list = res.data.rows;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 请求调用后台接口删除某条数据 根据mac地址
     * @param {*String 需要删除数据的mac地址} mac
     * @author yezi 2019-08-02
     */
    deleteData (mac) {
      this.axios({
        method: "post",
        url: "/cipher/acl/delete",
        data: { mac: mac }
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success("删除成功!");
            // 删除后判断总页数是否大于当前页
            let totalPage = Math.ceil((this.total - 1) / this.PageSize); // 当前总页数
            this.current = totalPage < this.current ? totalPage : this.current;
            let params = {
              name: this.searchWord2,
              page: this.current,
              rows: this.pageSize
            };
            this.getWEDList(params);
          } else {
            this.$myModal.error("删除失败");
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 点击删除触发，弹出删除确认框
     * @param {*String 需要删除数据对应的table列表行序} index
     * @author yezi 2019-08-02
     */
    delete (index) {
      this.$myModal.confirm({
        title: "确定删除吗？"
      }).then(async (val) => {
        // 点击确定的回调
        this.deleteData(this.list[index].mac);
      }).catch(() => { });
    },
    /**
     * 点击下线触发，弹出删除确认框
     * @param {*String 需要下线数据对应的table列表行序} index
     * @author yezi 2019-08-02
     */
    offline (index) {
      this.$myModal.confirm("确定下线吗？")
        .then(async (val) => {
          this.handleSubmitOffline(index);
        }).catch(() => { });
    },
    /**
     * 执行下线操作，向后台发送请求
     * @param {*String 需要下线数据对应的table列表行序} index
     * @author yezi 2019-08-02
     */
    handleSubmitOffline (index) {
      let params = {
        name: this.list[index].name,
        ip: this.list[index].ip,
        mac: this.list[index].mac
      };
      this.axios({
        method: "post",
        url: "/cipher/acl/logout?json",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success("下线成功!");
            let params = {
              name: this.searchWord2,
              page: this.current,
              rows: this.pageSize
            };
            this.getWEDList(params);
          } else {
            this.$myModal.error("下线失败");
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

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
