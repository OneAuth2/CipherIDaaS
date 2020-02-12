package cipher.console.oidc.controller.web;


import cipher.console.oidc.admapper.DnMapper;
import cipher.console.oidc.admapper.LdapDeptAttributeMapper;
import cipher.console.oidc.admapper.LdapUserAttributeMapper;
import cipher.console.oidc.common.LdapConstans;
import cipher.console.oidc.domain.web.AdUserInfoDomain;
import cipher.console.oidc.entity.LdapDept;
import cipher.console.oidc.enums.SearchScopeEnum;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.support.LdapNameBuilder;
import org.springframework.ldap.support.LdapUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.Context;
import javax.naming.Name;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Controller
@RequestMapping(value = "/ldapTest")
@EnableAutoConfiguration
@Api("AD域管理")
public class TestController {

    @Autowired
    private LdapTemplate ldapTemplate;

    @RequestMapping(value = "test/{pwd}", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "验证用户名密码")
    @ApiImplicitParam(name = "pwd")
    public boolean test(@PathVariable("pwd") String pwd) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"));
//        return authenticate(getDnForUser("testdb@cipherchina.com"), pwd);
        return authenticate("cn=testdb,ou=dbgroup,ou=java,ou=IT,dc=cipherchina,dc=com", pwd);
    }

    @RequestMapping(value = "/queryall", method = RequestMethod.GET)
    @ResponseBody
    @ApiOperation(value = "查询AD域下所有用户")
    public List queryAllUser() {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"));
//        查询所有内部人员
        return ldapTemplate.search("ou=IT", filter.encode(), new LdapUserAttributeMapper());

    }


    @RequestMapping(value = "/bindUser")
    @ResponseBody
    public Map<String, String> bindUser() {
        ldapTemplate.bind("cn=testdb123,ou=dbgroup,ou=java,ou=IT,dc=cipherchina,dc=com", null, buildAttributes());
        return null;
    }


    public static void main(String[] args) {
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //AD域的账户记得添加@+域名
        env.put(Context.SECURITY_PRINCIPAL, "administrator@cipherchina.com");
        env.put(Context.SECURITY_CREDENTIALS, LdapConstans.PASS_WORD); //密码
        String ldapURL = LdapConstans.LDAP_URL;
        env.put(Context.PROVIDER_URL, ldapURL);
        try {
            DirContext dirContext = new InitialDirContext(env);
             /*     dirContext.bind(buildDn("dc=cipherchina,dc=com"),null,buildAttributes());
            dirContext.createSubcontext(buildDn("dc=cipherchina,dc=com"),buildAttributes());*/

            String root = "dc=cipherchina,dc=com"; // LDAP的根节点的DC
            BasicAttributes attrs = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectClass");
            objclassSet.add("top");
            objclassSet.add("person");
            objclassSet.add("organizationalPerson");
            objclassSet.add("user");
            attrs.put(objclassSet);
            attrs.put("ou", "IT");
            dirContext.createSubcontext("cn=testdb123,ou=dbgroup,ou=java,ou=IT" + "," + root, attrs);
            System.err.println("res添加成功");
        } catch (NamingException e) {
            e.printStackTrace();
            System.err.println("res添加失败");
        }
    }


    private static Name buildDn(String baseDn) {
        DistinguishedName dn = new DistinguishedName(baseDn);//初始化时可以把根写上，也可写放在下面。
        dn.add("ou", "IT");   //跟前面的路径（国家）
        dn.add("ou", "java");
        dn.add("ou", "dbgroup");
        dn.add("cn", "testdb123");
        return dn;
    }

    private static Attributes buildAttributes() {
        Attributes attrs = new BasicAttributes();
        BasicAttribute ocattr = new BasicAttribute("objectclass");
        ocattr.add("top");
        ocattr.add("person");
        ocattr.add("organizationalPerson");
        ocattr.add("user");
        attrs.put(ocattr);
        attrs.put("cn", "测试用户");
        attrs.put("sn", "测试用户");
//        return attrs;
        return attrs;
    }


    @RequestMapping(value = "/update/{pwd}", method = RequestMethod.GET)
    @ResponseBody
    @SuppressWarnings({"unchecked", "rawtypes"})
    public String updatePwd(@PathVariable("pwd") String pwd, HttpServletResponse response) {

        response.setContentType("UTF-8");
        System.err.println("新密码:" + pwd);
        // ladp的一些配置
        Properties env = new Properties();

//        String adminName = "administrator@cipherchina.com"; //管理员账号
//        String adminPassword = "cipher20183edc5TGB!";//管理员密码

    /*   String keystore = "D:\\java\\jdk\\lib\\security\\cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.keyStorePassword", "Zt123456");*/

        env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        //文件服务提供
//        env.setProperty(Context.INITIAL_CONTEXT_FACTORY,"com.sun.jndi.fscontext.RefFSContextFactory");
        env.setProperty(Context.PROVIDER_URL, LdapConstans.LDAPS_URL);
        env.put("java.naming.ldap.factory.socket", "cipher.console.oidc.config.adcert.LTSSSLSocketFactory");
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.setProperty(Context.URL_PKG_PREFIXES, "com.sun.jndi.url");
        env.setProperty(Context.REFERRAL, "ignore");
        env.setProperty(Context.SECURITY_AUTHENTICATION, "simple");
        env.setProperty(Context.SECURITY_PRINCIPAL, LdapConstans.LDAPS_USERENAME);
        env.setProperty(Context.SECURITY_CREDENTIALS, LdapConstans.LDAPS_PWD);
        try {
            // 初始化ldapcontext
            LdapContext ctx = new InitialLdapContext(env, null);
            ModificationItem[] mods = new ModificationItem[1];
            String newQuotedPassword = "\"" + pwd + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
            // 修改密码
            String userName = "CN=zhoutao,OU=赛赋科技,DC=test1,DC=cipherchina,DC=com"; //用户

            ctx.modifyAttributes(userName, mods);
            System.out.println("Reset Password for: " + userName);
            ctx.close();
        } catch (Exception e) {
            /*
             * 根据ldap返回码判断错误原因并返回
             */
            if (e.toString().indexOf("LDAP: error code") > 0) {
                // 得到ldap返回的错误吗
                String errorMsg = e.toString();
                int startNum = errorMsg.indexOf("LDAP: error code") + 17;
                errorMsg = errorMsg.substring(startNum, startNum + 19);
                int endNum = errorMsg.indexOf(" - ");
                errorMsg = errorMsg.substring(0, endNum);
                if ("49".equals(errorMsg)) {
                    errorMsg = "域控管理员账户／密码错误";
                } else if ("32".equals(errorMsg) || "34".equals(errorMsg)) {
                    errorMsg = "域内账户错误";
                } else if ("10".equals(errorMsg)) {
                    errorMsg = "dc错误";
                } else if ("53".equals(errorMsg)) {
                    errorMsg = "密码策略不正确";
                } else {
                    errorMsg = "修改失败,错误吗：" + errorMsg;
                    e.printStackTrace();
                }
                System.out.println("Problem resetting password(ldap):" + errorMsg);
                return errorMsg;
            } else {
                if (e.toString().indexOf(
                        "algorithm: Default, provider: SunJSSE, class: sun.security.ssl.SSLContextImpl$DefaultSSLContext") > 0) {
                    System.out.println("Problem resetting password(ldap):" + "证书无效／证书密码错误");
                    e.printStackTrace();
                    return "修改失败，证书无效／证书密码错误";
                }
                if (e.toString().indexOf(
                        "the trustAnchors parameter must be non-empty") > 0) {
                    System.out.println("Problem resetting password(ldap):" + "证书不存在");
                    return "修改失败，证书不存在";
                }
                e.printStackTrace();
                return "修改失败！";
            }
        }
        return "修改成功";
    }


    @RequestMapping(value = "/addOu")
    @ResponseBody
    @ApiOperation("添加部门")
    public String addOu() {
        Attributes ouAttributes = new BasicAttributes();
        BasicAttribute ouBasicAttribute = new BasicAttribute("objectclass");
        ouBasicAttribute.add("organizationalUnit");
        ouAttributes.put(ouBasicAttribute);
        Map<String, String> map = new HashMap<String, String>();
        map.put("ou", "ui");
        map.put("description", "ui设计师岗位");
        for (String str : map.keySet()) {
            ouAttributes.put(str, map.get(str));
        }
        DistinguishedName newContactDN = new DistinguishedName("OU=java,OU=IT");
        newContactDN.add("ou", map.get("ou"));
        try {
            ldapTemplate.bind(newContactDN, null, ouAttributes);
            return "添加部门成功";
        } catch (Exception e) {
            e.printStackTrace();
            return "添加部门失败";
        }
    }

    @RequestMapping(value = "/queryOu", method = RequestMethod.GET)
    @ApiOperation("递归查询某个部门下所有部门")
    @ResponseBody
    public Object queryAllOu() {


        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "organizationalUnit"));
        filter.and(new EqualsFilter("objectClass", "top"));
        //search是根据过滤条件进行查询，第一个参数是父节点的dn，可以为空，不为空时查询效率更高
        List depts = ldapTemplate.search("ou=java,ou=IT", filter.encode(), new LdapDeptAttributeMapper());

//        List depts = ldapTemplate.search("", filter.encode(), new LdapDeptAttributeMapper());
        System.out.println(depts.size());
        return depts;
    }


    @SuppressWarnings("unchecked")
    private boolean authenticate(String userCn, String pwd) {
        DirContext ctx = null;
        System.out.println(userCn + ":" + pwd);
        try {
            //调用ldap 的 authenticate方法检验
            return ldapTemplate.authenticate(userCn, "(objectclass=person)", pwd);
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            LdapUtils.closeContext(ctx);
        }

    }


    @SuppressWarnings({"unused", "unchecked"})
    private String getDnForUser(String cn) {
      /*  List<String> results = ldapTemplate.search("ou=java,ou=4#608", "(&(objectclass=person)(userPrincipalName=" + cn + "))", new DnMapper());
        if (results.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }
        System.out.println(results.get(0));
        return results.get(0);*/

        List<String> results = ldapTemplate.search("ou=java", "(&(objectclass=person)(userPrincipalName=" + cn + "))", new DnMapper());
        if (results.size() != 1) {
            throw new RuntimeException("User not found or not unique");
        }
        System.out.println(results.get(0));
        return results.get(0);
    }
}


