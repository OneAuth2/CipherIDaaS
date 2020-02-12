package com.portal.daoAuthoriza;


import com.portal.domain.SendKapataDomain;
import org.apache.ibatis.annotations.Param;

/**
 * Created by 95744 on 2018/8/30.
 */
public interface SendKapataDAO {


    public SendKapataDomain selectSendKapataInfo(@Param("username") String username, @Param("count") int count);

    public int insertSendKapataInfo(SendKapataDomain sendKapataDomain);
}
