package com.portal.daoAuthoriza;


import org.apache.ibatis.annotations.Param;

/**
 * @Author: zt
 * @Date: 2019/1/7 14:45
 */
public interface CompanyInfoDAO {

    public Integer queryCompanyIdByPlatUserId(@Param("platUserId") Integer platUserId);

}
