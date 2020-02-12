package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: TK
 * @Date: 2019/1/2 17:33
 */
public interface PublishMapper {
     List<ApplicationInfoDomain> getPublishList(ApplicationInfoDomain applicationInfoDomain);
     int getPublishListCount(ApplicationInfoDomain applicationInfoDomain);
     public void publishApplication(@Param(value = "id") String id,@Param(value = "releaseState")String releaseState);
}
