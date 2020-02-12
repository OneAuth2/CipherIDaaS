package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

public class NewAdminBehaviorExcle implements Serializable {

    @ExcelIgnore
    private String type;

    @Excel(name = "姓名",orderNum = "1")
    private String userName;

    @Excel(name = "类型",orderNum = "2")
    private String typeStr;

    @Excel(name = "操作",orderNum = "3")
    private String operation;

    @Excel(name = "时间",orderNum = "4")
    private String createTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
