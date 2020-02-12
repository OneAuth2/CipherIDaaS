package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.web.AutoSyncAdDomain;
import cipher.console.oidc.domain.web.AutoSyncAppDomain;
import cipher.console.oidc.domain.web.AutoSyncDingDomain;

import java.util.List;

public interface AutoSyncMapper {

    //获取ad的jobkey
    String obtainAdKey(Integer id);

    //获取应用的jobkey
    String obtainAppKey(Integer id);

    List<AutoSyncDingDomain> getAllDingId();

    List<AutoSyncAdDomain> getAllAdId();

    List<AutoSyncAppDomain> getAllAppId();



}
