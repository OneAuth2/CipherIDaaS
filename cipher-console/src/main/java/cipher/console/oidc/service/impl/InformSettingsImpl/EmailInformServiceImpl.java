package cipher.console.oidc.service.impl.InformSettingsImpl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.EmailInformDomain.CreatEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.EmailBasicDomain;
import cipher.console.oidc.domain.EmailInformDomain.RandomEmailDomain;
import cipher.console.oidc.domain.EmailInformDomain.SeedkeyEmailDomain;
import cipher.console.oidc.domain.web.EmailInfoDomain;
import cipher.console.oidc.domain.web.NewUserInfo;
import cipher.console.oidc.mapper.EmailInformMapper;
import cipher.console.oidc.mapper.RegisterApprovalMapper;
import cipher.console.oidc.service.InformSettings.EmailInformService;
import cipher.console.oidc.util.EmailSendTool;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@Service
public class EmailInformServiceImpl implements EmailInformService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmailInformServiceImpl.class);

    @Autowired
    private EmailInformMapper emailInformMapper;

    @Override
    public Map<String, Object> compileEmailInform(EmailBasicDomain emailBasicDomain) {
        if(emailBasicDomain!=null){
            int count = emailInformMapper.companyUuidCount(emailBasicDomain.getCompanyUuid());
            if(count>0){
                try {
                    emailInformMapper.updateEmailInform(emailBasicDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileEmailInform() but update failed,emailBasicDomain=[{}]..==" + emailBasicDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getEmailMsg(2));
                }
            }else {
                try {
                    emailInformMapper.insertEmailInform(emailBasicDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileEmailInform() but insert failed,emailBasicDomain=[{}]..==" + emailBasicDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getEmailMsg(3));
                }
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.getEmailMsg(0));
    }

    @Override
    public Map<String, Object> echoEmailInform(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyUuid)){
            EmailBasicDomain emailBasicDomain = emailInformMapper.selectEmailInformBycompanyUuid(companyUuid);
            if(emailBasicDomain!=null){
                map = NewReturnUtils.successResponse(ReturnMsg.getEmailMsg(4));
                map.put("return_result", emailBasicDomain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getEmailMsg(5));
    }

    @Autowired
    private RegisterApprovalMapper registerApprovalMapper;

    @Override
    public Map<String,Object> emailInformByCompanyUuid(String companyUuid, String title, String writer, String emailAddr) {
        Integer service = registerApprovalMapper.serviceStateBycompanyUuid(companyUuid);
        if(service.equals(1)){
            return NewReturnUtils.failureResponse(ReturnMsg.APPROVALEMAIL);
        }
        if(StringUtils.isNotEmpty(companyUuid)&&StringUtils.isNotEmpty(title) &&StringUtils.isNotEmpty(writer)&&StringUtils.isNotEmpty(emailAddr)){
            return testSendMsg(companyUuid,title,writer,emailAddr);
        }
        return NewReturnUtils.failureResponse(ReturnMsg.EMAILSENDFAILED);
    }

    @Override
    public Map<String, Object> compileRandomEmail(RandomEmailDomain randomEmailDomain) {
        if(randomEmailDomain!= null){
            int count = emailInformMapper.companyUuidCount(randomEmailDomain.getCompanyUuid());
            if(count>0){
                try {
                    emailInformMapper.updateRandomEmail(randomEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileRandomEmail() but update failed,randomEmailDomain=[{}]..==" + randomEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getRandEmailMsg(2));
                }
            }else {
                try {
                    emailInformMapper.insertRandomEmail(randomEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileRandomEmail() but insert failed,randomEmailDomain=[{}]..==" + randomEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getRandEmailMsg(3));
                }
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.getRandEmailMsg(0));
    }

    @Override
    public Map<String, Object> echoRandomEmail(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyUuid)){
            RandomEmailDomain randomEmailDomain = emailInformMapper.selectRandomEmailBycompanyUuid(companyUuid);
            if(randomEmailDomain!=null){
                map = NewReturnUtils.successResponse(ReturnMsg.getRandEmailMsg(4));
                map.put("return_result",randomEmailDomain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getRandEmailMsg(5));
    }

    @Override
    public Map<String, Object> compileCreatEmail(CreatEmailDomain creatEmailDomain) {
        if(creatEmailDomain!=null){
            int count = emailInformMapper.companyUuidCount(creatEmailDomain.getCompanyUuid());
            if(count>0){
                try {
                    emailInformMapper.updateCreatEmail(creatEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileCreatEmail() but update failed,creatEmailDomain=[{}]..==" + creatEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getAcEmailMsg(2));
                }
            }else {
                try {
                    emailInformMapper.insertCreatEmail(creatEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileCreatEmail() but insert failed,creatEmailDomain=[{}]..==" + creatEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getAcEmailMsg(3));
                }
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.getAcEmailMsg(0));
    }

    @Override
    public Map<String, Object> echoCreatEmail(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyUuid)){
            CreatEmailDomain creatEmailDomain = emailInformMapper.selectCreatEmailBycompanyUuid(companyUuid);
            if(creatEmailDomain!=null){
                map = NewReturnUtils.successResponse(ReturnMsg.getAcEmailMsg(4));
                map.put("return_result",creatEmailDomain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getAcEmailMsg(5));
    }

    @Override
    public Map<String, Object> compileSeedkeyEmail(SeedkeyEmailDomain seedkeyEmailDomain) {
        if(seedkeyEmailDomain!=null){
            int count = emailInformMapper.companyUuidCount(seedkeyEmailDomain.getCompanyUuid());
            if(count>0){
                try {
                    emailInformMapper.updateSeedkeyEmail(seedkeyEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileSeedkeyEmail() but update failed,seedkeyEmailDomain=[{}]..==" + seedkeyEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getIssueSeedkey(2));
                }
            }else{
                try {
                    emailInformMapper.insertSeedkeyEmail(seedkeyEmailDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter EmailInformServiceImpl.compileSeedkeyEmail() but insert failed,seedkeyEmailDomain=[{}]..==" + seedkeyEmailDomain.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getIssueSeedkey(3));
                }
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.getIssueSeedkey(0));
    }

    @Override
    public Map<String, Object> echoSeedkeyEmail(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyUuid)){
            SeedkeyEmailDomain seedkeyEmailDomain = emailInformMapper.selectSeedkeyEmailBycompanyUuid(companyUuid);
            if(seedkeyEmailDomain!=null){
                map = NewReturnUtils.successResponse(ReturnMsg.getIssueSeedkey(4));
                map.put("return_result",seedkeyEmailDomain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getIssueSeedkey(5));
    }

    public Map<String, Object> testSendMsg(String companyUuid, String title, String writer, String emailAddr){
        if(StringUtils.isNotEmpty(companyUuid)&&StringUtils.isNotEmpty(title)&&StringUtils.isNotEmpty(writer)&&StringUtils.isNotEmpty(emailAddr)) {
            EmailInfoDomain emailInfoDomain = emailInformMapper.EmailInformByCompanyUuid(companyUuid);
            emailInfoDomain.setTitle(title);
            emailInfoDomain.setDescribes(writer);
            emailInfoDomain.setEmailAddress(emailAddr);
            try {
                EmailSendTool.sendTenxungEmail(emailInfoDomain);
                return NewReturnUtils.successResponse(ReturnMsg.EMAILSENDSUCCESS);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                return NewReturnUtils.failureResponse(ReturnMsg.EMAILSENDFAILED);
            } catch (Exception e) {
                e.printStackTrace();
                return NewReturnUtils.failureResponse(ReturnMsg.EMAILSENDFAILED);
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.EMAILSENDFAILED);
    }

}
