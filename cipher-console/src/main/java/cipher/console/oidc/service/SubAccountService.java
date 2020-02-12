package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
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
import java.util.Map;


/**
 * 子账户管理的service
 * @author sizhao、zhoutao
 * */
public interface SubAccountService {

    /**
     * 根据用户名和应用ID获取特定的子账户
     * @param
     * @param app_client_id 应用唯一Id
     * @return 子账户信息
     * */
    SubAccountDomain getTheSubAccount(String sub_account,
                                     String app_client_id);


    /**
     * 获取子账号列表所需要的所有应用信息
     * @param account_number 主账号
     * @return 应用的信息列表
     * */
    List<ApplicationSubAccountSubTableModel> getSubAccountApplication(String account_number,String sidx,String sord);

    /**
     * 获取主账号未授权的子账户的应用信息
     * @param account_number 主账号
     * @return 未授权的信息
     * */
    List<ApplicationSubAccountSubTableModel> getUnAuthorizedUserApplication(String account_number,String sidx,String sord);


    /**
     * 取消主账号和子账户的关联关系
     * @param account_number 主账号
     * @param sub_account 子账户
     * */
    void disconnectWithSubAccount(@Param(value = "account_number")String account_number,
                                  @Param(value = "sub_account")String sub_account);


    /**
     * 获取子账号表格的数据
     * @param pageModel 表格的分页信息
     * @param form 查询的条件
     * @return 子账号表格数据
     * */
    Map<String,Object> subAccountPageList(DataGridModel pageModel,MainSubAppAccountModel form);

    /**
     * 获取子账号授权信息的表格数据
     * @param pageModel 表格的分页信息
     * @param form 查询的条件
     * @return 子账号授权信息列表
     * */
    Map<String,Object> subAccountAuthPageList(DataGridModel pageModel, SubAccountAuthModel form);

    /**
     * 插入子账号和主账号关联信息
     * @param form 关联信息
     * */
    void insertSubAccountMap(SubAccountMapDomain form) throws Exception;

    /**
     * 删除子账号和主账号的关联信息
     * @param form 要删除的信息
     * */
    void deleteSubAccountMap(SubAccountAuthModel form) throws Exception;

    /**
     *  查询所有的子账号
     *  @param domain 子账号数据（Excel标准）
     * */
    SubAccountDomain querySubAccount(SubAccountExcel domain);

    /**
     * 插入excl导入的子账号
     * @param domain 子账号的数据（Excel标准）
     * */
    void insertSubAccount(SubAccountExcel domain);


    /**
     * 根据应用Id查询子账号的信息
     * @param subAccountApplicationModel 查询条件
     * @param dataGridModel 分页信息
     * */
    Map<String,Object> querySubAccountByApplicationId(SubAccountApplicationModel subAccountApplicationModel,DataGridModel dataGridModel);

    List<SubAccountDomain> querySubAccountInfoByAccountNumber(String accountNumber);
    int insertSubAccountInfo(SubAccountInfoDomain form) throws Exception;

    /**
     * 根据appClientId和子账号查询密码
     * @param appClientId
     * @param subAccount
     * @return
     */
    public String querySubPwdByClientAndName(String appClientId,String subAccount);



    public void updateSubAccountInfo(SubAccountDomain form);


}
