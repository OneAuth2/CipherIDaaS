package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.UserApplicationMapInfo;import org.apache.ibatis.annotations.Param;

public interface UserApplicationMapInfoMapper {
     int deleteUserAuth(Integer applicationId);
    int deleteByPrimaryKey(Integer id);

    int insert(@Param("record")UserApplicationMapInfo record);

    int insertSelective(UserApplicationMapInfo record);

    UserApplicationMapInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserApplicationMapInfo record);

    int updateByPrimaryKey(UserApplicationMapInfo record);

    UserApplicationMapInfo selectUserApplicationInfo(UserApplicationMapInfo record);
}
