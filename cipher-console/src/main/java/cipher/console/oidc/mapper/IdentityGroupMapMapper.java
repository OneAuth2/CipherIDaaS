package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;

import java.util.List;

/**
 * Created by 95744 on 2018/9/15.
 */
public interface IdentityGroupMapMapper {

    public int insertIdentityGroupMap(IdentityGroupMapDomain form);
    public int deleteIdentityGroupMap(int identityId);

    List<IdentityGroupMapDomain> selectIdentityGroupMap(IdentityGroupMapDomain form);
    List<IdentityGroupMapDomain>  selectedIdentityStrategy(Integer id);//搜索该id下的groupName和groupId
    List<IdentityGroupMapDomain> notSelectIdentityStrategy(Integer id);//搜索该id下的没有的groupName和GroupID

    List<GroupInfoDomain> notselectedIdentityGroupMapNew(List<Integer> list);



}
