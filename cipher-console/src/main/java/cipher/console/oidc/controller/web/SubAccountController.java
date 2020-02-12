package cipher.console.oidc.controller.web;

import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.DataGridModel;
import cipher.console.oidc.domain.exceldomain.SubAccountExcel;
import cipher.console.oidc.domain.exceldomain.SubNoClientIdExcel;
import cipher.console.oidc.domain.web.*;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.SubAccountMapper;
import cipher.console.oidc.model.MainSubAppAccountModel;
import cipher.console.oidc.model.SubAccountAuthModel;
import cipher.console.oidc.service.*;
import cipher.console.oidc.util.ConstantsUtils;
import cipher.console.oidc.util.FileUtil;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.ResponseUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.concurrent.TimeUnit;

import static cipher.console.oidc.common.ReturnUtils.successResponse;

/**
 * @Author: zt
 * @Date: 2018/6/4 17:24
 */
@Controller
@RequestMapping(value = "/cipher/subaccount")
@EnableAutoConfiguration
public class SubAccountController {

    @Autowired
    private SubAccountService subAccountService;

    @Autowired
    private UserService userService;

    @Autowired
    private SubAccountMapService subAccountMapService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private AppService appService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private SessionService sessionService;


    @Autowired
    private SubAccountMapper subAccountMapper;

    private static final Logger LOGGER = LoggerFactory.getLogger(SubAccountController.class);



    @RequestMapping(value = "/list", params = "json")
    @ResponseBody
    public Map<String, Object> queryData(HttpServletResponse response, DataGridModel pageModel, MainSubAppAccountModel form) {
        LOGGER.debug("Enter SubAccountController.queryData");
        return subAccountService.subAccountPageList(pageModel, form);
    }


    /**
     * 从账号授权页面
     *
     * @return
     */
    @RequestMapping(value = "/subAccountAuth")
    public Map<String,Object> subAccountList(@RequestParam(value = "subAccountId") String subAccountId,
                                       @RequestParam(value = "applicationName") String applicationName) {
        Map<String,Object> content=new HashMap<>();
        content.put("subAccountId", subAccountId);
        content.put("applicationName", applicationName);
        return successResponse("content",content);
    }

    @RequestMapping(value = "/accountList", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> subAuth(HttpServletResponse response, DataGridModel pageModel, SubAccountAuthModel form,
                                       @RequestParam(value = "sidx",required = false) String sidx,
                                       @RequestParam(value = "sord",required = false,defaultValue = "desc") String sord) {
        LOGGER.debug("Enter SubAccountController.subAuth");
        ApplicationInfo applicationInfo = new ApplicationInfo();
        applicationInfo.setApplicationName(form.getApplicationName());
        form.setSidx(sidx);
        form.setSord(sord);
        //应用id
        Integer id = appService.queryIdByName(applicationInfo);
        form.setApplicationId(id);
        return subAccountService.subAccountAuthPageList(pageModel, form);
    }

    /**
     * 将某个主账号授权给子账号的授权操作
     *
     * @param response
     * @param form
     */
    @RequestMapping(value = "/auth", params = "json", method = RequestMethod.POST)
    @ResponseBody
    public void subAccountAuth(HttpServletResponse response, SubAccountMapDomain form) {
        try {
            LOGGER.debug("Enter SubAccountController.subAccountAuth");
            subAccountService.insertSubAccountMap(form);
            ResponseUtils.customSuccessResponse(response, "授权成功");
        } catch (Exception e) {
            LOGGER.debug("Enter SubAccountController.subAccountAuth error:" + e.getCause());
            ResponseUtils.customFailueResponse(response, "授权失败");
            e.printStackTrace();
        }
    }

    @RequestMapping(value = "/disconnect", method = RequestMethod.POST)
    @ResponseBody
    public void cancellAuth(SubAccountAuthModel form, HttpServletResponse response) {
        try {
            LOGGER.debug("Enter SubAccountController.cancellAuth");
            subAccountService.deleteSubAccountMap(form);
            ResponseUtils.customSuccessResponse(response, "取消关联成功");
        } catch (Exception e) {
            LOGGER.error("Enter SubAccountController.cancellAuth Error:" + e.getCause());
            ResponseUtils.customFailueResponse(response, "取消关联失败");
            e.printStackTrace();
        }
    }


    @RequestMapping(value = "/upload", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadExcel(@RequestParam(value = "file") MultipartFile file, HttpSession session,
                                           @RequestParam(value = "applicationId", required = false, defaultValue = "") String appId,
                                           HttpServletResponse response, HttpServletRequest request) throws Exception {
        LOGGER.debug("Enter SubAccountController.uploadExcel;appId:" + appId);
       // List<SubAccountExcel> list1 = FileUtil.importExcel(file, 1, 1, SubAccountExcel.class);
        String companyUuid = sessionService.getCompanyUUid(request.getSession());
        //判断文件是否为空
        if (file == null) {
            ResponseUtils.customFailueResponse(response, "文件不能为空");
            return null;
        }
        ApplicationInfoDomain form=new ApplicationInfoDomain();
        form.setId(Integer.valueOf(appId));
        ApplicationInfoDomain applicationInfoDomain=  appService.findApplicationById(form) ;
        if(null==applicationInfoDomain){
            ResponseUtils.customFailueResponse(response, "应用ID不存在");
            return null;
        }
        String appClientId=applicationInfoDomain.getApplicationId();
        List<SubAccountExcel> respExcel = new ArrayList<>();
        List<SubAccountExcel> successExcel = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        int count = 0;
        try {
            List<SubAccountExcel> list = null;
            //带appClientId的情况
            if (StringUtils.isEmpty(appClientId)) {
                list = FileUtil.importExcel(file, 1, 1, SubAccountExcel.class);
            } else {
                List<SubNoClientIdExcel> noClientIdExcelDomainList = FileUtil.importExcel(file, 1, 1, SubNoClientIdExcel.class);
                list = copyDomain(noClientIdExcelDomainList, appClientId);
            }

            if (CollectionUtils.isEmpty(list)) {
                ResponseUtils.customFailueResponse(response, "未读取到数据");
                return null;
            }
            count = list.size();
            //1.判断数据格式问题,主账号可以没有，有必须为邮箱，appclientId和从账号不能为空
            Iterator<SubAccountExcel> iterator = list.iterator();
            while (iterator.hasNext()) {
                SubAccountExcel subAccountExcel = iterator.next();
                //如果appclientId为空或者，主账号为空，则为格式异常
                        if (StringUtils.isEmpty(subAccountExcel.getAppClientId()) ||
                        StringUtils.isEmpty(subAccountExcel.getAccountNumber())||
                        StringUtils.isEmpty(subAccountExcel.getSubAccount())) {
                    respExcel.add(subAccountExcel);
                    iterator.remove();
                }
            }
            //格式错误的记录条数
            int styleFaliuer = respExcel.size();

            //1.判断该从账号是否已经存在
            //如果已经存在，查找出它的主键id,如果导入的从账号对应的主账号存在，对比关联表是否已经存在相应的记录
            //2.导入的从账号不存在，插入该从账号并返回主键id
            SubAccountMapDomain subAccountMapDomain = new SubAccountMapDomain();
            SubAccountDomain subAccountDomain = null;
            UserInfoDomain userInfoDomain=null;
            System.out.println("SubAccountExcel-----------------"+list);

            for (SubAccountExcel subAccountExcel : list) {
                LOGGER.info("start1---------------------------------");
                userInfoDomain=userService.getUserByNewAccountNumber(subAccountExcel.getAccountNumber(),companyUuid);
                subAccountDomain = subAccountService.querySubAccount(subAccountExcel);
                //1.该子账号还不存在，插入该子账号，并建立与主账号的关联关系，如果有
                if(userInfoDomain!=null){
                    if (ObjectUtils.isEmpty(subAccountDomain)) {
                        subAccountService.insertSubAccount(subAccountExcel);
                        if (!StringUtils.isEmpty(subAccountExcel.getAccountNumber())) {
                            //此处的id为myBatis插入时自动返回的主键id
                            SubAccountMapDomain newDomain = new SubAccountMapDomain();
                            newDomain.setUserId(userInfoDomain.getUuid());
                            newDomain.setAccountNumber(subAccountExcel.getAccountNumber());
                            newDomain.setSubId(subAccountExcel.getId());
                            SubAccountDomain subDomain=subAccountMapper.querySubAccountInfo(userInfoDomain.getUuid(),appClientId);
                            if(null!=subDomain){
                                SubAccountAuthModel subAccountAuthModel=new SubAccountAuthModel();
                                subAccountAuthModel.setSubId(subDomain.getId());
                                subAccountAuthModel.setAccountNumber(subAccountExcel.getAccountNumber());
                                subAccountAuthModel.setUuid(userInfoDomain.getUuid());
                                subAccountMapper.deleteSubAccountMap(subAccountAuthModel);
                            }
                            SubAccountMapDomain domain = subAccountMapService.querySubMap(newDomain);
                            if (null == domain) {
                                subAccountMapService.insertSubAccountMap(newDomain);
                            }
                        }
                        successExcel.add(subAccountExcel);
                    } else {
                        LOGGER.info("start2---------------------------------");
                        //  2.该子账号已经存在；判断与主账号的映射是否已经存在
                        if (!StringUtils.isEmpty(subAccountExcel.getAccountNumber())) {
                            subAccountMapDomain.setSubId(subAccountDomain.getId());
                            subAccountMapDomain.setAccountNumber(subAccountExcel.getAccountNumber());
                            int total = subAccountMapService.querySubAccountMap(subAccountExcel);
                            if(total>0){
                                respExcel.add(subAccountExcel);
                            }
                            SubAccountMapDomain newDomain = new SubAccountMapDomain();
                            newDomain.setUserId(userInfoDomain.getUuid());
                            newDomain.setAccountNumber(subAccountExcel.getAccountNumber());
                            newDomain.setSubId(subAccountDomain.getId());
                            SubAccountDomain subDomain=subAccountMapper.querySubAccountInfo(userInfoDomain.getUuid(),appClientId);
                            if(null!=subDomain){
                                SubAccountAuthModel subAccountAuthModel=new SubAccountAuthModel();
                                subAccountAuthModel.setSubId(subDomain.getId());
                                subAccountAuthModel.setAccountNumber(subAccountExcel.getAccountNumber());
                                subAccountAuthModel.setUuid(userInfoDomain.getUuid());
                                subAccountMapper.deleteSubAccountMap(subAccountAuthModel);
                            }
                            SubAccountMapDomain domain = subAccountMapService.querySubMap(newDomain);
                            if (null == domain) {
                                subAccountMapService.insertSubAccountMap(newDomain);
                            }
                        } else {
                            respExcel.add(subAccountExcel);
                        }
                        successExcel.add(subAccountExcel);
                    }
                }

            }

            String downloadSecret = ConstantsUtils.EXCEL_RETURN + UUID.randomUUID().toString().replace("-", "");
          //  if (respExcel.size() > 0) {
                ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
               // valueOperations.set(downloadSecret, respExcel, 5 * 60, TimeUnit.SECONDS);
                valueOperations.set(downloadSecret, successExcel, 5 * 60, TimeUnit.SECONDS);
          //  }
            map.put("return_code", 1);
            map.put("secret", downloadSecret);
            map.put("return_msg", "Excel导入成功,格式错误" + styleFaliuer + "条;重复:" + (respExcel.size() - styleFaliuer) + "条;" + "成功:" + (count - respExcel.size()) + "条");
            try {
                JSONObject json = JSONObject.fromObject(map);
                String accountuser = ConstantsCMP.getCipherUuidInfo(request);
                String companyId=ConstantsCMP.getSessionCompanyId(request);
                AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.SUBACCOUNT_MAINTENANCE.getType(),  "导入子账号");
                adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return map;
        } catch (Exception e) {
            LOGGER.debug("Enter SubAccountController.uploadExcel;error:" + e.getCause());
            e.printStackTrace();
            map.put("return_code", 2);
            return map;
        }
    }

    /**
     * 下载反馈的excel
     *
     * @param response
     * @param secret
     */
    @RequestMapping(value = "/downloadReturnExcel")
    @SuppressWarnings("unchecked")
    public void downloadExcel(HttpServletResponse response, @RequestParam(value = "downloadSecret") String secret) {
        LOGGER.debug("Enter SubAccountController.downloadExcel;downloadSecret:" + secret);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<SubAccountExcel> list = (List<SubAccountExcel>) valueOperations.get(secret);
        FileUtil.exportExcel(list, "导入结果", "", SubAccountExcel.class, "结果反馈.xls", response);
    }


    /**
     * 填充appClientId
     *
     * @param list
     * @param appClientId
     * @return
     */
    private List<SubAccountExcel> copyDomain(List<SubNoClientIdExcel> list, String appClientId) {
        if (CollectionUtils.isEmpty(list)) {
            return null;
        }
        List<SubAccountExcel> subAccountExcelList = new ArrayList<>();
        for (SubNoClientIdExcel noClientIdExcel : list) {
            SubAccountExcel subAccountExcel = new SubAccountExcel(appClientId, noClientIdExcel.getSubAccount(), noClientIdExcel.getAccountNumber());
            subAccountExcelList.add(subAccountExcel);
        }
        return subAccountExcelList;
    }





}
