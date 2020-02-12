<template>
  <div class="formed-form">
    <div class="formed-form-item">
      <span>账号：</span>
      <span>{{userList.accountNumber}}</span>
    </div>
    <div class="formed-form-item">
      <span>姓名：</span>
      <span>{{userList.userName}}</span>
    </div>
    <div class="formed-form-item">
      <span>邮箱：</span>
      <span>{{userList.mail}}</span>
    </div>
    <div class="formed-form-item">
      <span>手机号：</span>
      <span>{{userList.phoneNumber}}</span>
    </div>
    <div class="formed-form-item">
      <span>身份证号：</span>
      <span>{{userList.idNum}}</span>
    </div>
    <div class="formed-form-item">
      <span>性别：</span>
      <span>{{userList.gender}}</span>
    </div>
    <div class="formed-form-item">
      <span>生日：</span>
      <span>{{userList.birthday}}</span>
    </div>
    <div class="formed-form-item">
      <span>职位：</span>
      <span>{{userList.job}}</span>
    </div>
    <div class="formed-form-item">
      <span>工号：</span>
      <span>{{userList.jobNo}}</span>
    </div>
    <div class="formed-form-item">
      <span>工作部门：</span>
      <span>
        <a class="link"
           @click="toGroupList(index)"
           v-for="(item, index) in groupList"
           :value="index"
           :key="index">
          {{item.parentPathName}}
          <br>
        </a>
      </span>
    </div>
    <div class="formed-form-item">
      <span>安全组：</span>
      <span>
        <a class="link"
           @click="toTeamDetail(index)"
           v-for="(item ,index) in teamList"
           :value="index"
           :key="index">
          {{item.teamName}}
          <br>
        </a>
      </span>
    </div>
    <div class="formed-form-item">
      <span>账号绑定：</span>
      <span>
        <div class="text">
          AD域:
          <span>{{adbinding}}</span>
          <span class="ad-name">{{adName}}</span>
        </div>
        <div class="text">
          赛赋认证:
          <span>{{saiFuBinding}}</span>
        </div>

        <div class="text">
          钉钉:
          <span>{{bindingDingDing}}</span>
        </div>
        <div class="text">
          大白认证::
          <span>{{dabbyBinding}}</span>
        </div>
        <div class="text">
          企业微信认证:
          <span>{{wxBinding}}</span>
        </div>
      </span>
    </div>
  </div>
</template>
<script>
import { mapState, mapMutations } from "vuex";
export default {
  data () {
    return {
      accountNumber: "",
      uuid: 0,
      groupId: 0,
      groupList: [],
      teamList: [],
      userList: {}, // 用户列表
      adbinding: "",
      adName: "",
      saiFuBinding: "",
      bindingDingDing: "",
      dabbyBinding: "",
      wxBinding: "",
      selectDepartment: [], // 选中的工作部门
      selectSecurityGroup: [] // 选中的安全组
    };
  },
  props: {
    // dataMsg: {
    //   type: Object,
    //   default: () => { }
    // },
    // userList: {
    //   type: Object,
    //   default: () => { }
    // }
  },
  // watch: {
  //   dataMsg: {
  //     handler: function () {
  //       // console.log(this.dataMsg);
  //       // this.userList = this.dataMsg.user;
  //       this.dealData();
  //     },
  //     deep: true
  //   }
  // },
  computed: {
    // userList () {
    //   return this.dataMsg.user;
    // },
    ...mapState(["group"])
  },
  created () {
    this.accountNumber = this.$route.query.accountNumber;
    this.groupId = this.$route.query.groupId;
    this.uuid = this.$route.query.uuid;
    this.getUserDetails();
  },
  // mounted () {
  //   this.getUserDetails();
  // },
  methods: {
    toGroupList (index) {
      this.$router.push({
        path: "/userCatalogOS"
      });
      this.changeGroup(this.groupList[index]);
    },
    // 跳转到安全组详情
    toTeamDetail (index) {
      this.$router.push({
        path: "/userCatalogSGM/detail",
        query: {
          teamId: this.teamList[index].id,
          teamName: this.teamList[index].teamName,
          dsgTeamId: this.teamList[index].dsgTeamId
        }
      });
    },
    edit () {
      this.$emit("msg", false);
    },
    getUserDetails () {
      let params = {
        queryType: "",
        accountNumber: this.accountNumber,
        uuid: this.uuid,
        groupId: this.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/userMsg/modifyUser"
      })
        .then(res => {
          if (res.data.code === 0) {
            this.userList = res.data.msg.user;
            this.birthday = res.data.msg.user.birthday;
            this.adbinding = res.data.msg.adbinding ? "已绑定" : "未绑定";
            this.adName = res.data.msg.adbinding || "";
            this.saiFuBinding = res.data.msg.saiFuBinding ? "已绑定" : "未绑定";
            this.bindingDingDing = res.data.msg.bindingDingDing ? "已绑定" : "未绑定";
            this.dabbyBinding = res.data.msg.dabbyBinding ? "已绑定" : "未绑定";
            this.wxBinding = res.data.msg.wxBinding ? "已绑定" : "未绑定";
            this.groupList = res.data.msg.groupList;
            this.teamList = res.data.msg.teamList;
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    ...mapMutations(["changeGroup"])
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
