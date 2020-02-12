<template>
  <div class="formed-wrap">
    <transition name="fade"
                mode='out-in'>
      <div class="formed-form"
           v-if="!isEdit"
           :key='!isEdit'>
        <div class="formed-form-item">
          <span>Portal类型：</span>
          <span>{{showData.portalType===0?"员工":"访客"}}</span>
        </div>
        <div class="formed-form-item">
          <span>portal描述：</span>
          <span>{{showData.description}}</span>
        </div>
        <div class="formed-form-item">
          <span>portal地址：</span>
          <span>{{showData.portalAddress}}</span>
        </div>
        <div class="formed-form-item">
          <span>portal序号：</span>
          <span>{{showData.portalNum}}</span>
        </div>
      </div>
      <!-- 编辑部分 -->
      <div class="formed-form"
           v-else
           key='edit'>
        <Form ref="formValidate"
              :model="formValidate"
              :rules="ruleValidate"
              :label-width="120">
          <FormItem label="Portal类型：">
            <RadioGroup v-model="formValidate.portalType">
              <Radio v-for="item in portalTypeList"
                     :label="item.value"
                     :key="item.value">{{item.label}}</Radio>
            </RadioGroup>
          </FormItem>
          <FormItem label="portal描述："
                    prop="description">
            <Input v-model.trim="formValidate.description"
                   placeholder="请输入描述" />
          </FormItem>
          <div class="address">
            <FormItem label="portal地址："
                      prop="ip">
              <span>http:// </span>
              <Input v-model.trim="formValidate.ip"
                     placeholder="请输入IP地址或域名"
                     style="width:180px"
                     @on-focus="portalAddress='ip'" />
            </FormItem>
            <FormItem prop="port"
                      :label-width="0">
              <span> ： </span>
              <Input v-model.trim="formValidate.port"
                     placeholder="请输入端口号"
                     style="width:100px"
                     @on-focus="portalAddress='port'" />
            </FormItem>
          </div>
          <FormItem label="portal序号："
                    prop="portalNum">
            <Input v-model.trim="formValidate.portalNum"
                   placeholder="请输入1-99" />
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
    const validatePortalNum = (rule, value, callback) => {
      if (value < 1 || value > 99) {
        callback(new Error("请输入1-99"));
      } else {
        callback();
      }
    };
    return {
      isEdit: false, // 是否编辑状态
      showData: {
        portal: ""
      },
      portalTypeList: [
        {
          label: "员工Portal",
          value: 0
        },
        {
          label: "访客Portal",
          value: 1
        }
      ],
      formValidate: {
        portalType: "",
        description: "",
        ip: "",
        port: "",
        portalNum: ""
      },
      ruleValidate: {
        description: [{ required: true, message: "请输入描述", trigger: "blur" }],
        ip: [{ required: true, message: "请输入地址", trigger: "blur" }],
        port: [{ required: true, message: "请输入地址", trigger: "blur" }],
        portalNum: [
          { required: true, message: "请输入1-99", trigger: "blur" },
          { validator: validatePortalNum, trigger: "blur" }
        ]
      }
    };
  },
  props: ["data"],
  created () {
    let data = this.$props.data.wifiPotalPageSettingInfo;
    this.getData(data);
  },

  methods: {
    /**
     * 进入编辑状态，并重新获取数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    edit () {
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "base"
      });
      this.getData(this.$props.data.wifiPotalPageSettingInfo);
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
        name: "base"
      });
    },
    /**
     * 获取数据并赋值
     * @param {*Object 基本配置信息，从父组件传递的数据} data
     * @author yezi 2019-08-02
     */
    getData (data) {
      this.showData = Object.assign({}, data);
      this.formValidate.portalType = data.portalType;
      this.formValidate.description = data.description;
      this.formValidate.ip = data.ip;
      this.formValidate.port = data.port;
      this.formValidate.portalNum = data.portalNum;
    },
    /**
     * 提交数据并验证，验证成功执行保存数据函数
     * @param {*String iview表单ref名称，表单验证所需} name
     * @author yezi 2019-08-02
     */
    handleSubmit (name) {
      this.$refs[name].validate((valid) => {
        if (valid) {
          this.saveData();
        }
      });
    },
    /**
     * 保存数据，发送请求
     * @param {*void}
     * @author yezi 2019-08-02
     */
    saveData () {
      let params = {
        id: this.showData.id,
        portalType: this.formValidate.portalType,
        portalAddress: `http://${this.formValidate.ip}:${this.formValidate.port}`,
        description: this.formValidate.description,
        portalNum: this.formValidate.portalNum
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wifiportal/updateOne"
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
.address {
  text-align: left;
  /deep/ .ivu-form-item {
    display: inline-block;
  }
}
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
