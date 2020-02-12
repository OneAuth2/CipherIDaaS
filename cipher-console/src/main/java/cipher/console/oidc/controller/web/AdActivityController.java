package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AdActivityDomain;
import cipher.console.oidc.domain.web.AdInfoDomain;
import cipher.console.oidc.service.AdActivityService;
import cipher.console.oidc.service.AdInfoService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
 * @Author: zt
 * @Date: 2018/10/24 19:37
 */

@Controller
@RequestMapping(value = "/cipher/aduserbuffer")
public class AdActivityController {


    @Autowired
    private AdActivityService adActivityService;

    @Autowired
    private AdInfoService adInfoService;


    private static final Logger LOGGER = LoggerFactory.getLogger(AdActivityController.class);


    /**
     * @param id            AD配置信息的ID
     * @return              查询结果
     * */
    @RequestMapping(value = "/list")
    @ResponseBody
    public Map<String,Object> list(HttpServletRequest request, @RequestParam(value = "id",required = false) Integer id) {
        LOGGER.info("id=" + id);
        if (id == null) {
            LOGGER.info("id为空，设置默认值为2");
            id = 2;
        }

        Map<String,Object> map = new HashMap<>();
        map.put("id",id);

        return map;
    }


    @RequestMapping(value = "/submit")
    @ResponseBody
    public void submit(@RequestParam("ids") List<Integer> ids, HttpServletRequest request) {
        for (Integer id : ids) {
            System.err.println(id);
        }

    }


    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(@RequestParam(value = "adId") Integer adId, HttpServletRequest request, DataGridModel model, AdActivityDomain adActivityDomain) {
        AdInfoDomain adInfoDomain=adInfoService.queryAdInfoById(adId);
        adActivityDomain.setSource(adInfoDomain.getIp());
        String companyId= ConstantsCMP.getSessionCompanyId(request);
        adActivityDomain.setCompanyId(companyId);
        return adActivityService.getAllUserBufferList(adActivityDomain, model);
    }




    @RequestMapping(value = "/info", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> info(HttpServletRequest request, HttpServletResponse response, @RequestParam("accountNumber") String accountNumber) {
        return adActivityService.getUserInfo(accountNumber);
    }
}
