package cipher.console.oidc.service.impl.authsettingsImpl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.AccountRegistration;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;
import cipher.console.oidc.domain.authsettingsdomain.IdentitySettingInfo;
import cipher.console.oidc.mapper.WirelessAuthMapper;
import cipher.console.oidc.service.authsettings.WirelessAuthService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
@Service
public class WirelessAuthServiceImpl implements WirelessAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(WirelessAuthServiceImpl.class);

    @Autowired
    private WirelessAuthMapper wirelessAuthMapper;

    @Override
    public Map<String, Object> compileWirelessAuth(String companyUuid,
                                                   IdentityAuthentication identityAuthentication,
                                                   AccountRegistration accountRegistration,
                                                   AccountBinding accountBinding) {
        if(StringUtils.isNotEmpty(companyUuid)&&identityAuthentication!=null&&accountRegistration!=null&&accountBinding!=null){
            int count = wirelessAuthMapper.companyUuidCount(companyUuid);
            Gson gson = new Gson();
            IdentitySettingInfo identitySettingInfo = new IdentitySettingInfo();
            identitySettingInfo.setCompany_uuid(companyUuid);
            identitySettingInfo.setType(3);
            identitySettingInfo.setAuth_mode(gson.toJson(identityAuthentication));
            identitySettingInfo.setRegist_flow(gson.toJson(accountRegistration));
            identitySettingInfo.setBinding_flow(gson.toJson(accountBinding));
            if(count>0){
                try {
                    wirelessAuthMapper.updateWirelessAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter WirelessAuthServiceImpl.compileWirelessAuth() but update failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getWireAuthMsg(2));
                }
            }else {
                try {
                    wirelessAuthMapper.insertWirelessAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter WirelessAuthServiceImpl.compileWirelessAuth() but insert failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getWireAuthMsg(3));
                }
            }

        }
        return NewReturnUtils.successResponse(ReturnMsg.getWireAuthMsg(0));
    }

    @Override
    public Map<String, Object> echoWirelessAuth(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(companyUuid)){
            IdentitySettingInfo identitySettingInfo = wirelessAuthMapper.selectWirelessAuthBycompanyUuid(companyUuid);
            if(identitySettingInfo!=null){
                Gson gson = new Gson();
                IdentityAuthentication identityAuthentication = gson.fromJson(identitySettingInfo.getAuth_mode(), IdentityAuthentication.class);
                identityAuthentication.setAuthMethod("用户名+密码");
                AccountRegistration accountRegistration = gson.fromJson(identitySettingInfo.getRegist_flow(), AccountRegistration.class);
                AccountBinding accountBinding = gson.fromJson(identitySettingInfo.getBinding_flow(), AccountBinding.class);
                Map<String, Object> domain = new HashMap<>();
                domain.put("ident_auth",identityAuthentication);
                domain.put("acco_regi",accountRegistration);
                domain.put("acco_bind",accountBinding);
                map = NewReturnUtils.successResponse(ReturnMsg.getWireAuthMsg(4));
                map.put("return_result",domain);
                return map;

            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getWireAuthMsg(5));
    }
}
