package cipher.console.oidc.admapper;

import cipher.console.oidc.entity.LdapDept;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * 将ldap返回的结果，转成指定对象
 */
public class LdapDeptAttributeMapper implements AttributesMapper {


    /**
     * 将单个Attributes转成单个对象
     *
     * @param attrs
     * @return
     * @throws NamingException
     */
    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        LdapDept dept = new LdapDept();
        if (attrs.get("ou") != null) {
         dept.setOu(attrs.get("ou").get().toString());
        }

        if(attrs.get("objectGUID")!=null){
            byte[] inArr= (byte[]) attrs.get("objectGUID").get();
            StringBuilder guid = new StringBuilder();
            for (byte anInArr : inArr) {
                StringBuilder dblByte = new StringBuilder(Integer.toHexString(anInArr & 0xff));
                if (dblByte.length() == 1) {
                    guid.append("0");
                }
                guid.append(dblByte);
            }
            dept.setObjectGuid(guid.toString());
        }


        if(attrs.get("description")!=null){
            dept.setDescription(attrs.get("description").get().toString());
        }

        if (attrs.get("distinguishedName") != null) {
            dept.setDistinguishedName(attrs.get("distinguishedName").get().toString());
        }

        if(attrs.get("name")!=null){
            dept.setName(attrs.get("name").get().toString());
        }
        return dept;
    }
}