package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.BaseRoleDomain;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.ApplicationSelectModel;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

/**
 * Created by 95744 on 2018/9/20.
 */


@Controller
@RequestMapping(value = "/cipher/role")
@EnableAutoConfiguration
public class RoleInfoController {


    private static final Logger LOGGER = LoggerFactory.getLogger(RoleInfoController.class);
    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RoleInfoDomainService roleInfoDomainService;

    @Autowired
    private RoleUserMapService roleUserMapService;

    @Autowired
    private RoleGroupMapService roleGroupMapService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private AppService appService;

    @Autowired
    private RoleApplicationMapInfoService roleApplicationMapInfoService;

    @Autowired
    private ApplicationGroupService applicationGroupService;



    @RequestMapping(value = "/list", params = "json")
    @ResponseBody
    public Map<String, Object> queryData(HttpServletResponse response, DataGridModel pageModel, RoleInfoDomain form) {
        LOGGER.debug("Enter SubAccountController.queryData");
        return roleInfoDomainService.selectRoleInfoList(form, pageModel);
    }


    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String, Object> update(RoleInfoDomain form) {
        RoleInfoDomain roleInfoDomain = roleInfoDomainService.selectRoleInfo(form);
        return successResponse("roleInfo", roleInfoDomain);
    }

    @RequestMapping(value = "/addOrUpdate")
    @ResponseBody
    public void addOrUpdate(HttpServletResponse response, RoleInfoDomain form, HttpServletRequest request) {
        LOGGER.debug("Enter RoleInfoController.addOrUpdate");
        try {
            if (null == form.getId()) {
                roleInfoDomainService.insertSelective(form);
            } else {
                roleApplicationMapInfoService.deleteByRoleId(form.getId());
                roleInfoDomainService.updateByPrimaryKeySelective(form);
            }
            if (StringUtils.isNotEmpty(form.getApplications())) {
                String[] appList = form.getApplications().split(",");
                for (int i = 0; i < appList.length; i++) {
                    RoleApplicationMapInfo domain = new RoleApplicationMapInfo();
                    domain.setRoleId(form.getId());
                    domain.setApplicationId(Integer.valueOf(appList[i]));
                    roleApplicationMapInfoService.insertSelective(domain);
                }
            }

            try {
                String accountNumber = ConstantsCMP.getCipherUuidInfo(request);
                String companyId=ConstantsCMP.getSessionCompanyId(request);


            } catch (Exception e) {
                e.printStackTrace();
            }
            ResponseUtils.customSuccessResponse(response, "操作成功！");
        } catch (Exception e) {
            e.getMessage();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }


    @RequestMapping(value = "/getAppList", params = "json")
    @ResponseBody
    public Map<String, Object> getAppList(HttpServletResponse response, Integer id) {
        Map<String, Object> map = new HashedMap();
        if (null == id) {
            List<ApplicationSelectModel> userHaveRoleList = appService.queryAppSelect();
            map.put("userNonRole", userHaveRoleList);
            return map;
        }
        List<RoleApplicationMapInfo> userHaveRole = roleApplicationMapInfoService.getRoleApplicationMapList(id);
        List<Integer> list = new ArrayList<>();
        List<ApplicationSelectModel> userNonRole = null;
        if (null != userHaveRole && userHaveRole.size() > 0) {
            for (RoleApplicationMapInfo roleInfo : userHaveRole) {
                list.add(roleInfo.getApplicationId());
            }
            userNonRole = appService.queryAppNotSelect(list);
            map.put("userNonRole", userNonRole);
            map.put("userHaveRole", userHaveRole);
        } else {
            List<ApplicationSelectModel> userHaveRoleList = appService.queryAppSelect();
            map.put("userNonRole", userHaveRoleList);
        }
        return map;
    }


    @RequestMapping(value = "/delete")
    @ResponseBody
    public void delete(HttpServletResponse response, Integer id) {
        try {
            RoleUserMapInfo form = new RoleUserMapInfo();
            form.setRoleId(id);
            List<RoleUserMapInfo> roleUserMapList = roleUserMapService.selectUserMapList(form);
            if (null != roleUserMapList && roleUserMapList.size() > 0) {
                ResponseUtils.customFailueResponse(response, "当前权限组正在被使用中，不能删除");
            } else {
                roleInfoDomainService.deleteByPrimaryKey(id);
                roleApplicationMapInfoService.deleteByRoleId(id);
                ResponseUtils.customSuccessResponse(response, "操作成功！");

            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }

    }



    @RequestMapping(value = "/auth")
    @ResponseBody
    public Map<String, Object> auth(@RequestParam(value = "id") String id) {

        return successResponse("roleId", id);
    }


    @RequestMapping(value = "/doAuth")
    @ResponseBody
    public void doAuth(HttpServletResponse response, @RequestParam(value = "groupId", required = false) String groupId,
                       @RequestParam(value = "accountNumber", required = false) String accountNumber,
                       @RequestParam(value = "roleId", required = false) int roleId
    ) {

        RoleGroupMapDomain roleGroupMapDomain = new RoleGroupMapDomain();
        roleGroupMapDomain.setRoleId(roleId);
        roleGroupMapService.deleteRoleGroupMap(roleGroupMapDomain);
        roleUserMapService.deleteRoleUserMap(roleId);
        if (StringUtils.isNotEmpty(groupId)) {
            String[] groupIds = groupId.split(",");
            for (int i = 0; i < groupIds.length; i++) {
                RoleGroupMapDomain domain = new RoleGroupMapDomain();
                domain.setRoleId(roleId);
                domain.setGroupId(Integer.valueOf(groupIds[i]));
                RoleGroupMapDomain ss = roleGroupMapService.gerRoleGroupMapInfo(domain);
                if (null == ss) {
                    roleGroupMapService.insertRoleGroupMap(domain);
                }
            }

            if (StringUtils.isNotBlank(accountNumber) && !accountNumber.equals("undefined")) {
                String[] accountNumbers = accountNumber.split(",");
                for (int i = 0; i < accountNumbers.length; i++) {
                    RoleUserMapInfo mm = new RoleUserMapInfo();
                    mm.setRoleId(roleId);
                    mm.setUserId(accountNumbers[i]);
                    RoleUserMapInfo ss = roleUserMapService.getRoleUserInfo(mm);
                    if (null == ss) {
                        roleUserMapService.insertRoleUserMapInfo(mm);
                    }
                }
                ResponseUtils.customSuccessResponse(response, "操作成功！");

            }
        }
    }


    @RequestMapping(value = "/getAuth")
    @ResponseBody
    public Map<String, Object> getAuth(HttpServletResponse response, DataGridModel page, @RequestParam(value = "roleId", required = false) int roleId) {
        Map<String, Object> map = new HashMap<>();
        try {
            RoleGroupMapDomain newInfo = new RoleGroupMapDomain();
            newInfo.setRoleId(roleId);
            List<RoleGroupMapDomain> roleGroupInfo = roleGroupMapService.getRoleGroupMapList(newInfo);
            RoleUserMapInfo roleUserMapInfo = new RoleUserMapInfo();
            roleUserMapInfo.setRoleId(roleId);
            List<RoleUserMapInfo> roleUserInfo = roleUserMapService.selectUserMapList(roleUserMapInfo);
            List<BaseRoleDomain> list = new ArrayList<BaseRoleDomain>();
            list.addAll(roleGroupInfo);
            list.addAll(roleUserInfo);
            int pages = list.size() / page.getRows();
            if (pages * page.getRows() != list.size()) {
                pages++;
            }
            map.put("return_code", "1");
            map.put("total", pages);
            map.put("records", list.size());
            if (page.getPage() == pages) {
                map.put("rows", list.subList((page.getPage() - 1) * page.getRows(), list.size()));
            } else {
                map.put("rows", list.subList((page.getPage() - 1) * page.getRows(), (page.getPage()) * page.getRows()));

            }
        } catch (Exception e) {
            map.put("return_code", "0");
            map.put("return_msg", "服务器错误");
            e.printStackTrace();
        }
        return map;
    }


    @RequestMapping(value = "/getRoleList", params = "json")
    @ResponseBody
    public Map<String, Object> getRoleList(HttpServletResponse response, String accountNumber) {
        Map<String, Object> map = new HashedMap();
        if (StringUtils.isEmpty(accountNumber)) {
            List<RoleInfoDomain> userHaveRoleList = roleInfoDomainService.selectAllRoleList();
            map.put("userNonRole", userHaveRoleList);
            return map;
        }
        List<RoleInfoDomain> userHaveRole = roleUserMapService.selectHaveRoleList(accountNumber);
        List<Integer> list = new ArrayList<>();
        List<RoleInfoDomain> userNonRole = null;
        if (null != userHaveRole && userHaveRole.size() > 0) {
            for (RoleInfoDomain roleInfo : userHaveRole) {
                list.add(roleInfo.getRoleId());
            }
            userNonRole = roleInfoDomainService.selectNoneRoleList(list);
            map.put("userNonRole", userNonRole);
            map.put("userHaveRole", userHaveRole);
        } else {
            List<RoleInfoDomain> userHaveRoleList = roleInfoDomainService.selectAllRoleList();
            map.put("userNonRole", userHaveRoleList);
        }
        return map;
    }


    //修改用户权限
    @RequestMapping(value = "/addUserRole")
    @ResponseBody
    public void addUserRole(HttpServletResponse response, RoleInfoDomain form, HttpServletRequest request) {
        LOGGER.debug("Enter RoleInfoController.addOrUpdate");
        try {
            roleUserMapService.deleteRoleUserMapByAccountNumber(form.getAccountNumber());
            if (StringUtils.isNotEmpty(form.getRoleIds())) {
                String[] roleList = form.getRoleIds().split(",");
                for (int i = 0; i < roleList.length; i++) {
                    RoleUserMapInfo domain = new RoleUserMapInfo();
                    domain.setRoleId(Integer.valueOf(roleList[i]));
                    domain.setUserId(form.getAccountNumber());
                    roleUserMapService.insertRoleUserMapInfo(domain);
                }
            }


            //管理员日志
            String admin = ConstantsCMP.getCipherUuidInfo(request);
            String companyId=ConstantsCMP.getSessionCompanyId(request);

            ResponseUtils.customSuccessResponse(response, "操作成功！");
        } catch (Exception e) {
            e.getMessage();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }


    @RequestMapping(value = "/getGroupList", params = "json")
    @ResponseBody
    public Map<String, Object> getGroupList(HttpServletRequest  request, String accountNumber) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashedMap();
        if (StringUtils.isEmpty(accountNumber)) {
            List<GroupInfoDomain> groupHaveRoleList = groupService.getAllGroup(companyId);
            map.put("userNonRole", groupHaveRoleList);
            return map;
        }
        List<GroupInfoDomain> userHaveRole = userGroupService.selectHaveGroupList(accountNumber);
        List<Integer> list = new ArrayList<>();
        List<GroupInfoDomain> userNonRole = null;
        if (null != userHaveRole && userHaveRole.size() > 0) {
            for (GroupInfoDomain groupInfo : userHaveRole) {
                list.add(groupInfo.getGroupId());
            }
            userNonRole = groupService.selectNoneGroupList(list);
            map.put("userNonRole", userNonRole);
            map.put("userHaveRole", userHaveRole);
        } else {
            List<RoleInfoDomain> userHaveRoleList = roleInfoDomainService.selectAllRoleList();
            map.put("userNonRole", userHaveRoleList);
        }
        return map;
    }


    @RequestMapping(value = "/addGroup")
    @ResponseBody
    public void addGroup(HttpServletResponse response, GroupUserMapDomain form) {
        LOGGER.debug("Enter RoleInfoController.addGroup");
        try {
            userGroupService.deleteUserGroupMap(form.getAccountNumber());
            if (StringUtils.isNotEmpty(form.getGroupIds())) {
                String[] groupList = form.getGroupIds().split(",");
                for (int i = 0; i < groupList.length; i++) {
                    GroupUserMapDomain domain = new GroupUserMapDomain();
                    domain.setGroupId(Integer.valueOf(groupList[i]));
                    domain.setAccountNumber(form.getAccountNumber());
                    userGroupService.insertUserGroup(domain);
                }
            }
            ResponseUtils.customSuccessResponse(response, "操作成功！");
        } catch (Exception e) {
            e.getMessage();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }


    @RequestMapping(value = "/doRoleGroupauth")
    @ResponseBody
    public Map<String, Object> doRoleGroupauth(int groupId) {
        Map<String, Object> content = new HashMap<>();
        content.put("groupId", groupId);
        GroupInfoDomain domain = groupService.getGroupById(groupId);
        content.put("groupName", domain.getGroupName());
        return successResponse("content",content);
    }


    @RequestMapping(value = "/getRoleGroupList", params = "json")
    @ResponseBody
    public Map<String, Object> getRoleGroupList(HttpServletResponse response, Integer groupId) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(String.valueOf(groupId))) {
            List<RoleInfoDomain> groupHaveRoleList = roleInfoDomainService.selectAllRoleList();
            map.put("groupNonRole", groupHaveRoleList);
            List<GroupApplicationModel> appList = appService.queryAllApplication();
            map.put("appNonList", appList);
            return map;
        }
        List<RoleGroupMapDomain> userHaveRole = roleGroupMapService.selectHaveRoleGroupList(groupId);
        List<Integer> list = new ArrayList<>();
        List<RoleInfoDomain> userNonRole = null;
        if (null != userHaveRole && userHaveRole.size() > 0) {
            for (RoleGroupMapDomain roleInfoDomain : userHaveRole) {
                list.add(roleInfoDomain.getRoleId());
            }
            userNonRole = roleInfoDomainService.selectNoneRoleGroupList(list);
            map.put("groupNonRole", userNonRole);
            map.put("groupHaveRole", userHaveRole);
        } else {
            List<RoleInfoDomain> userHaveRoleList = roleInfoDomainService.selectAllRoleList();
            map.put("groupNonRole", userHaveRoleList);
        }
        List<GroupApplicationModel> appHaveList = applicationGroupService.selectHaveGroupList(groupId);
        List<Integer> newlist = new ArrayList<>();
        List<GroupApplicationModel> appNonList = null;
        if (null != appHaveList && appHaveList.size() > 0) {
            for (GroupApplicationModel groupApplicationModel : appHaveList) {
                if (null != groupApplicationModel && StringUtils.isNotEmpty(String.valueOf(groupApplicationModel.getId()))) {
                    newlist.add(groupApplicationModel.getId());
                }

            }
            appNonList = applicationGroupService.selectNoneGroupList(newlist);
            map.put("appNonList", appNonList);
            map.put("appHaveList", appHaveList);
        } else {
            List<GroupApplicationModel> appList = appService.queryAllApplication();
            map.put("appNonList", appList);
        }
        return map;
    }


    @RequestMapping(value = "/addRoleGroup")
    @ResponseBody
    public void addRoleGroup(HttpServletResponse response, RoleGroupMapDomain form, HttpServletRequest request) {
        LOGGER.debug("Enter RoleInfoController.addGroup");
        try {

            RoleGroupMapDomain newdomain = new RoleGroupMapDomain();
            newdomain.setGroupId(form.getGroupId());
            roleGroupMapService.deleteRoleGroupMap(newdomain);
            GroupAuthorizationMapDomain record = new GroupAuthorizationMapDomain();
            record.setGroupId(Integer.valueOf(form.getGroupId()));
            applicationGroupService.deleteGroupAuthorizationMap(record);
            if (StringUtils.isNotEmpty(form.getRoleIds())) {
                String[] list = form.getRoleIds().split(",");
                List<String> roleList = new ArrayList<>();
                List<String> appList = new ArrayList<>();
                for (int i = 0; i < list.length; i++) {
                    boolean flag = isNumeric(list[i]);
                    if (flag == true) {
                        roleList.add(list[i]);
                    } else {
                        appList.add(list[i]);
                    }
                }
                for (int i = 0; i < appList.size(); i++) {
                    GroupAuthorizationMapDomain domain = new GroupAuthorizationMapDomain();
                    String appId = appList.get(i);
                    ApplicationInfoDomain applicationInfoDomain = appService.queryApplication(appId);
                    if (null != applicationInfoDomain) {
                        domain.setApplicationId(String.valueOf(applicationInfoDomain.getId()));
                        domain.setGroupId(form.getGroupId());
                        applicationGroupService.insertGroupAuthorizationMap(domain);
                    }
                }
                for (int i = 0; i < roleList.size(); i++) {
                    RoleGroupMapDomain domain = new RoleGroupMapDomain();
                    domain.setRoleId(Integer.valueOf(roleList.get(i)));
                    domain.setGroupId(form.getGroupId());
                    roleGroupMapService.insertRoleGroupMap(domain);
                }
            }


            ResponseUtils.customSuccessResponse(response, "操作成功！");
        } catch (Exception e) {
            e.getMessage();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }


    /**
     * 利用正则表达式判断字符串是否是数字
     *
     * @param str
     * @return
     */
    public boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

}