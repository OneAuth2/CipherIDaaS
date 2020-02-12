package com.portal.service.impl;


import com.portal.commons.Constants;
import com.portal.daoAuthoriza.GroupDAO;
import com.portal.domain.GroupInfoDomain;
import com.portal.domain.IdentityAuthTypeDomain;
import com.portal.domain.TreeNodesDomain;
import com.portal.service.GroupService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GroupServiceImpl implements GroupService {

    private static Logger logger = LoggerFactory.getLogger(GroupServiceImpl.class);

    @Autowired
    private GroupDAO groupDAO;

    @Override
    public List<GroupInfoDomain> getGroupInfoList(GroupInfoDomain form) {
        return groupDAO.selectGroupInfoList(form);
    }

    @Override
    public GroupInfoDomain getGroupInfoInfo(GroupInfoDomain form) {
        return groupDAO.selectGroupInfo(form);
    }


    @Override
    public int getGroupStruct(List<TreeNodesDomain> result, List<TreeNodesDomain> list, int groupId) {
        for (TreeNodesDomain treeNodesDomain : list) {
            if (Integer.valueOf(treeNodesDomain.getGroupId()) == groupId) {
                result.add(treeNodesDomain);
                return 1;
            } else {
                int d = getGroupStruct(result, treeNodesDomain.getNodes(), groupId);
                if (d == 1) {
                    result.add(treeNodesDomain);
                    return 1;
                }
            }
        }
        return 0;
    }


    @Override
    public List<TreeNodesDomain> getGroupList() {
        List<GroupInfoDomain> groupList = groupDAO.getAllGroup();
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        constructGroupTree(trees, 0, groupList);
        return trees;
    }

    @Override
    public List<Integer> queryGroupByAccount(String uuid) {
        return groupDAO.queryGroupByAccount(uuid);
    }

    @Override
    public IdentityAuthTypeDomain queryGlobalAuthType() {
        return groupDAO.queryGlobalAuthType();
    }

    @Override
    public IdentityAuthTypeDomain queryAuthTypeByGroupIdList(List<Integer> groupIdList) {
        return groupDAO.queryAuthTypeByGroupIdList(groupIdList);
    }

    @Override
    public IdentityAuthTypeDomain queryDefaultAuthType(String companyUUid) {
        return groupDAO.queryDefaultAuthType(companyUUid);
    }

    @Override
    public List<Integer> queryGroupByCompanyUuidAndAccount(String companyUUid, String accountNumber) {

        //入参检验
        if (StringUtils.isBlank(companyUUid) || StringUtils.isBlank(accountNumber)) {
            return null;
        }

        return groupDAO.queryGroupByCompanyIdAndAccount(companyUUid, accountNumber);
    }

    @Override
    public IdentityAuthTypeDomain queryDefaultAuthTypeByCompanyId(String companyUUid) {

        //入参校验
        if (StringUtils.isBlank(companyUUid)) {
            return null;
        }

        return groupDAO.queryDefaultAuthTypeByCompanyId(companyUUid);
    }

    @Override
    public IdentityAuthTypeDomain queryAuthTypeByGroupIdListAndCompanyUUid(List<Integer> groupIdList, String companyUUid) {
        return groupDAO.queryAuthTypeByGroupIdListAndCompanyUUid(groupIdList, companyUUid);
    }

    @Override
    public String getGroupsStringByUuid(String uuid) {
        //入参校验
        if (org.apache.commons.lang.StringUtils.isEmpty(uuid)) {
            logger.error("enter GroupserviceImpl.getGroupsStringByUuid(String uuid) error ! uuid is null", uuid);
            return "";
        }

        //获取部门列表
        List<GroupInfoDomain> list = getGroupsByUuid(uuid);
        //list为空或者list.size大小为0
        if (list == null || list.size() == Constants.SIZE) {
            logger.error("enter GroupserviceImpl.getGroupsStringByUuid(String uuid) error ! list is null or list.size()==0", list);
            return "";
        }

        String group = getGroupsByList(list);

        return group;
    }

    /**
     * 根据list传来用户所在的g部门 获取所在部门的所有上级部门的字符串拼接
     * 若部门在多个部门 ，之间以“，”的形式进行拼接
     * create by 安歌
     * create time 2019年7月25日15:24:37
     *
     * @param list
     * @return
     */
    private String getGroupsByList(List<GroupInfoDomain> list) {
        //入参校验
        if (list == null || list.size() == Constants.SIZE) {
            logger.error("enter GroupserviceImpl.getGroupsByList(List<GroupInfoDomain> list) error !" +
                    " list is null or list.size() is null", list);
            return "";
        }
        //初始化部门的字符串
        StringBuilder groups = new StringBuilder();
        //循环部门并且拼接部门
        for (GroupInfoDomain groupinfoDomain : list) {
            groups = getGroupString(groupinfoDomain,groups);
            groups.deleteCharAt(groups.length()-1);
            groups.append(",");
        }
        //删除最后一个字符串
        groups.deleteCharAt(groups.length()-1);
        //返回
        return groups.toString();
    }

    private StringBuilder getGroupString(GroupInfoDomain groupinfoDomain, StringBuilder groups) {
        //入参校验
        if (groupinfoDomain == null) {
            logger.error("enter GroupserviceImpl.getGroupString(GroupInfoDomain groupinfoDomain) error !" +
                    " groupinfoDomain is null", groupinfoDomain);
            return null;
        }

//        groups.append(groupinfoDomain.getGroupName()).append("/");

        groups.insert(0,groupinfoDomain.getGroupName()+"/");
//        groups = groupinfoDomain.getGroupName() + "/" + groups;

        if (groupinfoDomain.getParentGroupId() == 0) {
            return groups;
        }
        groupinfoDomain = getGroupInfoByGroupId(groupinfoDomain.getParentGroupId());
        getGroupString(groupinfoDomain, groups);

        return groups;
    }

    /**
     * 根据id查找部门信息
     *
     * @param parentGroupId
     * @return
     */
    private GroupInfoDomain getGroupInfoByGroupId(int parentGroupId) {
        //入参校验
        if (parentGroupId == Constants.SIZE) {
            logger.error("enter GroupserviceImpl.getGroupInfoByGroupId(int parentGroupId) error and parentGroupId is not exits", parentGroupId);
            return null;
        }

        return groupDAO.getGroupsByGroupId(parentGroupId);
    }

    private List<GroupInfoDomain> getGroupsByUuid(String uuid) {
        //入参校验
        if (org.apache.commons.lang.StringUtils.isEmpty(uuid)) {
            logger.error("enter GroupserviceImpl.getGroupsByUuid(String uuid) error ! uuid is null", uuid);
            return null;
        }
        return groupDAO.getGroupsByUuid(uuid);
    }
    @Override
    public IdentityAuthTypeDomain queryAuthTypeByUserId(String userId, String companyId) {
        return groupDAO.queryAuthTypeByUserId(userId, companyId);
    }


    private List<TreeNodesDomain> constructGroupTree(List<TreeNodesDomain> trees, int parentId, List<GroupInfoDomain> groupList) {
        for (GroupInfoDomain groupInfoDomain : groupList) {
            //查找根节点
            if (groupInfoDomain.getParentGroupId() == parentId) {
                TreeNodesDomain treeNode = new TreeNodesDomain(groupInfoDomain);
                treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                treeNode.setGroupName(groupInfoDomain.getGroupName());
                treeNode.setParentGroupId(groupInfoDomain.getParentGroupId() + "");
                treeNode.setNodes(constructGroupTree(treeNode.getNodes(), groupInfoDomain.getGroupId(), groupList));
                trees.add(treeNode);
            }
        }

        return trees;
    }


}
