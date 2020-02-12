package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.WxGroupDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lqgzj
 * @date 2019-10-11
 */
public interface WxGroupMapper {
    List<Long> queryAlredyExistsGroup(@Param(value = "list")List<WxGroupDomain> list);
}
