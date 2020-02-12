<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">添加免证设备</div>
      <myButton class="formed-header-btn"
                @click="cancel">取消</myButton>
    </div>
    <div class="formed-container">
      <Form class="formed-form"
            :model="formItem"
            :label-width="100"
            :rules="ruleValidate"
            ref="formValidate">
        <FormItem label="设备名称："
                  prop="name">
          <Input v-model.trim="formItem.name"
                 placeholder="请输入"
                 style="width:300px" />
        </FormItem>
        <FormItem label="用途："
                  prop="use">
          <Input v-model.trim="formItem.use"
                 placeholder="请输入"
                 style="width:300px" /></Input>
        </FormItem>
        <FormItem label="MAC地址："
                  prop="macAddress">
          <Input v-model.trim="formItem.macAddress"
                 placeholder="请输入"
                 style="width:300px"></Input>
        </FormItem>
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
import verify from "@/util/verify.js";
import { setTimeout } from "timers";
export default {
  name: "WFDAdd",
  data () {
    // mac地址校验
    const validateMacAddress = (rule, value, callback) => {
      let regx = verify.macAddress;
      value = value.replace(/^\s+|\s+$/g, ""); // 去除首尾空格
      if (!regx.test(value)) {
        callback(new Error("mac格式:XX XX XX XX XX XX或者XXXX XXXX XXXX，" +
          "中间请用 \" . : - / 或者空格\" 其中一种隔开"));
      }
      callback();
    };
    return {
      formItem: {
        name: "", // 设备名称
        use: "", // 用途
        macAddress: "" // MAC地址
      },
      ruleValidate: {
        name: [{ required: true, message: "设备名不能为空", trigger: "blur" }],
        use: [{ required: true, message: "用途不能为空", trigger: "blur" }],
        macAddress: [
          { required: true, message: "mac地址不能为空", trigger: "blur" },
          { validator: validateMacAddress, trigger: "blur" }
        ]
      }

    };
  },
  methods: {
    /**
     * 点击取消，跳转页面到列表页
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.$router.push({ path: "/netAccessServiceWNVD" });
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
      let AcAclInfo = {
        name: this.formItem.name,
        mac: this.formItem.macAddress,
        application: this.formItem.use
      };
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/acl/add",
        data: AcAclInfo
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
            // 成功后 跳转回原先页面
            setTimeout(() => {
              this.$router.push("/netAccessServiceWNVD");
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
