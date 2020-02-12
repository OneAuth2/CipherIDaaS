package cipher.console.oidc.domain.web;

public class IconSettingsDomain {
    private Integer id;
    private String companyUuid;
    private Integer type;
    private String title;
    private String leftTitle;
    private String rightTitle;
    private String iconfont;
    private String logo;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyUuid() {
        return companyUuid;
    }

    public void setCompanyUuid(String companyUuid) {
        this.companyUuid = companyUuid;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLeftTitle() {
        return leftTitle;
    }

    public void setLeftTitle(String leftTitle) {
        this.leftTitle = leftTitle;
    }

    public String getRightTitle() {
        return rightTitle;
    }

    public void setRightTitle(String rightTitle) {
        this.rightTitle = rightTitle;
    }

    public String getIconfont() {
        return iconfont;
    }

    public void setIconfont(String iconfont) {
        this.iconfont = iconfont;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    @Override
    public String toString() {
        return "IconSettingsDomain{" +
                "id=" + id +
                ", companyUuid='" + companyUuid + '\'' +
                ", type=" + type +
                ", title='" + title + '\'' +
                ", iconfont='" + iconfont + '\'' +
                ", logo='" + logo + '\'' +
                '}';
    }
}
