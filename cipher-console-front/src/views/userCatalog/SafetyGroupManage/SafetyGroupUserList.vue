  <template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 radius="half"
                 :search="true"
                 v-model="queryName"
                 @handleClick="queryNamebtn" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="addpeople">添加成员</myButton>
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
    <Modal v-model="showMadal"
           width="668"
           class="tree-transfer">
      <p slot="header"
         class="header">
        <span class="title">添加安全组用户</span>
        <span class="sub-title">（{{teamName}}）</span>
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
                style="margin-left:10px;">重置</Button>
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
      dataBak: [], // 穿梭框重置数据备份
      placeholder: "",
      userIds: "",
      data: [],
      data2: [],
      dataType: 0,
      modal1: false,
      showMadal: false,
      isDisable: false,
      loading: false,
      teamName: "",
      teamId: "",
      queryName: "",
      total: 0,
      curentPage: 1,
      size: 10,
      columns7: [
        {
          title: "姓名",
          key: "userName",
          align: "center",
          render: (h, params) => {
            return h("a", [
              h(
                "span",
                {
                  on: {
                    click: () => {
                      this.toUserDetail(params.index);
                    }
                  }
                },
                this.data6[params.index].userName
              )
            ]);
          }
        },
        {
          title: "主账号",
          key: "accountNumber",
          align: "center"
        },
        {
          title: "邮箱",
          key: "mail",
          align: "center"
        },
        {
          title: "状态",
          key: "status",
          align: "center"
        },
        {
          title: "操作",
          key: "edit",
          align: "center",
          render: (h, params) => {
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
                "移出安全组"
              )
            ]);
          }
        }
      ],
      data6: []
    };
  },
  created () {
    this.teamId = this.$route.query.teamId;
    this.teamName = this.$route.query.teamName;
  },
  mounted () {
    var that = this;
    let params = {
      rows: that.size,
      page: 1,
      teamId: that.teamId,
      sidx: "",
      sord: "asc"
    };
    that.finddata(params);
  },
  methods: {
    toUserDetail (index) {
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.data6[index].accountNumber, groupId: this.data6[index].groupId, uuid: this.data6[index].uuid }
      });
    },
    reset () {
      this.data = this.$common.clone(this.dataBak);
    },
    // 获取穿梭框已选择数据
    getRightData (data) {
      let userIdArr = [];
      data.forEach(item => {
        userIdArr.push(item.uuid);
      });
      this.userIds = userIdArr.join(",");
    },
    // 添加成员
    ok () {
      this.setUnderway();
      let params = {
        userIds: this.userIds,
        teamId: this.teamId
      };
      this.axios({
        method: "post",
        url: "/cipher/team/addUser",
        data: params
      })
        .then(res => {
          this.setCompleted();
          this.cancel2();
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
    // 初始化穿梭框数据
    init (teamId) {
      this.dataType = 1;
      let params = { id: teamId };
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
          this.dataBak = this.$common.clone(this.data);
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancel2 () {
      this.showMadal = false;
      this.dataType = 0;
      let params = {
        rows: this.size,
        page: 1,
        teamId: this.teamId,
        sidx: "",
        sord: "asc"
      };
      this.finddata(params);
    },
    queryNamebtn () {
      let that = this;
      let params = {
        rows: that.size,
        page: 1,
        queryName: that.queryName,
        sidx: "",
        sord: "asc",
        teamId: that.teamId
      };
      this.finddata(params);
    },
    finddata (params) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/team/userList",
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
        teamId: this.teamId,
        rows: size,
        page: 1,
        sidx: "",
        sord: "asc",
        queryName: this.queryName
      };
      this.finddata(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        teamId: this.teamId,
        rows: this.size,
        page: index,
        sidx: "",
        sord: "asc",
        queryName: this.queryName
      };
      this.curentPage = index;
      this.finddata(params);
    },
    // 弹出穿梭框
    addpeople () {
      this.init(this.teamId);
      this.showMadal = true;
    },
    delete (index) {
      var that = this;
      this.$myModal.confirm({
        title: "确定将此用户移除安全组吗？",
        content: "移除后将无法恢复，你还要继续吗？",
        confirmVal: "继续"
      }).then(async (val) => {
        let params = {
          teamId: that.teamId,
          accountNumber: that.data6[index].accountNumber,
          uuid: that.data6[index].uuid
        };
        this.axios({
          method: "post",
          url: "/cipher/team/deleteUserMap",
          data: params
        })
          .then(function (res) {
            if (res.data.return_code === 1) {
              that.$myMessage.success(res.data.return_msg);
              that.data6.splice(index, 1);
            }
          })
          .catch(error => {
            console.log(error);
          });
      }).catch(() => {
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
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
</style>
