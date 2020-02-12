<template>
  <div class="org-list tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput v-model="user"
                 :search="true"
                 radius="half"
                 width="400px"
                 @handleClick="initLoad()"
                 placeholder="请输入姓名 / 账号" />
      </div>
      <div class="tabled-header-btn">
        <mySelect :dataList="statusList"
                  showString="显示"
                  v-model="state"
                  @on-change="changeState" />
      </div>
    </div>

    <div class="tabled-table">
      <Table border
             :columns="orgStrTitle"
             :data="orgStrList">
        <template slot-scope="{ row }"
                  slot="userName">
          <!-- <router-link to="/userCatalogOS/detail"> -->
          <strong @click="skipUrl(row)">
            <a href="#">{{ row.userName }}</a>
          </strong>
          <!-- </router-link> -->
        </template>
        <template slot-scope="{ row, index }"
                  slot="action">
          <template v-if="row.status==='锁定'">
            <a type="primary"
               size="small"
               style="margin-right: 5px"
               @click="unlock(index)">解锁</a>
          </template>
          <template v-else>
            <a type="primary"
               size="small"
               style="margin-right: 5px"
               @click="openChangeStatus(index)">{{row.status==="启用" ? "停用" : "启用"}}</a>
          </template>
          <Dropdown trigger="click">
            <a href="#">
              更多
              <Icon type="ios-arrow-down"></Icon>
            </a>
            <DropdownMenu slot="list">
              <DropdownItem @click.native="openResetPW(row,index)">重置密码</DropdownItem>
              <DropdownItem @click.native="deleteAccount(index)">删除用户</DropdownItem>
            </DropdownMenu>
          </Dropdown>
        </template>
      </Table>
    </div>
    <Page :current="list.page"
          :total="list.total"
          show-sizer
          show-elevator
          show-total
          @on-change="changePage"
          @on-page-size-change="changePageSize"
          class="tabled-page" />
    <!-- 重置密码认证模态框 -->
    <Modal v-model="pwdFlag">
      <p slot="header"
         class="header">
        <span>重置密码</span>
        <span class="sub-title">{{userInfo.userName}} ({{userInfo.accountNumber}})</span>
      </p>
      <div>
        <Form :model="formItemPwd"
              ref="formItemPwd"
              :rules="ruleValidate"
              :label-width="110">
          <FormItem label="输入新密码："
                    prop="password">
            <Input v-model.trim="formItemPwd.password"
                   type="password"
                   placeholder="请输入密码"></Input>
          </FormItem>
          <FormItem label="确认新密码："
                    prop="checkPwd">
            <Input v-model.trim="formItemPwd.checkPwd"
                   type="password"
                   placeholder="请输入密码"></Input>
          </FormItem>
          <FormItem label="">
            <Checkbox v-model="formItemPwd.logFlag">登陆后修改密码</Checkbox>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <Button type="primary"
                @click="handleSubmitResetPW('formItemPwd')">确定</Button>
        <Button @click="handleCancel('formItemPwd')"
                style="margin-left:10px;">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
import api from "@/api/userCatalog/index";

const status = {
  start: {
    text: "启用",
    type: 1
  }, // 启用状态
  stop: {
    text: "停用",
    type: 2
  } // 停用状态
};
export default {
  data () {
    const validatePassCheck = (rule, value, callback) => {
      if (!value) {
        callback(new Error("请再次输入密码"));
      } else if (value !== this.formItemPwd.password) {
        callback(new Error("两次输入的密码不匹配"));
      } else {
        callback();
      }
    };
    return {
      selItemIndex: 0, // 所选项索引
      showAllFlag: true,
      orgStrTitle: [
        {
          title: "姓名",
          slot: "userName"
        },
        {
          title: "用户名",
          key: "accountNumber"
        },
        {
          title: "部门",
          key: "groupName"
        },
        {
          title: "安全组",
          key: "teamName"
        },
        {
          title: "状态",
          key: "status",
          render: (h, params) => {
            let status = params.row.status.includes("锁定");
            return h("div", [
              h("strong", params.row.status),
              h("i", {
                class: {
                  "ivu-icon": status,
                  "ivu-icon-md-lock": status
                }
              })
            ]);
          }
        },
        {
          title: "操作",
          slot: "action",
          width: 150,
          align: "center"
        }
      ],
      orgStrList: [],
      orgStrListBak: [],
      list: {},
      navItem: [],
      page: {
        pageNum: 1,
        pageSize: 10
      }, // 存储每页页码和条数
      // 上部搜索
      user: "",
      state: "全部",
      statusList: [{ value: "全部", label: "全部" }, { value: "启用", label: "启用" }, { value: "锁定", label: "锁定" }, { value: "停用", label: "停用" }],
      // pwd密码重置认证模态框
      userInfo: {}, // 用户信息
      pwdFlag: false,
      pwdType: "",
      pwdComplexRateInfo: {}, // pwd策略复杂度信息
      formItemPwd: {
        password: "",
        checkPwd: "",
        logFlag: true
      },
      ruleValidate: {
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" }
        ],
        checkPwd: [
          { required: true, message: "密码不能为空", trigger: "blur" },
          { validator: validatePassCheck, trigger: "blur" }
        ]

      }
    };
  },
  props: {
    selObj: Object,
    initPageNum: {
      type: Number,
      default: 1
    },
    initPageSize: {
      type: Number,
      default: 10
    },
    showAllTemp: Boolean
  },
  computed: {
    // ...mapState({ orgStrList: "selectOSTree" })
    ...mapState(["selectOSTree"]),
    ...mapState(["homeOrg", "group"]),
    homeOrgFlag () {
      return Object.keys(this.homeOrg).length !== 0; // true代表home页面进入
    }
  },
  watch: {
    selObj: {
      handler: function () {
        // todo 数据处理
        // this.generateNavFormat(this.selObj);
        // this.changeHomeOrg({}); // 清除首页跳转保存信息
        this.$store.commit("changeHomeOrg", {});
        this.showAllFlag = this.showAllTemp;
        this.initLoad();
      },
      deep: true
    }
  },
  mounted () {
    if (this.group.groupId) {
      this.showGroupPart(this.initPageNum, this.initPageSize);
    } else {
      this.initConditionCall();
    }
  },
  methods: {
    // 调用条件：1.点击左侧组织树；2.展示全部列表按钮执行，首次点击加载；3.搜索条件更改（包括关键字和状态）
    initLoad () {
      if (!this.showAllFlag) {
        // 必须传参
        this.showSelObj(this.initPageNum, this.initPageSize);
      } else {
        this.initConditionCall();
      }
    },
    // 非首次点击加载：1.输入框；2.更改状态；3.切换页码，分页等
    changeMainEnter () {
      if (!this.showAllFlag) {
        // 不能传参
        this.showSelObj();
      } else {
        this.changeConditionCall();
      }
    },
    // 改变页码
    changePage (page) {
      this.page.pageNum = page;
      this.changeMainEnter();
    },
    // 改变每页尺寸
    changePageSize (pageSize) {
      this.page.pageSize = pageSize;
      this.changeMainEnter();
    },
    // 调用条件：1.由home页面初始化进入；2.要显示所有对象
    initConditionCall () {
      if (this.homeOrgFlag) {
        this.getHomeRelatedList(this.initPageNum, this.initPageSize);
      } else {
        this.showAllObj(this.initPageNum, this.initPageSize);
      }
    },
    // 调用条件：1.由home页面初始化进入；2.要显示所有对象
    changeConditionCall () {
      if (this.homeOrgFlag) {
        this.getHomeRelatedList();
      } else {
        this.showAllObj();
      }
    },
    // 显示选中的对象
    showSelObj (initPageNum, initPageSize) {
      this.showAllFlag = false;
      let selObj = this.selObj;
      let params = {
        page: initPageNum || this.page.pageNum,
        rows: initPageSize || this.page.pageSize,
        groupId: selObj.groupId,
        accountNumber: selObj.accountNumber,
        status: this.state,
        queryName: this.user
      };
      this.axios({
        method: "post",
        data: params,
        url: selObj.href
      })
        .then(response => {
          if (response.status === api.statusOk) {
            let total = response.data.records || response.data.total; // 根据部门groupId获取所有人接口有records，其它接口没有
            this.list.total = total;
            this.list.page = params.page;
            this.orgStrList = this.$common.clone(response.data.rows);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 显示全部的对象
    showAllObj (initPageNum, initPageSize) {
      this.showAllFlag = true;
      // let state = this.convertStateFlag();
      let params = {
        page: initPageNum || this.page.pageNum,
        rows: initPageSize || this.page.pageSize,
        status: this.state,
        queryName: this.user
      };
      this.axios({
        method: "post",
        data: params,
        url: api.newUserGetlist
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.list.total = response.data.total;
            this.list.page = params.page;
            this.orgStrList = this.$common.clone(response.data.rows);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取首页跳转相关列表
    getHomeRelatedList (initPageNum, initPageSize) {
      let params = {
        page: initPageNum || this.page.pageNum,
        rows: initPageSize || this.page.pageSize,
        queryType: this.homeOrg.queryType,
        queryName: this.user
      };
      this.axios({
        method: "post",
        data: params,
        url: api.newList
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.list.total = response.data.total;
            this.list.page = params.page;
            this.orgStrList = this.$common.clone(response.data.rows);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // detail页面返回根据工作部门请求数据
    showGroupPart (initPageNum, initPageSize) {
      this.showAllFlag = false;
      let group = this.group;
      // let state = this.convertStateFlag();
      let params = {
        page: initPageNum || this.page.pageNum,
        rows: initPageSize || this.page.pageSize,
        groupId: group.groupId,
        accountNumber: group.accountNumber,
        status: this.state,
        queryName: this.user
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/newUser/userlist?json"
      })
        .then(response => {
          if (response.status === api.statusOk) {
            let total = response.data.records || response.data.total; // 根据部门groupId获取所有人接口有records，其它接口没有
            this.list.total = total;
            this.list.page = params.page;
            this.orgStrList = this.$common.clone(response.data.rows);
            this.orgStrListBak = this.$common.clone(response.data.rows);
            // this.filterData();
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },

    // 生成导航所需格式
    generateNavFormat (selObj) {
      // if(selObj.children.length){
      // }
    },
    // 改变状态
    changeState (item) {
      this.initLoad();
    },
    // 过滤数据
    filterData () {
      let user = this.user;
      let state = this.state;
      let statusAll = this.status[0]; // 全部状态

      let orgStrList = this.orgStrListBak.filter((item, index) => {
        let userName = item.userName;
        let accountNumber = item.accountNumber;
        if (item.status === state || statusAll === state) {
          if (userName !== null && userName.includes(user)) {
            return true;
          }
          if (accountNumber !== null && accountNumber.includes(user)) {
            return true;
          }
        }
        return false;
      });
      this.orgStrList = orgStrList;
    },
    // 打开更改状态模态框
    openChangeStatus (index) {
      let item = this.orgStrList[index];
      let itemTemp = {};
      if (item.status.includes(status.start.text)) {
        // 启用时设置为停用
        itemTemp.text = status.stop.text;
        itemTemp.type = 2;
      } else {
        // 停用时设置为启用
        itemTemp.text = "确定" + status.start.text + "吗？";
        itemTemp.text = status.start.text;
        itemTemp.type = 1;
      }
      itemTemp.accountNumber = item.accountNumber;
      itemTemp.uuid = item.uuid;

      this.$myModal.confirm({
        title: "确定" + itemTemp.text + "吗？"
      }).then(async (val) => {
        // 点击确定的回调
        this.handleSubmitStatus(index, itemTemp);
      }).catch(() => { });
    },
    // 执行更改状态为启用或停用
    handleSubmitStatus (index, item) {
      // accountNumber: 110120148319220491 status: 1
      // accountNumber: /110120148319220491 status: 2 停用
      let params = {
        accountNumber: item.accountNumber,
        uuid: item.uuid,
        status: item.type
      };
      this.axios({
        method: "post",
        data: params,
        url: api.setStatus
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.orgStrList[index].status = item.text;
            this.$myMessage.success(item.text);
          } else {
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 打开解锁模态框
    unlock (index) {
      this.$myModal.confirm("确认解锁此用户吗？")
        .then(async (val) => {
          this.handleSubmitUnlock(index);
        }).catch(() => { });
    },
    // 执行解锁
    handleSubmitUnlock (index) {
      let params = {
        accountNumber: this.orgStrList[index].accountNumber,
        uuid: this.orgStrList[index].uuid
      };
      this.axios({
        method: "post",
        data: params,
        url: api.unlock
      })
        .then(response => {
          if (
            response.status === api.statusOk &&
            response.data.return_code === api.successOk
          ) {
            // 解锁成功，重新拉取数据
            this.initLoad();
            this.$myMessage.success("解锁成功");
          } else {
            this.$myModal.error("解锁失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 打开重置密码模态框
    openResetPW (row, index) {
      this.selItemIndex = index; // 保存所选项索引
      this.userInfo = row;
      let params = {
        groupId: row.groupId,
        accountNumber: row.accountNumber,
        uuid: row.uuid
      };
      this.axios({
        method: "post",
        data: params,
        url: api.getStrategy
      })
        .then(response => {
          if (
            response.status === api.statusOk &&
            response.data.return_code === api.successOk
          ) {
            this.pwdFlag = true;
            if (response.data.is_ad_update === 0) {
              this.pwdType = "local";
            } else {
              this.pwdType = "ad";
            }
          } else {
            console.log(response.data.return_msg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    handleSubmitResetPW (name) {
      this.$refs[name].validate((valid) => {
        if (!valid) {
          return;
        }
        let url; let params = {};
        if (this.pwdType === "local") {
          url = "/cipher/user/reset/password";
          params = {
            accountNumber: this.orgStrList[this.selItemIndex].accountNumber,
            uuid: this.orgStrList[this.selItemIndex].uuid,
            newPwd: this.formItemPwd.password,
            logFlag: this.formItemPwd.logFlag ? 0 : 1
          };
        } else {
          url = "/cipher/resetPwd/resetAdPwd";
          params = {
            accountNumber: this.orgStrList[this.selItemIndex].accountNumber,
            uuid: this.orgStrList[this.selItemIndex].uuid,
            pwd: this.formItemPwd.password,
            logFlag: this.formItemPwd.logFlag ? 0 : 1
          };
        }
        this.resetPW(params, url);
      });
    },
    // 执行重置密码
    resetPW (params, url) {
      this.axios({
        method: "post",
        data: params,
        url: url
      })
        .then(response => {
          if (response.data.return_code === 1) {
            this.$myMessage.success("重置密码成功");
            this.handleCancel("formItemPwd");
          } else {
            let error = { title: "重置密码失败", content: response.data.return_msg };
            this.$myModal.error(error);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 打开删除账户模态框
    deleteAccount (index) {
      if (this.orgStrList[index].status === "启用") {
        this.$myModal.error({
          title: "账号启用状态不允许删除"
        });
        return;
      }
      this.$myModal.confirm({
        title: "确认删除此用户吗？",
        content: "删除后无法复原，你还要继续吗？",
        confirmVal: "继续"
      }).then(async (val) => {
        // 点击确定的回调
        this.handleSubmitDelete(index);
      }).catch(() => { });
    },
    // 执行删除
    handleSubmitDelete (index) {
      let params = {
        accountNumber: this.orgStrList[index].accountNumber,
        uuid: this.orgStrList[index].uuid
      };
      this.axios({
        method: "post",
        data: params,
        url: api.delete
      })
        .then(response => {
          if (
            response.status === api.statusOk &&
            response.data.return_code === api.successOk
          ) {
            this.orgStrList.splice(index, 1); // 删除当前项
            this.$emit("changeLeftTree"); // 更新左侧树
            this.$myMessage.success("删除成功!");
          } else {
            this.$myModal.error("删除失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    skipUrl (row) {
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: row.accountNumber, groupId: row.groupId, uuid: row.uuid }
      });
      // this.$store.commit("changeBreadCrumb", [
      //   ...this.$store.state.breadCrumb,
      //   { name: "用户详情" }
      // ]);
    },
    // 转换状态请求参数
    convertStateFlag () {
      let state = "";
      switch (this.state) {
        case "启用":
          state = 1;
          break;
        case "停用":
          state = 2;
          break;
        case "锁定":
          state = 3;
          break;
        default:
          state = ""; // 默认是全部
          break;
      }
      return state;
    },
    // 取消
    handleCancel (name) {
      this.pwdFlag = false;
      this.$refs[name].resetFields();
    },
    ...mapMutations["changeHomeOrg"]
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
.org-list {
  height: calc(~"100% - 68px") !important;
}
</style>
