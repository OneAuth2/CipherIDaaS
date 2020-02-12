package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.CustomAttributeInfo;

import java.util.List;
import java.util.Map;

public interface CustomAttributeService {
    Map<String,Object> addUserAttribute(String companyId, String attributeName, String attributeDescription);

    Map<String,Object> editUserAttribute(String uuid,String companyId, String attributeName, String attributeDescription);

    void delUserAttribute(String uuid,String companyId) throws Exception;

    List<CustomAttributeInfo> getAttributeList(String companyId);

}
