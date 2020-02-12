package cipher.console.oidc.controller;

import cipher.console.oidc.manager.QuartzManager;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author: zt
 * @Date: 2018/10/9 15:17
 */

@Controller
@RequestMapping(value = "/quartz")
public class QuartzTestController {

    @RequestMapping(value = "/remove")
    @ResponseBody
    public void stopJob(@RequestParam("jobKey") String jobKey, HttpServletRequest request, HttpServletResponse response){
        QuartzManager.removeJob(jobKey,jobKey);
    }

    @RequestMapping(value = "/startNow")
    @ResponseBody
    public void startJobNow(@RequestParam(value = "jobKey") String jobKey, HttpServletRequest request,HttpServletResponse response){
        QuartzManager.startNow(jobKey);
    }

    @RequestMapping(value = "/modify")
    @ResponseBody
    public void modifyTime(@RequestParam(value = "jobKey")String jobKey,HttpServletRequest request,HttpServletResponse response){
        QuartzManager.modifyTime(jobKey,"10 * * * * ?");
    }

    @RequestMapping(value = "/shutdown")
    @ResponseBody
    public void shutdown(HttpServletRequest request,HttpServletResponse response){
        QuartzManager.shutdownAllJobs();
    }


    @RequestMapping(value = "/startAllNow")
    @ResponseBody
    public String startAllNow(HttpServletRequest request,HttpServletResponse response){
        try {
            QuartzManager.startNow("synczhdepartment");
            QuartzManager.startNow("synczhpost");
            QuartzManager.startNow("synczhuser");
            return "启动所有定时任务成功";
        }catch (Exception e){
            e.printStackTrace();
            return "启动所有定时任务失败";
        }

    }

}
