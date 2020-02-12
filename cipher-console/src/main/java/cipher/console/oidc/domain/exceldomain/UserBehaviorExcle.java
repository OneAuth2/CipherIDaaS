package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

/**
 * Created by 95744 on 2018/6/6.
 */
public class UserBehaviorExcle implements Serializable {
    @ExcelIgnore
    private Integer id;

    @Excel(name = "姓名",orderNum = "0")
    private String  userInfo;

    @Excel(name = "事件类型",orderNum = "1")
    private String type;

    @Excel(name = "事件简介",orderNum = "2")
    private String msg;

    @Excel(name = "时间",orderNum = "3")
    private String createTime;

    @Excel(name = "ip",orderNum = "4")
    private String ip;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(String userInfo) {
        this.userInfo = userInfo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
