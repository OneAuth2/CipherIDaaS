package com.portal.daoAuthoriza;

import com.portal.domain.UserPathInfo;

/**
 * TODO:
 * create by liuying at 2019/9/23
 *
 * @author liuying
 * @since 2019/9/23 10:02
 */
public interface UserPathDAO {

    public UserPathInfo getUserPathInfo(UserPathInfo userPathInfo);

    public  void insertUserPahtInfo(UserPathInfo userPathInfo);

    public  void updateUserPathInfo(UserPathInfo userPathInfo);
}
