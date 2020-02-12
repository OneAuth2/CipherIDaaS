<template>
  <div class="formed-wrap">
    <Form ref="user"
          class="formed-form"
          :model="user"
          :rules="ruleValidate"
          :label-width="80">
      <div class="mainContent">
        <div class="common bottom">
          <FormItem label="账号"
                    prop="accountNumber">
            <Input v-model.trim="user.accountNumber"
                   placeholder="请输入账号"
                   disabled></Input>
          </FormItem>
          <FormItem label="姓名"
                    prop="userName">
            <Input v-model.trim="user.userName"
                   placeholder="请输入姓名"></Input>
          </FormItem>
          <FormItem label="邮箱"
                    prop="mail">
            <Input v-model.trim="user.mail"
                   placeholder="请输入邮箱"></Input>
          </FormItem>
          <FormItem label="手机号"
                    prop="phoneNumber">
            <Input v-model.trim="user.phoneNumber"
                   placeholder="请输入手机号"></Input>
          </FormItem>
          <FormItem label="身份证号"
                    prop="idNum">
            <Input v-model.trim="user.idNum"
                   placeholder="请输入身份证号"></Input>
          </FormItem>
          <FormItem label="性别"
                    prop="gender">
            <Select v-model="user.gender"
                    placeholder="请输入性别">
              <Option value="男">男</Option>
              <Option value="女">女</Option>
            </Select>
          </FormItem>
          <FormItem label="生日"
                    prop="birthday">
            <DatePicker type="date"
                        placeholder="请选择生日"
                        v-model="birthday"></DatePicker>
          </FormItem>
        </div>
        <div class="common bottom">
          <FormItem label="职位"
                    prop="job">
            <Input v-model.trim="user.job"
                   placeholder="请输入职位"></Input>
          </FormItem>
          <FormItem label="工号"
                    prop="jobNo">
            <Input v-model.trim="user.jobNo"
                   placeholder="请输入工号"></Input>
          </FormItem>
          <FormItem label="工作部门"
                    prop="department"
                    class="tag">
            <div>
              <Tag closable
                   @on-close="closeDepartment(index)"
                   v-for="(item,index) in groupList"
                   :key="index"
                   :name="index">{{item.groupName}}</Tag>
            </div>
            <span class="link"
                  @click="editDepartment()">修改</span>
          </FormItem>
        </div>
        <div class="common bottom">
          <FormItem label="安全组"
                    prop="securityGroup"
                    class="tag">
            <div>
              <Tag closable
                   @on-close="closeSecurityGroup(index)"
                   v-for="(item,index) in teamList"
                   :key="index"
                   :name="index">{{item.teamName}}</Tag>
            </div>
            <span class="link"
                  @click="editSecurityGroup()">修改</span>
          </FormItem>
          <FormItem label="账号绑定:">
            <div class="text">
              AD域:
              <span>{{adbinding}}</span>
              <span class="ad-name">{{adName}}</span>
            </div>
            <div class="text">
              赛赋认证:
              <span>{{saiFuBinding}}</span>
              <span class="link"
                    v-if="saiFuBinding==='已绑定' && unbindSf===1"
                    @click="unbind(1)">解绑</span>
              <span v-if="unbindSf===0"
                    class="disabled">已操作解绑，保存后生效</span>
            </div>
            <div class="text">
              钉钉:
              <span>{{bindingDingDing}}</span>
              <span class="link"
                    v-if="bindingDingDing==='已绑定' & unbindDd===1"
                    @click="unbind(2)">解绑</span>
              <span v-if="unbindDd===0"
                    class="disabled">已操作解绑，保存后生效</span>
            </div>
            <div class="text">
              大白认证:
              <span>{{dabbyBinding}}</span>
              <span class="link"
                    v-if="dabbyBinding==='已绑定' & unbindDb===1"
                    @click="unbind(3)">解绑</span>
              <span v-if="unbindDb===0"
                    class="disabled">已操作解绑，保存后生效</span>
            </div>
            <div class="text">
              企业微信认证:
              <span>{{wxBinding}}</span>
              <span class="link"
                    v-if="wxBinding==='已绑定' & unbindWx===1"
                    @click="unbind(4)">解绑</span>
              <span v-if="unbindWx===0"
                    class="disabled">已操作解绑，保存后生效</span>
            </div>
          </FormItem>
        </div>
      </div>
    </Form>
    <div class="formed-footer">
      <myButton btnType="info"
                @click="handleSubmitSave('user')">保存</myButton>
      <myButton @click="cancel">取消</myButton>
    </div>
    <!-- 编辑工作部门模态框 -->
    <Modal v-model="editDepartmentFlag"
           class="tree-transfer"
           width="668">
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
                @click="updateDepartment()">保存</Button>
        <Button @click="handleResetDepartment()"
                style="margin-left:10px;">重置</Button>
      </div>
    </Modal>

    <!-- 编辑安全组模态框 -->
    <Modal v-model="securityGroupFlag">
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
        <Button @click="handleResetTeam()"
                style="margin-left:10px;">重置</Button>
      </div>
    </Modal>
    <!-- 定时关闭 -->
    <Notice :text="message.text"
            :show="message.show"
            :duration="message.duration"
            :onClose="message.onClose"
            v-if="message.show"></Notice>
    <!-- 确认框 -->
    <Confirm v-if="confirm.flag"
             :flag="confirm.flag"
             :title="confirm.title"
             :content="confirm.content"
             :onOk="confirm.onOk"
             :onClose="confirm.onClose"></Confirm>
  </div>
</template>
<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
import Notice from "@/components/modal/Notice.vue";
import Confirm from "@/components/modal/Confirm.vue";
import verify from "@/util/verify.js";
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
    TreeTransfer,
    Notice,
    Confirm
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
        callback(new Error("请输入不超过100位姓名"));
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
    return {
      birthday2: "",
      birthday: "",
      accountNumber: "",
      uuid: 0,
      groupId: 0,
      securityGroupListBak: [],
      departmentListBak: [],
      unSaveDepartment: [],
      unSaveTeam: [],
      groupIds: "",
      teamIds: "",
      data2: [],
      departmentList: [],
      securityGroupList: [],
      user: {},
      groupList: [],
      teamList: [],
      adbinding: "",
      adName: "",
      saiFuBinding: "",
      bindingDingDing: "",
      dabbyBinding: "",
      wxBinding: "",
      unbindSf: 1,
      unbindDd: 1,
      unbindDb: 1,
      unbindWx: 1,
      unbindType: 0, // 解绑类型
      dataType: 0, // 穿梭框组件类型
      // 编辑工作部门
      editDepartmentFlag: false,

      placeholder: "",
      userIds: "",
      selectDepartment: [], // 选中的工作部门
      selectDepartmentBak: [],
      // 编辑安全组
      securityGroupFlag: false,
      selectSecurityGroup: [], // 选中的安全组
      selectSecurityGroupBak: [],
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
        ]
      },
      message: {
        text: "保存成功!",
        show: false,
        duration: 1,
        onClose: this.close
      },
      confirm: {
        flag: false,
        title: "你确定要解除绑定赛赋认证吗？",
        content: "点击确定后并保存解除绑定。",
        onOk: this.onConfirmOK,
        onClose: this.onConfirmClose
      }
    };
  },
  created () {
    this.accountNumber = this.$route.query.accountNumber;
    this.uuid = this.$route.query.uuid;
    this.groupId = this.$route.query.groupId;
  },
  mounted () {
    this.getUserDetails();
  },
  methods: {
    unbind (type) {
      this.confirm.flag = true;
      this.unbindType = type;
      switch (type) {
        case 1:
          this.confirm.title = "你确定要解除绑定赛赋认证吗？";
          break;
        case 2:
          this.confirm.title = "你确定要解除绑定钉钉吗？";
          break;
        case 3:
          this.confirm.title = "你确定要解除绑定大白认证吗？";
          break;
        case 4:
          this.confirm.title = "你确定要解除绑定企业微信认证吗？";
          break;
      }
    },
    onConfirmOK () {
      switch (this.unbindType) {
        case 1:
          this.unbindSf = 0;
          break;
        case 2:
          this.unbindDd = 0;
          break;
        case 3:
          this.unbindDb = 0;
          break;
        case 4:
          this.unbindWx = 0;
          break;
      }
      this.confirm.flag = false;
    },
    onConfirmClose () {
      this.confirm.flag = false;
    },
    getUserDetails () {
      let params = {
        queryType: "",
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        groupId: this.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/userMsg/modifyUser"
      })
        .then(res => {
          if (res.data.code === 0) {
            this.user = res.data.msg.user;
            this.birthday = res.data.msg.user.birthday;
            this.adbinding = res.data.msg.adbinding ? "已绑定" : "未绑定";
            this.adName = res.data.msg.adbinding || "";
            this.saiFuBinding = res.data.msg.saiFuBinding ? "已绑定" : "未绑定";
            this.bindingDingDing = res.data.msg.bindingDingDing ? "已绑定" : "未绑定";
            this.dabbyBinding = res.data.msg.dabbyBinding ? "已绑定" : "未绑定";
            this.wxBinding = res.data.msg.wxBinding ? "已绑定" : "未绑定";
            this.groupList = res.data.msg.groupList;
            let groupArr = [];
            this.groupList.forEach(item => {
              groupArr.push(item.groupId);
            });
            this.groupIds = groupArr.join(",");

            this.teamList = res.data.msg.teamList;
            let teamArr = [];
            this.teamList.forEach(item => {
              teamArr.push(item.id);
            });
            this.teamIds = teamArr.join(",");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    cancel () {
      this.$emit("msg", true);
    },
    // 修改工作部门树回显数据
    editDepartment () {
      let that = this;
      that.dataType = 2;
      that.editDepartmentFlag = true;
      let params = {
        queryType: "",
        accountNumber: that.accountNumber,
        uuid: that.uuid,
        groupId: that.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/userMsg/modifyUser"
      })
        .then(res => {
          if (res.data.code === 0) {
            that.data2 = res.data.msg.groupTrees;
            that.data2 = that.$common.cloneAssemblyAssignData(
              res.data.msg.groupTrees,
              cloneChangeKeys,
              true
            );
            that.data2 = that.$common.initSelect(that.data2);
            that.departmentList = that.data2;
            that.departmentListBak = that.$common.clone(that.departmentList);
            that.placeholder = "请输入部门";
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 修改安全组树回显
    editSecurityGroup () {
      let that = this;
      that.dataType = 3;
      that.securityGroupFlag = true;
      let params = {
        queryType: "",
        accountNumber: that.accountNumber,
        uuid: that.uuid,
        groupId: that.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/userMsg/modifyUser"
      })
        .then(res => {
          if (res.data.code === 0) {
            that.securityGroupList = res.data.msg.teamApplicationMaps;
            that.securityGroupListBak = that.$common.clone(
              that.securityGroupList
            );
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取工作部门数据
    getDepartmentData (data) {
      let that = this;
      that.unSaveDepartment = data;
    },
    // 将选中的部门数据转化成字符串
    updateDepartment () {
      let groupArr = [];
      this.unSaveDepartment.forEach(item => {
        groupArr.push(item.groupId);
      });
      this.groupIds = groupArr.join(",");
      this.groupList = this.unSaveDepartment;
      this.editDepartmentFlag = false;
    },
    // 获取安全组数据
    getSecurityGroup (data) {
      let that = this;
      that.unSaveTeam = data;
      // that.teamList = data
    },
    // 将选择的安全组数据转换成字符串
    updateSecurityGroup () {
      let teamArr = [];
      this.unSaveTeam.forEach(item => {
        teamArr.push(item.id);
      });
      this.teamIds = teamArr.join(",");
      this.teamList = this.unSaveTeam;
      this.securityGroupFlag = false;
    },
    // 重置部门
    handleResetDepartment () {
      this.departmentList = this.$common.clone(this.departmentListBak);
    },
    // 重置安全组
    handleResetTeam () {
      this.securityGroupList = this.$common.clone(this.securityGroupListBak);
    },
    // 提交保存
    handleSubmitSave (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          if (this.birthday !== null && this.birthday !== "") {
            this.birthday2 = this.$common.formatDate(this.birthday);
          }
          let params = {
            userName: this.user.userName,
            accountNumber: this.user.accountNumber,
            uuid: this.uuid,
            phoneNumber: this.user.phoneNumber,
            idNum: this.user.idNum,
            mail: this.user.mail,
            jobNo: this.user.jobNo,
            job: this.user.job,
            birthday: this.birthday2,
            gender: this.user.gender,
            groupIds: this.groupIds,
            teamIds: this.teamIds,
            unbindSf: this.unbindSf,
            unbindDd: this.unbindDd,
            unbindDb: this.unbindDb,
            unbindWx: this.unbindWx
          };
          this.axios({
            method: "post",
            data: params,
            url: "/cipher/user/updateNewUser"
          })
            .then(res => {
              if (res.data.return_code === "1") {
                this.$myMessage.success(res.data.msg);
                this.cancel();
              } else if (res.data.return_code === 0) {
                this.$myMessage.error(res.data.msg);
              }
            })
            .catch(function (error) {
              console.log(error);
            });
        } else {
          this.$myMessage.error("表单验证失败");
        }
      });
    },

    // 关闭当前工作部门
    closeDepartment (index) {
      this.groupList.splice(index, 1);
      let groupIdArr = [];
      this.groupList.forEach(item => {
        groupIdArr.push(item.groupId);
      });
      this.groupIds = groupIdArr.join(",");
    },
    // 关闭当前安全组
    closeSecurityGroup (index) {
      this.teamList.splice(index, 1);
      let teamIdArr = [];
      this.teamList.forEach(item => {
        teamIdArr.push(item.id);
      });
      this.teamIds = teamIdArr.join(",");
    },
    close () {
      this.message.show = false;
      this.$router.push({
        path: "/userCatalogOS/detail",
        query: { accountNumber: this.formValidate.accountNumber, uuid: this.$route.query.uuid }
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/modal.less";
@import "~@/assets/styles/formStyle.less";
.text span {
  margin-right: @customMargin;
}
</style>
