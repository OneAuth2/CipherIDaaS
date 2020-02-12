package cipher.console.oidc.service;

import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.web.SubAccountDomain;
import cipher.console.oidc.domain.web.SubAccountMapDomain;

/**
 * @Author: zt
 * @Date: 2018/6/6 14:57
 */
public interface SubAccountMapService {

    void insertSubAccountMap(SubAccountMapDomain domain);

    int querySubAccountMap(SubAccountExcel domain);

    Integer selectSubId(SubAccountDomain subAccountDomain);

    int deleteInfo(String accountNumber);

    Integer insertInfo(SubAccountMapDomain subAccountMapDomain);


    SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain);


    int deleteSubAccountMap(SubAccountMapDomain subAccountMapDomain);



}
