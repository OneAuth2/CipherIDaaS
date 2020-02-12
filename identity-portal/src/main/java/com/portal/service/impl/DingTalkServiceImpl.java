package com.portal.service.impl;



import com.portal.daoAuthoriza.DingTalkConfigDAO;
import com.portal.domain.DingTalkConfig;
import com.portal.service.DingTalkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DingTalkServiceImpl implements DingTalkService {

    @Autowired
    private DingTalkConfigDAO dingTalkConfigDAO;

    @Override
    public DingTalkConfig getDingTalkConfigByCompanyId(String companyId) {
        return dingTalkConfigDAO.getPortalDingConfig(companyId);
    }
}
