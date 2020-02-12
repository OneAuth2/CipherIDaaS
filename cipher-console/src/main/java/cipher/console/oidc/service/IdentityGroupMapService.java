package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.TreeNodesDomain;

import java.util.List;

/**
 * Created by 95744 on 2018/9/15.
 */
public interface IdentityGroupMapService {
    /**
     * create by 田扛
     * create time 2019年3月21日14:22:55
     * 初始化部门结构树
     * @param list
     * @return
     */
    List<TreeNodesDomain>  getTreeNodeDomain(List<IdentityGroupMapDomain> list);

    public int insertIdentityGroupMap(IdentityGroupMapDomain form);


    public int deleteIdentityGroupMap(int  identityId);

    List<IdentityGroupMapDomain> selectIdentityGroupMap(IdentityGroupMapDomain form);
    List<IdentityGroupMapDomain> getselectedIdentityGroupMap(Integer id);
    List<IdentityGroupMapDomain> notselectedIdentityGroupMap(Integer id);

    List<GroupInfoDomain> notselectedIdentityGroupMapNew(List<Integer> list);
}
