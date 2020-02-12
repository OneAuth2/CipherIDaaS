package cipher.console.oidc.domain.web;

import cipher.console.oidc.domain.BaseDomain;

public class ResourceManInfo extends BaseDomain {

    private Integer resourceId;
    private String resourceUrl;
    private String parent;
    private String displaySort;
    private String displayType;
    private String resourceName;
    private String permission;
    private String modifyTime;
    private String createTime;
    private String description;

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public String getDisplaySort() {
        return displaySort;
    }

    public void setDisplaySort(String displaySort) {
        this.displaySort = displaySort;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceUrl() {
        return resourceUrl;
    }

    public void setResourceUrl(String resourceUrl) {
        this.resourceUrl = resourceUrl;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getModifyNime() {
        return modifyTime;
    }

    public void setModifyNime(String modifyNime) {
        this.modifyTime = modifyTime;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }



    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    private String create_time;





}
