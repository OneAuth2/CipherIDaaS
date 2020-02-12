package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.ResSelectModel;

import java.util.List;
import java.util.Map;

public interface RoleInfoMapper {

  /**
   * 查询分页内容
   * 
   * @param page
   * @param role
   * @return
   * @throws Exception
   */
/*
  public List<Role> selectUserPageList(DataGridModel page, Role role) throws Exception;
*/

  /**
   * 保存角色的权限
   * 
   * @param forms
   * @return
   * @throws Exception
   */
  void insertRoleMenus(List<RoleMenu> forms) throws Exception;

  /**
   * 查询角色的权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  RoleMenu selectRoleMenu(RoleMenu form) throws Exception;

  /**
   * 删除角色
   * 
   * @param
   * @return
   * @throws Exception
   */
  int deleteRole(int roleId) throws Exception;

  /**
   * 删除角色的权限
   * 
   * @param form
   * @return
   * @throws Exception
   */
  int deleteRoleMenu(RoleMenu form) throws Exception;

  int insertRole(Role role);

  int updateRole(Role role);

  Role queryRole(Role role);

   List<Role> queryRoleList();

   List<Role> queryAllrole(Role role);

  List<UserRole> queryUserRoles(Role role);
  
  public void deleteRoleByMenuId(int id);

  int queryCipherCount(Role role);

  Role queryRoleById(int id);

  public void insertSelective(RoleMenu roleMenu);

  List<MenuForm> selectAuthList(RoleMenu roleMenu);

  List<ResSelectModel> ResSelect();
  RoleMenu getRoleMenusByRoleId(ResourceManInfo resourceManInfo,Role role);
  
}
