package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.mapper.CasualUserMapper;
import cipher.console.oidc.mapper.GroupMapper;
import cipher.console.oidc.mapper.RoleInfoDomainMapper;
import cipher.console.oidc.service.CasualUserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Created by 95744 on 2018/9/25.
 */
@Service
public class CasualUserServiceImpl implements CasualUserService {

    @Autowired
    private CasualUserMapper casualUserMapper;


    @Autowired
    private RoleInfoDomainMapper roleInfoDomainMapper;

    @Autowired
    private GroupMapper groupMapper;

    @Override
    public Map<String, Object> getCasualUserList(CasualUserInfo form, DataGridModel pageModel) {
       form=(form==null?new CasualUserInfo():form);
       form.setPageData(pageModel);
       Map<String,Object> map=new HashMap<>();
       List<CasualUserInfo> list=casualUserMapper.selectCasualUserList(form);
        String roleName="";
        String groupName="";
        for(CasualUserInfo casualUserInfo:list){
            List<RoleInfoDomain>  rolelist=roleInfoDomainMapper.selectRoleNameList(casualUserInfo.getAccountNumber());
            List<GroupInfoDomain> grouplist=groupMapper.selectGroupNameList(casualUserInfo.getAccountNumber());
            List<String> roleNameList=new ArrayList<>();
            List<String> groupNamelist=new ArrayList<>();
            for(RoleInfoDomain roleInfoDomain:rolelist){
                roleNameList.add(roleInfoDomain.getRoleName());
                roleName=StringUtils.join(roleNameList, ",");
                casualUserInfo.setRoleName(roleName);
            }
            for(GroupInfoDomain groupInfoDomain:grouplist){
                groupNamelist.add(groupInfoDomain.getGroupName());
                groupName=StringUtils.join(groupNamelist, ",");
                casualUserInfo.setGroupName(groupName);
            }


        }
       map.put("rows",list);
       map.put("total",casualUserMapper.selectCasualCount(form));
        return map;
    }

    @Override
    public Map<String,Object> getCasualUser(String accountNumber) {
        Map map=new HashMap();
        DatailCasualUserInfo user= casualUserMapper.getCasualUser(accountNumber);
        map.put("user",casualUserMapper.getCasualUser(accountNumber));
        return map;
    }

    @Override
    public List<CasualUserInfo> getUserListByGroupId(CasualUserInfo form) {
        return casualUserMapper.selectCasualUserListByGroupId(form);
    }

    @Override
    public List<TreeNodesDomain> getcasualUserList() {
        return casualUserMapper.getcasualUserList();
    }

    @Override
    public Map<String, Object> getCasualUserListByGroupId(CasualUserInfo form, DataGridModel page) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new CasualUserInfo() : form);
       // List<GroupInfoDomain> groupList=groupMapper.selectGroupList(Integer.valueOf(form.getGroupId()));
        List<CasualUserInfo>newlist= getListByGroupId(Integer.valueOf(form.getGroupId()), form);
        List<CasualUserInfo> list= new ArrayList<CasualUserInfo>();
        Set<String> set=new HashSet<String>();
        for (CasualUserInfo casualUserInfo : newlist) {
            if (casualUserInfo == null) {
                continue;
            }
            String  userName = casualUserInfo.getAccountNumber();
            if (userName != null) {
                if (!set.contains(userName)) { //set中不包含重复的
                    set.add(userName);
                    list.add(casualUserInfo);
                } else {
                    continue;
                }
            }
        }
        set.clear();
        int pages = list.size()/page.getRows();
        if (pages * page.getRows() != list.size()){
            pages++;
        }
        String roleName="";
        String groupName="";
        List<CasualUserInfo> newList=null;
        if(list.size()>0) {
            if (page.getPage() == pages) {
                newList = list.subList((page.getPage() - 1) * page.getRows(), list.size());
                map.put("rows", newList);
            } else {
                newList = list.subList((page.getPage() - 1) * page.getRows(), (page.getPage()) * page.getRows());
                map.put("rows", newList);
            }

            for(CasualUserInfo newUserInfo:newList){
                List<RoleInfoDomain>  rolelist=roleInfoDomainMapper.selectRoleNameList(newUserInfo.getAccountNumber());
                List<GroupInfoDomain> grouplist=groupMapper.selectGroupNameList(newUserInfo.getAccountNumber());
                List<String> roleNameList=new ArrayList<>();
                List<String> groupNamelist=new ArrayList<>();
                for(RoleInfoDomain roleInfoDomain:rolelist){
                    roleNameList.add(roleInfoDomain.getRoleName());
                    roleName=StringUtils.join(roleNameList, ",");
                    newUserInfo.setRoleName(roleName);
                }
                for(GroupInfoDomain groupInfoDomain:grouplist){
                    groupNamelist.add(groupInfoDomain.getGroupName());
                    groupName=StringUtils.join(groupNamelist, ",");
                    newUserInfo.setGroupName(groupName);
                }


            }
        }
        map.put("return_code", "1");
        map.put("total",pages);
        map.put("records",list.size());
        return map;
    }



    public  List<CasualUserInfo> getListByGroupId(int groupId,CasualUserInfo form) {
        List<CasualUserInfo> newUserInfoList = new ArrayList<>();
        List<GroupInfoDomain> groupInfoDomainList = groupMapper.selectGroupListByParentId(groupId);
        for (GroupInfoDomain GroupInfoDomain : groupInfoDomainList) {
            List<CasualUserInfo> userlist = getNewUserListByGroupId(groupId, form);
            newUserInfoList.addAll(userlist);
            if (GroupInfoDomain.getParentGroupId() == groupId) {
                List<CasualUserInfo> list = getListByGroupId(GroupInfoDomain.getGroupId(), form);
                newUserInfoList.addAll(list);
            }
        }

        List<CasualUserInfo> userlist = getNewUserListByGroupId(groupId, form);
        newUserInfoList.addAll(userlist);
        return newUserInfoList;
    }


    public  List<CasualUserInfo> getNewUserListByGroupId(int groupId,CasualUserInfo form){
        form.setGroupId(String.valueOf(groupId));
        List<CasualUserInfo> userList=casualUserMapper.selectCasualList(form);
        return userList;
    }


}
