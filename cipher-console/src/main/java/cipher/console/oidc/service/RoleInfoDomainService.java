package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.RoleInfoDomain;

import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/20.
 */
public interface RoleInfoDomainService {

    int deleteByPrimaryKey(Integer id);

    int insertSelective(RoleInfoDomain record);

    RoleInfoDomain selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(RoleInfoDomain record);

    Map<String,Object> selectRoleInfoList(RoleInfoDomain record, DataGridModel pageModel);

    List<RoleInfoDomain> selectAllRoleList();

    List<RoleInfoDomain> selectNoneRoleList(List<Integer> list);

    RoleInfoDomain selectRoleInfo(RoleInfoDomain record);



    List<RoleInfoDomain> selectNoneRoleGroupList(List<Integer> list);






}
