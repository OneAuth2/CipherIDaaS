package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.domain.web.RoleGroupMapDomain;
import cipher.console.oidc.model.ApplicationModel;
import cipher.console.oidc.model.GroupApplicationModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleGroupMapMapper {


    public RoleGroupMapDomain gerRoleGroupMapInfo(RoleGroupMapDomain form);

    public void insertRoleGroupMap(RoleGroupMapDomain form);

    public void deleteRoleGroupMap(RoleGroupMapDomain form);


    public List<RoleGroupMapDomain> getRoleGroupMapList(RoleGroupMapDomain form);

    public List<RoleGroupMapDomain> selectHaveRoleGroupList(@Param(value = "groupId") int groupId);


    public List<ApplicationInfoDomain> selectGroupAppList(@Param(value = "groupId") int groupId);


    public List<GroupApplicationModel> selectGroupApplicationList(@Param(value = "groupId") int groupId);


}
