package com.portal.service;
import java.util.Map;

/**
 * Created by 95744 on 2018/8/30.
 */
public interface SendMsgService {

    public Map<String,Object> sendkapataMsg(String email,int type,String username);

    public Map<String,Object> getkapataMsg(String email,int type,String kapataCode,String username);
}
