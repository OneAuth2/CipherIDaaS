package cipher.console.oidc.controller.web;


import cipher.console.oidc.domain.web.CustomPropertiesInfo;

import cipher.console.oidc.service.CustomPropertiesInfoService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.HttpRequest;
import io.swagger.models.auth.In;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
@RequestMapping(value = "/cipher/customproperties")
@EnableAutoConfiguration
public class CustomPropertiesController {

    @Autowired
    private CustomPropertiesInfoService customPropertiesInfoService;

    @Autowired
    private UserService userService;


    @RequestMapping("/set")
    public String index(){
        return "customproperties/set";
    }


    @RequestMapping(value = "/init",method = RequestMethod.GET)
    @ResponseBody
    public Map<String,Object> getInitData(){
        Integer companyId=4;
        CustomPropertiesInfo record = new CustomPropertiesInfo();
        record.setCompanyId(companyId);
        record.setState(0);
        List<CustomPropertiesInfo> initData = customPropertiesInfoService.getInitData(record);
        HashMap<String, Object> map = new HashMap<>();
        ArrayList<String> list = new ArrayList<>();
        for (CustomPropertiesInfo c: initData) {
           list.add(c.getCustomValue());
        }
        for(int i=1;i<list.size()+1;i++){
            map.put("properties"+i,list.get(i-1));
        }
        return map;
    }

    @RequestMapping(value = "/save",method = RequestMethod.POST)
    @ResponseBody
    public  Map<String,Integer>saveProperties(String prop){
        Integer companyId=4;
        HashMap<String, Integer> map = new HashMap<>();
        if(prop.trim().isEmpty()||prop.indexOf(' ')!=-1){
            map.put("msg",4);
            return map;
        }
        if(!prop.matches("[A-Za-z_]*")){
            map.put("msg",1);
            return map;
        }
        if(prop.length()>30){
            map.put("msg",2);
            return map;
        }
        List<String> list = userService.getUserTableCustomInfo();
        System.out.println("-----"+list);
        for (String s:list) {
            if(s.equals(prop)){
                map.put("msg",3);
                return map;
            }
        }
        CustomPropertiesInfo record = new CustomPropertiesInfo();
        record.setCustomValue(prop);
        record.setCompanyId(companyId);
        record.setState(0);
        customPropertiesInfoService.saveProperties(record);
        userService.addCustomProperties(record);
        return null;

    }


    @RequestMapping(value = "/close",method = RequestMethod.POST)
    @ResponseBody
    public void closeCustomProperties(String prop){
        Integer companyId=4;
        CustomPropertiesInfo cp = new CustomPropertiesInfo();
        cp.setState(1);
        cp.setCompanyId(companyId);
        cp.setCustomValue(prop);
        cp.setModifyTime(new Date());
        customPropertiesInfoService.closeCustomProperties(cp);
    }
}


