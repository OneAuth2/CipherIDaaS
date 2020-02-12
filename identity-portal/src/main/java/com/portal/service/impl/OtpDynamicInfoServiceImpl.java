package com.portal.service.impl;

import com.portal.daoAuthoriza.OtpDynamicInfoDAO;
import com.portal.domain.OtpDynamicInfo;
import com.portal.service.OtpDynamicInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * TODO:
 * create by liuying at 2019/9/20
 *
 * @author liuying
 * @since 2019/9/20 15:32
 */
@Service
public class OtpDynamicInfoServiceImpl implements OtpDynamicInfoService {

    @Autowired
    private OtpDynamicInfoDAO otpDynamicInfoDAO;

    @Override
    public String getOtpDynamicInfo(String userId) {
        return otpDynamicInfoDAO.getOtpDynamicInfo(userId);
    }

    @Override
    public void deleteOtpDynamicInfo(String userId) {
        otpDynamicInfoDAO.deleteOtpDynamicInfo(userId);
    }

    @Override
    public void insertOtpDynamicInfo(OtpDynamicInfo otpDynamicInfo) {
        otpDynamicInfoDAO.insertOtpDynamicInfo(otpDynamicInfo);
    }
}
