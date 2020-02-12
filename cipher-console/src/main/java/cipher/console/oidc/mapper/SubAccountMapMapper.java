package cipher.console.oidc.mapper;

import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.web.SubAccountDomain;
import cipher.console.oidc.domain.web.SubAccountMapDomain;

public interface SubAccountMapMapper {


    /**
     * 插入一条主从账号的关联关系
     * @param domain
     */
   void insertSubAccountMap(SubAccountMapDomain domain);

    /**
     * 判断一个关联关系是否已经存在
     * @param domain
     * @return
     */
   int querySubAccountMap(SubAccountExcel domain);

    Integer selectSubId(SubAccountDomain subAccountDomain);

   int deleteInfo(String accountNumber);

    Integer insertInfo(SubAccountMapDomain subAccountMapDomain);

    SubAccountMapDomain querySubAccountMap(String accountNumber);

     void deleteSubAccountNumber(SubAccountDomain subAccountDomain);


    SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain);

    int deleteSubAccountMap(SubAccountMapDomain subAccountMapDomain);




}
