<template>
  <span class="btn"
        ref="btn"
        :style="{width:width}"
        :class='[computedType,computedRadius,computedDisabled]'
        @click="clickEvent">
    <span class="btn-content">
      <slot></slot>
    </span>
  </span>
</template>

<script>
export default {
  props: {
    btnType: { // 按钮类型：primary info secondary default
      type: String,
      default: "default"
    },
    btnRadius: { // 按钮圆角：none half default
      type: String,
      default: "default"
    },
    disabled: {
      type: Boolean,
      default: false
    },
    width: {
      type: String,
      default: ""
    }
  },
  computed: {
    computedType () {
      return `btn-${this.btnType}`;
    },
    computedRadius () {
      return `btn-radius-${this.btnRadius}`;
    },
    computedDisabled () {
      return this.disabled ? "btn-disabled" : "";
    }
  },
  methods: {
    clickEvent () {
      if (this.disabled) return; // 如果是禁用，禁止访问
      this.$emit("click");
    },
    showBtn () {
      return this.$refs.btn;
    }
  }

};
</script>

<style lang="less" scoped>
.btn {
  display: inline-block;
  padding: 1px 15px;
  height: 32px;
  text-align: center;
  background: #fff;
  color: #fff;
  border-radius: 4px;
  cursor: pointer;
  user-select: none;
  transition: color 0.2s linear;
  .btn-content {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
  }
}
.btn-default {
  border: 1px solid #81cfe6;
  color: #81cfe6;
  &:hover {
    border: 2px solid #0091ff;
    padding: 0 14px;
  }
  &:active {
    border: 1px solid #81cfe6;
    padding: 1px 15px;
  }
}
.btn-blue {
  border: 1px solid #81cfe6;
  color: #81cfe6;
}
.btn-dark {
  background-color: #124678;
}
.btn-primary {
  background-color: rgba(142, 192, 238, 1);
  &:hover {
    background: rgba(142, 192, 238, 0.5);
  }
  &:active {
    background: rgba(18, 70, 120, 1);
  }
}
.btn-info {
  background-color: rgba(129, 207, 230, 1);
  &:hover {
    background: rgba(129, 207, 230, 0.5);
  }
  &:active {
    background: rgba(58, 151, 179, 1);
  }
}
.btn-secondary {
  background-color: rgba(167, 167, 167, 1);
  &:hover {
    background: rgba(167, 167, 167, 0.5);
  }
  &:active {
    background: rgba(109, 114, 120, 1);
  }
}
.btn-disabled {
  background-color: #cecece !important;
  border: none;
  color: #fff;
  &:hover {
    height: 32px;
    line-height: 32px;
    padding: 1px 15px;
    border: none;
  }
  &:active {
    border: none;
  }
}
.btn-radius-none {
  border-radius: 0;
}
.btn-radius-half {
  border-radius: 20px;
}
</style>
