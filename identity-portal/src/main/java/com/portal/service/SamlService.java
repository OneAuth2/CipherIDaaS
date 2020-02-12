package com.portal.service;

import com.portal.saml.entity.SamlEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TODO:
 * create by shizhao at 2019-12-12
 *
 * @author: shizhao
 * @since: 2019-12-12 10:57
 */
public interface SamlService {

    /**
     * 判断saml应用是否在本地添加
     * @param samlRequest
     * @return
     */
    public Boolean isSamlRegistInLocal(HttpServletRequest request,String samlRequest);

    /**
     * 发送Idp的认证信息
     * @param samlEntity
     */
    Boolean sendIdentityInfo(SamlEntity samlEntity);
}
