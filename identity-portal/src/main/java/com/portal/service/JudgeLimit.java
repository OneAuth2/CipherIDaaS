package com.portal.service;
/**
 * @Author: TK
 * @Date: 2019/7/4 11:13
 */
public interface JudgeLimit {


    /**
     * 根据应用的id以及uuid 判断用户是否拥有该应用的权限
     * create by 安歌
     * create time 2019年7月22日15:45:52
     * @param uuid
     * @param appId
     * @return
     */
    Boolean  isOwnAppLimit(String uuid, String appId);


}
