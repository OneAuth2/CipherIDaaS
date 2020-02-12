package cipher.console.oidc.service.impl;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.RoleInfoMapper;
import cipher.console.oidc.model.ResSelectModel;
import cipher.console.oidc.service.RoleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
public class RoleInfoServiceImpl implements RoleInfoService {


    @Autowired
    private  RoleInfoMapper roleInfoMapper;

    @Override
    public List<Role> selectRoleList() throws Exception {
        System.out.println(roleInfoMapper.queryRoleList());
        return roleInfoMapper.queryRoleList();
    }

    @Override
    public List<Role> queryAllrole(Role role) {
        System.out.println(roleInfoMapper.queryAllrole(role));
        return roleInfoMapper.queryAllrole(role);
    }

    @Override
    public Map<String, Object> selectUserPageList(Role role,DataGridModel page ) throws Exception {
        Map<String, Object> map = new HashMap<>();
        role = (role == null ? new Role() : role);
        role.setPageData(page);
        List<Role> list = this.queryAllrole(role);
        int total = roleInfoMapper.queryCipherCount(role);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public int modifyRole(Role role) throws Exception {
        return 0;
    }

    @Override
    public Role findRoleById(int id) throws Exception {
        Role role = roleInfoMapper.queryRoleById(id);
        return role;
    }

    @Override
    public Role findRoleByName(Role role) throws Exception {
        return null;
    }

    @Override
    public int removeRole(Role role) throws Exception {
        return 0;
    }

    @Override
    public List<UserRole> findUserRoles(Role role) throws Exception {
        return null;
    }

    @Override
    public void insertRoleMenus(List<RoleMenu> forms) throws Exception {
        roleInfoMapper.insertRoleMenus(forms);
    }

    @Override
    public void insertSelective(RoleMenu roleMenu) {
        roleInfoMapper.insertSelective(roleMenu);
    }

    @Override
    public RoleMenu selectRoleMenu(RoleMenu form) throws Exception {
        return roleInfoMapper.selectRoleMenu(form);
    }

    @Override
    public int deleteRole(int roleId) throws Exception {
         return roleInfoMapper.deleteRole(roleId);
    }

    @Override
    public int deleteRoleMenu(RoleMenu form) throws Exception {
          return roleInfoMapper.deleteRoleMenu(form);
    }

    @Override
    public List<MenuForm> selectAuthList(RoleMenu roleMenu) {
        return null;
    }

    @Override
    public int insertRole(Role role) {
        return  roleInfoMapper.insertRole(role);
    }

    @Override
    public List<ResSelectModel> ResSelect() {
        return roleInfoMapper.ResSelect();
    }

    @Override
    public void saveRoleMenus(List<RoleMenu> forms) throws Exception {

    }

    @Override
    public int removeRoleMenu(RoleMenu form) throws Exception {
        return 0;
    }

    @Override
    public int updateRole(Role role) {
         return roleInfoMapper.updateRole(role);
    }

    @Override
    public Role queryRole(Role role) {
        return null;
    }

    @Override
    public List<Role> queryRoleList(Role role) {
        return null;
    }

    @Override
    public List<UserRole> queryUserRoles(Role role) {
         return  roleInfoMapper.queryUserRoles(role);
    }

    @Override
    public int queryCipherCount(Role role) {
         return roleInfoMapper.queryCipherCount(role);
    }

    @Override
    public RoleMenu getRoleMenusByRoleId(ResourceManInfo resourceManInfo, Role role) {
        return roleInfoMapper.getRoleMenusByRoleId(resourceManInfo, role);
    }
    @Override
    public void deleteRoleByMenuId(int id) throws Exception {
        roleInfoMapper.deleteRoleByMenuId(id);
    }
}
