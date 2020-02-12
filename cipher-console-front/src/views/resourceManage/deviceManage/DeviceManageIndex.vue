<template>
  <div class="DM tabled-wrap">
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
                  @click="$router.push({ path: '/resourceManageDM/add'})">添加设备</myButton>
      </div>
    </div>
    <div class="tabled-table appManage-list-table">
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
          title: "设备名称",
          key: "devicename",
          align: "center",
          render: (h, params) => {
            const title = params.row.devicename;
            return h("a", {
              on: {
                click: () => {
                  this.toDetailPage(params.row.uuid);
                }
              }
            }, title);
          }
        },
        {
          title: "设备地址",
          key: "equipAddr",
          align: "center"
        },
        {
          title: "描述",
          key: "describee",
          align: "center"
        },
        {
          title: "状态",
          key: "status",
          align: "center",
          render: (h, params) => {
            const status = params.row.status === 0 ? "启用" : "停用";
            return h("div", status);
          }
        },
        {
          title: "操作",
          key: "option",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.toDetailPage(params.row.uuid);
                  }
                }
              }, "编辑"),
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
        devicename: "",
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
        devicename: this.searchWord,
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
        devicename: this.searchWord2,
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
        devicename: this.searchWord2,
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
        url: "/cipher/equip/list",
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
    deleteData (uuid) {
      this.axios({
        method: "post",
        url: "/cipher/equip/delete",
        data: { uuid: uuid }
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
            this.$myModal.error({
              title: "删除失败",
              content: "请稍后重试"
            });
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
        this.deleteData(this.list[index].uuid);
      }).catch(() => { });
    },
    /**
     * 跳转到详情页
     * @param {*void}
     * @author yezi 2019-08-06
     */
    toDetailPage (uuid) {
      this.$router.push({ name: "DMDetail", query: { uuid } });
    }

  }

};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
