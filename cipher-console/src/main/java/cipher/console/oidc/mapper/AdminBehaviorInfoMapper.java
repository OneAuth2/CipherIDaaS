package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.AdminBehaviorExcle;
import cipher.console.oidc.domain.exceldomain.NewAdminBehaviorExcle;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;

import java.util.List;

public interface AdminBehaviorInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AdminBehaviorInfo record);

    int insertSelective(AdminBehaviorInfo record);

    AdminBehaviorInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AdminBehaviorInfo record);

    int updateByPrimaryKey(AdminBehaviorInfo record);


    List<AdminBehaviorInfo> selectAdminBehaviorList(AdminBehaviorInfo record);

    int selectAdminBehaviorCount(AdminBehaviorInfo record);

     List<NewAdminBehaviorExcle> exportExcle(AdminBehaviorInfo form);


}