package com.portal.service.impl;


import com.portal.daoAuthoriza.InitPasswordDAO;
import com.portal.domain.PasswordSetting;
import com.portal.service.InitPasswordService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Author: zt
 * @Date: 2018/10/16 17:31
 */
@Service
public class InitPwdServiceImpl implements InitPasswordService {

    private static final int SIMPLE = 1;

    private static final int MEDIAN = 2;

    private static final int COMPLEX = 3;

    @Autowired
    private InitPasswordDAO initPwdDAO;

    @Override
    public String getInitPwd() {
        return initPwdDAO.getInitPwd();
    }

    @Override
    public PasswordSetting getPasswordSetting(String companyId) {
        return initPwdDAO.getPasswordSetting(companyId);
    }

    @Override
    public boolean isAcceptable(String password, PasswordSetting passwordSetting) {
        String pattern = "^(?=.*[0-9])(?=.*[a-zA-Z])(.{8,})$";
        if (StringUtils.isEmpty(passwordSetting.getPasswordType())){
            return false;
        }

        int type = 0;
        try {
            type = Integer.parseInt(passwordSetting.getPasswordType());
        }catch (Exception e){
            return false;
        }

        if (StringUtils.isEmpty(password)){
            return false;
        }

        switch (type){
            case SIMPLE:
                if (password.length() < 6){
                    return false;
                }
                return true;
            case MEDIAN:
                if (password.length() < 8){
                    return false;
                }
                return true;
            case COMPLEX:
                if (password.length() < 8){
                    return false;
                }
                if (!password.matches(pattern)){
                    return false;
                }
                return true;

                default:
                    return false;
        }
    }


}
