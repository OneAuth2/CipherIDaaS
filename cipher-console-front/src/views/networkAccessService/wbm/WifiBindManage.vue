<template>
  <div class="wbm tabled-wrap">
    <div class="tabled-header">
      <div class="tabled-header-search">
        <myInput placeholder="请输入姓名 / 账号"
                 v-model="account"
                 radius="half"
                 :search="true"
                 @handleClick="getDeviceList" />
      </div>
      <div class="tabled-header-btn">
        <span class="deviceNumber">设备数量</span>
        <myInput class="wbm-header__input--space"
                 type="number"
                 v-model="device"
                 :min="0"
                 :max="99"
                 :precision="0"
                 style="width: 120px"
                 placeholder="请输入设备数量" />
        <myButton btnType="info"
                  @click="updateStrategy">保存</myButton>
      </div>
    </div>
    <div class="tabled-table">
      <Table border
             :columns="columns7"
             :data="bindDeviceLists"></Table>
    </div>
    <Page class="tabled-page"
          :current="list.current"
          :total="list.total"
          show-sizer
          show-elevator
          show-total
          @on-change="changePage"
          @on-page-size-change="changePageSize" />
  </div>
</template>

<script>
import api from "@/api/networkAccessService/index.js";
export default {
  data () {
    return {
      account: "", // 账户
      device: 0, // 设备数量：保存成功后数据
      deviceBak: 0, // 设备数量：保存成功前备份
      list: {
        current: 1, // 当前页码
        pageSize: 10, // 每页条数
        total: 0 // 总条数
      },
      columns7: [
        {
          title: "姓名",
          key: "name",
          align: "center",
          render: (h, params) => {
            return h("div", [
              h("Icon", {
                props: {
                  type: "person"
                }
              }),
              h("strong", params.row.name)
            ]);
          }
        },
        {
          title: "账号",
          key: "userName",
          align: "center"
        },
        {
          title: "MAC地址",
          key: "mac",
          align: "center"
        },
        {
          title: "设备信息",
          key: "deviceInfo",
          align: "center"
        },
        {
          title: "绑定时间",
          key: "bindTime",
          align: "center"
        },
        {
          title: "Action",
          key: "action",
          width: 150,
          align: "center",
          render: (h, params) => {
            return h("a", {
              on: {
                click: () => {
                  this.unbind(params.index);
                }
              }
            }, "解绑设备编号");
          }
        }
      ],
      bindDeviceLists: [] // 绑定设备列表
    };
  },
  created () {
    this.initLoad();
  },
  methods: {
    /**
     * 获取绑定设备列表
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getDeviceList () {
      let params = { queryName: this.account, page: this.list.current, rows: this.list.pageSize };
      this.axios({
        method: "post",
        data: params,
        url: api.deviceList
      })
        .then(response => {
          if (response.data.return_code !== api.errorStatus) {
            this.bindDeviceLists = response.data.rows;
            this.list.total = response.data.total;
          } else {
            this.$myModal.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 获取Mac绑定策略
     * @param {*void}
     * @author yezi 2019-08-02
     */
    getMacBindStrategy () {
      this.axios({
        method: "post",
        data: {},
        url: api.strategyMac
      })
        .then(response => {
          if (response.data.return_code === api.returnOk) {
            this.device = response.data.strategy.count;
            this.deviceBak = this.device;
          } else {
            this.$myModal.error("数据错误");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 保存设备数量
     * @param {*void}
     * @author yezi 2019-08-02
     */
    updateStrategy () {
      let params = {
        count: this.device
      };
      this.axios({
        method: "post",
        data: params,
        url: api.macUpdate
      })
        .then(response => {
          if (response.data.return_code === api.returnOk) {
            this.$myMessage.success("保存成功!");
            this.deviceBak = this.device;
          } else {
            this.device = this.deviceBak; // 恢复保存之前数据
            this.$myModal.error("保存失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 点击解绑触发，弹出解绑设备弹出框
     * @param {*Number 需要解绑的数据对应的table列表行序} index
     * @author yezi 2019-08-02
     */
    unbind (index) {
      this.$myModal.confirm({
        title: "确定解绑吗？"
      }).then(async () => {
        // 点击确定的回调
        this.handleSubmitUnbind(index);
      }).catch(() => { });
    },
    /**
     * 提交解绑操作
     * @param {*Number 需要解绑的数据对应的table列表行序} index
     * @author yezi 2019-08-02
     */
    handleSubmitUnbind (index) {
      let params = {
        id: this.bindDeviceLists[index].id
      };
      this.axios({
        method: "post",
        data: params,
        url: api.deviceDelete
      })
        .then(response => {
          if (response.data.return_code === api.returnOk) {
            let totalPage = Math.ceil((this.list.total - 1) / this.PageSize); // 当前总页数
            this.list.current = totalPage < this.list.current ? totalPage : this.list.current;
            this.getDeviceList();
            this.$myMessage.success("解绑成功!");
          } else {
            this.$myModal.error("解绑失败");
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 改变页码时触发
     * @param {*Number 需要请求的页码} current
     * @author yezi 2019-08-02
     */
    changePage (current) {
      this.list.current = current;
      this.getDeviceList();
    },
    /**
     * 切换每页条数时触发
     * @param {*Number 每页需要显示的条数} pageSize
     * @author yezi 2019-08-02
     */
    changePageSize (pageSize) {
      this.list.pageSize = pageSize;
      this.getDeviceList();
    },
    /**
     * 初始化数据，获取列表和mac绑定策略
     * @param {*void}
     * @author yezi 2019-08-02
     */
    initLoad () {
      this.getDeviceList();
      this.getMacBindStrategy();
    }
  }
};
</script>

<style lang="less" scoped>
@import "~@/assets/styles/tableStyle.less";
.wbm-header__input--space {
  margin: 0 8px;
}
</style>
