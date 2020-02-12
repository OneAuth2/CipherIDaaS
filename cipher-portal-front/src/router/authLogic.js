export default {
  authLogic: {
    path: "/authLogic", // 登录认证逻辑
    name: "AuthLogic",
    component: () =>
      import(
        /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/AuthIndex.vue"
      ),
    children: [
      {
        path: "login", // 扫码登录
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/LoginIndex.vue"
          ),
        children: [
          {
            path: "admin", // 管理员页面
            name: "AuthLogicAdmin",
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/admin/AdminIndex.vue"
              )
          },
          {
            path: "staff", // 员工页面
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/staff/StaffIndex.vue"
              )
          },
          {
            path: "scan", // 扫码登录
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/scan/MultiScan.vue"
              )
          }
        ]
      },
      // 新用户注册
      {
        path: "newUserRegist",
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/RegistIndex.vue"
          ),
        children: [
          {
            path: "accountRegist", // 账号注册
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/AccountRegist.vue"
              )
          },
          {
            path: "phoneVerify", // 手机号验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/PhoneVerify.vue"
              )
          },
          {
            path: "mailVerify", // 邮箱验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/MailVerify.vue"
              )
          },
          {
            path: "waitReview", // 等待审核
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/WaitReview.vue"
              )
          },
          // 注册条款
          {
            path: "clause",
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/Clause.vue"
              )
          },
          {
            path: "legalAgreement",
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUserRegist/LegalAgreement.vue"
              )
          }
        ]
      },
      // 老用户
      {
        path: "oldUser",
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/OldUserIndex.vue"
          ),
        children: [
          {
            path: "bindPhone", // 绑定手机号
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/BindPhone.vue"
              )
          },
          {
            path: "bindMail", // 绑定邮箱
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/BindMail.vue"
              )
          },
          {
            path: "twoAuthDd", // 钉钉扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthDd.vue"
              )
          },
          {
            path: "twoAuthDingPush", // 钉钉push
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthDingPush.vue"
              )
          },
          {
            path: "twoAuthDb", // 大白扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthDb.vue"
              )
          },
          {
            path: "twoAuthWx", // 赛赋扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthWx.vue"
              )
          },
          {
            path: "twoAuthDt", // 动态码验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthDt.vue"
              )
          },
          {
            path: "twoAuthNum", // 手机验证码验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthNum.vue"
              )
          },
          {
            path: "twoAuthMail", // 邮箱验证码验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuthMail.vue"
              )
          },
          {
            path: "twoAuth", // 并行二次认证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/oldUser/TwoAuth.vue"
              )
          }
        ]
      },
      // 忘记密码
      {
        path: "forgetPW",
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/ForgetIndex.vue"
          ),
        children: [
          {
            path: "twoAuthNum", // 手机验证码验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/TwoAuthNum.vue"
              )
          },
          {
            path: "twoAuthMail", // 邮箱验证码验证
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/TwoAuthMail.vue"
              )
          },
          {
            path: "twoAuthDd", // 钉钉扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/TwoAuthDd.vue"
              )
          },
          {
            path: "twoAuthDb", // 大白扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/TwoAuthDb.vue"
              )
          },
          {
            path: "twoAuthWx", // 企业微信扫码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/TwoAuthWx.vue"
              )
          },
          {
            path: "setNewPassWord", // 设置新密码
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/forget/ResetPassWord.vue"
              )
          }
        ]
      },
      // 新用户扫码绑定
      {
        path: "newUser",
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/NewUserIndex.vue"
          ),
        children: [
          {
            path: "authGetInfo", // 获取授权信息
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/AuthGetInfo.vue"
              )
          },
          {
            path: "matchFail", // 匹配失败
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/MatchFail.vue"
              )
          },
          {
            path: "manualInput", // 手动输入绑定
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/manualBind/ManualInput.vue"
              )
          },
          {
            path: "phoneVerify", // 必须流程-手机校验
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/mustProcess/PhoneVerify.vue"
              )
          },
          {
            path: "mailVerify", // 必须流程-邮箱校验
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/mustProcess/MailVerify.vue"
              )
          },
          {
            path: "waitReview", // 等待审核
            component: () =>
              import(
                /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/newUser/mustProcess/WaitReview.vue"
              )
          }
        ]
      },
      {
        path: "setNewPassWord", // 设置新密码
        component: () =>
          import(
            /* webpackChunkName: "AuthLogic" */ "@/views/authLogic/SetNewPassWord.vue"
          )
      }
    ]
  }
};
