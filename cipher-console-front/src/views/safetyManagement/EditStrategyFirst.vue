<template>
  <div class="formed-wrap">
    <div class="formed-header">
      <div class="formed-header-title">{{strategyName}}</div>
      <div class="formed-header-btn">
        <myButton btnType="info"
                  @click="toedit">编辑</myButton>
      </div>
    </div>
    <div class="formed-container">
      <div class="formed-form">
        <div class="formed-form-item">
          <span>策略名称：</span>
          <span>{{formItem.strategyName}}</span>
        </div>
        <div class="formed-form-item">
          <span>优先级：</span>
          <span>{{formItem.priority}}</span>
        </div>
        <div class="formed-form-item">
          <span>认证源：</span>
          <span>{{formItem.authType=== 0 ? "本地认证" : formItem.authType === 1 ? "AD域" : "JLT_ERP"}}</span>
        </div>
        <div class="formed-form-item">
          <span>二次认证：</span>
          <span> {{secondAuthOption}}</span>
        </div>
        <div class="formed-form-item">
          <span>二次认证模式：</span>
          <span> {{formItem.switches===0?"并行":"串行"}}</span>
        </div>
        <div class="formed-form-item">
          <span>生效范围：</span>
          <span>
            <span v-if=" isDefaultStarategy">全员</span>
            <span v-else> <a @click="toGroupList(index)"
                 v-for="(item,index) in groupList"
                 :key="index"
                 :name="item"
                 class="link">
                {{item.parentPathName}}
                <br>
              </a></span>
          </span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import { mapState, mapMutations } from "vuex";
export default {
  data () {
    return {
      isDefaultStarategy: true,
      groupList: [],
      strategyName: "",
      id: "",
      secondAuthOption: "", // 选择的二次认证方式
      formItem: {
        strategyName: "",
        priority: 0,
        authType: 0,
        secondAuth: 0,
        switches: 0,
        groupIds: "",
        id: 0,
        scaveAuthState: 1
      }
    };
  },
  computed: {
    // ...mapState({ orgStrList: "selectOSTree" })
    ...mapState(["group"])
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
    toGroupList (index) {
      this.$router.push({
        path: "/userCatalogOS"
      });
      this.changeGroup(this.groupList[index]);
    },
    ...mapMutations(["changeGroup"]),
    toedit () {
      this.$router.push({
        name: "strategyEdit",
        query: { id: this.id, strategyName: this.strategyName }
      });
    },
    // 初始化数据
    finddata (params) {
      let that = this;
      this.axios({
        method: "post",
        url: "/cipher/identityStrategy/updatePre",
        data: params
      })
        .then(function (res) {
          if (res.data.code === 0) {
            if (res.data.identityObj.priority === 0) {
              that.isDefaultStarategy = true;
            } else {
              that.isDefaultStarategy = false;
            }
            that.groupList = res.data.groupList;
            that.formItem = res.data.identityObj;
            let tempArray = [];
            if (that.formItem.secondAuth === 0) {
              var secondAuthType = that.formItem.secondAuthType;
              // if (secondAuthType.twoAuthSf === 0) {
              //   tempArray.push("赛赋扫码");
              // }
              if (secondAuthType.twoAuthDt === 0) {
                tempArray.push("动态密码");
              }
              if (secondAuthType.twoAuthDd === 0) {
                tempArray.push("钉钉扫码");
              }
              if (secondAuthType.twoAuthDingPush === 0) {
                tempArray.push("钉钉PUSH认证");
              }
              if (secondAuthType.twoAuthDb === 0 && that.formItem.switches) {
                tempArray.push("大白扫码");
              }
              if (secondAuthType.twoAuthWx === 0 && that.formItem.switches === 0) {
                tempArray.push("企业微信扫码");
              }
              if (secondAuthType.twoAuthNum === 0) {
                tempArray.push("手机随机码");
              }
              if (secondAuthType.twoAuthMail === 0 && that.formItem.switches) {
                tempArray.push("邮件随机码");
              }
            } else {
              that.secondAuthOption = "关闭";
            }
            if (tempArray.length) {
              that.secondAuthOption = tempArray.join(",");
            }
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    }
  }
};
</script>
<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
.wrap {
  background-color: #fff;
}
.header {
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
.text {
  text-align: left;
}
</style>
