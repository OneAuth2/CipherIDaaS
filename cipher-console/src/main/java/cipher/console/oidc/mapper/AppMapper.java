package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/5/29 9:12
 */
public interface AppMapper {
    /**
     * 更新账号同步规则
     */
    void updaSonRule(AllRuleInfoDomain allRuleInfoDomain);

    /**
     * 获取所有该应用直接授权到人的accoutNumbers
     */
     List<String> getAppAuthzationUser( @Param(value = "id")String id);

    /**
     * 获取应用信息根据应用名称
     */

    public ApplicationInfoDomain getApplication(ApplicationInfoDomain form);

    /**
     * 查询应用信息，及其每个应用所在的组的信息。
     *
     * @param form
     * @return
     */
    List<ApplicationInfoDomain> queryPageList(ApplicationInfoDomain form);

    /**
     * 查询子账号下发规则是否存在
     */

    int getaccountSonRule(@Param(value = "applicationId") String applicationId);


    /**
     * 根据applicationID查询子账号的命名规则
     */
    AllRuleInfoDomain getRuleInfo(@Param(value = "applicationId") String applicationId);

    /**
     * 下发子账号的字段规则
     *
     * @param
     * @param
     */
    public void insertSonRule(AllRuleInfoDomain allRuleInfoDomain);

    /**
     * 通过ApplicationId查找绑定的主账号以及子账号的信息
     */

    List<AppSonAccountDomain> getAccountNumber(AppSonAccountDomain appSonAccountDomain);

    List<AppSonAccountDomain> getSubAccountList(AppSonAccountDomain appSonAccountDomain);

    /**
     * 通过applicationId查找主账号以及子账号的总数
     */
    List<AppSonAccountDomain> getAccountNumberCount(AppSonAccountDomain appSonAccountDomain);


    /**
     * 判断某个应用的某个账号是否授权
     */
    String queryCheckNumber(@Param("accountNumber") String accountNumber, @Param("applicationId") String applicationId);

    /**
     * 在某个条件下统计应用的总数
     *
     * @param form
     * @return
     */
    int queryPageCount(ApplicationInfoDomain form);

    /**
     * 统计能看到每个应用的人数
     *
     * @return
     */
    List<ApplicationUserCountModel> selectAppUserCount();

    /**
     * 根据应用id查询所有能看到该应用的用户信息
     *
     * @param form
     * @return
     */
    List<ApplicationSubAccountModel> selectAccountDetail(ApplicationSubAccountModel form);

    /**
     * 获取所有已集成的应用
     *
     * @param
     * @return
     */

    List<ApplicationSysInfo> getAllApplications(@Param(value = "startPageNum") Integer startPageNum, @Param(value = "pageSize") Integer pageSize, @Param(value = "queryName") String queryName);

    int selectAccountDetailTotal(ApplicationSubAccountModel form);

    /**
     * 查询所有用户和用户能看到的应用拼接
     *
     * @return
     */
    List<UserApplicationConcatModel> selectUserAppConcat();


    /**
     * 插入一个新的应用
     *
     * @param form
     * @throws Exception
     */
    void insertApplication(ApplicationInfoDomain form) throws Exception;


    /**
     * 根据id查询应用信息
     *
     * @param form
     * @return
     */
    ApplicationInfoDomain queryApplicationById(ApplicationInfoDomain form);

    /**
     * 根据id查询从账号配置
     * @param id
     * @return
     */
    String associatedAccountById(@Param(value = "id") Integer id);

    /**
     * 根据id查询自动同步配置
     * @param id
     * @return
     */
    String autoSyncById(@Param(value = "id") Integer id);

    /**
     * 根据应用序号查出是否发生重复
     * @param appIndex
     * @return
     */
    int selectCountByAppidx(@Param(value = "id")int id,@Param(value = "appIndex") int appIndex,@Param(value = "companyId") String companyId);

    /**
     * 通过id查出自动定时同步信息
     * @param id
     * @return
     */
    AutoSyncAppDomain selectAutoSyncById(Integer id);

    /**
     * 更新一个应用
     *
     * @param form
     * @throws Exception
     */
    void updateApplication(ApplicationInfoDomain form) throws Exception;


    /**
     * 统计应用重复情况
     * 名称，url不能重复
     *
     * @param form
     * @return
     */
    int countApplication(ApplicationInfoDomain form);

    /**
     * 应用的下拉选择框
     *
     * @return
     */
    List<ApplicationSelectModel> appSelect();


    /**
     * 获取所有应用的ID和名称
     *
     * @return
     */
    List<ApplicationInfoDomain> queryAllApplicationNameAndId();


    /**
     * 根据应用名称查找应用的主键id
     *
     * @param applicationInfo
     * @return
     */
    Integer queryIdByName(ApplicationInfo applicationInfo);


    List<ApplicationSelectModel> queryAppNotSelect(List<Integer> list);

    List<GroupApplicationModel> queryAllApplication();

    List<String> getAllListAccountNumber(@Param(value = "id") String id);

    int getAllNoAuthzationCount(@Param(value = "accountNumbers") List<String> accountNumbers,
                                @Param(value = "queryName") String queryName);//获得所有该应用id未授权的用户

    /**
     * 根据id查询应用信息
     *
     * @param
     * @return
     */
    ApplicationInfoDomain queryApplication(@Param(value = "applicationId") String applicationId);

    List<UserInfoDomain> getAllAuthzationUser(@Param(value = "accountNumbers") List<String> accountNumbers, @Param(value = "startPageNum") Integer startPageNum, @Param(value = "pageSize") Integer pageSize,
                                              @Param(value = "queryName") String queryName);

    int getAllApplicationsNumber(@Param(value = "queryName") String queryName);


    /**
     * 根据appSysId查询应用clientId
     *
     * @param appSysId
     * @return
     */
    public String queryAppClientIdByAppSysId(Integer appSysId);



    public  List<AppSonAccountDomain> getAppList(AppSonAccountDomain appSonAccountDomain);


    public int getAppCount(AppSonAccountDomain appSonAccountDomain);

    public   SubAccountDomain getSonAppAccount(AppSonAccountDomain appSonAccountDomain);

    void delApplication(@Param(value = "id") int id,@Param(value = "companyId") String companyId) throws Exception;

    List<NewAppSonAccountDomain> selectAssAccountByAppId(@Param(value = "id")Integer id);

    String selectApplicatinId(@Param(value = "id")Integer id);

    Integer selectAssAccountIdByUserid(@Param(value = "userId") String userId,@Param(value = "applicationId") String applicationId);

    void updateAssAccount(@Param(value = "id") Integer id, @Param(value = "param") String param);

    String selectMainAccountPwd(@Param(value = "userId") String userId);

    void updateAssAccountPwd(@Param(value = "id") Integer id, @Param(value = "password") String password);

    void updateDownRule(@Param(value = "applicationId") Integer applicationId, @Param(value = "accountType") Integer accountType);

    /**
     * 通过ApplicationId查找绑定的主账号以及子账号的所有信息
     */

    List<AppSonAccountDomain> getAccountNumberAll(AppSonAccountDomain appSonAccountDomain);


    Integer getAppSysIdById(Integer id);//查出从账号配置


    int queryAllApplicationNumber(String companyId);
}
