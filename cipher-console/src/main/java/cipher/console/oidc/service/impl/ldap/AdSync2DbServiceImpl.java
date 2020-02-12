package cipher.console.oidc.service.impl.ldap;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.SyncOu;
import cipher.console.oidc.mapper.AccountAdBindingMapper;
import cipher.console.oidc.model.ModifyAccountNumberModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.service.ldap.AdSync2DbService;
import cipher.console.oidc.util.ReturnJsonCode;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import java.util.*;

/**
 * @Author: zt
 * @Date: 2019-04-26 08:23
 */

@Service
public class AdSync2DbServiceImpl implements AdSync2DbService {


    @Autowired
    private AdUserBufferService adUserBufferService;

    @Autowired
    private GroupService groupService;

    @Autowired
    private UserGroupService userGroupService;

    @Autowired
    private AdInfoService adInfoService;

    @Autowired
    private UUIDService uuidService;


    @Override
    @Transactional
    public Map<String, Object> ayncBuffer2User(List<AdUserBufferDomain> toUpdateList, String companyId) {

        List<Integer> bufferUserIdList = new ArrayList<>();
        toUpdateList.forEach(adUserBufferDomain -> {
            bufferUserIdList.add(adUserBufferDomain.getId());
        });

        Map<String, Object> map = new HashMap<>();
        Iterator<AdUserBufferDomain> iterator = toUpdateList.iterator();
        AdInfoDomain adInfo = new AdInfoDomain();
        adInfo.setCompanyId(companyId);
        AdInfoDomain adInfoDomain = adInfoService.queryAdInfo(adInfo);

        String rootDept = adInfoDomain.getUserName();
        Integer rootGroupId = getRootDept(rootDept, companyId);
        //需要添加的用户列表
        List<AdUserBufferDomain> toAddList = new ArrayList<>();
        while (iterator.hasNext()) {
            AdUserBufferDomain adUserBufferDomain = iterator.next();
            if (adUserBufferDomain.getCheckNumber() == null) {
                adUserBufferDomain.setCompanyId(companyId);
                adUserBufferDomain.setUuid(uuidService.getUUid());
                toAddList.add(adUserBufferDomain);
                iterator.remove();
            }
        }


        /**
         * 同步新增的用户
         */
        if (!CollectionUtils.isEmpty(toAddList)) {
            //同步OU
            if (adInfoDomain.getSyncOu() == SyncOu.SYNC_OU.getCode()) {
                for (AdUserBufferDomain adUserBufferDomain : toAddList) {
                    synacAdUserAndGroup(adUserBufferDomain, rootGroupId);
                }
                try {
                    //添加绑定关系
                    addAccountAdUpnBinding(toAddList,companyId);
                    adUserBufferService.insertIntoCipherUser(toAddList);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
                    map.put(ReturnJsonCode.RETURN_MSG, "同步用户失败");
                    return map;
                }
            }
            //不同步OU
            if (adInfoDomain.getSyncOu() == SyncOu.NOT_SYNC_OU.getCode()) {
                try {
                    syncAdUser(toAddList, adInfoDomain.getUserName(),companyId);
                } catch (Exception e) {
                    e.printStackTrace();
                    map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
                    map.put(ReturnJsonCode.RETURN_MSG, "同步用户失败");
                    TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                    return map;
                }
            }
        }

        /**
         * 更新用户字段,(判断account_number是否发生了改变)
         */
        if (!CollectionUtils.isEmpty(toUpdateList)) {

            //改后的account_number与原来的account_number的对应关系
            List<ModifyAccountNumberModel> oldAndNewAccountNumberList = new ArrayList<>();

            //拆出更新account_number的那部分用户,toUpdateList只剩字段更改，但account_number未变的用户
            List<AdUserBufferDomain> accountModifiedList = queryModifyAccountNumber(toUpdateList, oldAndNewAccountNumberList, companyId);


            try {
                if (!CollectionUtils.isEmpty(toUpdateList)) {
                    adUserBufferService.updateCipherUserByBuffer(toUpdateList);
                }

                //更新更改accountNumber的用户，同时更改其他地方的account_number
                if (!CollectionUtils.isEmpty(accountModifiedList)) {
                    adUserBufferService.updateCipherUserByBuffer(accountModifiedList);
                    updateAllAccountNumber(oldAndNewAccountNumberList);
                }
            } catch (Exception e) {
                e.printStackTrace();
                map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
                map.put(ReturnJsonCode.RETURN_MSG, "同步用户失败");
//                return map;
//                throw new RuntimeException("同步");
                //事务回滚
                TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
                return map;
            }
        }

        try {
            if (bufferUserIdList.size() > 0) {
                adUserBufferService.deleteByIdList(bufferUserIdList);
            }
        } catch (Exception e) {
            e.printStackTrace();
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "同步用户失败");
            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
            return map;
        }

        map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
        map.put("add", toAddList.size());
        map.put("update", toUpdateList.size());
        map.put(ReturnJsonCode.RETURN_MSG, "同步用户成功");
        return map;

    }


    @Autowired
    private AccountAdBindingMapper accountAdBindingMapper;

    /**
     * 添加本地用户和AD用户之间的绑定关系
     *
     * @param list
     * @param companyId
     */
    private void addAccountAdUpnBinding(List<AdUserBufferDomain> list, String companyId) {

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        List<AccountAdBindingDomain> adBindingDomains = new ArrayList<>();
        for (AdUserBufferDomain adUserBufferDomain : list) {
            adBindingDomains.add(new AccountAdBindingDomain(adUserBufferDomain.getUuid(), adUserBufferDomain.getAccountNumber(),
                    companyId, adUserBufferDomain.getSource()));
        }

        if (CollectionUtils.isEmpty(adBindingDomains)) {
            return;
        }

        accountAdBindingMapper.insert(adBindingDomains);
    }


    @Autowired
    private ModifyAccountNumberService modifyAccountNumberService;


    /**
     * 更改所有用到account_number的表
     *
     * @param list
     */
    private void updateAllAccountNumber(List<ModifyAccountNumberModel> list) {
//        modifyAccountNumberService.updateCipherRoleUserMapAccountNumber(list);
//        modifyAccountNumberService.updateCipherUserAuthorizationMapAccountNumber(list);
//        modifyAccountNumberService.updateCipherGroupUserMapAccountNumber(list);
    }

    /**
     * @param toUpdateList               缓冲表中字段更改的列表(执行完后，只剩，发生字段更改，但account_number未改变的部分用户)
     * @param oldAndNewAccountNumberList 记录account_number的变更情况
     * @return 返回account_number发生了变化的用户
     */
    private List<AdUserBufferDomain> queryModifyAccountNumber(List<AdUserBufferDomain> toUpdateList, List<ModifyAccountNumberModel> oldAndNewAccountNumberList, String companyId) {
        List<UserInfoDomain> userInfoDomainList = adUserBufferService.queryUserInfoByBufferList(toUpdateList, companyId);
        //account_number发生变化的用户
        List<AdUserBufferDomain> accountModifyList = new ArrayList<>();

        Iterator<AdUserBufferDomain> iterator = toUpdateList.iterator();
        for (UserInfoDomain userInfoDomain : userInfoDomainList) {
            while (iterator.hasNext()) {
                AdUserBufferDomain bufferDomain = iterator.next();
                //同一个用户
                if (bufferDomain.getObjectGUID().equals(userInfoDomain.getObjectGUID())) {
                    //account_number发生变化
                    if (!bufferDomain.getAccountNumber().equals(userInfoDomain.getAccountNumber())) {
                        accountModifyList.add(bufferDomain);
                        ModifyAccountNumberModel modifyAccountNumberModel = new ModifyAccountNumberModel(userInfoDomain.getAccountNumber(), bufferDomain.getAccountNumber());
                        oldAndNewAccountNumberList.add(modifyAccountNumberModel);
                        break;
                    }
                }
            }
        }

        return accountModifyList;

    }


    /**
     * 同步Ad域的用户和组信息到数据库
     */
    private void synacAdUserAndGroup(AdUserBufferDomain adUserBufferDomain, int rootDepId) {
        List<GroupInfoDomain> allGroup = groupService.getAllGroup(adUserBufferDomain.getCompanyId());
        String dn = adUserBufferDomain.getDn();
        //去掉后面的dc部分
        dn = StringUtils.substringBetween(dn, "", ",DC=");

        String[] ous = dn.split(",");
        Integer groupId = null;
        //遍历用户携带的组信息
        for (int i = ous.length - 1; i >= 1; i--) {
            String[] deptName = ous[i].split("=");
            //组名
            String deptNameValue = deptName[1];
            GroupInfoDomain groupInfoDomain = checkContainGroup(allGroup, deptNameValue);

            //该组不存在，创建该组,并且设置该组的上级组织
            if (groupInfoDomain != null) {
                groupId = groupInfoDomain.getGroupId();
            }

            if (groupInfoDomain == null) {
                GroupInfoDomain newGroup = new GroupInfoDomain();
                if (i != ous.length - 1) {
                    String parentDpt = ous[i + 1];
                    String[] deptStr = parentDpt.split("=");
                    GroupInfoDomain parentGroup = checkContainGroup(allGroup, deptStr[1]);
                    if (null != parentGroup && StringUtils.isNotEmpty(String.valueOf(parentGroup.getGroupId()))) {
                        newGroup.setParentGroupId(parentGroup.getGroupId());
                    }
                } else {
                    newGroup.setParentGroupId(rootDepId);
                }
                newGroup.setOu(deptNameValue);
                newGroup.setGroupName(deptNameValue);
                newGroup.setGroupDescription("从AD导过来的组");
                newGroup.setModifyTime(new Date());
                newGroup.setCreateTime(new Date());
                newGroup.setCompanyId(adUserBufferDomain.getCompanyId());
                //插入新的组，
                groupService.insertGroup(newGroup);
                groupId = newGroup.getGroupId();
                allGroup.clear();
                allGroup = groupService.getAllGroup(adUserBufferDomain.getCompanyId());
            }
        }
        //绑定用户和组的关系
        GroupUserMapDomain groupUserMapDomain = new GroupUserMapDomain();
        groupUserMapDomain.setUserId(adUserBufferDomain.getUuid());
        groupUserMapDomain.setAccountNumber(adUserBufferDomain.getAccountNumber());
        if (groupId == null) {
            groupId = rootDepId;
        }
        groupUserMapDomain.setGroupId(groupId);
        userGroupService.insertUserGroup(groupUserMapDomain);
    }




    /**
     * 同步缓冲用户到自己用户体系，但不同步ou
     *
     * @param adUserBufferDomainList
     */
    private void syncAdUser(List<AdUserBufferDomain> adUserBufferDomainList, String rootDept,String companyId) throws Exception {

        String rootDep = rootDept.substring(rootDept.lastIndexOf("@") + 1, rootDept.length());
        Integer groupId = null;
        GroupInfoDomain groupInfoDomain = groupService.queryGroupByName(rootDep);
        Date now = new Date();
        if (groupInfoDomain == null) {
            GroupInfoDomain newGroup = new GroupInfoDomain();
            newGroup.setParentGroupId(0);
            newGroup.setGroupDescription("从AD导入时不带OU创建的根节点");
            newGroup.setGroupName(rootDep);
            newGroup.setCreateTime(now);
            newGroup.setModifyTime(now);
            groupId = groupService.insertGroup(newGroup);
        } else {
            groupId = groupInfoDomain.getGroupId();
        }

        adUserBufferDomainList.forEach(adUserBufferDomain -> adUserBufferDomain.setUuid(uuidService.getUUid()));

        //添加绑定关系
        addAccountAdUpnBinding(adUserBufferDomainList,companyId);
        //批量插入用户

        adUserBufferService.insertIntoCipherUser(adUserBufferDomainList);
        List<GroupUserMapDomain> groupUserMapDomainList = new ArrayList<>();
        //将用户放入根节点
        for (AdUserBufferDomain adUserBufferDomain : adUserBufferDomainList) {
            GroupUserMapDomain groupUserMapDomain = new GroupUserMapDomain();
            groupUserMapDomain.setAccountNumber(adUserBufferDomain.getAccountNumber());
            groupUserMapDomain.setGroupId(groupId);
            groupUserMapDomain.setUserId(adUserBufferDomain.getUuid());
            groupUserMapDomainList.add(groupUserMapDomain);
        }
        userGroupService.insertList(groupUserMapDomainList);
    }


    /**
     * 判断数据库中是否包含某个组
     *
     * @param allGroup  所有的组
     * @param groupName 组名
     * @return 若找到组则返回改组，否则返回空
     */
    private GroupInfoDomain checkContainGroup(List<GroupInfoDomain> allGroup, String groupName) {
        if (CollectionUtils.isEmpty(allGroup)) {
            return null;
        }
        for (GroupInfoDomain groupInfoDomain : allGroup) {
            if (groupName.equals(groupInfoDomain.getOu())) {
                return groupInfoDomain;
            }
        }
        return null;
    }


    /**
     * 查找根域名所在的id
     *
     * @param rootDept
     * @return
     */
    private Integer getRootDept(String rootDept, String companyId) {

        String rootDep = rootDept.substring(rootDept.lastIndexOf("@") + 1, rootDept.length());
        Integer rootGroupId = null;
        GroupInfoDomain groupInfoDomain = groupService.queryGroupByName(rootDep);
        if (groupInfoDomain == null) {
            groupInfoDomain = new GroupInfoDomain();
            groupInfoDomain.setOu(rootDep);
            groupInfoDomain.setGroupName(rootDep);
            groupInfoDomain.setParentGroupId(0);
            groupInfoDomain.setGroupDescription("从AD导过来的组");
            groupInfoDomain.setModifyTime(new Date());
            groupInfoDomain.setCreateTime(new Date());
            groupInfoDomain.setCompanyId(companyId);
            groupService.insertGroup(groupInfoDomain);
            rootGroupId = groupInfoDomain.getGroupId();
        } else {
            rootGroupId = groupInfoDomain.getGroupId();
        }
        return rootGroupId;
    }

}
