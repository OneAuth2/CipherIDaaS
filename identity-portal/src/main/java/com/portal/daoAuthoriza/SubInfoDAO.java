package com.portal.daoAuthoriza;

import org.apache.ibatis.annotations.Param;

import com.portal.domain.SubAccountDomain;
import com.portal.domain.SubAccountMapDomain;

public interface SubInfoDAO {

    SubAccountDomain selectSubAccountInfo(SubAccountDomain form);

    SubAccountMapDomain selectSubAccountMapInfo(SubAccountMapDomain form);

    void insertSubAccountInfo(SubAccountDomain form);

    void insertSubAccountMapInfo(SubAccountMapDomain form);

    void updateSubInfo(SubAccountDomain form);

    String getSubAccountInfo(@Param("applicationId") String applicationId , @Param("uuid") String uuid);


    SubAccountDomain getSubAccountInfoByHost(@Param("applicationId") String applicationId , @Param("uuid") String uuid);
}
