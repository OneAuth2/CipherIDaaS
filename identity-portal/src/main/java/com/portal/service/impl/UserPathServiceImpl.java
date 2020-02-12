package com.portal.service.impl;


import com.alibaba.dubbo.common.URL;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.portal.commons.ReturnCode;
import com.portal.daoAuthoriza.UserPathDAO;
import com.portal.domain.*;
import com.portal.service.*;
import com.portal.utils.aes.CsAppAESUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.portal.enums.ResultCode.*;
import static com.portal.enums.ResultCode.SUCCESS;

import com.portal.daoAuthoriza.UserPathDAO;
import com.portal.domain.UserPathInfo;
import com.portal.service.UserPathService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO:
 * create by liuying at 2019/9/23
 *
 * @author liuying
 * @since 2019/9/23 9:59
 */
@Service
public class UserPathServiceImpl implements UserPathService {

    @Autowired
    private  UserPathDAO  userPathDAO;

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private PortalService portalService;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private CsTypeService csTypeService;






    @Override
    public UserPathInfo getUserPathInfo(UserPathInfo userPathInfo) {
        return userPathDAO.getUserPathInfo(userPathInfo);
    }

    @Override
    public void insertUserPahtInfo(UserPathInfo userPathInfo) {
        userPathDAO.insertUserPahtInfo(userPathInfo);
    }

    @Override
    public void updateUserPahtInfo(UserPathInfo userPathInfo) {
        userPathDAO.updateUserPathInfo(userPathInfo);
    }

    @Override
    public Map<String, Object> getCsDate(String userId, String applyId) {
        Map<String,Object> map=new HashMap<>();

        PortalApplyInfo applyInfo = new PortalApplyInfo();
        applyInfo.setClientId(applyId);
        PortalApplyInfo portalApplyInfo=portalService.selectPortalInfo(new PortalApplyInfo(applyId));
        if (null == portalApplyInfo) {
            return serviceController.sendBaseErrorMap(APPLICATION_NO_EXIT);

        }

        if (null == portalApplyInfo.getAssociatedAccount() || portalApplyInfo.getAssociatedAccount().isEmpty()) {
            return serviceController.sendBaseErrorMap(APPLICATION_RULE_NOT_EXIT);
        }

        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(userId);
        if (null == userInfoDomain) {
            return serviceController.sendBaseErrorMap(USER_IS_NOT_EXIST);
        }

        String subName= subAccountRuleService.getSubAccountRule(userInfoDomain, portalApplyInfo.getAssociatedAccount(),applyId);

        if(StringUtils.isEmpty(subName)){
            return  serviceController.sendBaseErrorMap(USER_NO_SUB);
        }

        if(StringUtils.isNotEmpty(subName)&&subName.equals(ReturnCode.SUB_RULE_ERROR)){
            return serviceController.sendBaseErrorMap(SUB_NAME_RULE_ERROR);
        }

        String subPwd= subAccountRuleService.getSubPwdRule(userId, portalApplyInfo.getAssociatedAccount(), applyId);

        if(StringUtils.isEmpty(subPwd)){
            return  serviceController.sendBaseErrorMap(USER_NO_SUBPWD);
        }


        if(StringUtils.isNotEmpty(subPwd)&&subPwd.equals(ReturnCode.SUB_RULE_ERROR)){
            return  serviceController.sendBaseErrorMap(SUB_NAME_RULE_ERROE);
        }

        String json=csTypeService.getCsTypeJson(userId,subName,subPwd,portalApplyInfo);
        if(StringUtils.isEmpty(json)){
            return  serviceController.sendBaseErrorMap(APP_INFO_LOST);
        }

        System.out.println(json);
        String data="";
        try {
            data= CsAppAESUtil.encode(json, CsAppAESUtil.key);
        } catch (Exception e) {
            e.printStackTrace();
        }
        map.put("data",data);
        return serviceController.sendTheMap(map,SUCCESS.getCode(),SUCCESS.getMessage());
    }




}
