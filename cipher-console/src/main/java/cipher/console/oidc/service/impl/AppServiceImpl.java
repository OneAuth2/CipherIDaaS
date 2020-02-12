package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.NullCacheObject;
import cipher.console.oidc.domain.subapp.SubAccountDownDomain;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.ApplicationSelectModel;
import cipher.console.oidc.model.ApplicationSubAccountModel;
import cipher.console.oidc.model.ApplicationUserCountModel;
import cipher.console.oidc.model.GroupApplicationModel;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import cipher.console.oidc.util.NumberUtil;
import cipher.console.oidc.util.PageUtils;
import com.google.gson.Gson;
import com.sun.org.apache.bcel.internal.generic.NEW;
import edu.hziee.common.lang.StringUtil;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/5/29 9:22
 */
@Service
public class AppServiceImpl implements AppService {
    @Autowired
    private TeamInfoMapper teamInfoMapper;

    @Autowired
    private AppMapper appMapper;

    @Autowired
    private TeamApplicationMapMapper teamApplicationMapMapper;

    @Autowired
    private SubAccountMapper subAccountMapper;

    @Autowired
    private SubAccountDownMapper subAccountDownMapper;


    @Autowired
    private NewUserService newUserService;

    @Autowired
    private RoleApplicationMapInfoMapper roleApplicationMapInfoMapper;

    @Autowired
    private ApplicationGroupMapper applicationGroupMapper;

    @Autowired
    private GroupMapper groupMapper;
    @Autowired
    private GroupService groupService;
    @Autowired
    private RoleGroupMapMapper roleGroupMapMapper;

    @Autowired
    private RoleUserMapInfoMapper roleUserMapInfoMapper;

    @Autowired
    private TeamInfoService teamInfoService;

    @Autowired
    private RedisClient<String,Object> redisClient;

    @Autowired
    private AutoSyncService autoSyncService;


    @Override
    public ApplicationInfoDomain getApplication(ApplicationInfoDomain form) {
        return appMapper.getApplication(form);
    }

    @Override
    public int getaccountSonRule(String applicationId) {
        return appMapper.getaccountSonRule(applicationId);
    }

    @Override
    public Map<String, Object> getPageList(ApplicationInfoDomain form, DataGridModel pageModel) {
//        1.分页查询所有应用及其应用的分组信息
        form = (form == null ? new ApplicationInfoDomain() : form);
        form.setPageData(pageModel);
        List<ApplicationInfoDomain> list = appMapper.queryPageList(form);
        int count = appMapper.queryPageCount(form);
        Map<String, Object> map = new HashMap<>();
        map.put("total", count);

//        2.根据每个应用统计能看到该应用的人数
        List<ApplicationUserCountModel> appCount = appMapper.selectAppUserCount();
        if (CollectionUtils.isEmpty(appCount)) {
            map.put("rows", list);
            return map;
        }

        String groupName = "";

        for (ApplicationInfoDomain domain : list) {
            List<String> groupList = new ArrayList<>();
            List<GroupApplicationModel> groupApplicationModelList = applicationGroupMapper.selectApplicationList(domain.getId());
            if (null != groupApplicationModelList && groupApplicationModelList.size() > 0) {
                for (GroupApplicationModel groupApplicationModel : groupApplicationModelList) {
                    groupList.add(groupApplicationModel.getGroupName());
                    groupName = org.apache.commons.lang3.StringUtils.join(groupList, ",");
                }
            } else {
                groupName = "";
            }
            AppSonAccountDomain appSonAccountDomain = new AppSonAccountDomain();
            appSonAccountDomain.setApplicationId(String.valueOf(domain.getId()));
            appSonAccountDomain.setPageData(pageModel);
            domain.setGroupName(groupName);
            List<AppSonAccountDomain> listCount = appMapper.getAccountNumberCount(appSonAccountDomain);
            domain.setUserCount(listCount.size());
        }

        map.put("rows", list);
        return map;
    }


    @Override
    public Map<String, Object> getAccountPageListByAppId(ApplicationSubAccountModel form, DataGridModel pageModel) {
        form = (form == null ? new ApplicationSubAccountModel() : form);
        form.setPageData(pageModel);
        int total = roleApplicationMapInfoMapper.getUserListCountByApplicationId(form.getApplicationId());
        List<UserInfoDomain> list = roleApplicationMapInfoMapper.getUserListByApplicationId(form);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);
        map.put("rows", list);




     /*   List<ApplicationSubAccountModel> list = appMapper.selectAccountDetail(form);
        if (CollectionUtils.isEmpty(list)) {
            Map<String,Object> respMap=new HashMap<>();
            respMap.put("rows","");
            respMap.put("total",0);
            return respMap;
        }
        int total = appMapper.selectAccountDetailTotal(form);
        Map<String, Object> map = new HashMap<>();
        map.put("total", total);

        List<UserAuthorizationMapDomain> authTypeList = userAuthorizationMapper.qeryUserAuthType();


        for (UserAuthorizationMapDomain authorizationMapDomain : authTypeList) {

            for (ApplicationSubAccountModel subAccountModel : list) {
                if (authorizationMapDomain.getAccountNumber().equals(subAccountModel.getAccountNumber())) {
                    subAccountModel.setMethodType(authorizationMapDomain.getAuthTypeCon());
                    break;
                }
            }
        }

        List<UserApplicationConcatModel> userAppConcatList = appMapper.selectUserAppConcat();
        if (CollectionUtils.isEmpty(userAppConcatList)) {
            map.put("rows", list);
            return map;
        }

        for(UserApplicationConcatModel concatModel:userAppConcatList){
            for (ApplicationSubAccountModel subAccountModel : list) {
                if(concatModel.getAccountNumber().equals(subAccountModel.getAccountNumber())){
                    subAccountModel.setAuthApp(concatModel.getAppNameConcat());
                    break;
                }
            }
        }


        map.put("rows", list);*/
        return map;
    }

    @Override
    public Map<String, Object> insertApplication(ApplicationInfoDomain form) throws Exception {
        String appKey = NumberUtil.getStringRandom(6);
        String appSercet = NumberUtil.createRandomCharData(6);
       /* if (form.getApplicationType() == 1 || form.getApplicationType() == 2) {
            ClientDetail dd = new ClientDetail();
            dd.set_class("com.auth.oauth2.clientdetails.WXBaseClientDetails");
            dd.setClientId(appKey);
            dd.setClientSecret(appSercet);
            List<String> scopeList = new ArrayList<>();
            scopeList.add("openid");
            scopeList.add("read");
            List<String> authList = new ArrayList<>();
            authList.add("authorization_code");
            authList.add("client_credentials");
            authList.add("refresh_token");
            authList.add("password");
            List<Authorities> authoritiesList = new ArrayList<>();
            Authorities authorities = new Authorities();
            authorities.set_class("org.springframework.security.core.authority.SimpleGrantedAuthority");
            authorities.setRole("ROLE_USER");
            authoritiesList.add(authorities);
            dd.setScope(scopeList);
            dd.setAuthorizedGrantTypes(authList);
            dd.setAuthorities(authoritiesList);
            mongoTemplate.save(dd);
        }*/
        form.setApplicationId(appKey);
        form.setApplicationSecrect(appSercet);
        appMapper.insertApplication(form);
        Map<String, Object> map = new HashMap<>();
        map.put("appKey", appKey);
        map.put("appSercet", appSercet);
        return map;

        //批量插入从账号
      /*  List<SubAccountDomain> subAccountList=new ArrayList<>();
        List<NewUserInfo> userInfoList=newUserMapper.geUserList();
        if(form.getSubType()==1){
           for(NewUserInfo newUserInfo:userInfoList){
               SubAccountDomain domain=new SubAccountDomain();
               domain.setSubAccount(newUserInfo.getAccountNumber());
               domain.setAppClientId(appKey);
               subAccountList.add(domain);
           }
        }else {
           for(NewUserInfo newUserInfo:userInfoList){
               SubAccountDomain domain=new SubAccountDomain();
               domain.setSubAccount(newUserInfo.getMail());
               domain.setAppClientId(appKey);
               subAccountList.add(domain);
           }
       }
        subAccountMapper.insertByBatch(subAccountList);*/

    }

    @Override
    public void insertSonRule(AllRuleInfoDomain allRuleInfoDomain) {
        appMapper.insertSonRule(allRuleInfoDomain);

    }

    @Override
    public ApplicationInfoDomain findApplicationById(ApplicationInfoDomain form) {
        return appMapper.queryApplicationById(form);
    }

    @Override
    public String associatedAccountById(Integer id) {
        return appMapper.associatedAccountById(id);
    }

    @Override
    public String autoSyncById(Integer id) {
        return appMapper.autoSyncById(id);
    }

    @Override
    public int selectCountByAppIndex(int id,int appIndex,String companyId) {
        return appMapper.selectCountByAppidx(id,appIndex,companyId);
    }

    @Override
    public void updateApplicationInfo(ApplicationInfoDomain form) throws Exception {
        appMapper.updateApplication(form);
    }

    @Override
    public Map<String, Object> queryAccount(AppSonAccountDomain appSonAccountDomain, DataGridModel page, String companyId) {
        Map<String, Object> map = new HashedMap();

        appSonAccountDomain = (appSonAccountDomain == null ? new AppSonAccountDomain() : appSonAccountDomain);
        appSonAccountDomain.setPageData(page);

        //判断从账是否已同步：0表示全部 1：表示已同步 2：表示未同步
        List<AppSonAccountDomain> newList=new ArrayList<>();
        List<AppSonAccountDomain> sysSubList=new ArrayList<>();
        List<AppSonAccountDomain> nosysSubList=new ArrayList<>();

        if(appSonAccountDomain.getIsSynchron()==1||appSonAccountDomain.getIsSynchron()==2){
            List<AppSonAccountDomain> list = appMapper.getAccountNumberAll(appSonAccountDomain);
            for (AppSonAccountDomain domain : list){
                SubAccountDownDomain subAccountDownDomain=new SubAccountDownDomain();
                subAccountDownDomain.setAppId(Integer.valueOf(appSonAccountDomain.getApplicationId()));
                subAccountDownDomain.setUserId(domain.getUuid());
                SubAccountDownDomain form= subAccountDownMapper.getSubAccountDownInfo(subAccountDownDomain);
                if(null!=form){
                    sysSubList.add(domain);

                }else {
                    nosysSubList.add(domain);

                }
            }

            if(appSonAccountDomain.getIsSynchron()==1) {
                newList.addAll(sysSubList);
            }else {
                newList.addAll(nosysSubList);
            }

            map.put("total", newList.size());

        }else {
            List<AppSonAccountDomain> alllist = appMapper.getAccountNumber(appSonAccountDomain);
            newList.addAll(alllist);
            List<AppSonAccountDomain> total = appMapper.getAccountNumberCount(appSonAccountDomain);
            map.put("total", total.size());
        }

        int pages = newList.size() / page.getRows();
        if (pages * page.getRows() != newList.size()) {
            pages++;
        }

        if (sysSubList.size() > 0 && newList.size()>0) {
            if (page.getPage() == pages) {
                newList = newList.subList((page.getPage() - 1) * page.getRows(), newList.size());
            } else {
                newList = newList.subList((page.getPage() - 1) * page.getRows(), (page.getPage()) * page.getRows());
            }
        }

        if(newList.size()>0) {
            for (AppSonAccountDomain domain : newList) {

                //获取从账号信息
                SubAccountDomain subAccountDomain = subAccountMapper.querySubAccountInfo(domain.getUuid(), domain.getApplicationId());
                if (subAccountDomain != null) {
                    domain.setSonAccountNumber(subAccountDomain.getSubAccount());
                    domain.setSubId(String.valueOf(subAccountDomain.getId()));
                }

                //判断账号是否已授权
                String accountNumber = domain.getUuid();
                String checkNumber = appMapper.queryCheckNumber(accountNumber, appSonAccountDomain.getApplicationId());
                if (checkNumber != null || checkNumber != "") {
                    domain.setCheckNumber(checkNumber);
                }

                //获取授权路径
                PathInfo pathInfo = getPath(domain.getUuid(), companyId, domain.getId());
                domain.setPathInfo(pathInfo);

                //获取账号同步的时间
                SubAccountDownDomain subAccountDownDomain = new SubAccountDownDomain();
                subAccountDownDomain.setUserId(domain.getUuid());
                subAccountDownDomain.setAppId(Integer.valueOf(appSonAccountDomain.getApplicationId()));
                SubAccountDownDomain form = subAccountDownMapper.getSubAccountDownInfo(subAccountDownDomain);
                if (null != form) {
                    domain.setIsSyschronTime(form.getCreateTime());
                } else {
                    domain.setIsSyschronTime(null);
                }
            }
            map.put("rows", newList);
        }
        return map;
    }

    @Override
    public int countApplication(ApplicationInfoDomain form) {
        return appMapper.countApplication(form);
    }

    @Override
    public List<ApplicationSelectModel> queryAppSelect() {
        return appMapper.appSelect();
    }

    @Override
    public List<ApplicationInfoDomain> queryAllApplicationNameAndId() {
        return appMapper.queryAllApplicationNameAndId();
    }

    @Override
    public Integer queryIdByName(ApplicationInfo applicationInfo) {
        return appMapper.queryIdByName(applicationInfo);
    }

    @Override
    public List<ApplicationSelectModel> queryAppNotSelect(List<Integer> list) {
        return appMapper.queryAppNotSelect(list);
    }


    @Override
    public List<ApplicationInfoDomain> queryUserApplications(String accountNumber) {
        List<ApplicationInfoDomain> rolelist = roleApplicationMapInfoMapper.selectApplication(accountNumber);
        for (ApplicationInfoDomain domain : rolelist) {
            SubAccountDomain subaccount = subAccountMapper.getTheSubAccount(domain.getAccountNumber(), domain.getApplicationId());
            domain.setSubAccount(subaccount.getSubAccount());
        }
        List<ApplicationInfoDomain> grouplist = applicationGroupMapper.selectApplication(accountNumber);
        for (ApplicationInfoDomain domain : grouplist) {
            SubAccountDomain subaccount = subAccountMapper.getTheSubAccount(domain.getAccountNumber(), domain.getApplicationId());
            domain.setSubAccount(subaccount.getSubAccount());
        }
        grouplist.addAll(rolelist);
        return grouplist;
    }

    //获取这个组下的应用包括从账号信息
    @Override
    public List<ApplicationInfoDomain> queryUserGroup(String accountNumber, int groupId) {
        List<ApplicationInfoDomain> grouplist = queryUserOwnGroupAuth(groupId, accountNumber);
        return grouplist;
    }


    //获取这个组下的应用
    @Override
    public List<ApplicationInfoDomain> queryUserOwnGroup(int groupId) {
        return applicationGroupMapper.getApplicationsByGroupId(groupId);
    }


    //获取所有权限组
    @Override
    public Set<ApplicationInfoDomain> queryUserAllAuth(String accountNumber, int groupId) {
        GroupInfoDomain groupInfoDomain = groupMapper.queryparentGroupList(groupId);
        List<RoleGroupMapDomain> roleGroupMapDomains = roleGroupMapMapper.selectHaveRoleGroupList(groupId);
        List<RoleInfoDomain> roleInfoDomains = roleUserMapInfoMapper.selectHaveRoleList(accountNumber);

        return null;
    }

    @Override
    public List<GroupApplicationModel> queryAllApplication() {
        return appMapper.queryAllApplication();
    }

    /*
    根据application获取用户名规则
     */
    @Override
    public AllRuleInfoDomain getRuleInfo(String applicationId) {
        return appMapper.getRuleInfo(applicationId);
    }

    @Override
    public ApplicationInfoDomain queryApplication(String applicationId) {
        return appMapper.queryApplication(applicationId);
    }


    //获取用户权限组拥有的权限
    public Set<ApplicationInfoDomain> queryUserRoleAuth(String accountNumber) {
        Set<ApplicationInfoDomain> list = new HashSet<>();
        List<ApplicationInfoDomain> applist = roleApplicationMapInfoMapper.selectApplication(accountNumber);
        for (ApplicationInfoDomain domain : applist) {
            SubAccountDomain subaccount = subAccountMapper.querySubAccountInfo(domain.getAccountNumber(), domain.getApplicationId());
            if (null == subaccount) {
                domain.setSubAccount(null);
            } else {
                domain.setSubAccount(subaccount.getSubAccount());
            }
        }
        list.addAll(applist);
        return list;
    }


    //获取用户多部门下拥有的权限
    public Set<ApplicationInfoDomain> queryUserManyGroupAuth(String accountNumber) {
        Set<ApplicationInfoDomain> list = new HashSet<>();
        List<ApplicationInfoDomain> applist = applicationGroupMapper.selectApplication(accountNumber);
        for (ApplicationInfoDomain domain : applist) {
            SubAccountDomain subaccount = subAccountMapper.querySubAccountInfo(domain.getAccountNumber(), domain.getApplicationId());
            if (null == subaccount) {
                domain.setSubAccount(null);
            } else {
                domain.setSubAccount(subaccount.getSubAccount());
            }
        }
        list.addAll(applist);
        return list;
    }


    //获取用户当前所在组的权限
    public List<ApplicationInfoDomain> queryUserOwnGroupAuth(int groupId, String accountNumber) {
        List<ApplicationInfoDomain> applist = applicationGroupMapper.getApplicationsByGroupId(groupId);
        for (ApplicationInfoDomain domain : applist) {
            SubAccountDomain subaccount = subAccountMapper.querySubAccountInfo(accountNumber, domain.getApplicationId());
            if (null == subaccount) {
                domain.setSubAccount(null);
            } else {
                domain.setSubAccount(subaccount.getSubAccount());
            }
        }
        return applist;
    }


    //获取上级部门所拥有的权限
    public Set<ApplicationInfoDomain> getParentGroupAtuh(List<GroupInfoDomain> parentGroupList, int groupId, String accountNumber) {
        Set<ApplicationInfoDomain> groupAuthList = new HashSet<>();
        Set<ApplicationInfoDomain> applist = queryUserManyGroupAuth(accountNumber);
        groupAuthList.addAll(applist);
        return groupAuthList;
    }

    @Override
    public void undateSonRule(AllRuleInfoDomain allRuleInfoDomain) {
        appMapper.updaSonRule(allRuleInfoDomain);
    }

    @Override
    public List<String> getAuthUserAccountNumber(String id) {

        return appMapper.getAppAuthzationUser(id);
    }

    @Override
    public List<String> getAllListAccountNumber(String id) {
        return appMapper.getAllListAccountNumber(id);
    }

    @Override
    public List<TreeNodesDomain> getUserTree(List<String> accountNumbers, String companyId) {
        List<TreeNodesDomain> trees = new ArrayList<>();
        Object obj = redisClient.get(CacheKey.getCacheGroupTreeList(companyId));
        if (obj == null) {
            obj = gerTrees(companyId);
            if (obj == null) {
                obj = new NullCacheObject();
            }
            redisClient.put(CacheKey.getCacheGroupTreeList(companyId), obj);
        }
        if (obj == null || obj instanceof NullCacheObject) {
            return null;
        }
        return constructorSelect((List<TreeNodesDomain>) obj, accountNumbers);
    }



  public  List<TreeNodesDomain> gerTrees( String companyId){
      List<GroupInfoDomain> groupInfoDomains = groupService.getAllGroup(companyId);
      List<TreeNodesDomain> userList = newUserService.getAllUserList(companyId);
      List<TreeNodesDomain> trees = new ArrayList<>();
      String parentIds = "";
     return  constructor(trees, 0, groupInfoDomains, userList, parentIds);
  }



    @Override
    public List<TreeNodesDomain> getAuthGroupTree(String applicationId, String companyId) {
        List<GroupInfoDomain> groupInfoDomains = groupService.getAllGroup(companyId);
        List<GroupApplicationModel> groupApplicationModelList = applicationGroupMapper.selectApplicationList(Integer.valueOf(applicationId));
        List<TreeNodesDomain> trees = new ArrayList<>();
        String parentIds = "";
        constructorGroup(trees, 0, groupInfoDomains, parentIds, "");
        constructorSelectGroup(trees, groupApplicationModelList);
        return trees;
    }

    /**
     * Create by 田扛
     * 获取应用安全组授权的接口，返回所有的安全组
     * create time 2019年3月8日10:14:15
     *
     * @param id
     * @return
     */
    @Override
    public Map<String, Object> getAuthTeam(String id, String companyId) {

        Map<String, Object> map = new HashedMap();
        try {
            Map<String, Object> msg = new HashedMap();
            TeamApplicationMap record = new TeamApplicationMap();
            List<TeamInfo> allTeams = teamInfoMapper.getTeamList(companyId);//获取所有安全组
            record.setApplicationId(Integer.valueOf(id));
            List<TeamInfo> teamIds = teamApplicationMapMapper.selectTeamList(record);//获取该应用授权的安全组
            List<TeamApplicationChecked> teamApplicationMaps = getCheckedTeams(allTeams, teamIds);//设置所有安全组组中该应用授权的应用
            msg.put("list", teamApplicationMaps);
            map.put("code", 0);
            map.put("msg", msg);


        } catch (Exception e) {
            map.put("code", "1");
            map.put("msg", "数据查找异常");
        }


        return map;
    }

    /**
     * create by 田扛
     * create time 2019年3月8日10:24:41
     * 获取安全组是否被选中，设置选中的state为checked
     */
    @Override
    public List<TeamApplicationChecked> getCheckedTeams(List<TeamInfo> allTeams, List<TeamInfo> checkedTeams) {
        List<TeamApplicationChecked> list = new ArrayList<>();
        for (TeamInfo teamInfo : allTeams) {    //循环判断该应用授权的安全组是否在其中，如果是就把state.checked=true
            TeamApplicationChecked teamApplicationMap = new TeamApplicationChecked(teamInfo);
            for (TeamInfo teaminfo1 : checkedTeams) {
                if (teamInfo.getId().equals(teaminfo1.getId())) {
                    teamApplicationMap.setState();
                }
            }
            list.add(teamApplicationMap);


        }
        return list;

    }


    /**
     * 初始化哪些部门被选中
     */
    public List<TreeNodesDomain> constructorSelectGroup(List<TreeNodesDomain> trees, List<GroupApplicationModel> groupApplicationModelList) {
        for (TreeNodesDomain treeNodesDomain : trees) {
            if (null != treeNodesDomain && null != treeNodesDomain.getNodes() && treeNodesDomain.getNodes().size() > 0) {
                constructorSelectGroup(treeNodesDomain.getNodes(), groupApplicationModelList);

            }
            if (groupApplicationModelList != null && groupApplicationModelList.size() > 0) {
                for (GroupApplicationModel groupApplicationModel : groupApplicationModelList) {

                    if (!StringUtil.isEmpty(treeNodesDomain.getGroupId()) && treeNodesDomain.getGroupId().equals(String.valueOf(groupApplicationModel.getGroupId()))) {
                        //             System.out.println(accountNumber);
                        treeNodesDomain.setState();
                        // System.out.println((stack.empty())+"stack2");

                    }
                }


            }

        }
        return trees;
    }


    /**
     * 初始化哪些树的节点被选中
     */
    public List<TreeNodesDomain> constructorSelect(List<TreeNodesDomain> trees, List<String> accountNumbers) {
        for (TreeNodesDomain treeNodesDomain : trees) {
            if (null != treeNodesDomain && null != treeNodesDomain.getNodes() && treeNodesDomain.getNodes().size() > 0) {
                // System.out.println(stack.size());
                constructorSelect(treeNodesDomain.getNodes(), accountNumbers);

            } else {
                if (accountNumbers != null && accountNumbers.size() > 0) {

                    for (String accountNumber : accountNumbers) {

                        if (!StringUtil.isEmpty(treeNodesDomain.getUuid()) && treeNodesDomain.getUuid().equals(accountNumber)) {
                            treeNodesDomain.setState();
                        }
                    }


                } else {

                }

            }
        }

        return trees;
    }

    /**
     * 构造部门树
     */
    public List<TreeNodesDomain> constructorGroup(List<TreeNodesDomain> trees, int parentGroupId, List<
            GroupInfoDomain> groupInfoDomains, String path, String parentGroupName) {
        TreeNodesDomain treeNodesDomain;
        for (GroupInfoDomain groupInfoDomain : groupInfoDomains) {
            treeNodesDomain = new TreeNodesDomain(groupInfoDomain);
            if (groupInfoDomain.getParentGroupId() == parentGroupId) {
                if (parentGroupId == 0) {
                    path = "";
                    parentGroupName = "";
                }

                treeNodesDomain.setPath(path);
                treeNodesDomain.setParentGroupName(parentGroupName + treeNodesDomain.getGroupName());
                treeNodesDomain.setNodes(constructorGroup(treeNodesDomain.getNodes(), groupInfoDomain.getGroupId(), groupInfoDomains, path + treeNodesDomain.getGroupId() + "/", parentGroupName + treeNodesDomain.getGroupName() + "/"));
                trees.add(treeNodesDomain);
            }

        }
        return trees;
    }


    /**
     * 构造用户树
     */
    public List<TreeNodesDomain> constructor(List<TreeNodesDomain> trees, int parentGroupId, List<GroupInfoDomain> groupInfoDomains, List<TreeNodesDomain> userList, String path) {
        TreeNodesDomain treeNodesDomain;
        for (GroupInfoDomain groupInfoDomain : groupInfoDomains) {
            treeNodesDomain = new TreeNodesDomain(groupInfoDomain);
            if (groupInfoDomain.getParentGroupId() == parentGroupId) {
                if (parentGroupId == 0) {
                    path = "";
                }

                treeNodesDomain.setPath(path);
                treeNodesDomain.setNodes(constructor(treeNodesDomain.getNodes(), groupInfoDomain.getGroupId(), groupInfoDomains, userList, path + treeNodesDomain.getGroupId() + "/"));
                trees.add(treeNodesDomain);
            }

        }
        for (TreeNodesDomain userNode : userList) {
            if (Integer.valueOf(userNode.getGroupId()) == parentGroupId) {
                userNode.setText();
                userNode.setNodes(null);
                userNode.setPath(path);
                trees.add(userNode);
            }

        }
        return trees;
    }

    /**
     * 获得该应用所有未授权的用户
     *
     * @param
     * @return
     */
    @Override
    public PageUserAuthzationDomain getAllNoAuthzation(List<String> accountNumbers, String currentPage, String
            queryName) {
        int total = appMapper.getAllNoAuthzationCount(accountNumbers, queryName);
        // System.out.println("total="+total);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setAllRowsAmount(total);
        if (currentPage != null) {
            pageUtils.setCurrentPage(Integer.parseInt(currentPage));
        }
        pageUtils.calculatePage();
        Integer startPageNum = pageUtils.getCurrentPageStartRow() - 1;
        Integer pageSize = pageUtils.getPageSize();
        List<UserInfoDomain> applicationInfoList = appMapper.getAllAuthzationUser(accountNumbers, startPageNum, pageSize, queryName);
        PageUserAuthzationDomain pageUserAuthzationDomain = new PageUserAuthzationDomain();
        pageUserAuthzationDomain.setPrevPage(pageUtils.getPrevPage());
        pageUserAuthzationDomain.setNextPage(pageUtils.getNextPage());
        pageUserAuthzationDomain.setShowUsers(applicationInfoList);
        pageUserAuthzationDomain.setShowPageNums(pageUtils.getShowPageNums());
        pageUserAuthzationDomain.setCurrentPage(pageUtils.getCurrentPage());
        return pageUserAuthzationDomain;
    }


    /**
     * 获取分页下的应用
     *
     * @param currentPage
     * @return
     */
    @Override
    public PageDomain queryAllApplications(String currentPage, String queryName) {
        int total = appMapper.getAllApplicationsNumber(queryName);
        //System.out.println(total);
        PageUtils pageUtils = new PageUtils();
        pageUtils.setAllRowsAmount(total);
        if (currentPage != null) {
            pageUtils.setCurrentPage(Integer.parseInt(currentPage));
        }
        pageUtils.calculatePage();
        Integer startPageNum = pageUtils.getCurrentPageStartRow() - 1;
        System.out.println(startPageNum);
        Integer pageSize = pageUtils.getPageSize();
        //System.out.println("startPageNum:"+startPageNum+"   pageSixe="+pageSize);
        if (currentPage.equals("1")) {
            pageSize = 6;
        }
        List<ApplicationSysInfo> applicationInfoList = appMapper.getAllApplications(startPageNum, pageSize, queryName);
        PageDomain pageDomain = new PageDomain();
        pageDomain.setPrevPage(pageUtils.getPrevPage());
        pageDomain.setNextPage(pageUtils.getNextPage());
        pageDomain.setShowUsers(applicationInfoList);
        pageDomain.setShowPageNums(pageUtils.getShowPageNums());
        pageDomain.setCurrentPage(pageUtils.getCurrentPage());

        return pageDomain;
    }

    @Override
    public String queryAppClientIdByAppSysId(Integer appSysId) {
        return appMapper.queryAppClientIdByAppSysId(appSysId);
    }


    @Override
    public void delApplication(int id, String companyId) throws Exception {
        if (id > 0 && StringUtils.isNotEmpty(companyId)) {
            //删除应用
            appMapper.delApplication(id, companyId);
        }
    }

    @Override
    public List<NewAppSonAccountDomain> getAssAccountByAppId(Integer id) {
        return appMapper.selectAssAccountByAppId(id);
    }

    @Override
    public String getApplicatinId(Integer id) {
        return appMapper.selectApplicatinId(id);
    }


    @Override
    public String getAssAccountIdByAppId(String applicationId) {
        return subAccountMapper.selectAssAccountIdByAppId(applicationId);
    }

    @Override
    public void delAssAccountIdByAppId(String applicationId, String assAccountIdByAppId) {
        if (StringUtils.isNotEmpty(applicationId) && StringUtils.isNotEmpty(assAccountIdByAppId)) {
            subAccountMapper.deleteAssAccountId(applicationId, assAccountIdByAppId);
        }
    }

    @Override
    public String getMainAccountPwd(String userId) {
        return appMapper.selectMainAccountPwd(userId);
    }

    @Override
    public int editSubAccount(SubAccountInfoDomain subAccountInfoDomain) throws Exception {
        return subAccountMapper.insertSubAccountInfo(subAccountInfoDomain);
    }

    @Override
    public void editSubAccountMap(SubAccountMapDomain subAccountMapDomain) throws Exception {
        subAccountMapper.insertSubAccountMap(subAccountMapDomain);
    }

    @Override
    public void editDownRule(Integer applicationId, Integer accountType) {
        appMapper.updateDownRule(applicationId, accountType);
    }

    @Override
    public int queryAllApplicationNumber(String companyId) {
        return appMapper.queryAllApplicationNumber(companyId);
    }

    @Override
    public Integer getAppSysIdById(Integer id) {
        return appMapper.getAppSysIdById(id);
    }

    public PathInfo getPath(String userId, String companyId, int appId) {
        PathInfo pathInfo = new PathInfo();
        GroupInfoDomain groupInfoDomain= groupService.getGroupPath(companyId,userId,appId);
        if(null!=groupInfoDomain) {
            String groupNamePath = groupInfoDomain.getGroupName() + "/";
            String idPath = "";

            while (null != groupInfoDomain && groupInfoDomain.getChildGroup() != null) {
                groupInfoDomain = groupInfoDomain.getChildGroup();
                groupNamePath += groupInfoDomain.getGroupName() + "/";
                idPath += groupInfoDomain.getGroupId() + "/";
            }

            GroupInfoDomain groupInfoPath = new GroupInfoDomain();
            groupInfoPath.setParentPathName(groupNamePath);
            groupInfoPath.setPath(idPath);
            groupInfoPath.setGroupId(groupInfoDomain.getGroupId());

            if (null != groupInfoPath) {
                pathInfo.setGroupList(groupInfoPath);
                return pathInfo;
            }
        }
        TeamInfo teamInfo = teamInfoService.getAppTeamInfo(userId, appId);
        if (null != teamInfo) {
            pathInfo.setTeamList(teamInfo);
            return pathInfo;
        }
        return null;
    }
}



