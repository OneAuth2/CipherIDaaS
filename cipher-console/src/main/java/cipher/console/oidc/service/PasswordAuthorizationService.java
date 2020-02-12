package cipher.console.oidc.service;

import cipher.console.oidc.common.PasswordTypeCode;
import cipher.console.oidc.domain.web.PasswordAuthorizationMagDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 密码管理的service
 * @author sizhao
 *
 * */
public interface PasswordAuthorizationService {

    /**
     * 重置用户的密码
     *
     * */
    void resetAccountPassword(String uuid,String companyId,String newPwd,int logFlag);

    /**
     *
     * 初始化用户的密码
     * @param password 初始的密码
     *
     * */
    void insertAccountPassword(String password,String accountNumber);

    /**
     *
     * 初始化批量导入的用户的密码
     * @param passwordMagDomains 批量的初始化密码信息
     *
     * */
    void insertAccountPasswordExcel(List<PasswordAuthorizationMagDomain> passwordMagDomains);


    void deleteAccountPassword(@Param(value = "accountNumber") String accountNumber);



    /**
     *修改用户密码
     *
     * */
    PasswordTypeCode updatePassword(@Param(value = "uuid") String uuid, @Param(value = "password") String password,String companyId,int logFlag);






}
