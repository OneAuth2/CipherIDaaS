<template>
  <div>
    <Form ref="formValidate"
          :model="formValidate"
          :rules="ruleValidate"
          :label-width="120">
      <div class="config-title">设备参数：</div>
      <FormItem label="设备型号："
                prop="acDeviceId">
        <Select v-model.trim="formValidate.acDeviceId"
                placeholder="请选择AC设备型号">
          <Option v-for="item in acDeviceIdList"
                  :value="item.value"
                  :key="item.value">{{item.label}}</Option>
        </Select>
      </FormItem>
      <div v-show="formValidate.acDeviceId!==3&&formValidate.acDeviceId!==4">
        <FormItem label="AC设备地址："
                  prop="deviceAddress">
          <Input v-model.trim="formValidate.deviceAddress"
                 placeholder="请输入设备地址"></Input>
        </FormItem>
        <FormItem label="AC监听端口："
                  prop="monitorPort">
          <Input v-model.trim="formValidate.monitorPort"
                 placeholder="请输入监听端口"></Input>
        </FormItem>
        <FormItem label="协议版本号："
                  prop="protocolVersion">
          <Input v-model.trim="formValidate.protocolVersion"
                 placeholder="请输入协议版本号"></Input>
        </FormItem>
        <FormItem label="认证类型："
                  prop="authType">
          <Input v-model.trim="formValidate.authType"
                 placeholder="请输入认证类型"></Input>
        </FormItem>
        <FormItem label="超时等待时间："
                  prop="timeOut">
          <Input v-model.trim="formValidate.timeOut"
                 placeholder="请输入超时等待时间"></Input>
        </FormItem>
      </div>
      <!-- 选择aruba -->
      <div v-show="formValidate.acDeviceId===3">
        <FormItem label="switch_url：">
          <Input v-model.trim="formValidate.switchUrlAruba"
                 placeholder="请输入switch_url"></Input>
        </FormItem>
        <FormItem label="orig_url：">
          <Input v-model.trim="formValidate.origUrl"
                 placeholder="请输入orig_url"></Input>
        </FormItem>
        <FormItem label="opcode：">
          <Input v-model.trim="formValidate.opcode"
                 placeholder="请输入opcode"></Input>
        </FormItem>
        <FormItem label="agree：">
          <Input v-model.trim="formValidate.agree"
                 placeholder="请输入agree"></Input>
        </FormItem>
      </div>
      <div v-show="formValidate.acDeviceId===4">
        <FormItem label="switch_url：">
          <Input v-model.trim="formValidate.switchUrlCisco"></Input>
        </FormItem>
        <FormItem label="buttonClicked：">
          <Input v-model.trim="formValidate.buttonClicked"></Input>
        </FormItem>
        <FormItem label="err_flag：">
          <Input v-model.trim="formValidate.errFlag"></Input>
        </FormItem>
        <FormItem label="err_msg：">
          <Input v-model.trim="formValidate.errMsg"></Input>
        </FormItem>
        <FormItem label="info_flag：">
          <Input v-model.trim="formValidate.infoFlag"></Input>
        </FormItem>
        <FormItem label="info_msg：">
          <Input v-model.trim="formValidate.infoMsg"></Input>
        </FormItem>
        <FormItem label="redirect_url：">
          <Input v-model.trim="formValidate.redirectUrl"></Input>
        </FormItem>
        <FormItem label="network_name：">
          <Input v-model.trim="formValidate.networkName"></Input>
        </FormItem>
      </div>
    </Form>
    <Divider />
    <Form ref="formValidateRadius"
          :model="formValidate"
          :rules="ruleValidateRadius"
          :label-width="120">
      <div class="config-title">Radius配置：</div>
      <FormItem label="协议类型："
                prop="protocolType">
        <RadioGroup v-model="formValidate.protocolType">
          <Radio label="1">CHAP</Radio>
          <Radio label="2">PAP</Radio>
        </RadioGroup>
      </FormItem>
      <FormItem label="Radius地址：："
                prop="ip">
        <Input v-model.trim="formValidate.ip"
               placeholder="请输入Radius地址"></Input>
      </FormItem>
      <FormItem label="认证端口："
                prop="authPort">
        <Input v-model.trim="formValidate.authPort"
               placeholder="请输入认证端口"></Input>
      </FormItem>
      <FormItem label="计费端口："
                prop="feePort">
        <Input v-model.trim="formValidate.feePort"
               placeholder="请输入计费端口"></Input>
      </FormItem>
      <FormItem label="共享密钥："
                prop="sharedKey">
        <Input v-model.trim="formValidate.sharedKey"
               placeholder="请输入共享密钥"></Input>
      </FormItem>
    </Form>
  </div>
</template>
<script>
export default {
  data () {
    return {
      formValidate: {
        // 设备参数
        acDeviceId: "",
        // 通用设备参数
        deviceAddress: "",
        monitorPort: "",
        protocolVersion: "",
        authType: "",
        timeOut: "",
        // aruba 参数
        switchUrlAruba: "", // aruba和cisco共有参数
        origUrl: "",
        opcode: "",
        agree: "",
        // cisco参数
        switchUrlCisco: "",
        buttonClicked: "",
        errFlag: "",
        errMsg: "",
        infoFlag: "",
        infoMsg: "",
        redirectUrl: "",
        networkName: "",

        // Radius配置参数
        protocolType: null,
        ip: "",
        authPort: "",
        feePort: "",
        sharedKey: ""
      },
      ruleValidate: {
        acDeviceId: [{ required: true, message: "请选择AC设备型号", trigger: "blue", type: "number" }],
        deviceAddress: [{ required: true, message: "请输入设备地址", trigger: "blur" }],
        monitorPort: [{ required: true, message: "输入监听端口", trigger: "blur" }],
        authType: [{ required: true, message: "请输入认证类型", trigger: "blur" }],
        timeOut: [{ required: true, message: "请输入超时等待时间", trigger: "blur" }]
      },
      ruleValidateRadius: {
        protocolType: [{ required: true, message: "请选择协议类型", trigger: "blur" }],
        ip: [{ required: true, message: "请输入Radius地址", trigger: "blur" }],
        authPort: [{ required: true, message: "请输入认证端口", trigger: "blur" }],
        feePort: [{ required: true, message: "请输入计费端口", trigger: "blur" }],
        sharedKey: [{ required: true, message: "请输入共享秘钥", trigger: "blur" }]
      },
      acDeviceIdList: [// ac设备型号列表
        { label: "HUAWEI", value: 1 },
        { label: "H3C", value: 2 },
        { label: "Aruba", value: 3 },
        { label: "Cisco", value: 4 },
        { label: "锐捷", value: 5 }
      ]
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
      this.$refs["formValidateRadius"].validate(valid => { // 验证radius表单
        flag = valid;
        let deviceFlag = true; // 存储设备表单验证结果
        if (this.formValidate.acDeviceId !== 3 && this.formValidate.acDeviceId !== 4) { // 如果不是aruba和cisco，需要验证设备表单
          this.$refs["formValidate"].validate(valid => {
            deviceFlag = valid;
          });
        }
        flag = flag && deviceFlag;
        if (flag) {
          this.saveData();
          this.$emit("ListenChild", this.formValidate); // 验证通过将数据传递到父组件
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
      let deviceConfig = {};
      if (this.formValidate.acDeviceId === 3) {
        deviceConfig = {
          switchUrl: this.formValidate.switchUrlAruba,
          origUrl: this.formValidate.origUrl,
          opcode: this.formValidate.opcode,
          agree: this.formValidate.agree
        };
      } else if (this.formValidate.acDeviceId === 4) {
        deviceConfig = {
          switchUrl: this.formValidate.switchUrlCisco,
          buttonClicked: this.formValidate.buttonClicked,
          errFlag: this.formValidate.errFlag,
          errMsg: this.formValidate.errMsg,
          infoFlag: this.formValidate.infoFlag,
          infoMsg: this.formValidate.infoMsg,
          redirectUrl: this.formValidate.redirectUrl,
          networkName: this.formValidate.networkName
        };
      } else {
        deviceConfig = {
          deviceAddress: this.formValidate.deviceAddress,
          monitorPort: this.formValidate.monitorPort,
          protocolVersion: this.formValidate.protocolVersion,
          authType: this.formValidate.authType,
          timeOut: this.formValidate.timeOut
        };
      }
      let params = {
        acDeviceId: this.formValidate.acDeviceId,
        deviceConfig: JSON.stringify(deviceConfig),
        protocolType: this.formValidate.protocolType,
        ip: this.formValidate.ip,
        port: this.formValidate.authPort,
        feePort: this.formValidate.feePort,
        sharedKey: this.formValidate.sharedKey
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wifiportal/addTwo"
      })
        .then(res => {
          // 成功发送跳转
          if (res.return_code === 1) {
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
@import "~@/assets/styles/formStyle.less";
</style>
