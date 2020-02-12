package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

import java.util.Date;

public class WifiPortalWebInfo extends BaseDomain {
    private Integer id;

    private String portalAddress;

    private String description;

    private String title;

    private String copyWriting;

    private String masterPicture;

    private String subPicture;

    private String bottomTitle;



    public String getBottomTitle() {
        return bottomTitle;
    }

    public void setBottomTitle(String bottomTitle) {
        this.bottomTitle = bottomTitle;
    }

    private Date createTime;

    private Date modifyTime;

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
        this.portalAddress = portalAddress == null ? null : portalAddress.trim();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description == null ? null : description.trim();
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getCopyWriting() {
        return copyWriting;
    }

    public void setCopyWriting(String copyWriting) {
        this.copyWriting = copyWriting == null ? null : copyWriting.trim();
    }

    public String getMasterPicture() {
        return masterPicture;
    }

    public void setMasterPicture(String masterPicture) {
        this.masterPicture = masterPicture == null ? null : masterPicture.trim();
    }

    public String getSubPicture() {
        return subPicture;
    }

    public void setSubPicture(String subPicture) {
        this.subPicture = subPicture == null ? null : subPicture.trim();
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