
package com.portal.service.impl;


import com.portal.auth2.model.SsoClientDetails;
import com.portal.commons.CacheKey;
import com.portal.daoAuthoriza.ApplicationGroupDAO;
import com.portal.daoAuthoriza.PortalDAO;
import com.portal.daoAuthoriza.RoleGroupMapDAO;
import com.portal.daoAuthoriza.UserDAO;
import com.portal.domain.*;
import com.portal.model.GroupApplicationModel;
import com.portal.service.GroupService;
import com.portal.service.PortalService;
import com.portal.service.UserLoginRecService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 95744 on 2018/5/21.
 */
@Service
public class PortalServiceImpl  implements PortalService {

    @Autowired
    private PortalDAO portalDAO;

    @Autowired
    private RoleGroupMapDAO roleGroupMapDAO;


    @Autowired
    private UserDAO userDAO;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;



    private static final String SECRET_KEY = "cipher_2018";


    @Autowired
    private ApplicationGroupDAO applicationGroupDAO;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserLoginRecService userLoginRecService;





    @Override
    public List<PortalApplyInfo> queryPortalApplyList(PortalApplyInfo portalApplyInfo, String userName) {
        return portalDAO.queryPortalApplyList(portalApplyInfo,userName);
    }

    @Override
    public Map<String,Object> updateUserPwd(String username, String newpwd,String pwd) {
        boolean result = redisTemplate.hasKey(CacheKey.getPwdByUserNameCacheKey(username));
        if (result == true) {
            redisTemplate.delete(CacheKey.getPwdByUserNameCacheKey(username));
        }
        Map<String, Object> map = new HashMap<String, Object>();

        UserLoginRecInfo userLoginRecInfo = new UserLoginRecInfo();
        userLoginRecInfo.setAccountNumber(username);
        UserLoginRecInfo form = userLoginRecService.selectUserLoginRecInfo(userLoginRecInfo);
        if (null == form) {
            userLoginRecService.insertUserLoginRecInfo(userLoginRecInfo);
        } else if (null != form && StringUtils.isEmpty(userLoginRecInfo.getFirstFaceLoginTime())) {
            userLoginRecService.updateUserLoginRecInfo(userLoginRecInfo);
        }
        try {
            UserInfoDomain newuser = new UserInfoDomain();
            newuser.setPassword(newpwd);
            newuser.setAccountNumber(username);
            String oldPwd = userDAO.getPwd(username);
            if (StringUtils.isEmpty(oldPwd)) {
                userDAO.insertPwd(newuser);
            } else {
                userDAO.updateUserPwd(newuser);
            }
            map.put("returnCode", 0);
            map.put("msg", "修改密码成功");

        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }


    @Override
    public PortalApplyInfo selectPortalInfo(PortalApplyInfo portalApplyInfo) {
        return portalDAO.selectPortalInfo(portalApplyInfo);
    }

    @Override
    public List<PortalApplyInfo> queryPortalList() {
        return portalDAO.queryPortalList();
    }

    @Override
    public List<PortalApplyInfo> queryPortalByGroup(PortalApplyInfo portalApplyInfo) {
        return portalDAO.queryPortalByGroup(portalApplyInfo);
    }

    @Override
    public List<PortalApplyInfo> queryOidcInfo() {
        return portalDAO.queryOidcInfo();
    }

    @Override
    public String getReqUrl(String applyId) {
        return portalDAO.getReqUrl(applyId);
    }

    @Override
    public String getApplicationUrl(String applyId) {
        return portalDAO.getApplicationUrl(applyId);
    }

    @Override
    public String getRediretLoginUrl(String applyId) {
        return portalDAO.getRediretLoginUrl(applyId);
    }

    @Override
    public List<GroupApplicationModel> selectAllGroupApplication(int groupId, String accountNumber) {
        List<GroupApplicationModel> groupApplicationModels = applicationGroupDAO.selectAllGroupApplication();
        List<GroupApplicationModel> result = new ArrayList<GroupApplicationModel>();
        getGroupParentApplication(groupId,result,groupApplicationModels);
        List<GroupApplicationModel> newResult = new ArrayList<GroupApplicationModel>();
        for (GroupApplicationModel applicationModel : result){
            boolean add = true;
            for (GroupApplicationModel appModel : newResult){
                if (applicationModel == appModel || org.apache.commons.lang.StringUtils.isBlank(applicationModel.getApplicationId()) || org.apache.commons.lang.StringUtils.isBlank(appModel.getApplicationId())  ||applicationModel.getApplicationId().equals(appModel.getApplicationId())){
                    add = false;
                }
            }
            if (add){
                if (org.apache.commons.lang.StringUtils.isNotBlank(applicationModel.getApplicationId())) {
                    newResult.add(applicationModel);
                }
            }
        }

        List<GroupApplicationModel> userGroupAppList = applicationGroupDAO.selectUserRoleApplication(accountNumber);
        for (GroupApplicationModel model : userGroupAppList) {
            if (null == model) {
                continue;
            }
            boolean add = true;
            if (null != newResult) {
                for (GroupApplicationModel applicationModel : newResult) {
                    if (applicationModel.getApplicationId().equals(model.getApplicationId())) {
                        add = false;
                    }
                }
                if (add) {
                    newResult.add(model);
                }
            }
        }
        return newResult;
    }

    @Override
    public List<GroupApplicationModel> selectNewApplicationList(String uuid,String queryName) {
        //获取所有应用
        List<GroupApplicationModel> newApplicationList=applicationGroupDAO.selectNewApplicationList(uuid,queryName);
        Set<String> set=new HashSet<String>();
        List<GroupApplicationModel> newList=new ArrayList<>();
        if(null!=newApplicationList&&newApplicationList.size()>0) {
            for (GroupApplicationModel groupApplicationModel : newApplicationList) {
                if (groupApplicationModel == null) {
                    continue;
                }

                String applicationName = groupApplicationModel.getApplicationName();
                if (applicationName != null) {
                    if (!set.contains(applicationName)) { //set中不包含重复的
                        set.add(applicationName);
                        newList.add(groupApplicationModel);
                    } else {
                        continue;
                    }
                }
            }
        }
        set.clear();
        return newList;
    }

    @Override
    public IconSettingsDomain getIconSettiingInfo(String companyId) {
        return portalDAO.getIconSettiingInfo(companyId);
    }




    public void getGroupParentApplication(int groupId,List<GroupApplicationModel> result,List<GroupApplicationModel> applicationModels){
       if(null!=applicationModels && applicationModels.size()>0){
           for (GroupApplicationModel groupApplicationModel : applicationModels){
               if (groupApplicationModel.getGroupId() == groupId){
                   result.add(groupApplicationModel);
                   getGroupParentApplication(groupApplicationModel.getParentGroupId(),result,applicationModels);
               }
           }
       }

    }


    @Override
    public CsApplicationInfo getCsApplicationInfo(String applyId) {
        return portalDAO.getCsApplicationInfo(applyId);
    }

    @Override
    public SsoClientDetails getAuthApplicationInfo(SsoClientDetails ssoClientDetails) {
        return portalDAO.getAuthApplicationInfo(ssoClientDetails);
    }


}
