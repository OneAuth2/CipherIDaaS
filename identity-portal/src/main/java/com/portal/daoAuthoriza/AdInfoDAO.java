package com.portal.daoAuthoriza;


import com.portal.domain.AdInfoDomain;
import com.portal.domain.UserInfoDomain;

/**
 * AD信息查询的DAO层
 * create by shizhao at 2019/5/7
 *
 * @author shizhao
 * @since  2019/5/7
 */
public interface AdInfoDAO {


    /**
     * 根据用户的信息查询Ad信息
     * @param userInfoDomain
     * */
    AdInfoDomain selectAdInfoByUserInfo(UserInfoDomain userInfoDomain);


    public AdInfoDomain queryAdInfo(AdInfoDomain form);

}
