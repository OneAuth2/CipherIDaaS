package cipher.console.oidc.service.impl;

import cipher.console.oidc.domain.web.AutoSyncAdInfo;
import cipher.console.oidc.domain.web.AutoSyncInfo;
import cipher.console.oidc.job.ADSyncJob;
import cipher.console.oidc.job.ApplicationSyncJob;
import cipher.console.oidc.job.DingDingSyncJob;
import cipher.console.oidc.job.QiWeiSyncJob;
import cipher.console.oidc.manager.QuartzManager;
import cipher.console.oidc.mapper.AutoSyncMapper;
import cipher.console.oidc.service.AutoSyncService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class AutoSyncServiceImpl implements AutoSyncService {

    @Autowired
    private AutoSyncMapper autoSyncMapper;


      /**
       自动同步企业钉钉定时任务
     */

    @Override
    public boolean autoSyncDingTask(AutoSyncInfo autoSyncInfo,String jobkey,String companyId) {
        if(autoSyncInfo.getIsAutoSync()==0&&
                StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate())&&
                autoSyncInfo.getInterval()>-1&&
                StringUtils.isNotEmpty(companyId)){
            QuartzManager.addJob(DingDingSyncJob.class, getTimeFormat(autoSyncInfo),jobkey,companyId);
            return true;
        }
        return false;
    }

    @Override
    public boolean closeAutoSyncDingTask(String jobkey) {
        if(StringUtils.isNotEmpty(jobkey)){
            QuartzManager.removeJob(jobkey,jobkey);
            return true;
        }
        return false;
    }


    /**
     自动同步AD定时任务
    */
    @Override
    public boolean autoSyncAdTask(Integer id,String companyId,AutoSyncAdInfo autoSyncAdInfo) {
        if(autoSyncAdInfo.getIsAutoSync()==0&&
                StringUtils.isNotEmpty(autoSyncAdInfo.getAutoSyncDate())&&
                autoSyncAdInfo.getInterval()>-1&&
                StringUtils.isNotEmpty(companyId)){
            AutoSyncInfo autoSyncInfo = new AutoSyncInfo();
            autoSyncInfo.setIsAutoSync(autoSyncAdInfo.getIsAutoSync());
            autoSyncInfo.setAutoSyncDate(autoSyncAdInfo.getAutoSyncDate());
            autoSyncInfo.setInterval(autoSyncAdInfo.getInterval());
            String jobkey = String.valueOf(id);//autoSyncMapper.obtainAdKey(id);
            if(StringUtils.isNotEmpty(jobkey)){
                QuartzManager.addJob(ADSyncJob.class,getTimeFormat(autoSyncInfo),jobkey,companyId);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean closeAutoSyncAdTask(Integer id) {
        if(id!=null){
            String jobkey = String.valueOf(id);//autoSyncMapper.obtainAdKey(id);
            if(StringUtils.isNotEmpty(jobkey)){
                QuartzManager.removeJob(jobkey,jobkey);
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean autoSyncApplicationTask(Integer id,String companyId,AutoSyncInfo autoSyncInfo) {
        if(autoSyncInfo.getIsAutoSync()==0&&
                StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate())&&
                autoSyncInfo.getInterval()>-1&&
                StringUtils.isNotEmpty(companyId)){
            String jobkey = autoSyncMapper.obtainAppKey(id);
            if(StringUtils.isNotEmpty(jobkey)){
                QuartzManager.addJob(ApplicationSyncJob.class, getTimeFormat(autoSyncInfo),jobkey,companyId);
                return true;
            }
        }
        return false;
    }

    /**
    自动同步企业微信定时任务
     */
    @Override
    public boolean autoSyncWxTask(AutoSyncInfo autoSyncInfo, String jobkey, String companyId) {
        if (autoSyncInfo.getIsAutoSync() == 0 &&
                StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate()) &&
                autoSyncInfo.getInterval() > -1 &&
                StringUtils.isNotEmpty(companyId)) {
            QuartzManager.addJob(QiWeiSyncJob.class, getTimeFormat(autoSyncInfo), jobkey, companyId);
            return true;
        }
        return false;
    }

    @Override
    public boolean closeAutoSyncWxTask(String jobkey) {
        if (StringUtils.isNotEmpty(jobkey)) {
            QuartzManager.removeJob(jobkey, jobkey);
            return true;
        }
        return false;
    }

    @Override
    public boolean closeAutoSyncApplicationTask(Integer id) {
        if(id!=null){
            String jobkey = autoSyncMapper.obtainAppKey(id);
            if(StringUtils.isNotEmpty(jobkey)) {
                QuartzManager.removeJob(jobkey, jobkey);
                return true;
            }
        }
        return false;
    }

    public static String getTimeFormat(AutoSyncInfo autoSyncInfo){
        String timeFormat = "";
        String syncDate = "";
        String setTimes = "";
        List<String> list = new ArrayList<>();
        if(StringUtils.isNotEmpty(autoSyncInfo.getAutoSyncDate())){
            String[] strings = autoSyncInfo.getAutoSyncDate().split(":");
            if(strings.length==3){
                switch (autoSyncInfo.getInterval()){
                    case 0:
                        timeFormat=Integer.parseInt(strings[2])+" 0/15 * * * ?";
                        return timeFormat;
                    case 1:
                        timeFormat=Integer.parseInt(strings[2])+" "+Integer.parseInt(strings[1])+" * * * ?";
                        return timeFormat;
                    case 2:
                        timeFormat=Integer.parseInt(strings[2])+" "+Integer.parseInt(strings[1])+" ";
                        syncDate = autoSyncInfo.getAutoSyncDate();
                        for(int i=0;i<4;i++){
                            setTimes = setTime(syncDate, 6, i);
                            String[] split = setTimes.split(" ");
                            String[] split1 = split[1].split(":");
                            list.add(split1[0]);
                        }
                        Collections.sort(list);
                        timeFormat = jointTimeFormat(list,timeFormat);
                        return timeFormat;
                    case 3:
                        timeFormat=Integer.parseInt(strings[2])+" "+Integer.parseInt(strings[1])+" ";
                        syncDate = autoSyncInfo.getAutoSyncDate();
                        list = new ArrayList<>();
                        for(int i=0;i<2;i++){
                            setTimes = setTime(syncDate, 12, i);
                            String[] split = setTimes.split(" ");
                            String[] split1 = split[1].split(":");
                            list.add(split1[0]);
                        }
                        Collections.sort(list);
                        timeFormat = jointTimeFormat(list,timeFormat);
                        return timeFormat;
                    case 4:
                        timeFormat=Integer.parseInt(strings[2])+" "+Integer.parseInt(strings[1])+" "+Integer.parseInt(strings[0])+" * * ?";
                        return timeFormat;
                    default:
                        timeFormat="0 30 0 * * ?";
                        return timeFormat;
                }
            }
        }
        return timeFormat;
    }
    public static String setTime(String assignTime,int interval,int time){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sdf1=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date= new Date();//获取当前时间
        Date date1 = null;//指定时间
        String format = sdf.format(date);//当前时间年月日
        String format1 = format+" "+assignTime;//指定时间年月日时分秒
        try {
            date1 = sdf1.parse(format1);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date1);
        cal.add(Calendar.HOUR_OF_DAY,interval*time);
        date1 = cal.getTime();
        return sdf1.format(date1);
    }

    public static String jointTimeFormat(List<String> list,String timeFormat){
        for(int j=0;j<list.size();j++){
            if(j==(list.size()-1)){
                timeFormat+= Integer.parseInt(list.get(j))+" * * ?";
            }else {
                timeFormat+= Integer.parseInt(list.get(j))+",";
            }
        }
        return timeFormat;
    }

    public static void main(String[] args) {
        AutoSyncInfo autoSyncInfo = new AutoSyncInfo();
        autoSyncInfo.setInterval(2);
        autoSyncInfo.setAutoSyncDate("13:02:23");
        System.out.println(getTimeFormat(autoSyncInfo));
        /*String s = setTime("12:02:23", 6, 1);
        System.out.println(s);*/
    }
}
