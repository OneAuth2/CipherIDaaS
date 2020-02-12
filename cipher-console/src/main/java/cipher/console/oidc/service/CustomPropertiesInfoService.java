package cipher.console.oidc.service;


import cipher.console.oidc.domain.web.CustomPropertiesInfo;

import java.util.List;

public interface CustomPropertiesInfoService {


    void saveProperties(CustomPropertiesInfo record);

    List<CustomPropertiesInfo> getInitData(CustomPropertiesInfo record);
    void closeCustomProperties(CustomPropertiesInfo record);






























}
