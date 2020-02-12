package cipher.console.oidc.job;

import cipher.console.oidc.controller.web.DingUserController;
import cipher.console.oidc.util.SpringContextUtil;
import org.apache.catalina.connector.Request;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 钉钉自动同步任务
 *
 * @author cozi
 * @date 2019-08-07
 */
public class DingDingSyncJob implements Job {


    private static final Logger LOGGER = LoggerFactory.getLogger(DingDingSyncJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {

            String companyId = jobExecutionContext.getJobDetail().getJobDataMap().get("companyId").toString();
            DingUserController bean = SpringContextUtil.getBean(DingUserController.class);
            HttpServletRequest request = new Request();

            //将钉钉用户扫描到缓冲表
            bean.scanDingUser(request, companyId);
            //将缓冲表的用户同步到用户体系
            bean.syncAllDingUser(request, companyId);
        } catch (Exception e) {
            LOGGER.error("钉钉定时同步出现异常:{},{}",e.getMessage(),e.getCause());
            e.printStackTrace();
        }

    }
}
