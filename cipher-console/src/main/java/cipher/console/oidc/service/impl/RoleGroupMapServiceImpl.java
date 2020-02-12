package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.RoleGroupMapDomain;
import cipher.console.oidc.mapper.RoleGroupMapMapper;
import cipher.console.oidc.service.RoleGroupMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleGroupMapServiceImpl implements RoleGroupMapService {

    @Autowired
    private RoleGroupMapMapper  roleGroupMapMapper;



    @Override
    public RoleGroupMapDomain gerRoleGroupMapInfo(RoleGroupMapDomain form) {
        return roleGroupMapMapper.gerRoleGroupMapInfo(form);
    }

    @Override
    public void insertRoleGroupMap(RoleGroupMapDomain form) {
        roleGroupMapMapper.insertRoleGroupMap(form);
    }

    @Override
    public void deleteRoleGroupMap(RoleGroupMapDomain form) {
        roleGroupMapMapper.deleteRoleGroupMap(form);
    }

    @Override
    public List<RoleGroupMapDomain> getRoleGroupMapList(RoleGroupMapDomain form) {
        return roleGroupMapMapper.getRoleGroupMapList(form);
    }

    @Override
    public List<RoleGroupMapDomain> selectHaveRoleGroupList(int groupId) {
        return roleGroupMapMapper.selectHaveRoleGroupList(groupId);
    }
}
