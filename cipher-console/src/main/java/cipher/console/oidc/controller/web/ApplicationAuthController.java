package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.publistener.AppBehaviorPublistener;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
@RequestMapping(value = "/cipher/appAuth")
@EnableAutoConfiguration
public class ApplicationAuthController {

    @Autowired
    private ApplicationAuthService applicationAuthService;

    @Autowired
    private UserApplicationService userApplicationService;

    @Autowired
    private TeamApplicationMapService teamApplicationMapService;

    @Autowired
    private ApplicationGroupService applicationGroupService;

    @Autowired
    private AppService appService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private SubAccountMapService subAccountMapService;


    @Autowired
    private ApplicationAuditInfoService applicationAuditInfoService;

    @Autowired
    private AppBehaviorPublistener appBehaviorPublistener;

    @Autowired
    private RedisClient<String,Object> redisClient;



    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationAuthController.class);


    //获取安全组及用户

    @RequestMapping(value = "/authorizationSecurityGroup")
    @ResponseBody
    public Map<String, Object> applist(String currentPage, TeamInfo teamInfo) {
        //FIXME:FIXED-诗昭
        TeamPageInfoDomain teamPageInfoDomain = applicationAuthService.queryTeamPageList(currentPage, teamInfo);
        Map<String, Object> map = new HashMap<>();
        map.put("id", teamInfo.getApplicationId());
        map.put("teamPageInfoDomain", teamPageInfoDomain);
        return map;
    }


    //获取部门及用户

    @RequestMapping(value = "/authorizationDepartment")
    @ResponseBody
    public Map<String, Object> departlist(String currentPage, GroupInfoDomain groupInfoDomain) {
        //FIXME:FIXED-诗昭
        GroupPageInfoDomain groupPageInfoDomain = applicationAuthService.queryGroupPageList(currentPage, groupInfoDomain);
        Map<String, Object> map = new HashMap<>();
        map.put("id", groupInfoDomain.getApplicationId());
        map.put("groupPageInfoDomain", groupPageInfoDomain);
        return map;
    }

    /**
     * modify by 田扛
     * time 2019年3月10日19:50:10
     * @param accountNumbers
     * @param applicationId
     * @param request
     * @return
     * 应用授权到人
     * （数据隔离修改）
     */
    @CheckToken
    @RequestMapping(value = "/addUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addUser(@RequestParam(value = "accountNumber",required = false) String accountNumbers,
                                       @RequestParam(value = "uuid") String uuid,
                                       @RequestParam(value = "applicationId") String applicationId, HttpServletRequest request,
                                       HttpSession session) {

        try {
            //管理员日志
            ApplicationInfoDomain applicationInfo=appService.queryApplication(applicationId);
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.AUTHORIZED_MAINTENANC.getType(),applicationInfo.getApplicationName()+"应用授权用户");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
          } catch (Exception e1) {
            e1.printStackTrace();
         }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        redisClient.remove(CacheKey.getCacheApplicationList(companyId));
        String[] accounts = uuid.split(",");
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(applicationId)) {
            userApplicationService.deleteUserAuth(Integer.valueOf(applicationId));
            map.put("code", 0);
            map.put("msg", "授权成功");
            return map;
        }
        List<UserApplicationMapInfo> list = new ArrayList<>();
        for (int i = 0; i < accounts.length; i++) {
            UserApplicationMapInfo userApplicationMapInfo = new UserApplicationMapInfo();
            userApplicationMapInfo.setAccountNumber(accounts[i]);
            userApplicationMapInfo.setUserId(accounts[i]);
            userApplicationMapInfo.setApplicationId(Integer.valueOf(applicationId));
            list.add(userApplicationMapInfo);
        }
        try {
            userApplicationService.deleteUserAuth(Integer.valueOf(applicationId));
            for (UserApplicationMapInfo userApplicationMapInfo : list) {
                userApplicationService.insertSelective(userApplicationMapInfo);
            }
            map.put("code", 0);
            map.put("msg", "授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "服务器错误");
            return map;
        }

        return map;
    }

    /**
     * modify by 田扛
     * time 2019年3月10日19:49:31
     * @param teamId
     * @param applicationId
     * @param request
     * @return
     */
    //应用授权到安全组
    @RequestMapping(value = "/addTeam", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addTeam(@RequestParam(value = "teamId") String teamId,
                                       @RequestParam(value = "applicationId") String applicationId, HttpServletRequest request,
                                       HttpSession session) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(teamId) || StringUtils.isEmpty(applicationId)) {
            TeamApplicationMap teamApplicationMap = new TeamApplicationMap();
            teamApplicationMap.setApplicationId(Integer.valueOf(applicationId));
            teamApplicationMapService.deleteTeamApplicationMap(teamApplicationMap);
            map.put("code", 0);
            map.put("msg", "取消授权成功");
            return map;
        }
        String[] teamIds = teamId.split(",");
        List<TeamApplicationMap> list = new ArrayList<>();
        for (int i = 0; i < teamIds.length; i++) {
            TeamApplicationMap teamApplicationMap = new TeamApplicationMap();
            teamApplicationMap.setTeamId(Integer.valueOf(teamIds[i]));
            teamApplicationMap.setApplicationId(Integer.valueOf(applicationId));
            list.add(teamApplicationMap);
        }
        try {
            TeamApplicationMap teamApplicationMap = new TeamApplicationMap();
            teamApplicationMap.setApplicationId(Integer.valueOf(applicationId));
            teamApplicationMapService.deleteTeamApplicationMap(teamApplicationMap);
            for (TeamApplicationMap team : list) {
                teamApplicationMapService.insertSelective(team);
            }
            map.put("code",0);
            map.put("msg", "安全组授权成功授权");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "服务器错误");
            try {
                //管理员日志
                String admin = ConstantsCMP.getCipherUuidInfo(request);
                AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.AUTHORIZED_MAINTENANC.getType(), "应用授权到安全组");
                adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                ApplicationAuditInfo applicationAuditInfo = new ApplicationAuditInfo(Integer.valueOf(applicationId), admin, IpUtil.getIp(), ConstantsCMP.ApplicationConstant.AUTHORIZE, "应用授权到安全组", "应用授权到安全组", new Date(), "应用授权到安全组",companyId);
                applicationAuditInfoService.insertApplicationAuditInfo(applicationAuditInfo);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }

        return map;
    }


    //应用授权到部门

    /**
     * modify by 田扛
     * time 2019年3月10日19:50:37
     * @param groupId
     * @param applicationId
     * @param request
     * @return
     */
    @RequestMapping(value = "/addGroup", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addGroup(@RequestParam(value = "groupId") String groupId,HttpSession session,
                                        @RequestParam(value = "applicationId") String applicationId, HttpServletRequest request) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(groupId) || StringUtils.isEmpty(applicationId)) {

            GroupAuthorizationMapDomain groupAuthorizationMapDomain = new GroupAuthorizationMapDomain();
            groupAuthorizationMapDomain.setApplicationId(applicationId);
            applicationGroupService.deleteGroupAuthorizationMap(groupAuthorizationMapDomain);
            map.put("code", 0);
            map.put("msg", "取消授权成功");
            return map;
        }
        List<GroupAuthorizationMapDomain> list=new ArrayList<>();
        String[] groupIds=groupId.split(",");
        for (int i=0;i<groupIds.length;i++){

            GroupAuthorizationMapDomain groupAuthorizationMapDomain = new GroupAuthorizationMapDomain();
            groupAuthorizationMapDomain.setGroupId(Integer.valueOf(groupIds[i]));
            groupAuthorizationMapDomain.setApplicationId(applicationId);
            list.add(groupAuthorizationMapDomain);
        }
        try {
            GroupAuthorizationMapDomain groupAuthorizationMapDomain = new GroupAuthorizationMapDomain();
            groupAuthorizationMapDomain.setApplicationId(applicationId);
            applicationGroupService.deleteGroupAuthorizationMap(groupAuthorizationMapDomain);
            for (GroupAuthorizationMapDomain group:  list) {
                applicationGroupService.insertGroupAuthorizationMap(group);
            }
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.AUTHORIZED_MAINTENANC.getType(),"应用授权给部门");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
            map.put("code", 0);
            map.put("msg", "部门授权成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("code", 1);
            map.put("msg", "服务器错误");
            try {
                //管理员日志
                String admin = ConstantsCMP.getCipherUuidInfo(request);
                AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.AUTHORIZED_MAINTENANC.getType(), "应用授权到部门");
                adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                ApplicationAuditInfo applicationAuditInfo = new ApplicationAuditInfo(Integer.valueOf(applicationId), admin, IpUtil.getIp(), ConstantsCMP.ApplicationConstant.AUTHORIZE, "应用授权到部门", "应用授权到部门", new Date(), "应用授权到部门",companyId);
                applicationAuditInfoService.insertApplicationAuditInfo(applicationAuditInfo);
            } catch (Exception e1) {
                e1.printStackTrace();
            }

        }
        return map;
    }


    @RequestMapping(value = "/authList")
    @ResponseBody
    public Map<String, Object> authListPage(@RequestParam(value = "applicationId") Integer applicationId,
                                            @RequestParam(value = "queryType", defaultValue = "1") Integer queryType) {
        //FIXME:FIXED-诗昭
        Map<String, Object> map = new HashMap<>();
        map.put("applicationId", applicationId);
        map.put("queryType", queryType);
        return map;

    }

    /*
    * 子账号取消授权
    * （数据隔离修改）
    * */
    @CheckToken
    @RequestMapping("/deleteUserAuth")
    @ResponseBody
    public Map<String, Object> deleteUserAuth(@RequestParam(value = "applicationId") String applicationId, @RequestParam(value = "accountNumber",required = false) String accountNumber,
                                              @RequestParam(value = "uuid") String uuid, HttpServletResponse response,
                                              HttpServletRequest request, HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        try {
            applicationAuthService.deleteUserAuth(applicationId, uuid);
            List<GroupInfoDomain> groups = applicationAuthService.getDepatment(applicationId, uuid);
            List<TeamInfo> teams = applicationAuthService.getTeam(applicationId, uuid);
            String admin = ConstantsCMP.getCipherUuidInfo(request);
            String companyId=ConstantsCMP.getSessionCompanyId(request);
            map.put("depatment", groups);
            map.put("teams", teams);
            map.put("return_code", 1);
            //管理员日志
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.AUTHORIZED_MAINTENANC.getType(),"用户取消授权");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
            /*ApplicationAuditInfo applicationAuditInfo = new ApplicationAuditInfo(Integer.valueOf(applicationId), admin, IpUtil.getIp(), ConstantsCMP.ApplicationConstant.AUTHORIZE, "用户取消授权", "用户取消授权", new Date(), "用户取消授权",companyId);
            applicationAuditInfoService.insertApplicationAuditInfo(applicationAuditInfo);*/
        } catch (Exception e) {
            ResponseUtils.customFailueResponse(response, "取消授权失败");
            e.printStackTrace();
        }
        return map;
    }


   /*
   * 获取应用从账号列表
   *
   * */


    @RequestMapping(value = "/authList", params = "json")
    @ResponseBody
    public Map<String, Object> authList(DataGridModel pagemodel, QueryInfoDomain queryInfoDomain,HttpServletRequest request,
                                        @RequestParam(value = "queryType", defaultValue = "1") Integer queryType
                                       ) {


        Map<String, Object> map=new HashMap<>();
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        queryInfoDomain.setCompanyId(companyId);
        if (queryType == 1) {
            AppSonAccountDomain appSonAccountDomain = new AppSonAccountDomain();
            if (null != queryInfoDomain) {
                appSonAccountDomain.setAccountNumber(queryInfoDomain.getQueryName());
                if(null!=queryInfoDomain.getApplicationId()){
                    appSonAccountDomain.setApplicationId(String.valueOf(queryInfoDomain.getApplicationId()));
                }
            }
            appSonAccountDomain.setCompanyId(companyId);
            appSonAccountDomain.setIsSynchron(queryInfoDomain.getIsSynchron());
            map=appService.queryAccount(appSonAccountDomain, pagemodel,companyId);
        }


        /*else if (queryType == 2) {
            map=applicationAuthService.queryApplicationTeamPage(queryInfoDomain, pagemodel);
        } else {
            map=applicationAuthService.queryApplicationGroupPage(queryInfoDomain, pagemodel);
        }*/

        //获取从账号规则
        String associatedAccount=appService.associatedAccountById(queryInfoDomain.getApplicationId());
        if(StringUtils.isNotEmpty(associatedAccount)) {
            JSONObject jsonobject = JSONObject.fromObject(associatedAccount);
            SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
            map.put("subAccountRuleInfo", subAccountRuleInfo);
        }else {
            map.put("subAccountRuleInfo", null);
        }
        return map;
    }


    //取消用户与应用账号关联
    @RequestMapping(value = "/deleteAppUserMap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteMap(SubAccountMapDomain form, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        try {
            LOGGER.debug("Enter SubAccountController.deleteMap");
            subAccountMapService.deleteSubAccountMap(form);
            map.put("return_msg", "取消关联成功");
            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);

        } catch (Exception e) {
            LOGGER.error("Enter SubAccountController.cancellAuth Error:" + e.getCause());
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            e.printStackTrace();
        }
        return map;
    }


    //取消安全组与应用账号关联
    @RequestMapping(value = "/deleteAppTeamMap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteAppTeamMap(TeamApplicationMap form, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            LOGGER.debug("Enter ApplicationAuthController.deleteAppTeamMap");
            teamApplicationMapService.deleteTeamApplicationMap(form);
            map.put("return_msg", "取消关联成功");
            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
            String admin = ConstantsCMP.getCipherUuidInfo(request);
            String companyId=ConstantsCMP.getSessionCompanyId(request);
            ApplicationAuditInfo applicationAuditInfo = new ApplicationAuditInfo(Integer.valueOf(form.getApplicationId()), admin, IpUtil.getIp(), ConstantsCMP.ApplicationConstant.AUTHORIZE, "安全组取消授权", "安全组取消授权", new Date(), "安全组取消授权",companyId);
            applicationAuditInfoService.insertApplicationAuditInfo(applicationAuditInfo);
        } catch (Exception e) {
            LOGGER.error("Enter ApplicationAuthController.deleteAppTeamMaph Error:" + e.getCause());
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            e.printStackTrace();
        }
        return map;
    }


    //取消部门与应用账号关联
    @RequestMapping(value = "/deleteAppGroupMap", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> deleteAppGroupMap(GroupAuthorizationMapDomain form, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            LOGGER.debug("Enter ApplicationAuthController.deleteAppGroupMap");
            applicationGroupService.deleteGroupAuthorizationMap(form);
            map.put("return_msg", "取消关联成功");
            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
            String admin = ConstantsCMP.getSessionUser(request);
            String companyId=ConstantsCMP.getSessionCompanyId(request);
            /*ApplicationAuditInfo applicationAuditInfo = new ApplicationAuditInfo(Integer.valueOf(form.getApplicationId()), admin, IpUtil.getIp(), ConstantsCMP.ApplicationConstant.AUTHORIZE, "部门取消授权", "部门取消授权", new Date(), "部门取消授权",companyId);
            applicationAuditInfoService.insertApplicationAuditInfo(applicationAuditInfo);*/
            try{
                UserInfoDomain userInfoDomain = (UserInfoDomain) request.getSession().getAttribute(ConstantsCMP.CIPHER_CONSOLE_USER_SESSION_INFO);
                String userName = userInfoDomain.getUserName()+"("+userInfoDomain.getAccountNumber()+")";
                AppAuditInfo appAuditInfo = new AppAuditInfo(Integer.valueOf(form.getApplicationId()),userName,3,"部门取消授权成功",companyId);
                appBehaviorPublistener.publish(appAuditInfo);
            }catch (Exception e){
                e.printStackTrace();
            }
        } catch (Exception e) {
            LOGGER.error("Enter ApplicationAuthController.deleteAppGroupMap Error:" + e.getCause());
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            e.printStackTrace();
        }
        return map;
    }




}
