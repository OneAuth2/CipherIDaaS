package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.ApplicationSelectModel;
import cipher.console.oidc.model.ApplicationSubAccountModel;
import cipher.console.oidc.model.GroupApplicationModel;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @Author: zt
 * @Date: 2018/5/29 9:22
 */
public interface AppService {
    void undateSonRule(AllRuleInfoDomain allRuleInfoDomain);

    List<String> getAuthUserAccountNumber(String id);


    List<String> getAllListAccountNumber(String id);

    List<TreeNodesDomain> getUserTree(List<String> accountNumbers,String companyId);

    List<TreeNodesDomain> getAuthGroupTree(String id,String companyId);

    Map<String,Object> getAuthTeam(String id,String companyId);
    public List<TeamApplicationChecked>  getCheckedTeams(List<TeamInfo> allTeams,List<TeamInfo> checkedTeams);

    public PageUserAuthzationDomain getAllNoAuthzation(List<String> id, String currentPage, String queryName);

    public ApplicationInfoDomain getApplication(ApplicationInfoDomain form);

    public int getaccountSonRule(String applicationId);


    public Map<String, Object> getPageList(ApplicationInfoDomain form, DataGridModel pageModel);

    public Map<String, Object> getAccountPageListByAppId(ApplicationSubAccountModel form, DataGridModel pageModel);

    public Map<String, Object> insertApplication(ApplicationInfoDomain form) throws Exception;

    public void insertSonRule(AllRuleInfoDomain allRuleInfoDomain);//插入子账号的命名规则

    public ApplicationInfoDomain findApplicationById(ApplicationInfoDomain form);

    String associatedAccountById(Integer id);//查出从账号配置

    String autoSyncById(Integer id);//查出自动同步配置

    int selectCountByAppIndex(int id,int appIndex,String companyId);

    public void updateApplicationInfo(ApplicationInfoDomain form) throws Exception;

    public Map<String, Object> queryAccount(AppSonAccountDomain appSonAccountDomain, DataGridModel model,String companyId);//应用绑定的主账号和子账号

    int countApplication(ApplicationInfoDomain form);

    List<ApplicationSelectModel> queryAppSelect();

    List<ApplicationInfoDomain> queryAllApplicationNameAndId();

    Integer queryIdByName(ApplicationInfo applicationInfo);


    List<ApplicationSelectModel> queryAppNotSelect(List<Integer> list);


    List<ApplicationInfoDomain> queryUserApplications(String accountNumber);

    List<ApplicationInfoDomain> queryUserGroup(String accountNumber, int groupId);


    List<ApplicationInfoDomain> queryUserOwnGroup(int groupId);

    Set<ApplicationInfoDomain> queryUserAllAuth(String accountNumber, int groupId);

    List<GroupApplicationModel> queryAllApplication();

    AllRuleInfoDomain getRuleInfo(String applicationId);


    /**
     * 根据id查询应用信息
     *
     * @param
     * @return
     */
    ApplicationInfoDomain queryApplication(String applicationId);


    /**
     * 获取应用分页
     * 参数currentPage
     */
    PageDomain queryAllApplications(String currentPage, String queryName);

    /**
     * 根据appSysId查询应用clientId
     *
     * @param appSysId
     * @return
     */
    public String queryAppClientIdByAppSysId(Integer appSysId);

    /**
     * 根据应用id删除应用以及相关人，部门，安全组
     * @param id
     * @param companyId
     */
    void delApplication(int id,String companyId) throws Exception;

    /**
     * 根据应用id查出主从账号
     * @param id
     * @return
     */
    List<NewAppSonAccountDomain> getAssAccountByAppId(Integer id);

    String getApplicatinId(Integer id);

    /**
     * 通过applicationId查出该应用下的从账号
     * @param applicationId
     * @return
     */
    String getAssAccountIdByAppId(String applicationId);

    void delAssAccountIdByAppId(String applicationId,String assAccountIdByAppId);

    String getMainAccountPwd(String userId);

    int editSubAccount(SubAccountInfoDomain subAccountInfoDomain) throws Exception;

    void editSubAccountMap(SubAccountMapDomain subAccountMapDomain) throws Exception;

    void editDownRule(Integer applicationId,Integer accountType);

    Integer getAppSysIdById(Integer id);//查出从账号配置

    int queryAllApplicationNumber(String companyId);
}
