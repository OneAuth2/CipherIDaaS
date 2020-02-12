<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">账号锁定编辑</div>
    </div>
    <div class="formed-container">
      <Form :label-width="130"
            class="formed-form">
        <FormItem label="锁定账号：">
          <RadioGroup v-model="selected">
            <Radio label="1">开启</Radio>
            <Radio label="0">关闭</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="账号锁定策略：">
          <div class="formed-form-item-part">
            <InputNumber :min="0"
                         :precision="0"
                         v-model="formItem.timeRang" />分钟内，身份认证连接失败
          </div>
          <div class="formed-form-item-part">
            <InputNumber :min="1"
                         :step="1"
                         :precision="0"
                         v-model="formItem.failCount" />次后，将账号锁定
          </div>
          <div class="formed-form-item-part">
            <InputNumber :min="0"
                         :precision="0"
                         v-model="formItem.freezingTime" />分钟。
          </div>
        </FormItem>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="saveset">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
  </div>
</template>
<script>
export default {
  data () {
    return {
      buttonSize: "large",
      formItem: { timeRang: 0, failCount: 0, freezingTime: 0 },
      selected: "1"
    };
  },
  mounted () {
    this.finddata();
  },
  methods: {
    cancel () {
      this.$router.go(-1);
    },
    finddata () {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/loginfail/list",
        data: {}
      })
        .then(function (res) {
          that.formItem = res.data;
          if (res.data.status === 1) {
            that.selected = "1";
          } else if (res.data.status === 0) {
            that.selected = "0";
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    saveset () {
      var that = this;
      let params = {
        failCount: that.formItem.failCount,
        freezingTime: that.formItem.freezingTime,
        id: that.formItem.id,
        status: that.selected,
        timeRang: that.formItem.timeRang
      };

      this.axios({
        method: "post",
        url: "/cipher/loginfail/update",
        data: params
      })
        .then(function (res) {
          that.cancel();
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
    }
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/formStyle.less";
.formed-form-item-part {
  padding-left: 0;
  /deep/ .ivu-input-number {
    margin-right: 16px;
  }
}
</style>
