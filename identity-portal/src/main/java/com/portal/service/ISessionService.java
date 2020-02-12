package com.portal.service;


import javax.servlet.http.HttpServletRequest;

/**
 * session服务接口
 *
 * @author Jason
 */
public interface ISessionService {

    /**
     * 判断是否session失效：true-已经失效，false-未失效
     *
     * @param userName 账号
     */
    public boolean isSessionExpire(String userName);


    /**
     * 更新session
     * *
     *
     * @param userName 账号
     */
    public void updateSessionExpireTime(String requestIp, String companyUUid,String uuid);

    /**
     * 更新用户的session
     *
     * @param requestIp     当前请求ip
     * @param companyUUid 公司唯一id
     * @param uuid        用户的唯一id
     */
    void updateSession(String requestIp, String companyUUid, String uuid);

    /**
     * 销毁session
     *
     * @param userName 账号
     */
    public void removeSession(String userName);


    public String getSession(String userName);

}
