package cipher.console.oidc.entity;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;
import org.springframework.ldap.odm.annotations.Transient;

import javax.naming.Name;


//@Entry(objectClasses = {"person", "top","user"}, base = "dc=test2,dc=cipherchina,dc=com")
@Entry(objectClasses = {"person", "top"})
public final class LdapUser {

    @Id
    private Name dn;

    /**
     * 姓名
     */
    @Attribute(name = "cn")
    private String cn;

    /**
     * 姓
     */
    @Attribute(name = "sn")
    private String sn;

    /**
     * 名
     */
    @Attribute(name = "name")
    private String name;


    /**
     * 节点信息，
     * 例如:CN=xx,OU=saifu,DC=cipherchina,DC=com,DC=cn
     */
    @Attribute(name = "distinguishedName")
    private String distinguishedName;


    @Attribute(name = "instanceType")
    private Integer instanceType;

    /**
     * 创建时间
     * 如：20181009114835.0Z
     */
    @Attribute(name = "whenCreated")
    private String whenCreated;

    /**
     * 更新时间
     */
    @Attribute(name = "whenChanged")
    private String whenChanged;

    /**
     * 用户名
     */
    @Attribute(name = "displayName")
    private String displayName;

    /**
     * 姓名
     */
    @Attribute(name = "sAMAccountName")
    private String sAMAccountName;

    /**
     * 邮箱（upn）
     */
    @Attribute(name = "userPrincipalName")
    private String userPrincipalName;

    /**
     * 节点信息
     * 如：CN=Person,CN=Schema,CN=Configuration,DC=cipherchina,DC=com,DC=cn
     */
    @Attribute(name = "objectCategory")
    private String objectCategory;

    /**
     * 邮箱
     */
    @Attribute(name = "mail")
    private String mail;

    /**
     * 账号状态
     */
    @Attribute(name = "userAccountControl")
    private Integer userAccountControl;

    /**
     * 经理,CN=y y,OU=saifu,DC=cipherchina,DC=com,DC=cn
     */
    @Attribute(name = "manager")
    private String manager;

    /**
     * 电话
     */
    @Attribute(name = "telephoneNumber")
    private String mobile;


    @Attribute(name = "objectGUID")
    private String objectGUID;

    @Attribute(name = "objectSid")
    private String objectSid;

    /**
     * 职位
     */
    @Attribute(name = "title")
    private String title;

    /**
     * 来源，ip或者域名
     */
    @Transient
    private String source;

    /**
     * 公司ID
     */
    @Transient
    private String companyId;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getDn() {
        return dn.toString();
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }


    public String getCn() {
        return cn;
    }

    public void setCn(String cn) {
        this.cn = cn;
    }

    public String getSn() {
        return sn;
    }

    public void setSn(String sn) {
        this.sn = sn;
    }



    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public Integer getInstanceType() {
        return instanceType;
    }

    public void setInstanceType(Integer instanceType) {
        this.instanceType = instanceType;
    }

    public String getWhenCreated() {
        return whenCreated;
    }

    public void setWhenCreated(String whenCreated) {
        this.whenCreated = whenCreated;
    }

    public String getWhenChanged() {
        return whenChanged;
    }

    public void setWhenChanged(String whenChanged) {
        this.whenChanged = whenChanged;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getsAMAccountName() {
        return sAMAccountName;
    }

    public void setsAMAccountName(String sAMAccountName) {
        this.sAMAccountName = sAMAccountName;
    }

    public String getUserPrincipalName() {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName) {
        this.userPrincipalName = userPrincipalName;
    }

    public String getObjectCategory() {
        return objectCategory;
    }

    public void setObjectCategory(String objectCategory) {
        this.objectCategory = objectCategory;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Integer getUserAccountControl() {
        return userAccountControl;
    }

    public void setUserAccountControl(Integer userAccountControl) {
        this.userAccountControl = userAccountControl;
    }

    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getObjectGUID() {
        return objectGUID;
    }

    public void setObjectGUID(String objectGUID) {
//        if (!StringUtils.isEmpty(objectGUID)) {
//            BASE64Encoder base64Encoder = new BASE64Encoder();
//            objectGUID = base64Encoder.encode(objectGUID.getBytes());
//        }
        this.objectGUID = objectGUID;
    }

    public String getObjectSid() {
        return objectSid;
    }

    public void setObjectSid(String objectSid) {
        this.objectSid = objectSid;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LdapUser{" +
                "dn=" + dn +
                ", cn='" + cn + '\'' +
                ", sn='" + sn + '\'' +
                ", name='" + name + '\'' +
                ", distinguishedName='" + distinguishedName + '\'' +
                ", instanceType=" + instanceType +
                ", whenCreated='" + whenCreated + '\'' +
                ", whenChanged='" + whenChanged + '\'' +
                ", displayName='" + displayName + '\'' +
                ", sAMAccountName='" + sAMAccountName + '\'' +
                ", userPrincipalName='" + userPrincipalName + '\'' +
                ", objectCategory='" + objectCategory + '\'' +
                ", mail='" + mail + '\'' +
                ", userAccountControl=" + userAccountControl +
                ", manager='" + manager + '\'' +
                ", mobile='" + mobile + '\'' +
                ", objectGUID='" + objectGUID + '\'' +
                ", objectSid='" + objectSid + '\'' +
                ", title='" + title + '\'' +
                ", source='" + source + '\'' +
                '}';
    }
}
