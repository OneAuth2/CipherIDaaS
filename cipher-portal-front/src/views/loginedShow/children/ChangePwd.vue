<template>
  <div class="change-pwd">
    <div class="formsBody margin-center">
      <h3 class="title">修改密码</h3>
      <Form ref="formValidate"
            :model="formValidate"
            :rules="ruleValidate">
        <FormItem prop="pwdNow">
          <Input v-model.trim="formValidate.pwdNow"
                 type="password"
                 placeholder="输入当前密码"></Input>
        </FormItem>
        <FormItem prop="pwdNew">
          <Input v-model.trim="formValidate.pwdNew"
                 type="password"
                 placeholder="输入新密码"></Input>
        </FormItem>
        <FormItem prop="pwdCheck">
          <Input v-model.trim="formValidate.pwdCheck"
                 type="password"
                 placeholder="确认新密码"></Input>
        </FormItem>
        <FormItem>
          <button class="button"
                  type="button"
                  @click="handleSubmit('formValidate')">提交</button>
        </FormItem>
      </Form>
    </div>
  </div>
</template>

<script>
import api from "@/api/loginedShow/index.js";
export default {
  data () {
    const pwdCheckValidate = (rule, value, callback) => {
      if (this.formValidate.pwdNew !== value) {
        callback(new Error("新密码和确认密码应相同"));
      }
      callback();
    };
    return {
      formValidate: {
        pwdNow: "",
        pwdNew: "",
        pwdCheck: ""
      },
      ruleValidate: {
        pwdNow: [
          { required: true, message: "请输入当前密码", trigger: "blur" }
        ],
        pwdNew: [
          { required: true, message: "请输入新密码", trigger: "blur" }
        ],
        pwdCheck: [
          { required: true, message: "请确认新密码", trigger: "blur" },
          { required: false, validator: pwdCheckValidate, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    // 点击保存执行
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.checkPwd();
        }
      });
    },
    // 检查密码是否符合要求
    checkPwd () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        pwd: this.formValidate.pwdNow,
        newpwd: this.formValidate.pwdNew,
        repwd: this.formValidate.pwdCheck
      };
      this.axios({
        method: "post",
        data: params,
        url: api.checkPwd
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.changePwd();
          } else if (res.data.return_code !== 419) {
            let errorMsg = res.data.return_msg;
            this.$Message.warning(errorMsg);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 修改密码
    changePwd () {
      let params = {
        // companyUUid: "123456",
        // uuid: "be22b31d6b1411e9b5af00163e00cc6a",
        pwd: this.formValidate.pwdNow,
        newpwd: this.formValidate.pwdNew,
        repwd: this.formValidate.pwdCheck
      };
      this.axios({
        method: "post",
        data: params,
        url: api.changePwd
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.$Message.success("修改密码成功");
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
.change-pwd {
  position: relative;
  width: 100%;
  height: 100%;
  .padding(120px, 0);
}
</style>
