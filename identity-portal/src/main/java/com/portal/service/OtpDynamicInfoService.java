package com.portal.service;

import com.portal.domain.OtpDynamicInfo;

/**
 * TODO:
 * create by liuying at 2019/9/20
 *
 * @author liuying
 * @since 2019/9/20 15:31
 */
public interface OtpDynamicInfoService {

    public String getOtpDynamicInfo(String userId);


    public void deleteOtpDynamicInfo(String userId);


    public  void insertOtpDynamicInfo(OtpDynamicInfo otpDynamicInfo);
}
