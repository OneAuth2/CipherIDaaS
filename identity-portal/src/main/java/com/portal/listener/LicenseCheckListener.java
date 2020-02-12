package com.portal.listener;/**
 * @author lqgzj
 * @date 2019-09-28
 */

import com.portal.license.VerifyLicense;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * @Author qiaoxi
 * @Date 2019-09-2815:15
 **/
@Component
public class LicenseCheckListener {
    private static Logger logger = LogManager.getLogger(LicenseCheckListener.class);

   /* @EventListener
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //root application context 没有parent
        ApplicationContext context = event.getApplicationContext().getParent();
        if (context == null) {
            logger.info("++++++++ 开始安装证书 ++++++++");

            VerifyLicense vLicense = new VerifyLicense();
            //获取参数
            vLicense.setParam("/config/param.properties");
            //验证证书
            vLicense.install();

            logger.info("++++++++ 证书安装结束 ++++++++");
        }
    }*/

}
