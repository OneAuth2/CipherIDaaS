package cipher.console.oidc.service;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.DingGroupDomain;
import cipher.console.oidc.domain.web.WxGroupDomain;

import java.util.List;
import java.util.Map;

/**
 * @Author qiaoxi
 * @Date 2019-10-1115:02
 **/

public interface WxGroupService {
    /*
    获取企业微信group列表
     */
    Map<String, Object> getWxGroupList(DataGridModel dataGridModel, String companyId);

    /*
    扫描企业微信组织机构
     */
    Map<String, Object> scanWxGroup(String companyUUid);

    List<WxGroupDomain> getGroupFromWx(String companyUUid);

    /**
     * 将企业组织架构从缓冲表同步到自己的组织架构
     *
     * @param companyId
     * @return
     */
    Map<String, Object> sync(String companyId) throws Exception;
}
