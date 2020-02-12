package cipher.console.oidc.filter;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.util.aes.AES;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashSet;
import java.util.Set;


@Component
public class OidcSysInterceptor implements HandlerInterceptor {

    @Autowired
    private RedisClient redisClient;

    private static final Logger logger = LoggerFactory.getLogger(OidcSysInterceptor.class);

    private static Set<String> uriSet = new HashSet<>();


    static {
        uriSet.add("/callBack");
        //金山云登录
        uriSet.add("/login/do");
        //金山云验证密码
        uriSet.add("/login/checkpwd");
        uriSet.add("/cipher/userbehavior/exportExcle");
        uriSet.add("/cipher/adminbehavior/exportExcle");
        uriSet.add("/cipher/download/subexport");
        uriSet.add("/cipher/subaccount/upload");
        uriSet.add("/cipher/vistorLoginLog/downloadExcelLog");
        uriSet.add("/cipher/subaccount/downloadReturnExcel");
        uriSet.add("/cipher/applyAudit/exportExcle");
        uriSet.add("/cipher/app/imgUpload");
        uriSet.add("/cipher/newImportbak/userexport");
        //金山云单点登录
        uriSet.add("/sso/");
        uriSet.add("/sso");
        //金山云注销登录
        uriSet.add("/logout/do");
        uriSet.add("/cipher/checkpwd");
        uriSet.add("/cipher/ssoLogin/createToeken");
        uriSet.add("/cipher/ssoLogin/checkToken");

        uriSet.add("/cipher/welcome/userInfo");
        uriSet.add("/cipher/menu/getAllMenuList");
        uriSet.add("/cipher/welcome/draw");
        uriSet.add("/cipher/console/setSession");
        uriSet.add("/cipher/newImportbak/import");
        uriSet.add("/cipher/loginOut");
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        if (uriSet.contains(httpServletRequest.getRequestURI())) {
            return true;
        }
        HttpSession session = httpServletRequest.getSession();
        String userssion = ConstantsCMP.getSessionUser(httpServletRequest);
        String ss = httpServletRequest.getParameter("ticket");
        if (!StringUtils.isEmpty(ss)) {
            ss = ss.replaceAll(" ", "+");
        }
        logger.info("ticket--------------------------"+ ss);
        if (!StringUtils.isEmpty(userssion) && !StringUtils.isEmpty(ss) && !userssion.equals(ss)) {
            httpServletRequest.getSession().removeAttribute(ConstantsCMP.CIPHER_OIDC_USER_SESSION_INFO);
        }

        if (null == redisClient) {
            BeanFactory factory = WebApplicationContextUtils.getRequiredWebApplicationContext(httpServletRequest.getServletContext());
            redisClient = (RedisClient) factory.getBean("redisClient");
        }

        String username = httpServletRequest.getParameter("ticket");
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        username = username.replaceAll(" ", "+");
        String name=new String(AES.decryptFromBase64(username,ConstantsCMP.AES_KEY));

        System.out.println(name + "--------------------" + name);

        if (session.getAttribute(ConstantsCMP.CIPHER_OIDC_USER_SESSION_INFO) == null) {
            session.setAttribute(ConstantsCMP.CIPHER_OIDC_USER_SESSION_INFO, name);
            session.setAttribute(ConstantsCMP.CIPHER_CONSOLE_COMPANY_SESSION_INFO,"123456");
            session.setAttribute(ConstantsCMP.CIPHER_UUID_INFO,"be22b31d6b1411e9b5af00163e00cc6a");
            return true;
        }
        userssion = ConstantsCMP.getSessionUser(httpServletRequest);
        logger.info("userssion=======================" + userssion);
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    }


}
