package cipher.console.oidc.service.subapp;

import cipher.console.oidc.domain.subapp.TencentCreateSubReqDomain;
import cipher.console.oidc.domain.subapp.TencentDepDomain;

import java.util.Map;

/**
 * 腾讯邮箱账号下发管理
 * 接口文档地址:https://exmail.qq.com/qy_mng_logic/doc#10001
 * @Author: zt
 * @Date: 2018/11/29 9:22
 */
public interface TencentEmailService {

    /**
     * 获取access_token
     * @param ID
     * @param secret
     * @return
     */
    public String getAccessToken(String ID, String secret);

    /**
     * 创建单个从账号
     * 腾讯邮箱的姓名采用ISO-859-1编码
     */
    public Map<String,Object> createSubAccount(String accesToken,TencentCreateSubReqDomain tencentCreateSubReqDomain);

    /**
     * 否	部门id。获取指定部门及其下的子部门。id为1时可获取根部门下的子部门。
     * @param accessToken
     * @param id
     */
    public TencentDepDomain qeryDept(String accessToken, Long id);

}
