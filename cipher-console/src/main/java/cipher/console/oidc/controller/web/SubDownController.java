package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.AppMapper;
import cipher.console.oidc.mapper.PasswordAuthorizationMapper;
import cipher.console.oidc.mapper.SubAccountMapper;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.AppService;
import cipher.console.oidc.service.SubDownService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.aes.AES;
import cipher.console.oidc.util.aes.AesKey;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/subdown")
@EnableAutoConfiguration
public class SubDownController {

    @Autowired
    private AppService appService;

    @Autowired
    private UserService userService;


    @Autowired
    private SubDownService subDownService;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private SubAccountMapper subAccountMapper;

    @Autowired
    private PasswordAuthorizationMapper passwordAuthorizationMapper;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    private static final Logger logger = LoggerFactory.getLogger(SubDownController.class);

    /*
    * 单个应用下个子账号
    * (数据隔离)
    * */
   // @CheckToken
    @RequestMapping(value = "/dowmSingleSubAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> dowmSingleSubAccount(SubAccountInfoDomain subAccountInfoDomain,HttpSession session ) {
        Map<String, Object> map = new HashMap<>();
        if (null != subAccountInfoDomain) {
            AppSonAccountDomain appSonAccountDomain = new AppSonAccountDomain();
            UserInfoDomain userInfoDomain = userService.getUserByAccountNumber(subAccountInfoDomain.getUuid());
            ApplicationInfoDomain applicationInfoDomain=appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
            if(null==applicationInfoDomain){
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "应用不存在");
                return map;
            }
            //查看从账号是否与主账号一致1：一致；0：不一致
            /*   if(applicationInfoDomain.getIsSameAccount()==1){
                //单个配置从账号，如果配置的从账号与主账号不一致，返回失败
                PasswordAuthorizationMagDomain passwordAuthorizationMagDomain=passwordAuthorizationMapper.selectPasswordAuthorizationMagDomain(userInfoDomain.getUuid());
                if(null==passwordAuthorizationMagDomain){
                    map.put("return_code", ConstantsCMP.Code.FAIL);
                    map.put("return_msg", "应用已配置从账号与主账号一致规则,用户密码不存在");
                    return map;
                }
                String password= AES.decryptFromBase64(passwordAuthorizationMagDomain.getPassword(), AesKey.AES_KEY);
                if(!subAccountInfoDomain.getSubAccount().equals(userInfoDomain.getAccountNumber())||!subAccountInfoDomain.getPassword().equals(password)){
                    map.put("return_code", ConstantsCMP.Code.FAIL);
                    map.put("return_msg", "应用已配置从账号与主账号一致规则");
                    return map;
                }
            }

            if (null != userInfoDomain) {
                appSonAccountDomain.setAccountNumber(subAccountInfoDomain.getAccountNumber());
                appSonAccountDomain.setUserName(userInfoDomain.getUserName());
                appSonAccountDomain.setGender(userInfoDomain.getGender());
                appSonAccountDomain.setJob(userInfoDomain.getJob());
                appSonAccountDomain.setJobNo(userInfoDomain.getJobNo());
                appSonAccountDomain.setNickName(userInfoDomain.getNickname());
                appSonAccountDomain.setMail(userInfoDomain.getMail());
                appSonAccountDomain.setPhoneNumber(userInfoDomain.getPhoneNumber());
                appSonAccountDomain.setGroupName(userInfoDomain.getGroupName());
                appSonAccountDomain.setPassword(subAccountInfoDomain.getPassword());
                appSonAccountDomain.setSonAccountNumber(subAccountInfoDomain.getSubAccount());
                appSonAccountDomain.setUserId(subAccountInfoDomain.getUuid());
            }
            List<AppSonAccountDomain> userList=new ArrayList<>();
            userList.add(appSonAccountDomain);*/
          //  map = subDownService.getSubDownInfo(subAccountInfoDomain.getAppClientId(), userList);
            subAccountInfoDomain.setUserId(subAccountInfoDomain.getUuid());
            map=subDownService.doSaveSubAccount(subAccountInfoDomain);
            JSONObject jsonObject = JSONObject.fromObject(map);
            int returnCode = jsonObject.getInt("return_code");
            if(returnCode==0){
                map.put("successed", 1);
                map.put("fail", 0);
            }else {
                map.put("successed", 0);
                map.put("fail", 1);
            }
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SUBACCOUNT_MAINTENANCE.getType(),  "添加或修改子账号成功");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }


      /*
       * 全部下发子账号数量
       * */
    @RequestMapping(value = "/downAllsubAccountNum", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> downAllsubAccountNum(SubAccountInfoDomain subAccountInfoDomain) {
        Map<String, Object> map = new HashMap<>();
        List<AppSonAccountDomain> userList = new ArrayList<>();
        AppSonAccountDomain appSonAccountDomaim = new AppSonAccountDomain();
        ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
        if (null != applicationInfoDomain) {
            appSonAccountDomaim.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
            userList = appMapper.getSubAccountList(appSonAccountDomaim);
            /*if (null != userList && userList.size() > 0) {
                for (AppSonAccountDomain domain : userList) {
                    SubAccountDomain subAccountDomain = subAccountMapper.querySubAccountInfo(domain.getAccountNumber(), domain.getApplicationId());
                    if (subAccountDomain == null) {
                        list.add(domain);
                    }
                }
            }*/
        }
        map.put("return_code", ConstantsCMP.Code.SUCCESS);
        map.put("num", userList.size());
        return map;

    }



      /**
       * 全部下发子账号
       * */
    @RequestMapping(value = "/downAllsubAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> downAllsubAccount(SubAccountInfoDomain subAccountInfoDomain,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        List<AppSonAccountDomain> list = new ArrayList<>();
        AppSonAccountDomain appSonAccountDomaim = new AppSonAccountDomain();
        ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
        if (null != applicationInfoDomain) {
            appSonAccountDomaim.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
            List<AppSonAccountDomain> userList = appMapper.getSubAccountList(appSonAccountDomaim);
            if (null != userList && userList.size() > 0) {
                for (AppSonAccountDomain domain : userList) {
                    SubAccountDomain subAccountDomain = subAccountMapper.querySubAccountInfo(domain.getUuid(), domain.getApplicationId());
                    if (subAccountDomain == null) {
                        list.add(domain);
                    }
                }
            }
            if(list.size()>0) {
                map = subDownService.getSubDownInfo(subAccountInfoDomain.getAppClientId(), list);
            }else {
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "同步配置失败，从账号已同步");
            }
        }

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SUBACCOUNT_MAINTENANCE.getType(),  "全部下发子账号");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        return map;


    }

    /**
     * 部分下发子账号数量
     * */
    @RequestMapping(value = "/downSelectsubAccountNum", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> downSelectsubAccountNum(SubAccountInfoDomain subAccountInfoDomain) {
        Map<String, Object> map = new HashMap<>();
        List<AppSonAccountDomain> list = new ArrayList<>();
       if (StringUtils.isNotEmpty(subAccountInfoDomain.getUserIds())) {
           String[] userIds = subAccountInfoDomain.getUserIds().split(",");
           /* AppSonAccountDomain appSonAccountDomaim = new AppSonAccountDomain();
            ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
            if (null != applicationInfoDomain) {
                appSonAccountDomaim.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
                List<AppSonAccountDomain> userList = appMapper.getSubAccountList(appSonAccountDomaim);
                for (AppSonAccountDomain appSonAccountDomain : userList) {
                    for (int i = 0; i < userIds.length; i++) {
                        if (appSonAccountDomain.getAccountNumber().equals(userIds[i])) {
                            SubAccountDomain subAccountDomain = subAccountMapper.querySubAccountInfo(userIds[i], subAccountInfoDomain.getAppClientId());
                            if (subAccountDomain == null) {
                                list.add(appSonAccountDomain);
                            }
                        }
                    }
                }
            }*/
           map.put("num", userIds.length);
       }else {
           map.put("num", 0);
       }
        map.put("user", list);
        map.put("return_code", ConstantsCMP.Code.SUCCESS);

        return map;

    }


     /*
      * 部分下发子账号
      * */
    @RequestMapping(value = "/downSelectsubAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> downSelectsubAccount(SubAccountInfoDomain subAccountInfoDomain,HttpSession session) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(subAccountInfoDomain.getUserIds())) {
            String[] userIds = subAccountInfoDomain.getUserIds().split(",");
            AppSonAccountDomain appSonAccountDomaim = new AppSonAccountDomain();
            ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
            List<AppSonAccountDomain> list = new ArrayList<>();
            if (null != applicationInfoDomain) {
                appSonAccountDomaim.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
                //查询应用授权的所有用户的信息
                List<AppSonAccountDomain> userList = appMapper.getSubAccountList(appSonAccountDomaim);
                if(null!=userList&&userList.size()>0) {
                    for (AppSonAccountDomain appSonAccountDomain : userList) {
                        for (int i = 0; i < userIds.length; i++) {
                            if (appSonAccountDomain.getUuid().equals(userIds[i])) {
                                SubAccountDomain subAccountDomain = subAccountMapper.querySubAccountInfo(userIds[i], subAccountInfoDomain.getAppClientId());
                                if (subAccountDomain == null) {
                                    list.add(appSonAccountDomain);
                                }
                            }
                        }
                    }
                }
                if(list.size()>0){
                    map = subDownService.getSubDownInfo(subAccountInfoDomain.getAppClientId(), list);
                }else {

                    map.put("return_code", ConstantsCMP.Code.FAIL);
                    map.put("return_msg", "同步配置失败，从账号已同步");
                }
            }
        }else {
            map.put("return_code",ConstantsCMP.Code.FAIL);
            map.put("return_msg","配置失败，账号信息不存在");
        }
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SUBACCOUNT_MAINTENANCE.getType(),  "下发部分子账号");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        return map;

    }


    /*
   * 子账号推荐功能
   * 1.5.5
   * 更改为回显从账号
   * */
    @RequestMapping(value = "/recomendSubAccount", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> recomendSubAccount(SubAccountInfoDomain subAccountInfoDomain) {
        Map<String, Object> map = new HashMap<>();
        if(subAccountInfoDomain!=null){
            if(StringUtils.isNotEmpty(subAccountInfoDomain.getUuid())&&StringUtils.isNotEmpty(subAccountInfoDomain.getAppClientId())){
                String userName = subAccountMapper.selectSubByuuidAndAppId(subAccountInfoDomain.getUuid(), subAccountInfoDomain.getAppClientId());
                map.put("userName",userName);
            }else {
                map.put("userName","");
            }
        }
        //UserInfoDomain userInfoDomain = userService.getUserByAccountNumber(subAccountInfoDomain.getUuid());
        /*if(null==userInfoDomain){
            map.put("return_code",ConstantsCMP.ReturnCode.FAIL);
            map.put("return_msg","用户不存在");
            return map;
        }
        if (null != subAccountInfoDomain) {
            ApplicationInfoDomain applicationInfoDomain = appMapper.queryApplication(subAccountInfoDomain.getAppClientId());
            if (null != applicationInfoDomain&&StringUtils.isNotEmpty(applicationInfoDomain.getAccountType())) {
                    Integer ruleType = Integer.valueOf(applicationInfoDomain.getAccountType());
                    switch (ruleType) {
                        case 1:
                            map.put("userName", userInfoDomain.getAccountNumber());
                            break;
                        case 2:
                            map.put("userName", userInfoDomain.getPhoneNumber());
                            break;
                        case 3:
                            map.put("userName", userInfoDomain.getMail());
                            break;
                        case 4:
                            String mail = userInfoDomain.getMail().substring(0, userInfoDomain.getMail().indexOf("@"));
                            map.put("userName", mail);
                            break;
                        default:
                            break;
                    }
                }
            }*/

        map.put("return_code",ConstantsCMP.ReturnCode.SUCCESS);
        return map;
    }
}
