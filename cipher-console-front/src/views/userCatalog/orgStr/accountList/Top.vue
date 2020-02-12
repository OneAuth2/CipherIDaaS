<template>
  <div class="org-right-header clearfix">
    <div class="org-right-header-left">
      <template v-if="!showAllFlag">
        <Breadcrumb class="nav">
          <BreadcrumbItem v-for="(item,index) in navList"
                          :key="index"
                          @click.native="skipMenu(item,index)">
            <span v-if="index!=navList.length-1"
                  style="cursor:pointer">{{item.path}}</span>
            <template v-else>{{item.path}}</template>
          </BreadcrumbItem>
        </Breadcrumb>
      </template>
      <template v-else>
        <span class="all">全部用户</span>
      </template>
    </div>
    <div class="org-right-header-right">
      <router-link to="/userCatalogOS/add">
        <myButton btnType="info">添加用户</myButton>
      </router-link>
      <template v-if="!showAllFlag">
        <myButton btnType="info"
                  @click.native="departmentAuth">部门权限</myButton>
        <Dropdown class="more">
          <myDropBtn>更多操作</myDropBtn>
          <DropdownMenu slot="list">
            <DropdownItem @click.native="createModal">创建部门</DropdownItem>
            <DropdownItem @click.native="renameModal">编辑部门</DropdownItem>
            <DropdownItem @click.native="delModal">删除部门</DropdownItem>
            <DropdownItem>
              <Upload ref="upload"
                      :show-upload-list="false"
                      :on-success="handleSuccessImport"
                      :on-error="uploadhandleError"
                      :format="['xls','xlsx','docx']"
                      :on-format-error="handleFormatError"
                      :on-progress="handleProgress"
                      action="/cipher/newImportbak/import"
                      class="uploadClass">批量导入账号</Upload>
            </DropdownItem>
            <!-- 下载更新 -->
            <DropdownItem @click.native="downImportExampleList"
                          class="blue">下载导入样表</DropdownItem>
            <DropdownItem>
              <Upload ref="upload"
                      :show-upload-list="false"
                      :on-success="handleSuccessUpdate"
                      :on-error="uploadhandleError"
                      :format="['xls','xlsx','docx']"
                      :on-format-error="handleFormatError"
                      :on-progress="handleProgress"
                      action="/cipher/newImportbak/updateUserImport"
                      class="uploadClass">批量更新账号</Upload>
            </DropdownItem>
            <DropdownItem @click.native="downUpdateExampleList"
                          class="blue">下载更新样表</DropdownItem>
          </DropdownMenu>
        </Dropdown>
      </template>
      <template v-else>
        <myButton btnType="info"
                  @click.native="createModal">创建部门</myButton>
      </template>
    </div>

    <!-- 部门权限模态框 -->
    <Modal v-model="authFlag"
           class="tree-transfer"
           width="668">
      <p slot="header">
        <span>添加授权应用</span>
        <span class="departmentName">{{departmentName}}</span>
      </p>
      <div>
        <TreeTransfer :leftTreeData="appList"
                      :leftDataType="dataType"
                      @rightData="getAppListData"
                      :placeholder="placeholder">
          <span slot="leftText">选择要授权的应用</span>
          <span slot="rightText">已授权的应用</span>
        </TreeTransfer>
      </div>
      <div slot="footer">
        <myButton btnType="info"
                  @click="handleSubmitSave()">保存</myButton>
        <myButton @click="handleReset()"
                  style="margin-left:10px;vertical-align:top">重置</myButton>
      </div>
    </Modal>

    <!-- 创建部门模态框 -->
    <Modal v-model="createFlag"
           width="528"
           :closable="false"
           class="create-modal">
      <p slot="header"
         class="org-modal-header">
        <span>创建部门</span>
      </p>
      <div class="center coverage">
        <Form :model="formItemCreate"
              ref="formItemCreate"
              :rules="ruleValidateCreate"
              label-position="right">
          <FormItem label="部门名称："
                    prop="name">
            <Input v-model="formItemCreate.name"
                   placeholder="请输入部门名称"></Input>
          </FormItem>
          <FormItem label="部门描述：">
            <Input v-model="formItemCreate.description"
                   type="textarea"
                   :autosize="{minRows: 2,maxRows: 5}"
                   placeholder="请输入至少5个字符"></Input>
          </FormItem>
          <FormItem style="margin-bottom: 10px;"
                    label="上级部门："
                    prop="parentDepart">
            <Select v-model="formItemCreate.parentDepart">
              <Option v-for="(item,index) in parentDepartList"
                      :value="item.groupName"
                      :key="index"
                      @click.native="formItemCreate.parentGroupId = item.groupId">{{ item.groupName }}</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <myButton btnType="info"
                  @click="handleSubmitCreate('formItemCreate')">确定</myButton>
        <myButton @click="handleCancel('formItemCreate')"
                  style="margin-left:10px;vertical-align:top">取消</myButton>
      </div>
    </Modal>

    <!-- 重命名部门模态框 -->
    <Modal v-model="renameFlag"
           width="528"
           :closable="false"
           class="create-modal">
      <p slot="header"
         class="org-modal-header">
        <span>编辑部门</span>
        <span class="org-modal-header--sub">{{departmentName}}</span>
      </p>
      <div class="center coverage">
        <Form :model="formItemRename"
              ref="formItemRename"
              :rules="ruleValidateRename"
              label-position="right">
          <FormItem label="部门名称："
                    prop="name">
            <Input v-model.trim="formItemRename.name"
                   placeholder="请输入部门名称"></Input>
          </FormItem>
          <FormItem label="部门描述：">
            <Input v-model.trim="formItemRename.description"
                   type="textarea"
                   :autosize="{minRows: 2,maxRows: 5}"
                   placeholder="请输入至少5个字符"></Input>
          </FormItem>
          <FormItem style="margin-bottom: 10px;"
                    label="上级部门："
                    prop="parentDepart">
            <Select v-model="formItemRename.parentDepart">
              <Option v-for="(item,index) in parentDepartList"
                      :value="item.groupId"
                      :key="index"
                      @click.native="formItemRename.parentGroupId = item.groupId">{{ item.groupName }}</Option>
            </Select>
          </FormItem>
        </Form>
      </div>
      <div slot="footer">
        <myButton btnType="info"
                  @click="handleSubmitRename('formItemRename')">确定</myButton>
        <myButton @click="handleCancel('formItemRename')"
                  style="margin-left:10px;">取消</myButton>
      </div>
    </Modal>

    <!-- 文件上传进度条 -->
    <div v-for="(item,index) in uploadList"
         :key="index">
      <FixedCircle v-if="item.showProgress"
                   :percent="item.percentage"
                   hide-info></FixedCircle>
    </div>
  </div>
</template>

<script>
import api from "@/api/userCatalog/index.js";
import TreeTransfer from "@/components/modal/TreeTransfer.vue";
import { mapState } from "vuex";

export default {
  components: {
    TreeTransfer
  },
  data () {
    const validateName = (rule, value, callback) => {
      if (value.length > 20) {
        callback(new Error("请输入不超过20个字符名称"));
      }
      if (value === "组名已存在,请更换组名") {
        callback(new Error("组名已存在,请更换组名"));
      }
      callback();
    };
    return {
      dataType: 0, // 穿梭框组件类型
      showAllFlag: true,
      updateResultUrl: "", // 上传成功后反馈的参数，错误数据密钥
      uploadList: [], // 文件上传列表
      navList: [],
      message: {
        text: "成功!",
        show: false,
        duration: 1,
        onClose: this.close
      },
      // 部门权限
      authFlag: false,
      appList: [], // 应用列表
      appListBak: [], // 应用列表
      selectApp: "", // 选中的appId
      placeholder: "搜索应用",
      // 创建模态框
      formItemCreate: {
        name: "",
        description: "",
        parentDepart: "",
        parentGroupId: 0
      },
      ruleValidateCreate: {
        name: [
          {
            required: true,
            message: "部门名称不能为空",
            trigger: "blur"
          },
          { validator: validateName, trigger: "blur" }
        ],
        parentDepart: [
          {
            required: true,
            message: "请选择上级部门",
            trigger: "blur"
          }
        ]
      },
      createFlag: false,
      parentDepartList: [], // 上级部门列表
      // 重命名模态框
      formItemRename: {
        name: "",
        description: "",
        groupId: "",
        parentDepart: "",
        parentGroupId: 0
      },
      ruleValidateRename: {
        name: [
          {
            required: true,
            message: "部门名称不能为空",
            trigger: "blur"
          },
          { validator: validateName, trigger: "blur" }
        ],
        parentDepart: [
          {
            required: true,
            type: "number",
            message: "请选择上级部门",
            trigger: "blur"
          }
        ]
      },
      renameFlag: false,
      // 操作模态框
      actionFlag: false,
      actionItemTitle: "操作失败"
    };
  },
  props: {
    showAllTemp: Boolean,
    selObj: Object,
    navArray: Array
  },
  computed: {
    departmentName () {
      let len = this.navArray.length;
      if (len) {
        return "(" + this.navArray[len - 1].path + ")";
      } else {
        return "";
      }
    },
    ...mapState(["group"])
  },
  watch: {
    // 监听顶部菜单显示功能：1.全部用户的，2.根据左侧组织树显示
    showAllTemp: function () {
      return (this.showAllFlag = this.showAllTemp);
    },
    selObj: function () {
      // 点击左侧树时有重名功能，需要设置groupId
      if (!this.showAllTemp) {
        this.formItemRename.groupId = this.selObj.groupId;
      }
    },
    navArray () {
      this.navList = this.$props.navArray;
    }
  },
  methods: {
    skipMenu (item, index) {
      if (index !== this.navList.length - 1) { // 不是面包屑的最后一项
        let deleteLength = this.navList.length - index - 1;
        this.navList.splice(index + 1, deleteLength); // 去除点击的面包屑项后面部分
        this.$emit("skipMenu", item); // 触发跳转
      }
    },
    // 部门权限--添加授权应用
    departmentAuth () {
      this.dataType = 3;
      this.authFlag = true;
      let params = { groupId: this.selObj.groupId };
      // 获取应用列表
      this.axios({
        method: "post",
        data: params,
        url: api.appList
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.appList = response.data;
            this.appListBak = this.$common.clone(this.appList);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取要授权应用列表
    getAppListData (data) {
      let selectAppList = this.$common.clone(data);
      selectAppList = selectAppList.map(function (elem, index, arr) {
        return elem.id;
      });
      this.selectApp = selectAppList.join(",");
      // console.log(this.selectApp);
    },
    // 提交保存
    handleSubmitSave () {
      this.authFlag = false;
      let params = { groupId: this.selObj.groupId, appList: this.selectApp };
      // 获取应用列表
      this.axios({
        method: "post",
        data: params,
        url: api.addApp
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.$Message.success("操作成功");
          } else {
            this.handleError();
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取上级部门列表
    getParentDepartList () {
      let params = { id: this.selObj.groupId };
      // 获取上级部门列表
      this.axios({
        method: "post",
        data: params,
        url: api.parentDepart
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.parentDepartList = this.$common.clone(response.data);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 创建部门模态框
    createModal () {
      this.createFlag = true;
      this.initSetForm("formItemCreate"); // 清空表单
      this.getParentDepartList();
    },
    // 执行创建
    handleSubmitCreate (name) {
      this.$refs[name].validate(valid => {
        let params = {
          groupName: this.formItemCreate.name,
          description: this.formItemCreate.description,
          parentGroupId: this.formItemCreate.parentGroupId
        };
        if (valid) {
          this.axios({
            method: "post",
            data: params,
            url: api.createDepartment
          })
            .then(response => {
              if (
                response.status === api.statusOk &&
                response.data.return_code === api.returnOk
              ) {
                this.success(name);
              } else {
                this.$refs[name].resetFields();
                this.$Message.error(response.data.return_msg);
                this.actionItemTitle = "创建失败";
                this.actionFlag = true;
              }
            })
            .catch(function (error) {
              this.axios.error.handlingErrors(error);
            });
          this.createFlag = false;
        }
      });
    },
    // 编辑部门回显
    getEditDepartInform () {
      let params = { groupId: this.selObj.groupId };
      this.axios({
        method: "post",
        data: params,
        url: api.groupEditEcho
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.formItemRename.name = res.data.return_result.groupName;
            this.formItemRename.description = res.data.return_result.groupDescription;
            this.formItemRename.parentDepart = res.data.return_result.parentGroupId;
            this.formItemRename.groupId = res.data.return_result.groupId;
            this.getElseDepartList(res.data.return_result.groupId); // 获取部门
          } else {
            this.actionItemTitle = "获取信息失败";
            this.actionFlag = true;
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 获取上级部门列表
    getElseDepartList (groupId) {
      let params = { groupId: this.selObj.groupId };
      // 获取上级部门列表
      this.axios({
        method: "post",
        data: params,
        url: api.elseDepart
      })
        .then(response => {
          if (response.status === api.statusOk) {
            this.parentDepartList = this.$common.clone(response.data);
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 编辑部门模态框
    renameModal () {
      this.initSetForm("formItemRename");
      this.renameFlag = true;
      this.getEditDepartInform();
    },
    // 执行重命名
    handleSubmitRename (name) {
      this.$refs[name].validate(valid => {
        let params = {
          groupId: this.formItemRename.groupId,
          groupName: this.formItemRename.name,
          description: this.formItemRename.description,
          parentGroupId: this.formItemRename.parentGroupId
        };
        if (valid) {
          this.axios({
            method: "post",
            data: params,
            url: api.groupEdit
          })
            .then(res => {
              if (res.data.return_code === 0) {
                this.renameSuccess();
              } else {
                this.$myModal.error({
                  title: "编辑部门失败",
                  content: "请稍后重试"
                });
              }
            })
            .catch(function (error) {
              this.axios.error.handlingErrors(error);
            });
          this.renameFlag = false;
        }
      });
    },
    // 重命名成功
    renameSuccess () {
      let len = this.navArray.length;
      this.navArray[len - 1].path = this.formItemRename.name;
      this.$Message.success("操作成功"); ;
      this.$emit("changeLeftTree");
    },
    success (name) {
      this.$refs[name].resetFields();
      this.$Message.success("操作成功"); ;
      this.$emit("changeLeftTree");
    },
    delSuccess () {
      this.$Message.success("操作成功"); ;
      this.$emit("changeLeftTree");
    },
    // 删除部门模态框
    delModal () {
      this.$myModal.confirm({
        title: "确定删除该部门吗？",
        content: "删除后将无法恢复，你还要继续吗？",
        confirmVal: "继续"
      }).then(async (val) => {
        // 点击确定的回调
        this.handleSubmitDel();
      }).catch(() => { });
    },
    // 执行删除
    handleSubmitDel () {
      let params = {
        groupId: this.selObj.groupId
      };
      this.axios({
        method: "post",
        data: params,
        url: api.groupDelete
      })
        .then(response => {
          if (
            response.status === api.statusOk &&
            response.data.code === api.successOk
          ) {
            this.delSuccess();
          } else {
            this.$myModal.error({
              title: "部门下存在账号，不允许删除"
            });
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    // 初始化表单
    initSetForm (name) {
      this.$refs[name].resetFields();
    },
    // 取消
    handleCancel (name) {
      this.createFlag = false;
      this.renameFlag = false;
      this.$refs[name].resetFields();
    },
    handleError () {
      this.$myModal.error({
        title: "保存失败",
        content: "请稍后重试"
      });
    },
    handleReset () {
      this.appList = this.$common.clone(this.appListBak);
    },
    // 批量导入账号反馈
    uploadFeedback () {
      location.href = this.updateResultUrl;
    },
    /**
     * 文件上传时的钩子
     * @param 详见iview
     * @author yezi 2019-08-09
     */
    handleProgress (event, file, fileList) {
      // uploadList 就是 文件列表，渲染的 uplist 是个数组，所以要把filelist 赋值给他
      this.uploadList = fileList;
    },
    // 上传文件成功之后返回信息
    handleSuccessImport (res, file) {
      if (res.return_code === 1) {
        // 成功后重新请求左侧树和所有列表
        this.$emit("changeLeftTree");
        this.$emit("showAll");
        this.updateResultUrl = "/cipher/newImportbak/downloadReturnExcel?downloadSecret=" + res.secret;
        this.$myModal.confirm({
          title: "上传成功",
          content: res.return_msg,
          confirmVal: "下载反馈"
        }).then(async (val) => {
          // 点击确定的回调
          this.uploadFeedback();
        }).catch(() => { });
      } else if (res.return_code === 0) {
        this.$myModal.error({
          title: "导入账号失败",
          content: res.return_msg
        });
      }
    },
    // 上传文件成功之后返回信息
    handleSuccessUpdate (res, file) {
      if (res.return_code === 1) {
        // 成功后重新请求左侧树和所有列表
        this.$emit("changeLeftTree");
        this.$emit("showAll");
        this.updateResultUrl = "/cipher/newImportbak/downloadErrorExcel?downloadSecret=" + res.secret;
        this.$myModal.confirm({
          title: "上传成功",
          content: res.return_msg,
          confirmVal: "下载反馈"
        }).then(async (val) => {
          // 点击确定的回调
          this.uploadFeedback();
        }).catch(() => { });
      } else if (res.return_code === 0) {
        this.$myModal.error({
          title: "导入账号失败",
          content: res.return_msg
        });
      }
    },
    // 上传文件失败时调用的函数
    uploadhandleError (error, file) {
      this.$myModal.error({
        title: "Excel上传失败",
        content: error
      });
    },
    // 上传的不是表格
    handleFormatError (file) {
      this.$Notice.warning({
        title: "请选择表格文件",
        desc: "文件不正确！ " + file.name + " 该文件不是表格文件"
      });
    },
    // 下载导入样表
    downImportExampleList () {
      window.location.href = "/cipher/newImportbak/userexport";
    },
    // 下载更新样表
    downUpdateExampleList () {
      window.location.href = "/cipher/newImportbak/updateUserInfoExport";
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/common.less";
@import "~@/assets/styles/modal.less";
.org-right-header {
  .head;
  margin-bottom: @customMargin;
}
.org-right-header-left,
.org-right-header-right {
  float: left;
  width: 50%;
}
/* 左侧 */
.org-right-header-left {
  text-align: left;
  font-size: 14px;
  overflow: hidden;
  text-overflow: ellipsis;
  white-space: nowrap;
  min-width: @fontNormal;
}
.nav {
  display: inline-block;
  padding-left: 2px;
}
/* 右侧 */
.org-right-header-right {
  text-align: right;
}

.org-right-header-right .btn {
  margin-left: @customMargin;
}
.all {
  font-size: @fontSize14;
  font-weight: 700;
}
.org-modal-header {
  text-align: center;
  .org-modal-header--sub {
    font-weight: @fontNormal;
  }
}
// 模态框
.delHeader {
  font-size: 16px;
  color: #17233d;
  font-weight: 700;
  padding: 10px 0;
  i {
    font-size: 28px;
    color: #ed4014;
  }
}
.delCenter {
  color: #6d7278;
  height: 40px;
  padding-left: 30px;
}
.delFoter {
  text-align: right;
}
.departmentName {
  font-weight: @fontBold;
  color: #08142c;
}
</style>
