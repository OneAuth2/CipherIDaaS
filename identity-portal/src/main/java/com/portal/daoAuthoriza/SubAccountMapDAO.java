package com.portal.daoAuthoriza;


import com.portal.domain.SubAccountDomain;
import com.portal.domain.SubAccountMapDomain;

public interface SubAccountMapDAO {


    /**
     * 插入一条主从账号的关联关系
     * @param domain
     */
   void insertSubAccountMap(SubAccountMapDomain domain);



    Integer selectSubId(SubAccountDomain subAccountDomain);

   int deleteInfo(String accountNumber);

    Integer insertInfo(SubAccountMapDomain subAccountMapDomain);

    SubAccountMapDomain querySubAccountMap(String accountNumber);

     void deleteSubAccountNumber(SubAccountDomain subAccountDomain);


    SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain);

    int deleteSubAccountMap(SubAccountMapDomain subAccountMapDomain);




}
