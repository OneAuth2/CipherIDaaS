package com.portal.service.impl;

import com.portal.service.ApplicationService;
import com.portal.service.JudgeLimit;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JudgeLimitServiceImpl implements JudgeLimit {
    @Autowired
    private ApplicationService applicationService;

    @Override
    public Boolean isOwnAppLimit(String uuid, String appId) {
        //入参校验
        if (StringUtils.isEmpty(uuid) || StringUtils.isEmpty(appId)) {
            return false;
        }

        //判断人与部门和权限与应用是否存在关联
        if (applicationService.getIdFromGroupIdAndApplicationId(uuid, appId)){
            return true;
        }

        //获取人与安全组是否存在关联
        if (applicationService.getIdFromTeamAndApplicationId(uuid,appId)){
            return true;
        }

        //判断人是否直接拥有应用的权限
        if (applicationService.getIddFromUserAndApplication(uuid, appId)){
            return true;
        }


        return false;
    }
}
