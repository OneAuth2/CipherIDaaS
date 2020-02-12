package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.PasswordAuthorizationMagDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PasswordAuthorizationMapper {

    /**
     *重置用户密码
     *
     * */
    void resetAccountPassword(@Param(value = "uuid") String uuid,@Param(value = "initPwd") String initPwd);

    /**
     *
     * 初始化用户的密码
     * @param passwordAuthorizationMagDomain 初始的密码
     *
     * */
    void insertAccountPassword(PasswordAuthorizationMagDomain passwordAuthorizationMagDomain);

    /**
     *
     * 初始化批量导入的用户的密码
     * @param passwordMagDomains 批量的初始化密码信息
     *
     * */
    void insertAccountPasswordExcel(List<PasswordAuthorizationMagDomain> passwordMagDomains);



    void deleteAccountPassword(@Param(value = "uuid") String uuid);

    PasswordAuthorizationMagDomain selectPasswordAuthorizationMagDomain(@Param(value = "uuid") String uuid);


    /**
     *修改用户密码
     *
     * */
    void updatePassword(@Param(value = "uuid") String uuid,@Param(value = "password") String password);

}
