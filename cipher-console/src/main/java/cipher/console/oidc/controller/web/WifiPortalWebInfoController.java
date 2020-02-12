package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.*;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AcDeviceEnum;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.*;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.ResponseUtils;
import net.sf.json.JSONObject;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

/**
 * portal配置
 */

@Controller
@RequestMapping(value = "/cipher/wifiportal")
@EnableAutoConfiguration
public class WifiPortalWebInfoController {

    @Autowired
    private WifiPortalWebInfoService wifiPortWebInfoService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;


    @Autowired
    private WifiConfigPageService wifiConfigPageService;

    @Autowired
    private AcSetInfoService acSetInfoService;

    @Autowired
    private RadiusConfigInfoService radiusConfigInfoService;

    @Autowired
    private RedisClient<String,Object> redisClient;

    /**
     * create by田扛
     * create time 2019年3月11日23:05:59
     * 获取编辑protal界面的信息
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/editProtal")
    @ResponseBody
    public Map<String, Object> editProtal(String id) {
        return wifiPortWebInfoService.getEditProtal(id);
    }

    /*
     * 获取wifi-portal配置列表
     * （数据隔离修改）
     * */
    //@CheckToken
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> list(WifiPotalPageSettingInfo form, DataGridModel pageModel,
                                    HttpServletRequest request) {
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        return wifiPortWebInfoService.getWifiPortWebInfoPageList(form, pageModel);
    }


    /**
     * 更改wifi配置信息
     * modify by 田扛
     * date 2019/3/7
     *
     * @param response
     * @param request
     * @param form
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(HttpServletResponse response, HttpServletRequest request, WifiPotalPageSettingInfo form, HttpSession session) {
        String companyId = ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashedMap();
        try {
            if (form.getId() == null) {
                map.put("code", 1);
                map.put("msg", "参数错误");
                return map;
            } else {
                WifiPotalPageSettingInfo wifiPotalPageSettingInfo = wifiConfigPageService.getWifiPortalInfo(form);
                if (null != wifiPotalPageSettingInfo && wifiPotalPageSettingInfo.getPortalType().equals(form.getPortalType())) {
                    map.put("code", 1);
                    map.put("msg", "已有该类型的配置页面，请到编辑页面进行修改！");
                    return map;
                }
                int ret = wifiPortWebInfoService.updateByPrimaryKeySelective(form);
                if (ret != 1) {
                    map.put("code", 1);
                    map.put("msg", "更新数据库失败");
                    return map;
                } else {
                    map.put("code", 0);
                    map.put("msg", "修改portal设置成功");
                }
            }
        } catch (Exception e) {
            map.put("code", 1);
            map.put("msg", "内部服务器错误");
            e.printStackTrace();
        }

        try {
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SYSTEM_MANAGER.getType(), "更新无线portal配置页面");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo, session);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;

    }


    @RequestMapping(value = "/showPage", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> showPage(@RequestParam(value = "id") Integer id) {
        if (StringUtils.isEmpty(String.valueOf(id))) {
            return ReturnUtils.failureResponse("参数为空");
        }
        WifiPortalWebInfo wifiPortalWebInfo = wifiPortWebInfoService.selectByPrimaryKey(id);
        if (null == wifiPortalWebInfo) {
            return ReturnUtils.failureResponse("返回信息为空");
        }
        return ReturnUtils.successResponse("wifiPortalWebInfo", wifiPortalWebInfo);
    }


    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public void delete(HttpServletResponse response, HttpSession session, Integer id) {
        try {
            if (null != id) {
                wifiPortWebInfoService.deleteInfo(id);
                AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),  "删除无线portal配置");
                adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
                ResponseUtils.customSuccessResponse(response, "删除成功！");
            }
        } catch (Exception e) {
            e.printStackTrace();
            ResponseUtils.customFailueResponse(response, "服务器错误！");
        }
    }



    /* *
     * 无线portal页面网络配置 回显
     * */
    @RequestMapping(value = "/wifiShow", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> wifishow(@RequestParam(value = "id") Integer id) {
        Map<String, Object> map = new HashMap<>();
        if (!org.springframework.util.StringUtils.isEmpty(id)) {
            WifiPotalPageSettingInfo wifiPotalPageSettingInfo = wifiPortWebInfoService.getWifiPotalPageSettingInfo(String.valueOf(id));

            String portalUrl= wifiPotalPageSettingInfo.getPortalAddress();
            try {
                URL url = new URL(portalUrl);
                wifiPotalPageSettingInfo.setPort(String.valueOf(url.getPort()));
                wifiPotalPageSettingInfo.setIp(url.getHost());
                wifiPotalPageSettingInfo.setUri(url.getPath());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }

            if (null == wifiPotalPageSettingInfo) {
                return NewReturnUtils.failureResponse("返回数据为空");
            }

            if (StringUtils.isEmpty(String.valueOf(wifiPotalPageSettingInfo.getAcId())) || StringUtils.isEmpty(String.valueOf(wifiPotalPageSettingInfo.getRadiusId()))) {
                return NewReturnUtils.failureResponse("参数信息错误");
            }

            AcSetInfo acSetInfo = acSetInfoService.selectByPrimaryKey(wifiPotalPageSettingInfo.getAcId());
            if(null!=acSetInfo) {
                if (acSetInfo.getAcDeviceId() == AcDeviceEnum.CISCO.getType()) {
                    JSONObject jsonobject = JSONObject.fromObject(acSetInfo.getDeviceConfig());
                    CiscoInfo ciscoInfo = (CiscoInfo) JSONObject.toBean(jsonobject, CiscoInfo.class);
                    wifiPotalPageSettingInfo.setCiscoInfo(ciscoInfo);
                }


            if(acSetInfo.getAcDeviceId()== AcDeviceEnum.ARUBA.getType()){
                JSONObject jsonobject = JSONObject.fromObject(acSetInfo.getDeviceConfig());
                ArubaInfo arubaInfo = (ArubaInfo) JSONObject.toBean(jsonobject, ArubaInfo.class);
                wifiPotalPageSettingInfo.setArubaInfo(arubaInfo);
            }

            JSONObject jsonobject = JSONObject.fromObject(acSetInfo.getDeviceConfig());
            CommonDeviceInfo commonDeviceInfo = (CommonDeviceInfo) JSONObject.toBean(jsonobject, CommonDeviceInfo.class);
            wifiPotalPageSettingInfo.setCommonDeviceInfo(commonDeviceInfo);
            }
            RadiusConfigDomain radiusConfigDomain=new  RadiusConfigDomain();
            if(StringUtils.isNotEmpty(String.valueOf(wifiPotalPageSettingInfo.getRadiusId()))){
                radiusConfigDomain = radiusConfigInfoService.queryRadiusConfigById(wifiPotalPageSettingInfo.getRadiusId());
            }

            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
            map.put("wifiPotalPageSettingInfo", wifiPotalPageSettingInfo);
            map.put("acSetInfo", acSetInfo);
            map.put("radiusConfigDomain", radiusConfigDomain);
            return map;
        }
        return NewReturnUtils.failureResponse("参数信息错误");
    }

    /* *
     * 无线portal页面网络配置 添加和修改 第一步
     * *//*

    @RequestMapping(value = "/addOne", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(WifiPotalPageSettingInfo form,
                                       HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        try {
            if (StringUtils.isEmpty(String.valueOf(form.getId()))) {
                acSetInfoService.insertSelective(acSetInfo);
                radiusConfigInfoService.insertRadiusConfig(radiusConfigDomain);
                form.setRadiusId(radiusConfigDomain.getId());
                form.setAcId(acSetInfo.getId());
                wifiPortWebInfoService.insertWifiPotalPageSettingInfo(form);
            } else {
                acSetInfoService.deleteAcInfo(form.getAcId());
                radiusConfigInfoService.deleteRadiusConfig(form.getRadiusId());
                acSetInfoService.insertSelective(acSetInfo);
                radiusConfigInfoService.insertRadiusConfig(radiusConfigDomain);
                form.setRadiusId(radiusConfigDomain.getId());
                form.setAcId(acSetInfo.getId());
                wifiPortWebInfoService.updateByPrimaryKeySelective(form);
            }
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_code", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_code", "服务器错误");
        }
        return map;
    }

*/


    /* *
     * 无线portal页面网络配置 添加和修改 第一步
     * */

    @RequestMapping(value = "/addOne", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addOne(WifiPotalPageSettingInfo form,
                                      HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        try {
            redisClient.put(CacheKey.getCachePortalSetOne(),form);
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }


    /* *
     * 无线portal页面网络配置 添加和修改 第二步
     * */

    @RequestMapping(value = "/addTwo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> addTwo(WifiPortalSetInfoDomain form,
                                      HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        try {
            redisClient.put(CacheKey.getCachePortalSetTwo(),form);
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }



    /* *
     * 无线portal页面网络配置 添加和修改 第三步
     * */

    @RequestMapping(value = "/addThird", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(WifiPotalPageSettingInfo form,
                                      HttpSession session,HttpServletRequest request) {
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        Map<String, Object> map = new HashMap<>();
        try {
            WifiPotalPageSettingInfo wifiPotalPageSettingInfo=(WifiPotalPageSettingInfo)redisClient.get(CacheKey.getCachePortalSetOne());
            WifiPortalSetInfoDomain wifiPortalSetInfoDomain=(WifiPortalSetInfoDomain)redisClient.get(CacheKey.getCachePortalSetTwo());
            wifiPotalPageSettingInfo.setCompanyId(companyId);
            wifiPortalSetInfoDomain.setCompanyId(companyId);
            form.setPortalAddress(wifiPotalPageSettingInfo.getPortalAddress());
            form.setPortalNum(wifiPotalPageSettingInfo.getPortalNum());
            form.setDescription(wifiPotalPageSettingInfo.getDescription());
            //form.setPortalType(wifiPotalPageSettingInfo.getPortalType());
            acSetInfoService.insertSelective(wifiPortalSetInfoDomain);
            radiusConfigInfoService.insertRadiusConfig(wifiPortalSetInfoDomain);
            form.setRadiusId(wifiPortalSetInfoDomain.getRadiusId());
            form.setAcId(wifiPortalSetInfoDomain.getAcId());
            form.setCompanyId(companyId);
            wifiPortWebInfoService.insertWifiPotalPageSettingInfo(form);
            redisClient.remove(CacheKey.getCachePortalSetOne());
            redisClient.remove(CacheKey.getCachePortalSetTwo());
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),  "添加无线portal配置");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);

        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }







    /* *
     * 无线portal页面网络配置 添加和修改 第一步 和第三步
     * */

    @RequestMapping(value = "/updateOne", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateOne(WifiPotalPageSettingInfo form,
                                      HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(form.getPortalNum())){
            int  portalNum = 0;
            try {
                portalNum = Integer.parseInt(form.getPortalNum());
            }catch (Exception e){
                e.printStackTrace();
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "portal序号重复为正整数！");
                return map;
            }
            if(portalNum<0){
                map.put("return_code", ConstantsCMP.Code.FAIL);
                map.put("return_msg", "portal序号重复为正整数！");
                return map;
            }
            if(StringUtils.isNotEmpty(form.getPortalNum())){
                form.setPortalNum(String.valueOf(portalNum));
                int count = wifiPortWebInfoService.CountByPortalNum(form.getId(), form.getPortalNum());
                if(count>0){
                    map.put("return_code", ConstantsCMP.Code.FAIL);
                    map.put("return_msg", "portal序号重复！");
                    return map;
                }
            }
        }

        try {
            wifiPortWebInfoService.updateByPrimaryKeySelective(form);
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),  "更新无线portal配置");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }



    /* *
     * 无线portal页面网络配置 添加和修改 第一步
     * */

    @RequestMapping(value = "/updateTwo", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> updateTwo(WifiPortalSetInfoDomain form,
                                         HttpSession session) {

        Map<String, Object> map = new HashMap<>();
        try {
           /* acSetInfoService.deleteAcInfo(form.getAcId());
            radiusConfigInfoService.deleteRadiusConfig(form.getRadiusId());
            acSetInfoService.insertSelective(form);
            radiusConfigInfoService.insertRadiusConfig(form);
            WifiPotalPageSettingInfo wifiPotalPageSettingInfo=new WifiPotalPageSettingInfo();
            wifiPotalPageSettingInfo.setRadiusId(form.getRadiusId());
            wifiPotalPageSettingInfo.setAcId(form.getAcId());
            wifiPotalPageSettingInfo.setId(form.getPortalId());
            wifiPortWebInfoService.updateByPrimaryKeySelective(wifiPotalPageSettingInfo);*/

            radiusConfigInfoService.updateRadiusInfo(form);
            acSetInfoService.updateAcSetInfo(form);
            map.put("return_code", ConstantsCMP.Code.SUCCESS);
            map.put("return_msg", "操作成功");
            AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),  "更新ac和radius配置");
            adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        } catch (Exception e) {
            e.printStackTrace();
            map.put("return_code", ConstantsCMP.Code.FAIL);
            map.put("return_msg", "服务器错误");
        }
        return map;
    }


}
