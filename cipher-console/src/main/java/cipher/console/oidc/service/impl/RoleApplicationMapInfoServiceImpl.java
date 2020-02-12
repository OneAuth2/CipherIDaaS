package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.RoleApplicationMapInfo;
import cipher.console.oidc.mapper.RoleApplicationMapInfoMapper;
import cipher.console.oidc.service.RoleApplicationMapInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/9/20.
 */

@Service
public class RoleApplicationMapInfoServiceImpl implements RoleApplicationMapInfoService {
    @Autowired
    private RoleApplicationMapInfoMapper  roleApplicationMapInfoMapper;


    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleApplicationMapInfoMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(RoleApplicationMapInfo record) {
        return roleApplicationMapInfoMapper.insertSelective(record);
    }

    @Override
    public RoleApplicationMapInfo selectByPrimaryKey(Integer id) {
        return roleApplicationMapInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(RoleApplicationMapInfo record) {
        return roleApplicationMapInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public List<RoleApplicationMapInfo> getRoleApplicationMapList(Integer roleId) {
        return roleApplicationMapInfoMapper.selectApplicationName(roleId);
    }

    @Override
    public int deleteByRoleId(Integer RoleId) {
        return roleApplicationMapInfoMapper.deleteByRoleId(RoleId);
    }
}
