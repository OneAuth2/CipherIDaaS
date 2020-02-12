<template>
  <div class="WED">
    <div class="top clearfix">
      <Button icon="md-add"
              type="primary"
              class='add'
              @click="add()">添加自定义属性</Button>
    </div>
    <div class="table">
      <Table :columns="title"
             :data="list"></Table>
    </div>
    <!-- 添加自定义属性弹窗 -->
    <Modal v-model="showAddModal"
           width="600"
           class="add-modal"
           @on-ok="onOk">
      <p slot="header"
         class="header">
        <span class="left">添加自定义属性</span>
      </p>
      <div class="center">
        <Form :model="formItem"
              :rules="ruleValidate"
              ::label-width="300">
          <FormItem label="自定义属性名："
                    prop="attributeName">
            <Input v-model="formItem.attributeName"
                   placeholder="请输入自定义属性名"></Input>
          </FormItem>
          <FormItem label="描述："
                    prop="attributeDescription">
            <Input v-model="formItem.attributeDescription"
                   type="textarea"
                   :autosize="{minRows: 4,maxRows: 5}"
                   placeholder="请输入描述"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>
    <!-- 点击下线成功调用提示框 定时关闭 -->
    <Notice :text="message.text"
            :show="message.show"
            :duration="message.duration"
            :onClose="message.onClose"
            v-if="message.show"
            @close="close"></Notice>
    <!-- 下线失败时调用 提示框 -->
    <FailModal v-if="failModal.flag"
               :flag="failModal.flag"
               :title="failModal.title"
               :content="failModal.content"
               :onOk="failModal.onOk"></FailModal>
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
import Notice from "@/components/modal/Notice.vue";
import FailModal from "@/components/modal/AffirmModal.vue";
import verify from "@/util/verify.js";
export default {
  data () {
    const validateName = (rule, value, callback) => {
      var reg = verify.userDefinedName;
      if (!reg.test(value)) {
        callback(new Error("请输入中文、英文字母或数字，不超过20个字符"));
      }
      callback();
    };
    const validateDescription = (rule, value, callback) => {
      if (value.length > 100) {
        callback(new Error("请输入不超过100个字符的内容"));
      }
      callback();
    };
    return {
      showAddModal: false, // 添加弹窗
      addModalType: "", // 弹窗类型 添加 或 编辑
      list: [], // 表格列表数据
      formItem: { // 弹窗内容
        attributeattributeName: "",
        attributeDescription: "",
        uuid: ""
      },
      ruleValidate: { // 验证
        attributeName: [
          { required: true, message: "自定义属性名称不能为空", trigger: "blur" },
          { validator: validateName, trigger: "blur" }
        ],
        attributeDescription: [
          { validator: validateDescription, trigger: "change" }
        ]
      },
      // 表格数据
      title: [
        {
          title: "自定义属性",
          key: "attributeName"
        },
        {
          title: "描述",
          key: "attributeDescription"
        },
        {
          title: "操作",
          key: "option",
          width: "200",
          render: (h, params) => {
            return h("div", [
              h("span", {
                style: {
                  marginRight: "22px",
                  color: "rgb(24, 144, 255)",
                  cursor: "pointer"
                },
                on: {
                  click: () => {
                    this.edit(params.index);
                  }
                }
              }, "编辑"),
              h("span", {
                style: {
                  marginRight: "5px",
                  color: "rgb(24, 144, 255)",
                  cursor: "pointer"
                },
                on: {
                  click: () => {
                    this.delete(params.index);
                  }
                }
              }, "删除")

            ]);
          }
        }
      ],
      message: { // 成功提示框
        text: "下线成功",
        show: false,
        duration: 1,
        onClose: this.close
      },
      failModal: { // 失败时提示
        flag: false,
        title: "失败",
        content: "请稍后重试",
        onOk: this.onConfirm
      }

    };
  },
  components: {
    Notice,
    FailModal
  },
  created () {
    this.getList();
  },
  methods: {
    /**
     * 获取自定义属性列表
     * @param {*void}
     * @author 2019-08-01
     */
    getList () {
      this.axios({
        method: "post",
        url: "/cipher/attr/list",
        data: {}
      })
        .then(res => {
          this.list = res.data.attribute;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 显示失败弹出框
     * @param {*String 失败信息} title
     * @author 2019-08-01
     */
    showFialModal (title) {
      this.failModal.flag = true;
      this.failModal.title = title;
    },
    /**
     * 显示失败弹出框
     * @param {*String 成功信息} text
     * @author 2019-08-01
     */
    showSuccessNotice (text) {
      this.message.show = true;
      this.message.text = text;
    },
    /**
     * 点击去确定按钮，关闭失败弹窗
     * @param {*void}
     * @author 2019-08-01
     */
    onConfirm () {
      this.failModal.flag = false;
    },
    /**
     * 关闭定时成功信息窗
     * @param {*void}
     * @author 2019-08-01
     */
    close () {
      this.message.show = false;
    },
    /**
     * 点击添加按钮触发
     * @param {*void}
     * @author 2019-08-01
     */
    add () {
      this.addModalType = "add";
      this.showAddModal = true;
    },
    /**
     * 点击编辑按钮触发
     * @param {*Number 点击数据对应的列表行序} index
     * @author 2019-08-01
     */
    edit (index) {
      this.addModalType = "edit";
      this.showAddModal = true;
      this.formItem.attributeName = this.list[index].attributeName;
      this.formItem.attributeDescription = this.list[index].attributeDescription;
      this.formItem.uuid = this.list[index].uuid;
    },
    /**
     * 点击编辑或添加弹窗框的确认按钮触发
     * @param {*void}
     * @author 2019-08-01
     */
    onOk () {
      let params = {};
      if (this.addModalType === "add") {
        params = {
          attributeName: this.formItem.attributeName,
          attributeDescription: this.formItem.attributeDescription
        };
        this.addData(params);
      } else {
        params = {
          attributeName: this.formItem.attributeName,
          attributeDescription: this.formItem.attributeDescription,
          uuid: this.formItem.uuid
        };
        this.editData(params);
      }
    },
    /**
     * 调用接口，添加自定义数据
     * @param {*Object 添加自定义属性接口参数} params
     *        {attributeName:"自定义属性名称",attributeDescription:"自定义属性描述"}
     * @author 2019-08-01
     */
    addData (params) {
      this.axios({
        method: "post",
        url: "/cipher/attr/adding",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showSuccessNotice("添加成功!");
            this.getList();
          } else {
            this.showFialModal("添加失败");
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 调用接口，保存编辑的自定义数据
     * @param {*Object 编辑自定义属性接口参数} params
     *        {uuid:"当前属性id",attributeName:"自定义属性名称",attributeDescription:"自定义属性描述"}
     * @author 2019-08-01
     */
    editData (params) {
      this.axios({
        method: "post",
        url: "/cipher/attr/edited",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showSuccessNotice("编辑成功!");
            this.getList();
          } else {
            this.showFialModal("编辑失败");
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 调用后台接口删除某条数据 根据uuid
     * @param {*String 当前删除属性的id} uuid
     * @author yezi 2019-08-01
     */
    deleteData (uuid) {
      this.axios({
        method: "post",
        url: "/cipher/attr/del",
        data: { uuid: uuid }
      })
        .then(res => {
          if (res.data.return_code === 0) {
            this.showSuccessNotice("删除成功!");
            this.getList();
          } else {
            this.showFialModal("删除失败");
          }
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
     * 点击删除显示删除弹出框
     * @param {*Number 当前点击数据对应的table列表行序} index
     * @author yezi 2019-08-01
     */
    delete (index) {
      this.$Modal.confirm({
        title: "确定删除吗？",
        width: modal.simpleModal.width,
        onOk: () => {
          // 点击确定的回调
          this.deleteData(this.list[index].uuid);
        }
      });
    }
  }

};
</script>

<style lang="less" scoped>
.WED {
  height: 100%;
  padding: 20px 50px;
}
.clearfix:after {
  content: "";
  display: block;
  clear: both;
}
.add {
  float: right;
}
.table {
  height: calc(~"100% - 80px");
  overflow-y: auto;
  margin: 15px auto;
}
.page {
  overflow: hidden;
  span {
    float: left;
  }
  .ivu-page {
    float: right;
  }
}
.add-modal .center {
  /deep/ .ivu-input-wrapper {
    width: 350px;
  }
  /deep/ .ivu-form-item label {
    width: 120px !important;
    text-align: right;
  }
  /deep/ .ivu-form-item-error-tip {
    left: 40px;
  }
}
</style>
