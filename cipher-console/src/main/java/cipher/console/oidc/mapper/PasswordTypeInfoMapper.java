package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.PasswordTypeInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PasswordTypeInfoMapper {
    List<PasswordTypeInfoDomain> queryPasswordTypeInfo();
    int updatePasswordTypeInfo(@Param(value = "status")int status,
                               @Param(value = "password_code")int passwordCode);
}
