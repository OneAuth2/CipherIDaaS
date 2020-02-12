package com.portal.service;

import com.portal.auth2.model.SsoAccessToken;
import com.portal.auth2.model.SsoClientDetails;
import com.portal.auth2.model.SsoRefreshToken;
import com.portal.domain.UserInfoDomain;

/**
 * TODO:
 * create by liuying at 2019/11/27
 *
 * @author liuying
 * @since 2019/11/27 15:07
 */
public interface AuthSsoService {

    /**
     * 通过主键ID查询记录
     * @param id ID
     * @return cn.zifangsky.model.SsoAccessToken
     */
    SsoAccessToken selectByAccessId(Integer id);


    /**
     * 通过Access Token查询记录
     * @param accessToken Access Token
     * @return cn.zifangsky.model.SsoAccessToken
     */
    SsoAccessToken selectByAccessToken(String accessToken);

    /**
     * 通过tokenId查询记录
     * @param tokenId tokenId
     * @return cn.zifangsky.model.SsoRefreshToken
     */
    SsoRefreshToken selectByTokenId(Integer tokenId);

    /**
     * 通过Refresh Token查询记录
     * @param refreshToken Refresh Token
     * @return cn.zifangsky.model.SsoRefreshToken
     */
    SsoRefreshToken selectByRefreshToken(String refreshToken);

    /**
     * 生成Access Token
     * @param user 用户信息
     * @param expiresIn 过期时间
     * @param ssoClientDetails 接入客户端详情
     * @param requestIP 用户请求的IP
     * @return java.lang.String
     */
    String createAccessToken(UserInfoDomain user, Long expiresIn, String requestIP, SsoClientDetails ssoClientDetails);

    /**
     * 生成Refresh Token
     * @param user 用户信息
     * @param ssoAccessToken 生成的Access Token信息
     * @return java.lang.String
     */
    String createRefreshToken(UserInfoDomain user, SsoAccessToken ssoAccessToken);


}
