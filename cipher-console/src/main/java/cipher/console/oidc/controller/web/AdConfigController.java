package cipher.console.oidc.controller.web;

import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.model.AdMap2LocalConfigModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.apache.catalina.servlet4preview.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/11/14 17:46
 */
@Controller
@RequestMapping(value = "/cipher")
public class AdConfigController {

    /**
     * AD域配置
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/ldap/adconfig")
    public String pageList(HttpServletRequest request, HttpServletResponse response) {
        return "adconfig/list_xiangdao";
    }


    /**
     * 添加AD配置
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/ldap/add_test", method = RequestMethod.POST)
    @ResponseBody
    public void add(HttpServletRequest request, HttpServletResponse response, AdInfoDomain adInfoDomain) {
        System.err.println("进入添加Ad配置");
        List<AdMap2LocalConfigModel> list = adInfoDomain.getAdMap2LocalDomainList();
        String kvConfig = new Gson().toJson(list);

        System.err.println(kvConfig);
    }


}
