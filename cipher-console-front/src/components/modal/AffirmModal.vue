<template>
  <div>
    <!-- 删除失败提示 -->
    <Modal v-model="showModal" @on-visible-change="visibleChange" :footer-hide="true" width="360">
      <div class="header">
        <Icon type="ios-close-circle"/>
        <span>{{title}}</span>
      </div>
      <div class="center">{{content}}</div>
      <div class="footer">
        <Button type="primary" @click="ok">确定</Button>
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
    }
  },
  methods: {
    ok () {
      this.showModal = false;
      this.onOk();
    },
    visibleChange () {
      this.ok();
    }
  }
};
</script>

<style lang="less" scoped>
// 模态框
.header {
  font-size: 16px;
  color: #17233d;
  font-weight: 700;
  padding: 10px 0;
  i {
    font-size: 28px;
    color: #ed4014;
  }
  span {
    padding-left: 5px;
  }
}
.center {
  font-size: 14px;
  color: #515a6e;
  height: 40px;
  padding-left: 30px;
}
.footer {
  text-align: right;
}
</style>
