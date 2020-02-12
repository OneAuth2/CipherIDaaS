package com.portal.event;

import com.portal.domain.UserBehaviorInfo;
import org.springframework.context.ApplicationEvent;

/**
 * 插入用户日志自定义事件
 * @author cozi
 * @date 2019-07-02
 **/
public class UserBehaviorEvent extends ApplicationEvent {
    private UserBehaviorInfo userBehaviorInfo;

    public UserBehaviorEvent(Object source, UserBehaviorInfo userBehaviorInfo) {
        super(source);
        this.userBehaviorInfo = userBehaviorInfo;
    }

    public UserBehaviorInfo getUserBehaviorInfo() {
        return userBehaviorInfo;
    }

    public void setUserBehaviorInfo(UserBehaviorInfo userBehaviorInfo) {
        this.userBehaviorInfo = userBehaviorInfo;
    }
}
