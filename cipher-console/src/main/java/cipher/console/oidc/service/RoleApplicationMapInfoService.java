package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.RoleApplicationMapInfo;

import java.util.List;

/**
 * Created by 95744 on 2018/9/20.
 */
public interface RoleApplicationMapInfoService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(RoleApplicationMapInfo record);

    RoleApplicationMapInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleApplicationMapInfo record);

    List<RoleApplicationMapInfo> getRoleApplicationMapList(Integer RoleId);

    int deleteByRoleId(Integer RoleId);



}
