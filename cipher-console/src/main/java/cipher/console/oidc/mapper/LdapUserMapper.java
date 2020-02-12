package cipher.console.oidc.mapper;

import cipher.console.oidc.entity.LdapUser;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/25 10:44
 */
public interface LdapUserMapper {

    /**
     * 批量插入备份数据
     *
     * @param ldapUserList
     * @throws Exception
     */
    public void insertLdapUserList(@Param("ldapUserList") List<LdapUser> ldapUserList) throws Exception;


    /**
     * 批量插入临时表数据
     *
     * @param ldapUserList
     * @throws Exception
     */
    public void insertLdapUserGuidList(@Param("ldapUserList") List<LdapUser> ldapUserList) throws Exception;

    /**
     * 清空临时表数据
     * */
    public void truncateLdapUserGuidList(@Param("companyId") String companyId) throws Exception;


    /**
     * 批量更新
     * @param ldapUserList
     */
    public void updateList(@Param("ldapUserList") List<LdapUser> ldapUserList);

    /**
     * 查询AD域最后一次修改的时间戳
     * @return
     */
    public String queryLatestChangeTime(@Param("source") String source);

    /**
     * 查询已经存在于备份表中的用户，以userPrincpleName为唯一标识
     * @param ldapUserList
     * @return
     */
    public List<LdapUser> queryAlredyExistsUser(@Param("ldapUserList") List<LdapUser> ldapUserList,@Param("companyId") String companyId);

    /**
     * 查询已经存在于备份表中的用户，以userPrincpleName为唯一标识
     * @return
     */
    public List<LdapUser> queryAlredyExistsUserExtend();

    /**
     * 查询最后的创建时间
     * @return
     */
    public String queryLatestCreatedTime();

}
