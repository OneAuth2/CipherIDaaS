package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.MenuForm;
import cipher.console.oidc.domain.web.MenuInfoDomain;
import cipher.console.oidc.domain.web.MenuTreeNodesDomain;
import cipher.console.oidc.domain.web.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ResMenuMapper {

    public void deleteResMenu(int id);

    public  int insertResMenu(MenuForm menuForm);

    public  int updateResMenu(MenuForm menuForm);

    public List<MenuForm> selectMenuList(MenuForm menuForm);

    public List<MenuForm> selectAllRootName();

    public List<MenuTreeNodesDomain> selectAllChild();

    List<MenuForm> selectMenuForms();

    List<MenuForm> selectRootMenu(int resourceId);

    List<MenuForm> selectChildByParent(MenuForm menuForm);

    int selectMenuInfoCount(MenuForm menuForm);

    MenuForm queryMenuForm(int id);

    List<MenuForm> selectMenuListByParent(MenuForm menuForm);

    List<MenuForm> selectAllRoot();

    List<RoleMenu> selectRoleMenuList( int resourceId);

    List<MenuInfoDomain> selectAllChildNew();

    List<MenuInfoDomain> selectAllRootNew();
}
