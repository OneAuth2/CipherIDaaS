package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.service.PublishService;
import cipher.console.oidc.util.ResponseUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@Controller
@RequestMapping(value = "/cipher/applyPublish")
@EnableAutoConfiguration
public class ApplyPublishController {
    @Autowired
    private PublishService publishService;
    @RequestMapping(value = "publish")
    public String publish(){
        return "app/publish";
    }

    @RequestMapping(value = "publish",params = "json",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> publishList(ApplicationInfoDomain form, DataGridModel pageModel, HttpServletRequest request, HttpServletResponse response){
        return publishService.getPublishList(form,pageModel);
    }
    @RequestMapping(value = "publishApplication")
    public void publishApplication(String id,String releaseState,HttpServletResponse response){
        try {
            publishService.publishApplication(id,releaseState);
            if (releaseState=="1"){
                ResponseUtils.customSuccessResponse(response, "下架应用成功");
            }
            else {
                ResponseUtils.customSuccessResponse(response, "发布应用成功");
            }

        }catch (Exception e){
            if (releaseState=="1"){
                ResponseUtils.customFailueResponse(response, "下架应用失败");
            }
            else {
                ResponseUtils.customFailueResponse(response, "发布应用失败");
            }

            e.printStackTrace();
        }

    }

}
