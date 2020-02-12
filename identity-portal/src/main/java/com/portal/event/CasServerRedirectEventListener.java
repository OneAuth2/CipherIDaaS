package com.portal.event;

import com.alibaba.fastjson.JSON;

import com.portal.commons.Constants;
import com.portal.enums.ResultCode;
import com.portal.redis.RedisClient;
import com.portal.service.ApplicationService;
import com.portal.service.JudgeLimit;
import com.portal.utils.ResponseUtils;
import com.portal.utils.SessionUtils;
import com.portal.utils.TicketUtil;
import com.portal.utils.UrlUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.*;

/**
 * @Author: zt
 * @Date: 2019-07-02 14:48
 */
@Component
public class CasServerRedirectEventListener implements ApplicationListener<CasServerRedirectEvent> {



    @Autowired
    private RedisClient<String, String> redisClient;

    private Logger logger = LoggerFactory.getLogger(CasServerRedirectEventListener.class);


    @Override
    public void onApplicationEvent(CasServerRedirectEvent event) {
        HttpServletRequest request=event.getRequest();
        HttpServletResponse response=event.getResponse();
        ApplicationService applicationService=event.getApplicationService();
        JudgeLimit judgeLimit=event.getJudgeLimit();

        //获取存入session中serviceURL的地址以及名字并判断跳转地址是否存在
        String serviceUrl = (String) SessionUtils.getSessionByName(request, Constants.SERVICEURL);
        String userName= (String) SessionUtils.getSessionByName(request, Constants.USERNAME);
        if (StringUtils.isEmpty(serviceUrl)||StringUtils.isEmpty(userName)){
            ResponseUtils.sendResultMap(response,ResultCode.APPLICATION_NO_SKIP_URL);
            return;
        }

        //获取应用的id并判断该url是否在库中存在
        String appId=applicationService.getIdFromApplicationInfoByServiceUrl(serviceUrl);
        if (StringUtils.isEmpty(appId)){
            ResponseUtils.sendResultMap(response,ResultCode.APPLICATION_NO_URL);
            return;
        }

        //判断该账号是否拥有该应用的权限
        Boolean limit=judgeLimit.isOwnAppLimit(userName,appId);
        if (!limit){
            ResponseUtils.sendResultMap(response,ResultCode.USER_NO_LIMIT);
            return ;
        }

        //根据url获取的从账号
        String sonAccount = applicationService.querySubAccountDomain(serviceUrl,userName);
        //sonAccount为空直接返回错误信息
        if (sonAccount==null){
            ResponseUtils.sendResultMap(response,ResultCode.USER_NO_SUB);
            return;
        }

        //生成票据
        String ticket = TicketUtil.getCasSTTicket();
        //存入redis中
        redisClient.put(ticket, sonAccount,Constants.CAS_REDIS_TTL);
        //生成重定向地址
        String url = UrlUtils.getUrl(serviceUrl, ticket);
        PrintWriter out = null;
        try {
            //重定向到目标地址
            Map<String, Object> map = new HashMap<>();
            map.put(RETURN_CODE,Constants.AUTH_ERROR);
            map.put(SERVICE_URL, url);
            out = response.getWriter();
            String s = JSON.toJSONString(map);
            out.write(s);
            out.flush();
        } catch (IOException e) {
            //打印错误
            logger.error("System error by serviceUrl ,this url={}", url);
            logger.error(e.getMessage(), e);
            ResponseUtils.sendResultMap(response,ResultCode.PORTAL_SYSTEM_ERROR);
        }
    }


}
