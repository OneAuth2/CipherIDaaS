package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface NewUserService {


    public Map<String,Object> getNewUserList(NewUserInfo form, DataGridModel pageModel);

    public void updateByIsdelete(String accountNumber,int isdelete);

    public void reallyDelete(String accountNumber);

    public Map<String,Object> getDeleteUserList(NewUserInfo form, DataGridModel pageModel);

    public List<NewUserInfo> getUserListByGroupId(NewUserInfo form);


    public List<TreeNodesDomain> getAllUserList(String companyId);

    public Map<String,Object> getUserListByGroupId(NewUserInfo form, DataGridModel pageModel);

    public List<NewUserInfo> queryUserList(NewUserInfo form);

    NewUserInfo getNewUserInfo(@Param(value = "accountNumber")String accountNumber);

    List<TreeNodesDomain> getUserListByGroupId(String companyId,Integer groupId);


}
