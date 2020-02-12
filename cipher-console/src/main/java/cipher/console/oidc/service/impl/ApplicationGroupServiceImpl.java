package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.subapp.SubAccountDownDomain;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.model.UserInfoModel;
import cipher.console.oidc.service.*;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ApplicationGroupServiceImpl implements ApplicationGroupService {

    @Autowired
    private ApplicationGroupMapper applicationGroupMapper;


    @Autowired
    private GroupService groupService;

    @Autowired
    private AppService appService;

    @Autowired
    private RoleGroupMapMapper roleGroupMapMapper;


    @Autowired
    private OrganitionTreeSerive organitionTreeSerive;

    @Autowired
    private SubAccountDownMapper subAccountDownMapper;

    @Autowired
    private SubAccountMapper subAccountMapper;

    @Autowired
    private TeamInfoService teamInfoService;




    @Override
    public List<ApplicationInfoDomain> getApplicationsByGroupId(int groupId) {
        return applicationGroupMapper.getApplicationsByGroupId(groupId);
    }

    @Override
    public List<ApplicationInfoDomain> getApplicationByAccountNumberAndGroupId(UserInfoModel userInfo) {
        List<ApplicationInfoDomain> list = applicationGroupMapper.getApplicationByAccountNumberAndGroupId(userInfo);
        return list;
    }

    @Override
    public List<GroupApplicationModel> selectAllGroupApplication(int groupId,String accountNumber,List<ApplicationInfoDomain> showApplications) {
       List<GroupApplicationModel> groupApplicationModels = applicationGroupMapper.selectAllGroupApplication();
        List<GroupApplicationModel> result = new ArrayList<GroupApplicationModel>();
        getGroupParentApplication(groupId,result,groupApplicationModels);
        List<GroupApplicationModel> newResult = new ArrayList<GroupApplicationModel>();
        for (GroupApplicationModel applicationModel : result){
            boolean add = true;
            for (GroupApplicationModel appModel : newResult){
                if (applicationModel == appModel || StringUtils.isBlank(applicationModel.getApplicationId()) || StringUtils.isBlank(appModel.getApplicationId())  ||applicationModel.getApplicationId().equals(appModel.getApplicationId())){
                    add = false;
                }
            }
            if (add){
                if (StringUtils.isNotBlank(applicationModel.getApplicationId())) {
                    newResult.add(applicationModel);
                }
            }
        }
        List<SubAccountDomain> subAccountDomains = subAccountMapper.querySubAccountInfoByAccountNumber(accountNumber);
        for (SubAccountDomain subAccountDomain : subAccountDomains){
            for (GroupApplicationModel applicationModel : newResult){
                if (applicationModel.getApplicationId().equals(subAccountDomain.getAppClientId())){
                    applicationModel.setSubAccount(subAccountDomain.getSubAccount());
                }
            }
        }
        List<GroupApplicationModel> userGroupApp = applicationGroupMapper.selectUserRoleApplication(accountNumber);
       List<GroupApplicationModel> finalResult = new ArrayList<GroupApplicationModel>();
        for (GroupApplicationModel groupApplicationModel : userGroupApp){
           if(null==groupApplicationModel){
               continue;
           }
            boolean add = true;
            for (GroupApplicationModel applicationModel : newResult){
                if (applicationModel.getApplicationId().equals(groupApplicationModel.getApplicationId())){
                    add = false;
                }
            }
            if (add){
                newResult.add(groupApplicationModel);
            }
        }
        return newResult;
    }

    @Override
    public List<GroupApplicationModel> selectUserRoleApplication(String accountNumber) {
        return applicationGroupMapper.selectAllGroupApplication();
    }






    @Override
    public List<GroupApplicationModel> selectAllGroupApplicationByAdd(int groupId) {
        List<GroupApplicationModel> groupApplicationModels = applicationGroupMapper.selectAllGroupApplication();
        List<GroupApplicationModel> result = new ArrayList<GroupApplicationModel>();
        getGroupParentApplication(groupId,result,groupApplicationModels);
        List<GroupApplicationModel> newResult = new ArrayList<GroupApplicationModel>();
        for (GroupApplicationModel applicationModel : result){
                boolean add = true;
                    for (GroupApplicationModel appModel : newResult){
                        if (applicationModel == appModel || StringUtils.isBlank(applicationModel.getApplicationId()) || StringUtils.isBlank(appModel.getApplicationId())  ||applicationModel.getApplicationId().equals(appModel.getApplicationId())){
                            add = false;
                        }
            }
            if (add){
                if (StringUtils.isNotBlank(applicationModel.getApplicationId())) {
                    newResult.add(applicationModel);
                }
            }
        }
       return newResult;

    }

    @Override
    public List<ApplicationInfo> selectAppList(GroupAuthorizationMapDomain form) {
        return applicationGroupMapper.selectAppList(form);
    }

    @Override
    public void deleteGroupAuthorizationMap(GroupAuthorizationMapDomain form) {
        applicationGroupMapper.deleteGroupAuthorizationMap(form);
    }

    @Override
    public void insertGroupAuthorizationMap(GroupAuthorizationMapDomain form) {
        applicationGroupMapper.insertGroupAuthorizationMap(form);
    }



    public void getGroupParentApplication(int groupId,List<GroupApplicationModel> result,List<GroupApplicationModel> applicationModels){
        for (GroupApplicationModel groupApplicationModel : applicationModels){
            if (groupApplicationModel.getGroupId() == groupId){
                result.add(groupApplicationModel);
                getGroupParentApplication(groupApplicationModel.getParentGroupId(),result,applicationModels);
            }
        }
    }



    @Override
    public List<GroupApplicationModel> selectNewApplicationList(String uuid,String groupId,String companyId) {
        //获取所有应用
        List<TreeNodesDomain> tree = organitionTreeSerive.getGroupList(companyId);
        List<TreeNodesDomain> groupTrees = new ArrayList<TreeNodesDomain>();
        groupService.getGroupStruct(groupTrees, tree, Integer.valueOf(groupId));
        Collections.reverse(groupTrees);
        List<GroupApplicationModel> list=new ArrayList<>();
        for(TreeNodesDomain treeNodesDomain:groupTrees){
          List<GroupApplicationModel> groupRoleApplications=roleGroupMapMapper.selectGroupApplicationList(Integer.valueOf(treeNodesDomain.getGroupId()));
          list.addAll(groupRoleApplications);
        }
        List<GroupApplicationModel> newApplicationList=applicationGroupMapper.selectNewApplicationList(uuid);
        list.addAll(newApplicationList);
        Set<String> set=new HashSet<String>();
        List<GroupApplicationModel> newList=new ArrayList<>();
        for (GroupApplicationModel groupApplicationModel : list) {
            if (groupApplicationModel == null) {
                continue;
            }
            String  applicationName = groupApplicationModel.getApplicationName();
            if (applicationName != null) {
                if (!set.contains(applicationName)) { //set中不包含重复的
                    set.add(applicationName);
                    newList.add(groupApplicationModel);
                } else {
                    continue;
                }
            }
        }
        set.clear();
        //获取从账号
        for(GroupApplicationModel groupApplicationModel:newList){
            groupApplicationModel.setUuid(uuid);
            //获取应用的从账号配置信息 1.5.6
            String associatedAccount=appService.associatedAccountById(groupApplicationModel.getId());
            if(StringUtils.isNotEmpty(associatedAccount)) {
                JSONObject jsonobject = JSONObject.fromObject(associatedAccount);
                SubAccountRuleInfo subAccountRuleInfo = (SubAccountRuleInfo) JSONObject.toBean(jsonobject, SubAccountRuleInfo.class);
                groupApplicationModel.setSubAccountRuleInfo(subAccountRuleInfo);
            }
            //获取路径
            PathInfo pathInfo=getPath(uuid,groupId,companyId,groupApplicationModel.getId());
            groupApplicationModel.setPathInfo(pathInfo);
            SubAccountDomain subAccountDomain=subAccountMapper.querySubAccountInfo(uuid,groupApplicationModel.getApplicationId());
            //获取子账号是否需要同步
            Integer appSysId=appService.getAppSysIdById(groupApplicationModel.getId());

            if(null!=appSysId &&(appSysId==1||appSysId==5||appSysId==6||appSysId==7)){
                groupApplicationModel.setIsSynchron(0);
            }else {
                groupApplicationModel.setIsSynchron(1);
            }
            //获取账号同步的时间
            SubAccountDownDomain subAccountDownDomain=new SubAccountDownDomain();
            subAccountDownDomain.setUserId(uuid);

            subAccountDownDomain.setAppId(groupApplicationModel.getId());
            SubAccountDownDomain form=subAccountDownMapper.getSubAccountDownInfo(subAccountDownDomain);
            if(null!=form){
                groupApplicationModel.setIsSyschronTime(form.getCreateTime());
            }else {
                groupApplicationModel.setIsSyschronTime(null);
            }

            if(null!=subAccountDomain&& StringUtils.isNotEmpty(subAccountDomain.getSubAccount())){
              groupApplicationModel.setSubAccount(subAccountDomain.getSubAccount());
              groupApplicationModel.setSubId(subAccountDomain.getId());
           }

        }
        return newList;
    }

    @Override
    public List<GroupApplicationModel> selectHaveGroupList(int groupId) {
        return applicationGroupMapper.selectHaveApplicationList(groupId);
    }

    @Override
    public List<GroupApplicationModel> selectNoneGroupList(List<Integer> list) {
        return applicationGroupMapper.selectNoneApplicationList(list);
    }

    @Override
    public GroupAuthorizationMapDomain selectGroupAuthorizationMap(GroupAuthorizationMapDomain form) {
        return applicationGroupMapper.selectGroupAuthorizationMap(form);
    }




    public PathInfo getPath(String userId,String groupId,String companyId,int appId){
        GroupInfoDomain groupInfoDomain= groupService.getGroupPath(companyId,userId,appId);
        PathInfo pathInfo=new PathInfo();
        if(null!=groupInfoDomain){
        String groupNamePath=groupInfoDomain.getGroupName()+"/";
        String idPath="";

        while (null!=groupInfoDomain && groupInfoDomain.getChildGroup() != null){
            groupInfoDomain = groupInfoDomain.getChildGroup();
            groupNamePath += groupInfoDomain.getGroupName()+"/";
            idPath +=groupInfoDomain.getGroupId()+"/";
        }

        GroupInfoDomain groupInfoPath=new GroupInfoDomain();
        groupInfoPath.setParentPathName(groupNamePath);
        groupInfoPath.setPath(idPath);
        groupInfoPath.setGroupId(groupInfoDomain.getGroupId());

        if(null!=groupInfoPath){
             pathInfo.setGroupList(groupInfoPath);
             return pathInfo;
        }
        }
        TeamInfo teamInfo=teamInfoService.getAppTeamInfo(userId,appId);
        if(null!=teamInfo){
            pathInfo.setTeamList(teamInfo);
            return pathInfo;
        }
        return null;
    }

}
