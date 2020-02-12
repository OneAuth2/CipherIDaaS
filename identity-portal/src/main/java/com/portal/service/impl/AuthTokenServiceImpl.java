package com.portal.service.impl;

import com.portal.auth2.enums.ExpireEnum;
import com.portal.auth2.model.SsoAccessToken;
import com.portal.auth2.model.SsoClientDetails;
import com.portal.auth2.model.SsoRefreshToken;
import com.portal.auth2.utils.DateUtils;
import com.portal.auth2.utils.EncryptUtils;
import com.portal.daoAuthoriza.SsoAccessTokenDAO;
import com.portal.daoAuthoriza.SsoRefreshTokenDAO;
import com.portal.domain.UserInfoDomain;
import com.portal.service.AuthSsoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * TODO:
 * create by liuying at 2019/11/27
 *
 * @author liuying
 * @since 2019/11/27 17:55
 */

@Service
public class AuthTokenServiceImpl implements AuthSsoService {


    @Autowired
    private SsoAccessTokenDAO ssoAccessTokenDAO;


    @Autowired
    private SsoRefreshTokenDAO ssoRefreshTokenDAO;



    @Override
    public SsoAccessToken selectByAccessId(Integer id) {
        return ssoAccessTokenDAO.selectByPrimaryKey(id);
    }

    @Override
    public SsoAccessToken selectByAccessToken(String accessToken) {
        return ssoAccessTokenDAO.selectByAccessToken(accessToken);
    }

    @Override
    public SsoRefreshToken selectByTokenId(Integer tokenId) {

        return ssoRefreshTokenDAO.selectByTokenId(tokenId);
    }

    @Override
    public SsoRefreshToken selectByRefreshToken(String refreshToken) {
        return ssoRefreshTokenDAO.selectByRefreshToken(refreshToken);
    }



    @Override
    public String createAccessToken(UserInfoDomain user, Long expiresIn, String requestIP, SsoClientDetails ssoClientDetails) {
        Date current = new Date();
        //过期的时间戳
        Long expiresAt = DateUtils.nextDaysSecond(ExpireEnum.ACCESS_TOKEN.getTime(), null);

        //1. 拼装待加密字符串（username + 渠道CODE + 当前精确到毫秒的时间戳）
        String str = user.getUuid() + ssoClientDetails.getClientName() + String.valueOf(DateUtils.currentTimeMillis());

        //2. SHA1加密
        String accessTokenStr = "11." + EncryptUtils.sha1Hex(str) + "." + expiresIn + "." + expiresAt;

        //3. 保存Access Token
        SsoAccessToken savedAccessToken = ssoAccessTokenDAO.selectByUserIdAndClientId(user.getUuid(), ssoClientDetails.getId());
        //如果存在匹配的记录，则更新原记录，否则向数据库中插入新记录
        if(savedAccessToken != null){
            savedAccessToken.setAccessToken(accessTokenStr);
            savedAccessToken.setExpiresIn(expiresAt);
            savedAccessToken.setUpdateUser(user.getUuid());
            savedAccessToken.setUpdateTime(current);
            ssoAccessTokenDAO.updateByPrimaryKeySelective(savedAccessToken);
        }else{
            savedAccessToken = new SsoAccessToken();
            savedAccessToken.setAccessToken(accessTokenStr);
            savedAccessToken.setUserId(user.getUuid());
            savedAccessToken.setUserName(user.getUserName());
            savedAccessToken.setIp(requestIP);
            savedAccessToken.setClientId(String.valueOf(ssoClientDetails.getId()));
            savedAccessToken.setChannel(ssoClientDetails.getClientName());
            savedAccessToken.setExpiresIn(expiresAt);
            savedAccessToken.setUpdateUser(user.getUuid());
            savedAccessToken.setCreateTime(current);
            savedAccessToken.setUpdateTime(current);
            ssoAccessTokenDAO.insertSelective(savedAccessToken);
        }

        //4. 返回Access Token
        return accessTokenStr;
    }

    @Override
    public String createRefreshToken(UserInfoDomain user, SsoAccessToken ssoAccessToken) {
        Date current = new Date();
        //过期时间
        Long expiresIn = DateUtils.dayToSecond(ExpireEnum.REFRESH_TOKEN.getTime());
        //过期的时间戳
        Long expiresAt = DateUtils.nextDaysSecond(ExpireEnum.REFRESH_TOKEN.getTime(), null);

        //1. 拼装待加密字符串（username + accessToken + 当前精确到毫秒的时间戳）
        String str = user.getUuid() + ssoAccessToken.getAccessToken() + String.valueOf(DateUtils.currentTimeMillis());

        //2. SHA1加密
        String refreshTokenStr = "12." + EncryptUtils.sha1Hex(str) + "." + expiresIn + "." + expiresAt;

        //3. 保存Refresh Token
        SsoRefreshToken savedRefreshToken = ssoRefreshTokenDAO.selectByTokenId(ssoAccessToken.getId());
        //如果存在tokenId匹配的记录，则更新原记录，否则向数据库中插入新记录
        if(savedRefreshToken != null){
            savedRefreshToken.setRefreshToken(refreshTokenStr);
            savedRefreshToken.setExpiresIn(expiresAt);
            savedRefreshToken.setUpdateUser(user.getUuid());
            savedRefreshToken.setUpdateTime(current);
            ssoRefreshTokenDAO.updateByPrimaryKeySelective(savedRefreshToken);
        }else{
            savedRefreshToken = new SsoRefreshToken();
            savedRefreshToken.setTokenId(ssoAccessToken.getId());
            savedRefreshToken.setRefreshToken(refreshTokenStr);
            savedRefreshToken.setExpiresIn(expiresAt);
            savedRefreshToken.setUpdateUser(user.getUuid());
            savedRefreshToken.setCreateTime(current);
            savedRefreshToken.setUpdateTime(current);
            ssoRefreshTokenDAO.insertSelective(savedRefreshToken);
        }

        //4. 返回Refresh Tokens
        return refreshTokenStr;
    }

}
