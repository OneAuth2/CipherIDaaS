package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.web.AcSetInfo;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.TypeInfo;
import cipher.console.oidc.enums.AcDeviceEnum;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.redis.RedisClient;
import cipher.console.oidc.service.AcSetInfoService;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.token.CheckToken;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.http.client.support.HttpAccessor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

import static cipher.console.oidc.common.ReturnUtils.failureResponse;



/**
 * Created by on 2018/9/17.
 */

@Controller
@RequestMapping(value = "/cipher/acset")
@EnableAutoConfiguration
public class AcSetInfoController {

    @Autowired
    private AcSetInfoService acSetInfoService;

    @Autowired
    private RedisClient redisClient;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;




    /*
    * 获取AC配置列表
    * （数据隔离修改）
    * */
    @CheckToken
    @RequestMapping(value = "/list",method = RequestMethod.POST )
    @ResponseBody
    public Map<String, Object> list(AcSetInfo form, DataGridModel pageModel,HttpServletRequest request){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        form.setCompanyId(companyId);
        return acSetInfoService.getAcSetPageList(form,pageModel);
    }

    /**
     * create by 田扛
     * create time 2019年3月21日19:59:02
     * 删除ac配置
     * @param
     * @return
     */
    @RequestMapping(value = "/deleteAc")
    @ResponseBody
    public Map<String,Object> deleteAc(AcSetInfo acSetInfo, HttpSession session){
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),"删除AC配置信息");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return acSetInfoService.deleteAc(acSetInfo);
    }


    /**
     * @param id                需要更新的配置信息ID
     * @return                  需要更新的配置信息
     *                          该接口已废弃
     * */
    @RequestMapping(value = "/updateNew",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> update(@RequestParam(value = "id",required = true) Integer id){
        AcSetInfo acSetInfo=acSetInfoService.selectByPrimaryKey(id);

        //查询无线配置信息失败
        if (acSetInfo == null){
            return failureResponse("没有找到要更新的数据");
        }

        //查询设备的类型
        acSetInfo.setAcDeviceName(AcDeviceEnum.getAcDevice(acSetInfo.getAcDeviceId()));
        Map<String,Object> map = new HashMap<>();
        map.put("acSetInfo",acSetInfo);
        return map;
    }

    /*
    * AC配置修改接口
    * (数据隔离修改)
    * */
    @CheckToken
    @RequestMapping(value = "/update",method = RequestMethod.POST)
    @ResponseBody
    public void update(HttpServletResponse response, HttpServletRequest request, AcSetInfo form,HttpSession session){
        String companyId=ConstantsCMP.getSessionCompanyId(request);
        if(StringUtils.isNotEmpty(form.getBaseIp())){
            String str=form.getBaseIp().replace(",", "");
            form.setBaseIp(str);
        }
        if(StringUtils.isNotEmpty(form.getUrl())){
            if(form.getUrl().contains("http://")||form.getUrl().contains("https://")){
                form.setUrl(form.getUrl());
            }else{
                String url="http://"+form.getUrl();
                form.setUrl(url);
            }

        }

        if(null==form.getId()){
            form.setCompanyId(companyId);
            acSetInfoService.insertWifiPortalSetInfo(form);

            ResponseUtils.customSuccessResponse(response, "添加AC配置成功！");
        }else {
            int i = acSetInfoService.updateByPrimaryKeySelective(form);
            ResponseUtils.customSuccessResponse(response, "修改AC配置成功！");
        }

        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.WIFI_MANAGER.getType(),"插入或更改ac策略配置");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
    }

    /*
    * 获取AC设备类型
    * */
    @RequestMapping(value = "/common",method = RequestMethod.POST)
    @ResponseBody
    public List<TypeInfo> getList(){
            AcDeviceEnum[] values = AcDeviceEnum.values();
            List<TypeInfo> list=new ArrayList<>();
            for (AcDeviceEnum acDeviceEnum : values) {
                TypeInfo typeInfo=new TypeInfo();
                typeInfo.setType(acDeviceEnum.getType());
                typeInfo.setDesc(acDeviceEnum.getDesc());
                list.add(typeInfo);
            }
            return list;
        }
}
