package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingGroupDomain;
import cipher.console.oidc.domain.web.GroupTreeDomain;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019-05-11 15:28
 */
public interface DingGroupService {


    /**
     * 构造部门的树状结构，不包含用户
     *
     * @param companyId
     * @return
     */
    public List<GroupTreeDomain> getGroupTreeList(String companyId);

    /**
     * 扫描钉钉的组织结构
     *
     * @param companyId
     */
    public Map<String, Object> scanDingGroup(String companyId);

    /**
     * 获取钉钉的组织结构
     *
     * @param companyId
     * @return
     */
    public List<DingGroupDomain> getGroupFromDing(String companyId);


    /**
     * 分页获取钉钉组织结构的列表
     *
     * @param dataGridModel
     * @return
     */
    public Map<String, Object> getDingGroupList(DataGridModel dataGridModel, String companyId);

    /**
     * 将某个公司的钉钉组织架构从缓冲表同步到自己的组织架构
     *
     * @param companyId
     * @return
     */
    public Map<String, Object> sync(String companyId) throws Exception;


}
