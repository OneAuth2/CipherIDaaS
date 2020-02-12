package cipher.console.oidc.service;



import cipher.console.oidc.domain.web.MenuForm;
import org.springframework.stereotype.Service;

import java.util.List;


public interface IMenuService {

  public MenuForm findMenuById(MenuForm form) throws Exception;

  public void modifyMenu(MenuForm form) throws Exception;

  public MenuForm findMenuByName(MenuForm form) throws Exception;

 // public List<?> findMenuTreeList(MenuForm form) throws Exception;

  public List<MenuForm> findMenuForms(MenuForm form) throws Exception;

  /*public void saveMenu(MenuForm form) throws Exception;*/
  
  public void deleteMenuById(String id) throws Exception;
}
