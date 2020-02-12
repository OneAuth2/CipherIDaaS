package cipher.console.oidc.service.impl.authsettingsImpl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.*;
import cipher.console.oidc.mapper.DoorAuthMapper;
import cipher.console.oidc.service.authsettings.DoorAuthService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class DoorAuthServiceImpl implements DoorAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(DoorAuthServiceImpl.class);

    @Autowired
    private DoorAuthMapper doorAuthMapper;

    @Override
    public Map<String, Object> compileDoorAuth(String companyUuid, IdentityAuthentication identityAuthentication, AccountRegistration accountRegistration,
                                               AccountBinding accountBinding, ForgetPassword forgetPassword) {
        if (StringUtils.isNotEmpty(companyUuid)&&identityAuthentication != null && accountRegistration != null && accountBinding != null && forgetPassword != null) {
            int count = doorAuthMapper.companyUuidCount(companyUuid);
            Gson gson = new Gson();
            IdentitySettingInfo identitySettingInfo = new IdentitySettingInfo();
            identitySettingInfo.setCompany_uuid(companyUuid);
            identitySettingInfo.setType(2);
            identitySettingInfo.setAuth_mode(gson.toJson(identityAuthentication));
            identitySettingInfo.setRegist_flow(gson.toJson(accountRegistration));
            identitySettingInfo.setBinding_flow(gson.toJson(accountBinding));
            identitySettingInfo.setForget_flow(gson.toJson(forgetPassword));
            if (count > 0) {
                try {
                    doorAuthMapper.updateDoorAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter DoorAuthServiceImpl.compileDoorAuth() but update failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getDoorAuthMsg(2));
                }
            } else {
                try {
                    doorAuthMapper.insertDoorAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter DoorAuthServiceImpl.compileDoorAuth() but insert failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getDoorAuthMsg(3));
                }
            }
        }
        return NewReturnUtils.successResponse(ReturnMsg.getDoorAuthMsg(0));
    }

    @Override
    public Map<String, Object> echoDoorAuth(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(companyUuid)) {
            IdentitySettingInfo identitySettingInfo = doorAuthMapper.selectDoorAuthBycompanyUuid(companyUuid);
            if (identitySettingInfo != null) {
                Gson gson = new Gson();
                IdentityAuthentication identityAuthentication = gson.fromJson(identitySettingInfo.getAuth_mode(), IdentityAuthentication.class);
                identityAuthentication.setAuthMethod("用户名+密码");
                AccountRegistration accountRegistration = gson.fromJson(identitySettingInfo.getRegist_flow(), AccountRegistration.class);
                AccountBinding accountBinding = gson.fromJson(identitySettingInfo.getBinding_flow(), AccountBinding.class);
                ForgetPassword forgetPassword = gson.fromJson(identitySettingInfo.getForget_flow(), ForgetPassword.class);
                Map<String, Object> domain = new HashMap<>();
                domain.put("ident_auth",identityAuthentication);
                domain.put("acco_regi",accountRegistration);
                domain.put("acco_bind",accountBinding);
                domain.put("forg_pass",forgetPassword);
                map = NewReturnUtils.successResponse(ReturnMsg.getDoorAuthMsg(4));
                map.put("return_result", domain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getDoorAuthMsg(5));
    }
}
