let selectOSTree = {
  selectFlag: "all", // single单个人,group组和all全部,默认选择all
  // group: {
  //     groupId: '',
  //     href: ''
  // },//当选择为group时生效
  // accountNumber: '',//当选择为single时生效
  selectTree: {} // 当选择为single和group时生效
}; // 选择的用户目录组织结构左侧树

let homeOrg = {}; // 首页相关信息
export { selectOSTree, homeOrg };
