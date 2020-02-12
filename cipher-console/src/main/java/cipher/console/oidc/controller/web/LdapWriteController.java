package cipher.console.oidc.controller.web;

import cipher.console.oidc.admapper.LdapDeptAttributeMapper;
import cipher.console.oidc.admapper.LdapUserAttributeMapper;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.LdapConstans;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.entity.LdapDept;
import cipher.console.oidc.enums.AdAuthEnum;
import cipher.console.oidc.model.UserPwdModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.poifs.filesystem.DirectoryEntry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.ldap.core.DistinguishedName;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.naming.AuthenticationException;
import javax.naming.Context;
import javax.naming.NamingException;
import javax.naming.directory.*;
import javax.naming.ldap.InitialLdapContext;
import javax.naming.ldap.LdapContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/6/7 16:38
 */
@Controller
@RequestMapping(value = "/cipher/ldapWrite")
@EnableAutoConfiguration
public class LdapWriteController {


    @Autowired
    private LdapTemplate ldapTemplate;


    @Autowired
    private UserService userService;

    @Autowired
    private GroupService groupService;


    private static final Logger LOGGER = LoggerFactory.getLogger(LdapWriteController.class);

    @RequestMapping(value = "/list")
    public String page(HttpServletResponse response) {
        return "ldapWrite/ldap";
    }


    /**
     * 将某个组的用户写回到AD域
     */
    @RequestMapping(value = "/write", method = RequestMethod.POST)
    @ResponseBody
    public void write(@RequestParam(value = "groupId") Integer groupId, HttpServletResponse response, HttpServletRequest request) {
        System.err.println("groupId:" + groupId);
        List<AdUserInfoDomain> dbUserList = userService.queryUserByGrouId(groupId);
        if (CollectionUtils.isEmpty(dbUserList)) {
            ResponseUtils.customSuccessResponse(response, "该组没有任何用户");
            return;
        }
        List<String> groupNameList = new ArrayList<>();
        GroupInfoDomain groupInfoDomain = groupService.getGroupById(groupId);
        groupNameList.add(groupInfoDomain.getGroupName());
        //从db中递归查询该组及其上级组织
        while (groupInfoDomain.getParentGroupId() != null) {
            groupInfoDomain = groupService.getGroupById(groupInfoDomain.getParentGroupId());
            if (groupInfoDomain != null) {
                groupNameList.add(groupInfoDomain.getGroupName());
            }
        }

        if(groupNameList.size()<=2){
            ResponseUtils.customFailueResponse(response,"该组不符合AD域树结构");
            return;
        }
        //递归创建部门及其子部门
        buildAdGroup(groupNameList);
        //构建dn
        String dn = buildDn(groupNameList);
        List<AdUserInfoDomain> adUserList = null;
        if (!StringUtils.isEmpty(dn)) {
            adUserList = queryAdUserByGroup(dn);
        }
        //ad中该组及其子组下的用户
        List<AdUserInfoDomain> importList = getUniqueUserAdList(dbUserList, adUserList);

        List<String> accountNumberList = new ArrayList<>();
        Map<String, String> propMap = new HashMap<>();
        for (AdUserInfoDomain infoDomain : importList) {
            accountNumberList.add(infoDomain.getMail());
            propMap.put("userPrincipalName", infoDomain.getMail());
            createUser("cn=" + infoDomain.getUserName() + "," + dn, propMap);
            propMap.clear();
        }
        List<UserPwdModel> pwdList = null;
        if (!CollectionUtils.isEmpty(accountNumberList)) {
            pwdList = userService.queryPwdByAccount(accountNumberList);
        }

        if (!CollectionUtils.isEmpty(pwdList)) {
            for (UserPwdModel userPwdModel : pwdList) {
                String cn = "cn=" + userPwdModel.getUserName() + "," + dn + "," + LdapConstans.BASE_DC;
                LOGGER.info("LdapWriteController.write---->Reset password for:" + cn);
                updatePwd(userPwdModel.getPassword(), cn);
            }
        }
        ResponseUtils.customSuccessResponse(response, "同步成功");
    }


    /**
     * 修改密码
     * @param pwd 密码
     * @param userDn 用户节点,到根域名结束
     */
    private void updatePwd(String pwd, String userDn) {
        // ladp的一些配置
        Hashtable env = new Hashtable();
//        String adminName = "administrator@cipherchina.com"; //管理员账号
//        String adminPassword = "cipher20183edc5TGB!";//管理员密码
    /*   String keystore = "D:\\java\\jdk\\lib\\security\\cacerts";
        System.setProperty("javax.net.ssl.trustStore", keystore);
        System.setProperty("javax.net.ssl.keyStorePassword", "Zt123456");*/
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, LdapConstans.LDAPS_USERENAME);
        env.put(Context.SECURITY_CREDENTIALS, LdapConstans.LDAPS_PWD);
        env.put(Context.SECURITY_PROTOCOL, "ssl");
        env.put("java.naming.ldap.factory.socket", "cipher.console.oidc.config.adcert.DummySSLSocketFactory");
        env.put(Context.PROVIDER_URL, LdapConstans.LDAPS_URL);
        try {
            // 初始化ldapcontext
            LdapContext ctx = new InitialLdapContext(env, null);
            ModificationItem[] mods = new ModificationItem[1];
            String newQuotedPassword = "\"" + pwd + "\"";
            byte[] newUnicodePassword = newQuotedPassword.getBytes("UTF-16LE");
            mods[0] = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, new BasicAttribute("unicodePwd", newUnicodePassword));
            // 修改密码

//            String userName = "cn=test南柯,OU=IT,dc=cipherchina,dc=com"; //用户
//            String userName = "CN=testdb,OU=dbgroup,OU=java,OU=IT,DC=cipherchina,DC=com"; //用户
            ctx.modifyAttributes(userDn, mods);
            LOGGER.info("设置密码成功Reset Password for: " + userDn);
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
                LOGGER.error("LdapWriteController.write修改密码失败:----->" + errorMsg);
            } else {
                if (e.toString().indexOf(
                        "algorithm: Default, provider: SunJSSE, class: sun.security.ssl.SSLContextImpl$DefaultSSLContext") > 0) {
                    System.out.println("Problem resetting password(ldap):" + "证书无效／证书密码错误");
                    e.printStackTrace();
                    LOGGER.error("LdapWriteController.write修改密码失败:----->" + "修改失败，证书无效／证书密码错误");
                }
                if (e.toString().indexOf(
                        "the trustAnchors parameter must be non-empty") > 0) {
                    LOGGER.error("LdapWriteController.write修改密码失败:----->" + "修改失败，证书不存在");
                }
                e.printStackTrace();
                LOGGER.error("LdapWriteController.write修改密码失败:----->" + e.getCause());
            }
        }
    }

    /**
     * 创建用户
     *
     * @param userDn
     * @param propertity
     */
    private void createUser(String userDn, Map<String, String> propertity) {
        String root = LdapConstans.BASE_DC;
        Properties env = new Properties();
        env.put(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        //AD域的账户记得添加@+域名
        env.put(Context.SECURITY_PRINCIPAL, "administrator@cipherchina.com");
        env.put(Context.SECURITY_CREDENTIALS, LdapConstans.PASS_WORD); //密码
        String ldapURL = LdapConstans.LDAP_URL;
        env.put(Context.PROVIDER_URL, ldapURL);
        DirContext dirContext = null;
        try {
            dirContext = new InitialDirContext(env);

            BasicAttributes attrs = new BasicAttributes();
            BasicAttribute objclassSet = new BasicAttribute("objectClass");
            objclassSet.add("top");
            objclassSet.add("person");
            objclassSet.add("organizationalPerson");
            objclassSet.add("user");
            attrs.put(objclassSet);

            if (!CollectionUtils.isEmpty(propertity)) {
                for (Map.Entry entry : propertity.entrySet()) {
                    attrs.put(entry.getKey().toString(), entry.getValue());
                }
            }
            attrs.put("ou", "IT");
//            dirContext.createSubcontext("cn=testdb123,ou=dbgroup,ou=java,ou=IT" + "," + root, attrs);
            dirContext.createSubcontext(userDn + "," + root, attrs);
            LOGGER.info("LdapWriteController.write--->添加用户成功");
        } catch (NamingException e) {
            LOGGER.error("LdapWriteController.write--->添加用户失败:" + e.getCause());
        } finally {
            if (dirContext != null) {
                try {
                    dirContext.close();
                } catch (NamingException e) {
                    LOGGER.error("LdapWriteController.write--->关闭dirContext出错！");
                }
            }
        }

    }

    /**
     * 构建dn信息 ，如: ou=dbgroup,ou=java,ou=IT
     *
     * @param groupNameList 部门名称列表
     * @return dn
     */
    private String buildDn(List<String> groupNameList) {
        String dn = "";
        Boolean flag = true;
        for (int i = groupNameList.size() - 3; i >= 0; i--) {
            if (flag) {
                dn = "ou=" + groupNameList.get(i) + dn;
            } else {
                dn = "ou=" + groupNameList.get(i) + "," + dn;
            }
            flag = false;
        }
        return dn;
    }

    /**
     * 查询某个部门下的所有用户
     *
     * @param dn 部门节点
     * @return
     */
    @SuppressWarnings("unchecked")
    private List<AdUserInfoDomain> queryAdUserByGroup(String dn) {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"));
        return ldapTemplate.search(dn, filter.encode(), new LdapUserAttributeMapper());
    }

    /**
     * 递归创建部门及其子部门
     * 若部门已经存在则跳过，若不存在则创建
     *
     * @param groupNameList 部门名称列表
     */
    private void buildAdGroup(List<String> groupNameList) {

        String ou = "";
        String supherOu = "";
        Boolean flag = true;
        //递归创建部门及其子部门,默认根域名为两级
        for (int i = groupNameList.size() - 3; i >= 0; i--) {
            String deptName = groupNameList.get(i);
            ou = "ou=" + deptName + ou;
            if (flag) {
                if (!deptIsExist(ou)) {
                    addOu(deptName, "");
                }
            } else {
                supherOu = "ou=" + groupNameList.get(i + 1) + supherOu;
                if (!deptIsExist(ou)) {
                    addOu(deptName, supherOu);
                }
                supherOu = "," + supherOu;
            }
            LOGGER.info("本部门:" + deptName + "上级部门:" + supherOu);
            ou = "," + ou;
            flag = false;
        }
    }

    /**
     * 创建部门
     *
     * @param deptName 部门名称如:ou=测试部门
     * @param superOu  上级部门，多级，但不算根域名，若没有则为空
     * @return 返回创建是否成功
     */
    private Boolean addOu(String deptName, String superOu) {
        Attributes ouAttributes = new BasicAttributes();
        BasicAttribute ouBasicAttribute = new BasicAttribute("objectclass");
        ouBasicAttribute.add("organizationalUnit");
        ouAttributes.put(ouBasicAttribute);
        Map<String, String> map = new HashMap<String, String>();
        map.put("ou", deptName);
//        map.put("description", "ui设计师岗位");
        for (String str : map.keySet()) {
            ouAttributes.put(str, map.get(str));
        }
        DistinguishedName newContactDN = new DistinguishedName(superOu);
        newContactDN.add("ou", map.get("ou"));
        try {
            ldapTemplate.bind(newContactDN, null, ouAttributes);
            return true;
        } catch (Exception e) {
            LOGGER.error("LdapWriteController.write--->添加部门失败:" + e.getCause());
            return false;
        }
    }

    /**
     * 判断某个部门是否已经存在，
     *
     * @param ou
     * @return
     */
    private Boolean deptIsExist(String ou) {
        try {
            AndFilter filter = new AndFilter();
            filter.and(new EqualsFilter("objectClass", "organizationalUnit"));
            filter.and(new EqualsFilter("objectClass", "top"));
            ldapTemplate.search(ou, filter.encode(), new LdapDeptAttributeMapper());
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    /**
     * 从导入的用户列表中取出已经重复的用户
     *
     * @param importList
     * @param repeatList
     * @return
     */
    private List<AdUserInfoDomain> getUniqueUserAdList(List<AdUserInfoDomain> importList, List<AdUserInfoDomain> repeatList) {
        Iterator<AdUserInfoDomain> iterator = importList.iterator();
        while (iterator.hasNext()) {
            AdUserInfoDomain adUserInfoDomain = iterator.next();
            for (AdUserInfoDomain infoDomain : repeatList) {
                if (adUserInfoDomain.getMail().equals(infoDomain.getMail())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return importList;
    }


    /**
     * 去掉从Ad域中导入的用户列表中与已有用户列表中重复的数据
     *
     * @param importList
     * @param repeatList
     * @return
     */
    private List<AdUserInfoDomain> getUniqueUserAccountList(List<AdUserInfoDomain> importList, List<UserInfoDomain> repeatList) {
        Iterator<AdUserInfoDomain> iterator = importList.iterator();
        while (iterator.hasNext()) {
            AdUserInfoDomain adUserInfoDomain = iterator.next();
            for (UserInfoDomain infoDomain : repeatList) {
                if (adUserInfoDomain.getMail().equals(infoDomain.getMail())) {
                    iterator.remove();
                    break;
                }
            }
        }
        return importList;
    }


    /**
     * 导入用户
     *
     * @param form
     * @return
     * @throws NamingException
     */
    @SuppressWarnings("unchecked")
    private List<AdUserInfoDomain> importUser(AdInfoDomain form) throws NamingException {
        AndFilter filter = new AndFilter();
        filter.and(new EqualsFilter("objectClass", "person"));
        //  查询所有内部人员
        return ldapTemplate.search("ou=IT", filter.encode(), new LdapUserAttributeMapper());
    }


}
