package cipher.console.oidc.controller;


import cipher.console.oidc.util.AesUtil;
import cipher.console.oidc.util.aes.AES;
import cipher.console.oidc.util.aes.Base64;
import sun.misc.BASE64Decoder;

import javax.naming.Context;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author zt 获取AD域用户相关信息
 */
public class TestLdap {

    private Properties env = new Properties();

    private void getInfo() throws NamingException {

        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //AD域的账户记得添加@+域名
        env.put(Context.SECURITY_PRINCIPAL, "administrator@cipherchina.com");
        env.put(Context.SECURITY_CREDENTIALS, "106.15.204.31"); //密码
        String ldapURL = "ldap://" + "192.168.1.169" + ":" + 389;
        env.put(Context.PROVIDER_URL, ldapURL);
        LdapContext ctx = new InitialLdapContext(env, null);
        SearchControls searchCtls = new SearchControls();
        searchCtls.setSearchScope(SearchControls.SUBTREE_SCOPE);
        // 注意OU和DC的先后顺序
        NamingEnumeration results = ctx.search("OU=4#608,DC=cipherchina,DC=com", "objectClass=User", searchCtls);

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
                System.err.println("------------------------------------------------------------------------------------");
            }
        }
    }

    public static void main(String[] args) throws NamingException, IOException {
//        TestLdap ldap = new TestLdap();
//        ldap.getInfo();
//
//        String userUniqueName = "CN=test南柯,OU=IT,DC=cipherchina,DC=com";
//        String[] depts = userUniqueName.split(",");
//        //遍历组信息
//        for (int i = 1; i < depts.length; i++) {
//            String[] deptName = depts[i].split("=");
//            //组名
//            String deptNameValue = deptName[1];
//            System.err.
//
//        }
//


        String aesKey = "KX0A3phTHaromCwOQm1zug==";
        String content = "hwZsMh+Bk+aijUBgVx9g1aYv2LkJbiKOvkXlDJWa6MeV6FLH7/vFC9JUuW6aEYwYeFbeE+/eLCjmE163zJtx8NmTcz4uRRMmCmfmE0lFAbzpTTkimLefONYGtcsiwcIkMs/aPQLV/ceD81pOPeIjCiwhYDr2OjTT6CMZQDNJaJ9NOF2CusBBnxV5Rq+fWAYLHq4DPmra3hWKNcTqIHXzDTXezOIJm0ndBQQmFTouFvds15SoBWmOtWHivvv5Ag1J3shqkc4V8NlKgIgCv1Edk7XWhvgXGjf1eOoV+jBt+NmqfYzYIAgeoyvJrx0pRVOmh2qLTtuw2ysOJpk20OSc23gwae7p02U8dr46xRuS0zr/XXA35bbbZqBJSFlk0H9EMZQS650GYXp488ClKqPE2JsbMlkpQpevrNdLnpLMLs0eZYxh3KE/dEgPn7WCzAh/pOYeQiGrzFcFJWDoNLsDCCrEYrWBbn6KWuxr19ghSi6dsIH6Olz8Iwo4uBQti1225quXs1jjjsFaJq/UfxFIAsGltCxckgWM6tOHtBDoVQiECfhzaXwpSo4PTLzB7RVc";
        String s = AES.decryptWithKeyBase64(content, aesKey);
//
        System.err.println(s);


        String userName = "{\"username\":\"test@hengyi.com\",\"password\":\"HY@123456\"}";
        String s1 = AES.encryptWithKeyBase64(userName, aesKey);
        System.err.println(s1);
    }


}
