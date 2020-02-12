var Loading = {};

Loading.install = function (Vue, options) {
  // 添加实例方法
  Vue.prototype.$handleSpinCustom = function (methodOptions) {
    this.$Spin.show({
      render: h => {
        return h("div", [
          h("Icon", {
            class: "spin-icon-load",
            props: {
              type: "ios-loading",
              size: 50
            }
          }),
          h("div", "加载中")
        ]);
      }
    });
    setTimeout(() => {
      this.$Spin.hide();
    }, 1000);
  };
};
export {
  Loading
};
