package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.PasswordSettingDomain;
import org.apache.ibatis.annotations.Param;

public interface PasswordSettingMapper {
    PasswordSettingDomain queryPasswordSetting(String companyId);
    int updatePasswordSetting(@Param(value = "length")int length,
                              @Param(value = "init")String init);

     int  savePassword(PasswordSettingDomain passwordSettingDomain);




}
