package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;
import org.apache.commons.lang.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationInfoDomain extends BaseDomain {


    private String server; //cs 表示需要连接的服务器
    private String data; //需要连接的帐套名称
    private Integer pos; //需要连接的帐套在列表中的排序
    private String loginType;//登录方式 ncClient ，web
    private String CasServerUrl;//cas登入url

    private String CasLogoutUrl;//cas登出url

    public String getCasLogoutUrl() {
        return CasLogoutUrl;
    }

    public void setCasLogoutUrl(String casLogoutUrl) {
        CasLogoutUrl = casLogoutUrl;
    }

    public String getCasServerUrl() {
        return CasServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        CasServerUrl = casServerUrl;
    }

    private Object associatedAccount;

    public Object getAssociatedAccount() {
        return associatedAccount;
    }

    public void setAssociatedAccount(Object associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    private Boolean selected=false;

    public Boolean getSelected() {
        return selected;
    }

    public void setSelected(Boolean selected) {
        this.selected = selected;
    }

    private Integer id;

    /**
     * 应用的全局唯一id
     */
    private String applicationId;
    /**
     *应用名称
     */
    private String applicationName;

    /**
     * 应用描述
     */
    private String applicationDescription;
    /**
     * 应用授权开始日期
     */
    private Date applicationAuthorizedDate;

    /**
     * 应用授权结束日期
     */
    private Date applicationAuthorizedValidDate;

    /**
     * 应用的首页地址,以htp://开头的绝对地址
     */
    private String applicationUrl;


    /**
     * 应用端口
     */
    private Integer applicationPortId;

    /**
     * 应用图标地址
     */
    private String applicationIconUrl;


    /**
     * 应用状态，1可用，2.禁用
     */
    private String applicationStatus;


    /**
     * 一个应用中所有的组的拼接
     */
    private String concatName;


    /**
     * 能看到该应用的人数的统计
     */
    private int userCount;
    /**
     * 安全组
     */
    private String teamName;


    private String groupName;


    private String accountType;



    private Integer isSameAccount;


    private String dsgApiUrl;


    private String xdsgUrl;

    private String jsMethod;


    private int applicationIndex;

    private Object autoSync;

    private String  relayState;

    private String assertionConsumerServiceUrl;

    private String  exitTime;

    private String entityId;

    private String privateKey;

    private String publicKey;

    public String getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(String publicKey) {
        this.publicKey = publicKey;
    }

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getRelayState() {
        return relayState;
    }

    public void setRelayState(String relayState) {
        this.relayState = relayState;
    }

    public String getAssertionConsumerServiceUrl() {
        return assertionConsumerServiceUrl;
    }

    public void setAssertionConsumerServiceUrl(String assertionConsumerServiceUrl) {
        this.assertionConsumerServiceUrl = assertionConsumerServiceUrl;
    }

    public String getExitTime() {
        return exitTime;
    }

    public void setExitTime(String exitTime) {
        this.exitTime = exitTime;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public Object getAutoSync() {
        return autoSync;
    }

    public void setAutoSync(Object autoSync) {
        this.autoSync = autoSync;
    }

    public int getApplicationIndex() {
        return applicationIndex;
    }

    public void setApplicationIndex(int applicationIndex) {
        this.applicationIndex = applicationIndex;
    }

    public String getXdsgUrl() {
        return xdsgUrl;
    }

    public void setXdsgUrl(String xdsgUrl) {
        this.xdsgUrl = xdsgUrl;
    }

    public String getDsgApiUrl() {
        return dsgApiUrl;
    }

    public void setDsgApiUrl(String dsgApiUrl) {
        this.dsgApiUrl = dsgApiUrl;
    }

    public Integer getIsSameAccount() {
        return isSameAccount;
    }

    public void setIsSameAccount(Integer isSameAccount) {
        this.isSameAccount = isSameAccount;
    }

    private static Map<String, Object> stateMap = new HashMap<String, Object>();
    private Map<String, Object> state;


    static {
        stateMap.put("checked", true);
    }

    public void setState() {
        this.state = stateMap;
    }

    public Map<String, Object> getState() {
        return state;
    }

    private List<SecretKeyValue> secretKeyValueList;

    public List<SecretKeyValue> getSecretKeyValueList() {
        return secretKeyValueList;
    }

    public void setSecretKeyValueList(List<SecretKeyValue> secretKeyValueList) {
        this.secretKeyValueList = secretKeyValueList;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public Integer getAppSysId() {

        return appSysId;
    }
    private String configInfo;

    public String getConfigInfo() {
        if(StringUtils.isEmpty(configInfo)){
            configInfo="{}";
        }
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public void setAppSysId(Integer appSysId) {
        this.appSysId = appSysId;
    }

    private Integer appSysId;//系统集成应用的id

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    /**
     * s是否下架
     */
    private int releaseState;
    /**
     * 公司企业标识
     */
    private String companyId;

    /**
     * 创建人的账号
     */
    private String accountNumber;

    private String createTime;

    private String modifyTime;


    private String applicationRedirctUrl;


    private String applicationSecrect;

    private int applicationType;

    private String sord;
    private String sidx;

    private String subAccount;

    private  int subType;


    public String getServer() {
        return server;
    }

    public void setServer(String server) {
        this.server = server;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }


    public Integer getPos() {
        if(null==pos){
            pos=0;
        }
        return pos;
    }

    public void setPos(Integer pos) {
        this.pos = pos;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public int getSubType() {
        return subType;
    }

    public void setSubType(int subType) {
        this.subType = subType;
    }

    public String getSubAccount() {
        if (StringUtils.isNotBlank(subAccount)){
            return subAccount;
        }
        return "";
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getSord() {
        return sord;
    }

    public void setSord(String sord) {
        this.sord = sord;
    }

    public String getSidx() {
        return sidx;
    }

    public void setSidx(String sidx) {
        this.sidx = sidx;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId == null ? null : applicationId.trim();
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName == null ? null : applicationName.trim();
    }

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription == null ? null : applicationDescription.trim();
    }

    public Date getApplicationAuthorizedDate() {
        return applicationAuthorizedDate;
    }

    public void setApplicationAuthorizedDate(Date applicationAuthorizedDate) {
        this.applicationAuthorizedDate = applicationAuthorizedDate;
    }

    public Date getApplicationAuthorizedValidDate() {
        return applicationAuthorizedValidDate;
    }

    public void setApplicationAuthorizedValidDate(Date applicationAuthorizedValidDate) {
        this.applicationAuthorizedValidDate = applicationAuthorizedValidDate;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl == null ? null : applicationUrl.trim();
    }

    public Integer getApplicationPortId() {
        return applicationPortId;
    }

    public void setApplicationPortId(Integer applicationPortId) {
        this.applicationPortId = applicationPortId;
    }

    public String getApplicationIconUrl() {
        return applicationIconUrl;
    }

    public void setApplicationIconUrl(String applicationIconUrl) {
        this.applicationIconUrl = applicationIconUrl == null ? null : applicationIconUrl.trim();
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber == null ? null : accountNumber.trim();
    }

    public String getConcatName() {
        return concatName;
    }

    public void setConcatName(String concatName) {
        this.concatName = concatName;
    }

    public int getUserCount() {
        return userCount;
    }

    public void setUserCount(int userCount) {
        this.userCount = userCount;
    }



    public String getApplicationRedirctUrl() {
        return applicationRedirctUrl;
    }

    public void setApplicationRedirctUrl(String applicationRedirctUrl) {
        this.applicationRedirctUrl = applicationRedirctUrl;
    }

    public String getApplicationSecrect() {
        return applicationSecrect;
    }

    public void setApplicationSecrect(String applicationSecrect) {
        this.applicationSecrect = applicationSecrect;
    }

    public int getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(int applicationType) {
        this.applicationType = applicationType;
    }


    public int getReleaseState() {
        return releaseState;
    }

    public void setReleaseState(int releaseState) {
        this.releaseState = releaseState;
    }

    @Override
    public String getCompanyId() {
        return companyId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }


    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    @Override
    public String toString() {
        return "ApplicationInfoDomain{" +
                "server='" + server + '\'' +
                ", data='" + data + '\'' +
                ", pos=" + pos +
                ", loginType='" + loginType + '\'' +
                ", CasServerUrl='" + CasServerUrl + '\'' +
                ", CasLogoutUrl='" + CasLogoutUrl + '\'' +
                ", associatedAccount=" + associatedAccount +
                ", selected=" + selected +
                ", id=" + id +
                ", applicationId='" + applicationId + '\'' +
                ", applicationName='" + applicationName + '\'' +
                ", applicationDescription='" + applicationDescription + '\'' +
                ", applicationAuthorizedDate=" + applicationAuthorizedDate +
                ", applicationAuthorizedValidDate=" + applicationAuthorizedValidDate +
                ", applicationUrl='" + applicationUrl + '\'' +
                ", applicationPortId=" + applicationPortId +
                ", applicationIconUrl='" + applicationIconUrl + '\'' +
                ", applicationStatus='" + applicationStatus + '\'' +
                ", concatName='" + concatName + '\'' +
                ", userCount=" + userCount +
                ", teamName='" + teamName + '\'' +
                ", groupName='" + groupName + '\'' +
                ", accountType='" + accountType + '\'' +
                ", isSameAccount=" + isSameAccount +
                ", dsgApiUrl='" + dsgApiUrl + '\'' +
                ", xdsgUrl='" + xdsgUrl + '\'' +
                ", jsMethod='" + jsMethod + '\'' +
                ", applicationIndex=" + applicationIndex +
                ", autoSync=" + autoSync +
                ", relayState='" + relayState + '\'' +
                ", assertionConsumerServiceUrl='" + assertionConsumerServiceUrl + '\'' +
                ", exitTime='" + exitTime + '\'' +
                ", entityId='" + entityId + '\'' +
                ", privateKey='" + privateKey + '\'' +
                ", publicKey='" + publicKey + '\'' +
                ", state=" + state +
                ", secretKeyValueList=" + secretKeyValueList +
                ", configInfo='" + configInfo + '\'' +
                ", appSysId=" + appSysId +
                ", releaseState=" + releaseState +
                ", companyId='" + companyId + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                ", createTime='" + createTime + '\'' +
                ", modifyTime='" + modifyTime + '\'' +
                ", applicationRedirctUrl='" + applicationRedirctUrl + '\'' +
                ", applicationSecrect='" + applicationSecrect + '\'' +
                ", applicationType=" + applicationType +
                ", sord='" + sord + '\'' +
                ", sidx='" + sidx + '\'' +
                ", subAccount='" + subAccount + '\'' +
                ", subType=" + subType +
                '}';
    }
}
