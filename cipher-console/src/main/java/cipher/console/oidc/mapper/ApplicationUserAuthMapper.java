package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.TeamInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: TK
 * @Date: 2018/12/8 14:42
 */
public interface ApplicationUserAuthMapper {
    void deleteUserAuth(@Param(value = "applicationId") String applicationId, @Param("uuid") String uuid);
    List<GroupInfoDomain>  getDepatment(@Param(value = "applicationId") String applicationId, @Param("uuid") String uuid);
    List<TeamInfo>  getTeam(@Param(value = "applicationId") String applicationId, @Param("uuid") String uuid);
}
