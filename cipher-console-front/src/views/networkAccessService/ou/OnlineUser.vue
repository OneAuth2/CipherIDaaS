<template>
  <div class="ou tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="searchWord"
                 radius="half"
                 :search="true"
                 @handleClick="getSearchList" />
      </div>
      <div class="tabled-header-btn">
        <mySelect :dataList=" visitorTypeList"
                  showString="显示"
                  v-model="visitorType"
                  @on-change="changeVisitorType" />
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
          title: "姓名",
          key: "name",
          align: "center"
        },
        {
          title: "账号",
          key: "username",
          align: "center"
        },
        {
          title: "类型",
          key: "visitorType",
          align: "center"
        },
        {
          title: "Portal描述",
          key: "description",
          align: "center"
        },
        {
          title: "上线时间",
          key: "loginTime",
          align: "center"
        },
        {
          title: "操作",
          key: "option",
          align: "center",
          width: 100,
          render: (h, params) => {
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.offline(params.index);
                  }
                }
              }, "下线")
            ]);
          }
        }
      ],
      list: [], // 表格列表数据
      searchWord: "", // 搜索词语
      visitorType: "", // 用户类型
      visitorTypeList: [{ value: "", label: "全部" }, { value: "1", label: "员工" }, { value: "0", label: "访客" }],
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
        visitorType: "",
        page: this.current,
        rows: this.pageSize
      };
      this.getList(params);
    },
    /**
     * 根据搜索数据获取搜索列表数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getSearchList () {
      let params;
      params = {
        name: this.searchWord,
        visitorType: this.visitorType,
        page: 1,
        rows: this.pageSize
      };
      this.getList(params);
      this.current = 1;
      this.searchWord2 = this.searchWord;
    },
    /**
     * 根据选中的用户类型（员工、访客），获取列表数据
     * @param {*Object 组件回调函数参数，包含位置index和选项值value和选项label} data
     * @author yezi 2019-08-02
     */
    changeVisitorType (data) {
      this.visitorType = data.value;
      let params = {
        name: this.searchWord2,
        visitorType: this.visitorType,
        rows: this.pageSize,
        page: 1
      };
      this.getList(params);
      this.current = 1;
    },
    /**
     * 改变一页显示的页面条数时触发，重新获取列表数据
     * @param {*Number 一页显示的页面条数} size
     * @author yezi 2019-08-02
     */
    changePageSize (size) {
      let params = {
        name: this.searchWord2,
        visitorType: this.visitorType,
        rows: size,
        page: 1
      };
      this.getList(params);
      this.pageSize = size;
    },
    /**
     * 改变当前页时触发，重新获取列表数据
     * @param {*Number 需要显示的页码数} index
     * @author yezi 2019-08-02
     */
    changePage (index) {
      let params = {
        name: this.searchWord2,
        visitorType: this.visitorType,
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getList(params);
    },
    /**
     * 发送请求，获取列表数据
     * @param {*Object 获取列表请求参数} params
     *        {name:"搜索词",visitorType:"用户类型，员工1，访客0",
     *        rows:"一页显示的条数",page:"请求的页码"}
     * @author yezi 2019-08-02
     */
    getList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/vistor/getlist",
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
     * 点击下线，弹出下线提示框
     * @param {*Number 需要操作数据对应的table列表对应的行序} index
     * @author yezi 2019-08-02
     */
    offline (index) {
      this.$myModal.confirm({
        title: "确定下线吗？"
      }).then(async (val) => {
        // 点击确定的回调
        this.handleSubmitOffline(index);
      }).catch(() => { });
    },
    /**
     * 执行下线，请求数据
     * @param {*Number 需要操作数据对应的table列表对应的行序} index
     * @author yezi 2019-08-02
     */
    handleSubmitOffline (index) {
      let params = {
        name: this.list[index].name,
        ip: this.list[index].ip
        // mac: this.list[index].mac
      };
      this.axios({
        method: "post",
        url: "/cipher/vistorLoginLog/logout?json",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success("下线成功!");
            // 删除后判断总页数是否大于当前页
            let totalPage = Math.ceil((this.total - 1) / this.PageSize); // 当前总页数
            this.current = totalPage < this.current ? totalPage : this.current;
            let params = {
              name: this.searchWord2,
              page: this.current,
              rows: this.pageSize
            };
            this.getList(params);
          } else {
            this.$myModal.error("下线失败？");
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
