<template>
  <div class="tabled-wrap">
    <div class="tabled-table org-app-list-table">
      <Table border
             :columns="columns7"
             :data="appListData.rows"></Table>
    </div>
    <Page class="tabled-page"
          :total="appListData.records"
          :current="curentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="pageSizeChange"></Page>
    <Modal v-model="modal1"
           width="400">
      <p slot="header"
         class="header">
        <span>配置从账号</span>
        <span class="sub-title">（{{applicationName}}）</span>
      </p>
      <Form :label-width="80">
        <FormItem label="从账号："><Input v-model.trim="subAccount"
                 v-if="isSubConfig" />
          <Input v-model.trim="subAccount"
                 v-else
                 readonly />
        </FormItem>
        <FormItem label="密码："><Input v-model.trim="password"
                 v-if="isSubPwdConfig" />
          <Input v-model.trim="password"
                 v-else
                 readonly />
        </FormItem>
      </Form>
      <p slot="footer"
         class="footer">
        <Button type="primary"
                @click="ok()">确定</Button>
        <Button @click="modal1=false"
                style="margin-left: 8px">取消</Button>
      </p>
    </Modal>
  </div>
</template>

<script>
import { mapMutations } from "vuex";
export default {
  props: {
    userName: {
      type: String,
      required: true
    }
  },
  data () {
    return {
      accountNumber: "",
      uuid: 0,
      groupId: 0,
      id: 0,
      applicationName: "",
      appClientId: "",
      subAccount: "",
      password: "",
      modal1: false,
      modal2: false,
      tableh: "",
      appListData: {},
      curentPage: 1,
      size: 10,
      total: 0,
      ip: "",
      username: "",
      isSubConfig: true, // 从账号是否可配
      isSubPwdConfig: true, // 从账号密码是否可配
      columns7: [
        {
          title: "应用名称",
          key: "applicationName",
          render: (h, params) => {
            return h("a", {
              on: {
                click: () => {
                  this.changeAppId(params.row.id);
                  this.$router.push({ name: "appDetails" });
                }
              }
            }, params.row.applicationName);
          }
        },
        {
          title: "应用子账号",
          key: "subAccount"
        },
        {
          title: "授权路径",
          key: "pathInfo",
          render: (h, params) => {
            let pathInfo = params.row.pathInfo;
            let name = "个人授权";
            const isDeleteAuth = params.row.pathInfo === null;
            if (pathInfo !== null) {
              let isGroupInfo = pathInfo.groupList !== null;
              let grouInfo = pathInfo.groupList;
              name = isGroupInfo ? grouInfo.parentPathName.substring(0, grouInfo.parentPathName.length - 1) : pathInfo.teamList.teamName;
            }

            return h("a", {
              class: { normal: isDeleteAuth },
              on: {
                click: () => {
                  this.skip(pathInfo);
                }
              }
            }, name);
          }
        },
        {
          title: "同步记录",
          key: "isSyschronTime",
          render: (h, params) => {
            let isSyschronTime = params.row.isSyschronTime || "未同步";
            return h("span", {
            }, isSyschronTime);
          }
        },
        {
          title: "操作",
          key: "edit",
          width: 300,
          align: "center",
          render: (h, params) => {
            const isDeleteAuth = params.row.pathInfo === null;
            let synchron = params.row.isSynchron ? "" : "同步";
            return h("span", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      synchron && this.handleSync(params.index);
                    }
                  }
                },
                synchron
              ),
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.setAccountNumber(params.index);
                    }
                  }
                },
                "配置从账号"
              ),
              h(
                "a",
                {
                  class: { disabled: !isDeleteAuth },
                  on: {
                    click: () => {
                      this.deleteAuth(params.index);
                    }
                  }
                },
                "取消授权"
              )
            ]);
          }
        }
      ]
    };
  },
  created () {
    this.accountNumber = this.$route.query.accountNumber;
    this.uuid = this.$route.query.uuid;
    this.groupId = this.$route.query.groupId;
  },
  mounted () {
    let params = {
      accountNumber: this.accountNumber,
      uuid: this.uuid,
      groupId: this.groupId,
      rows: this.size,
      page: 1
    };
    this.getUserAppList(params);
  },
  methods: {
    deleteappAuth () {
      let params = {
        applicationId: this.id,
        accountNumber: this.accountNumber,
        uuid: this.uuid
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/appAuth/deleteUserAuth"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.error(res.data.return_msg);
            return;
          }
          if (
            res.data.return_code === 1 &&
            res.data.depatment.length === 0 &&
            res.data.teams.length === 0
          ) {
            this.$Modal.success({
              title: "操作成功",
              content: "取消授权成功"
            });
          } else if (
            res.data.return_code === 1 &&
            res.data.depatment.length > 0 &&
            res.data.teams.length > 0
          ) {
            let depatment = [];
            let teams = [];
            depatment = res.data.depatment;
            teams = res.data.teams;
            let depatmentName = "";
            let teamsName = "";
            for (let i = 0; i < depatment.length; i++) {
              depatmentName += "[" + depatment[i].groupName + "]";
            }
            for (let i = 0; i < teams.length; i++) {
              teamsName += "[" + teams[i].teamName + "]";
            }

            this.$Modal.error({
              title: "取消授权失败",
              content:
                "该应用在" +
                depatmentName +
                "部门以及" +
                teamsName +
                "安全组下。"
            });
          } else if (
            res.data.return_code === 1 &&
            res.data.depatment.length > 0 &&
            res.data.teams.length === 0
          ) {
            let depatment = [];
            depatment = res.data.depatment;

            let depatmentName = "";
            for (let i = 0; i < depatment.length; i++) {
              depatmentName += "[" + depatment[i].groupName + "]";
            }

            this.$Modal.error({
              title: "取消授权失败",
              content: "该应用在" + depatmentName + "部门下。"
            });
          } else if (
            res.data.return_code === 1 &&
            res.data.depatment.length === 0 &&
            res.data.teams.length > 0
          ) {
            let teams = [];

            teams = res.data.teams;
            let teamsName = "";
            for (let i = 0; i < teams.length; i++) {
              teamsName += "[" + teams[i].teamName + "]";
            }
            this.$Modal.error({
              title: "取消授权失败",
              content: "该应用在" + teamsName + "安全组下。"
            });
          }
          let params = {
            accountNumber: this.accountNumber,
            uuid: this.uuid,
            groupId: this.groupId,
            rows: this.size,
            page: 1
          };
          this.getUserAppList(params);
        })
        .catch(error => {
          console.log(error);
        });
    },
    deleteAuth (index) {
      this.applicationName = this.appListData.rows[index].applicationName;
      this.id = this.appListData.rows[index].id;
      this.$myModal.confirm({
        title: "取消授权",
        content: "确认取消该用户的" + this.applicationName + "权限吗?"
      }).then(async (val) => {
        this.deleteappAuth();
      }).catch(() => { });
    },
    ok () {
      let params = {
        appClientId: this.appClientId,
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        subAccount: this.subAccount,
        password: this.password
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/subdown/dowmSingleSubAccount"
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$Modal.success({
              title: "提示",
              content: res.data.return_msg + res.data.successed + "条，失败" + res.data.fail + "条"
            });
            let params = {
              accountNumber: this.accountNumber,
              uuid: this.uuid,
              groupId: this.groupId,
              rows: this.size,
              page: this.curentPage
            };
            this.getUserAppList(params);
          } else {
            this.$Modal.error({
              title: "同步失败",
              content: res.data.return_msg
            });
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    setAccountNumber (index) {
      this.modal1 = true;
      let rule = this.appListData.rows[index].subAccountRuleInfo;
      if (rule !== null) {
        if (rule.assManual === 1) { // 从账号不是手动配置
          this.isSubConfig = false;
        }
        if (rule.assPwdManual === 1) { // 从账号密码不是手动配置
          this.isSubPwdConfig = false;
        }
      }

      this.appClientId = this.appListData.rows[index].applicationId;
      this.applicationName = this.appListData.rows[index].applicationName;
      this.findsub(index);
    },
    findsub (index) {
      this.subAccount = "";
      this.password = "";
      let params = {
        appClientId: this.appClientId,
        accountNumber: this.accountNumber,
        uuid: this.uuid
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/subdown/recomendSubAccount"
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.subAccount = res.data.userName;
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    getUserAppList (params) {
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/userMsg/getSublist?json"
      })
        .then(res => {
          this.appListData = res.data;
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    pageSizeChange (size) {
      let params = {
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        groupId: this.groupId,
        page: 1,
        rows: size
      };
      this.getUserAppList(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        groupId: this.groupId,
        page: index,
        rows: this.size
      };
      this.curentPage = index;
      this.getUserAppList(params);
    },
    ...mapMutations(["changeGroup"]),
    skip (pathInfo) {
      if (pathInfo.groupList !== null) { // 工作部门存在，跳到工作部门
        this.toGroupList(pathInfo.groupList);
      } else { // 工作部门不存在，跳到安全组
        this.toTeamDetail(pathInfo.teamList);
      }
    },
    // 跳到工作部门
    toGroupList (data) {
      this.$router.push({
        path: "/userCatalogOS"
      });
      this.changeGroup(data);
    },
    // 跳转到安全组详情
    toTeamDetail (data) {
      this.$router.push({
        path: "/userCatalogSGM/detail",
        query: {
          teamId: data.id,
          teamName: data.teamName,
          dsgTeamId: data.dsgTeamId
        }
      });
    },
    /**
     * 执行同步操作
     *
     */
    handleSync (index) {
      let params = {
        appClientId: this.appListData.rows[index].applicationId,
        userIds: this.uuid
      };
      this.axios({
        method: "post",
        url: "/cipher/subdown/downSelectsubAccount",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.returnDemo = res.data;
            this.$myModal.success({
              title: "同步成功",
              content: `成功${this.returnDemo.successed}条,失败${
                this.returnDemo.fail}条,${this.returnDemo.return_msg}`
            });
            this.changePage(this.curentPage);
          } else {
            this.$myModal.error({
              title: "同步失败",
              content: res.data.return_msg
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
    ...mapMutations(["changeGroup", "changeAppId"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.org-app-list-table {
  height: calc(~"100% - @{footerHeight}");
}
</style>
