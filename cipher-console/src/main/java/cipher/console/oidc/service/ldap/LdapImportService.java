package cipher.console.oidc.service.ldap;

import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.domain.web.AdUserBufferDomain;
import com.unboundid.ldap.sdk.SearchResultEntry;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019-04-13 16:28
 */
public interface LdapImportService {


    /**
     * 从AD域导入数据
     * 使用LdapConnection
     *
     * @param adInfoDomain 用于获取AD域连接
     * @param timeStamp    时间戳
     * @return 导入的数据
     */
    public List<SearchResultEntry> importUserFromLdap(AdInfoDomain adInfoDomain, String timeStamp) throws Exception;


    /**
     * 从AD域导入数据
     * 使用ldaptemplate
     * @param adInfoDomain 用于获取连接
     * @param timeStamp 时间戳
     * @return 缓冲表中格式的数据
     * @throws Exception
     */
    public List<AdUserBufferDomain> importLdapUserWithTemplate(AdInfoDomain adInfoDomain, String timeStamp) throws Exception;


    /**
     * 将导入的数据，转换为缓冲表中的数据格式
     *
     * @param ldapUserList AD中的数据结构
     * @return 缓冲表数据结构
     */
    public List<AdUserBufferDomain> convertToAdUser(List<SearchResultEntry> ldapUserList, AdInfoDomain adInfoDomain) throws Exception;


    /**
     * 处理业务逻辑
     *
     * @param bufferDomainList
     * @return
     */
    public Map<String, Integer> handleLogic(List<AdUserBufferDomain> bufferDomainList,String companyId) throws Exception;


}
