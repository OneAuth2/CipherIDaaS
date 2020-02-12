package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.Constants;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.exception.IllegalParamException;
import cipher.console.oidc.model.ApplicationSelectModel;
import cipher.console.oidc.model.ResSelectModel;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.IMenuService;
import cipher.console.oidc.service.RoleInfoService;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.SimpleDateFormat;
import java.util.*;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

@Controller
@RequestMapping("/cipher/roleman")
@EnableAutoConfiguration
public class RoleController {

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);
    @Autowired
    private RoleInfoService roleService;

    @Autowired
    private IMenuService menuService;

    @Autowired
    private RedisClient redisClient;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        return "roleMan/list";
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(Role role, DataGridModel page) throws Exception {
        return roleService.selectUserPageList(role, page);
    }

    /**
     * 跳转到新增角色界面
     *
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add() {
        System.out.println("haha");
        return "roleMan/add";
    }

    /**
     * 保存角色并跳转至列表页
     * Role role1, HttpServletResponse response,
     * @return
     */

    /**
     * 跳转到编辑角色界面
     * "/{roleId}/update"
     *
     * @return
     */
    @RequestMapping(value = "/updatePre")
    @ResponseBody
    public Map<String, Object> update(Role role1) throws Exception {
        Role role = roleService.findRoleById(role1.getRoleId());
        return successResponse("role", role);
    }

    /**
     * 编辑角色
     * /{roleId}
     *
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletResponse response, HttpServletRequest request, Role role) {
        if (null == role.getRoleId()) {
            roleService.insertRole(role);
            ResponseUtils.customSuccessResponse(response, "添加角色信息成功！");
        } else {
            int ret = roleService.updateRole(role);
            if (Constants.OPERATE_SUCCESS == ret) {
               // redisClient.flushDB();
            }
            ResponseUtils.customSuccessResponse(response, "修改角色信息成功！");
        }
    }

    /**
     * 删除角色
     *
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    public void delete(Integer roleId, HttpServletResponse response, Model model) {
        try {
            if (null != roleId) {
                roleService.deleteRole(roleId);
                ResponseUtils.customSuccessResponse(response, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }


    /**
     * 跳转到权限管理界面
     *
     * @return
     */
    @RequestMapping(value = "/addAuthPre")
    @ResponseBody
    public Map<String, Object> addAuth(Role role1) throws Exception {
        Map<String, Object> content = new HashMap<>();
        Role role = roleService.findRoleById(role1.getRoleId());
//      ModelAndView res = new ModelAndView("roleman/addAuth");
        RoleMenu record = new RoleMenu();
        record.setRoleId(role1.getRoleId());
        List<MenuForm> authList = roleService.selectAuthList(record);
        if (null != authList && authList.size() > 0) {
            List<String> resIds = new ArrayList<>();
            for (MenuForm domain1 : authList) {
                resIds.add(String.valueOf(domain1.getResourceId()));
            }
            String str = String.join(",", resIds);
            content.put("authList", str);
        }
        content.put("roleName", role.getRoleName());
        content.put("roleId", role1.getRoleId());
        return successResponse("content", content);
    }


    @RequestMapping(value = "/addAuth", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addApp(RoleMenu form) {
        Map<String, Object> map = new HashMap<>();
        try {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(form.getRoleId());
            roleService.deleteRoleMenu(roleMenu);
            String authList = form.getAuthList();
            if (StringUtils.isNotEmpty(authList)) {
                String[] authIds = authList.split(",");
                for (int i = 0; i < authIds.length; i++) {
                    RoleMenu domain = new RoleMenu();
                    domain.setResourceId(Integer.valueOf(authIds[i]));
                    domain.setRoleId(form.getRoleId());
                    roleService.insertSelective(domain);
                }
            }
            map.put("return_msg", "操作成功");
            map.put("return_code", ConstantsCMP.ReturnCode.SUCCESS);

        } catch (Exception e) {
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.ReturnCode.FAIL);
            e.printStackTrace();
        }
        return map;
    }

    @RequestMapping(value = "/common", params = "json")
    @ResponseBody
    public List<ResSelectModel> ResSelect(HttpServletResponse response) {
        return roleService.ResSelect();
    }

    @RequestMapping(value = "/menu", params = "json")
    public List<MenuForm> queryMenuforms() {
        MenuForm form = new MenuForm();
        List<MenuForm> result = null;
        try {
            result = menuService.findMenuForms(form);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 检验form信息，检测form 信息中NotNull属性是否有空值 检查 角色名是否已存在，
     *
     * @param role
     * @return
     * @throws Exception
     */
    private boolean verifyFormInfo(Role role) throws Exception {
        if (role != null && role.getRoleName() != null && role.getRoles() != null) {
            // 角色名是否存在
            Role role1 = roleService.findRoleByName(role);
            if (role1 != null) {
                return false;// 存在
            }
            return true;
        }
        // 信息不完全
        throw new IllegalParamException("role form information not complete.");
    }

    /**
     * 根据role_name 动态获取角色
     *
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping(value = "/content", method = RequestMethod.GET)
    public void findlableFormList(HttpServletRequest request, HttpServletResponse response, Model model) {
        String roleName = request.getParameter("q");
        String defaultValues = request.getParameter("defaultValues");

        StringBuffer jsonData = new StringBuffer("[");
        try {
            Role role = new Role();
            role.setRoleName(roleName);
            List<Role> list = roleService.queryRoleList(role);
            if (list != null && list.size() > 0) {
                for (Role role1 : list) {
                    jsonData.append("{");
                    jsonData.append("\"roleId\":\"" + role1.getRoleId() + "\",");
                    if (defaultValues != null) {
                        String[] roleNames = defaultValues.split(";");
                        if (roleNames.length > 0) {
                            for (int i = 0; i < roleNames.length; i++) {
                                String string = roleNames[i];
                                if (role.getRoleName().equals(string)) {
                                    jsonData.append("\"selected\":\"" + true + "\",");
                                }

                            }
                        }
                    }
                    jsonData.append("\"roleName\":\"" + role.getRoleName() + "\"},");
                }
                jsonData.delete(jsonData.toString().length() - 1, jsonData.toString().length());
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        jsonData.append("]");
        ResponseUtils.renderJson(response, jsonData.toString());
    }

}
