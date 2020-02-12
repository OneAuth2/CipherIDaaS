<template>
  <div class="custom-test">
    <!--
      按钮组件使用，
      btnType表示类型 ：primary info secondary default，默认为default
      btnRadius表示圆角：none half default
      disabled 表示禁用：true false，默认为false
      @click 点击事件
    -->
    按钮：
    <myButton>default</myButton>
    <myButton btnType="primary">primary</myButton>
    <myButton btnType="info">info</myButton>
    <myButton btnType="secondary">secondary</myButton>
    <myButton btnType="dark">dark</myButton>
    <myButton btnType="secondary"
              btnRadius="none">无圆角</myButton>
    <myButton btnType="secondary"
              btnRadius="half">圆角</myButton>
    <myButton @click="test">default 点击事件</myButton>
    <myButton disabled
              @click="test">禁用</myButton>
    <myDropBtn>下拉按钮</myDropBtn>
    <br /><br />
    <!--
      下拉框组件使用，
      dataList表示类选项数组，默认为空数组，数组每个数据为一个Object，默认使用key值未value和label[{value:"",label:""},...]
      valueKey,labelKey如果数组key值不是value和label，需要传递
      showString表示初始显示文本：默认显示选择
      v-model绑定为选中的value值
      @change 选项发生变化触发，函数参数为一个对象Object，包含数组中的位置index和选项值value和选项label
    -->
    下拉框：
    <mySelect :dataList="list1"
              showString="请选择数字"
              v-model="value1"
              @on-change="getData" />
    <mySelect :dataList="list2"
              showString="请选择数字"
              v-model="value1"
              valueKey="type"
              labelKey="desc"
              @on-change="getData" />
    {{value1}}
    <br /><br />
    <!--
      输入框组件使用，
      type表示类选，默认为text
      name表示表单名称
      value表示数值，可以用v-model双项绑定数据传递
      error错误信息提示，报错的时候使用
      rows表示文本域的行数，只有在type为textarea时有效
      width表示宽度
      radius表示圆角：none half default
      readonly表示只读，boolean类型
      disabled表示按钮不可以，boolean类型
      btnText表示右侧按钮的文本内容
      search表示是否有搜索，Boolean类型，默认没有
      min表示最小值
      max表示最大值
      precision表示小数位数
      @handleClick 表示触发事件函数，在点击按钮、icon图标、回车时触发
    -->
    输入框：<br />
    默认-值1：{{value1}}
    <myInput v-model="value1"
             width="500px"
             placeholder="请输入" />
    半圆角-值1：{{value1}}
    <myInput v-model="value1"
             width="200px"
             radius="half" />
    无圆角-值1：{{value1}}
    <myInput v-model="value1"
             radius="none" />
    文本域-值2:{{value2}}
    <myInput v-model="value2"
             width="200px"
             type="textarea"
             radius="none" />
    文字按钮-值3:
    <myInput v-model="value3"
             btnText="获取验证码"
             width="200px"
             @handleClick="test" />
    search按钮-值3:
    <myInput v-model="value3"
             radius="half"
             :search="true"
             width="200px"
             @handleClick="test" />
    只读-值3
    <myInput v-model="value3"
             width="200px"
             :readonly="true" />
    数字-值4:
    <myInput v-model="value4"
             :min="0"
             :max="99"
             :precision="0"
             width="200px"
             type="number" />
    报错：
    <myInput v-model="value4"
             width="200px"
             error="出错" />
  </div>
</template>

<script>
export default {
  data () {
    return {
      list1: [{ value: "0", label: "选项一" }, { value: "1", label: "选项二" }, { value: "2", label: "选项三" }],
      list2: [{ type: "0", desc: "选项一" }, { type: "1", desc: "选项二" }, { type: "2", desc: "选项三" }],
      value1: "test1",
      value2: "test2",
      value3: "test3",
      value4: 1
    };
  },
  methods: {
    test () {
      this.$myMessage.success("操作");
      // this.$myModal.confirm({
      //   title: "提示",
      //   content: "确定要删除吗"
      // }).then(async (val) => {
      //   console.log("确认");
      // }).catch(() => {
      //   console.log("取消");
      // });
    },
    getData (data) { // data为一个对象，包含选项所在位置index和选项value和选项label
      console.log(data);
    }
  }
};
</script>

<style scoped lang="less">
.custom-test {
  padding: 30px;
  text-align: left;
}
.btn,
.dropdown {
  margin-right: 3px;
}
.input {
  display: block;
}
</style>
