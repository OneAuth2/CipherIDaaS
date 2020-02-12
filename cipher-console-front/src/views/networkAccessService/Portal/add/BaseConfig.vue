<template>
  <Form ref="formValidate"
        :model="formValidate"
        :rules="ruleValidate">
    <FormItem label="Portal类型："
              :label-width="120">
      <RadioGroup v-model="formValidate.portalType">
        <Radio label="0">员工Portal</Radio>
        <Radio label="1">访客Portal</Radio>
      </RadioGroup>
    </FormItem>
    <FormItem label="portal描述："
              prop="description"
              :label-width="120">
      <Input v-model.trim="formValidate.description"
             placeholder="请输入描述" />
    </FormItem>
    <div class="address">
      <FormItem label="portal地址："
                prop="ip"
                :label-width="120">
        <span>http:// </span>
        <Input v-model.trim="formValidate.ip"
               placeholder="请输入IP地址或域名"
               style="width:185px"
               @on-focus="portalAddress='ip'" />
      </FormItem>
      <FormItem prop="port">
        <span> ： </span>
        <Input v-model.trim="formValidate.port"
               placeholder="请输入端口号"
               style="width:100px"
               @on-focus="portalAddress='port'" />
      </FormItem>
    </div>
    <FormItem label="portal序号："
              prop="portalNum"
              :label-width="120">
      <Input v-model.trim="formValidate.portalNum"
             placeholder="请输入1-99" />
    </FormItem>
  </Form>
</template>

<script>
export default {
  data () {
    const validatePortalNum = (rule, value, callback) => {
      if (value < 1 || value > 99) {
        callback(new Error("请输入1-99"));
      } else {
        callback();
      }
    };
    return {
      isEdit: false, // 是否编辑状态
      portalAddress: "",
      formValidate: {
        protalType: "0",
        description: "",
        ip: "",
        port: "",
        portalNum: ""
      },
      ruleValidate: {
        description: [{ required: true, message: "请输入描述", trigger: "blur" }],
        ip: [{ required: true, message: "请输入地址", trigger: "blur" }],
        port: [{ required: true, message: "请输入地址", trigger: "blur" }],
        portalNum: [
          { required: true, message: "请输入1-99", trigger: "blur" },
          { validator: validatePortalNum, trigger: "blur" }
        ]
      }
    };
  },
  methods: {
    /**
     * 校验表单数据，成功返回true并执行保存数据函数
     * @param {*void}
     * @author yezi 2019-08-02
     */
    validateForm () {
      let flag = null;
      this.$refs["formValidate"].validate(valid => {
        if (valid) {
          flag = true;
          this.saveData();
          this.$emit("ListenChild", this.formValidate); // 验证通过将数据传递到父组件
        } else {
          flag = false;
        }
      });
      return flag;
    },
    /**
     * 保存数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    saveData () {
      let params = {
        portalAddress: `http://${this.formValidate.ip}:${this.formValidate.port}`,
        description: this.formValidate.description,
        portalNum: this.formValidate.portalNum
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wifiportal/addOne"
      })
        .then(res => {
          // 成功发送跳转
          if (res.data.return_code === 1) {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    }
  }
};
</script>

<style lang="less" scoped>
.address {
  text-align: left;
  /deep/ .ivu-form-item {
    display: inline-block;
  }
}
</style>
