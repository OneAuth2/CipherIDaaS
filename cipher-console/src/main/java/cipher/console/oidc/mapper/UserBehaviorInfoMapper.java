package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.UserBehaviorExcle;
import cipher.console.oidc.domain.web.UserBehaviorInfo;

import java.util.List;

public interface UserBehaviorInfoMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserBehaviorInfo record);

    int insertSelective(UserBehaviorInfo record);

    UserBehaviorInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserBehaviorInfo record);

    int updateByPrimaryKey(UserBehaviorInfo record);

    List<UserBehaviorInfo> selectUserBehaviorList(UserBehaviorInfo record);

    int selectUserBehaviorCount(UserBehaviorInfo record);

    List<UserBehaviorExcle> exportExcle(UserBehaviorInfo form);

    String selectUserInfoByUserId(String UserId);
}