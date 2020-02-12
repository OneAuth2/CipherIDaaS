package cipher.console.oidc.model;

import cipher.console.oidc.domain.web.AdMap2LocalDomain;

import java.util.List;

/**
 * @Author: zt
 * @Date: 2018/10/23 16:52
 */
public class AdMap2LocalModel {

   private List<AdMap2LocalDomain> adMap2LocalDomainList;

    public List<AdMap2LocalDomain> getAdMap2LocalDomainList() {
        return adMap2LocalDomainList;
    }

    public void setAdMap2LocalDomainList(List<AdMap2LocalDomain> adMap2LocalDomainList) {
        this.adMap2LocalDomainList = adMap2LocalDomainList;
    }
}
