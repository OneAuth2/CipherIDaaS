<template>
  <div style="height:100%">
    <div class="title">
      <label>应用库:</label>
      <Input class="search"
             search
             placeholder="应用名称"
             @on-enter="searchApp"
             v-model="searchAppInput"
             @on-click="searchApp" />
    </div>
    <ul class="appList">
      <li @click="gotoAddApp('dsg')"
          v-if="curentPage===1"
          class="add">
        <div class="img"><img src="../../../assets/add-App.png"></div>
        <label class="control-label">新应用</label>
        <Button shape="circle"
                class="btn_class">添加</Button>
      </li>
      <li @click="gotoAddApp('cas')"
          v-if="curentPage===1"
          class="add">
        <div class="img"><img src="../../../assets/add-App.png"></div>
        <label class="control-label">CAS应用</label>
        <Button shape="circle"
                class="btn_class">添加</Button>
      </li>
      <li v-for="(data,index) in  appListData"
          @click="getAssembledAppInfo(data)"
          :key="index">
        <div class="img"><img :src="data.applicationUrl"></div>
        <label class="control-label">{{data.applicationName}}</label>
        <Button shape="circle"
                class="btn_class">添加</Button>
      </li>
    </ul>
    <div class="page">
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
export default {
  name: "Add",
  data () {
    return {
      id: "",
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
            this.$router.push("/app/appDetails");
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
      this.$router.push({ path: "/app/addDsgApp", query: { type } });
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
ul,
li {
  list-style: none;
  margin: 0;
  padding: 0;
}
.content {
  padding-bottom: 0;
}
img {
  max-width: 100%;
}
.title {
  padding: 10px 50px;
  text-align: left;
  label {
    font-size: 18px;
    font-weight: 700;
    margin-right: 20px;
  }
  .search {
    width: 200px;
  }
}
.appList {
  height: calc(100% - 104px);
  padding: 10px 80px 0 80px;
  overflow: auto;
  li {
    width: 16.66%;
    height: 220px;
    padding: 0 10px;
    margin-bottom: 5px;
    box-sizing: border-box;
    float: left;
    &:hover {
      cursor: pointer;
    }
    .img {
      height: 130px;
      width: 130px;
      display: block;
      margin: auto;
      padding: 5px;
      border-radius: 50%;
      box-shadow: 6px 8px 10px rgba(0, 0, 0, 0.35);
      img {
        max-height: 100%;
        max-width: 100%;
      }
    }
    &.add img {
      border: none;
      max-height: 80px;
      max-width: 80px;
      margin-top: 22px;
    }
    label,
    button {
      text-align: center;
      display: inline-block;
      padding: 2px 20px;
      font-size: 14px;
      font-weight: 500;
      border-radius: 5px;
    }
    label {
      display: block;
      font-weight: 600;
      padding: 10px 0;
    }
  }
}
.page {
  padding: 9px 0;
  border: solid #ddd;
  border-width: 1px 0;
  margin: 0;
}
</style>
