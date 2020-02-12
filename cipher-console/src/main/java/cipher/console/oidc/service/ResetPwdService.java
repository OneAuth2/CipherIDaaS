package cipher.console.oidc.service;

import cipher.console.oidc.common.GlobalAuthType;
import cipher.console.oidc.common.GlobalReturnCode;

public interface ResetPwdService {


    /*
    *
    * 根据部门ID获取身份认证策略
    *
    * */
    public GlobalAuthType getGlobalAuthType(String userName, Integer groupId,String companyId);


    /**
     * 修改AD账号的密码
     *
     * @param accountNumber 账号
     * @param pwd           密码
     * @return
     */
    public GlobalReturnCode.MsgCodeEnum modifyPwd(String accountNumber, String pwd);

}
