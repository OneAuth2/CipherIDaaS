package com.portal.listener;

import com.portal.daoAuthoriza.UserBehaviorInfoDAO;
import com.portal.domain.UserBehaviorInfo;
import com.portal.event.UserBehaviorEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 插入用户日志监听器
 * @author cozi
 * @date 2019-07-02
 **/
@Component
public class UserBehaviorListener {

    @Autowired
    private UserBehaviorInfoDAO userBehaviorInfoDAO;

    @EventListener
    public void onAplicationEvent(UserBehaviorEvent userBehaviorEvent){
        //处理插入日志逻辑
        if(userBehaviorEvent.getUserBehaviorInfo()!=null){
            UserBehaviorInfo userBehaviorInfo = userBehaviorEvent.getUserBehaviorInfo();
            if(StringUtils.isNotEmpty(userBehaviorInfo.getUserId())){
                //获取用户信息
                String userInfo = userBehaviorInfoDAO.selectUserInfoByUserId(userBehaviorInfo.getUserId());
                //添加用户信息
                userBehaviorInfo.setUserInfo(userInfo);
                //添加事件简介
                userBehaviorInfo.setMsg(userBehaviorInfo.getMsg());
                try {
                    userBehaviorInfoDAO.insertSelective(userBehaviorInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
