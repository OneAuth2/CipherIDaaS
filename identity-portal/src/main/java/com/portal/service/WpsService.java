package com.portal.service;

import com.portal.domain.WpsCommonReqDomin;
import com.portal.domain.WpsQueryUserListDomain;
import com.portal.domain.WpsUserDomain;

import java.util.List;
import java.util.Map;

/**
 * 金山云协作文档
 *
 * @Author: zt
 * @Date: 2018/12/7 14:10
 */
public interface WpsService {

    /**
     * @param wpsCommonReqDomin appid不需要
     * @param userId   用户信息
     */
    public String createSession(WpsCommonReqDomin wpsCommonReqDomin, String userId);

    /**
     * 根据公司id获取用户列表
     * /v1/companies/:company_id/members
     * GET
     * @param wpsCommonReqDomin
     * @param wpsQueryUserListDomain
     */
    public List<WpsUserDomain> queryUserByConpanyId(WpsCommonReqDomin wpsCommonReqDomin, WpsQueryUserListDomain wpsQueryUserListDomain);





}
