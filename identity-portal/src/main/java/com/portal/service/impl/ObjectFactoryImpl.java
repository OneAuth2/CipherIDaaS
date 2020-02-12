package com.portal.service.impl;



import com.cipher.china.channel.pojo.AuthStrategy;
import com.portal.bto.PortalAppBto;
import com.portal.commons.ConstantType;
import com.portal.commons.GlobalAuthType;
import com.portal.domain.*;
import com.portal.model.GroupApplicationModel;
import com.portal.service.ObjectFactoryService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;


/**
 * @Author: TK
 * @Date: 2019/5/7 10:04
 */
@Service
public class ObjectFactoryImpl implements ObjectFactoryService {

    @Override
    public RegisterApprovalDomain userCreate(String companyUuid, String accountNumber, String phoneNumber, String mail) {
        RegisterApprovalDomain registerApprovalDomain = new RegisterApprovalDomain();
        registerApprovalDomain.setUuid(getUuid());
        registerApprovalDomain.setCompanyUuid(companyUuid);
        registerApprovalDomain.setPhoneNumber(phoneNumber);
        registerApprovalDomain.setUserEmail(mail);
        registerApprovalDomain.setUserName(accountNumber);
        registerApprovalDomain.setStatus(3);
        return registerApprovalDomain;
    }

    @Override
    public AuthStrategy authStrategyCreate(IdentityAuthentication identityAuthentication, GlobalAuthType authMode) {
        //  新建认证方式对象
        AuthStrategy authStrategy = new AuthStrategy();

        // 判断大白认证是否开启
        if (identityAuthentication.getOtherAuthDb() == ConstantType.ON) {
            authStrategy.setDabaiScan(ConstantType.AUTH_ON);
        }

        //判断赛赋认证是否开启
        if (identityAuthentication.getOtherAuthSf() == ConstantType.ON) {
            authStrategy.setSaifuScan(ConstantType.AUTH_ON);
        }

        //判断钉钉认证是否开启
        if (identityAuthentication.getOtherAuthDd() == ConstantType.ON) {
            authStrategy.setDingdingScan(ConstantType.AUTH_ON);
        }

        //判断手机认证是否开启
        if (identityAuthentication.getOtherAuthNum() == ConstantType.ON) {
            authStrategy.setPhoneRandomCode(ConstantType.AUTH_ON);
        }

        //判断totp动态验证码是否开启
        if (identityAuthentication.getOtherAuthDt() == ConstantType.ON){
            authStrategy.setTotpAuth(ConstantType.AUTH_ON);
        }

        //判断AD认证是否开启
        if (authMode.getCode() == ConstantType.AD_AUTH) {
            authStrategy.setAdAuth(ConstantType.AUTH_ON);
        }

        //判断本地认证是否开启
        if (authMode.getCode() == ConstantType.LOCAL_AUTH) {
            authStrategy.setLocalAuth(ConstantType.AUTH_ON);
        }

        //判断外部认证是否开启
        if (authMode.getCode() == ConstantType.OUTSIDE_AUTH) {
            authStrategy.setErpAuth(ConstantType.AUTH_ON);
        }





        return authStrategy;
    }

    @Override
    public AuthStrategy authStrategyCreate(IdentityAuthentication identityAuthentication) {
        //  新建认证方式对象

        AuthStrategy authStrategy = new AuthStrategy();


        // 判断大白认证是否开启

        if (identityAuthentication.getOtherAuthDb() == ConstantType.ON) {
            authStrategy.setDabaiScan(ConstantType.AUTH_ON);
        }

        //判断赛赋认证是否开启
        if (identityAuthentication.getOtherAuthSf() == ConstantType.ON) {
            authStrategy.setSaifuScan(ConstantType.AUTH_ON);
        }

        //判断钉钉认证是否开启
        if (identityAuthentication.getOtherAuthDd() == ConstantType.ON) {
            authStrategy.setDingdingScan(ConstantType.AUTH_ON);
        }

        //判断手机认证是否开启
        if (identityAuthentication.getOtherAuthNum() == ConstantType.ON) {
            authStrategy.setPhoneRandomCode(ConstantType.AUTH_ON);
        }
       //判断微信认证是否开启

        if (identityAuthentication.getOtherAuthWx() == ConstantType.ON) {
            authStrategy.setWeixinScan(ConstantType.AUTH_ON);
        }
        return authStrategy;
    }

    @Override
    public UserLoginRecInfo userLoginRecServiceCreate(String uuid) {
        //入参校验
        if (StringUtils.isBlank(uuid)){
            return null;
        }
        UserLoginRecInfo userLoginRecInfo=new UserLoginRecInfo();
        userLoginRecInfo.setUserId(uuid);
        return userLoginRecInfo;
    }

    @Override
    public Map<String, Object> getMap(Map<String, Object> map, IdentityAuthTypeDomain identityAuthTypeDomain) {


        return null;
    }

    @Override
    public BindingDingInfoDomain getBindingDingInfoDomain(String unionId, String openId, String userId, UserInfoDomain userInfoDomain) {

        //构建对象转换
        BindingDingInfoDomain bindingDingInfoDomain=new BindingDingInfoDomain();
        bindingDingInfoDomain.setDingUserId(userId);
        bindingDingInfoDomain.setOpenId(openId);
        bindingDingInfoDomain.setUnionId(unionId);
        bindingDingInfoDomain.setUserId(userInfoDomain.getUuid());

        return  bindingDingInfoDomain;
    }

    @Override
    public DaBaiBindingInfoDomain getBindingDaBaiInfoDomain(String daBaiId, UserInfoDomain user,String companyUUid) {
        //入参校验
        if (StringUtils.isBlank(daBaiId)){
            return null;
        }
        DaBaiBindingInfoDomain daBaiBindingInfoDomain=new DaBaiBindingInfoDomain();
        daBaiBindingInfoDomain.setCompanyId(companyUUid);
        daBaiBindingInfoDomain.setLinkAccount(daBaiId);
        daBaiBindingInfoDomain.setUserId(user.getUuid());
        return daBaiBindingInfoDomain;
    }

    @Override
    public SaiFuBindingInfoDomain getSaiFuBindingInfoDomain(String saifuId, UserInfoDomain user, String companyUUid) {
        //入参校验
        if (StringUtils.isBlank(saifuId)){
            return null;
        }
        SaiFuBindingInfoDomain saiFuBindingInfoDomain=new SaiFuBindingInfoDomain();
        saiFuBindingInfoDomain.setCompanyId(companyUUid);
        saiFuBindingInfoDomain.setPlatId(saifuId);
        saiFuBindingInfoDomain.setUserId(user.getUuid());
        return saiFuBindingInfoDomain;
    }

    @Override
    public AuthStrategy authStrategyCreateByAuthMode(GlobalAuthType authMode) {

        //  新建认证方式对象

        AuthStrategy authStrategy = new AuthStrategy();
        //判断AD认证是否开启
        if (authMode.getCode() == ConstantType.AD_AUTH) {
            authStrategy.setAdAuth(ConstantType.AUTH_ON);
        }

        //判断本地认证是否开启
        if (authMode.getCode() == ConstantType.LOCAL_AUTH) {
            authStrategy.setLocalAuth(ConstantType.AUTH_ON);
        }

        //判断外部认证是否开启
        if (authMode.getCode() == ConstantType.OUTSIDE_AUTH) {
            authStrategy.setErpAuth(ConstantType.AUTH_ON);
        }

        return authStrategy;
    }

    public String getUuid() {
        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
    }



    public  List<PortalAppBto> object2tBrandList(List<GroupApplicationModel> applist) {
        List<PortalAppBto> portalAppBtoList = new ArrayList<PortalAppBto>();
        if (applist != null && !applist.isEmpty()) {
            for (GroupApplicationModel groupApplicationModel : applist) {
                PortalAppBto portalAppBto = new PortalAppBto();
                portalAppBto.setApplicationUrl(groupApplicationModel.getApplicationUrl());
                portalAppBto.setApplicationIcon(groupApplicationModel.getImgUrl());
                portalAppBto.setApplicationName(groupApplicationModel.getApplicationName());
                portalAppBtoList.add(portalAppBto);
            }
        }
        return portalAppBtoList;
    }
}
