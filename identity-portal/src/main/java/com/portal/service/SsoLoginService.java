package com.portal.service;

import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;

/**
 * TODO:
 * create by liuying at 2019/12/10
 *
 * @author liuying
 * @since 2019/12/10 14:16
 */
public interface SsoLoginService {

    /**
     * 判断应用信息
     *
     */
    public void sendBaseInfo(String serviceUrl,String uuid,String companyUuid,String type);



    /**
     * 应用重定向地址
     */
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo);





}
