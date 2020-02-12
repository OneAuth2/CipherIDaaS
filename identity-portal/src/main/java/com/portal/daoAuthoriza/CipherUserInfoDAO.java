package com.portal.daoAuthoriza;

import com.portal.domain.UserInfoDomain;
import org.apache.ibatis.annotations.Param;


/**
 * @Author: zt
 * @Date: 2018/11/30 10:08
 */
public interface CipherUserInfoDAO {

    /**
     * 根据用户名查找用户的AD域来源
     *
     * @param uuid
     * @return
     */
    public UserInfoDomain queryUserByName(@Param("uuid") String uuid);


    public void modifyUserAccountControl(@Param("uuid") String accountNumber,
                                         @Param("control") int control,
                                         @Param("companyId") String companyId);


    public UserInfoDomain insertUserInfo(UserInfoDomain userInfoDomain);

}
