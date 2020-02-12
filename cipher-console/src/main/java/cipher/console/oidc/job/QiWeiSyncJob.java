package cipher.console.oidc.job;/**
 * @author lqgzj
 * @date 2019-10-15
 */

import cipher.console.oidc.controller.web.WxUserController;
import cipher.console.oidc.util.SpringContextUtil;
import org.apache.catalina.connector.Request;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;

/**
 * 企业微信自动同步任务
 * @Author qiaoxi
 * @Date 2019-10-1515:35
 **/
public class QiWeiSyncJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(QiWeiSyncJob.class);
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            String companyId = jobExecutionContext.getJobDetail().getJobDataMap().get("companyId").toString();
            WxUserController bean = SpringContextUtil.getBean(WxUserController.class);
            HttpServletRequest request = new Request();

            //将企业微信用户扫描到缓冲表
            bean.scanWxUser(request, companyId);
            //将缓冲表的用户同步到用户体系
            bean.syncAllWxUser(request, companyId);
        } catch (Exception e) {
            LOGGER.error("企业微信定时同步出现异常:{},{}",e.getMessage(),e.getCause());
            e.printStackTrace();
        }
    }
}
