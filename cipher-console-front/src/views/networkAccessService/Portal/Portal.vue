<template>
  <div class="portal tabled-wrap">
    <div class="tabled-header portal-header">
      <myButton btnType="info"
                @click="addPortal">添加Portal设置</myButton>
    </div>
    <div class="tabled-table">
      <Table border
             :columns="portalTitle"
             :data="portalList"
             class="portalTitle">
        <template slot-scope="{ row }"
                  slot="description">
          <strong>
            <a @click="editPortal(row.id)"
               size="small">{{ row.description }}</a>
          </strong>
        </template>
        <template slot-scope="{ row }"
                  slot="action">
          <a @click="editPortal(row.id)">编辑</a>
          <a @click="showDeleteModal(row.id)">删除</a>
        </template>
      </Table>
    </div>
    <Page class="tabled-page"
          :total="total"
          :current="current"
          show-elevator
          show-sizer
          show-total
          @on-change="changePage"
          @on-page-size-change="changePageSize"></Page>
  </div>
</template>
<script>
import api from "@/api/networkAccessService/index";
export default {
  data () {
    return {
      pageSize: 10,
      total: 0,
      current: 1,
      portalTitle: [
        {
          type: "index",
          width: 60,
          align: "center"
        },
        {
          title: "Portal描述",
          key: "description",
          slot: "description"
        },
        {
          title: "portal地址",
          key: "portalAddress"
        },
        {
          title: "类型",
          key: "portalType",
          render: (h, params) => {
            const portalName = params.row.portalType === 0 ? "员工portal" : "访客portal";
            return h("div", [
              h("span", portalName)
            ]);
          }
        },
        {
          title: "操作",
          slot: "action",
          minwidth: 250,
          align: "center"
        }
      ],
      portalList: []
    };
  },
  mounted () {
    let params = {
      page: 1,
      rows: this.pageSize
    };
    this.getList(params);
  },
  methods: {
    /**
     * 点击添加portal设置触发，跳转到添加页面
     * @param {*void}
     * @author yezi 2019-08-02
     */
    addPortal () {
      this.$router.push({
        name: "PortalAdd"
      });
    },
    /**
     * 点击表格中的portal描述或编辑触发，跳转到详情页
     * @param {*Number 需要查看编辑的portal配置对应id} id
     * @author yezi 2019-08-02
     */
    editPortal (id) {
      this.$router.push({
        name: "portalDetails",
        query: {
          id: id
        }
      });
    },
    /**
     * 点击删除触发，弹出删除提示框
     * @param {*Number 需要删除的portal配置对应id} id
     * @author yezi 2019-08-02
     */
    showDeleteModal (id) {
      this.$myModal.confirm({
        title: "确定要删除吗"
      }).then(async (val) => {
        this.deletePortal(id);
      }).catch(() => { });
    },
    /**
     * 点击删除确认框确认按钮触发，发送请求，删除数据
     * @param {*Number 需要删除的portal配置对应id} id
     * @author yezi 2019-08-02
     */
    deletePortal (id) {
      this.axios({
        method: "post",
        data: { id },
        url: api.deletePortal
      })
        .then(res => {
          // 成功发送跳转
          if (res.data.return_code === 1) {
            this.$myMessage.success(res.data.return_msg);
            // 删除后判断总页数是否大于当前页
            let totalPage = Math.ceil((this.total - 1) / this.PageSize); // 当前总页数
            this.current = totalPage < this.current ? totalPage : this.current;
            let params = {
              page: this.current,
              rows: this.pageSize
            };
            this.getList(params);
          } else {
            this.$myMessage.error(res.data.return_msg);
          }
        })
        .catch(function (error) {
          console.log(error);
        });
    },
    /**
    * 获取portal列表数据
    * @param {*Object 请求列表参数 } params
    *        {rows:"一页显示的条数",page:"请求的页码"}
    * @author yezi 2019-08-02
    */
    getList (params) {
      this.axios({
        method: "post",
        data: params,
        url: api.wifiportalList
      })
        .then(response => {
          if (response.data.code === 0) {
            var portalListbefore = response.data.msg.rows;
            this.total = response.data.msg.total;
            portalListbefore.forEach(item => {
              if (item.protalType === 0) {
                item.protalType = "员工Portal";
              } else if (item.protalType === 1) {
                item.protalType = "访客Portal";
              }
            });
            this.portalList = portalListbefore;
          }
        })
        .catch(function (error) {
          this.axios.error.handlingErrors(error);
        });
    },
    /**
     * 一页显示条数变化触发
     * @param {*Number 一页显示的条数} pageSize
     * @author yezi 2019-08-02
     */
    changePageSize (pageSize) {
      let params = {
        rows: pageSize,
        page: 1

      };
      this.getList(params);
      this.pageSize = pageSize;
    },
    /**
     * 当前页发生变化时触发
     * @param {*Number 点击的页码数} pageSize
     * @author yezi 2019-08-02
     */
    changePage (index) {
      let params = {
        rows: this.pageSize,
        page: index
      };
      this.current = index;
      this.getList(params);
    }
  }
};
</script>
<style scoped lang="less">
@import "~@/assets/styles/tableStyle.less";
.portal-header {
  text-align: right;
}
</style>
