package com.service;

/**
 * @author lqgzj
 * @date 2019/8/9
 */
public interface CipherAccountDingBindService {
    String getDingIdByUserId(String uuid);

    String getCompanyIdByUserId(String uuid);

    String getUuidByUserId(String dingUserId);
}
