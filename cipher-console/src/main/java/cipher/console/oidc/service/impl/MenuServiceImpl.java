package cipher.console.oidc.service.impl;


import cipher.console.oidc.domain.web.MenuForm;
import cipher.console.oidc.mapper.MenuMapper;
import cipher.console.oidc.service.IMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MenuServiceImpl implements IMenuService {

  private MenuMapper menuDAO;

  @Override
  public MenuForm findMenuById(MenuForm form) throws Exception {
    return menuDAO.queryMenuForm(form);
  }

  @Override
  public void modifyMenu(MenuForm form) throws Exception {
    menuDAO.updateMenuForm(form);
  }

  @Override
  public MenuForm findMenuByName(MenuForm form) throws Exception {
    return menuDAO.queryMenuForm(form);
  }

 /* @Override
  public List<?> findMenuTreeList(MenuForm form) throws Exception {
    // Map<String, Object> result = menuDAO.selectMenuPageList(page, form);
    List<?> result = menuDAO.selectMenuTreeList(form);
    for (Object o : result) {
      if (o instanceof MenuForm) {
        MenuForm tmp = (MenuForm) o;
        if ("0".equals(tmp.getState())) {
          tmp.setState("open");
        } else {
          tmp.setState("closed");
        }
      }
    }
    return result;
  }*/

/*  @Override
  public void saveMenu(MenuForm form) throws Exception {
    // 展示类型(1:模块 2:菜单 3:功能)
    if (form != null && "1".equals(form.getDisplayType())) {
      form.setParent(0);
    }
    menuDAO.insertMenuForm(form);
  }*/

  @Override
  public List<MenuForm> findMenuForms(MenuForm form) throws Exception {
    return menuDAO.selectMenuForms(form);
  }

  public MenuMapper getMenuDAO() {
    return menuDAO;
  }

  public void setMenuDAO(MenuMapper menuDAO) {
    this.menuDAO = menuDAO;
  }

  @Override
  public void deleteMenuById(String id) throws Exception {
    menuDAO.deleteMenuById(id);
  }

}
