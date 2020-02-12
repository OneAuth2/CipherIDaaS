package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.exceldomain.*;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import cipher.console.oidc.enums.AdminBehaviorEnum;
import cipher.console.oidc.mapper.UserGroupMapper;
import cipher.console.oidc.model.GroupInfoModel;
import cipher.console.oidc.service.AdminBehaviorInfoService;
import cipher.console.oidc.service.GroupService;
import cipher.console.oidc.service.SessionService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.*;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static cipher.console.oidc.util.VerifyUtil.*;

@Controller
@RequestMapping(value = "/cipher/newImportbak")
@EnableAutoConfiguration
public class UserImportExcelBakController {


    private static final Logger logger = LoggerFactory.getLogger(UserImportExcelBakController.class.getSimpleName());

    @Autowired
    private UserService userService;

    @Autowired
    private AdminBehaviorInfoService adminBehaviorInfoService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Autowired
    private UserGroupMapper userGroupMapper;


    @Autowired
    private GroupService groupService;

    @Autowired
    private SessionService sessionService;

    @RequestMapping(value = "/importpage")
    public String accountImportPage(){
        return "uploadFile";
    }

    @RequestMapping(value = "/import")
    @ResponseBody
    public Map<String, Object> accountImport(MultipartFile file, HttpSession session,
                                             HttpServletResponse response, HttpServletRequest requeset) {
        String companyId = sessionService.getCompanyUUid(requeset.getSession());
        //判断文件是否为空
        if (file == null) {
            ResponseUtils.customFailueResponse(response, "文件不能为空");
            return null;
        }

        ImportExcelUtil eu = null;
        try {
            eu = new ImportExcelUtil(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<String, Object> map = new HashMap<>();
        List<NewUserErrorExcle> userErrorExcles = new ArrayList<>();
        List<NewUserInfoExcle> respList = new ArrayList<>();
        List<NewUserInfoExcle> repeatNameList = new ArrayList<>();
        int success = 0;
        int total = 0;
        int typeError = 0;
        try {
            //添加人员信息表
            List<NewUserInfoExcle> userInfoExcleList = new ArrayList<>();
            Map<Integer, Map<String, String>> usermap = eu.readUserExcelContent(companyId);
            System.out.println("获得人员表格的内容:");
            for (Map.Entry<Integer, Map<String, String>> entry : usermap.entrySet()) {
                NewUserInfoExcle newUserInfoExcle = ImportExcelUtil.jsonToPojo(entry.getValue().toString(), NewUserInfoExcle.class);
                userInfoExcleList.add(newUserInfoExcle);
            }
            //导入的总的数据条数
            total = userInfoExcleList.size();
            if (userInfoExcleList.size() <= 0) {
                ResponseUtils.customFailueResponse(response, "文件数据为空");
                return null;
            }
            //查询格式错误 姓名，主账号，邮箱不能为空
            Iterator<NewUserInfoExcle> iterator = userInfoExcleList.iterator();
            while (iterator.hasNext()) {
                NewUserInfoExcle excel = iterator.next();
                if (StringUtils.isEmpty(excel.getUserName()) || StringUtils.isEmpty(excel.getAccountNumber()) || StringUtils.isEmpty(excel.getMail())) {
                    respList.add(excel);
                    iterator.remove();
                }
            }
            typeError = respList.size();
            if (null != userInfoExcleList && userInfoExcleList.size() > 0) {
                // 从数据库中查询已经存在
                repeatNameList = userService.queryRepeatList(userInfoExcleList);
                List<NewUserInfoExcle> diffList = diffRepeatName(userInfoExcleList, repeatNameList, respList);
                success = diffList.size();
                if (diffList.size() > 0) {
                    userService.batchInsertNewUser(diffList);
                }
                //添加部门以及人和部门之间的关联表
                Map<Integer, Map<String, Object>> groupmap = eu.readGroupExcelContent();
                System.out.println("获得部门表格的内容:");
                for (Map.Entry<Integer, Map<String, Object>> entry : groupmap.entrySet()) {
                    int count = 0;
                    int parentGroupId = 0;
                    String groupName = "";
                    String accountNumber = "";
                    String title1 = "";
                    List<Integer> path = new ArrayList<>();
                    for (Map.Entry<String, Object> newentry : entry.getValue().entrySet()) {
                        System.out.println(newentry.getKey() + ": " + newentry.getValue());
                        title1 = newentry.getKey();
                        if("主账号（用户名）".equals(title1)){
                            accountNumber = String.valueOf(newentry.getValue());
                        }
                        groupName = String.valueOf(newentry.getValue());
                        if (!"\"".equals(groupName) && "" != groupName&&!"主账号（用户名）".equals(title1)) {
                            GroupInfoDomain groupInfoDomain = groupService.queryGroupByNameAndcompanyId(companyId, groupName);
                            GroupInfoModel group = new GroupInfoModel();
                            group.setCompanyId(companyId);
                            group.setGroupName(groupName);
                            if (null == groupInfoDomain) {
                                group.setParentGroupId(parentGroupId);
                                groupService.insertNewGroup(group);
                                parentGroupId = group.getGroupId();
                                path.add(group.getGroupId());
                                group.setPath(listToString(path));
                                groupService.updateNewGroup(group);
                            } else {
                                if (groupInfoDomain.getParentGroupId() != parentGroupId) {
                                    group.setParentGroupId(parentGroupId);
                                    groupService.insertNewGroup(group);
                                    parentGroupId = group.getGroupId();
                                    path.add(group.getGroupId());
                                    group.setPath(listToString(path));
                                    groupService.updateNewGroup(group);
                                } else {
                                    path.add(groupInfoDomain.getGroupId());
                                    parentGroupId = groupInfoDomain.getGroupId();
                                }

                            }

                        }
                        count++;
                    }
                    if ("" != accountNumber) {
                        GroupUserMapDomain groupUserMapDomain = new GroupUserMapDomain();
                        groupUserMapDomain.setGroupId(parentGroupId);
                        //设置userId
                        String userId = userGroupMapper.selectUuidByaccountNumber(accountNumber);
                        groupUserMapDomain.setUserId(userId);
                        GroupUserMapDomain groupUserMap = userGroupMapper.selectUserGroup(groupUserMapDomain);
                        if (null == groupUserMap) {
                            userGroupMapper.insertUserGroupMap(groupUserMapDomain);
                        }
                    }

                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("未找到指定路径的文件!");
            logger.error(e.getMessage(),e);
            e.printStackTrace();
        }
        //导入信息错误列表

        if (respList.size() > 0) {
            for (NewUserInfoExcle resp : respList) {
                NewUserErrorExcle newResp = new NewUserErrorExcle(resp);
                newResp.setErrorMsg("姓名，主账号，邮箱不能为空");
                userErrorExcles.add(newResp);
            }
        }
        if (repeatNameList.size() > 0) {
            for (NewUserInfoExcle newreapeat : repeatNameList) {
                NewUserErrorExcle reapeat = new NewUserErrorExcle(newreapeat);
                reapeat.setErrorMsg("数据重复");
                userErrorExcles.add(reapeat);
            }
        }
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String downloadSecret = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(ConstantsUtils.EXCEL_RETURN + downloadSecret, userErrorExcles, 5 * 60, TimeUnit.SECONDS);
        map.put("return_code", 1);
        map.put("secret", downloadSecret);
        logger.info("格式错误" + typeError + "条数据," + "重复" + (total - typeError - success) + "条数据," + "成功" + success + "条数据");
        map.put("return_msg", "格式错误" + typeError + "条数据," + "重复" + (total - typeError - success) + "条数据," + "成功" + success + "条数据");
        JSONObject json = JSONObject.fromObject(map);
        String userAccount = ConstantsCMP.getSessionUser(requeset);
        AdminBehaviorInfo adminBehaviorInfo = new AdminBehaviorInfo(AdminBehaviorEnum.ACCOUNT_MAINTENANCE.getType(),  "导入账号列表");
        adminBehaviorInfoService.insertSelective(adminBehaviorInfo,session);
        return map;
    }


    private List<NewUserInfoExcle> diffRepeatName(List<NewUserInfoExcle> sourceList, List<NewUserInfoExcle> repeatList, List<NewUserInfoExcle> respList) {
        Iterator<NewUserInfoExcle> iterator = sourceList.iterator();
        while (iterator.hasNext()) {
            NewUserInfoExcle excel = iterator.next();
            if(StringUtils.isEmpty(excel.getGender())){
                excel.setGender("男");
            }
            for (NewUserInfoExcle newUserInfoExcle : repeatList) {
                if (excel.getAccountNumber().equals(newUserInfoExcle.getAccountNumber())) {
                    iterator.remove();
                    // respList.add(excel);
                    break;
                }
            }
        }
        return sourceList;
    }


    @RequestMapping(value = "/userexport")
    public void UserInfoExport(HttpServletResponse response) {
        List<NewUserExportExcel> list = new ArrayList<>();
        FileUtil.exportExcel(list, "账号模板表", "", NewUserExportExcel.class, "账号模板.xls", response);
    }


    @RequestMapping(value = "/downloadReturnExcel")
    @SuppressWarnings("unchecked")
    public void downloadExcel(HttpServletResponse response, @RequestParam(value = "downloadSecret") String secret) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<NewUserErrorExcle> list = (List<NewUserErrorExcle>) valueOperations.get(ConstantsUtils.EXCEL_RETURN + secret);
        FileUtil.exportExcel(list, "导入结果", "", NewUserErrorExcle.class, "数据错误反馈.xls", response);
    }



    /**
     * 更新用户信息样表
     * @author cozi
     * @date 2019-07-16
     * @param response
     */
    @RequestMapping(value = "/updateUserInfoExport")
    public void updateUserInfo(HttpServletResponse response){
        List<UpdateUserInfoExcle> list = new ArrayList<>();
        FileUtil.exportExcel(list, "账号更新模板表", "", UpdateUserInfoExcle.class, "账号更新模板.xls", response);
    }

    /**
     * 使用文件批量更新账号
     * @author cozi
     * @date 2019-07-16
     * @param file
     * @param response
     * @param requeset
     * @return
     */
    @RequestMapping(value = "/updateUserImport")
    @ResponseBody
    public Map<String, Object> userInfoImport(MultipartFile file,
                                              HttpServletResponse response, HttpServletRequest requeset) {
        String companyId = sessionService.getCompanyUUid(requeset.getSession());
        Map<String, Object> map = new HashMap<>();
        if(file==null){
            ResponseUtils.customFailueResponse(response, "文件不能为空");
            return null;
        }
        //文件中的数据
        List<UpdateUserInfoExcle> list = new ArrayList<>();
        //异常数据
        List<UpdateUserInfoErrorExcle> errorList = new ArrayList<>();
        try {
            list = FileUtil.importExcel(file, 1, 1,UpdateUserInfoExcle.class);
        }catch (Exception e){
            logger.debug("Enter UserImportExcelBakController.userInfoImport;error:" + e.getCause());
            e.printStackTrace();
            map.put("return_code", 2);
            map.put("return_msg", "excle数据格式化异常");
            return map;
        }
        //更新成功的条数
        int success = 0;
        //账号不存在
        int exist = 0;
        //数据重复
        int repetition = 0;
        //数据格式异常
        int format = 0;
        if(list!=null&&list.size()>0){
            for(int i=0;i<list.size();i++){
                UpdateUserInfoExcle updateUserInfoExcle = list.get(i);
                if(!StringUtils.isEmpty(updateUserInfoExcle.getAccountNumber())){
                    int count = userService.selectUserInfoCount(updateUserInfoExcle.getAccountNumber(), companyId);
                    if(count>0){
                        //校验邮箱，手机号，身份证号不能发生重复
                        if(!StringUtils.isEmpty(updateUserInfoExcle.getMail())){
                            if(verifyMail(updateUserInfoExcle.getMail())){
                                int email = userService.selectCountByETI(updateUserInfoExcle.getAccountNumber(),updateUserInfoExcle.getMail(), "", "", companyId);
                                if(email>0){
                                    UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                    updateUserInfoErrorExcle.setMsg("邮箱重复");
                                    errorList.add(updateUserInfoErrorExcle);
                                    repetition++;
                                    continue;
                                }
                            }else {
                                UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                updateUserInfoErrorExcle.setMsg("邮箱格式错误");
                                errorList.add(updateUserInfoErrorExcle);
                                format++;
                                continue;
                            }

                        }
                        if(!StringUtils.isEmpty(updateUserInfoExcle.getPhoneNumber())){
                            if(verifyPhoneNumber(updateUserInfoExcle.getPhoneNumber())){
                                int telephone = userService.selectCountByETI(updateUserInfoExcle.getAccountNumber(),"",updateUserInfoExcle.getPhoneNumber(), "", companyId);
                                if(telephone>0){
                                    UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                    updateUserInfoErrorExcle.setMsg("手机号重复");
                                    errorList.add(updateUserInfoErrorExcle);
                                    repetition++;
                                    continue;
                                }
                            }else {
                                UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                updateUserInfoErrorExcle.setMsg("手机号格式错误");
                                errorList.add(updateUserInfoErrorExcle);
                                format++;
                                continue;
                            }

                        }
                        if(!StringUtils.isEmpty(updateUserInfoExcle.getIdNum())){
                            if(verifyIdNum(updateUserInfoExcle.getIdNum())){
                                int idNum = userService.selectCountByETI(updateUserInfoExcle.getAccountNumber(),"", "", updateUserInfoExcle.getIdNum(), companyId);
                                if(idNum>0){
                                    UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                    updateUserInfoErrorExcle.setMsg("身份证号重复");
                                    errorList.add(updateUserInfoErrorExcle);
                                    repetition++;
                                    continue;
                                }
                            }else {
                                UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                                updateUserInfoErrorExcle.setMsg("身份证号格式错误");
                                errorList.add(updateUserInfoErrorExcle);
                                format++;
                                continue;
                            }

                        }
                        userService.updateUserInfo(updateUserInfoExcle,companyId);
                        success++;
                    }else {
                        UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                        updateUserInfoErrorExcle.setMsg("账号不存在");
                        errorList.add(updateUserInfoErrorExcle);
                        exist++;
                    }
                }else {
                    UpdateUserInfoErrorExcle updateUserInfoErrorExcle = new UpdateUserInfoErrorExcle(updateUserInfoExcle);
                    updateUserInfoErrorExcle.setMsg("主账号（用户名）不能为空");
                    errorList.add(updateUserInfoErrorExcle);
                }
            }
        }else {
            ResponseUtils.customFailueResponse(response, "文件中无数据");
            return null;
        }
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        String downloadSecret = UUID.randomUUID().toString().replace("-", "");
        valueOperations.set(ConstantsUtils.EXCEL_RETURN + downloadSecret,errorList, 5 * 60, TimeUnit.SECONDS);
        map.put("secret",downloadSecret);
        map.put("return_code", 1);
        map.put("return_msg", "excle更新用户信息成功："+success+"条，主账号（用户名）为空："
                +(list.size()-success-exist-repetition-format)+"条,账号不存在："+exist+"条,数据重复："
                +repetition+"条，数据格式错误："+format+"条");
        return map;
    }

    @RequestMapping(value = "/downloadErrorExcel")
    @SuppressWarnings("unchecked")
    public void downloadErrorExcel(HttpServletResponse response, @RequestParam(value = "downloadSecret") String secret) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<UpdateUserInfoErrorExcle> list = (List<UpdateUserInfoErrorExcle>) valueOperations.get(ConstantsUtils.EXCEL_RETURN + secret);
        FileUtil.exportExcel(list, "更新结果", "", UpdateUserInfoErrorExcle.class, "数据错误反馈.xls", response);
    }

    public static String listToString(List<Integer> list) {

        if (list == null) {
            return null;
        }
        StringBuilder result = new StringBuilder();
        boolean first = true;
        //第一个前面不拼接","
        for (Integer integer : list) {
            if (first) {
                first = false;
            } else {
                result.append(",");
            }
            result.append(integer);
        }
        return result.toString();
    }
}