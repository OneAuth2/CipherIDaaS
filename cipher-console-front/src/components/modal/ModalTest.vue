<template>
  <div class="example">
    <div>
      <!-- 确认提示框 -->
      <Button @click="confirmNotice">确认提示框</Button>
    </div>
    <div>
      <!-- 错误提示框 -->
      <Button @click="error">错误提示框</Button>
    </div>
    <div>
      <!-- 标准模态框 -->
      <Button @click="modal2 = true">标准模态框</Button>
      <Modal v-model="modal2">
        <p slot="header"
           class="header">
          <span class="left">弹窗标题</span>
          <span class="right">辅助信息，如果必要</span>
        </p>
        <div class="center">
          <Form :model="formItem">
            <FormItem label="应用名称：">
              <Input v-model="formItem.input"
                     placeholder="请输入应用名称"></Input>
            </FormItem>
            <FormItem label="应用描述：">
              <Input v-model="formItem.textarea"
                     type="textarea"
                     :autosize="{minRows: 2,maxRows: 5}"
                     placeholder="请输入应用描述"></Input>
            </FormItem>
          </Form>
        </div>
      </Modal>
    </div>
    <div>
      <Button @click="toggle">定时关闭提示框</Button>
      <!-- :onClose和@close选择一种方式，可完成子组件向父组件通信 -->
      <Notice :text="message.text"
              :show="message.show"
              :duration="message.duration"
              :onClose="message.onClose"
              v-if="message.show"
              @close="close"></Notice>
    </div>
    <div>
      <Button @click="toggleDel">删除模态框</Button>
      <DelModal v-if="delModal.flag"
                :flag="delModal.flag"
                :title="delModal.title"
                :content="delModal.content"
                :onOk="delModal.onOk"></DelModal>
    </div>
    <div>
      <Button @click="toggleConfirm">确认模态框</Button>
      <Confirm v-if="confirm.flag"
               :flag="confirm.flag"
               :title="confirm.title"
               :content="confirm.content"
               :onOk="confirm.onOk"
               :onClose="confirm.onClose"></Confirm>
    </div>
  </div>
</template>

<script>
import modal from "@/util/modal/index.js";
import Notice from "./Notice.vue";
import DelModal from "@/components/modal/AffirmModal.vue";
import Confirm from "@/components/modal/Confirm.vue";

export default {
  data () {
    return {
      modal2: false,
      formItem: {
        input: "",
        textarea: ""
      },
      message: {
        text: "这是一条提示消息!",
        show: false,
        duration: 1,
        onClose: this.close
      },
      delModal: {
        flag: false,
        title: "删除失败",
        content: "请稍后重试",
        onOk: this.onOk
      },
      confirm: {
        flag: false,
        title: "确认。。。",
        content: "确认。。。。",
        onOk: this.onConfirmOK,
        onClose: this.onConfirmClose
      }
    };
  },
  components: {
    Notice,
    DelModal,
    Confirm
  },
  methods: {
    confirmNotice () {
      this.$Modal.confirm({
        title: "这是进行一项操作时必须了解的信息",
        content:
          "<span class=\"simpleModalContent\">用户须知的信息，</span><span> 你还要继续吗？</span>",
        width: modal.simpleModal.width,
        onOk: () => {
          // 点击确定的回调
          console.log("Clicked ok");
        },
        onCancel: () => {
          // 点击取消的回调
          console.log("Clicked cancel");
        }
      });
    },
    error () {
      this.$Modal.error({
        title: "保存失败",
        content: "请稍后重试",
        width: modal.simpleModal.width
      });
    },
    close () {
      this.message.show = false;
    },
    toggle () {
      this.message.show = true;
    },
    toggleDel () {
      this.delModal.flag = true;
    },
    toggleConfirm () {
      this.confirm.flag = true;
    },
    onOk () {
      this.delModal.flag = false;
    },
    onConfirmOK () {
      this.confirm.flag = false;
    },
    onConfirmClose () {
      this.confirm.flag = false;
    }
  }
};
</script>

<style scoped>
.example > div {
  padding-top: 30px;
}
</style>
