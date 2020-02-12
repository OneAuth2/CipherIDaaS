package com.portal.service.impl;


import com.portal.commons.TcpServiceKey;
import com.portal.daoAuthoriza.CompanyInfoDAO;
import com.portal.daoAuthoriza.CompanyServiceMapDAO;
import com.portal.daoAuthoriza.GlobalServiceInfoDAO;
import com.portal.daoAuthoriza.PlatUserCompanyMapDAO;
import com.portal.domain.ServiceInfo;
import com.portal.domain.ServiceSwitchInfo;
import com.portal.service.GlobalServiceInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:16
 */
@Service
public class GlobalServiceInfoServiceImpl implements GlobalServiceInfoService {

    @Autowired
    private GlobalServiceInfoDAO globalServiceInfoDAO;

    @Autowired
    private PlatUserCompanyMapDAO platUserCompanyMapDAO;

    @Autowired
    private CompanyServiceMapDAO companyServiceMapDAO;

    @Autowired
    private CompanyInfoDAO companyInfoDAO;

    @Override
    public ServiceInfo queryServiceInfoByIndexId(String indexId) {
        return globalServiceInfoDAO.queryServiceInfoByIndexId(indexId);
    }

    @Override
    public boolean serviceIsOpen(String serviceIndex, Integer platUserId) {

        ServiceInfo serviceInfo = globalServiceInfoDAO.queryServiceInfoByIndexId(serviceIndex);

        /**
         * 服务总开关关闭
         */
        if (serviceInfo.getTotalControStatus() == TcpServiceKey.SERVICE_STATUS_CLOSE) {
            return false;
        }

        //一个用户的companyId有两个来源，1.是创建公司的人员，2.通过邀请码绑定
        Integer companyId = platUserCompanyMapDAO.queryCompayIdByPlatUserID(platUserId);
        if (companyId == null) {
            companyId = companyInfoDAO.queryCompanyIdByPlatUserId(platUserId);
        }
        //该用户还未加入任何公司，不受，user provision限制
        if (companyId == null) {
            return false;
        }

        //某公司对应某服务的开关
        Integer status = companyServiceMapDAO.queryServiceStatus(companyId, serviceInfo.getId());

        if (status == null) {
            return false;
        }

        return status == TcpServiceKey.SERVICE_STATUS_OPEN;

    }

    @Override
    public ServiceSwitchInfo queryServiceSwitchInfo(String serviceName) {
        return globalServiceInfoDAO.queryServiceSwitchInfo(serviceName);
    }


}
