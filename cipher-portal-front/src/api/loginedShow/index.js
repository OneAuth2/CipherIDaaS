export default {
  getBaseInform: "/cipher/portal/index", // 基本信息
  getAppList: "/cipher/portal/getAppList", // app列表数据
  doAuthOidcAuthorize: "/cipher/oidc/doAuthOidcAuthorize", // portal到Xdsg请求方法
  doCsAuthorize: "/cipher/oidc/doCsAuthorize", // cs
  saveCsPath: "/cipher/user/saveCsPath", // cs保存路径
  doNcAuthorize: "/cipher/oidc/doNcAuthorize", // 用友
  checkSubAccount: "/cipher/oidc/checkUser", // 检验从账号密码是否正确
  saveSubAccount: "/cipher/user/saveSubName", // 保存从账号接口
  toAdmin: "/cipher/oidc/doOidcConsoleAuthorize", // 管理后台接口
  doOidcAuthorize: "/cipher/oidc/doOidcAuthorize", // 可信用网关跳转接口
  getUserInfo: "/cipher/user/getUserInfo", // 用户信息
  updateUserInfo: "/cipher/user/updateuser", // 修改用户信息
  checkPwd: "/cipher/resetpwd/checkpwd", // 检验密码是否符合要求
  changePwd: "/cipher/resetpwd/reset", // 修改密码
  loginOut: "/cipher/user/logout" // 注销账号
};
