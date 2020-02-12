package com.portal.daoAuthoriza;
import org.apache.ibatis.annotations.Param;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:36
 */
public interface CompanyServiceMapDAO {

    /**
     * @param companyId 公司id
     * @param serviceId 服务id
     * @return 服务状态
     */
    public Integer queryServiceStatus(@Param("companyId") Integer companyId, @Param("serviceId") Integer serviceId);



}
