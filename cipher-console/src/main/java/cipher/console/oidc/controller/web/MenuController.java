package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.Constants;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.ResMenuService;
import cipher.console.oidc.service.RoleInfoService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/cipher/menu")
public class MenuController {

  private static final Logger logger = LoggerFactory.getLogger(MenuController.class);
  
  @Autowired
  private RoleInfoService roleInfoService;
  
  @Autowired
  private ResMenuService resMenuService;



    //一进来展示所有子菜单
  @RequestMapping(value = "/hahalist",method = RequestMethod.POST)
  @ResponseBody
  public Map<String, Object> list(MenuForm menuForm, DataGridModel pageModel) {
    logger.debug("Enter NewUserInfoController.queryData");
    System.out.println("哈哈");
  return resMenuService.selectMenuTreeList(menuForm, pageModel);
  }



    @RequestMapping(value = "/pmlist",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> pmlist(HttpServletResponse response, DataGridModel pageModel,
                                      @RequestParam(value = "resourceId", required = false) String resourceId) {
        logger.debug("Enter NewUserInfoController.queryData");
        System.out.println("啦啦啦");
        MenuForm menuForm=new MenuForm();
        menuForm.setResourceId(Integer.valueOf(resourceId));
        return resMenuService.getMenuListByParentId(pageModel,menuForm);
    }



    //组装成树
  @RequestMapping(value = "/menulist")
  @ResponseBody
  public List<MenuTreeNodesDomain> queryData(HttpServletResponse response) {
        // System.out.println("嘻嘻");
        return resMenuService.getAllMenuList();
    }



  @RequestMapping(value = "/update", method = RequestMethod.POST)
  public void update(MenuForm menuForm, HttpServletResponse response, Model model) {
     // MenuForm domain=resMenuService.queryMenuForm(menuForm.getResourceId());
      System.out.println(menuForm.toString());
      if(null==menuForm.getResourceId()){
      System.out.println("add前");
      if ("1".equals(menuForm.getDisplayType())) {
        menuForm.setParent(0);
        ResponseUtils.customSuccessResponse(response, "添加模块信息成功！");
          resMenuService.insertResMenu(menuForm);
      }
    }else{
       if("2".equals(menuForm.getDisplayType())){
         menuForm.setParent(menuForm.getResourceId());
         resMenuService.insertResMenu(menuForm);
         ResponseUtils.customSuccessResponse(response, "添加菜单信息成功！");
       }
       if("0".equals(menuForm.getParent())){
         resMenuService.updateResMenu(menuForm);
         ResponseUtils.customSuccessResponse(response, "修改模块信息成功！");
       }
       else{
         resMenuService.updateResMenu(menuForm);
         ResponseUtils.customSuccessResponse(response, "修改菜单信息成功！");
       }
    }
  }


  /**
   * 删除菜单
   * 
   * @return
   */
  @RequestMapping(value = "/delete", method = RequestMethod.POST)
  public void delete(MenuForm menuForm, HttpServletResponse response, Model model) {
    logger.debug("menuId -> " + menuForm.getResourceId());
    try {
      if(resMenuService.selectMenuListByParent(menuForm).size()!=0){
        ResponseUtils.customFailueResponse(response, "不能删除含有菜单的模块！");
      }
      else{
        resMenuService.deleteResMenu(menuForm.getResourceId());
       roleInfoService.deleteRoleByMenuId(menuForm.getResourceId());
        ResponseUtils.customSuccessResponse(response, "删除成功！");
      }

    } catch (Exception e) {
      e.printStackTrace();
      ResponseUtils.customFailueResponse(response, "服务器错误！");
    }
  }







  /*
  * 获取菜单树的接口
  * （数据隔离修改）
  *  TODO
  *  需要调整
  * */

  @RequestMapping(value = "/getAllMenuList")
  @ResponseBody
  public List<MenuInfoDomain> getAllMenuList(HttpServletResponse response) {

    return resMenuService.getAllMenuListNew();
  }



}
