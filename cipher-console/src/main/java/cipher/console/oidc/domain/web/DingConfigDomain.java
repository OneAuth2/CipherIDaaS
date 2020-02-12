package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 钉钉配置信息
 *
 * @Author: zt
 * @Date: 2019-04-29 14:44
 */
public class DingConfigDomain implements Serializable {

    /**
     * 主键 uuid
     */
    private String id;

    /**
     * 公司id
     */
    private String companyUUid;

    /**
     * 对应的扫码信息
     */
    private String scanAppInfoList;

    private List<DingScanInfoDomain> scanInfoDomainList;

    /**
     * 扫码所需的appID
     */
    @Deprecated
    private String scanAppId;

    /**
     * 扫码所需秘钥
     */
    @Deprecated
    private String scanAppSecret;

    /**
     * 公司id
     */
    private String corpId;

    /**
     * 公司秘钥
     */
    private String corpSecret;

    /**
     * 应用appKey,请求接口所需，
     */
    private String appKey;


    /**
     * 应用秘钥，请求接口所需
     */
    private String appSecret;

    /**
     * 属性映射关系
     */
    private String attrMap;


    /**
     * 匹配规则
     * 账号匹配规则,0-手机号,1-邮箱,2-手机号和邮箱,3-手机号或邮箱，满足其中一个条件则认为是一致
     */
    private Integer matchRule;


    private Date createTime;

    private Date modifyTime;


    public String getScanAppInfoList() {
        return scanAppInfoList;
    }

    public void setScanAppInfoList(String scanAppInfoList) {
        this.scanAppInfoList = scanAppInfoList;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyUUid() {
        return companyUUid;
    }

    public void setCompanyUUid(String companyUUid) {
        this.companyUUid = companyUUid;
    }

    public List<DingScanInfoDomain> getScanInfoDomainList() {
        return scanInfoDomainList;
    }

    public void setScanInfoDomainList(List<DingScanInfoDomain> scanInfoDomainList) {
        this.scanInfoDomainList = scanInfoDomainList;
    }

    public String getScanAppId() {
        return scanAppId;
    }

    public void setScanAppId(String scanAppId) {
        this.scanAppId = scanAppId;
    }

    public String getScanAppSecret() {
        return scanAppSecret;
    }

    public void setScanAppSecret(String scanAppSecret) {
        this.scanAppSecret = scanAppSecret;
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

    public String getAppKey() {
        return appKey;
    }

    public void setAppKey(String appKey) {
        this.appKey = appKey;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public String getAttrMap() {
        return attrMap;
    }

    public void setAttrMap(String attrMap) {
        this.attrMap = attrMap;
    }

    public Integer getMatchRule() {
        return matchRule;
    }

    public void setMatchRule(Integer matchRule) {
        this.matchRule = matchRule;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }
}
