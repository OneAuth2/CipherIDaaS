package com.portal.service;

import com.portal.domain.WeiXinConfig;

/**
 * TODO:
 * create by liuying at 2019/9/29
 *
 * @author liuying
 * @since 2019/9/29 11:44
 */
public interface WxInfoService {

    public WeiXinConfig getWeiXinConfigInfo(String companyId);
}
