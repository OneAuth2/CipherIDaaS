package com.portal.service;

import com.portal.domain.PortalApplyInfo;
import org.apache.http.NameValuePair;

import java.util.List;
import java.util.Map;

/**
 * @Author: 安歌
 * @Date: 2019/9/23 11:03
 */
public interface NcResService {

    /**
     * 构建用友的返回参数
     *
     * @param map
     * @return
     */
    String getNcResInfo(Map<String, Object> map);

    /**
     * 构建Nc的请求地址
     * @param portalApplyInfo
     * @param map
     * @param userCode
     * @return
     */
    String getNcClientUrl(PortalApplyInfo portalApplyInfo,String map,String userCode);

    /**
     * 获取NcIe端的url
     * @param portalApplyInfo
     * @param map
     * @return
     */
    String getNcIeUrl(PortalApplyInfo portalApplyInfo,String map);

    /**
     * 构建请求参数
     * @param portalApplyInfo
     * @return
     */
    List<NameValuePair> consNcReq(PortalApplyInfo portalApplyInfo,String userCode);
}
