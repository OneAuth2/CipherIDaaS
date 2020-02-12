package com.portal.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.portal.commons.CsType;
import com.portal.daoAuthoriza.PortalDAO;
import com.portal.domain.*;
import com.portal.service.CsTypeService;
import com.portal.service.PortalService;
import com.portal.service.UserPathService;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO:
 * create by liuying at 2019/9/28
 *
 * @author liuying
 * @since 2019/9/28 15:37
 */
@Service
public class CsTypeServiceImpl implements CsTypeService {

    @Autowired
    private PortalDAO portalDAO;

    @Autowired
    private UserPathService userPathService;


    @Override
    public String getCsTypeJson(String userId,String subName, String subPwd, PortalApplyInfo portalApplyInfo) {
        String csType=portalDAO.getSystemType(portalApplyInfo.getAppSysId());
        if(StringUtils.isEmpty(csType)){
            return null;
        }
        String json="";
        if(csType.equals(CsType.JING_DIE)){
            json=getJinDieInfo(userId,subName,subPwd,portalApplyInfo);
        }
        return json;
    }




    public String  getJinDieInfo(String userId,String subName,String subPwd,PortalApplyInfo portalApplyInfo){

        CsApplicationInfo csApplicationInfo=new CsApplicationInfo(portalApplyInfo);
        csApplicationInfo.getPath().replace("\\","\r");


        CsAppInfo csAppInfo=new CsAppInfo();
        UserPathInfo userPathInfo=new UserPathInfo();
        userPathInfo.setUserId(userId);
        userPathInfo.setAppId(portalApplyInfo.getClientId());
        userPathInfo=userPathService.getUserPathInfo(userPathInfo);
        if(null!=userPathInfo&& StringUtils.isNotEmpty(userPathInfo.getPath())){
            csAppInfo.setDefaultPath(userPathInfo.getPath());
        }else {
            csAppInfo.setDefaultPath("");
        }

        net.sf.json.JSONObject jsonobject = net.sf.json.JSONObject.fromObject(portalApplyInfo.getConfigInfo());
        CsParamInfo csParamInfo = (CsParamInfo) net.sf.json.JSONObject.toBean(jsonobject, CsParamInfo.class);
        csAppInfo.setRelativePath(portalApplyInfo.getApplyUrl());
        csAppInfo.setNotFoundMsg(csParamInfo.getNotFoundMsg());
        String notFoundUrl="";
        try {
            notFoundUrl= URLEncoder.encode(csParamInfo.getNotFoundUrl(), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        csAppInfo.setNotFoundUrl(notFoundUrl);
        csAppInfo.setRegisterTitle(csParamInfo.getRegisterTitle());

        csAppInfo.setType(0);
        csAppInfo.setLoginWindow(new CsAppInfo.LoginWindow(csParamInfo.getLoginWindowTitle(),csParamInfo.getLoginWindowType()));
        csAppInfo.setLoginedWindow(new CsAppInfo.LoginedWindow(csParamInfo.getLoginedWindowTitle(),csParamInfo.getLoginedWindowType()));
        CsAppInfo.Actions actions=new CsAppInfo.Actions();
        List<CsAppInfo.Success> successList=new ArrayList();
        CsAppInfo.Success success1=new CsAppInfo.Success();
        success1.setType(0);
        success1.setnId(9);
        success1.setValue(subName);
        successList.add(success1);

        CsAppInfo.Success success2=new CsAppInfo.Success();
        success2.setType(0);
        success2.setnId(10);
        success2.setValue(subPwd);
        successList.add(success2);

        CsAppInfo.Success success3=new CsAppInfo.Success();
        success3.setType(1);
        success3.setnId(8);
        successList.add(success3);

        List<CsAppInfo.Fail> failList=new ArrayList();
        CsAppInfo.Fail fail1=new CsAppInfo.Fail();
        fail1.setType(2);
        fail1.setnId(7);
        fail1.setSleep(1000);
        fail1.setNeedRefresh(1);
        failList.add(fail1);

        CsAppInfo.Fail fail2=new CsAppInfo.Fail();
        fail2.setType(0);
        fail2.setValue(subName);
        fail2.setnId(9);
        failList.add(fail2);

        CsAppInfo.Fail fail3=new CsAppInfo.Fail();
        fail3.setType(0);
        fail3.setnId(10);
        fail3.setValue(subPwd);
        failList.add(fail3);

        CsAppInfo.Fail fail4=new CsAppInfo.Fail();
        fail4.setType(3);
        fail4.setnId(12);
        fail4.setValue(portalApplyInfo.getServer());
        fail4.setSleep(2000);
        fail4.setSub(1);
        failList.add(fail4);

        CsAppInfo.Fail fail5=new CsAppInfo.Fail();
        fail5.setType(4);
        fail5.setnId(0);
        fail5.setCount(1);
        failList.add(fail5);

        CsAppInfo.Fail fail6=new CsAppInfo.Fail();
        fail6.setType(1);
        fail6.setnId(0);
        failList.add(fail6);

        actions.setFail(failList);
        actions.setSuccess(successList);
        Map<String,Object> request=new HashMap<>();
        request.put(csParamInfo.getRequestId(),csParamInfo.getRequestValue());
        JSONObject requestJson = new JSONObject(request);
        actions.setRequest(requestJson);
        csAppInfo.setActions(actions);

        Gson gson=new Gson();
        String json=gson.toJson(csAppInfo);
        return json;



    }




}
