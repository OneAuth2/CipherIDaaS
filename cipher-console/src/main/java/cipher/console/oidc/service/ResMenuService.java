package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.MenuForm;
import cipher.console.oidc.domain.web.MenuInfoDomain;
import cipher.console.oidc.domain.web.MenuTreeNodesDomain;
import cipher.console.oidc.domain.web.RoleMenu;

import java.util.List;
import java.util.Map;

public interface ResMenuService {
    public void deleteResMenu(int id);

    public void saveMenu(MenuForm form);

    public  int insertResMenu(MenuForm menuForm);

    public  int updateResMenu(MenuForm menuForm);

    public List<MenuTreeNodesDomain> getAllMenuList();

    List<MenuForm> selectMenuListByParent(MenuForm menuForm);

    public List<MenuForm> getNowListByResourceId(MenuForm menuForm);

    public Map<String, Object> getMenuListByParentId(DataGridModel pageModel,MenuForm menuForm);

    public List<MenuForm> selectAll(MenuForm menuForm);

    public Map<String, Object> selectMenuTreeList(MenuForm menuForm,DataGridModel pageModel);

    List<MenuForm> selectMenuForms(MenuForm menuForm);

    public List<MenuTreeNodesDomain> getAllRootList();

    public int getRootStruct(List<MenuTreeNodesDomain> result, List<MenuTreeNodesDomain> list, int resourceId);

    List<MenuForm> selectRootMenu(int resourceId);

    public List<MenuForm> selectByParent(MenuForm menuForm);

    MenuForm queryMenuForm(int id);

    List<MenuForm>getListByResourceId(MenuForm menuForm);

    List<RoleMenu> selectRoleMenuList(int id);

    List<MenuInfoDomain> getAllMenuListNew();
}
