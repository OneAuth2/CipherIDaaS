package cipher.console.oidc.mapper;/**
 * @author lqgzj
 * @date 2019-10-18
 */

import cipher.console.oidc.domain.web.WxScanInfoDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author qiaoxi
 * @Date 2019-10-1811:35
 **/
public interface WxScanInfoMapper {
    void insertList(@Param("list")List<WxScanInfoDomain> wxScanInfoDomains);

    List<WxScanInfoDomain> getScanInfoListByConfigId(String configId);

    void updateScanAppInfo(WxScanInfoDomain wxScanInfoDomain);
}
