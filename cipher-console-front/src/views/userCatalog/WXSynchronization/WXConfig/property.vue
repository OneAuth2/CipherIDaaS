<template>
  <div class="property formed-wrap">
    <div class="formed-form"
         v-if="!isEdit">
      <div class="formed-form-part">
        <div class="formed-form-part-title">属性映射</div>
        <div class="formed-form-item">
          <Table :columns="tableTitle"
                 :data="localDingMapList"
                 width="400"></Table>
        </div>
      </div>
      <div class="formed-form-part">
        <div class="formed-form-part-title">自动同步</div>
        <div class="formed-form-item">
          <span>自动同步：</span>
          <span>{{showAutoSyn.isAutoSync?"关闭":"开启"}}</span>
        </div>
        <div class="formed-form-item">
          <span>同步开始时间：</span>
          <span>{{showAutoSyn.autoSyncDate}}</span>
        </div>
        <div class="formed-form-item">
          <span>每隔：</span>
          <span>{{getIntervalTime}} <span>同步一次</span></span>
        </div>
      </div>
    </div>
    <Form v-if="isEdit"
          :label-width="120"
          ref="formValidate"
          :model="formItem"
          :rules="ruleValidate"
          class="formed-form">
      <div class="formed-form-part">
        <div class="formed-form-part-title">属性映射</div>
        <div class="formed-form-item">
          <Table :columns="tableEditTitle"
                 :data="localDingMapList"
                 width="400">
            <template slot-scope="{ row,index }"
                      slot="wxKey">
              <Select v-model="selectKey[index]"
                      size="large"
                      style="width:120px">
                <Option v-for="item in selectKeyList"
                        :value="item"
                        :key="item">{{ item }}</Option>
              </Select>
            </template>
          </Table>
        </div>
      </div>
      <!-- 自动同步 -->
      <div class="formed-form-part">
        <div class="formed-form-part-title">自动同步</div>
        <FormItem label="自动同步："
                  prop="isAutoSync">
          <RadioGroup v-model="formItem.isAutoSync">
            <Radio v-for="item in isAutoSyncList"
                   :label="item.value"
                   :key="item.value">{{item.label}}</Radio>
          </RadioGroup>
        </FormItem>
        <FormItem label="同步开始时间："
                  prop="autoSyncDate">
          <TimePicker type="time"
                      placeholder="请选择开始时间"
                      v-model="formItem.autoSyncDate"
                      style="width: 280px"></TimePicker>
        </FormItem>
        <FormItem label="每隔："
                  prop="interval">
          <Select v-model="formItem.interval"
                  style="width:225px">
            <Option v-for="item in intervalList"
                    :value="item.value"
                    :key="item.value">{{ item.label }}</Option>
          </Select>
          <span style="margin-left:8px">同步一次</span>
        </FormItem>
      </div>
    </Form>
    <div class="formed-footer"
         v-if="isEdit">
      <myButton btnType="info"
                @click="saveProperty">保存</myButton>
      <myButton @click="cancelStatus">取消</myButton>
    </div>
  </div>
</template>

<script>
export default {
  data () {
    return {
      isEdit: false, // 是否编辑状态
      tableTitle: [{ title: "本地属性", key: "localLabel", align: "center" }, { title: "企业微信属性", key: "wxKey", align: "center" }],
      tableEditTitle: [{ title: "本地属性", key: "localLabel", align: "center" }, { title: "企业微信属性", key: "wxKey", align: "center", slot: "wxKey" }],
      // 自动同步显示数据
      showAutoSyn: { // 自动同步显示数据
        isAutoSync: 1, // 是否开启自动同步
        autoSyncDate: "00:00:00", // 自动同步开始时间
        interval: 0 // 间隔时间
      },
      formItem: { // 自动同步编辑数据
        isAutoSync: 1, // 是否开启自动同步
        autoSyncDate: "00:00:00", // 自动同步开始时间
        interval: 0 // 间隔时间
      },
      ruleValidate: {},
      isAutoSyncList: [ // 是否开启自动同步
        { value: 0, label: "开启" },
        { value: 1, label: "关闭" }
      ],
      intervalList: [ // 自动同步间隔
        { value: 0, label: "15分钟" },
        { value: 1, label: "1小时" },
        { value: 2, label: "6小时" },
        { value: 3, label: "12小时" },
        { value: 4, label: "24小时" }
      ],
      selectKey: [],
      selectKeyList: [
        "",
        "userId",
        "orgEmail",
        "name",
        "email",
        "mobile",
        "position"
      ],
      localKeyList: [
        { label: "账号", value: "accountNumber" },
        { label: "姓名", value: "userName" },
        { label: "邮箱", value: "mail" },
        { label: "手机", value: "phoneNumber" },
        { label: "职位", value: "job" }
      ],
      localDingMapList: []
    };
  },
  computed: {
    getIntervalTime () {
      if (typeof (this.showAutoSyn.interval) !== "undefined") {
        return this.intervalList[this.showAutoSyn.interval].label;
      } else {
        return 0;
      }
    }
  },
  mounted () {
    this.getDingMapList();
  },
  methods: {
    editStatus () {
      this.getDingMapList();
      this.isEdit = true;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "property"
      });
    },
    cancelStatus () {
      this.isEdit = false;
      this.$emit("statusChange", {
        isEdit: this.isEdit,
        name: "property"
      });
    },
    getDingMapList () {
      let params = {};
      this.axios({
        method: "post",
        data: params,
        url: "/cipher/wxConfig/getAttrConfig"
      })
        .then(res => {
          this.localDingMapList = res.data.localWx;
          this.selectKey = [];
          for (let i = 0; i < this.localDingMapList.length; i++) {
            this.localDingMapList[i].localLabel = this.localKeyList[i].label;
            this.selectKey.push(this.localDingMapList[i].wxKey);
          }
          if (res.data.autoSync !== null) { // 自动同步数据不为空时
            this.showAutoSyn = JSON.parse(JSON.stringify(res.data.autoSyncInfo)); // 自动同步显示数据
            this.formItem = JSON.parse(JSON.stringify(res.data.autoSyncInfo)); // 自动同步编辑数据
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    saveProperty () {
      let params = [];
      for (var i = 0; i < this.localKeyList.length; i++) {
        let temp = {};
        temp.localKey = this.localKeyList[i].value;
        temp.wxKey = this.selectKey[i];
        params.push(temp);
      }
      params = JSON.stringify(params);
      this.axios({
        method: "post",
        data: {
          localWxMapList: params,
          isAutoSync: this.formItem.isAutoSync,
          autoSyncDate: this.formItem.autoSyncDate,
          interval: this.formItem.interval
        },
        url: "/cipher/wxConfig/editAttrConfig"
      })
        .then(res => {
          if (res.status === 200) {
            this.$myMessage.success("保存成功!");
            this.getDingMapList();
            this.cancelStatus();
          } else {
            this.$myMessage.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/formStyle.less";
</style>
