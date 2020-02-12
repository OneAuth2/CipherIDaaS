package com.portal.service.impl;


import com.portal.daoAuthoriza.ApplicationDAO;
import com.portal.daoAuthoriza.SubInfoDAO;
import com.portal.daoAuthoriza.UserDAO;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.SubAccountDomain;
import com.portal.service.ApplicationService;
import com.portal.utils.UrlUtils;
import edu.hziee.common.lang.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ApplicationServiceImpl.class);

    @Autowired
    private ApplicationDAO applicationDAO;


    @Autowired
    private SubInfoDAO subInfoDAO;

    @Autowired
    private UserDAO userDAO;

    /**
     * 根据访问地址和账号获取从账号信息
     *
     * @param service
     * @param uuid
     * @return
     */
    @Override
    public String querySubAccountDomain(String service, String uuid) {
        if (StringUtil.isEmpty(service) || StringUtil.isEmpty(uuid)) {
            return null;
        }
        try {
            URL url = new URL(service);
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();
            service = protocol + "://" + host + ':' + port;
            if (port == -1) {
                service = protocol + "://" + host;
            }
            if (StringUtil.isEmpty(service)) {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //获取应用id
        String applicationId = applicationDAO.findApplicationId(service);
        if (StringUtil.isEmpty(applicationId)) {
            return null;
        }
        //根据应用id和uuid获取从账号和密码
        return subInfoDAO.getSubAccountInfo(applicationId, uuid);

    }

    @Override
    public SubAccountDomain getSubAccountDomain(String host, String uuid) {
        if (StringUtil.isEmpty(host) || StringUtil.isEmpty(uuid)) {
            return null;
        }
        try {
            URL url = new URL(host);
            String protocol = url.getProtocol();
            String host1 = url.getHost();
            int port = url.getPort();
            host = protocol + "://" + host + ':' + port;
            if (port == -1) {
                host = protocol + "://" + host;
            }
            if (StringUtil.isEmpty(host)) {
                return null;
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        //获取应用id
        String applicationId = applicationDAO.findApplicationId(host);
        if (StringUtil.isEmpty(applicationId)) {
            return null;
        }
        //根据应用id和uuid获取从账号和密码
        return subInfoDAO.getSubAccountInfoByHost(applicationId, uuid);

    }

    /**
     * 根据传入的serviceurl获取该应用的id
     * create by 安歌
     *
     * @param serviceUrl 传入的url
     * @return
     */
    @Override
    public String getApplicationId(String serviceUrl) {

        //入参校验
        if (StringUtils.isEmpty(serviceUrl)) {

            return null;
        }

        //定义标准地址service
        String service = "";

        //获取该url的标准地址
        try {
            //根据URL类获取协议 ip 端口
            URL url = new URL(serviceUrl);
            String protocol = url.getProtocol();
            String host = url.getHost();
            int port = url.getPort();

            //给service赋值 端口如果为-1则是默认端口8080 默认可以不加
            service = protocol + "://" + host + ':' + port;
            if (port == -1) {
                service = protocol + "://" + host;
            }

            //如果service为空 直接为空
            if (StringUtil.isEmpty(service)) {

                return null;
            }
        } catch (MalformedURLException e) {
            LOGGER.error("new URL error the serviceUrl={{}}", serviceUrl);
            LOGGER.error(e.getMessage(), e);

            return null;
        }

        //获取应用id
        String applicationId = applicationDAO.findApplicationId(service);

        //返回应用id
        return applicationId;
    }

    @Override
    public String getIdByAppHost(String appHost) {
        //入参校验
        if (StringUtils.isEmpty(appHost)) {
            return "";
        }


        //根据真实地址获取应用的id
        String id = applicationDAO.getAppIdByAppHost(appHost);

        return id;
    }

    @Override
    public ApplicationInfoDomain getApplicationInfoByAssertionConsumerServiceURL(String acs) {
        //入参校验
        if (StringUtils.isEmpty(acs)){
            return null;
        }
        return applicationDAO.getApplicationInfoByAssertionConsumerServiceURL(acs);
    }

    @Override
    public Boolean getIddFromUserAndApplication(String uuid, String appId) {

        //入参校验
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(appId)) {

            return null;
        }

        //获取人与应用的权限
        String userApp = applicationDAO.getIdFromUserAndApplication(uuid, appId);
        //userApp不为空返回拥有该账号的权限
        if (StringUtils.isEmpty(userApp)) {
            return false;
        }
        return true;
    }

    @Override
    public SubAccountDomain getSubAccountDomainByAppHost(String appHost, String userId) {

        //入参校验
        if (StringUtil.isEmpty(appHost) || StringUtil.isEmpty(userId)) {
            return null;
        }

        //获取应用id
        String applicationId = applicationDAO.findApplicationIdByAppHost(appHost);
         //根据应用id和uuid获取从账号和密码
        return subInfoDAO.getSubAccountInfoByHost(applicationId, userId);
    }

    @Override
    public Boolean getIdFromTeamAndApplicationId(String teamId, String appId) {

        //入参校验
        if (StringUtils.isEmpty(teamId) || StringUtils.isEmpty(appId)) {
            return null;
        }

        //获取用户与安全组和应用时候存在关联
        List<String> teamList = applicationDAO.getIdFromTeamAndApplicationId(teamId, appId);
        if (teamList != null && teamList.size()>0) {
            return true;
        }
        return false;
    }

    @Override
    public Boolean getIdFromGroupIdAndApplicationId(String uuid, String appId) {

        //入参校验

        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(appId)) {
            return false;
        }


        //获取部门与应用的关联关系并且返回该表的id的list
        List<String> groupLists = applicationDAO.getIdFromGroupIdAndApplicationId(uuid, appId);
        if (groupLists != null&&groupLists.size()>0) {

            return true;
        }

        //返回失败
        return false;
    }

    @Override
    public String getIdFromApplicationInfoByServiceUrl(String serviceUrl) {

        //入参校验
        if (StringUtils.isEmpty(serviceUrl)) {
            return "";
        }

        //获取真实地址
        String service = UrlUtils.getUrl(serviceUrl);
        if (StringUtils.isEmpty(service)) {
            return "";
        }

        //根据真实地址获取应用的id
        String id = applicationDAO.getIdFromApplicationByServiceUrl(service);

        return id;
    }



    @Override
    public ApplicationInfoDomain getApplicationById(String id) {

        //入参校验
        if(org.apache.commons.lang.StringUtils.isEmpty(id)){

            return null;
        }

        //转换id的值并查询应用信息返回
        int appId=Integer.parseInt(id);
        return applicationDAO.queryApplicationById(appId);
    }

    @Override
    public ApplicationInfoDomain queryApplication(String applicationId) {
        return applicationDAO.queryApplication(applicationId);
    }

}
