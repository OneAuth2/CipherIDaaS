package cipher.console.oidc.domain.web;/**
 * @author lqgzj
 * @date 2019-10-11
 */

import java.io.Serializable;
import java.util.List;

/**
 * @Author qiaoxi
 * @Date 2019-10-1116:07
 **/
public class WxConfigDomain implements Serializable {


    private String id;
    private String corpId;
    private String agentId;
    private String corpSecret;
    private String companyUUid;
    private String callbackUrl;
    private Integer matchRule;
    private String attrMap;
    private String scanAppInfoList;
    private List<WxScanInfoDomain> scanInfoDomainList;


    @Override
    public String toString() {
        return "WxConfigDomain{" +
                "id='" + id + '\'' +
                ", corpId='" + corpId + '\'' +
                ", agentId='" + agentId + '\'' +
                ", corpSecret='" + corpSecret + '\'' +
                ", companyUUid='" + companyUUid + '\'' +
                ", callbackUrl='" + callbackUrl + '\'' +
                ", matchRule=" + matchRule +
                ", attrMap='" + attrMap + '\'' +
                ", scanAppInfoList='" + scanAppInfoList + '\'' +
                '}';
    }

    public String getCallbackUrl() {
        return callbackUrl;
    }

    public void setCallbackUrl(String callbackUrl) {
        this.callbackUrl = callbackUrl;
    }

    public String getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(String attrMap) {
        this.attrMap = attrMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCorpId() {
        return corpId;
    }

    public void setCorpId(String corpId) {
        this.corpId = corpId;
    }

    public String getCorpSecret() {
        return corpSecret;
    }

    public void setCorpSecret(String corpSecret) {
        this.corpSecret = corpSecret;
    }

    public Integer getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(Integer matchRule) {
        this.matchRule = matchRule;
    }

    public String getCompanyUUid() {
        return companyUUid;
    }

    public void setCompanyUUid(String companyUUid) {
        this.companyUUid = companyUUid;
    }

    public String getAgentId() {
        return agentId;
    }

    public void setAgentId(String agentId) {
        this.agentId = agentId;
    }

    public String getScanAppInfoList() {
        return scanAppInfoList;
    }

    public void setScanAppInfoList(String scanAppInfoList) {
        this.scanAppInfoList = scanAppInfoList;
    }

    public List<WxScanInfoDomain> getScanInfoDomainList() {
        return scanInfoDomainList;
    }

    public void setScanInfoDomainList(List<WxScanInfoDomain> scanInfoDomainList) {
        this.scanInfoDomainList = scanInfoDomainList;
    }



}
