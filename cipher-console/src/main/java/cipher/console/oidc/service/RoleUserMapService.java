package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.RoleInfoDomain;
import cipher.console.oidc.domain.web.RoleUserMapInfo;

import java.util.List;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface RoleUserMapService {

    List<RoleUserMapInfo> selectUserMapList(RoleUserMapInfo form);

    public RoleUserMapInfo getRoleUserInfo(RoleUserMapInfo form);

    public  void insertRoleUserMapInfo(RoleUserMapInfo form);

    public void deleteRoleUserMap(int roleId);

    List<RoleInfoDomain> selectHaveRoleList(String accountNumber);

    public void deleteRoleUserMapByAccountNumber(String accountNumber);


    public void deleteRoleUser(RoleUserMapInfo form);





}
