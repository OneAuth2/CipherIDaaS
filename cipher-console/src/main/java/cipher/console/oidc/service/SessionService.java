package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.UserSessionResp;

import javax.servlet.http.HttpSession;

public interface SessionService  {

    public String getCompanyUUid(HttpSession session);


    /**
     * 判断是否session失效：true-已经失效，false-未失效
     *
     * @param uuid 账号
     * */
    public boolean isSessionExpire(String uuid);


    /**
     *  更新session
     *  *
     *  @param uuid 账号
     * */
    public void updateSessionExpireTime(String requestIp,String uuid, String companyUUid);

    /**
     * 销毁session
     *
     * @param uuid 账号
     * */
    public void removeSession(String uuid);


    public String getSession(String uuid,String companyId);


    public UserSessionResp setUserLoginInfo(String uuid);






}
