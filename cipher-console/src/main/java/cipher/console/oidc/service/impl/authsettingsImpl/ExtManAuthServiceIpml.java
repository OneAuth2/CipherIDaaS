package cipher.console.oidc.service.impl.authsettingsImpl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AccountBinding;
import cipher.console.oidc.domain.authsettingsdomain.IdentityAuthentication;
import cipher.console.oidc.domain.authsettingsdomain.IdentitySettingInfo;
import cipher.console.oidc.mapper.ExtManAuthMapper;
import cipher.console.oidc.service.authsettings.ExtManAuthService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class ExtManAuthServiceIpml implements ExtManAuthService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ExtManAuthServiceIpml.class);

    @Autowired
    private ExtManAuthMapper extManAuthMapper;

    @Override
    public Map<String, Object> compileExtManAuth(String companyUuid,
                                                 IdentityAuthentication identityAuthentication,
                                                 AccountBinding accountBinding) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(companyUuid) && identityAuthentication != null && accountBinding != null) {
            int count = extManAuthMapper.companyUuidCount(companyUuid);
            Gson gson = new Gson();
            IdentitySettingInfo identitySettingInfo = new IdentitySettingInfo();
            identitySettingInfo.setCompany_uuid(companyUuid);
            identitySettingInfo.setType(1);
            identitySettingInfo.setAuth_mode(gson.toJson(identityAuthentication));
            identitySettingInfo.setBinding_flow(gson.toJson(accountBinding));
            if (count > 0) {
                try {
                    extManAuthMapper.updateExtManAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter ExtManAuthServiceIpml.compileExtManAuth() but update failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getExtmanAuthMsg(2));
                }
            } else {
                try {
                    extManAuthMapper.insertExtManAuth(identitySettingInfo);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter ExtManAuthServiceIpml.compileExtManAuth() but insert failed,identitySettingInfo=[{}]..==" + identitySettingInfo.toString());
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getExtmanAuthMsg(3));
                }
            }

        }
        return NewReturnUtils.successResponse(ReturnMsg.getExtmanAuthMsg(0));
    }

    @Override
    public Map<String, Object> echoExtManAuth(String companyUuid) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isNotEmpty(companyUuid)) {
            IdentitySettingInfo identitySettingInfo = extManAuthMapper.selectExtManAuthBycompanyUuid(companyUuid);
            if (identitySettingInfo != null) {
                Gson gson = new Gson();
                IdentityAuthentication identityAuthentication = gson.fromJson(identitySettingInfo.getAuth_mode(), IdentityAuthentication.class);
                identityAuthentication.setAuthMethod("用户名+密码");
                AccountBinding accountBinding = gson.fromJson(identitySettingInfo.getBinding_flow(), AccountBinding.class);
                Map<String, Object> domain = new HashMap<>();
                domain.put("ident_auth",identityAuthentication);
                domain.put("acco_bind",accountBinding);
                map = NewReturnUtils.successResponse(ReturnMsg.getExtmanAuthMsg(4));
                map.put("return_result", domain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getExtmanAuthMsg(5));
    }
}
