package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;

public class EquipBehaviorExcle implements Serializable {
    @ExcelIgnore
    private Integer id;

    @Excel(name = "姓名",orderNum = "0")
    private String  userInfo;

    @Excel(name = "事件简介",orderNum = "1")
    private String intro;

    @Excel(name = "事件内容",orderNum = "2")
    private String msg;

    @Excel(name = "时间",orderNum = "3")
    private String createTime;

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

    public String getIntro() {
        return intro;
    }

    public void setIntro(String intro) {
        this.intro = intro;
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
}
