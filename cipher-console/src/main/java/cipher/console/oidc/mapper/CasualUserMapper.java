package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.CasualUserInfo;
import cipher.console.oidc.domain.web.DatailCasualUserInfo;
import cipher.console.oidc.domain.web.TreeNodesDomain;

import java.util.List;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface CasualUserMapper {

    public List<CasualUserInfo> selectCasualUserList(CasualUserInfo form);
    public int selectCasualCount(CasualUserInfo form);
    public DatailCasualUserInfo getCasualUser(String accountNumber);
    public List<CasualUserInfo> selectCasualUserListByGroupId(CasualUserInfo form);

    List<TreeNodesDomain> getcasualUserList();

    public List<CasualUserInfo>selectCasualList(CasualUserInfo form);

}
