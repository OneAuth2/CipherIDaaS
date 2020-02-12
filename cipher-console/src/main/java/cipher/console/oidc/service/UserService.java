package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.NewUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UpdateUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UserInfoExcel;
import cipher.console.oidc.domain.web.AdUserInfoDomain;
import cipher.console.oidc.domain.web.CustomPropertiesInfo;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/5/28 19:49
 */

public interface UserService {



    String getAdBindAccount( String uuid);

    /**
     * create by 田扛
     * create time 2019年3月21日15:50:53
     * 查看该账号是否绑定赛赋认证
     */
     int getSaifubinding(String accountNumber);
    /**
     * create by Tiankang
     * create time  2019年3月11日14:17:15
     * 添加管理员
     */
    Map<String ,Object> adminAdd(String accountNumber);
    /**
     * 获取添加管理员数据
     * create by 田扛
     * create time 2019/3/11
     * @return
     */
    Map<String, Object> getAddAdmin(String companyId);

    /**
     * 通过accountNumber获取绑定钉钉的用户数量
     */
    int getDingDingCount(String accountNumber);

    /**
     * 通过uuid获取绑定大白
     */
    int getDabbyCount(String uuid);

    /**
     * 获取所有用户数据
     */
    List<UserInfoDomain> getAllUser();


    /**
     * 根据账户获取用户数据
     */
    UserInfoDomain getUserByAccountNumber(String accountNumber);

    /**
     * @author cozi
     * @param accountNumber 主账号
     * @return
     */
    UserInfoDomain getUserByNewAccountNumber(String accountNumber,String companyId);

    /**
     * 获取所有用户所在组的列表信息
     */
    List<UserManagementTableModel> getAllUserGroupMap();


    /**
     * 获得用户数据总的条数
     */
    int getUserCount();


    int authOutOfDateCount();


    int authBanCount();


    /**
     * 更新用户的状态
     */
    void updateAccountStatus(UserManagementModel user);


    /**
     * 获取用户表数据
     */
    Map<String, Object> queryUserTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel);


    /**
     * 获取用户的关联应用数据
     */
    Map<String, Object> querySubAccountApp(GroupUserMapDomain groupUserMapDomain);


    /**
     * 获取未授权的用户信息
     */
    List<String> queryNotAuthAccount();


    Map<String, Object> queryWelcomeUserPageList(List<String> nameList, UserManagementModel form, DataGridModel pageModel);


    List<String> queryAuthOutOfDate();


    List<String> queryAccountAuthOutOfDate();

    List<UserInfoDomain> queryRepeatNameUserList(List<AdUserInfoDomain> list);

    void insertAdUserList(List<AdUserInfoDomain> list);


    public List<AccountInfoModel> queryAllUser();

    int deleteByAccountNumber(GroupUserMapDomain groupUserMapDomain);

    int insertAccountInMap(GroupUserMapDomain groupUserMapDomain);

    List<UserInfoDomain> queryRepeatNameAccountList(List<UserInfoExcel> list);

    void batchInsertUser(List<UserInfoExcel> list);

    int insetIntoUserTable(UserInfoModel userInfoModel);

    int updateUserTable(UserInfoModel userInfoModel);


    /**
     * 获取用户表数据
     */
    Map<String, Object> queryAdminTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel);


    public void updateAdmin(UserManagementModel UserManagementModel);


    Map<String, Object> queryOrignTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel);

    /**
     * 更新用户设备绑定限制信息
     *
     * @param userInfoDomain 绑定限制信息
     * @return 绑定结果
     */
    boolean updateUserBundledInfo(UserInfoDomain userInfoDomain);


    /**
     * 根据账户获取用户数据
     */
    UserInfoDomain getUserInfo(String accountNumber);

    List<AdUserInfoDomain> queryUserByGrouId(Integer groupId);

    List<UserPwdModel> queryPwdByAccount(List<String> nameList);


    List<UserInfoDomain> querycheckInfo(String groupId);

    int queryWelcomeUserPageListTotal(List<String> nameList, UserManagementModel form);


    UserInfoModel getNewUserByAccountNumber(String accountNumber, int groupId);

    void deleteUserInfoByAccountNumber(String accountNumber);

    UserInfoDomain queryPwd(@Param(value = "accountNumber") String accountNumber,String companyId);

    List<String> getUserTableCustomInfo();

    void addCustomProperties(CustomPropertiesInfo record);

    UserInfoDomain getUserInfo(UserInfoDomain user);

    /**
     * 通过用户uuid 删除关联数据 解除认证绑定
     * @param type
     * @param uuid
     */
    void deleteBindingByUuid(String type,String uuid);

    List<NewUserInfoExcle> queryRepeatList(List<NewUserInfoExcle> list);

    void batchInsertNewUser(List<NewUserInfoExcle> list);


    UserInfoDomain getUserInfoByCompany(String uuid);

    /**
     * @author cozi
     * 用于判断是否是ad域绑定
     * @param uuid
     * @return
     */
    String getAdBinding(String uuid);


    void updatePhoneNumber(String accountNumber,String phoneNumber);


    void updateUserInfo(@Param(value = "excle") UpdateUserInfoExcle excle, @Param(value = "companyId") String companyId);

    int selectUserInfoCount(@Param(value = "accountNumber") String accountNumber,@Param(value = "companyId") String companyId);

    int selectCountByETI(String accountNumber,String mail,String phoneNumber,String idNum,String companyId);

    int queryUserNumber(String companyId);

    int getWxAccount(@Param(value = "userId") String uuid);

    void deleteWxAccountMap(@Param(value = "userId") String uuid);
}
