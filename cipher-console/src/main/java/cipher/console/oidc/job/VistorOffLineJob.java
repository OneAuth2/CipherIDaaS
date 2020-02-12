package cipher.console.oidc.job;


import cipher.console.oidc.domain.quartz.QuartzInfo;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.domain.web.WifiActionMessage;
import cipher.console.oidc.service.OnlineVisitorService;
import cipher.console.oidc.service.QuartzInfoService;
import cipher.console.oidc.service.WifiOffLineService;
import cipher.console.oidc.util.HttpRequest;
import cipher.console.oidc.util.SpringContextUtil;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class VistorOffLineJob implements Job {

    private OnlineVisitorService onlineVisitorService= SpringContextUtil.getBean(OnlineVisitorService.class);

    private WifiOffLineService wifiOffLineService= SpringContextUtil.getBean(WifiOffLineService.class);


    private QuartzInfoService quartzInfoService= SpringContextUtil.getBean(QuartzInfoService.class);

    private static final Logger logger = LoggerFactory.getLogger(VistorOffLineJob.class);


    public String doOffLineVistor() {

        int succeed = 0;
        int fail = 0;
        int total = 0;
        try {
            List<OnlineVisitor> onlineVisitorList = onlineVisitorService.selectOffLineVistor();
            if(null!=onlineVisitorList&&onlineVisitorList.size()>0) {
                for (cipher.console.oidc.domain.web.OnlineVisitor onlineVisitor : onlineVisitorList) {
                    WifiActionMessage wifiActionMessage=new WifiActionMessage();
                    wifiActionMessage.setUserName(onlineVisitor.getUsername());
                    wifiActionMessage.setMac(onlineVisitor.getMac());
                    wifiActionMessage.setIp(onlineVisitor.getIp());
                    wifiOffLineService.sendWifiOffLineMsg(wifiActionMessage);
                }
                succeed++;
            }
        } catch (Exception e) {
            fail++;
            logger.info("访客下线报错" + e.getMessage());
        }
        total++;
        String quartzRemark = "访客下线" + total + ",成功" + succeed + "次,失败" + fail + "次";
        return quartzRemark;
    }


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
      //  SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
        Map<String, Object> qiData = new HashMap<String, Object>();
        QuartzInfo qi = null;
        try {
            qi = quartzInfoService.findByCode("doOffLine");
            qiData.put("id", qi.getId());
            String remark = doOffLineVistor();
            qiData.put("succeed", qi.getSucceed() + 1);
        } catch (Exception e) {
            logger.error("告警通知事件报错：", e);
        } finally {
            logger.info("保存告警事件任务日志");
            try {
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}

