package com.portal.service;


import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.SubAccountDomain;


public interface ApplicationService {

      /**
       * 获取人与应用是否存在关联
       * create by 安歌
       * @param uuid
       * @param appId
       * @return
       */
      Boolean  getIddFromUserAndApplication(String uuid ,String appId);

      /**
       * create by 安歌
       * create time 2019年7月18日15:41:47
       * 根据appHost www.cipher.china:9005  userName=uuid 获取与此应用关联的从账号的信息
       * @param appHost
       * @param userId
       * @return
       */
      SubAccountDomain getSubAccountDomainByAppHost(String appHost,String userId);

      /**
       * 获取安全组与应用的关联
       * create by 安歌
       * @param teamId
       * @param appId
       * @return
       */
      Boolean getIdFromTeamAndApplicationId(String teamId, String appId);

      /**
       * 获取部门与应用的关联
       * create by 安歌
       * @param groupId
       * @param appId
       * @return
       */
      Boolean getIdFromGroupIdAndApplicationId(String groupId,String appId);

      /**
       * 根据serviceurl获取应用的id
       * create by 安歌
       * @param serviceUrl 应用地址
       * @return
       */
      String getIdFromApplicationInfoByServiceUrl(String serviceUrl);

      /**
       *
       */

      /**
       * create by 安歌
       * create time 2019年7月18日09:53:29
       * 根据应用id查询应用信息
       * @param id 应用id
       * @return
       */
      ApplicationInfoDomain getApplicationById(String id);

      ApplicationInfoDomain queryApplication(String applicationId) ;

      //根据访问地址和账号从数据库中获取从账号
      String querySubAccountDomain(String service, String uuid);

      /**
       * create by 安歌
       * 根据url获取从账号的账号密码
       * create time 2019年7月17日16:58:21
       * @param host
       * @param uuid
       * @return
       */
      SubAccountDomain getSubAccountDomain(String host, String uuid);

      String  getApplicationId(String serviceUrl);


      /**
       * create by 安歌
       * create time 2019年7月18日10:28:07
       * 根据apphost获取应用id
       * @param appHost 应用地址
       * @return
       */
      String getIdByAppHost(String appHost);

      /**
       * 根据回调的地址获取应用信息
       * @param acs
       * @return
       */
    ApplicationInfoDomain getApplicationInfoByAssertionConsumerServiceURL(String acs);
}
