package com.portal.service.impl;


import com.alibaba.dubbo.common.utils.CollectionUtils;
import com.portal.commons.GlobalReturnCode;
import com.portal.commons.LdapConstants;
import com.portal.config.LdapMultiSourceConfig;
import com.portal.daoAuthoriza.CipherUserInfoDAO;
import com.portal.domain.AdInfoDomain;
import com.portal.domain.UserInfoDomain;
import com.portal.service.AdInfoService;
import com.portal.service.CipherUserInfoService;
import com.portal.service.LdapService;
import com.unboundid.ldap.sdk.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-04-08 17:05
 */

@Service
public class LdapServiceImpl implements LdapService {

    @Autowired
    private CipherUserInfoService cipherUserInfoService;

    @Autowired
    private AdInfoService adInfoService;

    private static final Logger LOGGER = LoggerFactory.getLogger(LdapServiceImpl.class);


    @Override
    public GlobalReturnCode.MsgCodeEnum modifyPwd(String uuid, String pwd) {
        LDAPConnection ldapConnection = null;
        try {
            AdInfoDomain adInfoDomain = getAdInfoDomain(uuid);
            //该用户不是AD用户，或者AD配置缺失
            if (adInfoDomain == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }
            ldapConnection = LdapMultiSourceConfig.getLdapsConnection(adInfoDomain);
            if (ldapConnection == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }

            //根据uuid获取accountNumber
            UserInfoDomain userInfoDomain = cipherUserInfoService.queryUserByName(uuid);

            String userDn = getUserDnByAccount(ldapConnection, userInfoDomain.getAccountNumber(), adInfoDomain.getUserName());
            if (userDn == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }
//          String newQuotedPassword = "\"" + "Cipher@2019!" + "\"";
            pwd = "\"" + pwd + "\"";
            byte[] newUnicodePassword = pwd.getBytes(StandardCharsets.UTF_16LE);
            Modification modification = new Modification(ModificationType.REPLACE, "unicodePwd", newUnicodePassword);
            ldapConnection.modify(userDn, modification);
            return GlobalReturnCode.MsgCodeEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            //判断密码强度不够的异常
            if (e.getMessage().contains("WILL_NOT_PERFORM")) {
                LOGGER.error("update ad user pwd fail,the pwd not strong enough!==>" + uuid);
                return GlobalReturnCode.MsgCodeEnum.AD_PWD_NOT_STRONG_ENOUGH;
            }
            return GlobalReturnCode.MsgCodeEnum.AUTH_CAUSE_EXCEPTION;
        } finally {
            if (ldapConnection != null) {
                ldapConnection.close();
            }
        }
    }


    @Autowired
    private CipherUserInfoDAO cipherUserInfoDAO;

    @Override
    public GlobalReturnCode.MsgCodeEnum modifyUserAccountContro(String accountNumber) {
        LDAPConnection ldapConnection = null;
        AdInfoDomain adInfoDomain = getAdInfoDomain(accountNumber);
        //该用户不是AD用户，或者AD配置缺失
        if (adInfoDomain == null) {
            return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
        }

        try {
            ldapConnection = LdapMultiSourceConfig.getLdapsConnection(adInfoDomain);
            if (ldapConnection == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }

            //TODO


            UserInfoDomain userInfoDomain = cipherUserInfoDAO.queryUserByName(accountNumber);

            String userDn = getUserDnByAccount(ldapConnection, userInfoDomain.getAccountNumber(), adInfoDomain.getUserName());
            if (userDn == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }
            Modification modification = new Modification(ModificationType.REPLACE, "userAccountControl", String.valueOf(LdapConstants.AD_PWD_STRATEGY));
            ldapConnection.modify(userDn, modification);
            return GlobalReturnCode.MsgCodeEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            return GlobalReturnCode.MsgCodeEnum.AUTH_CAUSE_EXCEPTION;
        }
    }

    /**
     * 根据用户获取该用户所在的AD域账号信息
     *
     * @param uuid
     * @return
     */
    private AdInfoDomain getAdInfoDomain(String uuid) {
        UserInfoDomain userInfoDomain = cipherUserInfoService.queryUserByName(uuid);
        if (StringUtils.isBlank(userInfoDomain.getSource()) || StringUtils.equals(userInfoDomain.getSource(), "newAdd")) {
            LOGGER.info(uuid + ":该用户是本地用户,获取不到ldap连接");
            return null;
        }
        AdInfoDomain adInfoDomain = new AdInfoDomain();
        adInfoDomain.setIp(userInfoDomain.getSource());

        adInfoDomain = adInfoService.queryAdInfo(adInfoDomain);
        return adInfoDomain;
    }


    /**
     * 根据管理员账号，获取根域名
     *
     * @param account 形如：administrator@cipherchina.com
     * @return
     */
    private String getRootDnByAccount(String account) {
        if (!account.contains("@")) {
            return null;
        }

        int len = account.length();
        StringBuilder stringBuilder = new StringBuilder();
        String s = account.substring(account.indexOf("@") + 1, len);
        String[] dcs = s.split("\\.");
        for (int i = 0; i < dcs.length; i++) {
            if (i != dcs.length - 1) {
                stringBuilder.append("dc=").append(dcs[i]).append(",");
            } else {
                stringBuilder.append("dc=").append(dcs[i]);
            }
        }
        return stringBuilder.toString();
    }


    /**
     * @param ldapConnection ldaps连接
     * @param accountNumber  主账号
     * @param admin          ad域管理员账号
     * @return accountNumber对应的Ad的 userDn
     * @throws Exception
     */
    private String getUserDnByAccount(LDAPConnection ldapConnection, String accountNumber, String admin) throws Exception {


        if (StringUtils.isEmpty(accountNumber)) {
            return null;
        }

        if (!accountNumber.contains("@")) {
            accountNumber += admin.substring(admin.indexOf("@"), admin.length());
        }


        Filter filter = Filter.createEqualityFilter("userPrincipalName", accountNumber);
        String[] s = new String[]{"distinguishedName", "userPrincipalName"};

        String rootDc = getRootDnByAccount(admin);

        if (StringUtils.isEmpty(rootDc)) {
            return null;
        }

        SearchRequest searchRequest = new SearchRequest(rootDc, SearchScope.SUB, filter, s);
        SearchResult searchResult = ldapConnection.search(searchRequest);
        List<SearchResultEntry> searchEntries = searchResult.getSearchEntries();

        //未在AD域中找到对应的用户
        if (CollectionUtils.isEmpty(searchEntries)) {
            return null;
        }

        //AD中查出了多个用户
        if (searchEntries.size() != 1) {
            return null;
        }

        //返回userDn
        return searchEntries.get(0).getAttributeValue("distinguishedName");
    }
}
