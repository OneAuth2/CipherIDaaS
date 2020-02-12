package cipher.console.oidc.event;

import cipher.console.oidc.domain.web.AppAuditInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 插入应用日志自定义事件
 * @author cozi
 * @date 2019-07-09
 */
public class AppBehaviorEvent extends ApplicationEvent {
    private AppAuditInfo appAuditInfo;

    public AppBehaviorEvent(Object source,AppAuditInfo appAuditInfo) {
        super(source);
        this.appAuditInfo=appAuditInfo;
    }

    public AppAuditInfo getAppAuditInfo() {
        return appAuditInfo;
    }

    public void setAppAuditInfo(AppAuditInfo appAuditInfo) {
        this.appAuditInfo = appAuditInfo;
    }
}
