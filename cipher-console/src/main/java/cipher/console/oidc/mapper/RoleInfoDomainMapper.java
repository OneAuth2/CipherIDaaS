package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.RoleInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleInfoDomainMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RoleInfoDomain record);

    int insertSelective(RoleInfoDomain record);

    RoleInfoDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfoDomain record);

    int updateByPrimaryKey(RoleInfoDomain record);


    List<RoleInfoDomain> selectRoleInfoList(RoleInfoDomain record);

    int selectRoleInfoCount(RoleInfoDomain record);

    List<RoleInfoDomain> selectAllRoleList();

    List<RoleInfoDomain> selectNoneRoleList(List<Integer> list);

    List<RoleInfoDomain> selectRoleNameList(@Param(value = "accountNumber") String accountNumber);


    RoleInfoDomain selectRoleInfo(RoleInfoDomain form);

    List<RoleInfoDomain> selectNoneRoleGroupList(List<Integer> list);

}