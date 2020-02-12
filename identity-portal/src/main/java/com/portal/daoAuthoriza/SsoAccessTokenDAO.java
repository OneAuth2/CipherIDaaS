package com.portal.daoAuthoriza;

import com.portal.auth2.model.SsoAccessToken;
import org.apache.ibatis.annotations.Param;

/**
 * TODO:
 * create by liuying at 2019/11/27
 *
 * @author liuying
 * @since 2019/11/27 18:05
 */
public interface SsoAccessTokenDAO {

    int deleteByPrimaryKey(Integer id);

    int insert(SsoAccessToken record);

    int insertSelective(SsoAccessToken record);

    SsoAccessToken selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(SsoAccessToken record);

    int updateByPrimaryKey(SsoAccessToken record);

    /**
     * 通过用户ID查询记录
     * @author zifangsky
     * @date 2018/8/30 14:24
     * @since 1.0.0
     * @param userId 用户ID
     * @param clientId 请求Token的渠道
     * @return cn.zifangsky.model.SsoAccessToken
     */
    SsoAccessToken selectByUserIdAndClientId(@Param("userId") String userId, @Param("clientId") Integer clientId);

    /**
     * 通过Access Token查询记录
     * @author zifangsky
     * @date 2018/8/30 14:24
     * @since 1.0.0
     * @param accessToken Access Token
     * @return cn.zifangsky.model.SsoAccessToken
     */
    SsoAccessToken selectByAccessToken(@Param("accessToken") String accessToken);
}
