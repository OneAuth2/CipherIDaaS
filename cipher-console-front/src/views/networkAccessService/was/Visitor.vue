<template>
  <div class="formed-wrap">
    <div class="formed-form"
         v-if="!isEdit"
         key="edit">
      <div class="formed-form-item">
        <span>访客账号源：</span>
        <span>{{visitorList.sourceType}}</span>
      </div>
      <template v-if="adminAdd">
        <div class="formed-form-item">
          <span>访客有效期：</span>
          <span>{{visitorList.timeRange}} 小时</span>
        </div>
        <div class="formed-form-item">
          <span>验证方式：</span>
          <span>{{visitorList.authType}}</span>
        </div>
        <div class="formed-form-item">
          <span>被访人信息校验：</span>
          <span>{{visitorList.needStuffInfo}}</span>
        </div>
      </template>
      <div class="formed-form-item">
        <span>员工帮忙扫码认证：</span>
        <span>
          {{visitorList.needStuffScan}}
          <Icon :class="needStuffScanObj.class"
                :type="needStuffScanObj.type" />
          <br />
          <span>{{visitorList.otherAuthtype}}</span>
        </span>
      </div>
    </div>
    <div class="formed-form was-visitor-body"
         v-else
         key="list">
      <Form ref="formCustom"
            :model="formCustom"
            :rules="ruleCustom"
            :label-width="130">
        <div class="form">
          <FormItem label="访客账号源："
                    prop="sourceType">
            <RadioGroup v-model="formCustom.sourceType">
              <Radio :label="0">管理员添加</Radio>
              <Radio :label="1">访客自注册</Radio>
            </RadioGroup>
          </FormItem>
          <template v-if="adminAdd">
            <FormItem label="访客有效期："
                      prop="timeRange">

              <InputNumber :min="2"
                           :precision="0"
                           v-model="formCustom.timeRange"
                           style="width:68px"
                           placeholder="请输入有效期"></InputNumber>
              <span style="margin-left:8px">单位（小时）</span>
            </FormItem>
            <FormItem label="验证方式："
                      prop="authType">
              <RadioGroup v-model="formCustom.authType">
                <Radio v-for="(item) in authTypeList"
                       :label="item.value"
                       :key="item.value">{{item.label}}</Radio>
              </RadioGroup>
            </FormItem>
            <FormItem label="被访人信息校验："
                      prop="needStuffInfo">
              <RadioGroup v-model="formCustom.needStuffInfo">
                <Radio :label="0">否</Radio>
                <Radio :label="1">是</Radio>
              </RadioGroup>
            </FormItem>
          </template>
          <FormItem label="员工帮忙扫码认证："
                    prop="needStuffScan">
            <RadioGroup v-model="formCustom.needStuffScan">
              <Radio :label="0">开启</Radio>
              <Radio :label="1">关闭</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem prop="otherAuthtype"
                    v-if="formCustom.needStuffScan===0">
            <CheckboxGroup v-model="formCustom.otherAuthtype">
              <Checkbox v-for="(item) in otherAuthtypeList"
                        :label="item.value"
                        :key="item.value">{{item.label}}</Checkbox>
            </CheckboxGroup>
          </FormItem>
        </div>
      </Form>
    </div>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="handleSubmit('formCustom')">确定</myButton>
      <myButton @click="handleReset('formCustom')"
                style="margin-left: 8px">取消</myButton>
    </div>
  </div>
</template>

<script>
import api from "@/api/networkAccessService/index.js";
import icon from "@/util/icon.js";
export default {
  data () {
    return {
      isEdit: false, // 默认非编辑状态
      id: 0, // 策略id
      adminAdd: true, // 管理员添加，默认true,false代表访客自注册
      sourceType: [
        "管理员添加", "访客自注册"
      ],
      authTypeList: [
        {
          value: 0,
          label: "访客接收短信密码"
        },
        {
          value: 1,
          label: "员工接收短信密码"
        },
        {
          value: 2,
          label: "员工接收邮件密码"
        }
      ],
      otherAuthtypeList: [
        {
          value: 1,
          label: "赛赋扫码"
        },
        {
          value: 2,
          label: "钉钉扫码"
        },
        {
          value: 3,
          label: "大白认证"
        }
      ],
      visitorList: {
        sourceType: "", // 访客账号源
        timeRange: 2, // 访客有效期
        authType: "", // 验证方式
        needStuffInfo: "", // 被访问人信息
        needStuffScan: "", // 员工帮忙扫码认证
        otherAuthtype: "" // 扫码方式
      }, // 访客策略列表
      needStuffScanObj: {}, // 员工帮忙扫码认证
      formCustom: {
        sourceType: 0, // 访客账号源
        timeRange: 2, // 访客有效期
        authType: 1, // 验证方式
        needStuffInfo: 0, // 被访问人信息
        needStuffScan: 0, // 员工帮忙扫码认证
        otherAuthtype: [] //  扫码方式
      },
      ruleCustom: {
        // timeRange: [
        //   { validator: validateTimeRange, trigger: "blur" }
        // ]
      }
    };
  },
  created () {
    this.getVisitorInfo();
  },
  watch: {
    "formCustom.sourceType" () {
      this.setAdminStatus(this.formCustom.sourceType);
    }
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
        name: "visitor"
      });
    },
    /**
     * 取消编辑状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    cancelVisitorEdit () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "visitor"
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
          let tempType = { authSf: 1, authDd: 1, authDb: 1 };
          if (this.formCustom.needStuffScan === 0) {
            for (let item of this.formCustom.otherAuthtype) {
              switch (item) {
                case 1:
                  tempType.authSf = 0;
                  break;
                case 2:
                  tempType.authDd = 0;
                  break;
                case 3:
                  tempType.authDb = 0;
                  break;
              }
            }
          }
          let params = {};
          if (this.formCustom.sourceType) { // 根据访客账号源 不同，传参不同
            params = {
              id: this.id,
              sourceType: this.formCustom.sourceType,
              timeRange: this.formCustom.timeRange,
              authType: this.formCustom.authType,
              needStuffInfo: this.formCustom.needStuffInfo,
              needStuffScan: this.formCustom.needStuffScan,
              authSf: tempType.authSf,
              authDd: tempType.authDd,
              authDb: tempType.authDb
            };
          } else {
            params = {
              id: this.id,
              sourceType: this.formCustom.sourceType,
              needStuffScan: this.formCustom.needStuffScan,
              authSf: tempType.authSf,
              authDd: tempType.authDd,
              authDb: tempType.authDb
            };
          }

          this.axios({
            method: "post",
            data: params,
            url: api.visitorUpdate
          })
            .then(response => {
              if (response.data.return_code === api.returnOk) {
                this.getVisitorInfo();
                this.cancelVisitorEdit();
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
     * 显示失败提示框
     * @param {*void}
     * @author yezi 2019-08-02
     */
    showFailModal () {
      this.$myModal.error({
        title: "保存失败",
        content: "请稍后重试"
      });
    },
    /**
     * 点击取消触发，重置表单并取消编辑状态
     * @param {*String 需要验证的表单ref名称} name
     * @author yezi 2019-08-02
     */
    handleReset (name) {
      this.$refs[name].resetFields();
      this.cancelVisitorEdit();
    },
    /**
     * 获取访客策略信息
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getVisitorInfo () {
      this.axios({
        method: "post",
        data: {},
        url: api.strategVisitor
      })
        .then(response => {
          if (response.data.return_code === api.returnOk) {
            this.id = response.data.id;
            this.assembleData(response.data.strategy);
            this.cancelVisitorEdit();
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
      // 设置管理源添加状态
      this.setAdminStatus(strategy.sourceType);
      // 设置访客策略列表页面数据
      this.visitorList.sourceType = this.sourceType[strategy.sourceType];
      this.visitorList.timeRange = strategy.timeRange;
      this.visitorList.authType = strategy.authType >= 0 ? this.authTypeList[strategy.authType].label : "";
      this.visitorList.needStuffInfo = strategy.needStuffInfo === 0 ? "否" : "是";
      // 开启或关闭图标及状态
      if (strategy.needStuffScan === 0) {
        this.visitorList.needStuffScan = "已开启";
        this.needStuffScanObj = icon.openIcon; ;
      } else {
        this.visitorList.needStuffScan = "已关闭";
        this.needStuffScanObj = icon.closeIcon;
      }
      this.formCustom.otherAuthtype = [];
      let tempArray = [];
      if (strategy.authSf === 0) {
        this.formCustom.otherAuthtype.push(1);
        tempArray.push("赛赋扫码");
      }
      if (strategy.authDd === 0) {
        this.formCustom.otherAuthtype.push(2);
        tempArray.push("钉钉扫码");
      }
      if (strategy.authDb === 0) {
        this.formCustom.otherAuthtype.push(3);
        tempArray.push("大白认证");
      }
      this.visitorList.otherAuthtype = tempArray.join("，");
      // 设置访客策略编辑页面数据
      this.formCustom.sourceType = strategy.sourceType; // 访客账号源
      this.formCustom.timeRange = strategy.timeRange; // 访客有效期
      this.formCustom.authType = strategy.authType >= 0 ? this.authTypeList[strategy.authType].value : 0; // 验证方式
      this.formCustom.needStuffInfo = strategy.needStuffInfo;// 被访人信息
      this.formCustom.needStuffScan = strategy.needStuffScan;// 被访人信息
    },
    /**
     * 设置管理源添加状态还是访客自注册状态
     * @param {*void}
     * @author yezi 2019-08-02
     */
    setAdminStatus (sourceType) {
      this.adminAdd = sourceType !== 0;
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
/deep/ .ivu-radio-wrapper,
.ivu-checkbox-wrapper {
  margin-right: 8px;
}
</style>
