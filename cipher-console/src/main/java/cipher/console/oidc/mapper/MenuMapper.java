package cipher.console.oidc.mapper;


import cipher.console.oidc.domain.web.MenuForm;

import java.util.List;

public interface MenuMapper {
  /**
   * 查询分页内容
   * 
   * @param form
   * @return
   * @throws Exception
   */
  public List<?> selectMenuTreeList(MenuForm form) throws Exception;

  public List<MenuForm> selectMenuForms(MenuForm form) throws Exception;

  MenuForm queryMenuForm(MenuForm form);

  int updateMenuForm(MenuForm form);

  int insertMenuForm(MenuForm form);
  
  public void deleteMenuById(String id);
  
}
