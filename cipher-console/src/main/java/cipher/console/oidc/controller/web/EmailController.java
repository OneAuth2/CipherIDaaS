package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.EmailInfoDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.EmailService;
import cipher.console.oidc.util.EmailSendTool;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.collections.map.HashedMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Map;

/**
 * Created by 95744 on 2018/8/23.
 */

@Controller
@RequestMapping(value = "/cipher/email")
@EnableAutoConfiguration
public class EmailController {

    @Autowired
    private EmailService emailService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    private static final Logger logger = LoggerFactory.getLogger(EmailController.class);


    /**
     * 查询已有的信息
     *
     * @param response
     * @return
     */
    @RequestMapping(value = "/list", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public EmailInfoDomain queryDataInfo(HttpServletResponse response) {
        logger.debug("enter EmailController.queryDataInfo");
        EmailInfoDomain emailInfoDomain=new EmailInfoDomain();
        return emailService.getEmailInfo(emailInfoDomain);
    }


    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void insertEmailInfo(HttpServletResponse response, HttpSession session, HttpServletRequest request, EmailInfoDomain form) {
        logger.debug("enter EmailController.insertEmailInfo");
        EmailInfoDomain emailInfoDomain =  emailService.getEmailInfo(form);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(),"添加或修改短信配置信息");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        try {
        if (ObjectUtils.isEmpty(emailInfoDomain)) {
            emailService.insertEmailInfo(form);
            ResponseUtils.customSuccessResponse(response, "保存配置信息成功");
        }
            emailService.updateEmailInfo(form);
            ResponseUtils.customSuccessResponse(response, "更新配置信息成功");
        } catch (Exception e) {
            logger.error("enter EmailController.insertEmailInfo; Error:" + e.getCause());
            ResponseUtils.customFailueResponse(response, "更新配置失败，请稍后重试");
            e.printStackTrace();
        }
    }







    @RequestMapping(value = "/sendMsg", method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> sendMsg(HttpServletResponse response,EmailInfoDomain form) {
        logger.debug("enter EmailController.sendMsg");
        Map<String,Object> map=new HashedMap();
      /*  EmailInfoDomain emailInfoDomain=new EmailInfoDomain();
        emailInfoDomain= emailService.getEmailInfo(emailInfoDomain);
        emailInfoDomain.setEmailAddress(emailAddress);*/

       /* EmailSendTool sendEmail = new EmailSendTool(emailInfoDomain.getSmtp(),
                emailInfoDomain.getAccount(), emailInfoDomain.getPwd(), emailAddress,
                "主题", "邮箱的验证码为:"+ NumberUtil.createData(6), "发送人名称", "", "");
        try {
            sendEmail.send();
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        try {
            EmailSendTool.sendTenxungEmail(form);
            map.put("return_code",1);
            map.put("msg","邮件发送成功");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            map.put("return_code",0);
            map.put("msg","邮件发送失败");
        }catch (Exception e){
            map.put("return_code",0);
            map.put("msg","邮件发送失败");
            e.printStackTrace();
        }
        return map;
    }
    }



