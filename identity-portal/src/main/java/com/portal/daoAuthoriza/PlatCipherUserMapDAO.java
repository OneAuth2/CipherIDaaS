package com.portal.daoAuthoriza;

/**
 * @Author: zt
 * @Date: 2019/1/17 10:23
 */
public interface PlatCipherUserMapDAO {

    /**
     * 根据平台用户查询portal用户
     * @param platUserId
     * @return
     */
    public String queryCipherUserByPlatUserId(Integer platUserId);

    /**
     * 根据portal用户查询关联的平台用户id
     * @param accountNumber
     * @return
     */
    public Integer queryPlatUserIdByCipherUser(String accountNumber);

}
