package com.portal.daoAuthoriza;

/**
 * @Author: zt
 * @Date: 2019/1/5 19:29
 */
public interface PlatUserCompanyMapDAO {

    /**
     * 根据账号查询公司id
     *
     * @param platUserId
     * @return
     */
    public Integer queryCompayIdByPlatUserID(Integer platUserId);

}
