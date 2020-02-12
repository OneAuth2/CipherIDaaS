package com.portal.service;

import com.portal.domain.RdsgDomain;

/**
 * @Author: TK
 * @Date: 2019/7/24 9:30
 */
public interface RdsgService {

    /**
     * rdsg到portal portal判断已经登录直接跳转到RDSG
     * create by 安歌
     * create time 2019年7月24日09:33:18
     *
     */
    public void  isLoggedIn(RdsgDomain rdsgDomain);
}
