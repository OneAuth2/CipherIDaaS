package cipher.console.oidc.service;

import java.util.Map;

public interface IconSettingsService {
    Map<String,Object> compileDoorPage(String companyUuid,String title,String leftTitle,String rightTitle,String iconfont,String logo);
    Map<String,Object> echoDoorPage(String companyUuid);

    Map<String,Object> compileApplicationPage(String companyUuid,String title,String iconfont);
    Map<String,Object> echoApplicationPage(String companyUuid);

    Map<String,Object> compileManagePage(String companyUuid,String title,String leftTitle,String rightTitle,String iconfont);
    Map<String,Object> echoManagePage(String companyUuid);

    Map<String,Object> compileTitleTag(String companyId,String title, String iconfont);

    Map<String,Object> echoTitleTag(String companyUuid);
}
