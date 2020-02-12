package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;

import java.io.Serializable;

/**
 * @Author: zt
 * 子账号不带appliClientId的模板
 * @Date: 2018/6/7 10:20
 */
public class SubNoClientIdExcel implements Serializable {

    /**
     * 从账号
     */
    @Excel(name = "从账号(必填)",orderNum = "0")
    private String subAccount;

    /**
     * 主账号
     */

    @Excel(name = "主账号(必填)",orderNum = "1")
    private String accountNumber;

    public SubNoClientIdExcel() {
    }

    public SubNoClientIdExcel(String subAccount, String accountNumber) {
        this.subAccount = subAccount;
        this.accountNumber = accountNumber;
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
}
