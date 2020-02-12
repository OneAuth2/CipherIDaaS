package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.RoleInfoDomain;
import cipher.console.oidc.domain.web.RoleUserMapInfo;
import cipher.console.oidc.mapper.RoleUserMapInfoMapper;
import cipher.console.oidc.service.RoleUserMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/9/25.
 */
@Service
public class RoleUserMapServiceImpl implements RoleUserMapService{

    @Autowired
    private RoleUserMapInfoMapper roleUserMapInfoMapper;
    @Override
    public List<RoleUserMapInfo> selectUserMapList(RoleUserMapInfo form) {
        return roleUserMapInfoMapper.selectUserMapList(form);
    }

    @Override
    public RoleUserMapInfo getRoleUserInfo(RoleUserMapInfo form) {
        return roleUserMapInfoMapper.selectRoleUserInfo(form);
    }

    @Override
    public void insertRoleUserMapInfo(RoleUserMapInfo form) {
        roleUserMapInfoMapper.insertSelective(form);
    }

    @Override
    public void deleteRoleUserMap(int roleId) {
        roleUserMapInfoMapper.deleteRoleUserMap(roleId);
    }

    @Override
    public List<RoleInfoDomain> selectHaveRoleList(String accountNumber) {
        return roleUserMapInfoMapper.selectHaveRoleList(accountNumber);
    }

    @Override
    public void deleteRoleUserMapByAccountNumber(String uuid) {
        roleUserMapInfoMapper.deleteRoleUserMapByAccountNumber(uuid);
    }

    @Override
    public void deleteRoleUser(RoleUserMapInfo form) {
        roleUserMapInfoMapper.deleteRoleUser(form);
    }
}
