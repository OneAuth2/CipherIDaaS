package com.portal.service.tcp;


import com.alibaba.fastjson.JSONObject;
import com.portal.commons.GlobalReturnCode;
import com.portal.commons.TcpServiceKey;
import com.portal.daoAuthoriza.PlatCipherUserMapDAO;
import com.portal.domain.BaseReq;
import com.portal.service.CheckAccountSafeService;
import com.portal.service.GlobalServiceInfoService;
import com.portal.service.SendTcpMsgService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * @Author: zt
 * @Date: 2019/1/5 18:49
 */
@Service
public class CheckAccountSafeServiceImpl implements CheckAccountSafeService {

    @Autowired
    private SendTcpMsgService sendTcpMsgService;

    @Autowired
    private GlobalServiceInfoService globalServiceInfoService;

    @Value("${cipher.account.safer.ip}")
    private String host;

    @Value("${cipher.account.safer.port}")
    private int port;



    @Override
    public Map<String, Object> checkAccountLinsence(String whichService,Integer platUserId) {

        //1.判断服务是否开启
        boolean isOpen = globalServiceInfoService.serviceIsOpen(whichService, platUserId);
        Map<String, Object> map = new HashMap<>();

        //服务未开启，直接默认通过
        if (!isOpen) {
            map.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode());
            map.put(GlobalReturnCode.RETURN_MSG, "服务未开启!");
            return map;
        }

//        String accountNumber=platCipherUserMapDAO.queryCipherUserByPlatUserId(platUserId);

        map.put("platUserId", platUserId);

        BaseReq baseRequest = new BaseReq(TcpServiceKey.ACCOUNT_LINSENCE_KEY, map);
        String msg = sendTcpMsgService.sendMsg(host,port,baseRequest);
        Map<String, Object> resMap = new HashMap<>();

        //userProvision 调用失败
        if (StringUtils.isEmpty(msg)) {
            resMap.put(GlobalReturnCode.RETURN_CODE, GlobalReturnCode.MsgCodeEnum.FAILURE.getCode());
            resMap.put(GlobalReturnCode.RETURN_MSG, GlobalReturnCode.MsgCodeEnum.FAILURE.getMsg());
            return resMap;
        }

        JSONObject jsonObject = JSONObject.parseObject(msg);
        int code = jsonObject.getInteger("code");
        String resMsg = jsonObject.getString("msg");

        //失败
        if (code != GlobalReturnCode.MsgCodeEnum.SUCCESS.getCode()) {
            resMap.put(GlobalReturnCode.RETURN_CODE, code);
            resMap.put(GlobalReturnCode.RETURN_MSG, resMsg);
            return resMap;
        }

        //成功
        String tokenJson = jsonObject.getString("content");
        String token=JSONObject.parseObject(tokenJson).getString("token");
        resMap.put(GlobalReturnCode.RETURN_CODE, code);
        resMap.put(GlobalReturnCode.RETURN_MSG, resMsg);
        resMap.put("token", token);
        return resMap;
    }



}
