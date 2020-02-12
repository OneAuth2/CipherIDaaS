package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;

import cipher.console.oidc.model.UserInfoModel;
import cipher.console.oidc.service.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Created by 95744 on 2018/9/25.
 */

@Controller
@RequestMapping(value = "/cipher/casual")
@EnableAutoConfiguration
public class CasualUserController {
    private static final Logger logger = LoggerFactory.getLogger(NewUserInfoController.class.getSimpleName());

    @Autowired
    private CasualUserService casualUserService;
    @Autowired
    private GroupService groupService;
    @Autowired
    private AuthorizationMethodService authorizationMethodService;

    @Autowired
    private RoleGroupMapService roleGroupMapService;

    @Autowired
    private OrganitionTreeSerive organitionTreeSerive;

    @Autowired
    private ApplicationGroupService applicationGroupService;



    @Autowired
    private AppService appService;

    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> appPage( @RequestParam(value = "groupId",required = false) String groupId,
                                       @RequestParam(value = "accountNumber",required = false) String accountNumber,
                                       HttpServletRequest request)
    {
        //FIXME:FIXED-诗昭
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String,Object> map = new HashMap<>();
        map.put("accountNumber", accountNumber);
        GroupInfoDomain groupInfoDomain=null;
        if(!StringUtils.isEmpty(groupId)){
             groupInfoDomain=groupService.getGroupById(Integer.valueOf(groupId));
            List<TreeNodesDomain> tree = organitionTreeSerive.getCasualGroupList(companyId);
            List<TreeNodesDomain> groupTrees = new ArrayList<TreeNodesDomain>();
            groupService.getGroupStruct(groupTrees,tree,Integer.valueOf(groupId));
            Collections.reverse(groupTrees);
            map.put("groupList", groupTrees);
            map.put("groupId", groupId);
        }else {
            groupInfoDomain = groupService.getGroupByParentId(0);
            if (null != groupInfoDomain && !StringUtils.isEmpty(String.valueOf(groupInfoDomain.getGroupId()))) {
                map.put("groupId", groupInfoDomain.getGroupId());
                map.put("groupName", groupInfoDomain.getGroupName());
            }
        }
        if(StringUtils.isNotBlank(groupId)) {
            List<RoleGroupMapDomain> userHaveRole = roleGroupMapService.selectHaveRoleGroupList(Integer.valueOf(groupId));
            String roleNames = "";
            List<String> roleNameList = new ArrayList<>();
            for (RoleGroupMapDomain roleGroupMapDomain : userHaveRole) {
                roleNameList.add(roleGroupMapDomain.getRoleName());
                roleNames = org.apache.commons.lang3.StringUtils.join(roleNameList, ",");
            }
            map.put("roleNames", roleNames);
        }
        return map;
    }


    @RequestMapping(value = "/getlist", params = "json")
    @ResponseBody
    public Map<String, Object> querynewData(HttpServletResponse response, DataGridModel pageModel, CasualUserInfo form) {
        logger.debug("Enter NewUserInfoController.queryData");
      /*  System.out.println(form.getQueryName());
        System.out.println(form.getStatus());*/
        return casualUserService.getCasualUserList(form, pageModel);
    }


    @RequestMapping(value = "/userlist")
    @ResponseBody
    public Map<String,Object> userlist(String groupId, HttpServletRequest request)

    {
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        //FIXME:FIXED-诗昭
        List<RoleGroupMapDomain> userHaveRole=roleGroupMapService.selectHaveRoleGroupList(Integer.valueOf(groupId));
        String roleNames="";
        List<String> roleNameList=new ArrayList<>();
        for(RoleGroupMapDomain roleGroupMapDomain:userHaveRole){
            roleNameList.add(roleGroupMapDomain.getRoleName());
            roleNames= org.apache.commons.lang3.StringUtils.join(roleNameList, ",");
        }
        Map<String,Object> map = new HashMap<>();
        map.put("groupId", groupId);
        List<TreeNodesDomain> tree = organitionTreeSerive.getCasualGroupList(companyId);
        List<TreeNodesDomain> groupTrees = new ArrayList<TreeNodesDomain>();
        groupService.getGroupStruct(groupTrees,tree,Integer.valueOf(groupId));
        Collections.reverse(groupTrees);
        map.put("groupList", groupTrees);
        map.put("roleNames",roleNames);
        return map;
    }


    @RequestMapping(value = "/userlist", params = "json")
    @ResponseBody
    public Map<String, Object> userlist(HttpServletResponse response, DataGridModel pageModel, CasualUserInfo form) {
        logger.debug("Enter NewUserInfoController.queryData");
        return casualUserService.getCasualUserListByGroupId(form, pageModel);
    }

    /**
     * 向前端返回视图并返回数据
     * @param accountNumber
     * @return
     */
    @RequestMapping(value = "/detail")
    @ResponseBody
    public Map<String,Object> detailPage(@RequestParam(value = "accountNumber") String accountNumber,HttpServletRequest request) {
        //FIXME:FIXED-诗昭
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String,Object> map = new HashMap<>();
        map.put("groups", groupService.getAllGroup(companyId));
        map.put("methods", authorizationMethodService.queryAllAuthorizationMethod());
        map.put("applications", appService.queryAllApplicationNameAndId());
        return  map;
    }



    @RequestMapping(value = "/add")
    @ResponseBody
    public Map<String,Object> newadd(@RequestParam(value = "groupId", required = false) Integer groupId) {
        //FIXME:FIXED-诗昭
        if (logger.isDebugEnabled()){
            logger.debug("turn to add on the userAdd, accountNumber=[{}]",new Object[]{groupId});
        }
        Map<String,Object> map = new HashMap<>();
        map.put("groupId", groupId);
        map.put("groupName", groupService.getGroupById(groupId).getGroupName());
        map.put("applications", applicationGroupService.selectAllGroupApplicationByAdd(groupId));
        if (logger.isDebugEnabled()){
            logger.debug("turn to add on the newadd,modelAndView=[{}]",new Object[]{map.toString()});
        }
        return map;
    }

    @RequestMapping(value = "/update")
    @ResponseBody
    public Map<String,Object> newupdate(@RequestParam(value = "accountNumber", required = false, defaultValue = "") String accountNumber,
                                  @RequestParam(value = "groupId", required = false) Integer groupId) {
        //FIXME:FIXED-诗昭
        if (logger.isDebugEnabled()){
            logger.debug("turn to /newupdate on the newupdate,accountNumber=[{}],groupId=[{}]",new Object[]{accountNumber,groupId});
        }
        accountNumber = accountNumber.replace("/", "");

        Map<String,Object> map = new HashMap<>();

        map.put("accountNumber",accountNumber);
        List<GroupInfoDomain> grouplist=groupService.getGroupNamesById(accountNumber);
        List<String> groupNamelist=new ArrayList<>();
        String groupNames="";
        for(GroupInfoDomain groupInfoDomain:grouplist){
            groupNamelist.add(groupInfoDomain.getGroupName());
            groupNames= org.apache.commons.lang3.StringUtils.join(groupNamelist, ",");
        }
        List<ApplicationInfoDomain> applications = null;
        GroupInfoDomain groupInfoDomain= groupService.getGroupById(groupId);
        if(org.springframework.util.StringUtils.isEmpty(groupId)){
            map.put("groupNames", groupNames);
        }else {
            applications = applicationGroupService.getApplicationByAccountNumberAndGroupId(new UserInfoModel(accountNumber,groupId));
            if (applications == null){
                applications = new ArrayList<ApplicationInfoDomain>();
            }
            map.put("groupNames", groupNames);
            map.put("groupId",groupId);
            map.put("groupName",groupInfoDomain.getGroupName());
            map.put("applications",applications);
            map.put("allapplications", applicationGroupService.selectAllGroupApplication(groupId,accountNumber,applications));
        }
        map.put("groupNames", groupNames);
        return map;
    }




    @RequestMapping(value = "/detail/add")
    @ResponseBody
    public Map<String,Object> detailObject(@RequestParam(value = "accountNumber") String accountNumber){
        accountNumber=accountNumber.substring(1,accountNumber.length()-1);//获取字符串主场号
        return casualUserService.getCasualUser(accountNumber);

    }

    @RequestMapping(value = "/list", params = "json")
    @ResponseBody
    public Map<String, Object> queryData(HttpServletResponse response, DataGridModel pageModel, CasualUserInfo form) {
        logger.debug("Enter NewUserInfoController.queryData");
        return casualUserService.getCasualUserList(form,pageModel);
    }


}
