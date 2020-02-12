package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import cipher.console.oidc.mapper.IdentityGroupMapMapper;
import cipher.console.oidc.service.IdentityGroupMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 95744 on 2018/9/15.
 */
@Service
public class IdentityGroupMapServiceImpl implements IdentityGroupMapService{


    @Autowired
    private IdentityGroupMapMapper  identityGroupMapMapper;


    @Override
    public List<TreeNodesDomain> getTreeNodeDomain(List<IdentityGroupMapDomain> list) {
        return null;
    }

    @Override
    public int insertIdentityGroupMap(IdentityGroupMapDomain form) {
        return identityGroupMapMapper.insertIdentityGroupMap(form);
    }

    @Override
    public int deleteIdentityGroupMap(int identityId) {
        return identityGroupMapMapper.deleteIdentityGroupMap(identityId);
    }

    @Override
    public List<IdentityGroupMapDomain> selectIdentityGroupMap(IdentityGroupMapDomain form) {
        return identityGroupMapMapper.selectIdentityGroupMap(form);
    }

    @Override
    public List<IdentityGroupMapDomain> getselectedIdentityGroupMap(Integer id) {
        return identityGroupMapMapper.selectedIdentityStrategy(id);
    }

    @Override
    public List<IdentityGroupMapDomain> notselectedIdentityGroupMap(Integer id) {
        return identityGroupMapMapper.notSelectIdentityStrategy(id);
    }

    @Override
    public List<GroupInfoDomain> notselectedIdentityGroupMapNew(List<Integer> list) {
        return identityGroupMapMapper.notselectedIdentityGroupMapNew(list);
    }

}
