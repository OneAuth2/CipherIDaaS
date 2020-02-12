package com.portal.service.impl;




import com.portal.daoAuthoriza.PlatCipherUserMapDAO;
import com.portal.service.PlatCipherUserMapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * @Author: zt
 * @Date: 2019/1/17 10:54
 */
@Service
public class PlatCipherUserMapServiceImpl implements PlatCipherUserMapService {

    @Autowired
    private PlatCipherUserMapDAO platCipherUserMapDAO;

    @Override
    public String queryCipherUserByPlatUserId(Integer platUserId) {
        return platCipherUserMapDAO.queryCipherUserByPlatUserId(platUserId);
    }

    @Override
    public Integer queryPlatUserIdByCipherUser(String accountNumber) {
        return platCipherUserMapDAO.queryPlatUserIdByCipherUser(accountNumber);
    }
}
