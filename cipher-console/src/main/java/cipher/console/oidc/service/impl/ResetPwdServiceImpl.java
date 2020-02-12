package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.GlobalAuthType;
import cipher.console.oidc.common.GlobalReturnCode;
import cipher.console.oidc.config.LdapMultiSourceConfig;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.mapper.IdentityStrategyMapper;
import cipher.console.oidc.mapper.UserGroupMapper;
import cipher.console.oidc.service.AdInfoService;
import cipher.console.oidc.service.ResetPwdService;
import cipher.console.oidc.service.UserService;
import com.unboundid.ldap.sdk.*;
import io.swagger.models.auth.In;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.nio.charset.StandardCharsets;
import java.util.List;


@Service
public class ResetPwdServiceImpl implements ResetPwdService {

    @Autowired
    private IdentityStrategyMapper identityStrategyMapper;

    @Autowired
    private AdInfoService adInfoService;

    @Autowired
    private UserService userService;


    @Autowired
    private UserGroupMapper userGroupMapper;

    private static final Logger logger = LoggerFactory.getLogger(ResetPwdServiceImpl.class.getSimpleName());


    /**
     * 用户修改AD密码
     *
     * @param
     * @return
     */

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

            UserInfoDomain userInfoDomain=userService.getUserInfoByCompany(uuid);
            String userDn = getUserDnByAccount(ldapConnection, userInfoDomain.getAccountNumber(), adInfoDomain.getUserName());
            if (userDn == null) {
                return GlobalReturnCode.MsgCodeEnum.USER_INFO_NOT_COMPLETE;
            }
//            String newQuotedPassword = "\"" + "Cipher@2019!" + "\"";
            pwd = "\"" + pwd + "\"";
            byte[] newUnicodePassword = pwd.getBytes(StandardCharsets.UTF_16LE);
            Modification modification = new Modification(ModificationType.REPLACE, "unicodePwd", newUnicodePassword);
            ldapConnection.modify(userDn, modification);
            return GlobalReturnCode.MsgCodeEnum.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            //判断密码强度不够的异常
            if (e.getMessage().contains("WILL_NOT_PERFORM")) {
                logger.error("update ad user pwd fail,the pwd not strong enough!==>" + uuid);
                return GlobalReturnCode.MsgCodeEnum.AD_PWD_NOT_STRONG_ENOUGH;
            }
            return GlobalReturnCode.MsgCodeEnum.AUTH_CAUSE_EXCEPTION;
        }

    }


    /**
     * 根据用户获取该用户所在的AD域账号信息
     *
     * @param userName
     * @return
     */
    private AdInfoDomain getAdInfoDomain(String userName) {
        UserInfoDomain userInfoDomain = userService.getUserInfo(userName);
        if (StringUtils.isBlank(userInfoDomain.getSource()) || StringUtils.equals(userInfoDomain.getSource(), "newAdd")) {
            logger.info(userName + ":该用户是本地用户,获取不到ldap连接");
            return null;
        }
        AdInfoDomain adInfoDomain = new AdInfoDomain();
        adInfoDomain.setIp(userInfoDomain.getSource());

        adInfoDomain = adInfoService.queryAdInfoNew(adInfoDomain);
        return adInfoDomain;
    }


    /**
     * @param ldapConnection ldaps连接
     * @param accountNumber  主账号
     * @param admin          ad域管理员账号
     * @return accountNumber对应的Ad的 userDn
     * @throws Exception
     */
    private String getUserDnByAccount(LDAPConnection ldapConnection, String accountNumber, String admin) throws Exception {

        if (org.apache.commons.lang3.StringUtils.isEmpty(accountNumber)) {
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


    @Override
    public GlobalAuthType getGlobalAuthType(String uuid, Integer groupId,String companyId) {
        List<Integer> groupIdList = userGroupMapper.queryGroupByAccount(uuid, groupId);
        IdentityStrategyDomain defaultAuthType = identityStrategyMapper.queryDefaultAuthType(companyId);
        int defaultAuthTypeCode = defaultAuthType == null ? 0 : defaultAuthType.getAuthType();
        //该用户没有任何策略，使用默认策略
        if (CollectionUtils.isEmpty(groupIdList)) {
            return getAuthType(defaultAuthTypeCode);
        }
        IdentityStrategyDomain identityAuthTypeDomain = identityStrategyMapper.queryAuthTypeByGroupIdList(groupIdList);
        if (identityAuthTypeDomain == null) {
            return getAuthType(defaultAuthTypeCode);
        }
        int authType = identityAuthTypeDomain.getAuthType() == null ? 0 : identityAuthTypeDomain.getAuthType();
        return getAuthType(authType);
    }

    private GlobalAuthType getAuthType(int authType) {
        switch (authType) {
            case 0:
                return GlobalAuthType.LOCALAUTH;
            case 1:
                return GlobalAuthType.ADAUTH;
            default:
                return GlobalAuthType.JTLERP;
        }
    }


}
