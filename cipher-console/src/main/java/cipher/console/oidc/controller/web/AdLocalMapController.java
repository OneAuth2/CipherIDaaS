package cipher.console.oidc.controller.web;

import cipher.console.oidc.domain.web.AdMap2LocalDomain;

import cipher.console.oidc.model.AdMap2LocalModel;
import cipher.console.oidc.service.AdMap2LocalService;
import cipher.console.oidc.util.ResponseUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ldap.core.LdapTemplate;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;


import javax.naming.directory.SearchControls;
import javax.naming.ldap.LdapName;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;

import java.util.Iterator;
import java.util.List;
/**
 * 将AD域的字段映射到本地的属性
 *
 * @Author: zt
 * @Date: 2018/10/22 16:10
 */
@Controller
@RequestMapping(value = "/cipher/adMap2Local")
public class AdLocalMapController {

    @Autowired
    private AdMap2LocalService adMap2LocalService;

    @Autowired
    private LdapTemplate ldapTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(AdLocalMapController.class);


    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String page(HttpServletRequest request, HttpServletResponse response) {
        return "admap2local/list";
    }


    @RequestMapping(value = "/queryList",method = RequestMethod.POST)
    @ResponseBody
    public List<AdMap2LocalDomain> queryConfigList(HttpServletRequest request,HttpServletResponse response){
        return adMap2LocalService.queryAdMapLocalConfig();
    }

    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public void adMap2Local(AdMap2LocalModel adMap2LocalModel, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("enter AdLocalMapController.adMap2Local 进入AD和mysql字段映射的配置");
        if (adMap2LocalModel.getAdMap2LocalDomainList() == null || adMap2LocalModel.getAdMap2LocalDomainList().size() == 0) {
            ResponseUtils.customFailueResponse(response, "后台为接收到配置信息");
            return;
        }

        List<AdMap2LocalDomain> formConfigList = new ArrayList<>(adMap2LocalModel.getAdMap2LocalDomainList());

        List<AdMap2LocalDomain> dbMapList = adMap2LocalService.queryAdMapLocalConfig();
        try {
            //数据库里还没有配置
            if (dbMapList == null||dbMapList.size()==0) {
                adMap2LocalService.insertAdMap2LocalConfig(formConfigList);
                ResponseUtils.customSuccessResponse(response, "写入配置成功");
                return;
            }

            //所有配置均已存在，执行更新操作
            if (dbMapList.size() == formConfigList.size()) {
                for (AdMap2LocalDomain adMap2LocalDomain : formConfigList) {
                    adMap2LocalService.updateAdMapConfig(adMap2LocalDomain);
                }
                ResponseUtils.customSuccessResponse(response, "更新配置成功");
            }

            //数据库中配置条数和前端传来的配置条数不相等
            if (dbMapList.size() != formConfigList.size()) {
                ResponseUtils.customFailueResponse(response, "数据不合法");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "更新映射关系发生异常，请稍后重试！");
            return;

        }
        ResponseUtils.customSuccessResponse(response, "更新配置信息成功");
        LOGGER.info("leave from  AdLocalMapController.adMap2Local 离开AD和mysql字段映射的配置");
    }

    /**
     * @param dbAdMapConfigList 数据库中的映射配置信息
     * @param formConfigList    前端传来的字段配置信息
     * @return 更新的配置信息
     */
    private List<AdMap2LocalDomain> getTheUpdate(List<AdMap2LocalDomain> dbAdMapConfigList, List<AdMap2LocalDomain> formConfigList) {

        Iterator<AdMap2LocalDomain> iterator = formConfigList.iterator();

        for (AdMap2LocalDomain adMap2LocalDomain : dbAdMapConfigList) {
            while (iterator.hasNext()) {
                AdMap2LocalDomain formConfig = iterator.next();
                if (adMap2LocalDomain.getLocalVal().equals(formConfig.getLocalVal())) {
                    iterator.remove();
                }
            }
        }

        return formConfigList;

    }


}
