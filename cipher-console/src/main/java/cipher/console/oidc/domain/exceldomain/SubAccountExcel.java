package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

/**
 * @Author: zt
 * @Date: 2018/6/5 20:47
 */
public class SubAccountExcel implements Serializable{


    @ExcelIgnore
    private Integer id;

    /**
     * 应用id
     */
    @Excel(name = "应用ID(必填)",orderNum = "0")
    private String appClientId;

    /**
     * 从账号
     */
    @Excel(name = "从账号(必填)",orderNum = "1")
    private String subAccount;

    /**
     * 主账号
     */

    @Excel(name = "主账号(非必填)",orderNum = "2")
    private String accountNumber;

    public SubAccountExcel() {
    }

    public SubAccountExcel(String appClientId, String subAccount, String accountNumber) {
        this.appClientId = appClientId;
        this.subAccount = subAccount;
        this.accountNumber = accountNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAppClientId() {
        return appClientId;
    }

    public void setAppClientId(String appClientId) {
        this.appClientId = appClientId;
    }

    public String getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(String subAccount) {
        this.subAccount = subAccount;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }


    @Override
    public String toString() {
        return "SubAccountExcel{" +
                "id=" + id +
                ", appClientId='" + appClientId + '\'' +
                ", subAccount='" + subAccount + '\'' +
                ", accountNumber='" + accountNumber + '\'' +
                '}';
    }
}
