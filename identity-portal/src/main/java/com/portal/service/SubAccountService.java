package com.portal.service;

import com.portal.domain.SubAccountDomain;
import com.portal.domain.SubAccountMapDomain;
import com.portal.model.SubAccountAuthModel;
import org.apache.ibatis.annotations.Param;


import java.util.List;
import java.util.Map;


/**
 * 子账户管理的service
 *
 * @author sizhao、zhoutao
 */

public interface SubAccountService {

    /**
     * 根据用户名和应用ID获取特定的子账户
     *
     * @param
     * @param app_client_id 应用唯一Id
     * @return 子账户信息
     */
    SubAccountDomain getTheSubAccount(String sub_account,
                                      String app_client_id);


    int insertSubAccountInfo(SubAccountDomain form) throws Exception;


    SubAccountDomain querySubAccountInfo(@Param(value = "userId") String userId,
                                         @Param(value = "appClientId") String appClientId);


    void deleteSubAccountMap(SubAccountAuthModel form) throws Exception;


    SubAccountMapDomain querySubMap(SubAccountMapDomain subAccountMapDomain);

    void insertSubAccountMap(SubAccountMapDomain domain) throws Exception;

    public Integer queryPrimaryId(@Param("subAccount") String subAccount,@Param("appClientId") String appClientId);


    int updateSubAccountInfo(SubAccountDomain form) throws Exception;




}
