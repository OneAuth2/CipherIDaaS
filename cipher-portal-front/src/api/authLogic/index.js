export default {
  // 非业务型接口
  sendPhoneCode: "/cipher/user/sendPhoneCode", // 发送手机验证码接口
  phoneCodeChecked: "/cipher/user/phoneCodeChecked", // 绑定手机验证码校验接口
  sendMailCode: "/cipher/user/sendMailCode", // 邮箱验证码发送接口
  mailCodeChecked: "/cipher/user/mailCodeChecked", // 绑定钉钉邮箱验证码校验接口
  pwdChecked: "/cipher/user/pwdChecked", // 用户名密码校验接口
  passwordSet: "/cipher/user/passwordSet", // 设置新密码

  // 账号注册接口
  registFlow: "/cipher/regist/registFlow", // 账号注册流程接口
  phoneNumberExist: "/cipher/user/phoneNumberExist", // 账号注册验证手机号是否存在
  registPhoneChecked: "/cipher/regist/registPhoneChecked", // 账号注册手机验证接口
  registMailChecked: "/cipher/regist/registMailChecked", // 账号注册邮箱验证接口,
  regist: "/cipher/user/regist", // 账号注册流程校验并保存接口
  getContactInfo: "/cipher/user/getContactInfo" // 根据用户的唯一ID获取手机号和邮箱
};
