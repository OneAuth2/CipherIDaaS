<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="searchAppInput"
                 radius="half"
                 :search="true"
                 @handleClick="searchApplicatioName" />
      </div>
      <div class="tabled-header-right">
        <myButton @click="authrization()">授权</myButton>
        <template v-if="isShowSyn">
          <myButton @click="synSelectSubAccount">同步</myButton>
          <mySelect :dataList="statusList"
                    showString="选择"
                    v-model="synStatus"
                    @on-change="changeSynStatus" />
        </template>
        <Dropdown trigger="click">
          <myDropBtn>更多操作</myDropBtn>
          <DropdownMenu slot="list">
            <DropdownItem v-if="isShowSyn"
                          @click.native="synAllNumber">全部同步</DropdownItem>
            <DropdownItem>
              <Upload ref="upload"
                      :show-upload-list="false"
                      :on-success="handleSuccess"
                      :on-error="handleError"
                      :format="['xls','xlsx','docx']"
                      :on-format-error="handleFormatError"
                      :before-upload="handleBeforeUpload"
                      type="drag"
                      :data="{applicationId:appId}"
                      action="/cipher/subaccount/upload"
                      class="uploadClass">导入应用从账号</Upload>
            </DropdownItem>
            <DropdownItem>
              <p @click="sampleDownload">下载导入样表</p>
            </DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
    </div>
    <div class="tabled-table">
      <Table :data="tableData"
             border
             :columns="getTableColumns"
             ref="selection"
             @on-selection-change="handleSelectRow()">
        <template slot-scope="{row}"
                  slot="sonAccountNumber">
          <div v-if="row.sonAccountNumber===null">未下发或配置从账号</div>
          <div v-else>{{row.sonAccountNumber}}</div>
        </template>
      </Table>
    </div>
    <Page :total="total"
          :current="currentPage"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="pageSizeChange"
          class="tabled-page"></Page>
    <!-- 配置从账号弹窗 -->
    <Modal title="demo"
           v-model="showSubConfigModal"
           class="modal"
           @on-ok="confimSubConfig()">
      <p slot="header">
        <span>配置从账号</span>
        <span class="sub-title">{{info.userName}}({{info.accountNumber}})</span>
      </p>
      <div class="addApplication">
        <Form ref="formValidate"
              :model="formValidate"
              :label-width="100">
          <Row>
            <Col span="24"
                 class="fromInput">
            <FormItem label="从账号："
                      class="appInputDemo">
              <Input v-model.trim="formValidate.sonAccountNumber"
                     class="inputdemo"
                     style="width:200px"
                     v-if="isSubConfig"></Input>
              <Input v-model.trim="formValidate.sonAccountNumber"
                     class="inputdemo"
                     style="width:200px"
                     v-else
                     readonly></Input>
            </FormItem>
            <FormItem label="从账号密码："
                      class="appInputDemo"
                      v-if="applicationType!==7">
              <Input v-model.trim="formValidate.password"
                     class="inputdemo"
                     style="width:200px"
                     type="password"
                     v-if="isSubPwdConfig"></Input>
              <Input v-model.trim="formValidate.password"
                     class="inputdemo"
                     style="width:200px"
                     type="password"
                     readonly
                     v-else></Input>
            </FormItem>
            </Col>
          </Row>
        </Form>
      </div>
    </Modal>
    <Modal v-model="showAuthMadal"
           @on-cancel="closeAuthModal"
           width="668"
           class="tree-transfer">
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
    <!-- 同步确认框 -->
    <Modal v-model="showSynModal"
           :footer-hide="true"
           width="360"
           class='synModal'>
      <div class="header">
        <Icon type="ios-help-circle" />
        <span>是否同步</span>
      </div>
      <div class="center"><span class="simpleModalContent">将同步{{length}}
          条账号数据，点击「确定」继续操作。</span><span> 你还要继续吗？</span></div>
      <div class="footer">
        <Button type="primary"
                @click="cofirmSynModal">确定</Button>
        <Button @click="showSynModal=false">取消</Button>
      </div>
    </Modal>
    <!-- 图片上传进度条 -->
    <div v-for="(item,index) in uploadList"
         :key="index">
      <FixedCircle v-if="item.showProgress"
                   :percent="item.percentage"
                   hide-info></FixedCircle>
    </div>
  </div>
</template>
<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
import { mapState, mapMutations } from "vuex";
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
  /**
   * 同步从账号的界面
   */
  name: "User",
  components: {
    TreeTransfer
  },
  props: ["appInfoData"],
  data () {
    return {
      appData: {}, // 父组件传递的数据
      applicationType: "", // app类型
      appSysId: "", // 标准应用id
      isSubConfig: true, // 从账号是否可配
      isSubPwdConfig: true, // 从账号密码是否可配
      synStatus: "0", // 同步状态
      showAuthMadal: false, // 授权弹出框是否显示
      authType: "authUserTab", // 弹出授权页面中tab的绑定值,默认显示用户授权页
      authUserData: [], // 穿梭框用户树 数据
      authGroupData: [], // 穿梭框部门树 数据
      authTeamData: [], // 穿梭框安全组树 数据
      dataType: 0, // 穿梭框的类型的数据
      secret: "", // 该属性是点击同步的时候，后端接口返回的secret  带着该secret去同步数据
      info: {
        accountNumber: "",
        userName: "",
        uuid: ""
      }, // 点击配置从账号时后台返回的数据
      uploadData: {}, // 上传的数据
      accountNumber: "", // 主账号  //
      uuids: "", // 主账号id
      showSubConfigModal: false, // 配置从账号弹窗是否显示
      accountNumbers: "", // 主账号的字符串拼接
      showSynModal: false, // 同步确认框显示与否
      statusList: [
        { value: 0, label: "全部" },
        { value: 1, label: "已同步" },
        { value: 2, label: "未同步" }
      ],
      isDownsubAccount: false, // 同步选择的类型，是否同步所有子账号
      appClientId: "", // app的clientId
      length: "", // 同步条数
      selection: [], // 保存从账号的信息
      formValidate: {
        sonAccountNumber: "",
        password: ""
      }, // 表单校验
      uploadList: [], // 文件上传列表
      size: 10, // 分页大小
      total: 1, // 分页总数
      tableData: [], // table中的绑定数据
      searchAppInput: "",
      currentPage: 1,
      tableColumns: [
        {
          type: "selection",
          width: 60,
          align: "center"
        },
        {
          title: "姓名",
          key: "userName",
          width: 240,
          align: "center",
          render: (h, params) => {
            return h("a", {
              on: {
                click: () => {
                  this.toUserDetail(params.index);
                }
              }
            }, `${params.row.userName}(${params.row.accountNumber})`);
          }
        },
        {
          title: "子账号",
          slot: "sonAccountNumber",
          align: "center"
        },
        {
          title: "授权路径",
          key: "pathInfo",
          align: "center",
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
                  this.skipAuthPath(pathInfo);
                }
              }
            }, name);
          }
        },
        {
          title: "最近同步时间",
          key: "isSyschronTime",
          align: "center",
          render: (h, params) => {
            const synTime = params.row.isSyschronTime || "未同步";
            return h("div", synTime);
          }
        },
        {
          title: "操作",
          key: "edit",
          width: 180,
          align: "center",
          render: (h, params) => {
            const isDeleteAuth = params.row.pathInfo === null;
            return h("div", [
              h(
                "a",
                {
                  on: {
                    click: () => {
                      this.configSonAccount(params.row.accountNumber, params.row.userName, params.row.uuid);
                    }
                  }
                },
                "配置从账号"),
              h(
                "a",
                {
                  class: { disabled: !isDeleteAuth },
                  on: {
                    click: () => {
                      if (!isDeleteAuth) return;
                      this.cancelAuth(params.row.accountNumber, params.row.uuid);
                    }
                  }
                },
                "取消授权")
            ]);
          }
        }
      ]
    };
  },
  computed: {
    ...mapState(["appId"]),
    isShowSyn () {
      let appSysIdList = [1, 2, 5, 6, 7];
      return appSysIdList.includes(this.appSysId);
    },
    getTableColumns () {
      let tempArray = this.tableColumns;
      if (!this.isShowSyn) {
        tempArray = this.tableColumns.filter((item, index) => {
          return index !== 4;
        });
      }
      return tempArray;
    }
  },
  mounted () {
    this.appData = this.$props.appInfoData;
    this.init();
    this.uploadList = this.$refs.upload.fileList;
  },
  methods: {
    ...mapMutations(["changeGroup"]),
    /**
     * 应用列表 点击授权路径跳转
     * @param {*Object 跳转路径信息，从后台获取工作部门或安全组信息} pathInfo
     * @author yezi 2019-07-31
     */
    skipAuthPath (pathInfo) {
      if (pathInfo.groupList !== null) { // 工作部门存在，跳到工作部门
        this.toGroupList(pathInfo.groupList);
      } else { // 工作部门不存在，跳到安全组
        this.toTeamDetail(pathInfo.teamList);
      }
    },
    /**
     * 跳转到相应组织结构
     * @param {*Object 工作部门信息} data
     * @author yezi 2019-07-31
     */
    toGroupList (data) {
      this.$router.push({
        path: "/userCatalogOS"
      });
      this.changeGroup(data);
    },
    /**
     * 跳转到相应安全组
     * @param {*Object 安全组信息} data
     * @author yezi 2019-07-31
     */
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
     * 获取未授权的用户
     * @param {*void}
     * @author yezi 2019-07-31
     */
    authrization () {
      this.showAuthMadal = true;
      this.authType = "authUserTab";
      this.dataType = 1;
      this.changeAuthUser();
    },
    /**
     * 穿梭框的回调函数，获取右侧数据
     * @param {*Array 右侧数据} data
     * @author yezi 2019-07-31
     */
    getRightData (data) {
      this.AuthData = data;
    },
    /**
     * 关闭授权穿梭框的时候触发的方法
     * @param {*void}
     * @author yezi 2019-07-31
     */
    closeAuthModal () {
      this.showAuthMadal = false;
      this.dataType = 0;
      this.currentPage = 1;
      this.getUserList();
    },
    /**
     * 保存穿梭框授权，保存当前teb中的数据
     * @param {*void}
     * @author yezi 2019-07-31
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
     * @author yezi 2019-07-31
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
        applicationId: this.appId,
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
     * @author yezi 2019-07-31
     */
    authGroup () {
      let groupIds = [];
      for (let i = 0; i < this.AuthData.length; i++) {
        groupIds.push(this.AuthData[i].groupId);
      }
      let group = groupIds.join(",");
      let params = {
        applicationId: this.appId,
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
     * @author yezi 2019-07-31
     */
    authTeam () {
      let teamIds = [];
      for (let i = 0; i < this.AuthData.length; i++) {
        teamIds.push(this.AuthData[i].id);
      }
      let teamId = teamIds.join(",");
      let params = {
        applicationId: this.appId,
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
            }).then(() => { this.changeAuthTeam(); });
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
     * 获取用户树的方法
     * @param {*void}
     * @author yezi 2019-07-31
     */
    changeAuthUser () {
      this.dataType = 1;
      let params = { applicationId: this.appId };
      this.axios({
        method: "post",
        url: "/cipher/app/authorizationUser",
        data: params
      })
        .then(res => {
          let rawData = this.$common.cloneAssemblyAssignData(res.data.trees, cloneChangeKeys, true);
          this.authUserData = this.$common.initSelect(rawData);
        })
        .catch(() => {
          // console.log(error);
        });
    },

    /**
   * 获取部门树的方法
   * @param {*void}
   * @author yezi 2019-07-31
   */
    changeAuthGroup () {
      this.dataType = 4;
      let params = { applicationId: this.appId };
      this.axios({
        method: "post",
        url: "/cipher/app/getAuthGroup",
        data: params
      })
        .then(res => {
          let rawData = this.$common.cloneAssemblyAssignData(res.data.trees, cloneChangeKeys, true);
          this.authGroupData = this.$common.initSelect(rawData);
        })
        .catch(() => {
          // console.log(error);
        });
    },

    /**
     * 获取安全组树的方法
     * @param {*void}
     * @author yezi 2019-07-31
     */
    changeAuthTeam () {
      this.dataType = 3;
      let params = { applicationId: this.appId };
      this.axios({
        method: "post",
        url: "/cipher/app/getAuthTeam",
        data: params
      })
        .then(res => {
          this.authTeamData = res.data.msg.list;
        })
        .catch(() => {
          // console.log(error);
        });
    },

    /**
     * 点击授权中的授权到tab页触发的方法
     */
    changetabs (name) {
      if (name === "authUserTab") {
        this.changeAuthUser();
      } else if (name === "authGroupTab") {
        this.changeAuthGroup();
      } else if (name === "authTeamTab") {
        this.changeAuthTeam();
      }
    },
    /**
      * 点击授权框内重置按钮 执行的方法，返回上次保存的数据
      * @param {*void}
      * @author yezi 2019-07-31
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
     * 配置从账号
     * @param {*void}
     * @author yezi 2019-07-31
     */
    confimSubConfig () {
      if (
        this.formValidate.sonAccountNumber === null ||
        this.formValidate.sonAccountNumber === ""
      ) {
        this.$myModal.error({
          title: "错误",
          content: "从账号不能为空！"
        });
      } else if ((this.formValidate.password === null || this.formValidate.password === "") && this.isSubPwdConfig && this.applicationType !== 7) {
        this.$myModal.error({
          title: "错误",
          content: "从账号密码不能为空！"
        });
      } else {
        this.showSubConfigModal = false;
        let params = {
          appClientId: this.appClientId,
          accountNumber: this.info.accountNumber,
          uuid: this.info.uuid,
          subAccount: this.formValidate.sonAccountNumber,
          password: this.formValidate.password
        };
        this.axios({
          method: "post",
          url: "/cipher/subdown/dowmSingleSubAccount",
          data: params
        })
          .then(res => {
            if (res.data.return_code === 0) {
              this.$myModal.success({
                title: "配置成功",
                content:
                  res.data.return_msg +
                  res.data.successed +
                  "条，失败" +
                  res.data.fail +
                  "条"
              });
              this.currentPage = 1;
              this.getUserList();
            } else {
              this.$myModal.error({
                title: "配置失败",
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
      }
    },
    /**
     * 弹出取消授权框
     * @param {*String 用户账号} accountNumber
     * @param {*String 用户id} uuid
     * @author yezi 2019-07-31
     */
    cancelAuth (accountNumber, uuid) {
      this.accountNumber = accountNumber;
      this.uuid = uuid;
      this.$myModal.confirm({
        title: "取消授权",
        content: "确认取消该用户" + this.accountNumber + "的权限吗?"
      }).then(async (val) => {
        this.cancelAuthUser();
      }).catch(() => { });
    },
    /**
     * 确定取消授权，发送请求
     * @param {*String 用户账号} accountNumber
     * @param {*String 用户id} uuid
     * @author yezi 2019-07-31
     */
    cancelAuthUser () {
      let params = {
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        applicationId: this.appId
      };
      this.axios({
        method: "post",
        url: "/cipher/appAuth/deleteUserAuth",
        data: params
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
            this.$myModal.success({
              title: "操作成功",
              content: "取消授权成功"
            }).then(() => {
              // iview 当前页和总条数有延时，删除数据后，显示还是删除当前数据前的数据，需要处理
              let totalPage = Math.ceil((this.total - 1) / this.size); // 当前总页数
              this.currentPage = totalPage < this.currentPage ? totalPage : this.currentPage;
              this.getUserList();
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
              depatmentName += depatment[i].groupName + " ";
            }
            for (let i = 0; i < teams.length; i++) {
              teamsName += teams[i].teamName + " ";
            }

            this.$myModal.error({
              title: "取消授权失败",
              content:
                "该用户在" +
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
              depatmentName += depatment[i].groupName + " ";
            }

            this.$myModal.error({
              title: "取消授权失败",
              content: "该用户在" + depatmentName + "部门下。"
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
              teamsName += teams[i].teamName + " ";
            }
            this.$myModal.error({
              title: "取消授权失败",
              content: "该用户在" + teamsName + "安全组下。"
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
     * 样表下载 excel表单下载
     * @param {*void}
     * @author yezi 2019-07-31
     */
    sampleDownload () {
      window.location.href = "/cipher/download/subexport";
    },
    /**
     * 下载反馈信息 excel表单下载
     * @param {*void}
     * @author yezi 2019-07-31
     */
    downLoadFeedback () {
      location.href =
        "/cipher/subaccount/downloadReturnExcel?downloadSecret=" + this.secret;
    },
    /**
     * 上传文件前触发
     * @param {*void}
     * @author yezi 2019-07-31
     */
    handleBeforeUpload () {
      this.uploadData = { applicationId: this.appId };
    },
    /**
     * 上传文件成功之后返回信息
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleSuccess (res, file) {
      if (res.return_code === 1) {
        this.secret = res.secret;
        this.$myModal.confirm({
          title: "导入从账号成功",
          content: res.return_msg,
          confirmVal: "下载反馈"
        }).then(async (val) => {
          this.downLoadFeedback();
        }).catch(() => { });
      } else if (res.return_code === 0) {
        this.$myModal.error({
          title: "Excel上传失败",
          content: res.return_msg
        });
      }
    },
    /**
     * 上传文件失败时触发
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleError (error, file) {
      this.$myModal.error({
        title: "Excel上传失败",
        content: error
      });
    },
    /**
     * 上传文件格式错误触发
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleFormatError (file) {
      this.$Notice.warning({
        title: "请选择表格文件",
        desc: "文件不正确！ " + file.name + " 该文件不是表格文件"
      });
    },

    /**
     * 表格checked选中状态改变时触发，保存选中的数据
     * @param 详见iview
     * @author yezi 2019-07-31
     */
    handleSelectRow (selection, index) {
      this.selection = this.$refs.selection.getSelection();
    },

    /**
     * 点击同步选择账号触发
     * @param {*void}
     * @author yezi 2019-07-31
     */
    synSelectSubAccount () {
      if (this.selection.length === 0) {
        this.$myModal.error({
          title: "同步数据为空",
          content: "请先选择需要同步的数据"
        });
        return;
      }
      let uuids = [];
      this.selection.forEach(function (i, n) {
        uuids.push(i.uuid);
      });
      this.uuids = uuids.join(",");
      let params = {
        appClientId: this.appClientId,
        userIds: this.uuids
      };
      this.axios({
        method: "post",
        url: "/cipher/subdown/downSelectsubAccountNum",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.length = res.data.num;
            this.showSynModal = true;
            this.isDownsubAccount = false;
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
     * 点击同步所有同账号按钮触发
     * @param {*void}
     * @author yezi 2019-07-31
     */
    synAllSubAccount () {
      let params = {
        appClientId: this.appClientId
      };
      this.axios({
        method: "post",
        url: "/cipher/subdown/downAllsubAccountNum",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.length = res.data.num;
            this.showSynModal = true;
            this.isDownsubAccount = true;
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
     * 点击同步弹出框确认按钮触发
     * @param {*void}
     * @author yezi 2019-07-31
     */
    cofirmSynModal () {
      this.showSynModal = false; // 关闭同步弹框
      if (this.isDownsubAccount) {
        this.downAllsubAccount();
      } else {
        this.downSelectsubAccount();
      }
    },
    /**
     * 同步下发选择的从账号
    * @param {*void}
    * @author yezi 2019-07-31
    */
    downSelectsubAccount () {
      let params = {
        appClientId: this.appClientId,
        userIds: this.uuids
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
            this.currentPage = 1;
            this.getUserList();
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
    /**
     * 同步下发所有从账号
     * @param {*void}
     * @author yezi 2019-07-31
     */
    downAllsubAccount () {
      let params = {
        appClientId: this.appClientId
      };
      this.axios({
        method: "post",
        url: "/cipher/subdown/downAllsubAccount",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.returnDemo = res.data;
            // console.log(this.returnDemo);
            this.$myModal.success({
              title: "同步成功",
              content: `成功${this.returnDemo.successed}条,失败${
                this.returnDemo.fail}条,${this.returnDemo.return_msg}`
            });
            this.currentPage = 1;
            this.getUserList();
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
    /**
     * 改变同步状态触发
     * @param {*void}
     * @author yezi 2019-08-05
     */
    changeSynStatus () {
      this.currentPage = 1;
      this.getUserList();
    },
    /**
     * 当分页中的大小改变时触发的方法
     * @param {*Number 一页显示条数} size
     * @author yezi 2019-07-31
     */
    pageSizeChange (size) {
      this.size = size;
      this.currentPage = 1;
      this.getUserList();
    },
    /**
     * 改变分页时触发该方法
     * @param {*Number 当前页码} index
     * @author yezi 2019-07-31
     */
    changePage (index) {
      this.currentPage = index;
      this.getUserList();
    },
    /**
     * 点击配置从账号，获取配置从账号信息
     * @param {*String 用户账号} accountNumber
     * @param {*String 用户名} userName
     * @param {*String 用户id} uuid
     * @author yezi 2019-07-31
     */
    configSonAccount (accountNumber, userName, uuid) {
      this.info.accountNumber = accountNumber;
      this.info.userName = userName;
      this.info.uuid = uuid;
      let params = {
        appClientId: this.appClientId,
        accountNumber: this.info.accountNumber,
        uuid: this.info.uuid
      };
      this.axios({
        method: "post",
        url: "/cipher/subdown/recomendSubAccount",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.formValidate.sonAccountNumber = res.data.userName;
          } else {
            this.$myModal.error({
              title: "操作失败",
              content: "请联系管理员"
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
      this.showSubConfigModal = true;
    },
    /**
     * 根据应用名称搜索的方法
    * @param {*void}
     * @author yezi 2019-07-31
     */
    searchApplicatioName () {
      this.currentPage = 1;
      this.getUserList();
    },
    /**
     * 初始化页码数据，获取列表及从账号信息
     * @param {*Object} params
     *        { applicationId:"应用id",rows:"一页显示条数",page:"当前页",sidx:"排序key值",
     *          sord: "排序方式:升序asc降序desc",queryName:"搜索词",queryType: "1"}
     * @author yezi 2019-07-31
     */
    init (params) {
      let data = this.appData;
      this.applicationType = data.applicationType;
      this.appClientId = data.applicationId;
      this.appSysId = data.appSysId;
      if (data.associatedAccount.assManual === 1) { // 从账号不是手动配置
        this.isSubConfig = false;
      }
      if (data.associatedAccount.assPwdManual === 1) { // 从账号密码不是手动配置
        this.isSubPwdConfig = false;
      }
      this.getUserList();
    },
    /**
      * 获取用户列表
      * @param {*void}
      * @author yezi 2019-10-18
      */
    getUserList () {
      let params = {
        applicationId: this.appId,
        rows: this.size,
        page: this.currentPage,
        queryName: this.searchAppInput,
        isSynchron: this.synStatus
      };
      this.axios({
        method: "post",
        url: "/cipher/appAuth/authList?json",
        data: params
      })
        .then(res => {
          this.tableData = res.data.rows;
          this.total = res.data.total;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 点击列表中的用户，跳转到组织结构详情页
     * @param {*Number table中的序号} index
     * @author yezi 2019-07-31
     */
    toUserDetail (index) {
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.tableData[index].accountNumber, groupId: this.tableData[index].id, uuid: this.tableData[index].uuid }
      });
    }
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/modal.less";
@import "~@/assets/styles/tableStyle.less";
.uploadClass {
  display: inline-block;
}
.uploadClass /deep/ div {
  width: 86px;
  border: none;
  text-align: left;
  background-color: transparent;
}
</style>
