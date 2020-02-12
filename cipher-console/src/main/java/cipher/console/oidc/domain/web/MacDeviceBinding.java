package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseParamDomain;

public class MacDeviceBinding extends BaseParamDomain {

    private Integer count;

    public MacDeviceBinding(){
        count = 2;
    }

    public MacDeviceBinding(Integer count) {
        this.count = count;
    }


    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
