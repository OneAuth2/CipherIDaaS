package cipher.console.oidc.job;

import cipher.console.oidc.controller.web.AdUserBufferController;
import cipher.console.oidc.controller.web.AdUserBufferV3Controller;
import cipher.console.oidc.controller.web.Buffer2UserController;
import cipher.console.oidc.util.SpringContextUtil;
import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;
import org.apache.http.HttpRequest;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.JobKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;

/**
 * ad自动同步任务
 *
 * @author cozi
 * @date 2019-08-07
 */
public class ADSyncJob implements Job {

    private static final Logger LOGGER = LoggerFactory.getLogger(ADSyncJob.class);

    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        try {
            //获取要同步的AD的主键id
            JobKey key = jobExecutionContext.getJobDetail().getKey();
            Integer id = Integer.parseInt(key.getName());
            String companyId = jobExecutionContext.getJobDetail().getJobDataMap().get("companyId").toString();

            //同步AD数据到缓冲表
            AdUserBufferV3Controller bean = SpringContextUtil.getBean(AdUserBufferV3Controller.class);
            HttpServletRequest request = new Request();
            bean.allSyncAdUser2BufferOld(request, id, companyId);

            //将缓冲表的数据同步到用户体系结构
            Buffer2UserController buffer2UserController= SpringContextUtil.getBean(Buffer2UserController.class);
           // AdUserBufferV3Controller bufferV3Controller = SpringContextUtil.getBean(AdUserBufferV3Controller.class);
            HttpServletRequest request1 = new Request();
            buffer2UserController.syncAll(request1,companyId);
           // bufferV3Controller.allSyncAdUser2BufferOld(request1, id, companyId);

        } catch (Exception e) {
            LOGGER.error("AD定时同步出现异常:{},{}", e.getMessage(), e.getCause());
            e.printStackTrace();
        }

    }
}
