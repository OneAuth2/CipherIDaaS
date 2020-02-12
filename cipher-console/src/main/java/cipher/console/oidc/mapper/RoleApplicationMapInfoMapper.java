package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.RoleApplicationMapInfo;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.ApplicationSubAccountModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleApplicationMapInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleApplicationMapInfo record);

    int insertSelective(RoleApplicationMapInfo record);

    RoleApplicationMapInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleApplicationMapInfo record);

    int updateByPrimaryKey(RoleApplicationMapInfo record);

    List<RoleApplicationMapInfo> selectApplicationName(@Param(value = "roleId") int roleId);

    int deleteByRoleId(@Param(value = "roleId") int roleId);


    List<ApplicationInfoDomain> selectApplication(@Param(value = "accountNumber") String accountNumber);


    Integer getUserAccount(@Param(value = "roleId") int roleId);

    List<UserInfoDomain> getUserListByApplicationId(ApplicationSubAccountModel applicationSubAccountModel);

    int getUserListCountByApplicationId(@Param(value = "applicationId") int applicationId );
    int getUserListCountByApplicationId_1( int applicationId );

}