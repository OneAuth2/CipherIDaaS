package cipher.console.oidc.admapper;

/**
 * @Author: zt
 * @Date: 2018/7/4 16:48
 */

import org.springframework.ldap.core.ContextMapper;
import org.springframework.ldap.core.DirContextAdapter;

import javax.naming.Name;

/**
 * 节点的 Dn映射
 */
public class DnMapper implements ContextMapper {
    @Override
    public String mapFromContext(Object ctx) {
        DirContextAdapter context = (DirContextAdapter) ctx;
        Name name = context.getDn();
        String dn = name.toString();
        return dn;
    }
}


