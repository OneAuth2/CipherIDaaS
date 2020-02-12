package cipher.console.oidc.service.subapp;

import cipher.console.oidc.domain.subapp.WangyiCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.WangyiUnReadMailCountDomain;

import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/12/3 10:04
 */
public interface WangyiEmailService {

    /**
     * 获取网易邮箱单点登录地址
     * @param priKey 私钥
     * @param domain 域名
     * @param accountName 要单点登录的账号
     * @return ssoUrl
     */
    public String getSSOUrl(String priKey,String domain,String accountName);

    /**
     * 创建网易邮箱账号
     * @param wangyiCreateSubReqDomain 请求参数
     * @param priKey 私钥
     */
    public Map<String,Object> createSubAccount(WangyiCreateSubReqDomain wangyiCreateSubReqDomain, String priKey);


    /**
     * 获取收件箱中未读邮件的数量
     * @param wangyiUnReadMailCountDomain 获取未读邮件的数量
     * @param priKey 私钥
     * @return
     */
    public Map<String,Object> getUnReadMailCount(WangyiUnReadMailCountDomain wangyiUnReadMailCountDomain, String priKey);

}
