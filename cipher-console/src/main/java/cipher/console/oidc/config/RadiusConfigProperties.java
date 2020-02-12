package cipher.console.oidc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * @Author: zt
 * @Date: 2018/7/25 15:26
 */

@Configuration
@ConfigurationProperties(prefix = "totp", ignoreUnknownFields = false)
@PropertySource("classpath:config/RadiusConfig.properties")
@Component
public class RadiusConfigProperties {

    //提供totp生成功能的服务器地址
    private String requestUrl;

    public String getRequestUrl() {
        return requestUrl;
    }

    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

}
