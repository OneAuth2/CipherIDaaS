package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingGroupDomain;
import cipher.console.oidc.domain.web.WxGroupDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author lqgzj
 * @date 2019-10-11
 */
public interface WxGroupBufferMapper {
    List<WxGroupDomain> queryList(@Param(value = "dataGridModel") DataGridModel dataGridModel,
                                  @Param(value = "companyId") String companyId);

    int queryCountByCompany(String companyId);

    void insert(@Param(value = "list") List<WxGroupDomain> list);

    List<Long> queryIdList(@Param(value = "list") List<WxGroupDomain> list);

    List<WxGroupDomain> queryGroupByCompany(String companyId);

    void delete(String companyId);
}
