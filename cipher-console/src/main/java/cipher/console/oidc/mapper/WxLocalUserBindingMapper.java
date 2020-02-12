package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.WxLocalUserMapDomain;
import cipher.console.oidc.domain.web.WxUserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lqgzj
 * @date 2019-10-12
 */
public interface WxLocalUserBindingMapper {
    /**
     * 查出企业微信与本地用户的绑定表
     * @param allWxUser
     * @return
     */
    List<String> queryAlreDyInBindUser(@Param(value = "list") List<WxUserDomain> allWxUser);

    /**
     * 批量插入企业微信与本地用户表的映射关系
     * @param mapList
     */
    void insertList(@Param(value = "list") List<WxLocalUserMapDomain> mapList);
}
