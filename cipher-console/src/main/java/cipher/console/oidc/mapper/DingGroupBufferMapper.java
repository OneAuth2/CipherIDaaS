package cipher.console.oidc.mapper;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingGroupDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-05-13 10:14
 */
public interface DingGroupBufferMapper {

    public void insert(@Param(value = "list") List<DingGroupDomain> list);

    /**
     * 查询已经存在于缓冲表的group
     *
     * @param list
     * @return
     */
    public List<Long> queryIdList(@Param("list") List<DingGroupDomain> list);

    /**
     * 分页获取某个公司下钉钉的组织结构
     *
     * @param dataGridModel
     * @param companyId
     * @return
     */
    public List<DingGroupDomain> queryList(@Param(value = "dataGridModel") DataGridModel dataGridModel,
                                           @Param(value = "companyId") String companyId);

    /**
     * 查询某个公司下部门的总数
     *
     * @param companyId
     * @return
     */
    public int queryCountByCompany(String companyId);

    /**
     * 查询某个公司下所有的组织架构
     *
     * @param companyId
     * @return
     */
    public List<DingGroupDomain> queryGroupByCompany(String companyId);

    /**
     * 删除某个公司在缓冲表中的数据
     *
     * @param companyId
     */
    public void delete(String companyId);

}
