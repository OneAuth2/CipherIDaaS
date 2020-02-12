package cipher.console.oidc.controller.gold_mantis;

import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.gold_mantis.GoldMantisUserLog;
import cipher.console.oidc.service.GoldMantisLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2018/9/18 9:59
 */
@Controller
@RequestMapping(value = "/cipher/goldmantis")
public class GoldMantisLogController {

    @Autowired
    private GoldMantisLogService goldMantisLogService;


    @RequestMapping(value = "/list")
    public String page(HttpServletRequest request, HttpServletResponse response) {
        return "goldmantislog/list";
    }


    @RequestMapping(value = "/list",params = "json",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> listJson(GoldMantisUserLog form, DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response) {
        try {
            return goldMantisLogService.queryPageList(form, pageModel);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
