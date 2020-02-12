package com.portal;

import com.portal.radius.CipherRadius;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.tinyradius.util.RadiusServer;


/**
 * @Author: zt
 * @Date: 2019-08-08 15:08
 */

@Component
public class MainRunner implements ApplicationRunner {

    @Override
    public void run(ApplicationArguments applicationArguments) throws Exception {
        RadiusServer server=new CipherRadius();
        server.start(true,false);
    }

}
