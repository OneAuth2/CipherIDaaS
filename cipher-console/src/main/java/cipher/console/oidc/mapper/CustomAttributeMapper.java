package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.CustomAttributeInfo;
import cipher.console.oidc.domain.web.UserAttributeDomain;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface CustomAttributeMapper {
    int countAttributeByCompanyId(@Param(value = "companyId") String companyId);

    int countAttributeByName(@Param(value = "companyId") String companyId,@Param(value = "attributeValue") String attributeValue);

    void insertUserAttribute(UserAttributeDomain userAttributeDomain) throws Exception;

    void updateUserAttribute(UserAttributeDomain userAttributeDomain) throws Exception;

    String selectUserAttribute(@Param(value = "uuid") String uuid,@Param(value = "companyId") String companyId);

    void delUserAttribute(@Param(value = "attribute") String attribute,@Param(value = "companyId") String companyId) throws Exception;

    void delUserAttributeMap(@Param(value = "uuid") String uuid,@Param(value = "companyId") String companyId) throws Exception;

    List<CustomAttributeInfo> selectAttributeList(@Param(value = "companyId") String companyId);
}
