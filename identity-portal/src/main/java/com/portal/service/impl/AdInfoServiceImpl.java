package com.portal.service.impl;





import com.portal.daoAuthoriza.AdInfoDAO;
import com.portal.domain.AdInfoDomain;
import com.portal.service.AdInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2018/6/7 17:38
 */

@Service
public class AdInfoServiceImpl implements AdInfoService {

    @Autowired
    private AdInfoDAO adInfoDAO;


    @Override
    public AdInfoDomain queryAdInfo(AdInfoDomain form) {
        return adInfoDAO.queryAdInfo(form);
    }


}
