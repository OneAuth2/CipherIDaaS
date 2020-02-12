package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class AppManageinfo extends BaseDomain {

    public String getModifyTime() {
        return ModifyTime;
    }

    public void setModifyTime(String modifyTime) {
        ModifyTime = modifyTime;
    }

    public String getCreateTime() {
        return CreateTime;
    }

    public void setCreateTime(String createTime) {
        CreateTime = createTime;
    }

    public String getUrlMd5() {
        return UrlMd5;
    }

    public void setUrlMd5(String urlMd5) {
        UrlMd5 = urlMd5;
    }

    public String getResMd5() {
        return ResMd5;
    }

    public void setResMd5(String resMd5) {
        ResMd5 = resMd5;
    }

    public String getResUrl() {
        return ResUrl;
    }

    public void setResUrl(String resUrl) {
        ResUrl = resUrl;
    }

    public String getResSize() {
        return ResSize;
    }

    public void setResSize(String resSize) {
        ResSize = resSize;
    }

    public String getResName() {
        return ResName;
    }

    public void setResName(String resName) {
        ResName = resName;
    }

    public String getResVer() {
        return ResVer;
    }

    public void setResVer(String resVer) {
        ResVer = resVer;
    }

    public String getPackageName() {
        return PackageName;
    }

    public void setPackageName(String packageName) {
        PackageName = packageName;
    }

    public String getUpdateType() {
        return UpdateType;
    }

    public void setUpdateType(String updateType) {
        UpdateType = updateType;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        Content = content;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getCityId() {
        return CityId;
    }

    public void setCityId(String cityId) {
        CityId = cityId;
    }

    public String getProvinceId() {
        return ProvinceId;
    }

    public void setProvinceId(String provinceId) {
        ProvinceId = provinceId;
    }

    public String getChannelId() {
        return ChannelId;
    }

    public void setChannelId(String channelId) {
        ChannelId = channelId;
    }

    public String getAppId() {
        return AppId;
    }

    public void setAppId(String appId) {
        AppId = appId;
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    private Integer  id;
    private String AppId;
    private String ChannelId;
    private String ProvinceId;
    private String CityId;
    private String Title;
    private String Content;
    private String UpdateType;
    private String PackageName;
    private String ResVer;
    private String ResName;
    private String ResSize;
    private String ResUrl;
    private  String ResMd5;
    private String UrlMd5;
    private String CreateTime;
    private String ModifyTime;




}
