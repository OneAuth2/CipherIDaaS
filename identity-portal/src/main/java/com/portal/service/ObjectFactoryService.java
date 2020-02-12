package com.portal.service;




import com.cipher.china.channel.pojo.AuthStrategy;
import com.portal.bto.PortalAppBto;
import com.portal.commons.GlobalAuthType;
import com.portal.domain.*;
import com.portal.model.GroupApplicationModel;

import java.util.List;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/5/7 10:04
 */
public interface ObjectFactoryService {

    /**
     * 构建 注册用户实体类
     * @param companyUuid
     * @param accountNumber
     * @param phoneNumber
     * @param mail
     * @return
     */
    public RegisterApprovalDomain userCreate(String companyUuid, String accountNumber, String phoneNumber, String mail);

    /**
     * 根据认证方式创建认证对象
     * @param identityAuthentication
     * @param authMode
     * @return
     */
    public AuthStrategy authStrategyCreate(IdentityAuthentication identityAuthentication, GlobalAuthType authMode);

    /**
     * 根据认证方式创建认证对象
     * @param identityAuthentication
     * @return
     */
    public AuthStrategy  authStrategyCreate(IdentityAuthentication identityAuthentication);


    /**
     * 构建用户首次登陆对象
     * @param uuid
     * @return
     */
    public UserLoginRecInfo userLoginRecServiceCreate(String uuid);

    Map<String ,Object> getMap(Map<String, Object> map, IdentityAuthTypeDomain identityAuthTypeDomain);

    BindingDingInfoDomain getBindingDingInfoDomain(String unionId, String openId, String userId, UserInfoDomain userInfoDomain);

    /**
     * 构建大白绑定对象
     * @param daiBaiId
     * @param user
     * @param companyUUId
     * @return
     */
    DaBaiBindingInfoDomain getBindingDaBaiInfoDomain(String daiBaiId, UserInfoDomain user ,String companyUUId);

    /**
     * 构建赛赋绑定对象
     * @param saifuId
     * @param user
     * @param companyUUid
     * @return
     */
    SaiFuBindingInfoDomain getSaiFuBindingInfoDomain(String saifuId, UserInfoDomain user, String companyUUid);

    AuthStrategy authStrategyCreateByAuthMode(GlobalAuthType authMode);


    public List<PortalAppBto> object2tBrandList(List<GroupApplicationModel> applist);
}
