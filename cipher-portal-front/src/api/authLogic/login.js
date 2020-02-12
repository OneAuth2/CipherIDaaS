export default {
  // 登录接口
  pwdLoginChecked: "/cipher/user/pwdLoginChecked", // 账号密码登录接口
  phoneCodeLogin: "/cipher/user/phoneCodeLogin", // 手机验证码登录接口
  pwdTotpLoginChecked: "/cipher/user/pwdTotpLoginChecked", //  totp账号密码登录接口
  pwdOtpLoginChecked: "/cipher/user/otpLoginChecked", // otp账号动态密码登录
  saiFuPolling: "/cipher/user/saiFuPolling", // 获取赛赋扫码状态接口
  daBaiPolling: "/cipher/user/daBaiPolling", // 获取大白扫码状态接口
  weixinPolling: "/cipher/user/weixinPolling", // 获取企业微信扫码状态接口
  // dingDingPolling: "/cipher/user/dingDingPolling", // 获取钉钉扫码状态接口
  dingTalkLogin: "/cipher/user/dingTalkLogin", // 获取钉钉扫码状态接口
  secondLoginFlow: "/cipher/user/secondLoginFlow", // 获取二次认证方式
  collectPhoneNumber: "/cipher/user/collectPhoneNumber", // 录入手机号
  collectMailNumber: "/cipher/user/collectMailNumber", // 录入邮箱

  // 登录页接口
  getConfigService: "/cipher/index/service", // 获取portal登录页配置及对应的服务信息
  dingQrcode: "/cipher/login/dingQrcode", // 获取钉钉扫码
  saiFuQrcode: "/cipher/login/saiFuQrcode", // 获取赛赋扫码
  daBaiQrcode: "/cipher/login/daBaiQrcode", // 获取大白扫码
  getWxId: "/cipher/user/getWeiXinAppId", // 获取企业微信扫码
  getDingTalkId: "/cipher/user/getDingTalkId", // 获取钉钉请求id

  // 二次认证接口
  saifuSecondPolling: "/cipher/user/saifuSecondPolling", // 获取赛赋扫码结果接口
  daBaiSecondPolling: "/cipher/user/daBaiSecondPolling", // 获取大白扫码状态接口
  weixinSecondAuth: "/cipher/user/weixinSecondAuth", // 获取企业微信扫码二次认证接口
  dingTalkSecondAuth: "/cipher/user/dingTalkSecondAuth", // 获取钉钉扫码状态接口
  dingPushSecondCheck: "/cipher/user/dingPushSecondCheck", // 进行钉钉push验证接口
  totpSecondCheck: "/cipher/user/totpSecondCheck", // 进行赛赋动态码验证接口
  otpSecondCheck: "/cipher/user/otpDynamicSecondCheck", // 进行otp动态码验证接口
  phoneCodeSecondChecked: "/cipher/user/phoneCodeSecondChecked", // 手机验证码二次认证校验
  mailCodeSecondChecked: "/cipher/user/mailCodeSecondChecked", // 邮箱验证码二次认证校验

  // 二次认证获取二维码接口
  otpQrcode: "/cipher/login/otpQrcode"
};
