import Vue from "vue";
import Router from "vue-router";
import router from "./path.js";
import store from "@/store/index";
// import url from "@/api/url.js";

Vue.use(Router);
// console.log('process.env.BASE_URL:' + process.env.BASE_URL)

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/login", //
      name: "Login",
      component: () =>
        import(/* webpackChunkName: "Login" */ "@/views/login/LoginIndex.vue")
    },
    // 用户目录
    {
      path: router.userCatalogOS, // 用户目录-组织结构
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/userCatalog/orgStr/Index.vue"
        ),
      children: [
        {
          // path: ':path', // 组织结构-主页
          path: "", // 组织结构-主页
          name: "OrgStr",
          meta: {
            title: "用户目录/组织结构"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/orgStr/accountList/Index.vue"
            )
        },
        {
          path: "add", // 详情
          name: "AddUser",
          meta: {
            lsUrl: router.userCatalogOS,
            title: "用户目录/组织结构/添加用户"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/orgStr/AddUser.vue"
            )
        },
        {
          path: "detail", // 详情
          name: "Detail",
          meta: {
            lsUrl: router.userCatalogOS,
            title: "用户目录/组织结构/用户详情"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/orgStr/UserDetail.vue"
            )
        }
      ]
    },
    {
      path: router.userCatalogSGM, // 用户目录-安全组管理
      name: "SafetyGroupManage",
      meta: {
        title: "用户目录/安全组管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/userCatalog/SafetyGroupManage/Index.vue"
        ),
      children: [
        {
          path: "",
          name: "SafetyGroupManage",
          meta: {
            title: "用户目录/安全组管理"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/SafetyGroupManage/SafetyGroupManage.vue"
            )
        },
        {
          path: "detail", // 用户目录-安全组管理-安全组管理详情
          name: "detail",
          meta: {
            lsUrl: router.userCatalogSGM,
            title: "用户目录/安全组管理/安全组管理详情"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/SafetyGroupManage/SafetyGroupDetails.vue"
            )
        }
      ]
    },
    {
      path: router.userCatalogRA, // 用户目录-注册审批
      name: "RegisterApproval",
      meta: {
        title: "用户目录/注册审批"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/userCatalog/RegisterApproval/RegisterApproval.vue"
        )
    },
    {
      path: router.userCatalogAD, // 用户目录-AD活动目录
      name: "ActiveDirectory",
      meta: {
        title: "用户目录/AD活动目录"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/userCatalog/ActiveDirectory/Index.vue"
        ),
      children: [
        {
          path: "", // 用户目录-AD活动目录
          name: "ActiveDirectory",
          meta: {
            title: "用户目录/AD活动目录"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/ActiveDirectory/ActiveDirectory.vue"
            )
        },
        {
          path: "adList", // 用户目录-AD活动目录-详情
          name: "adList",
          meta: {
            lsUrl: router.userCatalogAD,
            title: "用户目录/AD活动目录/详情"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/ActiveDirectory/ActiveDirectoryAdList.vue"
            )
        },
        {
          path: router.userCatalogDS, // 用户目录-钉钉同步
          name: "DingSynchronization",
          meta: {
            title: "用户目录/钉钉同步"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/DingSynchronization/Index.vue"
            ),
          children: [
            {
              path: "", // 用户目录-钉钉同步
              name: "DingSynchronization",
              meta: {
                title: "用户目录/钉钉同步"
              },
              component: () =>
                import(
                  /* webpackChunkName: "home" */ "@/views/userCatalog/DingSynchronization/DingSynchronization.vue"
                )
            },
            {
              path: "dingConfig", // 用户目录-钉钉同步-钉钉配置
              name: "DingConfig",
              meta: {
                lsUrl: router.userCatalogDS,
                title: "用户目录/钉钉同步/钉钉配置"
              },
              component: () =>
                import(
                  /* webpackChunkName: "home" */ "@/views/userCatalog/DingSynchronization/DingConfig.vue"
                )
            }
          ]
        },
        {
          path: router.userCatalogWX, // 用户目录-企业微信同步
          name: "WXSynchronization",
          meta: {
            title: "用户目录/企业微信同步"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/WXSynchronization/Index.vue"
            ),
          children: [
            {
              path: "", // 用户目录-企业微信同步
              name: "WXSynchronization",
              meta: {
                title: "用户目录/企业微信同步"
              },
              component: () =>
                import(
                  /* webpackChunkName: "home" */ "@/views/userCatalog/WXSynchronization/WXSynchronization.vue"
                )
            },
            {
              path: "WXConfig", // 用户目录-企业微信同步-企业微信配置
              name: "WXConfig",
              meta: {
                lsUrl: router.userCatalogWX,
                title: "用户目录/企业微信同步/企业微信配置"
              },
              component: () =>
                import(
                  /* webpackChunkName: "home" */ "@/views/userCatalog/WXSynchronization/WXConfig.vue"
                )
            }
          ]
        },
        {
          path: "adAdd", // 用户目录-AD活动目录-添加活动目录
          name: "adAdd",
          meta: {
            lsUrl: router.userCatalogAD,
            title: "用户目录/AD活动目录/添加"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/ActiveDirectory/ActiveDirectoryAdd.vue"
            )
        },
        {
          path: "adSet", // 用户目录-AD活动目录-添加活动目录
          name: "adSet",
          meta: {
            lsUrl: router.userCatalogAD,
            title: "用户目录/AD活动目录/配置"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/userCatalog/ActiveDirectory/ActiveDirectorySet.vue"
            )
        }
      ]
    },
    {
      path: router.inviteManagement, // 用户目录-邀请管理
      name: "InviteManagement",
      meta: {
        title: "用户目录/邀请管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/userCatalog/InviteManagement.vue"
        )
    },
    // 网络接入服务
    {
      path: router.netAccessServiceWAS, // 网络接入服务-无线认证策略
      name: "WifiAuthService",
      meta: {
        title: "网络接入服务/无线认证策略"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/networkAccessService/was/WifiAuthService.vue"
        )
    },
    {
      path: router.netAccessServicePortal, // 网络接入服务-portal设置
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/networkAccessService/Portal/PortalIndex.vue"
        ),
      children: [
        {
          path: "", // Portal设置-主页
          name: "Portal",
          meta: {
            title: "网络接入服务/portal设置"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/Portal/Portal.vue"
            )
        },
        {
          path: "portalAdd", // 添加
          name: "PortalAdd",
          meta: {
            lsUrl: router.netAccessServicePortal,
            title: "网络接入服务/portal设置/添加"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/Portal/add/PortalAdd.vue"
            )
        },
        {
          path: "portalDetails", // Portal设置-编辑
          name: "portalDetails",
          meta: {
            lsUrl: router.netAccessServicePortal,
            title: "网络接入服务/portal设置/详情"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/Portal/detail/PortalDetails.vue"
            )
        }
      ]
    },
    {
      path: router.netAccessServiceWVM, // 网络接入服务-无线访客管理
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/networkAccessService/wvm/VisitorManageIndex.vue"
        ),
      children: [
        {
          path: "", // 线免证设备-主页
          name: "WifiVisitorManage",
          meta: {
            title: "网络接入服务/无线访客管理"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/wvm/VisitorManage.vue"
            )
        },
        {
          path: "visitorAdd", // 线免证设备添加
          name: "visitorAdd",
          meta: {
            lsUrl: router.netAccessServiceWVM,
            title: "网络接入服务/无线访客管理/添加访客"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/wvm/VisitorAdd.vue"
            )
        }
      ]
    },
    {
      path: router.netAccessServiceWBM, // 网络接入服务-WiFi绑定管理
      name: "WifiBindManage",
      meta: {
        title: "网络接入服务/WIFI绑定管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/networkAccessService/wbm/WifiBindManage.vue"
        )
    },
    {
      path: router.netAccessServiceWNVD, // 网络接入服务-无线免证设备
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/networkAccessService/wnvd/WFDIndex.vue"
        ),
      children: [
        {
          path: "", // 线免证设备-主页
          name: "WifiNoVerifyDevice",
          meta: {
            title: "网络接入服务/无线免证设备"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/wnvd/WFD.vue"
            )
        },
        {
          path: "WFDAdd", // 线免证设备添加
          name: "WFDAdd",
          meta: {
            lsUrl: router.netAccessServiceWNVD,
            title: "网络接入服务/无线免证设备/添加无线免证设备"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/networkAccessService/wnvd/WFDAdd.vue"
            )
        }
      ]
    },
    {
      path: router.netAccessServiceWASU, // 网络接入服务-WIFI认证设置
      name: "WifiAuthSetUp",
      meta: {
        title: "网络接入服务/WIFI认证设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "WifiAuthSetUp" */ "@/views/networkAccessService/wasu/WifiAuthSetUp.vue"
        )
    },
    {
      path: router.netAccessServiceOU, // 网络接入服务-在线用户
      name: "OnlineUser",
      meta: {
        title: "网络接入服务/在线用户"
      },
      component: () =>
        import(
          /* webpackChunkName: "WifiAuthSetUp" */ "@/views/networkAccessService/ou/OnlineUser.vue"
        )
    },
    // 安全管理
    {
      path: router.safetyManagementSA, // 安全管理-系统认证设置
      name: "SystemAuth",
      meta: {
        title: "安全管理/系统认证设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/SystemAuth/SystemAuth.vue"
        )
    },
    {
      path: router.safetyManagementIP, // 安全管理-身份认证策略
      name: "IdentityPolicy",
      meta: {
        title: "安全管理/身份认证策略"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/Index.vue"
        ),
      children: [
        {
          path: "", // 安全管理-身份认证策略
          name: "IdentityPolicy",
          meta: {
            title: "安全管理/身份认证策略"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/IdentityPolicy.vue"
            )
        },
        {
          path: "strategyDetail", // 安全管理-身份认证策略-详情
          name: "strategyDetail",
          meta: {
            lsUrl: router.safetyManagementIP,
            title: "安全管理/身份认证策略/详情"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/EditStrategyFirst.vue"
            )
        },
        {
          path: "strategyEdit", // 安全管理-身份认证策略-编辑
          name: "strategyEdit",
          meta: {
            lsUrl: router.safetyManagementIP,
            title: "安全管理/身份认证策略/编辑"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/EditStrategy.vue"
            )
        },
        {
          path: "strategyAdd", // 安全管理-身份认证策略添加
          name: "strategyAdd",
          meta: {
            lsUrl: router.safetyManagementIP,
            title: "安全管理/身份认证策略/添加"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/AddStrategy.vue"
            )
        }
      ]
    },
    {
      path: router.safetyManagementAL, // 安全管理-异常登录设置
      name: "AbnormaLogin",
      meta: {
        title: "安全管理/异常登录设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/Index.vue"
        ),
      children: [
        {
          path: "", // 安全管理-异常登录设置
          name: "AbnormaLogin",
          meta: {
            title: "安全管理/异常登录设置"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/AbnormaLogin.vue"
            )
        },
        {
          path: "ALEdit", // 安全管理-异常登录-编辑
          name: "ALEdit",
          meta: {
            lsUrl: router.safetyManagementAL,
            title: "安全管理/异常登录设置/编辑"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/AbnormaLoginEdit.vue"
            )
        }
      ]
    },
    {
      path: router.safetyManagementLF, // 安全管理-登录失败
      name: "LoginFailure",
      meta: {
        title: "安全管理/登录失败控制"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/Index.vue"
        ),
      children: [
        {
          path: "", // 安全管理-登录失败详情
          name: "LoginFailure",
          meta: {
            title: "安全管理/登录失败控制"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/LoginFailure.vue"
            )
        },
        {
          path: "LFEdit", // 安全管理-登录失败编辑
          name: "LFEdit",
          meta: {
            lsUrl: router.safetyManagementLF,
            title: "安全管理/登录失败控制/编辑"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/LoginFailureEdit.vue"
            )
        }
      ]
    },
    {
      path: router.safetyManagementCommand, // 安全管理-口令管理
      name: "Command",
      meta: {
        title: "安全管理/口令管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/Index.vue"
        ),
      children: [
        {
          path: "", // 安全管理-口令管理
          name: "Command",
          meta: {
            title: "安全管理/口令管理"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/safetyManagement/Command.vue"
            )
        }
      ]
    },
    {
      path: router.safetyManagementOTP, // 安全管理-OTP管理
      name: "OTP",
      meta: {
        title: "安全管理/OTP管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/OTP.vue"
        )
    },
    {
      path: router.safetyManagementMD, // 安全管理-移动设备管理
      name: "MobilDevices",
      meta: {
        title: "安全管理/移动设备管理"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/safetyManagement/MobilDevices.vue"
        )
    },
    // 审计报表
    {
      path: router.auditReportUA, // 审计报表-用户审计
      name: "UserAudit",
      meta: {
        title: "审计报表/用户审计"
      },
      component: () =>
        import(
          /* webpackChunkName: "audit" */ "@/views/auditReport/UserAudit.vue"
        )
    },
    {
      path: router.auditReportApp, // 审计报表-应用审计
      name: "AppAudit",
      meta: {
        title: "审计报表/应用审计"
      },
      component: () =>
        import(/* webpackChunkName: "audit" */ "@/views/auditReport/Index.vue"),
      children: [
        {
          path: "", // 审计报表-应用审计
          name: "AppAudit",
          meta: {
            title: "审计报表/应用审计"
          },
          component: () =>
            import(
              /* webpackChunkName: "audit" */ "@/views/auditReport/AppAudit.vue"
            )
        },
        {
          path: "appAuditDetail", // 审计报表-应用审计详情
          name: "appAuditDetail",
          meta: {
            title: "审计报表/应用审计/应用审计列表",
            lsUrl: router.auditReportApp
          },
          component: () =>
            import(
              /* webpackChunkName: "audit" */ "@/views/auditReport/AppAuditList.vue"
            )
        }
      ]
    },
    {
      path: router.auditReportAdmin, // 审计报表-管理员审计
      name: "AdminAudit",
      meta: {
        title: "审计报表/管理员审计"
      },
      component: () =>
        import(
          /* webpackChunkName: "audit" */ "@/views/auditReport/AdminAudit.vue"
        )
    },
    {
      path: router.auditReportWireless, // 审计报表-无线审计
      name: "VisitorsLogin",
      meta: {
        title: "审计报表/无线审计"
      },
      component: () =>
        import(
          /* webpackChunkName: "audit" */ "@/views/auditReport/VisitorsLogin.vue"
        )
    },
    {
      path: router.auditReportFacility, // 审计报表-设备审计
      name: "",
      meta: {
        title: "审计报表/设备审计"
      },
      component: () =>
        import(
          /* webpackChunkName: "audit" */ "@/views/auditReport/facilityAudit/Index.vue"
        ),
      children: [
        {
          path: "", // 审计报表-设备审计-设备列表
          meta: {
            title: "审计报表/设备审计"
          },
          component: () =>
            import(
              /* webpackChunkName: "audit" */ "@/views/auditReport/facilityAudit/FacilityList.vue"
            )
        },
        {
          path: "detailList", // 审计报表-设备审计-详情列表
          meta: {
            title: "审计报表/设备审计/详情列表",
            lsUrl: router.auditReportFacility
          },
          component: () =>
            import(
              /* webpackChunkName: "audit" */ "@/views/auditReport/facilityAudit/FacilityDetailList.vue"
            )
        }
      ]
    },
    // 系统设置
    {
      path: router.systemSetupAdmin, // 系统设置-管理员设置
      name: "Admin",
      meta: {
        title: "系统设置/管理员设置"
      },
      component: () =>
        import(/* webpackChunkName: "home" */ "@/views/systemSetup/Admin.vue")
    },
    {
      path: router.systemSetupAP, // 系统设置-账号属性设置
      name: "AccountProp",
      meta: {
        title: "系统设置/账号属性设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/systemSetup/AccountProp.vue"
        )
    },
    {
      path: router.systemSetupAdminMsg, // 系统设置-短信验证码设置
      name: "MessageAuthCode",
      meta: {
        title: "系统设置/短信验证码设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/systemSetup/MessageAuth/MessageAuthCodeIndex.vue"
        ),
      children: [
        {
          path: "", // 系统设置-短信验证码设置
          name: "MessageAuthCode",
          meta: {
            title: "系统设置/短信验证码设置"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/systemSetup/MessageAuth/MessageAuthCode.vue"
            )
        },
        {
          path: "messageEdit", // 短信设置-编辑
          name: "messageEdit",
          meta: {
            lsUrl: router.systemSetupAdminMsg,
            title: "系统设置/短信验证码设置/编辑"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/systemSetup/MessageAuth/MessageAuthCodeEdit.vue"
            )
        }
      ]
    },
    {
      path: router.systemSetupAdminMail, // 系统设置-邮箱验证码设置
      name: "MailboxAuthCode",
      meta: {
        title: "系统设置/邮箱验证码设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/systemSetup/MailAuth/MailAuthIndex.vue"
        )
    },
    {
      path: router.systemSetupAuthSource, // 系统设置- 认证源
      name: "AuthSource",
      meta: {
        title: "系统设置/认证源设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "AuthSource" */ "@/views/systemSetup/AuthSource.vue"
        )
    },
    {
      path: router.systemSetupPageSet, // 系统设置- 页面设置
      name: "PageSet",
      meta: {
        title: "系统设置/页面设置"
      },
      component: () =>
        import(
          /* webpackChunkName: "AuthSource" */ "@/views/systemSetup/PageSet/PageIndex.vue"
        )
    },
    {
      path: router.systemSetupUserDefined, // 系统设置- 页面设置
      name: "systemSetupUserDefined",
      meta: {
        title: "系统设置/自定义属性"
      },
      component: () =>
        import(
          /* webpackChunkName: "AuthSource" */ "@/views/systemSetup/UserDefined/UserDefinedIndex.vue"
        ),
      children: [
        {
          path: "", // 自定义属性-主页
          name: "systemSetupUserDefined",
          meta: {
            title: "系统设置/自定义属性"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/systemSetup/UserDefined/UserDefined.vue"
            )
        }
      ]
    },
    // 资源管理
    {
      path: router.resourceManageAM, // 资源管理-应用管理

      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/views/resourceManage/appManage/AppManage.vue"
        ),

      children: [
        {
          path: "",
          name: "appList",
          meta: {
            title: "资源管理/应用管理"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/appManage/AppList.vue"
            )
        },
        // 应用管理--->跳转到添加新应用
        {
          path: "appAddList",
          name: "appAddList",
          meta: {
            lsUrl: router.resourceManageAM,
            title: "资源管理/应用管理/添加新应用"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/appManage/appAdd/AppAddList.vue"
            )
        },
        // 应用管理--->跳转到添加应用界面
        {
          path: "appAddType",
          name: "appAddType",
          meta: {
            lsUrl: router.resourceManageAM,
            title: "资源管理/应用管理/添加应用"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/appManage/appAdd/AppAddType.vue"
            )
        },
        // 跳转到应用配置界面这个页面中有四个菜单栏
        {
          path: "appDetails",
          name: "appDetails",
          meta: {
            lsUrl: router.resourceManageAM,
            title: "资源管理/应用管理/应用信息"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/appManage/appDetails/AppDetails.vue"
            )
        }
      ]
    },
    {
      path: router.resourceManageDM, // 资源管理-设备管理
      component: () =>
        import(
          /* webpackChunkName: "resourceManage" */ "@/views/resourceManage/deviceManage/DeviceManage.vue"
        ),

      children: [
        {
          path: "",
          name: "DMIndex",
          meta: {
            title: "资源管理/设备管理"
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/deviceManage/DeviceManageIndex.vue"
            )
        },
        {
          path: "add",
          name: "DMAdd",
          meta: {
            title: "资源管理/设备管理/添加设备",
            lsUrl: router.resourceManageDM
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/deviceManage/DeviceManageAdd.vue"
            )
        },
        {
          path: "detail",
          name: "DMDetail",
          meta: {
            title: "资源管理/设备管理/配置详情",
            lsUrl: router.resourceManageDM
          },
          component: () =>
            import(
              /* webpackChunkName: "home" */ "@/views/resourceManage/deviceManage/DeviceManageDetail.vue"
            )
        }
      ]
    },
    // 控制台首页
    { path: "/home", redirect: { path: "/" } },
    {
      path: "/",
      name: "home",
      meta: {
        title: "首页"
      },
      component: () => import("@/views/home/HomeIndex.vue"),
      beforeEnter: (to, from, next) => {
        // 页面刷新时，重新赋值token
        // let token;
        // let tempToken = window.localStorage.getItem('token');
        // if (tempToken && tempToken !== undefined) { // 登录后，非第一次跳转到/路径下
        //   token = tempToken;
        // } else { // 第一次跳转到/路径下
        //   token = to.query.ticket
        // }
        let token = to.query.ticket;
        if (token && token !== undefined && token !== null) {
          window.localStorage.setItem("token", token);
        } else {
          token = window.localStorage.getItem("token");
        }
        store.commit("changeToken", token); // token不为空时才保存

        if (store.state.token) {
          // 1.如果请求中有登录状态ticket，则继续请求
          next();
        } else {
          // 2.如果没有ticket，跳转到登录页面
          console.log("skip");
          // window.location = url.portal;
          next("/login");
        }
        next();
      }
    },
    {
      path: "*",
      name: "NotFound",
      component: () =>
        import(/* webpackChunkName: "NotFound" */ "@/views/404.vue")
    },
    // 测试添加的二级路由
    {
      path: "/testCustom",
      name: "testCustom",
      meta: {
        title: "test"
      },
      component: () =>
        import(/* webpackChunkName: "home" */ "@/components/custom/Test.vue")
    },
    // 测试添加的二级路由
    {
      path: "/testTabs",
      name: "testTabs",
      meta: {
        title: "testTabs"
      },
      component: () =>
        import(/* webpackChunkName: "home" */ "@/components/tabs/Test.vue")
    },
    // 测试添加的二级路由
    {
      path: "/testTransfer",
      name: "test",
      meta: {
        title: "test"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/components/modal/TransferTest.vue"
        )
    },
    // 测试添加文件上传圆形进度环
    {
      path: "/testCircle",
      name: "test",
      meta: {
        title: "test"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/components/modal/FixedCircleTest.vue"
        )
    },
    {
      path: "/test",
      name: "test",
      meta: {
        title: "test"
      },
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/components/modal/ModalTest.vue"
        )
    },
    // 测试添加的二级路由loading
    {
      path: "/testLoading",
      component: () =>
        import(
          /* webpackChunkName: "home" */ "@/components/loading/Loading.vue"
        )
    },
    // 测试添加的二级路由
    {
      path: "*",
      component: () => import(/* webpackChunkName: "home" */ "@/views/404.vue")
    }
  ]
});
