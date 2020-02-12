package cipher.console.oidc.util;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author zt 获取AD域用户相关信息
 */
public class GetADUserInfo {

    private Properties env = new Properties();

    private String ldapURL = "ldap://" + "192.168.1.169" + ":" + 389;

    private void getInfo() throws NamingException {

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
//        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //AD域的账户记得添加@+域名
        env.put(Context.SECURITY_PRINCIPAL, "administrator@cipherchina.com");
        env.put(Context.SECURITY_CREDENTIALS, "ns25000@360"); //密码
        env.put(Context.PROVIDER_URL, ldapURL);
        LdapContext ctx = new InitialLdapContext(env, null);


        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);

        // 注意OU和DC的先后顺序
        NamingEnumeration results = ctx.search("OU=4#608,DC=cipherchina,DC=com", "objectClass=User", searchCtls);

//        NamingEnumeration results = ctx.search("CN=System,DC=cipherchina,DC=com", "objectClass=secret", searchCtls);


        while (results.hasMoreElements()) {

            SearchResult sr = (SearchResult) results.next();
            Attributes attributes = sr.getAttributes();
            NamingEnumeration values = attributes.getAll();

            while (values.hasMore()) {

                Attribute attr = (Attribute) values.next();
                Enumeration vals = attr.getAll();

                while (vals.hasMoreElements()) {
                    Object o = vals.nextElement();
                    System.out.println(attr.getID() + "--------------" + o.toString());
                }

//                System.err.println();
//                System.err.println();
//                System.err.println();
//                System.err.println("------------------------------------------------------------------------------------");

            }



        }
    }

    public static void main(String[] args) throws NamingException {
        GetADUserInfo ldap = new GetADUserInfo();
        ldap.getInfo();
    }

}