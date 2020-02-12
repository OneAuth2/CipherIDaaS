package com.portal.service;

import com.portal.domain.PortalApplyInfo;

/**
 * TODO:
 * create by liuying at 2019/9/28
 *
 * @author liuying
 * @since 2019/9/28 15:34
 */


public interface CsTypeService {

    public  String getCsTypeJson(String userId,String subName, String subPwd, PortalApplyInfo portalApplyInfo);

}
