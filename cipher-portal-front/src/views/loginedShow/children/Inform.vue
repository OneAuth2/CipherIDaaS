<template>
  <div class="inform">
    <div class="formsBody margin-center">
      <h3 class="title">个人信息</h3>
      <Form ref="formValidate"
            :model="formValidate"
            :rules="ruleValidate"
            :label-width="55"
            class="demo">
        <FormItem label="姓名"
                  prop="userName"
                  class="grey">
          <Input v-model.trim="formValidate.userName"
                 placeholder="请输入姓名"
                 size="default"
                 readonly></Input>
        </FormItem>
        <FormItem label="昵称"
                  prop="nickname"
                  class="grey">
          <Input v-model.trim="formValidate.nickname"
                 size="default"
                 placeholder="请输入名称"></Input>
        </FormItem>
        <FormItem label="邮箱"
                  prop="mail"
                  class="grey">
          <Input v-model.trim="formValidate.mail"
                 placeholder="请输入邮箱"
                 size="default"
                 readonly></Input>
        </FormItem>
        <FormItem label="部门"
                  prop="groupName"
                  class="grey">
          <Input v-model.trim="formValidate.groupName"
                 size="default"
                 readonly></Input>
        </FormItem>
        <FormItem label="手机"
                  prop="phoneNumber"
                  class="grey">
          <Input v-model.trim="formValidate.phoneNumber"
                 size="default"
                 placeholder="请输入手机号码"
                 readonly></Input>
        </FormItem>
        <FormItem :label-width="0">
          <button class="button"
                  type="button"
                  @click="handleSubmit('formValidate')">
            提交
          </button>
        </FormItem>
      </Form>
    </div>
  </div>
</template>
<script>
import api from "@/api/loginedShow/index.js";
export default {
  data () {
    return {
      formValidate: {
        userName: "",
        nickname: "",
        mail: "",
        groupName: "",
        phoneNumber: ""
      },
      ruleValidate: {
        userName: [
          { required: true, message: "用户名不能为空", trigger: "blur" }
        ],
        mail: [
          { required: true, message: "邮箱不能为空", trigger: "blur" },
          {
            type: "string",
            pattern: /^(\w-*\.*)+@(\w-?)+(\.\w{2,})+$/,
            message: "邮箱格式不正确",
            trigger: "blur"
          }
        ],
        phoneNumber: [
          { required: true, message: "手机号码不能为空", trigger: "blur" },
          {
            type: "string",
            pattern: /^1[3-9]\d{9}$/,
            message: "手机号格式不正确",
            trigger: "blur"
          }
        ]
      }
    };
  },
  created () {
    this.getUserInfo();
  },
  methods: {
    getUserInfo () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a"
      };
      this.axios({
        method: "post",
        data: params,
        url: api.getUserInfo
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.formValidate = res.data.user;
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.save();
        }
      });
    },
    save () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        userName: this.formValidate.userName,
        nickname: this.formValidate.nickname,
        mail: this.formValidate.mail,
        phoneNumber: this.formValidate.phoneNumber
      };
      this.axios({
        method: "post",
        data: params,
        url: api.updateUserInfo
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$Message.success("提交成功");
            this.getUserInfo();
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/showList/common.less";
.inform {
  position: relative;
  width: 100%;
  height: 100%;
  .padding(120px, 0);
}
.grey /deep/ input {
  opacity: 0.7;
}
</style>
