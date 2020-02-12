package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.jms.JMSProducer;
import cipher.console.oidc.jms.JMSType;
import cipher.console.oidc.mapper.DynamicStateMapper;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.UserMapper;
import cipher.console.oidc.service.DynamicStateService;
import cipher.console.oidc.service.GroupService;
import cipher.console.oidc.service.UUIDService;
import cipher.console.oidc.totp.GoogleAuthenticator;
import cipher.console.oidc.totp.GoogleAuthenticatorKey;
import cipher.console.oidc.util.NumberUtil;
import cipher.console.oidc.util.aes.AES;
import cipher.console.oidc.util.aes.AesKey;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class DynamicStateServiceImpl implements DynamicStateService {

    private static String deptPath = "";

    @Autowired
    private DynamicStateMapper dynamicStateMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private JMSProducer jmsProducer;

    @Autowired
    private UUIDService uuidService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserMapper userMapper;

    @Override
    public Map<String, Object> getDynamicStateList(String queryName, Integer issueStatus, String companyId, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        int total = 0;
        if(StringUtils.isNotEmpty(companyId)){
            List<DynamicStateDomain> dynamicStateDomains = new ArrayList<>();
            DynamicStateInfo dynamicStateInfo = new DynamicStateInfo();
            dynamicStateInfo.setQueryName(queryName);
            dynamicStateInfo.setCompanyId(companyId);
            dynamicStateInfo.setPageModel(pageModel);
            if(issueStatus.equals(0)){
                //全部
                dynamicStateDomains = dynamicStateMapper.selectDynamicStateList(dynamicStateInfo);
                total = dynamicStateMapper.selectDynamicStateListCount(dynamicStateInfo);
            }else if(issueStatus.equals(1)){
                //已下发
                dynamicStateDomains = dynamicStateMapper.selectDynamicStateFinishList(dynamicStateInfo);
                total = dynamicStateMapper.selectDynamicStateFinishListCount(dynamicStateInfo);
            }else {
                //未下发
                dynamicStateDomains = dynamicStateMapper.selectDynamicStateUndoneList(dynamicStateInfo);
                total = dynamicStateMapper.selectDynamicStateUndoneListCount(dynamicStateInfo);
            }
            if(dynamicStateDomains!=null&&dynamicStateDomains.size()>0){
                List<GroupInfoDomain> allGroup = groupMapper.getAllGroup(companyId);
                for(DynamicStateDomain dynamicStateDomain:dynamicStateDomains){
                    //种子密钥状态
                    if(dynamicStateDomain.getIssueStatus()!=null&&dynamicStateDomain.getIssueStatus().equals(1)){
                        dynamicStateDomain.setIssueStatusDescribe("已下发");
                    }else{
                        dynamicStateDomain.setIssueStatusDescribe("未下发");
                    }
                    //部门
                    if(dynamicStateDomain.getGroupId()!=null&&!(dynamicStateDomain.getGroupId().equals(0))){
                        deptPath="";
                        dynamicStateDomain.setGroupNames(getDeptPath(allGroup,dynamicStateDomain.getGroupId()));
                        //获取用户所在的部门
                        List<GroupInfoDomain> groupList = groupService.getGroupNamesById(dynamicStateDomain.getUserId());
                        //获取部门转换后的数据
                        List<IdentityGroupMapDomain> identityGroupMapDomains = ObjectServiceFactory.getIdentityGroupMapDomain(groupList);
                        //构造用户部门结构树
                        List<TreeNodesDomain> groupTrees = groupService.getNodeTree(identityGroupMapDomains, companyId);
                        //统一设置路劲信息
                        groupList = groupService.getPath(groupTrees, groupList);
                        dynamicStateDomain.setGroupList(groupList);
                    }else {
                        dynamicStateDomain.setGroupNames("");
                    }
                }
            }
            map = NewReturnUtils.successResponse(ReturnMsg.DYNAMICSTATELISTSUCCESS);
            map.put("rows",dynamicStateDomains);
            map.put("total",total);
            return map;
        }
        return NewReturnUtils.failureResponse(ReturnMsg.DYNAMICSTATELISTFAILED);
    }

    @Override
    public Map<String, Object> issueSeedKey(String userId, String companyId) {
        //在下发新的种子之前先删除上次下发的种子
        if(StringUtils.isEmpty(userId)){
            return NewReturnUtils.failureResponse(ReturnMsg.DYNAMICSTATELISTISNULL);
        }
        String mail = dynamicStateMapper.getMailByuserId(userId);
        if(StringUtils.isNotEmpty(mail)){
            addDynamicPassword(userId,companyId,mail);
        }else{
            return NewReturnUtils.failureResponse(ReturnMsg.DYNAMICSTATELISTISNOMAIL);
        }
        return NewReturnUtils.successResponse(ReturnMsg.DYNAMICSTATELISTISSUE);
    }

    @Override
    public Map<String, Object> resetSeedkey(String userId, String companyId) {
        if(StringUtils.isEmpty(userId)){
            return NewReturnUtils.failureResponse(ReturnMsg.DYNAMICSTATELISTISNULL);
        }
        //先删除上一次的种子密钥

        dynamicStateMapper.delDynamicPassword(userId);
        userMapper.updateUserIssue(2,userId);
      /*  //谷歌认证器密钥生成
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey credentials = googleAuthenticator.createCredentials();
        String encryptPassword = AES.encryptToBase64(credentials.getKey(), AesKey.AES_KEY);
        String uuid = uuidService.getUUid();
        //将用户下发状态为1
        dynamicStateMapper.updateIssueStatus(userId);
        dynamicStateMapper.insertDynamicPassword(uuid,userId,companyId,encryptPassword);*/
        return NewReturnUtils.successResponse(ReturnMsg.DYNAMICSTATELISTRESET);
    }

    @Override
    public List<String> getSelectedUserId(String companyId) {
        List<String> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(companyId)){
            list = dynamicStateMapper.selectedUserId(companyId);
        }
        return list;
    }

    @Override
    public Map<String, Object> batchIssueSeedkey(String userIds, String companyId) {
        Integer count = 0;
        if(StringUtils.isNotEmpty(userIds)&&StringUtils.isNotEmpty(companyId)){
            String[] users = userIds.split(",");
            if(users!=null&& users.length>0){
                for(String user:users){
                    String mail = dynamicStateMapper.getMailByuserId(user);
                    if(StringUtils.isNotEmpty(mail)){
                        addDynamicPassword(user,companyId,mail);
                    }else {
                        count++;
                    }
                }
            }
        }
        return NewReturnUtils.successResponse("种子密钥批量下发成功,邮箱未绑定："+count+"条");
    }

    public void addDynamicPassword(String userId,String companyId,String mail){
        dynamicStateMapper.delDynamicPassword(userId);
        //生成种子密钥
        //String dynamicPassword = NumberUtil.getStringRandom(16).toUpperCase();
        //谷歌认证器密钥生成
        GoogleAuthenticator googleAuthenticator = new GoogleAuthenticator();
        GoogleAuthenticatorKey credentials = googleAuthenticator.createCredentials();
        //谷歌认证器扫码数据格式
        //String dynamicPassword = "otpauth://totp/"+mail+"?secret="+credentials.getKey();
        //微信小程序扫码数据格式
        //String dynamicPassword ="https://mp.weixin.qq.com/a/~I1uNVyu5JSHYjLWVr6SjWQ~~";
        //经过配置以后的小程序扫码数据格式
        String acountNum = dynamicStateMapper.selectAccountNumByUserId(userId);
        if(StringUtils.isEmpty(acountNum)){
            acountNum = "MasterAccountIsEmpty";
        }
        String dynamicPassword = "http://www.cipherchina.com:8000/a?user="+acountNum+"&secret="+credentials.getKey();
        //String dynamicPassword = "http://www.cipherchina.com:8000/";
        String encryptPassword = AES.encryptToBase64(credentials.getKey(), AesKey.AES_KEY);
        String uuid = uuidService.getUUid();
        //将用户下发状态为1
        dynamicStateMapper.updateIssueStatus(userId);
        dynamicStateMapper.insertDynamicPassword(uuid,userId,companyId,encryptPassword);
        //发送邮件，内容包含动态密码
        if(StringUtils.isNotEmpty(companyId)&&StringUtils.isNotEmpty(mail)&&StringUtils.isNotEmpty(dynamicPassword)){
            EmailSeedKeyInfo emailSeedKeyInfo = new EmailSeedKeyInfo();
            emailSeedKeyInfo.setCompanyId(companyId);
            emailSeedKeyInfo.setMail(mail);
            emailSeedKeyInfo.setDynamicPassword(dynamicPassword);
            jmsProducer.sendMessage(emailSeedKeyInfo,JMSType.EMAIL_ISSUE_SEEDKEY);
        }
    }

    public static void main(String[] args) {
        /*List<GroupInfoDomain> list = new ArrayList<>();
        GroupInfoDomain groupInfoDomain = new GroupInfoDomain();
        groupInfoDomain.setGroupId(1);
        groupInfoDomain.setParentGroupId(0);
        groupInfoDomain.setGroupName("赛赋科技");
        list.add(groupInfoDomain);
        String deptPath = getDeptPath(list, 5);
        System.out.println(deptPath);*/
        String dynamicPassword = NumberUtil.getStringRandom(16);
        String encryptPassword = AES.encryptToBase64(dynamicPassword, AesKey.AES_KEY);
        //System.out.println(encryptPassword.length());
    }



    public static String getDeptPath(List<GroupInfoDomain> list,Integer groupId){
        if(groupId!=null){
            for(GroupInfoDomain groupInfoDomain:list){
                if(groupInfoDomain.getGroupId().equals(groupId)){
                    deptPath = "/"+groupInfoDomain.getGroupName()+deptPath;
                    if(!groupId.equals(0)){
                        getDeptPath(list,groupInfoDomain.getParentGroupId());
                    }
                }
            }
        }
        return deptPath;
    }
}
