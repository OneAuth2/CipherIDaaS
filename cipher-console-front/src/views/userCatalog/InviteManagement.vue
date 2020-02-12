<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="姓名,邮箱"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryUser" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="showCode">邀请码</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table :columns="columns1"
             :data="data1"></Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="curentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="pageSizeChange"></Page>
    <Modal v-model="createModal"
           width="528"
           class="create-modal">
      <p slot="header"
         class="invite-create-modal-header">
        <span>生成邀请码</span>
      </p>
      <div class="create-modal-content">
        <span>邀请码：</span>
        <myInput width="326px"
                 v-model="codelist"></myInput>
      </div>
      <div slot="footer"
           class="invite-create-modal-footer">
        <myButton btnType="info"
                  @click="createCode()">获取</myButton>
        <myButton @click="close()"
                  class="cancel__btn ">取消</myButton>
      </div>
    </Modal>
  </div>
</template>
<script>
export default {
  data () {
    return {
      codelist: "", // 已生成未使用的邀请码
      createModal: false,
      queryName: "",
      curentPage: 1,
      total: 0,
      size: 10,
      columns1: [
        {
          title: "姓名",
          key: "userName"
        },
        {
          title: "邮箱",
          key: "mail"
        },
        {
          title: "加入时间",
          key: "createTime"
        },
        {
          title: "加入方式",
          key: "invitCode",
          render: (h, params) => {
            if (this.data1[params.index].invitCode !== null) {
              return h("div", [
                h(
                  "span",
                  "邀请码:" + this.data1[params.index].invitCode
                )
              ]);
            } else {
              return h("div", [
                h(
                  "span",
                  "邀请链接加入")
              ]);
            }
          }
        }
      ],
      data1: []
    };
  },
  mounted () {
    let params = {
      rows: this.size,
      page: 1
    };
    this.finddata(params);
  },
  methods: {
    // 生成邀请码
    createCode () {
      let that = this;
      let params = {
      };
      this.axios({
        method: "post",
        url: "/cipher/invit/create",
        data: params
      })
        .then(function (res) {
          if (res.status === 200) {
            that.codelist = res.data.codelist.join("");
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    close () {
      this.createModal = false;
    },
    // 获取已经生成未使用的邀请码
    getOldCode () {
      let that = this;
      let params = {
      };
      this.axios({
        method: "post",
        url: "/cipher/invit/oldCode",
        data: params
      })
        .then(function (res) {
          that.codelist = res.data.codelist.join("");
        })
        .catch(error => {
          console.log(error);
        });
    },
    showCode () {
      this.getOldCode();

      this.createModal = true;
    },
    queryUser () {
      let params = {
        queryName: this.queryName,
        rows: this.size,
        page: 1
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/invit/list?json",
        data: params
      })
        .then(function (res) {
          that.data1 = res.data.rows;
          that.total = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    pageSizeChange (size) {
      let params = {
        queryName: this.queryName,
        rows: size,
        page: 1
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        queryName: this.queryName,
        rows: this.size,
        page: index
      };
      this.curentPage = index;
      this.finddata(params);
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/modal.less";
@import "~@/assets/styles/tableStyle.less";
</style>
