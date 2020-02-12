<template>
  <div class="nabBarList">
    <template v-if="path !=='' && finalData.length">
      <div ref="leftMenu">
        <Menu :theme="theme2"
              :open-names="open"
              :active-name="activeNames"
              accordion>
          <template v-for="item in finalData">
            <Submenu :name="item.id"
                     v-bind:key="item.id">
              <template slot="title">
                <template v-if="item.id !== 4">
                  <Icon :type="item.icon"
                        v-if="item.menuList.length" />
                </template>
                <template v-else>
                  <i class="icon icon-shield"></i>
                </template>
                {{item.resourceName}}
              </template>
              <template v-for="childItem in item.menuList">
                <MenuItem :name="childItem.name"
                          v-bind:key="childItem.id"
                          @click.native="skipNewUrl(childItem,item.resourceName)">
                <div :ref="childItem.name">{{childItem.resourceName}}</div>
                </MenuItem>
              </template>
            </Submenu>
          </template>
        </Menu>
      </div>
    </template>
  </div>
</template>

<script>
import api from "@/api/api.js";
import { mapState, mapMutations } from "vuex";
import router from "@/router/path.js";
export default {
  name: "NavBarCollapse",
  data () {
    return {
      allMenuList: [],
      theme2: "light",
      finalData: [],
      open: [],
      activeIdTemp: "",
      selParentId: 1,
      activeNames: "",
      tabId: "",
      tabName: "",
      isFirst: true // 是否是刚加载组件
    };
  },
  watch: {
    allMenuList: {
      handler () {
        this.assemblyData();
        this.triggerClick(); // 刷新模拟点击节点展开
      },
      // immediate: true,
      deep: true
    },
    $route () {
      this.changeRoute(this.$route.meta.title); // 随路由改变menu选中状态
      // tab切换
      this.changeSelTab(this.tabName);

      // 模拟menu展开
      if (!this.isFirst) { // 非刷新点击模拟点击节点
        this.$nextTick(function () {
          let index = this.open[0] - 1;
          let parent = this.$refs.leftMenu.childNodes[0].childNodes[index].childNodes[0];
          parent.click();
        });
      };
    }
  },
  created () {
    this.init();
    this.assemblyData();
  },
  computed: {
    ...mapState(["tab", "path"])
  },
  methods: {
    init () {
      this.axios({
        method: "post",
        data: {},
        url: api.getAllMenuList
      })
        .then(response => {
          let data = response.data;
          if (data.length) {
            this.allMenuList = this.$common.clone(data, this);
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error, this);
        });
    },
    /**
     * 组装数据
     */
    assemblyData () {
      let data = this.allMenuList;
      let assemblyData = [];
      // 组装根级
      for (var i in data) {
        // console.log(data[i].resourceName);
        let temp = {};
        let number = Number(i) + 1;
        let icon = this.addIcon(number);
        temp.id = number;
        temp.idString = String(number);
        temp.icon = icon;
        temp.resourceName = data[i].resourceName;
        let menuList = [];
        let tempChild = data[i].menuList;
        let len = tempChild.length;
        // 组装子级
        if (len) {
          for (var j in tempChild) {
            let menuListTemp = {};
            let numberChild = Number(j) + 1;
            menuListTemp.id = numberChild;
            menuListTemp.name = number + "-" + numberChild;
            menuListTemp.resourceName = tempChild[j].resourceName;
            menuListTemp.link = this.getUrl(tempChild[j].resourceId); // 跳转url
            menuList.push(menuListTemp);
          }
        }
        temp.menuList = menuList;
        assemblyData.push(temp);
      }
      this.finalData = assemblyData;
      // this.finalData = this.changeMenuList(assemblyData);
    },
    /**
     * 获取跳转url
     * resourceId后端接口固定唯一
     */
    getUrl (resourceId) {
      switch (resourceId) {
        case 8: // 组织结构
          return router.userCatalogOS;
        case 9: // 安全组管理
          return router.userCatalogSGM;
        case 10: // AD活动目录
          return router.userCatalogAD;
        case 180: // 邀请管理
          return router.inviteManagement;
        case 187: // 钉钉同步
          return router.userCatalogDS;
        case 191: // 注册审批
          return router.userCatalogRA;
        case 195: // 企业微信同步
          return router.userCatalogWX;
        case 11: // 资源管理
          return router.resourceManageAM;
        case 193: // 设备管理
          return router.resourceManageDM;
        // case 12: // 应用发布
        //   return router.resourceManageAI;
        // case 13: // Radius配置
        //   return router.exterCertServiceRadius;
        case 14: // 无线认证策略
          return router.netAccessServiceWAS;
        // case 15: // AC配置
        //   return router.netAccessServiceAC;
        case 16: // portal设置
          return router.netAccessServicePortal;
        case 17: // 身份认证策略
          return router.safetyManagementIP;
        case 18: // 异常登录设置
          return router.safetyManagementAL;
        case 19: // 登录失败控制
          return router.safetyManagementLF;
        case 20: // 口令管理
          return router.safetyManagementCommand;
        case 21: // OTP管理
          return router.safetyManagementOTP;
        case 22: // 移动设备管理
          return router.safetyManagementMD;
        case 190: // 系统认证设置
          return router.safetyManagementSA;
        case 23: // 用户审计
          return router.auditReportUA;
        case 24: // 应用审计
          return router.auditReportApp;
        case 25: // 管理员审计
          return router.auditReportAdmin;
        // case 26: // 无线访客记录
        //   return router.auditReportWifi;
        //   break;
        case 181: // 无线审计
          return router.auditReportWireless;
        case 194: // 设备审计
          return router.auditReportFacility;
        case 182: // 无线访客管理
          return router.netAccessServiceWVM;
        case 184: // WIFI绑定管理
          return router.netAccessServiceWBM;
        case 185: // 无线免证设备
          return router.netAccessServiceWNVD;
        case 188: // WIFI认证设置
          return router.netAccessServiceWASU;
        case 192: // WIFI认证设置
          return router.netAccessServiceOU;
        case 29: // 管理员设置
          return router.systemSetupAdmin;
        case 30: // 账号属性设置
          return router.systemSetupAP;
        case 31: // 短信验证码设置
          return router.systemSetupAdminMsg;
        case 32: // 邮箱验证码设置
          return router.systemSetupAdminMail;
        case 186: // 认证源
          return router.systemSetupAuthSource;
        case 189: // 页面设置
          return router.systemSetupPageSet;
        default:
          console.log("非法资源id");
          return "/";
      }
    },
    skipNewUrl (item, parentName) {
      item.parentName = parentName;
      // let name = item.name;
      this.$router.push(item.link);
      // this.selectTab(item);
    },
    // 遍历meunu，找到名字
    mapMenu (name) {
      for (let i = 0; i < this.allMenuList.length; i++) {
        for (let key in this.allMenuList[i]) {
          if (Array.prototype.isPrototypeOf(this.allMenuList[i][key])) { // 是数组继续遍历
            let arrTemp = this.allMenuList[i][key];

            for (let j = 0; j < arrTemp.length; j++) {
              if (name === arrTemp[j].resourceName) {
                this.open = (i + 1).toString().split("");
                this.activeNames = (i + 1) + "-" + (j + 1);
              }
            }
          }
        }
      }
    },
    changeRoute (routeName) {
      if (routeName === "首页") {
        this.open = ["1"];
        this.activeNames = "";
        this.tabName = "首页";
      } else {
        let name = routeName.split("/")[1];
        this.tabName = name;
        this.mapMenu(name);
      }
    },
    // 根据路由切换标签
    changeSelTab (tabName) {
      if (this.tabName === "") {
        return;
      }
      let tab = this.$common.clone(this.tab);
      // 包含home在内所有的tab标签
      let newTabFlag = true; // 记录新标签项，默认是

      /**
       * 1.选中状态
       * 1).点击左侧选项名在tab中存在时,设置为选中状态
       * 2).点击左侧选项名在tab中不存在时,设置为非选中状态
       * 2.新标签
       * 1).遍历检查tab中是否存在当前项，存在时设置为不是新标签，不存在保持是新标签记录
       */
      for (var i in tab) {
        tab[i].id = Number(i);
        if (tabName === tab[i].resourceName) {
          // 名字一样时,设置为选中状态
          tab[i].select = true;
          tab[0].activeId = tab[i].id; // 记录当前选中的标签id
          newTabFlag = false;
        } else {
          tab[i].select = false;
        }
      }
      if (newTabFlag) {
        // 如果tab列表中不存在选择tab则添加新tab项
        let temp = {};
        temp.id = tab.length;
        temp.name = this.$route.name;
        temp.resourceName = tabName;
        temp.href = this.$route.path;
        temp.select = true;
        tab[0].activeId = temp.id; // 记录当前选中的标签id
        tab.push(temp);
      }
      this.changeTab(tab);
    },
    // 更改菜单
    // changeMenuList (data) {
    //   let finalData = data.filter(function (elem, index) {});
    // },
    // 增加图标字段
    addIcon (id) {
      var iconType;
      switch (id) {
        case 1:
          iconType = "md-reorder";
          break;
        case 2:
          iconType = "ios-help-buoy";
          break;
        case 3:
          // iconType = "md-git-network";
          iconType = "ios-wifi";
          break;
        case 4:
          iconType = "icon-shield";
          break;
        case 5:
          iconType = "ios-pie";
          break;
        case 6:
          iconType = "md-settings";
          break;
        default:
          iconType = "md-settings";
      }
      return iconType;
    },
    // 刷新页面，模拟点击左侧导航，放到数据更新后
    triggerClick () {
      this.isFirst = false;
      let parentData = this.finalData;
      // 刷新进入是/路径时
      if (this.path === "/") {
        this.open = ["1"];
        // 展开左侧导航
        this.$nextTick(() => {
          let child = this.$refs.leftMenu.childNodes[0].childNodes[0]
            .childNodes[0];
          child.click(); // 点击父节点
        });
        return;
      }
      // 刷新进入时，是非/路径时
      for (var i in parentData) {
        let data = parentData[i].menuList;
        for (var j in data) {
          let item = data[j];
          if (this.path === item.link) {
            this.selParentId = parentData[i].id;
            let parentName = String(this.selParentId);
            this.open = [parentName];
            this.activeIdTemp = item.name;
            this.skipNewUrl(item, parentData[j].resourceName);

            // 展开左侧导航
            this.$nextTick(() => {
              let child = this.$refs.leftMenu.childNodes[0].childNodes[this.selParentId - 1].childNodes[0];
              child.click(); // 点击父节点

              let itemDom = this.$refs[item.name][0];
              itemDom.click(); // 点击子节点
            });
          }
        }
      }
    },
    ...mapMutations(["changeTab", "changeBreadCrumb"])
  }
};
</script>

<style scoped lang="less">
@import "~@/assets/styles/common.less";
@import "~@/assets/fonts/iconfont.css";
.nabBarList {
  text-align: left;
  background-color: #4f5f6f;
}

.nabBarList,
.nabBarList > div,
.nabBarList > div > div,
.nabBarList > div > div > div {
  width: 100%;
  min-width: 200px;
}
.nabBarList .ivu-menu {
  width: 100% !important;
  min-width: 200px !important;
  background-color: #4f5f6f;
  color: #fff;
  position: relative;
  left: 1px;
}
.ivu-menu li {
  color: #fff;
}

.title {
  padding: 30px;
}
.title > i {
  font-size: 36px;
  display: block;
  color: #fff;
}
.title > span {
  font-size: 20px;
  display: block;
}
.icon {
  width: 16px;
  margin-right: 7px;
  vertical-align: middle;
}
/deep/ .ivu-menu-item {
  font-weight: 300;
}
/deep/ .ivu-menu-submenu-title {
  font-weight: 800;
}
/deep/
  .ivu-menu-light.ivu-menu-vertical
  .ivu-menu-item-active:not(.ivu-menu-submenu) {
  background: @colorDarkBlue;
  z-index: 2;
  color: #fff;
}
</style>
