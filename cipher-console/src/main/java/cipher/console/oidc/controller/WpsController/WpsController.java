package cipher.console.oidc.controller.WpsController;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.HttpKey;
import cipher.console.oidc.common.subapp.SecretManager;
import cipher.console.oidc.domain.subapp.WpsCommonReqDomin;
import cipher.console.oidc.domain.subapp.WpsQueryUserListDomain;
import cipher.console.oidc.domain.subapp.WpsUserDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
import cipher.console.oidc.model.UserPwdModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.service.subapp.WpsService;
import cipher.console.oidc.util.ReturnJsonCode;
import cipher.console.oidc.util.aes.AES;
import cipher.console.oidc.util.aes.AesKey;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

/**
 * @Author: zt
 * @Date: 2018/12/10 16:59
 */

@Controller
@RequestMapping(value = "/")
public class WpsController {


    private static final Logger LOGGER = LoggerFactory.getLogger(WpsController.class);

    private static WpsCommonReqDomin wpsCommonReqDomin = null;

    static {
        wpsCommonReqDomin = new WpsCommonReqDomin();
        wpsCommonReqDomin.setAccessId(SecretManager.WPS_ACCESS_ID);
        wpsCommonReqDomin.setApiPre(SecretManager.WPS_API_PRE);
        wpsCommonReqDomin.setSecret(SecretManager.WPS_SECRET_KEY);
        wpsCommonReqDomin.setAppId(SecretManager.WPS_APPID);
        wpsCommonReqDomin.setSsoApiPre(SecretManager.WPS_SESSION_API_PRE);
        wpsCommonReqDomin.setCompanyId("2");
    }

    /**
     * 接收金山云登录请求
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "login/do")
    public ModelAndView Login(@RequestParam(value = "cb") String cb, HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("金山回调地址:{}", cb);
        ModelAndView modelAndView = new ModelAndView("wps/login");
        modelAndView.addObject("cb", cb);
        return modelAndView;
    }


    @Autowired
    private WpsService wpsService;

    @Autowired
    private UserService userService;

    @Autowired
    private RadiusConfigInfoService radiusConfigInfoService;

    @Autowired
    private TotpService totpService;

    @RequestMapping(value = "login/checkpwd")
    @ResponseBody
    public Map<String, Object> checkpwd(@RequestParam(value = "userName") String userName,
                                        @RequestParam(value = "pwd") String pwd,
                                        @RequestParam(value = "totp") String totp,
                                        @RequestParam(value = "cb") String cb,
                                        HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> map = new HashMap<>();
        String companyId= ConstantsCMP.getSessionCompanyId(request);

        if (StringUtils.isBlank(userName) || StringUtils.isBlank(pwd) || StringUtils.isBlank(totp)) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "必填信息不能为空");
            return map;
        }

        String totpKey = radiusConfigInfoService.querySecretByUserName(userName);
        int createdTotp = totpService.buildTotp(totpKey);
        String sTotp = String.format("%1$06d", createdTotp);
        if (!sTotp.equals(totp)) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "totp码不正确");
            return map;
        }

        UserInfoDomain userPwdModel = userService.getUserInfo(userName);
        if (userPwdModel == null) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "该用户不存在");
            return map;
        }

       // userInfoDomain userPwdModel = userService.queryPwd(userName,companyId);

        //用户名或者密码错误
        if (!userPwdModel.getPassword().equals(AES.encryptToBase64(pwd, AesKey.AES_KEY))) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "用户名或者密码错误");
            return map;
        }


        WpsQueryUserListDomain wpsQueryUserListDomain = new WpsQueryUserListDomain();
        wpsQueryUserListDomain.setUnique_name(userName);
        wpsQueryUserListDomain.setOffset(0);
        wpsQueryUserListDomain.setLimit(-1);

        List<WpsUserDomain> wpsUserDomainList = wpsService.queryUserByConpanyId(wpsCommonReqDomin, wpsQueryUserListDomain);
        if (CollectionUtils.isEmpty(wpsUserDomainList)) {
            map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.FAILURE.getCode());
            map.put(ReturnJsonCode.RETURN_MSG, "该用户不存在");
            return map;
        }

        Map<String, Object> sessionMap = wpsService.createSession(wpsCommonReqDomin, String.valueOf(wpsUserDomainList.get(0).getUser_id()));
        String sessionRes = (String) sessionMap.get(HttpKey.RES);
        JSONObject jsonObject = JSONObject.fromObject(sessionRes);

        String wpsId = jsonObject.getString("wps_sid");
        LOGGER.info("wpsId==>" + wpsId);

        int MAXAGE = 7 * 24 * 60 * 60;

        Cookie cookie = new Cookie("wps_sid", wpsId);
        cookie.setDomain("wps.ciphersso.cn");
        cookie.setPath("/");
        cookie.setMaxAge(MAXAGE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);

        map.put(ReturnJsonCode.RETURN_CODE, ReturnJsonCode.MsgCodeEnum.SUCCESS.getCode());
        map.put(ReturnJsonCode.RETURN_MSG, "认证成功");
        map.put("href", cb);
        return map;
    }


    /**
     * wps单点登录接口待实现
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/sso", method = RequestMethod.GET)
    public String wpsSSOHandle(@RequestParam(value = "userName") String userName, @RequestParam(value = "timestamp") long timestamp, HttpServletRequest request, HttpServletResponse response) {
        System.out.println("sso参数:" + request.getQueryString());

        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-type", "text/html;charset=UTF-8");

        if (System.currentTimeMillis() - timestamp > 5000) {
            try (PrintWriter out = response.getWriter()) {
                out.write("<script>alert('已过期!');</script>");
                out.flush();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //解密
        userName = AES.decryptFromBase64(userName, AesKey.AES_KEY);


        WpsQueryUserListDomain wpsQueryUserListDomain = new WpsQueryUserListDomain();
        wpsQueryUserListDomain.setUnique_name(userName);
        wpsQueryUserListDomain.setOffset(0);
        wpsQueryUserListDomain.setLimit(-1);

        List<WpsUserDomain> wpsUserDomainList = wpsService.queryUserByConpanyId(wpsCommonReqDomin, wpsQueryUserListDomain);


        response.setStatus(200);
        //用户不存在，
        if (CollectionUtils.isEmpty(wpsUserDomainList)) {
            try (PrintWriter out = response.getWriter()) {
                out.write("<script>alert('该用户不存在!');</script>");
                out.flush();
                return null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Map<String, Object> sessionMap = wpsService.createSession(wpsCommonReqDomin, String.valueOf(wpsUserDomainList.get(0).getUser_id()));
        String sessionRes = (String) sessionMap.get(HttpKey.RES);
        JSONObject jsonObject = JSONObject.fromObject(sessionRes);

        String wpsId = jsonObject.getString("wps_sid");
        LOGGER.info("wpsId==>" + wpsId);

        int MAXAGE = 7 * 24 * 60 * 60;
        Cookie cookie = new Cookie("wps_sid", wpsId);
        cookie.setDomain("wps.ciphersso.cn");
        cookie.setPath("/");
        cookie.setMaxAge(MAXAGE);
        cookie.setHttpOnly(true);
        response.addCookie(cookie);


        return "redirect:http://wps.ciphersso.cn/cts";
    }

    /**
     * 金山云用户退出接口
     *
     * @param request
     * @param response
     */
    @RequestMapping(value = "/logout/do")
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        LOGGER.info("进入wps用户退出");
        try {
            Cookie killMyCookie = new Cookie("wps_sid", null);
            killMyCookie.setDomain("wps.ciphersso.cn");
            killMyCookie.setMaxAge(0);
            killMyCookie.setPath("/");
            killMyCookie.setHttpOnly(true);
            response.addCookie(killMyCookie);
            response.sendRedirect("http://wps.ciphersso.cn");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
