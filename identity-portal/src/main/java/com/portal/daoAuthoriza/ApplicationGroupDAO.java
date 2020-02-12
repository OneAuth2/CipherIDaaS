package com.portal.daoAuthoriza;


import com.portal.domain.ApplicationInfoDomain;
import com.portal.model.GroupApplicationModel;
import com.portal.model.UserInfoModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ApplicationGroupDAO {
    public List<ApplicationInfoDomain> getApplicationsByGroupId(int groupId);

    List<ApplicationInfoDomain> selectApplication(@Param(value = "accountNumber") String accountNumber);

    List<ApplicationInfoDomain> getApplicationByAccountNumberAndGroupId(UserInfoModel userInfo);

    List<GroupApplicationModel> selectAllGroupApplication();

    List<GroupApplicationModel> selectUserRoleApplication(String accountNumber);

    public List<GroupApplicationModel>  selectNewApplicationList(@Param(value = "uuid") String uuid,@Param(value = "queryName")String queryName);
}
