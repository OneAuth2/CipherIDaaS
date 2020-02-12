<template>
  <div class="page-set formed-wrap">
    <myButton btnType="info"
              class="portal-detail-change__btn"
              v-if="isShowEdit"
              @click="changeStatus">编辑</myButton>
    <myComplexTabs v-model="PageSet">
      <myPane label="Portal基本配置"
              name="base">
        <Base ref="base"
              :data="showData"
              @statusChange="getChildStatus"
              @ListenChild="reGetData"
              v-if="isGetData" />
      </myPane>
      <myPane label="网络配置"
              name="ac">
        <Ac ref="ac"
            :data="showData"
            v-if="isGetData"
            @statusChange="getChildStatus"
            @ListenChild="reGetData" />
      </myPane>
      <myPane label="Portal页面配置"
              name="page">
        <Page ref="page"
              :data="showData"
              v-if="isGetData"
              @statusChange="getChildStatus"
              @ListenChild="reGetData" />
      </myPane>
    </myComplexTabs>
  </div>
</template>

<script>
import Base from "./BaseConfig.vue";
import Ac from "./AcConfig.vue";
import Page from "./PageConfig.vue";

export default {
  components: {
    Base,
    Ac,
    Page
  },
  data () {
    return {
      PageSet: "base",
      id: "",
      componentStatus: { // 组件是否编辑状态
        isPortalBaseEdit: false,
        isPortalAcEdit: false,
        isPortalPageEdit: false
      },
      showData: {},
      isGetData: false
    };
  },
  computed: {
    isShowEdit () { // 是否显示修改按钮
      let isShow;
      switch (this.PageSet) {
        case "base":
          isShow = !this.componentStatus.isPortalBaseEdit;
          break;
        case "ac":
          isShow = !this.componentStatus.isPortalAcEdit;
          break;
        case "page":
          isShow = !this.componentStatus.isPortalPageEdit;
          break;
      }
      return isShow;
    }
  },
  created () {
    this.id = this.$route.query.id;
    this.getData();
  },
  methods: {
    /**
    * 点击修改时执行，调用子组件方法，进入编辑状态
    * @param {*void}
    * @author yezi 2019-10-11
    */
    changeStatus () {
      this.$refs[this.PageSet].edit();
    },
    /**
     * 子组件触发的函数，获取子组件状态，是修改还是显示状态
     * @param {*Object 子组件返回状态，包含是否编辑状态isEdit和组件名称name}
     * @author yezi 2019-10-11
     */
    getChildStatus (status) {
      if (status.name === "base") {
        this.componentStatus.isPortalBaseEdit = status.isEdit;
      } else if (status.name === "ac") {
        this.componentStatus.isPortalAcEdit = status.isEdit;
      } else {
        this.componentStatus.isPortalPageEdit = status.isEdit;
      }
    },
    /**
     * 获取portal详情页所有信息数据
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getData () {
      this.axios({
        method: "post",
        data: { id: this.id },
        url: "/cipher/wifiportal/wifiShow"
      })
        .then(res => {
          // 成功发送,并取消编辑状态
          if (res.data.return_code === 0) {
            this.showData = Object.assign({}, res.data);
            this.isGetData = true;
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /**
     * 子组件保存成功后传递数据，重新获取数据刷新页面
     * @param {*Object 子组件传递的数据} data
     * @author yezi 2019-08-02
     */
    reGetData (data) { // 重新获取数据刷新页面
      this.isGetData = false;
      this.getData();
    }
  }
};

</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
.portal-detail-change__btn {
  position: absolute;
  right: 8px;
  top: 31px;
  transform: translateY(-50%);
}
/deep/ .my-tabs {
  .tabs-bar-nav {
    .head;
    margin-bottom: 1px;
  }
  .tabs-content {
    height: calc(~"100% - @{headerHeight}");
    background-color: #fff;
    .border-radius;
    > .pane {
      height: 100%;
      overflow: auto;
    }
  }
}
</style>
