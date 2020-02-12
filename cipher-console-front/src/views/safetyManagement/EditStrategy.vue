<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">{{strategyName}}</div>
    </div>
    <div class="formed-container">
      <Form ref="formItem"
            :model="formItem"
            :rules="ruleValidate"
            class="formed-form"
            :label-width="130">
        <FormItem label="策略名称："
                  prop="strategyName">
          <!-- 默认策略 -->
          <Input v-model.trim="formItem.strategyName"
                 readonly
                 v-if="isDefaultStarategy" />
          <!-- 非默认策略 -->
          <Input v-model.trim="formItem.strategyName"
                 v-else />
        </FormItem>
        <FormItem label="优先级："
                  prop="priority">
          <!-- 默认策略 -->
          <Input v-model.trim="formItem.priority"
                 readonly
                 v-if="isDefaultStarategy" />
          <!-- 非默认策略 -->
          <Input v-model="formItem.priority"
                 v-else />
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
          <!-- 默认策略 -->
          <template v-if="isDefaultStarategy">全员</template>
          <!-- 非默认策略 -->
          <template v-else>
            <a @click="changedepartment"
               class="link">修改</a>
            <Tag v-for="(item ,index) in groupList"
                 :key="index"
                 :name="index"
                 closable
                 @on-close="handleClose(index)">{{item.groupName}}</Tag>
          </template>
        </FormItem>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="submit('formItem')">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
    <Modal v-model="showMadal"
           width="668"
           class="tree-transfer"
           @on-cancel="cancelmodal">
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
      data1Bak: [], // 用于穿梭框重置的数据
      isDefaultStarategy: true, // 是否是默认策略
      unSaveData: [], // 穿梭框已经保存未保存到数据库的数据
      groupIds: "",
      authType: 0,
      groupList: [],
      placeholder: "",
      dataType: 0,
      data1: [],
      data2: [],
      showMadal: false,
      buttonSize: "large",
      id: 0,
      strategyName: "",
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
        priority: 0,
        authType: 3,
        secondAuth: 0,
        secondAuthMode: 0, // 二次认证模式
        secondAuthOption: [],
        groupIds: "",
        id: 0,
        scaveAuthState: 1
      },
      // 验证规则
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
            message: "优先级不能为空"
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
  created () {
    this.id = this.$route.query.id;
    this.strategyName = this.$route.query.strategyName;
  },
  mounted () {
    let params = {
      id: this.id
    };
    this.finddata(params);
  },
  methods: {
    // 二次认证方式或认证模式改变时，清空选择的值
    changesecondAuth () {
      this.formItem.secondAuthOption = [];
      this.secondAuthModeTip = this.formItem.secondAuthMode === 0 ? "用户选择一种可用的二认认证通过即可登录" : "用户需要通过以下配置的所有的二认认证成功后登录";
    },
    // 关闭生效范围的标签
    handleClose (index) {
      this.groupList.splice(index, 1);
      let groupIdArr = [];
      this.groupList.forEach(item => {
        groupIdArr.push(item.groupId);
      });
      this.groupIds = groupIdArr.join(",");
    },
    cancelmodal () {
      this.showMadal = false;
    },

    // 保存
    ok () {
      let groupIdArr = [];
      this.unSaveData.forEach(item => {
        groupIdArr.push(item.groupId);
      });
      this.groupIds = groupIdArr.join(",");
      this.groupList = this.unSaveData;
      // this.showMadal = false;
    },
    reset () {
      this.data1 = this.$common.clone(this.data1Bak);
    },
    // 穿梭框已选择数据
    getRightData (data) {
      this.unSaveData = data;
      // this.groupList = data
    },
    // 弹出穿梭框初始化数据
    changedepartment () {
      let that = this;
      that.dataType = 2;
      that.showMadal = true;
      let params = {
        id: that.id
      };
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/updatePre",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            that.data2 = res.data.trees;
            that.data2 = that.$common.cloneAssemblyAssignData(
              res.data.trees,
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
    finddata (params) {
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/updatePre",
        data: params
      })
        .then(res => {
          if (res.data.code === 0) {
            if (res.data.identityObj.priority === 0) {
              this.isDefaultStarategy = true;
            } else {
              this.isDefaultStarategy = false;
            }
            this.groupList = res.data.groupList;
            let groupIdArr = [];
            this.groupList.forEach(item => {
              groupIdArr.push(item.groupId);
            });
            this.groupIds = groupIdArr.join(",");
            this.formItem.strategyName = res.data.identityObj.strategyName;
            this.formItem.priority = res.data.identityObj.priority;
            this.formItem.authType = res.data.identityObj.authType;
            this.formItem.secondAuth = res.data.identityObj.secondAuth;
            this.formItem.secondAuthMode = res.data.identityObj.switches;
            this.secondAuthModeTip = this.formItem.secondAuthMode === 0 ? "用户选择一种可用的二认认证通过即可登录" : "用户需要通过以下配置的所有的二认认证成功后登录";

            // 二次认证方式编辑显示
            this.formItem.secondAuthOption = [];
            let secondAuthType = res.data.identityObj.secondAuthType;
            for (let key in secondAuthType) {
              if (secondAuthType[key] === 0) {
                this.formItem.secondAuthOption.push(key);
              }
            }
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    // 保存数据
    submit (name) {
      let secondAuthValidata = true;
      if (this.formItem.secondAuth === "1" && this.formItem.secondAuthOption.length === 0) {
        secondAuthValidata = false;
      }
      this.$refs[name].validate(valid => {
        let that = this;
        if (valid) {
          if (that.formItem.priority === 0) {
            that.save();
          } else if (that.groupList.length === 0) {
            that.$myMessage.error("生效范围不能为空！");
          } else if (!secondAuthValidata) {
            that.$myMessage.error("请至少选择一种认证方式！");
          } else {
            that.save();
          }
        } else {
          that.$myMessage.error("表单验证失败，请重试！");
        }
      });
    },
    save () {
      // 二次认证方式
      let secondAuthType = {};
      for (let item of this.getSecondAuthList) { // 将所有的二次认证方式默认设置为1
        secondAuthType[item.value] = 1;
      }
      for (let item of this.formItem.secondAuthOption) { // 将选中的二次认证方式设置为0
        secondAuthType[item] = 0;
      }
      let params = {
        id: this.id,
        strategyName: this.formItem.strategyName,
        priority: this.formItem.priority,
        scaveAuthState: this.formItem.scaveAuthState,
        groupIds: this.groupIds,
        authType: this.formItem.authType,
        secondAuth: this.formItem.secondAuth,
        switches: this.formItem.secondAuthMode,
        // twoAuthSf: secondAuthType.twoAuthSf,
        twoAuthDd: secondAuthType.twoAuthDd,
        twoAuthDb: secondAuthType.twoAuthDb,
        twoAuthNum: secondAuthType.twoAuthNum,
        twoAuthMail: secondAuthType.twoAuthMail,
        twoAuthDt: secondAuthType.twoAuthDt,
        twoAuthDingPush: secondAuthType.twoAuthDingPush, // 二次认证：钉钉PUSH认证
        twoAuthWx: secondAuthType.twoAuthWx // 二次认证：企业微信扫码
      };
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/update",
        data: params
      })
        .then(res => {
          this.cancel();
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
          } else {
            this.$Modal.error({
              title: "提示",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          this.$Modal.error({
            title: "提示",
            content: "优先级不能为0，或500错误！"
          });
          console.log(error);
        });
    },
    cancel () {
      this.$router.go(-1);
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
@import "~@/assets/styles/modal.less";
.wrap {
  background-color: #fff;
}
.edit_box2 {
  height: calc(~"100% - 120px");
  overflow-y: auto;
  text-align: left;
}
.header2 {
  height: 70px;
}
.policy_name_box {
  float: left;
  text-align: center;
  line-height: 70px;
  margin-left: 30px;
  font-size: 16px;
  font-weight: bold;
}
.texta {
  text-align: center;
}
.second-auth-mode-tip {
  position: absolute;
  left: 0px;
  top: 28px;
  color: rgb(204, 204, 204);
}
</style>
