package com.portal.service.impl;

import com.portal.daoAuthoriza.WxInfoInfoDAO;
import com.portal.domain.WeiXinConfig;
import com.portal.service.WxInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO:
 * create by liuying at 2019/9/29
 *
 * @author liuying
 * @since 2019/9/29 11:45
 */

@Service
public class WxInfoServiceImpl implements WxInfoService {


    @Autowired
    private WxInfoInfoDAO wxInfoInfoDAO;

    @Override
    public WeiXinConfig getWeiXinConfigInfo(String companyId) {
        return wxInfoInfoDAO.getWeiXinConfigInfo(companyId);
    }
}
