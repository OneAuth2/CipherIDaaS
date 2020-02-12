package cipher.console.oidc.domain.web;

import com.google.gson.Gson;

public class WifiMacDeviceBindingStrategy extends WifiStrategyInfo{

    public WifiMacDeviceBindingStrategy(MacDeviceBinding macDeviceBinding){
        setId(1);
        setValue(new Gson().toJson(macDeviceBinding));
        setCode("mac");
        setName("MAC绑定策略");
    }

    @Override
    public String toString() {
        return super.toString();
    }
}
