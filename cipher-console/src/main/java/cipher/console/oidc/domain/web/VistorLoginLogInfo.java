package cipher.console.oidc.domain.web;
/**
 * modify by tiankang
 * modify time 2019年3月13日16:36:09
 * 添加了访客类型字段
 */

import cipher.console.oidc.common.CustomDateTimeSerializer;
import cipher.console.oidc.domain.BaseDomain;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import java.util.Date;

public class VistorLoginLogInfo extends BaseDomain{

    private Integer id;
    private String name;
    private String vistorName;
    private String  state;
    private Integer type;
    private String ip;
    private String typeName;//认证类型

    private String operation;

    private String msg;

    private Date createTime;
    private String startTime;
    private String endTime;

    private  String typeStr;
    private Integer visitorType;//无线访客类型 0是员，1是访客,空是全部


    public String getTypeStr() {
        return typeStr;
    }

    public void setTypeStr(String typeStr) {
        this.typeStr = typeStr;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getVistorName() {
        return vistorName;
    }

    public void setVistorName(String vistorName) {
        this.vistorName = vistorName == null ? null : vistorName.trim();
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip == null ? null : ip.trim();
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation == null ? null : operation.trim();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getVisitorType() {
        return visitorType;
    }

    public void setVisitorType(Integer visitorType) {
        this.visitorType = visitorType;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VistorLoginLogInfo{" +
                "id=" + id +
                ", vistorName='" + vistorName + '\'' +
                ", state='" + state + '\'' +
                ", type=" + type +
                ", ip='" + ip + '\'' +
                ", operation='" + operation + '\'' +
                ", msg='" + msg + '\'' +
                ", createTime=" + createTime +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", typeStr='" + typeStr + '\'' +
                ", visitorType=" + visitorType +
                '}';
    }
}
