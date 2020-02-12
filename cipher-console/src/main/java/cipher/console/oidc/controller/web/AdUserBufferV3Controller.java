package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.config.LdapMultiSourceConfig;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;
import cipher.console.oidc.model.AdOuModel;
import cipher.console.oidc.service.AdInfoService;
import cipher.console.oidc.service.ldap.AdUpdateTimeService;
import cipher.console.oidc.service.ldap.LdapImportService;
import com.alibaba.fastjson.JSON;
import com.unboundid.ldap.sdk.*;
import com.unboundid.ldap.sdk.controls.SimplePagedResultsControl;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.security.GeneralSecurityException;
import java.util.*;

/**
 * @Author: zt
 * 重构于2019-04-12
 * 本次目标：添加userAccountControl字段
 * 最终目的:根据前端配置，只读取需要的字段，查询数据由，ldapTemplate转为ldapConnection
 * @Date: 2019/04/12 19:37
 */
@Controller
@RequestMapping(value = "/cipher")
public class AdUserBufferV3Controller {
    @Autowired
    private AdInfoService adInfoService;

    @Autowired
    private LdapImportService ldapImportService;

    @Autowired
    private AdUpdateTimeService adUpdateTimeService;

    private static final Logger LOGGER = LoggerFactory.getLogger(AdUserBufferV3Controller.class);


    /**
     * 将AD域的用户导入缓冲表中
     *
     * @param request
     */
    @RequestMapping(value = "/depart_sync")
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Integer> syncAdUser2Buffer(HttpServletRequest request,
                                                  @RequestParam(value = "adId") Integer adId) {
        LOGGER.info("Enter the 进入增量同步AD域用户");
        String companyId = ConstantsCMP.getSessionCompanyId(request);

        AdInfoDomain adInfoDomain = adInfoService.queryAdInfoById(adId);

        String latestTimeStamp = adUpdateTimeService.queryByIp(adInfoDomain.getIp());

        LOGGER.info("上次更新的时间:{}", latestTimeStamp);

        if (StringUtils.isEmpty(latestTimeStamp)) {
            latestTimeStamp = "19700916080801.0Z";
        }

        try {
            adInfoDomain.setCompanyId(companyId);
            List<SearchResultEntry> entryList = ldapImportService.importUserFromLdap(adInfoDomain, latestTimeStamp);
            List<AdUserBufferDomain> adUserBufferDomainList = ldapImportService.convertToAdUser(entryList, adInfoDomain);
//            List<AdUserBufferDomain> adUserBufferDomainList = ldapImportService.importLdapUserWithTemplate(adInfoDomain, latestTimeStamp);
            return ldapImportService.handleLogic(adUserBufferDomainList, companyId);
        } catch (Exception e) {
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 全量扫描
     *
     * @param request
     * @return
     */
    @RequestMapping(value = "/all_sync")
    @ResponseBody
    @Transactional
    public Map<String, Integer> allSyncAdUser2BufferOld(HttpServletRequest request,
                                                        @RequestParam(value = "adId") Integer adId,
                                                        @RequestParam(required = false) String companyIdParam) {
        LOGGER.info("Enter the 进入全量同步AD域用户");
        String companyId = null;
        try {
            companyId = ConstantsCMP.getSessionCompanyId(request);
        } catch (Exception e) {
            LOGGER.error("定时同步无法获取session");
        }

        if(StringUtils.isEmpty(companyId)){
            companyId=companyIdParam;
        }

        AdInfoDomain adInfoDomain = adInfoService.queryAdInfoById(adId);
        String latestTimeStamp = "19700916080801.0Z";
        try {
            adInfoDomain.setCompanyId(companyId);
            List<SearchResultEntry> entryList = ldapImportService.importUserFromLdap(adInfoDomain, latestTimeStamp);
            List<AdUserBufferDomain> adUserBufferDomainList = ldapImportService.convertToAdUser(entryList, adInfoDomain);
//            List<AdUserBufferDomain> adUserBufferDomainList = ldapImportService.importLdapUserWithTemplate(adInfoDomain, latestTimeStamp);
            return ldapImportService.handleLogic(adUserBufferDomainList, companyId);
        } catch (Exception e) {
            e.printStackTrace();
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
        }
        return null;
    }

}
