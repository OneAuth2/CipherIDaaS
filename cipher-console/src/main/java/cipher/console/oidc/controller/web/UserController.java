package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.exceldomain.UserInfoExcel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.SubAccountMapper;
import cipher.console.oidc.mapper.UserGroupMapper;
import cipher.console.oidc.model.ApplicationModel;
import cipher.console.oidc.model.ApplicationSubAccountSubTableModel;
import cipher.console.oidc.model.UserInfoModel;
import cipher.console.oidc.model.UserManagementModel;
import cipher.console.oidc.publistener.UserBehaviorPublistener;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.*;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;


/**
 * 用户管理选项卡
 * create by shizhao
 *
 * @author shizhao
 * @since 2018/5/30
 */
@Controller
@RequestMapping(value = "/cipher/user")
@EnableAutoConfiguration
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class.getSimpleName());

    @Autowired
    private AppService appService;
    @Autowired
    private UserService userService;
    @Autowired
    private UserGroupMapper userGroupMapper;

    @Autowired
    private PasswordAuthorizationService passwordAuthorizationService;

    @Autowired
    private SubAccountService subAccountService;


    @Autowired
    private SubAccountMapService subAccountMapService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @Autowired
    private PasswordSettingService passwordSettingService;

    @Autowired
    private TeamUserMapInfoService teamUserMapInfoService;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private UserBehaviorInfoService userBehaviorInfoService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;

    /**
     * 用户管理-用户状态改变
     *(数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "/status")
    @ResponseBody
    public Map<String, Object> enableAccount(@RequestParam(value = "accountNumber",required = false) String accountNumber,
                                             @RequestParam(value = "uuid") String uuid,
                                             @RequestParam(value = "status") int status,HttpSession session,
                                             DataGridModel dataGridModel, HttpServletRequest request) {

        if (logger.isDebugEnabled()) {
            logger.debug("turn to /status the enableAccount uuid=[{}],status=[{}]",
                    new Object[]{uuid, status});
        }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        try {
            accountNumber = accountNumber.replace("/", "");
            accountNumber = accountNumber.replace(",", "");
            UserManagementModel userManagementModel = new UserManagementModel();
            userManagementModel.setUuid(uuid);
            userManagementModel.setStatus(status);
            userManagementModel.setUserName("");
            userService.updateAccountStatus(userManagementModel);
           boolean result = redisTemplate.hasKey(CacheKey.getUserByNameCacheKey(uuid));
            if (result == true) {
                redisTemplate.delete(CacheKey.getUserByNameCacheKey(uuid));

            }
            UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
            try {
                if (status == 1) {
                    //管理员日志
                    AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "启用账号:"+userInfoDomain.getAccountNumber());
                    adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                } else if (status == 2) {
                    //管理员日志
                    String admin = ConstantsCMP.getCipherUuidInfo(request);
                    AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "停用账号:"+userInfoDomain.getAccountNumber());
                    adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);
        } catch (Exception e) {
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            e.printStackTrace();
        }
        return map;
    }


    /**
     * 用户管理-用户密码重置(本地重置密码)
     * (数据隔离调整)
     *
     * @param account 主账号
     */
    /**
     *create by cozi
     * date from 2019-06-11
     * 重置密码增加自定义密码，修改密码后首次登录是否需要修改密码
     * @param newPwd
     * @param logFlag
     */
    @CheckToken
    @RequestMapping(value = "/reset/password")
    @ResponseBody
    public void resetAccountPassword(@RequestParam(value = "accountNumber",required = false) String account,
                                     @RequestParam(value = "uuid") String uuid,
                                     @RequestParam(value = "newPwd",required = false) String newPwd,
                                     @RequestParam(value = "logFlag",required = false,defaultValue = "0") int logFlag,
                                     HttpServletRequest request, HttpServletResponse response, HttpSession session) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /reset/password the resetAccountPassword accountNumber=[{}]",
                    new Object[]{account});
        }
        if(newPwd.length()<6){
            ResponseUtils.customFailueResponse(response,"密码长度需大于等于6位数！");
            return;
        }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
        passwordAuthorizationService.resetAccountPassword(uuid,companyId,newPwd,logFlag);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "重置账号:"+userInfoDomain.getAccountNumber()+"密码");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        ResponseUtils.customSuccessResponse(response, "重置密码成功");


    }


    @Override
    public int hashCode() {

        return super.hashCode();
    }

    @RequestMapping(value = "/import")
    @ResponseBody
    public Map<String, Object> accountImport(@RequestParam(value = "accountExcel") MultipartFile file, HttpServletResponse response, HttpServletRequest requeset,HttpSession session) {

        //判断文件是否为空
        if (file == null) {
            ResponseUtils.customFailueResponse(response, "文件不能为空");
            return null;
        }
        List<UserInfoExcel> list = FileUtil.importExcel(file, 1, 1, UserInfoExcel.class);
        if (CollectionUtils.isEmpty(list)) {
            ResponseUtils.customFailueResponse(response, "该表格中没有任何数据");
        }
        //导入的总的数据条数
        int total = list.size();
        List<UserInfoExcel> respList = new ArrayList<>();

        Iterator<UserInfoExcel> iterator = list.iterator();
        while (iterator.hasNext()) {
            UserInfoExcel excel = iterator.next();
            if (StringUtils.isEmpty(excel.getUserName()) ||
                    StringUtils.isEmpty(excel.getMail())
                    ) {
                respList.add(excel);
                iterator.remove();
            }
        }
        //格式错误的总记录条数
        int typeError = respList.size();

//        从数据库中查询已经存在
        List<UserInfoDomain> repeatNameList = userService.queryRepeatNameAccountList(list);

        List<UserInfoExcel> diffList = diffRepeatName(list, repeatNameList, respList);


        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String downloadSecret = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(ConstantsUtils.EXCEL_RETURN + downloadSecret, respList, 5 * 60, TimeUnit.SECONDS);
        int success = diffList.size();
        String companyId=ConstantsCMP.getSessionCompanyId(requeset);
        if (!CollectionUtils.isEmpty(diffList)) {
            userService.batchInsertUser(diffList);
            List<PasswordAuthorizationMagDomain> passwordDomains = new ArrayList<PasswordAuthorizationMagDomain>();
            PasswordSettingDomain passwordSettingDomain = passwordSettingService.queryPasswordSetting(companyId);
            for (UserInfoExcel userInfoExcel : diffList) {
                passwordDomains.add(new PasswordAuthorizationMagDomain(userInfoExcel.getMail(), passwordSettingDomain.getInit()));
            }
            passwordAuthorizationService.insertAccountPasswordExcel(passwordDomains);
//            ResponseUtils.customSuccessResponse(response,"格式错误"+typeError+"条数据,"+"重复"+(total-typeError-success)+"条数据,"+"成功"+success+"条数据");
        }

        Map<String, Object> map = new HashMap<>();
        map.put("return_code", 1);
        map.put("secret", downloadSecret);
        map.put("return_msg", "格式错误" + typeError + "条数据," + "重复" + (total - typeError - success) + "条数据," + "成功" + success + "条数据");
        try {
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "导入账号列表");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/downloadReturnExcel")
    @SuppressWarnings("unchecked")
    public void downloadExcel(HttpServletResponse response, @RequestParam(value = "downloadSecret") String secret) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<UserInfoExcel> list = (List<UserInfoExcel>) valueOperations.get(ConstantsUtils.EXCEL_RETURN + secret);
        FileUtil.exportExcel(list, "导入结果", "", UserInfoExcel.class, "结果反馈.xls", response);
    }


    private List<UserInfoExcel> diffRepeatName(List<UserInfoExcel> sourceList, List<UserInfoDomain> repeatList, List<UserInfoExcel> respList) {
        Iterator<UserInfoExcel> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            UserInfoExcel excel = iterator.next();
            for (UserInfoDomain userInfoDomain : repeatList) {
                if (excel.getMail().equals(userInfoDomain.getAccountNumber())) {
                    iterator.remove();
                    respList.add(excel);
                    break;
                }
            }
        }

        return sourceList;
    }


    private List<ApplicationSubAccountSubTableModel> getApplicationsById(String accountNumber, String sidx, String sord) {
        List<ApplicationSubAccountSubTableModel> applicationList = subAccountService.getSubAccountApplication(accountNumber, sidx, sord);
        applicationList.addAll(subAccountService.getUnAuthorizedUserApplication(accountNumber, sidx, sord));
        return applicationList;
    }




    /**
     *获取管理员列表
     * （数据隔离修改）
     * */
    @CheckToken
    @RequestMapping(value = "/userAdmin")
    @ResponseBody
    public Map<String, Object> getAdminTable(@RequestParam(value = "queryName", required = false, defaultValue = "") String queryName,
                                             @RequestParam(value = "accountStatus", required = false, defaultValue = "0") int accountStatus,
                                             @RequestParam(value = "sord", required = false, defaultValue = "desc") String sord,
                                             @RequestParam(value = "sidx", required = false) String sidx,
                                             DataGridModel dataGridModel,HttpServletRequest request) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /userAdmin on the getMyTable name=[{}]",
                    new Object[]{queryName});
        }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        UserManagementModel userManagementModel = new UserManagementModel();
        userManagementModel.setCompanyId(companyId);
        userManagementModel.setQueryName(queryName);
        userManagementModel.setStatus(accountStatus);
        userManagementModel.setSidx(sidx);
        userManagementModel.setSord(sord);
        return userService.queryAdminTableMapper(userManagementModel, dataGridModel);
    }

    /*
    * 移除管理员
    * (数据隔离修改)
    * */
    @CheckToken
    @RequestMapping(value = "/deleteAdmin")
    @ResponseBody
    public Map<String, Object> deleteAdmin(@RequestParam(value = "accountNumber",required = false) String accountNumber,
                                           @RequestParam(value = "uuid") String uuid,HttpSession session,
                                           HttpServletResponse response,HttpServletRequest request) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashedMap();
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /deleteAdmin accountNumber=[{}] uuid=[{}]",
                    new Object[]{accountNumber,uuid});
        }
        accountNumber = accountNumber.replace("/", "");
        UserManagementModel userManagementModel = new UserManagementModel();
        userManagementModel.setAccountNumber(accountNumber);
        userManagementModel.setUuid(uuid);
        userManagementModel.setIsAdmin(ConstantsCMP.IS_NOT_ADMIN);
        userService.updateAdmin(userManagementModel);
        map.put("return_code", "1");
        map.put("msg", "用户移除管理组成功");
        JSONObject json = JSONObject.fromObject(map);
        String admin=ConstantsCMP.getCipherUuidInfo(request);
        UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "移除管理账号:"+userInfoDomain.getAccountNumber());
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }


    /**
     * 新增管理员
     * create by田扛
     * create time 2019年3月11日14:14:09
     * @param accountNumber
     * @return
     * (数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "/adminAdd")
    @ResponseBody
    public Map<String,Object> adminAdd(@RequestParam(value = "accountNumber",required = false) String accountNumber,HttpSession session,
            @RequestParam(value = "uuid") String uuid){
        Map<String,Object> map=userService.adminAdd(uuid);
        UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
        try {
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(), "添加管理账号成功");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo, session);
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }


    /**
     * create by 田扛
     * create time 2019年3月11日11:15:14
     * 获取添加管理员列表接口
     * (数据隔离修改)
     */
    @CheckToken
    @RequestMapping(value = "getAddAdmin")
    @ResponseBody
    public Map<String,Object> getAddAdmin(HttpServletRequest request){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String,Object> map= userService.getAddAdmin(companyId);
        return map;
    }


    @Autowired
    private SessionService sessionService;

    @Autowired
    private CheckUserService checkUserService;

    /**
     *
     * modify by cozi
     * 2019-05-09 10:18
     * 添加认证解绑 赛赋，大白，钉钉
     */

    @RequestMapping(value = "/updateNewUser", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateNewUser(HttpServletResponse response, HttpServletRequest request, UserInfoModel form,TeamUserMapInfo form1,HttpSession session) {
        if (logger.isDebugEnabled()) {
            logger.debug("turn to add on the UserController.updateNewUser, param userform,teamUsermap", new Object[]{form.toString(),form1.toString()});
        }
        String companyId = sessionService.getCompanyUUid(request.getSession());
        Integer Flag = checkUserService.checkUserInfoUpdate(companyId,form.getUuid(), form.getAccountNumber(), form.getMail(), form.getPhoneNumber());
        Map<String, Object> mapFlag = new HashMap<>();
        Map<String, Object> map = new HashMap<>();
        mapFlag.put("return_code",ConstantsCMP.ReturnCode.FAIL);
        if(Flag.equals(1)){
            mapFlag.put("msg","主账号发生重复！");
            return mapFlag;
        }else if(Flag.equals(2)){
            mapFlag.put("msg","邮箱发生重复！");
            return mapFlag;
        }else if(Flag.equals(3)){
            mapFlag.put("msg","手机号发生重复！");
            return mapFlag;
        }else {
            UserInfoDomain domain = userService.getUserByAccountNumber(form.getUuid());
            try {
                if (null != domain) {
                    int i = userService.updateUserTable(form);
                   /* if (i == 1) {
                        redisClient.flushDB();
                    }*/
                    //解除赛赋认证绑定
                    if(null!=form && !StringUtils.isEmpty(form.getUnbindSf())){
                        if(form.getUnbindSf()==0){
                            userService.deleteBindingByUuid("unbindSf", domain.getUuid());
                        }
                    }
                    //解除钉钉认证绑定
                    if(null!=form && !StringUtils.isEmpty(form.getUnbindDd())) {

                        if (form.getUnbindDd() == 0) {
                            userService.deleteBindingByUuid("unbindDd", domain.getUuid());
                        }
                    }
                    //解除大白认证绑定
                    if(null!=form && !StringUtils.isEmpty(form.getUnbindDb())) {
                        if (form.getUnbindDb() == 0) {
                            userService.deleteBindingByUuid("unbindDb", domain.getUuid());
                        }
                    }
                    //解除微信认证绑定
                    if(null!=form && !StringUtils.isEmpty(form.getUnbindWx())) {
                        if (form.getUnbindWx() == 0) {
                            userService.deleteWxAccountMap(domain.getUuid());
                        }
                    }


                    try {
                        //添加管理员日志
                        String userName = ConstantsCMP.getCipherUuidInfo(request);
                        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "更新账号:"+domain.getAccountNumber());
                        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    TeamUserMapInfo teamUserMapInfo = new TeamUserMapInfo();
                    teamUserMapInfo.setUserId(form.getUuid());
                    teamUserMapInfoService.deleteUserMap(teamUserMapInfo);
                    String teamList = form1.getTeamIds();
                    String groupList = form1.getGroupIds();
                    if (org.apache.commons.lang.StringUtils.isNotEmpty(teamList)) {
                        String[] teamIds = teamList.split(",");
                        for (int j = 0; j < teamIds.length; j++) {
                            TeamUserMapInfo domain1 = new TeamUserMapInfo();
                            domain1.setTeamId(Integer.valueOf(teamIds[j]));
                            domain1.setUserId(form.getUuid());
                            teamUserMapInfoService.insertSelective(domain1);
                        }
                    }

                    if (StringUtils.isEmpty(groupList)) {
                        groupList="0";
                    }
                    String[] groupIds = groupList.split(",");
                    if(groupIds.length>1){
                        boolean flag = Arrays.asList(groupIds).contains("0");
                        if(flag==true){
                            map.put("return_msg", "部门选择不能同时包含无部门和有部门，请重新选择");
                            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
                            return map;
                        }
                    }
                    userGroupMapper.deleteUserGroupMap(form.getUuid());
                    for (int k = 0; k < groupIds.length; k++) {
                        GroupUserMapDomain domain2 = new GroupUserMapDomain();
                        domain2.setGroupId(Integer.valueOf(groupIds[k]));
                        domain2.setUserId(form.getUuid());
                        userGroupMapper.insertUserGroupMap(domain2);
                    }
                }

                map.put("return_code", "1");
                map.put("msg", "修改用户成功");
                return map;

            } catch (Exception e) {
                ResponseUtils.customFailueResponse(response, "服务器错误");
                e.printStackTrace();
            }
        }
        return map;
    }


    /**
     * 用户管理-用户管理列表页-添加用户功能-提交用户子账号的
     *
     * @param accountNumber 主账号
     * @return 添加信息
     */
    @RequestMapping(value = "/saveSub")
    @ResponseBody
    public Map<String, Object> addingSub(@RequestParam(value = "accountNumber") String accountNumber,
                                         @RequestParam(value = "appValues") String appValues,HttpSession session,
                                         HttpServletResponse response) {

        Map<String, Object> map = new HashedMap();
        System.out.println("accountNumber:" + accountNumber + "appValues:" + appValues);
        if (logger.isDebugEnabled()) {
            logger.debug("turn to /saveSub on the addingSub accountNumber=[{}],appValues=[{}]",
                    new Object[]{accountNumber, appValues});
        }
        if (!StringUtils.isEmpty(appValues) && appValues.contains("[")) {
            String acount = accountNumber.replaceAll("[\\[\\]]", "");
            subAccountMapService.deleteInfo(acount);
            // 转换方法1
            JSONArray array = JSONArray.fromObject(appValues);
            List<ApplicationModel> list = JSONArray.toList(array, ApplicationModel.class);// 过时方法
            try {
                for (ApplicationModel applicationModel : list) {
                    SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
                    subAccountMapDomain.setAccountNumber(acount);
                    SubAccountDomain subAccountDomain = new SubAccountDomain();
                    subAccountDomain.setAppClientId(applicationModel.getApplicationId());
                    subAccountDomain.setSubAccount(applicationModel.getSubAccount());
                    int result = 0;
                    SubAccountDomain subDomain = subAccountService.getTheSubAccount(applicationModel.getSubAccount(), applicationModel.getApplicationId());
                    if (null != subDomain) {
                        Integer subId = subAccountMapService.selectSubId(subAccountDomain);
                        subAccountMapDomain.setSubId(subId);
                        SubAccountMapDomain domain = subAccountMapService.querySubMap(subAccountMapDomain);
                        if (domain == null) {
                            result = subAccountMapService.insertInfo(subAccountMapDomain);
                        }
                    }
                }

            } catch (Exception e) {
                map.put("return_code", ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
                map.put("return_msg", "服务器错误");
                e.printStackTrace();
            }
        }
        map.put("return_code", ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
        map.put("return_msg", "操作成功");
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SUBACCOUNT_MAINTENANCE.getType(),  "更新子账号信息");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }



}
