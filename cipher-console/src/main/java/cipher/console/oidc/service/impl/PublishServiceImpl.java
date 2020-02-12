package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.mapper.PublishMapper;
import cipher.console.oidc.service.PublishService;
import org.apache.commons.collections.map.HashedMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/1/2 17:24
 */
@Service
public class PublishServiceImpl implements PublishService {
    @Autowired
    private PublishMapper publishMapper;
    @Override
    public Map<String, Object> getPublishList(ApplicationInfoDomain applicationInfoDomain, DataGridModel form) {
        applicationInfoDomain=(applicationInfoDomain==null?new ApplicationInfoDomain():applicationInfoDomain);
        applicationInfoDomain.setPageData(form);

        Map<String,Object>   map=new HashedMap();
        List rows=publishMapper.getPublishList(applicationInfoDomain);
        int total=publishMapper.getPublishListCount(applicationInfoDomain);
        map.put("rows",rows);
        map.put("total",total);
        return map;
    }

    @Override
    public void publishApplication(String id,String releaseState) {
        publishMapper.publishApplication(id,releaseState);
    }
}
