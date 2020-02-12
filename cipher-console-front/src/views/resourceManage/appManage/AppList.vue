<template>
  <div class="appManage-list tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入应用名称..."
                 v-model="searchAppName"
                 radius="half"
                 :search="true"
                 @handleClick="searchApplicatioName" />
      </div>
      <div class="tabled-header-btn">
        <myButton btnType="info"
                  @click="addApp">添加应用</myButton>
      </div>
    </div>
    <div class="tabled-table appManage-list-table">
      <Table :data="appList"
             :columns="appListTitle">
        <template slot-scope="{row}"
                  slot="applicationUrl">
          <img :src="row.applicationIconUrl? row.applicationIconUrl: imgTitle"
               width="60"
               style="border:1px solid #eee;border-radius:50%;vertical-align: middle" />
        </template>
        <template slot-scope="{row}"
                  slot="applicationName">
          <a @click="skipDetail(row.id)">{{row.applicationName}}</a>
        </template>
        <template slot-scope="{row}"
                  slot="userCount">{{row.userCount}}人</template>
        <template slot-scope="{row}"
                  slot="opration">
          <a @click="authrization(row.id,row.applicationName)"
             class="auth">授权</a>
          <a @click="onDeleteApp(row.id)">删除应用</a>
        </template>
      </Table>
    </div>
    <div class="tabled-page">
      <Page :total="total"
            :current="current"
            show-elevator
            show-sizer
            show-total
            :page-size="20"
            @on-change="changePage"
            @on-page-size-change="changePageSize"></Page>
    </div>
    <Modal v-model="showAuthMadal"
           @on-cancel="closeAuthModal"
           width="668"
           class="tree-transfer">
      <p slot="header"
         class="header">
        <span>授权</span>
        <span class="sub-title">({{applicatinName}})</span>
      </p>
      <myComplexTabs v-model="authType"
                     class="tabs"
                     @on-click="changetabs">
        <myPane label="用户"
                name="authUserTab">
          <TreeTransfer :leftTreeData="authUserData"
                        :leftDataType="dataType"
                        @rightData="getRightData"
                        placeholder="请输入用户或部门">
            <span slot="leftText">授权用户</span>
            <span slot="rightText">已授权用户</span>
          </TreeTransfer>
        </myPane>
        <myPane label="部门"
                name="authGroupTab">
          <TreeTransfer :leftTreeData="authGroupData"
                        :leftDataType="dataType"
                        @rightData="getRightData"
                        placeholder="请输入部门">
            <span slot="leftText">授权部门</span>
            <span slot="rightText">已授权部门</span>
          </TreeTransfer>
        </myPane>
        <myPane label="安全组"
                name="authTeamTab">
          <TreeTransfer :leftTreeData="authTeamData"
                        :leftDataType="dataType"
                        @rightData="getRightData"
                        placeholder="请输入安全组">
            <span slot="leftText">授权安全组</span>
            <span slot="rightText">已授权安全组</span>
          </TreeTransfer>
        </myPane>
      </myComplexTabs>
      <div slot="footer"
           class="textAlain">
        <Button type="info"
                @click="saveAuth">保存</Button>
        <Button @click="resetAuth">重置</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
const cloneChangeKeys = { // 复制数组时触发的方法
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
  name: "ApplicationList",
  components: {
    TreeTransfer
  },
  data () {
    return {
      searchAppName: "", // 应用名称搜索词
      authType: "authUserTab", // 弹出授权页面中tab的绑定值,默认显示用户授权页
      showAuthMadal: false, // 授权弹出框是否显示
      dataType: 0, // 穿梭框类型
      authUserData: [], // 穿梭框用户树 数据
      authGroupData: [], // 穿梭框部门树 数据
      authTeamData: [], // 穿梭框安全组树 数据
      applicationType: 0, // 应用类型 1，安全代理 2密码代填  3腾讯企业邮箱 4 标准api接口
      applicationId: 0, // 应用id
      applicatinName: "", // 应用名称
      current: 1,
      pageSize: 20,
      total: 1,
      imgTitle: "/static/img/img-01.png", // 默认图片地址
      appList: [],
      appListTitle: [
        {
          title: "序号",
          key: "applicationIndex",
          align: "center"
        },
        {
          title: "应用图标",
          slot: "applicationUrl",
          align: "center"
        },
        {
          title: "应用名称",
          slot: "applicationName",
          align: "center"
        },
        {
          title: "授权用户",
          slot: "userCount",
          align: "center"
        },
        {
          title: "操作",
          slot: "opration",
          align: "center"
        }
      ]
    };
  },
  mounted () {
    let params = {
      rows: this.pageSize,
      page: 1
    };
    this.getList(params);
  },

  computed: {
    ...mapState["appId"]
  },
  methods: {
    /**
     * 点击搜索应用名称的方法
     * @param {*void}
     * @author yezi 2019-07-30
     */
    searchApplicatioName () {
      let params = {
        rows: this.pageSize, // 一页显示返回条数
        page: 1, // 当前页面
        sidx: "", // 排序key值
        sord: "asc", // 排序方式：升序或降序
        applicationName: this.searchAppName // 搜索词
      };
      this.getList(params);
    },
    /**
     * 分页pageSize每页条数变化触发的方法
     * @param {*Number 每页显示条数} pageSize
     * @author yezi 2019-07-30
     */
    changePageSize (pageSize) {
      let params = {
        rows: pageSize,
        page: 1,
        sidx: "",
        sord: "asc",
        applicationName: this.searchAppName
      };
      this.getList(params);
      this.pageSize = pageSize;
    },
    /**
     * 当前页发生变化时触发
     * @param {*Number 当前页} index
     * @author yezi 2019-07-30
     */
    changePage (index) {
      let params = {
        rows: this.pageSize,
        page: index,
        sidx: "",
        sord: "asc",
        applicationName: this.searchAppName
      };
      this.current = index;
      this.getList(params);
    },
    /**
     * 发送ajax，请求应用列表数据
     * @param {*Object应用列表请求参数} params
     *        {rows:"*Number一页显示返回条数"，page:"*Number当前页面",sidx:"*String排序key值",
     *        sord:"*String排序方式：升序asc或降序desc",applicationName:"*String搜索词"}
     * @author yezi 2019-07-30
     */
    getList (params) {
      this.axios({
        method: "post",
        url: "/cipher/app/list?json",
        data: params
      })
        .then(res => {
          this.appList = res.data.rows;
          this.total = res.data.total;
        })
        .catch(error => {
          console.log(error);
        });
    },
    /**
     * 点击添加应用，跳转到添加应用页面
     * @param {*void}
     * @author yezi 2019-07-30
     */
    addApp () {
      this.$router.push({ name: "appAddList" });
    },
    /**
     * 点击授权框内重置按钮 执行的方法，返回上次保存的数据
     * @param {*void}
     * @author yezi 2019-07-30
     */
    resetAuth () {
      this.dataType = 0;
      switch (this.authType) {
        case "authUserTab":
          this.changeAuthUser();
          break;
        case "authGroupTab":
          this.changeAuthGroup();
          break;
        case "authTeamTab":
          this.changeAuthTeam();
          break;
        default:
          break;
      }
    },
    /**
     * 关闭授权穿梭框，重新刷新应用列表
     * @param {*void}
     * @author yezi 2019-07-30
     */
    closeAuthModal () {
      this.showAuthMadal = false;
      this.dataType = 0;
      let params = {
        rows: this.pageSize,
        page: 1
      };
      this.getList(params);
    },
    /**
     * 保存穿梭框授权，保存当前teb中的数据
     * @param {*void}
     * @author yezi 2019-07-30
     */
    saveAuth () {
      switch (this.authType) {
        case "authUserTab": // 授权到用户
          this.authUser();
          break;
        case "authGroupTab": // 授权到部门
          this.authGroup();
          break;
        case "authTeamTab": // 授权到安全组
          this.authTeam();
          break;
        default:
          break;
      }
    },
    /**
     * 授权到用户
     * @param {*void}
     * @author yezi 2019-07-30
     */
    authUser () {
      let accountNumbers = [];
      let uuids = [];
      for (let i = 0; i < this.AuthData.length; i++) {
        accountNumbers.push(this.AuthData[i].accountNumber);
        uuids.push(this.AuthData[i].uuid);
      }
      let account = accountNumbers.join(",");
      uuids = uuids.join(",");

      let params = {
        applicationId: this.applicationId,
        accountNumber: account,
        uuid: uuids
      };
      this.axios({
        method: "post",
        url: "/cipher/appAuth/addUser",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            this.$myModal.success({
              title: "授权成功",
              content: "授权用户成功"
            }).then(() => { this.changeAuthUser(); });
          } else {
            this.$myModal.error({
              title: "授权失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(() => {
          // console.log(error);
        });
    },
    /**
    * 授权到部门
    * @param {*void}
    * @author yezi 2019-07-30
    */
    authGroup () {
      let groupIds = [];
      for (let i = 0; i < this.AuthData.length; i++) {
        groupIds.push(this.AuthData[i].groupId);
      }
      let group = groupIds.join(",");
      let params = {
        applicationId: this.applicationId,
        groupId: group
      };
      this.axios({
        method: "post",
        url: "/cipher/appAuth/addGroup",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            this.$myModal.success({
              title: "授权成功",
              content: "授权部门成功"
            }).then(() => { this.changeAuthGroup(); });
          } else {
            this.$myModal.error({
              title: "授权失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(() => {
          // console.log(error);
        });
    },
    /**
     * 授权到安全组
     * @param {*void}
     * @author yezi 2019-07-30
     */
    authTeam () {
      let teamIds = [];
      for (let i = 0; i < this.AuthData.length; i++) {
        teamIds.push(this.AuthData[i].id);
      }
      let teamId = teamIds.join(",");
      let params = {
        applicationId: this.applicationId,
        teamId: teamId
      };
      this.axios({
        method: "post",
        url: "/cipher/appAuth/addTeam",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            this.$myModal.success({
              title: "授权成功",
              content: "授权安全组成功"
            }).then(() => {
              this.changeAuthTeam();
            });
          } else {
            this.$myModal.error({
              title: "授权失败",
              content: "请稍后重试"
            });
          }
        })
        .catch(() => {
          // console.log(error);
        });
    },
    /**
      * 获取右侧数据的回调函数
      * @param {*Array 组件TreeTransfer的回调函数返回，穿梭框右侧数据} data
      * @author yezi 2019-07-30
      */
    getRightData (data) {
      this.AuthData = data;
    },
    /**
     * 点击切换授权穿梭框tab执行的方法
     * @param {*String 用户、部门、安全组三种tab} name
     * @author yezi 2019-07-30
     */
    changetabs (name) {
      if (name === "authUserTab") { // 用户
        this.changeAuthUser();
      } else if (name === "authGroupTab") { // 部门
        this.changeAuthGroup();
      } else if (name === "authTeamTab") { // 安全组
        this.changeAuthTeam();
      }
    },
    /**
     *  获取用户树的方法
     * @param {*void}
     * @author yezi 2019-07-30
     */
    changeAuthUser () {
      this.dataType = 1;
      let params = { applicationId: this.applicationId };
      this.axios({
        method: "post",
        url: "/cipher/app/authorizationUser",
        data: params
      })
        .then(res => {
          let tempArray = this.$common.cloneAssemblyAssignData(res.data.trees, cloneChangeKeys, true);
          this.authUserData = this.$common.initSelect(tempArray);
        })
        .catch(() => {
          // console.log(error);
        });
    },
    /**
     * 获取部门树的方法
     * @param {*void}
     * @author yezi 2019-07-30
     */
    changeAuthGroup () {
      this.dataType = 4;
      let params = { applicationId: this.applicationId };
      this.axios({
        method: "post",
        url: "/cipher/app/getAuthGroup",
        data: params
      })
        .then(res => {
          let tempArray = this.$common.cloneAssemblyAssignData(res.data.trees, cloneChangeKeys, true);
          this.authGroupData = this.$common.initSelect(tempArray);
        })
        .catch(() => {
          // console.log(error);
        });
    },
    /**
     * 获取安全组树的方法
     * @param {*void}
     * @author yezi 2019-07-30
     */
    changeAuthTeam () {
      this.dataType = 3;
      let params = { applicationId: this.applicationId };
      this.axios({
        method: "post",
        url: "/cipher/app/getAuthTeam",
        data: params
      })
        .then(res => {
          this.authTeamData = res.data.msg.list;
        })
        .catch((error) => {
          console.log(error);
        });
    },
    /**
     * 跳转到应用详情
     * @param {*Number 每个应用的id} id
     * @author yezi 2019-07-30
     */
    skipDetail (id) {
      this.changeAppId(id);
      this.$router.push({ name: "appDetails" });
    },
    /**
    * 获取未授权的用户
    * @param {*Number 每个应用的id} id
    * @author yezi 2019-07-30
    */
    authrization (id, applicatinName) {
      this.showAuthMadal = true;
      this.applicationId = id;
      this.applicatinName = applicatinName;
      this.authType = "authUserTab";
      this.dataType = 1;
      this.changeAuthUser();
    },
    /**
     * 点击删除应用，弹出删除框
     * @param {*Number 每个应用的id} id
     * @author yezi 2019-07-30
     */
    onDeleteApp (id) {
      this.applicationId = id;
      this.$myModal.confirm({
        title: "确认删除该应用吗"
      }).then(async (val) => {
        this.deleteApp();
      }).catch(() => { });
    },
    /**
    * 请求ajax，删除应用
    * @author yezi 2019-07-30
    */
    deleteApp () {
      this.axios({
        method: "post",
        url: "/cipher/app/delApplication",
        data: { id: this.applicationId }
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$myMessage.success("删除成功!");
            // iview 当前页和总条数有延时，删除数据后，显示还是删除当前数据前的数据，需要处理
            let totalPage = Math.ceil((this.total - 1) / this.pageSize); // 当前总页数
            this.current = totalPage < this.current ? totalPage : this.current;
            let params = {
              rows: this.pageSize,
              page: this.current,
              sidx: "",
              sord: "asc",
              applicationName: this.searchAppName
            };
            this.getList(params);
          } else {
            this.$myModal.error("删除失败");
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    ...mapMutations(["changeAppId"])
  }
};
</script>

 <style  lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/common.less";
@import "~@/assets/styles/modal.less";
/deep/ .ivu-table-wrapper table tbody tr td {
  height: 70px;
}
</style>
