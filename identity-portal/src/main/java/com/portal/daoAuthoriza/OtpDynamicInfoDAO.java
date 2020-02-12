package com.portal.daoAuthoriza;

import com.portal.domain.OtpDynamicInfo;
import org.apache.ibatis.annotations.Param;

/**
 * TODO:
 * create by liuying at 2019/9/20
 *
 * @author liuying
 * @since 2019/9/20 15:29
 */
public interface OtpDynamicInfoDAO {

    public String getOtpDynamicInfo(@Param(value = "userId") String userId);

    public void deleteOtpDynamicInfo(@Param(value = "userId")String userId);

    public  void insertOtpDynamicInfo(OtpDynamicInfo otpDynamicInfo);

}
