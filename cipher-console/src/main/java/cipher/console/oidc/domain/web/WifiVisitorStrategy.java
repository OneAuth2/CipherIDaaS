package cipher.console.oidc.domain.web;

import com.google.gson.Gson;

public class WifiVisitorStrategy extends WifiStrategyInfo{

    public WifiVisitorStrategy(VisitorInfoDomain visitorInfoDomain,String companyUuid){
        setValue(new Gson().toJson(visitorInfoDomain));
        setCompanyUuid(companyUuid);
        setCode("visitor");
        setName("访客策略");
        setId(19);
    }

}
