package com.portal.daoAuthoriza;

import com.portal.domain.WeiXinConfig;
import org.apache.ibatis.annotations.Param;

/**
 * TODO:
 * create by liuying at 2019/10/14
 *
 * @author liuying
 * @since 2019/10/14 10:36
 */
public interface WxInfoInfoDAO {

  public WeiXinConfig getWeiXinConfigInfo(@Param("companyId") String  companyId);

}
