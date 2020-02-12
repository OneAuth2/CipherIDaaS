package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.WifeLogExcle;
import cipher.console.oidc.domain.web.OnlineVisitor;
import cipher.console.oidc.domain.web.VistorLoginLogInfo;
import cipher.console.oidc.domain.web.WifiActionMessage;
import cipher.console.oidc.mapper.OnlineVisitorMapper;
import cipher.console.oidc.mapper.VistorLoginLogInfoMapper;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.VisitorLoginLogInfoService;
import cipher.console.oidc.service.VistorInfoService;
import cipher.console.oidc.service.WifiOffLineService;
import cipher.console.oidc.util.FileUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 95744 on 2018/9/26.
 */

@Controller
@RequestMapping(value = "/cipher/vistorLoginLog")
@EnableAutoConfiguration
public class VistorLoginLogController {


    @Autowired
    private VistorLoginLogInfoMapper vistorLoginLogInfoMapper;

    @Autowired
    private OnlineVisitorMapper onlineVisitorMapper;

    @Autowired
    private SessionService sessionService;

    private static final Logger logger = LoggerFactory.getLogger(VistorLoginLogController.class.getSimpleName());

    @Autowired
    private VisitorLoginLogInfoService visitorLoginLogInfoService;

    @Autowired
    private WifiOffLineService wifiOffLineService;

    @Autowired
    private VistorInfoService vistorInfoService;


    @RequestMapping(value = "/list")
    public String appPage() {
        return "vistorLoginLog/list";
    }



    @RequestMapping(value = "/list", params = "json")
    @ResponseBody
    public Map<String, Object> queryData(HttpServletRequest request, DataGridModel pageModel, VistorLoginLogInfo form) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        logger.debug("Enter VistorLoginLogController.queryData");
        form.setCompanyId(companyId);
        return visitorLoginLogInfoService.getVistorLoginLogList(form, pageModel);
    }

    /**
     * create by 田扛
     * create time 2019年3月14日14:03:23
     * 下载日志报表，导出日志报表
     */
    @RequestMapping(value = "/downloadExcelLog")
    @ResponseBody
    public void downloadExcelLog(VistorLoginLogInfo vistorLoginLogInfo,HttpServletResponse response,HttpServletRequest request) {
        String companyId = sessionService.getCompanyUUid(request.getSession());
        vistorLoginLogInfo.setCompanyId(companyId);
        List<WifeLogExcle> list = vistorLoginLogInfoMapper.downloadExcelLog(vistorLoginLogInfo);
//        List<OnlineVisitor> onlineVisitors = onlineVisitorMapper.selectAllOnlineMember();
//
//        for (OnlineVisitor onlineVisitor : onlineVisitors) {
//            VistorLoginLogInfo vistorLoginLogInfo = vistorLoginLogInfoMapper.getUserNameState(onlineVisitor.getUsername());
//            for (WifeLogExcle loginLogInfo : list) {
//                if (vistorLoginLogInfo!=null) {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String date1= sdf.format(vistorLoginLogInfo.getCreateTime());
//                    String date2=loginLogInfo.getCreateTime();
//
//                    if (date1.equals(date2) && vistorLoginLogInfo.getVistorName().equals(loginLogInfo.getVistorName())) {
//                        loginLogInfo.setState("在线");
//                    }
//                }
//            }
//        }
        FileUtil.exportExcel(list,"无线日志审计表","", WifeLogExcle.class,"无线日志审计表.xls",response);
    }



    /*
    * 访客下线操作
    * */

    @RequestMapping(value = "/logout", params = "json")
    @ResponseBody
    public Map<String, Object> onlineMemberLogout(HttpServletResponse response,
                                                  @RequestParam(value = "ip",required = true)String ip,
                                                  @RequestParam(value = "name",required = true)String name) {

        Map<String, Object> map = new HashMap<>();
        try {
            WifiActionMessage wifiActionMessage = new WifiActionMessage();
            wifiActionMessage.setIp(ip);
            wifiActionMessage.setUserName(name);
            wifiOffLineService.sendWifiOffLineMsg(wifiActionMessage);
            OnlineVisitor onlineVisitor=new OnlineVisitor();
            onlineVisitor.setIp(ip);
            vistorInfoService.deleteVistor(onlineVisitor);
            map.put("return_msg", "下线成功");
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            System.out.println(map);
        }catch (Exception e){
            e.printStackTrace();
            map.put("return_msg", "服务器错误");
            map.put("return_code", ConstantsCMP.Code.FAIL);
        }
        return map;
    }

}
