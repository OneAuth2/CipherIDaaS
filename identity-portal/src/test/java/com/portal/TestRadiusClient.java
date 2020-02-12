package com.portal;

import com.alibaba.fastjson.support.odps.udf.CodecCheck;
import org.tinyradius.packet.AccessRequest;
import org.tinyradius.util.RadiusClient;
import org.tinyradius.util.RadiusException;

import java.io.IOException;

/**
 * @Author: zt
 * @Date: 2019-08-07 14:51
 */
public class TestRadiusClient {

    public static void main(String[] args) {
        RadiusClient radiusClient=new RadiusClient("127.0.0.1","123456");
        try {
//            radiusClient.setAuthProtocol(AccessRequest.AUTH_PAP);
//            radiusClient.authenticate("test","123456");
            AccessRequest ar=new AccessRequest("admin","123456");
            ar.setAuthProtocol("pap");
            ar.addAttribute("NAS-Identifier", "this.is.my.nas-identifier.de");
            ar.addAttribute("NAS-IP-Address", "192.168.0.100");
            ar.addAttribute("Service-Type", "Login-User");
            ar.addAttribute("WISPr-Redirection-URL", "http://www.sourceforge.net/");
            ar.addAttribute("WISPr-Location-ID", "net.sourceforge.ap1");

            radiusClient.authenticate(ar);


        } catch (IOException e) {
            e.printStackTrace();
        } catch (RadiusException e) {
            e.printStackTrace();
        }
    }

}
