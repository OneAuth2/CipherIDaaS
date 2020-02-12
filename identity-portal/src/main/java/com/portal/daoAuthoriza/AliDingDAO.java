package com.portal.daoAuthoriza;


import com.portal.domain.AliDingInfoDomain;

public interface AliDingDAO {


    public AliDingInfoDomain getAliDingInfo(AliDingInfoDomain form);

    public void insertAliDingInfo(AliDingInfoDomain form);
}
