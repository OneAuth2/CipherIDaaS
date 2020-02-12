package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.CasualUserInfo;
import cipher.console.oidc.domain.web.TreeNodesDomain;

import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface CasualUserService {


    public Map<String,Object> getCasualUserList(CasualUserInfo form,DataGridModel pageModel);
    public Map<String,Object> getCasualUser(String accountNumber);

    public List<CasualUserInfo>  getUserListByGroupId(CasualUserInfo form);

    public List<TreeNodesDomain> getcasualUserList();

    public Map<String, Object> getCasualUserListByGroupId(CasualUserInfo form,DataGridModel pageModel);
}
