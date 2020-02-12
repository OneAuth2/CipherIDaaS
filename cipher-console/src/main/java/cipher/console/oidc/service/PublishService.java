package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;

import java.util.Map;

/**
 * @Author: TK
 * @Date: 2019/1/2 17:21
 */
public interface PublishService {
    public Map<String,Object> getPublishList(ApplicationInfoDomain applicationInfoDomain, DataGridModel form);
    public void publishApplication(String id,String releaseState);
}
