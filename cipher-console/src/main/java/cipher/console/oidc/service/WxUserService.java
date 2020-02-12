package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.WxSimpleGroup;
import cipher.console.oidc.domain.web.WxUserDomain;

import java.util.List;
import java.util.Map;

/**
 * @author lqgzj
 * @date 2019-10-12
 */
public interface WxUserService {
    /**
     * @param dataGridModel 分页参数
     * @param status        筛选条件:0-全部，1-新增，2-待绑定，默认全部
     * @param queryStr      根据姓名参数
     * @return
     */
    Map<String, Object> queryBufferWxList(DataGridModel dataGridModel, String companyUUid, Integer status, String queryStr);

    Map<String, Object> scanWxUser(String companyUUid);

    /**
     * 获取所有的企业微信用户
     *
     * @param companyId 公司id
     * @return
     */
    List<WxUserDomain> getAllWxUser(String companyId);


    /**
     * 获取钉钉所有的部门
     *
     * @param companyId 公司id
     * @return
     */
    List<WxSimpleGroup> getAllWxGroup(String companyId);

    /**
     * 获取企业微信某个部门下所有的用户
     *
     * @param companyId
     * @param departMentId 部门Id
     * @return
     */
    List<WxUserDomain> getUserFromWx(String companyId, Long departMentId);

    /*
    同步部分企业微信用户
     */
    Map<String, Object> syncDepartWxUser(String companyId, List<String> useridList) throws Exception;

    /*
    同步全部企业用户
     */
    Map<String, Object> syncAllWxUser(String companyId);
}
