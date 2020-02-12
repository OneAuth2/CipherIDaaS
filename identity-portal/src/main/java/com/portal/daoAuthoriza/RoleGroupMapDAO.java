package com.portal.daoAuthoriza;


import com.portal.model.GroupApplicationModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RoleGroupMapDAO {

    public List<GroupApplicationModel> selectGroupApplicationList(@Param(value = "groupId") int groupId);


}
