package cipher.console.oidc.entity;

import org.springframework.ldap.odm.annotations.Attribute;
import org.springframework.ldap.odm.annotations.Entry;
import org.springframework.ldap.odm.annotations.Id;

import javax.naming.Name;

/**
 * @Author: zt
 * @Date: 2018/7/5 9:06
 */
@Entry(objectClasses = {"organizationalUnit", "top"}, base = "")
public class LdapDept {

    @Id
    private Name dn;

    private String objectGuid;


    @Attribute(name = "ou")
    private String ou;

    @Attribute(name = "description")
    private String description;

    @Attribute(name = "distinguishedName")
    private String distinguishedName;

    @Attribute(name = "name")
    private String name;

    public Name getDn() {
        return dn;
    }

    public void setDn(Name dn) {
        this.dn = dn;
    }

    public String getObjectGuid() {
        return objectGuid;
    }

    public void setObjectGuid(String objectGuid) {
        this.objectGuid = objectGuid;
    }

    public String getOu() {
        return ou;
    }

    public void setOu(String ou) {
        this.ou = ou;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDistinguishedName() {
        return distinguishedName;
    }

    public void setDistinguishedName(String distinguishedName) {
        this.distinguishedName = distinguishedName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "LdapDept{" +
                "dn=" + dn +
                ", ou='" + ou + '\'' +
                ", description='" + description + '\'' +
                ", distinguishedName='" + distinguishedName + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
