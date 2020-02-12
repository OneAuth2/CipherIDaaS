<template>
  <div class="visitor-add formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">添加访客</div>
      <div class="formed-header-btn">
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <div class="formed-container">
      <Form class="formed-form"
            :model="formItem"
            :label-width="100"
            :rules="ruleValidate"
            ref="formValidate">
        <div class="formed-form-part">
          <div class="formed-form-part-title">账号基本信息</div>
          <FormItem label="访客姓名："
                    prop="name">
            <Input v-model.trim="formItem.name"
                   placeholder="请输入"></Input>
          </FormItem>
          <FormItem label="手机号："
                    prop="phone">
            <Input v-model.trim="formItem.phone"
                   placeholder="请输入"></Input>
          </FormItem>
          <FormItem label="登录密码："
                    prop="pwd">
            <Input v-model.trim="formItem.pwd"
                   placeholder="请输入"></Input>
          </FormItem>
          <FormItem label="登访客单位："
                    prop="unit">
            <Input v-model.trim="formItem.unit"
                   placeholder="请输入"></Input>
          </FormItem>
        </div>
        <div class="formed-form-part">
          <div class="formed-form-part-title">账号有效期</div>
          <FormItem label="开始时间："
                    prop="startTime">
            <date-picker type="datetime"
                         v-model="formItem.startTime"
                         placeholder="请选择开始时间"></date-picker>
          </FormItem>
          <FormItem label="结束时间："
                    prop="endTime">
            <date-picker type="datetime"
                         v-model="formItem.endTime"
                         placeholder="请选择结束时间"></date-picker>
          </FormItem>
        </div>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="handleSubmit('formValidate')">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: "VisitorAdd",
  data () {
    // 手机号校验
    const validatePhone = (rule, value, callback) => {
      let regx = /^[1][3,4,5,7,8][0-9]{9}$/;
      if (!regx.test(value)) {
        callback(new Error("手机号不正确，请重新输入"));
      }
      callback();
    };
    // 开始时间校验
    const validateStartTime = (rule, value, callback) => {
      let now = new Date();
      if (value <= now) {
        callback(new Error("开始时间不能小于或等于目前时间"));
      }
      callback();
    };
    // 开始时间校验
    const validateEndTime = (rule, value, callback) => {
      if (value < this.formItem.startTime) {
        callback(new Error("结束时间不能小于开始时间"));
      }
      callback();
    };
    return {
      formItem: {
        name: "", // 设备名称
        phone: "", // 手机号码
        pwd: "", // 登录密码
        unit: "", // 访客单位
        startTime: "", // 账号开始时间
        endTime: "", // 账号结束时间
        startTimeOption: {},
        endTimeOption: {}
      },
      ruleValidate: {
        name: [
          { required: true, message: "访客姓名不能为空", trigger: "blur" },
          { type: "string", max: 20, message: "不能超过20个字符", trigger: "blur" }
        ],
        phone: [
          { required: true, message: " 手机号不能为空", trigger: "blur" },
          { validator: validatePhone, trigger: "blur" }],
        pwd: [
          { required: true, message: "登录密码不能为空", trigger: "blur" },
          { type: "string", min: 6, message: "密码不能小于6位", trigger: "blur" },
          { type: "string", max: 20, message: "密码不能超过20个字符", trigger: "blur" }
        ],
        unit: [{ type: "string", max: 30, message: "不能超过30个字符", trigger: "blur" }],
        startTime: [
          { type: "date", required: true, message: " 账号有效期不能为空", trigger: "blur" },
          { validator: validateStartTime, trigger: "blur" }
        ],
        endTime: [
          { type: "date", required: true, message: " 账号有效期不能为空", trigger: "blur" },
          { validator: validateEndTime, trigger: "blur" }
        ]
      }

    };
  },
  mounted () {
    this.formItem.startTime = new Date();
    this.formItem.endTime = new Date() + 1;
  },
  methods: {
    /**
     * 点击取消，跳转页面到列表页
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.$router.push({ path: "/netAccessServiceWVM" });
    },
    /**
    * 点击保存执行，表单验证通过后，调用保存函数
    * @param {*String iview表单ref名称，表单验证所需} name
    * @author yezi 2019-08-02
    */
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.saveData();
        } else {
          this.$myMessage.error("表单验证失败，请重试");
        }
      });
    },
    /**
     * 保存数据，发送请求
     * @param {*void}
     * @author yezi 2019-08-02
     */
    saveData () {
      let GoldMantisUser = {
        loginName: this.formItem.name,
        password: this.formItem.pwd,
        mobile: this.formItem.phone,
        unit: this.formItem.unit,
        startTime: this.$common.formatDate(this.formItem.startTime),
        endTime: this.$common.formatDate(this.formItem.endTime)
      };
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/vistor/add",
        data: GoldMantisUser
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
            // 成功后 跳转回原先页面
            setTimeout(() => {
              this.$router.push("/netAccessServiceWVM");
            }, 1000);
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
