<template>
  <div class="formed-wrap">
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">账号绑定和匹配</h3>
        <div class="formed-form-item"
             v-if="accountMatchList[accountMatchShow]">
          <span>{{accountMatchList[accountMatchShow].label}}:</span>
          <span>{{accountMatchList[accountMatchShow].tip}}</span>
        </div>
      </div>
    </div>

    <Form class="formed-form"
          :label-width="130"
          v-else>
      <div class="formed-form-part">
        <h3 class="formed-form-part-title">账号绑定和匹配</h3>
        <FormItem label="规则选择：">
          <RadioGroup v-model="selectAccountMatch">
            <Radio v-for="item in  accountMatchList"
                   :label="item.value"
                   :key="item.value">
              <span class="label">{{item.label}}：</span>
              <span class="tip">{{item.tip}}</span>
            </Radio>
          </RadioGroup>
        </FormItem>
      </div>
    </Form>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="saveAccountMatch">保存</myButton>
      <myButton @click="cancelStatus">取消</myButton>
    </div>

    <!-- 成功提示框 -->
    <Notice :text="message.text"
            :show="message.show"
            :duration="message.duration"
            :onClose="message.onClose"
            v-if="message.show"
            @close="close"></Notice>
  </div>
</template>

<script>
import Notice from "@/components/modal/Notice.vue";
export default {
  components: {
    Notice
  },
  data () {
    return {
      isEdit: false, // 是否编辑状态
      accountMatchShow: "", // 后台获取的匹配规则，回显页面展示
      selectAccountMatch: "", // 选择的匹配规则
      accountMatchList: [ // 规则选项
        {
          label: "手机号",
          tip: "钉钉手机号与本地手机号相同时认为是同一用户。",
          value: 0
        },
        {
          label: "邮箱",
          tip: "钉钉邮箱与本地邮箱相同时认为是同一用户。",
          value: 1
        },
        {
          label: "手机号 and 邮箱",
          tip: "钉钉手机号、邮箱与本地手机号、邮箱都相同时认为是同一用户。",
          value: 2
        },
        {
          label: "手机号 or 邮箱",
          tip: "钉钉手机号与本地手机号或钉钉邮箱与本地邮箱相同认为是同一用户。",
          value: 3
        }
      ],
      message: {
        text: "保存成功!",
        show: false,
        duration: 1,
        onClose: this.close
      }
    };
  },
  mounted () {
    this.getAccountMatch();
  },
  methods: {
    editStatus () {
      this.getAccountMatch();
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "account"
      });
    },
    cancelStatus () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "account"
      });
    },
    getAccountMatch () { // 获取匹配规则数据
      let params = {};
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/dingConfig/getMatchRule"
      })
        .then(res => {
          if (res.status === 200) {
            this.accountMatchShow = res.data.rule;
            this.selectAccountMatch = res.data.rule;
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    saveAccountMatch () { // 保存匹配规则数据
      let params = { rule: this.selectAccountMatch };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/dingConfig/editMatchRule"
      })
        .then(res => {
          if (res.status === 200) {
            this.toggle();
            this.getAccountMatch();
            this.cancelStatus();
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 成功提示框显示隐藏
    close () {
      this.message.show = false;
    },
    toggle () {
      this.message.show = true;
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
