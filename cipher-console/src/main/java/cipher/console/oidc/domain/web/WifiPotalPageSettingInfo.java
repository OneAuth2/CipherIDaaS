package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;

/**
 * @Author: TK
 * desc  添加wife配置界面的实体类
 * @Date: 2019/3/6 20:22
 */
public class WifiPotalPageSettingInfo  extends BaseDomain {
    private Integer id;//protal id

    private String portalAddress;//protal地址

    private String ip;
    private String port;
    private String uri;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    private String description;//protal描述

    private Integer portalType;//Protal类型 0，员工 1 访客

    private String logo;//logo

    private String topText;//顶部文案

    private String loginDraw;//登录插画

    private String loginText;//登录窗体文案

    private String successText;//成功窗体文案

    private Date createTime;//创建时间

    private Date ModifyTime;    //最近一次修改时间

    private String portalNum; //portal序号

    private Integer acId; //对应ac表主键ID

    private Integer radiusId; //对应radius表主键ID

    public Integer getPortalType() {
        return portalType;
    }

    public void setPortalType(Integer portalType) {
        this.portalType = portalType;
    }

    private CiscoInfo ciscoInfo;

    private ArubaInfo arubaInfo;

    private CommonDeviceInfo commonDeviceInfo;

    public CiscoInfo getCiscoInfo() {
        return ciscoInfo;
    }

    public void setCiscoInfo(CiscoInfo ciscoInfo) {
        this.ciscoInfo = ciscoInfo;
    }

    public ArubaInfo getArubaInfo() {
        return arubaInfo;
    }

    public void setArubaInfo(ArubaInfo arubaInfo) {
        this.arubaInfo = arubaInfo;
    }

    public CommonDeviceInfo getCommonDeviceInfo() {
        return commonDeviceInfo;
    }

    public void setCommonDeviceInfo(CommonDeviceInfo commonDeviceInfo) {
        this.commonDeviceInfo = commonDeviceInfo;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPortalAddress() {
        return portalAddress;
    }

    public void setPortalAddress(String portalAddress) {
        this.portalAddress = portalAddress;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getTopText() {
        return topText;
    }

    public void setTopText(String topText) {
        this.topText = topText;
    }

    public String getLoginDraw() {
        return loginDraw;
    }

    public void setLoginDraw(String loginDraw) {
        this.loginDraw = loginDraw;
    }

    public String getLoginText() {
        return loginText;
    }

    public void setLoginText(String loginText) {
        this.loginText = loginText;
    }

    public String getSuccessText() {
        return successText;
    }

    public void setSuccessText(String successText) {
        this.successText = successText;
    }



    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        ModifyTime = modifyTime;
    }

    public String getPortalNum() {
        return portalNum;
    }

    public void setPortalNum(String portalNum) {
        this.portalNum = portalNum;
    }

    public Integer getAcId() {
        return acId;
    }

    public void setAcId(Integer acId) {
        this.acId = acId;
    }

    public Integer getRadiusId() {
        return radiusId;
    }

    public void setRadiusId(Integer radiusId) {
        this.radiusId = radiusId;
    }

    @Override
    public String toString() {
        return "WifiPotalPageSettingInfo{" +
                "id=" + id +
                ", portalAddress='" + portalAddress + '\'' +
                ", description='" + description + '\'' +

                ", logo='" + logo + '\'' +
                ", topText='" + topText + '\'' +
                ", loginDraw='" + loginDraw + '\'' +
                ", loginText='" + loginText + '\'' +
                ", successText='" + successText + '\'' +
                ", createTime=" + createTime +
                ", ModifyTime=" + ModifyTime +
                ", portalNum='" + portalNum + '\'' +
                ", acId=" + acId +
                ", radiusId=" + radiusId +
                '}';
    }
}
