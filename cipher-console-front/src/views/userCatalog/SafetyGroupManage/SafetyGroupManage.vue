<template>
  <div class="sgm tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="安全组名称"
                 v-model="queryName"
                 radius="half"
                 :search="true"
                 @handleClick="searchName" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="CreateGroup">创建安全组</myButton>
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
    <Modal v-model="showMadal2"
           @on-ok="submit"
           width="528"
           @on-cancel="cancel2"
           class="create-modal">
      <p slot="header">
        <span>创建新的安全组</span>
      </p>
      <div class="create-modal-content">
        <span style="font-size:14px">请输入安全组名称:</span>
        <myInput v-model="teamName"></myInput>
      </div>
    </Modal>
    <Modal v-model="showMadal"
           width="668"
           class="tree-transfer"
           @on-cancel="cancel">
      <p slot="header"
         class="header">
        <span>添加安全组用户</span>
        <span class="sub-title">({{treeTeamName}})</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="data"
                      :leftDataType="dataType"
                      @rightData="getRightData"
                      :placeholder="placeholder">
          <span slot="leftText">授权用户</span>
          <span slot="rightText">已授权用户</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                :loading="loading"
                :disabled="isDisable"
                @click="ok">保存</Button>
        <Button @click="reset"
                class="cancel__btn">重置</Button>
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
  // 引入穿梭框组件
  components: {
    TreeTransfer
  },
  data () {
    return {
      treeTeamName: "", // 穿梭框标题的安全组名称
      userIds: "",
      data2: [],
      data: [],
      dataType: 0,
      placeholder: "请输入用户或部门",
      showMadal2: false,
      id: 0,
      showMadal: false,
      isDisable: false,
      loading: false,
      teamName: "",
      modal1: false,
      queryName: "",
      curentPage: 1,
      total: 0,
      size: 10,
      accountStatus: 0,
      accountNumber: "",
      columns7: [
        {
          title: "安全组名称",
          key: "teamName",
          width: 200,
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.todetails(params.index);
                    }
                  }
                },
                this.data6[params.index].teamName
              )
            ]);
          }
        },
        {
          title: "安全组ID",
          key: "dsgTeamId",
          width: 200,
          renderHeader: (h, params) => {
            return h("div", [
              h("span", "安全组ID"),
              h("Tooltip", {
                props: {
                  placement: "right",
                  transfer: true
                }
              }, [
                h("span", {
                  slot: "content", // slot属性
                  style: {
                    display: "inline-block",
                    maxWidth: "120px",
                    whiteSpace: "normal",
                    wordBreak: "break-all"
                  }
                }, "安全组ID是系统随机生成的8位包含数字和字母的ID"),
                h("Icon", {
                  style: {
                    diaplay: "inline-block",
                    marginTop: "-2px",
                    marginLeft: "3px"
                    // color: "#2d8cf0"
                  },
                  props: {
                    type: "ios-information-circle",
                    size: "18"
                  }
                })
              ])
            ]);
          }
        },
        {
          title: "安全组应用权限",
          key: "applicationName",
          render: (h, params) => {
            return h("div", [
              h(
                "span",
                {
                  style: {
                    diaplay: "inline-block",
                    width: "100%",
                    overflow: "hidden",
                    textOverflow: "ellipsis",
                    whiteSpace: "nowrap"
                  },
                  domProps: {
                    title: this.data6[params.index].applicationName
                  }
                },
                this.data6[params.index].applicationName
              )
            ]);
          }
        },

        {
          title: "安全组人数",
          key: "teamNum",
          width: 200,
          render: (h, params) => {
            return h("div", [
              h("span", this.data6[params.index].teamNum + "人")
            ]);
          }
        },
        {
          title: "操作",
          key: "edit",
          width: 200,
          align: "center",
          render: (h, params) => {
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.addpeople(params.index);
                    }
                  }
                },
                "添加成员" + " "
              ),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.delete(params.index);
                    }
                  }
                },
                " " + "删除安全组"
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
    that.finddata(params);
  },

  methods: {
    // 穿梭框已选择的用户数据
    getRightData (data) {
      let userIdArr = [];
      data.forEach(item => {
        userIdArr.push(item.uuid);
      });
      this.userIds = userIdArr.join(",");
    },
    // 重置穿梭框初始化数据
    reset () {
      this.dataType = 0;
      this.init(this.id);
    },
    // 添加成员
    ok () {
      this.setUnderway();
      let params = {
        userIds: this.userIds,
        teamId: this.id
      };
      this.axios({
        method: "post",
        url: "/cipher/team/addUser",
        data: params
      })
        .then(res => {
          this.setCompleted();
          this.cancel();
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
          } else if (res.data.return_code === 0) {
            this.$Modal.error({
              title: "提示",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          this.setCompleted();
          console.log(error);
        });
    },
    // 设置进行中状态
    setUnderway () {
      this.loading = true;
      this.isDisable = true;
    },
    // 设置完成状态
    setCompleted () {
      this.loading = false;
      this.isDisable = false;
      this.showMadal = false;
    },
    // 创建安全组
    submit () {
      let that = this;
      if (!that.teamName.replace(/^ +| +$/g, "")) {
        that.$myMessage.error("安全组名称不能为空！");
        return;
      }
      let params = {
        teamName: that.teamName
      };
      this.axios({
        method: "post",
        url: "/cipher/team/addorUpdate",
        data: params
      })
        .then(function (res) {
          if (res.data.return_code === 1) {
            that.$myMessage.success(res.data.return_msg);
            let params = {
              rows: that.size,
              page: 1
            };
            that.finddata(params);
            that.teamName = "";
          } else {
            that.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancel2 () {
      this.showMadal2 = false;
    },

    cancel () {
      this.showMadal = false;
      this.dataType = 0;
      let params = {
        rows: this.size,
        page: 1
      };
      this.finddata(params);
    },
    CreateGroup () {
      this.showMadal2 = true;
    },
    skipNewUrl (url) {
      this.$router.push(url);
    },
    remove (index) {
      this.data6.splice(index, 1);
    },
    searchName () {
      let params = {
        rows: this.size,
        page: 1,
        sidx: "",
        sord: "asc",
        teamName: this.queryName
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/team/list?json",
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
        sidx: "",
        sord: "asc",
        teamName: this.queryName
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        sidx: "",
        sord: "asc",
        teamName: this.queryName
      };
      this.curentPage = index;
      this.finddata(params);
    },
    addpeople (index) {
      this.id = this.data6[index].id;
      this.init(this.id);
      this.treeTeamName = this.data6[index].teamName;
      this.showMadal = true;
    },
    init (id) {
      this.dataType = 1;
      let params = { id: id };
      this.axios({
        method: "post",
        url: "/cipher/team/getUser?json",
        data: params
      })
        .then(res => {
          this.data2 = res.data.msg.trees;
          this.data2 = this.$common.cloneAssemblyAssignData(
            res.data.msg.trees,
            cloneChangeKeys,
            true
          );
          this.data2 = this.$common.initSelect(this.data2);
          this.data = this.data2;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 删除安全组
    delete (index) {
      this.$myModal.confirm({
        title: "确定删除该安全组吗？",
        content: "删除后无法恢复，你还要继续吗？"
      }).then(async (val) => {
        this.dodelete(this.data6[index].id, index);
      }).catch(() => { });
    },
    dodelete (id, index) {
      let that = this;
      let params = {
        id: id
      };
      this.axios({
        method: "post",
        url: "/cipher/team/delete",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            that.$myMessage.success(res.data.return_msg);
            that.data6.splice(index, 1);
          } else if (res.data.return_code === 0) {
            that.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 跳转到详情页面
    todetails (index) {
      this.$router.push({
        name: "detail",
        query: {
          teamId: this.data6[index].id,
          teamName: this.data6[index].teamName,
          dsgTeamId: this.data6[index].dsgTeamId
        }
      });
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
</style>
