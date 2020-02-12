package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import cipher.console.oidc.model.UserInfoModel;
import cipher.console.oidc.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 获取组织结构树实现类
 */
@Service
public class OrganitionTreeSeriveImpl implements OrganitionTreeSerive{

    @Autowired
    private CasualUserService casualUserService;

    @Autowired
    private NewUserService newUserService;

    @Autowired
    private GroupService groupService;

    @Override
    public  List<TreeNodesDomain> getAllUserList(String companyId) {
        List<GroupInfoDomain> groupList = groupService.getAllGroup(companyId);
        List<TreeNodesDomain> userList = newUserService.getAllUserList(companyId);
        List<TreeNodesDomain> userListYes = new ArrayList<>();
        List<TreeNodesDomain> userListNo = new ArrayList<>();
        for(TreeNodesDomain treeNodesDomain:userList){
            if("0".equals(treeNodesDomain.getGroupId())){
                userListNo.add(treeNodesDomain);
            }else {
                userListYes.add(treeNodesDomain);
            }
        }
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        String path="";
        String idPath = "";
        constructTreeV2(trees,0,groupList, userListYes,path,idPath);
        if(userListNo.size()>0){
            //创建一个无部们
            TreeNodesDomain treeNodesDomain = new TreeNodesDomain();
            treeNodesDomain.setGroupId("0");
            treeNodesDomain.setGroupName("无部门用户");
            treeNodesDomain.setHref("/cipher/newUser/userlist?json");
            treeNodesDomain.setIdPath("0/");
            treeNodesDomain.setParentGroupId("0");
            treeNodesDomain.setPath("无部门用户/");
            treeNodesDomain.setRootNode("0");
            treeNodesDomain.setSelected(false);
            treeNodesDomain.setText("无部门用户");
            for(TreeNodesDomain treeNodesDomain1:userListNo){
                treeNodesDomain1.setText(treeNodesDomain1.getUserName());
                treeNodesDomain1.setHref("/cipher/newUser/getlist?json");
            }
            treeNodesDomain.setNodes(userListNo);
            trees.add(treeNodesDomain);
        }
        return trees;

    }

    @Override
    public List<TreeNodesDomain> getUserList(List<UserInfoModel> teamlist,String companyId) {
        List<GroupInfoDomain> groupList = groupService.getAllGroup(companyId);
        List<TreeNodesDomain> userList = newUserService.getAllUserList(companyId);
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        constructTreeV3(trees,0,groupList, userList,teamlist);
        return trees;

    }


    /**
     *
     *临时用户
     * ***/
    @Override
    public List<TreeNodesDomain> getCasualUserList(String companyId) {
        List<GroupInfoDomain> groupList = groupService.getAllGroup(companyId);
        List<TreeNodesDomain> userList = casualUserService.getcasualUserList();
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        constructTreeCasual(trees,0,groupList, userList);
        return trees;
    }

    @Override
    public List<TreeNodesDomain> getGroupList(String companyId) {
        List<GroupInfoDomain> groupList = groupService.getAllGroup(companyId);
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        constructGroupTree(trees,0,groupList);
        return trees;
    }

    @Override
    public List<TreeNodesDomain> getCasualGroupList(String companyId) {
        List<GroupInfoDomain> groupList = groupService.getAllGroup(companyId);
        List<TreeNodesDomain> trees = new ArrayList<TreeNodesDomain>();
        constructCasualGroupTree(trees,0,groupList);
        return trees;
    }


    private List<TreeNodesDomain> constructTreeCasual(List<TreeNodesDomain> trees,int parentId,List<GroupInfoDomain> groupList, List<TreeNodesDomain> userList) {
        TreeNodesDomain treeNode = new TreeNodesDomain();
           for (GroupInfoDomain groupInfoDomain : groupList) {
               //查找根节点
               if (null!= groupInfoDomain.getParentGroupId() && groupInfoDomain.getParentGroupId() == parentId) {
                    treeNode = new TreeNodesDomain(groupInfoDomain);
                   if (parentId == 0) {
                       treeNode.setHref("/cipher/casual/userlist?groupId=" + groupInfoDomain.getGroupId());
                   } else {
                       treeNode.setHref("/cipher/casual/userlist?groupId=" + groupInfoDomain.getGroupId());
                   }
                   treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                   treeNode.setGroupName(groupInfoDomain.getGroupName());
                   treeNode.setParentGroupId(groupInfoDomain.getParentGroupId() + "");
                   treeNode.setNodes(constructTreeCasual(treeNode.getNodes(), groupInfoDomain.getGroupId(), groupList, userList));
                   trees.add(treeNode);
               }
           }
           for (TreeNodesDomain userInfo : userList) {
               if (Integer.valueOf(userInfo.getGroupId()) == parentId) {
                   userInfo.setHref("/cipher/casual/list?accountNumber=" + userInfo.getAccountNumber() + "&groupId=" + userInfo.getGroupId());
                   userInfo.setText();
                   userInfo.setAccountNumber(userInfo.getAccountNumber());
                   userInfo.setGroupId(userInfo.getGroupId());
                   trees.add(userInfo);
               }
           }
        return trees;
    }

    private Integer rootNode = 0 ;//根节点
    private List<TreeNodesDomain> constructTreeV2(List<TreeNodesDomain> trees,int parentId,List<GroupInfoDomain> groupList, List<TreeNodesDomain> userList,String path,String idPath) {
        TreeNodesDomain treeNode = new TreeNodesDomain();

        if(groupList.size()>0){
            for (GroupInfoDomain groupInfoDomain : groupList) {
                //查找根节点
                if (!StringUtils.isEmpty(String.valueOf(groupInfoDomain.getParentGroupId()))&&groupInfoDomain.getParentGroupId()== parentId) {
                    treeNode = new TreeNodesDomain(groupInfoDomain);
                    if (parentId == 0){
                        path="";
                        idPath = "";
                        treeNode.setPath(groupInfoDomain.getGroupName());
                        treeNode.setIdPath(String.valueOf(groupInfoDomain.getGroupId()));
                        rootNode = groupInfoDomain.getGroupId();
                    }
                    treeNode.setRootNode(String.valueOf(rootNode));
                    treeNode.setHref("/cipher/newUser/userlist?json");
                    treeNode.setPath(path+groupInfoDomain.getGroupName()+"/");
                    treeNode.setIdPath(idPath+groupInfoDomain.getGroupId()+"/");
                    treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                    treeNode.setGroupName(groupInfoDomain.getGroupName());
                    treeNode.setParentGroupId(groupInfoDomain.getParentGroupId()+"");
                    treeNode.setNodes(constructTreeV2(treeNode.getNodes(),groupInfoDomain.getGroupId(),groupList,userList,path+groupInfoDomain.getGroupName()+"/",idPath+groupInfoDomain.getGroupId()+"/"));
                    if (treeNode.getNodes() == null || treeNode.getNodes().size() == 0){
                        treeNode.setNodes(null);
                    }
                    trees.add(treeNode);
                }
            }
            for (TreeNodesDomain userInfo:userList){
                if (Integer.valueOf(userInfo.getGroupId()) == parentId){
                    userInfo.setHref("/cipher/newUser/getlist?json");
                    userInfo.setText();
                    userInfo.setAccountNumber(userInfo.getAccountNumber());
                    userInfo.setGroupId(userInfo.getGroupId());
                    userInfo.setNodes(null);
                    userInfo.setPath(path);
                    trees.add(userInfo);
                }

            }
       }else{
        treeNode.setHref("/cipher/newUser/getlist?json");
       }
        return trees;
    }


    private List<TreeNodesDomain> constructGroupTree(List<TreeNodesDomain> trees,int parentId,List<GroupInfoDomain> groupList) {
        for (GroupInfoDomain groupInfoDomain : groupList) {
            //查找根节点
            if (groupInfoDomain.getParentGroupId() == parentId) {
                TreeNodesDomain treeNode = new TreeNodesDomain(groupInfoDomain);
                treeNode.setHref("/cipher/newUser/userlist?groupId="+groupInfoDomain.getGroupId());
                treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                treeNode.setGroupName(groupInfoDomain.getGroupName());
                treeNode.setParentGroupId(groupInfoDomain.getParentGroupId()+"");
                treeNode.setNodes(constructGroupTree(treeNode.getNodes(),groupInfoDomain.getGroupId(),groupList));
                trees.add(treeNode);
            }
        }

        return trees;
    }

    private List<TreeNodesDomain> constructCasualGroupTree(List<TreeNodesDomain> trees,int parentId,List<GroupInfoDomain> groupList) {
        for (GroupInfoDomain groupInfoDomain : groupList) {
            //查找根节点
            if (groupInfoDomain.getParentGroupId() == parentId) {
                TreeNodesDomain treeNode = new TreeNodesDomain(groupInfoDomain);
                if (parentId == 0){
                    treeNode.setHref("/cipher/casual/userlist?groupId="+groupInfoDomain.getGroupId());
                }else {
                    treeNode.setHref("/cipher/casual/userlist?groupId="+groupInfoDomain.getGroupId());
                }
                treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                treeNode.setGroupName(groupInfoDomain.getGroupName());
                treeNode.setParentGroupId(groupInfoDomain.getParentGroupId()+"");
                treeNode.setNodes(constructCasualGroupTree(treeNode.getNodes(),groupInfoDomain.getGroupId(),groupList));
                trees.add(treeNode);
            }
        }

        return trees;
    }



    private List<TreeNodesDomain> constructTreeV3(List<TreeNodesDomain> trees,int parentId,List<GroupInfoDomain> groupList, List<TreeNodesDomain> userList,List<UserInfoModel> teamList) {
        TreeNodesDomain treeNode = new TreeNodesDomain();
        if(groupList.size()>0){
            for (GroupInfoDomain groupInfoDomain : groupList) {
                //查找根节点
                if (!StringUtils.isEmpty(String.valueOf(groupInfoDomain.getParentGroupId()))&&groupInfoDomain.getParentGroupId()== parentId) {
                    treeNode = new TreeNodesDomain(groupInfoDomain);
                    treeNode.setGroupId(String.valueOf(groupInfoDomain.getGroupId()));
                    treeNode.setGroupName(groupInfoDomain.getGroupName());
                    treeNode.setParentGroupId(groupInfoDomain.getParentGroupId()+"");
                    treeNode.setNodes(constructTreeV3(treeNode.getNodes(),groupInfoDomain.getGroupId(),groupList,userList,teamList));
                    if (treeNode.getNodes() == null || treeNode.getNodes().size() == 0){
                        treeNode.setNodes(null);
                     }
                    trees.add(treeNode);
                }
            }

            for (TreeNodesDomain userInfo:userList){
                if (Integer.valueOf(userInfo.getGroupId()) == parentId){
                    for(UserInfoModel userInfoModel:teamList){
                        if(userInfo.getAccountNumber().equals(userInfoModel.getAccountNumber())){
                            userInfo.setState();
                        }
                    }
                    userInfo.setText();
                    userInfo.setAccountNumber(userInfo.getAccountNumber());
                    userInfo.setGroupId(userInfo.getGroupId());
                    userInfo.setNodes(null);
                    trees.add(userInfo);
                }
            }
        }
        return trees;
    }




}
