package cipher.console.oidc.model;

public class ResSelectModel {
    /**
     * 权限名称
     */

    private  Integer resourceId;
    private String resourceName;

    /**
     * 应用全局唯一id
     */
    private String authId;

    public Integer getResourceId() {
        return resourceId;
    }

    public void setResourceId(Integer resourceId) {
        this.resourceId = resourceId;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public String getAuthId() {
        return authId;
    }

    public void setAuthId(String authId) {
        this.authId = authId;
    }
}
