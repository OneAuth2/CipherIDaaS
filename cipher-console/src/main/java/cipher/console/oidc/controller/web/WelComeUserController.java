package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.domain.web.WifiPortalWebInfo;
import cipher.console.oidc.model.UserManagementModel;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.service.WelcomeUserService;
import cipher.console.oidc.token.CheckToken;
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
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: zt
 * 从首页点击查看根据不同的条件展示用户列表
 * @Date: 2018/6/7 14:09
 */
@Controller
@RequestMapping(value = "/cipher/welcomeuser")
@EnableAutoConfiguration
public class WelComeUserController {

    @Autowired
    private UserService userService;

    @Autowired
    private WelcomeUserService welcomeUserService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    private static final Logger LOGGER= LoggerFactory.getLogger(WelComeUserController.class);




    @RequestMapping(value = "/listPage",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> listPage(@RequestParam(value = "queryType") String queryType){
        if(org.apache.commons.lang.StringUtils.isEmpty(String.valueOf(queryType))){
            return   ReturnUtils.failureResponse("参数为空");
        }
        return ReturnUtils.successResponse("queryType", queryType);
    }






    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> pageList(HttpServletResponse response,
                                        DataGridModel pageModel,
                                        UserManagementModel form,
                                        @RequestParam(value = "queryType") int queryType1) {

        LOGGER.debug("enter WelComeUserController.pageList;queryType+"+queryType1);
        String queryType = String.valueOf(queryType1);
        //1.在线用户
        if (UserQueryTypeConstants.ONLINE_USER.equals(queryType)) {
            //所有在线用户的key集合
            Set<String> accountKeyList = redisTemplate.keys(OnlineUserConstants.UNIQUE_PRE + "*");
            List<String> nameList = queryOnlineAccountList(accountKeyList);
            if(CollectionUtils.isEmpty(nameList)){
                return null;
            }
            return userService.queryWelcomeUserPageList(nameList, form, pageModel);
        }

        //2.未授权用户
        if (UserQueryTypeConstants.NOT_AUH_USER.equals(queryType)) {
            List<String> accontNumberList = userService.queryNotAuthAccount();
            return userService.queryWelcomeUserPageList(accontNumberList, form, pageModel);

        }

        //3.临时授权已过期账号
        if (UserQueryTypeConstants.TMP_ACCOUNT_OUT_OF_DATE.equals(queryType)) {
            List<String> nameList = userService.queryAuthOutOfDate();
            return userService.queryWelcomeUserPageList(nameList, form, pageModel);
        }

        //4.授权过期
        if (UserQueryTypeConstants.AUTH_OUT_OF_DATE.equals(queryType)) {
            List<String> nameList = userService.queryAccountAuthOutOfDate();
            return userService.queryWelcomeUserPageList(nameList, form, pageModel);
        }

        //5.异常地点登录
        if (UserQueryTypeConstants.UNUSUAL_LOGIN_ADRESS.equals(queryType)) {

        }

        return null;
    }

    /**
     * 根据在线用户的key列表，查询出所有在线用户的用户名列表
     *
     * @param keyList
     * @return
     */
    private List<String> queryOnlineAccountList(Set<String> keyList) {
        List<String> nameList = new ArrayList<>();
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        for (String key : keyList) {
            nameList.add(valueOperations.get(key));
        }
        return nameList;
    }






    @RequestMapping(value = "/newListPage",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> newListPage(@RequestParam(value = "queryType") String queryType,
                                           @RequestParam(value = "groupId",required = false) String groupId){
        if(org.apache.commons.lang.StringUtils.isEmpty(String.valueOf(queryType))||StringUtils.isEmpty(groupId)){
            return   ReturnUtils.failureResponse("参数为空");
        }
        Map<String, Object> resMap = new HashMap<>();
        resMap.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode());
        resMap.put(GlobalReturnCode.RETURN_MSG, GlobalReturnCode.MsgCodeEnum.SUCCESS.getMsg());
        resMap.put("queryType", queryType);
        resMap.put("groupId", groupId);
        return resMap;
    }


    /*
    *首页用户信息列表
    * （数据隔离修改）
    *
    * */
    @CheckToken
    @RequestMapping(value = "/newList", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> newList(DataGridModel pageModel,
                                       NewUserInfo newUserInfo, HttpServletRequest request,
                                       @RequestParam(value = "queryType") int queryType
     ) {

        //1.无部门用户
        if(newUserInfo!=null && !StringUtils.isEmpty(newUserInfo.getStatus())){
            if(newUserInfo.getStatus().equals(String.valueOf(ConstantsCMP.UserStatus.START))){
                newUserInfo.setStatus("启用");
            }else if(newUserInfo.getStatus().equals(String.valueOf(ConstantsCMP.UserStatus.STOP))){
                newUserInfo.setStatus("停用");
            }
        }
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        newUserInfo.setCompanyId(companyId);
        if (ConstantsCMP.WelcomeConstant.NO_GROUP == queryType) {
            return  welcomeUserService.getNoGroupUserList(newUserInfo,pageModel);
        }
        //2.锁定用户
        if (ConstantsCMP.WelcomeConstant.LOCK_NUMBER==queryType) {
            return   welcomeUserService.getLockUserList(newUserInfo,pageModel);

        }
        //3.已创建30天并且30天未登录用户
        if (ConstantsCMP.WelcomeConstant.NOT_LOGIN ==queryType) {
            return  welcomeUserService.getNoLoginUserList(newUserInfo,pageModel);
        }
        return null;
    }






}
