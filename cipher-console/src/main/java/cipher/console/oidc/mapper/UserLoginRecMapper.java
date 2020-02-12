package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.UserLoginRecInfo;
import org.apache.ibatis.annotations.Param;

public interface UserLoginRecMapper {


    public UserLoginRecInfo selectUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);


    public void updateUserLoginRecInfo(UserLoginRecInfo userLoginRecInfo);

    void deleteUserLoginRecInfo(@Param(value = "uuid") String uuid);

}
