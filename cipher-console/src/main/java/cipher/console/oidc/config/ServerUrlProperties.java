package cipher.console.oidc.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;


@Configuration
@PropertySource("classpath:config/ServerCallBack.properties")
@ConfigurationProperties(prefix = "serverurl", ignoreUnknownFields = false)
@Component
public class ServerUrlProperties {

   private String urlPre;

   private String secret;

   private String callBack;

   private String loginUUID;

    public String getUrlPre() {
        return urlPre;
    }

    public void setUrlPre(String urlPre) {
        this.urlPre = urlPre;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getCallBack() {
        return callBack;
    }

    public void setCallBack(String callBack) {
        this.callBack = callBack;
    }

    public String getLoginUUID() {
        return loginUUID;
    }

    public void setLoginUUID(String loginUUID) {
        this.loginUUID = loginUUID;
    }
}
