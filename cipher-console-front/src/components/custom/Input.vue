<template>
  <div class="input"
       :style="{width:width}">
    <div class="input-item"
         :class="[computedRadius,computedError,computedReadonly,computedIsTeaxtarea,focusStyle]">
      <template v-if="type!=='textarea'">
        <div class="input-item-wrap">
          <input :type="type"
                :name="name"
                :value="computedValue"
                :placeholder="placeholder"
                :readonly="readonly"
                @focus="isFocus=true"
                @blur="isFocus=false"
                @keydown.enter="$emit('handleClick')"
                @input="$emit('input',$event.target.value)">
        </div>
        <Icon type="ios-search"
              v-if="search"
              @click="$emit('handleClick')" />
        <button v-if="btnText"
                :disabled="disabled"
                @click="$emit('handleClick')">{{ btnText }}</button>
      </template>
      <textarea v-else
                :placeholder="placeholder"
                :disabled="disabled"
                :value="computedValue"
                :rows="rows"
                @focus="isFocus=true"
                @blur="isFocus=false"
                @input="$emit('input',$event.target.value)">
      </textarea>

    </div>
    <div v-if="error"
         class="invalid-feedback">{{error}}</div>
  </div>
</template>

<script>
export default {
  props: {
    type: {
      type: String,
      default: "text"
    },
    name: String, // 输入框框名字
    error: String, // 错误信息提示
    value: [String, Number], // 输入框的值
    rows: { // 文本域行数
      type: Number,
      default: 2
    },
    search: { // 是否显示搜索icon
      type: Boolean,
      default: false
    },
    btnText: String, // 是否显示按钮
    readonly: { // 禁用输入框
      type: Boolean,
      default: false
    },
    disabled: { // 禁用点击文字
      type: Boolean,
      default: false
    },
    placeholder: String, // 输入框提示文字
    width: {
      type: String,
      default: ""
    },
    radius: {
      type: String,
      default: "default"
    },
    min: {
      type: Number,
      default: -Infinity
    },
    max: {
      type: Number,
      default: Infinity
    },
    step: {
      type: Number,
      default: 1
    },
    precision: {
      type: Number
    }
  },
  data () {
    return {
      isFocus: false
    };
  },
  computed: {
    computedRadius () {
      return `input-radius-${this.radius}`;
    },
    computedError () {
      return this.error ? "is-invalid" : "";
    },
    computedReadonly () {
      return this.readonly ? "onlyread" : "";
    },
    computedIsTeaxtarea () {
      return this.type !== "textarea" ? "not-textarea" : "";
    },
    focusStyle () {
      return this.isFocus ? "focus" : "";
    },
    computedValue () {
      let currentValue = this.value;
      if (this.type === "number") {
        if (this.value > this.max) {
          currentValue = this.max;
        } else if (this.value < this.min) {
          currentValue = this.min;
        }
        currentValue = this.precision > -1 ? parseFloat(currentValue).toFixed(this.precision) : currentValue;
      }
      return currentValue;
    }
  }
};
</script>
<style lang="less" scoped>
.input {
  display: inline-block;
  width: 100%;
}
.input-item {
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  background: #fff;
  border: 1px solid rgba(167, 167, 167, 1);
  color: #08142c;
  box-sizing:border-box;
  &:hover,
  &:focus {
    border-color: #81cfe6;
  }
}
.focus {
  border-color: #81cfe6;
}
.not-textarea {
  padding: 0 15px;
}
.input-item-wrap{
  flex: 1;
  flex-grow: 1;
  -moz-box-flex: 1;
  height: 32px;
  line-height: 32px;
}
.input-item input {
  outline: none;
  border: 0;
  box-sizing: border-box;
  width: 100%;
  height: calc(~"100% -2px");
  &::placeholder {
    color: #a7a7a7;
  }
}

.input-item textarea {
  width: 100%;
  outline: none;
  border: none;
  padding: 10px 15px;
  &::placeholder {
    color: #a7a7a7;
  }
}
.input-radius-default {
  border-radius: 5px;
}
.input-radius-none {
  border-radius: 0;
}
.input-radius-half {
  border-radius: 25px;
}
.input-item button {
  border: none;
  outline: none;
  background: #fff;
  color: #81cfe6;
}
.input-item i {
  color: #81cfe6;
  font-size: 24px;
}
.input-item button[disabled] {
  color: #aaa;
}
.is-invalid .input-item {
  border-color: #e02020;
  color: #e02020;
  margin-bottom: 23px;
}
.invalid-feedback {
  color: #e02020;
  padding: 0 15px;
}
.onlyread {
  background: rgba(231, 231, 231, 1);
  border: none;
  input {
    background: rgba(231, 231, 231, 1);
  }
}
// 去除type为number时右边的上下三角
input::-webkit-outer-spin-button,
input::-webkit-inner-spin-button {
  -webkit-appearance: none;
}
input[type="number"] {
  -moz-appearance: textfield;
}
</style>
