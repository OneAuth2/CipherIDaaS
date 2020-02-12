package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.NewUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UpdateUserInfoExcle;
import cipher.console.oidc.domain.exceldomain.UserInfoExcel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.*;
import cipher.console.oidc.model.*;
import cipher.console.oidc.service.AppService;
import cipher.console.oidc.service.PasswordAuthorizationService;
import cipher.console.oidc.service.PasswordSettingService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.aes.AES;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/5/28 19:50
 */
@Service("userService")
public class UserServiceImpl implements UserService {


    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordAuthorizationService passwordAuthorizationService;

    @Autowired
    private PasswordSettingService passwordSettingService;


    @Override
    public List<String> getUserTableCustomInfo() {
        return userMapper.getUserTableCustomInfo();
    }

    @Override
    public  void addCustomProperties(CustomPropertiesInfo record) {
        userMapper.addCustomProperties(record);
    }

    @Override
    public UserInfoDomain getUserInfo(UserInfoDomain user) {
        return userMapper.selectUserInfo(user);
    }

    @Override
    public void deleteBindingByUuid(String type, String uuid) {
        if("unbindSf".equals(type)){
            userMapper.delectBindingByUuid(uuid,"cipher_account_plat_mapping");
        }else if("unbindDd".equals(type)){
            userMapper.delectBindingByUuid(uuid,"cipher_account_ding_bind_mapping");
        }else if("unbindDb".equals(type)){
            userMapper.delectBindingByUuid(uuid,"cipher_account_link_map");
        }
    }

    @Override
    public List<NewUserInfoExcle> queryRepeatList(List<NewUserInfoExcle> list) {
        return userMapper.queryRepeatList(list);
    }

    @Override
    public void batchInsertNewUser(List<NewUserInfoExcle> list) {
        userMapper.insertNewUserInfoExcel(list);
    }

    @Override
    public UserInfoDomain getUserInfoByCompany(String uuid) {
        return userMapper.getUserInfoByCompany(uuid);
    }

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private AdminBehaviorInfoMapper adminBehaviorInfoMapper;


   @Autowired
   private AppService appService;

    private final String clientId="Osr47F";


    @Autowired
    private PasswordSettingMapper passwordSettingMapper;

    @Autowired
    private PasswordAuthorizationMapper passwordAuthorizationMapper;

    @Override
    public String getAdBindAccount(String uuid) {
        return userMapper.getAdBindAccount(uuid);
    }

    @Override
    public int getSaifubinding(String accountNumber) {
        return  userMapper.getSaifubinding(accountNumber);
    }

    @Override
    public Map<String, Object> adminAdd(String uuid) {
        Map<String , Object> map=new HashMap<>();
        try {
            UserManagementModel userManagementModel=new UserManagementModel();
            userManagementModel.setIsAdmin(0);
            userMapper.deleteAdmin(userManagementModel);//取消所有的管理员
            String[] account=uuid.split(",");
            for (int i=0;i<account.length;i++){
                UserManagementModel userManagement=new UserManagementModel();
                userManagement.setUuid(account[i]);
                userManagement.setIsAdmin(1);
                userMapper.deleteAdmin(userManagement);
            }
            map.put("code",0);
            map.put("msg","添加管理员成功");

        }catch (Exception e){
            map.put("code",1);
            map.put("msg","添加管理员失败");
            e.printStackTrace();
            return map;
        }
        return map;
    }

    @Override
    public Map<String, Object> getAddAdmin(String companyId) {
        Map<String,Object> map =new HashedMap();
        Map<String,Object> msg=new HashMap<>();
        try{
            List<UserManagementModel>  userList= userMapper.AllQueryOrignTableMapper(companyId);//获取已授权的用户
            List<String> accountNumbers= new  ArrayList();
            for (UserManagementModel userManage:userList) {
                accountNumbers.add(userManage.getUuid());
            }
            List<TreeNodesDomain> trees= appService.getUserTree(accountNumbers,companyId);
            msg.put("trees",trees);

        }catch (Exception e){
            map.put("code",1);
            map.put("msg","获取列表失败");
            e.printStackTrace();
            return  map;
        }
        map.put("code",0);
        map.put("msg",msg);
        return map;
    }

    @Override
    public int getDingDingCount(String uuid) {
        return userMapper.getDingDingAccount(uuid);
    }

    @Override
    public int getDabbyCount(String uuid) {
        return userMapper.getDabbyAccount(uuid);
    }

    @Override
    public List<UserInfoDomain> getAllUser() {
        return userMapper.getAllUser();
    }

    @Override
    public UserInfoDomain getUserByAccountNumber(String uuid) {
        UserInfoDomain domain= userMapper.getUserByAccountNumber(uuid);
        if(null!=domain) {
           List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(uuid);
           List<String> groupNamelist = new ArrayList<>();
           String groupName = "";
           if(null!=grouplist && grouplist.size()>0){
               domain.setGroupName(grouplist.get(0).getGroupName());
           }
           if (null!=grouplist && grouplist.size() > 1) {
               for (GroupInfoDomain groupInfoDomain : grouplist) {
                   groupNamelist.add(groupInfoDomain.getGroupName());
                   groupName = org.apache.commons.lang3.StringUtils.join(groupNamelist, ",");
               }
               String [] groupNames=groupName.split(",");
               List<String> groupNamelistNew= new ArrayList<>();
               for(int i=0; i<groupNames.length-1;i++){
                   groupNamelistNew.add(groupNames[i+1]);
               }
               groupName = org.apache.commons.lang3.StringUtils.join(groupNamelistNew, ",");
               domain.setGroupNames(groupName);
           }
       }

        return domain;
    }

    @Override
    public UserInfoDomain getUserByNewAccountNumber(String accountNumber,String companyId) {
        UserInfoDomain domain= userMapper.getUserByNewAccountNumber(accountNumber,companyId);
        if(null!=domain) {
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(accountNumber);
            List<String> groupNamelist = new ArrayList<>();
            String groupName = "";
            if(null!=grouplist && grouplist.size()>0){
                domain.setGroupName(grouplist.get(0).getGroupName());
            }
            if (null!=grouplist && grouplist.size() > 1) {
                for (GroupInfoDomain groupInfoDomain : grouplist) {
                    groupNamelist.add(groupInfoDomain.getGroupName());
                    groupName = org.apache.commons.lang3.StringUtils.join(groupNamelist, ",");
                }
                String [] groupNames=groupName.split(",");
                List<String> groupNamelistNew= new ArrayList<>();
                for(int i=0; i<groupNames.length-1;i++){
                    groupNamelistNew.add(groupNames[i+1]);
                }
                groupName = org.apache.commons.lang3.StringUtils.join(groupNamelistNew, ",");
                domain.setGroupNames(groupName);
            }
        }

        return domain;
    }

    @Override
    public List<UserManagementTableModel> getAllUserGroupMap() {
        return userMapper.getAllUserGroupMap();
    }

    @Override
    public int getUserCount() {
        return userMapper.getUserCount();
    }

    @Override
    public int authOutOfDateCount() {
        return userMapper.authOutOfDateCount();
    }

    @Override
    public int authBanCount() {
        return userMapper.authBanCount();
    }

    @Override
    public void updateAccountStatus(UserManagementModel user)
    {
        String status="";
        UserInfoDomain userInfoDomain=userMapper.getUserByAccountNumber(user.getUuid());
        if(null!=userInfoDomain&&userInfoDomain.getIsAdmin()==1&&user.getStatus()==2){
            UserManagementModel userManagementModel=new UserManagementModel();
            userManagementModel.setIsAdmin(ConstantsCMP.IS_NOT_ADMIN);
            userManagementModel.setUuid(user.getUuid());
            userMapper.updateAdmin(userManagementModel);
        }
        userMapper.updateAccountStatus(user);
        try {
            if (user.getStatus() == 1) {
                status = "启用";
            } else {
                status = "禁用";
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> queryUserTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel) {
        userManagementModel = (userManagementModel == null ? new UserManagementModel() : userManagementModel);
        userManagementModel.setPageData(dataGridModel);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", userMapper.queryUserTableMapper(userManagementModel));
        map.put("total", userMapper.getUserPageDataCount(userManagementModel));
        return map;
    }

    @Override
    public Map<String, Object> querySubAccountApp(GroupUserMapDomain groupUserMapDomain) {
        groupUserMapDomain = (groupUserMapDomain == null ? new GroupUserMapDomain():groupUserMapDomain);
        Map<String,Object> map = new HashMap<>();
        map.put("groupSubAccount",userMapper.querySubAccountApp(groupUserMapDomain));
        return map;
    }

    @Override
    public List<String> queryNotAuthAccount() {
        return userMapper.queryNotAuthAccount();
    }

    @Override
    public Map<String, Object> queryWelcomeUserPageList(List<String> nameList, UserManagementModel form, DataGridModel pageModel) {
        form = form == null ? new UserManagementModel() : form;
        form.setPageData(pageModel);
        List<UserManagementModel> list = userMapper.queryWelcomeUserPageList(form, nameList);
        int total = userMapper.queryWelcomeUserPageListTotal(form, nameList);
        Map<String, Object> map = new HashMap<>();
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public List<String> queryAuthOutOfDate() {
        return userMapper.queryTmpAuthOutOfDate();
    }

    @Override
    public List<String> queryAccountAuthOutOfDate() {
        return userMapper.queryAuthOutOfDate();
    }

    @Override
    public List<UserInfoDomain> queryRepeatNameUserList(List<AdUserInfoDomain> list) {
        return userMapper.queryRepeatAccount(list);
    }

    @Override
    public void insertAdUserList(List<AdUserInfoDomain> list) {
        userMapper.insertAdUserList(list);
    }

    @Override
    public List<AccountInfoModel> queryAllUser() {
        return userMapper.queryAllUser();
    }

    @Override
    public int deleteByAccountNumber(GroupUserMapDomain groupUserMapDomain) {
        return userMapper.deleteByAccountNumber(groupUserMapDomain);
    }

    @Override
    public int insertAccountInMap(GroupUserMapDomain groupUserMapDomain) {
        return userMapper.insertAccountInMap(groupUserMapDomain);
    }

    @Override
    public List<UserInfoDomain> queryRepeatNameAccountList(List<UserInfoExcel> list) {
        return userMapper.queryRepeatNameAccountList(list);
    }

    @Override
    public void batchInsertUser(List<UserInfoExcel> list) {
        userMapper.insertUserInfoExcel(list);
    }


    @Override
    public int insetIntoUserTable(UserInfoModel userInfoModel) {
        String initPwd="";
        if(org.apache.commons.lang.StringUtils.isEmpty(userInfoModel.getPassword())){
            PasswordSettingDomain passwordSettingDomain=passwordSettingService.queryPasswordSetting(userInfoModel.getCompanyId());
            initPwd=AES.encryptToBase64(passwordSettingDomain.getInit(), ConstantsCMP.AES_KEY);
        }else{
            initPwd=AES.encryptToBase64(userInfoModel.getPassword(), ConstantsCMP.AES_KEY);
        }
         int flag=userMapper.insetIntoUserTable(userInfoModel);
         passwordAuthorizationService.insertAccountPassword(initPwd,userInfoModel.getUuid());
         return flag;
    }



    @Override
    public int updateUserTable(UserInfoModel userInfoModel) {
        return userMapper.updateUserTable(userInfoModel);
    }

    @Override
    public Map<String, Object> queryAdminTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel) {
        userManagementModel = (userManagementModel == null ? new UserManagementModel() : userManagementModel);
        userManagementModel.setPageData(dataGridModel);
        Map<String, Object> map = new HashMap<>();
        String groupName="";
        List<UserManagementModel> list= userMapper.queryAdminTableMapper(userManagementModel);
        if(null!=list&&list.size()>0){
        for(UserManagementModel userModel:list) {
            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(userModel.getUuid());
            if(null!=grouplist&&grouplist.size()>0) {
                List<String> groupNamelist = new ArrayList<>();
                for (GroupInfoDomain groupInfoDomain : grouplist) {
                    groupNamelist.add(groupInfoDomain.getGroupName());
                    groupName = StringUtils.join(groupNamelist, ",");
                    userModel.setGroupName(groupName);
                }
            }
        }
        }
        map.put("rows",list );
        map.put("total", userMapper.getAdminPageDataCount(userManagementModel));
        return map;
    }

    @Override
    public void updateAdmin(UserManagementModel userManagementModel) {
       /* SubAccountDomain subAccountDomain = subAccountMapper.getTheSubAccount(userManagementModel.getAccountNumber(), clientId);
        if(userManagementModel.getIsAdmin()==1) {
           if (null == subAccountDomain) {
               SubAccountExcel domain = new SubAccountExcel();
               domain.setSubAccount(userManagementModel.getAccountNumber());
               domain.setAppClientId(clientId);
               subAccountMapper.insertSubAccount(domain);
               SubAccountMapDomain subDomain = new SubAccountMapDomain();
               subDomain.setAccountNumber(userManagementModel.getAccountNumber());
               subDomain.setSubId(domain.getId());
               SubAccountMapDomain subAccountMapDomain=subAccountMapMapper.querySubMap(subDomain);
               if(null==subAccountMapDomain) {
                   subAccountMapMapper.insertSubAccountMap(subDomain);
               }
           }
       }else{
           try {
               SubAccountAuthModel form=new SubAccountAuthModel();
               form.setAccountNumber(userManagementModel.getAccountNumber());
               form.setSubId(subAccountDomain.getId());
               subAccountMapper.deleteSubAccountMap(form);
               SubAccountDomain domain=new SubAccountDomain();
               domain.setSubAccount(userManagementModel.getAccountNumber());
               domain.setAppClientId(clientId);
               subAccountMapMapper.deleteSubAccountNumber(domain);
           } catch (Exception e) {
               e.printStackTrace();
           }
       }*/
        userMapper.updateAdmin(userManagementModel);
    }

    @Override
    public Map<String, Object> queryOrignTableMapper(UserManagementModel userManagementModel, DataGridModel dataGridModel) {
        userManagementModel = (userManagementModel == null ? new UserManagementModel() : userManagementModel);
        userManagementModel.setPageData(dataGridModel);
        Map<String, Object> map = new HashMap<>();
        String groupName="";
        List<UserManagementModel>  userList= userMapper.queryOrignTableMapper(userManagementModel);
        for(UserManagementModel managementModel:userList){

            List<GroupInfoDomain> grouplist = groupMapper.selectGroupNameList(managementModel.getAccountNumber());
            List<String> groupNamelist = new ArrayList<>();
            for (GroupInfoDomain groupInfoDomain : grouplist) {
                groupNamelist.add(groupInfoDomain.getGroupName());
                groupName = StringUtils.join(groupNamelist, ",");
                managementModel.setGroupName(groupName);
            }

        }
        map.put("rows",userList);
        map.put("total", userMapper.getOrignPageDataCount(userManagementModel));
        return map;
    }

    @Override
    public boolean updateUserBundledInfo(UserInfoDomain userInfoDomain) {
        int result = userMapper.updateUserBundledInfo(userInfoDomain);
        if (result > 0){
            return true;
        }
        return false;
    }

    @Override
    public UserInfoDomain getUserInfo(String uuid) {
        return userMapper.getUserInfo(uuid);
    }

    @Override
    public List<AdUserInfoDomain> queryUserByGrouId(Integer groupId) {
        return userMapper.queryUserByGrouId(groupId);
    }

    @Override
    public List<UserPwdModel> queryPwdByAccount(List<String> nameList) {
        return userMapper.queryPwdByAccount(nameList);
    }

    @Override
    public List<UserInfoDomain> querycheckInfo(String groupId) {
        return userMapper.querycheckInfo(groupId);
    }

    @Override
    public int queryWelcomeUserPageListTotal(List<String> nameList,UserManagementModel form) {
        return userMapper.queryWelcomeUserPageListTotal(form, nameList);
    }

    @Override
    public UserInfoModel getNewUserByAccountNumber(String accountNumber,int groupId) {
        UserInfoModel userInfoModel=userMapper.getNewUserByAccountNumber(accountNumber);
        List<GroupInfoDomain> grouplist=groupMapper.selectGroupNameListBycondition(accountNumber,groupId);
        List<String> groupNamelist=new ArrayList<>();
        String groupNames="";
        for(GroupInfoDomain groupInfoDomain:grouplist){
            groupNamelist.add(groupInfoDomain.getGroupName());
            groupNames= org.apache.commons.lang3.StringUtils.join(groupNamelist, ",");
        }
        userInfoModel.setGroupNames(groupNames);
        return userInfoModel;
    }

    @Override
    public void deleteUserInfoByAccountNumber(String uuid) {
        PasswordAuthorizationMagDomain passwordAuthorizationMagDomain=passwordAuthorizationMapper.selectPasswordAuthorizationMagDomain(uuid);
        if(null!=passwordAuthorizationMagDomain){
            passwordAuthorizationMapper.deleteAccountPassword(uuid);
        }
        userMapper.deleteUserInfoByAccountNumber(uuid);
    }

    @Override
    public UserInfoDomain queryPwd(String accountNumber,String companyId) {
        return userMapper.queryPwd(accountNumber,companyId);
    }

    @Override
    public String getAdBinding(String uuid) {
        return userMapper.getAdBinding(uuid);
    }

    @Override
    public void updatePhoneNumber(String accountNumber, String phoneNumber) {
        userMapper.updatePhoneNumber(accountNumber,phoneNumber);
    }

    @Override
    public void updateUserInfo(UpdateUserInfoExcle excle, String companyId) {
        userMapper.updateUserInfo(excle, companyId);
    }

    @Override
    public int selectUserInfoCount(String accountNumber, String companyId) {
        return userMapper.selectUserInfoCount(accountNumber,companyId);
    }

    @Override
    public int queryUserNumber(String companyId) {
        return userMapper.queryUserNumber(companyId);
    }


    @Override
    public int selectCountByETI(String accountNumber,String mail, String phoneNumber, String idNum, String companyId) {
        return userMapper.selectCountByETI(accountNumber,mail,phoneNumber,idNum,companyId);
    }

    @Override
    public int getWxAccount(String uuid) {
        return userMapper.getWxAccount(uuid);
    }

    @Override
    public void deleteWxAccountMap(String uuid) {
        userMapper.deleteWxAccountMap(uuid);
    }

}
