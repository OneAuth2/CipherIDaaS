package com.portal.service;


import com.portal.domain.DingTalkConfig;

public interface DingTalkService {

    DingTalkConfig getDingTalkConfigByCompanyId(String companyId);

}
