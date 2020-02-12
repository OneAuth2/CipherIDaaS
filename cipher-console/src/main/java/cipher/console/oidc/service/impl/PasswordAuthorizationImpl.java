package cipher.console.oidc.service.impl;

import cipher.console.oidc.common.CacheKey;
import cipher.console.oidc.common.ConstansUMP;
import cipher.console.oidc.common.ConstantsCMP;
import cipher.console.oidc.common.PasswordTypeCode;
import cipher.console.oidc.domain.web.PasswordAuthorizationMagDomain;
import cipher.console.oidc.domain.web.PasswordSettingDomain;
import cipher.console.oidc.domain.web.UserBehaviorInfo;
import cipher.console.oidc.domain.web.UserLoginRecInfo;
import cipher.console.oidc.mapper.AdminBehaviorInfoMapper;
import cipher.console.oidc.mapper.PasswordAuthorizationMapper;
import cipher.console.oidc.mapper.PasswordSettingMapper;
import cipher.console.oidc.mapper.UserLoginRecMapper;
import cipher.console.oidc.publistener.UserBehaviorPublistener;
import cipher.console.oidc.service.PasswordAuthorizationService;
import cipher.console.oidc.service.PasswordSettingService;
import cipher.console.oidc.service.UserBehaviorInfoService;
import cipher.console.oidc.service.UserService;
import cipher.console.oidc.util.IpUtil;
import cipher.console.oidc.util.aes.AES;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class PasswordAuthorizationImpl implements PasswordAuthorizationService{

    @Autowired
    PasswordAuthorizationMapper passwordAuthorizationMapper;

    @Autowired
    PasswordSettingMapper passwordSettingMapper;

    @Autowired
    UserLoginRecMapper userLoginRecMapper;

    @Autowired
    private AdminBehaviorInfoMapper adminBehaviorInfoMapper;
    @Autowired
    private UserBehaviorInfoService userBehaviorInfoService;
    @Autowired
    private RedisTemplate<String, String> redisTemplate;


    @Autowired
    private PasswordSettingService passwordSettingService;

    @Autowired
    private UserBehaviorPublistener userBehaviorPublistener;


    @Override
    public void resetAccountPassword(String uuid,String companyId,String newPwd,int logFlag) {
        boolean result = redisTemplate.hasKey(CacheKey.getPwdByUserNameCacheKey(uuid));
        if (result == true) {
            redisTemplate.delete(CacheKey.getPwdByUserNameCacheKey(uuid));
        }

        //1.5.5之前的版本
        /*PasswordSettingDomain  passwordSettingDomain=passwordSettingMapper.queryPasswordSetting(companyId);
        if(null!=passwordSettingDomain&& StringUtils.isNotEmpty(passwordSettingDomain.getInit())){
            String initPwd= AES.encryptToBase64(passwordSettingDomain.getInit(), ConstantsCMP.AES_KEY);
            passwordAuthorizationMapper.resetAccountPassword(uuid,initPwd);
            UserLoginRecInfo userLoginRecInfo=new UserLoginRecInfo();
            userLoginRecInfo.setUuid(uuid);
            UserLoginRecInfo record=userLoginRecMapper.selectUserLoginRecInfo(userLoginRecInfo);
            if(null!=record){
                userLoginRecInfo.setFirstFaceLoginTime(null);
                userLoginRecMapper.updateUserLoginRecInfo(userLoginRecInfo);
            }
        }*/
        if(StringUtils.isNotEmpty(newPwd)){
            //重置密码
            String initPwd= AES.encryptToBase64(newPwd, ConstantsCMP.AES_KEY);
            passwordAuthorizationMapper.resetAccountPassword(uuid,initPwd);
            //重置密码后，首次登录是否需要修改密码
            if(logFlag==0){
                UserLoginRecInfo userLoginRecInfo=new UserLoginRecInfo();
                userLoginRecInfo.setUuid(uuid);
                UserLoginRecInfo record=userLoginRecMapper.selectUserLoginRecInfo(userLoginRecInfo);
                if(null!=record){
                    userLoginRecInfo.setFirstFaceLoginTime(null);
                    userLoginRecMapper.updateUserLoginRecInfo(userLoginRecInfo);
                }
            }
        }
    }

    @Override
    public void insertAccountPassword(String password,String uuid) {
        PasswordAuthorizationMagDomain passwordAuthorizationMagDomain = new PasswordAuthorizationMagDomain(uuid, password);
        passwordAuthorizationMapper.insertAccountPassword(passwordAuthorizationMagDomain);
    }

    @Override
    public void insertAccountPasswordExcel(List<PasswordAuthorizationMagDomain> passwordMagDomains) {
        passwordAuthorizationMapper.insertAccountPasswordExcel(passwordMagDomains);
    }

    @Override
    public void deleteAccountPassword(String uuid) {
        passwordAuthorizationMapper.deleteAccountPassword(uuid);
    }

    @Override
    public PasswordTypeCode updatePassword(String uuid, String password,String companyId,int logFlag) {
        Map<String,Object> map=new HashMap<>();
        //修改密码后首次是否修改
        if(logFlag==0){
            userLoginRecMapper.deleteUserLoginRecInfo(uuid);
        }
        Integer pwdType=Integer.valueOf(passwordSettingService.queryPasswordSetting(companyId).getPasswordType());
        boolean flag=true;
        String regEx="[`~!@#$%^&*()+=|{}':;',\\[\\].<>/?~！@#￥%……&*（）——+|{}【】‘；：”“’。，、？]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher(password);
        password=m.replaceAll("").trim();
        switch (pwdType) {
            case 1:
                flag=checkPasswordOne(password);
                if(flag==false){
                    return PasswordTypeCode.EASY;
                }
                break;
            case 2:
                flag=checkPasswordTwo(password);
                if(flag==false){
                    return PasswordTypeCode.MEDIUM;
                }
                break;
            default:
                flag=checkPasswordThird(password);;
                if(flag==false){
                    return PasswordTypeCode.COMPLEX;
                }
                break;

        }

        return PasswordTypeCode.SUCCESS;
    }





    /**
     *配置8位及8位以上密码
     */

    public static boolean checkPasswordOne(String password){
        Pattern Password_Pattern = Pattern.compile("(.{6,20})$");
        Matcher matcher = Password_Pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 配置8位及8位以上密码，至少包含数字+大写字母+小写字母
     */

    public static boolean checkPasswordThird(String value) {
        if( null == value ){
            return false;
        }else{
            String passWord = (String) value;
            if(passWord.matches("\\w+")){
                String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).*$";
                return  ( passWord.length()>=6  && passWord.length() <=20  && passWord.matches(regex) )  ? true : false;
            }else{
                return false;
            }
        }
    }


    /**
     * 配置8位及8位以上密码，至少包含数字和字母
     */

    public static boolean checkPasswordTwo(String value) {
        if( null == value ){
            return false;
        }else{
            String passWord = (String) value;
            if(passWord.matches("\\w+")){
                String regex = "^(?![0-9]+$)(?![a-zA-Z]+$)[0-9A-Za-z]{6,20}$";
                return  ( passWord.length()>=6  && passWord.length() <=20  && passWord.matches(regex) )  ? true : false;
            }else{
                return false;
            }
        }
    }




}
