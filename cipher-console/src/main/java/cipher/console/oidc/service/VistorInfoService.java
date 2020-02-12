package cipher.console.oidc.service;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.WifiVistorExcle;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/25.
 */
public interface VistorInfoService {
    /**
     * create by 田扛
     * 时间 2019年3月15日14:15:10
     * 获取最近一年的数据
     * @return
     */
    public Map<String,Object> echartsYear(String date1,String date2,String companyId) throws ParseException;

    public Map<String,Object> getVistorList(GoldMantisUser form, DataGridModel pageModel);

    /**
     * create by 田扛
     * create time 2019年3月15日09:03:12
     * 获取echarts的近三十天的数据
     * @return
     */
    public Map<String,Object> getEcharts(String date1,String date2,String companyId) throws ParseException;

    public void insertVistorUser(GoldMantisUser goldMantisUser) throws Exception;

     public void updateVistorUser(GoldMantisUser goldMantisUser) throws Exception;

     public  void deleteVistor(OnlineVisitor onlineVisitor);

    public Map<String,Object> getOnlineVistorList(OnlineVisitor form, DataGridModel pageModel);

    List<WifiVistorExcle> export(OnlineVisitor form);

    Map<String,Object> getChartsData(Integer dimension,Integer granularity,String startTime,String endTime,String companyId);



}
