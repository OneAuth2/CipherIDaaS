package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.domain.exceldomain.ApplicationAuditExcle;
import cipher.console.oidc.domain.web.ApplicationAuditInfo;
import cipher.console.oidc.domain.web.ApplicationInfo;
import cipher.console.oidc.domain.web.ApplicationChartInfo;
import cipher.console.oidc.mapper.ApplicationAuditInfoMapper;
import cipher.console.oidc.service.ApplicationAuditInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by 95744 on 2018/6/4.
 */
@Service
public class ApplicationAuditInfoServiceImpl implements ApplicationAuditInfoService {

    @Autowired
    private ApplicationAuditInfoMapper applicationAuditInfoMapper;

    @Override
    public Map<String, Object> getApplicationAuditPageList(ApplicationAuditInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new ApplicationAuditInfo() : form);
        form.setPageData(pageModel);
        if(StringUtils.isNotEmpty(form.getStartTime())){
            form.setStartTime(form.getStartTime()+" 00:00:00");
        }
        if(StringUtils.isNotEmpty(form.getEndTime())){
            form.setEndTime(form.getEndTime()+" 23:59:59");
        }
        List<ApplicationAuditInfo> list = null;
        int total = 0;
        if(StringUtils.isNotEmpty(form.getStartTime())||StringUtils.isNotEmpty(form.getEndTime())){
            list = applicationAuditInfoMapper.selectApplicationAuditList(form);
            total = applicationAuditInfoMapper.selectApplicationAuditCount(form);
        }else{
            if(form.getRecently()!=null){
                if(form.getRecently().equals(0)){
                    //七天内应用日志
                    list = applicationAuditInfoMapper.selectAppAuditListBySev(form);
                    total = applicationAuditInfoMapper.selectAppAuditCountBySev(form);
                }else {
                    //30天内应用日志
                    list = applicationAuditInfoMapper.selectAppAuditListByMon(form);
                    total = applicationAuditInfoMapper.selectAppAuditCountByMon(form);
                }
            }else{
                list = applicationAuditInfoMapper.selectAppAuditListByAll(form);
                total = applicationAuditInfoMapper.selectAppAuditCountByAll(form);
            }
        }
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

  /*  private static List<Integer> sev=new ArrayList<>();
    private static List<Integer> mon=new ArrayList<>();
    static {
        for(int i=0;i<30;i++){
            sev.add(getRandom(30,50));
            mon.add(getRandom(80,150));
        }
    }

    private static int getRandom(int min, int max){
        Random random = new Random();
        return random.nextInt(max) % (max - min + 1) + min;
    }*/


    @Override
    public Map<String, Object> getApplicationPageList(ApplicationInfo form, DataGridModel pageModel) {
        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new ApplicationInfo() : form);
        form.setPageData(pageModel);
        //获取应用
        List<ApplicationInfo> list = applicationAuditInfoMapper.selectApplicationList(form);
        int total = 0;
        if(list!=null&&list.size()>0){
            DecimalFormat df = new DecimalFormat("0.00%");
            String format = "";
            //只统计应用已存在的日志
            String ids = applicationAuditInfoMapper.selectIdsBycompanyid(form.getCompanyId());
            if (StringUtils.isNotEmpty(form.getStartTime()) || StringUtils.isNotEmpty(form.getEndTime())) {
                //按照时间范围
                Integer totalByTime = applicationAuditInfoMapper.getTotalByTime(form.getCompanyId(), form.getStartTime(), form.getEndTime(),ids);
                for (ApplicationInfo applicationInfo:list){
                    Integer countByTime = applicationAuditInfoMapper.getCountByTime(applicationInfo.getId(), form.getCompanyId(), form.getStartTime(), form.getEndTime());
                    if(totalByTime.intValue()>0){
                        format = df.format(countByTime.doubleValue() / totalByTime.doubleValue());
                    }
                    applicationInfo.setProportion(format);
                    applicationInfo.setVisitTimes(countByTime);
                }
            } else {
                //根据时间是最近7天，还是30天,还是空值
                Integer totalBySev = applicationAuditInfoMapper.getTotalBySev(form.getCompanyId(),ids);
                Integer totalByMon = applicationAuditInfoMapper.getTotalByMon(form.getCompanyId(),ids);
                Integer totalByAll = applicationAuditInfoMapper.getTotalByAll(form.getCompanyId(),ids);
                for (int flg = 0; flg < list.size(); flg++) {
                    ApplicationInfo applicationInfo = list.get(flg);
                    if((null!=form && form.getRecently()==null)||StringUtils.isEmpty(form.getRecently().toString())){
                        Integer countByAll = applicationAuditInfoMapper.getCountByAll(applicationInfo.getId(), form.getCompanyId());
                        if(totalByAll.intValue()>0){
                            format = df.format(countByAll.doubleValue()/totalByAll.doubleValue());
                        }
                        applicationInfo.setProportion(format);
                        applicationInfo.setVisitTimes(countByAll);
                    }else {
                        if (form.getRecently().equals(0)) {
                            Integer countBySev = applicationAuditInfoMapper.getCountBySev(applicationInfo.getId(),form.getCompanyId());
                            if(totalBySev.intValue()>0){
                                format = df.format(countBySev.doubleValue() / totalBySev.doubleValue());
                            }
                            applicationInfo.setProportion(format);
                            applicationInfo.setVisitTimes(countBySev);
                        } else {
                            Integer countByMon = applicationAuditInfoMapper.getCountByMon(applicationInfo.getId(),form.getCompanyId());
                            if(totalByMon.intValue()>0){
                                format = df.format(countByMon.doubleValue() / totalByMon.doubleValue());
                            }
                            applicationInfo.setProportion(format);
                            applicationInfo.setVisitTimes(countByMon);
                        }
                    }

                }
            }
            total = applicationAuditInfoMapper.selectApplicationCount(form);
        }
        map.put("rows", list);
        map.put("total", total);
        return map;
    }

    @Override
    public List<ApplicationInfo> getApplicationList(ApplicationInfo form, DataGridModel pageModel) {
        form.setPageData(pageModel);
        List<ApplicationInfo> list = applicationAuditInfoMapper.selectApplicationList(form);
        for(ApplicationInfo applicationInfo:list){
            Integer countBySev = applicationAuditInfoMapper.getCountBySev(applicationInfo.getId(),form.getCompanyId());
            Integer countByMon = applicationAuditInfoMapper.getCountByMon(applicationInfo.getId(),form.getCompanyId());
            applicationInfo.setCountBySev(countBySev);
            applicationInfo.setCountByMon(countByMon);
        }
        return list;
    }

    @Override
    public List<ApplicationAuditExcle> exportExcle(ApplicationAuditInfo form) {
        return applicationAuditInfoMapper.exportExcle(form);
    }

    @Override
    public List<ApplicationInfo> getApplyList(String applicationId, String companyId) {
        return applicationAuditInfoMapper.getApplyList(Integer.valueOf(applicationId), companyId);
    }

    @Override
    public ApplicationInfo getApplyInfo(ApplicationInfo form) {
        return applicationAuditInfoMapper.getApplyInfo(form);
    }

    @Override
    public void insertApplicationAuditInfo(ApplicationAuditInfo form) {
        applicationAuditInfoMapper.insertSelective(form);
    }

    @Override
    public Map<String, Object> getChartData(List<String> list,String companyId) {
        Map<String, Object> reMap = new HashMap<>();
        List<Map<String,Object>> chartList = new ArrayList();
        if(list!=null&&list.size()>0){
            List<ApplicationChartInfo> applicationInfoList = applicationAuditInfoMapper.getApplicationInfoList(companyId);
            if(applicationInfoList!=null&&applicationInfoList.size()>0){
                for(ApplicationChartInfo applicationChartInfo:applicationInfoList){
                    Map<String, Object> map = new HashMap<>();
                    map.put("applicationName", applicationChartInfo.getApplicationName());
                    for(int i=0;i<list.size();i++){
                        Integer applicationAuditCount = applicationAuditInfoMapper.getApplicationAuditCount(list.get(i), companyId, applicationChartInfo.getId());
                        map.put("day"+list.get(i),applicationAuditCount);
                    }
                    chartList.add(map);
                }
                reMap = NewReturnUtils.successResponse("数据成功取出");
                reMap.put("rows", chartList);
                return reMap;
            }
        }
        return NewReturnUtils.failureResponse("数据查询失败");
    }
}
