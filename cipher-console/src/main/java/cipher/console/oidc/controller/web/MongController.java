package cipher.console.oidc.controller.web;


import cipher.console.oidc.domain.web.ApplicationInfoDomain;
import cipher.console.oidc.mongodb.domain.Authorities;
import cipher.console.oidc.mongodb.domain.ClientDetail;
import cipher.console.oidc.service.AppService;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping(value = "/cipher/mongo")
@EnableAutoConfiguration
public class MongController {


    private static boolean isOne;

    static {
        isOne = true;
    }

    @Autowired
    private AppService appService;

    @Autowired
    //private MongoOperations mongoTemplate;

    private static final Logger LOGGER= LoggerFactory.getLogger(MongController.class);

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    @ResponseBody
    public boolean insertMong() {
        if (isOne) {
            isOne = false;
            LOGGER.info("mongodb插入数据");
            List<ApplicationInfoDomain> applist = appService.queryAllApplicationNameAndId();
            for (ApplicationInfoDomain applicationInfoDomain : applist) {
                ClientDetail dd = new ClientDetail();
                dd.set_class("com.auth.oauth2.clientdetails.WXBaseClientDetails");
                dd.setClientId(applicationInfoDomain.getApplicationId());
                dd.setClientSecret(applicationInfoDomain.getApplicationSecrect());
                List<String> scopeList = new ArrayList<>();
                scopeList.add("openid");
                scopeList.add("read");
                List<String> authList = new ArrayList<>();
                authList.add("authorization_code");
                authList.add("client_credentials");
                authList.add("refresh_token");
                authList.add("password");
                List<Authorities> authoritiesList = new ArrayList<>();
                Authorities authorities = new Authorities();
                authorities.set_class("org.springframework.security.core.authority.SimpleGrantedAuthority");
                authorities.setRole("ROLE_USER");
                authoritiesList.add(authorities);
                dd.setScope(scopeList);
                dd.setAuthorizedGrantTypes(authList);
                dd.setAuthorities(authoritiesList);
              //  mongoTemplate.save(dd);
            }
            ClientDetail dd = new ClientDetail();
            dd.set_class("com.auth.oauth2.clientdetails.WXBaseClientDetails");
            dd.setClientId("oidc");
            dd.setClientSecret("oidcsole");
            List<String> scopeList = new ArrayList<>();
            scopeList.add("openid");
            scopeList.add("read");
            List<String> authList = new ArrayList<>();
            authList.add("authorization_code");
            authList.add("client_credentials");
            authList.add("refresh_token");
            authList.add("password");
            List<Authorities> authoritiesList = new ArrayList<>();
            Authorities authorities = new Authorities();
            authorities.set_class("org.springframework.security.core.authority.SimpleGrantedAuthority");
            authorities.setRole("ROLE_USER");
            authoritiesList.add(authorities);
            dd.setScope(scopeList);
            dd.setAuthorizedGrantTypes(authList);
            dd.setAuthorities(authoritiesList);
            //mongoTemplate.save(dd);
        }

        return isOne;
    }
}
