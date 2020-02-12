// 1.用户名密码，手机随机码，totp码可看做已绑定用户，3种扫码存在没有绑定用户
export default {
  scanWay: "", // 记录扫码类型，用在没有绑定用户
  userInfo: {
    // 用户信息
    userId: "",
    userName: "",
    phoneNumber: "",
    mail: ""
  },
  portalConfig: {}, // 登录页的配置项
  loginInfo: { userId: "" }, // 登录信息
  modes: {}, // 对应的显示的认证方式
  registFlow: {}, // 注册流程
  binDingFlow: {}, // 绑定流程
  contactInfo: {}, // 绑定流程--关联信息
  secondLoginInfo: {}, // 二次认证流程
  forgetPwdFlow: {}, // 忘记密码流程
  registMode: {} // 注册和忘记密码是否显示对象
};
