// 认证方式类别
const applicationAuthList = [
  { value: 1, label: "安全代理" },
  { value: 2, label: "密码代填" },
  { value: 3, label: "API接口" },
  { value: 4, label: "API接口" },
  { value: 5, label: "未定" },
  { value: 6, label: "非SSO应用" },
  { value: 7, label: "CAS应用" },
  { value: 8, label: "C/S应用" },
  { value: 9, label: "C/S应用" }, // 用友
  { value: 10, label: "saml应用" },
  { value: 11, label: "oauth应用" }
];
// 应用状态列表
const applicationStatusList = [
  { value: "可用", label: "启用" },
  { value: "禁用", label: "停用" }
];
// 从账号列表
const subAccountConfigList = [
  { label: "手动配置", value: "assManual" },
  { label: "账号", value: "assPrimaryAccount" },
  { label: "邮箱", value: "assEmail" },
  { label: "邮箱前缀", value: "assEmailPrefix" },
  { label: "手机号", value: "assTelephone" },
  { label: "工号", value: "assWorkers" }
];
// 从账号密码列表
const subAccountPwdConfigList = [
  { label: "手动配置", value: "assPwdManual" },
  { label: "门户登录密码", value: "assPwdPrimaryAccount" }
];
// 应用特有属性列表
const configInfoList = [
  { appSysId: 1, label: "企业邮箱域名:", name: "domain" },
  { appSysId: 1, label: "企业标识（由网易提供）:", name: "product" },
  { appSysId: 1, label: "私钥:", name: "secret" },
  { appSysId: 2, label: "secretKey（由每刻管理员提供）:", name: "secretKey" },
  { appSysId: 2, label: "entCode（企业代码）:", name: "entCode" },
  { appSysId: 5, label: "CorpID（腾讯企业邮箱企业ID）:", name: "cropId" },
  { appSysId: 5, label: "CorpSecret（应用的凭证密钥）:", name: "cropSecret" },
  { appSysId: 5, label: "DeptMngId(腾讯部门秘钥) :", name: "deptMngId" },
  { appSysId: 6, label: "WPS_API_PRE:", name: "apiId" },
  { appSysId: 6, label: "SESSION_API_PRE:", name: "sessionId" },
  { appSysId: 6, label: "WPS_APPID:", name: "appId" },
  { appSysId: 6, label: "WPS_SECRET_KEY:", name: "secretKey" },
  { appSysId: 6, label: "WPS_ACCESS_ID:", name: "accessId" }
];
// 登陆方式列表
const loginTypeList = [{ value: "1", label: "NClient客户端" }];

// 获取需要显示的字段
function getShowAppList (applicationType, appSysId) {
  let appList = [
    {
      name: "applicationName",
      label: "应用名称:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 0
    },
    {
      name: "applicationDescription",
      label: "应用描述:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 1
    },
    {
      name: "applicationAuth",
      label: "认证方式:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 2
    },
    {
      name: "applicationStatus",
      label: "应用状态:",
      show: true,
      formType: "radio",
      radioList: "applicationStatusList",
      isEdit: true,
      index: 3
    },
    {
      name: "applicationIndex",
      label: "应用序号:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 4
    },
    {
      name: "applicationId",
      label: "APP-ID:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 5
    },
    {
      name: "applicationSecrect",
      label: "APP-KEY:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 6
    },
    {
      name: "applicationUrl",
      label: "应用地址:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 7
    }, // 应用地址 6 7
    {
      name: "casServerUrl",
      label: "CAS服务器地址:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 8
    }, // cas服务器地址 7
    {
      name: "subAccountConfig",
      label: "从账号:",
      show: true,
      formType: "radio",
      radioList: "subAccountConfigList",
      isEdit: true,
      index: 9
    }, // 从账号
    {
      name: "subAccountPwdConfig",
      label: "从账号密码:",
      show: true,
      formType: "radio",
      radioList: "subAccountPwdConfigList",
      isEdit: true,
      index: 10
    }, // 从账号密码,7,9没有
    {
      name: "applicationUrl",
      label: "启动路径:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 11
    }, // C/S应用启动路径 8
    {
      name: "server",
      label: "服务器地址:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 12
    }, // C/S应用服务器地址 8,9
    {
      name: "data",
      label: "账套名称:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 13
    }, // C/S应用账套名称 8,9
    {
      name: "pos",
      label: "账套序号:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 14
    }, // C/S应用账套序号 8
    {
      name: "loginType",
      label: "登陆方式:",
      show: false,
      formType: "radio",
      radioList: "loginTypeList",
      isEdit: true,
      index: 15
    }, // C/S应用登陆方式 9
    {
      name: "configInfo",
      label: "无",
      show: false,
      formType: "input",
      isEdit: true,
      index: 16
    }, // appSysId为1，2，5，6
    {
      name: "xdsgUrl",
      label: "DSG网关地址:",
      show: true,
      formType: "input",
      isEdit: true,
      index: 17
    }, // dsg网关地址，C/S应用，applicationType为8，9时不可编辑
    {
      name: "applicationUrl",
      label: "应用登录地址:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 18
    }, // saml的登陆地址
    {
      name: "relayState",
      label: "relayState地址:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 19
    }, // saml的成功之后地址
    {
      name: "assertionConsumerServiceUrl",
      label: "AssertionConsumerServiceUrl地址",
      show: false,
      formType: "input",
      isEdit: true,
      index: 20
    }, // saml的assertionConsumerServiceUrl的信息
    {
      name: "exitTime",
      label: "断言有效期:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 21
    }, // saml断言的实效
    {
      name: "entityId",
      label: "idp的EntityeId:",
      show: false,
      formType: "input",
      isEdit: true,
      index: 22
    }, // samlidp的entityId
    {
      name: "downloadXml",
      label: "下载元数据:",
      show: false,
      formType: "a",
      isEdit: false,
      index: 23
    } // saml断言的实效
  ];
  switch (applicationType) {
    case 6: // cas应用
      appList[7].show = true;
      break;
    case 7: // cas应用
      appList[7].show = true;
      appList[8].show = true;
      appList[10].show = false;
      break;
    case 8: // c/s应用
      appList[11].show = true;
      appList[12].show = true;
      appList[13].show = true;
      appList[14].show = true;
      appList[17].isEdit = false;
      break;
    case 9: // cas应用 用友
      appList[10].show = false;
      appList[12].show = true;
      appList[13].show = true;
      appList[15].show = false;
      appList[17].isEdit = false;
      appList[12].label = "应用地址：";
      break;
    case 10:
      appList[10].show = false;
      appList[15].show = false;
      appList[17].show = false;
      appList[18].show = true;
      appList[19].show = true;
      appList[20].show = true;
      appList[21].show = true;
      appList[22].show = true;
      appList[23].show = true;
      // appList[22].show = true;
      break;
    case 11:
      appList[10].show = false;
      appList[7].show = true;
      appList[7].label = "重定向地址:";
      appList[17].show = false;
      break;
  }
  if (appSysId === 1 || appSysId === 2 || appSysId === 5 || appSysId === 6) {
    appList[16].show = true;
  }
  return appList.filter(item => {
    return item.show === true;
  });
}
export default {
  applicationAuthList,
  applicationStatusList,
  subAccountConfigList,
  subAccountPwdConfigList,
  configInfoList,
  loginTypeList,
  getShowAppList
};
