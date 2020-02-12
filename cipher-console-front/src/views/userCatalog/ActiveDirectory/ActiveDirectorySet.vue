<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">配置活动目录</div>
      <Button @click="cancel"
              class="formed-header-btn">取消</Button>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <div class="formed-form-part-title">
          <Steps :current="currentStep"
                 direction="vertical">
            <Step title="连接配置"
                  content="配置连接活动目录的方式和参数"></Step>
            <Step title="同步选项"
                  content="配置同步组织结构"></Step>
            <Step title="属性映射"
                  content="配置与AD的属性映射关系"></Step>
          </Steps>
        </div>
        <Form ref="formdata"
              :model="formdata"
              :rules="ruleValidate"
              :label-width="120"
              v-if="currentStep===0">
          <FormItem label="地址："
                    prop="ip">
            <Input placeholder
                   v-model="formdata.ip"
                   readonly />
          </FormItem>
          <FormItem label="端口："
                    prop="port">
            <Input placeholder
                   v-model="formdata.port"
                   readonly />
          </FormItem>
          <FormItem label="用户名："
                    prop="userName">
            <Input placeholder
                   v-model="formdata.userName"
                   readonly />
          </FormItem>
          <FormItem label="密码："
                    prop="password">
            <Input placeholder
                   type="password"
                   v-model="formdata.password"
                   readonly />
          </FormItem>
        </Form>
        <Form ref="formdata"
              :model="formdata"
              :rules="ruleValidate"
              :label-width="120"
              v-if="currentStep===1">
          <FormItem label="组织结构：">
            <RadioGroup v-model="formdata.syncOu" @on-change="radiaChange" >
              <Radio label="1">同步组织结构</Radio>
              <Tooltip content="同步时会将AD组织结构与账号一并同步进来"
                       placement="bottom"
                       theme="light">
                <Icon type="md-alert"
                      size="24"
                      color="rgba(24, 144, 255, 1)"
                      class="iconInterVal" />
              </Tooltip>
              <Radio label="2">不同步组织结构</Radio>
              <Tooltip content="同步时只将AD中的账号同步进来"
                       placement="bottom"
                       theme="light">
                <Icon type="md-alert"
                      size="24"
                      color="rgba(24, 144, 255, 1)"
                      class="iconInterVal" />
              </Tooltip>
            </RadioGroup>
          </FormItem>
          <FormItem label="同步目标："
                    prop="syncTarget"
                    class="syncTarget">
            <Input v-model="formdata.syncTarget"
                   type="textarea"
                   :autosize="{minRows: 2,maxRows: 5}"
                   placeholder="请选择同步目标"></Input>
            <span @click="showOUModal()"
                  class="link">选择需要同步的OU</span>
          </FormItem>
          <FormItem label="自动同步："
                    prop="isAutoSync">
            <RadioGroup v-model="formdata.isAutoSync">
              <Radio v-for="item in isAutoSyncList"
                     :label="item.value"
                     :key="item.value">{{item.label}}</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="同步开始时间："
                    prop="autoSyncDate">
            <TimePicker type="time"
                        placeholder="请选择开始时间"
                        v-model="formdata.autoSyncDate"></TimePicker>
          </FormItem>
          <FormItem label="每隔："
                    prop="interval">
             <Select v-model="formdata.interval" style="width:200px">
                  <Option v-for="item in intervalList" :value="item.value" :key="item.value">{{ item.label }}</Option>
             </Select>

          </FormItem>
        </Form>
        <Form ref="formdata"
              :model="formdata"
              :rules="ruleValidate"
              v-if="currentStep===2"
              :label-width="120">
          <p style="font-size:14px;font-weight:bold;text-align:left;margin-left:50px">本地属性&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;AD属性</p>
          <FormItem label="账号：">
            <div id="input3">
              <Input v-model="formdata.adMap2LocalDomainList[0].adKey"
                     readonly />
            </div>
          </FormItem>
          <FormItem label="姓名：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[1].adKey"
                     readonly />
            </div>
          </FormItem>
          <FormItem label="邮箱：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[2].adKey"
                     readonly />
            </div>
          </FormItem>
          <FormItem label="手机：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[3].adKey"
                     readonly />
            </div>
          </FormItem>
          <FormItem label="职位：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[4].adKey"
                     readonly />
            </div>
          </FormItem>
          <FormItem label="工号：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[5].adKey"
                     readonly />
            </div>
          </FormItem>

          <FormItem label="性别：">
            <div id="input3">
              <Input placeholder
                     v-model="formdata.adMap2LocalDomainList[6].adKey"
                     readonly />
            </div>
          </FormItem>
        </Form>
      </div>
      <div class="formed-footer">
        <myButton v-if="currentStep!==0"
                  @click="tolast">上一步</myButton>
        <myButton btnType="info"
                  v-if="currentStep!==2"
                  @click="tonext('formdata')">下一步</myButton>
        <myButton v-if="currentStep===0"
                  @click="testConnect">测试连接</myButton>
        <myButton btnType="info"
                  v-if="currentStep===2"
                  @click="tosave('formdata')">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <!-- ou弹窗 -->
    <Modal v-model="OUModal"
           @on-cancel="OUCancel">
      <p slot="header"
         class="OUModalTitle">
        选择需要同步的OU
      </p>
      <div class="OUModal">
        <p class="OUModalContentTitle">
          选择OU
        </p>
        <div class="OUModalContentBody">
          <Input search
                 enter-button
                 v-model="keyword"
                 placeholder="搜索应用" />
          <div class="OUTree">
            <Tree :data="OUData"
                  v-if="OUData.length!==0"
                  ref="OUTree"
                  show-checkbox></Tree>
          </div>
        </div>
      </div>
      <div slot="footer">
        <Button type="primary"
                @click="OUSave">保存</Button>
        <Button @click="OUReset">重置</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import syncOu from "./ou/index.js";
export default {
  data () {
    // 验证用户名  ^[a-zA-Z][a-zA-Z0-9_]$
    const validateUserName = (rule, value, callback) => {
      let regx = /^([a-zA-Z]|[a-zA-Z0-9_]|[a-zA-Z0-9_*])+@[a-zA-Z0-9]+\.([a-zA-Z]{2,4})$/;
      if (!regx.test(value)) {
        callback(new Error("请输入合法用户名:如name@domain.com"));
      }
      callback();
    };
    return {
      adId: 0, // ad的id
      currentStep: 0, // 当前步数
      isAutoSyncList: [ // 是否开启自动同步
        { value: 0, label: "开启" },
        { value: 1, label: "关闭" }
      ],
      intervalList: [ // 自动同步间隔
        { value: 0, label: "15分钟" },
        { value: 1, label: "1小时" },
        { value: 2, label: "6小时" },
        { value: 3, label: "12小时" },
        { value: 4, label: "24小时" }
      ],
      syncTarget: [], // 存储提交OU表单数据
      syncOu: "1",
      formdata: {
        isAutoSync: 1, // 是否开启自动同步
        syncTarget: "", // 存储提交OU表单数据
        autoSyncDate: "00:00:00", // 自动同步开始时间
        interval: 0, // 间隔时间
        adAutoSyncInfo: {
        }, // 自动同步信息
        adMap2LocalDomainList: [
          { adKey: "userPrincipalName", localVal: "accountNumber" },
          { adKey: "givenName", localVal: "userName" },
          { adKey: "mail", localVal: "mail" },
          { adKey: "mobile", localVal: "phoneNumber" },
          { adKey: "title", localVal: "title" },
          { adKey: "ID", localVal: "ID" },
          { adKey: "sex", localVal: "sex" }
        ],
        ip: "",
        password: "",
        port: 389,
        userName: "",
        syncOu: "1"
      },
      ruleValidate: {
        ip: [
          {
            required: true,
            message: "输入ip地址",
            trigger: "blur"
          }
        ],
        port: [
          {
            required: true,
            message: "输入端口号"
          }
        ],
        userName: [
          {
            required: true,
            // message: "输入用户名",
            trigger: "blur",
            validator: validateUserName
          }
        ],
        password: [
          {
            required: true,
            message: "输入密码",
            trigger: "blur"
          }
        ],
        syncTarget: [
          {
            required: true,
            message: "请选择同步目标",
            trigger: "blur"
          }
        ]
      },
      // OU组织
      keyword: "", // 搜索关键字
      OUModal: false,
      OUData: [],
      OUDataBak: [],
      OUSDataBak: ""
    };
  },
  created () {
    this.adId = this.$route.query.adId;
  },
  watch: {
    keyword (val, oldVal) {
      syncOu.keyword(this);
    }
  },
  mounted () {
    var that = this;
    let params = {
      id: that.adId
    };
    that.finddata(params);
  },
  methods: {
    radiaChange (status) {
      if (status === "1") {
        this.formdata.syncTarget = this.OUSDataBak;
      }
      if (status === "2") {
        this.OUSDataBak = this.formdata.syncTarget;
        this.formdata.syncTarget = "";
      }
    },

    /**
    * 状态改变，重新请求数据
    * @param {*Object 包含index，value和label} status
    * @author yezi 2019-08-26
    */
    changeState (status) {
      console.log(status);
      this.formdata.interval = status.value;
      console.log(this.formdata.interval);
    },

    finddata (params) {
      this.axios({
        method: "post",
        url: "/cipher/ldap/queryOne",
        data: params
      })
        .then(res => {
          this.formdata = res.data;
          this.formdata.syncTarget = JSON.parse(res.data.ous).join("\r\n");
          this.syncTarget = JSON.parse(res.data.ous);
          this.formdata.isAutoSync = res.data.adAutoSyncInfo.isAutoSync;
          this.formdata.autoSyncDate = res.data.adAutoSyncInfo.autoSyncDate; // 自动同步开始时间
          this.formdata.interval = res.data.adAutoSyncInfo.interval; // 自动同步开始时间
          if (res.data.syncOu === 1) {
            this.formdata.syncOu = "1";
          } else if (res.data.syncOu === 2) {
            this.formdata.syncOu = "2";
          }
        })
        .catch(error => {
          console.log(error);
        });
      this.isReaderOnly = true;
    },
    cancel () {
      // this.$router.go(-1);
      this.$router.push("/userCatalogAD");
    },
    // 下一步
    tonext (name) {
      this.currentStep++;
    },
    // 上一步
    tolast () {
      this.currentStep--;
    },
    testConnect () {
      let that = this;
      let params = {
        ip: this.formdata.ip,
        port: this.formdata.port,
        userName: this.formdata.userName,
        password: this.formdata.password
      };
      this.axios({
        method: "post",
        url: "/cipher/ldap/testConnect",
        data: params
      })
        .then((res) => {
          if (res.data.return_code === 1) {
            that.$myMessage.success(res.data.return_msg);
          } else {
            that.$Modal.error({
              title: "提示",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 保存
    tosave () {
      let that = this;
      let params = {
        id: that.adId,
        ip: this.formdata.ip,
        port: this.formdata.port,
        userName: this.formdata.userName,
        password: this.formdata.password,
        ous: JSON.stringify(this.syncTarget),
        syncOu: Number(this.formdata.syncOu),
        isAutoSync: this.formdata.isAutoSync,
        autoSyncDate: this.formdata.autoSyncDate,
        interval: this.formdata.interval,
        "adMap2LocalDomainList[0].localVal": this.formdata
          .adMap2LocalDomainList[0].localVal,
        "adMap2LocalDomainList[0].adKey": this.formdata.adMap2LocalDomainList[0]
          .adKey,
        "adMap2LocalDomainList[1].localVal": this.formdata
          .adMap2LocalDomainList[1].localVal,
        "adMap2LocalDomainList[1].adKey": this.formdata.adMap2LocalDomainList[1]
          .adKey,
        "adMap2LocalDomainList[2].localVal": this.formdata
          .adMap2LocalDomainList[2].localVal,
        "adMap2LocalDomainList[2].adKey": this.formdata.adMap2LocalDomainList[2]
          .adKey,
        "adMap2LocalDomainList[3].localVal": this.formdata
          .adMap2LocalDomainList[3].localVal,
        "adMap2LocalDomainList[3].adKey": this.formdata.adMap2LocalDomainList[3]
          .adKey,
        "adMap2LocalDomainList[4].localVal": this.formdata
          .adMap2LocalDomainList[4].localVal,
        "adMap2LocalDomainList[4].adKey": this.formdata.adMap2LocalDomainList[4]
          .adKey,
        "adMap2LocalDomainList[5].localVal": this.formdata
          .adMap2LocalDomainList[5].localVal,
        "adMap2LocalDomainList[5].adKey": this.formdata.adMap2LocalDomainList[5]
          .adKey,
        "adMap2LocalDomainList[6].localVal": this.formdata
          .adMap2LocalDomainList[6].localVal,
        "adMap2LocalDomainList[6].adKey": this.formdata.adMap2LocalDomainList[6]
          .adKey
      };
      this.axios({
        method: "post",
        url: "/cipher/ldap/update",
        data: params
      })
        .then((res) => {
          if (res.data.return_code === 1) {
            this.cancel();
            this.$myMessage.success(res.data.return_msg);
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    showOUModal () {
      syncOu.showOUModal(this);
    },
    OUSave () {
      syncOu.OUSave(this);
    },
    OUCancel () {
      syncOu.OUCancel(this);
    },
    OUReset () {
      syncOu.OUReset(this);
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
@import "~@/assets/styles/userCatalog/ActiveDirectory/ou.less";
.ivu-form {
  display: inline-block;
  width: 440px;
}
.img {
  //height: 10px;
  width: 15px;
  margin-left: 5px;
  display: inline-block;
  position: absolute;
  left: 270px;
  top: 10px;
}
.iconInterVal {
  margin-right: 16px;
}
</style>
