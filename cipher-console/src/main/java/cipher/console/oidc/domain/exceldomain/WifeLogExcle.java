package cipher.console.oidc.domain.exceldomain;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author: TK
 * 无线日志的excel实体类
 * @Date: 2019/3/14 14:14
 */
public class WifeLogExcle implements Serializable {
    @ExcelIgnore
    private Integer id;
    @Excel(name = "姓名", orderNum = "1")
    private String visitorName;
    @Excel(name = "类型", orderNum = "2")
    private String visitorType;
    @Excel(name = "portal描述", orderNum = "3")
    private String msg;
    @Excel(name = "上线时间", orderNum = "4")
    private Date createTime;

    public WifeLogExcle() {
    }

    public WifeLogExcle(Integer id,String vistorName, String visitorType, String msg,Date createTime) {
        this.id=id;
        this.visitorName=vistorName;
        this.visitorType=visitorType;
        this.msg=msg;
        this.createTime=createTime;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVisitorType() {
        if (visitorType.equals("0")){
            visitorType="员工";
        }else  if (visitorType.equals("1")){
            visitorType="访客";
        }
        return visitorType;
    }

    public String getCreateTime() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String date1= sdf.format(createTime);
        return date1;
    }

    public String getVisitorName() {
        return visitorName;
    }

    public void setVisitorName(String visitorName) {
        this.visitorName = visitorName;
    }

    public void setVisitorType(String visitorType) {
        this.visitorType = visitorType;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
