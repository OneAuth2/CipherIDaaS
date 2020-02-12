package cipher.console.oidc.service;

import cipher.console.oidc.domain.web.LadpInfoDomain;

/**
 * Created by 95744 on 2018/9/14.
 */
public interface LadpManageService {

    public LadpInfoDomain getLadpManageInfo(LadpInfoDomain ladpInfoDomain);

    public int updateLadpManageInfo(LadpInfoDomain ladpInfoDomain);


    public int insertLadpManageInfo(LadpInfoDomain ladpInfoDomain);

}
