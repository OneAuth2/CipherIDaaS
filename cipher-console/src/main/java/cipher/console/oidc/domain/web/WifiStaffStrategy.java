package cipher.console.oidc.domain.web;

import com.google.gson.Gson;

public class WifiStaffStrategy extends WifiStrategyInfo{
    public WifiStaffStrategy(StaffInfoDomain staffInfoDomain,String companyUuid) {
        setValue(new Gson().toJson(staffInfoDomain));
        setCompanyUuid(companyUuid);
        setName("员工策略");
        setCode("staff");
        setId(18);
    }
}
