package com.portal.service.impl;


import com.portal.daoAuthoriza.AbnormalStrategyDAO;
import com.portal.domain.AbnormalStrategyInfo;
import com.portal.service.AbnormalStrategyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by 95744 on 2018/8/30
 */

@Service
public class AbnormalStrategyServiceImpl implements AbnormalStrategyService {

    @Autowired
    private AbnormalStrategyDAO abnormalStrategyDAO;


    @Override
    public AbnormalStrategyInfo getAbnormalStrategyInfo() {
        return abnormalStrategyDAO.getAbnormalStrategyInfo();
    }
}
