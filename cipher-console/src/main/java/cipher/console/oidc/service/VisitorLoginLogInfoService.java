package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;

import java.util.Map;

/**
 * Created by 95744 on 2018/9/26.
 */
public interface VisitorLoginLogInfoService {

    public  Map<String,Object> getVistorLoginLogList(VistorLoginLogInfo form,DataGridModel pageModel);


}
