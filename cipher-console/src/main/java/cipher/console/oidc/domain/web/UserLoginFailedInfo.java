package cipher.console.oidc.domain.web;

import java.io.Serializable;
import java.util.Date;

public class UserLoginFailedInfo implements Serializable {

    /**
     * 账号
     */
    private String            account;

    /**
     * 第一次登录失败时间
     */
    private Date firstFailedTime;

    /**
     * 是否冻结
     */
    private boolean           isFreezed;

    /**
     * 登录失败次数
     */
    private int               failedNum;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getFirstFailedTime() {
        return firstFailedTime;
    }

    public void setFirstFailedTime(Date firstFailedTime) {
        this.firstFailedTime = firstFailedTime;
    }

    public boolean isFreezed() {
        return isFreezed;
    }

    public void setFreezed(boolean isFreezed) {
        this.isFreezed = isFreezed;
    }

    public int getFailedNum() {
        return failedNum;
    }

    public void setFailedNum(int failedNum) {
        this.failedNum = failedNum;
    }
}
