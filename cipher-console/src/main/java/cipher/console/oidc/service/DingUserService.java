package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingSimpleGroup;
import cipher.console.oidc.domain.web.DingUserDomain;

import java.util.List;
import java.util.Map;

public interface DingUserService {

    /**
     * add by ly
     *
     * @param accountNumber
     */
    public void deleteUserMap(String accountNumber);

    /**
     * 获取钉钉某个部门下所有的用户
     *
     * @param companyId
     * @param departMentId 部门Id
     * @return
     */
    public List<DingUserDomain> getUserFromDing(String companyId, Long departMentId);


    /**
     * 获取钉钉所有的部门
     *
     * @param companyId 公司id
     * @return
     */
    public List<DingSimpleGroup> getAllDingGroup(String companyId);

    /**
     * 获取所有的钉钉用户
     *
     * @param companyId 公司id
     * @return
     */
    public List<DingUserDomain> getAllDingUser(String companyId);

    /**
     * 同步钉钉用户
     *
     * @param companyId
     * @return
     */
    public Map<String, Object> syncDingUser(String companyId);

    /**
     * @param dataGridModel 分页参数
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param queryStr      根据姓名参数
     * @return
     */
    public Map<String, Object> queryBufferDingList(DataGridModel dataGridModel,
                                                   String companyId,
                                                   Integer status,
                                                   String queryStr);

    /**
     * 同步部分钉钉用户
     *
     * @param companyId  公司id
     * @param userIdList 待同步的钉钉用户id列表
     * @return
     */
    public Map<String, Object> syncDepartDingUser(String companyId, List<String> userIdList) throws Exception;

    public Map<String,Object> syncAllDingUser(String companyId) throws Exception;
}
