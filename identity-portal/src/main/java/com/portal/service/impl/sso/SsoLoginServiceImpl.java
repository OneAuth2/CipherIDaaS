package com.portal.service.impl.sso;

import com.portal.commons.Constants;
import com.portal.domain.ApplicationInfoDomain;
import com.portal.domain.PortalApplyInfo;
import com.portal.domain.UserInfoDomain;
import com.portal.enums.ResultCode;
import com.portal.enums.SsoLoginTypeEnum;
import com.portal.saml.OpenSamlImplementation;
import com.portal.service.*;
import com.portal.utils.ResponseUtils;
import com.portal.utils.SpringContextUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.opensaml.saml.saml2.core.AuthnRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.Inflater;
import java.util.zip.InflaterOutputStream;
import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * TODO:
 * create by liuying at 2019/12/10
 *
 * @author liuying
 * @since 2019/12/10 15:05
 */
@Service("ssoLoginService")
public class SsoLoginServiceImpl implements SsoLoginService {
    private static final Logger logger = LoggerFactory.getLogger(SsoLoginServiceImpl.class);

    @Autowired
    private ServiceAdapter serviceController;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private PortalService portalService;

    @Autowired
    private JudgeLimit judgeLimit;

    @Autowired
    private SubAccountRuleService subAccountRuleService;

    @Autowired
    private ApplicationService applicationService;

    @Resource
    private OpenSamlImplementation openSamlImplementation;

    private static Base64 UNCHUNKED_ENCODER = new Base64(0, new byte[]{'\n'});

    /**
     * 判断字符是否是中文
     *
     * @param c 字符
     * @return 是否是中文
     */
    public static boolean isChinese(char c) {
        Character.UnicodeBlock ub = Character.UnicodeBlock.of(c);
        if (ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_COMPATIBILITY_IDEOGRAPHS
                || ub == Character.UnicodeBlock.CJK_UNIFIED_IDEOGRAPHS_EXTENSION_A
                || ub == Character.UnicodeBlock.GENERAL_PUNCTUATION
                || ub == Character.UnicodeBlock.CJK_SYMBOLS_AND_PUNCTUATION
                || ub == Character.UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
            return true;
        }
        return false;
    }

    public static boolean isMessyCode(String strName) {
        Pattern p = Pattern.compile("\\s*|t*|r*|n*");
        Matcher m = p.matcher(strName);
        String after = m.replaceAll("");
        String temp = after.replaceAll("\\p{P}", "");
        char[] ch = temp.trim().toCharArray();
        float chLength = ch.length;
        float count = 0;
        for (int i = 0; i < ch.length; i++) {
            char c = ch[i];
            if (!Character.isLetterOrDigit(c)) {
                if (!isChinese(c)) {
                    count = count + 1;
                }
            }
        }
        float result = count / chLength;
        if (result > 0.4) {
            return true;
        } else {
            return false;
        }

    }

    public AuthnRequest getAuthReqest(String saml){

        //decode saml的参数
        BASE64Decoder decoder = new BASE64Decoder();
        String samlEncode;
        try {
            samlEncode = new String(decoder.decodeBuffer(saml), "UTF-8");
        } catch (IOException e) {
            return null;
        }
        //判断是否乱码 如果乱码采用另一种方式
        if (isMessyCode(samlEncode)){
            byte[] bytes=UNCHUNKED_ENCODER.decode(saml);
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            InflaterOutputStream iout = new InflaterOutputStream(out, new Inflater(true));
            try {
                iout.write(bytes);
                iout.finish();
            } catch (IOException e) {
                logger.info("samlRequst转String 失败 ");
                logger.error(e.getMessage(), e);
                return null;
            }

            samlEncode= new String(out.toByteArray(), UTF_8);
        }
        //生成saml的Authrequest对象
        AuthnRequest authnRequest = (AuthnRequest) openSamlImplementation.transferXML2SAMLObject(samlEncode);
        return authnRequest;
    }

    @Override
    public void sendBaseInfo(String serviceUrl, String uuid, String companyUuid, String type) {
        logger.info(MessageFormat.format("SSO单点登录参数：serviceUrl:【{0}】,uuid:【{1}】,companyUuid:【{2}】,type:【{3}】",
                serviceUrl, uuid, companyUuid, type));

        if (StringUtils.isEmpty(serviceUrl) || StringUtils.isEmpty(uuid)) {
            serviceController.sendResultMap(ResultCode.APPLICATION_NO_SKIP_URL, Constants.TYPE, type);
            return;
        }

        ApplicationInfoDomain applicationInfoDomain;
        //通过appid获取应用的信息并判断应用是否存在
        if ("CAS".equals(type)){
            applicationInfoDomain = getApplication(serviceUrl);
        }else if ("RDSG".equals(type)){
            String appId= applicationService.getIdByAppHost(serviceUrl);
            applicationInfoDomain=applicationService.getApplicationById(appId);
        } else if (!type.equals("SAML")){
            applicationInfoDomain = getApplication(serviceUrl);
        }
        else {
            //获取应用信息
            AuthnRequest authnRequest=getAuthReqest(serviceUrl);
            if (authnRequest ==null){
                serviceController.sendResultMap(ResultCode.SAML_REGIST_IS_ERROR, Constants.TYPE, type);
                return;
            }
            String acs= authnRequest.getAssertionConsumerServiceURL();
            applicationInfoDomain=applicationService.getApplicationInfoByAssertionConsumerServiceURL(acs);
        }
        if (applicationInfoDomain == null) {
            serviceController.sendResultMap(ResultCode.APPLICATION_NO_URL, Constants.TYPE, type);
            return;
        }

        //判断该账号是否拥有该应用的权限
        Boolean limit = judgeLimit.isOwnAppLimit(uuid, applicationInfoDomain.getId().toString());
        if (!limit) {
            serviceController.sendResultMap(ResultCode.USER_NO_LIMIT, Constants.TYPE, type);
            return;
        }

        //根据uuId获取userDomain
        UserInfoDomain userInfoDomain = userInfoService.getUserInfoByUUid(uuid);
        //获取从账号的配置规则

        PortalApplyInfo portalApplyInfo = portalService.selectPortalInfo(new PortalApplyInfo(applicationInfoDomain.getApplicationId()));
        //如果参数为空直接返回应用从账号规则未设置
        if (portalApplyInfo == null || StringUtils.isEmpty(portalApplyInfo.getAssociatedAccount())) {
            serviceController.sendResultMap(ResultCode.APPLICATION_RULE_NOT_EXIT, Constants.TYPE, type);
            return;
        }


        //根据应用类型获取应用跳转地址
        SsoLoginService ssoLoginService = (SsoLoginService) SpringContextUtil.getBean(SsoLoginTypeEnum.getSsoLoginType(type));
        ssoLoginService.sendRedirectUrl(serviceUrl,userInfoDomain,portalApplyInfo);

    }

    @Override
    public void sendRedirectUrl(String serviceUrl, UserInfoDomain userInfoDomain, PortalApplyInfo portalApplyInfo) {

    }


    /**
     * 根据应用地址获取应用信息
     *
     * @auth liuying
     */
    public ApplicationInfoDomain getApplication(String serviceUrl) {
        //入参校验
        if (serviceUrl == null) {
            return null;
        }
        String appId = applicationService.getIdFromApplicationInfoByServiceUrl(serviceUrl);
        //appid直接为空
        if (StringUtils.isEmpty(appId)) {
            return null;
        }
        //通过appid获取应用的信息并判断应用是否存在
        return applicationService.getApplicationById(appId);
    }
}
