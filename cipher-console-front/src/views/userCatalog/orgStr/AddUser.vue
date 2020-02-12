<template>
  <div class="addUser formed-wrap">
    <div class="formed-header">
      <span class="formed-header-title">添加用户</span>
      <span class="formed-header-btn">
        <router-link to="/userCatalogOS">
          <myButton>取消</myButton>
        </router-link>
      </span>
    </div>
    <div class="formed-container">
      <Form ref="formValidate"
            :model="formValidate"
            :rules="ruleValidate"
            :label-width="100"
            class="formed-form">
        <FormItem label="账号："
                  prop="accountNumber">
          <Input v-model.trim="formValidate.accountNumber"
                 placeholder="请输入账号"></Input>
        </FormItem>
        <FormItem label="姓名："
                  prop="userName">
          <Input v-model.trim="formValidate.userName"
                 placeholder="请输入姓名"></Input>
        </FormItem>
        <FormItem label="邮箱："
                  prop="mail">
          <Input v-model.trim="formValidate.mail"
                 placeholder="请输入邮箱"></Input>
        </FormItem>
        <FormItem label="手机号："
                  prop="phoneNumber">
          <Input v-model.trim="formValidate.phoneNumber"
                 placeholder="请输入手机号"></Input>
        </FormItem>
        <FormItem label="身份证号："
                  prop="idNum">
          <Input v-model.trim="formValidate.idNum"
                 placeholder="请输入身份证号"></Input>
        </FormItem>
        <FormItem label="性别："
                  prop="gender">
          <Select v-model="formValidate.gender"
                  placeholder="请输入性别">
            <Option value="未知">未知</Option>
            <Option value="男">男</Option>
            <Option value="女">女</Option>
          </Select>
        </FormItem>
        <FormItem label="生日："
                  prop="birthday">
          <DatePicker type="date"
                      placeholder="请选择生日"
                      v-model="formValidate.birthday"
                      style="width:300px;"></DatePicker>
        </FormItem>

        <FormItem label="职位："
                  prop="job">
          <Input v-model.trim="formValidate.job"
                 placeholder="请输入职位"></Input>
        </FormItem>
        <FormItem label="工号："
                  prop="jobNo">
          <Input v-model.trim="formValidate.jobNo"
                 placeholder="请输入工号"></Input>
        </FormItem>
        <FormItem label="工作部门："
                  prop="department"
                  class="tag">
          <div>
            <Tag closable
                 @on-close="closeDepartment(index)"
                 v-for="(item,index) in selectDepartment"
                 :key="index">{{item.title}}</Tag>
          </div>
          <span class="link"
                @click="editDepartment()">编辑</span>
        </FormItem>
        <FormItem label="安全组："
                  prop="securityGroup"
                  class="tag">
          <div>
            <Tag closable
                 @on-close="closeSecurityGroup(index)"
                 v-for="(item,index) in selectSecurityGroup"
                 :key="index">{{item.teamName}}</Tag>
          </div>
          <span class="link"
                @click="editSecurityGroup()">编辑</span>
        </FormItem>

        <FormItem label="设置密码："
                  prop="password">
          <RadioGroup v-model="password">
            <Radio label="default">使用默认密码</Radio>
            <Radio label="singal">单独设置密码</Radio>
          </RadioGroup>
          <Input type="password"
                 v-if="password !== 'default'"
                 v-model="formValidate.password"
                 placeholder="请输入密码"></Input>
        </FormItem>
        <FormItem prop="checkPassword"
                  v-if="password !== 'default'">
          <Input type="password"
                 v-model="formValidate.checkPassword"
                 placeholder="请确认密码"></Input>
        </FormItem>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  :disabled="isDisable"
                  @click="handleSubmitSave('formValidate')">保存</myButton>
        <router-link to="/userCatalogOS">
          <myButton>取消</myButton>
        </router-link>
      </div>
    </div>

    <!-- 编辑工作部门模态框 -->
    <Modal v-model="editDepartmentFlag"
           width="668"
           class="tree-transfer">
      <p slot="header">
        <span>设置用户部门</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="departmentList"
                      :leftDataType="dataType"
                      @rightData="getDepartmentData"
                      :placeholder="placeholder">
          <span slot="leftText">选择部门</span>
          <span slot="rightText">已选择部门</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                :disabled="isDisable"
                @click="updateDepartment()">保存</Button>
        <Button @click="handleResetDepartment"
                style="margin-left:10px;">重置</Button>
      </div>
    </Modal>

    <!-- 编辑安全组模态框 -->
    <Modal v-model="securityGroupFlag"
           width="668">
      <p slot="header">
        <span>设置安全组</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="securityGroupList"
                      :leftDataType="dataType"
                      @rightData="getSecurityGroup"
                      :placeholder="placeholder">
          <span slot="leftText">选择安全组</span>
          <span slot="rightText">已选择安全组</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                @click="updateSecurityGroup()">保存</Button>
        <Button @click="handleResetSecurityGroup"
                style="margin-left:10px;">重置</Button>
      </div>
    </Modal>
    <!-- 定时关闭 -->
    <Notice :text="message.text"
            :show="message.show"
            :duration="message.duration"
            :onClose="message.onClose"
            v-if="message.show"></Notice>
  </div>
</template>
<script>
import api from "@/api/userCatalog/index.js";
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
import Notice from "@/components/modal/Notice.vue";
import modal from "@/util/modal/index.js";
import verify from "@/util/verify.js";

const assignKeysDepartment = {
  title: {
    target: "title",
    original: "text"
  },
  children: {
    target: "children",
    original: "nodes"
  }
};
const assignKeysSelectDepart = {
  title: {
    original: "title"
  },
  groupId: {
    original: "groupId"
  }
};

export default {
  components: {
    TreeTransfer,
    Notice
  },
  data () {
    // 账号校验
    const validateAccountNumber = (rule, value, callback) => {
      if (!value.replace(/^ +| +$/, "") || value.length > 50) {
        callback(new Error("请输入不超过50位账号"));
      }
      let regx = verify.accountNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入包含英文字符，数字或特殊符号的账号"));
      }
      callback();
    };
    // 姓名校验
    const validateUserName = (rule, value, callback) => {
      if (!value.replace(/^ +| +$/, "") || value.length > 100) {
        callback(new Error("请输入不超过100位的姓名"));
      }
      callback();
    };
    // 身份证号校验
    const validateIsNum = (rule, value, callback) => {
      var city = { 11: "北京", 12: "天津", 13: "河北", 14: "山西", 15: "内蒙古", 21: "辽宁", 22: "吉林", 23: "黑龙江 ", 31: "上海", 32: "江苏", 33: "浙江", 34: "安徽", 35: "福建", 36: "江西", 37: "山东", 41: "河南", 42: "湖北 ", 43: "湖南", 44: "广东", 45: "广西", 46: "海南", 50: "重庆", 51: "四川", 52: "贵州", 53: "云南", 54: "西藏 ", 61: "陕西", 62: "甘肃", 63: "青海", 64: "宁夏", 65: "新疆", 71: "台湾", 81: "香港", 82: "澳门", 91: "国外 " };
      let reg = verify.identity;
      if (value === null || value.length === 0) {
        callback();
      } else if (!reg.test(value)) {
        callback(new Error("身份证格式不正确"));
      } else if (!city[value.substr(0, 2)]) {
        callback(new Error("身份证格式不正确"));
      } else {
        // 18位身份证需要验证最后一位校验位
        if (value.length === 18) {
          value = value.split("");
          // ∑(ai×Wi)(mod 11)
          // 加权因子
          var factor = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2];
          // 校验位
          var parity = [1, 0, "X", 9, 8, 7, 6, 5, 4, 3, 2];
          var sum = 0;
          var ai = 0;
          var wi = 0;
          for (var i = 0; i < 17; i++) {
            ai = value[i];
            wi = factor[i];
            sum += ai * wi;
          }
          if (parity[sum % 11].toString() !== value[17]) {
            callback(new Error("身份证格式不正确"));
          }
        }
      }
      callback();
    };
    // 密码校验
    const validatePass = (rule, value, callback) => {
      if (value.length === 0 || value.length > 30 || value.length < 6) {
        callback(new Error("请输入不少于6位，不超过30位的密码"));
      }
      let regx = verify.accountNumber;
      if (!regx.test(value)) {
        callback(new Error("请输入包含英文字符，数字或特殊符号的密码"));
      }
      callback();
    };
    // 确认密码
    const validateCheckPass = (rule, value, callback) => {
      if (value !== this.formValidate.password) {
        callback(new Error("两次输入密码不一致"));
      }
      callback();
    };
    return {
      dataType: 0, // 穿梭框组件类型
      // 编辑工作部门
      editDepartmentFlag: false,
      departmentList: [],
      departmentListBak: [],
      placeholder: "搜索应用",
      userIds: "",
      selectDepartment: [], // 选中的工作部门
      selectDepartmentBak: [],
      // 编辑安全组
      securityGroupFlag: false,
      securityGroupList: [],
      selectSecurityGroup: [], // 选中的安全组
      selectSecurityGroupBak: [],

      password: "singal", // default代表使用默认密码,singal 表示单独设置密码
      pwDefault: "******",
      uuid: 0, // 账号id
      formValidate: {
        accountNumber: "",
        userName: "",
        mail: "",
        phoneNumber: "",
        idNum: "", // 身份证号
        gender: "",
        job: "",
        jobNo: "",
        birthday: "",
        department: [], // groupId用逗号拼接
        securityGroup: [],
        password: "",
        checkPassword: ""
      },
      ruleValidate: {
        accountNumber: [
          {
            required: true,
            validator: validateAccountNumber,
            trigger: "blur"
          }
        ],
        userName: [
          {
            required: true,
            validator: validateUserName,
            trigger: "blur"
          }
        ],
        mail: [
          {
            required: true,
            message: "请输入邮箱",
            trigger: "blur"
          },
          { type: "email", message: "邮箱格式不正确", trigger: "blur" }
        ],
        idNum: [
          {
            validator: validateIsNum,
            trigger: "blur"
          }
        ],
        password: [
          {
            required: true,
            validator: validatePass,
            trigger: "blur"
          }
        ],
        checkPassword: [
          {
            required: true,
            message: "确认密码不能为空",
            trigger: "blur"
          },
          {
            required: true,
            validator: validateCheckPass,
            trigger: "blur"
          }
        ]
      },
      message: {
        text: "保存成功!",
        show: false,
        duration: 1,
        onClose: this.close
      },
      isDisable: false
    };
  },
  watch: {
    // 根据页面改变表单校验前的默认值
    password () {
      if (this.password === "default") {
        this.formValidate.password = this.pwDefault;
        this.$refs["formValidate"].validate();
      } else {
        this.formValidate.password = "";
      }
    }
  },
  methods: {
    getRightData (data) {
      console.log(data);
      let userIdArr = [];
      data.forEach(item => {
        userIdArr.push(item.accountNumber);
      });
      console.log(userIdArr);
      this.userIds = userIdArr.join(",");
    },
    // 编辑工作部门
    editDepartment () {
      this.dataType = 2;
      this.editDepartmentFlag = true;
      let params = {
        accountNumber: "",
        uuid: ""
      };
      // if (this.departmentList.length <= 0) {
      this.axios({
        method: "post",
        data: params,
        url: api.groupTreeList
      })
        .then(response => {
          if (response.status === api.statusOk) {
            let departmentList = this.$common.cloneAssemblyAssignData(
              response.data,
              assignKeysDepartment,
              true
            );
            this.departmentList = this.$common.initSelect(departmentList);
            this.departmentListBak = this.$common.clone(this.departmentList);
          } else {
            throw new Error("请求失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
      // }
    },
    // 获取工作部门数据
    getDepartmentData (data) {
      this.selectDepartmentBak = this.$common.cloneAssemblyAssignData(
        data,
        assignKeysSelectDepart,
        false
      );
    },
    // 更新工作部门模态框中选中数据到页面
    updateDepartment () {
      this.editDepartmentFlag = false;
      this.selectDepartment = this.selectDepartmentBak;
    },
    // 重置部门
    handleResetDepartment () {
      this.departmentList = this.$common.clone(this.departmentListBak);
    },
    // 编辑安全组
    editSecurityGroup () {
      this.dataType = 3;
      this.securityGroupFlag = true;
      let params = {
        accountNumber: ""
      };
      // if (this.securityGroupList.length <= 0) {
      this.axios({
        method: "post",
        data: params,
        url: api.securityGroup
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.securityGroupList = response.data;
            this.securityGroupListBak = this.$common.clone(
              this.securityGroupList
            );
          } else {
            throw new Error("请求失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
      // }
    },
    // 获取安全组数据
    getSecurityGroup (data) {
      this.selectSecurityGroupBak = data;
    },
    // 更新安全组模态框中选中数据到页面
    updateSecurityGroup () {
      this.securityGroupFlag = false;
      this.selectSecurityGroup = this.selectSecurityGroupBak;
    },
    // 重置安全组
    handleResetSecurityGroup () {
      this.securityGroupList = this.$common.clone(this.securityGroupListBak);
    },
    // 在表单校验后提交前，处理请求参数
    dealParam () {
      // 拼接字符串，组装成api需要的数据
      let department = this.selectDepartment.map(function (elem, index, arr) {
        return elem.groupId;
      });
      department = department.join(",");

      let securityGroup = this.selectSecurityGroup.map(function (
        elem,
        index,
        arr
      ) {
        return elem.id;
      });
      securityGroup = securityGroup.join(",");

      let birthday = this.$common.formatDate(this.formValidate.birthday);

      let password;
      if (this.password === "default") {
        // 1.使用默认密码时，默认值为空
        password = "";
      } else {
        // 2.使用默认密码时，密码为用户输入内容
        password = this.formValidate.password;
      }
      let params = {
        accountNumber: this.formValidate.accountNumber,
        userName: this.formValidate.userName,
        mail: this.formValidate.mail,
        phoneNumber: this.formValidate.phoneNumber,
        idNum: this.formValidate.idNum,
        gender: this.formValidate.gender,
        birthday: birthday,
        job: this.formValidate.job,
        jobNo: this.formValidate.jobNo,
        department: department,
        securityGroup: securityGroup,
        password: password
      };
      return params;
    },
    // 提交保存
    handleSubmitSave (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          let params = this.dealParam();
          this.setUnderway();
          this.axios({
            method: "post",
            data: params,
            url: api.add
          })
            .then(response => {
              this.setCompleted();
              if (response.data.return_code === api.returnOk) {
                this.uuid = response.data.uuid;
                this.success();
              } else {
                this.error(response.data.msg);
              }
            })
            .catch(function (error) {
              this.setCompleted();
              this.axios.error.handlingErrors(error);
            });
        }
      });
    },
    // 关闭当前工作部门
    closeDepartment (index) {
      this.selectDepartment.splice(index, 1);
    },
    // 关闭当前安全组
    closeSecurityGroup (index) {
      this.selectSecurityGroup.splice(index, 1);
    },
    close () {
      this.message.show = false;
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.formValidate.accountNumber, uuid: this.uuid }
      });
      // 加入公共导航
      // this.$store.commit("changeBreadCrumb", [
      //   ...this.$store.state.breadCrumb,
      //   { name: "用户详情" }
      // ]);
    },
    success () {
      this.message.show = true;
      // 加入公共导航
      // this.$store.commit("changeBreadCrumb", [
      //   ...this.$store.state.breadCrumb,
      //   { name: "用户详情" }
      // ]);
      // 参数传入用户详情页面
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.formValidate.accountNumber, groupId: "", uuid: this.uuid }
      });
    },
    error (msg) {
      this.$Modal.error({
        title: "保存失败",
        content: msg,
        width: modal.simpleModal.width
      });
    },
    // 设置进行中状态
    setUnderway () {
      this.isDisable = true;
    },
    // 设置完成状态
    setCompleted () {
      this.isDisable = false;
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/modal.less";
@import "~@/assets/styles/formStyle.less";
/deep/ .ivu-input,
/deep/ .ivu-select-selection {
  width: 312px;
}
.tag {
  text-align: left;
}
</style>
