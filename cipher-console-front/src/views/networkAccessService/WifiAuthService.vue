<template>
  <div class="content">
    <div class="background">
      <Row>
        <Col span="12">
        <div class="spanDemo">
          <!-- <template v-if="acFlag==='edit'">编辑ac设置</template> -->
          <template v-if="flag==1">信息展示</template>
          <template v-else-if="flag==2">策略修改</template>
        </div>
        </Col>
        <Col span="12">
        <div class="btn"
             v-if="flag==1">
          <Button @click="flag=2"
                  style="background-color:#1890FF;border:none;margin-left: 8px;color:white">编辑</Button>
        </div>
        <div class="btn"
             v-if="flag==2">
          <Button @click="flag=1"
                  style="margin-left: 8px">取消</Button>
        </div>
        </Col>
      </Row>
    </div>
    <div class="height2">
      <div class="wifiAuthService"
           v-if="flag==2">
        <Form ref="formValidate"
              :model="formValidate"
              :rules="ruleValidate"
              :label-width="120">
          <div class="background1">
            <Row>
              <Col span="24">
              <div class="spanDemo">
                <!-- <template v-if="acFlag==='edit'">编辑ac设置</template> -->
                <template>访客策略</template>
              </div>
              </Col>
            </Row>
          </div>
          <FormItem label="访客有效期:"
                    prop="usefulLife"
                    class="widthSetting">
            <Input v-model="formValidate.usefulLife"
                   placeholder="请输入访客有效期"></Input>
            <span class="usefulLifeUnit">单位（小时）</span>
          </FormItem>
          <FormItem label="验证方式:"
                    prop="verificationMode"
                    class="widthSetting">
            <Select v-model="formValidate.verificationMode"
                    placeholder="请选择验证方式">
              <Option v-for="item in verificationModeList"
                      :value="item.value"
                      :key="item.value">{{item.label}}</Option>
            </Select>
            <!-- <Select v-model="device.type" :label-in-value="true" @on-change="v=>{ setOption(v,'type')}">
          <Option v-for="item in deviceTypeList" :value="item.value" :key="item">{{ item.label }}</Option>
            </Select>-->
          </FormItem>
          <FormItem label="员工帮助扫码:"
                    prop="scanCode"
                    class="widthSetting">
            <RadioGroup v-model="formValidate.scanCode">
              <Radio label="isScanCode">开启</Radio>
              <Radio label="noScanCode">关闭</Radio>
            </RadioGroup>
            <!-- <Checkbox v-model="formValidate.scanCode">员工帮助扫码</Checkbox> -->
          </FormItem>

          <div class="background1">
            <Row>
              <Col span="24">
              <div class="spanDemo">
                <!-- <template v-if="acFlag==='edit'">编辑ac设置</template> -->
                <template>员工策略</template>
              </div>
              </Col>
            </Row>
          </div>
          <FormItem prop="strategy"
                    class="widthSetting">
            <RadioGroup v-model="formValidate.strategy"
                        @on-change="changeRadio">
              <Radio label="strategyWifi">定义无线认证策略</Radio>
              <Radio label="strategySys">系统认证策略</Radio>
            </RadioGroup>
          </FormItem>

          <FormItem label="认证方式:"
                    prop="authMethod"
                    class="widthSetting">
            <Select v-model="formValidate.authMethod"
                    placeholder="请选择认证方式"
                    :disabled="disable">
              <Option v-for="item in authMethodList"
                      :value="item.value"
                      :key="item.value">{{item.label}}</Option>
            </Select>
          </FormItem>
          <FormItem label="扫码登录:"
                    prop="scanLogin"
                    class="widthSetting">
            <Select v-model="formValidate.scanLogin"
                    placeholder="请选择扫码登录"
                    :disabled="disable">
              <Option v-for="item in scanLoginList"
                      :value="item.value"
                      :key="item.value">{{item.label}}</Option>
            </Select>
          </FormItem>
          <FormItem label="开启mac认证:"
                    prop="openMac"
                    class="widthSetting">
            <RadioGroup v-model="formValidate.openMac">
              <Radio label="openMac">开启</Radio>
              <Radio label="noOpenMac">关闭</Radio>
            </RadioGroup>
            <!-- <Checkbox v-model="formValidate.openMac">开启mac认证</Checkbox> -->
          </FormItem>
          <FormItem style="text-align: left;margin-left: 20%;">
            <Button type="primary"
                    @click="handleSubmit('formValidate')">保存</Button>
            <Button @click="flag=1"
                    style="margin-left: 8px">取消</Button>
          </FormItem>
        </Form>
      </div>
      <div v-else>
        <div>
          <Row>
            <Col span="24"
                 class="titleSetting">访客策略</Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">访客有效期：</Col>
            <Col span="21"
                 class="fontSetting2">{{vistorInfo.effectiveTime}}小时</Col>
            </Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">验证方式：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="vistorInfo.type===1">短信密码（访客接收密码）</div>
            <div v-else-if="vistorInfo.type===2">邮件密码（访客接收密码）</div>
            <div v-else-if="vistorInfo.type===3">短信密码（员工接收密码）</div>
            <div v-else-if="vistorInfo.type===4">邮件密码（员工接收密码）</div>
            </Col>
            </Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">员工帮忙扫码认证：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="vistorInfo.isStaffHelp===1">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#00A854;">●</span>
              已开启
            </div>
            <div v-else-if="vistorInfo.isStaffHelp===0">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#FF0000;">●</span>
              已关闭
            </div>
            </Col>
            </Col>
          </Row>
        </div>
        <hr style="background-color: rgb(204, 204, 204);margin-left:15px;margin-right:15px">
        <div>
          <Row>
            <Col span="24"
                 class="titleSetting">员工策略</Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">认证策略：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="staffInfo.strategyType===1">无线认证策略</div>
            <div v-else>系统认证策略</div>
            </Col>
            </Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">认证方式：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="staffInfo.authType===1">用户名+密码+动态码</div>
            <div v-else-if="staffInfo.authType===2">用户名+动态密码</div>
            <div v-else-if="staffInfo.authType===3">用户名+密码</div>
            </Col>
            </Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">扫码登录：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="staffInfo.scaveAuthState===1">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#00A854;">●</span>
              已开启
            </div>
            <div v-else-if="staffInfo.scaveAuthState===2">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#FF0000;">●</span>
              已关闭
            </div>
            </Col>
            </Col>
            <Col span="24"
                 class="padingSetting">
            <Col span="3"
                 class="fontSetting1">MAC认证：</Col>
            <Col span="21"
                 class="fontSetting2">
            <div v-if="staffInfo.isMac===1">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#00A854;">●</span>
              已开启
            </div>
            <div v-else-if="staffInfo.isMac===0">
              <span style="font-family:'LucidaGrande', 'Lucida Grande';color:#FF0000;">●</span>
              已关闭
            </div>
            </Col>
            </Col>
          </Row>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import api from "@/api/networkAccessService/index";

export default {
  data () {
    const validateUsefulLife = (rule, value, callback) => {
      if (value === "") {
        return callback(new Error("访客有效期不能为空"));
      }
      var re = /^\d+$/;// 判断字符串是否为数字//判断正整数/[1−9]+[0−9]∗]∗/
      if (!re.test(value)) {
        return callback(new Error("请输入数字！！！"));
      }
      callback();
    };
    const validateVerificationMode = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择验证方式"));
      }
      callback();
    };
    const validateStrategy = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择策略"));
      }
      callback();
    };
    const validateAuthMethod = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择认证方式"));
      }
      callback();
    };

    const validateScanLogin = (rule, value, callback) => {
      if (!value) {
        return callback(new Error("请选择是否扫码登录"));
      }
      callback();
    };
    return {
      disable: false,
      flag: 1,
      formValidate: {
        usefulLife: "",
        verificationMode: "",
        scanCode: 0,
        strategy: "",
        authMethod: "",
        scanLogin: "",
        openMac: ""
      },
      data: [],
      ruleValidate: {
        usefulLife: [{ validator: validateUsefulLife, trigger: "blur" }],
        verificationMode: [
          { validator: validateVerificationMode, trigger: "blur" }
        ],
        strategy: [{ validator: validateStrategy, trigger: "blur" }],
        authMethod: [{ validator: validateAuthMethod, trigger: "blur" }],
        scanLogin: [{ validator: validateScanLogin, trigger: "blur" }]
      },
      vistorInfo: {
        effectiveTime: "",
        isStaffHelp: "",
        type: "",
        vistorId: ""
      },
      staffInfo: {
        authType: "",
        isMac: "",
        scaveAuthState: "",
        staffId: "",
        strategyType: ""
      },
      verificationModeList: [
        { value: 1, label: "短信密码（访客接收密码）" },
        { value: 2, label: "邮件密码（访客接收密码）" },
        { value: 3, label: "短信密码（员工接收密码）" },
        { value: 4, label: "邮件密码（员工接收密码）" }
      ],
      authMethodList: [
        { value: 1, label: "用户名+密码+动态码" },
        { value: 2, label: "用户名+动态密码" },
        { value: 3, label: "用户名+密码" }
      ],

      scanLoginList: [{ value: 1, label: "开启" }, { value: 2, label: "关闭" }]
    };
  },
  mounted () {
    this.getList();
  },
  watch: {
    data () {
      this.formValidate = {
        usefulLife: this.data.vistorInfo.effectiveTime,
        verificationMode: this.data.vistorInfo.type,
        scanCode:
          this.data.vistorInfo.isStaffHelp === 1 ? "isScanCode" : "noScanCode",
        strategy:
          this.data.staffInfo.strategyType === 1
            ? "strategyWifi"
            : "strategySys",
        authMethod: this.data.staffInfo.authType,
        scanLogin: this.data.staffInfo.scaveAuthState,
        openMac: this.data.staffInfo.isMac === 1 ? "openMac" : "noOpenMac"
      };
      // console.log("this.formValidate");
      // console.log(this.formValidate);
    }
  },
  methods: {
    changeRadio () {
      if (this.formValidate.strategy === "strategySys") {
        this.disable = true;
        this.formValidate.authMethod = 2;
        this.formValidate.scanLogin = 2;
      } else {
        this.disable = false;
        this.formValidate.authMethod = 1;
        this.formValidate.scanLogin = 1;
      }
    },
    handleSubmit (name) {
      this.$refs[name].validate(valid => {
        if (valid) {
          this.save();
        } else {
          this.$myMessage.error("表单验证失败，请重试");
        }
      });
    },
    // 获取信息列表
    getList () {
      let params = {};
      let _this = this;
      this.axios({
        method: "post",
        data: params,
        url: api.wifiauthList
      })
        .then(function (response) {
          // console.log(response);
          // 成功发送跳转
          if (response.status === api.statusOk) {
            _this.data = _this.$common.clone(response.data, _this);
            _this.staffInfo = response.data.staffInfo;
            _this.vistorInfo = response.data.vistorInfo;
            // console.log(_this.data);
          } else {
            _this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          _this.axios.error.handlingErrors(error, _this);
        });
    },
    // 保存信息
    save () {
      let params = {
        effectiveTime: this.formValidate.usefulLife,
        type: this.formValidate.verificationMode,
        isStaffHelp: this.formValidate.scanCode === "isScanCode" ? 1 : 0,
        strategyType: this.formValidate.strategy === "strategyWifi" ? 1 : 2,
        authType: this.formValidate.authMethod,
        scaveAuthState: this.formValidate.scanLogin,
        isMac: this.formValidate.openMac === "openMac" ? 1 : 0,
        staffId: this.data.staffInfo.staffId,
        vistorId: this.data.vistorInfo.vistorId
      };
      let _this = this;
      this.axios({
        method: "post",
        data: params,
        url: api.wifiauthUpdate
      })
        .then(function (response) {
          //   console.log(response);
          _this.reminderModal();
        })
        .catch(function (error) {
          _this.axios.error.handlingErrors(error, _this);
        });
    },
    // 提示弹窗
    reminderModal () {
      const title = "提示";
      const content = "<p>提示更新wifi策略配置成功</p>";
      this.$Modal.success({
        title: title,
        content: content,
        onOk: () => {
          this.flag = 1;
          this.getList();
        }
      });
    }
  }
};
</script>

<style scoped>
.widthSetting {
  width: 30%;
}
.padingSetting {
  padding: 15px;
}
.fontSetting1 {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: #1e1e1e;
  text-align: right;
  line-height: 22px;
}
.fontSetting2 {
  font-family: "PingFangSC-Regular", "PingFang SC";
  font-weight: 400;
  font-style: normal;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.647058823529412);
  text-align: left;
  line-height: 22px;
}

.titleSetting {
  font-family: "PingFangSC-Semibold", "PingFang SC Semibold", "PingFang SC";
  font-weight: 650;
  font-style: normal;
  color: #1e1e1e;
  font-size: 14px;
  padding: 20px;
  text-align: left;
}
.content {
  height: 100%-20px;
}
.btn {
  text-align: right;
}
.background1 {
  background-color: #fafafa;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(233, 233, 233, 1);
  border-radius: 2px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
  white-space: nowrap;
  padding: 10px;
  height: 50px;
  margin-top: 10px;
}
.background {
  background-color: #fafafa;
  border-width: 1px;
  border-style: solid;
  border-color: rgba(233, 233, 233, 1);
  border-radius: 2px;
  border-bottom-right-radius: 0px;
  border-bottom-left-radius: 0px;
  white-space: nowrap;
  padding: 10px;
  height: 50px;
}
.spanDemo {
  font-family: "PingFangSC-Semibold", "PingFang SC Semibold", "PingFang SC";
  font-weight: 650;
  font-style: normal;
  font-size: 14px;
  color: rgba(0, 0, 0, 0.847058823529412);
  text-align: left;
}
.wifiAuthService {
  width: 100%;
  margin: auto;
  height: calc(100%);
  overflow-y: auto;
}
.title >>> div {
  font-size: 18px;
}
.usefulLifeUnit {
  position: absolute;
  min-width: 90px;
}
.height2 {
  height: calc(100% - 50px);
  overflow-y: auto;
}
</style>
