package com.portal.daoAuthoriza;


import com.portal.domain.SubAccountDomain;
import com.portal.domain.SubAccountMapDomain;
import com.portal.model.SubAccountAuthModel;
import org.apache.ibatis.annotations.Param;

public interface SubAccountDAO {

    SubAccountDomain getTheSubAccount(@Param("subAccount") String sub_account,
                                      @Param("appClientId") String app_client_id);


    /**
     * 插入一条关联记录
     * @param form
     * @throws Exception
     */
    void insertSubAccountMap(SubAccountMapDomain form) throws Exception;





    int insertSubAccountInfo(SubAccountDomain form) throws Exception;



    SubAccountDomain querySubAccountInfo(@Param(value = "userId")String userId,
                                         @Param(value = "appClientId")String appClientId);


    void deleteSubAccountMap(SubAccountAuthModel form) throws Exception;

    SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain);


    int deleteSubAccountMap(SubAccountMapDomain subAccountMapDomain);


    public Integer queryPrimaryId(@Param("subAccount") String subAccount,@Param("appClientId") String appClientId);



    int updateSubAccountInfo(SubAccountDomain form) throws Exception;



}
