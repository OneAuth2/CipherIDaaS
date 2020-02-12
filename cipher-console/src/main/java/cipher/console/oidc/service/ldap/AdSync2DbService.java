package cipher.console.oidc.service.ldap;

import cipher.console.oidc.domain.web.AdUserBufferDomain;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019-04-26 08:21
 */
public interface AdSync2DbService {

    /**
     * 将缓冲表的数据同步到用户表
     *
     * @param list
     * @return
     */
    public Map<String, Object> ayncBuffer2User(List<AdUserBufferDomain> list,String companyId);

}
