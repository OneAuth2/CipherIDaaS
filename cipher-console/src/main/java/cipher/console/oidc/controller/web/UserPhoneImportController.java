package cipher.console.oidc.controller.web;


import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.domain.exceldomain.*;
import cipher.console.oidc.domain.web.AdminBehaviorInfo;
import cipher.console.oidc.domain.web.GroupInfoDomain;
import cipher.console.oidc.domain.web.GroupUserMapDomain;
import cipher.console.oidc.domain.web.UserInfoDomain;
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
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Controller
@RequestMapping(value = "/cipher/phone")
@EnableAutoConfiguration
public class UserPhoneImportController {


    private static final Logger logger = LoggerFactory.getLogger(UserPhoneImportController.class.getSimpleName());

    @Autowired
    private UserService userService;

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;


    @RequestMapping(value = "/import")
    @ResponseBody
    public void accountImport(MultipartFile file) {
        List<UserPhoneExcle> userPhoneExcleList = FileUtil.importExcel(file, 1, 1, UserPhoneExcle.class);
        int count=0;
        List<UserPhoneExcle> list=new ArrayList<>();
        for (UserPhoneExcle userPhoneExcle : userPhoneExcleList) {
            UserInfoDomain userInfoDomain=userService.getUserInfo(userPhoneExcle.getMail());
            /*if(null==userInfoDomain){
                UserPhoneExcle user=new UserPhoneExcle();
                user.setJobNo(userPhoneExcle.getJobNo());
                user.setMail(userPhoneExcle.getMail());
                list.add(user);
                System.out.println(userPhoneExcle.getJobNo());
                count++;
            }*/
            if(null!=userInfoDomain){
                userService.updatePhoneNumber(userPhoneExcle.getMail(),userPhoneExcle.getPhoneNumber());
            }

        }

        String downloadSecret = ConstantsUtils.EXCEL_RETURN + UUID.randomUUID().toString().replace("-", "");
        System.out.println("downloadSecret"+downloadSecret);
        System.out.println("count"+count);
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(downloadSecret, list, 5 * 60, TimeUnit.SECONDS);

    }


    @RequestMapping(value = "/downloadReturnExcel")
    @SuppressWarnings("unchecked")
    public void downloadExcel(HttpServletResponse response, @RequestParam(value = "downloadSecret") String secret) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        List<UserPhoneExcle> list = (List<UserPhoneExcle>) valueOperations.get(secret);
        FileUtil.exportExcel(list, "导入结果", "", UserPhoneExcle.class, "结果反馈.xls", response);
    }


}