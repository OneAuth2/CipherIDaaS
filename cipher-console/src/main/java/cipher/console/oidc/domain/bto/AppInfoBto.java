package cipher.console.oidc.domain.bto;

import cipher.console.oidc.domain.web.ApplicationInfoDomain;

/**
 * TODO:
 * create by liuying at 2019/9/17
 *
 * @author liuying
 * @since 2019/9/17 15:21
 */
public class AppInfoBto {
    private int id;
    private String server; //cs 表示需要连接的服务器
    private String data; //需要连接的帐套名称
    private int pos; //需要连接的帐套在列表中的排序
    private String CasServerUrl;//cas登入url
    private Object associatedAccount;

    private String loginType; //用友Nc登录的类型

    /**
     * 应用的全局唯一id
     */
    private String applicationId;


    private String applicationSecrect;


    /**
     *应用名称
     */
    private String applicationName;

    /**
     * 应用的首页地址,以htp://开头的绝对地址
     */
    private String applicationUrl;


    /**
     * 应用图标地址
     */
    private String applicationIconUrl;


    /**
     * 应用状态，1可用，2.禁用
     */
    private String applicationStatus;

    private String xdsgUrl;

    private int applicationIndex;

    private Object autoSync;

    private String configInfo;

    private Integer appSysId;

    private Integer applicationType;

    private String accountType;

    private String  relayState;

    private String assertionConsumerServiceUrl;

    private String  exitTime;

    private String entityId;

    private String privateKey;

    private String applicationDescription;

    public String getApplicationDescription() {
        return applicationDescription;
    }

    public void setApplicationDescription(String applicationDescription) {
        this.applicationDescription = applicationDescription;
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

    public String getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(String privateKey) {
        this.privateKey = privateKey;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getConfigInfo() {
        return configInfo;
    }

    public void setConfigInfo(String configInfo) {
        this.configInfo = configInfo;
    }

    public Integer getAppSysId() {
        return appSysId;
    }

    public void setAppSysId(Integer appSysId) {
        this.appSysId = appSysId;
    }

    public Integer getApplicationType() {
        return applicationType;
    }

    public void setApplicationType(Integer applicationType) {
        this.applicationType = applicationType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public int getPos() {
        return pos;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public String getCasServerUrl() {
        return CasServerUrl;
    }

    public void setCasServerUrl(String casServerUrl) {
        CasServerUrl = casServerUrl;
    }

    public Object getAssociatedAccount() {
        return associatedAccount;
    }

    public void setAssociatedAccount(Object associatedAccount) {
        this.associatedAccount = associatedAccount;
    }

    public String getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(String applicationId) {
        this.applicationId = applicationId;
    }

    public String getApplicationSecrect() {
        return applicationSecrect;
    }

    public void setApplicationSecrect(String applicationSecrect) {
        this.applicationSecrect = applicationSecrect;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getApplicationIconUrl() {
        return applicationIconUrl;
    }

    public void setApplicationIconUrl(String applicationIconUrl) {
        this.applicationIconUrl = applicationIconUrl;
    }

    public String getApplicationStatus() {
        return applicationStatus;
    }

    public void setApplicationStatus(String applicationStatus) {
        this.applicationStatus = applicationStatus;
    }

    public String getXdsgUrl() {
        return xdsgUrl;
    }

    public void setXdsgUrl(String xdsgUrl) {
        this.xdsgUrl = xdsgUrl;
    }

    public int getApplicationIndex() {
        return applicationIndex;
    }

    public void setApplicationIndex(int applicationIndex) {
        this.applicationIndex = applicationIndex;
    }

    public Object getAutoSync() {
        return autoSync;
    }

    public void setAutoSync(Object autoSync) {
        this.autoSync = autoSync;
    }

    public String getLoginType() {
        return loginType;
    }

    public void setLoginType(String loginType) {
        this.loginType = loginType;
    }

    public AppInfoBto(ApplicationInfoDomain applicationInfoDomain) {
        this.id=applicationInfoDomain.getId();
        this.applicationName=applicationInfoDomain.getApplicationName();
        this.applicationId=applicationInfoDomain.getApplicationId();
        this.applicationSecrect=applicationInfoDomain.getApplicationSecrect();
        this.applicationIconUrl=applicationInfoDomain.getApplicationIconUrl();
        this.applicationIndex=applicationInfoDomain.getApplicationIndex();
        this.applicationStatus=applicationInfoDomain.getApplicationStatus();
        this.applicationUrl=applicationInfoDomain.getApplicationUrl();
        this.CasServerUrl=applicationInfoDomain.getCasServerUrl();
        this.server=applicationInfoDomain.getServer();
        this.pos=applicationInfoDomain.getPos();
        this.data=applicationInfoDomain.getData();
        this.xdsgUrl=applicationInfoDomain.getXdsgUrl();
        this.associatedAccount=applicationInfoDomain.getAssociatedAccount();
        this.autoSync=applicationInfoDomain.getAutoSync();
        this.appSysId=applicationInfoDomain.getAppSysId();
        this.applicationType=applicationInfoDomain.getApplicationType();
        this.configInfo=applicationInfoDomain.getConfigInfo();
        this.accountType=applicationInfoDomain.getAccountType();
        this.loginType=applicationInfoDomain.getLoginType();
        this.relayState=applicationInfoDomain.getRelayState();
        this.assertionConsumerServiceUrl=applicationInfoDomain.getAssertionConsumerServiceUrl();
        this.entityId=applicationInfoDomain.getEntityId();
        this.exitTime=applicationInfoDomain.getExitTime();
        this.privateKey=applicationInfoDomain.getPrivateKey();
        this.applicationDescription=applicationInfoDomain.getApplicationDescription();
    }


}
