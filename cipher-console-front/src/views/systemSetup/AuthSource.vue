<template>
  <div class='auth-source'>
    <Table :columns="title"
           class="tabled-table"
           :data="data"></Table>
    <Modal v-model="isEdit"
           class="create-modal"
           width="528"
           @on-ok="ok">
      <p slot="header">编辑认证源</p>
      <div class="center">
        <Form :model="formItem"
              ref="formItem"
              :label-width="130"
              label-position="right">
          <FormItem label="证源名称："
                    prop="name">
            <Input v-model="formItem.name"></Input>
          </FormItem>
          <FormItem label="认证源地址：">
            <Input v-model="formItem.src"></Input>
          </FormItem>
        </Form>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isEdit: false,
      title: [
        {
          title: "认证源名称",
          key: "name"
        },
        {
          title: "认证源地址",
          key: "src"
        },
        {
          title: "操作",
          key: "option",
          render: (h, params) => {
            return h("div", [
              h("a", {
                on: {
                  click: () => {
                    this.showEditModal(params.index);
                  }
                }
              }, "编辑")

            ]);
          }
        }
      ],
      data: [{ name: "", src: "", id: "" }], // 认证源信息
      formItem: {
        id: "",
        name: "", // 编辑的认证源名称
        src: "" //  编辑的认证源src
      }

    };
  },
  created () {
    this.getAuthSourceData();
  },
  methods: {
    /**
     * 获取认证源数据
     * @param {*void}
     * @author yezi 2019-08-01
     */
    getAuthSourceData () {
      // 调用接口
      this.axios({
        method: "post",
        url: "/cipher/mantis/erp",
        data: ""
      })
        .then(res => {
          this.data[0].name = res.data.name;
          this.data[0].src = res.data.src;
          this.data[0].id = res.data.id;
        })
        .catch(error => {
          this.$Notice.warning({
            title: "网络未知错误！",
            desc: error
          });
        });
    },
    /**
    * 点击编辑的时候，显示对话框，并获取 回显认证源信息
    * @param {*Number 当前点击数据在table列表的行序} index
    * @author yezi 2019-08-01
    */
    showEditModal (index) {
      this.isEdit = true;
      this.formItem.id = this.data[0].id;
      this.formItem.name = this.data[0].name;
      this.formItem.src = this.data[0].src;
    },
    /**
     * 在编辑对话框 点击确认后执行 修改
     * @param {*void}
     * @author yezi 2019-08-01
     */
    ok () {
      let erpConfigInfo = {
        id: this.formItem.id,
        name: this.formItem.name,
        src: this.formItem.src
      };
      // 调用修改接口
      this.axios({
        method: "post",
        url: "/cipher/mantis/update",
        data: erpConfigInfo
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
            this.getAuthSourceData();
          } else {
            this.$myMessage.error(res.data.return_msg);
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

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
@import "~@/assets/styles/modal.less";
.edit-item {
  margin-bottom: 10px;
}
</style>
