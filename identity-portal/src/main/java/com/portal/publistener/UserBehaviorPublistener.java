package com.portal.publistener;

import com.portal.domain.UserBehaviorInfo;
import com.portal.event.UserBehaviorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * 插入用户日志消息发布者
 * @author cozi
 * @date 2019-07-02
 **/
@Component
public class UserBehaviorPublistener {
    private final ApplicationContext applicationContext;

    @Autowired
    public UserBehaviorPublistener(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

    public void publish(UserBehaviorInfo userBehaviorInfo){
        applicationContext.publishEvent(new UserBehaviorEvent(this,userBehaviorInfo));
    }
}
