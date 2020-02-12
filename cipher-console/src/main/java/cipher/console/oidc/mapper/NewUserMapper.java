package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.domain.web.TreeNodesDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface NewUserMapper {

    public List<NewUserInfo> selectNewUserInfo(NewUserInfo newUserInfo);

    int selectNewUserCount(NewUserInfo newUserInfo);

    public void updateByIsdelete(@Param(value = "uuid")String uuid,@Param(value = "isdelete") int isdelete);

    public List<NewUserInfo> selectDeleteUserInfo(NewUserInfo newUserInfo);

    int selectDeleteUserCount(NewUserInfo newUserInfo);

    public void reallyDelete(@Param(value = "uuid")String uuid);

    List<NewUserInfo> getUserListByGroupId(NewUserInfo form);

    List<TreeNodesDomain> getAllUserList(String companyId);

    List<NewUserInfo> geUserList();

    public List<NewUserInfo> queryUserList(NewUserInfo form);

    List<NewUserInfo> getNoGroupUserList(NewUserInfo form);

    int getNoGroupUserCount(NewUserInfo form);

    public List<NewUserInfo> getAllList(NewUserInfo form);


    public List<NewUserInfo> getUserListByCondition(@Param(value = "list") List<String> list,@Param(value = "userInfo") NewUserInfo form);


    public  List<String> getNoLoginUser(NewUserInfo form);

    int getUserListByConditionCount(@Param(value = "list") List<String> list,@Param(value = "userInfo")NewUserInfo form);



    NewUserInfo getNewUserInfo(@Param(value = "uuid")String uuid);


    public List<NewUserInfo> getNewAllList(NewUserInfo form);

    //在无部门下查出
    List<NewUserInfo> getNewUserByGroupId(NewUserInfo form);



    List<TreeNodesDomain> getNewUserListByGroupId(@Param(value = "companyId") String companyId,@Param(value = "groupId") Integer groupId);
}
