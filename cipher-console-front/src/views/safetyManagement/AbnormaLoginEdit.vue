<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">异常登录设置</div>
    </div>
    <div class="formed-container">
      <Form :label-width="130"
            class="formed-form">
        <FormItem label="异地登录验证：">
          <RadioGroup v-model="selected">
            <Radio label="open">开启</Radio>
            <Radio label="close">关闭</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="异地登录验证方式：">
          <mySelect :dataList="options2"
                    showString="请选择随机码"
                    valueKey="type"
                    labelKey="desc"
                    v-model="selected2" />
        </FormItem>
      </Form>
      <div class="formed-footer">
        <myButton btnType="info"
                  @click="save">保存</myButton>
        <myButton @click="cancel">取消</myButton>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      options2: [
        { type: 1, desc: "邮件随机码" },
        { type: 2, desc: "短信随机码" }
      ],
      selected: "",
      selected2: ""
    };
  },
  mounted () {
    var that = this;
    that.finddata();
  },
  methods: {
    cancel () {
      this.$router.go(-1);
    },
    // 初始化数据
    finddata () {
      let that = this;
      this.axios({
        method: "post",
        url: "/cipher/exceptionLogin/preList",
        data: {}
      })
        .then(function (res) {
          if (res.data.exceptionLogin.status === 1) {
            that.selected = "open";
          } else if (res.data.exceptionLogin.status === 0) {
            that.selected = "close";
          }

          that.selected2 = res.data.exceptionLogin.type;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 保存数据
    save () {
      var that = this;
      let params = {
        status: that.status,
        type: that.selected2
      };
      this.axios({
        method: "post",
        url: "/cipher/exceptionLogin/update",
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

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
</style>
