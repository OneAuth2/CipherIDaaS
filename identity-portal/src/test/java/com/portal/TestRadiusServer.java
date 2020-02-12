package com.portal;

import com.portal.radius.CipherRadius;
import org.tinyradius.util.RadiusServer;

/**
 * @Author: zt
 * @Date: 2019-08-07 14:50
 */
public class TestRadiusServer {

    public static void main(String[] args) {
        RadiusServer radiusServer=new CipherRadius();
        radiusServer.start(true,false);
    }

}
