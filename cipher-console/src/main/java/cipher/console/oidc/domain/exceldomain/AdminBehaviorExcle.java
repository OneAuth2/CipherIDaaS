package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/6/6.
 */
public class AdminBehaviorExcle implements Serializable{
    @ExcelIgnore
    private Integer id;

    @Excel(name = "管理员uid",orderNum = "0")
    private String userId;
    @Excel(name = "姓名",orderNum = "1")
    private String userName;

    @Excel(name = "昵称",orderNum = "2")
    private String nickName;


    @Excel(name = "操作",orderNum = "5")
    private String operation;

    @Excel(name = "详情",orderNum = "6")
    private String msg;

    @Excel(name = "时间",orderNum = "7")
    private String createTime;

    @Excel(name = "IP",orderNum = "8")
    private String ip;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public AdminBehaviorExcle(Integer id, String userId, String userName, String nickName, String operation, String msg, String createTime, String ip) {
        this.id = id;
        this.userId = userId;
        this.userName = userName;
        this.nickName = nickName;
        this.operation = operation;
        this.msg = msg;
        this.createTime = createTime;
        this.ip = ip;
    }

    public AdminBehaviorExcle() {
    }
}
