package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.RoleGroupMapDomain;

import java.util.List;

public interface RoleGroupMapService {


    public RoleGroupMapDomain gerRoleGroupMapInfo(RoleGroupMapDomain form);

    public void insertRoleGroupMap(RoleGroupMapDomain form);

    public void deleteRoleGroupMap(RoleGroupMapDomain form);

    public List<RoleGroupMapDomain> getRoleGroupMapList(RoleGroupMapDomain form);

    public List<RoleGroupMapDomain>selectHaveRoleGroupList(int groupId);
}
