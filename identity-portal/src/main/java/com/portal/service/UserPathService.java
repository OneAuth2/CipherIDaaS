package com.portal.service;


import com.portal.domain.UserPathInfo;

import java.util.Map;

/**
 * TODO:
 * create by liuying at 2019/9/23
 *
 * @author liuying
 * @since 2019/9/23 9:54
 */
public interface UserPathService {

    public UserPathInfo getUserPathInfo(UserPathInfo userPathInfo);

    public  void insertUserPahtInfo(UserPathInfo userPathInfo);

    public  void updateUserPahtInfo(UserPathInfo userPathInfo);


    public Map<String,Object> getCsDate(String userId, String applyId);
}
