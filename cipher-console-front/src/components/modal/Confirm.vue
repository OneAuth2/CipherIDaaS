<template>
  <div>
    <!-- 确认框 -->
    <Modal v-model="showModal"
           :closable="false"
           @on-visible-change="visibleChange"
           :footer-hide="true"
           width="528"
           class='synModal'>
      <div class="header">
        <Icon type="md-alert" />
        <span>{{title}}</span>
      </div>
      <div class="center">{{content}}</div>
      <div class="footer">
        <Button type="primary"
                @click="ok">{{okString}}</Button>
        <Button @click="close">取消</Button>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  data () {
    return {
      showModal: this.flag
    };
  },
  props: {
    flag: {
      type: Boolean,
      default: false
    },
    title: {
      type: String,
      default: "失败"
    },
    content: {
      type: String,
      default: "请稍后重试"
    },
    onOk: {
      type: Function
    },
    onClose: {
      type: Function
    },
    okString: {
      type: String,
      default: "确认"
    }
  },
  methods: {
    ok () {
      this.showModal = false;
      this.onOk();
    },
    close () {
      this.showModal = false;
      this.onClose();
    },
    visibleChange () {
      this.close();
    }
  }
};
</script>

<style lang="less" scoped>
// 模态框
/deep/ .ivu-modal-body {
  padding: 0 32px;
}
.header {
  font-size: 16px;
  color: #17233d;
  padding-top: 24px;
  i {
    font-size: 40px;
    color: #f7b500;
    margin-right: 16px;
    position: relative;
    top: -1px;
  }
  span {
    color: #fa6400;
    font-size: 20px;
    font-weight: 500;
  }
}
.center {
  display: block;
  width: 100%;
  font-size: 14px;
  line-height: 20px;
  color: #08142c;
  padding: 24px 56px 0;
}
.footer {
  width: 100%;
  padding: 20px 0 40px;
  text-align: right;
  button {
    margin-left: 10px;
  }
}
</style>
