//package cipher.console.oidc.config;
//
//import cipher.console.oidc.common.LdapConstans;
//import cipher.console.oidc.domain.web.AdInfoDomain;
//import cipher.console.oidc.service.AdInfoService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.ldap.core.LdapTemplate;
//import org.springframework.ldap.core.support.LdapContextSource;
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
///**
// * LDAP 的自动配置类
// * <p>
// * 完成连接 及LdapTemplate生成
// */
//@Configuration
//public class LdapConfiguration {
//
//
//    private LdapTemplate ldapTemplate;
//
//
//    @Autowired
//    private AdInfoService adInfoService;
//
//
//    @Bean
//    public LdapContextSource getContextSource() {
//
//        AdInfoDomain adInfoDomain = adInfoService.queryAdInfo(new AdInfoDomain());
//
//        int port = adInfoDomain.getPort();
//
//        if (port == 389) {
//            port = 3268;
//        }
//
//        if (port == 636) {
//            port = 3269;
//        }
//
//        String ldap_url = "ldap://" + adInfoDomain.getIp() + ":" + port;
//        String userName = adInfoDomain.getUserName();
//        String pwd = adInfoDomain.getPassword();
//
//        //从用户名中切分出来，dc=cipherchina,dc=com.dc=cn
//        String s = userName.substring(userName.lastIndexOf("@") + 1, userName.length());
//        String[] dcs = s.split("\\.");
//        StringBuilder baseDc = new StringBuilder();
//        for (int i = 0; i < dcs.length; i++) {
//            if (i == dcs.length - 1) {
//                baseDc.append("dc=").append(dcs[i]);
//            } else {
//                baseDc.append("dc=").append(dcs[i]).append(",");
//            }
//        }
//
//        System.err.println("ldapUrl:" + ldap_url);
//        System.err.println("userName:" + userName);
//        System.err.println("pwd:" + pwd);
//        System.err.println("baseDc:" + baseDc);
//
//        LdapContextSource contextSource = new LdapContextSource();
//        Map<String, Object> config = new HashMap<>();
//
//        contextSource.setUrl(ldap_url);
//        contextSource.setBase(baseDc.toString());
//        contextSource.setUserDn(userName);
//        contextSource.setPassword(pwd);
//        contextSource.setReferral(LdapConstans.REFERRAL);
////        contextSource.setReferral("ignore");
//        contextSource.setPooled(true);
//
//        //  解决乱码的关键一句
//        config.put("java.naming.ldap.attributes.binary", "objectGUID");
//        //objectSid
////        contextSource.afterPropertiesSet();
//        contextSource.setBaseEnvironmentProperties(config);
//        return contextSource;
//    }
//
//    @Bean
//    public LdapTemplate ldapTemplate() {
//        if (null == ldapTemplate)
//            ldapTemplate = new LdapTemplate(getContextSource());
//        return ldapTemplate;
//    }
//
//}