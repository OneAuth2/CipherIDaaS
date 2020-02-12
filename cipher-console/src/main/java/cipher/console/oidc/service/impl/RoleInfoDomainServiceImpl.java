package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.RoleApplicationMapInfo;
import cipher.console.oidc.domain.web.RoleInfoDomain;
import cipher.console.oidc.mapper.RoleApplicationMapInfoMapper;
import cipher.console.oidc.mapper.RoleInfoDomainMapper;
import cipher.console.oidc.service.RoleInfoDomainService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/20.
 */
@Service
public class RoleInfoDomainServiceImpl implements RoleInfoDomainService{

    @Autowired
    private RoleInfoDomainMapper  roleInfoDomainMapper;

    @Autowired
    private RoleApplicationMapInfoMapper roleApplicationMapInfoMapper;
    @Override
    public int deleteByPrimaryKey(Integer id) {
        return roleInfoDomainMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insertSelective(RoleInfoDomain record) {
        return roleInfoDomainMapper.insertSelective(record);
    }

    @Override
    public RoleInfoDomain selectByPrimaryKey(Integer id) {
        return roleInfoDomainMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(RoleInfoDomain record) {
        return roleInfoDomainMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public Map<String, Object> selectRoleInfoList(RoleInfoDomain form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new RoleInfoDomain() : form);
        form.setPageData(pageModel);
        List<RoleInfoDomain> list = roleInfoDomainMapper.selectRoleInfoList(form);
        for(RoleInfoDomain domain:list){
            List<RoleApplicationMapInfo> appList=roleApplicationMapInfoMapper.selectApplicationName(domain.getId());
            List<String> newList=new ArrayList<>();
            for(RoleApplicationMapInfo newdomain:appList){
                newList.add(newdomain.getApplicationName());
            }
            String ss =  StringUtils.join(newList , ",");
            domain.setApplications(ss);
            int count=roleApplicationMapInfoMapper.getUserAccount(domain.getId());
            domain.setAuthCount(count);
        }
        int total = roleInfoDomainMapper.selectRoleInfoCount(form);
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public List<RoleInfoDomain> selectAllRoleList() {
        return roleInfoDomainMapper.selectAllRoleList();
    }


    @Override
    public List<RoleInfoDomain> selectNoneRoleList(List<Integer> list) {
        return roleInfoDomainMapper.selectNoneRoleList(list);
    }

    @Override
    public RoleInfoDomain selectRoleInfo(RoleInfoDomain record) {
        return roleInfoDomainMapper.selectRoleInfo(record);
    }

    @Override
    public List<RoleInfoDomain> selectNoneRoleGroupList(List<Integer> list) {
        return roleInfoDomainMapper.selectNoneRoleGroupList(list);
    }
}
