package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;
import cipher.console.oidc.mapper.OnlineVisitorMapper;
import cipher.console.oidc.mapper.VistorLoginLogInfoMapper;
import cipher.console.oidc.service.VisitorLoginLogInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/26.
 */
@Service
public class VistorLoginLogInfoServiceImpl implements VisitorLoginLogInfoService {

    @Autowired
    private VistorLoginLogInfoMapper vistorLoginLogInfoMapper;
    @Autowired
    private OnlineVisitorMapper onlineVisitorMapper;

    /**
     * modify by 田扛
     * modify time 2019年3月13日17:28:23
     * 获取无线审计数据列表
     *
     * @param form
     * @param pageModel
     * @return
     */
    @Override
    public Map<String, Object> getVistorLoginLogList(VistorLoginLogInfo form, DataGridModel pageModel) {

        if(StringUtils.isNotEmpty(form.getStartTime())) {
            String startTime = form.getStartTime()+" 00:00:00";
            form.setStartTime(startTime);
        }
        if(StringUtils.isNotEmpty(form.getEndTime())) {
            String endTime = form.getEndTime()+" 23:59:59";
            form.setEndTime(endTime);
           // System.out.println(form.getEndTime());
        }

        Map<String, Object> map = new HashMap<>();
        form = (form == null ? new VistorLoginLogInfo() : form);
        form.setPageData(pageModel);
         //   if (StringUtils.isEmpty(form.getState())){
        List<VistorLoginLogInfo> list = vistorLoginLogInfoMapper.selectVistorLoginLogList(form);
        //将姓名和账号拼接（格式："xxx(xx)"）
        for(VistorLoginLogInfo vistorLoginLogInfo:list){
            String name = vistorLoginLogInfo.getName();
            String vistorName = vistorLoginLogInfo.getVistorName();
            vistorLoginLogInfo.setVistorName(name+"("+vistorName+")");
        }

//            for (OnlineVisitor onlineVisitor : onlineVisitors) {
//                VistorLoginLogInfo vistorLoginLogInfo = vistorLoginLogInfoMapper.getUserNameState(onlineVisitor.getUsername());
//                for (VistorLoginLogInfo loginLogInfo : list) {
//                    if (vistorLoginLogInfo!=null) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String date1= sdf.format(vistorLoginLogInfo.getCreateTime());
//                        String date2=sdf.format(loginLogInfo.getCreateTime());
//
//                        if (date1.equals(date2) && vistorLoginLogInfo.getVistorName().equals(loginLogInfo.getVistorName())) {
//                            loginLogInfo.setState("在线");
//                        }else {
//                            loginLogInfo.setState("离线");
//                        }
//                    }
//                }
//            }
            int total = vistorLoginLogInfoMapper.selectVistorLogCount(form);

            map.put("rows", list);
            map.put("total", total);
            return map;
//        }else {
//            List<VistorLoginLogInfo> list=  vistorLoginLogInfoMapper.selectAllVistorLoginLogList();
//            List<OnlineVisitor> onlineVisitors = onlineVisitorMapper.selectAllOnlineMember();
//            for (OnlineVisitor onlineVisitor : onlineVisitors) {
//                VistorLoginLogInfo vistorLoginLogInfo = vistorLoginLogInfoMapper.getUserNameState(onlineVisitor.getUsername());
//                for (VistorLoginLogInfo loginLogInfo : list) {
//                    if (vistorLoginLogInfo!=null) {
//                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        String date1= sdf.format(vistorLoginLogInfo.getCreateTime());
//                        String date2=sdf.format(loginLogInfo.getCreateTime());
//
//                        if (date1.equals(date2) && vistorLoginLogInfo.getVistorName().equals(loginLogInfo.getVistorName())) {
//                            loginLogInfo.setState("在线");
//                        }else {
//                            loginLogInfo.setState("离线");
//                        }
//                    }
//                }
//            }
//            List<VistorLoginLogInfo>  onLine=new ArrayList();
//            List<VistorLoginLogInfo>  offLine=new ArrayList();
//            for (VistorLoginLogInfo loginLogInfo :  list){
//                if ("在线".equals(loginLogInfo.getState())){
//                    onLine.add(loginLogInfo);
//                }else {
//                    offLine.add(loginLogInfo);
//                }
//            }
//            if ("在线".equals(form.getState())){
//                map.put("total",onLine.size());
//                if (onLine.size()<form.getPageData().getPage()*form.getPageData().getRows()){
//                    onLine=onLine.subList((form.getPageData().getPage()-1)*form.getPageData().getRows(),onLine.size());
//
//                }else {
//                    onLine=onLine.subList((form.getPageData().getPage()-1)*form.getPageData().getRows(),form.getPageData().getPage()*form.getPageData().getRows());
//
//                }
//
////                onLine=onLine.subList((form.getPageData().getPage()-1)*form.getPageData().getRows(),form.getPageData().getPage()*form.getPageData().getRows());
//                map.put("rows",onLine);
//
//                return  map;
//            }else if("离线".equals(form.getState())){
//                map.put("total",offLine.size());
//                if (offLine.size()<form.getPageData().getPage()*form.getPageData().getRows()){
//                    offLine=offLine.subList((form.getPageData().getPage()-1)*form.getPageData().getRows(),offLine.size());
//
//                }else {
//                    offLine=offLine.subList((form.getPageData().getPage()-1)*form.getPageData().getRows(),form.getPageData().getPage()*form.getPageData().getRows());
//
//                }
//                map.put("rows",offLine);
//
//                return  map;
//            }
//        }
 //       return map;
    }


}
