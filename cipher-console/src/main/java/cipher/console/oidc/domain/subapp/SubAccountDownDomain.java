package cipher.console.oidc.domain.subapp;

import java.util.Date;

/**
 * TODO:
 * create by liuying at 2019/8/5
 *
 * @author liuying
 * @since 2019/8/5 9:28
 */
public class SubAccountDownDomain {

    private int id;
    private String userId;
    private int appId;
    private String createTime;
    private String modifyTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getAppId() {
        return appId;
    }

    public void setAppId(int appId) {
        this.appId = appId;
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
}
