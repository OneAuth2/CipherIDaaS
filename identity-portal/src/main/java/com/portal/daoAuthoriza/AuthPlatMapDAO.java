package com.portal.daoAuthoriza;


import com.portal.domain.AuthPlatMapInfo;

public interface AuthPlatMapDAO {

    public AuthPlatMapInfo getAuthPlatMapInfo(AuthPlatMapInfo form);

    public void insertAuthPlatMapInfo(AuthPlatMapInfo form);
}
