<template>
  <div class="wvm tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="输入姓名/手机号"
                 v-model="searchWord"
                 radius="half"
                 :search="true"
                 @handleClick="getSearchList" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="$router.push({ path:'/netAccessServiceWVM/VisitorAdd'})">添加访客</myButton>
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
            show-toatl
            @on-change="changePage"
            @on-page-size-change="changePageSize" />
    </div>

  </div>
</template>

<script>
export default {
  data () {
    return {
      title: [
        {
          title: "外部ID",
          key: "id"
        },
        {
          title: "姓名",
          key: "loginName"
        },
        {
          title: "手机号",
          key: "mobile"
        },
        {
          title: "登录密码",
          key: "password"
        },
        {
          title: "单位",
          key: "unit"
        },
        {
          title: "开始时间",
          key: "startTime"
        },
        {
          title: "结束时间",
          key: "endTime"
        }
      ],
      list: [], // 表格列表数据
      searchWord: "", // 搜索词语
      total: 0, // page总条数
      pageSize: 10, // 每页显示多少条
      current: 1, // 当前页
      searchWord2: "" // 点击搜索后存放的搜索词
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
      let params = {
        queryName: "",
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
      let params = {
        queryName: this.searchWord,
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
        queryName: this.searchWord2,
        rows: size,
        page: 1
      };
      this.pageSize = size;
      this.getWEDList(params);
    },
    /**
     * 改变页码时触发
     * @param {*Number 需要请求的页码} index
     * @author yezi 2019-08-02
     */
    changePage (index) {
      let params = {
        queryName: this.searchWord2,
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getWEDList(params);
    },
    /**
     * 获取列表数据，发送请求
     * @param {*Object 发送请求参数} params
     *        {queryName:"查询搜索词",rows:"每页显示的条数",page:"请求数据的页码"}
     * @author yezi 2019-08-02
     */
    getWEDList (params) {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/vistor/list?json",
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
    }
  }

};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
</style>
