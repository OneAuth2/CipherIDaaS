package com.portal.utils;

import java.util.UUID;

/**
 * @Author: TK
 * @Date: 2019/7/2 17:34
 */
public class TicketUtil {

    public static String getCasSTTicket(){
        UUID uuidStr = UUID.randomUUID();
        String s = uuidStr.toString().toLowerCase().replaceAll("-", "");
        s="ST-"+s;
        return s;
    }

    public static String getNcSsoKey(){
        UUID uuidStr = UUID.randomUUID();
        String s = uuidStr.toString().toLowerCase().replaceAll("-", "");
        s="SF-"+s;
        return s;
    }

    public static void main(String... args){
        System.out.println(getNcSsoKey());
    }


}
