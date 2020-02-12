package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.web.CustomAttributeInfo;
import cipher.console.oidc.domain.web.UserAttributeDomain;
import cipher.console.oidc.mapper.CustomAttributeMapper;
import cipher.console.oidc.service.CustomAttributeService;
import cipher.console.oidc.service.UUIDService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class CustomAttributeServiceImpl implements CustomAttributeService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomAttributeServiceImpl.class);

    @Autowired
    private UUIDService uuidService;

    @Autowired
    private CustomAttributeMapper customAttributeMapper;

    @Override
    public Map<String,Object> addUserAttribute(String companyId, String attributeName, String attributeDescription) {
        if(StringUtils.isNotEmpty(companyId)&&StringUtils.isNotEmpty(attributeName)){
            int total = customAttributeMapper.countAttributeByCompanyId(companyId);
            if(total==20){
                return NewReturnUtils.failureResponse(ReturnMsg.ADDATTRIBUTETOTAL);
            }
            UserAttributeDomain userAttributeDomain = new UserAttributeDomain();
            userAttributeDomain.setUuid(uuidService.getUUid());
            userAttributeDomain.setCompanyId(companyId);
            userAttributeDomain.setAttributeName(attributeName);
            String attributeValue = "";
            for(int i=1;i<=20;i++){
                String attrValue = "attribute"+i;
                int count = customAttributeMapper.countAttributeByName(companyId,attrValue);
                if(count<1){
                    attributeValue = attrValue;
                    break;
                }
            }
            userAttributeDomain.setAttributeValue(attributeValue);
            userAttributeDomain.setAttributeDescription(attributeDescription);
            try {
                customAttributeMapper.insertUserAttribute(userAttributeDomain);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("enter CustomAttributeServiceImpl.addUserAttribute() but insert failed,userAttributeDomain=[{}]..==" + userAttributeDomain.toString());
                LOGGER.error(e.getMessage(), e);
                return NewReturnUtils.failureResponse(ReturnMsg.ADDATTRIBUTEFAILED);
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.ADDATTRIBUTESUCCESS);
    }

    @Override
    public Map<String, Object> editUserAttribute(String uuid, String companyId, String attributeName, String attributeDescription) {
        if(StringUtils.isNotEmpty(uuid)&&StringUtils.isNotEmpty(companyId)&&StringUtils.isNotEmpty(attributeName)){
            UserAttributeDomain userAttributeDomain = new UserAttributeDomain();
            userAttributeDomain.setUuid(uuid);
            userAttributeDomain.setCompanyId(companyId);
            userAttributeDomain.setAttributeName(attributeName);
            userAttributeDomain.setAttributeDescription(attributeDescription);
            try {
                customAttributeMapper.updateUserAttribute(userAttributeDomain);
            } catch (Exception e) {
                e.printStackTrace();
                LOGGER.error("enter CustomAttributeServiceImpl.editUserAttribute() but update failed,userAttributeDomain=[{}]..==" + userAttributeDomain.toString());
                LOGGER.error(e.getMessage(), e);
                return NewReturnUtils.failureResponse(ReturnMsg.EDITATTRIBUTEFAILED);
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.EDITATTRIBUTESUCCESS);
    }

    @Override
    public void delUserAttribute(String uuid, String companyId) throws Exception {
        if(StringUtils.isNotEmpty(uuid)&&StringUtils.isNotEmpty(companyId)){
            String attributeValue = customAttributeMapper.selectUserAttribute(uuid, companyId);
            if(StringUtils.isNotEmpty(attributeValue)){
                customAttributeMapper.delUserAttribute(attributeValue,companyId);
                customAttributeMapper.delUserAttributeMap(uuid,companyId);
            }
        }
    }

    @Override
    public List<CustomAttributeInfo> getAttributeList(String companyId) {
        List<CustomAttributeInfo> attributeList = null;
        if(StringUtils.isNotEmpty(companyId)){
            return customAttributeMapper.selectAttributeList(companyId);
        }
        return attributeList;
    }
}
