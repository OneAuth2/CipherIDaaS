package cipher.console.oidc.service;

import cipher.console.oidc.entity.LdapUser;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/10/25 10:55
 */
public interface LdapUserService {

    /**
     * 批量插入备份数据
     *
     * @param ldapUserList
     * @throws Exception
     */
    public void insertLdapUserList(List<LdapUser> ldapUserList) throws Exception;

    /**
     * 批量插入临时表数据
     *
     * @param ldapUserList
     * @throws Exception
     */
    public void insertLdapUserGuidList(List<LdapUser> ldapUserList,String companyId);


    /**
     * 批量更新
     * @param ldapUserList
     */
    public void updateList(List<LdapUser> ldapUserList);

    /**
     * 查询AD域最后一次修改的时间戳
     * @return
     */
    public String queryLatestChangeTime(String source);

    /**
     * 查询已经存在于备份表中的用户，以userPrincpleName为唯一标识
     * @param ldapUserList
     * @return
     */
    public List<LdapUser> queryAlredyExistsUser(List<LdapUser> ldapUserList,String companyId);

    /**
     * 查询已经存在于备份表中的用户，以userPrincpleName为唯一标识
     * @return
     */
    public List<LdapUser> queryAlredyExistsUser();

    /**
     * 查询最后的创建时间
     * @return
     */
    public String queryLatestCreatedTime();


    /**
     * 全量处理AD域同步数据
     * */
    public Map<String, Integer> allSyncAdUser2Buffer(List<LdapUser> ldapUserList, List<AdMap2LocalConfigModel> adMap2LocalConfigModelList, Map<String, Integer> map,String companyId);

}
