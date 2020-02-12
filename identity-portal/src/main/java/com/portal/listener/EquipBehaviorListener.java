package com.portal.listener;

import com.portal.daoAuthoriza.EquipBehaviorInfoDAO;
import com.portal.daoAuthoriza.UserBehaviorInfoDAO;
import com.portal.domain.EquipBehaviorInfo;
import com.portal.domain.UserBehaviorInfo;
import com.portal.event.EquipBehaviorEvent;
import com.portal.event.UserBehaviorEvent;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 插入设备审计日志监听器
 * @author cozi
 * @date 2019-07-02
 **/
@Component
public class EquipBehaviorListener {

    @Autowired
    private EquipBehaviorInfoDAO equipBehaviorInfoDAO;


    @EventListener
    public void onAplicationEvent(EquipBehaviorEvent equipBehaviorEvent){
        //处理插入日志逻辑
        if(equipBehaviorEvent.getEquipBehaviorInfo()!=null){
            EquipBehaviorInfo equipBehaviorInfo = equipBehaviorEvent.getEquipBehaviorInfo();
            if(StringUtils.isNotEmpty(equipBehaviorInfo.getUserId())){
                //获取用户信息
                String userInfo = equipBehaviorInfoDAO.selectUserInfoByUserId(equipBehaviorInfo.getUserId());
                //添加用户信息
                equipBehaviorInfo.setUserInfo(userInfo);
                try {
                    equipBehaviorInfoDAO.insertEquipBehavior(equipBehaviorInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }

    }
}
