package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.CustomPropertiesInfo;

import java.util.List;

public interface CustomPropertiesInfoMapper {

    List<CustomPropertiesInfo> getInitData(CustomPropertiesInfo record);

    void closeCustomProperties(CustomPropertiesInfo record);

    int deleteByPrimaryKey(Integer id);

    int insert(CustomPropertiesInfo record);

    int insertSelective(CustomPropertiesInfo record);

    CustomPropertiesInfo selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(CustomPropertiesInfo record);

    int updateByPrimaryKey(CustomPropertiesInfo record);
}