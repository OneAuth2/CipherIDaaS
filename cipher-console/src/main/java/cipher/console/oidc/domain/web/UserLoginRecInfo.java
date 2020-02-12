package cipher.console.oidc.domain.web;

import javax.xml.crypto.Data;
import java.io.Serializable;
import java.util.Date;

public class UserLoginRecInfo implements Serializable {

     private Integer id;
     private String accountNumber;
     private String firstFaceLoginTime;
     private Date createTime;
     private Data modifyTime;
     private String uuid;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getFirstFaceLoginTime() {
        return firstFaceLoginTime;
    }

    public void setFirstFaceLoginTime(String firstFaceLoginTime) {
        this.firstFaceLoginTime = firstFaceLoginTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Data getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Data modifyTime) {
        this.modifyTime = modifyTime;
    }

    public UserLoginRecInfo(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public UserLoginRecInfo() {
    }
}
