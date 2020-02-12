package com.jms.consumer;

import com.alibaba.fastjson.JSON;
import com.config.Constant;
import com.config.URLConstant;
import com.dingtalk.api.DefaultDingTalkClient;
import com.dingtalk.api.request.OapiGettokenRequest;
import com.dingtalk.api.response.OapiGettokenResponse;
import com.dingtalk.api.response.OapiMessageCorpconversationAsyncsendV2Response;
import com.model.DingPushAuthInfoMessage;
import com.service.CipherAccountDingBindService;
import com.service.CipherDingConfigInfoService;
import com.service.CipherVpnConfigurationService;
import com.service.impl.DingOAPIServiceImpl;
import com.util.AccessTokenUtil;
import com.util.AddressIp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author: zt
 * @Date: 2019-08-09 12:00
 */

@Component
public class DingPushAuthConsumer {

    private static final Logger LOGGER = LoggerFactory.getLogger(DingPushAuthConsumer.class);
    @Resource
    private DingOAPIServiceImpl dingOAPIService;
    @Resource
    private CipherAccountDingBindService cipherAccountDingBindService;
    @Resource
    private CipherDingConfigInfoService cipherDingConfigInfoService;
    @Resource
    private CipherVpnConfigurationService cipherVpnConfigurationService;

    @JmsListener(destination = "cipher.vpn.auth.code")
    public void reciveQue(String message) {

        DingPushAuthInfoMessage dingPushAuthInfoMessage = JSON.parseObject(message, DingPushAuthInfoMessage.class);

        String uuid = dingPushAuthInfoMessage.getUuid();
        if (StringUtils.isEmpty(uuid)) {
            return;
        }
        //获取钉钉id
        String dingId = cipherAccountDingBindService.getDingIdByUserId(uuid);
        if (StringUtils.isEmpty(dingId)) {
            return;
        }
        //获取公司id
        String companyId = cipherAccountDingBindService.getCompanyIdByUserId(uuid);
        if (StringUtils.isEmpty(companyId)) {
            return;
        }
        //获取钉钉公司id
        String corpId = cipherDingConfigInfoService.getCorpIdByCompanyId(companyId);
        if (StringUtils.isEmpty(corpId)) {
            return;
        }
        //获取企业信息
        Long agentId = Constant.AGENTID;
        //获取访问凭证
        String accessToken = AccessTokenUtil.getToken();
        //设置消息接收人
        List<String> userIdList = new ArrayList<>();
        userIdList.add(dingId);
        //页面参数
        String ip = dingPushAuthInfoMessage.getVpnIp();
        String deviceName = dingPushAuthInfoMessage.getDeviceName();
        String address = "";
        Long timeStamp = dingPushAuthInfoMessage.getCurrentTimeStamp();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String loginDate = sdf.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        SimpleDateFormat sdf2 = new SimpleDateFormat(" HH:mm:ss");
        String loginTime = sdf2.format(new Date(Long.parseLong(String.valueOf(timeStamp))));
        //发送一个带有参数的链接消息。客户端打开应用可以拿到参数
        String url = "eapp://page/push/push?deviceName=" + deviceName + "&ip=" +
                ip + "&address=" + address + "&loginTime=" + loginTime + "&loginDate=" + loginDate + "&timeStamp=" + timeStamp + "";
        OapiMessageCorpconversationAsyncsendV2Response response = dingOAPIService.sendLinkMessage(agentId, userIdList, url, accessToken);

    }
}
