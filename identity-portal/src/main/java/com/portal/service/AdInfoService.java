package com.portal.service;


import com.portal.domain.AdInfoDomain;

/**
 * @Author: zt
 * @Date: 2018/6/7 17:38
 */
public interface AdInfoService {


    /**
     * 查询AD域的配置是否存在
     *
     * @param form
     * @return
     */
    public AdInfoDomain queryAdInfo(AdInfoDomain form);

}
