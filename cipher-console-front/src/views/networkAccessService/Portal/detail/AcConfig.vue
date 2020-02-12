<template>
  <div class="formed-wrap">
    <transition name="fade"
                mode='out-in'>
      <div class="formed-form"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="formed-form-part">
          <div class="formed-form-part-title">设备参数</div>
          <div class="formed-form-item">
            <span>设备型号：</span>
            <span>{{acDeviceIdList[showData.acDeviceId-1].label}}</span>
          </div>
          <template v-if="showData.acDeviceId!==3&&showData.acDeviceId!==4">
            <div class="formed-form-item">
              <span>AC设备地址：</span>
              <span>{{showData.deviceConfig.deviceAddress}}</span>
            </div>
            <div class="formed-form-item">
              <span>AC监听端口：</span>
              <span>{{showData.deviceConfig.monitorPort}}</span>
            </div>
            <div class="formed-form-item">
              <span>协议版本号：</span>
              <span>{{showData.deviceConfig.protocolVersion}}</span>
            </div>
            <div class="formed-form-item">
              <span>认证类型：</span>
              <span>{{showData.deviceConfig.authType}}</span>
            </div>
            <div class="formed-form-item">
              <span>超时等待时间：</span>
              <span>{{showData.deviceConfig.timeOut}}</span>
            </div>
          </template>
          <!-- 选择aruba -->
          <template v-if="showData.acDeviceId===3">
            <div class="formed-form-item">
              <span>switch_url：</span>
              <span>{{showData.deviceConfig.switchUrl}}</span>
            </div>
            <div class="formed-form-item">
              <span>orig_url：</span>
              <span>{{showData.deviceConfig.origUrl}}</span>
            </div>
            <div class="formed-form-item">
              <span>opcode：</span>
              <span>{{showData.deviceConfig.opcode}}</span>
            </div>
            <div class="formed-form-item">
              <span>agree：</span>
              <span>{{showData.deviceConfig.agree}}</span>
            </div>
          </template>
          <!-- 选择cisco -->
          <template v-if="showData.acDeviceId===4">
            <div class="formed-form-item">
              <span>switch_url：</span>
              <span>{{showData.deviceConfig.switchUrl}}</span>
            </div>
            <div class="formed-form-item">
              <span>buttonClicked：</span>
              <span>{{showData.deviceConfig.buttonClicked}}</span>
            </div>
            <div class="formed-form-item">
              <span>err_flag：</span>
              <span>{{showData.deviceConfig.errFlag}}</span>
            </div>
            <div class="formed-form-item">
              <span>err_msg：</span>
              <span>{{showData.deviceConfig.errMsg}}</span>
            </div>
            <div class="formed-form-item">
              <span>info_flag：</span>
              <span>{{showData.deviceConfig.infoFlag}}</span>
            </div>
            <div class="formed-form-item">
              <span>info_msg：</span>
              <span>{{showData.deviceConfig.infoMsg}}</span>
            </div>
            <div class="formed-form-item">
              <span>redirect_url：</span>
              <span>{{showData.deviceConfig.redirectUrl}}</span>
            </div>
            <div class="formed-form-item">
              <span>network_name：</span>
              <span>{{showData.deviceConfig.networkName}}</span>
            </div>
          </template>
        </div>
        <div class="formed-form-part">
          <div class="formed-form-part-title">Radius配置</div>
          <div class="formed-form-item">
            <span>协议类型：</span>
            <span>{{showData.radiusConfigDomain.protocolType===1?"CHAP":"PAP"}}</span>
          </div>
          <div class="formed-form-item">
            <span>Radius地址：</span>
            <span>{{showData.radiusConfigDomain.ip}}</span>
          </div>
          <div class="formed-form-item">
            <span>认证端口：</span>
            <span>{{showData.radiusConfigDomain.port}}</span>
          </div>
          <div class="formed-form-item">
            <span>计费端口：</span>
            <span>{{showData.radiusConfigDomain.feePort}}</span>
          </div>
          <div class="formed-form-item">
            <span>共享密钥：</span>
            <span>{{showData.radiusConfigDomain.sharedKey}}</span>
          </div>
        </div>
      </div>
      <!--编辑部分-->
      <div class="formed-form"
           v-else
           key='edit'>
        <Form class="formed-form-part"
              ref="formValidate"
              :model="formValidate"
              :rules="ruleValidate"
              :label-width="120">
          <div class="formed-form-part-title">设备参数：</div>
          <FormItem label="设备型号："
                    prop="acDeviceId">
            <Select v-model.trim="formValidate.acDeviceId"
                    placeholder="请选择AC设备型号">
              <Option v-for="item in acDeviceIdList"
                      :value="item.value"
                      :label="item.label"
                      :key="item.value"></Option>
            </Select>
          </FormItem>
          <div v-show="formValidate.acDeviceId !==3&&formValidate.acDeviceId !==4">
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
          <div v-show="formValidate.acDeviceId ===3">
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
          <div v-show="formValidate.acDeviceId ===4">
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
        <Form class="formed-form-part"
              ref="formValidateRadius"
              :model="formValidate"
              :rules="ruleValidateRadius"
              :label-width="120">
          <div class="formed-form-title">Radius配置：</div>
          <FormItem label="协议类型："
                    prop="protocolType">
            <RadioGroup v-model="formValidate.protocolType">
              <Radio v-for="item in  protocolTypeList"
                     :label="item.value"
                     :key="item.value">{{item.label}}</Radio>
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
    </transition>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="handleSubmit('formValidate')">保存</myButton>
      <myButton @click="cancel">取消</myButton>
    </div>
  </div>

</template>
<script>
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      message: {
        text: "保存成功!",
        show: false,
        duration: 1,
        onClose: this.close
      },
      selectType: "", // 设备型号选择
      showData: {},
      formValidate: {
        id: "",
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
        protocolType: "",
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
        protocolType: [{ required: true, message: "请选择协议类型", trigger: "blur", type: "number" }],
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
      ],
      protocolTypeList: [// ac设备型号列表
        { label: "CHAP", value: 1 },
        { label: "PAP", value: 2 }
      ]
    };
  },
  props: ["data"],
  created () {
    let data = this.$props.data;
    this.getData(data);
  },
  methods: {
    /**
     * 进入编辑状态，并重新获取数据赋值
     * @param {*void}
     * @author yezi 2019-08-02
     */
    edit () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "ac"
      });
      this.getData(this.$props.data);
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancel () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "ac"
      });
    },
    /**
     * 获取数据并赋值
     * @param {*Object 基本配置信息，从父组件传递的数据} data
     * @author yezi 2019-08-02
     */
    getData (data) {
      let temp = { acDeviceId: "", acId: "", deviceConfig: {}, radiusConfigDomain: "" };
      temp.acDeviceId = data.acSetInfo.acDeviceId;
      temp.deviceConfig = JSON.parse(data.acSetInfo.deviceConfig);
      temp.radiusConfigDomain = data.radiusConfigDomain;
      this.showData = JSON.parse(JSON.stringify(temp));
      this.formValidate.id = data.wifiPotalPageSettingInfo.id;
      this.formValidate.acDeviceId = data.acSetInfo.acDeviceId;
      this.formValidate.acId = data.wifiPotalPageSettingInfo.acId;
      let deviceConfig = JSON.parse(data.acSetInfo.deviceConfig);
      if (this.formValidate.acDeviceId === 3) {
        this.formValidate.switchUrlAruba = deviceConfig.switchUrl;
        this.formValidate.origUrl = deviceConfig.origUrl;
        this.formValidate.opcode = deviceConfig.opcode;
        this.formValidate.agree = deviceConfig.agree;
      } else if (this.formValidate.acDeviceId === 4) {
        this.formValidate.switchUrlCisco = deviceConfig.switchUrl;
        this.formValidate.buttonClicked = deviceConfig.buttonClicked;
        this.formValidate.errFlag = deviceConfig.errFlag;
        this.formValidate.errMsg = deviceConfig.errMsg;
        this.formValidate.infoFlag = deviceConfig.infoFlag;
        this.formValidate.infoMsg = deviceConfig.infoMsg;
        this.formValidate.redirectUrl = deviceConfig.redirectUrl;
        this.formValidate.networkName = deviceConfig.networkName;
      } else {
        this.formValidate.deviceAddress = deviceConfig.deviceAddress;
        this.formValidate.monitorPort = deviceConfig.monitorPort;
        this.formValidate.protocolVersion = deviceConfig.protocolVersion;
        this.formValidate.authType = deviceConfig.authType;
        this.formValidate.timeOut = deviceConfig.timeOut;
      }
      this.formValidate.radiusId = data.radiusConfigDomain.id;
      this.formValidate.protocolType = data.radiusConfigDomain.protocolType;
      this.formValidate.ip = data.radiusConfigDomain.ip;
      this.formValidate.authPort = data.radiusConfigDomain.port.toString();
      this.formValidate.feePort = data.radiusConfigDomain.feePort.toString();
      this.formValidate.sharedKey = data.radiusConfigDomain.sharedKey;
    },
    /**
     * 提交数据并验证，验证成功执行保存数据函数
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit () {
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
      });

      if (flag) {
        this.saveData();
      } else {
        this.$myMessage.error("表单验证失败，请重试");
      }
    },
    /**
     * 保存数据，发送请求
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
        portalId: this.formValidate.id,
        acId: this.formValidate.acId,
        radiusId: this.formValidate.radiusId,
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
        url: "/cipher/wifiportal/updateTwo"
      })
        .then(res => {
          // 成功发送跳转
          if (res.data.return_code === 0) {
            this.$myMessage.success(res.data.return_msg);
            this.cancel();
            this.$emit("ListenChild", this.formValidate); // 验证通过将数据传递到父组件
          } else {
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

/* 显示动画样式 */
.fade-enter {
  opacity: 0;
} /* 显示的开始样式*/
.fade-enter-active {
  transition: opacity 0.5s;
} /* 显示的过度 */
.fade-enter-to {
  opacity: 1;
} /* 显示的结束样式*/
/* 隐藏动画样式 */
.fade-leave {
  opacity: 1;
}
.fade-leave-active {
  transition: opacity 0.5s;
}
.fade-leave-to {
  opacity: 0;
}
</style>
