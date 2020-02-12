package cipher.console.oidc.service.subapp;

import cipher.console.oidc.domain.subapp.MaycurCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.MaycurTokenReqDomain;

import java.util.Map;

/**
 * 每刻报销系统
 *
 * @Author: zt
 * @Date: 2018/12/4 11:45
 */
public interface MaycurService {

    /**
     * 获取每刻报销系统单点登录地址
     * @param apiPre 接口前缀地址
     * @param ent_code 公司没变吗
     * @param userName 待登录用户
     * @param secret 秘钥
     * @return
     */
    public String getSSOUrl(String apiPre,String ent_code, String userName, String secret);

    /**
     * 获取tokenId
     * @param apiPre 接口前缀地址
     * @param ent_code 公司编码
     * @param userName 管理员账号
     * @param secret 秘钥
     * @return
     */
    public Map<String,Object> getTokenInfo(String apiPre,String ent_code, String userName, String secret);


    /**
     * 创建账号
     * @param apiPre api前缀
     * @param token  token
     * @param entCode 公司编码
     * @param maycurCreateSubReqDomain
     * @return
     */
    public Map<String,Object> createAccount(String apiPre,String token,String entCode,MaycurCreateSubReqDomain maycurCreateSubReqDomain);

    /**
     * 查询部门信息
     * @param apiPre
     * @param token
     * @param entCode
     * @param startTime 开始时间 可不传
     * @param endTime 结束时间 可不传
     */
    public void queryDepartmen(String apiPre,String token,String entCode,Long startTime,Long endTime);

}
