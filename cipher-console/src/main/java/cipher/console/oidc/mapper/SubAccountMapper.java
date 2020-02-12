package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.web.SubAccountDomain;
import cipher.console.oidc.domain.web.SubAccountInfoDomain;
import cipher.console.oidc.domain.web.SubAccountMapDomain;
import cipher.console.oidc.model.ApplicationSubAccountSubTableModel;
import cipher.console.oidc.model.MainSubAppAccountModel;
import cipher.console.oidc.model.SubAccountApplicationModel;
import cipher.console.oidc.model.SubAccountAuthModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SubAccountMapper {

    SubAccountDomain getTheSubAccount(@Param("subAccount")String sub_account,
                     @Param("appClientId")String app_client_id);

    List<ApplicationSubAccountSubTableModel> getSubAccountApplication(
            @Param(value = "accountName")String account_number,
            @Param(value = "sidx")String sidx,
            @Param(value = "sord")String sord);

    List<ApplicationSubAccountSubTableModel> getUnAuthorizedUserApplication(  @Param(value = "accountName")String account_number,
                                                                              @Param(value = "sidx")String sidx,
                                                                              @Param(value = "sord")String sord);

    void disconnectWithSubAccount(@Param(value = "account_number")String account_number,
                                  @Param(value = "sub_account")String sub_account);


    /**
     * 从账号列表
     * @param form
     * @return
     */
    List<MainSubAppAccountModel> subAccountList(MainSubAppAccountModel form);

    /**
     * 账号列表总的数据条数
     * @param form
     * @return
     */
    int subAccountListTotal(MainSubAppAccountModel form);


    /**
     * 从账号授权的列表
     * @param form
     * @return
     */
    List<SubAccountAuthModel> querySubAuthPageList(SubAccountAuthModel form);

    /**
     * 从账号授权列表的数据条数
     * @param form
     * @return
     */
    int querySubAuthPageListTotal(SubAccountAuthModel form);


    /**
     * 插入一条关联记录
     * @param form
     * @throws Exception
     */
    void insertSubAccountMap(SubAccountMapDomain form) throws Exception;


    /**
     * 删除一条子账号和主账号的关联记录
     * @param form
     * @throws Exception
     */
    void deleteSubAccountMap(SubAccountAuthModel form) throws Exception;

    /**
     * 根据子账号的账号地址和appclientId查询子账号信息,只去一条
     * @param domain
     * @return
     */
    SubAccountDomain querySubAccount(SubAccountExcel domain);

    /**
     * 插入一条导入的记录，并返回生成的主键id
     * @param domain
     * @return
     */
    Integer insertSubAccount(SubAccountExcel domain);

    /**
     * 查询某应用下的所有子账户
     * @param subAccountApplicationModel
     * @return
     */
    List<SubAccountApplicationModel> querySubAccountByApplicationId(SubAccountApplicationModel subAccountApplicationModel);

    /**
     * 查询某应用下的所有子账户数据条数
     * @return
     */
    int countAppSubAccount();



    SubAccountDomain querySubAccountInfo(@Param(value = "uuid")String uuid,
                                         @Param(value = "appClientId")String appClientId);

    List<SubAccountDomain> querySubAccountInfoByAccountNumber(String accountNumber);



    void insertByBatch(List<SubAccountDomain> subAccountDomain);

    int insertSubAccountInfo(SubAccountInfoDomain form) throws Exception;

    String selectAssAccountIdByAppId(@Param(value = "applicationId") String applicationId);

    void deleteAssAccountId(@Param(value = "applicationId") String applicationId, @Param(value = "assAccountIdByAppId")String assAccountIdByAppId);

    /**
     * 根据appClientId和子账号查询密码
     * @param appClientId
     * @param subAccount
     * @return
     */
    public String querySubPwdByClientAndName(@Param("appClientId") String appClientId,@Param("subAccount") String subAccount);


    public void updateSubAccountInfo(SubAccountDomain form);

    String selectSubByuuidAndAppId(@Param(value = "uuid")String uuid,
                                   @Param(value = "appClientId")String appClientId);

}
