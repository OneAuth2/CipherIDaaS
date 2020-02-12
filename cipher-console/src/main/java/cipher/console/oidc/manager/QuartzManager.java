package cipher.console.oidc.manager;
import org.quartz.*;
import org.quartz.impl.StdSchedulerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Date;

/**
 * 定时任务管理
 */
@SuppressWarnings({"rawtypes"})
public class QuartzManager {

    private static SchedulerFactory gSchedulerFactory = new StdSchedulerFactory();
    private static final Logger LOGGER = LoggerFactory.getLogger(QuartzManager.class);


    /**
     * @param cls    定时任务的类
     * @param time   触发条件的字符串 cronTrigger
     * @param jobKey 定时任务的key
     */
    public static void addJob(Class cls, String time, String jobKey,String companyId) {
        try {
            Scheduler sched = gSchedulerFactory.getScheduler();
            JobDetail jobDetail = JobBuilder.newJob(cls)
                    .withIdentity(jobKey)
                    .build();
            jobDetail.getJobDataMap().put("companyId", companyId);
//            "0 0 0 * * * *"   格式: [秒] [分] [小时] [日] [月] [周] [年]
            CronTrigger cronTrigger = TriggerBuilder.newTrigger()
                    .withIdentity(jobKey)
                    .withSchedule(CronScheduleBuilder.cronSchedule(time))
                    .startNow()
                    .build();
            sched.scheduleJob(jobDetail, cronTrigger);
            sched.start();
        } catch (Exception e) {
            LOGGER.error("添加任务失败", e);
        }
    }


    /**
     * 移除一个定时任务
     *
     * @param triggerKey
     * @param jobKey
     */
    public static void removeJob(String triggerKey, String jobKey) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            scheduler.pauseTrigger(TriggerKey.triggerKey(triggerKey));
            scheduler.unscheduleJob(TriggerKey.triggerKey(triggerKey));
            scheduler.deleteJob(JobKey.jobKey(jobKey));
            LOGGER.info("移除任务成功:" + jobKey);
        } catch (SchedulerException e) {
            LOGGER.error("移除任务失败", e);
        }
    }

    /**
     * 立即执行一个定时任务
     *
     * @param jobKey 任务名称
     */
    public static void startNow(String jobKey) {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            scheduler.triggerJob(JobKey.jobKey(jobKey));
            LOGGER.info("立即执行一个定时任务成功" + jobKey);
        } catch (SchedulerException e) {
            LOGGER.error("立即执行一个定时任务失败" + jobKey, e);
        }
    }

    /**
     * 关闭所有的定时任务
     *
     */
    public static void shutdownAllJobs() {
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            if (!scheduler.isShutdown()) {
                scheduler.shutdown();
            }
            LOGGER.info("关闭所有定时任务成功:" + new Date());
        } catch (SchedulerException e) {
            LOGGER.error("关闭所有定时任务失败:" + new Date(), e);
        }
    }

    /**
     * 更改一个定时任务的触发时间
     *
     * @param triggerKey  任务名称
     * @param triggerTime 新的触发条件
     */
    public static void modifyTime(String triggerKey, String triggerTime) {
        LOGGER.info("更改一个任务的触发时间:"+triggerKey+"->"+triggerTime);
        try {
            Scheduler scheduler = gSchedulerFactory.getScheduler();
            CronTrigger cronTrigger = (CronTrigger) scheduler.getTrigger(TriggerKey.triggerKey(triggerKey));
            if (cronTrigger == null) {
                return;
            }
            String oldTime = cronTrigger.getCronExpression();
            if (!oldTime.equals(triggerTime)) {
                TriggerBuilder<Trigger> triggerBuilder = TriggerBuilder.newTrigger();
                Trigger trigger = triggerBuilder.withIdentity(triggerKey)
                        .withSchedule(CronScheduleBuilder.cronSchedule(triggerTime))
                        .build();
                scheduler.rescheduleJob(TriggerKey.triggerKey(triggerKey), trigger);
            }
        } catch (SchedulerException e) {
           LOGGER.error("更改任务的触发时间失败:"+triggerKey+"->"+triggerTime,e);
        }
    }


}
