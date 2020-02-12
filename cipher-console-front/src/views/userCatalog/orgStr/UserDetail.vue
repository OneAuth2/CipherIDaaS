<template>
  <div class="userDetail formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">
        {{userName}}
        <span class="sub-title samll-font"> <span :style="statusStyle"
                class="point"></span> {{status}}</span>
      </div>
      <div class="formed-header-btn"
           style="z-index: 2">
        <Dropdown class="dropdown">
          <myDropBtn>选择操作</myDropBtn>
          <DropdownMenu slot="list">
            <DropdownItem v-for="(item,index) in operateList"
                          :value="item.value"
                          :key="index"
                          @click.native="operate(item.label)">{{ item.label }}</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </div>
    </div>
    <myButton btnType="info"
              class="user-detail-change__btn"
              v-if="detail && currentTab==='user'"
              @click="changeStatus">编辑</myButton>
    <div class="formed-container">
      <myComplexTabs v-model="currentTab">
        <myPane label="用户信息"
                name="user">
          <template v-if="Object.keys(userList).length !== 0">
            <DetailList v-if="detail"
                        key="detail"
                        ref="user"
                        @msg="sendMsgFromChild"
                        :dataMsg="dataMsg"
                        :userList="userList"></DetailList>
            <DetailEdit v-else
                        key="edit"
                        @msg="sendMsgFromChild"></DetailEdit>
          </template>
        </myPane>
        <myPane label="应用列表"
                name="list">
          <appList v-bind:userName="userName"></appList>
        </myPane>
      </myComplexTabs>
    </div>
    <!-- 重置密码认证模态框 -->
    <Modal v-model="pwdFlag">
      <p slot="header"
         class="header">
        <span>重置密码</span>
        <span class="sub-title">{{userName}} ({{accountNumber}})</span>
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
import api from "@/api/userCatalog/index.js";
import DetailList from "@/views/userCatalog/orgStr/Detail.vue";
import DetailEdit from "@/views/userCatalog/orgStr/DetailEdit.vue";
import appList from "@/views/userCatalog/orgStr/AppList.vue";
import { mapState } from "vuex";
const status = {
  start: {
    text: "启用",
    type: 1
  }, // 启用状态
  stop: {
    text: "停用",
    type: 2
  } // 停用状态
}; // 打开更改状态模态框

export default {
  components: {
    DetailList,
    DetailEdit,
    appList
  },
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
      number: 1,
      fresh: 0,
      detail: true, // 默认详情列表
      userName: "", // 用户名
      status: "", // 状态
      statusStyle: {
        backgroundColor: "#00a854"
      },
      operateList: [], // 操作列表，与左侧对应关系：启用（停用），停用（启用），解锁（锁定）
      operateListBak: [
        {
          value: "重置密码",
          label: "重置密码"
        },
        {
          value: "删除用户",
          label: "删除用户"
        }
      ],
      currentTab: "user",
      userList: {}, // 用户详情
      accountNumber: "", // 账号
      uuid: 0, // 账号id
      groupId: "", // 用户名
      // pwd密码重置认证模态框
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
  computed: {
    ...mapState(["selectOSTree"])
  },
  created () {
    this.accountNumber = this.$route.query.accountNumber;
    this.uuid = this.$route.query.uuid;
    this.groupId = this.$route.query.groupId;
    this.getUserDetails();
  },
  methods: {
    /**
    * 点击修改时执行，调用子组件方法，进入编辑状态
    * @param {*void}
    * @author yezi 2019-10-11
    */
    changeStatus () {
      this.$refs[this.currentTab].edit();
    },
    sendMsgFromChild (data) {
      this.detail = data;
      if (data) { // 详情列表时
        this.getUserDetails();
      }
    },
    // 获取用户详情
    getUserDetails () {
      this.accountNumber = this.$route.query.accountNumber;
      this.groupId = this.$route.query.groupId;
      let params = {
        queryType: "",
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        groupId: this.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: api.getUserDetails
      })
        .then(response => {
          if (response.data.code === api.returnOk) {
            let userList = response.data.msg;
            this.userName = userList.user.userName;
            this.status = userList.user.accountStatus;
            this.userList = userList.user;
            this.dataMsg = userList;
            this.assembleOperate();
          } else {
            throw new Error("请求失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 组装操作列表
    assembleOperate () {
      let text = this.status;
      switch (text) {
        case "启用":
          this.statusStyle.backgroundColor = "#00a854";
          text = "停用";
          break;
        case "停用":
          this.statusStyle.backgroundColor = "#ff9900";
          text = "启用";
          break;
        case "锁定":
          this.statusStyle.backgroundColor = "#ed4014";
          text = "解锁";
          break;
        default:
          break;
      }
      let item = {
        value: text,
        label: text
      };
      this.operateList = this.$common.clone(this.operateListBak);
      this.operateList.unshift(item);
    },
    // 选择操作
    operate (text) {
      switch (text) {
        case "启用":
        case "停用":
          this.openChangeStatus();
          break;
        case "解锁":
          this.unlock();
          break;
        case "重置密码":
          this.openResetPW();
          break;
        case "删除用户":
          this.deleteAccount();
          break;
        default:
          break;
      }
    },
    openChangeStatus () {
      let item = this.userList;
      let itemTemp = {};
      if (item.accountStatus.includes(status.start.text)) {
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
        this.handleSubmitStatus(itemTemp);
      }).catch(() => { });
    },
    // 执行更改状态为启用或停用
    handleSubmitStatus (item) {
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
            this.$myMessage.success(item.text + "成功");
            this.getUserDetails();
          } else {
            let error = { title: item.text + "失败", content: "请稍后重试" };
            this.$myModal.error(error);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 打开解锁模态框
    unlock () {
      this.$myModal.confirm({
        title: "确认解锁此用户吗？"
      }).then(async (val) => {
        // 点击确定的回调
        this.handleSubmitUnlock();
      }).catch(() => { });
    },
    // 执行解锁
    handleSubmitUnlock () {
      let params = {
        accountNumber: this.userList.accountNumber,
        uuid: this.userList.uuid
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
            this.changeMainEnter();
            this.$myMessage.success("解锁成功");
          } else {
            let error = { title: "解锁失败", content: "请稍后重试" };
            this.$myModal.error(error);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 打开重置密码模态框
    openResetPW () {
      let params = {
        groupId: this.userList.groupId,
        accountNumber: this.userList.accountNumber,
        uuid: this.userList.uuid
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
            accountNumber: this.userList.accountNumber,
            uuid: this.uuid,
            newPwd: this.formItemPwd.password,
            logFlag: this.formItemPwd.logFlag ? 0 : 1
          };
        } else {
          url = "/cipher/resetPwd/resetAdPwd";
          params = {
            accountNumber: this.userList.accountNumber,
            uuid: this.uuid,
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
    deleteAccount () {
      if (this.status === "启用") {
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
        this.handleSubmitDelete();
      }).catch(() => { });
    },
    // 执行删除
    handleSubmitDelete () {
      let params = {
        accountNumber: this.userList.accountNumber,
        uuid: this.userList.uuid
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
            this.$myMessage.success("删除成功");
            this.$router.push({ path: "/userCatalogOS" });
          } else {
            let error = { title: "删除失败", content: "请稍后重试" };
            this.$myModal.error(error);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 取消
    handleCancel (name) {
      this.pwdFlag = false;
      this.$refs[name].resetFields();
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.user-detail-change__btn {
  position: absolute;
  right: 8px;
  top: 91px;
  transform: translateY(-50%);
  z-index: 1;
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - @{headerHeight} - 1px");
    background-color: #fff;
    .border-radius;
    > .pane {
      height: 100%;
      overflow: auto;
    }
  }
}
</style>
