package cipher.console.oidc.domain.web;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/9/29.
 */
public class AuthReqParam implements Serializable{
    private int groupId;
    private int parentId;
    private String accountNumber;

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(int parentId) {
        this.parentId = parentId;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }
}
