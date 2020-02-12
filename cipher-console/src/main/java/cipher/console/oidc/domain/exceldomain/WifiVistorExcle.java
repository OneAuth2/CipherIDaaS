package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

public class WifiVistorExcle implements Serializable {


    @Excel(name = "姓名", orderNum = "0")
    private String name;

    @Excel(name = "账号", orderNum = "2")
    private String username;

    @Excel(name = "类型", orderNum = "3")
    private String visitorType;

    @Excel(name = "portal描述", orderNum = "4")
    private String description;

    @Excel(name = "上线时间", orderNum = "5")
    private String loginTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(String loginTime) {
        this.loginTime = loginTime;
    }
}
