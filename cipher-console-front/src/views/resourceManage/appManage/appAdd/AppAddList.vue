<template>
  <div class="tabled-wrap">
    <div class="tabled-header">
      <span class="add-app-label">应用库：</span>
      <myInput class="tabled-header-search"
               radius="half"
               :search="true"
               placeholder="应用名称"
               v-model="searchAppInput"
               :maxlength="14"
               @handleClick="searchApp" />
    </div>
    <ul class="tabled-table app-list">
      <template v-if="curentPage===1">
        <li v-for="item in addTypeList"
            :key="item.value"
            @click="gotoAddApp(item.value)"
            class="add link">
          <div class="app-list-crcle-img"><img :src="item.imgUrl"></div>
          <label class="control-label"><span class="add-font">+</span> {{item.label}}</label>
        </li>
      </template>
      <li v-for="(data,index) in  appListData"
          @click="getAssembledAppInfo(data)"
          :key="index"
          class="link">
        <div class="app-list-crcle-img"><img :src="data.applicationUrl"></div>
        <label class="control-label"
               :title="data.applicationName"><span class="add-font">+</span> {{data.applicationName}}</label>
      </li>
    </ul>
    <div class="tabled-page">
      <Page :total="total"
            :page-size="pageSize"
            :current="curentPage"
            class="pageDemo"
            @on-change="changePage" />
    </div>

    <Modal title="demo"
           v-model="showAssembledAppAddModal"
           style="width: 500px">
      <p slot="header"
         class="modalHeadr">
        <Icon type="ios-add"
              class="icon"></Icon>
        <span>添加{{formValidate.applicationName}}</span>
      </p>
      <div>
        <Form ref="formValidate"
              :model="formValidate">
          <Row>
            <Col span="14">
            <Col span="24">
            <FormItem label="应用名称："
                      prop="applicationDes"
                      class="appInputDemo">
              <Input v-model="formValidate.applicationName"></Input>
            </FormItem>
            </Col>
            <Col span="24">
            <Col span="6">
            <label class="lableClass">应用描述:</label>
            </Col>
            <Col span="18"
                 style="font-size:13px">
            <label>{{markLable}}</label>
            </Col>
            </Col>
            </Col>
            <Col span="9"
                 offset="1">
            <FormItem prop="applicationUrl">
              <img :src="formValidate.applicationUrl">
            </FormItem>
            </Col>
          </Row>
          <FormItem label="应用类型："
                    prop="applicationType"
                    v-show="false">
            <Input v-model="formValidate.applicationType"></Input>
          </FormItem>
        </Form>
      </div>
      <p slot="footer"
         class="footer">
        <Button type="primary"
                @click="confirmAddApp()">确定</Button>
        <Button @click="cancel()"
                style="margin-left: 8px">取消</Button>
      </p>
    </Modal>
  </div>
</template>
<script>
import { mapMutations } from "vuex";
import home from "../../../../assets/add-App.png";
import cas from "../../../../assets/cas.png";
import saml from "../../../../assets/saml.png";
import oauth2 from "../../../../assets/Oauth2.png";

export default {
  name: "Add",
  data () {
    return {
      id: "",
      addTypeList: [
        { value: "新", label: "新应用", imgUrl: home },
        { value: "CAS", label: "CAS应用", imgUrl: cas },
        { value: "SAML", label: "SAML应用", imgUrl: saml },
        { value: "OAUTH", label: "OAUTH应用", imgUrl: oauth2 }
      ],
      msg: "",
      // 编辑表单验证
      formValidate: {
        applicationDes: "", // 应用名称
        applicationUrl: "", // 应用图标
        applicationType: "", // 应用类型
        appSysId: 0 // 应用id
      },
      markLable: "", // 应用介绍
      applicationName: "", // 应用名称
      showAssembledAppAddModal: false, // 添加封装应用弹出框
      searchAppInput: "", // 应用搜索的input的绑定值
      appListData: [], // 应用列表
      curentPage: 1, // 当前页
      queryName: "", // 传入后端的搜索数据
      prevPage: 0, // 上一页
      nextPage: 0, // 下一页
      showPageNums: [], // 分页的list
      total: 0, // 分页的总数
      pageSize: 8 // 分页的大小
    };
  },
  mounted () {
    this.getAssembledAppList();
  },
  methods: {
    // 取消弹出框
    cancel () {
      this.showAssembledAppAddModal = false;
    },
    /**
     * 封装应用弹出框点击确认时触发该方法，该方法主要是添加应用，并跳转到应用详情页
     * @param {*void}
     * @author yezi 2019-07-31
     */
    confirmAddApp () {
      if (!this.validateApplicationName(this.formValidate.applicationName)) {
        return;
      }
      let params = {
        applicationName: this.formValidate.applicationName,
        applicationIconUrl: this.formValidate.applicationUrl,
        appSysId: this.formValidate.appSysId,
        applicationType: this.formValidate.applicationType
      };
      this.axios({
        method: "post",
        url: "/cipher/app/add?json",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.id = res.data.return_result;
            this.showAssembledAppAddModal = false;
            this.$myMessage.success("添加应用成功!");
            this.changeAppId(this.id);
            this.$router.push({ name: "appDetails" });
          } else {
            this.$myModal.error({
              title: "添加应用失败",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          error = "网络未知错误！";
          this.$myMessage.error(error);
        });
    },
    /**
     * 验证应用名称是否为空
     * @param {*String 应用名称} val
     * @author yezi 2019-07-31
     */
    validateApplicationName (val) {
      if (!val.replace(/^ +| +$/, "")) {
        this.$myModal.error({
          title: "添加失败",
          content: "应用名称为空"
        });
        return false;
      }
      return true;
    },
    /**
     * 获取封装应用列表
     * @param {*void}
     * @author yezi 2019-07-31
     */
    getAssembledAppList () {
      this.axios({
        method: "post",
        url: "/cipher/app/applist",
        data: {
          currentPage: this.curentPage,
          queryName: this.queryName
        }
      })
        .then(res => {
          this.appListData = res.data.pageDomain.showUsers;
          this.curentPage = res.data.pageDomain.currentPage;
          this.prevPage = res.data.pageDomain.prevPage;
          this.nextPage = res.data.pageDomain.nextPage;
          this.showPageNums = res.data.pageDomain.showPageNums;
          this.total = this.showPageNums.length * this.pageSize;
        })
        .catch(() => {
          // console.log("error");
        });
    },
    /**
     * 点击封装应用添加按钮时赋值，获取弹出框所需显示内容
     * @param {*Object 后台获取的当前应用信息} data
     * @author yezi 2019-07-31
     */
    getAssembledAppInfo (data) {
      this.showAssembledAppAddModal = true;
      this.formValidate.applicationName = data.applicationName;
      this.formValidate.applicationUrl = data.applicationUrl;
      this.formValidate.applicationType = data.applicationType;
      this.formValidate.appSysId = data.id;
      this.markLable = data.mark;
    },
    /**
     * 跳转到添加dsg应用、cas应用的页面
     * @param {*String 应用类型：dsg应用或cas应用} type
     * @author yezi 2019-07-31
     */
    gotoAddApp (type) {
      this.$router.push({ name: "appAddType", query: { type } });
    },
    /**
     * 点击分页时触发该方法
     * @param {*Number 点击页面页码} index
     * @author yezi 2019-07-31
     */
    changePage (index) {
      this.curentPage = index;
      this.getAssembledAppList();
    },
    /**
    * 搜索应用触发的方法
    * @param {*void}
    * @author yezi 2019-07-31
    */
    searchApp () {
      this.queryName = this.searchAppInput;
      this.curentPage = 1;
      this.getAssembledAppList();
    },
    ...mapMutations(["changeAppId"])
  }
};
</script>
<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
ul,
li {
  list-style: none;
  margin: 0;
  padding: 0;
}
.add-app-label {
  color: @colorFontMain;
  margin-right: @customMargin;
}

.app-list {
  background: #fff;
  .border-radius;
  padding: 40px 80px 0 80px;
  overflow: auto;
  li {
    width: 20%;
    height: 200px;
    padding: 0 10px;
    margin-bottom: 5px;
    box-sizing: border-box;
    float: left;
    &:hover {
      transition: all 0.3s;
      transform: scale(1.1);
      .app-list-crcle-img {
        .box-shadow(13px, 7px, 17px, 0px, rgba(199, 201, 203, 1));
      }
    }
    .app-list-crcle-img {
      .wh(110px, 110px);
      .flex(center, center);
      margin: auto;
      .border-radius(50%);
      border: 1px solid #eee;
      img {
        max-height: 100%;
        max-width: 100%;
      }
    }
    label,
    button {
      text-align: center;
      display: inline-block;
      padding: 2px 20px;
      font-size: 14px;
      font-weight: fontBold;
    }
    label {
      display: block;
      padding: @customPadding*2 0;
      .ellipsis;
    }
  }
}
/deep/ .ivu-form-item-content img {
  max-width: 100%;
  max-height: 100%;
}
.add-font {
  font-size: 16px;
}
</style>
