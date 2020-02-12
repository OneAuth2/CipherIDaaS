package cipher.console.oidc.service.impl.authsettingsImpl;

import cipher.console.oidc.common.NewReturnUtils;
import cipher.console.oidc.common.ReturnMsg;
import cipher.console.oidc.domain.authsettingsdomain.AuthStrategyDomain;
import cipher.console.oidc.domain.authsettingsdomain.SecondAuthStrategyDomain;
import cipher.console.oidc.mapper.AuthStrategyMapper;
import cipher.console.oidc.service.authsettings.AuthStrategyService;
import com.google.gson.Gson;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AuthStrategyServiceImpl implements AuthStrategyService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AuthStrategyServiceImpl.class);

    @Autowired
    private AuthStrategyMapper authStrategyMapper;

    @Override
    public Map<String, Object> compileAuthStrategy(String uuid,
                                                   String companyUuid,
                                                   AuthStrategyDomain authStrategyDomain,
                                                   SecondAuthStrategyDomain secondAuthStrategyDomain) {
        if(StringUtils.isNotEmpty(companyUuid)&&authStrategyDomain!=null&&secondAuthStrategyDomain!=null){
            Gson gson = new Gson();
            String secondAuth = gson.toJson(secondAuthStrategyDomain);
            authStrategyDomain.setAuth_type(secondAuth);
            authStrategyDomain.setCompanyUuid(companyUuid);
            if(StringUtils.isNotEmpty(uuid)&&uuid.length()==36){
                try {
                    authStrategyDomain.setUuid(uuid);
                    authStrategyMapper.updateAuthStrategy(authStrategyDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter AuthStrategyServiceImpl.compileAuthStrategy() but update failed,companyUuid=[{}]" +
                            ",authStrategyDomain=[{}],secondAuthStrategyDomain=[{}]..==" + new Object[]{companyUuid,authStrategyDomain.toString(),
                            secondAuthStrategyDomain.toString()});
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getAuthStrategyMsg(2));
                }
            }else{
                try {
                    authStrategyDomain.setUuid(UUID.randomUUID().toString());
                    authStrategyMapper.insertAuthStrategy(authStrategyDomain);
                } catch (Exception e) {
                    e.printStackTrace();
                    LOGGER.error("enter AuthStrategyServiceImpl.compileAuthStrategy() but insert failed,companyUuid=[{}]" +
                            ",authStrategyDomain=[{}],secondAuthStrategyDomain=[{}]..==" + new Object[]{companyUuid,authStrategyDomain.toString(),
                            secondAuthStrategyDomain.toString()});
                    LOGGER.error(e.getMessage(), e);
                    return NewReturnUtils.failureResponse(ReturnMsg.getAuthStrategyMsg(3));
                }
            }

        }
        return NewReturnUtils.successResponse(ReturnMsg.getAuthStrategyMsg(0));
    }

    @Override
    public Map<String, Object> echoAuthStrategy(String uuid) {
        Map<String, Object> map = new HashMap<>();
        if(StringUtils.isNotEmpty(uuid)){
            AuthStrategyDomain authStrategyDomain = authStrategyMapper.selectAuthStrategyByUuid(uuid);
            if(authStrategyDomain!=null){
                Gson gson =  new Gson();
                Map<String, Object> domain = new HashMap<>();
                SecondAuthStrategyDomain secondAuthStrategyDomain = gson.fromJson(authStrategyDomain.getAuth_type(), SecondAuthStrategyDomain.class);
                domain.put("strategyName", authStrategyDomain.getStrategyName());
                domain.put("priority", authStrategyDomain.getPriority());
                domain.put("authOrigin", authStrategyDomain.getAuthOrigin());
                domain.put("secondAuth", authStrategyDomain.getSecondAuth());
                domain.put("auth_type", secondAuthStrategyDomain);
                domain.put("effectiveRange", authStrategyDomain.getEffectiveRange());
                map = NewReturnUtils.successResponse(ReturnMsg.getAuthStrategyMsg(4));
                map.put("return_result",domain);
                return map;
            }
        }
        return NewReturnUtils.failureResponse(ReturnMsg.getAuthStrategyMsg(5));
    }
}
