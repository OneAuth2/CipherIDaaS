package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AccountAdBindingDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-06-10 16:37
 */
public interface AccountAdBindingMapper {

    /**
     * 批量插入AD用户和本地用户之间的绑定关系
     *
     * @param list
     */
    public void insert(@Param(value = "list") List<AccountAdBindingDomain> list);

}
