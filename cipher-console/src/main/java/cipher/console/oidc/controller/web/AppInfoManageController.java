package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.Constants;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AppManageinfo;
import cipher.console.oidc.domain.web.WifiPortalWebInfo;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AppManageInfoService;
import cipher.console.oidc.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 *
 * */
@Controller
@RequestMapping("/cipher/appversioninfo")
@EnableAutoConfiguration

public class AppInfoManageController {
    @Autowired
    private AppManageInfoService appManageInfoService;

    AppManageinfo appManageinfo;

    @Autowired
    private RedisClient redisClient;
    @RequestMapping(value = "/list",method = RequestMethod.GET )
    public String list(Model model){
        return "appversioninfo/list";
    }

    /*@RequestMapping(value = "/list",method = RequestMethod.POST )
    @ResponseBody
    public List<AppManageinfo> list(){
        return appManageInfoService.queryallinfo();
    }*/

    @RequestMapping(value = "/list",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> list(AppManageinfo appManageinfo,DataGridModel pageModel){
        return appManageInfoService.getAppVersionInfoList(appManageinfo,pageModel);
    }


    @RequestMapping(value = "/updateinfo")
    @ResponseBody
    public Map<String,Object> update(@RequestParam(value = "id") Integer id){
        //FIXME:FIXED-诗昭
        AppManageinfo appManageinfo=appManageInfoService.queryInfoById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("appManageinfo",appManageinfo);
        return map;
    }

    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletResponse response, HttpServletRequest request, AppManageinfo appManageinfo){
        if(null==appManageinfo.getId()){
            appManageInfoService.insertAppinfo(appManageinfo);
            ResponseUtils.customSuccessResponse(response, "添加AppVersion设置成功！");
        }else {
            int ret = appManageInfoService.updateAppInfoId(appManageinfo);
            ResponseUtils.customSuccessResponse(response, "修改AppVersion设置成功！");
        }
    }



    @RequestMapping(value = "/show",method = RequestMethod.GET )
    @ResponseBody
    public Map<String,Object> show(Integer id){
        //FIXME:FIXED-诗昭
        AppManageinfo appManageinfo=appManageInfoService.queryInfoById(id);
        Map<String,Object> map = new HashMap<>();
        map.put("appManageinfo",appManageinfo);
        return map;
    }



    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public void delete(HttpServletResponse response, HttpServletRequest request,Integer id) {
        try {
            if (null!= id) {
                appManageInfoService.deleteInfoById(id);
                ResponseUtils.customSuccessResponse(response, "删除成功！");
            }
        }catch(Exception e){
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }




}
