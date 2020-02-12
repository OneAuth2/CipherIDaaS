package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.exceldomain.WifiVistorExcle;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.domain.web.PortalLogInfo;
import cipher.console.oidc.mapper.GoldMantisMapper;
import cipher.console.oidc.mapper.OnlineVisitorMapper;
import cipher.console.oidc.mapper.VistorLoginLogInfoMapper;
import cipher.console.oidc.service.VistorInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by 95744 on 2018/9/25.
 */

@Service
public class VistorInfoServiceImpl implements VistorInfoService {

    @Autowired
    private VistorLoginLogInfoMapper vistorLoginLogInfoMapper;
    @Autowired
    private GoldMantisMapper goldMantisMapper;


    @Autowired
    private OnlineVisitorMapper onlineVisitorMapper;
    /**
     * create by 田扛
     * create time 2019年3月18日16:43:46
     * 获取月份的timeList
     */
    public List getMonthTimeList(int count,Date date1){
        List list=new ArrayList();
        for (int i=count;i>=0;i--){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date1);
            calendar.add(Calendar.MONTH, (-i));
            Date date = calendar.getTime();
            SimpleDateFormat sdf1 = new SimpleDateFormat("YYYY/MM");
            String s1 = sdf1.format(date)+"月";
            list.add(s1);
        }
        return  list;
    }
    /**
     *
     *create by 田扛
     * create time 2019年3月15日11:22:15
     * @params 员工还是访客
     * 获取员工或者访客一年的每个月的访问量
     */
    public List getEchartsList(String type,int count ,Date date,String companyId){
        List<Integer> list = new ArrayList<>();
        for (int i=count;i>=0;i--){
            int count1= vistorLoginLogInfoMapper.getEchartsYear(i,type,date,companyId);
            list.add(count1);
        }
        return list;
    }

    /**
     * create by田扛
     * create time 2019年3月15日15:32:19
     * 获取当年的十二个月的每个月的数量
     * @return
     */
    @Override
    public Map<String, Object> echartsYear(String date2,String date1,String companyId) throws ParseException {
        Map<String, Object> map = new HashMap<>();
       if (date1!=null&&date2!=null&&!"".equals(date1)&&!"".equals(date2)){//转换搜索日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date start=sdf.parse(date1);
            Date end=sdf.parse(date2);
           Calendar calendar = Calendar.getInstance();
           calendar.setTime(start);
           int startYear= calendar.get(Calendar.YEAR);
           int startMonth=calendar.get(Calendar.MONTH);
           calendar.setTime(end);
           int endYear=calendar.get(Calendar.YEAR);
           int endMonth=calendar.get(Calendar.MONTH);
           //System.out.println(startYear+"  "+startMonth+"  "+endYear+"  "+endMonth);
           int months=(endYear-startYear)*12+endMonth-startMonth;
           System.out.println(months);
           List list = getMonthTimeList(months,end);
           List list1 = getEchartsList("0",months,end,companyId);
           List list2 = getEchartsList("1",months,end,companyId);
           map.put("timeList", list);
           map.put("data1", list1);
           map.put("data2", list2);

//            long stateTimeLong = start.getTime();
//            long endTimeLong = end.getTime();
//            // 结束时间-开始时间 = 天数
//            long day = (endTimeLong-stateTimeLong)/(24*60*60*1000);
//            int days= (int) day;
//            List  list1=getcountList("0",days,end);
//            List  list2=getcountList("1",days,end);
//            List list3=getTimeList(days,end);
//            map.put( "timeList",list3);
//            map.put( "data1",list1);
//            map.put( "data2",list2);
           //List  list1=getDay
       }else {

            List list = getMonthTimeList(11,new Date());
            List list1 = getEchartsList("0",11,new Date(),companyId);
            List list2 = getEchartsList("1",11,new Date(),companyId);

            map.put("timeList", list);
            map.put("data1", list1);
            map.put("data2", list2);
        }
        return map;

    }

    @Override
    public Map<String, Object> getVistorList(GoldMantisUser form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new GoldMantisUser() : form);
        form.setPageData(pageModel);
        List<GoldMantisUser> list = goldMantisMapper.selectVistorList(form);
        map.put("rows", list);
        map.put("total", goldMantisMapper.selectVistorCount(form));
        return map;
    }

    /**
     * create by 田扛
     * create time 2019年3月18日16:09:41
     * 获取天数的timeList
     */
    public List getTimeList(int number,Date endTime){
        List<String> dates = new ArrayList<>();
        for (int i = number; i >=0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date date = calendar.getTime();
                SimpleDateFormat sdf1 = new SimpleDateFormat("MM/dd");
                String s1 = sdf1.format(date);
                dates.add(s1);

        }
        return dates;
    }
    /**
     *
     *create by 田扛
     * create time 2019年3月15日11:22:15
     * @params 员工还是访客
     * 获取员工或者访客近三十天的访问量
     */
    public List getcountList(String type,int number,Date endTime,String companyId){
        List<Integer> list = new ArrayList<>();
        for (int i = number; i >=0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(endTime);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date date = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date);
            String date5 = s + " 00:00:00";
            String date6 = s + " 23:59:59";
            int count = vistorLoginLogInfoMapper.selectCount(date5, date6,type,companyId);
            list.add(count);
        }
        return list;
    }

    @Override
    public Map<String, Object> getEcharts(String date1,String date2,String companyId) throws ParseException {
        Map<String,Object> map=new HashMap<>();
        if (date1!=null&&date2!=null&&!"".equals(date1)&&!"".equals(date2)){//转换搜索日期
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date start=sdf.parse(date1);
            Date end=sdf.parse(date2);
            long stateTimeLong = start.getTime();
            long endTimeLong = end.getTime();
            // 结束时间-开始时间 = 天数
            long day = (endTimeLong-stateTimeLong)/(24*60*60*1000);
            int days= (int) day;
            List  list1=getcountList("0",days,end,companyId);
            List  list2=getcountList("1",days,end,companyId);
            List list3=getTimeList(days,end);
            map.put( "timeList",list3);
            map.put( "data1",list1);
            map.put( "data2",list2);
           //List  list1=getDay
        }else {//获取默认31天数据
            List  list1=getcountList("0",30,new Date(),companyId);
            List  list2=getcountList("1",30,new Date(),companyId);
            List list3=getTimeList(30,new Date());
            map.put( "timeList",list3);
            map.put( "data1",list1);
            map.put( "data2",list2);
        }
        return map;
    }

    @Override
    public void insertVistorUser(GoldMantisUser goldMantisUser) throws Exception {
        goldMantisMapper.insertVistorUser(goldMantisUser);

    }

    @Override
    public void updateVistorUser(GoldMantisUser goldMantisUser) throws Exception {
        goldMantisMapper.updateGoldMantisUser(goldMantisUser);
    }

    @Override
    public void deleteVistor(OnlineVisitor onlineVisitor) {
        onlineVisitorMapper.deleteVistor(onlineVisitor);
    }

    @Override
    public Map<String, Object> getOnlineVistorList(OnlineVisitor form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new OnlineVisitor() : form);
        form.setPageData(pageModel);
        List<OnlineVisitor> list = onlineVisitorMapper.selectOnlineVistorList(form);
        map.put("rows", list);
        map.put("total", onlineVisitorMapper.selectOnlineVistorListCount(form));
        return map;
    }

    @Override
    public List<WifiVistorExcle> export(OnlineVisitor form) {
        return onlineVisitorMapper.export(form);
    }

    @Override
    public Map<String, Object> getChartsData(Integer dimension, Integer granularity, String startTime, String endTime,String companyId) {
        //账号类型
        if(dimension.equals(0)){
            //自定义时间
                if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)){
                    if(granularity.equals(0)){
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        Date start= null;
                        Date end = null;
                        try {
                            start = sdf.parse(startTime);
                            end=sdf.parse(endTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        long stateTimeLong = start.getTime();
                        long endTimeLong = end.getTime();
                        // 结束时间-开始时间 = 天数
                        long day = (endTimeLong-stateTimeLong)/(24*60*60*1000);
                        int days= (int) day;
                        return getDaysData(days,end,companyId);
                    }else {
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                        Date start= null;
                        Date end = null;
                        try {
                            start = sdf.parse(startTime);
                            end=sdf.parse(endTime);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        Calendar calendar = Calendar.getInstance();
                        calendar.setTime(start);
                        int startYear= calendar.get(Calendar.YEAR);
                        int startMonth=calendar.get(Calendar.MONTH);
                        calendar.setTime(end);
                        int endYear=calendar.get(Calendar.YEAR);
                        int endMonth=calendar.get(Calendar.MONTH);
                        //System.out.println(startYear+"  "+startMonth+"  "+endYear+"  "+endMonth);
                        int months=(endYear-startYear)*12+endMonth-startMonth;
                        return getMonthsData(months,end,companyId);
                    }
             //默认31天
            }else {
                    if(granularity.equals(0)){
                        return getDaysData(30,new Date(),companyId);
                    }else {
                        return getMonthsData(11,new Date(),companyId);
                    }
            }
        //SSID
        }else{
            //自定义时间
            if(StringUtils.isNotEmpty(startTime)&&StringUtils.isNotEmpty(endTime)) {
                if (granularity.equals(0)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    Date start= null;
                    Date end = null;
                    try {
                        start = sdf.parse(startTime);
                        end=sdf.parse(endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    long stateTimeLong = start.getTime();
                    long endTimeLong = end.getTime();
                    // 结束时间-开始时间 = 天数
                    long day = (endTimeLong-stateTimeLong)/(24*60*60*1000);
                    int days= (int) day;
                    return getSsidDaysData(days,end,companyId);
                }else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
                    Date start= null;
                    Date end = null;
                    try {
                        start = sdf.parse(startTime);
                        end=sdf.parse(endTime);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTime(start);
                    int startYear= calendar.get(Calendar.YEAR);
                    int startMonth=calendar.get(Calendar.MONTH);
                    calendar.setTime(end);
                    int endYear=calendar.get(Calendar.YEAR);
                    int endMonth=calendar.get(Calendar.MONTH);
                    //System.out.println(startYear+"  "+startMonth+"  "+endYear+"  "+endMonth);
                    int months=(endYear-startYear)*12+endMonth-startMonth;
                    return getSsidMonthData(months,end,companyId);
                }
            }else {
                if (granularity.equals(0)) {
                    return getSsidDaysData(30,new Date(),companyId);
                }else {
                    return getSsidMonthData(11,new Date(),companyId);
                }
            }
        }
    }
    private Map<String, Object> getDaysData(int number,Date date,String companyId){
        Map<String, Object> map = new HashMap<>();
        List  list1=getcountList("0",number,date,companyId);
        List  list2=getcountList("1",number,date,companyId);
        List list3=getTimeList(number,date);
        map.put( "timeList",list3);
        map.put( "data1",list1);
        map.put( "data2",list2);
        return map;
    }
    private Map<String, Object> getMonthsData(int count,Date date,String companyId){
        Map<String, Object> map = new HashMap<>();
        List list = getMonthTimeList(count,date);
        List list1 = getEchartsList("0",count,date,companyId);
        List list2 = getEchartsList("1",count,date,companyId);
        map.put("timeList", list);
        map.put("data1", list1);
        map.put("data2", list2);
        return map;
    }

    /**
     * 获取统计维度为ssid
     * @param number
     * @param date
     * @param companyId
     * @return
     */
    private Map<String, Object> getSsidDaysData(int number,Date date,String companyId){
        Map<String, Object> result = new HashMap<>();
        List<PortalLogInfo> portalLogInfos = vistorLoginLogInfoMapper.selectPortalList(companyId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(PortalLogInfo portalLogInfo:portalLogInfos){
            Map<String, Object> map = new HashMap<>();
            List list=getTimeList(number,date);
            List list1 = getSsidDays(number,date,companyId,portalLogInfo.getPortalId());
            map.put("timeList",list);
            map.put("data", list1);
            map.put("portalName",portalLogInfo.getPortalName());
            mapList.add(map);
        }
        result.put("portal", mapList);
        return result;
    }
    private Map<String, Object> getSsidMonthData(int count,Date date,String companyId){
        Map<String, Object> result = new HashMap<>();
        List<PortalLogInfo> portalLogInfos = vistorLoginLogInfoMapper.selectPortalList(companyId);
        List<Map<String, Object>> mapList = new ArrayList<>();
        for(PortalLogInfo portalLogInfo:portalLogInfos){
            Map<String, Object> map = new HashMap<>();
            List list = getMonthTimeList(count,date);
            List list1 = getSsidMonths(count,date,companyId, portalLogInfo.getPortalId());
            map.put("timeList",list);
            map.put("date", list1);
            map.put("portalName", portalLogInfo.getPortalName());
            mapList.add(map);
        }
        result.put("portal", mapList);
        return result;
    }
    public List getSsidDays(int number,Date date,String companyId,Integer portalId){
        List<Integer> list = new ArrayList<>();
        if(number<=0||StringUtils.isEmpty(companyId)||StringUtils.isEmpty(portalId.toString())){
            return list;
        }
        for (int i = number; i >=0; i--) {
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            Date date1 = calendar.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String s = sdf.format(date1);
            String date5 = s + " 00:00:00";
            String date6 = s + " 23:59:59";
            int time = vistorLoginLogInfoMapper.selectSsidCount(date5,date6,companyId,portalId);
            list.add(time);
        }
        return list;
    }
    public List getSsidMonths(int number,Date date,String companyId,Integer portalId){
        List<Integer> list = new ArrayList<>();
        if(number<=0||StringUtils.isEmpty(companyId)||StringUtils.isEmpty(portalId.toString())){
            return list;
        }
        for (int i=number;i>=0;i--){
            int time=vistorLoginLogInfoMapper.selectSsidMonthCount(i, date,companyId,portalId);
            list.add(time);
        }
        return list;
    }
}
