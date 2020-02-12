package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.DingLocalUserMapDomain;
import cipher.console.oidc.domain.web.DingUserDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 钉钉和本地用户的绑定关系
 *
 * @Author: zt
 * @Date: 2019-05-14 15:34
 */
public interface DingLocalUserBindingMapper {

    /**
     * 查询出已经存在于钉钉和本地用户绑定表的部分用户
     *
     * @param list
     * @return
     */
    public List<String> queryAlreDyInBindUser(@Param("list") List<DingUserDomain> list);

    /**
     * 批量插入钉钉和本地用户的关联关系
     *
     * @param list
     */
    public void insertList(@Param("list") List<DingLocalUserMapDomain> list);

}
