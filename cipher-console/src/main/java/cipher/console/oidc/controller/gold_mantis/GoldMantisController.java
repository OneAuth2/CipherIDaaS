package cipher.console.oidc.controller.gold_mantis;

import cipher.console.oidc.domain.gold_mantis.GoldMantisUser;
import cipher.console.oidc.service.GoldMantisService;
import cipher.console.oidc.util.TimeUtils;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 金螳螂接口
 *
 * @Author:
 * @Date: 2018/9/17 16:26
 */
@Controller
@RequestMapping(value = "/am/rest/p/1/identity/user")
public class GoldMantisController {

    @Autowired
    private GoldMantisService goldMantisService;

    private static final Logger LOGGER = LoggerFactory.getLogger(GoldMantisController.class);

    @RequestMapping(value = "/incSync")
    @ResponseBody
    public Map<String, Object> importUser(HttpServletRequest request, HttpServletResponse response, GoldMantisUser goldMantisUser) {
        LOGGER.info("进入GoldMantisController.importUser导入用户");
        Map<String, Object> res = new HashMap<>();
        if (StringUtils.isEmpty(goldMantisUser.getAdminLoginName()) ||
                StringUtils.isEmpty(goldMantisUser.getAdminPassword()) ||
                StringUtils.isEmpty(goldMantisUser.getTenantName()) ||
                StringUtils.isEmpty(goldMantisUser.getLoginName())) {
            res.put("success", false);
            res.put("message", "必填信息不能为空");
            return res;
        }

        if (StringUtils.isNotEmpty(goldMantisUser.getNeedPasswordPolicy())) {
            if (!"true".equals(goldMantisUser.getNeedPasswordPolicy())
                    || !"false".equals(goldMantisUser.getNeedPasswordPolicy())
                    ) {
                res.put("success", false);
                res.put("message", "needPasswordPolicy只能为true或者false");
                return res;
            }
        }


        if (StringUtils.isEmpty(goldMantisUser.getMobile()) || StringUtils.isEmpty(goldMantisUser.getPassword())) {
            res.put("success", false);
            res.put("message", "手机号或密码不能为空");
            return res;
        }

        if (StringUtils.isNotEmpty(goldMantisUser.getRoleName()) && StringUtils.isNotEmpty(goldMantisUser.getRoleNames())) {
            res.put("success", false);
            res.put("message", "roleName和roleNames不能同时使用");
            return res;
        }

        if ((StringUtils.isEmpty(goldMantisUser.getStartTime()) && StringUtils.isNotEmpty(goldMantisUser.getEndTime()))
                || (StringUtils.isNotEmpty(goldMantisUser.getStartTime()) && StringUtils.isEmpty(goldMantisUser.getEndTime()))) {
            res.put("success", false);
            res.put("message", "startTime和endTime必须同时有或者同时为空");
            return res;
        }

        try {

            /**
             * 判断该手机号是否已经存在
             */
            GoldMantisUser mobileAExist = goldMantisService.queryGoldMantisUserByMobile(goldMantisUser);
            String startTime= TimeUtils.stringToDatenew(goldMantisUser.getStartTime());
            String endTime=TimeUtils.stringToDatenew(goldMantisUser.getEndTime());
            goldMantisUser.setStartTime(startTime);
            goldMantisUser.setEndTime(endTime);

            if (mobileAExist == null) {
                goldMantisService.insertGoldMantisUser(goldMantisUser);
            } else {
                goldMantisUser.setId(mobileAExist.getId());
                goldMantisService.updateGoldMantisUser(goldMantisUser);
            }
            goldMantisService.insertGoldMantisLog(goldMantisUser);

        } catch (Exception e) {
            LOGGER.error("金螳螂导入用户时发生异常:" + e.getCause() + ":" + e.getMessage());
            res.put("success", false);
            res.put("message", "写数据发生异常:" + e.getCause());
            return res;
        }

        res.put("success", true);
        res.put("message", "写入数据成功");
        return res;
    }


    @RequestMapping(value = "/incSync1",produces = "application/json; charset=utf-8")
    @ResponseBody
    public String importUserCros(HttpServletRequest request, HttpServletResponse response, GoldMantisUser goldMantisUser) {
        LOGGER.info("进入 cros版:GoldMantisController.importUser导入用户");
        String callback=request.getParameter("callback");
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> res = new HashMap<>();
        if (StringUtils.isEmpty(goldMantisUser.getAdminLoginName()) ||
                StringUtils.isEmpty(goldMantisUser.getAdminPassword()) ||
                StringUtils.isEmpty(goldMantisUser.getTenantName()) ||
                StringUtils.isEmpty(goldMantisUser.getLoginName())) {
            res.put("success", false);
            res.put("message", "必填信息不能为空");
            String jsonStr = JSONObject.toJSONString(res);
            return callback+"(" + jsonStr + ")";
        }

        if (StringUtils.isNotEmpty(goldMantisUser.getNeedPasswordPolicy())) {
            if (!"true".equals(goldMantisUser.getNeedPasswordPolicy())
                    || !"false".equals(goldMantisUser.getNeedPasswordPolicy())
                    ) {
                res.put("success", false);
                res.put("message", "needPasswordPolicy只能为true或者false");
                String jsonStr = JSONObject.toJSONString(res);
                return callback+"(" + jsonStr + ")";
            }
        }


        if (StringUtils.isEmpty(goldMantisUser.getMobile()) || StringUtils.isEmpty(goldMantisUser.getPassword())) {
            res.put("success", false);
            res.put("message", "手机号或密码不能为空");
            String jsonStr = JSONObject.toJSONString(res);
            return callback+"(" + jsonStr + ")";
        }

        if (StringUtils.isNotEmpty(goldMantisUser.getRoleName()) && StringUtils.isNotEmpty(goldMantisUser.getRoleNames())) {
            res.put("success", false);
            res.put("message", "roleName和roleNames不能同时使用");
            String jsonStr = JSONObject.toJSONString(res);
            return callback+"(" + jsonStr + ")";
        }

        if ((StringUtils.isEmpty(goldMantisUser.getStartTime()) && StringUtils.isNotEmpty(goldMantisUser.getEndTime()))
                || (StringUtils.isNotEmpty(goldMantisUser.getStartTime()) && StringUtils.isEmpty(goldMantisUser.getEndTime()))) {
            res.put("success", false);
            res.put("message", "startTime和endTime必须同时有或者同时为空");
            String jsonStr = JSONObject.toJSONString(res);
            return callback+"(" + jsonStr + ")";
        }

        try {
            /**
             * 判断该手机号是否已经存在
             */
            GoldMantisUser mobileAExist = goldMantisService.queryGoldMantisUserByMobile(goldMantisUser);
            String startTime= TimeUtils.stringToDatenew(goldMantisUser.getStartTime());
            String endTime=TimeUtils.stringToDatenew(goldMantisUser.getEndTime());
            goldMantisUser.setStartTime(startTime);
            goldMantisUser.setEndTime(endTime);
            if (mobileAExist == null) {
                goldMantisService.insertGoldMantisUser(goldMantisUser);
            } else {
                goldMantisUser.setId(mobileAExist.getId());
                goldMantisService.updateGoldMantisUser(goldMantisUser);
            }

            goldMantisService.insertGoldMantisLog(goldMantisUser);

        } catch (Exception e) {
            LOGGER.error("金螳螂导入用户时发生异常:" + e.getCause() + ":" + e.getMessage());
            res.put("success", false);
            res.put("message", "写数据发生异常:" + e.getCause());
            String jsonStr = JSONObject.toJSONString(res);
            return callback+"(" + jsonStr + ")";
        }

        res.put("success", true);
        res.put("message", "写入数据成功");
        String jsonStr = JSONObject.toJSONString(res);
        return callback+"(" + jsonStr + ")";
    }

}
