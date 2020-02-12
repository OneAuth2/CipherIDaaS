package cipher.console.oidc.model;

/**
 * @Author: zt
 * 各个应用下能看到该应用的人数对应的model
 * @Date: 2018/5/31 15:41
 */
public class ApplicationUserCountModel {

    /**
     * 应用名称
     */
    private String applicationName;

    /**
     * 能看到该应用的人数
     */
    private int count;

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
