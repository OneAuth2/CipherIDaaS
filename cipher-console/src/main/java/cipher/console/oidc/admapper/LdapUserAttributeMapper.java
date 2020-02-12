package cipher.console.oidc.admapper;

import cipher.console.oidc.domain.web.AdUserInfoDomain;
import org.springframework.ldap.core.AttributesMapper;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;

/**
 * 将ldap返回的结果，转成指定对象
 */
public class LdapUserAttributeMapper implements AttributesMapper {


    /**
     * 将单个Attributes转成单个对象
     * @param attrs
     * @return
     * @throws NamingException
     */
    @Override
    public Object mapFromAttributes(Attributes attrs) throws NamingException {
        AdUserInfoDomain user  = new AdUserInfoDomain();
        if(attrs.get("displayName") != null){
            user.setUserName( attrs.get("displayName").get().toString());
        }
       /* if(attrs.get("givenName") != null){
            user.setFirstName( attrs.get("givenName").get().toString());
        }*/
        if(attrs.get("initials") != null){
            user.setNickName( attrs.get("initials").get().toString());
        }
        if(attrs.get("userPrincipalName") != null){
            user.setMail( attrs.get("userPrincipalName").get().toString());
        }
        if(attrs.get("distinguishedName") != null){
            user.setUniqueName( attrs.get("distinguishedName").get().toString());
        }

       /* if(attrs.get("sn") != null){
            user.setLastName( attrs.get("sn").get().toString());
        }*/
        if(attrs.get("sn") != null){
            user.setUserName( attrs.get("sn").get().toString());
        }
        if(attrs.get("objectCategory") != null){
            user.setNodeInfo(attrs.get("objectCategory").get().toString());
        }

        if(attrs.get("userAccountControl") != null){
            user.setUserAccountControl(Integer.valueOf(attrs.get("userAccountControl").get().toString()));
        }
//        if(attrs.get("userPrincipalName") != null){
//            user.setUserPrincipalName(attrs.get("userPrincipalName").get().toString());
//        }
        return user;
    }
}