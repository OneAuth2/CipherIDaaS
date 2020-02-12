package com.portal.daoAuthoriza;


import com.portal.domain.ApplicationInfoDomain;



import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface ApplicationDAO {

    ApplicationInfoDomain queryApplication(String applicationId) ;

    ApplicationInfoDomain queryApplicationById(int id) ;

    String findApplicationId(@Param("service") String service);

    String getIdFromUserAndApplication(@Param("uuid") String uuid, @Param("appId") String appId);

    List<String> getIdFromTeamAndApplicationId(@Param("uuid") String uuid, @Param("appId") String appId);

    List<String> getIdFromGroupIdAndApplicationId(@Param("uuid")String uuid, @Param("appId")String appId);

    String getIdFromApplicationByServiceUrl(@Param("service") String service);

    String getAppIdByAppHost(@Param("appHost") String appHost);

    String findApplicationIdByAppHost(@Param("appHost")String appHost);

    ApplicationInfoDomain getApplicationInfoByAssertionConsumerServiceURL(@Param("acs")String acs);
}
