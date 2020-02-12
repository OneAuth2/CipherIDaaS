package com.portal.service;

import com.portal.domain.PortalApplyInfo;
import com.portal.domain.RealParamDomain;
import com.portal.domain.UserInfoDomain;

import java.util.Map;

public interface SubAccountRuleService {
    /**
     * 根据从账号规则获取从账号
     *
     * @param user
     * @param associatedAccount
     */
    public String getSubAccountRule(UserInfoDomain user, String associatedAccount, String applyId);

    /**
     * 根据从账号密码规则获取从账号密码
     *
     * @param userId
     * @param associatedAccount
     */
    public String getSubPwdRule(String userId, String associatedAccount, String applyId);

    /**
     * 根据应用Ii获取应用从账号信息
     *
     * @Param userId
     * @Param applyId
     */
    public Map<String, Object> getSubAccountInfo(UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo);

    /**
     * 检验子应用账号密码是否正确
     *
     * @Param userId
     * @Param applyId
     * @Param subName
     * @Param subPwd
     */

    public Map<String, Object> chenckSubAccountAndPwd(String userId, PortalApplyInfo portalApplyInfo, String subName, String subPwd);


    /**
     * 根据XDSG返回的url转换信息、
     * @Param url
     */

    public RealParamDomain getRealParamInfo(String url);

    /**
     * 保存子账号信息
     * @param subName
     * @param subPwd
     * @param applyId
     * @param uuid
     */
    public Map<String,Object> saveSubAccount(String subName,String subPwd,String applyId,String uuid);


    /**
     * 根据从账号规则获取从账号
     *
     * @param user
     * @param associatedAccount
     */
    public String checkSubAccountRule(UserInfoDomain user, String associatedAccount, String applyId);

    /**
     * 根据从账号密码规则获取从账号密码
     *
     * @param userId
     * @param associatedAccount
     */
    public String checkSubPwdRule(String userId, String associatedAccount, String applyId);


}
