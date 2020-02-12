<template>
  <div class="formed-wrap">
    <Form v-if="isEdit"
          :label-width="120"
          ref="formValidate"
          :model="formItem"
          :rules="ruleValidate"
          class="formed-form">
      <div class="formed-form-part">
        <div class="formed-form-part-title">应用同步</div>
        <div class="formed-form-item">
          <Table :columns="tableEditTitle"
                 :data="tableData"
                 width="400">
            <template slot-scope="{ row,index }"
                      slot="localKey">
              <!-- 属性为应用账号 -->
              <Select v-model="select[0]"
                      size="large"
                      style="width:120px"
                      :disabled="isSubConfig"
                      v-if="row.syncKey==='*应用账号'">
                <Option v-for="item in localAccountOptions"
                        :value="item.value"
                        :key="item.value">{{ item.label }}</Option>
              </Select>
              <!-- 属性为性别 -->
              <Select v-model="select[index]"
                      size="large"
                      style="width:120px"
                      v-else-if="row.syncKey==='性别'">
                <Option v-for="item in localGenderOptions"
                        :value="item.value"
                        :key="item.value">{{ item.label }}</Option>
              </Select>
              <!-- 除应用账号、性别外的其他属性其他属性 -->
              <Select v-model="select[index]"
                      size="large"
                      style="width:120px"
                      v-else>
                <Option v-for="item in localCommonOptions"
                        :value="item.value"
                        :key="item.value">{{ item.label }}</Option>
              </Select>
            </template>
          </Table>
        </div>
      </div>
      <!-- 密码 -->
      <div class="formed-form-part">
        <div class="formed-form-part-title">密码设置</div>
        <FormItem label="设置应用初始密码："
                  prop="initPwd">
          <Input v-model="ruleInfo.initPwd"
                 placeholder="请输出初始密码"
                 clearable
                 style="width: 200px"
                 :type="type" />
          <span class="link"
                style="margin-left:8px"
                @click="changePwdType">{{showPassword?"不显示":"显示"}}</span>
        </FormItem>
      </div>
      <!-- 自动同步 -->
      <div class="formed-form-part">
        <div class="formed-form-part-title">自动同步</div>
        <FormItem label="自动同步："
                  prop="isAutoSync">
          <RadioGroup v-model="formItem.isAutoSync">
            <Radio v-for="item in isAutoSyncList"
                   :label="item.value"
                   :key="item.value">{{item.label}}</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="同步开始时间："
                  prop="autoSyncDate">
          <TimePicker type="time"
                      placeholder="请选择开始时间"
                      v-model="formItem.autoSyncDate"
                      style="width: 200px"></TimePicker>
        </FormItem>
        <FormItem label="每隔："
                  prop="interval">
          <Select v-model="formItem.interval"
                  style="width:200px">
            <Option v-for="item in intervalList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
          <span style="margin-left:8px">同步一次</span>
        </FormItem>
      </div>
    </Form>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="saveEdit('formValidate')">保存</myButton>
      <myButton @click="cancel">取消</myButton>
    </div>
    <div v-else
         class="formed-form">
      <div class="formed-form-part">
        <div class="formed-form-part-title">应用同步</div>
        <div class="formed-form-item">
          <Table :columns="tableTitle"
                 :data="tableData"
                 width="400"></Table>
        </div>
      </div>
      <div class="formed-form-part">
        <div class="formed-form-part-title">自动同步</div>
        <div class="formed-form-item">
          <span>自动同步：</span>
          <span>{{showAutoSyn.isAutoSync?"关闭":"开启"}}</span>
        </div>
        <div class="formed-form-item">
          <span>同步开始时间：</span>
          <span>{{showAutoSyn.autoSyncDate}}</span>
        </div>
        <div class="formed-form-item">
          <span>每隔：</span>
          <span>{{getIntervalTime}} <span>同步一次</span></span>
        </div>
      </div>
    </div>
  </div>
</template>
<script>
import { mapState } from "vuex";
export default {
  name: "SynAppConfig",
  props: ["appData"],
  data () {
    return {
      appDada: {},
      tableTitle: [{ title: "本地属性", key: "localKey", align: "center" }, { title: "同步属性", key: "syncKey", align: "center" }],
      tableEditTitle: [{ title: "本地属性", key: "localKey", align: "center", slot: "localKey" }, { title: "同步属性", key: "syncKey", align: "center" }],
      showPassword: false, // 是否显示密码
      type: "password", // 类型为password
      isSubConfig: false, // 从账号认证，与主账号是否一致
      select: [], // 分别对应从第一个select绑定的值组成的数组
      isEdit: false, // 用于视图切换的属性  true为显示编辑视图  false显示为查阅视图
      appSysId: "", // 标准系统应用的id
      ruleInfo: { // 规则
        accountType: "",
        ruleValue: [],
        initPwd: ""
      },
      showData: [], // 属性映射显示数据
      showAutoSyn: { // 自动同步显示数据
        isAutoSync: 1, // 是否开启自动同步
        autoSyncDate: "00:00:00", // 自动同步开始时间
        interval: 0 // 间隔时间
      }, // 自动同步显示数据
      formItem: { // 自动同步编辑数据
        isAutoSync: 1, // 是否开启自动同步
        autoSyncDate: "00:00:00", // 自动同步开始时间
        interval: 0 // 间隔时间
      },
      ruleValidate: {
        autoSyncDate: [{ required: true, message: "请输入自动同步开始时间", trigger: "blur" }]
      },
      localGenderOptions: [{ value: "genderStr", label: "性别" }], // 同步性别属性对应的本地属性select选项
      localAccountOptions: [ // 同步应用账号属性对应的本地属性select选项
        { value: "1", label: "赛赋身份平台账号" },
        { value: "2", label: "邮箱" },
        { value: "3", label: "邮箱前缀" },
        { value: "4", label: "手机号" },
        { value: "5", label: "工号" }
      ],
      localCommonOptions: [ // 同步其他属性对应的本地属性select选项
        { value: "accountNumber", label: "主账号" },
        { value: "userName", label: "姓名" },
        { value: "nickName", label: "昵称" },
        { value: "groupName", label: "部门" },
        { value: "mail", label: "邮箱" },
        { value: "job", label: "职位" },
        { value: "phoneNumber", label: "手机" },
        { value: "jobNo", label: "工号" },
        { value: "genderStr", label: "性别" }
      ],
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
      ]
    };
  },
  computed: {
    ...mapState(["appId"]),
    tableData () { // 属性值
      let tableData = [];
      for (let i = 0; i < this.getSynAttribute.length; i++) {
        let localKey = this.showData[i];
        let syncKey = this.getSynAttribute[i];
        tableData.push({ localKey, syncKey });
      }
      return tableData;
    },
    tabledEditData () {
      let tableData = [];
      for (let i = 0; i < this.getSynAttribute.length; i++) {
        let syncKey = this.getSynAttribute[i];
        tableData.push({ syncKey });
      }
      return tableData;
    },
    getSynAttribute () { // 获取同步属性，不同封装的标准应用同步属性不同
      let tempArray = [];
      switch (this.appSysId) { // 封装应用类型
        case 1:
          tempArray = ["*应用账号", "*姓名", "手机", "工号", "性别"];
          break;
        case 2:
          tempArray = ["*应用账号", "*姓名", "*邮箱", "*手机号", "部门"];
          break;
        case 5:
          tempArray = ["*应用账号", "*姓名", "职位", "手机", "编号", "性别"];
          break;
        case 6:
          tempArray = ["*应用账号", "昵称", "邮箱", "手机号", "性别"];
          break;
        case 7:
          tempArray = ["*应用账号", "*昵称", "*手机号", "性别"];
          break;
        default:
          break;
      }
      return tempArray;
    },
    getIntervalTime () {
      if (typeof (this.showAutoSyn.interval) !== "undefined") {
        return this.intervalList[this.showAutoSyn.interval].label;
      } else {
        return 0;
      }
    }
  },
  mounted () {
    this.appData = this.$props.appData;
    this.init();
  },
  methods: {
    /**
     * 初始化加载数据，显示页面和编辑页面数据回显
     * @param {*void}
     * @author yezi 2019-10-08
     */
    init () {
      let data = this.appData;
      this.appSysId = data.app.appSysId;
      if (data.app.associatedAccount.assManual === 1) { // 不是手动配置从账号
        this.isSubConfig = true;
      }
      // 属性数据
      if (data.app.accountType !== null || data.app.accountType !== "") { // accountType为空的时候，表明该页面未保存过
        // 应用账号属性
        this.select = []; // 编辑选择属性数据
        this.showData = []; // 显示属性数据
        this.select.push(data.app.accountType);
        this.ruleInfo.accountType = data.app.accountType;
        this.showData.push(this.localAccountOptions[this.ruleInfo.accountType - 1].label);
      }
      // 其他同步属性
      let ruleValue = JSON.parse(data.ruleInfo.ruleValue) || [];
      ruleValue.forEach((item, n) => {
        this.select.push(item.localValue);
        let index = this.localCommonOptions.findIndex(option => option.value === item.localValue); // 该属性在同步本地属性里面找到数组序号
        let tempItem = index !== -1 ? this.localCommonOptions[index].label : ""; // 如果该属性为空或未找到，则为"""
        this.showData.push(tempItem); // 显示页面对应的本地属性
      });

      // 密码数据
      this.ruleInfo.initPwd = data.ruleInfo.initPwd || ""; // 显示密码
      // 自动同步数据
      if (data.app.autoSync !== null) { // 自动同步数据不为空时
        this.showAutoSyn = JSON.parse(JSON.stringify(data.app.autoSync)); // 自动同步显示数据
        this.formItem = JSON.parse(JSON.stringify(data.app.autoSync)); // 自动同步编辑数据
      }
    },
    /**
     * 表单验证1 主要验证网易的必填信息 以及腾讯企业邮箱的表单校验
     * @param {*void}
     * @author yezi 2019-08-01
     */
    validate1 () {
      if (
        this.select[0] === "" ||
        this.select[0] === null ||
        this.select[1] === "" ||
        this.select[1] === null
      ) {
        this.$Modal.error({
          title: "必填项未选择",
          content: "姓名未选择"
        });
        return false;
      }
      if (this.ruleInfo.initPwd === null || this.ruleInfo.initPwd === "") {
        this.$Modal.error({
          title: "初始密码为空",
          content: "初始密码不能为空"
        });

        return false;
      }
      return true;
    },
    /**
     * 表单验证金山wps的校验
     * @param {*void}
     * @author yezi 2019-08-01
     */
    validate3 () {
      if (this.ruleInfo.initPwd === null || this.ruleInfo.initPwd === "") {
        this.$Modal.error({
          title: "初始密码为空",
          content: "初始密码不能为空"
        });

        return false;
      }
      return true;
    },
    /**
     * 表单验证 每时每刻的表单校验
     * @param {*void}
     * @author yezi 2019-08-01
     */
    validate2 () {
      if (
        this.select[0] === "" ||
        this.select[0] === null ||
        this.select[1] === "" ||
        this.select[1] === null ||
        this.select[2] === "" ||
        this.select[2] === null ||
        this.select[3] === "" ||
        this.select[3] === null
      ) {
        this.$Modal.error({
          title: "必填项未选择",
          content: "请选择必填项"
        });

        return false;
      }
      return true;
    },
    /**
    * 表单验证 众合OA系统验证
    * @param {*void}
    * @author yezi 2019-08-01
    */
    validate4 () {
      if (
        this.select[0] === "" ||
        this.select[0] === null ||
        this.select[1] === "" ||
        this.select[1] === null
      ) {
        this.$Modal.error({
          title: "必填项未选择",
          content: "姓名未选择"
        });
        return false;
      }
      if (this.select[2] === "" ||
        this.select[2] === null) {
        this.$Modal.error({
          title: "必填项未选择",
          content: "手机未选择"
        });
        return false;
      }
      if (this.ruleInfo.initPwd === null || this.ruleInfo.initPwd === "") {
        this.$Modal.error({
          title: "初始密码为空",
          content: "初始密码不能为空"
        });

        return false;
      }
      return true;
    },
    /**
     * 保存编辑时触发该方法
     * @param {*void}
     * @author yezi 2019-08-01
     */
    saveEdit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          let params;
          if (this.appSysId === 1) { // 网易的校验以及参数
            if (!this.validate1()) {
              return;
            }
            params = {
              applicationId: this.appId,
              "subSynLocalDomainList[0].subKey": "nickname",
              "subSynLocalDomainList[0].localValue": this.select[1],
              "subSynLocalDomainList[1].subKey": "mobile",
              "subSynLocalDomainList[1].localValue": this.select[2],
              "subSynLocalDomainList[2].subKey": "job_no",
              "subSynLocalDomainList[2].localValue": this.select[3],
              "subSynLocalDomainList[3].subKey": "genderStr",
              "subSynLocalDomainList[3].localValue": this.select[4],
              initPwd: this.ruleInfo.initPwd
            };
          } else if (this.appSysId === 2) { // 每时每刻的校验以及传到后台的接口的数据
            if (!this.validate2()) {
              return;
            }
            params = {
              applicationId: this.appId,
              "subSynLocalDomainList[0].subKey": "name",
              "subSynLocalDomainList[0].localValue": this.select[1],
              "subSynLocalDomainList[1].subKey": "email",
              "subSynLocalDomainList[1].localValue": this.select[2],
              "subSynLocalDomainList[2].subKey": "mobile",
              "subSynLocalDomainList[2].localValue": this.select[3],
              "subSynLocalDomainList[3].subKey": "department",
              "subSynLocalDomainList[3].localValue": this.select[4],
              initPwd: this.ruleInfo.initPwd
            };
          } else if (this.appSysId === 5) { // 腾讯的校验以及传到后端的数据
            if (!this.validate1()) {
              return;
            }
            params = {
              applicationId: this.appId,
              "subSynLocalDomainList[0].subKey": "name",
              "subSynLocalDomainList[0].localValue": this.select[1],
              "subSynLocalDomainList[1].subKey": "position",
              "subSynLocalDomainList[1].localValue": this.select[2],
              "subSynLocalDomainList[2].subKey": "mobile",
              "subSynLocalDomainList[2].localValue": this.select[3],
              "subSynLocalDomainList[3].subKey": "extid",
              "subSynLocalDomainList[3].localValue": this.select[4],
              "subSynLocalDomainList[4].subKey": "genderStr",
              "subSynLocalDomainList[4].localValue": this.select[5],
              initPwd: this.ruleInfo.initPwd
            };
          } else if (this.appSysId === 6) { // 金山wps的表单校验以及数据
            if (!this.validate3()) {
              return;
            }
            params = {
              applicationId: this.appId,
              "subSynLocalDomainList[0].subKey": "nick_name",
              "subSynLocalDomainList[0].localValue": this.select[1],
              "subSynLocalDomainList[1].subKey": "email",
              "subSynLocalDomainList[1].localValue": this.select[2],
              "subSynLocalDomainList[2].subKey": "phone_number",
              "subSynLocalDomainList[2].localValue": this.select[3],
              "subSynLocalDomainList[3].subKey": "sex",
              "subSynLocalDomainList[3].localValue": this.select[4],
              initPwd: this.ruleInfo.initPwd
            };
          } else if (this.appSysId === 7) { // 众合OA系统
            if (!this.validate4()) {
              return;
            }
            params = {
              applicationId: this.appId,
              "subSynLocalDomainList[0].subKey": "profileName",
              "subSynLocalDomainList[0].localValue": this.select[1],
              "subSynLocalDomainList[1].subKey": "mobile",
              "subSynLocalDomainList[1].localValue": this.select[2],
              initPwd: this.ruleInfo.initPwd
            };
          }
          this.axios({ // 保存应用信息
            method: "post",
            url: "/cipher/app/change",
            data: {
              id: this.appId,
              applicationName: this.appData.app.applicationName,
              accountType: this.select[0],
              isAutoSync: this.formItem.isAutoSync,
              autoSyncDate: this.formItem.autoSyncDate,
              interval: this.formItem.interval

            }
          })
            .then(res => {
              if (res.data.code === 0) { // 成功后请求保存应用同步信息
                this.saveSynRule(params);
              } else {
                this.$Modal.error({
                  title: "错误",
                  content: res.data.msg
                });
              }
            })
            .catch(error => {
              this.$Notice.warning({
                title: "网络未知错误",
                desc: error
              });
            });
        }
      });
    },
    /**
     * 保存同步应用配置信息
     * @param {*Object 保存应用配置请求后台所需参数：应用id，同步属性，密码} params
     * @author yezi 2019-08-01
     */
    saveSynRule (params) {
      this.axios({
        method: "post",
        url: "/cipher/app/ruleAdd",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$Modal.success({
              title: "成功",
              content: res.data.return_msg
            });
            this.cancel();
            this.$emit("getData");
          } else {
            this.$Modal.error({
              title: "错误",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误",
            desc: error
          });
        });
    },

    /**
     * 点击密码时显示明文还是密文的触发方法
     * @param {*void}
     * @author yezi 2019-08-01
     */
    changePwdType () {
      this.showPassword = !this.showPassword;
      this.type = this.type === "password" ? "text" : "password";
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-01
     */
    cancel () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "synConfig"
      });
    },
    /**
     * 进入编辑状态
     * @param {*void}
     * @author yezi 2019-08-01
     */
    editStatus () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "synConfig"
      });
    }
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
</style>
