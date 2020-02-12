package cipher.console.oidc.listener;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 * @Author: zt
 * @Date: 2018/10/8 11:06
 */
@WebListener
public class QuartzInitListener implements ServletContextListener  {

    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzInitListener.class);

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
//    SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        LOGGER.info("==========开始启动所有定时任务==================");
      /*  QuartzManager.addJob(SyncZhUserJob.class,"0 59 23 * * ?","synczhuser");
        QuartzManager.addJob(SyncZhDepartJob.class,"0 59 23 * * ?","synczhdepartment");
        QuartzManager.addJob(SyncZhPostJob.class,"0 59 23 * * ?","synczhpost");
        QuartzManager.addJob(VistorOffLineJob.class,"0 0/10 * * * ?","doOffLine");*/
        LOGGER.info("==========启动所有定时任务成功==================");
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {

    }
}
