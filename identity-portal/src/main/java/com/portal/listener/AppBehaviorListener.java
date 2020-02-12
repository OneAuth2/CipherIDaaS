package com.portal.listener;

import com.portal.daoAuthoriza.ApplicationAuditInfoDAO;
import com.portal.domain.AppAuditInfo;
import com.portal.event.AppBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * 插入应用日志监听器
 * @author cozi
 * @date 2019-07-09
 */
@Component
public class AppBehaviorListener {

    @Autowired
    private ApplicationAuditInfoDAO applicationAuditInfoDAO;

    @EventListener
    public void onAplicationEvent(AppBehaviorEvent appBehaviorEvent){
        if(appBehaviorEvent.getAppAuditInfo()!=null){
            AppAuditInfo appAuditInfo = appBehaviorEvent.getAppAuditInfo();
            if(appAuditInfo!=null){
                applicationAuditInfoDAO.insertAppAuditInfo(appAuditInfo);
            }
        }
    }
}
