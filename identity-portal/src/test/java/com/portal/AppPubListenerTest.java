package com.portal;

import com.portal.domain.AppAuditInfo;
import com.portal.publistener.AppBehaviorPublistener;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 添加应用日志
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class AppPubListenerTest {

    @Autowired
    private AppBehaviorPublistener appBehaviorPublistener;

    @Test
    public void test(){
        AppAuditInfo appAuditInfo = new AppAuditInfo(123, "admin(123456)", 1, "测试", "123456");
        appBehaviorPublistener.publish(appAuditInfo);
    }
}
