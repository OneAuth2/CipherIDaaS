package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.NewUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UpdateUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UserInfoExcel;
import cipher.console.oidc.domain.web.CustomPropertiesInfo;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import cipher.console.oidc.domain.web.AdUserInfoDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt, sz
 * @Date: 2018/5/28 19:33
 */
public interface UserMapper {
    /**
     * create by 田扛
     * create time 2019年3月21日15:52:56
     * 查看是否绑定赛赋认证
     *
     * @param
     * @return
     */
    int getSaifubinding(String uuid);

    int deleteAdmin(UserManagementModel userManagementModel);

    int getDabbyAccount(@Param(value = "uuid") String uuid);

    int getDingDingAccount(@Param(value = "uuid") String uuid);

    /**
     * 获取绑定AD的对应账号
     *
     * @param uuid
     * @return
     */
    String getAdBindAccount(@Param(value = "uuid") String uuid);

    /**
     * 获取所有用户信息
     *
     * @return
     */
    List<UserInfoDomain> getAllUser();

    /**
     * 根据用户的账户名获取用户信息
     *
     * @param uuid 用户名
     * @return
     */
    UserInfoDomain getUserByAccountNumber(@Param(value = "uuid")String uuid);

    UserInfoDomain getUserByNewAccountNumber(@Param(value = "accountNumber") String accountNumber, @Param(value = "companyId") String companyId);

    UserInfoDomain getUserInfo(String accountNumber);

    /**
     * 获取用户与组的关系表
     *
     * @return
     */
    List<UserManagementTableModel> getAllUserGroupMap();

    /**
     * 获取账号总数
     *
     * @return
     */
    int getUserCount();

    /**
     * 统计已经过期的账号的数量
     *
     * @return
     */
    int authOutOfDateCount();

    /**
     * 统计被禁用的账号数量
     *
     * @return
     */
    int authBanCount();

    /**
     * 更新用户的状态
     */
    void updateAccountStatus(UserManagementModel user);

    /**
     * 查询用户表格所需的用户所有信息
     */
    List<UserManagementModel> queryUserTableMapper(UserManagementModel model);

    /**
     * 查询用户表格的信息总数
     */
    int getUserPageDataCount(UserManagementModel userManagementModel);

    /**
     * 查询未授权用户的用户名
     *
     * @return
     */
    List<String> queryNotAuthAccount();

    /**
     * 查询欢迎页各种条件下的列表
     *
     * @param form
     * @return
     */
    List<UserManagementModel> queryWelcomeUserPageList(@Param(value = "form") UserManagementModel form,
                                                       @Param(value = "nameList") List<String> nameList);

    int queryWelcomeUserPageListTotal(@Param(value = "form") UserManagementModel form,
                                      @Param(value = "nameList") List<String> nameList);

    /**
     * 统计临时授权已经过期的用户名
     *
     * @return
     */
    List<String> queryTmpAuthOutOfDate();

    /**
     * 4.查询授权过期的用户名
     *
     * @return
     */
    List<String> queryAuthOutOfDate();

    List<UserSubAccountModel> querySubAccountApp(GroupUserMapDomain groupUserMapDomain);


    /**
     * 从AD域中导入用户时先查找已经存在的用户
     *
     * @param list
     * @return
     */
    List<UserInfoDomain> queryRepeatAccount(List<AdUserInfoDomain> list);

    /**
     * 插入从Ad域中导入的用户列表
     *
     * @param list
     */
    void insertAdUserList(List<AdUserInfoDomain> list);


    /**
     * 主账号导入时，查询已经存在的账号的账号名
     *
     * @param list
     * @return
     */
    List<UserInfoDomain> queryRepeatNameAccountList(List<UserInfoExcel> list);

    /**
     * 用户批量导入
     *
     * @param excelList
     */
    void insertUserInfoExcel(List<UserInfoExcel> excelList);


    /**
     * 获取所有的用户名
     *
     * @return
     */
    List<AccountInfoModel> queryAllUser();

    /**
     * 根据账户名删除
     *
     * @return
     */
    int deleteByAccountNumber(GroupUserMapDomain groupUserMapDomain);

    /**
     * 增加用户
     *
     * @return
     */
    int insertAccountInMap(GroupUserMapDomain groupUserMapDomain);


    /**
     * SessionUtil
     * 增加用户
     *
     * @return
     */
    int insetIntoUserTable(UserInfoModel userInfoModel);

    /**
     * 更新用户
     *
     * @return
     */
    int updateUserTable(UserInfoModel userInfoModel);


    /**
     * 查询管理员表格所需的用户所有信息
     */
    List<UserManagementModel> queryAdminTableMapper(UserManagementModel model);

    /**
     * 查询管理员表格的信息总数
     */
    int getAdminPageDataCount(UserManagementModel userManagementModel);

    public void updateAdmin(UserManagementModel userManagementModel);

    /**
     * 查询普通员表格的信息总数
     */
    int getOrignPageDataCount(UserManagementModel userManagementModel);

    List<UserManagementModel> AllQueryOrignTableMapper(String companyId);

    List<UserManagementModel> queryOrignTableMapper(UserManagementModel model);

    List<AdUserInfoDomain> queryUserByGrouId(Integer groupId);

    /**
     * 根据用户列表查询密码
     *
     * @param nameList
     * @return
     */
    List<UserPwdModel> queryPwdByAccount(@Param(value = "nameList") List<String> nameList);


    UserInfoDomain queryPwd(@Param(value = "accountNumber") String accountNumber, @Param(value = "companyId") String companyId);

    int updateUserBundledInfo(UserInfoDomain userInfoDomain);


    List<UserInfoDomain> querycheckInfo(@Param(value = "groupId") String groupId);

    UserInfoModel getNewUserByAccountNumber(String accountNumber);

    public void deleteUserInfoByAccountNumber(@Param(value = "uuid") String uuid);

    List<String> getUserTableCustomInfo();

    void addCustomProperties(CustomPropertiesInfo record);

    void delectBindingByUuid(@Param(value = "uuid") String uuid, @Param(value = "table") String table);

    List<NewUserInfoExcle> queryRepeatList(List<NewUserInfoExcle> list);

    void insertNewUserInfoExcel(List<NewUserInfoExcle> excelList);

    UserInfoDomain selectUserInfo(UserInfoDomain userInfoDomain);


    public void insertUserList(@Param(value = "list") List<UserInfoDomain> list);

    UserInfoDomain getUserInfoByCompany(@Param(value = "uuid") String uuid);

    String getAdBinding(String uuid);

    public void updatePhoneNumber(@Param(value ="accountNumber")String accountNumber,@Param(value ="phoneNumber") String mail);


    void updateUserInfo(@Param(value = "excle") UpdateUserInfoExcle excle, @Param(value = "companyId") String companyId);

    int selectUserInfoCount(@Param(value = "accountNumber") String accountNumber, @Param(value = "companyId")String companyId);

    int selectCountByETI(@Param(value = "accountNumber") String accountNumber,
                         @Param(value = "mail") String mail,
                         @Param(value = "phoneNumber")String phoneNumber,
                         @Param(value = "idNum")String idNum,
                         @Param(value = "companyId")String companyId);

    int queryUserNumber(String companyId);


    int getWxAccount(@Param(value = "userId") String userId);


    int deleteWxAccountMap(@Param(value = "userId") String userId);


    void updateUserIssue(@Param(value = "issueStatus")Integer issueStatus, @Param(value = "userId") String userId);

}
