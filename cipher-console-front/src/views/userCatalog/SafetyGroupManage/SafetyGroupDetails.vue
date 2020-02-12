  <template>
  <div class="wrap">
    <div class="formed-header">
      <div class="formed-header-title">
        <span>{{teamName}}</span>
        <span>(安全组ID：{{dsgTeamId}})</span>
      </div>
      <div class="formed-header-btn">
        <myButton btnType="info"
                  @click="rename">重命名安全组</myButton>
      </div>
    </div>

    <div class="formed-container">
      <myComplexTabs class="tabs">
        <myPane label="成员列表"
                name="user">
          <userList></userList>
        </myPane>
        <myPane label="应用权限"
                name="app">
          <appList></appList>
        </myPane>
        <myPane label="用户导入设置"
                name="rule">
          <ruleList></ruleList>
        </myPane>
      </myComplexTabs>
    </div>

    <Modal v-model="modal1"
           class="create-modal"
           width="528"
           @on-ok="dosaveteamName">
      <p slot="header">
        <span class="title">重命名安全组</span>
        <span class="sub-title">（{{teamName}}）</span>
      </p>
      <div class="create-modal-content">
        <span style="font-size:14px">新的安全组名称：</span>
        <myInput v-model="teamName1"></myInput>
      </div>
    </Modal>
  </div>
</template>
<script>
import userList from "@/views/userCatalog/SafetyGroupManage/SafetyGroupUserList.vue";
import appList from "@/views/userCatalog/SafetyGroupManage/SafetyGroupAppList.vue";
import ruleList from "@/views/userCatalog/SafetyGroupManage/SafetyGroupRuleList.vue";
export default {
  // 引入组件
  components: {
    userList,
    appList,
    ruleList
  },
  data () {
    return {
      teamName1: "",
      modal1: false,
      teamName: "",
      teamId: "",
      dsgTeamId: ""
    };
  },
  created () {
    this.teamId = this.$route.query.teamId;
    this.teamName = this.$route.query.teamName;
    this.dsgTeamId = this.$route.query.dsgTeamId;
  },
  methods: {
    rename () {
      this.teamName1 = "";
      this.modal1 = true;
    },
    // 重命名安全组名称
    dosaveteamName () {
      var that = this;
      if (that.teamName1 === null || that.teamName1 === "") {
        that.$myMessage.error("安全组名称不能为空！");
        return;
      }
      let params = {
        id: that.teamId,
        teamName: that.teamName1
      };
      this.axios({
        method: "post",
        url: "/cipher/team/addorUpdate",
        data: params
      })
        .then(function (res) {
          if (res.data.return_code === 1) {
            that.$myMessage.success(res.data.return_msg);
            that.teamName = that.teamName1;
          } else if (res.data.return_code === 0) {
            that.$myMessage.error(res.data.return_msg);
            that.teamName1 = "";
          }
        })
        .catch(error => {
          console.log(error);
        });
    }

  }
};
</script>

<style lang='less' scoped>
@import "~@/assets/styles/formStyle.less";
@import "~@/assets/styles/modal.less";
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - @{headerHeight} - 1px");
    background-color: #fff;
    .border-radius;
    > .pane {
      height: 100%;
      overflow: auto;
    }
  }
}
</style>
