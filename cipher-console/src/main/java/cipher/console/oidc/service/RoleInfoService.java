package cipher.console.oidc.service;



import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.ApplicationSelectModel;
import cipher.console.oidc.model.ResSelectModel;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


public interface RoleInfoService {
  public List<Role> selectRoleList() throws Exception;

  public List<Role> queryAllrole(Role role);

  public Map<String, Object> selectUserPageList(Role role,DataGridModel page) throws Exception;

  public int modifyRole(Role role) throws Exception;

  public Role findRoleById(int id) throws Exception;

  public Role findRoleByName(Role role) throws Exception;

  public int removeRole(Role role) throws Exception;

  public List<UserRole> findUserRoles(Role role) throws Exception;

  List<Role> queryRoleList(Role role);

  int insertRole(Role role);

  List<ResSelectModel> ResSelect();
  public void saveRoleMenus(List<RoleMenu> forms) throws Exception;

  public int removeRoleMenu(RoleMenu form) throws Exception;

  void insertRoleMenus(List<RoleMenu> forms) throws Exception;

  void insertSelective(RoleMenu roleMenu);
  RoleMenu selectRoleMenu(RoleMenu form) throws Exception;

  int deleteRole(int roleId) throws Exception;

  int deleteRoleMenu(RoleMenu form) throws Exception;

  List<MenuForm> selectAuthList(RoleMenu roleMenu);

  int updateRole(Role role);

  Role queryRole(Role role);

  List<UserRole> queryUserRoles(Role role);

  int queryCipherCount(Role role );

  RoleMenu getRoleMenusByRoleId(ResourceManInfo resourceManInfo, Role role);

  public void deleteRoleByMenuId(int id) throws Exception;
}
