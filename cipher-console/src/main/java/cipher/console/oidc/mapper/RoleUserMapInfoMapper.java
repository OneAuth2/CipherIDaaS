package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.RoleInfoDomain;
import cipher.console.oidc.domain.web.RoleUserMapInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleUserMapInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleUserMapInfo record);

    int insertSelective(RoleUserMapInfo record);

    RoleUserMapInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleUserMapInfo record);

    int updateByPrimaryKey(RoleUserMapInfo record);

    List<RoleUserMapInfo> selectUserMapList(RoleUserMapInfo form);

    RoleUserMapInfo selectRoleUserInfo(RoleUserMapInfo form);

   void deleteRoleUserMap(int roleId);

    List<RoleInfoDomain> selectHaveRoleList(@Param(value = "accountNumber") String accountNumber);

    public void deleteRoleUserMapByAccountNumber(@Param(value = "uuid") String uuid);

    public void deleteRoleUser(RoleUserMapInfo form);


}