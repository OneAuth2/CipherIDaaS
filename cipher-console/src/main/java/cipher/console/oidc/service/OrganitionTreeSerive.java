package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import cipher.console.oidc.model.UserInfoModel;


import java.util.List;

/**
 * Created by 95744 on 2018/9/28.
 */
public interface OrganitionTreeSerive {

    public List<TreeNodesDomain> getAllUserList(String companyId);

    public List<TreeNodesDomain> getUserList(List<UserInfoModel> list,String companyId);

    public List<TreeNodesDomain> getCasualUserList(String companyId);

    public List<TreeNodesDomain> getGroupList(String companyId);

    public List<TreeNodesDomain> getCasualGroupList(String companyId);




}
