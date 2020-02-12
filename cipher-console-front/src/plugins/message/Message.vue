<template>
  <transition name="fade">
    <div class="message"
         :class="messageCls"
         v-show="visible">
      <Icon :type="iconType" />
      <span>{{content}}</span>
    </div>
  </transition>
</template>

<script>
export default {
  name: "toast",
  props: {
    icon: {
      type: String,
      default: "info"
    },
    content: {
      type: String,
      default: ""
    },
    duration: { // 显示时长，毫秒
      type: Number,
      default: 3000
    }
  },
  data () {
    return {
      closed: false, // 用来判断消息框是否关闭
      timer: null, // 计时器
      visible: false // 是否显示
    };
  },
  computed: {
    iconType () {
      let icoType;
      switch (this.icon) {
        case "success":
          icoType = "md-checkmark-circle";
          break;
        case "fail":
          icoType = "md-remove-circle";
          break;
      }
      return icoType;
    },
    messageCls () {
      return `message-${this.icon}`;
    }
  },
  mounted () {
    this.startTimer();
  },
  watch: {
    closed (newVal) {
      if (newVal) {
        this.visible = false;
        this.destroyElement();
      }
    }
  },
  methods: {
    destroyElement () {
      this.$destroy();
      this.$el.parentNode.removeChild(this.$el);
    },
    startTimer () {
      this.timer = setTimeout(() => {
        if (!this.closed) {
          this.closed = true;
          clearTimeout(this.timer);
        }
      }, this.duration);
    }
  }
};
</script>

<style lang="less" scoped>
.message {
  position: fixed;
  top: 50%;
  left: 50%;
  z-index: 1001;
  min-width: 300px;
  padding: 0 15px;
  height: 80px !important;
  transform: translate(-50%, -50%);
  line-height: 80px;
  background-color: #fff;
  box-shadow: 0px 2px 4px 0px rgba(206, 206, 206, 1);
  color: #fff;
  border-radius: 4px;
  text-align: center;
  font-size: 18px;
  /deep/ i {
    font-size: 28px;
    margin-right: 3px;
  }
}

.fade-enter,
.fade-leave-to {
  top: 0;
  opacity: 0;
}
.fade-enter-active,
.fade-leave-active {
  transition: all 0.5s;
}
.message-success {
  i,
  span {
    color: #81cfe6;
  }
}
.message-fail {
  i,
  span {
    color: #fa6400;
  }
}
</style>
