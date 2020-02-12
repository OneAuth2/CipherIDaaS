<template>
  <div class="formed-wrap was-staff">
    <template v-if="Object.keys(this.staffList).length !== 0">
      <div class="formed-form was-staff-body"
           v-if="!isEdit"
           key="edit">
        <div class="formed-form-item">
          <span>认证方式：</span>
          <span>用户名+密码</span>
        </div>
        <div class="formed-form-item">
          <span>密码认证源：</span>
          <span>{{staffList.authSource}}</span>
        </div>
        <div class="formed-form-item">
          <span>MAC认证：</span>
          <span>{{staffList.macAuth}}
            <Icon :class="macAuthObj.class"
                  :type="macAuthObj.type" /></span>
        </div>
        <div class="formed-form-item"
             v-if="staffList.macAuth === '已开启'">
          <span>有效期：</span>
          <span>{{staffList.usefulLife}} {{staffList.usefulLifeUnit}}</span>
        </div>
      </div>
      <div class="formed-form was-staff-body"
           v-else
           key="list">
        <Form ref="formCustom"
              :model="formCustom"
              :rules="ruleCustom"
              :label-width="120">
          <div class="form">
            <FormItem label="认证方式："
                      prop="authMethod">
              用户名+密码
            </FormItem>
            <FormItem label="密码认证源："
                      prop="authSource">
              <mySelect :dataList="authSource"
                        showString="请选择单位"
                        :disabled="usefulLifeDisable"
                        v-model="formCustom.authSource" />
            </FormItem>
            <FormItem label="MAC认证："
                      prop="macAuth">
              <RadioGroup v-model="formCustom.macAuth">
                <Radio label="开启">开启</Radio>
                <Radio label="关闭">关闭</Radio>
              </RadioGroup>
            </FormItem>
            <template v-if="formCustom.macAuth === '开启'">
              <FormItem label="有效期："
                        prop="usefulLife">
                <Input type="text"
                       :disabled="usefulLifeDisable"
                       v-model="formCustom.usefulLife"
                       style="width:200px"
                       placeholder="请输入有效期"></Input>
                <mySelect :dataList="usefulLifeUnit"
                          showString="请选择单位"
                          :disabled="usefulLifeDisable"
                          style="margin-left:8px;vertical-align:middle"
                          v-model="formCustom.usefulLifeUnit" />

              </FormItem>
            </template>
          </div>
        </Form>
      </div>
      <div class="formed-footer was-staff-footer"
           v-if="isEdit">
        <myButton btnType="info"
                  @click="handleSubmit('formCustom')">确定</myButton>
        <myButton @click="handleReset('formCustom')"
                  style="margin-left: 8px">取消</myButton>
      </div>
    </template>
  </div>
</template>

<script>
import api from "@/api/networkAccessService/index.js";
import icon from "@/util/icon.js";

export default {
  data () {
    const validateUsefulLife = (rule, value, callback) => {
      let regx = /^[0-9]*$/;
      if (!regx.test(value) || value.length === 0) {
        callback(new Error("请输入大于0的数字"));
      }
      callback();
    };

    return {
      isEdit: false, // 默认显示非编辑按钮，列表状态
      id: 0, // 策略id
      usefulLifeDisable: false, // 用户有效期默认可设置
      staffList: {
        authSource: "",
        macAuth: "",
        usefulLife: ""
      }, // 员工策略列表
      authSource: [
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
        }],
      usefulLifeUnit: ["小时", "天"],
      macAuthObj: {}, // mac认证类
      formCustom: {
        authMethod: 1, // 认证方式类别,默认1-用户名
        authSource: 0, // 密码认证源
        macAuth: "关闭", // MAC认证，默认关闭
        usefulLife: 7, // 时间长度，默认7
        usefulLifeUnit: "天" // 时间单位，默认天
      },
      ruleCustom: {
        usefulLife: [
          { validator: validateUsefulLife, trigger: "blur" }
        ]
      }
    };
  },
  created () {
    this.getStaffInfo();
  },
  methods: {
    /**
     * 进入编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    enterEdit () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "staff"
      });
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancelStaffEdit () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "staff"
      });
    },
    /**
     * 提交数据，验证表单通过请求保存数据
     * @param {*String 需要验证的表单ref名称} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          // 转换数据
          let macAuth = this.formCustom.macAuth === "开启" ? 1 : 0;
          let usefulLifeUnit = this.formCustom.usefulLifeUnit === "天" ? 1 : 0;
          let params = {
            id: this.id,
            authType: 2, // 2表示认证方式为用户名+密码，固定不变
            sourceType: this.formCustom.authSource,
            macFactor: macAuth,
            timeRanges: this.formCustom.usefulLife,
            timeUnits: usefulLifeUnit
          };
          this.axios({
            method: "post",
            data: params,
            url: api.staffUpdate
          })
            .then(response => {
              if (response.data.return_code === api.returnOk) {
                this.getStaffInfo();
                this.cancelStaffEdit();
              } else {
                this.showFailModal();
              }
            })
            .catch(function (error) {
              this.axios.error.handlingErrors(error);
            });
        }
      });
    },
    /**
     * 点击取消触发，重置表单并取消编辑状态
     * @param {*String 需要验证的表单ref名称} name
     * @author yezi 2019-08-02
     */
    handleReset (name) {
      this.$refs[name].resetFields();
      this.cancelStaffEdit();
    },
    /**
     * 获取员工策略信息
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getStaffInfo () {
      this.axios({
        method: "post",
        data: {},
        url: api.strategyStaff
      })
        .then(response => {
          if (response.data.return_code === api.returnOk) {
            this.id = response.data.id;
            this.assembleData(response.data.strategy);
          } else {
            console.log("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 处理显示页面和编辑页面数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    assembleData (strategy) {
      // 设置员工策略列表页面数据
      this.staffList.authSource = this.authSource[strategy.sourceType].label;
      // 开启或关闭图标及状态
      if (strategy.macFactor) {
        this.staffList.macAuth = "已开启";
        this.macAuthObj = icon.openIcon; ;
      } else {
        this.staffList.macAuth = "已关闭";
        this.macAuthObj = icon.closeIcon;
      }
      this.staffList.usefulLife = strategy.timeRanges;
      this.staffList.usefulLifeUnit = this.usefulLifeUnit[strategy.timeUnits];

      // 设置员工策略编辑页面数据
      this.formCustom.authSource = strategy.sourceType; // 密码认证源
      this.formCustom.macAuth = strategy.macFactor === 0 ? "关闭" : "开启"; // MAC认证
      this.formCustom.usefulLife = strategy.timeRanges; // 时间长度
      this.formCustom.usefulLifeUnit = this.usefulLifeUnit[strategy.timeUnits];// 时间单位
    },
    /**
     * 显示失败提示框
     * @param {*void}
     * @author yezi 2019-08-02
     */
    showFailModal () {
      this.$myModal.error({
        title: "保存失败",
        content: "请稍后重试"
      });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
