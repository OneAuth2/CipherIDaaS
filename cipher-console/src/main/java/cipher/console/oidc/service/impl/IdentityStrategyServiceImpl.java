package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.authsettingsdomain.SecondAuthStrategyDomain;
import cipher.console.oidc.domain.web.IdentityGroupMapDomain;
import cipher.console.oidc.domain.web.IdentityStrategyDomain;
import cipher.console.oidc.mapper.IdentityStrategyMapper;
import cipher.console.oidc.service.IdentityGroupMapService;
import cipher.console.oidc.service.IdentityStrategyService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class IdentityStrategyServiceImpl implements IdentityStrategyService {

    @Autowired
    private IdentityStrategyMapper identityStrategyMapper;
    @Autowired
    private IdentityGroupMapService identityGroupMapService;
    @Override
    public Map<String,Object> queryIdentityStrategyList(IdentityStrategyDomain identityStrategyDomain) {
         List<IdentityStrategyDomain> list=identityStrategyMapper.queryIdentityStrategyPageList(identityStrategyDomain);
        Map<String,Object> map=new HashMap<>();
        List<IdentityStrategyDomain> domainList = new ArrayList<>();
        for(IdentityStrategyDomain domain:list){
            if(domain.getId()==1){
                domain.setGroupNames("全员");
            }
            IdentityGroupMapDomain ss=new IdentityGroupMapDomain();
            ss.setIdentityId(domain.getId());
            List<IdentityGroupMapDomain> newlist=identityGroupMapService.selectIdentityGroupMap(ss);
            List<String> groupIds=new ArrayList<>();
            for(IdentityGroupMapDomain domain1:newlist){
                if(domain1.getGroupId()==0){
                    groupIds.add("全员");
                }else{
                    if (StringUtils.isNotBlank(domain1.getGroupName())){
                        groupIds.add(String.valueOf(domain1.getGroupName()));
                    }

                }
                String str=String.join(",", groupIds);
                domain.setGroupNames(str);
            }
            Gson gson = new Gson();
            domain.setSecondAuthType(gson.fromJson(domain.getSecondAuthType().toString(), SecondAuthStrategyDomain.class));
            domainList.add(domain);
        }
        map.put("rows",domainList);
        map.put("total",identityStrategyMapper.queryIdentityStrategyCount(identityStrategyDomain));
        return map;
    }



    @Override
    public void saveIdentityStrategy(IdentityStrategyDomain identityStrategyDomain) {
        identityStrategyMapper.saveIdentityStrategy(identityStrategyDomain);
    }

    @Override
    public void deleteIdentityStrategy(IdentityStrategyDomain identityStrategyDomain) {
        identityStrategyMapper.deleteIdentityStrategy(identityStrategyDomain);
    }

    @Override
    public void modifyIdentityStrategy(IdentityStrategyDomain identityStrategyDomain) {
        identityStrategyMapper.modifyIdentityStrategy(identityStrategyDomain);
    }

    @Override
    public IdentityStrategyDomain queryIdentityStrategyById(IdentityStrategyDomain identityStrategyDomain) {
        return identityStrategyMapper.queryIdentityStrategyById(identityStrategyDomain);
    }

    @Override
    public int queryCountByStrategyName(Integer id, String strategyName) {
        return identityStrategyMapper.queryCountByStrategyName(id,strategyName);
    }

    @Override
    public int queryCountByPriority(Integer id, Integer priority) {
        return identityStrategyMapper.queryCountByPriority(id,priority);
    }


}
