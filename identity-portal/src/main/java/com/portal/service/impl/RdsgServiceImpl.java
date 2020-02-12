package com.portal.service.impl;

import com.portal.commons.Constants;
import com.portal.domain.RdsgDomain;
import com.portal.enums.ResultCode;
import com.portal.enums.ServiceResultMap;
import com.portal.event.CasServerRedirec;
import com.portal.redis.RedisClient;
import com.portal.service.*;
import com.portal.utils.ResponseUtils;
import com.portal.utils.TicketUtil;
import com.portal.utils.UrlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.portal.commons.Constants.RETURN_CODE;
import static com.portal.commons.Constants.SUCCESS_CODE;

/**
 * @Author: TK
 * @Date: 2019/7/24 9:34
 */
@Service
public class RdsgServiceImpl implements RdsgService {

    private static Logger logger = LoggerFactory.getLogger(RdsgServiceImpl.class);

    @Autowired
    private CasAndRdsgConfigService casAndRdsgConfigService;

    @Autowired
    private JudgeLimit judgeLimit;

    @Autowired
    private ResponseRedirectService responseRedirectService;

    @Autowired
    private PortalToXDsgService portalToXDsgService;


    @Autowired
    private RedisClient<String, Object> redisClient = new RedisClient<>();


    @Override
    public void isLoggedIn(RdsgDomain rdsgDomain) {

        String rdsgUrl =casAndRdsgConfigService.getRdsgServerUrl();
        //入参校验
        if (rdsgDomain == null) {
            return;
        }

        //判断是否拥有应用的权限
        Boolean limit = judgeLimit.isOwnAppLimit(rdsgDomain.getUserId(), rdsgDomain.getAppId());
        if (!limit) {
            ResponseUtils.sendResultMap(rdsgDomain.getResponse(), ResultCode.USER_NO_LIMIT);
            return;
        }

        //判断是否有子账号  如果没有直接跳转到配置从账号界面
        if (!responseRedirectService.redirectUrl(rdsgDomain.getPlatformUrl(), rdsgDomain.getApplicationId(), rdsgDomain.getUserId(),rdsgDomain.getResponse(), rdsgDomain.getAppHost())) {
            return;
        }

        //根据从账号以及密码获取xDSg的地址
        Map<String, Object> resMap = portalToXDsgService.portalToXDsg(rdsgDomain.getResponse(), rdsgDomain.getApplicationId(), rdsgDomain.getUserId(), rdsgDomain.getCompanyUuid());
        //参数校验
        if (resMap == null) {
            Map<String, Object> map = new HashMap<>();
            map.put(RETURN_CODE, Constants.AUTH_ERROR);
            map.put(Constants.RETURN_MSG, Constants.SYS_ERROR);
            //把数据返回前端
            CasServerRedirec.responseMap(rdsgDomain.getResponse(), map);
            return;
        }

        //获取返回值的returnCode
        int returnCode = (int) resMap.get(Constants.RETURN_CODE);
        if (returnCode == Constants.ERROR_RETURN_CODE) {
            //判断从账号是否配置成功，如果失败 直接跳转到配置从账号的界面
            responseRedirectService.redirectPortalBind(rdsgDomain.getPlatformUrl(), rdsgDomain.getApplicationId(), rdsgDomain.getUserId(),rdsgDomain.getResponse(), rdsgDomain.getAppHost());
            return;
        }

        //返回值不是成功值返回报错信息
        if (returnCode != SUCCESS_CODE) {
            logger.error("enter RdsgController.getTicket() error when requst x-dsg return resultMap resultMap={{}}", resMap);
            ResponseUtils.sendResultMap(rdsgDomain.getResponse(), ServiceResultMap.SYSTEM_ERROR);
            return;
        }

        //获取url
        String url = (String) resMap.get(Constants.APPLY_URL);
        //生成票据并存入redis
        String ticket = TicketUtil.getCasSTTicket();
        //存入redis中
        redisClient.put(ticket, url);

        String realUrl=UrlUtils.getDynamicUrl(rdsgDomain.getAppHost(),rdsgUrl);
        //生成重定向地址并重定向rdsg其中携带ticket
        String rdgUrl = UrlUtils.getRDsgUrl(realUrl, ticket,url);

        try {
            rdsgDomain.getResponse().sendRedirect(rdgUrl);
        } catch (IOException e) {
            logger.error("error when sendRedirecr and rdsgUrl={{}}", realUrl);
            logger.error(e.getMessage());
            ResponseUtils.sendResultMap(rdsgDomain.getResponse(), ResultCode.PORTAL_SYSTEM_ERROR);
        }
    }

    public static void main(String... args){
        String realUrl=UrlUtils.getDynamicUrl("http://it.hengyi.com:80","http://192.168.0.154:80/sfLogin");
        System.out.println(realUrl);
    }

}
