package cipher.console.oidc.publistener;

import cipher.console.oidc.domain.web.AppAuditInfo;
import cipher.console.oidc.event.AppBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 插入应用日志消息发布者
 * @author cozi
 * @date 2019-07-09
 */
@Component
public class AppBehaviorPublistener {
    private final ApplicationContext applicationContext;

    @Autowired
    public AppBehaviorPublistener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(AppAuditInfo appAuditInfo){
        applicationContext.publishEvent(new AppBehaviorEvent(this,appAuditInfo));
    }
}
