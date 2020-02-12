package cipher.console.oidc.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import javax.servlet.MultipartConfigElement;

/**
 * @Author: zt
 * @Date: 2018/6/1 20:45
 */
@Configuration
@ConfigurationProperties(prefix = "img", ignoreUnknownFields = false)
@PropertySource("classpath:config/imgconfig.properties")
@Component
public class ImgProperties {



    private String serverHost;

    private String saveDir;

    private String accessAddress;

    public String getServerHost() {
        return serverHost;
    }

    public void setServerHost(String serverHost) {
        this.serverHost = serverHost;
    }

    public String getSaveDir() {
        return saveDir;
    }

    public void setSaveDir(String saveDir) {
        this.saveDir = saveDir;
    }

    public String getAccessAddress() {
        return accessAddress;
    }

    public void setAccessAddress(String accessAddress) {
        this.accessAddress = accessAddress;
    }



  /*  @Bean
    public MultipartConfigElement multipartConfigElement() {
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation("/data/tmp");//指定临时文件路径，这个路径可以随便写
        return factory.createMultipartConfig();
    }*/
}
