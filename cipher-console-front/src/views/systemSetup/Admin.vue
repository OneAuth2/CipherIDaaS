<template>
  <div class="ssAdmin tabled-wrap">
    <div class="tabled-header app-audit-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="queryAdminList" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="addAdmin">添加管理员</myButton>
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
          @on-page-size-change="changePageSize"></Page>
    <Modal v-model="showAddModal"
           width="668"
           class="tree-transfer"
           @on-cancelAddModal="cancelAddModal">
      <p slot="header">
        <span>添加管理员</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="data"
                      :leftDataType="dataType"
                      @rightData="getRightData"
                      :placeholder="placeholder">
          <span slot="leftText">选择要添加的管理员</span>
          <span slot="rightText">当前管理员</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                :loading="loading"
                :disabled="isDisable"
                @click="ok">保存</Button>
        <Button @click="reset">重置</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
const cloneChangeKeys = {
  title: {
    target: "title",
    original: "text"
  },
  children: {
    target: "children",
    original: "nodes"
  }
};
export default {
  components: {
    TreeTransfer
  },
  data () {
    return {
      accountNumbers: "",
      uuids: "",
      placeholder: "",
      dataType: 0,
      data: [],
      data2: [],
      showAddModal: false,
      isDisable: false,
      loading: false,
      queryName: "",
      curentPage: 1,
      total: 0,
      size: 10,
      accountStatus: 0,
      accountNumber: "",
      columns7: [
        {
          title: "姓名",
          key: "userName",
          width: 200,
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.todetail(params.index);
                    }
                  }
                },
                this.data6[params.index].userName
              )
            ]);
          }
        },

        {
          title: "邮箱",
          key: "mail"
        },
        {
          title: "部门",
          key: "groupName"
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
                  on: {
                    click: () => {
                      this.handleDelete(params.index);
                    }
                  }
                },
                "移除管理员"
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
      page: 1,
      sidx: "",
      sord: "asc"
    };
    that.finddata(params);
  },

  methods: {
    /**
     * 跳转到组织结构-用户详情页
     * @param {*Number 点击数据在table列表页面的行序} index
     * @author yezi 2019-08-01
     */
    todetail (index) {
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.data6[index].accountNumber, groupId: this.data6[index].groupId, uuid: this.data6[index].uuid }
      });
    },
    /**
    * 关闭添加管理员弹出框
    * @param {*void}
    * @author yezi 2019-08-01
    */
    cancelAddModal () {
      this.showAddModal = false;
      this.dataType = 0;
      let params = {
        rows: this.size,
        page: 1,
        sidx: "",
        sord: "asc"
      };
      this.finddata(params);
    },
    /**
    * 重置添加管理员弹出框数据
    * @param {*void}
    * @author yezi 2019-08-01
    */
    reset () {
      this.dataType = 0;
      this.init();
    },
    /**
     * 穿梭框回调函数，获取添加管理员穿梭框右边数据
     * @param {*Array 右边数据组成的数组} data
     * @author yezi 2019-08-01
     */
    getRightData (data) {
      let userAccountArr = []; let userIdArr = [];
      data.forEach(item => {
        userIdArr.push(item.uuid);
        userAccountArr.push(item.accountNumber);
      });
      this.uuids = userIdArr.join(",");
      this.accountNumbers = userAccountArr.join(",");
    },
    /**
     * 点击添加管理员弹出框 保存按钮，发送请求添加管理员
     * @param {*void}
     * @author yezi 2019-08-01
     */
    ok () {
      this.setUnderway();
      let params = {
        accountNumber: this.accountNumbers,
        uuid: this.uuids
      };
      this.axios({
        method: "post",
        url: "/cipher/user/adminAdd",
        data: params
      })
        .then(res => {
          this.setCompleted();
          this.cancelAddModal();
          if (res.data.code === 0) {
            this.$myMessage.success(res.data.msg);
          } else if (res.data.code === 1) {
            this.$myMessage.error(res.data.msg);
          }
        })
        .catch(error => {
          this.setCompleted();
          console.log(error);
        });
    },
    /**
     * 显示正在设置中动画
     * @param {*void}
     * @author yezi 2019-08-01
     */
    setUnderway () {
      this.loading = true;
      this.isDisable = true;
    },
    /**
     * 设置完成，关闭动画，关闭弹出框
     * @param {*void}
     * @author yezi 2019-08-01
     */
    setCompleted () {
      this.loading = false;
      this.isDisable = false;
      this.showAddModal = false;
    },
    /**
    * 初始化，获取穿梭框数据
    * @param {*void}
    * @author yezi 2019-08-01
    */
    init () {
      this.dataType = 1;
      let params = {};
      this.axios({
        method: "post",
        url: "/cipher/user/getAddAdmin",
        data: params
      })
        .then(res => {
          this.data2 = res.data.msg.trees;
          this.data2 = this.$common.cloneAssemblyAssignData(
            res.data.msg.trees,
            cloneChangeKeys, // copyDeepTreeData
            true
          );
          //   this.data2 = this.data2.trees
          this.data2 = this.$common.initSelect(this.data2);
          this.data = this.data2;
          this.placeholder = "搜索部门/姓名";
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 点击添加管理员按钮显示弹窗，并调用初始化数据函数
     * @param {*void}
     * @author yezi 2019-08-01
     */
    addAdmin () {
      this.init();
      this.showAddModal = true;
    },
    /**
     * 搜索管理员列表
     * @param {*void}
     * @author yezi 2019-08-01
     */
    queryAdminList () {
      let params = {
        rows: this.size,
        page: 1,
        sidx: "",
        sord: "asc",
        accountStatus: 0,
        queryName: this.queryName
      };
      this.finddata(params);
    },
    /**
     * 获取管理员列表数据
     * @param {*void}
     * @author yezi 2019-08-01
     */
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/user/userAdmin",
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
    /**
     * 改变一页显示条数时触发
     * @param {*Number 一页显示的条数} size
     * @author yezi 2019-08-01
     */
    changePageSize (size) {
      let params = {
        rows: size,
        page: 1,
        sidx: "",
        sord: "asc",
        queryName: this.queryName
      };
      this.finddata(params);
      this.size = size;
    },
    /**
     * 改变页码触发
     * @param {*Number 当前页面} index
     * @author yezi 2019-08-01
     */
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        sidx: "",
        sord: "asc",
        queryName: this.queryName
      };
      this.curentPage = index;
      this.finddata(params);
    },
    /**
      * 点击移除管理员弹出该确认弹窗
      * @param {*Number 删除数据所在table列表所在的行序} index
      * @author yezi 2019-10-10
      */
    handleDelete (index) {
      this.$myModal.confirm({
        title: "确定要移除该管理员？"
      }).then(async (val) => {
        this.deleteAdmin(index);
      }).catch(() => { });
    },
    /**
     * 删除管理员
     * @param {*Number 删除数据所在table列表所在的行序} index
     * @author yezi 2019-08-01
     */
    deleteAdmin (index) {
      let that = this;
      let params = {
        accountNumber: that.data6[index].accountNumber,
        uuid: that.data6[index].uuid
      };
      this.axios({
        method: "post",
        url: "/cipher/user/deleteAdmin",
        data: params
      })
        .then(res => {
          if (res.data.return_code === "1") {
            this.$myModal.success({
              title: "操作成功",
              content: res.data.msg
            });
            let params = {
              rows: that.size,
              page: 1,
              sidx: "",
              sord: "asc"
            };
            this.finddata(params);
          } else {
            that.$myModal.error({
              title: "操作失败",
              content: "请稍后重试"
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

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
</style>
