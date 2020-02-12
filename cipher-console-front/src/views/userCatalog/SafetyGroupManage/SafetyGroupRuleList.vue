<template>
  <div class="wrap">
    <myButton btnType="info"
              class="sg-rule-save"
              @click="doSaveRule">立刻执行</myButton>
    <div class="formed-form">
      <div class="sg-rule-part">
        <span>将符合以下</span>
        <mySelect class="sg-rule_select"
                  :dataList="options1"
                  showString="请选择"
                  v-model="selected1"
                  valueKey="type"
                  labelKey="desc" />
        <span>条件的用户自动的移动到当前安全组。</span>
      </div>
      <div class="sg-rule-part">
        <div class="sg-rulr-part-item-title">
          <div class="text-small-title">选择条件</div>
          <div class="text-small-title">匹配规则</div>
          <div class="text-small-title">
            输入参数
            <Tooltip trigger="hover"
                     placement="top"
                     max-width="200"
                     content="如选择条件为部门，输入参数就为组织结构下的某一部门名称，如产品部。">
              <Icon type="ios-information-circle"
                    class="tip" />
            </Tooltip>
          </div>
        </div>
        <div class="sg-rulr-part-item-text">
          <div v-for="(item, index) in formDynamic.items"
               :key="index">
            <div class="module">
              <mySelect :dataList="options2"
                        showString="请选择"
                        v-model="item.condition"
                        valueKey="type"
                        labelKey="desc" />
            </div>
            <div class="module">
              <mySelect :dataList="options3"
                        showString="请选择"
                        v-model="item.conditionName"
                        valueKey="type"
                        labelKey="desc" />
            </div>
            <div class="module">
              <Input v-model="item.paramName"
                     style="width:86px;" />
            </div>
            <div class="sg-rule-part-btn">
              <myButton @click="handleRemove(index)">删除</myButton>
              <myButton btnType="info"
                        @click="handleAdd">增加</myButton>
            </div>
          </div>
        </div>
      </div>
    </div>
    <Modal width="400"
           v-model="modal1"
           title="确认执行规则"
           @on-ok="ok"
           okText="继续">
      <p style="font-size:14px">
        确认要将以下{{returndata.num}}个账号加入到{{teamName}}吗?
        <br>
        <a @click="show(value)">查看详情</a>
      </p>
    </Modal>
    <Modal v-model="modal2"
           title="移动到当前安全组"
           width="600">
      <div class="modal2">
        <div class="table">
          <Table :columns="columns1"
                 :data="data1"></Table>
        </div>
        <Page :total="total"
              :current="curentPage"
              show-elevator
              show-sizer
              show-total
              @on-change="changePage"
              @on-page-size-change="pageSizeChange"></Page>
      </div>
    </Modal>
  </div>
</template>

<script>
export default {
  data () {
    return {
      index: 1,
      formDynamic: {
        items: [
          {
            condition: 1,
            conditionName: 1,
            paramName: "",
            index: 1
          }
        ]
      },
      value: "",
      size: 10,
      total: 0,
      curentPage: 1,
      columns1: [
        {
          title: "姓名",
          key: "userName"
        },
        {
          title: "账号",
          key: "accountNumber"
        },
        {
          title: "部门",
          key: "groupName"
        }
      ],
      data1: [],
      returndata: [],
      modal1: false,
      modal2: false,
      teamId: "",
      options1: [{ type: 1, desc: "任何" }, { type: 2, desc: "全部" }],
      options2: [],
      options3: [],
      teamName: "",
      selected1: 1
    };
  },
  created () {
    this.teamId = this.$route.query.teamId;
    this.teamName = this.$route.query.teamName;
  },
  mounted () {
    var that = this;
    let params1 = {};
    let params2 = {};
    that.finddata1(params1);
    that.finddata2(params2);
  },
  methods: {
    handleAdd () {
      let i = 0;
      this.formDynamic.items.forEach(item => {
        i = i + 1;
      });
      if (i < 10) {
        this.index++;
        this.formDynamic.items.push({
          condition: 1,
          conditionName: 1,
          paramName: "",
          index: this.index
        });
      }
    },
    handleRemove (index) {
      let i = 0;
      this.formDynamic.items.forEach(item => {
        i = i + 1;
      });
      if (i > 1) {
        this.formDynamic.items.splice(index, 1);
      }
    },

    finddata1 (params1) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/team/ruleContent",
        data: params1
      })
        .then(function (res) {
          that.options2 = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    finddata2 (params2) {
      var that = this;
      this.axios({
        method: "post",
        url: "/cipher/team/condtionContent",
        data: params2
      })
        .then(function (res) {
          that.options3 = res.data;
        })
        .catch(error => {
          console.log(error);
        });
    },
    // 添加执行规则后得到的用户数据
    ok () {
      let params = {
        userIds: this.returndata.uuid,
        teamId: this.teamId
      };
      this.axios({
        method: "post",
        url: "/cipher/team/addRuleUser",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
          } else {
            this.$Modal.error({
              title: "提示",
              content: res.data.return_msg
            });
          }
        })
        .catch(error => {
          console.log(error);
        });
    },
    finddata3 (params) {
      this.axios({
        method: "post",
        url: "/cipher/team/showUser",
        data: params
      })
        .then(res => {
          this.data1 = res.data.rows;
          this.total = res.data.records;
        })
        .catch(error => {
          console.log(error);
        });
    },
    show (value) {
      this.data1 = [];
      if (value !== "" && value !== null) {
        let params = {
          page: 1,
          rows: this.size,
          sidx: "",
          sord: "asc",
          userList: value
        };
        this.finddata3(params);
        this.modal2 = true;
      }
    },
    pageSizeChange (size) {
      let params = {
        rows: size,
        page: 1,
        sidx: "",
        sord: "asc",
        userList: this.value
      };
      this.finddata3(params);
      this.size = size;
    },
    changePage (index) {
      let params = {
        rows: this.size,
        page: index,
        sidx: "",
        sord: "asc",
        userList: this.value
      };
      this.curentPage = index;
      this.finddata3(params);
    },
    doSaveRule () {
      let paramName = [];
      let data = [];
      this.formDynamic.items.forEach(item => {
        paramName.push(item.paramName);
        data.push({
          condition: item.condition,
          conditionName: item.conditionName,
          paramName: item.paramName
        });
      });
      let params = {
        id: this.teamId,
        ruleValue: JSON.stringify(data),
        type: this.selected1,
        paramName: paramName
      };

      this.axios({
        method: "post",
        url: "/cipher/team/addRule",
        data: params
      })
        .then(res => {
          if (res.data.return_code === 1) {
            this.modal1 = true;
            this.returndata = res.data;
            var list = res.data.userList;
            // var json = JSON.stringify(list)
            // var xqo = eval('(' + json + ')')
            var tt = "";
            for (var i in list) {
              tt = tt + list[i].uuid + ",";
            }
            this.value = escape(tt);
          }
        })
        .catch(error => {
          console.log(error);
        });
    }
  }
};
</script>

<style scoped lang='less'>
@import "~@/assets/styles/formStyle.less";
.sg-rule-save {
  position: absolute;
  top: @customMargin;
  right: @customMargin;
}
.sg-rule-part {
  .sc(@fontSize14, @colorFontMain);
  margin-bottom: 40px;
  > span {
    height: 20px;
    line-height: 20px;
  }
}
.sg-rule_select {
  margin: 0 16px;
}
.tip {
  .sc(@fontSize20, @colorPrimary);
}
.sg-rulr-part-item-title {
  > div {
    display: inline-block;
    width: 100px;
    text-align: center;
  }
}
.sg-rulr-part-item-text > div {
  margin-top: @customMargin * 2;
  > .module {
    display: inline-block;
    width: 100px;
    text-align: center;
    vertical-align: top;
  }
}
.sg-rule-part-btn {
  display: inline-block;
  margin-left: @customMargin * 8;
  .btn {
    margin-right: @customMargin;
    vertical-align: top;
  }
}
</style>
