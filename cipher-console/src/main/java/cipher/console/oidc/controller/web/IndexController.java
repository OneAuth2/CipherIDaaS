package cipher.console.oidc.controller.web;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: zt
 * @Date: 2018/5/28 20:43
 */
@Controller
@RequestMapping(value = "/cipher")
@EnableAutoConfiguration
public class IndexController {

    @RequestMapping(value = "/index")
    public String indexPage(){
        return "index/welcome";
    }

    @RequestMapping(value = "/welcome")
    public String index() {
        return "index/index";
    }

}
