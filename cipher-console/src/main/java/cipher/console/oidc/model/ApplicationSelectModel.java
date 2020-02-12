package cipher.console.oidc.model;

/**
 * @Author: zt
 * 应用下拉选择框
 * @Date: 2018/6/4 19:27
 */
public class ApplicationSelectModel {

    /**
     * 应用名称
     */

    private  Integer id;
    private String applicationName;

    /**
     * 应用全局唯一id
     */
    private String appClientId;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }
}
