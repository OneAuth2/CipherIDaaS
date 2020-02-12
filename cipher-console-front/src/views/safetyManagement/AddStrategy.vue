<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">身份认证策略</div>
      <div class="formed-header-btn">
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <div class="formed-container">
      <Form ref="formItem"
            :model="formItem"
            class="formed-form"
            :rules="ruleValidate"
            :label-width="130">
        <FormItem label="策略名称："
                  prop="strategyName">
          <div id="input">
            <Input v-model.trim="formItem.strategyName" />
          </div>
        </FormItem>
        <FormItem label="优先级："
                  prop="priority">
          <div id="input">
            <Input v-model.trim="formItem.priority" />
          </div>
        </FormItem>
        <FormItem label="认证源："
                  prop="authType">
          <Select v-model="formItem.authType">
            <Option v-for="item in authTypeList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
        </FormItem>
        <FormItem label="二次认证："
                  prop="secondAuth">
          <!-- 开启关闭 -->
          <RadioGroup v-model="formItem.secondAuth"
                      @on-change="changesecondAuth">
            <Radio v-for="item in switchList"
                   :label="item.value"
                   :key="item.value">{{item.label}}</Radio>
          </RadioGroup>
        </FormItem>
        <!-- 并行串行 -->
        <FormItem label="二次认证模式："
                  prop="secondAuthMode">
          <RadioGroup v-model="formItem.secondAuthMode"
                      @on-change="changesecondAuth">
            <Radio v-for="item in secondAuthModeList"
                   :label="item.value"
                   :key="item.value">{{item.label}}</Radio>
          </RadioGroup>
          <span class="second-auth-mode-tip">{{secondAuthModeTip}}</span>
        </FormItem>
        <!-- 二次认证方式 -->
        <FormItem label="二次认证方式："
                  prop="secondAuthOption">
          <CheckboxGroup v-model="formItem.secondAuthOption"
                         style="width:120%">
            <Checkbox v-for="item in getSecondAuthList"
                      :label="item.value"
                      :key="item.label"
                      :disabled="formItem.secondAuth===1"
                      style="margin-right:20px">{{ item.label }}</Checkbox>
          </CheckboxGroup>
        </FormItem>
        <FormItem label="生效范围：">
          <Tag v-for="item in count"
               :key="item.groupId"
               :name="item.groupId"
               closable
               @on-close="handleClose">{{ item.groupName}}</Tag>
          <span @click="changedepartment"
                class="link">修改</span>
        </FormItem>
      </Form>

      <div class="formed-footer">
        <myButton btnType="info"
                  @click="submit('formItem')">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <Modal v-model="showMadal"
           class="tree-transfer"
           width="668">
      <p slot="header"
         class="header">
        <span>设置生效部门</span>
        <span class="sub-title">（{{formItem.strategyName}}）</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="data1"
                      :leftDataType="dataType"
                      @rightData="getRightData"
                      :placeholder="placeholder">
          <span slot="leftText">授权部门</span>
          <span slot="rightText">已授权部门</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <Button type="primary"
                @click="ok">保存</Button>
        <Button @click="reset"
                style="margin-left:8px;">重置</Button>
      </div>
    </Modal>
  </div>
</template>
<script>
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
const cloneChangeKeys = {
  title: {
    target: "title",
    original: "text"
  },
  children: {
    target: "children",
    original: "nodes"
  }
};

export default {
  components: {
    TreeTransfer
  },
  data () {
    return {
      unSaveData: [], // 穿梭框已选择保存未保存到数据库的数据
      data1Bak: [], // 用于穿梭框重置的数据备份
      placeholder: "",
      dataType: 0,
      data1: [],
      data2: [],
      showMadal: false,
      count: [],
      queryName: "",
      // 开启或关闭
      switchList: [
        {
          value: 0,
          label: "开启"
        },
        {
          value: 1,
          label: "关闭"
        }
      ],
      // 认证源列表
      authTypeList: [
        {
          value: 0,
          label: "本地认证"
        },
        {
          value: 1,
          label: "AD域"
        },
        {
          value: 2,
          label: "JTL_ERP"
        }
      ],
      secondAuthModeList: [
        {
          value: 0,
          label: "并行"
        },
        {
          value: 1,
          label: "串行"
        }
      ],
      secondAuthModeTip: "用户选择一种可用的二认认证通过即可登录",
      formItem: {
        strategyName: "",
        priority: "",
        authType: "",
        secondAuth: 0,
        secondAuthOption: [],
        secondAuthMode: 0,
        groupIds: "",
        scaveAuthState: 1
      },
      // 校验规则
      ruleValidate: {
        strategyName: [
          {
            required: true,
            message: "策略名不能为空",
            trigger: "blur"
          }
        ],
        priority: [
          {
            required: true,
            message: "优先级不能为空",
            trigger: "blur"
          },
          {
            pattern: /^[0-9]{1,2}$/,
            message: "优先级必须为数字，范围为0-99",
            trigger: "blur"
          }
        ],
        authType: [
          {
            required: true,
            type: "number",
            message: "认证源不能为空",
            trigger: "change"
          }
        ]
        // secondAuthOption: [
        //   { validator: validateSecondAuthOption, trigger: "change" }
        // ]
      }
    };
  },
  computed: {
    getSecondAuthList () {
      if (this.formItem.secondAuthMode === 0) {
        return [
          { value: "twoAuthDingPush", label: "钉钉PUSH认证" },
          { value: "twoAuthDd", label: "钉钉扫码" },
          { value: "twoAuthWx", label: "企业微信扫码" },
          // { value: "twoAuthDb", label: "大白扫码" },
          { value: "twoAuthDt", label: "动态密码" },
          { value: "twoAuthNum", label: "手机随机码" }];
      } else {
        return [
          { value: "twoAuthDingPush", label: "钉钉PUSH认证" },
          { value: "twoAuthDd", label: "钉钉扫码" },
          { value: "twoAuthWx", label: "企业微信扫码" },
          { value: "twoAuthDb", label: "大白扫码" },
          { value: "twoAuthDt", label: "动态密码" },
          { value: "twoAuthNum", label: "手机随机码" },
          { value: "twoAuthMail", label: "邮件随机码" }];
      }
    }
  },
  methods: {
    // 二次认证方式关闭时，清空选择的值
    changesecondAuth () {
      this.formItem.secondAuthOption = [];
      this.secondAuthModeTip = this.formItem.secondAuthMode === 0 ? "用户选择一种可用的二认认证通过即可登录" : "用户需要通过以下配置的所有的二认认证成功后登录";
    },
    ok () {
      this.count = [];
      this.unSaveData.forEach(item => {
        this.count.push({ groupId: item.groupId, groupName: item.groupName });
      });
      // this.showMadal = false;
    },
    // 重置穿梭框规则
    reset () {
      this.data1 = this.$common.clone(this.data1Bak);
    },
    // 关闭生效范围内对应标签
    handleClose (event, name) {
      const index = this.count.indexOf(name);
      this.count.splice(index, 1);
    },
    getRightData (data) {
      this.unSaveData = data;
    },
    init () {
      let that = this;
      that.dataType = 2;
      let params = {};
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/group",
        data: params
      })
        .then(function (res) {
          if (res.data.code === "0") {
            that.data2 = that.$common.cloneAssemblyAssignData(
              res.data.msg.trees,
              cloneChangeKeys,
              true
            );
            that.data2 = that.$common.initSelect(that.data2);
            that.data1 = that.data2;
            that.data1Bak = that.$common.clone(that.data1);
            that.placeholder = "搜索部门";
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    changedepartment () {
      this.init();
      this.showMadal = true;
    },
    submit (name) {
      let secondAuthValidata = true;
      if (this.formItem.secondAuth === "1" && this.formItem.secondAuthOption.length === 0) {
        secondAuthValidata = false;
      }
      this.$refs[name].validate(valid => {
        if (valid && this.count.length && secondAuthValidata) {
          this.save();
        } else if (!secondAuthValidata) {
          this.$myMessage.error("请选择至少一种二次认证方式！");
        } else if (!this.count.length) {
          this.$myMessage.error("生效范围不能为空，请重试！");
        } else {
          this.$myMessage.error("表单验证失败，请重试！");
        }
      });
    },
    save () {
      let groupIdss = [];
      this.count.forEach(item => {
        groupIdss.push(item.groupId);
      });
      // 二次认证方式
      let secondAuthType = {};
      for (let item of this.getSecondAuthList) { // 将所有的二次认证方式默认设置为1
        secondAuthType[item.value] = 1;
      }
      for (let item of this.formItem.secondAuthOption) { // 将选中的二次认证方式设置为0
        secondAuthType[item] = 0;
      }
      let params = {
        strategyName: this.formItem.strategyName,
        priority: this.formItem.priority,
        groupIds: groupIdss.join(","),
        scaveAuthState: 1,
        authType: this.formItem.authType,
        secondAuth: this.formItem.secondAuth,
        switches: this.formItem.secondAuthMode,
        twoAuthSf: secondAuthType.twoAuthSf,
        twoAuthDd: secondAuthType.twoAuthDd,
        twoAuthDb: secondAuthType.twoAuthDb,
        twoAuthNum: secondAuthType.twoAuthNum,
        twoAuthMail: secondAuthType.twoAuthMail,
        twoAuthDt: secondAuthType.twoAuthDt,
        twoAuthDingPush: secondAuthType.twoAuthDingPush, // 二次认证：钉钉PUSH认证
        twoAuthDingWx: secondAuthType.twoAuthWx // 二次认证：企业微信扫码

      };
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/add",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.cancel();
            this.$myMessage.success(res.data.return_msg);
          } else {
            this.$Modal.error({
              title: "提示",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    search () {
      let mockData = [];
      let that = this;
      let params = {
        queryName: that.queryName
      };
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/query",
        data: params
      })
        .then(function (res) {
          mockData = that.dohand(res.data.userNonRole, mockData);
          that.data1 = mockData;
        })
        .catch(error => {
          console.log(error);
        });
    },
    cancel () {
      this.$router.push("/safetyManagementIP");
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
@import "~@/assets/styles/modal.less";
.second-auth-mode-tip {
  position: absolute;
  left: 0px;
  top: 28px;
  color: rgb(204, 204, 204);
}
</style>
