package cipher.console.oidc.listener;

import cipher.console.oidc.domain.web.AppAuditInfo;
import cipher.console.oidc.event.AppBehaviorEvent;
import cipher.console.oidc.mapper.ApplicationAuditInfoMapper;
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
    private ApplicationAuditInfoMapper applicationAuditInfoMapper;

    @EventListener
    public void onAplicationEvent(AppBehaviorEvent appBehaviorEvent){
        if(appBehaviorEvent.getAppAuditInfo()!=null){
            AppAuditInfo appAuditInfo = appBehaviorEvent.getAppAuditInfo();
            if(appAuditInfo!=null){
                applicationAuditInfoMapper.insertNew(appAuditInfo);
            }
        }
    }
}
