<template>
  <div class="command formed-wrap">
    <div class="formed-header">
      <span class="formed-header-title">密码策略</span>
      <myButton btnType="info"
                v-if="!isEdit"
                @click="toEdit"
                class="formed-header-btn">编辑</myButton>
    </div>
    <div class="formed-container">
      <div class="formed-form"
           v-if="!isEdit">
        <div class="formed-form-item">
          <span>密码策略：</span>
          <span>{{passwordtext}}</span>
        </div>
        <div class="formed-form-item">
          <span>初始密码：</span>
          <span>{{init}}</span>
        </div>
      </div>
      <div class="formed-form"
           v-else>
        <Form ref="setting"
              :model="setting"
              :rules="ruleValidate"
              :label-width="130">
          <FormItem label="密码策略："
                    prop="passwordType">
            <RadioGroup vertical
                        v-model="setting.passwordType">
              <div class="text">
                <Radio label="1">
                  <span>简单（6位及6位以上密码，无特殊要求）</span>
                </Radio>
              </div>
              <div class="text">
                <Radio label="2">
                  <span>中等（8位及8位以上密码，至少包含数字+字母）</span>
                </Radio>
              </div>
              <div class="text">
                <Radio label="3">
                  <span>复杂 （8位及8位以上密码，至少包含数字+大写字母+小写字母）</span>
                </Radio>
              </div>
            </RadioGroup>
          </FormItem>
          <FormItem label="初始密码："
                    prop="init">
            <Input class="initpassword"
                   :type="pwdType"
                   :maxlength="30"
                   v-model="setting.init" />
            <img class="img"
                 :src="src"
                 @click="changeType"
                 draggable="false">
          </FormItem>
        </Form>
      </div>
      <div class="formed-footer"
           v-if="isEdit">
        <myButton btnType="info"
                  @click="tosave('setting')">保存</myButton>
        <myButton @click="cancelEdit">取消</myButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      passwordtext: "",
      init: "",
      isEdit: false, // 是否编辑状态
      pwdType: "password",
      src: require("@/assets/colse_eyes.png"),
      setting: {
        pageData: null,
        id: 0,
        length: 0,
        init: "",
        passwordType: ""
      },
      ruleValidate: {
        init: []
      }

    };
  },
  watch: {
    "setting.passwordType" (val, oldVal) {
      if (val === "1") {
        this.ruleValidate = {
          init: [
            {
              validator: (rule, value, callback) => {
                if (value.length < 6) {
                  callback(new Error("请输入6位及6位以上密码"));
                } else if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)) {
                  callback(new Error("密码不能是中文"));
                }
                callback();
              },
              trigger: "blur"
            }
          ]
        };
      }
      if (val === "2") {
        this.ruleValidate = {
          init: [
            {
              validator: (rule, value, callback) => {
                let count = /[\d]/.test(value) + /[a-z]/i.test(value);
                if (value.length < 8 || count < 2) {
                  callback(new Error("8位及8位以上密码，至少包含数字+字母"));
                } else if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)) {
                  callback(new Error("密码不能是中文"));
                }
                callback();
              },
              trigger: "blur"
            }
          ]
        };
      }
      if (val === "3") {
        this.ruleValidate = {
          init: [
            {
              validator: (rule, value, callback) => {
                let count = /[\d]/.test(value) + /[a-z]/.test(value) + /[A-Z]/.test(value);
                if (value.length < 8 || count < 3) {
                  callback(new Error("8位及8位以上密码，至少包含数字+大写字母+小写字母"));
                } else if (/[\u4E00-\u9FA5\uF900-\uFA2D]/.test(value)) {
                  callback(new Error("密码不能是中文"));
                }
                callback();
              },
              trigger: "blur"
            }
          ]
        };
      }
    }
  },
  mounted () {
    var that = this;
    that.finddata();
  },
  methods: {
    /**
     * 进入编辑状态
     * @param {*void}
     * @author yezi 2019-08-28
     */
    toEdit () {
      this.isEdit = true;
    },
    /**
    * 取消编辑状态，并重置表单
    * @param {*void}
    * @author yezi 2019-08-28
    */
    cancelEdit () {
      this.isEdit = false;
      this.$refs["setting"].resetFields();
      this.pwdType = "password";
    },
    // 初始化数据
    finddata () {
      let that = this;
      this.axios({
        method: "post",
        url: "/cipher/auth/management/password/info",
        data: {
        }
      })
        .then(function (res) {
          if (res.data.code === 0) {
            // 显示数据
            that.init = res.data.msg.setting.init;
            if (res.data.msg.setting.passwordType === "1") {
              that.passwordtext = "简单（6位及6位以上密码，无特殊要求）";
            } else if (res.data.msg.setting.passwordType === "2") {
              that.passwordtext = "中等（8位及8位以上密码，至少包含数字+字母）";
            } else if (res.data.msg.setting.passwordType === "3") {
              that.passwordtext = "复杂 （8位及8位以上密码，至少包含数字+大写字母+小写字母）";
            }
            // 编辑数据
            that.setting = res.data.msg.setting;
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 改变密码框的类型
    changeType () {
      this.pwdType = this.pwdType === "password" ? "text" : "password";
      this.src =
        this.src === require("@/assets/colse_eyes.png")
          ? require("@/assets/eye.png")
          : require("@/assets/colse_eyes.png");
    },
    // 保存数据
    tosave (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.save();
        } else {
          this.$myMessage.error("表单验证失败");
        }
      });
    },

    save () {
      let params = {
        init: this.setting.init,
        passwordType: this.setting.passwordType
      };
      this.axios({
        method: "post",
        url: "/cipher/auth/management/password/save",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            this.finddata();
            this.$myMessage.success(res.data.msg);
            this.cancelEdit();
          } else if (res.data.code === 1) {
            this.$Modal.error({
              title: "提示",
              content: res.data.msg
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    }

  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
.img {
  height: 10px;
  width: 15px;
  margin-left: 8px;
}
.initpassword {
  width: 326px;
}
</style>
